package io.vertx.codegen;

import javax.lang.model.element.Element;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Model {

  String getKind();

  Element getElement();

  String getFqn();

  Map<String, Object> getVars();

}
