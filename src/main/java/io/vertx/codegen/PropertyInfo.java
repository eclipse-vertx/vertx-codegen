package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PropertyInfo {

  final boolean declared;
  final String name;
  final Doc doc;
  final TypeInfo type;
  final String methodName;
  final boolean array;
  final boolean adder;

  public PropertyInfo(boolean declared, String name, Doc doc, TypeInfo type, String methodName, boolean array, boolean adder) {
    this.declared = declared;
    this.name = name;
    this.doc = doc;
    this.type = type;
    this.methodName = methodName;
    this.array = array;
    this.adder = adder;
  }

  /**
   * @return true if the property is declared by the its data object, that means it does not override the same property
   *   from other data object ancestors
   */
  public boolean isDeclared() {
    return declared;
  }

  public Doc getDoc() {
    return doc;
  }

  public String getName() {
    return name;
  }

  public TypeInfo getType() {
    return type;
  }

  public String getMethodName() {
    return methodName;
  }

  public boolean isArray() {
    return array;
  }

  public boolean isAdder() {
    return adder;
  }
}
