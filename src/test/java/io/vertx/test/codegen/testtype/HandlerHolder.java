package io.vertx.test.codegen.testtype;

import io.vertx.core.Handler;
import io.vertx.test.codegen.testapi.GenericInterface;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface HandlerHolder<ClassTypeParam> {

  Handler<Void> handlerVoid();
  Handler<String> handlerString();
  Handler<List<String>> handlerListString();
  Handler<List<ApiObject>> handlerListApi();
  Handler<GenericInterface<ClassTypeParam>> handlerParameterizedByClassTypeParam();
  <MethodTypeParam> Handler<GenericInterface<MethodTypeParam>> handlerParameterizedByMethodTypeParam();

}
