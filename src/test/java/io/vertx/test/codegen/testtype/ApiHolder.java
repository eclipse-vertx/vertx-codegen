package io.vertx.test.codegen.testtype;

import io.vertx.test.codegen.testapi.GenericInterface;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface ApiHolder<ClassTypeParam> {


  ApiObject api();
  GenericInterface<String> apiParameterizedByClass();
  GenericInterface<ClassTypeParam> apiParameterizedByClassTypeParam();
  <MethodTypeParam> GenericInterface<MethodTypeParam> apiParameterizedByMethodTypeParam();

}
