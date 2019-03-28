package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject(generateConverter = true)
public abstract class AbstractConverterGeneratesEncoderWithToJsonDataObject {

  int a;

  // This should be ignored
  public AbstractConverterGeneratesEncoderWithToJsonDataObject(JsonObject obj) {
    a = -1;
  }

  public AbstractConverterGeneratesEncoderWithToJsonDataObject(int a) { this.a = a; }

  public int getA() {
    return a;
  }

  @Fluent
  public AbstractConverterGeneratesEncoderWithToJsonDataObject setA(int a) {
    this.a = a;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractConverterGeneratesEncoderWithToJsonDataObject that = (AbstractConverterGeneratesEncoderWithToJsonDataObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }

  public static AbstractConverterGeneratesEncoderWithToJsonDataObject decode(JsonObject obj) {
    return new AbstractConverterGeneratesEncoderWithToJsonDataObject(-5) {};
  }
}
