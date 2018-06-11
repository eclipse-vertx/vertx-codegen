package io.vertx.codegen;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.VertxGen;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface ModelProvider {

  ModelProvider CLASS = (env, elt) -> {
    if (elt.getAnnotation(VertxGen.class) != null && elt.getKind() != ElementKind.ENUM) {
      ClassModel model = new ClassModel(env, elt);
      return model;
    } else {
      return null;
    }
  };

  ModelProvider DATA_OBJECT = (env, elt) -> {
    if (elt.getAnnotation(DataObject.class) != null) {
      DataObjectModel model = new DataObjectModel(env, elt);
      return model;
    } else {
      return null;
    }
  };

  ModelProvider ENUM = (env, elt) -> {
    if (elt.getAnnotation(VertxGen.class) != null && elt.getKind() == ElementKind.ENUM) {
      EnumModel model = new EnumModel(env, elt);
      return model;
    } else {
      return null;
    }
  };

  Model getModel(ProcessingEnvironment env, TypeElement elt);

}
