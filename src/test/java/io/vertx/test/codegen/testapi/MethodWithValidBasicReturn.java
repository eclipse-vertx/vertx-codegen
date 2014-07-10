package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidBasicReturn {

  byte methodWithByteReturn();
  short methodWithShortReturn();
  int methodWithIntReturn();
  long methodWithLongReturn();
  float methodWithFloatReturn();
  double methodWithDoubleReturn();
  boolean methodWithBooleanReturn();
  char methodWithCharReturn();
  String methodWithStringReturn();

  Byte methodWithByteObjectReturn();
  Short methodWithShortObjectReturn();
  Integer methodWithIntObjectReturn();
  Long methodWithLongObjectReturn();
  Float methodWithFloatObjectReturn();
  Double methodWithDoubleObjectReturn();
  Boolean methodWithBooleanObjectReturn();
  Character methodWithCharObjectReturn();


}
