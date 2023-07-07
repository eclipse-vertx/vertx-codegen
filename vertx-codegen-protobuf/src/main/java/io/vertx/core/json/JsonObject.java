package io.vertx.core.json;

import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonObject implements Iterable<Map.Entry<String, Object>> {

  private final Map<String, Object> map;

  public JsonObject() {
    this(new HashMap<>());
  }

  public JsonObject(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public Iterator<Map.Entry<String, Object>> iterator() {
    return map.entrySet().iterator();
  }

  public Object getValue(String name) {
    return map.get(name);
  }

  public void remove(String name) {
    map.remove(name);
  }

  public JsonObject copy() {
    return new JsonObject(new HashMap<>(map));
  }

  public int size() {
    return map.size();
  }

  public JsonObject put(String fieldName, Object value) {
    map.put(fieldName, value);
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof JsonObject) {
      JsonObject that = (JsonObject) obj;
      return map.equals(that.map);
    }
    return false;
  }

  public Map<String, Object> getMap() {
    return map;
  }

  public JsonObject getJsonObject(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name, String def) { throw new UnsupportedOperationException(); }
  public Instant getInstant(String name) { throw new UnsupportedOperationException(); }
  public Instant getInstant(String name, Instant def) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName) { throw new UnsupportedOperationException(); }
  public Boolean getBoolean(String fieldName, Boolean def) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName, Integer def) { throw new UnsupportedOperationException(); }
  public Long getLong(String fieldName, Long def) { throw new UnsupportedOperationException(); }
  public Float getFloat(String fieldName, Float def) { throw new UnsupportedOperationException(); }
  public Double getDouble(String fieldName, Double def) { throw new UnsupportedOperationException(); }
  public JsonArray getJsonArray(String name) { throw new UnsupportedOperationException(); }
  public byte[] getBinary(String name) { throw new UnsupportedOperationException(); }
  public Stream<Map.Entry<String, Object>> stream() { throw new UnsupportedOperationException(); }
}
