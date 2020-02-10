package io.vertx.codegen.type;

import io.vertx.codegen.MapperKind;

/**
 * DataObject could be of two types: static methods annotated with {@link io.vertx.codegen.annotations.Mapper} and the one that is annotated with {@link io.vertx.codegen.annotations.DataObject}
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
