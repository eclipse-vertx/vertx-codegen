package io.vertx.core.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
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

  public Object getValue(int pos) {
    return list.get(pos);
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

  public String getString(final int index) { throw new UnsupportedOperationException(); }
  public byte[] getBinary(int pos) { throw new UnsupportedOperationException(); }

//  public JsonArray add(String str) { throw new UnsupportedOperationException(); }
//  public JsonArray add(JsonObject obj) { throw new UnsupportedOperationException(); }
//  public JsonArray add(JsonArray array) { throw new UnsupportedOperationException(); }
// public JsonArray add(byte[] value) { throw new UnsupportedOperationException(); }
  public List getList() {
    throw new UnsupportedOperationException();
  }
  public Stream<Object> stream() {
    throw new UnsupportedOperationException();
  }
  public JsonArray copy() { return new JsonArray(new ArrayList<>(list)); }
}
