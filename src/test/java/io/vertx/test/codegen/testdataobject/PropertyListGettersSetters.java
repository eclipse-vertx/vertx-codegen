package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyListGettersSetters {

  public static PropertyListGettersSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyListGettersSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular special case
  List<String> getExtraClassPath();
  PropertyListGettersSetters setExtraClassPath(List<String> s);

  // Regular case
  List<String> getStrings();
  PropertyListGettersSetters setStrings(List<String> s);
  List<Instant> getInstants();
  PropertyListGettersSetters setInstants(List<Instant> s);
  List<Integer> getBoxedIntegers();
  PropertyListGettersSetters setBoxedIntegers(List<Integer> i);
  List<Boolean> getBoxedBooleans();
  PropertyListGettersSetters setBoxedBooleans(List<Boolean> b);
  List<Long> getBoxedLongs();
  PropertyListGettersSetters setBoxedLongs(List<Long> b);
  List<ApiObject> getApiObjects();
  PropertyListGettersSetters setApiObjects(List<ApiObject> s);
  List<EmptyDataObject> getDataObjects();
  PropertyListGettersSetters setDataObjects(List<EmptyDataObject> nested);
  List<ToJsonDataObject> getToJsonDataObjects();
  PropertyListGettersSetters setToJsonDataObjects(List<ToJsonDataObject> nested);
  List<JsonObject> getJsonObjects();
  PropertyListGettersSetters setJsonObjects(List<JsonObject> jsonObject);
  List<JsonArray> getJsonArrays();
  PropertyListGettersSetters setJsonArrays(List<JsonArray> jsonArray);
  List<Enumerated> getEnumerateds();
  PropertyListGettersSetters setEnumerateds(List<Enumerated> enumerated);

}
