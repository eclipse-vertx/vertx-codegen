package io.vertx.core.json;

import java.util.Iterator;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonArray implements Iterable<Object> {

  public int size() { throw new UnsupportedOperationException(); }
  public <T> T get(final int index) { throw new UnsupportedOperationException(); }
  public JsonArray add(Object str) { throw new UnsupportedOperationException(); }
  public Iterator<Object> iterator() { throw new UnsupportedOperationException(); }

}
