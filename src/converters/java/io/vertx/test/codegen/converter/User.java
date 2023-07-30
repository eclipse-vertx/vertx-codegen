package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;

import java.util.List;

// Temporary Test Object, maybe will switch to test with TestDataObject
@DataObject(generateConverter = true, protoConverter = true)
public class User {
  private String userName;
  private Integer age;
  private List<Integer> integerListField;
  private Address address;

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
}
