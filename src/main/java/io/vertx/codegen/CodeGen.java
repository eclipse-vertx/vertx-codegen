package io.vertx.codegen;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.overloadcheck.MethodOverloadChecker;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.AnnotationValueInfoFactory;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGen {

  private static final Logger logger = Logger.getLogger(CodeGen.class.getName());

  private final HashMap<String, TypeElement> dataObjects = new HashMap<>();
  private final HashMap<String, TypeElement> classes = new HashMap<>();
  private final HashMap<String, TypeElement> enums = new HashMap<>();
  private final HashMap<String, PackageElement> modules = new HashMap<>();
  private final HashMap<String, TypeElement> proxyClasses = new HashMap<>();
  private final ProcessingEnvironment env;
  private final Elements elementUtils;
  private final Types typeUtils;
  private final Messager messager;
  private final MethodOverloadChecker methodOverloadChecker = new MethodOverloadChecker();

  public CodeGen(ProcessingEnvironment env, RoundEnvironment round) {
    this.env = env;
    this.messager = env.getMessager();
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    Predicate<Element> implFilter = elt -> {
      String fqn = elementUtils.getPackageOf(elt).getQualifiedName().toString();
      if (fqn.contains(".impl.") || fqn.endsWith(".impl"))  {
        logger.warning("Processed element " + elt + " is in an implementation package");
        return false;
      } else {
        return true;
      }
    };
    round.getElementsAnnotatedWith(DataObject.class).
      stream().
      filter(implFilter).
      forEach(element -> dataObjects.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(VertxGen.class).
      stream().
      filter(implFilter).
      filter(elt -> elt.getKind() != ElementKind.ENUM).
      forEach(element -> classes.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(VertxGen.class).
        stream().
        filter(implFilter).
        filter(elt -> elt.getKind() == ElementKind.ENUM).
        forEach(element -> enums.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(ModuleGen.class).
      stream().
      map(element -> (PackageElement) element).
      forEach(element -> modules.put(element.getQualifiedName().toString(), element));
    round.getElementsAnnotatedWith(ProxyGen.class).
      stream().
      filter(implFilter).
      forEach(element -> proxyClasses.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
  }

  public Stream<Map.Entry<? extends Element, ? extends Model>> getModels() {
    return Stream.concat(getDataObjectModels(),
        Stream.concat(getModuleModels(),
            Stream.concat(getPackageModels(),
                Stream.concat(getClassModels(),
                    Stream.concat(getEnumModels(),
                        getProxyModels())))));
  }

  public Stream<Map.Entry<TypeElement, ClassModel>> getClassModels() {
    return classes.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getClassModel(entry.getKey())));
  }

  public Stream<Map.Entry<PackageElement, PackageModel>> getPackageModels() {
    return classes.values().
        stream().
        map(elementUtils::getPackageOf).distinct().
        map(element ->
            new ModelEntry<>(element, () -> new PackageModel(
                element.getQualifiedName().toString(),
                ModuleInfo.resolve(elementUtils, element))
            ));
  }

  public Stream<Map.Entry<PackageElement, ModuleModel>> getModuleModels() {
    return modules.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getModuleModel(entry.getKey())));
  }

  public Stream<Map.Entry<TypeElement, DataObjectModel>> getDataObjectModels() {
    return dataObjects.entrySet().stream().map(element -> new ModelEntry<>(element.getValue(), () -> getDataObjectModel(element.getKey())));
  }

  public Stream<Map.Entry<TypeElement, ProxyModel>> getProxyModels() {
    return proxyClasses.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getProxyModel(entry.getKey())));
  }

  public Stream<Map.Entry<TypeElement, EnumModel>> getEnumModels() {
    return enums.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getEnumModel(entry.getKey())));
  }

  public ModuleModel getModuleModel(String modulePackage) {
    PackageElement element = modules.get(modulePackage);
    ModuleGen annotation = element.getAnnotation(ModuleGen.class);
    String moduleName = annotation.name();
    if (moduleName.isEmpty()) {
      throw new GenException(element, "A module name cannot be empty");
    }
    try {
      Case.KEBAB.parse(moduleName);
    } catch (IllegalArgumentException e) {
      throw new GenException(element, "Module name '" + moduleName + "' does not follow the snake case format (dash separated name)");
    }
    String groupPackage = annotation.groupPackage();
    if (groupPackage.equals("")) {
      groupPackage = modulePackage;
    } else if (!modulePackage.startsWith(groupPackage)) {
      throw new GenException(element, "A module package (" + modulePackage + ") must be prefixed by the group package (" + groupPackage + ")");
    }
    try {
      Case.QUALIFIED.parse(groupPackage);
    } catch (Exception e) {
      throw new GenException(element, "Invalid group package name " + groupPackage);
    }
    ModuleInfo info = new ModuleInfo(modulePackage, moduleName, groupPackage);
    AnnotationValueInfoFactory annotationFactory = new AnnotationValueInfoFactory(elementUtils, typeUtils);
    List<AnnotationValueInfo> annotationValueInfos = element.getAnnotationMirrors().stream().map(annotationFactory::processAnnotation).collect(Collectors.toList());
    return new ModuleModel(element, info, annotationValueInfos);
  }

  public PackageModel getPackageModel(String fqn) {
    return getPackageModels().filter(pkg -> pkg.getValue().getFqn().equals(fqn)).findFirst().map(Map.Entry::getValue).orElse(null);
  }

  public ClassModel getClassModel(String fqcn) {
    TypeElement element = classes.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      ClassModel model = new ClassModel(env, methodOverloadChecker, messager, classes, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
  }

  public EnumModel getEnumModel(String fqcn) {
    TypeElement element = enums.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      EnumModel model = new EnumModel(messager, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
  }

  public DataObjectModel getDataObjectModel(String fqcn) {
    TypeElement element = dataObjects.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      DataObjectModel model = new DataObjectModel(elementUtils, typeUtils, element, messager);
      model.process();
      return model;
    }
  }

  public ProxyModel getProxyModel(String fqcn) {
    TypeElement element = proxyClasses.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      ProxyModel model = new ProxyModel(env, methodOverloadChecker, messager, classes, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
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
      }
      return value;
    }

    @Override
    public M setValue(M value) {
      throw new UnsupportedOperationException();
    }
  }
}
