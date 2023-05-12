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
public interface PropertySetSetters {

  public static PropertySetSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertySetSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular special case
  PropertySetSetters setExtraClassPath(Set<String> s);

  // Regular case
  PropertySetSetters setStrings(Set<String> s);
  PropertySetSetters setInstants(Set<Instant> i);
  PropertySetSetters setLocalDates(Set<LocalDate> localDates);
  PropertySetSetters setLocalDateTimes(Set<LocalDateTime> localDateTimes);
  PropertySetSetters setLocalTimes(Set<LocalTime> localTimes);
  PropertySetSetters setOffsetDateTimes(Set<OffsetDateTime> offsetDateTimes);
  PropertySetSetters setZonedDateTimes(Set<ZonedDateTime> zonedDateTimes);
  PropertySetSetters setBoxedIntegers(Set<Integer> i);
  PropertySetSetters setBoxedBooleans(Set<Boolean> b);
  PropertySetSetters setBoxedLongs(Set<Long> b);
  PropertySetSetters setApiObjects(Set<ApiObject> s);
  PropertySetSetters setApiObjectWithMappers(Set<ApiObjectWithMapper> s);
  PropertySetSetters setDataObjects(Set<EmptyDataObject> nested);
  PropertySetSetters setToJsonDataObjects(Set<ToJsonDataObject> nested);
  PropertySetSetters setJsonObjects(Set<JsonObject> jsonObject);
  PropertySetSetters setJsonArrays(Set<JsonArray> jsonArray);
  PropertySetSetters setEnumerateds(Set<Enumerated> enumerated);

}
