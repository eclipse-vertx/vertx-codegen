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

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyListGettersAdders {

  public static PropertyListGettersAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyListGettersAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  List<String> getStrings();
  PropertyListGettersAdders addString(String s);
  List<Instant> getInstants();
  PropertyListGettersAdders addInstant(Instant i);
  List<LocalDate> getLocalDates();
  PropertyListGettersAdders addLocalDate(LocalDate l);
  List<LocalDateTime> getLocalDateTimes();
  PropertyListGettersAdders addLocalDateTime(LocalDateTime l);
  List<LocalTime> getLocalTimes();
  PropertyListGettersAdders addLocalTime(LocalTime l);
  List<OffsetDateTime> getOffsetDateTimes();
  PropertyListGettersAdders addOffsetDateTime(OffsetDateTime o);
  List<ZonedDateTime> getZonedDateTimes();
  PropertyListGettersAdders addZonedDateTime(ZonedDateTime z);
  List<Integer> getBoxedIntegers();
  PropertyListGettersAdders addBoxedInteger(Integer i);
  List<Boolean> getBoxedBooleans();
  PropertyListGettersAdders addBoxedBoolean(Boolean b);
  List<Long> getBoxedLongs();
  PropertyListGettersAdders addBoxedLong(Long b);

  List<ApiObject> getApiObjects();
  PropertyListGettersAdders addApiObject(ApiObject s);
  List<ApiObjectWithMapper> getApiObjectWithMappers();
  PropertyListGettersAdders addApiObjectWithMapper(ApiObjectWithMapper s);
  List<EmptyDataObject> getDataObjects();
  PropertyListGettersAdders addDataObject(EmptyDataObject s);
  List<ToJsonDataObject> getToJsonDataObjects();
  PropertyListGettersAdders addToJsonDataObject(ToJsonDataObject s);

  List<JsonObject> getJsonObjects();
  PropertySetters addJsonObject(JsonObject jsonObject);
  List<JsonArray> getJsonArrays();
  PropertySetters addJsonArray(JsonArray jsonArray);

  List<Enumerated> getEnumerateds();
  PropertySetters addEnumerated(Enumerated enumerated);
}
