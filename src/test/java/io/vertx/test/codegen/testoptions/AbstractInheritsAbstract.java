package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options(concrete = false)
public interface AbstractInheritsAbstract extends Abstract {

  public static AbstractInheritsAbstract options() {
    throw new UnsupportedOperationException();
  }

  public static AbstractInheritsAbstract optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
