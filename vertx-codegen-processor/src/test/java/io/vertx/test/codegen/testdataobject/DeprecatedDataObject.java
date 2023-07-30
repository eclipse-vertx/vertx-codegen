package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
/**
 * @deprecated deprecated info
 */
@DataObject
@Deprecated
public interface DeprecatedDataObject {
  /**
   * @deprecated property deprecated info
   */
  @Deprecated
  int getProperty1();
  /**
   * @deprecated property deprecated info
   */
  @Deprecated
  DeprecatedDataObject setProperty2(int property2);
  /**
   * @deprecated property deprecated info
   */
  @Deprecated
  void addProperty3(int property3);
}
