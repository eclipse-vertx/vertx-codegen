package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;

@DataObject
@Deprecated
public interface DeprecatedDataObject {
  @Deprecated
  int getProperty1();
  @Deprecated
  DeprecatedDataObject setProperty2(int property2);
  @Deprecated
  void addProperty3(int property3);
}
