package io.vertx.codegen.type;

import io.vertx.codegen.MapperKind;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

/**
 * DataObject could be of two types: static methods annotated with {@link io.vertx.codegen.annotations.Mapper} and the one that is annotated with {@link io.vertx.codegen.annotations.DataObject}
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author <a href="slinkydeveloper.com">Francesco Guardiani</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  private final MapperInfo serializer;
  private final MapperInfo deserializer;

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, MapperInfo serializer, MapperInfo deserializer, TypeInfo jsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.serializer = serializer;
    this.deserializer = deserializer;
  }

  public boolean isDataObjectAnnotatedType() {
    return deserializer != null && deserializer.getKind() == MapperKind.SELF;
  }

  public TypeInfo getTargetType() {
    return deserializer != null ? deserializer.getTargetType() : serializer.getTargetType();
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
