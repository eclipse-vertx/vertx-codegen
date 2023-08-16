package io.vertx.core.json;

import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Compile stub
 *
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

}
