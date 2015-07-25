package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface DataObjectInterfaceWithIgnoredProperty {

  @GenIgnore
  DataObjectInterfaceWithIgnoredProperty setIgnoredProperty1(String value);

  @GenIgnore
  DataObjectInterfaceWithIgnoredProperty addIgnoredProperty2(String value);

}
