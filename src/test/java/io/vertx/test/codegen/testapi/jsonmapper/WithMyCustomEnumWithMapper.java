package io.vertx.test.codegen.testapi.jsonmapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@VertxGen
public interface WithMyCustomEnumWithMapper {

  @GenIgnore
  public static MyEnumWithCustomConstructor deserializeMyEnumWithCustomConstructor(String value) {
    return (value != null) ? MyEnumWithCustomConstructor.of(value) : null;
  }

  @GenIgnore
  public static String serializeMyEnumWithCustomConstructor(MyEnumWithCustomConstructor value) {
    return (value != null) ? value.getLongName() : null;
  }
  
  MyEnumWithCustomConstructor returnMyEnumWithCustomConstructor();
  void setMyEnumWithCustomConstructor(MyEnumWithCustomConstructor myEnum);
}
