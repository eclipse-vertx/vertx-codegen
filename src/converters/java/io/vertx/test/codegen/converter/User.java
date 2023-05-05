package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;

import java.util.List;
import java.util.Map;

// Temporary Test Object, maybe will switch to test with TestDataObject
@DataObject(generateConverter = true, protoConverter = true)
public class User {
  private String userName;
  private Integer age;
  private List<Integer> integerListField;
  private Address address;
  private Double doubleField;
  private Long longField;
  private Boolean boolField;
  private Short shortField;
  private Character charField;
  private Map<String, String> stringValueMap;
  private Map<String, Integer> integerValueMap;

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
}
