package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.DataObjectProperty;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * @author Richard Gomez
 */
@DataObject()
public class IndividualPropertyValueEmptyDataObject {

  @DataObjectProperty("")
  private List<Object> fooList;
  private Map<String, Object> barMap;
  private String bazString;

  public IndividualPropertyValueEmptyDataObject() {
  }

  public IndividualPropertyValueEmptyDataObject(IndividualPropertyValueEmptyDataObject copy) {
  }

  public IndividualPropertyValueEmptyDataObject(JsonObject json) {
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
