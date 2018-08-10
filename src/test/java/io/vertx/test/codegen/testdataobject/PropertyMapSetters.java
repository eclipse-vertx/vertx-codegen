package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyMapSetters {

  static PropertyMapSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  static PropertyMapSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Regular case
  PropertyMapSetters setStringMap(Map<String, String> s);
  PropertyMapSetters setInstantMap(Map<String, Instant> i);
  PropertyMapSetters setBoxedIntegerMap(Map<String, Integer> i);
  PropertyMapSetters setBoxedBooleanMap(Map<String, Boolean> b);
  PropertyMapSetters setBoxedLongMap(Map<String, Long> b);
  PropertyMapSetters setApiObjectMap(Map<String, ApiObject> s);
  PropertyMapSetters setDataObjectMap(Map<String, EmptyDataObject> nested);
  PropertyMapSetters setToJsonDataObjectMap(Map<String, ToJsonDataObject> nested);
  PropertyMapSetters setJsonObjectMap(Map<String, JsonObject> jsonObject);
  PropertyMapSetters setJsonArrayMap(Map<String, JsonArray> jsonArray);
  PropertyMapSetters setEnumeratedMap(Map<String, Enumerated> enumerated);
  PropertyMapSetters setObjectMap(Map<String, Object> map);

}
