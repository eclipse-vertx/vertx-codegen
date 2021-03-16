package io.vertx.test.codegen.testdataobject.jsonmapper;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = true)
public class DataObjectWithEnumWithMapper {

  @GenIgnore
  public static MyEnumWithCustomConstructor deserializeMyEnumWithCustomConstructor(String value) {
    return (value != null) ? MyEnumWithCustomConstructor.of(value) : null;
  }

  @GenIgnore
  public static String serializeMyEnumWithCustomConstructor(MyEnumWithCustomConstructor value) {
    return (value != null) ? value.getLongName() : null;
  }
  
  MyEnumWithCustomConstructor customEnum;

  public DataObjectWithEnumWithMapper(JsonObject customEnum) {
    
  }

  public DataObjectWithEnumWithMapper(MyEnumWithCustomConstructor customEnum) {
    super();
    this.customEnum = customEnum;
  }

  public MyEnumWithCustomConstructor getCustomEnum() {
    return customEnum;
  }

  public DataObjectWithEnumWithMapper setCustomEnum(MyEnumWithCustomConstructor a) {
    this.customEnum = a;
    return this;
  }

  public JsonObject toJson() {
    return null;
  }
  
}
