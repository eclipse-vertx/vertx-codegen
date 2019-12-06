package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;

@DataObject(generateConverter = true)
public class ConverterGeneratesCompleteCodec {

  private int a;

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }
}
