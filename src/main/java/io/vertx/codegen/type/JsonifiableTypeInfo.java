package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

public class JsonifiableTypeInfo extends ClassTypeInfo {

  ClassTypeInfo jsonCodec;
  TypeInfo targetJsonType;

  public JsonifiableTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, ClassTypeInfo jsonCodec, TypeInfo targetJsonType) {
    super(ClassKind.JSONIFIABLE, name, module, nullable, params);
    this.jsonCodec = jsonCodec;
    this.targetJsonType = targetJsonType;
  }

  public ClassTypeInfo getJsonCodec() {
    return jsonCodec;
  }

  public TypeInfo getTargetJsonType() {
    return targetJsonType;
  }
}
