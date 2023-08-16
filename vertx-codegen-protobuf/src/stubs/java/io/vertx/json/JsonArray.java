package io.vertx.core.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Compile stub
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonArray implements Iterable<Object> {

  private final List<Object> list;

  public JsonArray() {
    this(new ArrayList<>());
  }

  public JsonArray(List<Object> list) {
    this.list = list;
  }

  public JsonArray add(Object o) {
    list.add(o);
    return this;
  }

  public Iterator<Object> iterator() {
    return list.iterator();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof JsonArray) {
      JsonArray that = (JsonArray) obj;
      return list.equals(that.list);
    }
    return false;
  }

  public int size() {
    return list.size();
  }

  public List getList() {
    return list;
  }
}
