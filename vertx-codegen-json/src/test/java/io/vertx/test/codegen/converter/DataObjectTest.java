/*
 * Copyright (c) 2011-2017 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.vertx.test.codegen.converter;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.impl.JsonUtil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTest {

  @Test
  public void testJsonToDataObject() throws Exception {

    String key = TestUtils.randomAlphaString(10);
    String stringValue = TestUtils.randomAlphaString(20);
    boolean booleanValue = TestUtils.randomBoolean();
    byte byteValue = TestUtils.randomByte();
    short shortValue = TestUtils.randomShort();
    int intValue = TestUtils.randomInt();
    long longValue = TestUtils.randomLong();
    float floatValue = TestUtils.randomFloat();
    double doubleValue = TestUtils.randomDouble();
    char charValue = TestUtils.randomChar();
    Boolean boxedBooleanValue = TestUtils.randomBoolean();
    Byte boxedByteValue = TestUtils.randomByte();
    Short boxedShortValue = TestUtils.randomShort();
    Integer boxedIntValue = TestUtils.randomInt();
    Long boxedLongValue = TestUtils.randomLong();
    Float boxedFloatValue = TestUtils.randomFloat();
    Double boxedDoubleValue = TestUtils.randomDouble();
    Character boxedCharValue = TestUtils.randomChar();
    NestedJsonObjectDataObject jsonObjectDataObject = new NestedJsonObjectDataObject().setValue(TestUtils.randomAlphaString(20));
    NestedStringDataObject stringDataObject = new NestedStringDataObject().setValue(TestUtils.randomAlphaString(20));
    Buffer buffer = TestUtils.randomBuffer(20);
    JsonObject jsonObject = new JsonObject().put("wibble", TestUtils.randomAlphaString(20));
    JsonArray jsonArray = new JsonArray().add(TestUtils.randomAlphaString(20));
    TimeUnit httpMethod = TimeUnit.values()[TestUtils.randomPositiveInt() % TimeUnit.values().length];
    ZonedDateTime methodMapped = ZonedDateTime.now();
    TestCustomEnum enumMapped = TestCustomEnum.DEV;

    Map<String, Object> map = new HashMap<>();
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomAlphaString(20));
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomBoolean());
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomInt());
    List<Object> list = new ArrayList<>();
    list.add(TestUtils.randomAlphaString(20));
    list.add(TestUtils.randomBoolean());
    list.add(TestUtils.randomInt());

    JsonObject json = new JsonObject();
    json.put("string", stringValue);
    json.put("primitiveBoolean", booleanValue);
    json.put("primitiveByte", byteValue);
    json.put("primitiveShort", shortValue);
    json.put("primitiveInt", intValue);
    json.put("primitiveLong", longValue);
    json.put("primitiveFloat", floatValue);
    json.put("primitiveDouble", doubleValue);
    json.put("primitiveChar", Character.toString(charValue));
    json.put("boxedBoolean", boxedBooleanValue);
    json.put("boxedByte", boxedByteValue);
    json.put("boxedShort", boxedShortValue);
    json.put("boxedInt", boxedIntValue);
    json.put("boxedLong", boxedLongValue);
    json.put("boxedFloat", boxedFloatValue);
    json.put("boxedDouble", boxedDoubleValue);
    json.put("boxedChar", Character.toString(boxedCharValue));
    json.put("jsonObjectDataObject", jsonObjectDataObject.toJson());
    json.put("stringDataObject", stringDataObject.toJson());
    json.put("buffer", toBase64(buffer));
    json.put("jsonObject", jsonObject);
    json.put("jsonArray", jsonArray);
    json.put("httpMethod", httpMethod.toString());
    json.put("methodMapped", methodMapped.toString());
    json.put("enumMapped", enumMapped.getShortName());
    json.put("stringList", new JsonArray().add(stringValue));
    json.put("boxedBooleanList", new JsonArray().add(boxedBooleanValue));
    json.put("boxedByteList", new JsonArray().add(boxedByteValue));
    json.put("boxedShortList", new JsonArray().add(boxedShortValue));
    json.put("boxedIntList", new JsonArray().add(boxedIntValue));
    json.put("boxedLongList", new JsonArray().add(boxedLongValue));
    json.put("boxedFloatList", new JsonArray().add(boxedFloatValue));
    json.put("boxedDoubleList", new JsonArray().add(boxedDoubleValue));
    json.put("boxedCharList", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("jsonObjectDataObjectList", new JsonArray().add(jsonObjectDataObject.toJson()));
    json.put("stringDataObjectList", new JsonArray().add(stringDataObject.toJson()));
    json.put("bufferList", new JsonArray().add(toBase64(buffer)));
    json.put("jsonObjectList", new JsonArray().add(jsonObject));
    json.put("jsonArrayList", new JsonArray().add(jsonArray));
    json.put("httpMethodList", new JsonArray().add(httpMethod.toString()));
    json.put("methodMappedList", new JsonArray().add(methodMapped.toString()));
    json.put("objectList", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("enumMappedList", new JsonArray().add(enumMapped.getShortName()));
    json.put("stringSet", new JsonArray().add(stringValue));
    json.put("boxedBooleanSet", new JsonArray().add(boxedBooleanValue));
    json.put("boxedByteSet", new JsonArray().add(boxedByteValue));
    json.put("boxedShortSet", new JsonArray().add(boxedShortValue));
    json.put("boxedIntSet", new JsonArray().add(boxedIntValue));
    json.put("boxedLongSet", new JsonArray().add(boxedLongValue));
    json.put("boxedFloatSet", new JsonArray().add(boxedFloatValue));
    json.put("boxedDoubleSet", new JsonArray().add(boxedDoubleValue));
    json.put("boxedCharSet", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("jsonObjectDataObjectSet", new JsonArray().add(jsonObjectDataObject.toJson()));
    json.put("stringDataObjectSet", new JsonArray().add(stringDataObject.toJson()));
    json.put("bufferSet", new JsonArray().add(toBase64(buffer)));
    json.put("jsonObjectSet", new JsonArray().add(jsonObject));
    json.put("jsonArraySet", new JsonArray().add(jsonArray));
    json.put("httpMethodSet", new JsonArray().add(httpMethod.toString()));
    json.put("methodMappedSet", new JsonArray().add(methodMapped.toString()));
    json.put("objectSet", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("enumMappedSet", new JsonArray().add(enumMapped.getShortName()));
    json.put("addedStringValues", new JsonArray().add(stringValue));
    json.put("addedBooleanValues", new JsonArray().add(boxedBooleanValue));
    json.put("addedByteValues", new JsonArray().add(boxedByteValue));
    json.put("addedShortValues", new JsonArray().add(boxedShortValue));
    json.put("addedIntValues", new JsonArray().add(boxedIntValue));
    json.put("addedLongValues", new JsonArray().add(boxedLongValue));
    json.put("addedFloatValues", new JsonArray().add(boxedFloatValue));
    json.put("addedDoubleValues", new JsonArray().add(boxedDoubleValue));
    json.put("addedCharValues", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("addedBoxedBooleanValues", new JsonArray().add(boxedBooleanValue));
    json.put("addedBoxedByteValues", new JsonArray().add(boxedByteValue));
    json.put("addedBoxedShortValues", new JsonArray().add(boxedShortValue));
    json.put("addedBoxedIntValues", new JsonArray().add(boxedIntValue));
    json.put("addedBoxedLongValues", new JsonArray().add(boxedLongValue));
    json.put("addedBoxedFloatValues", new JsonArray().add(boxedFloatValue));
    json.put("addedBoxedDoubleValues", new JsonArray().add(boxedDoubleValue));
    json.put("addedBoxedCharValues", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("addedJsonObjectDataObjects", new JsonArray().add(jsonObjectDataObject.toJson()));
    json.put("addedStringDataObjects", new JsonArray().add(stringDataObject.toJson()));
    json.put("addedBuffers", new JsonArray().add(toBase64(buffer)));
    json.put("addedJsonObjects", new JsonArray().add(jsonObject));
    json.put("addedJsonArrays", new JsonArray().add(jsonArray));
    json.put("addedHttpMethods", new JsonArray().add(httpMethod.toString()));
    json.put("addedMethodMappeds", new JsonArray().add(methodMapped.toString()));
    json.put("addedObjects", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("addedEnumMappeds", new JsonArray().add(enumMapped.getShortName()));
    json.put("stringValueMap", new JsonObject().put(key, stringValue));
    json.put("boxedBooleanValueMap", new JsonObject().put(key, boxedBooleanValue));
    json.put("boxedByteValueMap", new JsonObject().put(key, boxedByteValue));
    json.put("boxedShortValueMap", new JsonObject().put(key, boxedShortValue));
    json.put("boxedIntValueMap", new JsonObject().put(key, boxedIntValue));
    json.put("boxedLongValueMap", new JsonObject().put(key, boxedLongValue));
    json.put("boxedFloatValueMap", new JsonObject().put(key, boxedFloatValue));
    json.put("boxedDoubleValueMap", new JsonObject().put(key, boxedDoubleValue));
    json.put("boxedCharValueMap", new JsonObject().put(key, Character.toString(boxedCharValue)));
    json.put("jsonObjectDataObjectMap", new JsonObject().put(key, jsonObjectDataObject.toJson()));
    json.put("stringDataObjectMap", new JsonObject().put(key, stringDataObject.toJson()));
    json.put("bufferMap", new JsonObject().put(key, toBase64(buffer)));
    json.put("jsonObjectMap", new JsonObject().put(key, jsonObject));
    json.put("jsonArrayMap", new JsonObject().put(key, jsonArray));
    json.put("httpMethodMap", new JsonObject().put(key, httpMethod.toString()));
    json.put("methodMappedMap", new JsonObject().put(key, methodMapped.toString()));
    json.put("objectMap", toJson(map));
    json.put("enumMappedMap", new JsonObject().put(key, enumMapped.getShortName()));
    json.put("keyedStringValues", new JsonObject().put(key, stringValue));
    json.put("keyedBoxedBooleanValues", new JsonObject().put(key, boxedBooleanValue));
    json.put("keyedBoxedByteValues", new JsonObject().put(key, boxedByteValue));
    json.put("keyedBoxedShortValues", new JsonObject().put(key, boxedShortValue));
    json.put("keyedBoxedIntValues", new JsonObject().put(key, boxedIntValue));
    json.put("keyedBoxedLongValues", new JsonObject().put(key, boxedLongValue));
    json.put("keyedBoxedFloatValues", new JsonObject().put(key, boxedFloatValue));
    json.put("keyedBoxedDoubleValues", new JsonObject().put(key, boxedDoubleValue));
    json.put("keyedBoxedCharValues", new JsonObject().put(key, Character.toString(boxedCharValue)));
    json.put("keyedJsonObjectDataObjectValues", new JsonObject().put(key, jsonObjectDataObject.toJson()));
    json.put("keyedStringDataObjectValues", new JsonObject().put(key, stringDataObject.toJson()));
    json.put("keyedBufferValues", new JsonObject().put(key, toBase64(buffer)));
    json.put("keyedJsonObjectValues", new JsonObject().put(key, jsonObject));
    json.put("keyedJsonArrayValues", new JsonObject().put(key, jsonArray));
    json.put("keyedEnumValues", new JsonObject().put(key, httpMethod.name()));
    json.put("keyedMethodMappedValues", new JsonObject().put(key, methodMapped.toString()));
    json.put("keyedObjectValues", toJson(map));
    json.put("keyedEnumMappedValues", new JsonObject().put(key, enumMapped.getShortName()));

    TestDataObject obj = new TestDataObject();
    TestDataObjectConverter.fromJson(json, obj);

    Assert.assertEquals(stringValue, obj.getString());
    Assert.assertEquals(booleanValue, obj.isPrimitiveBoolean());
    Assert.assertEquals(byteValue, obj.getPrimitiveByte());
    Assert.assertEquals(shortValue, obj.getPrimitiveShort());
    Assert.assertEquals(intValue, obj.getPrimitiveInt());
    Assert.assertEquals(longValue, obj.getPrimitiveLong());
    Assert.assertEquals(floatValue, obj.getPrimitiveFloat(), 0);
    Assert.assertEquals(doubleValue, obj.getPrimitiveDouble(), 0);
    Assert.assertEquals(charValue, obj.getPrimitiveChar());
    Assert.assertEquals(boxedBooleanValue, obj.isBoxedBoolean());
    Assert.assertEquals(boxedByteValue, obj.getBoxedByte());
    Assert.assertEquals(boxedShortValue, obj.getBoxedShort());
    Assert.assertEquals(boxedIntValue, obj.getBoxedInt());
    Assert.assertEquals(boxedLongValue, obj.getBoxedLong());
    Assert.assertEquals(boxedFloatValue, obj.getBoxedFloat(), 0);
    Assert.assertEquals(boxedDoubleValue, obj.getBoxedDouble(), 0);
    Assert.assertEquals(boxedCharValue, obj.getBoxedChar());
    assertEquals(jsonObjectDataObject, obj.getJsonObjectDataObject());
    assertEquals(stringDataObject, obj.getStringDataObject());
    Assert.assertEquals(buffer, obj.getBuffer());
    Assert.assertEquals(jsonObject, obj.getJsonObject());
    Assert.assertEquals(jsonArray, obj.getJsonArray());
    Assert.assertEquals(httpMethod, obj.getHttpMethod());
    Assert.assertEquals(methodMapped, obj.getMethodMapped());
    Assert.assertEquals(enumMapped, obj.getEnumMapped());
    Assert.assertEquals(Collections.singletonList(stringValue), obj.getStringList());
    Assert.assertEquals(Collections.singletonList(boxedBooleanValue), obj.getBoxedBooleanList());
    Assert.assertEquals(Collections.singletonList(boxedByteValue), obj.getBoxedByteList());
    Assert.assertEquals(Collections.singletonList(boxedShortValue), obj.getBoxedShortList());
    Assert.assertEquals(Collections.singletonList(boxedIntValue), obj.getBoxedIntList());
    Assert.assertEquals(Collections.singletonList(boxedLongValue), obj.getBoxedLongList());
    Assert.assertEquals(Collections.singletonList(boxedFloatValue), obj.getBoxedFloatList());
    Assert.assertEquals(Collections.singletonList(boxedDoubleValue), obj.getBoxedDoubleList());
    Assert.assertEquals(Collections.singletonList(boxedCharValue), obj.getBoxedCharList());
    Assert.assertEquals(Collections.singletonList(jsonObjectDataObject), obj.getJsonObjectDataObjectList());
    Assert.assertEquals(Collections.singletonList(stringDataObject), obj.getStringDataObjectList());
    Assert.assertEquals(Collections.singletonList(buffer), obj.getBufferList());
    Assert.assertEquals(Collections.singletonList(jsonObject), obj.getJsonObjectList());
    Assert.assertEquals(Collections.singletonList(jsonArray), obj.getJsonArrayList());
    Assert.assertEquals(Collections.singletonList(httpMethod), obj.getHttpMethodList());
    Assert.assertEquals(Collections.singletonList(methodMapped), obj.getMethodMappedList());
    Assert.assertEquals(list, obj.getObjectList());
    Assert.assertEquals(Collections.singletonList(enumMapped), obj.getEnumMappedList());
    Assert.assertEquals(Collections.singleton(stringValue), obj.getStringSet());
    Assert.assertEquals(Collections.singleton(boxedBooleanValue), obj.getBoxedBooleanSet());
    Assert.assertEquals(Collections.singleton(boxedByteValue), obj.getBoxedByteSet());
    Assert.assertEquals(Collections.singleton(boxedShortValue), obj.getBoxedShortSet());
    Assert.assertEquals(Collections.singleton(boxedIntValue), obj.getBoxedIntSet());
    Assert.assertEquals(Collections.singleton(boxedLongValue), obj.getBoxedLongSet());
    Assert.assertEquals(Collections.singleton(boxedFloatValue), obj.getBoxedFloatSet());
    Assert.assertEquals(Collections.singleton(boxedDoubleValue), obj.getBoxedDoubleSet());
    Assert.assertEquals(Collections.singleton(boxedCharValue), obj.getBoxedCharSet());
    Assert.assertEquals(Collections.singleton(jsonObjectDataObject), obj.getJsonObjectDataObjectSet());
    Assert.assertEquals(Collections.singleton(stringDataObject), obj.getStringDataObjectSet());
    Assert.assertEquals(Collections.singleton(buffer), obj.getBufferSet());
    Assert.assertEquals(Collections.singleton(jsonObject), obj.getJsonObjectSet());
    Assert.assertEquals(Collections.singleton(jsonArray), obj.getJsonArraySet());
    Assert.assertEquals(Collections.singleton(httpMethod), obj.getHttpMethodSet());
    Assert.assertEquals(Collections.singleton(methodMapped), obj.getMethodMappedSet());
    Assert.assertEquals(new LinkedHashSet<>(list), obj.getObjectSet());
    Assert.assertEquals(Collections.singleton(enumMapped), obj.getEnumMappedSet());
    Assert.assertEquals(Collections.singletonList(stringValue), obj.getAddedStringValues());
    Assert.assertEquals(Collections.singletonList(boxedBooleanValue), obj.getAddedBoxedBooleanValues());
    Assert.assertEquals(Collections.singletonList(boxedByteValue), obj.getAddedBoxedByteValues());
    Assert.assertEquals(Collections.singletonList(boxedShortValue), obj.getAddedBoxedShortValues());
    Assert.assertEquals(Collections.singletonList(boxedIntValue), obj.getAddedBoxedIntValues());
    Assert.assertEquals(Collections.singletonList(boxedLongValue), obj.getAddedBoxedLongValues());
    Assert.assertEquals(Collections.singletonList(boxedFloatValue), obj.getAddedBoxedFloatValues());
    Assert.assertEquals(Collections.singletonList(boxedDoubleValue), obj.getAddedBoxedDoubleValues());
    Assert.assertEquals(Collections.singletonList(boxedCharValue), obj.getAddedBoxedCharValues());
    Assert.assertEquals(Collections.singletonList(jsonObjectDataObject), obj.getAddedJsonObjectDataObjects());
    Assert.assertEquals(Collections.singletonList(stringDataObject), obj.getAddedStringDataObjects());
    Assert.assertEquals(Collections.singletonList(buffer), obj.getAddedBuffers());
    Assert.assertEquals(Collections.singletonList(jsonObject), obj.getAddedJsonObjects());
    Assert.assertEquals(Collections.singletonList(jsonArray), obj.getAddedJsonArrays());
    Assert.assertEquals(Collections.singletonList(httpMethod), obj.getAddedHttpMethods());
    Assert.assertEquals(Collections.singletonList(methodMapped), obj.getAddedMethodMappeds());
    Assert.assertEquals(list, obj.getAddedObjects());
    Assert.assertEquals(Collections.singletonList(enumMapped), obj.getAddedEnumMappeds());
    Assert.assertEquals(Collections.singletonMap(key, stringValue), obj.getStringValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedBooleanValue), obj.getBoxedBooleanValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedByteValue), obj.getBoxedByteValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedShortValue), obj.getBoxedShortValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedIntValue), obj.getBoxedIntValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedLongValue), obj.getBoxedLongValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedFloatValue), obj.getBoxedFloatValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedDoubleValue), obj.getBoxedDoubleValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedCharValue), obj.getBoxedCharValueMap());
    Assert.assertEquals(Collections.singletonMap(key, jsonObjectDataObject), obj.getJsonObjectDataObjectMap());
    Assert.assertEquals(Collections.singletonMap(key, stringDataObject), obj.getStringDataObjectMap());
    Assert.assertEquals(Collections.singletonMap(key, buffer), obj.getBufferMap());
    Assert.assertEquals(Collections.singletonMap(key, jsonObject), obj.getJsonObjectMap());
    Assert.assertEquals(Collections.singletonMap(key, jsonArray), obj.getJsonArrayMap());
    Assert.assertEquals(Collections.singletonMap(key, httpMethod), obj.getHttpMethodMap());
    Assert.assertEquals(Collections.singletonMap(key, methodMapped), obj.getMethodMappedMap());
    Assert.assertEquals(map, obj.getObjectMap());
    Assert.assertEquals(Collections.singletonMap(key, enumMapped), obj.getEnumMappedMap());
    Assert.assertEquals(Collections.singletonMap(key, stringValue), obj.getKeyedStringValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedBooleanValue), obj.getKeyedBoxedBooleanValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedByteValue), obj.getKeyedBoxedByteValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedShortValue), obj.getKeyedBoxedShortValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedIntValue), obj.getKeyedBoxedIntValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedLongValue), obj.getKeyedBoxedLongValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedFloatValue), obj.getKeyedBoxedFloatValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedDoubleValue), obj.getKeyedBoxedDoubleValues());
    Assert.assertEquals(Collections.singletonMap(key, boxedCharValue), obj.getKeyedBoxedCharValues());
    Assert.assertEquals(Collections.singletonMap(key, jsonObjectDataObject), obj.getKeyedJsonObjectDataObjectValues());
    Assert.assertEquals(Collections.singletonMap(key, stringDataObject), obj.getKeyedStringDataObjectValues());
    Assert.assertEquals(Collections.singletonMap(key, buffer), obj.getKeyedBufferValues());
    Assert.assertEquals(Collections.singletonMap(key, jsonObject), obj.getKeyedJsonObjectValues());
    Assert.assertEquals(Collections.singletonMap(key, jsonArray), obj.getKeyedJsonArrayValues());
    Assert.assertEquals(Collections.singletonMap(key, httpMethod), obj.getKeyedEnumValues());
    Assert.assertEquals(Collections.singletonMap(key, methodMapped), obj.getKeyedMethodMappedValues());
    Assert.assertEquals(map, obj.getObjectMap());
    Assert.assertEquals(Collections.singletonMap(key, enumMapped), obj.getKeyedEnumMappedValues());

    // Sometimes json can use java collections so test it runs fine in this case
//    json = new JsonObject();
//    json.put("aggregatedDataObject", new JsonObject().put("value", aggregatedDataObject.getValue()).getMap());
//    json.put("aggregatedDataObjects", new JsonArray().add(new JsonObject().put("value", aggregatedDataObject.getValue()).getMap()));
//    json.put("addedAggregatedDataObjects", new JsonArray().add(new JsonObject().put("value", aggregatedDataObject.getValue()).getMap()));
//    obj = new TestDataObject();
//    TestDataObjectConverter.fromJson(json, obj);
//    assertEquals(aggregatedDataObject, obj.getAggregatedDataObject());
//    assertEquals(Collections.singletonList(aggregatedDataObject), obj.getAggregatedDataObjects());
//    assertEquals(Collections.singletonList(aggregatedDataObject), obj.getAddedAggregatedDataObjects());
  }

  private String toBase64(Buffer buffer) {
    return JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes());
  }

  private static JsonObject toJson(Map<String, Object> map) {
    JsonObject json = new JsonObject();
    map.forEach(json::put);
    return json;
  }

  @Test
  public void testEmptyJsonToDataObject() {

    JsonObject json = new JsonObject();

    TestDataObject obj = new TestDataObject();
    TestDataObjectConverter.fromJson(json, obj);

    Assert.assertEquals(null, obj.getString());
    Assert.assertEquals(false, obj.isPrimitiveBoolean());
    Assert.assertEquals(0, obj.getPrimitiveByte());
    Assert.assertEquals(0, obj.getPrimitiveShort());
    Assert.assertEquals(0, obj.getPrimitiveInt());
    Assert.assertEquals(0l, obj.getPrimitiveLong());
    Assert.assertEquals(0f, obj.getPrimitiveFloat(), 0);
    Assert.assertEquals(0d, obj.getPrimitiveDouble(), 0);
    Assert.assertEquals((char)0, obj.getPrimitiveChar());
    Assert.assertEquals(null, obj.isBoxedBoolean());
    Assert.assertEquals(null, obj.getBoxedByte());
    Assert.assertEquals(null, obj.getBoxedShort());
    Assert.assertEquals(null, obj.getBoxedInt());
    Assert.assertEquals(null, obj.getBoxedLong());
    Assert.assertEquals(null, obj.getBoxedFloat());
    Assert.assertEquals(null, obj.getBoxedDouble());
    Assert.assertEquals(null, obj.getBoxedChar());
    assertEquals(null, obj.getJsonObjectDataObject());
    assertEquals(null, obj.getStringDataObject());
    Assert.assertEquals(null, obj.getBuffer());
    Assert.assertEquals(null, obj.getJsonObject());
    Assert.assertEquals(null, obj.getJsonArray());
    Assert.assertEquals(null, obj.getMethodMapped());
    Assert.assertEquals(null, obj.getStringList());
    Assert.assertEquals(null, obj.getEnumMapped());
    Assert.assertEquals(null, obj.getBoxedBooleanList());
    Assert.assertEquals(null, obj.getBoxedByteList());
    Assert.assertEquals(null, obj.getBoxedShortList());
    Assert.assertEquals(null, obj.getBoxedIntList());
    Assert.assertEquals(null, obj.getBoxedLongList());
    Assert.assertEquals(null, obj.getBoxedFloatList());
    Assert.assertEquals(null, obj.getBoxedDoubleList());
    Assert.assertEquals(null, obj.getBoxedCharList());
    Assert.assertEquals(null, obj.getJsonObjectDataObjectList());
    Assert.assertEquals(null, obj.getStringDataObjectList());
    Assert.assertEquals(null, obj.getBufferList());
    Assert.assertEquals(null, obj.getJsonObjectList());
    Assert.assertEquals(null, obj.getJsonArrayList());
    Assert.assertEquals(null, obj.getHttpMethodList());
    Assert.assertEquals(null, obj.getMethodMappedList());
    Assert.assertEquals(null, obj.getObjectList());
    Assert.assertEquals(null, obj.getEnumMappedList());
    Assert.assertEquals(null, obj.getStringSet());
    Assert.assertEquals(null, obj.getBoxedBooleanSet());
    Assert.assertEquals(null, obj.getBoxedByteSet());
    Assert.assertEquals(null, obj.getBoxedShortSet());
    Assert.assertEquals(null, obj.getBoxedIntSet());
    Assert.assertEquals(null, obj.getBoxedLongSet());
    Assert.assertEquals(null, obj.getBoxedFloatSet());
    Assert.assertEquals(null, obj.getBoxedDoubleSet());
    Assert.assertEquals(null, obj.getBoxedCharSet());
    Assert.assertEquals(null, obj.getJsonObjectDataObjectSet());
    Assert.assertEquals(null, obj.getStringDataObjectSet());
    Assert.assertEquals(null, obj.getBufferSet());
    Assert.assertEquals(null, obj.getJsonObjectSet());
    Assert.assertEquals(null, obj.getJsonArraySet());
    Assert.assertEquals(null, obj.getHttpMethodSet());
    Assert.assertEquals(null, obj.getMethodMappedSet());
    Assert.assertEquals(null, obj.getObjectSet());
    Assert.assertEquals(null, obj.getEnumMappedSet());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedStringValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedBooleanValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedByteValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedShortValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedIntValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedLongValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedFloatValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedDoubleValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedCharValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedJsonObjectDataObjects());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedStringDataObjects());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBuffers());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedJsonObjects());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedJsonArrays());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedHttpMethods());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedMethodMappeds());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedObjects());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedEnumMappeds());
    Assert.assertEquals(null, obj.getStringValueMap());
    Assert.assertEquals(null, obj.getBoxedBooleanValueMap());
    Assert.assertEquals(null, obj.getBoxedByteValueMap());
    Assert.assertEquals(null, obj.getBoxedShortValueMap());
    Assert.assertEquals(null, obj.getBoxedIntValueMap());
    Assert.assertEquals(null, obj.getBoxedLongValueMap());
    Assert.assertEquals(null, obj.getBoxedFloatValueMap());
    Assert.assertEquals(null, obj.getBoxedDoubleValueMap());
    Assert.assertEquals(null, obj.getBoxedCharValueMap());
    Assert.assertEquals(null, obj.getJsonObjectDataObjectMap());
    Assert.assertEquals(null, obj.getStringDataObjectMap());
    Assert.assertEquals(null, obj.getBufferMap());
    Assert.assertEquals(null, obj.getJsonObjectMap());
    Assert.assertEquals(null, obj.getJsonArrayMap());
    Assert.assertEquals(null, obj.getHttpMethodMap());
    Assert.assertEquals(null, obj.getMethodMappedMap());
    Assert.assertEquals(null, obj.getObjectMap());
    Assert.assertEquals(null, obj.getEnumMappedMap());
  }

  @Test
  public void testDataObjectToJson() throws Exception {
    String key = TestUtils.randomAlphaString(10);
    String stringValue = TestUtils.randomAlphaString(20);
    boolean booleanValue = TestUtils.randomBoolean();
    byte byteValue = TestUtils.randomByte();
    short shortValue = TestUtils.randomShort();
    int intValue = TestUtils.randomInt();
    long longValue = TestUtils.randomLong();
    float floatValue = TestUtils.randomFloat();
    double doubleValue = TestUtils.randomDouble();
    char charValue = TestUtils.randomChar();
    Boolean boxedBooleanValue = TestUtils.randomBoolean();
    Byte boxedByteValue = TestUtils.randomByte();
    Short boxedShortValue = TestUtils.randomShort();
    Integer boxedIntValue = TestUtils.randomInt();
    Long boxedLongValue = TestUtils.randomLong();
    Float boxedFloatValue = TestUtils.randomFloat();
    Double boxedDoubleValue = TestUtils.randomDouble();
    Character boxedCharValue = TestUtils.randomChar();
    NestedJsonObjectDataObject jsonObjectDataObject = new NestedJsonObjectDataObject().setValue(TestUtils.randomAlphaString(20));
    NestedStringDataObject stringDataObject = new NestedStringDataObject().setValue(TestUtils.randomAlphaString(20));
    Buffer buffer = TestUtils.randomBuffer(20);
    JsonObject jsonObject = new JsonObject().put("wibble", TestUtils.randomAlphaString(20));
    JsonArray jsonArray = new JsonArray().add(TestUtils.randomAlphaString(20));
    TimeUnit httpMethod = TimeUnit.values()[TestUtils.randomPositiveInt() % TimeUnit.values().length];
    ZonedDateTime dateTime = ZonedDateTime.now();
    Locale uri = new Locale("en");
    TestCustomEnum testCustomEnum = TestCustomEnum.DEV;

    Map<String, Object> map = new HashMap<>();
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomAlphaString(20));
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomBoolean());
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomInt());
    List<Object> list = new ArrayList<>();
    list.add(TestUtils.randomAlphaString(20));
    list.add(TestUtils.randomBoolean());
    list.add(TestUtils.randomInt());

    TestDataObject obj = new TestDataObject();
    obj.setString(stringValue);
    obj.setPrimitiveBoolean(booleanValue);
    obj.setPrimitiveByte(byteValue);
    obj.setPrimitiveShort(shortValue);
    obj.setPrimitiveInt(intValue);
    obj.setPrimitiveLong(longValue);
    obj.setPrimitiveFloat(floatValue);
    obj.setPrimitiveDouble(doubleValue);
    obj.setPrimitiveChar(charValue);
    obj.setBoxedBoolean(boxedBooleanValue);
    obj.setBoxedByte(boxedByteValue);
    obj.setBoxedShort(boxedShortValue);
    obj.setBoxedInt(boxedIntValue);
    obj.setBoxedLong(boxedLongValue);
    obj.setBoxedFloat(boxedFloatValue);
    obj.setBoxedDouble(boxedDoubleValue);
    obj.setBoxedChar(boxedCharValue);
    obj.setJsonObjectDataObject(jsonObjectDataObject);
    obj.setStringDataObject(stringDataObject);
    obj.setBuffer(buffer);
    obj.setJsonObject(jsonObject);
    obj.setJsonArray(jsonArray);
    obj.setHttpMethod(httpMethod);
    obj.setMethodMapped(dateTime);
    obj.setEnumMapped(testCustomEnum);
    obj.setStringList(Collections.singletonList(stringValue));
    obj.setBoxedBooleanList(Collections.singletonList(boxedBooleanValue));
    obj.setBoxedByteList(Collections.singletonList(boxedByteValue));
    obj.setBoxedShortList(Collections.singletonList(boxedShortValue));
    obj.setBoxedIntList(Collections.singletonList(boxedIntValue));
    obj.setBoxedLongList(Collections.singletonList(boxedLongValue));
    obj.setBoxedFloatList(Collections.singletonList(boxedFloatValue));
    obj.setBoxedDoubleList(Collections.singletonList(boxedDoubleValue));
    obj.setBoxedCharList(Collections.singletonList(boxedCharValue));
    obj.setJsonObjectDataObjectList(Collections.singletonList(jsonObjectDataObject));
    obj.setStringDataObjectList(Collections.singletonList(stringDataObject));
    obj.setBufferList(Collections.singletonList(buffer));
    obj.setJsonObjectList(Collections.singletonList(jsonObject));
    obj.setJsonArrayList(Collections.singletonList(jsonArray));
    obj.setHttpMethodList(Collections.singletonList(httpMethod));
    obj.setMethodMappedList(Collections.singletonList(dateTime));
    obj.setObjectList(list);
    obj.setEnumMappedList(Collections.singletonList(testCustomEnum));
    obj.setStringValueMap(Collections.singletonMap(key, stringValue));
    obj.setStringSet(Collections.singleton(stringValue));
    obj.setBoxedBooleanSet(Collections.singleton(boxedBooleanValue));
    obj.setBoxedByteSet(Collections.singleton(boxedByteValue));
    obj.setBoxedShortSet(Collections.singleton(boxedShortValue));
    obj.setBoxedIntSet(Collections.singleton(boxedIntValue));
    obj.setBoxedLongSet(Collections.singleton(boxedLongValue));
    obj.setBoxedFloatSet(Collections.singleton(boxedFloatValue));
    obj.setBoxedDoubleSet(Collections.singleton(boxedDoubleValue));
    obj.setBoxedCharSet(Collections.singleton(boxedCharValue));
    obj.setJsonObjectDataObjectSet(Collections.singleton(jsonObjectDataObject));
    obj.setStringDataObjectSet(Collections.singleton(stringDataObject));
    obj.setBufferSet(Collections.singleton(buffer));
    obj.setJsonObjectSet(Collections.singleton(jsonObject));
    obj.setJsonArraySet(Collections.singleton(jsonArray));
    obj.setHttpMethodSet(Collections.singleton(httpMethod));
    obj.setMethodMappedSet(Collections.singleton(dateTime));
    obj.setObjectSet(new LinkedHashSet<>(list));
    obj.setEnumMappedSet(Collections.singleton(testCustomEnum));
    obj.setBoxedBooleanValueMap(Collections.singletonMap(key, boxedBooleanValue));
    obj.setBoxedByteValueMap(Collections.singletonMap(key, boxedByteValue));
    obj.setBoxedShortValueMap(Collections.singletonMap(key, boxedShortValue));
    obj.setBoxedIntValueMap(Collections.singletonMap(key, boxedIntValue));
    obj.setBoxedLongValueMap(Collections.singletonMap(key, boxedLongValue));
    obj.setBoxedFloatValueMap(Collections.singletonMap(key, boxedFloatValue));
    obj.setBoxedDoubleValueMap(Collections.singletonMap(key, boxedDoubleValue));
    obj.setBoxedCharValueMap(Collections.singletonMap(key, boxedCharValue));
    obj.setJsonObjectDataObjectMap(Collections.singletonMap(key, jsonObjectDataObject));
    obj.setStringDataObjectMap(Collections.singletonMap(key, stringDataObject));
    obj.setBufferMap(Collections.singletonMap(key, buffer));
    obj.setJsonObjectMap(Collections.singletonMap(key, jsonObject));
    obj.setJsonArrayMap(Collections.singletonMap(key, jsonArray));
    obj.setHttpMethodMap(Collections.singletonMap(key, httpMethod));
    obj.setMethodMappedMap(Collections.singletonMap(key, dateTime));
    obj.setObjectMap(map);
    obj.setEnumMappedMap(Collections.singletonMap(key, testCustomEnum));
    obj.addKeyedStringValue(key, stringValue);
    obj.addKeyedBoxedBooleanValue(key, boxedBooleanValue);
    obj.addKeyedBoxedByteValue(key, boxedByteValue);
    obj.addKeyedBoxedShortValue(key, boxedShortValue);
    obj.addKeyedBoxedIntValue(key, boxedIntValue);
    obj.addKeyedBoxedLongValue(key, boxedLongValue);
    obj.addKeyedBoxedFloatValue(key, boxedFloatValue);
    obj.addKeyedBoxedDoubleValue(key, boxedDoubleValue);
    obj.addKeyedBoxedCharValue(key, boxedCharValue);
    obj.addKeyedJsonObjectDataObjectValue(key, jsonObjectDataObject);
    obj.addKeyedStringDataObjectValue(key, stringDataObject);
    obj.addKeyedBufferValue(key, buffer);
    obj.addKeyedJsonObjectValue(key, jsonObject);
    obj.addKeyedJsonArrayValue(key, jsonArray);
    obj.addKeyedEnumValue(key, httpMethod);
    obj.addKeyedMethodMappedValue(key, dateTime);
    map.forEach(obj::addKeyedObjectValue);
    obj.addKeyedEnumMappedValue(key, testCustomEnum);

    Map<String, Object> json = new HashMap<>();
    TestDataObjectConverter.toJson(obj, json);

    assertEquals(stringValue, json.get("string"));
    assertEquals(booleanValue, json.get("primitiveBoolean"));
    assertEquals(byteValue, (byte)json.get("primitiveByte"));
    assertEquals(shortValue, (short)json.get("primitiveShort"));
    assertEquals(intValue, (int)json.get("primitiveInt"));
    assertEquals(longValue, (long)json.get("primitiveLong"));
    assertEquals(floatValue, (float)json.get("primitiveFloat"), 0.001);
    assertEquals(doubleValue, (double)json.get("primitiveDouble"), 0.001);
    assertEquals(Character.toString(charValue), json.get("primitiveChar"));
    assertEquals(boxedBooleanValue, json.get("boxedBoolean"));
    assertEquals((byte)boxedByteValue, (byte)json.get("boxedByte"));
    assertEquals((short)boxedShortValue, (short)json.get("boxedShort"));
    assertEquals(boxedIntValue, json.get("boxedInt"));
    assertEquals(boxedLongValue, json.get("boxedLong"));
    assertEquals(boxedFloatValue, (float)json.get("boxedFloat"), 0.001);
    assertEquals(boxedDoubleValue, (double) json.get("boxedDouble"), 0.001);
    assertEquals(Character.toString(boxedCharValue), json.get("boxedChar"));
    assertEquals(jsonObjectDataObject.toJson(), json.get("jsonObjectDataObject"));
    assertEquals(stringDataObject.toJson(), json.get("stringDataObject"));
    assertEquals(buffer, Buffer.buffer(JsonUtil.BASE64_DECODER.decode((String)json.get("buffer"))));
    assertEquals(jsonObject, json.get("jsonObject"));
    assertEquals(jsonArray, json.get("jsonArray"));
    assertEquals(httpMethod.name(), json.get("httpMethod"));
    assertEquals(dateTime.toString(), json.get("methodMapped"));
    assertEquals(testCustomEnum.getShortName(), json.get("enumMapped"));
    assertEquals(new JsonArray().add(stringValue), json.get("stringList"));
    assertEquals(new JsonArray().add(boxedBooleanValue), json.get("boxedBooleanList"));
    assertEquals(new JsonArray().add(boxedByteValue), json.get("boxedByteList"));
    assertEquals(new JsonArray().add(boxedShortValue), json.get("boxedShortList"));
    assertEquals(new JsonArray().add(boxedIntValue), json.get("boxedIntList"));
    assertEquals(new JsonArray().add(boxedLongValue), json.get("boxedLongList"));
    assertEquals(1, ((JsonArray)json.get("boxedFloatList")).size());
    assertEquals(boxedFloatValue, (float)((JsonArray)json.get("boxedFloatList")).getValue(0), 0.001);
    assertEquals(1, ((JsonArray)json.get("boxedDoubleList")).size());
    assertEquals(boxedDoubleValue, (double)((JsonArray)json.get("boxedDoubleList")).getValue(0), 0.001);
    assertEquals(new JsonArray().add(Character.toString(boxedCharValue)), json.get("boxedCharList"));
    assertEquals(new JsonArray().add(jsonObjectDataObject.toJson()), json.get("jsonObjectDataObjectList"));
    assertEquals(new JsonArray().add(stringDataObject.toJson()), json.get("stringDataObjectList"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonArray)json.get("bufferList")).getValue(0));
    assertEquals(new JsonArray().add(jsonObject), json.get("jsonObjectList"));
    assertEquals(new JsonArray().add(jsonArray), json.get("jsonArrayList"));
    assertEquals(new JsonArray().add(httpMethod.name()), json.get("httpMethodList"));
    assertEquals(new JsonArray().add(dateTime.toString()), json.get("methodMappedList"));
    assertEquals(new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)), json.get("objectList"));
    assertEquals(new JsonArray().add(testCustomEnum.getShortName()), json.get("enumMappedList"));
    assertEquals(new JsonArray().add(stringValue), json.get("stringSet"));
    assertEquals(new JsonArray().add(boxedBooleanValue), json.get("boxedBooleanSet"));
    assertEquals(new JsonArray().add(boxedByteValue), json.get("boxedByteSet"));
    assertEquals(new JsonArray().add(boxedShortValue), json.get("boxedShortSet"));
    assertEquals(new JsonArray().add(boxedIntValue), json.get("boxedIntSet"));
    assertEquals(new JsonArray().add(boxedLongValue), json.get("boxedLongSet"));
    assertEquals(1, ((JsonArray)json.get("boxedFloatSet")).size());
    assertEquals(boxedFloatValue, (float)((JsonArray)json.get("boxedFloatSet")).getValue(0), 0.001);
    assertEquals(1, ((JsonArray)json.get("boxedDoubleSet")).size());
    assertEquals(boxedDoubleValue, (double)((JsonArray)json.get("boxedDoubleSet")).getValue(0), 0.001);
    assertEquals(new JsonArray().add(Character.toString(boxedCharValue)), json.get("boxedCharSet"));
    assertEquals(new JsonArray().add(jsonObjectDataObject.toJson()), json.get("jsonObjectDataObjectSet"));
    assertEquals(new JsonArray().add(stringDataObject.toJson()), json.get("stringDataObjectSet"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonArray)json.get("bufferSet")).getValue(0));
    assertEquals(new JsonArray().add(jsonObject), json.get("jsonObjectSet"));
    assertEquals(new JsonArray().add(jsonArray), json.get("jsonArraySet"));
    assertEquals(new JsonArray().add(httpMethod.name()), json.get("httpMethodSet"));
    assertEquals(new JsonArray().add(dateTime.toString()), json.get("methodMappedSet"));
    assertEquals(new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)), json.get("objectSet"));
    assertEquals(new JsonArray().add(testCustomEnum.getShortName()), json.get("enumMappedSet"));
    assertEquals(new JsonObject().put(key, stringValue), json.get("stringValueMap"));
    assertEquals(new JsonObject().put(key, boxedBooleanValue), json.get("boxedBooleanValueMap"));
    assertEquals(new JsonObject().put(key, boxedByteValue), json.get("boxedByteValueMap"));
    assertEquals(new JsonObject().put(key, boxedShortValue), json.get("boxedShortValueMap"));
    assertEquals(new JsonObject().put(key, boxedIntValue), json.get("boxedIntValueMap"));
    assertEquals(new JsonObject().put(key, boxedLongValue), json.get("boxedLongValueMap"));
    assertEquals(1, ((JsonObject)json.get("boxedFloatValueMap")).size());
    assertEquals(boxedFloatValue, (float)((JsonObject)json.get("boxedFloatValueMap")).getValue(key), 0.001);
    assertEquals(1, ((JsonObject)json.get("boxedDoubleValueMap")).size());
    assertEquals(boxedDoubleValue, (double)((JsonObject)json.get("boxedDoubleValueMap")).getValue(key), 0.001);
    assertEquals(new JsonObject().put(key, Character.toString(boxedCharValue)), json.get("boxedCharValueMap"));
    assertEquals(new JsonObject().put(key, jsonObjectDataObject.toJson()), json.get("jsonObjectDataObjectMap"));
    assertEquals(new JsonObject().put(key, stringDataObject.toJson()), json.get("stringDataObjectMap"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonObject)json.get("bufferMap")).getValue(key));
    assertEquals(new JsonObject().put(key, jsonObject), json.get("jsonObjectMap"));
    assertEquals(new JsonObject().put(key, jsonArray), json.get("jsonArrayMap"));
    assertEquals(new JsonObject().put(key, httpMethod.name()), json.get("httpMethodMap"));
    assertEquals(new JsonObject().put(key, dateTime.toString()), json.get("methodMappedMap"));
    assertEquals(toJson(map), json.get("objectMap"));
    assertEquals(new JsonObject().put(key, testCustomEnum.getShortName()), json.get("enumMappedMap"));
    assertEquals(new JsonObject().put(key, stringValue), json.get("keyedStringValues"));
    assertEquals(new JsonObject().put(key, boxedBooleanValue), json.get("keyedBoxedBooleanValues"));
    assertEquals(new JsonObject().put(key, boxedByteValue), json.get("keyedBoxedByteValues"));
    assertEquals(new JsonObject().put(key, boxedShortValue), json.get("keyedBoxedShortValues"));
    assertEquals(new JsonObject().put(key, boxedIntValue), json.get("keyedBoxedIntValues"));
    assertEquals(new JsonObject().put(key, boxedLongValue), json.get("keyedBoxedLongValues"));
    assertEquals(1, ((JsonObject)json.get("keyedBoxedFloatValues")).size());
    assertEquals(boxedFloatValue, (float)((JsonObject)json.get("keyedBoxedFloatValues")).getValue(key), 0.001);
    assertEquals(1, ((JsonObject)json.get("keyedBoxedDoubleValues")).size());
    assertEquals(boxedDoubleValue, (double)((JsonObject)json.get("keyedBoxedDoubleValues")).getValue(key), 0.001);
    assertEquals(new JsonObject().put(key, Character.toString(boxedCharValue)), json.get("keyedBoxedCharValues"));
    assertEquals(new JsonObject().put(key, jsonObjectDataObject.toJson()), json.get("keyedJsonObjectDataObjectValues"));
    assertEquals(new JsonObject().put(key, stringDataObject.toJson()), json.get("keyedStringDataObjectValues"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonObject)json.get("keyedBufferValues")).getValue(key));
    assertEquals(new JsonObject().put(key, jsonObject), json.get("keyedJsonObjectValues"));
    assertEquals(new JsonObject().put(key, jsonArray), json.get("keyedJsonArrayValues"));
    assertEquals(new JsonObject().put(key, httpMethod.name()), json.get("keyedEnumValues"));
    assertEquals(new JsonObject().put(key, dateTime.toString()), json.get("keyedMethodMappedValues"));
    assertEquals(toJson(map), json.get("keyedObjectValues"));
    assertEquals(new JsonObject().put(key, testCustomEnum.getShortName()), json.get("keyedEnumMappedValues"));
  }

  @Test
  public void testEmptyDataObjectToJson() {

    TestDataObject obj = new TestDataObject();

    Map<String, Object> json = new HashMap<>();
    TestDataObjectConverter.toJson(obj, json);

    assertEquals(null, json.get("string"));
    assertEquals(false, json.get("primitiveBoolean"));
    assertEquals(0, (byte)json.get("primitiveByte"));
    assertEquals(0, (short)json.get("primitiveShort"));
    assertEquals(0, (int)json.get("primitiveInt"));
    assertEquals(0L, (long) json.get("primitiveLong"));
    assertEquals(0f, (float) json.get("primitiveFloat"), 0);
    assertEquals(0d, (double) json.get("primitiveDouble"), 0);
    assertEquals(Character.toString((char)0), json.get("primitiveChar"));
    assertEquals(null, json.get("boxedBoolean"));
    assertEquals(null, json.get("boxedByte"));
    assertEquals(null, json.get("boxedShort"));
    assertEquals(null, json.get("boxedInt"));
    assertEquals(null, json.get("boxedLong"));
    assertEquals(null, json.get("boxedFloat"));
    assertEquals(null, json.get("boxedDouble"));
    assertEquals(null, json.get("boxedChar"));
    assertEquals(null, json.get("jsonObjectDataObject"));
    assertEquals(null, json.get("stringDataObject"));
    assertEquals(null, json.get("buffer"));
    assertEquals(null, json.get("jsonObject"));
    assertEquals(null, json.get("jsonArray"));
    assertEquals(null, json.get("httpMethod"));
    assertEquals(null, json.get("methodMapped"));
    assertEquals(null, json.get("enumMapped"));
    assertEquals(null, json.get("stringList"));
    assertEquals(null, json.get("boxedBooleanList"));
    assertEquals(null, json.get("boxedByteList"));
    assertEquals(null, json.get("boxedShortList"));
    assertEquals(null, json.get("boxedIntList"));
    assertEquals(null, json.get("boxedLongList"));
    assertEquals(null, json.get("boxedFloatList"));
    assertEquals(null, json.get("boxedDoubleList"));
    assertEquals(null, json.get("boxedCharList"));
    assertEquals(null, json.get("jsonObjectDataObjectList"));
    assertEquals(null, json.get("stringDataObjectList"));
    assertEquals(null, json.get("bufferList"));
    assertEquals(null, json.get("jsonObjectList"));
    assertEquals(null, json.get("jsonArrayList"));
    assertEquals(null, json.get("httpMethodList"));
    assertEquals(null, json.get("methodMappedList"));
    assertEquals(null, json.get("objectList"));
    assertEquals(null, json.get("enumMappedList"));
    assertEquals(null, json.get("stringSet"));
    assertEquals(null, json.get("boxedBooleanSet"));
    assertEquals(null, json.get("boxedByteSet"));
    assertEquals(null, json.get("boxedShortSet"));
    assertEquals(null, json.get("boxedIntSet"));
    assertEquals(null, json.get("boxedLongSet"));
    assertEquals(null, json.get("boxedFloatSet"));
    assertEquals(null, json.get("boxedDoubleSet"));
    assertEquals(null, json.get("boxedCharSet"));
    assertEquals(null, json.get("jsonObjectDataObjectSet"));
    assertEquals(null, json.get("stringDataObjectSet"));
    assertEquals(null, json.get("bufferSet"));
    assertEquals(null, json.get("jsonObjectSet"));
    assertEquals(null, json.get("jsonArraySet"));
    assertEquals(null, json.get("httpMethodSet"));
    assertEquals(null, json.get("methodMappedSet"));
    assertEquals(null, json.get("objectSet"));
    assertEquals(null, json.get("enumMappedSet"));
    assertEquals(new JsonArray(), json.get("addedStringValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedBooleanValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedByteValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedShortValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedIntValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedLongValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedFloatValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedDoubleValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedCharValues"));
    assertEquals(new JsonArray(), json.get("addedStringDataObjects"));
    assertEquals(new JsonArray(), json.get("addedBuffers"));
    assertEquals(new JsonArray(), json.get("addedJsonObjects"));
    assertEquals(new JsonArray(), json.get("addedJsonArrays"));
    assertEquals(new JsonArray(), json.get("addedHttpMethods"));
    assertEquals(new JsonArray(), json.get("addedMethodMappeds"));
    assertEquals(new JsonArray(), json.get("addedObjects"));
    assertEquals(new JsonArray(), json.get("addedEnumMappeds"));
    assertEquals(null, json.get("stringValueMap"));
    assertEquals(null, json.get("boxedBooleanValueMap"));
    assertEquals(null, json.get("boxedByteValueMap"));
    assertEquals(null, json.get("boxedShortValueMap"));
    assertEquals(null, json.get("boxedIntValueMap"));
    assertEquals(null, json.get("boxedLongValueMap"));
    assertEquals(null, json.get("boxedFloatValueMap"));
    assertEquals(null, json.get("boxedDoubleValueMap"));
    assertEquals(null, json.get("boxedCharValueMap"));
    assertEquals(null, json.get("stringDataObjectMap"));
    assertEquals(null, json.get("bufferMap"));
    assertEquals(null, json.get("jsonObjectMap"));
    assertEquals(null, json.get("jsonArrayMap"));
    assertEquals(null, json.get("httpMethodMap"));
    assertEquals(null, json.get("methodMappedMap"));
    assertEquals(null, json.get("objectMap"));
    assertEquals(null, json.get("enumMappedMap"));
  }

  @Test
  public void testInherit() {
    ChildInheritingDataObject obj = new ChildInheritingDataObject();
    JsonObject expectedJson = new JsonObject();
    expectedJson.put("childProperty", "childProperty_value");
    expectedJson.put("parentProperty", "parentProperty_value");
    ChildInheritingDataObjectConverter.fromJson(expectedJson, obj);
    assertEquals("childProperty_value", obj.getChildProperty());
    assertEquals("parentProperty_value", obj.getParentProperty());
    JsonObject json = new JsonObject();
    ChildInheritingDataObjectConverter.toJson(obj, json);
    assertEquals(expectedJson, json);
  }

  @Test
  public void testNotInherit() {
    ChildNotInheritingDataObject obj = new ChildNotInheritingDataObject();
    JsonObject expectedJson = new JsonObject();
    expectedJson.put("childProperty", "childProperty_value");
    expectedJson.put("parentProperty", "parentProperty_value");
    ChildNotInheritingDataObjectConverter.fromJson(expectedJson, obj);
    assertEquals("childProperty_value", obj.getChildProperty());
    assertEquals(null, obj.getParentProperty());
    JsonObject json = new JsonObject();
    ChildNotInheritingDataObjectConverter.toJson(obj, json);
    expectedJson.remove("parentProperty");
    assertEquals(expectedJson, json);
  }

  @Test
  public void testPreferSetterToAdder() {
    SetterAdderDataObject obj = new SetterAdderDataObject();
    SetterAdderDataObjectConverter.fromJson(new JsonObject().put("values", new JsonArray().add("first").add("second")), obj);
    Assert.assertEquals(Arrays.asList("first", "second"), obj.getValues());
    Assert.assertEquals(1, obj.sets);
    Assert.assertEquals(0, obj.adds);
  }

  @Test
  public void testSnakeFormatted() {
    SnakeFormattedDataObject obj = new SnakeFormattedDataObject();
    JsonObject expected = new JsonObject()
      .put("foo", "val1")
      .put("foo_bar", "val2")
      .put("foo_bar_juu", "val3");
    SnakeFormattedDataObjectConverter.fromJson(expected
      , obj);
    Assert.assertEquals("val1", obj.getFoo());
    Assert.assertEquals("val2", obj.getFooBar());
    Assert.assertEquals("val3", obj.getFooBarJuu());
    JsonObject test = new JsonObject();
    SnakeFormattedDataObjectConverter.toJson(obj, test);
    Assert.assertEquals(expected, test);
  }

  @Test
  public void testScreamingSnakeFormatted() {
    ScreamingSnakeFormattedDataObject obj = new ScreamingSnakeFormattedDataObject();
    JsonObject expected = new JsonObject()
        .put("FOO", "val1")
        .put("FOO_BAR", "val2")
        .put("FOO_BAR_JUU", "val3");
    ScreamingSnakeFormattedDataObjectConverter.fromJson(expected
        , obj);
    Assert.assertEquals("val1", obj.getFoo());
    Assert.assertEquals("val2", obj.getFooBar());
    Assert.assertEquals("val3", obj.getFooBarJuu());
    JsonObject test = new JsonObject();
    ScreamingSnakeFormattedDataObjectConverter.toJson(obj, test);
    Assert.assertEquals(expected, test);
  }

  @Test
  public void testBase64Basic() {
    TestDataObjectBase64Basic obj = new TestDataObjectBase64Basic();
    JsonObject expected = new JsonObject()
      .put("data", "PDw/Pz8+Pg==");

    // parse
    TestDataObjectBase64BasicConverter.fromJson(expected, obj);
    Assert.assertEquals(Buffer.buffer("<<???>>".getBytes(StandardCharsets.UTF_8)), obj.getData());

    // encode
    JsonObject json = new JsonObject();
    TestDataObjectBase64BasicConverter.toJson(obj, json);
    Assert.assertEquals("PDw/Pz8+Pg==", json.getValue("data"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBase64BasicBad() {
    TestDataObjectBase64Basic obj = new TestDataObjectBase64Basic();
    JsonObject expected = new JsonObject()
      .put("data", "PDw_Pz8-Pg");

    // parse should fail
    TestDataObjectBase64BasicConverter.fromJson(expected, obj);
  }

  @Test
  public void testBase64URL() {
    TestDataObjectBase64URL obj = new TestDataObjectBase64URL();
    JsonObject expected = new JsonObject()
      .put("data", "PDw_Pz8-Pg");

    // parse
    TestDataObjectBase64URLConverter.fromJson(expected, obj);
    Assert.assertEquals(Buffer.buffer("<<???>>".getBytes(StandardCharsets.UTF_8)), obj.getData());

    // encode
    JsonObject json = new JsonObject();
    TestDataObjectBase64URLConverter.toJson(obj, json);
    Assert.assertEquals("PDw_Pz8-Pg", json.getValue("data"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBase64URLBad() {
    TestDataObjectBase64URL obj = new TestDataObjectBase64URL();
    JsonObject expected = new JsonObject()
      .put("data", "PDw/Pz8+Pg==");

    // parse should fail
    TestDataObjectBase64URLConverter.fromJson(expected, obj);
  }
}
