package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;

@DataObject(generateConverter = true, protoConverter = true)
public class User {
  private String userName;
  private Integer age;

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
}
