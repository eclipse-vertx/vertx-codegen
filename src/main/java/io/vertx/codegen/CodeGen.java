package io.vertx.codegen;

import io.vertx.codegen.annotations.GenModule;
import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGen {

  private final HashMap<String, TypeElement> options = new HashMap<>();
  private final HashMap<String, TypeElement> classes = new HashMap<>();
  private final HashMap<String, PackageElement> modules = new HashMap<>();
  private final Elements elementUtils;
  private final Types typeUtils;

  public CodeGen(ProcessingEnvironment env, RoundEnvironment round) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    round.getElementsAnnotatedWith(Options.class).
      stream().
      forEach(element -> options.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(VertxGen.class).
      stream().
      filter(elt -> !elementUtils.getPackageOf(elt).getQualifiedName().toString().contains("impl")).
      forEach(element -> classes.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(GenModule.class).
      stream().
      map(element -> (PackageElement) element).
      forEach(element -> modules.put(element.getQualifiedName().toString(), element));
  }

  public Stream<Map.Entry<? extends Element, ? extends Model>> getModels() {
    return Stream.concat(getOptionsModels(),
      Stream.concat(getModuleModels(),
        Stream.concat(getPackageModels(),
          getClassModels())));
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

  public Stream<Map.Entry<TypeElement, ClassModel>> getClassModels() {
    return classes.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getClassModel(entry.getKey())));
  }

  public Stream<Map.Entry<PackageElement, PackageModel>> getPackageModels() {
    return classes.values().stream().map(elementUtils::getPackageOf).distinct().map(element -> new ModelEntry<>(element, () -> new PackageModel(element.getQualifiedName().toString())));
  }

  public Stream<Map.Entry<PackageElement, ModuleModel>> getModuleModels() {
    return modules.entrySet().stream().map(entry -> new ModelEntry<>(entry.getValue(), () -> getModuleModel(entry.getKey())));
  }

  public Stream<Map.Entry<TypeElement, OptionsModel>> getOptionsModels() {
    return options.entrySet().stream().map(element -> new ModelEntry<>(element.getValue(), () -> getOptionsModel(element.getKey())));
  }

  public ModuleModel getModuleModel(String fqcn) {
    PackageElement element = modules.get(fqcn);
    GenModule annotation = element.getAnnotation(GenModule.class);
    return new ModuleModel(element, new ModuleInfo(fqcn, annotation.name()));
  }

  public PackageModel getPackageModel(String fqn) {
    return getPackageModels().filter(pkg -> pkg.getValue().getFqn().equals(fqn)).findFirst().map(Map.Entry::getValue).orElse(null);
  }

  public ClassModel getClassModel(String fqcn) {
    TypeElement element = classes.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      ClassModel model = new ClassModel(classes, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
  }

  public OptionsModel getOptionsModel(String fqcn) {
    TypeElement element = options.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      OptionsModel model = new OptionsModel(elementUtils, typeUtils, element);
      model.process();
      return model;
    }
  }
}
