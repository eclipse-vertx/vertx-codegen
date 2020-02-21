package io.vertx.test.codegen.testapi.jsonmapper;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

@VertxGen
public interface WithMyPojo {

  @GenIgnore
  static MyPojo deserializeMyPojo(Integer value) {
    return new MyPojo().setA(value);
  }

  @GenIgnore
  static Integer serializeMyPojo(MyPojo value) {
    return value.getA();
  }

  MyPojo returnMyPojo();
  List<MyPojo> returnMyPojoList();
  Set<MyPojo> returnMyPojoSet();
  Map<String, MyPojo> returnMyPojoMap();

  void myPojoParam(MyPojo p);
  void myPojoListParam(List<MyPojo> p);
  void myPojoSetParam(Set<MyPojo> p);
  void myPojoMapParam(Map<String, MyPojo> p);

  void myPojoHandler(Handler<MyPojo> handler);
  void myPojoAsyncResultHandler(Handler<AsyncResult<MyPojo>> handler);

}
