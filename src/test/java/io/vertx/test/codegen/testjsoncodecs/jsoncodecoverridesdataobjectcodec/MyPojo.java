package io.vertx.test.codegen.testjsoncodecs.jsoncodecoverridesdataobjectcodec;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class MyPojo {

  private int a;

  public MyPojo(JsonObject json) {
  }

  public JsonObject toJson() {
    return new JsonObject();
  }

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }
}
