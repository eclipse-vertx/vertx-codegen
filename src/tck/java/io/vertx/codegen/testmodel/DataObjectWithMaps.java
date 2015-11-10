package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class DataObjectWithMaps {

  private static <T> JsonObject toObject(Map<String, T> map) {
    return toObject(map, o -> o);
  }

  private static <T> JsonObject toObject(Map<String, T> map, Function<T, Object> converter) {
    JsonObject object = new JsonObject();
    map.forEach((key, value) -> {
      object.getMap().put(key, converter.apply(value));
    });
    return object;
  }

  private static <T> Map<String, T> fromObject(JsonObject obj, String name) {
    return fromObject(obj, name, o -> (T) o);
  }

  private static <T> Map<String, T> fromObject(JsonObject obj, String name, Function<Object, T> converter) {
    JsonObject array = obj.getJsonObject(name);
    if (array != null) {
      Map<String, Object> map = array.getMap();
      map.entrySet().forEach(entry -> entry.setValue(converter.apply(entry.getValue())));
      return (Map<String, T>) map;
    } else {
      return null;
    }
  }

  Map<String, Short> shortValues = new HashMap<>();
  Map<String, Integer> integerValues = new HashMap<>();
  Map<String, Long> longValues = new HashMap<>();
  Map<String, Float> floatValues = new HashMap<>();
  Map<String, Double> doubleValues = new HashMap<>();
  Map<String, Boolean> booleanValues = new HashMap<>();
  Map<String, String> stringValues = new HashMap<>();
  Map<String, JsonObject> jsonObjectValues = new HashMap<>();
  Map<String, JsonArray> jsonArrayValues = new HashMap<>();
  Map<String, TestDataObject> dataObjectValues = new HashMap<>();
  Map<String, TestEnum> enumValues = new HashMap<>();
  Map<String, TestGenEnum> genEnumValues = new HashMap<>();

  public DataObjectWithMaps() {
  }

  public DataObjectWithMaps(DataObjectWithMaps that) {
    throw new UnsupportedOperationException("not used");
  }

  public DataObjectWithMaps(JsonObject json) {
    booleanValues = fromObject(json, "booleanValues");
    shortValues = fromObject(json, "shortValues", o -> Short.parseShort(o.toString()));
    integerValues = fromObject(json, "integerValues");
    longValues = fromObject(json, "longValues", o -> Long.parseLong(o.toString()));
    floatValues = fromObject(json, "floatValues", o -> Float.parseFloat(o.toString()));
    doubleValues = fromObject(json, "doubleValues");
    stringValues = fromObject(json, "stringValues");
    jsonObjectValues = fromObject(json, "jsonObjectValues", o -> new JsonObject((Map<String, Object>) o));
    jsonArrayValues = fromObject(json, "jsonArrayValues", o -> new JsonArray((List) o));
    dataObjectValues = fromObject(json, "dataObjectValues", o -> new TestDataObject(new JsonObject((Map<String, Object>) o)));
    enumValues = fromObject(json, "enumValues", o -> TestEnum.valueOf(o.toString()));
    genEnumValues = fromObject(json, "genEnumValues", o -> TestGenEnum.valueOf(o.toString()));
  }

  public DataObjectWithMaps setShortValues(Map<String, Short> shortValues) {
    this.shortValues = shortValues;
    return this;
  }

  public DataObjectWithMaps setIntegerValues(Map<String, Integer> integerValues) {
    this.integerValues = integerValues;
    return this;
  }

  public DataObjectWithMaps setLongValues(Map<String, Long> longValues) {
    this.longValues = longValues;
    return this;
  }

  public DataObjectWithMaps setFloatValues(Map<String, Float> floatValues) {
    this.floatValues = floatValues;
    return this;
  }

  public DataObjectWithMaps setDoubleValues(Map<String, Double> doubleValues) {
    this.doubleValues = doubleValues;
    return this;
  }

  public DataObjectWithMaps setBooleanValues(Map<String, Boolean> booleanValues) {
    this.booleanValues = booleanValues;
    return this;
  }

  public DataObjectWithMaps setStringValues(Map<String, String> stringValue) {
    this.stringValues = stringValue;
    return this;
  }

  public DataObjectWithMaps setEnumValues(Map<String, TestEnum> enumValues) {
    this.enumValues = enumValues;
    return this;
  }

  public DataObjectWithMaps setGenEnumValues(Map<String, TestGenEnum> genEnumValues) {
    this.genEnumValues = genEnumValues;
    return this;
  }

  public DataObjectWithMaps setDataObjectValues(Map<String, TestDataObject> dataObjectValues) {
    this.dataObjectValues = dataObjectValues;
    return this;
  }

  public DataObjectWithMaps setJsonObjectValues(Map<String, JsonObject> jsonObjectValues) {
    this.jsonObjectValues = jsonObjectValues;
    return this;
  }

  public DataObjectWithMaps setJsonArrayValues(Map<String, JsonArray> jsonArrayValues) {
    this.jsonArrayValues = jsonArrayValues;
    return this;
  }

  public JsonObject toJson() {
    return new JsonObject().
        put("booleanValues", toObject(booleanValues)).
        put("shortValues", toObject(shortValues)).
        put("integerValues", toObject(integerValues)).
        put("longValues", toObject(longValues)).
        put("floatValues", toObject(floatValues)).
        put("doubleValues", toObject(doubleValues)).
        put("stringValues", toObject(stringValues)).
        put("jsonObjectValues", toObject(jsonObjectValues)).
        put("jsonArrayValues", toObject(jsonArrayValues)).
        put("dataObjectValues", toObject(dataObjectValues, TestDataObject::toJson)).
        put("enumValues", toObject(enumValues)).
        put("genEnumValues", toObject(genEnumValues));
  }
}
