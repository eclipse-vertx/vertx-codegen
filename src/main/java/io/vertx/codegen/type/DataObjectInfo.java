package io.vertx.codegen.type;

import io.vertx.codegen.MapperKind;

/**
 * Data object info.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author <a href="slinkydeveloper.com">Francesco Guardiani</a>
 */
public class DataObjectInfo {

  private final MapperInfo serializer;
  private final MapperInfo deserializer;

  public DataObjectInfo(MapperInfo serializer, MapperInfo deserializer) {
    this.serializer = serializer;
    this.deserializer = deserializer;
  }

  public boolean isAnnotated() {
    return deserializer != null && deserializer.getKind() == MapperKind.SELF || serializer != null && serializer.getKind() == MapperKind.SELF;
  }

  public TypeInfo getJsonType() {
    return deserializer != null ? deserializer.getJsonType() : serializer.getJsonType();
  }

  public MapperInfo getSerializer() {
    return serializer;
  }

  public MapperInfo getDeserializer() {
    return deserializer;
  }

  public boolean isSerializable() {
    return serializer != null;
  }

  public boolean isDeserializable() {
    return deserializer != null;
  }
}
