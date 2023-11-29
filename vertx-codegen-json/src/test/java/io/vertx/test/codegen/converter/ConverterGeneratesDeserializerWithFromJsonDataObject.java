package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
@JsonGen
public class ConverterGeneratesDeserializerWithFromJsonDataObject {

  int a = 0;

  public JsonObject toJson() {
    return new JsonObject().put("hello", "francesco");
  }

  public int getA() {
    return a;
  }

  @Fluent
  public ConverterGeneratesDeserializerWithFromJsonDataObject setA(int a) {
    this.a = a;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConverterGeneratesDeserializerWithFromJsonDataObject that = (ConverterGeneratesDeserializerWithFromJsonDataObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }
}
