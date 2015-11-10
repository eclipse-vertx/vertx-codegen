package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class DataObjectWithLists {

  private static <T> JsonArray toArray(List<T> list) {
    JsonArray array = new JsonArray();
    array.getList().addAll(list);
    return array;
  }

  private static <T> List<T> fromArray(JsonObject obj, String name) {
    return fromArray(obj, name, o->(T)o);
  }

  private static <T> List<T> fromArray(JsonObject obj, String name, Function<Object, T> converter) {
    JsonArray array = obj.getJsonArray(name);
    if (array != null) {
      List<?> list = array.getList();
      return list.stream().map(converter).collect(Collectors.toList());
    } else {
      return null;
    }
  }

  List<Short> shortValues = new ArrayList<>();
  List<Integer> integerValues = new ArrayList<>();
  List<Long> longValues = new ArrayList<>();
  List<Float> floatValues = new ArrayList<>();
  List<Double> doubleValues = new ArrayList<>();
  List<Boolean> booleanValues = new ArrayList<>();
  List<String> stringValues = new ArrayList<>();
  List<JsonObject> jsonObjectValues = new ArrayList<>();
  List<JsonArray> jsonArrayValues = new ArrayList<>();
  List<TestDataObject> dataObjectValues = new ArrayList<>();
  List<TestEnum> enumValues = new ArrayList<>();
  List<TestGenEnum> genEnumValues = new ArrayList<>();

  public DataObjectWithLists() {
  }

  public DataObjectWithLists(DataObjectWithLists that) {
    throw new UnsupportedOperationException("not used");
  }

  public DataObjectWithLists(JsonObject json) {
    booleanValues = fromArray(json, "booleanValues");
    shortValues = fromArray(json, "shortValues", o -> Short.parseShort(o.toString()));
    integerValues = fromArray(json, "integerValues");
    longValues = fromArray(json, "longValues", o -> Long.parseLong(o.toString()));
    floatValues = fromArray(json, "floatValues", o -> Float.parseFloat(o.toString()));
    doubleValues = fromArray(json, "doubleValues");
    stringValues = fromArray(json, "stringValues");
    jsonObjectValues = fromArray(json, "jsonObjectValues", o -> new JsonObject((Map<String, Object>) o));
    jsonArrayValues = fromArray(json, "jsonArrayValues", o -> new JsonArray((List) o));
    dataObjectValues = fromArray(json, "dataObjectValues", o -> new TestDataObject(new JsonObject((Map<String, Object>) o)));
    enumValues = fromArray(json, "enumValues", o -> TestEnum.valueOf(o.toString()));
    genEnumValues = fromArray(json, "genEnumValues", o -> TestGenEnum.valueOf(o.toString()));
  }

  public DataObjectWithLists setShortValues(List<Short> shortValues) {
    this.shortValues = shortValues;
    return this;
  }

  public DataObjectWithLists setIntegerValues(List<Integer> integerValues) {
    this.integerValues = integerValues;
    return this;
  }

  public DataObjectWithLists setLongValues(List<Long> longValues) {
    this.longValues = longValues;
    return this;
  }

  public DataObjectWithLists setFloatValues(List<Float> floatValues) {
    this.floatValues = floatValues;
    return this;
  }

  public DataObjectWithLists setDoubleValues(List<Double> doubleValues) {
    this.doubleValues = doubleValues;
    return this;
  }

  public DataObjectWithLists setBooleanValues(List<Boolean> booleanValues) {
    this.booleanValues = booleanValues;
    return this;
  }

  public DataObjectWithLists setStringValues(List<String> stringValue) {
    this.stringValues = stringValue;
    return this;
  }

  public DataObjectWithLists setEnumValues(List<TestEnum> enumValues) {
    this.enumValues = enumValues;
    return this;
  }

  public DataObjectWithLists setGenEnumValues(List<TestGenEnum> genEnumValues) {
    this.genEnumValues = genEnumValues;
    return this;
  }

  public DataObjectWithLists setDataObjectValues(List<TestDataObject> dataObjectValues) {
    this.dataObjectValues = dataObjectValues;
    return this;
  }

  public DataObjectWithLists setJsonObjectValues(List<JsonObject> jsonObjectValues) {
    this.jsonObjectValues = jsonObjectValues;
    return this;
  }

  public DataObjectWithLists setJsonArrayValues(List<JsonArray> jsonArrayValues) {
    this.jsonArrayValues = jsonArrayValues;
    return this;
  }

  public JsonObject toJson() {
    return new JsonObject().
        put("booleanValues", toArray(booleanValues)).
        put("shortValues", toArray(shortValues)).
        put("integerValues", toArray(integerValues)).
        put("longValues", toArray(longValues)).
        put("floatValues", toArray(floatValues)).
        put("doubleValues", toArray(doubleValues)).
        put("stringValues", toArray(stringValues)).
        put("jsonObjectValues", toArray(jsonObjectValues)).
        put("jsonArrayValues", toArray(jsonArrayValues)).
        put("dataObjectValues", toArray(dataObjectValues.stream().map(o -> o.toJson().getMap()).collect(Collectors.toList()))).
        put("enumValues", toArray(enumValues)).
        put("genEnumValues", toArray(genEnumValues));
  }
}
