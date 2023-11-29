package io.vertx.test.codegen.testdataobject.jsonmapper;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class DataObjectWithAutoMapper {

  AutoMapped value1;
  AutoMappedWithVertxGen value2;

  public DataObjectWithAutoMapper(JsonObject obj) {

  }

  public JsonObject toJson() {
    return null;
  }

  public AutoMapped getValue1() {
    return value1;
  }

  public DataObjectWithAutoMapper setValue1(AutoMapped value1) {
    this.value1 = value1;
    return this;
  }

  public AutoMappedWithVertxGen getValue2() {
    return value2;
  }

  public void setValue2(AutoMappedWithVertxGen value2) {
    this.value2 = value2;
  }
}
