package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.TestDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.TestDataObject} original class using Vert.x codegen.
 */
public class TestDataObjectConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, TestDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "addedBoxedBooleanValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Boolean)
                obj.addAddedBoxedBooleanValue((Boolean)item);
            });
          }
          break;
        case "addedBoxedByteValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                obj.addAddedBoxedByteValue(((Number)item).byteValue());
            });
          }
          break;
        case "addedBoxedCharValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedBoxedCharValue(((String)item).charAt(0));
            });
          }
          break;
        case "addedBoxedDoubleValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                obj.addAddedBoxedDoubleValue(((Number)item).doubleValue());
            });
          }
          break;
        case "addedBoxedFloatValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                obj.addAddedBoxedFloatValue(((Number)item).floatValue());
            });
          }
          break;
        case "addedBoxedIntValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                obj.addAddedBoxedIntValue(((Number)item).intValue());
            });
          }
          break;
        case "addedBoxedLongValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                obj.addAddedBoxedLongValue(((Number)item).longValue());
            });
          }
          break;
        case "addedBoxedShortValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                obj.addAddedBoxedShortValue(((Number)item).shortValue());
            });
          }
          break;
        case "addedBuffers":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedBuffer(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)item)));
            });
          }
          break;
        case "addedEnumMappeds":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedEnumMapped(io.vertx.test.codegen.converter.TestDataObject.deserializeCustomEnum((String)item));
            });
          }
          break;
        case "addedHttpMethods":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedHttpMethod(java.util.concurrent.TimeUnit.valueOf((String)item));
            });
          }
          break;
        case "addedJsonArrays":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonArray)
                obj.addAddedJsonArray(((JsonArray)item).copy());
            });
          }
          break;
        case "addedJsonObjectDataObjects":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                obj.addAddedJsonObjectDataObject(new io.vertx.test.codegen.converter.NestedJsonObjectDataObject((io.vertx.core.json.JsonObject)item));
            });
          }
          break;
        case "addedJsonObjects":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                obj.addAddedJsonObject(((JsonObject)item).copy());
            });
          }
          break;
        case "addedMethodMappeds":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedMethodMapped(io.vertx.test.codegen.converter.TestDataObject.deserializeZonedDateTime((String)item));
            });
          }
          break;
        case "addedObjects":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                obj.addAddedObject(item);
            });
          }
          break;
        case "addedStringDataObjects":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedStringDataObject(new io.vertx.test.codegen.converter.NestedStringDataObject((java.lang.String)item));
            });
          }
          break;
        case "addedStringValues":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addAddedStringValue((String)item);
            });
          }
          break;
        case "boxedBoolean":
          if (member.getValue() instanceof Boolean) {
            obj.setBoxedBoolean((Boolean)member.getValue());
          }
          break;
        case "boxedBooleanList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Boolean> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Boolean)
                list.add((Boolean)item);
            });
            obj.setBoxedBooleanList(list);
          }
          break;
        case "boxedBooleanSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Boolean> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Boolean)
                list.add((Boolean)item);
            });
            obj.setBoxedBooleanSet(list);
          }
          break;
        case "boxedBooleanValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Boolean> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Boolean)
                map.put(entry.getKey(), (Boolean)entry.getValue());
            });
            obj.setBoxedBooleanValueMap(map);
          }
          break;
        case "boxedByte":
          if (member.getValue() instanceof Number) {
            obj.setBoxedByte(((Number)member.getValue()).byteValue());
          }
          break;
        case "boxedByteList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Byte> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).byteValue());
            });
            obj.setBoxedByteList(list);
          }
          break;
        case "boxedByteSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Byte> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).byteValue());
            });
            obj.setBoxedByteSet(list);
          }
          break;
        case "boxedByteValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Byte> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                map.put(entry.getKey(), ((Number)entry.getValue()).byteValue());
            });
            obj.setBoxedByteValueMap(map);
          }
          break;
        case "boxedChar":
          if (member.getValue() instanceof String) {
            obj.setBoxedChar(((String)member.getValue()).charAt(0));
          }
          break;
        case "boxedCharList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Character> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(((String)item).charAt(0));
            });
            obj.setBoxedCharList(list);
          }
          break;
        case "boxedCharSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Character> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(((String)item).charAt(0));
            });
            obj.setBoxedCharSet(list);
          }
          break;
        case "boxedCharValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Character> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), ((String)entry.getValue()).charAt(0));
            });
            obj.setBoxedCharValueMap(map);
          }
          break;
        case "boxedDouble":
          if (member.getValue() instanceof Number) {
            obj.setBoxedDouble(((Number)member.getValue()).doubleValue());
          }
          break;
        case "boxedDoubleList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Double> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).doubleValue());
            });
            obj.setBoxedDoubleList(list);
          }
          break;
        case "boxedDoubleSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Double> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).doubleValue());
            });
            obj.setBoxedDoubleSet(list);
          }
          break;
        case "boxedDoubleValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Double> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                map.put(entry.getKey(), ((Number)entry.getValue()).doubleValue());
            });
            obj.setBoxedDoubleValueMap(map);
          }
          break;
        case "boxedFloat":
          if (member.getValue() instanceof Number) {
            obj.setBoxedFloat(((Number)member.getValue()).floatValue());
          }
          break;
        case "boxedFloatList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Float> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).floatValue());
            });
            obj.setBoxedFloatList(list);
          }
          break;
        case "boxedFloatSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Float> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).floatValue());
            });
            obj.setBoxedFloatSet(list);
          }
          break;
        case "boxedFloatValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Float> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                map.put(entry.getKey(), ((Number)entry.getValue()).floatValue());
            });
            obj.setBoxedFloatValueMap(map);
          }
          break;
        case "boxedInt":
          if (member.getValue() instanceof Number) {
            obj.setBoxedInt(((Number)member.getValue()).intValue());
          }
          break;
        case "boxedIntList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Integer> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).intValue());
            });
            obj.setBoxedIntList(list);
          }
          break;
        case "boxedIntSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Integer> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).intValue());
            });
            obj.setBoxedIntSet(list);
          }
          break;
        case "boxedIntValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Integer> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                map.put(entry.getKey(), ((Number)entry.getValue()).intValue());
            });
            obj.setBoxedIntValueMap(map);
          }
          break;
        case "boxedLong":
          if (member.getValue() instanceof Number) {
            obj.setBoxedLong(((Number)member.getValue()).longValue());
          }
          break;
        case "boxedLongList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Long> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).longValue());
            });
            obj.setBoxedLongList(list);
          }
          break;
        case "boxedLongSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Long> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).longValue());
            });
            obj.setBoxedLongSet(list);
          }
          break;
        case "boxedLongValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Long> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                map.put(entry.getKey(), ((Number)entry.getValue()).longValue());
            });
            obj.setBoxedLongValueMap(map);
          }
          break;
        case "boxedShort":
          if (member.getValue() instanceof Number) {
            obj.setBoxedShort(((Number)member.getValue()).shortValue());
          }
          break;
        case "boxedShortList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Short> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).shortValue());
            });
            obj.setBoxedShortList(list);
          }
          break;
        case "boxedShortSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Short> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Number)
                list.add(((Number)item).shortValue());
            });
            obj.setBoxedShortSet(list);
          }
          break;
        case "boxedShortValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Short> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                map.put(entry.getKey(), ((Number)entry.getValue()).shortValue());
            });
            obj.setBoxedShortValueMap(map);
          }
          break;
        case "buffer":
          if (member.getValue() instanceof String) {
            obj.setBuffer(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)member.getValue())));
          }
          break;
        case "bufferList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.core.buffer.Buffer> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)item)));
            });
            obj.setBufferList(list);
          }
          break;
        case "bufferMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.core.buffer.Buffer> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)entry.getValue())));
            });
            obj.setBufferMap(map);
          }
          break;
        case "bufferSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.core.buffer.Buffer> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)item)));
            });
            obj.setBufferSet(list);
          }
          break;
        case "enumMapped":
          if (member.getValue() instanceof String) {
            obj.setEnumMapped(io.vertx.test.codegen.converter.TestDataObject.deserializeCustomEnum((String)member.getValue()));
          }
          break;
        case "enumMappedList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.test.codegen.converter.TestCustomEnum> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.test.codegen.converter.TestDataObject.deserializeCustomEnum((String)item));
            });
            obj.setEnumMappedList(list);
          }
          break;
        case "enumMappedMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.test.codegen.converter.TestCustomEnum> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), io.vertx.test.codegen.converter.TestDataObject.deserializeCustomEnum((String)entry.getValue()));
            });
            obj.setEnumMappedMap(map);
          }
          break;
        case "enumMappedSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.test.codegen.converter.TestCustomEnum> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.test.codegen.converter.TestDataObject.deserializeCustomEnum((String)item));
            });
            obj.setEnumMappedSet(list);
          }
          break;
        case "httpMethod":
          if (member.getValue() instanceof String) {
            obj.setHttpMethod(java.util.concurrent.TimeUnit.valueOf((String)member.getValue()));
          }
          break;
        case "httpMethodList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.util.concurrent.TimeUnit> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(java.util.concurrent.TimeUnit.valueOf((String)item));
            });
            obj.setHttpMethodList(list);
          }
          break;
        case "httpMethodMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.util.concurrent.TimeUnit> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), java.util.concurrent.TimeUnit.valueOf((String)entry.getValue()));
            });
            obj.setHttpMethodMap(map);
          }
          break;
        case "httpMethodSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.util.concurrent.TimeUnit> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(java.util.concurrent.TimeUnit.valueOf((String)item));
            });
            obj.setHttpMethodSet(list);
          }
          break;
        case "jsonArray":
          if (member.getValue() instanceof JsonArray) {
            obj.setJsonArray(((JsonArray)member.getValue()).copy());
          }
          break;
        case "jsonArrayList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.core.json.JsonArray> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonArray)
                list.add(((JsonArray)item).copy());
            });
            obj.setJsonArrayList(list);
          }
          break;
        case "jsonArrayMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.core.json.JsonArray> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonArray)
                map.put(entry.getKey(), ((JsonArray)entry.getValue()).copy());
            });
            obj.setJsonArrayMap(map);
          }
          break;
        case "jsonArraySet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.core.json.JsonArray> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonArray)
                list.add(((JsonArray)item).copy());
            });
            obj.setJsonArraySet(list);
          }
          break;
        case "jsonObject":
          if (member.getValue() instanceof JsonObject) {
            obj.setJsonObject(((JsonObject)member.getValue()).copy());
          }
          break;
        case "jsonObjectDataObject":
          if (member.getValue() instanceof JsonObject) {
            obj.setJsonObjectDataObject(new io.vertx.test.codegen.converter.NestedJsonObjectDataObject((io.vertx.core.json.JsonObject)member.getValue()));
          }
          break;
        case "jsonObjectDataObjectList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.test.codegen.converter.NestedJsonObjectDataObject> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.vertx.test.codegen.converter.NestedJsonObjectDataObject((io.vertx.core.json.JsonObject)item));
            });
            obj.setJsonObjectDataObjectList(list);
          }
          break;
        case "jsonObjectDataObjectMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.test.codegen.converter.NestedJsonObjectDataObject> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonObject)
                map.put(entry.getKey(), new io.vertx.test.codegen.converter.NestedJsonObjectDataObject((io.vertx.core.json.JsonObject)entry.getValue()));
            });
            obj.setJsonObjectDataObjectMap(map);
          }
          break;
        case "jsonObjectDataObjectSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.test.codegen.converter.NestedJsonObjectDataObject> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.vertx.test.codegen.converter.NestedJsonObjectDataObject((io.vertx.core.json.JsonObject)item));
            });
            obj.setJsonObjectDataObjectSet(list);
          }
          break;
        case "jsonObjectList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.core.json.JsonObject> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(((JsonObject)item).copy());
            });
            obj.setJsonObjectList(list);
          }
          break;
        case "jsonObjectMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.core.json.JsonObject> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonObject)
                map.put(entry.getKey(), ((JsonObject)entry.getValue()).copy());
            });
            obj.setJsonObjectMap(map);
          }
          break;
        case "jsonObjectSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.core.json.JsonObject> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(((JsonObject)item).copy());
            });
            obj.setJsonObjectSet(list);
          }
          break;
        case "keyedBoxedBooleanValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Boolean)
                obj.addKeyedBoxedBooleanValue(entry.getKey(), (Boolean)entry.getValue());
            });
          }
          break;
        case "keyedBoxedByteValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                obj.addKeyedBoxedByteValue(entry.getKey(), ((Number)entry.getValue()).byteValue());
            });
          }
          break;
        case "keyedBoxedCharValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedBoxedCharValue(entry.getKey(), ((String)entry.getValue()).charAt(0));
            });
          }
          break;
        case "keyedBoxedDoubleValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                obj.addKeyedBoxedDoubleValue(entry.getKey(), ((Number)entry.getValue()).doubleValue());
            });
          }
          break;
        case "keyedBoxedFloatValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                obj.addKeyedBoxedFloatValue(entry.getKey(), ((Number)entry.getValue()).floatValue());
            });
          }
          break;
        case "keyedBoxedIntValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                obj.addKeyedBoxedIntValue(entry.getKey(), ((Number)entry.getValue()).intValue());
            });
          }
          break;
        case "keyedBoxedLongValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                obj.addKeyedBoxedLongValue(entry.getKey(), ((Number)entry.getValue()).longValue());
            });
          }
          break;
        case "keyedBoxedShortValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Number)
                obj.addKeyedBoxedShortValue(entry.getKey(), ((Number)entry.getValue()).shortValue());
            });
          }
          break;
        case "keyedBufferValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedBufferValue(entry.getKey(), io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)entry.getValue())));
            });
          }
          break;
        case "keyedEnumMappedValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedEnumMappedValue(entry.getKey(), io.vertx.test.codegen.converter.TestDataObject.deserializeCustomEnum((String)entry.getValue()));
            });
          }
          break;
        case "keyedEnumValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedEnumValue(entry.getKey(), java.util.concurrent.TimeUnit.valueOf((String)entry.getValue()));
            });
          }
          break;
        case "keyedJsonArrayValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonArray)
                obj.addKeyedJsonArrayValue(entry.getKey(), ((JsonArray)entry.getValue()).copy());
            });
          }
          break;
        case "keyedJsonObjectDataObjectValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonObject)
                obj.addKeyedJsonObjectDataObjectValue(entry.getKey(), new io.vertx.test.codegen.converter.NestedJsonObjectDataObject((io.vertx.core.json.JsonObject)entry.getValue()));
            });
          }
          break;
        case "keyedJsonObjectValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonObject)
                obj.addKeyedJsonObjectValue(entry.getKey(), ((JsonObject)entry.getValue()).copy());
            });
          }
          break;
        case "keyedMethodMappedValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedMethodMappedValue(entry.getKey(), io.vertx.test.codegen.converter.TestDataObject.deserializeZonedDateTime((String)entry.getValue()));
            });
          }
          break;
        case "keyedObjectValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                obj.addKeyedObjectValue(entry.getKey(), entry.getValue());
            });
          }
          break;
        case "keyedStringDataObjectValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedStringDataObjectValue(entry.getKey(), new io.vertx.test.codegen.converter.NestedStringDataObject((java.lang.String)entry.getValue()));
            });
          }
          break;
        case "keyedStringValues":
          if (member.getValue() instanceof JsonObject) {
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                obj.addKeyedStringValue(entry.getKey(), (String)entry.getValue());
            });
          }
          break;
        case "methodMapped":
          if (member.getValue() instanceof String) {
            obj.setMethodMapped(io.vertx.test.codegen.converter.TestDataObject.deserializeZonedDateTime((String)member.getValue()));
          }
          break;
        case "methodMappedList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.time.ZonedDateTime> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.test.codegen.converter.TestDataObject.deserializeZonedDateTime((String)item));
            });
            obj.setMethodMappedList(list);
          }
          break;
        case "methodMappedMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.time.ZonedDateTime> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), io.vertx.test.codegen.converter.TestDataObject.deserializeZonedDateTime((String)entry.getValue()));
            });
            obj.setMethodMappedMap(map);
          }
          break;
        case "methodMappedSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.time.ZonedDateTime> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.test.codegen.converter.TestDataObject.deserializeZonedDateTime((String)item));
            });
            obj.setMethodMappedSet(list);
          }
          break;
        case "notConvertibleDataObject":
          if (member.getValue() instanceof JsonObject) {
            obj.setNotConvertibleDataObject(new io.vertx.test.codegen.converter.NoConverterDataObject((io.vertx.core.json.JsonObject)member.getValue()));
          }
          break;
        case "notConvertibleDataObjectList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.test.codegen.converter.NoConverterDataObject> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.vertx.test.codegen.converter.NoConverterDataObject((io.vertx.core.json.JsonObject)item));
            });
            obj.setNotConvertibleDataObjectList(list);
          }
          break;
        case "notConvertibleDataObjectMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.test.codegen.converter.NoConverterDataObject> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof JsonObject)
                map.put(entry.getKey(), new io.vertx.test.codegen.converter.NoConverterDataObject((io.vertx.core.json.JsonObject)entry.getValue()));
            });
            obj.setNotConvertibleDataObjectMap(map);
          }
          break;
        case "notConvertibleDataObjectSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.test.codegen.converter.NoConverterDataObject> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(new io.vertx.test.codegen.converter.NoConverterDataObject((io.vertx.core.json.JsonObject)item));
            });
            obj.setNotConvertibleDataObjectSet(list);
          }
          break;
        case "objectList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Object> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setObjectList(list);
          }
          break;
        case "objectMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                map.put(entry.getKey(), entry.getValue());
            });
            obj.setObjectMap(map);
          }
          break;
        case "objectSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.Object> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setObjectSet(list);
          }
          break;
        case "primitiveBoolean":
          if (member.getValue() instanceof Boolean) {
            obj.setPrimitiveBoolean((Boolean)member.getValue());
          }
          break;
        case "primitiveByte":
          if (member.getValue() instanceof Number) {
            obj.setPrimitiveByte(((Number)member.getValue()).byteValue());
          }
          break;
        case "primitiveChar":
          if (member.getValue() instanceof String) {
            obj.setPrimitiveChar(((String)member.getValue()).charAt(0));
          }
          break;
        case "primitiveDouble":
          if (member.getValue() instanceof Number) {
            obj.setPrimitiveDouble(((Number)member.getValue()).doubleValue());
          }
          break;
        case "primitiveFloat":
          if (member.getValue() instanceof Number) {
            obj.setPrimitiveFloat(((Number)member.getValue()).floatValue());
          }
          break;
        case "primitiveInt":
          if (member.getValue() instanceof Number) {
            obj.setPrimitiveInt(((Number)member.getValue()).intValue());
          }
          break;
        case "primitiveLong":
          if (member.getValue() instanceof Number) {
            obj.setPrimitiveLong(((Number)member.getValue()).longValue());
          }
          break;
        case "primitiveShort":
          if (member.getValue() instanceof Number) {
            obj.setPrimitiveShort(((Number)member.getValue()).shortValue());
          }
          break;
        case "string":
          if (member.getValue() instanceof String) {
            obj.setString((String)member.getValue());
          }
          break;
        case "stringDataObject":
          if (member.getValue() instanceof String) {
            obj.setStringDataObject(new io.vertx.test.codegen.converter.NestedStringDataObject((java.lang.String)member.getValue()));
          }
          break;
        case "stringDataObjectList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.test.codegen.converter.NestedStringDataObject> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(new io.vertx.test.codegen.converter.NestedStringDataObject((java.lang.String)item));
            });
            obj.setStringDataObjectList(list);
          }
          break;
        case "stringDataObjectMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, io.vertx.test.codegen.converter.NestedStringDataObject> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), new io.vertx.test.codegen.converter.NestedStringDataObject((java.lang.String)entry.getValue()));
            });
            obj.setStringDataObjectMap(map);
          }
          break;
        case "stringDataObjectSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<io.vertx.test.codegen.converter.NestedStringDataObject> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(new io.vertx.test.codegen.converter.NestedStringDataObject((java.lang.String)item));
            });
            obj.setStringDataObjectSet(list);
          }
          break;
        case "stringList":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setStringList(list);
          }
          break;
        case "stringSet":
          if (member.getValue() instanceof JsonArray) {
            java.util.LinkedHashSet<java.lang.String> list =  new java.util.LinkedHashSet<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setStringSet(list);
          }
          break;
        case "stringValueMap":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof String)
                map.put(entry.getKey(), (String)entry.getValue());
            });
            obj.setStringValueMap(map);
          }
          break;
      }
    }
  }

  public static void toJson(TestDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(TestDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getAddedBoxedBooleanValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedBooleanValues().forEach(item -> array.add(item));
      json.put("addedBoxedBooleanValues", array);
    }
    if (obj.getAddedBoxedByteValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedByteValues().forEach(item -> array.add(item));
      json.put("addedBoxedByteValues", array);
    }
    if (obj.getAddedBoxedCharValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedCharValues().forEach(item -> array.add(Character.toString(item)));
      json.put("addedBoxedCharValues", array);
    }
    if (obj.getAddedBoxedDoubleValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedDoubleValues().forEach(item -> array.add(item));
      json.put("addedBoxedDoubleValues", array);
    }
    if (obj.getAddedBoxedFloatValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedFloatValues().forEach(item -> array.add(item));
      json.put("addedBoxedFloatValues", array);
    }
    if (obj.getAddedBoxedIntValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedIntValues().forEach(item -> array.add(item));
      json.put("addedBoxedIntValues", array);
    }
    if (obj.getAddedBoxedLongValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedLongValues().forEach(item -> array.add(item));
      json.put("addedBoxedLongValues", array);
    }
    if (obj.getAddedBoxedShortValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBoxedShortValues().forEach(item -> array.add(item));
      json.put("addedBoxedShortValues", array);
    }
    if (obj.getAddedBuffers() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedBuffers().forEach(item -> array.add(BASE64_ENCODER.encodeToString(item.getBytes())));
      json.put("addedBuffers", array);
    }
    if (obj.getAddedEnumMappeds() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedEnumMappeds().forEach(item -> array.add(io.vertx.test.codegen.converter.TestDataObject.serializeCustomEnum(item)));
      json.put("addedEnumMappeds", array);
    }
    if (obj.getAddedHttpMethods() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedHttpMethods().forEach(item -> array.add(item.name()));
      json.put("addedHttpMethods", array);
    }
    if (obj.getAddedJsonArrays() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedJsonArrays().forEach(item -> array.add(item));
      json.put("addedJsonArrays", array);
    }
    if (obj.getAddedJsonObjectDataObjects() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedJsonObjectDataObjects().forEach(item -> array.add(item.toJson()));
      json.put("addedJsonObjectDataObjects", array);
    }
    if (obj.getAddedJsonObjects() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedJsonObjects().forEach(item -> array.add(item));
      json.put("addedJsonObjects", array);
    }
    if (obj.getAddedMethodMappeds() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedMethodMappeds().forEach(item -> array.add(io.vertx.test.codegen.converter.TestDataObject.serializeZonedDateTime(item)));
      json.put("addedMethodMappeds", array);
    }
    if (obj.getAddedObjects() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedObjects().forEach(item -> array.add(item));
      json.put("addedObjects", array);
    }
    if (obj.getAddedStringDataObjects() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedStringDataObjects().forEach(item -> array.add(item.toJson()));
      json.put("addedStringDataObjects", array);
    }
    if (obj.getAddedStringValues() != null) {
      JsonArray array = new JsonArray();
      obj.getAddedStringValues().forEach(item -> array.add(item));
      json.put("addedStringValues", array);
    }
    if (obj.isBoxedBoolean() != null) {
      json.put("boxedBoolean", obj.isBoxedBoolean());
    }
    if (obj.getBoxedBooleanList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedBooleanList().forEach(item -> array.add(item));
      json.put("boxedBooleanList", array);
    }
    if (obj.getBoxedBooleanSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedBooleanSet().forEach(item -> array.add(item));
      json.put("boxedBooleanSet", array);
    }
    if (obj.getBoxedBooleanValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedBooleanValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedBooleanValueMap", map);
    }
    if (obj.getBoxedByte() != null) {
      json.put("boxedByte", obj.getBoxedByte());
    }
    if (obj.getBoxedByteList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedByteList().forEach(item -> array.add(item));
      json.put("boxedByteList", array);
    }
    if (obj.getBoxedByteSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedByteSet().forEach(item -> array.add(item));
      json.put("boxedByteSet", array);
    }
    if (obj.getBoxedByteValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedByteValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedByteValueMap", map);
    }
    if (obj.getBoxedChar() != null) {
      json.put("boxedChar", Character.toString(obj.getBoxedChar()));
    }
    if (obj.getBoxedCharList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedCharList().forEach(item -> array.add(Character.toString(item)));
      json.put("boxedCharList", array);
    }
    if (obj.getBoxedCharSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedCharSet().forEach(item -> array.add(Character.toString(item)));
      json.put("boxedCharSet", array);
    }
    if (obj.getBoxedCharValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedCharValueMap().forEach((key, value) -> map.put(key, Character.toString(value)));
      json.put("boxedCharValueMap", map);
    }
    if (obj.getBoxedDouble() != null) {
      json.put("boxedDouble", obj.getBoxedDouble());
    }
    if (obj.getBoxedDoubleList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedDoubleList().forEach(item -> array.add(item));
      json.put("boxedDoubleList", array);
    }
    if (obj.getBoxedDoubleSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedDoubleSet().forEach(item -> array.add(item));
      json.put("boxedDoubleSet", array);
    }
    if (obj.getBoxedDoubleValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedDoubleValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedDoubleValueMap", map);
    }
    if (obj.getBoxedFloat() != null) {
      json.put("boxedFloat", obj.getBoxedFloat());
    }
    if (obj.getBoxedFloatList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedFloatList().forEach(item -> array.add(item));
      json.put("boxedFloatList", array);
    }
    if (obj.getBoxedFloatSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedFloatSet().forEach(item -> array.add(item));
      json.put("boxedFloatSet", array);
    }
    if (obj.getBoxedFloatValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedFloatValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedFloatValueMap", map);
    }
    if (obj.getBoxedInt() != null) {
      json.put("boxedInt", obj.getBoxedInt());
    }
    if (obj.getBoxedIntList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedIntList().forEach(item -> array.add(item));
      json.put("boxedIntList", array);
    }
    if (obj.getBoxedIntSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedIntSet().forEach(item -> array.add(item));
      json.put("boxedIntSet", array);
    }
    if (obj.getBoxedIntValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedIntValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedIntValueMap", map);
    }
    if (obj.getBoxedLong() != null) {
      json.put("boxedLong", obj.getBoxedLong());
    }
    if (obj.getBoxedLongList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedLongList().forEach(item -> array.add(item));
      json.put("boxedLongList", array);
    }
    if (obj.getBoxedLongSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedLongSet().forEach(item -> array.add(item));
      json.put("boxedLongSet", array);
    }
    if (obj.getBoxedLongValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedLongValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedLongValueMap", map);
    }
    if (obj.getBoxedShort() != null) {
      json.put("boxedShort", obj.getBoxedShort());
    }
    if (obj.getBoxedShortList() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedShortList().forEach(item -> array.add(item));
      json.put("boxedShortList", array);
    }
    if (obj.getBoxedShortSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBoxedShortSet().forEach(item -> array.add(item));
      json.put("boxedShortSet", array);
    }
    if (obj.getBoxedShortValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBoxedShortValueMap().forEach((key, value) -> map.put(key, value));
      json.put("boxedShortValueMap", map);
    }
    if (obj.getBuffer() != null) {
      json.put("buffer", BASE64_ENCODER.encodeToString(obj.getBuffer().getBytes()));
    }
    if (obj.getBufferList() != null) {
      JsonArray array = new JsonArray();
      obj.getBufferList().forEach(item -> array.add(BASE64_ENCODER.encodeToString(item.getBytes())));
      json.put("bufferList", array);
    }
    if (obj.getBufferMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBufferMap().forEach((key, value) -> map.put(key, BASE64_ENCODER.encodeToString(value.getBytes())));
      json.put("bufferMap", map);
    }
    if (obj.getBufferSet() != null) {
      JsonArray array = new JsonArray();
      obj.getBufferSet().forEach(item -> array.add(BASE64_ENCODER.encodeToString(item.getBytes())));
      json.put("bufferSet", array);
    }
    if (obj.getEnumMapped() != null) {
      json.put("enumMapped", io.vertx.test.codegen.converter.TestDataObject.serializeCustomEnum(obj.getEnumMapped()));
    }
    if (obj.getEnumMappedList() != null) {
      JsonArray array = new JsonArray();
      obj.getEnumMappedList().forEach(item -> array.add(io.vertx.test.codegen.converter.TestDataObject.serializeCustomEnum(item)));
      json.put("enumMappedList", array);
    }
    if (obj.getEnumMappedMap() != null) {
      JsonObject map = new JsonObject();
      obj.getEnumMappedMap().forEach((key, value) -> map.put(key, io.vertx.test.codegen.converter.TestDataObject.serializeCustomEnum(value)));
      json.put("enumMappedMap", map);
    }
    if (obj.getEnumMappedSet() != null) {
      JsonArray array = new JsonArray();
      obj.getEnumMappedSet().forEach(item -> array.add(io.vertx.test.codegen.converter.TestDataObject.serializeCustomEnum(item)));
      json.put("enumMappedSet", array);
    }
    if (obj.getHttpMethod() != null) {
      json.put("httpMethod", obj.getHttpMethod().name());
    }
    if (obj.getHttpMethodList() != null) {
      JsonArray array = new JsonArray();
      obj.getHttpMethodList().forEach(item -> array.add(item.name()));
      json.put("httpMethodList", array);
    }
    if (obj.getHttpMethodMap() != null) {
      JsonObject map = new JsonObject();
      obj.getHttpMethodMap().forEach((key, value) -> map.put(key, value.name()));
      json.put("httpMethodMap", map);
    }
    if (obj.getHttpMethodSet() != null) {
      JsonArray array = new JsonArray();
      obj.getHttpMethodSet().forEach(item -> array.add(item.name()));
      json.put("httpMethodSet", array);
    }
    if (obj.getJsonArray() != null) {
      json.put("jsonArray", obj.getJsonArray());
    }
    if (obj.getJsonArrayList() != null) {
      JsonArray array = new JsonArray();
      obj.getJsonArrayList().forEach(item -> array.add(item));
      json.put("jsonArrayList", array);
    }
    if (obj.getJsonArrayMap() != null) {
      JsonObject map = new JsonObject();
      obj.getJsonArrayMap().forEach((key, value) -> map.put(key, value));
      json.put("jsonArrayMap", map);
    }
    if (obj.getJsonArraySet() != null) {
      JsonArray array = new JsonArray();
      obj.getJsonArraySet().forEach(item -> array.add(item));
      json.put("jsonArraySet", array);
    }
    if (obj.getJsonObject() != null) {
      json.put("jsonObject", obj.getJsonObject());
    }
    if (obj.getJsonObjectDataObject() != null) {
      json.put("jsonObjectDataObject", obj.getJsonObjectDataObject().toJson());
    }
    if (obj.getJsonObjectDataObjectList() != null) {
      JsonArray array = new JsonArray();
      obj.getJsonObjectDataObjectList().forEach(item -> array.add(item.toJson()));
      json.put("jsonObjectDataObjectList", array);
    }
    if (obj.getJsonObjectDataObjectMap() != null) {
      JsonObject map = new JsonObject();
      obj.getJsonObjectDataObjectMap().forEach((key, value) -> map.put(key, value.toJson()));
      json.put("jsonObjectDataObjectMap", map);
    }
    if (obj.getJsonObjectDataObjectSet() != null) {
      JsonArray array = new JsonArray();
      obj.getJsonObjectDataObjectSet().forEach(item -> array.add(item.toJson()));
      json.put("jsonObjectDataObjectSet", array);
    }
    if (obj.getJsonObjectList() != null) {
      JsonArray array = new JsonArray();
      obj.getJsonObjectList().forEach(item -> array.add(item));
      json.put("jsonObjectList", array);
    }
    if (obj.getJsonObjectMap() != null) {
      JsonObject map = new JsonObject();
      obj.getJsonObjectMap().forEach((key, value) -> map.put(key, value));
      json.put("jsonObjectMap", map);
    }
    if (obj.getJsonObjectSet() != null) {
      JsonArray array = new JsonArray();
      obj.getJsonObjectSet().forEach(item -> array.add(item));
      json.put("jsonObjectSet", array);
    }
    if (obj.getKeyedBoxedBooleanValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedBooleanValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedBooleanValues", map);
    }
    if (obj.getKeyedBoxedByteValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedByteValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedByteValues", map);
    }
    if (obj.getKeyedBoxedCharValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedCharValues().forEach((key, value) -> map.put(key, Character.toString(value)));
      json.put("keyedBoxedCharValues", map);
    }
    if (obj.getKeyedBoxedDoubleValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedDoubleValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedDoubleValues", map);
    }
    if (obj.getKeyedBoxedFloatValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedFloatValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedFloatValues", map);
    }
    if (obj.getKeyedBoxedIntValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedIntValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedIntValues", map);
    }
    if (obj.getKeyedBoxedLongValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedLongValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedLongValues", map);
    }
    if (obj.getKeyedBoxedShortValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBoxedShortValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedBoxedShortValues", map);
    }
    if (obj.getKeyedBufferValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedBufferValues().forEach((key, value) -> map.put(key, BASE64_ENCODER.encodeToString(value.getBytes())));
      json.put("keyedBufferValues", map);
    }
    if (obj.getKeyedEnumMappedValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedEnumMappedValues().forEach((key, value) -> map.put(key, io.vertx.test.codegen.converter.TestDataObject.serializeCustomEnum(value)));
      json.put("keyedEnumMappedValues", map);
    }
    if (obj.getKeyedEnumValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedEnumValues().forEach((key, value) -> map.put(key, value.name()));
      json.put("keyedEnumValues", map);
    }
    if (obj.getKeyedJsonArrayValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedJsonArrayValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedJsonArrayValues", map);
    }
    if (obj.getKeyedJsonObjectDataObjectValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedJsonObjectDataObjectValues().forEach((key, value) -> map.put(key, value.toJson()));
      json.put("keyedJsonObjectDataObjectValues", map);
    }
    if (obj.getKeyedJsonObjectValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedJsonObjectValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedJsonObjectValues", map);
    }
    if (obj.getKeyedMethodMappedValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedMethodMappedValues().forEach((key, value) -> map.put(key, io.vertx.test.codegen.converter.TestDataObject.serializeZonedDateTime(value)));
      json.put("keyedMethodMappedValues", map);
    }
    if (obj.getKeyedObjectValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedObjectValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedObjectValues", map);
    }
    if (obj.getKeyedStringDataObjectValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedStringDataObjectValues().forEach((key, value) -> map.put(key, value.toJson()));
      json.put("keyedStringDataObjectValues", map);
    }
    if (obj.getKeyedStringValues() != null) {
      JsonObject map = new JsonObject();
      obj.getKeyedStringValues().forEach((key, value) -> map.put(key, value));
      json.put("keyedStringValues", map);
    }
    if (obj.getMethodMapped() != null) {
      json.put("methodMapped", io.vertx.test.codegen.converter.TestDataObject.serializeZonedDateTime(obj.getMethodMapped()));
    }
    if (obj.getMethodMappedList() != null) {
      JsonArray array = new JsonArray();
      obj.getMethodMappedList().forEach(item -> array.add(io.vertx.test.codegen.converter.TestDataObject.serializeZonedDateTime(item)));
      json.put("methodMappedList", array);
    }
    if (obj.getMethodMappedMap() != null) {
      JsonObject map = new JsonObject();
      obj.getMethodMappedMap().forEach((key, value) -> map.put(key, io.vertx.test.codegen.converter.TestDataObject.serializeZonedDateTime(value)));
      json.put("methodMappedMap", map);
    }
    if (obj.getMethodMappedSet() != null) {
      JsonArray array = new JsonArray();
      obj.getMethodMappedSet().forEach(item -> array.add(io.vertx.test.codegen.converter.TestDataObject.serializeZonedDateTime(item)));
      json.put("methodMappedSet", array);
    }
    if (obj.getObjectList() != null) {
      JsonArray array = new JsonArray();
      obj.getObjectList().forEach(item -> array.add(item));
      json.put("objectList", array);
    }
    if (obj.getObjectMap() != null) {
      JsonObject map = new JsonObject();
      obj.getObjectMap().forEach((key, value) -> map.put(key, value));
      json.put("objectMap", map);
    }
    if (obj.getObjectSet() != null) {
      JsonArray array = new JsonArray();
      obj.getObjectSet().forEach(item -> array.add(item));
      json.put("objectSet", array);
    }
    json.put("primitiveBoolean", obj.isPrimitiveBoolean());
    json.put("primitiveByte", obj.getPrimitiveByte());
    json.put("primitiveChar", Character.toString(obj.getPrimitiveChar()));
    json.put("primitiveDouble", obj.getPrimitiveDouble());
    json.put("primitiveFloat", obj.getPrimitiveFloat());
    json.put("primitiveInt", obj.getPrimitiveInt());
    json.put("primitiveLong", obj.getPrimitiveLong());
    json.put("primitiveShort", obj.getPrimitiveShort());
    if (obj.getString() != null) {
      json.put("string", obj.getString());
    }
    if (obj.getStringDataObject() != null) {
      json.put("stringDataObject", obj.getStringDataObject().toJson());
    }
    if (obj.getStringDataObjectList() != null) {
      JsonArray array = new JsonArray();
      obj.getStringDataObjectList().forEach(item -> array.add(item.toJson()));
      json.put("stringDataObjectList", array);
    }
    if (obj.getStringDataObjectMap() != null) {
      JsonObject map = new JsonObject();
      obj.getStringDataObjectMap().forEach((key, value) -> map.put(key, value.toJson()));
      json.put("stringDataObjectMap", map);
    }
    if (obj.getStringDataObjectSet() != null) {
      JsonArray array = new JsonArray();
      obj.getStringDataObjectSet().forEach(item -> array.add(item.toJson()));
      json.put("stringDataObjectSet", array);
    }
    if (obj.getStringList() != null) {
      JsonArray array = new JsonArray();
      obj.getStringList().forEach(item -> array.add(item));
      json.put("stringList", array);
    }
    if (obj.getStringSet() != null) {
      JsonArray array = new JsonArray();
      obj.getStringSet().forEach(item -> array.add(item));
      json.put("stringSet", array);
    }
    if (obj.getStringValueMap() != null) {
      JsonObject map = new JsonObject();
      obj.getStringValueMap().forEach((key, value) -> map.put(key, value));
      json.put("stringValueMap", map);
    }
  }
}
