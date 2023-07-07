package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@SomeAnnotation
@DataObject(generateConverter = true)
public class DataObjectWithAnnotatedField {

  @SomeAnnotation
  private Long id;

  private String name;

  private String author;

  @SomeAnnotation
  private String fieldWithMethodAnnotation;

  // Mandatory for JPA entities
  protected DataObjectWithAnnotatedField() {
  }

  public DataObjectWithAnnotatedField(String name, String author) {
    this.name = name;
    this.author = author;
  }

  public DataObjectWithAnnotatedField(JsonObject jsonObject) {
    // Not important here
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @SomeMethodAnnotation
  public String getFieldWithMethodAnnotation() {
    return fieldWithMethodAnnotation;
  }

  public void setFieldWithMethodAnnotation(String fieldWithMethodAnnotation) {
    this.fieldWithMethodAnnotation = fieldWithMethodAnnotation;
  }

  public JsonObject toJson() {
    // Not important here.
    return new JsonObject();
  }

  @Override
  public String toString() {
    return "Book{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", author='" + author + '\'' +
      '}';
  }

}
