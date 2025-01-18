package io.vertx.codegen.processor;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.processor.type.ClassKind;
import io.vertx.codegen.processor.type.MapperInfo;
import io.vertx.codegen.processor.type.TypeMirrorFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGen {

  private static final List<ModelProvider> PROVIDERS;

  static {
    List<ModelProvider> list = new ArrayList<>();
    list.add(ModelProvider.CLASS);
    list.add(ModelProvider.DATA_OBJECT);
    list.add(ModelProvider.ENUM);
    try {
      ServiceLoader<ModelProvider> loader = ServiceLoader.load(ModelProvider.class, ModelProvider.class.getClassLoader());
      for (ModelProvider aLoader : loader) {
        list.add(aLoader);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    PROVIDERS = list;
  }

  final static Map<ProcessingEnvironment, ClassLoader> loaderMap = new WeakHashMap<>();

  private final Map<String, Map<String, Map.Entry<TypeElement, Model>>> models = new HashMap<>();
  private final Set<TypeElement> all = new HashSet<>();

  private final HashMap<String, PackageElement> modules = new HashMap<>();
  private final ProcessingEnvironment env;
  private final Elements elementUtils;
  private final Types typeUtils;
  private final TypeMirrorFactory tmf;
  private final List<Converter> converters = new ArrayList<>();

  public CodeGen(ProcessingEnvironment env) {
    this.env = env;
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.tmf = new TypeMirrorFactory(elementUtils, typeUtils);
  }

  public void init(RoundEnvironment round, ClassLoader loader) {
    loaderMap.put(env, loader);
    Predicate<Element> implFilter = elt -> {
      try {
        // Since JDK 16, method elementUtils.getPackageOf(Element)
        // can return null instead of throwing a NPE.
        return Optional.ofNullable(elementUtils.getPackageOf(elt))
                       .map(pkg -> pkg.getQualifiedName().toString())
                       .map(fqn -> !fqn.contains(".impl.") && !fqn.endsWith(".impl"))
                       .orElse(true);
      } catch (NullPointerException e) {
        // This might happen with JDK 11 using modules and it looks
        // like a JDK bug
        return true;
      }
    };

    // Process serializers
    converters.forEach(converter -> {
      TypeMirror type = null;
      if (converter.className != null) {
        TypeElement typeElt = elementUtils.getTypeElement(converter.className);
        if (typeElt != null) {
          type = typeElt.asType();
        }
      }
      TypeElement converterElt = elementUtils.getTypeElement(converter.converter);
      if (converterElt == null) {
        throw new RuntimeException("Cannot load " + converter.converter + " converter class");
      }
      TypeMirror converterType = converterElt.asType();
      for (int i = 0;i < converter.selectors.size();i++) {
        Resolved next = resolveMember(converterElt, converterType, converter.selectors.get(i));
        Element nextElt = next.element;
        Set<Modifier> modifiers = nextElt.getModifiers();
        if (!modifiers.contains(Modifier.PUBLIC)) {
          throw new GenException(converterElt, "Annotated mapper element must be public");
        }
        if (i == 0 && !modifiers.contains(Modifier.STATIC)) {
          throw new GenException(converterElt, "Annotated mapper element must be static");
        }
        converterType = next.type;
      }
      if (converterType.getKind() == TypeKind.EXECUTABLE) {
        ExecutableType execType = (ExecutableType) converterType;
        processConverter(converterElt, type, converter.selectors, execType);
      }
    });
    round.getRootElements().stream()
      .filter(implFilter)
      .filter(elt -> elt instanceof TypeElement)
      .map(elt -> (TypeElement)elt).forEach(te -> {
      for (ModelProvider provider : PROVIDERS) {
        Model model = provider.getModel(env, tmf, te);
        if (model != null) {
          String kind = model.getKind();
          all.add(te);
          Map<String, Map.Entry<TypeElement, Model>> map = models.computeIfAbsent(kind, a -> new HashMap<>());
          ModelEntry<TypeElement, Model> entry = new ModelEntry<>(te, () -> model);
          map.put(Helper.getNonGenericType(te.asType().toString()), entry);
        }
      }
    });
    round.getElementsAnnotatedWith(ModuleGen.class).
      stream().
      map(element -> (PackageElement) element).
      forEach(element -> modules.put(element.getQualifiedName().toString(), element));
  }

  private static class Resolved {
    final Element element;
    final TypeMirror type;
    private Resolved(Element element, TypeMirror type) {
      this.element = element;
      this.type = type;
    }
  }

  /**
   * Resolve the {@code member} of a given {@code type}
   *
   * @param elt for reporting properly error
   * @param type the type to resolve the member from
   * @param member the member string name to resolve
   * @return the resolved member
   */
  private Resolved resolveMember(Element elt, TypeMirror type, String member) {
    TypeKind kind = type.getKind();
    if (kind == TypeKind.DECLARED) {
      // For now only support this
      DeclaredType declaredType = (DeclaredType) type;
      return declaredType
        .asElement()
        .getEnclosedElements()
        .stream()
        .filter(e -> e.getSimpleName().toString().equals(member))
        .findFirst()
        .map(memberElt -> new Resolved(memberElt, typeUtils.asMemberOf(declaredType, memberElt)))
        .orElseThrow(() -> new GenException(elt, "Cannot find member " + member + " of type " + type));
    }
    throw new GenException(elt, "Only declared element are supported");
  }

  private void processConverter(TypeElement converterElt, TypeMirror dataObjectType, List<String> selectors, ExecutableType methodType) {
    if (methodType.getParameterTypes().size() < 1) {
      throw new GenException(converterElt, "Annotated method mapper cannot have empty arguments");
    }
    if (methodType.getParameterTypes().size() > 1) {
      throw new GenException(converterElt, "Annotated method mapper must have one argument");
    }
    TypeMirror paramType = methodType.getParameterTypes().get(0);
    if (paramType.toString().equals("java.lang.CharSequence")) {
      // Special handling for ZonedDateTime.parse(CharSequence) and such
      // this only works for parameters
      paramType = elementUtils.getTypeElement("java.lang.String").asType();
    }
    TypeMirror returnType = methodType.getReturnType();
    ClassKind paramKind = ClassKind.getKind(paramType.toString(), false);
    ClassKind returnKind = ClassKind.getKind(returnType.toString(), false);
    if (paramKind.json || paramKind.basic || paramKind == ClassKind.OBJECT) {
      MapperInfo mapper = new MapperInfo();
      mapper.setQualifiedName(converterElt.getQualifiedName().toString());
      mapper.setTargetType(tmf.create(paramType));
      mapper.setSelectors(selectors);
      mapper.setKind(MapperKind.STATIC_METHOD);
      if (!typeUtils.isSubtype(returnType, dataObjectType)) {
        throw new GenException(converterElt, methodType + " return should be the same or extend " + dataObjectType);
      }
      tmf.addDataObjectDeserializer(converterElt, dataObjectType, mapper);
    } else if (returnKind.json || returnKind.basic || returnKind == ClassKind.OBJECT) {
      MapperInfo mapper = new MapperInfo();
      mapper.setQualifiedName(converterElt.getQualifiedName().toString());
      mapper.setTargetType(tmf.create(returnType));
      mapper.setSelectors(selectors);
      mapper.setKind(MapperKind.STATIC_METHOD);
      if (!typeUtils.isSubtype(dataObjectType, paramType)) {
        throw new GenException(converterElt, methodType + " parameter should be the same or be a super type of " + dataObjectType);
      }
      tmf.addDataObjectSerializer(converterElt, dataObjectType, mapper);
    } else {
      throw new GenException(converterElt, "Mapper method does not declare a JSON type");
    }
  }

  public static class Converter {
    public final String className;
    public final String converter;
    public final List<String> selectors;
    public Converter(String className, String converter, List<String> selectors) {
      this.className = className;
      this.converter = converter;
      this.selectors = selectors;
    }

    @Override
    public String toString() {
      return className + "=" + converter + "#" + selectors;
    }
  }

  public void registerConverter(Converter b) {
    converters.add(b);
  }

  public void registerConverter(String className, String serializer, String... selectors) {
    converters.add(new Converter(className, serializer, Arrays.asList(selectors)));
  }

  public Stream<Map.Entry<? extends Element, ? extends Model>> getModels() {
    Stream<Map.Entry<? extends Element, ? extends Model>> s = Stream.empty();
    for (Map<String, Map.Entry<TypeElement, Model>> m : models.values()) {
      s = Stream.concat(s, m.values().stream());
    }
    return Stream.concat(s, Stream.concat(getModuleModels(), getPackageModels()));
  }

  private Stream<Map.Entry<PackageElement, PackageModel>> getPackageModels() {
    return all.stream()
      .map(elementUtils::getPackageOf).distinct()
      .map(element ->
            new ModelEntry<>(element, () -> new PackageModel(
                element.getQualifiedName().toString(),
                ModuleInfo.resolve(elementUtils, element))
            ));
  }

  private Stream<Map.Entry<PackageElement, ModuleModel>> getModuleModels() {
    return modules.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getModuleModel(entry.getKey())));
  }

  public ModuleModel getModuleModel(String modulePackage) {
    PackageElement element = modules.get(modulePackage);
    return new ModuleModel(elementUtils, typeUtils, element);
  }

  public PackageModel getPackageModel(String fqn) {
    return getPackageModels().filter(pkg -> pkg.getValue().getFqn().equals(fqn)).findFirst().map(Map.Entry::getValue).orElse(null);
  }

  public Model getModel(String fqcn, String kind) {
    Map<String, Map.Entry<TypeElement, Model>> map = models.get(kind);
    if (map == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    }
    Map.Entry<TypeElement, Model> entry = map.get(fqcn);
    if (entry == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    }
    return entry.getValue();
  }

  public ClassModel getClassModel(String fqcn) {
    return (ClassModel) getModel(fqcn, "class");
  }

  public EnumModel getEnumModel(String fqcn) {
    return (EnumModel) getModel(fqcn, "enum");
  }

  public DataObjectModel getDataObjectModel(String fqcn) {
    return (DataObjectModel) getModel(fqcn, "dataObject");
  }

  private static class ModelEntry<E extends Element, M extends Model> implements Map.Entry<E, M> {

    private final E key;
    private final Supplier<M> supplier;
    private M value;

    private ModelEntry(E key, Supplier<M> supplier) {
      this.key = key;
      this.supplier = supplier;
    }

    @Override
    public E getKey() {
      return key;
    }

    @Override
    public M getValue() {
      if (value == null) {
        value = supplier.get();
        value.process();
      }
      return value;
    }

    @Override
    public M setValue(M value) {
      throw new UnsupportedOperationException();
    }
  }
}
