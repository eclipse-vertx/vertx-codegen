package io.vertx.codegen.type;

import io.vertx.codegen.MapperKind;

/**
 * Data object info.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author <a href="slinkydeveloper.com">Francesco Guardiani</a>
 */
public class DataObjectInfo {

  private final boolean annotated;
  private final MapperInfo serializer;
  private final MapperInfo deserializer;

  public DataObjectInfo(boolean annotated, MapperInfo serializer, MapperInfo deserializer) {
    this.annotated = annotated;
    this.serializer = serializer;
    this.deserializer = deserializer;
  }

  public boolean isAnnotated() {
    return annotated;
  }

  public TypeInfo getJsonType() {
    if (deserializer != null) {
      return deserializer.getJsonType();
    } else if (serializer != null) {
      return serializer.getJsonType();
    } else {
      return null;
    }
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
