package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface BasicGetters {

  public static BasicGetters options() {
    throw new UnsupportedOperationException();
  }

  public static BasicGetters optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  String getString();
  Integer getBoxedInteger();
  int getPrimitiveInteger();
  Boolean isBoxedBoolean();
  boolean isPrimitiveBoolean();
  Long getBoxedLong();
  long getPrimitiveLong();

}
