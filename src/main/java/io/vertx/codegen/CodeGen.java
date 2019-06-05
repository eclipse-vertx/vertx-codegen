package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;
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

  private static final Logger logger = Logger.getLogger(CodeGen.class.getName());
  final static Map<ProcessingEnvironment, ClassLoader> loaderMap = new WeakHashMap<>();

  private final Map<String, Map<String, Map.Entry<TypeElement, Model>>> models = new HashMap<>();
  private final Set<TypeElement> all = new HashSet<>();

  private final HashMap<String, PackageElement> modules = new HashMap<>();
  private final Elements elementUtils;
  private final Types typeUtils;

  public CodeGen(ProcessingEnvironment env, RoundEnvironment round, ClassLoader loader) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    loaderMap.put(env, loader);
    Predicate<Element> implFilter = elt -> {
      String fqn = elementUtils.getPackageOf(elt).getQualifiedName().toString();
      if (fqn.contains(".impl.") || fqn.endsWith(".impl"))  {
        logger.warning("Processed element " + elt + " is in an implementation package");
        return false;
      } else {
        return true;
      }
    };
    round.getRootElements().stream()
      .filter(implFilter)
      .filter(elt -> elt instanceof TypeElement)
      .map(elt -> (TypeElement)elt).forEach(te -> {
        for (ModelProvider provider : PROVIDERS) {
          Model model = provider.getModel(env, te);
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
