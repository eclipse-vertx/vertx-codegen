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

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject(generateConverter = true)
public class TestDataObject {

  public static String serializeZonedDateTime(ZonedDateTime value) {
    return value.toString();
  }

  public static ZonedDateTime deserializeZonedDateTime(String value) {
    return ZonedDateTime.parse(value);
  }

  public static final Function<String, Locale> LOCALE_DESERIALIZER = Locale::new;
  public static final Function<Locale, String> LOCALE_SERIALIZER = Locale::toString;

  private String string;
  private boolean primitiveBoolean;
  private byte primitiveByte;
  private short primitiveShort;
  private int primitiveInt;
  private long primitiveLong;
  private float primitiveFloat;
  private double primitiveDouble;
  private char primitiveChar;
  private Boolean boxedBoolean;
  private Byte boxedByte;
  private Short boxedShort;
  private Integer boxedInt;
  private Long boxedLong;
  private Float boxedFloat;
  private Double boxedDouble;
  private Character boxedChar;
  private NestedJsonObjectDataObject jsonObjectDataObject;
  private NestedStringDataObject stringDataObject;
  private Buffer buffer;
  private JsonObject jsonObject;
  private JsonArray jsonArray;
  private TimeUnit httpMethod;
  private ZonedDateTime methodMapped;
  private Locale functionMapped;
  private NoConverterDataObject notConvertibleDataObject;
  private ByteBuffer unmapped;

  private List<String> stringList;
  private List<Boolean> boxedBooleanList;
  private List<Byte> boxedByteList;
  private List<Short> boxedShortList;
  private List<Integer> boxedIntList;
  private List<Long> boxedLongList;
  private List<Float> boxedFloatList;
  private List<Double> boxedDoubleList;
  private List<Character> boxedCharList;
  private List<NestedJsonObjectDataObject> jsonObjectDataObjectList;
  private List<NestedStringDataObject> stringDataObjectList;
  private List<Buffer> bufferList;
  private List<JsonObject> jsonObjectList;
  private List<JsonArray> jsonArrayList;
  private List<TimeUnit> httpMethodList;
  private List<ZonedDateTime> methodMappedList;
  private List<Locale> functionMappedList;
  private List<Object> objectList;
  private List<NoConverterDataObject> notConvertibleDataObjectList;

  private Set<String> stringSet;
  private Set<Boolean> boxedBooleanSet;
  private Set<Byte> boxedByteSet;
  private Set<Short> boxedShortSet;
  private Set<Integer> boxedIntSet;
  private Set<Long> boxedLongSet;
  private Set<Float> boxedFloatSet;
  private Set<Double> boxedDoubleSet;
  private Set<Character> boxedCharSet;
  private Set<NestedJsonObjectDataObject> jsonObjectDataObjectSet;
  private Set<NestedStringDataObject> stringDataObjectSet;
  private Set<Buffer> bufferSet;
  private Set<JsonObject> jsonObjectSet;
  private Set<JsonArray> jsonArraySet;
  private Set<TimeUnit> httpMethodSet;
  private Set<ZonedDateTime> methodMappedSet;
  private Set<Locale> functionMappedSet;
  private Set<Object> objectSet;
  private Set<NoConverterDataObject> notConvertibleDataObjectSet;

  private List<String> addedStringValues = new ArrayList<>();
  private List<Boolean> addedBoxedBooleanValues = new ArrayList<>();
  private List<Byte> addedBoxedByteValues = new ArrayList<>();
  private List<Short> addedBoxedShortValues = new ArrayList<>();
  private List<Integer> addedBoxedIntValues = new ArrayList<>();
  private List<Long> addedBoxedLongValues = new ArrayList<>();
  private List<Float> addedBoxedFloatValues = new ArrayList<>();
  private List<Double> addedBoxedDoubleValues = new ArrayList<>();
  private List<Character> addedBoxedCharValues = new ArrayList<>();
  private List<NestedJsonObjectDataObject> addedJsonObjectDataObjects = new ArrayList<>();
  private List<NestedStringDataObject> addedStringDataObjects = new ArrayList<>();
  private List<Buffer> addedBuffers = new ArrayList<>();
  private List<JsonObject> addedJsonObjects = new ArrayList<>();
  private List<JsonArray> addedJsonArrays = new ArrayList<>();
  private List<TimeUnit> addedHttpMethods = new ArrayList<>();
  private List<ZonedDateTime> addedMethodMappeds = new ArrayList<>();
  private List<Locale> addedFunctionMappeds = new ArrayList<>();
  private List<Object> addedObjects = new ArrayList<>();

  private Map<String, String> stringValueMap;
  private Map<String, Boolean> boxedBooleanValueMap;
  private Map<String, Byte> boxedByteValueMap;
  private Map<String, Short> boxedShortValueMap;
  private Map<String, Integer> boxedIntValueMap;
  private Map<String, Long> boxedLongValueMap;
  private Map<String, Float> boxedFloatValueMap;
  private Map<String, Double> boxedDoubleValueMap;
  private Map<String, Character> boxedCharValueMap;
  private Map<String, NestedJsonObjectDataObject> jsonObjectDataObjectMap;
  private Map<String, NestedStringDataObject> stringDataObjectMap;
  private Map<String, Buffer> bufferMap;
  private Map<String, JsonObject> jsonObjectMap;
  private Map<String, JsonArray> jsonArrayMap;
  private Map<String, TimeUnit> httpMethodMap;
  private Map<String, ZonedDateTime> methodMappedMap;
  private Map<String, Locale> functionMappedMap;
  private Map<String, Object> objectMap;
  private Map<String, NoConverterDataObject> notConvertibleDataObjectMap;

  private Map<String, String> keyedStringValues = new HashMap<>();
  private Map<String, Boolean> keyedBoxedBooleanValues = new HashMap<>();
  private Map<String, Byte> keyedBoxedByteValues = new HashMap<>();
  private Map<String, Short> keyedBoxedShortValues = new HashMap<>();
  private Map<String, Integer> keyedBoxedIntValues = new HashMap<>();
  private Map<String, Long> keyedBoxedLongValues = new HashMap<>();
  private Map<String, Float> keyedBoxedFloatValues = new HashMap<>();
  private Map<String, Double> keyedBoxedDoubleValues = new HashMap<>();
  private Map<String, Character> keyedBoxedCharValues = new HashMap<>();
  private Map<String, NestedJsonObjectDataObject> keyedJsonObjectDataObjectValues = new HashMap<>();
  private Map<String, NestedStringDataObject> keyedStringDataObjectValues = new HashMap<>();
  private Map<String, Buffer> keyedBufferValues = new HashMap<>();
  private Map<String, JsonObject> keyedJsonObjectValues = new HashMap<>();
  private Map<String, JsonArray> keyedJsonArrayValues = new HashMap<>();
  private Map<String, TimeUnit> keyedEnumValues = new HashMap<>();
  private Map<String, ZonedDateTime> keyedMethodMappedValues = new HashMap<>();
  private Map<String, Locale> keyedFunctionMappedValues = new HashMap<>();
  private Map<String, Object> keyedObjectValues = new HashMap<>();

  public TestDataObject() {
  }

  public TestDataObject(TestDataObject copy) {
  }

  public TestDataObject(JsonObject json) {
  }

  public String getString() {
    return string;
  }

  public TestDataObject setString(String value) {
    this.string = value;
    return this;
  }

  public boolean isPrimitiveBoolean() {
    return primitiveBoolean;
  }

  public TestDataObject setPrimitiveBoolean(boolean value) {
    this.primitiveBoolean = value;
    return this;
  }

  public byte getPrimitiveByte() {
    return primitiveByte;
  }

  public TestDataObject setPrimitiveByte(byte primitiveByte) {
    this.primitiveByte = primitiveByte;
    return this;
  }

  public short getPrimitiveShort() {
    return primitiveShort;
  }

  public TestDataObject setPrimitiveShort(short primitiveShort) {
    this.primitiveShort = primitiveShort;
    return this;
  }

  public int getPrimitiveInt() {
    return primitiveInt;
  }

  public TestDataObject setPrimitiveInt(int primitiveInt) {
    this.primitiveInt = primitiveInt;
    return this;
  }

  public long getPrimitiveLong() {
    return primitiveLong;
  }

  public TestDataObject setPrimitiveLong(long primitiveLong) {
    this.primitiveLong = primitiveLong;
    return this;
  }

  public float getPrimitiveFloat() {
    return primitiveFloat;
  }

  public TestDataObject setPrimitiveFloat(float primitiveFloat) {
    this.primitiveFloat = primitiveFloat;
    return this;
  }

  public double getPrimitiveDouble() {
    return primitiveDouble;
  }

  public TestDataObject setPrimitiveDouble(double primitiveDouble) {
    this.primitiveDouble = primitiveDouble;
    return this;
  }

  public char getPrimitiveChar() {
    return primitiveChar;
  }

  public TestDataObject setPrimitiveChar(char primitiveChar) {
    this.primitiveChar = primitiveChar;
    return this;
  }

  public Boolean isBoxedBoolean() {
    return boxedBoolean;
  }

  public TestDataObject setBoxedBoolean(Boolean value) {
    this.boxedBoolean = value;
    return this;
  }

  public Byte getBoxedByte() {
    return boxedByte;
  }

  public TestDataObject setBoxedByte(Byte boxedByte) {
    this.boxedByte = boxedByte;
    return this;
  }

  public Short getBoxedShort() {
    return boxedShort;
  }

  public TestDataObject setBoxedShort(Short boxedShort) {
    this.boxedShort = boxedShort;
    return this;
  }

  public Integer getBoxedInt() {
    return boxedInt;
  }

  public TestDataObject setBoxedInt(Integer boxedInt) {
    this.boxedInt = boxedInt;
    return this;
  }

  public Long getBoxedLong() {
    return boxedLong;
  }

  public TestDataObject setBoxedLong(Long boxedLong) {
    this.boxedLong = boxedLong;
    return this;
  }

  public Float getBoxedFloat() {
    return boxedFloat;
  }

  public TestDataObject setBoxedFloat(Float boxedFloat) {
    this.boxedFloat = boxedFloat;
    return this;
  }

  public Double getBoxedDouble() {
    return boxedDouble;
  }

  public TestDataObject setBoxedDouble(Double boxedDouble) {
    this.boxedDouble = boxedDouble;
    return this;
  }

  public Character getBoxedChar() {
    return boxedChar;
  }

  public TestDataObject setBoxedChar(Character boxedChar) {
    this.boxedChar = boxedChar;
    return this;
  }

  public NestedJsonObjectDataObject getJsonObjectDataObject() {
    return jsonObjectDataObject;
  }

  public TestDataObject setJsonObjectDataObject(NestedJsonObjectDataObject jsonObjectDataObject) {
    this.jsonObjectDataObject = jsonObjectDataObject;
    return this;
  }

  public NestedStringDataObject getStringDataObject() {
    return stringDataObject;
  }

  public TestDataObject setStringDataObject(NestedStringDataObject stringDataObject) {
    this.stringDataObject = stringDataObject;
    return this;
  }

  public Buffer getBuffer() {
    return buffer;
  }

  public TestDataObject setBuffer(Buffer buffer) {
    this.buffer = buffer;
    return this;
  }

  public JsonObject getJsonObject() {
    return jsonObject;
  }

  public TestDataObject setJsonObject(JsonObject jsonObject) {
    this.jsonObject = jsonObject;
    return this;
  }

  public JsonArray getJsonArray() {
    return jsonArray;
  }

  public TestDataObject setJsonArray(JsonArray jsonArray) {
    this.jsonArray = jsonArray;
    return this;
  }

  public TimeUnit getHttpMethod() {
    return httpMethod;
  }

  public TestDataObject setHttpMethod(TimeUnit httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  public ZonedDateTime getMethodMapped() {
    return methodMapped;
  }

  public TestDataObject setMethodMapped(ZonedDateTime methodMapped) {
    this.methodMapped = methodMapped;
    return this;
  }

  public Locale getFunctionMapped() {
    return functionMapped;
  }

  public TestDataObject setFunctionMapped(Locale functionMapped) {
    this.functionMapped = functionMapped;
    return this;
  }

  public NoConverterDataObject getNotConvertibleDataObject() {
    return notConvertibleDataObject;
  }

  public TestDataObject setNotConvertibleDataObject(NoConverterDataObject notConvertibleDataObject) {
    this.notConvertibleDataObject = notConvertibleDataObject;
    return this;
  }

  public ByteBuffer getUnmapped() {
    return unmapped;
  }

  public TestDataObject setUnmapped(ByteBuffer unmapped) {
    this.unmapped = unmapped;
    return this;
  }

  public List<String> getStringList() {
    return stringList;
  }

  public TestDataObject setStringList(List<String> stringList) {
    this.stringList = stringList;
    return this;
  }

  public List<Boolean> getBoxedBooleanList() {
    return boxedBooleanList;
  }

  public TestDataObject setBoxedBooleanList(List<Boolean> boxedBooleanList) {
    this.boxedBooleanList = boxedBooleanList;
    return this;
  }

  public List<Byte> getBoxedByteList() {
    return boxedByteList;
  }

  public TestDataObject setBoxedByteList(List<Byte> boxedByteList) {
    this.boxedByteList = boxedByteList;
    return this;
  }

  public List<Short> getBoxedShortList() {
    return boxedShortList;
  }

  public TestDataObject setBoxedShortList(List<Short> boxedShortList) {
    this.boxedShortList = boxedShortList;
    return this;
  }

  public List<Integer> getBoxedIntList() {
    return boxedIntList;
  }

  public TestDataObject setBoxedIntList(List<Integer> boxedIntList) {
    this.boxedIntList = boxedIntList;
    return this;
  }

  public List<Long> getBoxedLongList() {
    return boxedLongList;
  }

  public TestDataObject setBoxedLongList(List<Long> boxedLongList) {
    this.boxedLongList = boxedLongList;
    return this;
  }

  public List<Float> getBoxedFloatList() {
    return boxedFloatList;
  }

  public TestDataObject setBoxedFloatList(List<Float> boxedFloatList) {
    this.boxedFloatList = boxedFloatList;
    return this;
  }

  public List<Double> getBoxedDoubleList() {
    return boxedDoubleList;
  }

  public TestDataObject setBoxedDoubleList(List<Double> boxedDoubleList) {
    this.boxedDoubleList = boxedDoubleList;
    return this;
  }

  public List<Character> getBoxedCharList() {
    return boxedCharList;
  }

  public TestDataObject setBoxedCharList(List<Character> boxedCharList) {
    this.boxedCharList = boxedCharList;
    return this;
  }

  public List<NestedJsonObjectDataObject> getJsonObjectDataObjectList() {
    return jsonObjectDataObjectList;
  }

  public TestDataObject setJsonObjectDataObjectList(List<NestedJsonObjectDataObject> jsonObjectDataObjectList) {
    this.jsonObjectDataObjectList = jsonObjectDataObjectList;
    return this;
  }

  public List<NestedStringDataObject> getStringDataObjectList() {
    return stringDataObjectList;
  }

  public TestDataObject setStringDataObjectList(List<NestedStringDataObject> stringDataObjectList) {
    this.stringDataObjectList = stringDataObjectList;
    return this;
  }

  public List<Buffer> getBufferList() {
    return bufferList;
  }

  public TestDataObject setBufferList(List<Buffer> bufferList) {
    this.bufferList = bufferList;
    return this;
  }

  public List<JsonObject> getJsonObjectList() {
    return jsonObjectList;
  }

  public TestDataObject setJsonObjectList(List<JsonObject> jsonObjectList) {
    this.jsonObjectList = jsonObjectList;
    return this;
  }

  public List<JsonArray> getJsonArrayList() {
    return jsonArrayList;
  }

  public TestDataObject setJsonArrayList(List<JsonArray> jsonArrayList) {
    this.jsonArrayList = jsonArrayList;
    return this;
  }

  public List<TimeUnit> getHttpMethodList() {
    return httpMethodList;
  }

  public TestDataObject setHttpMethodList(List<TimeUnit> httpMethodList) {
    this.httpMethodList = httpMethodList;
    return this;
  }

  public List<ZonedDateTime> getMethodMappedList() {
    return methodMappedList;
  }

  public TestDataObject setMethodMappedList(List<ZonedDateTime> methodMappedList) {
    this.methodMappedList = methodMappedList;
    return this;
  }

  public List<Locale> getFunctionMappedList() {
    return functionMappedList;
  }

  public TestDataObject setFunctionMappedList(List<Locale> functionMappedList) {
    this.functionMappedList = functionMappedList;
    return this;
  }

  public List<Object> getObjectList() {
    return objectList;
  }

  public TestDataObject setObjectList(List<Object> objectList) {
    this.objectList = objectList;
    return this;
  }


  public Set<String> getStringSet() {
    return stringSet;
  }

  public TestDataObject setStringSet(Set<String> stringSet) {
    this.stringSet = stringSet;
    return this;
  }

  public Set<Boolean> getBoxedBooleanSet() {
    return boxedBooleanSet;
  }

  public TestDataObject setBoxedBooleanSet(Set<Boolean> boxedBooleanSet) {
    this.boxedBooleanSet = boxedBooleanSet;
    return this;
  }

  public Set<Byte> getBoxedByteSet() {
    return boxedByteSet;
  }

  public TestDataObject setBoxedByteSet(Set<Byte> boxedByteSet) {
    this.boxedByteSet = boxedByteSet;
    return this;
  }

  public Set<Short> getBoxedShortSet() {
    return boxedShortSet;
  }

  public TestDataObject setBoxedShortSet(Set<Short> boxedShortSet) {
    this.boxedShortSet = boxedShortSet;
    return this;
  }

  public Set<Integer> getBoxedIntSet() {
    return boxedIntSet;
  }

  public TestDataObject setBoxedIntSet(Set<Integer> boxedIntSet) {
    this.boxedIntSet = boxedIntSet;
    return this;
  }

  public Set<Long> getBoxedLongSet() {
    return boxedLongSet;
  }

  public TestDataObject setBoxedLongSet(Set<Long> boxedLongSet) {
    this.boxedLongSet = boxedLongSet;
    return this;
  }

  public Set<Float> getBoxedFloatSet() {
    return boxedFloatSet;
  }

  public TestDataObject setBoxedFloatSet(Set<Float> boxedFloatSet) {
    this.boxedFloatSet = boxedFloatSet;
    return this;
  }

  public Set<Double> getBoxedDoubleSet() {
    return boxedDoubleSet;
  }

  public TestDataObject setBoxedDoubleSet(Set<Double> boxedDoubleSet) {
    this.boxedDoubleSet = boxedDoubleSet;
    return this;
  }

  public Set<Character> getBoxedCharSet() {
    return boxedCharSet;
  }

  public TestDataObject setBoxedCharSet(Set<Character> boxedCharSet) {
    this.boxedCharSet = boxedCharSet;
    return this;
  }

  public Set<NestedJsonObjectDataObject> getJsonObjectDataObjectSet() {
    return jsonObjectDataObjectSet;
  }

  public TestDataObject setJsonObjectDataObjectSet(Set<NestedJsonObjectDataObject> jsonObjectDataObjectSet) {
    this.jsonObjectDataObjectSet = jsonObjectDataObjectSet;
    return this;
  }

  public Set<NestedStringDataObject> getStringDataObjectSet() {
    return stringDataObjectSet;
  }

  public TestDataObject setStringDataObjectSet(Set<NestedStringDataObject> stringDataObjectSet) {
    this.stringDataObjectSet = stringDataObjectSet;
    return this;
  }

  public Set<Buffer> getBufferSet() {
    return bufferSet;
  }

  public TestDataObject setBufferSet(Set<Buffer> bufferSet) {
    this.bufferSet = bufferSet;
    return this;
  }

  public Set<JsonObject> getJsonObjectSet() {
    return jsonObjectSet;
  }

  public TestDataObject setJsonObjectSet(Set<JsonObject> jsonObjectSet) {
    this.jsonObjectSet = jsonObjectSet;
    return this;
  }

  public Set<JsonArray> getJsonArraySet() {
    return jsonArraySet;
  }

  public TestDataObject setJsonArraySet(Set<JsonArray> jsonArraySet) {
    this.jsonArraySet = jsonArraySet;
    return this;
  }

  public Set<TimeUnit> getHttpMethodSet() {
    return httpMethodSet;
  }

  public TestDataObject setHttpMethodSet(Set<TimeUnit> httpMethodSet) {
    this.httpMethodSet = httpMethodSet;
    return this;
  }

  public Set<ZonedDateTime> getMethodMappedSet() {
    return methodMappedSet;
  }

  public TestDataObject setMethodMappedSet(Set<ZonedDateTime> methodMappedSet) {
    this.methodMappedSet = methodMappedSet;
    return this;
  }

  public Set<Locale> getFunctionMappedSet() {
    return functionMappedSet;
  }

  public TestDataObject setFunctionMappedSet(Set<Locale> functionMappedSet) {
    this.functionMappedSet = functionMappedSet;
    return this;
  }

  public Set<Object> getObjectSet() {
    return objectSet;
  }

  public TestDataObject setObjectSet(Set<Object> objectSet) {
    this.objectSet = objectSet;
    return this;
  }

  public List<String> getAddedStringValues() {
    return addedStringValues;
  }

  public TestDataObject addAddedStringValue(String addedStringValue) {
    this.addedStringValues.add(addedStringValue);
    return this;
  }

  public List<Boolean> getAddedBoxedBooleanValues() {
    return addedBoxedBooleanValues;
  }

  public TestDataObject addAddedBoxedBooleanValue(Boolean addedBoxedBooleanValue) {
    this.addedBoxedBooleanValues.add(addedBoxedBooleanValue);
    return this;
  }

  public List<Byte> getAddedBoxedByteValues() {
    return addedBoxedByteValues;
  }

  public TestDataObject addAddedBoxedByteValue(Byte addedBoxedByteValue) {
    this.addedBoxedByteValues.add(addedBoxedByteValue);
    return this;
  }

  public List<Short> getAddedBoxedShortValues() {
    return addedBoxedShortValues;
  }

  public TestDataObject addAddedBoxedShortValue(Short addedBoxedShortValue) {
    this.addedBoxedShortValues.add(addedBoxedShortValue);
    return this;
  }

  public List<Integer> getAddedBoxedIntValues() {
    return addedBoxedIntValues;
  }

  public TestDataObject addAddedBoxedIntValue(Integer addedBoxedIntValue) {
    this.addedBoxedIntValues.add(addedBoxedIntValue);
    return this;
  }

  public List<Long> getAddedBoxedLongValues() {
    return addedBoxedLongValues;
  }

  public TestDataObject addAddedBoxedLongValue(Long addedBoxedLongValue) {
    this.addedBoxedLongValues.add(addedBoxedLongValue);
    return this;
  }

  public List<Float> getAddedBoxedFloatValues() {
    return addedBoxedFloatValues;
  }

  public TestDataObject addAddedBoxedFloatValue(Float addedBoxedFloatValue) {
    this.addedBoxedFloatValues.add(addedBoxedFloatValue);
    return this;
  }

  public List<Double> getAddedBoxedDoubleValues() {
    return addedBoxedDoubleValues;
  }

  public TestDataObject addAddedBoxedDoubleValue(Double addedBoxedDoubleValue) {
    this.addedBoxedDoubleValues.add(addedBoxedDoubleValue);
    return this;
  }

  public List<Character> getAddedBoxedCharValues() {
    return addedBoxedCharValues;
  }

  public TestDataObject addAddedBoxedCharValue(Character addedBoxedCharValue) {
    this.addedBoxedCharValues.add(addedBoxedCharValue);
    return this;
  }

  public List<NestedJsonObjectDataObject> getAddedJsonObjectDataObjects() {
    return addedJsonObjectDataObjects;
  }

  public TestDataObject addAddedJsonObjectDataObject(NestedJsonObjectDataObject addedAggregatedDataObject) {
    this.addedJsonObjectDataObjects.add(addedAggregatedDataObject);
    return this;
  }

  public List<NestedStringDataObject> getAddedStringDataObjects() {
    return addedStringDataObjects;
  }

  public TestDataObject addAddedStringDataObject(NestedStringDataObject addedAggregatedDataObject) {
    this.addedStringDataObjects.add(addedAggregatedDataObject);
    return this;
  }

  public List<Buffer> getAddedBuffers() {
    return addedBuffers;
  }

  public TestDataObject addAddedBuffer(Buffer addedBuffer) {
    this.addedBuffers.add(addedBuffer);
    return this;
  }

  public List<JsonObject> getAddedJsonObjects() {
    return addedJsonObjects;
  }

  public TestDataObject addAddedJsonObject(JsonObject addedJsonObject) {
    this.addedJsonObjects.add(addedJsonObject);
    return this;
  }

  public List<JsonArray> getAddedJsonArrays() {
    return addedJsonArrays;
  }

  public TestDataObject addAddedJsonArray(JsonArray addedJsonArray) {
    this.addedJsonArrays.add(addedJsonArray);
    return this;
  }

  public List<TimeUnit> getAddedHttpMethods() {
    return addedHttpMethods;
  }

  public TestDataObject addAddedHttpMethod(TimeUnit addedHttpMethod) {
    this.addedHttpMethods.add(addedHttpMethod);
    return this;
  }

  public List<ZonedDateTime> getAddedMethodMappeds() {
    return addedMethodMappeds;
  }

  public TestDataObject addAddedMethodMapped(ZonedDateTime addedDateTime) {
    this.addedMethodMappeds.add(addedDateTime);
    return this;
  }

  public List<Locale> getAddedFunctionMappeds() {
    return addedFunctionMappeds;
  }

  public TestDataObject addAddedFunctionMapped(Locale addedUri) {
    this.addedFunctionMappeds.add(addedUri);
    return this;
  }

  public List<Object> getAddedObjects() {
    return addedObjects;
  }

  public TestDataObject addAddedObject(Object addedObject) {
    this.addedObjects.add(addedObject);
    return this;
  }

  public Map<String, String> getStringValueMap() {
    return stringValueMap;
  }

  public TestDataObject setStringValueMap(Map<String, String> stringValueMap) {
    this.stringValueMap = stringValueMap;
    return this;
  }

  public Map<String, Boolean> getBoxedBooleanValueMap() {
    return boxedBooleanValueMap;
  }

  public TestDataObject setBoxedBooleanValueMap(Map<String, Boolean> boxedBooleanValueMap) {
    this.boxedBooleanValueMap = boxedBooleanValueMap;
    return this;
  }

  public Map<String, Byte> getBoxedByteValueMap() {
    return boxedByteValueMap;
  }

  public TestDataObject setBoxedByteValueMap(Map<String, Byte> boxedByteValueMap) {
    this.boxedByteValueMap = boxedByteValueMap;
    return this;
  }

  public Map<String, Short> getBoxedShortValueMap() {
    return boxedShortValueMap;
  }

  public TestDataObject setBoxedShortValueMap(Map<String, Short> boxedShortValueMap) {
    this.boxedShortValueMap = boxedShortValueMap;
    return this;
  }

  public Map<String, Integer> getBoxedIntValueMap() {
    return boxedIntValueMap;
  }

  public TestDataObject setBoxedIntValueMap(Map<String, Integer> boxedIntValueMap) {
    this.boxedIntValueMap = boxedIntValueMap;
    return this;
  }

  public Map<String, Long> getBoxedLongValueMap() {
    return boxedLongValueMap;
  }

  public TestDataObject setBoxedLongValueMap(Map<String, Long> boxedLongValueMap) {
    this.boxedLongValueMap = boxedLongValueMap;
    return this;
  }

  public Map<String, Float> getBoxedFloatValueMap() {
    return boxedFloatValueMap;
  }

  public TestDataObject setBoxedFloatValueMap(Map<String, Float> boxedFloatValueMap) {
    this.boxedFloatValueMap = boxedFloatValueMap;
    return this;
  }

  public Map<String, Double> getBoxedDoubleValueMap() {
    return boxedDoubleValueMap;
  }

  public TestDataObject setBoxedDoubleValueMap(Map<String, Double> boxedDoubleValueMap) {
    this.boxedDoubleValueMap = boxedDoubleValueMap;
    return this;
  }

  public Map<String, Character> getBoxedCharValueMap() {
    return boxedCharValueMap;
  }

  public TestDataObject setBoxedCharValueMap(Map<String, Character> boxedCharValueMap) {
    this.boxedCharValueMap = boxedCharValueMap;
    return this;
  }

  public Map<String, NestedJsonObjectDataObject> getJsonObjectDataObjectMap() {
    return jsonObjectDataObjectMap;
  }

  public TestDataObject setJsonObjectDataObjectMap(Map<String, NestedJsonObjectDataObject> nestedJsonObjectDataObjectMap) {
    this.jsonObjectDataObjectMap = nestedJsonObjectDataObjectMap;
    return this;
  }

  public Map<String, NestedStringDataObject> getStringDataObjectMap() {
    return stringDataObjectMap;
  }

  public TestDataObject setStringDataObjectMap(Map<String, NestedStringDataObject> nestedJsonObjectDataObjectMap) {
    this.stringDataObjectMap = nestedJsonObjectDataObjectMap;
    return this;
  }

  public Map<String, Buffer> getBufferMap() {
    return bufferMap;
  }

  public TestDataObject setBufferMap(Map<String, Buffer> bufferMap) {
    this.bufferMap = bufferMap;
    return this;
  }

  public Map<String, JsonObject> getJsonObjectMap() {
    return jsonObjectMap;
  }

  public TestDataObject setJsonObjectMap(Map<String, JsonObject> jsonObjectMap) {
    this.jsonObjectMap = jsonObjectMap;
    return this;
  }

  public Map<String, JsonArray> getJsonArrayMap() {
    return jsonArrayMap;
  }

  public TestDataObject setJsonArrayMap(Map<String, JsonArray> jsonArrayMap) {
    this.jsonArrayMap = jsonArrayMap;
    return this;
  }

  public Map<String, TimeUnit> getHttpMethodMap() {
    return httpMethodMap;
  }

  public TestDataObject setHttpMethodMap(Map<String, TimeUnit> httpMethodMap) {
    this.httpMethodMap = httpMethodMap;
    return this;
  }

  public Map<String, ZonedDateTime> getMethodMappedMap() {
    return methodMappedMap;
  }

  public TestDataObject setMethodMappedMap(Map<String, ZonedDateTime> methodMappedMap) {
    this.methodMappedMap = methodMappedMap;
    return this;
  }

  public Map<String, Locale> getFunctionMappedMap() {
    return functionMappedMap;
  }

  public TestDataObject setFunctionMappedMap(Map<String, Locale> functionMappedMap) {
    this.functionMappedMap = functionMappedMap;
    return this;
  }

  public Map<String, Object> getObjectMap() {
    return objectMap;
  }

  public TestDataObject setObjectMap(Map<String, Object> objectMap) {
    this.objectMap = objectMap;
    return this;
  }

  public Map<String, String> getKeyedStringValues() {
    return keyedStringValues;
  }

  public TestDataObject addKeyedStringValue(String name, String value) {
    this.keyedStringValues.put(name, value);
    return this;
  }

  public Map<String, Boolean> getKeyedBoxedBooleanValues() {
    return keyedBoxedBooleanValues;
  }

  public TestDataObject addKeyedBoxedBooleanValue(String key, Boolean value) {
    keyedBoxedBooleanValues.put(key, value);
    return this;
  }

  public Map<String, Byte> getKeyedBoxedByteValues() {
    return keyedBoxedByteValues;
  }

  public TestDataObject addKeyedBoxedByteValue(String key, Byte value) {
    keyedBoxedByteValues.put(key, value);
    return this;
  }

  public Map<String, Short> getKeyedBoxedShortValues() {
    return keyedBoxedShortValues;
  }

  public TestDataObject addKeyedBoxedShortValue(String key, Short value) {
    keyedBoxedShortValues.put(key, value);
    return this;
  }

  public Map<String, Integer> getKeyedBoxedIntValues() {
    return keyedBoxedIntValues;
  }

  public TestDataObject addKeyedBoxedIntValue(String key, Integer value) {
    keyedBoxedIntValues.put(key, value);
    return this;
  }

  public Map<String, Long> getKeyedBoxedLongValues() {
    return keyedBoxedLongValues;
  }

  public TestDataObject addKeyedBoxedLongValue(String key, Long value) {
    keyedBoxedLongValues.put(key, value);
    return this;
  }

  public Map<String, Float> getKeyedBoxedFloatValues() {
    return keyedBoxedFloatValues;
  }

  public TestDataObject addKeyedBoxedFloatValue(String key, Float value) {
    keyedBoxedFloatValues.put(key, value);
    return this;
  }

  public Map<String, Double> getKeyedBoxedDoubleValues() {
    return keyedBoxedDoubleValues;
  }

  public TestDataObject addKeyedBoxedDoubleValue(String key, Double value) {
    keyedBoxedDoubleValues.put(key, value);
    return this;
  }

  public Map<String, Character> getKeyedBoxedCharValues() {
    return keyedBoxedCharValues;
  }

  public TestDataObject addKeyedBoxedCharValue(String key, Character value) {
    keyedBoxedCharValues.put(key, value);
    return this;
  }

  public Map<String, NestedJsonObjectDataObject> getKeyedJsonObjectDataObjectValues() {
    return keyedJsonObjectDataObjectValues;
  }

  public TestDataObject addKeyedJsonObjectDataObjectValue(String key, NestedJsonObjectDataObject value) {
    keyedJsonObjectDataObjectValues.put(key, value);
    return this;
  }

  public Map<String, NestedStringDataObject> getKeyedStringDataObjectValues() {
    return keyedStringDataObjectValues;
  }

  public TestDataObject addKeyedStringDataObjectValue(String key, NestedStringDataObject value) {
    keyedStringDataObjectValues.put(key, value);
    return this;
  }

  public Map<String, Buffer> getKeyedBufferValues() {
    return keyedBufferValues;
  }

  public TestDataObject addKeyedBufferValue(String key, Buffer value) {
    keyedBufferValues.put(key, value);
    return this;
  }

  public Map<String, JsonObject> getKeyedJsonObjectValues() {
    return keyedJsonObjectValues;
  }

  public TestDataObject addKeyedJsonObjectValue(String key, JsonObject value) {
    keyedJsonObjectValues.put(key, value);
    return this;
  }

  public Map<String, JsonArray> getKeyedJsonArrayValues() {
    return keyedJsonArrayValues;
  }

  public TestDataObject addKeyedJsonArrayValue(String key, JsonArray value) {
    keyedJsonArrayValues.put(key, value);
    return this;
  }

  public Map<String, TimeUnit> getKeyedEnumValues() {
    return keyedEnumValues;
  }

  public TestDataObject addKeyedEnumValue(String key, TimeUnit value) {
    keyedEnumValues.put(key, value);
    return this;
  }

  public Map<String, ZonedDateTime> getKeyedMethodMappedValues() {
    return keyedMethodMappedValues;
  }

  public TestDataObject addKeyedMethodMappedValue(String key, ZonedDateTime value) {
    this.keyedMethodMappedValues.put(key, value);
    return this;
  }

  public Map<String, Locale> getKeyedFunctionMappedValues() {
    return keyedFunctionMappedValues;
  }

  public TestDataObject addKeyedFunctionMappedValue(String key, Locale value) {
    this.keyedFunctionMappedValues.put(key, value);
    return this;
  }

  public Map<String, Object> getKeyedObjectValues() {
    return keyedObjectValues;
  }

  public TestDataObject addKeyedObjectValue(String key, Object value) {
    keyedObjectValues.put(key, value);
    return this;
  }

  public List<NoConverterDataObject> getNotConvertibleDataObjectList() {
    return notConvertibleDataObjectList;
  }

  public TestDataObject setNotConvertibleDataObjectList(List<NoConverterDataObject> notConvertibleDataObjectList) {
    this.notConvertibleDataObjectList = notConvertibleDataObjectList;
    return this;
  }

  public Set<NoConverterDataObject> getNotConvertibleDataObjectSet() {
    return notConvertibleDataObjectSet;
  }

  public TestDataObject setNotConvertibleDataObjectSet(Set<NoConverterDataObject> notConvertibleDataObjectSet) {
    this.notConvertibleDataObjectSet = notConvertibleDataObjectSet;
    return this;
  }

  public Map<String, NoConverterDataObject> getNotConvertibleDataObjectMap() {
    return notConvertibleDataObjectMap;
  }

  public TestDataObject setNotConvertibleDataObjectMap(Map<String, NoConverterDataObject> notConvertibleDataObjectMap) {
    this.notConvertibleDataObjectMap = notConvertibleDataObjectMap;
    return this;
  }
}
