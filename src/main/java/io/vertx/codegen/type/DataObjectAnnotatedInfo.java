package io.vertx.codegen.type;

public class DataObjectAnnotatedInfo {

  private final boolean serializable;
  private final boolean deserializable;

  public DataObjectAnnotatedInfo(boolean serializable, boolean deserializable) {
    this.serializable = serializable;
    this.deserializable = deserializable;
  }

  public boolean isSerializable() {
    return serializable;
  }

  public boolean isDeserializable() {
    return deserializable;
  }
}
