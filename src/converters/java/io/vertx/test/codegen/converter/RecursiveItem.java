package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;

// Temporary Test Object, maybe will switch to test with TestDataObject
@DataObject(generateConverter = true, protoConverter = true)
public class RecursiveItem {

  private String id;
  private RecursiveItem childA;
  private RecursiveItem childB;
  private RecursiveItem childC;

  public RecursiveItem() {
  }

  public RecursiveItem(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RecursiveItem getChildA() {
    return childA;
  }

  public void setChildA(RecursiveItem childA) {
    this.childA = childA;
  }

  public RecursiveItem getChildB() {
    return childB;
  }

  public void setChildB(RecursiveItem childB) {
    this.childB = childB;
  }

  public RecursiveItem getChildC() {
    return childC;
  }

  public void setChildC(RecursiveItem childC) {
    this.childC = childC;
  }

  @Override
  public String toString() {
    return id;
  }
}
