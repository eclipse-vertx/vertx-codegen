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
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
@JsonGen
public class TestDataObject {

  public static String serializeZonedDateTime(ZonedDateTime value) {
    return value.toString();
  }

  public static ZonedDateTime deserializeZonedDateTime(String value) {
    return ZonedDateTime.parse(value);
  }

  public static String serializeCustomEnum(TestCustomEnum value) {
    return (value != null) ? value.getShortName() : null;
  }

  public static TestCustomEnum deserializeCustomEnum(String value) {
    return (value != null) ? TestCustomEnum.of(value) : null;
  }

  private ByteBuffer unmapped;

  private String stringValue;
  private boolean primitiveBooleanValue;
  private byte primitiveByteValue;
  private short primitiveShortValue;
  private int primitiveIntValue;
  private long primitiveLongValue;
  private float primitiveFloatValue;
  private double primitiveDoubleValue;
  private char primitiveCharValue;
  private Boolean boxedBooleanValue;
  private Byte boxedByteValue;
  private Short boxedShortValue;
  private Integer boxedIntValue;
  private Long boxedLongValue;
  private Float boxedFloatValue;
  private Double boxedDoubleValue;
  private Character boxedCharValue;
  private NestedJsonObjectDataObject jsonObjectDataObjectValue;
  private NestedStringDataObject stringDataObjectValue;
  private Buffer bufferValue;
  private JsonObject jsonObjectValue;
  private JsonArray jsonArrayValue;
  private TimeUnit enumValue;
  private ZonedDateTime methodMappedValue;
  private Object objectValue;
  private NoConverterDataObject noConverterDataObjectValue;
  private NoConverter2DataObject noConverter2DataObjectValue;
  private TestCustomEnum customEnumValue;
  private AutoMapped autoMappedValue;
  private AutoMappedWithVertxGen autoMappedWithVertxGenValue;

  private List<String> stringValueList;
  private List<Boolean> boxedBooleanValueList;
  private List<Byte> boxedByteValueList;
  private List<Short> boxedShortValueList;
  private List<Integer> boxedIntValueList;
  private List<Long> boxedLongValueList;
  private List<Float> boxedFloatValueList;
  private List<Double> boxedDoubleValueList;
  private List<Character> boxedCharValueList;
  private List<NestedJsonObjectDataObject> jsonObjectDataObjectValueList;
  private List<NestedStringDataObject> stringDataObjectValueList;
  private List<Buffer> bufferValueList;
  private List<JsonObject> jsonObjectValueList;
  private List<JsonArray> jsonArrayValueList;
  private List<TimeUnit> enumValueList;
  private List<ZonedDateTime> methodMappedValueList;
  private List<Object> objectValueList;
  private List<NoConverterDataObject> noConverterDataObjectValueList;
  private List<NoConverter2DataObject> noConverter2DataObjectValueList;
  private List<TestCustomEnum> customEnumValueList;
  private List<AutoMapped> autoMappedValueList;
  private List<AutoMappedWithVertxGen> autoMappedWithVertxGenValueList;

  private Set<String> stringValueSet;
  private Set<Boolean> boxedBooleanValueSet;
  private Set<Byte> boxedByteValueSet;
  private Set<Short> boxedShortValueSet;
  private Set<Integer> boxedIntValueSet;
  private Set<Long> boxedLongValueSet;
  private Set<Float> boxedFloatValueSet;
  private Set<Double> boxedDoubleValueSet;
  private Set<Character> boxedCharValueSet;
  private Set<NestedJsonObjectDataObject> jsonObjectDataObjectValueSet;
  private Set<NestedStringDataObject> stringDataObjectValueSet;
  private Set<Buffer> bufferValueSet;
  private Set<JsonObject> jsonObjectValueSet;
  private Set<JsonArray> jsonArrayValueSet;
  private Set<TimeUnit> enumValueSet;
  private Set<ZonedDateTime> methodMappedValueSet;
  private Set<Object> objectValueSet;
  private Set<NoConverterDataObject> noConverterDataObjectValueSet;
  private Set<NoConverter2DataObject> noConverter2DataObjectValueSet;
  private Set<TestCustomEnum> customEnumValueSet;
  private Set<AutoMapped> autoMappedValueSet;
  private Set<AutoMappedWithVertxGen> autoMappedWithVertxGenValueSet;

  private List<String> addedStringValues = new ArrayList<>();
  private List<Boolean> addedBoxedBooleanValues = new ArrayList<>();
  private List<Byte> addedBoxedByteValues = new ArrayList<>();
  private List<Short> addedBoxedShortValues = new ArrayList<>();
  private List<Integer> addedBoxedIntValues = new ArrayList<>();
  private List<Long> addedBoxedLongValues = new ArrayList<>();
  private List<Float> addedBoxedFloatValues = new ArrayList<>();
  private List<Double> addedBoxedDoubleValues = new ArrayList<>();
  private List<Character> addedBoxedCharValues = new ArrayList<>();
  private List<NestedJsonObjectDataObject> addedJsonObjectDataObjectValues = new ArrayList<>();
  private List<NestedStringDataObject> addedStringDataObjectValues = new ArrayList<>();
  private List<Buffer> addedBufferValues = new ArrayList<>();
  private List<JsonObject> addedJsonObjectValues = new ArrayList<>();
  private List<JsonArray> addedJsonArrayValues = new ArrayList<>();
  private List<TimeUnit> addedEnumValues = new ArrayList<>();
  private List<ZonedDateTime> addedMethodMappedValues = new ArrayList<>();
  private List<Object> addedObjectValues = new ArrayList<>();
  private List<NoConverterDataObject> addedNoConverterDataObjectValues = new ArrayList<>();
  private List<NoConverter2DataObject> addedNoConverter2DataObjectValues = new ArrayList<>();
  private List<TestCustomEnum> addedCustomEnumValues = new ArrayList<>();
  private List<AutoMapped> addedAutoMappedValues = new ArrayList<>();
  private List<AutoMappedWithVertxGen> addedAutoMappedWithVertxGenValues = new ArrayList<>();

  private Map<String, String> stringValueMap;
  private Map<String, Boolean> boxedBooleanValueMap;
  private Map<String, Byte> boxedByteValueMap;
  private Map<String, Short> boxedShortValueMap;
  private Map<String, Integer> boxedIntValueMap;
  private Map<String, Long> boxedLongValueMap;
  private Map<String, Float> boxedFloatValueMap;
  private Map<String, Double> boxedDoubleValueMap;
  private Map<String, Character> boxedCharValueMap;
  private Map<String, NestedJsonObjectDataObject> jsonObjectDataObjectValueMap;
  private Map<String, NestedStringDataObject> stringDataObjectValueMap;
  private Map<String, Buffer> bufferValueMap;
  private Map<String, JsonObject> jsonObjectValueMap;
  private Map<String, JsonArray> jsonArrayValueMap;
  private Map<String, TimeUnit> enumValueMap;
  private Map<String, ZonedDateTime> methodMappedValueMap;
  private Map<String, Object> objectValueMap;
  private Map<String, NoConverterDataObject> noConverterDataObjectValueMap;
  private Map<String, NoConverter2DataObject> noConverter2DataObjectValueMap;
  private Map<String, TestCustomEnum> customEnumValueMap;
  private Map<String, AutoMapped> autoMappedValueMap;
  private Map<String, AutoMappedWithVertxGen> autoMappedWithVertxGenValueMap;

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
  private Map<String, Object> keyedObjectValues = new HashMap<>();
  private Map<String, NoConverterDataObject> keyedNoConverterDataObjectValues = new HashMap<>();
  private Map<String, NoConverter2DataObject> keyedNoConverter2DataObjectValues = new HashMap<>();
  private Map<String, TestCustomEnum> keyedCustomEnumValues = new HashMap<>();
  private Map<String, AutoMapped> keyedAutoMappedValues = new HashMap<>();
  private Map<String, AutoMappedWithVertxGen> keyedAutoMappedWithVertxGenValues = new HashMap<>();

  public TestDataObject() {
  }

  public TestDataObject(TestDataObject copy) {
  }

  public TestDataObject(JsonObject json) {
  }

  public String getStringValue() {
    return stringValue;
  }

  public TestDataObject setStringValue(String value) {
    this.stringValue = value;
    return this;
  }

  public boolean isPrimitiveBooleanValue() {
    return primitiveBooleanValue;
  }

  public TestDataObject setPrimitiveBooleanValue(boolean value) {
    this.primitiveBooleanValue = value;
    return this;
  }

  public byte getPrimitiveByteValue() {
    return primitiveByteValue;
  }

  public TestDataObject setPrimitiveByteValue(byte primitiveByteValue) {
    this.primitiveByteValue = primitiveByteValue;
    return this;
  }

  public short getPrimitiveShortValue() {
    return primitiveShortValue;
  }

  public TestDataObject setPrimitiveShortValue(short primitiveShortValue) {
    this.primitiveShortValue = primitiveShortValue;
    return this;
  }

  public int getPrimitiveIntValue() {
    return primitiveIntValue;
  }

  public TestDataObject setPrimitiveIntValue(int primitiveIntValue) {
    this.primitiveIntValue = primitiveIntValue;
    return this;
  }

  public long getPrimitiveLongValue() {
    return primitiveLongValue;
  }

  public TestDataObject setPrimitiveLongValue(long primitiveLongValue) {
    this.primitiveLongValue = primitiveLongValue;
    return this;
  }

  public float getPrimitiveFloatValue() {
    return primitiveFloatValue;
  }

  public TestDataObject setPrimitiveFloatValue(float primitiveFloatValue) {
    this.primitiveFloatValue = primitiveFloatValue;
    return this;
  }

  public double getPrimitiveDoubleValue() {
    return primitiveDoubleValue;
  }

  public TestDataObject setPrimitiveDoubleValue(double primitiveDoubleValue) {
    this.primitiveDoubleValue = primitiveDoubleValue;
    return this;
  }

  public char getPrimitiveCharValue() {
    return primitiveCharValue;
  }

  public TestDataObject setPrimitiveCharValue(char primitiveCharValue) {
    this.primitiveCharValue = primitiveCharValue;
    return this;
  }

  public Boolean isBoxedBooleanValue() {
    return boxedBooleanValue;
  }

  public TestDataObject setBoxedBooleanValue(Boolean value) {
    this.boxedBooleanValue = value;
    return this;
  }

  public Byte getBoxedByteValue() {
    return boxedByteValue;
  }

  public TestDataObject setBoxedByteValue(Byte boxedByteValue) {
    this.boxedByteValue = boxedByteValue;
    return this;
  }

  public Short getBoxedShortValue() {
    return boxedShortValue;
  }

  public TestDataObject setBoxedShortValue(Short boxedShortValue) {
    this.boxedShortValue = boxedShortValue;
    return this;
  }

  public Integer getBoxedIntValue() {
    return boxedIntValue;
  }

  public TestDataObject setBoxedIntValue(Integer boxedIntValue) {
    this.boxedIntValue = boxedIntValue;
    return this;
  }

  public Long getBoxedLongValue() {
    return boxedLongValue;
  }

  public TestDataObject setBoxedLongValue(Long boxedLongValue) {
    this.boxedLongValue = boxedLongValue;
    return this;
  }

  public Float getBoxedFloatValue() {
    return boxedFloatValue;
  }

  public TestDataObject setBoxedFloatValue(Float boxedFloatValue) {
    this.boxedFloatValue = boxedFloatValue;
    return this;
  }

  public Double getBoxedDoubleValue() {
    return boxedDoubleValue;
  }

  public TestDataObject setBoxedDoubleValue(Double boxedDoubleValue) {
    this.boxedDoubleValue = boxedDoubleValue;
    return this;
  }

  public Character getBoxedCharValue() {
    return boxedCharValue;
  }

  public TestDataObject setBoxedCharValue(Character boxedCharValue) {
    this.boxedCharValue = boxedCharValue;
    return this;
  }

  public NestedJsonObjectDataObject getJsonObjectDataObjectValue() {
    return jsonObjectDataObjectValue;
  }

  public TestDataObject setJsonObjectDataObjectValue(NestedJsonObjectDataObject jsonObjectDataObjectValue) {
    this.jsonObjectDataObjectValue = jsonObjectDataObjectValue;
    return this;
  }

  public NestedStringDataObject getStringDataObjectValue() {
    return stringDataObjectValue;
  }

  public TestDataObject setStringDataObjectValue(NestedStringDataObject stringDataObjectValue) {
    this.stringDataObjectValue = stringDataObjectValue;
    return this;
  }

  public Buffer getBufferValue() {
    return bufferValue;
  }

  public TestDataObject setBufferValue(Buffer bufferValue) {
    this.bufferValue = bufferValue;
    return this;
  }

  public JsonObject getJsonObjectValue() {
    return jsonObjectValue;
  }

  public TestDataObject setJsonObjectValue(JsonObject jsonObjectValue) {
    this.jsonObjectValue = jsonObjectValue;
    return this;
  }

  public JsonArray getJsonArrayValue() {
    return jsonArrayValue;
  }

  public TestDataObject setJsonArrayValue(JsonArray jsonArrayValue) {
    this.jsonArrayValue = jsonArrayValue;
    return this;
  }

  public TimeUnit getEnumValue() {
    return enumValue;
  }

  public TestDataObject setEnumValue(TimeUnit enumValue) {
    this.enumValue = enumValue;
    return this;
  }

  public ZonedDateTime getMethodMappedValue() {
    return methodMappedValue;
  }

  public TestDataObject setMethodMappedValue(ZonedDateTime methodMappedValue) {
    this.methodMappedValue = methodMappedValue;
    return this;
  }

  public Object getObjectValue() {
    return objectValue;
  }

  public TestDataObject setObjectValue(Object objectValue) {
    this.objectValue = objectValue;
    return this;
  }

  public NoConverterDataObject getNoConverterDataObjectValue() {
    return noConverterDataObjectValue;
  }

  public TestDataObject setNoConverterDataObjectValue(NoConverterDataObject noConverterDataObjectValue) {
    this.noConverterDataObjectValue = noConverterDataObjectValue;
    return this;
  }

  public NoConverter2DataObject getNoConverter2DataObjectValue() {
    return noConverter2DataObjectValue;
  }

  public TestDataObject setNoConverter2DataObjectValue(NoConverter2DataObject noConverter2DataObjectValue) {
    this.noConverter2DataObjectValue = noConverter2DataObjectValue;
    return this;
  }

  public ByteBuffer getUnmapped() {
    return unmapped;
  }

  public TestDataObject setUnmapped(ByteBuffer unmapped) {
    this.unmapped = unmapped;
    return this;
  }

  public TestCustomEnum getCustomEnumValue() {
    return customEnumValue;
  }

  public TestDataObject setCustomEnumValue(TestCustomEnum customEnumValue) {
    this.customEnumValue = customEnumValue;
    return this;
  }

  public AutoMapped getAutoMappedValue() {
    return autoMappedValue;
  }

  public TestDataObject setAutoMappedValue(AutoMapped autoMappedValue) {
    this.autoMappedValue = autoMappedValue;
    return this;
  }

  public AutoMappedWithVertxGen getAutoMappedWithVertxGenValue() {
    return autoMappedWithVertxGenValue;
  }

  public TestDataObject setAutoMappedWithVertxGenValue(AutoMappedWithVertxGen autoMappedWithVertxGenValue) {
    this.autoMappedWithVertxGenValue = autoMappedWithVertxGenValue;
    return this;
  }

  public List<String> getStringValueList() {
    return stringValueList;
  }

  public TestDataObject setStringValueList(List<String> stringValueList) {
    this.stringValueList = stringValueList;
    return this;
  }

  public List<Boolean> getBoxedBooleanValueList() {
    return boxedBooleanValueList;
  }

  public TestDataObject setBoxedBooleanValueList(List<Boolean> boxedBooleanValueList) {
    this.boxedBooleanValueList = boxedBooleanValueList;
    return this;
  }

  public List<Byte> getBoxedByteValueList() {
    return boxedByteValueList;
  }

  public TestDataObject setBoxedByteValueList(List<Byte> boxedByteValueList) {
    this.boxedByteValueList = boxedByteValueList;
    return this;
  }

  public List<Short> getBoxedShortValueList() {
    return boxedShortValueList;
  }

  public TestDataObject setBoxedShortValueList(List<Short> boxedShortValueList) {
    this.boxedShortValueList = boxedShortValueList;
    return this;
  }

  public List<Integer> getBoxedIntValueList() {
    return boxedIntValueList;
  }

  public TestDataObject setBoxedIntValueList(List<Integer> boxedIntValueList) {
    this.boxedIntValueList = boxedIntValueList;
    return this;
  }

  public List<Long> getBoxedLongValueList() {
    return boxedLongValueList;
  }

  public TestDataObject setBoxedLongValueList(List<Long> boxedLongValueList) {
    this.boxedLongValueList = boxedLongValueList;
    return this;
  }

  public List<Float> getBoxedFloatValueList() {
    return boxedFloatValueList;
  }

  public TestDataObject setBoxedFloatValueList(List<Float> boxedFloatValueList) {
    this.boxedFloatValueList = boxedFloatValueList;
    return this;
  }

  public List<Double> getBoxedDoubleValueList() {
    return boxedDoubleValueList;
  }

  public TestDataObject setBoxedDoubleValueList(List<Double> boxedDoubleValueList) {
    this.boxedDoubleValueList = boxedDoubleValueList;
    return this;
  }

  public List<Character> getBoxedCharValueList() {
    return boxedCharValueList;
  }

  public TestDataObject setBoxedCharValueList(List<Character> boxedCharValueList) {
    this.boxedCharValueList = boxedCharValueList;
    return this;
  }

  public List<NestedJsonObjectDataObject> getJsonObjectDataObjectValueList() {
    return jsonObjectDataObjectValueList;
  }

  public TestDataObject setJsonObjectDataObjectValueList(List<NestedJsonObjectDataObject> jsonObjectDataObjectValueList) {
    this.jsonObjectDataObjectValueList = jsonObjectDataObjectValueList;
    return this;
  }

  public List<NestedStringDataObject> getStringDataObjectValueList() {
    return stringDataObjectValueList;
  }

  public TestDataObject setStringDataObjectValueList(List<NestedStringDataObject> stringDataObjectValueList) {
    this.stringDataObjectValueList = stringDataObjectValueList;
    return this;
  }

  public List<Buffer> getBufferValueList() {
    return bufferValueList;
  }

  public TestDataObject setBufferValueList(List<Buffer> bufferValueList) {
    this.bufferValueList = bufferValueList;
    return this;
  }

  public List<JsonObject> getJsonObjectValueList() {
    return jsonObjectValueList;
  }

  public TestDataObject setJsonObjectValueList(List<JsonObject> jsonObjectValueList) {
    this.jsonObjectValueList = jsonObjectValueList;
    return this;
  }

  public List<JsonArray> getJsonArrayValueList() {
    return jsonArrayValueList;
  }

  public TestDataObject setJsonArrayValueList(List<JsonArray> jsonArrayValueList) {
    this.jsonArrayValueList = jsonArrayValueList;
    return this;
  }

  public List<TimeUnit> getEnumValueList() {
    return enumValueList;
  }

  public TestDataObject setEnumValueList(List<TimeUnit> enumValueList) {
    this.enumValueList = enumValueList;
    return this;
  }

  public List<ZonedDateTime> getMethodMappedValueList() {
    return methodMappedValueList;
  }

  public TestDataObject setMethodMappedValueList(List<ZonedDateTime> methodMappedValueList) {
    this.methodMappedValueList = methodMappedValueList;
    return this;
  }

  public List<Object> getObjectValueList() {
    return objectValueList;
  }

  public TestDataObject setObjectValueList(List<Object> objectValueList) {
    this.objectValueList = objectValueList;
    return this;
  }

  public List<NoConverterDataObject> getNoConverterDataObjectValueList() {
    return noConverterDataObjectValueList;
  }

  public TestDataObject setNoConverterDataObjectValueList(List<NoConverterDataObject> noConverterDataObjectValueList) {
    this.noConverterDataObjectValueList = noConverterDataObjectValueList;
    return this;
  }

  public List<NoConverter2DataObject> getNoConverter2DataObjectValueList() {
    return noConverter2DataObjectValueList;
  }

  public TestDataObject setNoConverter2DataObjectValueList(List<NoConverter2DataObject> noConverter2DataObjectValueList) {
    this.noConverter2DataObjectValueList = noConverter2DataObjectValueList;
    return this;
  }

  public List<TestCustomEnum> getCustomEnumValueList() {
    return customEnumValueList;
  }

  public TestDataObject setCustomEnumValueList(List<TestCustomEnum> customEnumValueList) {
    this.customEnumValueList = customEnumValueList;
    return this;
  }

  public List<AutoMapped> getAutoMappedValueList() {
    return autoMappedValueList;
  }

  public TestDataObject setAutoMappedValueList(List<AutoMapped> autoMappedValueList) {
    this.autoMappedValueList = autoMappedValueList;
    return this;
  }

  public List<AutoMappedWithVertxGen> getAutoMappedWithVertxGenValueList() {
    return autoMappedWithVertxGenValueList;
  }

  public TestDataObject setAutoMappedWithVertxGenValueList(List<AutoMappedWithVertxGen> autoMappedValueList) {
    this.autoMappedWithVertxGenValueList = autoMappedValueList;
    return this;
  }

  public Set<String> getStringValueSet() {
    return stringValueSet;
  }

  public TestDataObject setStringValueSet(Set<String> stringValueSet) {
    this.stringValueSet = stringValueSet;
    return this;
  }

  public Set<Boolean> getBoxedBooleanValueSet() {
    return boxedBooleanValueSet;
  }

  public TestDataObject setBoxedBooleanValueSet(Set<Boolean> boxedBooleanValueSet) {
    this.boxedBooleanValueSet = boxedBooleanValueSet;
    return this;
  }

  public Set<Byte> getBoxedByteValueSet() {
    return boxedByteValueSet;
  }

  public TestDataObject setBoxedByteValueSet(Set<Byte> boxedByteValueSet) {
    this.boxedByteValueSet = boxedByteValueSet;
    return this;
  }

  public Set<Short> getBoxedShortValueSet() {
    return boxedShortValueSet;
  }

  public TestDataObject setBoxedShortValueSet(Set<Short> boxedShortValueSet) {
    this.boxedShortValueSet = boxedShortValueSet;
    return this;
  }

  public Set<Integer> getBoxedIntValueSet() {
    return boxedIntValueSet;
  }

  public TestDataObject setBoxedIntValueSet(Set<Integer> boxedIntValueSet) {
    this.boxedIntValueSet = boxedIntValueSet;
    return this;
  }

  public Set<Long> getBoxedLongValueSet() {
    return boxedLongValueSet;
  }

  public TestDataObject setBoxedLongValueSet(Set<Long> boxedLongValueSet) {
    this.boxedLongValueSet = boxedLongValueSet;
    return this;
  }

  public Set<Float> getBoxedFloatValueSet() {
    return boxedFloatValueSet;
  }

  public TestDataObject setBoxedFloatValueSet(Set<Float> boxedFloatValueSet) {
    this.boxedFloatValueSet = boxedFloatValueSet;
    return this;
  }

  public Set<Double> getBoxedDoubleValueSet() {
    return boxedDoubleValueSet;
  }

  public TestDataObject setBoxedDoubleValueSet(Set<Double> boxedDoubleValueSet) {
    this.boxedDoubleValueSet = boxedDoubleValueSet;
    return this;
  }

  public Set<Character> getBoxedCharValueSet() {
    return boxedCharValueSet;
  }

  public TestDataObject setBoxedCharValueSet(Set<Character> boxedCharValueSet) {
    this.boxedCharValueSet = boxedCharValueSet;
    return this;
  }

  public Set<NestedJsonObjectDataObject> getJsonObjectDataObjectValueSet() {
    return jsonObjectDataObjectValueSet;
  }

  public TestDataObject setJsonObjectDataObjectValueSet(Set<NestedJsonObjectDataObject> jsonObjectDataObjectValueSet) {
    this.jsonObjectDataObjectValueSet = jsonObjectDataObjectValueSet;
    return this;
  }

  public Set<NestedStringDataObject> getStringDataObjectValueSet() {
    return stringDataObjectValueSet;
  }

  public TestDataObject setStringDataObjectValueSet(Set<NestedStringDataObject> stringDataObjectValueSet) {
    this.stringDataObjectValueSet = stringDataObjectValueSet;
    return this;
  }

  public Set<Buffer> getBufferValueSet() {
    return bufferValueSet;
  }

  public TestDataObject setBufferValueSet(Set<Buffer> bufferValueSet) {
    this.bufferValueSet = bufferValueSet;
    return this;
  }

  public Set<JsonObject> getJsonObjectValueSet() {
    return jsonObjectValueSet;
  }

  public TestDataObject setJsonObjectValueSet(Set<JsonObject> jsonObjectValueSet) {
    this.jsonObjectValueSet = jsonObjectValueSet;
    return this;
  }

  public Set<JsonArray> getJsonArrayValueSet() {
    return jsonArrayValueSet;
  }

  public TestDataObject setJsonArrayValueSet(Set<JsonArray> jsonArrayValueSet) {
    this.jsonArrayValueSet = jsonArrayValueSet;
    return this;
  }

  public Set<TimeUnit> getEnumValueSet() {
    return enumValueSet;
  }

  public TestDataObject setEnumValueSet(Set<TimeUnit> enumValueSet) {
    this.enumValueSet = enumValueSet;
    return this;
  }

  public Set<ZonedDateTime> getMethodMappedValueSet() {
    return methodMappedValueSet;
  }

  public TestDataObject setMethodMappedValueSet(Set<ZonedDateTime> methodMappedValueSet) {
    this.methodMappedValueSet = methodMappedValueSet;
    return this;
  }

  public Set<Object> getObjectValueSet() {
    return objectValueSet;
  }

  public TestDataObject setObjectValueSet(Set<Object> objectValueSet) {
    this.objectValueSet = objectValueSet;
    return this;
  }

  public Set<NoConverterDataObject> getNoConverterDataObjectValueSet() {
    return noConverterDataObjectValueSet;
  }

  public TestDataObject setNoConverterDataObjectValueSet(Set<NoConverterDataObject> noConverterDataObjectValueSet) {
    this.noConverterDataObjectValueSet = noConverterDataObjectValueSet;
    return this;
  }

  public Set<NoConverter2DataObject> getNoConverter2DataObjectValueSet() {
    return noConverter2DataObjectValueSet;
  }

  public TestDataObject setNoConverter2DataObjectValueSet(Set<NoConverter2DataObject> noConverter2DataObjectValueSet) {
    this.noConverter2DataObjectValueSet = noConverter2DataObjectValueSet;
    return this;
  }

  public Set<TestCustomEnum> getCustomEnumValueSet() {
    return customEnumValueSet;
  }

  public TestDataObject setCustomEnumValueSet(Set<TestCustomEnum> customEnumValueSet) {
    this.customEnumValueSet = customEnumValueSet;
    return this;
  }

  public Set<AutoMapped> getAutoMappedValueSet() {
    return autoMappedValueSet;
  }

  public TestDataObject setAutoMappedValueSet(Set<AutoMapped> autoMappedValueSet) {
    this.autoMappedValueSet = autoMappedValueSet;
    return this;
  }

  public Set<AutoMappedWithVertxGen> getAutoMappedWithVertxGenValueSet() {
    return autoMappedWithVertxGenValueSet;
  }

  public TestDataObject setAutoMappedWithVertxGenValueSet(Set<AutoMappedWithVertxGen> autoMappedValueList) {
    this.autoMappedWithVertxGenValueSet = autoMappedValueList;
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

  public List<NestedJsonObjectDataObject> getAddedJsonObjectDataObjectValues() {
    return addedJsonObjectDataObjectValues;
  }

  public TestDataObject addAddedJsonObjectDataObjectValue(NestedJsonObjectDataObject addedAggregatedDataObject) {
    this.addedJsonObjectDataObjectValues.add(addedAggregatedDataObject);
    return this;
  }

  public List<NestedStringDataObject> getAddedStringDataObjectValues() {
    return addedStringDataObjectValues;
  }

  public TestDataObject addAddedStringDataObjectValue(NestedStringDataObject addedAggregatedDataObject) {
    this.addedStringDataObjectValues.add(addedAggregatedDataObject);
    return this;
  }

  public List<Buffer> getAddedBufferValues() {
    return addedBufferValues;
  }

  public TestDataObject addAddedBufferValue(Buffer addedBuffer) {
    this.addedBufferValues.add(addedBuffer);
    return this;
  }

  public List<JsonObject> getAddedJsonObjectValues() {
    return addedJsonObjectValues;
  }

  public TestDataObject addAddedJsonObjectValue(JsonObject addedJsonObject) {
    this.addedJsonObjectValues.add(addedJsonObject);
    return this;
  }

  public List<JsonArray> getAddedJsonArrayValues() {
    return addedJsonArrayValues;
  }

  public TestDataObject addAddedJsonArrayValue(JsonArray addedJsonArray) {
    this.addedJsonArrayValues.add(addedJsonArray);
    return this;
  }

  public List<TimeUnit> getAddedEnumValues() {
    return addedEnumValues;
  }

  public TestDataObject addAddedEnumValue(TimeUnit addedHttpMethod) {
    this.addedEnumValues.add(addedHttpMethod);
    return this;
  }

  public List<ZonedDateTime> getAddedMethodMappedValues() {
    return addedMethodMappedValues;
  }

  public TestDataObject addAddedMethodMappedValue(ZonedDateTime addedDateTime) {
    this.addedMethodMappedValues.add(addedDateTime);
    return this;
  }

  public List<Object> getAddedObjectValues() {
    return addedObjectValues;
  }

  public TestDataObject addAddedObjectValue(Object addedObject) {
    this.addedObjectValues.add(addedObject);
    return this;
  }

  public List<NoConverterDataObject> getAddedNoConverterDataObjectValues() {
    return addedNoConverterDataObjectValues;
  }

  public TestDataObject addAddedNoConverterDataObjectValue(NoConverterDataObject addedObject) {
    this.addedNoConverterDataObjectValues.add(addedObject);
    return this;
  }

  public List<NoConverter2DataObject> getAddedNoConverter2DataObjectValues() {
    return addedNoConverter2DataObjectValues;
  }

  public TestDataObject addNoConverter2DataObjectValue(NoConverter2DataObject addedObject) {
    this.addedNoConverter2DataObjectValues.add(addedObject);
    return this;
  }

  public List<TestCustomEnum> getAddedCustomEnumValues() {
    return addedCustomEnumValues;
  }

  public TestDataObject addAddedCustomEnumValue(TestCustomEnum addedEnumMappeds) {
    this.addedCustomEnumValues.add(addedEnumMappeds);
    return this;
  }

  public List<AutoMapped> getAddedAutoMappedValues() {
    return addedAutoMappedValues;
  }

  public TestDataObject addAddedAutoMappedValue(AutoMapped autoMappedValue) {
    this.addedAutoMappedValues.add(autoMappedValue);
    return this;
  }

  public List<AutoMappedWithVertxGen> getAddedAutoMappedWithVertxGenValues() {
    return addedAutoMappedWithVertxGenValues;
  }

  public TestDataObject addAddedAutoMappedWithVertxGenValue(AutoMappedWithVertxGen autoMappedValue) {
    this.addedAutoMappedWithVertxGenValues.add(autoMappedValue);
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

  public Map<String, NestedJsonObjectDataObject> getJsonObjectDataObjectValueMap() {
    return jsonObjectDataObjectValueMap;
  }

  public TestDataObject setJsonObjectDataObjectValueMap(Map<String, NestedJsonObjectDataObject> nestedJsonObjectDataObjectMap) {
    this.jsonObjectDataObjectValueMap = nestedJsonObjectDataObjectMap;
    return this;
  }

  public Map<String, NestedStringDataObject> getStringDataObjectValueMap() {
    return stringDataObjectValueMap;
  }

  public TestDataObject setStringDataObjectValueMap(Map<String, NestedStringDataObject> nestedJsonObjectDataObjectMap) {
    this.stringDataObjectValueMap = nestedJsonObjectDataObjectMap;
    return this;
  }

  public Map<String, Buffer> getBufferValueMap() {
    return bufferValueMap;
  }

  public TestDataObject setBufferValueMap(Map<String, Buffer> bufferValueMap) {
    this.bufferValueMap = bufferValueMap;
    return this;
  }

  public Map<String, JsonObject> getJsonObjectValueMap() {
    return jsonObjectValueMap;
  }

  public TestDataObject setJsonObjectValueMap(Map<String, JsonObject> jsonObjectValueMap) {
    this.jsonObjectValueMap = jsonObjectValueMap;
    return this;
  }

  public Map<String, JsonArray> getJsonArrayValueMap() {
    return jsonArrayValueMap;
  }

  public TestDataObject setJsonArrayValueMap(Map<String, JsonArray> jsonArrayValueMap) {
    this.jsonArrayValueMap = jsonArrayValueMap;
    return this;
  }

  public Map<String, TimeUnit> getEnumValueMap() {
    return enumValueMap;
  }

  public TestDataObject setEnumValueMap(Map<String, TimeUnit> enumValueMap) {
    this.enumValueMap = enumValueMap;
    return this;
  }

  public Map<String, ZonedDateTime> getMethodMappedValueMap() {
    return methodMappedValueMap;
  }

  public TestDataObject setMethodMappedValueMap(Map<String, ZonedDateTime> methodMappedValueMap) {
    this.methodMappedValueMap = methodMappedValueMap;
    return this;
  }

  public Map<String, Object> getObjectValueMap() {
    return objectValueMap;
  }

  public TestDataObject setObjectValueMap(Map<String, Object> objectValueMap) {
    this.objectValueMap = objectValueMap;
    return this;
  }

  public Map<String, NoConverterDataObject> getNoConverterDataObjectValueMap() {
    return noConverterDataObjectValueMap;
  }

  public TestDataObject setNoConverterDataObjectValueMap(Map<String, NoConverterDataObject> noConverterDataObjectValueMap) {
    this.noConverterDataObjectValueMap = noConverterDataObjectValueMap;
    return this;
  }

  public Map<String, NoConverter2DataObject> getNoConverter2DataObjectValueMap() {
    return noConverter2DataObjectValueMap;
  }

  public TestDataObject setNoConverter2DataObjectValueMap(Map<String, NoConverter2DataObject> noConverter2DataObjectValueMap) {
    this.noConverter2DataObjectValueMap = noConverter2DataObjectValueMap;
    return this;
  }

  public Map<String, TestCustomEnum> getCustomEnumValueMap() {
    return customEnumValueMap;
  }

  public TestDataObject setCustomEnumValueMap(Map<String, TestCustomEnum> customEnumValueMap) {
    this.customEnumValueMap = customEnumValueMap;
    return this;
  }

  public Map<String, AutoMapped> getAutoMappedValueMap() {
    return autoMappedValueMap;
  }

  public TestDataObject setAutoMappedValueMap(Map<String, AutoMapped> autoMappedValueMap) {
    this.autoMappedValueMap = autoMappedValueMap;
    return this;
  }

  public Map<String, AutoMappedWithVertxGen> getAutoMappedWithVertxGenValueMap() {
    return autoMappedWithVertxGenValueMap;
  }

  public TestDataObject setAutoMappedWithVertxGenValueMap(Map<String, AutoMappedWithVertxGen> autoMappedWithVertxGenValueMap) {
    this.autoMappedWithVertxGenValueMap = autoMappedWithVertxGenValueMap;
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

  public Map<String, Object> getKeyedObjectValues() {
    return keyedObjectValues;
  }

  public TestDataObject addKeyedObjectValue(String key, Object value) {
    keyedObjectValues.put(key, value);
    return this;
  }

  public Map<String, NoConverterDataObject> getKeyedNoConverterDataObjectValues() {
    return keyedNoConverterDataObjectValues;
  }

  public TestDataObject addKeyedNoConverterDataObjectValue(String key, NoConverterDataObject value) {
    keyedNoConverterDataObjectValues.put(key, value);
    return this;
  }

  public Map<String, NoConverter2DataObject> getKeyedNoConverter2DataObjectValues() {
    return keyedNoConverter2DataObjectValues;
  }

  public TestDataObject addKeyedNoConverter2DataObjectValue(String key, NoConverter2DataObject value) {
    keyedNoConverter2DataObjectValues.put(key, value);
    return this;
  }

  public Map<String, TestCustomEnum> getKeyedCustomEnumValues() {
    return keyedCustomEnumValues;
  }

  public TestDataObject addKeyedCustomEnumValue(String key, TestCustomEnum value) {
    this.keyedCustomEnumValues.put(key, value);
    return this;
  }

  public Map<String, AutoMapped> getKeyedAutoMappedValues() {
    return keyedAutoMappedValues;
  }

  public TestDataObject addKeyedAutoMappedValue(String key, AutoMapped value) {
    this.keyedAutoMappedValues.put(key, value);
    return this;
  }

  public Map<String, AutoMappedWithVertxGen> getKeyedAutoMappedWithVertxGenValues() {
    return keyedAutoMappedWithVertxGenValues;
  }

  public TestDataObject addKeyedAutoMappedWithVertxGenValue(String key, AutoMappedWithVertxGen value) {
    this.keyedAutoMappedWithVertxGenValues.put(key, value);
    return this;
  }
}
