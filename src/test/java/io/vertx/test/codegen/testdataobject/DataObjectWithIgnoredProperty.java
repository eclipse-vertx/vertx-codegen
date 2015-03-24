package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface DataObjectWithIgnoredProperty {

  @GenIgnore
  DataObjectWithIgnoredProperty setIgnoredProperty1(String value);

  @GenIgnore
  DataObjectWithIgnoredProperty addIgnoredProperty2(String value);

}
