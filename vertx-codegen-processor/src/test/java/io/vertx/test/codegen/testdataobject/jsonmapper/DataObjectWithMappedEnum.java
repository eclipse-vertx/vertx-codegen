package io.vertx.test.codegen.testdataobject.jsonmapper;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = true)
public class DataObjectWithMappedEnum {

  @GenIgnore
  public static MyEnumWithCustomFactory deserializeMyEnumWithCustomFactory(String value) {
    return (value != null) ? MyEnumWithCustomFactory.of(value) : null;
  }

  @GenIgnore
  public static String serializeMyEnumWithCustomFactory(MyEnumWithCustomFactory value) {
    return (value != null) ? value.getLongName() : null;
  }
  
  MyEnumWithCustomFactory customEnum;

  public DataObjectWithMappedEnum(JsonObject customEnum) {
    
  }

  public DataObjectWithMappedEnum(MyEnumWithCustomFactory customEnum) {
    super();
    this.customEnum = customEnum;
  }

  public MyEnumWithCustomFactory getCustomEnum() {
    return customEnum;
  }

  public DataObjectWithMappedEnum setCustomEnum(MyEnumWithCustomFactory a) {
    this.customEnum = a;
    return this;
  }

  public JsonObject toJson() {
    return null;
  }
  
}
