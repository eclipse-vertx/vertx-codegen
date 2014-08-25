package io.vertx.codegen;

import io.vertx.codegen.annotations.GenModule;
import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGen {

  private final HashMap<String, TypeElement> options = new HashMap<>();
  private final HashMap<String, TypeElement> models = new HashMap<>();
  private final HashMap<String, PackageElement> modules = new HashMap<>();
  private final Elements elementUtils;
  private final Types typeUtils;
  private final RoundEnvironment round;

  public CodeGen(ProcessingEnvironment env, RoundEnvironment round) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.round = round;
    round.getElementsAnnotatedWith(Options.class).
        stream().
        forEach(element -> options.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(VertxGen.class).
          stream().
          filter(elt -> !elementUtils.getPackageOf(elt).getQualifiedName().toString().contains("impl")).
          forEach(element -> models.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
    round.getElementsAnnotatedWith(GenModule.class).
          stream().
          map(element -> (PackageElement)element).
          forEach(element -> modules.put(element.getQualifiedName().toString(), element));
  }

  public Iterable<Model> getModels() {
    ArrayList<Model> models = new ArrayList<>();
    getModuleModels().forEach(models::add);
    getPackageModels().forEach(models::add);
    getClassModels().forEach(models::add);
    return models;
  }

  public Iterable<ClassModel> getClassModels() {
    return models.keySet().stream().map(this::getClassModel).collect(Collectors.toList());
  }

  public Collection<PackageModel> getPackageModels() {
    return models.keySet().stream().map(fqn -> fqn.substring(0, fqn.lastIndexOf('.'))).distinct().map(PackageModel::new).collect(Collectors.toList());
  }

  public Iterable<ModuleModel> getModuleModels() {
    return modules.keySet().stream().map(this::getModuleModel).collect(Collectors.toList());
  }

  public ModuleModel getModuleModel(String fqcn) {
    PackageElement element = modules.get(fqcn);
    GenModule annotation = element.getAnnotation(GenModule.class);
    return new ModuleModel(element, new ModuleInfo(fqcn, annotation.name()));
  }

  public PackageModel getPackageModel(String fqn) {
    return getPackageModels().stream().filter(pkg -> pkg.getFqn().equals(fqn)).findFirst().orElse(null);
  }

  public ClassModel getClassModel(String fqcn) {
    TypeElement element = models.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      ClassModel model = new ClassModel(models, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
  }

  public void validateOption(String fqcn) {
    validateOption(options.get(fqcn));
  }

  public void validateOption(Element optionElt) {
    if (optionElt.getKind() == ElementKind.INTERFACE) {
      for (Element memberElt : elementUtils.getAllMembers((TypeElement) optionElt)) {
        if (memberElt.getKind() == ElementKind.METHOD) {
          if (memberElt.getSimpleName().toString().equals("optionsFromJson")) {
            if (memberElt.getModifiers().contains(Modifier.STATIC)) {
              // TODO should probably also test that the method returns the right options type and
              // takes JsonObject as a parameter
              return;
            }
          }
        }
      }
      throw new GenException(optionElt, "Options " + optionElt + " class does not have a static factory method called optionsFromJson");
    } else {
      throw new GenException(optionElt, "Options " + optionElt + " must be an interface not a class");
    }
  }
}
