package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject(generateConverter = true)
public class ConverterGeneratesEncoderWithToJsonDataObject {

  int a;

  public ConverterGeneratesEncoderWithToJsonDataObject(JsonObject obj) {
    a = -1;
  }

  public ConverterGeneratesEncoderWithToJsonDataObject(int a) { this.a = a; }

  public int getA() {
    return a;
  }

  @Fluent
  public ConverterGeneratesEncoderWithToJsonDataObject setA(int a) {
    this.a = a;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConverterGeneratesEncoderWithToJsonDataObject that = (ConverterGeneratesEncoderWithToJsonDataObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }

  // This should be ignored
  public static ConverterGeneratesEncoderWithToJsonDataObject decode(JsonObject obj) {
    return new ConverterGeneratesEncoderWithToJsonDataObject(-5);
  }
}
