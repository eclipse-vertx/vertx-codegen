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
public interface PropertySetSetters {

  public static PropertySetSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertySetSetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular special case
  PropertySetSetters setExtraClassPath(Set<String> s);

  // Regular case
  PropertySetSetters setStrings(Set<String> s);
  PropertySetSetters setInstants(Set<Instant> i);
  PropertySetSetters setBoxedIntegers(Set<Integer> i);
  PropertySetSetters setBoxedBooleans(Set<Boolean> b);
  PropertySetSetters setBoxedLongs(Set<Long> b);
  PropertySetSetters setApiObjects(Set<ApiObject> s);
  PropertySetSetters setDataObjects(Set<EmptyDataObject> nested);
  PropertySetSetters setToJsonDataObjects(Set<ToJsonDataObject> nested);
  PropertySetSetters setJsonObjects(Set<JsonObject> jsonObject);
  PropertySetSetters setJsonArrays(Set<JsonArray> jsonArray);
  PropertySetSetters setEnumerateds(Set<Enumerated> enumerated);

}
