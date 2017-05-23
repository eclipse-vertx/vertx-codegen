package io.vertx.core.json;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonObject  {

  public JsonObject(String str) {
  }

  public JsonObject() {
  }

  public JsonObject(Map<String, Object> map) {
  }

  public JsonObject getJsonObject(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name, String def) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, JsonObject value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, JsonArray value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Boolean value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, String value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Integer value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Long value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Double value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Float value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Enum value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String key, byte[] value) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName) { throw new UnsupportedOperationException(); }
  public Boolean getBoolean(String fieldName, Boolean def) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName, Integer def) { throw new UnsupportedOperationException(); }
  public Long getLong(String fieldName, Long def) { throw new UnsupportedOperationException(); }
  public Float getFloat(String fieldName, Float def) { throw new UnsupportedOperationException(); }
  public Double getDouble(String fieldName, Double def) { throw new UnsupportedOperationException(); }
  public JsonArray getJsonArray(String name) { throw new UnsupportedOperationException(); }
  public byte[] getBinary(String name) { throw new UnsupportedOperationException(); }
  public Map<String, Object> getMap() {
    throw new UnsupportedOperationException();
  }
  public Stream<Map.Entry<String, Object>> stream() {
    throw new UnsupportedOperationException();
  }
}
