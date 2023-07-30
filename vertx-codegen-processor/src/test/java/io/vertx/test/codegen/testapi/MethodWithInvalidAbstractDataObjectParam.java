package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author Julien Viet
 */
@VertxGen
public interface MethodWithInvalidAbstractDataObjectParam {

  void methodWithAbstractDataObjectParam(InvalidDataObject dataObject);
}
