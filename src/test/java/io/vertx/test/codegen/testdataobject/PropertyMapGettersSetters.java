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
public interface PropertyMapGettersSetters {

  public static PropertyMapGettersSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyMapGettersSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Regular case
  Map<String, String> getStringMap();
  PropertyMapGettersSetters setStringMap(Map<String, String> s);
  Map<String, Instant> getInstantMap();
  PropertyMapGettersSetters setInstantMap(Map<String, Instant> i);
  Map<String, Integer> getBoxedIntegerMap();
  PropertyMapGettersSetters setBoxedIntegerMap(Map<String, Integer> i);
  Map<String, Boolean> getBoxedBooleanMap();
  PropertyMapGettersSetters setBoxedBooleanMap(Map<String, Boolean> b);
  Map<String, Long> getBoxedLongMap();
  PropertyMapGettersSetters setBoxedLongMap(Map<String, Long> b);
  Map<String, ApiObject> getApiObjectMap();
  PropertyMapGettersSetters setApiObjectMap(Map<String, ApiObject> s);
  Map<String, EmptyDataObject> getDataObjectMap();
  PropertyMapGettersSetters setDataObjectMap(Map<String, EmptyDataObject> nested);
  Map<String, ToJsonDataObject> getToJsonDataObjectMap();
  PropertyMapGettersSetters setToJsonDataObjectMap(Map<String, ToJsonDataObject> nested);
  Map<String, JsonObject> getJsonObjectMap();
  PropertyMapGettersSetters setJsonObjectMap(Map<String, JsonObject> jsonObject);
  Map<String, JsonArray> getJsonArrayMap();
  PropertyMapGettersSetters setJsonArrayMap(Map<String, JsonArray> jsonArray);
  Map<String, Enumerated> getEnumeratedMap();
  PropertyMapGettersSetters setEnumeratedMap(Map<String, Enumerated> enumerated);
  Map<String, Object> getObjectMap();
  PropertyMapGettersSetters setObjectMap(Map<String, Object> enumerated);

}
