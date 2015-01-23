package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface BasicAdders {

  public static BasicAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static BasicAdders dataObjectFromJson(JsonObject obj) {
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
