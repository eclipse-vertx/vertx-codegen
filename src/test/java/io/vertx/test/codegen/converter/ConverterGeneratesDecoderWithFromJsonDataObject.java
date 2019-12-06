package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject(generateConverter = true)
public class ConverterGeneratesDecoderWithFromJsonDataObject {

  int a = 0;

  public JsonObject toJson() {
    return new JsonObject().put("hello", "francesco");
  }

  public int getA() {
    return a;
  }

  @Fluent
  public ConverterGeneratesDecoderWithFromJsonDataObject setA(int a) {
    this.a = a;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConverterGeneratesDecoderWithFromJsonDataObject that = (ConverterGeneratesDecoderWithFromJsonDataObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }
}
