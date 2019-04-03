package io.vertx.codegen.testmodel;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonCodec;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MyPojoToJsonObject {

  public static class MyPojoToJsonObjectCodec implements JsonCodec<MyPojoToJsonObject, JsonObject> {

    public static final MyPojoToJsonObjectCodec INSTANCE = new MyPojoToJsonObjectCodec();

    @Override
    public MyPojoToJsonObject decode(JsonObject value) throws IllegalArgumentException {
      return new MyPojoToJsonObject(value.getInteger("a"));
    }

    @Override
    public JsonObject encode(MyPojoToJsonObject value) throws IllegalArgumentException {
      return new JsonObject().put("a", value.getA());
    }
  }

  int a;

  public MyPojoToJsonObject() { }

  public MyPojoToJsonObject(int a) {
    this.a = a;
  }

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MyPojoToJsonObject that = (MyPojoToJsonObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }
}
