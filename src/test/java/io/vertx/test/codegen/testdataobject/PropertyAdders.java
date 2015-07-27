package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyAdders {

  public static PropertyAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  PropertyAdders addString(String s);
  PropertyAdders addBoxedInteger(Integer i);
  PropertyAdders addPrimitiveInteger(int i);
  PropertyAdders addBoxedBoolean(Boolean b);
  PropertyAdders addPrimitiveBoolean(boolean b);
  PropertyAdders addBoxedLong(Long b);
  PropertyAdders addPrimitiveLong(long b);

  PropertyAdders addApiObject(ApiObject s);
  PropertyAdders addDataObject(EmptyDataObject s);
  PropertyAdders addToJsonDataObject(ToJsonDataObject s);

  PropertySetters addJsonObject(JsonObject jsonObject);
  PropertySetters addJsonArray(JsonArray jsonArray);

  PropertySetters addEnumerated(Enumerated enumerated);
}
