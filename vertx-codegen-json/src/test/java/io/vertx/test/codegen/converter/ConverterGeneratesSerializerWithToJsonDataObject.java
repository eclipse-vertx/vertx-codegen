package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
@JsonGen
public class ConverterGeneratesSerializerWithToJsonDataObject {

  int a;

  public ConverterGeneratesSerializerWithToJsonDataObject(JsonObject obj) {
    a = -1;
  }

  public ConverterGeneratesSerializerWithToJsonDataObject(int a) { this.a = a; }

  public int getA() {
    return a;
  }

  @Fluent
  public ConverterGeneratesSerializerWithToJsonDataObject setA(int a) {
    this.a = a;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConverterGeneratesSerializerWithToJsonDataObject that = (ConverterGeneratesSerializerWithToJsonDataObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }

  // This should be ignored
  public static ConverterGeneratesSerializerWithToJsonDataObject decode(JsonObject obj) {
    return new ConverterGeneratesSerializerWithToJsonDataObject(-5);
  }
}
