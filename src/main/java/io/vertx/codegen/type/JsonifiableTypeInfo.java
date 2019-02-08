package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import javax.lang.model.type.DeclaredType;
import java.util.List;

public class JsonifiableTypeInfo extends ClassTypeInfo {

  DeclaredType jsonCodec;

  public JsonifiableTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, DeclaredType jsonCodec) {
    super(ClassKind.JSONIFIABLE, name, module, nullable, params);
    this.jsonCodec = jsonCodec;
  }

  public DeclaredType getJsonCodec() {
    return jsonCodec;
  }
}
