package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.Fluent;

import java.util.Objects;

@DataObject(generateConverter = true)
public class ConverterGeneratesCompleteCodecDataObject {

  private int a = 0;

  public int getA() {
    return a;
  }

  @Fluent
  public ConverterGeneratesCompleteCodecDataObject setA(int a) {
    this.a = a;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConverterGeneratesCompleteCodecDataObject that = (ConverterGeneratesCompleteCodecDataObject) o;
    return a == that.a;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }
}
