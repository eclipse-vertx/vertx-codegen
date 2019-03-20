package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  private final String jsonEncoderFQCN;
  private final String jsonDecoderFQCN;
  private final TypeInfo targetJsonType;

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, String jsonEncoderFQCN, String jsonDecoderFQCN, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonEncoderFQCN = jsonEncoderFQCN;
    this.jsonDecoderFQCN = jsonDecoderFQCN;
    this.targetJsonType = targetJsonType;
  }

  public String getJsonEncoderFQCN() {
    return jsonEncoderFQCN;
  }

  public String getJsonDecoderFQCN() {
    return jsonDecoderFQCN;
  }

  public TypeInfo getTargetJsonType() {
    return targetJsonType;
  }

  public boolean hasJsonEncoder() {
    return jsonEncoderFQCN != null;
  }

  public boolean hasJsonDecoder() {
    return jsonDecoderFQCN != null;
  }
}
