package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.ConverterIgnore;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author Richard Gomez
 */
@DataObject(generateConverter = true)
public class IgnoreConverterDataObject {

  @ConverterIgnore
  private String ignoredProperty;
  private String regularProperty;

  public IgnoreConverterDataObject() {
  }

  public IgnoreConverterDataObject(JsonObject json) {
    IgnoreConverterDataObjectConverter.fromJson(json, this);
  }

  public String getIgnoredProperty() {
    return ignoredProperty;
  }

  public void setIgnoredProperty(String ignoredProperty) {
    this.ignoredProperty = ignoredProperty;
  }

  public String getRegularProperty() {
    return regularProperty;
  }

  public void setRegularProperty(String regularProperty) {
    this.regularProperty = regularProperty;
  }
}
