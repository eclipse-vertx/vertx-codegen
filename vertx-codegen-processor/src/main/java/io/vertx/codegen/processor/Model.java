package io.vertx.codegen.processor;

import io.vertx.codegen.processor.type.AnnotationValueInfo;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Model {

  boolean process();

  String getKind();

  Element getElement();

  String getFqn();

  default List<AnnotationValueInfo> getAnnotations() {
    return Collections.emptyList();
  }

  default Optional<AnnotationValueInfo> getAnnotation(Class<? extends Annotation> annotationType) {
    String annotationName = annotationType.getName();
    for (AnnotationValueInfo annotation : getAnnotations()) {
      if (annotation.getName().equals(annotationName)) {
        return Optional.of(annotation);
      }
    }
    return Optional.empty();
  }

  default Map<String, Object> getVars() {
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("helper", new Helper());
    return vars;
  }

  ModuleInfo getModule();

}
