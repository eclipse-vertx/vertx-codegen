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
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertySetGettersSetters {

  public static PropertySetGettersSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertySetGettersSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular special case
  Set<String> getExtraClassPath();
  PropertySetGettersSetters setExtraClassPath(Set<String> s);

  // Regular case
  Set<String> getStrings();
  PropertySetGettersSetters setStrings(Set<String> s);
  Set<Instant> getInstants();
  PropertySetGettersSetters setInstants(Set<Instant> i);
  Set<LocalDate> getLocalDates();
  PropertySetGettersSetters setLocalDates(Set<LocalDate> l);
  Set<LocalDateTime> getLocalDateTimes();
  PropertySetGettersSetters setLocalDateTimes(Set<LocalDateTime> l);
  Set<LocalTime> getLocalTimes();
  PropertySetGettersSetters setLocalTimes(Set<LocalTime> l);
  Set<OffsetDateTime> getOffsetDateTimes();
  PropertySetGettersSetters setOffsetDateTimes(Set<OffsetDateTime> o);
  Set<ZonedDateTime> getZonedDateTimes();
  PropertySetGettersSetters setZonedDateTimes(Set<ZonedDateTime> z);
  Set<Integer> getBoxedIntegers();
  PropertySetGettersSetters setBoxedIntegers(Set<Integer> i);
  Set<Boolean> getBoxedBooleans();
  PropertySetGettersSetters setBoxedBooleans(Set<Boolean> b);
  Set<Long> getBoxedLongs();
  PropertySetGettersSetters setBoxedLongs(Set<Long> b);
  Set<ApiObject> getApiObjects();
  PropertySetGettersSetters setApiObjects(Set<ApiObject> s);
  Set<ApiObjectWithMapper> getApiObjectWithMappers();
  PropertySetGettersSetters setApiObjectWithMappers(Set<ApiObjectWithMapper> s);
  Set<EmptyDataObject> getDataObjects();
  PropertySetGettersSetters setDataObjects(Set<EmptyDataObject> nested);
  Set<ToJsonDataObject> getToJsonDataObjects();
  PropertySetGettersSetters setToJsonDataObjects(Set<ToJsonDataObject> nested);
  Set<JsonObject> getJsonObjects();
  PropertySetGettersSetters setJsonObjects(Set<JsonObject> jsonObject);
  Set<JsonArray> getJsonArrays();
  PropertySetGettersSetters setJsonArrays(Set<JsonArray> jsonArray);
  Set<Enumerated> getEnumerateds();
  PropertySetGettersSetters setEnumerateds(Set<Enumerated> enumerated);

}
