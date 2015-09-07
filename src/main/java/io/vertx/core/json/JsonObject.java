package io.vertx.core.json;

import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonObject  {

  public JsonObject(String str) {
  }

  public JsonObject() {
  }

  public JsonObject getJsonObject(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name, String def) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, JsonObject value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, JsonArray value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, String value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Integer value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Long value) { throw new UnsupportedOperationException(); }
  public JsonObject put(String fieldName, Double value) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName, Integer def) { throw new UnsupportedOperationException(); }
  public Double getDouble(String fieldName, Double def) { throw new UnsupportedOperationException(); }
  public JsonArray getJsonArray(String name) { throw new UnsupportedOperationException(); }
  public Map<String, Object> getMap() {
    throw new UnsupportedOperationException();
  }
  public JsonObject putAll(Map<String, Object> map) {
    throw new UnsupportedOperationException();
  }
}
