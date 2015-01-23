package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface BasicSetters {

  public static BasicSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static BasicSetters dataObjectFromJson(JsonObject obj) {
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
