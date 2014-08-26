package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface BasicAdders {

  public static BasicAdders options() {
    throw new UnsupportedOperationException();
  }

  public static BasicAdders optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  BasicAdders addString(String s);
  BasicAdders addBoxedInteger(Integer i);
  BasicAdders addPrimitiveInteger(int i);
  BasicAdders addBoxedBoolean(Boolean b);
  BasicAdders addPrimitiveBoolean(boolean b);
  BasicAdders addBoxedLong(Long b);
  BasicAdders addPrimitiveLong(long b);

}
