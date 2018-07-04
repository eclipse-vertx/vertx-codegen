package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
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
