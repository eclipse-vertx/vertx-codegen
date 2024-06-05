package io.vertx.codegen.processor;

import io.vertx.codegen.processor.doc.Doc;
import io.vertx.codegen.processor.type.TypeInfo;

public class ConstantInfo {

  private Doc doc;
  private String name;
  private TypeInfo type;

  public ConstantInfo(Doc doc, String name, TypeInfo type) {
    this.doc = doc;
    this.name = name;
    this.type = type;
  }

  public Doc getDoc() {
    return doc;
  }

  public ConstantInfo setDoc(Doc doc) {
    this.doc = doc;
    return this;
  }

  public String getName() {
    return name;
  }

  public ConstantInfo setName(String name) {
    this.name = name;
    return this;
  }

  public TypeInfo getType() {
    return type;
  }

  public ConstantInfo setType(TypeInfo type) {
    this.type = type;
    return this;
  }

  public ConstantInfo copy() {
    return new ConstantInfo(doc, name, type);
  }
}
