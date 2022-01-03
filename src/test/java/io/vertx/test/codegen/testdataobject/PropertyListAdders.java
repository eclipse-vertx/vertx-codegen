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
public interface PropertyListAdders {

  public static PropertyListAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyListAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  PropertyListAdders addString(String s);
  PropertyListAdders addInstant(Instant i);
  PropertySetSetters addLocalDate(LocalDate localDate);
  PropertySetSetters addLocalDateTime(LocalDateTime localDateTime);
  PropertySetSetters addLocalTime(LocalTime localTime);
  PropertySetSetters addOffsetDateTime(OffsetDateTime offsetDateTime);
  PropertySetSetters addZonedDateTime(ZonedDateTime zonedDateTime);
  PropertyListAdders addBoxedInteger(Integer i);
  PropertyListAdders addPrimitiveInteger(int i);
  PropertyListAdders addBoxedBoolean(Boolean b);
  PropertyListAdders addPrimitiveBoolean(boolean b);
  PropertyListAdders addBoxedLong(Long b);
  PropertyListAdders addPrimitiveLong(long b);

  PropertyListAdders addApiObject(ApiObject s);
  PropertyListAdders addApiObjectWithMapper(ApiObjectWithMapper s);
  PropertyListAdders addDataObject(EmptyDataObject s);
  PropertyListAdders addToJsonDataObject(ToJsonDataObject s);

  PropertySetters addJsonObject(JsonObject jsonObject);
  PropertySetters addJsonArray(JsonArray jsonArray);

  PropertySetters addEnumerated(Enumerated enumerated);
}
