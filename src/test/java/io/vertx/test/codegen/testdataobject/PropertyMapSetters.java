package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
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
  PropertyMapSetters setLocalDateMap(Map<String, LocalDate> l);
  PropertyMapSetters setLocalDateTimeMap(Map<String, LocalDateTime> l);
  PropertyMapSetters setLocalTimeMap(Map<String, LocalTime> l);
  PropertyMapSetters setOffsetDateTimeMap(Map<String, OffsetDateTime> o);
  PropertyMapSetters setZonedDateTimeMap(Map<String, ZonedDateTime> z);
  PropertyMapSetters setBoxedIntegerMap(Map<String, Integer> i);
  PropertyMapSetters setBoxedBooleanMap(Map<String, Boolean> b);
  PropertyMapSetters setBoxedLongMap(Map<String, Long> b);
  PropertyMapSetters setApiObjectMap(Map<String, ApiObject> s);
  PropertyMapSetters setApiObjectWithMapperMap(Map<String, ApiObjectWithMapper> s);
  PropertyMapSetters setDataObjectMap(Map<String, EmptyDataObject> nested);
  PropertyMapSetters setToJsonDataObjectMap(Map<String, ToJsonDataObject> nested);
  PropertyMapSetters setJsonObjectMap(Map<String, JsonObject> jsonObject);
  PropertyMapSetters setJsonArrayMap(Map<String, JsonArray> jsonArray);
  PropertyMapSetters setEnumeratedMap(Map<String, Enumerated> enumerated);
  PropertyMapSetters setObjectMap(Map<String, Object> map);

}
