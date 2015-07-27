package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyGetters {

  public static PropertyGetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyGetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  void setString(String s);
  String getString();
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

  PropertyGetters setApiObject(ApiObject s);
  ApiObject getApiObject();
  PropertyGetters setDataObject(EmptyDataObject obj);
  EmptyDataObject getDataObject();
  PropertyGetters setToJsonDataObject(ToJsonDataObject obj);
  ToJsonDataObject getToJsonDataObject();

  JsonObject getJsonObject();
  PropertySetters setJsonObject(JsonObject jsonObject);
  JsonArray getJsonArray();
  PropertySetters setJsonArray(JsonArray jsonArray);
}
