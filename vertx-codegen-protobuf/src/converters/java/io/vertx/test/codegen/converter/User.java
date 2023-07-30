package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@DataObject
@ProtobufGen
public class User {
  private String userName;
  private Integer age;
  private List<Integer> integerListField;
  private List<Address> structListField;
  private List<ZonedDateTime> zonedDateTimeListField;
  private List<JsonObject> jsonListField;
  private Address address;
  private Byte byteField;
  private Double doubleField;
  private Float floatField;
  private Long longField;
  private Boolean boolField;
  private Short shortField;
  private Character charField;
  private Map<String, String> stringValueMap;
  private Map<String, Integer> integerValueMap;
  private Map<String, Address> structValueMap;
  private Map<String, JsonObject> jsonValueMap;
  private Map<String, ZonedDateTime> zonedDateTimeValueMap;
  private ZonedDateTime zonedDateTimeField;
  private Instant instantField;
  private JsonObject jsonObjectField;
  private boolean primitiveBoolean;
  private byte primitiveByte;
  private short primitiveShort;
  private int primitiveInt;
  private long primitiveLong;
  private float primitiveFloat;
  private double primitiveDouble;
  private char primitiveChar;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public List<Integer> getIntegerListField() {
    return integerListField;
  }

  public void setIntegerListField(List<Integer> integerListField) {
    this.integerListField = integerListField;
  }

  public List<Address> getStructListField() {
    return structListField;
  }

  public void setStructListField(List<Address> structListField) {
    this.structListField = structListField;
  }

  public List<ZonedDateTime> getZonedDateTimeListField() {
    return zonedDateTimeListField;
  }

  public void setZonedDateTimeListField(List<ZonedDateTime> zonedDateTimeListField) {
    this.zonedDateTimeListField = zonedDateTimeListField;
  }

  public List<JsonObject> getJsonListField() {
    return jsonListField;
  }

  public void setJsonListField(List<JsonObject> jsonListField) {
    this.jsonListField = jsonListField;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Byte getByteField() {
    return byteField;
  }

  public void setByteField(Byte byteField) {
    this.byteField = byteField;
  }

  public Double getDoubleField() {
    return doubleField;
  }

  public void setDoubleField(Double doubleField) {
    this.doubleField = doubleField;
  }

  public Float getFloatField() {
    return floatField;
  }

  public void setFloatField(Float floatField) {
    this.floatField = floatField;
  }

  public Long getLongField() {
    return longField;
  }

  public void setLongField(Long longField) {
    this.longField = longField;
  }

  public Boolean getBoolField() {
    return boolField;
  }

  public void setBoolField(Boolean boolField) {
    this.boolField = boolField;
  }

  public Short getShortField() {
    return shortField;
  }

  public void setShortField(Short shortField) {
    this.shortField = shortField;
  }

  public Character getCharField() {
    return charField;
  }

  public void setCharField(Character charField) {
    this.charField = charField;
  }

  public Map<String, String> getStringValueMap() {
    return stringValueMap;
  }

  public void setStringValueMap(Map<String, String> stringValueMap) {
    this.stringValueMap = stringValueMap;
  }

  public Map<String, Integer> getIntegerValueMap() {
    return integerValueMap;
  }

  public void setIntegerValueMap(Map<String, Integer> integerValueMap) {
    this.integerValueMap = integerValueMap;
  }

  public Map<String, Address> getStructValueMap() {
    return structValueMap;
  }

  public void setStructValueMap(Map<String, Address> structValueMap) {
    this.structValueMap = structValueMap;
  }

  public Map<String, JsonObject> getJsonValueMap() {
    return jsonValueMap;
  }

  public void setJsonValueMap(Map<String, JsonObject> jsonValueMap) {
    this.jsonValueMap = jsonValueMap;
  }

  public Map<String, ZonedDateTime> getZonedDateTimeValueMap() {
    return zonedDateTimeValueMap;
  }

  public void setZonedDateTimeValueMap(Map<String, ZonedDateTime> zonedDateTimeValueMap) {
    this.zonedDateTimeValueMap = zonedDateTimeValueMap;
  }

  public ZonedDateTime getZonedDateTimeField() {
    return zonedDateTimeField;
  }

  public void setZonedDateTimeField(ZonedDateTime zonedDateTimeField) {
    this.zonedDateTimeField = zonedDateTimeField;
  }

  public Instant getInstantField() {
    return instantField;
  }

  public void setInstantField(Instant instantField) {
    this.instantField = instantField;
  }

  public JsonObject getJsonObjectField() {
    return jsonObjectField;
  }

  public void setJsonObjectField(JsonObject jsonObjectField) {
    this.jsonObjectField = jsonObjectField;
  }

  public boolean isPrimitiveBoolean() {
    return primitiveBoolean;
  }

  public void setPrimitiveBoolean(boolean primitiveBoolean) {
    this.primitiveBoolean = primitiveBoolean;
  }

  public byte getPrimitiveByte() {
    return primitiveByte;
  }

  public void setPrimitiveByte(byte primitiveByte) {
    this.primitiveByte = primitiveByte;
  }

  public short getPrimitiveShort() {
    return primitiveShort;
  }

  public void setPrimitiveShort(short primitiveShort) {
    this.primitiveShort = primitiveShort;
  }

  public int getPrimitiveInt() {
    return primitiveInt;
  }

  public void setPrimitiveInt(int primitiveInt) {
    this.primitiveInt = primitiveInt;
  }

  public long getPrimitiveLong() {
    return primitiveLong;
  }

  public void setPrimitiveLong(long primitiveLong) {
    this.primitiveLong = primitiveLong;
  }

  public float getPrimitiveFloat() {
    return primitiveFloat;
  }

  public void setPrimitiveFloat(float primitiveFloat) {
    this.primitiveFloat = primitiveFloat;
  }

  public double getPrimitiveDouble() {
    return primitiveDouble;
  }

  public void setPrimitiveDouble(double primitiveDouble) {
    this.primitiveDouble = primitiveDouble;
  }

  public char getPrimitiveChar() {
    return primitiveChar;
  }

  public void setPrimitiveChar(char primitiveChar) {
    this.primitiveChar = primitiveChar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return primitiveBoolean == user.primitiveBoolean && primitiveByte == user.primitiveByte && primitiveShort == user.primitiveShort && primitiveInt == user.primitiveInt && primitiveLong == user.primitiveLong && Float.compare(user.primitiveFloat, primitiveFloat) == 0 && Double.compare(user.primitiveDouble, primitiveDouble) == 0 && primitiveChar == user.primitiveChar && Objects.equals(userName, user.userName) && Objects.equals(age, user.age) && Objects.equals(integerListField, user.integerListField) && Objects.equals(structListField, user.structListField) && Objects.equals(zonedDateTimeListField, user.zonedDateTimeListField) && Objects.equals(jsonListField, user.jsonListField) && Objects.equals(address, user.address) && Objects.equals(byteField, user.byteField) && Objects.equals(doubleField, user.doubleField) && Objects.equals(longField, user.longField) && Objects.equals(boolField, user.boolField) && Objects.equals(shortField, user.shortField) && Objects.equals(charField, user.charField) && Objects.equals(stringValueMap, user.stringValueMap) && Objects.equals(integerValueMap, user.integerValueMap) && Objects.equals(structValueMap, user.structValueMap) && Objects.equals(jsonValueMap, user.jsonValueMap) && Objects.equals(zonedDateTimeValueMap, user.zonedDateTimeValueMap) && Objects.equals(zonedDateTimeField, user.zonedDateTimeField) && Objects.equals(instantField, user.instantField) && Objects.equals(jsonObjectField, user.jsonObjectField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, age, integerListField, structListField, zonedDateTimeListField, jsonListField, address, byteField, doubleField, longField, boolField, shortField, charField, stringValueMap, integerValueMap, structValueMap, jsonValueMap, zonedDateTimeValueMap, zonedDateTimeField, instantField, jsonObjectField, primitiveBoolean, primitiveByte, primitiveShort, primitiveInt, primitiveLong, primitiveFloat, primitiveDouble, primitiveChar);
  }
}
