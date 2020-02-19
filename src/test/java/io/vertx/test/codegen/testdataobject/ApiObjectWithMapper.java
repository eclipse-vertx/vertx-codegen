package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.Mapper;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ApiObjectWithMapper {

  @Mapper
  static ApiObjectWithMapper fromJson(JsonObject json) {
    throw new UnsupportedOperationException();
  }

  @Mapper
  static JsonObject toJson(ApiObjectWithMapper obj) {
    throw new UnsupportedOperationException();
  }

  String getFoo();

  void setFoo(String foo);

}
