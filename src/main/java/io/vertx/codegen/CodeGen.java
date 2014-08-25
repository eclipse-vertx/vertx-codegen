package io.vertx.codegen;

import io.vertx.codegen.annotations.VertxGen;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGen {

  private final HashMap<String, TypeElement> modelSources = new HashMap<>();
  private final Elements elementUtils;
  private final Types typeUtils;
  private final RoundEnvironment round;

  public CodeGen(ProcessingEnvironment env, RoundEnvironment round) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.round = round;

    //
    round.getElementsAnnotatedWith(VertxGen.class).
          stream().
          filter(elt -> !elementUtils.getPackageOf(elt).getQualifiedName().toString().contains("impl")).
          forEach(element -> modelSources.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
  }

  public Iterable<Model> getModels() {
    return modelSources.keySet().stream().map(this::getModel).collect(Collectors.toList());
  }

  public Model getModel(String fqcn) {
    TypeElement element = modelSources.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      Model model = new Model(modelSources, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
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
