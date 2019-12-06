package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public abstract class ConverterWithAbstractClassAndDecodeGeneratesCompleteCodec {

  public ConverterWithAbstractClassAndDecodeGeneratesCompleteCodec(int a) {
    this.a = a;
  }

  private int a;

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }

  public static ConverterWithAbstractClassAndDecodeGeneratesCompleteCodec decode(JsonObject obj) {
    return null;
  }
}
