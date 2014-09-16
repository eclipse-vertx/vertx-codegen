package io.vertx.core.json;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonObject  {

  public JsonObject(String str) {
  }

  public JsonObject() {
  }

  public JsonObject getObject(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name) { throw new UnsupportedOperationException(); }
  public String getString(String name, String def) { throw new UnsupportedOperationException(); }
  public JsonObject putString(String fieldName, String value) { throw new UnsupportedOperationException(); }
  public JsonObject putNumber(String fieldName, Number value) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName) { throw new UnsupportedOperationException(); }
  public Integer getInteger(String fieldName, int def) { throw new UnsupportedOperationException(); }
  public Number getNumber(String fieldName, int def) { throw new UnsupportedOperationException(); }
  public JsonArray getArray(String name) { throw new UnsupportedOperationException(); }
}
