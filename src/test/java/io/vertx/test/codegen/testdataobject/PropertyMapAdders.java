package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyMapAdders {

  static PropertyMapAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  static PropertyMapAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Regular case
  PropertyMapAdders addString(String key, String s);
  PropertyMapAdders addInstant(String key, Instant i);
  PropertyMapAdders addBoxedInteger(String key, Integer i);
  PropertyMapAdders addPrimitiveInteger(String key, int i);
  PropertyMapAdders addBoxedBoolean(String key, Boolean b);
  PropertyMapAdders addPrimitiveBoolean(String key, boolean b);
  PropertyMapAdders addBoxedLong(String key, Long b);
  PropertyMapAdders addPrimitiveLong(String key, long b);
  PropertyMapAdders addApiObject(String key, ApiObject s);
  PropertyMapAdders addDataObject(String key, EmptyDataObject nested);
  PropertyMapAdders addToJsonDataObject(String key, ToJsonDataObject nested);
  PropertyMapAdders addJsonObject(String key, JsonObject jsonObject);
  PropertyMapAdders addJsonArray(String key, JsonArray jsonArray);
  PropertyMapAdders addEnumerated(String key, Enumerated enumerated);
  PropertyMapAdders addObject(String key, Object omap);

}
