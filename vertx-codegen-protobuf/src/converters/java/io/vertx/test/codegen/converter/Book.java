package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.ProtobufField;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

import java.util.Objects;

@DataObject
@ProtobufGen(fieldNumberStrategy = FieldNumberStrategy.SEGMENTED, reservedFieldNumbers = 2, reservedFieldNames = "title")
public class Book {
  private String name;
  private String author;
  @ProtobufField(10)
  private String isbn;
  @ProtobufField(20)
  private String genre;

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

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn) && Objects.equals(genre, book.genre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, author, isbn, genre);
  }
}
