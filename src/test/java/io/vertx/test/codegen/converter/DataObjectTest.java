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
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTest {

  private static JsonObject toJson(Map<String, Object> map) {
    JsonObject json = new JsonObject();
    map.forEach(json::put);
    return json;
  }

  @Test
  public void testJsonToDataObject() {

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
    AggregatedDataObject aggregatedDataObject = new AggregatedDataObject().setValue(TestUtils.randomAlphaString(20));
    Buffer buffer = TestUtils.randomBuffer(20);
    JsonObject jsonObject = new JsonObject().put("wibble", TestUtils.randomAlphaString(20));
    JsonArray jsonArray = new JsonArray().add(TestUtils.randomAlphaString(20));
    TimeUnit httpMethod = TimeUnit.values()[TestUtils.randomPositiveInt() % TimeUnit.values().length];
    ZonedDateTime dateTime = ZonedDateTime.now();
    ZonedDateTimeCodec zonedDateTimeCodec = new ZonedDateTimeCodec();

    Map<String, Object> map = new HashMap<>();
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomAlphaString(20));
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomBoolean());
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomInt());
    List<Object> list = new ArrayList<>();
    list.add(TestUtils.randomAlphaString(20));
    list.add(TestUtils.randomBoolean());
    list.add(TestUtils.randomInt());

    JsonObject json = new JsonObject();
    json.put("stringValue", stringValue);
    json.put("booleanValue", booleanValue);
    json.put("byteValue", byteValue);
    json.put("shortValue", shortValue);
    json.put("intValue", intValue);
    json.put("longValue", longValue);
    json.put("floatValue", floatValue);
    json.put("doubleValue", doubleValue);
    json.put("charValue", Character.toString(charValue));
    json.put("boxedBooleanValue", boxedBooleanValue);
    json.put("boxedByteValue", boxedByteValue);
    json.put("boxedShortValue", boxedShortValue);
    json.put("boxedIntValue", boxedIntValue);
    json.put("boxedLongValue", boxedLongValue);
    json.put("boxedFloatValue", boxedFloatValue);
    json.put("boxedDoubleValue", boxedDoubleValue);
    json.put("boxedCharValue", Character.toString(boxedCharValue));
    json.put("aggregatedDataObject", aggregatedDataObject.toJson());
    json.put("buffer", toBase64(buffer));
    json.put("jsonObject", jsonObject);
    json.put("jsonArray", jsonArray);
    json.put("httpMethod", httpMethod.toString());
    json.put("dateTime", zonedDateTimeCodec.encode(dateTime));
    json.put("stringValues", new JsonArray().add(stringValue));
    json.put("boxedBooleanValues", new JsonArray().add(boxedBooleanValue));
    json.put("boxedByteValues", new JsonArray().add(boxedByteValue));
    json.put("boxedShortValues", new JsonArray().add(boxedShortValue));
    json.put("boxedIntValues", new JsonArray().add(boxedIntValue));
    json.put("boxedLongValues", new JsonArray().add(boxedLongValue));
    json.put("boxedFloatValues", new JsonArray().add(boxedFloatValue));
    json.put("boxedDoubleValues", new JsonArray().add(boxedDoubleValue));
    json.put("boxedCharValues", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("aggregatedDataObjects", new JsonArray().add(aggregatedDataObject.toJson()));
    json.put("buffers", new JsonArray().add(toBase64(buffer)));
    json.put("jsonObjects", new JsonArray().add(jsonObject));
    json.put("jsonArrays", new JsonArray().add(jsonArray));
    json.put("httpMethods", new JsonArray().add(httpMethod.toString()));
    json.put("dateTimes", new JsonArray().add(zonedDateTimeCodec.encode(dateTime)));
    json.put("objects", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("stringSet", new JsonArray().add(stringValue));
    json.put("boxedBooleanSet", new JsonArray().add(boxedBooleanValue));
    json.put("boxedByteSet", new JsonArray().add(boxedByteValue));
    json.put("boxedShortSet", new JsonArray().add(boxedShortValue));
    json.put("boxedIntSet", new JsonArray().add(boxedIntValue));
    json.put("boxedLongSet", new JsonArray().add(boxedLongValue));
    json.put("boxedFloatSet", new JsonArray().add(boxedFloatValue));
    json.put("boxedDoubleSet", new JsonArray().add(boxedDoubleValue));
    json.put("boxedCharSet", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("aggregatedDataObjectSet", new JsonArray().add(aggregatedDataObject.toJson()));
    json.put("bufferSet", new JsonArray().add(toBase64(buffer)));
    json.put("jsonObjectSet", new JsonArray().add(jsonObject));
    json.put("jsonArraySet", new JsonArray().add(jsonArray));
    json.put("httpMethodSet", new JsonArray().add(httpMethod.toString()));
    json.put("dateTimeSet", new JsonArray().add(zonedDateTimeCodec.encode(dateTime)));
    json.put("objectSet", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
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
    json.put("addedAggregatedDataObjects", new JsonArray().add(aggregatedDataObject.toJson()));
    json.put("addedBuffers", new JsonArray().add(toBase64(buffer)));
    json.put("addedJsonObjects", new JsonArray().add(jsonObject));
    json.put("addedJsonArrays", new JsonArray().add(jsonArray));
    json.put("addedHttpMethods", new JsonArray().add(httpMethod.toString()));
    json.put("addedDateTimes", new JsonArray().add(zonedDateTimeCodec.encode(dateTime)));
    json.put("addedObjects", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("stringValueMap", new JsonObject().put(key, stringValue));
    json.put("boxedBooleanValueMap", new JsonObject().put(key, boxedBooleanValue));
    json.put("boxedByteValueMap", new JsonObject().put(key, boxedByteValue));
    json.put("boxedShortValueMap", new JsonObject().put(key, boxedShortValue));
    json.put("boxedIntValueMap", new JsonObject().put(key, boxedIntValue));
    json.put("boxedLongValueMap", new JsonObject().put(key, boxedLongValue));
    json.put("boxedFloatValueMap", new JsonObject().put(key, boxedFloatValue));
    json.put("boxedDoubleValueMap", new JsonObject().put(key, boxedDoubleValue));
    json.put("boxedCharValueMap", new JsonObject().put(key, Character.toString(boxedCharValue)));
    json.put("aggregatedDataObjectMap", new JsonObject().put(key, aggregatedDataObject.toJson()));
    json.put("bufferMap", new JsonObject().put(key, toBase64(buffer)));
    json.put("jsonObjectMap", new JsonObject().put(key, jsonObject));
    json.put("jsonArrayMap", new JsonObject().put(key, jsonArray));
    json.put("httpMethodMap", new JsonObject().put(key, httpMethod.toString()));
    json.put("dateTimeMap", new JsonObject().put(key, zonedDateTimeCodec.encode(dateTime)));
    json.put("objectMap", toJson(map));
    json.put("keyedStringValues", new JsonObject().put(key, stringValue));
    json.put("keyedBoxedBooleanValues", new JsonObject().put(key, boxedBooleanValue));
    json.put("keyedBoxedByteValues", new JsonObject().put(key, boxedByteValue));
    json.put("keyedBoxedShortValues", new JsonObject().put(key, boxedShortValue));
    json.put("keyedBoxedIntValues", new JsonObject().put(key, boxedIntValue));
    json.put("keyedBoxedLongValues", new JsonObject().put(key, boxedLongValue));
    json.put("keyedBoxedFloatValues", new JsonObject().put(key, boxedFloatValue));
    json.put("keyedBoxedDoubleValues", new JsonObject().put(key, boxedDoubleValue));
    json.put("keyedBoxedCharValues", new JsonObject().put(key, Character.toString(boxedCharValue)));
    json.put("keyedDataObjectValues", new JsonObject().put(key, aggregatedDataObject.toJson()));
    json.put("keyedBufferValues", new JsonObject().put(key, toBase64(buffer)));
    json.put("keyedJsonObjectValues", new JsonObject().put(key, jsonObject));
    json.put("keyedJsonArrayValues", new JsonObject().put(key, jsonArray));
    json.put("keyedEnumValues", new JsonObject().put(key, httpMethod.name()));
    json.put("keyedDateTimeValues", new JsonObject().put(key, zonedDateTimeCodec.encode(dateTime)));
    json.put("keyedObjectValues", toJson(map));

    TestDataObject obj = new TestDataObject();
    TestDataObjectConverter.fromJson(json, obj);

    assertEquals(stringValue, obj.getStringValue());
    assertEquals(booleanValue, obj.isBooleanValue());
    assertEquals(byteValue, obj.getByteValue());
    assertEquals(shortValue, obj.getShortValue());
    assertEquals(intValue, obj.getIntValue());
    assertEquals(longValue, obj.getLongValue());
    assertEquals(floatValue, obj.getFloatValue(), 0);
    assertEquals(doubleValue, obj.getDoubleValue(), 0);
    assertEquals(charValue, obj.getCharValue());
    assertEquals(boxedBooleanValue, obj.isBoxedBooleanValue());
    assertEquals(boxedByteValue, obj.getBoxedByteValue());
    assertEquals(boxedShortValue, obj.getBoxedShortValue());
    assertEquals(boxedIntValue, obj.getBoxedIntValue());
    assertEquals(boxedLongValue, obj.getBoxedLongValue());
    assertEquals(boxedFloatValue, obj.getBoxedFloatValue(), 0);
    assertEquals(boxedDoubleValue, obj.getBoxedDoubleValue(), 0);
    assertEquals(boxedCharValue, obj.getBoxedCharValue());
    assertEquals(aggregatedDataObject, obj.getAggregatedDataObject());
    assertEquals(buffer, obj.getBuffer());
    assertEquals(jsonObject, obj.getJsonObject());
    assertEquals(jsonArray, obj.getJsonArray());
    assertEquals(httpMethod, obj.getHttpMethod());
    assertEquals(dateTime, obj.getDateTime());
    assertEquals(Collections.singletonList(stringValue), obj.getStringValues());
    assertEquals(Collections.singletonList(boxedBooleanValue), obj.getBoxedBooleanValues());
    assertEquals(Collections.singletonList(boxedByteValue), obj.getBoxedByteValues());
    assertEquals(Collections.singletonList(boxedShortValue), obj.getBoxedShortValues());
    assertEquals(Collections.singletonList(boxedIntValue), obj.getBoxedIntValues());
    assertEquals(Collections.singletonList(boxedLongValue), obj.getBoxedLongValues());
    assertEquals(Collections.singletonList(boxedFloatValue), obj.getBoxedFloatValues());
    assertEquals(Collections.singletonList(boxedDoubleValue), obj.getBoxedDoubleValues());
    assertEquals(Collections.singletonList(boxedCharValue), obj.getBoxedCharValues());
    assertEquals(Collections.singletonList(aggregatedDataObject), obj.getAggregatedDataObjects());
    assertEquals(Collections.singletonList(buffer), obj.getBuffers());
    assertEquals(Collections.singletonList(jsonObject), obj.getJsonObjects());
    assertEquals(Collections.singletonList(jsonArray), obj.getJsonArrays());
    assertEquals(Collections.singletonList(httpMethod), obj.getHttpMethods());
    assertEquals(Collections.singletonList(dateTime), obj.getDateTimes());
    assertEquals(list, obj.getObjects());
    assertEquals(Collections.singleton(stringValue), obj.getStringSet());
    assertEquals(Collections.singleton(boxedBooleanValue), obj.getBoxedBooleanSet());
    assertEquals(Collections.singleton(boxedByteValue), obj.getBoxedByteSet());
    assertEquals(Collections.singleton(boxedShortValue), obj.getBoxedShortSet());
    assertEquals(Collections.singleton(boxedIntValue), obj.getBoxedIntSet());
    assertEquals(Collections.singleton(boxedLongValue), obj.getBoxedLongSet());
    assertEquals(Collections.singleton(boxedFloatValue), obj.getBoxedFloatSet());
    assertEquals(Collections.singleton(boxedDoubleValue), obj.getBoxedDoubleSet());
    assertEquals(Collections.singleton(boxedCharValue), obj.getBoxedCharSet());
    assertEquals(Collections.singleton(aggregatedDataObject), obj.getAggregatedDataObjectSet());
    assertEquals(Collections.singleton(buffer), obj.getBufferSet());
    assertEquals(Collections.singleton(jsonObject), obj.getJsonObjectSet());
    assertEquals(Collections.singleton(jsonArray), obj.getJsonArraySet());
    assertEquals(Collections.singleton(httpMethod), obj.getHttpMethodSet());
    assertEquals(Collections.singleton(dateTime), obj.getDateTimeSet());
    assertEquals(new LinkedHashSet<>(list), obj.getObjectSet());
    assertEquals(Collections.singletonList(stringValue), obj.getAddedStringValues());
    assertEquals(Collections.singletonList(boxedBooleanValue), obj.getAddedBoxedBooleanValues());
    assertEquals(Collections.singletonList(boxedByteValue), obj.getAddedBoxedByteValues());
    assertEquals(Collections.singletonList(boxedShortValue), obj.getAddedBoxedShortValues());
    assertEquals(Collections.singletonList(boxedIntValue), obj.getAddedBoxedIntValues());
    assertEquals(Collections.singletonList(boxedLongValue), obj.getAddedBoxedLongValues());
    assertEquals(Collections.singletonList(boxedFloatValue), obj.getAddedBoxedFloatValues());
    assertEquals(Collections.singletonList(boxedDoubleValue), obj.getAddedBoxedDoubleValues());
    assertEquals(Collections.singletonList(boxedCharValue), obj.getAddedBoxedCharValues());
    assertEquals(Collections.singletonList(aggregatedDataObject), obj.getAddedAggregatedDataObjects());
    assertEquals(Collections.singletonList(buffer), obj.getAddedBuffers());
    assertEquals(Collections.singletonList(jsonObject), obj.getAddedJsonObjects());
    assertEquals(Collections.singletonList(jsonArray), obj.getAddedJsonArrays());
    assertEquals(Collections.singletonList(httpMethod), obj.getAddedHttpMethods());
    assertEquals(Collections.singletonList(dateTime), obj.getAddedDateTimes());
    assertEquals(list, obj.getAddedObjects());
    assertEquals(Collections.singletonMap(key, stringValue), obj.getStringValueMap());
    assertEquals(Collections.singletonMap(key, boxedBooleanValue), obj.getBoxedBooleanValueMap());
    assertEquals(Collections.singletonMap(key, boxedByteValue), obj.getBoxedByteValueMap());
    assertEquals(Collections.singletonMap(key, boxedShortValue), obj.getBoxedShortValueMap());
    assertEquals(Collections.singletonMap(key, boxedIntValue), obj.getBoxedIntValueMap());
    assertEquals(Collections.singletonMap(key, boxedLongValue), obj.getBoxedLongValueMap());
    assertEquals(Collections.singletonMap(key, boxedFloatValue), obj.getBoxedFloatValueMap());
    assertEquals(Collections.singletonMap(key, boxedDoubleValue), obj.getBoxedDoubleValueMap());
    assertEquals(Collections.singletonMap(key, boxedCharValue), obj.getBoxedCharValueMap());
    assertEquals(Collections.singletonMap(key, aggregatedDataObject), obj.getAggregatedDataObjectMap());
    assertEquals(Collections.singletonMap(key, buffer), obj.getBufferMap());
    assertEquals(Collections.singletonMap(key, jsonObject), obj.getJsonObjectMap());
    assertEquals(Collections.singletonMap(key, jsonArray), obj.getJsonArrayMap());
    assertEquals(Collections.singletonMap(key, httpMethod), obj.getHttpMethodMap());
    assertEquals(Collections.singletonMap(key, dateTime), obj.getDateTimeMap());
    assertEquals(map, obj.getObjectMap());
    assertEquals(Collections.singletonMap(key, stringValue), obj.getKeyedStringValues());
    assertEquals(Collections.singletonMap(key, boxedBooleanValue), obj.getKeyedBoxedBooleanValues());
    assertEquals(Collections.singletonMap(key, boxedByteValue), obj.getKeyedBoxedByteValues());
    assertEquals(Collections.singletonMap(key, boxedShortValue), obj.getKeyedBoxedShortValues());
    assertEquals(Collections.singletonMap(key, boxedIntValue), obj.getKeyedBoxedIntValues());
    assertEquals(Collections.singletonMap(key, boxedLongValue), obj.getKeyedBoxedLongValues());
    assertEquals(Collections.singletonMap(key, boxedFloatValue), obj.getKeyedBoxedFloatValues());
    assertEquals(Collections.singletonMap(key, boxedDoubleValue), obj.getKeyedBoxedDoubleValues());
    assertEquals(Collections.singletonMap(key, boxedCharValue), obj.getKeyedBoxedCharValues());
    assertEquals(Collections.singletonMap(key, aggregatedDataObject), obj.getKeyedDataObjectValues());
    assertEquals(Collections.singletonMap(key, buffer), obj.getKeyedBufferValues());
    assertEquals(Collections.singletonMap(key, jsonObject), obj.getKeyedJsonObjectValues());
    assertEquals(Collections.singletonMap(key, jsonArray), obj.getKeyedJsonArrayValues());
    assertEquals(Collections.singletonMap(key, httpMethod), obj.getKeyedEnumValues());
    assertEquals(Collections.singletonMap(key, dateTime), obj.getKeyedDateTimeValues());
    assertEquals(map, obj.getObjectMap());

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

  @Test
  public void testEmptyJsonToDataObject() {

    JsonObject json = new JsonObject();

    TestDataObject obj = new TestDataObject();
    TestDataObjectConverter.fromJson(json, obj);

    assertEquals(null, obj.getStringValue());
    assertEquals(false, obj.isBooleanValue());
    assertEquals(0, obj.getByteValue());
    assertEquals(0, obj.getShortValue());
    assertEquals(0, obj.getIntValue());
    assertEquals(0l, obj.getLongValue());
    assertEquals(0f, obj.getFloatValue(), 0);
    assertEquals(0d, obj.getDoubleValue(), 0);
    assertEquals((char)0, obj.getCharValue());
    assertEquals(null, obj.isBoxedBooleanValue());
    assertEquals(null, obj.getBoxedByteValue());
    assertEquals(null, obj.getBoxedShortValue());
    assertEquals(null, obj.getBoxedIntValue());
    assertEquals(null, obj.getBoxedLongValue());
    assertEquals(null, obj.getBoxedFloatValue());
    assertEquals(null, obj.getBoxedDoubleValue());
    assertEquals(null, obj.getBoxedCharValue());
    assertEquals(null, obj.getAggregatedDataObject());
    assertEquals(null, obj.getBuffer());
    assertEquals(null, obj.getJsonObject());
    assertEquals(null, obj.getJsonArray());
    assertEquals(null, obj.getDateTime());
    assertEquals(null, obj.getStringValues());
    assertEquals(null, obj.getBoxedBooleanValues());
    assertEquals(null, obj.getBoxedByteValues());
    assertEquals(null, obj.getBoxedShortValues());
    assertEquals(null, obj.getBoxedIntValues());
    assertEquals(null, obj.getBoxedLongValues());
    assertEquals(null, obj.getBoxedFloatValues());
    assertEquals(null, obj.getBoxedDoubleValues());
    assertEquals(null, obj.getBoxedCharValues());
    assertEquals(null, obj.getAggregatedDataObjects());
    assertEquals(null, obj.getBuffers());
    assertEquals(null, obj.getJsonObjects());
    assertEquals(null, obj.getJsonArrays());
    assertEquals(null, obj.getHttpMethods());
    assertEquals(null, obj.getDateTimes());
    assertEquals(null, obj.getObjects());
    assertEquals(null, obj.getStringSet());
    assertEquals(null, obj.getBoxedBooleanSet());
    assertEquals(null, obj.getBoxedByteSet());
    assertEquals(null, obj.getBoxedShortSet());
    assertEquals(null, obj.getBoxedIntSet());
    assertEquals(null, obj.getBoxedLongSet());
    assertEquals(null, obj.getBoxedFloatSet());
    assertEquals(null, obj.getBoxedDoubleSet());
    assertEquals(null, obj.getBoxedCharSet());
    assertEquals(null, obj.getAggregatedDataObjectSet());
    assertEquals(null, obj.getBufferSet());
    assertEquals(null, obj.getJsonObjectSet());
    assertEquals(null, obj.getJsonArraySet());
    assertEquals(null, obj.getHttpMethodSet());
    assertEquals(null, obj.getDateTimeSet());
    assertEquals(null, obj.getObjectSet());
    assertEquals(Collections.emptyList(), obj.getAddedStringValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedBooleanValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedByteValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedShortValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedIntValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedLongValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedFloatValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedDoubleValues());
    assertEquals(Collections.emptyList(), obj.getAddedBoxedCharValues());
    assertEquals(Collections.emptyList(), obj.getAddedAggregatedDataObjects());
    assertEquals(Collections.emptyList(), obj.getAddedBuffers());
    assertEquals(Collections.emptyList(), obj.getAddedJsonObjects());
    assertEquals(Collections.emptyList(), obj.getAddedJsonArrays());
    assertEquals(Collections.emptyList(), obj.getAddedHttpMethods());
    assertEquals(Collections.emptyList(), obj.getAddedDateTimes());
    assertEquals(Collections.emptyList(), obj.getAddedObjects());
    assertEquals(null, obj.getStringValueMap());
    assertEquals(null, obj.getBoxedBooleanValueMap());
    assertEquals(null, obj.getBoxedByteValueMap());
    assertEquals(null, obj.getBoxedShortValueMap());
    assertEquals(null, obj.getBoxedIntValueMap());
    assertEquals(null, obj.getBoxedLongValueMap());
    assertEquals(null, obj.getBoxedFloatValueMap());
    assertEquals(null, obj.getBoxedDoubleValueMap());
    assertEquals(null, obj.getBoxedCharValueMap());
    assertEquals(null, obj.getAggregatedDataObjectMap());
    assertEquals(null, obj.getBufferMap());
    assertEquals(null, obj.getJsonObjectMap());
    assertEquals(null, obj.getJsonArrayMap());
    assertEquals(null, obj.getHttpMethodMap());
    assertEquals(null, obj.getDateTimeMap());
    assertEquals(null, obj.getObjectMap());
  }

  @Test
  public void testDataObjectToJson() {
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
    AggregatedDataObject aggregatedDataObject = new AggregatedDataObject().setValue(TestUtils.randomAlphaString(20));
    Buffer buffer = TestUtils.randomBuffer(20);
    JsonObject jsonObject = new JsonObject().put("wibble", TestUtils.randomAlphaString(20));
    JsonArray jsonArray = new JsonArray().add(TestUtils.randomAlphaString(20));
    TimeUnit httpMethod = TimeUnit.values()[TestUtils.randomPositiveInt() % TimeUnit.values().length];
    ZonedDateTime dateTime = ZonedDateTime.now();
    ZonedDateTimeCodec zonedDateTimeCodec = new ZonedDateTimeCodec();

    Map<String, Object> map = new HashMap<>();
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomAlphaString(20));
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomBoolean());
    map.put(TestUtils.randomAlphaString(10), TestUtils.randomInt());
    List<Object> list = new ArrayList<>();
    list.add(TestUtils.randomAlphaString(20));
    list.add(TestUtils.randomBoolean());
    list.add(TestUtils.randomInt());

    TestDataObject obj = new TestDataObject();
    obj.setStringValue(stringValue);
    obj.setBooleanValue(booleanValue);
    obj.setByteValue(byteValue);
    obj.setShortValue(shortValue);
    obj.setIntValue(intValue);
    obj.setLongValue(longValue);
    obj.setFloatValue(floatValue);
    obj.setDoubleValue(doubleValue);
    obj.setCharValue(charValue);
    obj.setBoxedBooleanValue(boxedBooleanValue);
    obj.setBoxedByteValue(boxedByteValue);
    obj.setBoxedShortValue(boxedShortValue);
    obj.setBoxedIntValue(boxedIntValue);
    obj.setBoxedLongValue(boxedLongValue);
    obj.setBoxedFloatValue(boxedFloatValue);
    obj.setBoxedDoubleValue(boxedDoubleValue);
    obj.setBoxedCharValue(boxedCharValue);
    obj.setAggregatedDataObject(aggregatedDataObject);
    obj.setBuffer(buffer);
    obj.setJsonObject(jsonObject);
    obj.setJsonArray(jsonArray);
    obj.setHttpMethod(httpMethod);
    obj.setDateTime(dateTime);
    obj.setStringValues(Collections.singletonList(stringValue));
    obj.setBoxedBooleanValues(Collections.singletonList(boxedBooleanValue));
    obj.setBoxedByteValues(Collections.singletonList(boxedByteValue));
    obj.setBoxedShortValues(Collections.singletonList(boxedShortValue));
    obj.setBoxedIntValues(Collections.singletonList(boxedIntValue));
    obj.setBoxedLongValues(Collections.singletonList(boxedLongValue));
    obj.setBoxedFloatValues(Collections.singletonList(boxedFloatValue));
    obj.setBoxedDoubleValues(Collections.singletonList(boxedDoubleValue));
    obj.setBoxedCharValues(Collections.singletonList(boxedCharValue));
    obj.setAggregatedDataObjects(Collections.singletonList(aggregatedDataObject));
    obj.setBuffers(Collections.singletonList(buffer));
    obj.setJsonObjects(Collections.singletonList(jsonObject));
    obj.setJsonArrays(Collections.singletonList(jsonArray));
    obj.setHttpMethods(Collections.singletonList(httpMethod));
    obj.setDateTimes(Collections.singletonList(dateTime));
    obj.setObjects(list);
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
    obj.setAggregatedDataObjectSet(Collections.singleton(aggregatedDataObject));
    obj.setBufferSet(Collections.singleton(buffer));
    obj.setJsonObjectSet(Collections.singleton(jsonObject));
    obj.setJsonArraySet(Collections.singleton(jsonArray));
    obj.setHttpMethodSet(Collections.singleton(httpMethod));
    obj.setDateTimeSet(Collections.singleton(dateTime));
    obj.setObjectSet(new LinkedHashSet<>(list));
    obj.setBoxedBooleanValueMap(Collections.singletonMap(key, boxedBooleanValue));
    obj.setBoxedByteValueMap(Collections.singletonMap(key, boxedByteValue));
    obj.setBoxedShortValueMap(Collections.singletonMap(key, boxedShortValue));
    obj.setBoxedIntValueMap(Collections.singletonMap(key, boxedIntValue));
    obj.setBoxedLongValueMap(Collections.singletonMap(key, boxedLongValue));
    obj.setBoxedFloatValueMap(Collections.singletonMap(key, boxedFloatValue));
    obj.setBoxedDoubleValueMap(Collections.singletonMap(key, boxedDoubleValue));
    obj.setBoxedCharValueMap(Collections.singletonMap(key, boxedCharValue));
    obj.setAggregatedDataObjectMap(Collections.singletonMap(key, aggregatedDataObject));
    obj.setBufferMap(Collections.singletonMap(key, buffer));
    obj.setJsonObjectMap(Collections.singletonMap(key, jsonObject));
    obj.setJsonArrayMap(Collections.singletonMap(key, jsonArray));
    obj.setHttpMethodMap(Collections.singletonMap(key, httpMethod));
    obj.setDateTimeMap(Collections.singletonMap(key, dateTime));
    obj.setObjectMap(map);
    obj.addKeyedStringValue(key, stringValue);
    obj.addKeyedBoxedBooleanValue(key, boxedBooleanValue);
    obj.addKeyedBoxedByteValue(key, boxedByteValue);
    obj.addKeyedBoxedShortValue(key, boxedShortValue);
    obj.addKeyedBoxedIntValue(key, boxedIntValue);
    obj.addKeyedBoxedLongValue(key, boxedLongValue);
    obj.addKeyedBoxedFloatValue(key, boxedFloatValue);
    obj.addKeyedBoxedDoubleValue(key, boxedDoubleValue);
    obj.addKeyedBoxedCharValue(key, boxedCharValue);
    obj.addKeyedDataObjectValue(key, aggregatedDataObject);
    obj.addKeyedBufferValue(key, buffer);
    obj.addKeyedJsonObjectValue(key, jsonObject);
    obj.addKeyedJsonArrayValue(key, jsonArray);
    obj.addKeyedEnumValue(key, httpMethod);
    obj.addKeyedDateTimeValue(key, dateTime);
    map.forEach(obj::addKeyedObjectValue);

    Map<String, Object> json = new HashMap<>();
    TestDataObjectConverter.toJson(obj, json);

    assertEquals(stringValue, json.get("stringValue"));
    assertEquals(booleanValue, json.get("booleanValue"));
    assertEquals(byteValue, (byte)json.get("byteValue"));
    assertEquals(shortValue, (short)json.get("shortValue"));
    assertEquals(intValue, (int)json.get("intValue"));
    assertEquals(longValue, (long)json.get("longValue"));
    assertEquals(floatValue, (float)json.get("floatValue"), 0.001);
    assertEquals(doubleValue, (double)json.get("doubleValue"), 0.001);
    assertEquals(Character.toString(charValue), json.get("charValue"));
    assertEquals(boxedBooleanValue, json.get("boxedBooleanValue"));
    assertEquals((byte)boxedByteValue, (byte)json.get("boxedByteValue"));
    assertEquals((short)boxedShortValue, (short)json.get("boxedShortValue"));
    assertEquals(boxedIntValue, json.get("boxedIntValue"));
    assertEquals(boxedLongValue, json.get("boxedLongValue"));
    assertEquals(boxedFloatValue, (float)json.get("boxedFloatValue"), 0.001);
    assertEquals(boxedDoubleValue, (double) json.get("boxedDoubleValue"), 0.001);
    assertEquals(Character.toString(boxedCharValue), json.get("boxedCharValue"));
    assertEquals(aggregatedDataObject.toJson(), json.get("aggregatedDataObject"));
    assertEquals(buffer, Buffer.buffer(Base64.getDecoder().decode((String)json.get("buffer"))));
    assertEquals(jsonObject, json.get("jsonObject"));
    assertEquals(jsonArray, json.get("jsonArray"));
    assertEquals(httpMethod.name(), json.get("httpMethod"));
    assertEquals(zonedDateTimeCodec.encode(dateTime), json.get("dateTime"));
    assertEquals(new JsonArray().add(stringValue), json.get("stringValues"));
    assertEquals(new JsonArray().add(boxedBooleanValue), json.get("boxedBooleanValues"));
    assertEquals(new JsonArray().add(boxedByteValue), json.get("boxedByteValues"));
    assertEquals(new JsonArray().add(boxedShortValue), json.get("boxedShortValues"));
    assertEquals(new JsonArray().add(boxedIntValue), json.get("boxedIntValues"));
    assertEquals(new JsonArray().add(boxedLongValue), json.get("boxedLongValues"));
    assertEquals(1, ((JsonArray)json.get("boxedFloatValues")).size());
    assertEquals(boxedFloatValue, (float)((JsonArray)json.get("boxedFloatValues")).getValue(0), 0.001);
    assertEquals(1, ((JsonArray)json.get("boxedDoubleValues")).size());
    assertEquals(boxedDoubleValue, (double)((JsonArray)json.get("boxedDoubleValues")).getValue(0), 0.001);
    assertEquals(new JsonArray().add(Character.toString(boxedCharValue)), json.get("boxedCharValues"));
    assertEquals(new JsonArray().add(aggregatedDataObject.toJson()), json.get("aggregatedDataObjects"));
    assertEquals(Base64.getEncoder().encodeToString(buffer.getBytes()), ((JsonArray)json.get("buffers")).getValue(0));
    assertEquals(new JsonArray().add(jsonObject), json.get("jsonObjects"));
    assertEquals(new JsonArray().add(jsonArray), json.get("jsonArrays"));
    assertEquals(new JsonArray().add(httpMethod.name()), json.get("httpMethods"));
    assertEquals(new JsonArray().add(zonedDateTimeCodec.encode(dateTime)), json.get("dateTimes"));
    assertEquals(new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)), json.get("objects"));
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
    assertEquals(new JsonArray().add(aggregatedDataObject.toJson()), json.get("aggregatedDataObjectSet"));
    assertEquals(Base64.getEncoder().encodeToString(buffer.getBytes()), ((JsonArray)json.get("bufferSet")).getValue(0));
    assertEquals(new JsonArray().add(jsonObject), json.get("jsonObjectSet"));
    assertEquals(new JsonArray().add(jsonArray), json.get("jsonArraySet"));
    assertEquals(new JsonArray().add(httpMethod.name()), json.get("httpMethodSet"));
    assertEquals(new JsonArray().add(zonedDateTimeCodec.encode(dateTime)), json.get("dateTimeSet"));
    assertEquals(new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)), json.get("objectSet"));
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
    assertEquals(new JsonObject().put(key, aggregatedDataObject.toJson()), json.get("aggregatedDataObjectMap"));
    assertEquals(Base64.getEncoder().encodeToString(buffer.getBytes()), ((JsonObject)json.get("bufferMap")).getValue(key));
    assertEquals(new JsonObject().put(key, jsonObject), json.get("jsonObjectMap"));
    assertEquals(new JsonObject().put(key, jsonArray), json.get("jsonArrayMap"));
    assertEquals(new JsonObject().put(key, httpMethod.name()), json.get("httpMethodMap"));
    assertEquals(new JsonObject().put(key, zonedDateTimeCodec.encode(dateTime)), json.get("dateTimeMap"));
    assertEquals(toJson(map), json.get("objectMap"));
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
    assertEquals(new JsonObject().put(key, aggregatedDataObject.toJson()), json.get("keyedDataObjectValues"));
    assertEquals(Base64.getEncoder().encodeToString(buffer.getBytes()), ((JsonObject)json.get("keyedBufferValues")).getValue(key));
    assertEquals(new JsonObject().put(key, jsonObject), json.get("keyedJsonObjectValues"));
    assertEquals(new JsonObject().put(key, jsonArray), json.get("keyedJsonArrayValues"));
    assertEquals(new JsonObject().put(key, httpMethod.name()), json.get("keyedEnumValues"));
    assertEquals(new JsonObject().put(key, zonedDateTimeCodec.encode(dateTime)), json.get("keyedDateTimeValues"));
    assertEquals(toJson(map), json.get("keyedObjectValues"));
  }

  @Test
  public void testEmptyDataObjectToJson() {

    TestDataObject obj = new TestDataObject();

    Map<String, Object> json = new HashMap<>();
    TestDataObjectConverter.toJson(obj, json);

    assertEquals(null, json.get("stringValue"));
    assertEquals(false, json.get("booleanValue"));
    assertEquals(0, (byte)json.get("byteValue"));
    assertEquals(0, (short)json.get("shortValue"));
    assertEquals(0, (int)json.get("intValue"));
    assertEquals(0L, (long) json.get("longValue"));
    assertEquals(0f, (float) json.get("floatValue"), 0);
    assertEquals(0d, (double) json.get("doubleValue"), 0);
    assertEquals(Character.toString((char)0), json.get("charValue"));
    assertEquals(null, json.get("boxedBooleanValue"));
    assertEquals(null, json.get("boxedByteValue"));
    assertEquals(null, json.get("boxedShortValue"));
    assertEquals(null, json.get("boxedIntValue"));
    assertEquals(null, json.get("boxedLongValue"));
    assertEquals(null, json.get("boxedFloatValue"));
    assertEquals(null, json.get("boxedDoubleValue"));
    assertEquals(null, json.get("boxedCharValue"));
    assertEquals(null, json.get("aggregatedDataObject"));
    assertEquals(null, json.get("buffer"));
    assertEquals(null, json.get("jsonObject"));
    assertEquals(null, json.get("jsonArray"));
    assertEquals(null, json.get("httpMethod"));
    assertEquals(null, json.get("dateTime"));
    assertEquals(null, json.get("stringValues"));
    assertEquals(null, json.get("boxedBooleanValues"));
    assertEquals(null, json.get("boxedByteValues"));
    assertEquals(null, json.get("boxedShortValues"));
    assertEquals(null, json.get("boxedIntValues"));
    assertEquals(null, json.get("boxedLongValues"));
    assertEquals(null, json.get("boxedFloatValues"));
    assertEquals(null, json.get("boxedDoubleValues"));
    assertEquals(null, json.get("boxedCharValues"));
    assertEquals(null, json.get("aggregatedDataObjects"));
    assertEquals(null, json.get("buffers"));
    assertEquals(null, json.get("jsonObjects"));
    assertEquals(null, json.get("jsonArrays"));
    assertEquals(null, json.get("httpMethods"));
    assertEquals(null, json.get("dateTimes"));
    assertEquals(null, json.get("objects"));
    assertEquals(null, json.get("stringSet"));
    assertEquals(null, json.get("boxedBooleanSet"));
    assertEquals(null, json.get("boxedByteSet"));
    assertEquals(null, json.get("boxedShortSet"));
    assertEquals(null, json.get("boxedIntSet"));
    assertEquals(null, json.get("boxedLongSet"));
    assertEquals(null, json.get("boxedFloatSet"));
    assertEquals(null, json.get("boxedDoubleSet"));
    assertEquals(null, json.get("boxedCharSet"));
    assertEquals(null, json.get("aggregatedDataObjectSet"));
    assertEquals(null, json.get("bufferSet"));
    assertEquals(null, json.get("jsonObjectSet"));
    assertEquals(null, json.get("jsonArraySet"));
    assertEquals(null, json.get("httpMethodSet"));
    assertEquals(null, json.get("dateTimeSet"));
    assertEquals(null, json.get("objectSet"));
    assertEquals(new JsonArray(), json.get("addedStringValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedBooleanValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedByteValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedShortValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedIntValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedLongValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedFloatValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedDoubleValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedCharValues"));
    assertEquals(new JsonArray(), json.get("addedAggregatedDataObjects"));
    assertEquals(new JsonArray(), json.get("addedBuffers"));
    assertEquals(new JsonArray(), json.get("addedJsonObjects"));
    assertEquals(new JsonArray(), json.get("addedJsonArrays"));
    assertEquals(new JsonArray(), json.get("addedHttpMethods"));
    assertEquals(new JsonArray(), json.get("addedDateTimes"));
    assertEquals(new JsonArray(), json.get("addedObjects"));
    assertEquals(null, json.get("stringValueMap"));
    assertEquals(null, json.get("boxedBooleanValueMap"));
    assertEquals(null, json.get("boxedByteValueMap"));
    assertEquals(null, json.get("boxedShortValueMap"));
    assertEquals(null, json.get("boxedIntValueMap"));
    assertEquals(null, json.get("boxedLongValueMap"));
    assertEquals(null, json.get("boxedFloatValueMap"));
    assertEquals(null, json.get("boxedDoubleValueMap"));
    assertEquals(null, json.get("boxedCharValueMap"));
    assertEquals(null, json.get("aggregatedDataObjectMap"));
    assertEquals(null, json.get("bufferMap"));
    assertEquals(null, json.get("jsonObjectMap"));
    assertEquals(null, json.get("jsonArrayMap"));
    assertEquals(null, json.get("httpMethodMap"));
    assertEquals(null, json.get("dateTimeMap"));
    assertEquals(null, json.get("objectMap"));
  }

  @Test
  public void testNoConvertersFromJsonMethod() throws ClassNotFoundException {
    Class<?> clazz = NoConverterDataObject.class.getClassLoader().loadClass(NoConverterDataObject.class.getName() + "Converter");
    try {
      clazz.getMethod("fromJson", Iterable.class, NoConverterDataObject.class);
      fail("Data Object marked with generateConverter = false must not generate fromJson");
    } catch (NoSuchMethodException e) {
      // Ok
    } //TODO wtf? should remove?
  }

  @Test
  public void testNoConvertersToJsonMethod() throws ClassNotFoundException {
    Class<?> clazz = NoConverterDataObject.class.getClassLoader().loadClass(NoConverterDataObject.class.getName() + "Converter");
    try {
      clazz.getMethod("toJson", NoConverterDataObject.class, JsonObject.class);
      fail("Data Object marked with generateConverter = false must not generate fromJson");
    } catch (NoSuchMethodException e) {
      // Ok
    } //TODO wtf? should remove?
  }

  @Test
  public void testGeneratedCodecMustUseGeneratedFromJson() {
    ConverterGeneratesDecoderWithFromJsonDataObjectConverter conv = ConverterGeneratesDecoderWithFromJsonDataObjectConverter.INSTANCE;
    assertEquals(new JsonObject().put("hello", "francesco"), conv.encode(new ConverterGeneratesDecoderWithFromJsonDataObject()));
    assertEquals(1, conv.decode(new JsonObject().put("a", 1)).getA());
  }

  @Test
  public void testGeneratedCodecMustUseGeneratedToJson() {
    ConverterGeneratesEncoderWithToJsonDataObjectConverter conv = ConverterGeneratesEncoderWithToJsonDataObjectConverter.INSTANCE;
    assertEquals(new JsonObject().put("a", 2), conv.encode(new ConverterGeneratesEncoderWithToJsonDataObject(null).setA(2)));
    assertEquals(-1, conv.decode(new JsonObject().put("a", 1)).getA());
  }

  @Test
  public void testGeneratedCodecMustUseGeneratedToJsonAndStaticCodec() {
    AbstractConverterGeneratesEncoderWithToJsonDataObjectConverter conv = AbstractConverterGeneratesEncoderWithToJsonDataObjectConverter.INSTANCE;
    assertEquals(new JsonObject().put("a", 2), conv.encode(new AbstractConverterGeneratesEncoderWithToJsonDataObject(null) {}.setA(2)));
    assertEquals(-5, conv.decode(new JsonObject().put("a", 1)).getA());
  }

  @Test
  public void testGeneratedCodecMustUseConverterStaticMethods() {
    ConverterGeneratesCompleteCodecDataObjectConverter conv = ConverterGeneratesCompleteCodecDataObjectConverter.INSTANCE;
    assertEquals(new JsonObject().put("a", 2), conv.encode(new ConverterGeneratesCompleteCodecDataObject().setA(2)));
    assertEquals(2, conv.decode(new JsonObject().put("a", 2)).getA());
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

  private String toBase64(Buffer buffer) {
    return Base64.getEncoder().encodeToString(buffer.getBytes());
  }

  @Test
  public void testPreferSetterToAdder() {
    SetterAdderDataObject obj = new SetterAdderDataObject();
    SetterAdderDataObjectConverter.fromJson(new JsonObject().put("values", new JsonArray().add("first").add("second")), obj);
    assertEquals(Arrays.asList("first", "second"), obj.getValues());
    assertEquals(1, obj.sets);
    assertEquals(0, obj.adds);
  }
}
