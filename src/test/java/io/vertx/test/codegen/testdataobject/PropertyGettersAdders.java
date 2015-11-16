package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyGettersAdders {

  public static PropertyGettersAdders dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertyGettersAdders dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  List<String> getStrings();
  PropertyGettersAdders addString(String s);
  List<Integer> getBoxedIntegers();
  PropertyGettersAdders addBoxedInteger(Integer i);
  List<Boolean> getBoxedBooleans();
  PropertyGettersAdders addBoxedBoolean(Boolean b);
  List<Long> getBoxedLongs();
  PropertyGettersAdders addBoxedLong(Long b);

  List<ApiObject> getApiObjects();
  PropertyGettersAdders addApiObject(ApiObject s);
  List<EmptyDataObject> getDataObjects();
  PropertyGettersAdders addDataObject(EmptyDataObject s);
  List<ToJsonDataObject> getToJsonDataObjects();
  PropertyGettersAdders addToJsonDataObject(ToJsonDataObject s);

  List<JsonObject> getJsonObjects();
  PropertySetters addJsonObject(JsonObject jsonObject);
  List<JsonArray> getJsonArrays();
  PropertySetters addJsonArray(JsonArray jsonArray);

  List<Enumerated> getEnumerateds();
  PropertySetters addEnumerated(Enumerated enumerated);
}
