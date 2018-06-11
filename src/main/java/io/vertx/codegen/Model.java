package io.vertx.codegen;

import io.vertx.codegen.type.AnnotationValueInfo;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  default Map<String, Object> getVars() {
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("helper", new Helper());
    return vars;
  }

  ModuleInfo getModule();

}
