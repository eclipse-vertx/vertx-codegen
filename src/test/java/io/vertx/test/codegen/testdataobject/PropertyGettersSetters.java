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
public interface PropertyGettersSetters {

  static PropertyGettersSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  static PropertyGettersSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  void setString(String s);
  String getString();
  void setInstant(Instant i);
  Instant getInstant();
  void setLocalDate(LocalDate l);
  LocalDate getLocalDate();
  void setLocalDateTime(LocalDateTime l);
  LocalDateTime getLocalDateTime();
  void setLocalTime(LocalTime l);
  LocalTime getLocalTime();
  void setOffsetDateTime(OffsetDateTime o);
  OffsetDateTime getOffsetDateTime();
  void setZonedDateTime(ZonedDateTime z);
  ZonedDateTime getZonedDateTime();
  void setBoxedInteger(Integer i);
  Integer getBoxedInteger();
  void setPrimitiveInteger(int i);
  int getPrimitiveInteger();
  void setBoxedBoolean(Boolean b);
  Boolean isBoxedBoolean();
  void setPrimitiveBoolean(boolean b);
  boolean isPrimitiveBoolean();
  void setBoxedLong(Long l);
  Long getBoxedLong();
  void setPrimitiveLong(long l);
  long getPrimitiveLong();

  PropertyGettersSetters setApiObject(ApiObject s);
  ApiObject getApiObject();
  PropertyGettersSetters setApiObjectWithMapper(ApiObjectWithMapper s);
  ApiObjectWithMapper getApiObjectWithMapper();
  PropertyGettersSetters setDataObject(EmptyDataObject obj);
  EmptyDataObject getDataObject();
  PropertyGettersSetters setToJsonDataObject(ToJsonDataObject obj);
  ToJsonDataObject getToJsonDataObject();

  JsonObject getJsonObject();
  PropertyGettersSetters setJsonObject(JsonObject jsonObject);
  JsonArray getJsonArray();
  PropertyGettersSetters setJsonArray(JsonArray jsonArray);

  Enumerated getEnumerated();
  PropertyGettersSetters setEnumerated(Enumerated enumerated);
}
