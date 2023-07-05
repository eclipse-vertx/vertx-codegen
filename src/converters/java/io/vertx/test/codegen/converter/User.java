package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// Temporary Test Object, maybe will switch to test with TestDataObject
@DataObject(generateConverter = true, protoConverter = true)
public class User {
  private String userName;
  private Integer age;
  private List<Integer> integerListField;
  private List<Address> structListField;
  private List<ZonedDateTime> zonedDateTimeListField;
  private Address address;
  private Double doubleField;
  private Long longField;
  private Boolean boolField;
  private Short shortField;
  private Character charField;
  private Map<String, String> stringValueMap;
  private Map<String, Integer> integerValueMap;
  private Map<String, Address> structValueMap;
  private Map<String, ZonedDateTime> zonedDateTimeValueMap;
  private ZonedDateTime zonedDateTimeField;
  private Instant instantField;
  private JsonObject jsonObjectField;
  private int primitiveInt = 0;

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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Double getDoubleField() {
    return doubleField;
  }

  public void setDoubleField(Double doubleField) {
    this.doubleField = doubleField;
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

  public int getPrimitiveInt() {
    return primitiveInt;
  }

  public void setPrimitiveInt(int primitiveInt) {
    this.primitiveInt = primitiveInt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userName, user.userName) && Objects.equals(age, user.age) && Objects.equals(integerListField, user.integerListField) && Objects.equals(structListField, user.structListField) && Objects.equals(zonedDateTimeListField, user.zonedDateTimeListField) && Objects.equals(address, user.address) && Objects.equals(doubleField, user.doubleField) && Objects.equals(longField, user.longField) && Objects.equals(boolField, user.boolField) && Objects.equals(shortField, user.shortField) && Objects.equals(charField, user.charField) && Objects.equals(stringValueMap, user.stringValueMap) && Objects.equals(integerValueMap, user.integerValueMap) && Objects.equals(structValueMap, user.structValueMap) && Objects.equals(zonedDateTimeValueMap, user.zonedDateTimeValueMap) && Objects.equals(zonedDateTimeField, user.zonedDateTimeField) && Objects.equals(instantField, user.instantField) && Objects.equals(jsonObjectField, user.jsonObjectField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, age, integerListField, structListField, zonedDateTimeListField, address, doubleField, longField, boolField, shortField, charField, stringValueMap, integerValueMap, structValueMap, zonedDateTimeValueMap, zonedDateTimeField, instantField, jsonObjectField);
  }
}
