package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.DataObjectProperty;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * @author Richard Gomez
 */
@DataObject(generateConverter = true)
public class IndividualPropertyValueDataObject {

  @DataObjectProperty("listOfFoo")
  private List<Object> fooList;
  @DataObjectProperty("map-of-bar")
  private Map<String, Object> barMap;
  @DataObjectProperty("string_of_baz")
  private String bazString;

  public IndividualPropertyValueDataObject() {
  }

  public IndividualPropertyValueDataObject(IndividualPropertyValueDataObject copy) {
  }

  public IndividualPropertyValueDataObject(JsonObject json) {
  }

  public List<Object> getFooList() {
    return fooList;
  }

  public void setFooList(List<Object> fooList) {
    this.fooList = fooList;
  }

  public Map<String, Object> getBarMap() {
    return barMap;
  }

  public void setBarMap(Map<String, Object> barMap) {
    this.barMap = barMap;
  }

  public String getBazString() {
    return bazString;
  }

  public void setBazString(String bazString) {
    this.bazString = bazString;
  }
}
