package io.vertx.test.codegen.testdataobject.jsonmapper;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.json.JsonObject;

@DataObject
public class DataObjectWithPojoWithMapper {

  @GenIgnore
  public static MyPojo serializeMyPojo(Integer val) {
    return new MyPojo().setA(val);
  }

  @GenIgnore
  public static Integer deserializeMyPojo(MyPojo val) {
    return val.getA();
  }

  MyPojo myPojo;

  public DataObjectWithPojoWithMapper(MyPojo myPojo) {
    this.myPojo = myPojo;
  }

  public DataObjectWithPojoWithMapper(JsonObject obj) {

  }

  public JsonObject toJson() {
    return null;
  }

  public MyPojo getMyPojo() {
    return myPojo;
  }

  public DataObjectWithPojoWithMapper setMyPojo(MyPojo myPojo) {
    this.myPojo = myPojo;
    return this;
  }
}
