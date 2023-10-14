package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

import java.util.Objects;

@DataObject
@ProtobufGen
public class SimplePojo {
  private Integer integerField;
  private Long LongField;
  private Boolean booleanField;
  private String stringField;

  public Integer getIntegerField() {
    return integerField;
  }

  public void setIntegerField(Integer integerField) {
    this.integerField = integerField;
  }

  public Long getLongField() {
    return LongField;
  }

  public void setLongField(Long longField) {
    LongField = longField;
  }

  public Boolean getBooleanField() {
    return booleanField;
  }

  public void setBooleanField(Boolean booleanField) {
    this.booleanField = booleanField;
  }

  public String getStringField() {
    return stringField;
  }

  public void setStringField(String stringField) {
    this.stringField = stringField;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimplePojo that = (SimplePojo) o;
    return Objects.equals(integerField, that.integerField) && Objects.equals(LongField, that.LongField) && Objects.equals(booleanField, that.booleanField) && Objects.equals(stringField, that.stringField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(integerField, LongField, booleanField, stringField);
  }
}
