package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertySetGettersSetters {

  public static PropertySetGettersSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertySetGettersSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular special case
  Set<String> getExtraClassPath();
  PropertySetGettersSetters setExtraClassPath(Set<String> s);

  // Regular case
  Set<String> getStrings();
  PropertySetGettersSetters setStrings(Set<String> s);
  Set<Instant> getInstants();
  PropertySetGettersSetters setInstants(Set<Instant> i);
  Set<Integer> getBoxedIntegers();
  PropertySetGettersSetters setBoxedIntegers(Set<Integer> i);
  Set<Boolean> getBoxedBooleans();
  PropertySetGettersSetters setBoxedBooleans(Set<Boolean> b);
  Set<Long> getBoxedLongs();
  PropertySetGettersSetters setBoxedLongs(Set<Long> b);
  Set<ApiObject> getApiObjects();
  PropertySetGettersSetters setApiObjects(Set<ApiObject> s);
  Set<EmptyDataObject> getDataObjects();
  PropertySetGettersSetters setDataObjects(Set<EmptyDataObject> nested);
  Set<ToJsonDataObject> getToJsonDataObjects();
  PropertySetGettersSetters setToJsonDataObjects(Set<ToJsonDataObject> nested);
  Set<JsonObject> getJsonObjects();
  PropertySetGettersSetters setJsonObjects(Set<JsonObject> jsonObject);
  Set<JsonArray> getJsonArrays();
  PropertySetGettersSetters setJsonArrays(Set<JsonArray> jsonArray);
  Set<Enumerated> getEnumerateds();
  PropertySetGettersSetters setEnumerateds(Set<Enumerated> enumerated);

}
