package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface BasicSetters {

  public static BasicSetters options() {
    throw new UnsupportedOperationException();
  }

  public static BasicSetters optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  BasicSetters setString(String s);
  BasicSetters setBoxedInteger(Integer i);
  BasicSetters setPrimitiveInteger(int i);
  BasicSetters setBoxedBoolean(Boolean b);
  BasicSetters setPrimitiveBoolean(boolean b);
  BasicSetters setBoxedLong(Long b);
  BasicSetters setPrimitiveLong(long b);

}
