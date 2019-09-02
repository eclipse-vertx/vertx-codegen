package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.core.spi.json.JsonMapper;

import java.util.List;
import java.util.function.Function;

/**
 * DataObject could be of two types: the one that has a {@link JsonMapper} and the one that is annotated with {@link io.vertx.codegen.annotations.DataObject}
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author <a href="slinkydeveloper.com">Francesco Guardiani</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  private final JsonMapperInfo jsonMapperInfo;
  private final DataObjectAnnotatedInfo dataObjectAnnotatedInfo;

  private final TypeInfo targetJsonType;

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, JsonMapperInfo jsonMapperInfo, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonMapperInfo = jsonMapperInfo;
    this.dataObjectAnnotatedInfo = null;
    this.targetJsonType = targetJsonType;
  }

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, DataObjectAnnotatedInfo dataObjectAnnotatedInfo, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonMapperInfo = null;
    this.dataObjectAnnotatedInfo = dataObjectAnnotatedInfo;
    this.targetJsonType = targetJsonType;
  }

  public TypeInfo getTargetJsonType() {
    return targetJsonType;
  }

  public boolean hasJsonMapper() {
    return jsonMapperInfo != null;
  }

  public boolean isDataObjectAnnotatedType() {
    return dataObjectAnnotatedInfo != null;
  }

  public JsonMapperInfo getJsonMapperInfo() {
    return jsonMapperInfo;
  }

  public DataObjectAnnotatedInfo getDataObjectAnnotatedInfo() {
    return dataObjectAnnotatedInfo;
  }

  public <T> T match(Function<JsonMapperInfo, T> hasJsonMapper, Function<DataObjectAnnotatedInfo, T> isDataObjectAnnotated) {
    return hasJsonMapper() ? hasJsonMapper.apply(jsonMapperInfo) : isDataObjectAnnotated.apply(dataObjectAnnotatedInfo);
  }

  public boolean isSerializable() {
    return match(jsonMapperInfo -> jsonMapperInfo.getJsonSerializerSimpleName() != null, DataObjectAnnotatedInfo::isSerializable);
  }

  public boolean isDeserializable() {
    return match(jsonMapperInfo -> jsonMapperInfo.getJsonDeserializerSimpleName() != null, DataObjectAnnotatedInfo::isDeserializable);
  }
}
