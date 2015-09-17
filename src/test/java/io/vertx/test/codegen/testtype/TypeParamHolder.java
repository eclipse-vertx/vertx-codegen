package io.vertx.test.codegen.testtype;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface TypeParamHolder<ClassTypeParam> {

  ClassTypeParam classTypeParam();
  <MethodTypeParam> MethodTypeParam methodTypeParam();

}
