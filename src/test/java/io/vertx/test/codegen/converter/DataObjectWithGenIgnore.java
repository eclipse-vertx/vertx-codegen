package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.json.JsonObject;

@DataObject(generateHashCode = true, generateEquals = true)
public class DataObjectWithGenIgnore {

  private String firstName;

  private String lastName;

  public DataObjectWithGenIgnore() {
  }

  public DataObjectWithGenIgnore(JsonObject json) {
  }

  public DataObjectWithGenIgnore(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @GenIgnore
  public String getFullName() {
    return String.format("%s %s", firstName, lastName);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
