package io.vertx.test.codegen.testdataobject.jsoncodec;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = true)
public class DataObjectWithPojoWithCodec {

  MyPojo myPojo;

  public DataObjectWithPojoWithCodec(MyPojo myPojo) {
    this.myPojo = myPojo;
  }

  public DataObjectWithPojoWithCodec(JsonObject obj) {

  }

  public JsonObject toJson() {
    return null;
  }

  public MyPojo getMyPojo() {
    return myPojo;
  }

  public DataObjectWithPojoWithCodec setMyPojo(MyPojo myPojo) {
    this.myPojo = myPojo;
    return this;
  }
}
