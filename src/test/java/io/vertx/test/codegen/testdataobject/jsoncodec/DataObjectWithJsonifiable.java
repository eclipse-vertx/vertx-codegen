package io.vertx.test.codegen.testdataobject.jsoncodec;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = true)
public class DataObjectWithJsonifiable {

  MyPojo myPojo;

  public DataObjectWithJsonifiable(MyPojo myPojo) {
    this.myPojo = myPojo;
  }

  public DataObjectWithJsonifiable(JsonObject obj) {

  }

  public JsonObject toJson() {
    return null;
  }

  public MyPojo getMyPojo() {
    return myPojo;
  }

  public DataObjectWithJsonifiable setMyPojo(MyPojo myPojo) {
    this.myPojo = myPojo;
    return this;
  }
}
