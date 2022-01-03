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
public interface PropertyListSetters {

  public static PropertyListSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyListSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular special case
  PropertyListSetters setExtraClassPath(List<String> s);

  // Regular case
  PropertyListSetters setStrings(List<String> s);
  PropertyListSetters setInstants(List<Instant> s);
  PropertyListSetters setLocalDates(List<LocalDate> l);
  PropertyListSetters setLocalDateTimes(List<LocalDateTime> l);
  PropertyListSetters setLocalTimes(List<LocalTime> l);
  PropertyListSetters setOffsetDateTimes(List<OffsetDateTime> o);
  PropertyListSetters setZonedDateTimes(List<ZonedDateTime> z);
  PropertyListSetters setBoxedIntegers(List<Integer> i);
  PropertyListSetters setBoxedBooleans(List<Boolean> b);
  PropertyListSetters setBoxedLongs(List<Long> b);
  PropertyListSetters setApiObjects(List<ApiObject> s);
  PropertyListSetters setApiObjectWithMappers(List<ApiObjectWithMapper> s);
  PropertyListSetters setDataObjects(List<EmptyDataObject> nested);
  PropertyListSetters setToJsonDataObjects(List<ToJsonDataObject> nested);
  PropertyListSetters setJsonObjects(List<JsonObject> jsonObject);
  PropertyListSetters setJsonArrays(List<JsonArray> jsonArray);
  PropertyListSetters setEnumerateds(List<Enumerated> enumerated);

}
