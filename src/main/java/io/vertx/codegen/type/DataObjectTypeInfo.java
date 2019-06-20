package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;
import java.util.function.Function;

/**
 * DataObject could be of two types: the one that has a {@link io.vertx.core.spi.json.JsonCodec} and the one that is annotated with {@link io.vertx.codegen.annotations.DataObject}
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author <a href="slinkydeveloper.com">Francesco Guardiani</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  private final JsonCodecInfo jsonCodecInfo;
  private final DataObjectAnnotatedInfo dataObjectAnnotatedInfo;

  private final TypeInfo targetJsonType;

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, JsonCodecInfo jsonCodecInfo, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonCodecInfo = jsonCodecInfo;
    this.dataObjectAnnotatedInfo = null;
    this.targetJsonType = targetJsonType;
  }

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, DataObjectAnnotatedInfo dataObjectAnnotatedInfo, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonCodecInfo = null;
    this.dataObjectAnnotatedInfo = dataObjectAnnotatedInfo;
    this.targetJsonType = targetJsonType;
  }

  public TypeInfo getTargetJsonType() {
    return targetJsonType;
  }

  public boolean hasJsonCodec() {
    return jsonCodecInfo != null;
  }

  public boolean isDataObjectAnnotatedType() {
    return dataObjectAnnotatedInfo != null;
  }

  public JsonCodecInfo getJsonCodecInfo() {
    return jsonCodecInfo;
  }

  public DataObjectAnnotatedInfo getDataObjectAnnotatedInfo() {
    return dataObjectAnnotatedInfo;
  }

  public <T> T match(Function<JsonCodecInfo, T> hasJsonCodec, Function<DataObjectAnnotatedInfo, T> isDataObjectAnnotated) {
    return hasJsonCodec() ? hasJsonCodec.apply(jsonCodecInfo) : isDataObjectAnnotated.apply(dataObjectAnnotatedInfo);
  }

  public boolean isEncodable() {
    return match(jsonCodecInfo -> jsonCodecInfo.getJsonEncoderSimpleName() != null, DataObjectAnnotatedInfo::isEncodable);
  }

  public boolean isDecodable() {
    return match(jsonCodecInfo -> jsonCodecInfo.getJsonDecoderSimpleName() != null, DataObjectAnnotatedInfo::isDecodable);
  }
}
