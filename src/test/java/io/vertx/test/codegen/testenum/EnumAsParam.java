package io.vertx.test.codegen.testenum;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public interface EnumAsParam {

  void callMe(ValidEnum e);
}
