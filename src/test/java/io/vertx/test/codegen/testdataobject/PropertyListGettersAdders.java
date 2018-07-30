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
public interface PropertyListGettersAdders {

  public static PropertyListGettersAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyListGettersAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  List<String> getStrings();
  PropertyListGettersAdders addString(String s);
  List<Instant> getInstants();
  PropertyListGettersAdders addInstant(Instant i);
  List<Integer> getBoxedIntegers();
  PropertyListGettersAdders addBoxedInteger(Integer i);
  List<Boolean> getBoxedBooleans();
  PropertyListGettersAdders addBoxedBoolean(Boolean b);
  List<Long> getBoxedLongs();
  PropertyListGettersAdders addBoxedLong(Long b);

  List<ApiObject> getApiObjects();
  PropertyListGettersAdders addApiObject(ApiObject s);
  List<EmptyDataObject> getDataObjects();
  PropertyListGettersAdders addDataObject(EmptyDataObject s);
  List<ToJsonDataObject> getToJsonDataObjects();
  PropertyListGettersAdders addToJsonDataObject(ToJsonDataObject s);

  List<JsonObject> getJsonObjects();
  PropertySetters addJsonObject(JsonObject jsonObject);
  List<JsonArray> getJsonArrays();
  PropertySetters addJsonArray(JsonArray jsonArray);

  List<Enumerated> getEnumerateds();
  PropertySetters addEnumerated(Enumerated enumerated);
}
