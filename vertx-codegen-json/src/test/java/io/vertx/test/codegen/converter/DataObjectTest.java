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

  private static JsonObject toJson(Map<String, Object> map) {
    JsonObject json = new JsonObject();
    map.forEach(json::put);
    return json;
  }

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
    AutoMapped autoMapped = AutoMapped.of("vertx.io", 80);
    AutoMappedWithVertxGen autoMappedWithVertxGen = AutoMappedWithVertxGen.of("vertx.io", 80);

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
    json.put("primitiveBooleanValue", booleanValue);
    json.put("primitiveByteValue", byteValue);
    json.put("primitiveShortValue", shortValue);
    json.put("primitiveIntValue", intValue);
    json.put("primitiveLongValue", longValue);
    json.put("primitiveFloatValue", floatValue);
    json.put("primitiveDoubleValue", doubleValue);
    json.put("primitiveCharValue", Character.toString(charValue));
    json.put("boxedBooleanValue", boxedBooleanValue);
    json.put("boxedByteValue", boxedByteValue);
    json.put("boxedShortValue", boxedShortValue);
    json.put("boxedIntValue", boxedIntValue);
    json.put("boxedLongValue", boxedLongValue);
    json.put("boxedFloatValue", boxedFloatValue);
    json.put("boxedDoubleValue", boxedDoubleValue);
    json.put("boxedCharValue", Character.toString(boxedCharValue));
    json.put("jsonObjectDataObjectValue", jsonObjectDataObject.toJson());
    json.put("stringDataObjectValue", stringDataObject.toJson());
    json.put("bufferValue", toBase64(buffer));
    json.put("jsonObjectValue", jsonObject);
    json.put("jsonArrayValue", jsonArray);
    json.put("enumValue", httpMethod.toString());
    json.put("methodMappedValue", methodMapped.toString());
    json.put("noConverterDataObjectValue", new JsonObject());
    json.put("noConverter2DataObjectValue", new JsonObject());
    json.put("customEnumValue", enumMapped.getShortName());
    json.put("autoMappedValue", autoMapped.toJson());
    json.put("autoMappedWithVertxGenValue", autoMappedWithVertxGen.toJson());
    json.put("stringValueList", new JsonArray().add(stringValue));
    json.put("boxedBooleanValueList", new JsonArray().add(boxedBooleanValue));
    json.put("boxedByteValueList", new JsonArray().add(boxedByteValue));
    json.put("boxedShortValueList", new JsonArray().add(boxedShortValue));
    json.put("boxedIntValueList", new JsonArray().add(boxedIntValue));
    json.put("boxedLongValueList", new JsonArray().add(boxedLongValue));
    json.put("boxedFloatValueList", new JsonArray().add(boxedFloatValue));
    json.put("boxedDoubleValueList", new JsonArray().add(boxedDoubleValue));
    json.put("boxedCharValueList", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("jsonObjectDataObjectValueList", new JsonArray().add(jsonObjectDataObject.toJson()));
    json.put("stringDataObjectValueList", new JsonArray().add(stringDataObject.toJson()));
    json.put("bufferValueList", new JsonArray().add(toBase64(buffer)));
    json.put("jsonObjectValueList", new JsonArray().add(jsonObject));
    json.put("jsonArrayValueList", new JsonArray().add(jsonArray));
    json.put("enumValueList", new JsonArray().add(httpMethod.toString()));
    json.put("methodMappedValueList", new JsonArray().add(methodMapped.toString()));
    json.put("noConverterDataObjectValueList", new JsonArray().add(new JsonObject()));
    json.put("noConverter2DataObjectValueList", new JsonArray().add(new JsonObject()));
    json.put("objectValueList", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("customEnumValueList", new JsonArray().add(enumMapped.getShortName()));
    json.put("autoMappedValueList", new JsonArray().add(autoMapped.toJson()));
    json.put("autoMappedWithVertxGenValueList", new JsonArray().add(autoMappedWithVertxGen.toJson()));
    json.put("stringValueSet", new JsonArray().add(stringValue));
    json.put("boxedBooleanValueSet", new JsonArray().add(boxedBooleanValue));
    json.put("boxedByteValueSet", new JsonArray().add(boxedByteValue));
    json.put("boxedShortValueSet", new JsonArray().add(boxedShortValue));
    json.put("boxedIntValueSet", new JsonArray().add(boxedIntValue));
    json.put("boxedLongValueSet", new JsonArray().add(boxedLongValue));
    json.put("boxedFloatValueSet", new JsonArray().add(boxedFloatValue));
    json.put("boxedDoubleValueSet", new JsonArray().add(boxedDoubleValue));
    json.put("boxedCharValueSet", new JsonArray().add(Character.toString(boxedCharValue)));
    json.put("jsonObjectDataObjectValueSet", new JsonArray().add(jsonObjectDataObject.toJson()));
    json.put("stringDataObjectValueSet", new JsonArray().add(stringDataObject.toJson()));
    json.put("bufferValueSet", new JsonArray().add(toBase64(buffer)));
    json.put("jsonObjectValueSet", new JsonArray().add(jsonObject));
    json.put("jsonArrayValueSet", new JsonArray().add(jsonArray));
    json.put("enumValueSet", new JsonArray().add(httpMethod.toString()));
    json.put("methodMappedValueSet", new JsonArray().add(methodMapped.toString()));
    json.put("objectValueSet", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("noConverterDataObjectValueSet", new JsonArray().add(new JsonObject()));
    json.put("noConverter2DataObjectValueSet", new JsonArray().add(new JsonObject()));
    json.put("customEnumValueSet", new JsonArray().add(enumMapped.getShortName()));
    json.put("autoMappedValueSet", new JsonArray().add(autoMapped.toJson()));
    json.put("autoMappedWithVertxGenValueSet", new JsonArray().add(autoMappedWithVertxGen.toJson()));
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
    json.put("addedJsonObjectDataObjectValues", new JsonArray().add(jsonObjectDataObject.toJson()));
    json.put("addedStringDataObjectValues", new JsonArray().add(stringDataObject.toJson()));
    json.put("addedBufferValues", new JsonArray().add(toBase64(buffer)));
    json.put("addedJsonObjectValues", new JsonArray().add(jsonObject));
    json.put("addedJsonArrayValues", new JsonArray().add(jsonArray));
    json.put("addedEnumValues", new JsonArray().add(httpMethod.toString()));
    json.put("addedMethodMappedValues", new JsonArray().add(methodMapped.toString()));
    json.put("addedObjectValues", new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)));
    json.put("addedNoConverterDataObjectValues", new JsonArray().add(new JsonObject()));
    json.put("addedNoConverter2DataObjectValues", new JsonArray().add(new JsonObject()));
    json.put("addedCustomEnumValues", new JsonArray().add(enumMapped.getShortName()));
    json.put("addedAutoMappedValues", new JsonArray().add(autoMapped.toJson()));
    json.put("addedAutoMappedWithVertxGenValues", new JsonArray().add(autoMappedWithVertxGen.toJson()));
    json.put("stringValueMap", new JsonObject().put(key, stringValue));
    json.put("boxedBooleanValueMap", new JsonObject().put(key, boxedBooleanValue));
    json.put("boxedByteValueMap", new JsonObject().put(key, boxedByteValue));
    json.put("boxedShortValueMap", new JsonObject().put(key, boxedShortValue));
    json.put("boxedIntValueMap", new JsonObject().put(key, boxedIntValue));
    json.put("boxedLongValueMap", new JsonObject().put(key, boxedLongValue));
    json.put("boxedFloatValueMap", new JsonObject().put(key, boxedFloatValue));
    json.put("boxedDoubleValueMap", new JsonObject().put(key, boxedDoubleValue));
    json.put("boxedCharValueMap", new JsonObject().put(key, Character.toString(boxedCharValue)));
    json.put("jsonObjectDataObjectValueMap", new JsonObject().put(key, jsonObjectDataObject.toJson()));
    json.put("stringDataObjectValueMap", new JsonObject().put(key, stringDataObject.toJson()));
    json.put("bufferValueMap", new JsonObject().put(key, toBase64(buffer)));
    json.put("jsonObjectValueMap", new JsonObject().put(key, jsonObject));
    json.put("jsonArrayValueMap", new JsonObject().put(key, jsonArray));
    json.put("enumValueMap", new JsonObject().put(key, httpMethod.toString()));
    json.put("methodMappedValueMap", new JsonObject().put(key, methodMapped.toString()));
    json.put("objectValueMap", toJson(map));
    json.put("noConverterDataObjectValueMap", new JsonObject().put(key, new JsonObject()));
    json.put("noConverter2DataObjectValueMap", new JsonObject().put(key, new JsonObject()));
    json.put("customEnumValueMap", new JsonObject().put(key, enumMapped.getShortName()));
    json.put("autoMappedValueMap", new JsonObject().put(key, autoMapped.toJson()));
    json.put("autoMappedWithVertxGenValueMap", new JsonObject().put(key, autoMapped.toJson()));
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
    json.put("keyedNoConverterDataObjectValues", new JsonObject());
    json.put("keyedNoConverter2DataObjectValues", new JsonObject());
    json.put("keyedCustomEnumValues", new JsonObject().put(key, enumMapped.getShortName()));
    json.put("keyedAutoMappedValues", new JsonObject().put(key, autoMapped.toJson()));
    json.put("keyedAutoMappedWithVertxGenValues", new JsonObject().put(key, autoMapped.toJson()));

    TestDataObject obj = new TestDataObject();
    TestDataObjectConverter.fromJson(json, obj);

    Assert.assertEquals(stringValue, obj.getStringValue());
    Assert.assertEquals(booleanValue, obj.isPrimitiveBooleanValue());
    Assert.assertEquals(byteValue, obj.getPrimitiveByteValue());
    Assert.assertEquals(shortValue, obj.getPrimitiveShortValue());
    Assert.assertEquals(intValue, obj.getPrimitiveIntValue());
    Assert.assertEquals(longValue, obj.getPrimitiveLongValue());
    Assert.assertEquals(floatValue, obj.getPrimitiveFloatValue(), 0);
    Assert.assertEquals(doubleValue, obj.getPrimitiveDoubleValue(), 0);
    Assert.assertEquals(charValue, obj.getPrimitiveCharValue());
    Assert.assertEquals(boxedBooleanValue, obj.isBoxedBooleanValue());
    Assert.assertEquals(boxedByteValue, obj.getBoxedByteValue());
    Assert.assertEquals(boxedShortValue, obj.getBoxedShortValue());
    Assert.assertEquals(boxedIntValue, obj.getBoxedIntValue());
    Assert.assertEquals(boxedLongValue, obj.getBoxedLongValue());
    Assert.assertEquals(boxedFloatValue, obj.getBoxedFloatValue(), 0);
    Assert.assertEquals(boxedDoubleValue, obj.getBoxedDoubleValue(), 0);
    Assert.assertEquals(boxedCharValue, obj.getBoxedCharValue());
    assertEquals(jsonObjectDataObject, obj.getJsonObjectDataObjectValue());
    assertEquals(stringDataObject, obj.getStringDataObjectValue());
    Assert.assertEquals(buffer, obj.getBufferValue());
    Assert.assertEquals(jsonObject, obj.getJsonObjectValue());
    Assert.assertEquals(jsonArray, obj.getJsonArrayValue());
    Assert.assertEquals(httpMethod, obj.getEnumValue());
    Assert.assertEquals(methodMapped, obj.getMethodMappedValue());
    Assert.assertNotNull(obj.getNoConverterDataObjectValue());
    Assert.assertNull(obj.getNoConverter2DataObjectValue());
    Assert.assertEquals(enumMapped, obj.getCustomEnumValue());
    Assert.assertEquals(autoMapped.toJson(), obj.getAutoMappedValue().toJson());
    Assert.assertEquals(autoMappedWithVertxGen.toJson(), obj.getAutoMappedWithVertxGenValue().toJson());
    Assert.assertEquals(Collections.singletonList(stringValue), obj.getStringValueList());
    Assert.assertEquals(Collections.singletonList(boxedBooleanValue), obj.getBoxedBooleanValueList());
    Assert.assertEquals(Collections.singletonList(boxedByteValue), obj.getBoxedByteValueList());
    Assert.assertEquals(Collections.singletonList(boxedShortValue), obj.getBoxedShortValueList());
    Assert.assertEquals(Collections.singletonList(boxedIntValue), obj.getBoxedIntValueList());
    Assert.assertEquals(Collections.singletonList(boxedLongValue), obj.getBoxedLongValueList());
    Assert.assertEquals(Collections.singletonList(boxedFloatValue), obj.getBoxedFloatValueList());
    Assert.assertEquals(Collections.singletonList(boxedDoubleValue), obj.getBoxedDoubleValueList());
    Assert.assertEquals(Collections.singletonList(boxedCharValue), obj.getBoxedCharValueList());
    Assert.assertEquals(Collections.singletonList(jsonObjectDataObject), obj.getJsonObjectDataObjectValueList());
    Assert.assertEquals(Collections.singletonList(stringDataObject), obj.getStringDataObjectValueList());
    Assert.assertEquals(Collections.singletonList(buffer), obj.getBufferValueList());
    Assert.assertEquals(Collections.singletonList(jsonObject), obj.getJsonObjectValueList());
    Assert.assertEquals(Collections.singletonList(jsonArray), obj.getJsonArrayValueList());
    Assert.assertEquals(Collections.singletonList(httpMethod), obj.getEnumValueList());
    Assert.assertEquals(Collections.singletonList(methodMapped), obj.getMethodMappedValueList());
    Assert.assertEquals(list, obj.getObjectValueList());
    Assert.assertEquals(1, obj.getNoConverterDataObjectValueList().size());
    Assert.assertNull(obj.getNoConverter2DataObjectValueList());
    Assert.assertEquals(Collections.singletonList(enumMapped), obj.getCustomEnumValueList());
    Assert.assertEquals(Collections.singletonList(autoMapped), obj.getAutoMappedValueList());
    Assert.assertEquals(Collections.singletonList(autoMappedWithVertxGen), obj.getAutoMappedWithVertxGenValueList());
    Assert.assertEquals(Collections.singleton(stringValue), obj.getStringValueSet());
    Assert.assertEquals(Collections.singleton(boxedBooleanValue), obj.getBoxedBooleanValueSet());
    Assert.assertEquals(Collections.singleton(boxedByteValue), obj.getBoxedByteValueSet());
    Assert.assertEquals(Collections.singleton(boxedShortValue), obj.getBoxedShortValueSet());
    Assert.assertEquals(Collections.singleton(boxedIntValue), obj.getBoxedIntValueSet());
    Assert.assertEquals(Collections.singleton(boxedLongValue), obj.getBoxedLongValueSet());
    Assert.assertEquals(Collections.singleton(boxedFloatValue), obj.getBoxedFloatValueSet());
    Assert.assertEquals(Collections.singleton(boxedDoubleValue), obj.getBoxedDoubleValueSet());
    Assert.assertEquals(Collections.singleton(boxedCharValue), obj.getBoxedCharValueSet());
    Assert.assertEquals(Collections.singleton(jsonObjectDataObject), obj.getJsonObjectDataObjectValueSet());
    Assert.assertEquals(Collections.singleton(stringDataObject), obj.getStringDataObjectValueSet());
    Assert.assertEquals(Collections.singleton(buffer), obj.getBufferValueSet());
    Assert.assertEquals(Collections.singleton(jsonObject), obj.getJsonObjectValueSet());
    Assert.assertEquals(Collections.singleton(jsonArray), obj.getJsonArrayValueSet());
    Assert.assertEquals(Collections.singleton(httpMethod), obj.getEnumValueSet());
    Assert.assertEquals(Collections.singleton(methodMapped), obj.getMethodMappedValueSet());
    Assert.assertEquals(new LinkedHashSet<>(list), obj.getObjectValueSet());
    Assert.assertEquals(1, obj.getNoConverterDataObjectValueSet().size());
    Assert.assertNull(obj.getNoConverter2DataObjectValueSet());
    Assert.assertEquals(Collections.singleton(enumMapped), obj.getCustomEnumValueSet());
    Assert.assertEquals(Collections.singleton(autoMapped), obj.getAutoMappedValueSet());
    Assert.assertEquals(Collections.singleton(autoMappedWithVertxGen), obj.getAutoMappedWithVertxGenValueSet());
    Assert.assertEquals(Collections.singletonList(stringValue), obj.getAddedStringValues());
    Assert.assertEquals(Collections.singletonList(boxedBooleanValue), obj.getAddedBoxedBooleanValues());
    Assert.assertEquals(Collections.singletonList(boxedByteValue), obj.getAddedBoxedByteValues());
    Assert.assertEquals(Collections.singletonList(boxedShortValue), obj.getAddedBoxedShortValues());
    Assert.assertEquals(Collections.singletonList(boxedIntValue), obj.getAddedBoxedIntValues());
    Assert.assertEquals(Collections.singletonList(boxedLongValue), obj.getAddedBoxedLongValues());
    Assert.assertEquals(Collections.singletonList(boxedFloatValue), obj.getAddedBoxedFloatValues());
    Assert.assertEquals(Collections.singletonList(boxedDoubleValue), obj.getAddedBoxedDoubleValues());
    Assert.assertEquals(Collections.singletonList(boxedCharValue), obj.getAddedBoxedCharValues());
    Assert.assertEquals(Collections.singletonList(jsonObjectDataObject), obj.getAddedJsonObjectDataObjectValues());
    Assert.assertEquals(Collections.singletonList(stringDataObject), obj.getAddedStringDataObjectValues());
    Assert.assertEquals(Collections.singletonList(buffer), obj.getAddedBufferValues());
    Assert.assertEquals(Collections.singletonList(jsonObject), obj.getAddedJsonObjectValues());
    Assert.assertEquals(Collections.singletonList(jsonArray), obj.getAddedJsonArrayValues());
    Assert.assertEquals(Collections.singletonList(httpMethod), obj.getAddedEnumValues());
    Assert.assertEquals(Collections.singletonList(methodMapped), obj.getAddedMethodMappedValues());
    Assert.assertEquals(list, obj.getAddedObjectValues());
    Assert.assertEquals(1, obj.getAddedNoConverterDataObjectValues().size());
    Assert.assertEquals(0, obj.getAddedNoConverter2DataObjectValues().size());
    Assert.assertEquals(Collections.singletonList(enumMapped), obj.getAddedCustomEnumValues());
    Assert.assertEquals(Collections.singletonList(autoMapped), obj.getAddedAutoMappedValues());
    Assert.assertEquals(Collections.singletonList(autoMappedWithVertxGen), obj.getAddedAutoMappedWithVertxGenValues());
    Assert.assertEquals(Collections.singletonMap(key, stringValue), obj.getStringValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedBooleanValue), obj.getBoxedBooleanValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedByteValue), obj.getBoxedByteValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedShortValue), obj.getBoxedShortValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedIntValue), obj.getBoxedIntValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedLongValue), obj.getBoxedLongValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedFloatValue), obj.getBoxedFloatValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedDoubleValue), obj.getBoxedDoubleValueMap());
    Assert.assertEquals(Collections.singletonMap(key, boxedCharValue), obj.getBoxedCharValueMap());
    Assert.assertEquals(Collections.singletonMap(key, jsonObjectDataObject), obj.getJsonObjectDataObjectValueMap());
    Assert.assertEquals(Collections.singletonMap(key, stringDataObject), obj.getStringDataObjectValueMap());
    Assert.assertEquals(Collections.singletonMap(key, buffer), obj.getBufferValueMap());
    Assert.assertEquals(Collections.singletonMap(key, jsonObject), obj.getJsonObjectValueMap());
    Assert.assertEquals(Collections.singletonMap(key, jsonArray), obj.getJsonArrayValueMap());
    Assert.assertEquals(Collections.singletonMap(key, httpMethod), obj.getEnumValueMap());
    Assert.assertEquals(Collections.singletonMap(key, methodMapped), obj.getMethodMappedValueMap());
    Assert.assertEquals(map, obj.getObjectValueMap());
    Assert.assertEquals(1, obj.getNoConverterDataObjectValueMap().size());
    Assert.assertNull(obj.getNoConverter2DataObjectValueMap());
    Assert.assertEquals(Collections.singletonMap(key, enumMapped), obj.getCustomEnumValueMap());
    Assert.assertEquals(Collections.singletonMap(key, autoMapped), obj.getAutoMappedValueMap());
    Assert.assertEquals(Collections.singletonMap(key, autoMappedWithVertxGen), obj.getAutoMappedWithVertxGenValueMap());
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
    Assert.assertEquals(map, obj.getObjectValueMap());
    Assert.assertEquals(1, obj.getNoConverterDataObjectValueMap().size());
    Assert.assertNull(obj.getNoConverter2DataObjectValueMap());
    Assert.assertEquals(Collections.singletonMap(key, enumMapped), obj.getKeyedCustomEnumValues());
    Assert.assertEquals(Collections.singletonMap(key, autoMapped), obj.getKeyedAutoMappedValues());
    Assert.assertEquals(Collections.singletonMap(key, autoMappedWithVertxGen), obj.getKeyedAutoMappedWithVertxGenValues());

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

    Assert.assertEquals(null, obj.getStringValue());
    Assert.assertEquals(false, obj.isPrimitiveBooleanValue());
    Assert.assertEquals(0, obj.getPrimitiveByteValue());
    Assert.assertEquals(0, obj.getPrimitiveShortValue());
    Assert.assertEquals(0, obj.getPrimitiveIntValue());
    Assert.assertEquals(0l, obj.getPrimitiveLongValue());
    Assert.assertEquals(0f, obj.getPrimitiveFloatValue(), 0);
    Assert.assertEquals(0d, obj.getPrimitiveDoubleValue(), 0);
    Assert.assertEquals((char)0, obj.getPrimitiveCharValue());
    Assert.assertEquals(null, obj.isBoxedBooleanValue());
    Assert.assertEquals(null, obj.getBoxedByteValue());
    Assert.assertEquals(null, obj.getBoxedShortValue());
    Assert.assertEquals(null, obj.getBoxedIntValue());
    Assert.assertEquals(null, obj.getBoxedLongValue());
    Assert.assertEquals(null, obj.getBoxedFloatValue());
    Assert.assertEquals(null, obj.getBoxedDoubleValue());
    Assert.assertEquals(null, obj.getBoxedCharValue());
    assertEquals(null, obj.getJsonObjectDataObjectValue());
    assertEquals(null, obj.getStringDataObjectValue());
    Assert.assertEquals(null, obj.getBufferValue());
    Assert.assertEquals(null, obj.getJsonObjectValue());
    Assert.assertEquals(null, obj.getJsonArrayValue());
    Assert.assertEquals(null, obj.getMethodMappedValue());
    Assert.assertEquals(null, obj.getNoConverterDataObjectValue());
    Assert.assertEquals(null, obj.getNoConverter2DataObjectValue());
    Assert.assertEquals(null, obj.getStringValueList());
    Assert.assertEquals(null, obj.getCustomEnumValue());
    Assert.assertEquals(null, obj.getBoxedBooleanValueList());
    Assert.assertEquals(null, obj.getBoxedByteValueList());
    Assert.assertEquals(null, obj.getBoxedShortValueList());
    Assert.assertEquals(null, obj.getBoxedIntValueList());
    Assert.assertEquals(null, obj.getBoxedLongValueList());
    Assert.assertEquals(null, obj.getBoxedFloatValueList());
    Assert.assertEquals(null, obj.getBoxedDoubleValueList());
    Assert.assertEquals(null, obj.getBoxedCharValueList());
    Assert.assertEquals(null, obj.getJsonObjectDataObjectValueList());
    Assert.assertEquals(null, obj.getStringDataObjectValueList());
    Assert.assertEquals(null, obj.getBufferValueList());
    Assert.assertEquals(null, obj.getJsonObjectValueList());
    Assert.assertEquals(null, obj.getJsonArrayValueList());
    Assert.assertEquals(null, obj.getEnumValueList());
    Assert.assertEquals(null, obj.getMethodMappedValueList());
    Assert.assertEquals(null, obj.getObjectValueList());
    Assert.assertEquals(null, obj.getNoConverterDataObjectValueList());
    Assert.assertEquals(null, obj.getNoConverter2DataObjectValueList());
    Assert.assertEquals(null, obj.getCustomEnumValueList());
    Assert.assertEquals(null, obj.getStringValueSet());
    Assert.assertEquals(null, obj.getBoxedBooleanValueSet());
    Assert.assertEquals(null, obj.getBoxedByteValueSet());
    Assert.assertEquals(null, obj.getBoxedShortValueSet());
    Assert.assertEquals(null, obj.getBoxedIntValueSet());
    Assert.assertEquals(null, obj.getBoxedLongValueSet());
    Assert.assertEquals(null, obj.getBoxedFloatValueSet());
    Assert.assertEquals(null, obj.getBoxedDoubleValueSet());
    Assert.assertEquals(null, obj.getBoxedCharValueSet());
    Assert.assertEquals(null, obj.getJsonObjectDataObjectValueSet());
    Assert.assertEquals(null, obj.getStringDataObjectValueSet());
    Assert.assertEquals(null, obj.getBufferValueSet());
    Assert.assertEquals(null, obj.getJsonObjectValueSet());
    Assert.assertEquals(null, obj.getJsonArrayValueSet());
    Assert.assertEquals(null, obj.getEnumValueSet());
    Assert.assertEquals(null, obj.getMethodMappedValueSet());
    Assert.assertEquals(null, obj.getObjectValueSet());
    Assert.assertEquals(null, obj.getNoConverterDataObjectValueSet());
    Assert.assertEquals(null, obj.getNoConverter2DataObjectValueSet());
    Assert.assertEquals(null, obj.getCustomEnumValueSet());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedStringValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedBooleanValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedByteValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedShortValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedIntValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedLongValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedFloatValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedDoubleValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBoxedCharValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedJsonObjectDataObjectValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedStringDataObjectValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedBufferValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedJsonObjectValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedJsonArrayValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedEnumValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedMethodMappedValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedObjectValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedNoConverterDataObjectValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedNoConverter2DataObjectValues());
    Assert.assertEquals(Collections.emptyList(), obj.getAddedCustomEnumValues());
    Assert.assertEquals(null, obj.getStringValueMap());
    Assert.assertEquals(null, obj.getBoxedBooleanValueMap());
    Assert.assertEquals(null, obj.getBoxedByteValueMap());
    Assert.assertEquals(null, obj.getBoxedShortValueMap());
    Assert.assertEquals(null, obj.getBoxedIntValueMap());
    Assert.assertEquals(null, obj.getBoxedLongValueMap());
    Assert.assertEquals(null, obj.getBoxedFloatValueMap());
    Assert.assertEquals(null, obj.getBoxedDoubleValueMap());
    Assert.assertEquals(null, obj.getBoxedCharValueMap());
    Assert.assertEquals(null, obj.getJsonObjectDataObjectValueMap());
    Assert.assertEquals(null, obj.getStringDataObjectValueMap());
    Assert.assertEquals(null, obj.getBufferValueMap());
    Assert.assertEquals(null, obj.getJsonObjectValueMap());
    Assert.assertEquals(null, obj.getJsonArrayValueMap());
    Assert.assertEquals(null, obj.getEnumValueMap());
    Assert.assertEquals(null, obj.getMethodMappedValueMap());
    Assert.assertEquals(null, obj.getObjectValueMap());
    Assert.assertEquals(null, obj.getNoConverterDataObjectValueMap());
    Assert.assertEquals(null, obj.getNoConverter2DataObjectValueMap());
    Assert.assertEquals(null, obj.getCustomEnumValueMap());
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
    AutoMapped autoMapped = AutoMapped.of("vertx.io", 80);
    AutoMappedWithVertxGen autoMappedWithVertxGen = AutoMappedWithVertxGen.of("vertx.io", 80);

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
    obj.setPrimitiveBooleanValue(booleanValue);
    obj.setPrimitiveByteValue(byteValue);
    obj.setPrimitiveShortValue(shortValue);
    obj.setPrimitiveIntValue(intValue);
    obj.setPrimitiveLongValue(longValue);
    obj.setPrimitiveFloatValue(floatValue);
    obj.setPrimitiveDoubleValue(doubleValue);
    obj.setPrimitiveCharValue(charValue);
    obj.setBoxedBooleanValue(boxedBooleanValue);
    obj.setBoxedByteValue(boxedByteValue);
    obj.setBoxedShortValue(boxedShortValue);
    obj.setBoxedIntValue(boxedIntValue);
    obj.setBoxedLongValue(boxedLongValue);
    obj.setBoxedFloatValue(boxedFloatValue);
    obj.setBoxedDoubleValue(boxedDoubleValue);
    obj.setBoxedCharValue(boxedCharValue);
    obj.setJsonObjectDataObjectValue(jsonObjectDataObject);
    obj.setStringDataObjectValue(stringDataObject);
    obj.setBufferValue(buffer);
    obj.setJsonObjectValue(jsonObject);
    obj.setJsonArrayValue(jsonArray);
    obj.setEnumValue(httpMethod);
    obj.setMethodMappedValue(dateTime);
    obj.setNoConverterDataObjectValue(new NoConverterDataObject());
    obj.setNoConverter2DataObjectValue(new NoConverter2DataObject());
    obj.setCustomEnumValue(testCustomEnum);
    obj.setAutoMappedValue(autoMapped);
    obj.setAutoMappedWithVertxGenValue(autoMappedWithVertxGen);
    obj.setStringValueList(Collections.singletonList(stringValue));
    obj.setBoxedBooleanValueList(Collections.singletonList(boxedBooleanValue));
    obj.setBoxedByteValueList(Collections.singletonList(boxedByteValue));
    obj.setBoxedShortValueList(Collections.singletonList(boxedShortValue));
    obj.setBoxedIntValueList(Collections.singletonList(boxedIntValue));
    obj.setBoxedLongValueList(Collections.singletonList(boxedLongValue));
    obj.setBoxedFloatValueList(Collections.singletonList(boxedFloatValue));
    obj.setBoxedDoubleValueList(Collections.singletonList(boxedDoubleValue));
    obj.setBoxedCharValueList(Collections.singletonList(boxedCharValue));
    obj.setJsonObjectDataObjectValueList(Collections.singletonList(jsonObjectDataObject));
    obj.setStringDataObjectValueList(Collections.singletonList(stringDataObject));
    obj.setBufferValueList(Collections.singletonList(buffer));
    obj.setJsonObjectValueList(Collections.singletonList(jsonObject));
    obj.setJsonArrayValueList(Collections.singletonList(jsonArray));
    obj.setEnumValueList(Collections.singletonList(httpMethod));
    obj.setMethodMappedValueList(Collections.singletonList(dateTime));
    obj.setObjectValueList(list);
    obj.setNoConverterDataObjectValueList(Collections.singletonList(new NoConverterDataObject()));
    obj.setNoConverter2DataObjectValueList(Collections.singletonList(new NoConverter2DataObject()));
    obj.setCustomEnumValueList(Collections.singletonList(testCustomEnum));
    obj.setAutoMappedValueList(Collections.singletonList(autoMapped));
    obj.setAutoMappedWithVertxGenValueList(Collections.singletonList(autoMappedWithVertxGen));
    obj.setStringValueMap(Collections.singletonMap(key, stringValue));
    obj.setStringValueSet(Collections.singleton(stringValue));
    obj.setBoxedBooleanValueSet(Collections.singleton(boxedBooleanValue));
    obj.setBoxedByteValueSet(Collections.singleton(boxedByteValue));
    obj.setBoxedShortValueSet(Collections.singleton(boxedShortValue));
    obj.setBoxedIntValueSet(Collections.singleton(boxedIntValue));
    obj.setBoxedLongValueSet(Collections.singleton(boxedLongValue));
    obj.setBoxedFloatValueSet(Collections.singleton(boxedFloatValue));
    obj.setBoxedDoubleValueSet(Collections.singleton(boxedDoubleValue));
    obj.setBoxedCharValueSet(Collections.singleton(boxedCharValue));
    obj.setJsonObjectDataObjectValueSet(Collections.singleton(jsonObjectDataObject));
    obj.setStringDataObjectValueSet(Collections.singleton(stringDataObject));
    obj.setBufferValueSet(Collections.singleton(buffer));
    obj.setJsonObjectValueSet(Collections.singleton(jsonObject));
    obj.setJsonArrayValueSet(Collections.singleton(jsonArray));
    obj.setEnumValueSet(Collections.singleton(httpMethod));
    obj.setMethodMappedValueSet(Collections.singleton(dateTime));
    obj.setObjectValueSet(new LinkedHashSet<>(list));
    obj.setNoConverterDataObjectValueSet(Collections.singleton(new NoConverterDataObject()));
    obj.setNoConverter2DataObjectValueSet(Collections.singleton(new NoConverter2DataObject()));
    obj.setCustomEnumValueSet(Collections.singleton(testCustomEnum));
    obj.setAutoMappedValueSet(Collections.singleton(autoMapped));
    obj.setAutoMappedWithVertxGenValueSet(Collections.singleton(autoMappedWithVertxGen));
    obj.setBoxedBooleanValueMap(Collections.singletonMap(key, boxedBooleanValue));
    obj.setBoxedByteValueMap(Collections.singletonMap(key, boxedByteValue));
    obj.setBoxedShortValueMap(Collections.singletonMap(key, boxedShortValue));
    obj.setBoxedIntValueMap(Collections.singletonMap(key, boxedIntValue));
    obj.setBoxedLongValueMap(Collections.singletonMap(key, boxedLongValue));
    obj.setBoxedFloatValueMap(Collections.singletonMap(key, boxedFloatValue));
    obj.setBoxedDoubleValueMap(Collections.singletonMap(key, boxedDoubleValue));
    obj.setBoxedCharValueMap(Collections.singletonMap(key, boxedCharValue));
    obj.setJsonObjectDataObjectValueMap(Collections.singletonMap(key, jsonObjectDataObject));
    obj.setStringDataObjectValueMap(Collections.singletonMap(key, stringDataObject));
    obj.setBufferValueMap(Collections.singletonMap(key, buffer));
    obj.setJsonObjectValueMap(Collections.singletonMap(key, jsonObject));
    obj.setJsonArrayValueMap(Collections.singletonMap(key, jsonArray));
    obj.setEnumValueMap(Collections.singletonMap(key, httpMethod));
    obj.setMethodMappedValueMap(Collections.singletonMap(key, dateTime));
    obj.setObjectValueMap(map);
    obj.setNoConverterDataObjectValueMap(Collections.singletonMap(key, new NoConverterDataObject()));
    obj.setNoConverter2DataObjectValueMap(Collections.singletonMap(key, new NoConverter2DataObject()));
    obj.setCustomEnumValueMap(Collections.singletonMap(key, testCustomEnum));
    obj.setAutoMappedValueMap(Collections.singletonMap(key, autoMapped));
    obj.setAutoMappedWithVertxGenValueMap(Collections.singletonMap(key, autoMappedWithVertxGen));
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
    obj.addKeyedNoConverterDataObjectValue(key, new NoConverterDataObject());
    obj.addKeyedNoConverter2DataObjectValue(key, new NoConverter2DataObject());
    obj.addKeyedCustomEnumValue(key, testCustomEnum);
    obj.addKeyedAutoMappedValue(key, autoMapped);
    obj.addKeyedAutoMappedWithVertxGenValue(key, autoMappedWithVertxGen);

    Map<String, Object> json = new HashMap<>();
    TestDataObjectConverter.toJson(obj, json);

    assertEquals(stringValue, json.get("stringValue"));
    assertEquals(booleanValue, json.get("primitiveBooleanValue"));
    assertEquals(byteValue, (byte)json.get("primitiveByteValue"));
    assertEquals(shortValue, (short)json.get("primitiveShortValue"));
    assertEquals(intValue, (int)json.get("primitiveIntValue"));
    assertEquals(longValue, (long)json.get("primitiveLongValue"));
    assertEquals(floatValue, (float)json.get("primitiveFloatValue"), 0.001);
    assertEquals(doubleValue, (double)json.get("primitiveDoubleValue"), 0.001);
    assertEquals(Character.toString(charValue), json.get("primitiveCharValue"));
    assertEquals(boxedBooleanValue, json.get("boxedBooleanValue"));
    assertEquals((byte)boxedByteValue, (byte)json.get("boxedByteValue"));
    assertEquals((short)boxedShortValue, (short)json.get("boxedShortValue"));
    assertEquals(boxedIntValue, json.get("boxedIntValue"));
    assertEquals(boxedLongValue, json.get("boxedLongValue"));
    assertEquals(boxedFloatValue, (float)json.get("boxedFloatValue"), 0.001);
    assertEquals(boxedDoubleValue, (double) json.get("boxedDoubleValue"), 0.001);
    assertEquals(Character.toString(boxedCharValue), json.get("boxedCharValue"));
    assertEquals(jsonObjectDataObject.toJson(), json.get("jsonObjectDataObjectValue"));
    assertEquals(stringDataObject.toJson(), json.get("stringDataObjectValue"));
    assertEquals(buffer, Buffer.buffer(JsonUtil.BASE64_DECODER.decode((String)json.get("bufferValue"))));
    assertEquals(jsonObject, json.get("jsonObjectValue"));
    assertEquals(jsonArray, json.get("jsonArrayValue"));
    assertEquals(httpMethod.name(), json.get("enumValue"));
    assertEquals(dateTime.toString(), json.get("methodMappedValue"));
    assertNull(json.get("noConverterDataObjectValue"));
    assertEquals(new JsonObject(), json.get("noConverter2DataObjectValue"));
    assertEquals(testCustomEnum.getShortName(), json.get("customEnumValue"));
    assertEquals(autoMapped.toJson(), json.get("autoMappedValue"));
    assertEquals(autoMappedWithVertxGen.toJson(), json.get("autoMappedWithVertxGenValue"));
    assertEquals(new JsonArray().add(stringValue), json.get("stringValueList"));
    assertEquals(new JsonArray().add(boxedBooleanValue), json.get("boxedBooleanValueList"));
    assertEquals(new JsonArray().add(boxedByteValue), json.get("boxedByteValueList"));
    assertEquals(new JsonArray().add(boxedShortValue), json.get("boxedShortValueList"));
    assertEquals(new JsonArray().add(boxedIntValue), json.get("boxedIntValueList"));
    assertEquals(new JsonArray().add(boxedLongValue), json.get("boxedLongValueList"));
    assertEquals(1, ((JsonArray)json.get("boxedFloatValueList")).size());
    assertEquals(boxedFloatValue, (float)((JsonArray)json.get("boxedFloatValueList")).getValue(0), 0.001);
    assertEquals(1, ((JsonArray)json.get("boxedDoubleValueList")).size());
    assertEquals(boxedDoubleValue, (double)((JsonArray)json.get("boxedDoubleValueList")).getValue(0), 0.001);
    assertEquals(new JsonArray().add(Character.toString(boxedCharValue)), json.get("boxedCharValueList"));
    assertEquals(new JsonArray().add(jsonObjectDataObject.toJson()), json.get("jsonObjectDataObjectValueList"));
    assertEquals(new JsonArray().add(stringDataObject.toJson()), json.get("stringDataObjectValueList"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonArray)json.get("bufferValueList")).getValue(0));
    assertEquals(new JsonArray().add(jsonObject), json.get("jsonObjectValueList"));
    assertEquals(new JsonArray().add(jsonArray), json.get("jsonArrayValueList"));
    assertEquals(new JsonArray().add(httpMethod.name()), json.get("enumValueList"));
    assertEquals(new JsonArray().add(dateTime.toString()), json.get("methodMappedValueList"));
    assertEquals(new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)), json.get("objectValueList"));
    assertNull(json.get("noConverterDataObjectValueList"));
    assertEquals(new JsonArray().add(new JsonObject()), json.get("noConverter2DataObjectValueList"));
    assertEquals(new JsonArray().add(testCustomEnum.getShortName()), json.get("customEnumValueList"));
    assertEquals(new JsonArray().add(autoMapped.toJson()), json.get("autoMappedValueList"));
    assertEquals(new JsonArray().add(autoMappedWithVertxGen.toJson()), json.get("autoMappedWithVertxGenValueList"));
    assertEquals(new JsonArray().add(stringValue), json.get("stringValueSet"));
    assertEquals(new JsonArray().add(boxedBooleanValue), json.get("boxedBooleanValueSet"));
    assertEquals(new JsonArray().add(boxedByteValue), json.get("boxedByteValueSet"));
    assertEquals(new JsonArray().add(boxedShortValue), json.get("boxedShortValueSet"));
    assertEquals(new JsonArray().add(boxedIntValue), json.get("boxedIntValueSet"));
    assertEquals(new JsonArray().add(boxedLongValue), json.get("boxedLongValueSet"));
    assertEquals(1, ((JsonArray)json.get("boxedFloatValueSet")).size());
    assertEquals(boxedFloatValue, (float)((JsonArray)json.get("boxedFloatValueSet")).getValue(0), 0.001);
    assertEquals(1, ((JsonArray)json.get("boxedDoubleValueSet")).size());
    assertEquals(boxedDoubleValue, (double)((JsonArray)json.get("boxedDoubleValueSet")).getValue(0), 0.001);
    assertEquals(new JsonArray().add(Character.toString(boxedCharValue)), json.get("boxedCharValueSet"));
    assertEquals(new JsonArray().add(jsonObjectDataObject.toJson()), json.get("jsonObjectDataObjectValueSet"));
    assertEquals(new JsonArray().add(stringDataObject.toJson()), json.get("stringDataObjectValueSet"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonArray)json.get("bufferValueSet")).getValue(0));
    assertEquals(new JsonArray().add(jsonObject), json.get("jsonObjectValueSet"));
    assertEquals(new JsonArray().add(jsonArray), json.get("jsonArrayValueSet"));
    assertEquals(new JsonArray().add(httpMethod.name()), json.get("enumValueSet"));
    assertEquals(new JsonArray().add(dateTime.toString()), json.get("methodMappedValueSet"));
    assertEquals(new JsonArray().add(list.get(0)).add(list.get(1)).add(list.get(2)), json.get("objectValueSet"));
    assertNull(json.get("noConverterDataObjectValueSet"));
    assertEquals(new JsonArray().add(new JsonObject()), json.get("noConverter2DataObjectValueSet"));
    assertEquals(new JsonArray().add(testCustomEnum.getShortName()), json.get("customEnumValueSet"));
    assertEquals(new JsonArray().add(autoMapped.toJson()), json.get("autoMappedValueSet"));
    assertEquals(new JsonArray().add(autoMappedWithVertxGen.toJson()), json.get("autoMappedWithVertxGenValueSet"));
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
    assertEquals(new JsonObject().put(key, jsonObjectDataObject.toJson()), json.get("jsonObjectDataObjectValueMap"));
    assertEquals(new JsonObject().put(key, stringDataObject.toJson()), json.get("stringDataObjectValueMap"));
    assertEquals(JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes()), ((JsonObject)json.get("bufferValueMap")).getValue(key));
    assertEquals(new JsonObject().put(key, jsonObject), json.get("jsonObjectValueMap"));
    assertEquals(new JsonObject().put(key, jsonArray), json.get("jsonArrayValueMap"));
    assertEquals(new JsonObject().put(key, httpMethod.name()), json.get("enumValueMap"));
    assertEquals(new JsonObject().put(key, dateTime.toString()), json.get("methodMappedValueMap"));
    assertEquals(toJson(map), json.get("objectValueMap"));
    assertNull(json.get("noConverterDataObjectValueMap"));
    assertEquals(new JsonObject().put(key, new JsonObject()), json.get("noConverter2DataObjectValueMap"));
    assertEquals(new JsonObject().put(key, testCustomEnum.getShortName()), json.get("customEnumValueMap"));
    assertEquals(new JsonObject().put(key, autoMapped.toJson()), json.get("autoMappedValueMap"));
    assertEquals(new JsonObject().put(key, autoMappedWithVertxGen.toJson()), json.get("autoMappedWithVertxGenValueMap"));
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
    assertNull(json.get("keyedNoConverterDataObjectValues"));
    assertEquals(new JsonObject().put(key, new JsonObject()), json.get("keyedNoConverter2DataObjectValues"));
    assertEquals(new JsonObject().put(key, testCustomEnum.getShortName()), json.get("keyedCustomEnumValues"));
    assertEquals(new JsonObject().put(key, autoMapped.toJson()), json.get("keyedAutoMappedValues"));
    assertEquals(new JsonObject().put(key, autoMappedWithVertxGen.toJson()), json.get("keyedAutoMappedWithVertxGenValues"));
  }

  @Test
  public void testEmptyDataObjectToJson() {

    TestDataObject obj = new TestDataObject();

    Map<String, Object> json = new HashMap<>();
    TestDataObjectConverter.toJson(obj, json);

    assertEquals(null, json.get("stringValue"));
    assertEquals(false, json.get("primitiveBooleanValue"));
    assertEquals(0, (byte)json.get("primitiveByteValue"));
    assertEquals(0, (short)json.get("primitiveShortValue"));
    assertEquals(0, (int)json.get("primitiveIntValue"));
    assertEquals(0L, (long) json.get("primitiveLongValue"));
    assertEquals(0f, (float) json.get("primitiveFloatValue"), 0);
    assertEquals(0d, (double) json.get("primitiveDoubleValue"), 0);
    assertEquals(Character.toString((char)0), json.get("primitiveCharValue"));
    assertEquals(null, json.get("boxedBooleanValue"));
    assertEquals(null, json.get("boxedByteValue"));
    assertEquals(null, json.get("boxedShortValue"));
    assertEquals(null, json.get("boxedIntValue"));
    assertEquals(null, json.get("boxedLongValue"));
    assertEquals(null, json.get("boxedFloatValue"));
    assertEquals(null, json.get("boxedDoubleValue"));
    assertEquals(null, json.get("boxedCharValue"));
    assertEquals(null, json.get("jsonObjectDataObjectValue"));
    assertEquals(null, json.get("stringDataObjectValue"));
    assertEquals(null, json.get("bufferValue"));
    assertEquals(null, json.get("jsonObjectValue"));
    assertEquals(null, json.get("jsonArrayValue"));
    assertEquals(null, json.get("enumValue"));
    assertEquals(null, json.get("methodMappedValue"));
    assertEquals(null, json.get("noConverterDataObjectValue"));
    assertEquals(null, json.get("noConverter2DataObjectValue"));
    assertEquals(null, json.get("customEnumValue"));
    assertEquals(null, json.get("autoMappedValue"));
    assertEquals(null, json.get("autoMappedWithVertxGenValue"));
    assertEquals(null, json.get("stringValueList"));
    assertEquals(null, json.get("boxedBooleanValueList"));
    assertEquals(null, json.get("boxedByteValueList"));
    assertEquals(null, json.get("boxedShortValueList"));
    assertEquals(null, json.get("boxedIntValueList"));
    assertEquals(null, json.get("boxedLongValueList"));
    assertEquals(null, json.get("boxedFloatValueList"));
    assertEquals(null, json.get("boxedDoubleValueList"));
    assertEquals(null, json.get("boxedCharValueList"));
    assertEquals(null, json.get("jsonObjectDataObjectValueList"));
    assertEquals(null, json.get("stringDataObjectValueList"));
    assertEquals(null, json.get("bufferValueList"));
    assertEquals(null, json.get("jsonObjectValueList"));
    assertEquals(null, json.get("jsonArrayValueList"));
    assertEquals(null, json.get("enumValueList"));
    assertEquals(null, json.get("methodMappedValueList"));
    assertEquals(null, json.get("objectValueList"));
    assertEquals(null, json.get("customEnumValueList"));
    assertEquals(null, json.get("autoMappedValueList"));
    assertEquals(null, json.get("autoMappedWithVertxGenValueList"));
    assertEquals(null, json.get("stringValueSet"));
    assertEquals(null, json.get("boxedBooleanValueSet"));
    assertEquals(null, json.get("boxedByteValueSet"));
    assertEquals(null, json.get("boxedShortValueSet"));
    assertEquals(null, json.get("boxedIntValueSet"));
    assertEquals(null, json.get("boxedLongValueSet"));
    assertEquals(null, json.get("boxedFloatValueSet"));
    assertEquals(null, json.get("boxedDoubleValueSet"));
    assertEquals(null, json.get("boxedCharValueSet"));
    assertEquals(null, json.get("jsonObjectDataObjectValueSet"));
    assertEquals(null, json.get("stringDataObjectValueSet"));
    assertEquals(null, json.get("bufferValueSet"));
    assertEquals(null, json.get("jsonObjectValueSet"));
    assertEquals(null, json.get("jsonArrayValueSet"));
    assertEquals(null, json.get("enumValueSet"));
    assertEquals(null, json.get("methodMappedValueSet"));
    assertEquals(null, json.get("objectValueSet"));
    assertEquals(null, json.get("noConverterDataObjectValueSet"));
    assertEquals(null, json.get("noConverter2DataObjectValueSet"));
    assertEquals(null, json.get("customEnumValueSet"));
    assertEquals(null, json.get("autoMappedValueSet"));
    assertEquals(null, json.get("autoMappedWithVertxGenValueSet"));
    assertEquals(new JsonArray(), json.get("addedStringValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedBooleanValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedByteValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedShortValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedIntValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedLongValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedFloatValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedDoubleValues"));
    assertEquals(new JsonArray(), json.get("addedBoxedCharValues"));
    assertEquals(new JsonArray(), json.get("addedStringDataObjectValues"));
    assertEquals(new JsonArray(), json.get("addedBufferValues"));
    assertEquals(new JsonArray(), json.get("addedJsonObjectValues"));
    assertEquals(new JsonArray(), json.get("addedJsonArrayValues"));
    assertEquals(new JsonArray(), json.get("addedEnumValues"));
    assertEquals(new JsonArray(), json.get("addedMethodMappedValues"));
    assertEquals(new JsonArray(), json.get("addedObjectValues"));
    assertEquals(null, json.get("addedNoConverterDataObjectValues"));
    assertEquals(new JsonArray(), json.get("addedNoConverter2DataObjectValues"));
    assertEquals(new JsonArray(), json.get("addedCustomEnumValues"));
    assertEquals(new JsonArray(), json.get("addedAutoMappedValues"));
    assertEquals(new JsonArray(), json.get("addedAutoMappedWithVertxGenValues"));
    assertEquals(null, json.get("stringValueMap"));
    assertEquals(null, json.get("boxedBooleanValueMap"));
    assertEquals(null, json.get("boxedByteValueMap"));
    assertEquals(null, json.get("boxedShortValueMap"));
    assertEquals(null, json.get("boxedIntValueMap"));
    assertEquals(null, json.get("boxedLongValueMap"));
    assertEquals(null, json.get("boxedFloatValueMap"));
    assertEquals(null, json.get("boxedDoubleValueMap"));
    assertEquals(null, json.get("boxedCharValueMap"));
    assertEquals(null, json.get("stringDataObjectValueMap"));
    assertEquals(null, json.get("bufferValueMap"));
    assertEquals(null, json.get("jsonObjectValueMap"));
    assertEquals(null, json.get("jsonArrayValueMap"));
    assertEquals(null, json.get("enumValueMap"));
    assertEquals(null, json.get("methodMappedValueMap"));
    assertEquals(null, json.get("objectValueMap"));
    assertEquals(null, json.get("noConverterDataObjectValueMap"));
    assertEquals(null, json.get("noConverter2DataObjectValueMap"));
    assertEquals(null, json.get("customEnumValueMap"));
    assertEquals(null, json.get("autoMappedValueMap"));
    assertEquals(null, json.get("autoMappedWithVertxGenValueMap"));
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
    return JsonUtil.BASE64_ENCODER.encodeToString(buffer.getBytes());
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
