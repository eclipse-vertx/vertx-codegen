package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyListAdders {

  public static PropertyListAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyListAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  PropertyListAdders addString(String s);
  PropertyListAdders addInstant(Instant i);
  PropertyListAdders addBoxedInteger(Integer i);
  PropertyListAdders addPrimitiveInteger(int i);
  PropertyListAdders addBoxedBoolean(Boolean b);
  PropertyListAdders addPrimitiveBoolean(boolean b);
  PropertyListAdders addBoxedLong(Long b);
  PropertyListAdders addPrimitiveLong(long b);

  PropertyListAdders addApiObject(ApiObject s);
  PropertyListAdders addDataObject(EmptyDataObject s);
  PropertyListAdders addToJsonDataObject(ToJsonDataObject s);

  PropertySetters addJsonObject(JsonObject jsonObject);
  PropertySetters addJsonArray(JsonArray jsonArray);

  PropertySetters addEnumerated(Enumerated enumerated);
}
