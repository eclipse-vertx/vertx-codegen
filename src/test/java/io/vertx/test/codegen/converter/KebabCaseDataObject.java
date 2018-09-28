package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.type.CaseFormat;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * @author Richard Gomez
 */
@DataObject(generateConverter = true, caseFormat = CaseFormat.KEBAB)
public class KebabCaseDataObject {

  private List<Object> listName;
  private Map<String, Object> mapName;
  private String stringName;

  public KebabCaseDataObject() {
  }

  public KebabCaseDataObject(KebabCaseDataObject copy) {
  }

  public KebabCaseDataObject(JsonObject json) {
  }

  public List<Object> getListName() {
    return listName;
  }

  public void setListName(List<Object> listName) {
    this.listName = listName;
  }

  public Map<String, Object> getMapName() {
    return mapName;
  }

  public void setMapName(Map<String, Object> mapName) {
    this.mapName = mapName;
  }

  public String getStringName() {
    return stringName;
  }

  public void setStringName(String stringName) {
    this.stringName = stringName;
  }

}
