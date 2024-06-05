package io.vertx.codegen.processor;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.processor.type.TypeMirrorFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public interface ModelProvider {

  ModelProvider CLASS = (env, typeFactory, elt) -> {
    if (elt.getAnnotation(VertxGen.class) != null && elt.getKind() != ElementKind.ENUM) {
      ClassModel model = new ClassModel(env, typeFactory, elt);
      return model;
    } else {
      return null;
    }
  };

  ModelProvider DATA_OBJECT = (env, typeFactory, elt) -> {
    if (elt.getAnnotation(DataObject.class) != null) {
      DataObjectModel model = new DataObjectModel(env, typeFactory, elt);
      return model;
    } else {
      return null;
    }
  };

  ModelProvider ENUM = (env, typeFactory, elt) -> {
    if (elt.getAnnotation(VertxGen.class) != null && elt.getKind() == ElementKind.ENUM) {
      EnumModel model = new EnumModel(env, elt);
      return model;
    } else {
      return null;
    }
  };

  Model getModel(ProcessingEnvironment env, TypeMirrorFactory typeFactory, TypeElement elt);

}
