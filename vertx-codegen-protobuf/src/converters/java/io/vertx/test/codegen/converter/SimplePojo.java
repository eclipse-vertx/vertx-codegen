package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

import java.util.Objects;

@DataObject
@ProtobufGen
public class SimplePojo {
  private Integer nullInteger;
  private Integer zeroInteger;
  private Boolean nullBoolean;
  private Boolean zeroBoolean;
  private String nullString;
  private String ZeroString;
  private int primitiveInteger;
  private boolean primitiveBoolean;

  public Integer getNullInteger() {
    return nullInteger;
  }

  public void setNullInteger(Integer nullInteger) {
    this.nullInteger = nullInteger;
  }

  public Integer getZeroInteger() {
    return zeroInteger;
  }

  public void setZeroInteger(Integer zeroInteger) {
    this.zeroInteger = zeroInteger;
  }

  public Boolean getNullBoolean() {
    return nullBoolean;
  }

  public void setNullBoolean(Boolean nullBoolean) {
    this.nullBoolean = nullBoolean;
  }

  public Boolean getZeroBoolean() {
    return zeroBoolean;
  }

  public void setZeroBoolean(Boolean zeroBoolean) {
    this.zeroBoolean = zeroBoolean;
  }

  public String getNullString() {
    return nullString;
  }

  public void setNullString(String nullString) {
    this.nullString = nullString;
  }

  public String getZeroString() {
    return ZeroString;
  }

  public void setZeroString(String zeroString) {
    this.ZeroString = zeroString;
  }

  public int getPrimitiveInteger() {
    return primitiveInteger;
  }

  public void setPrimitiveInteger(int primitiveInteger) {
    this.primitiveInteger = primitiveInteger;
  }

  public boolean isPrimitiveBoolean() {
    return primitiveBoolean;
  }

  public void setPrimitiveBoolean(boolean primitiveBoolean) {
    this.primitiveBoolean = primitiveBoolean;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimplePojo that = (SimplePojo) o;
    return primitiveInteger == that.primitiveInteger && primitiveBoolean == that.primitiveBoolean && Objects.equals(nullInteger, that.nullInteger) && Objects.equals(zeroInteger, that.zeroInteger) && Objects.equals(nullBoolean, that.nullBoolean) && Objects.equals(zeroBoolean, that.zeroBoolean) && Objects.equals(nullString, that.nullString) && Objects.equals(ZeroString, that.ZeroString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nullInteger, zeroInteger, nullBoolean, zeroBoolean, nullString, ZeroString, primitiveInteger, primitiveBoolean);
  }
}
