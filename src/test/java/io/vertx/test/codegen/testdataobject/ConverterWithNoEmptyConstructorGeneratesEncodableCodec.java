package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;

@DataObject(generateConverter = true)
public class ConverterWithNoEmptyConstructorGeneratesEncodableCodec {

  public ConverterWithNoEmptyConstructorGeneratesEncodableCodec(int a) {
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
