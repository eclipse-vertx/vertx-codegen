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
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyMapGettersAdders {

  static PropertyMapGettersAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  static PropertyMapGettersAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Regular case
  Map<String, String> getStrings();
  PropertyMapGettersAdders addString(String key, String s);
  Map<String, Instant> getInstants();
  PropertyMapGettersAdders addInstant(String key, Instant i);
  Map<String, LocalDate> getLocalDates();
  PropertyMapGettersAdders addLocalDate(String key, LocalDate l);
  Map<String, LocalDateTime> getLocalDateTimes();
  PropertyMapGettersAdders addLocalDateTime(String key, LocalDateTime l);
  Map<String, LocalTime> getLocalTimes();
  PropertyMapGettersAdders addLocalTime(String key, LocalTime l);
  Map<String, OffsetDateTime> getOffsetDateTimes();
  PropertyMapGettersAdders addOffsetDateTime(String key, OffsetDateTime o);
  Map<String, ZonedDateTime> getZonedDateTimes();
  PropertyMapGettersAdders addZonedDateTime(String key, ZonedDateTime z);
  Map<String, Integer> getBoxedIntegers();
  PropertyMapGettersAdders addBoxedInteger(String key, Integer i);
  Map<String, Boolean> getBoxedBooleans();
  PropertyMapGettersAdders addBoxedBoolean(String key, Boolean b);
  Map<String, Long> getBoxedLongs();
  PropertyMapGettersAdders addBoxedLong(String key, Long b);
  Map<String, ApiObject> getApiObjects();
  PropertyMapGettersAdders addApiObject(String key, ApiObject s);
  Map<String, ApiObjectWithMapper> getApiObjectWithMappers();
  PropertyMapGettersAdders addApiObjectWithMapper(String key, ApiObjectWithMapper s);
  Map<String, EmptyDataObject> getDataObjects();
  PropertyMapGettersAdders addDataObject(String key, EmptyDataObject nested);
  Map<String, ToJsonDataObject> getToJsonDataObjects();
  PropertyMapGettersAdders addToJsonDataObject(String key, ToJsonDataObject nested);
  Map<String, JsonObject> getJsonObjects();
  PropertyMapGettersAdders addJsonObject(String key, JsonObject jsonObject);
  Map<String, JsonArray> getJsonArrays();
  PropertyMapGettersAdders addJsonArray(String key, JsonArray jsonArray);
  Map<String, Enumerated> getEnumerateds();
  PropertyMapGettersAdders addEnumerated(String key, Enumerated enumerated);
  Map<String, Object> getObjects();
  PropertyMapGettersAdders addObject(String key, Object omap);

}
