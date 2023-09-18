package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.ProtobufField;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

import java.util.Objects;

@DataObject
@ProtobufGen(fieldNumberStrategy = FieldNumberStrategy.MANUAL)
public class Person {
  @ProtobufField(2)
  private String name;
  @ProtobufField(4)
  private int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(name, person.name) && Objects.equals(age, person.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }
}
