package io.vertx.core.json;

import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonArray implements Iterable<Object> {

  public int size() { throw new UnsupportedOperationException(); }
  public String getString(final int index) { throw new UnsupportedOperationException(); }
  public JsonArray add(String str) { throw new UnsupportedOperationException(); }
  public JsonArray add(JsonObject obj) { throw new UnsupportedOperationException(); }
  public JsonArray add(JsonArray array) { throw new UnsupportedOperationException(); }
  public Iterator<Object> iterator() { throw new UnsupportedOperationException(); }
  public List getList() {
    throw new UnsupportedOperationException();
  }

}
