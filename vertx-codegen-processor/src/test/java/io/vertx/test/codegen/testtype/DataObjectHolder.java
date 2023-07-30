package io.vertx.test.codegen.testtype;

import io.vertx.codegen.testmodel.TestDataObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface DataObjectHolder {

  TestDataObject dataObject();

  BareDataObject bareDataObject();

}
