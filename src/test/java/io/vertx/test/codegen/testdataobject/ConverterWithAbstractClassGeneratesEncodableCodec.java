package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;

@DataObject(generateConverter = true)
public abstract class ConverterWithAbstractClassGeneratesEncodableCodec {

  public ConverterWithAbstractClassGeneratesEncodableCodec(int a) {
    this.a = a;
  }

  private int a;

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }
}
