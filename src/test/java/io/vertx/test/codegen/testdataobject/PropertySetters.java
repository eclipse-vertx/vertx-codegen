package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertySetters {

  public static PropertySetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertySetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  PropertySetters setString(String s);
  PropertySetters setBoxedInteger(Integer i);
  PropertySetters setPrimitiveInteger(int i);
  PropertySetters setBoxedBoolean(Boolean b);
  PropertySetters setPrimitiveBoolean(boolean b);
  PropertySetters setBoxedLong(Long b);
  PropertySetters setPrimitiveLong(long b);

  PropertySetters setApiObject(ApiObject s);
  PropertySetters setDataObject(EmptyDataObject nested);
  PropertySetters setToJsonDataObject(ToJsonDataObject nested);
}
