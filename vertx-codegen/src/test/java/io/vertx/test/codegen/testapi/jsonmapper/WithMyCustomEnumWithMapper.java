package io.vertx.test.codegen.testapi.jsonmapper;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public interface WithMyCustomEnumWithMapper {

  @GenIgnore
  public static MyEnumWithCustomFactory deserializeMyEnumWithCustomFactory(String value) {
    return (value != null) ? MyEnumWithCustomFactory.of(value) : null;
  }

  @GenIgnore
  public static String serializeMyEnumWithCustomFactory(MyEnumWithCustomFactory value) {
    return (value != null) ? value.getLongName() : null;
  }

  MyEnumWithCustomFactory returnMyEnumWithCustomFactory();
  void setMyEnumWithCustomFactory(MyEnumWithCustomFactory myEnum);
}
