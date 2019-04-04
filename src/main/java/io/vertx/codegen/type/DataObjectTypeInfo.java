package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  private final String jsonEncoderSimpleName;
  private final String jsonEncoderPackage;
  private final String jsonDecoderSimpleName;
  private final String jsonDecoderPackage;
  private final TypeInfo targetJsonType;

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, String jsonEncoderSimpleName, String jsonEncoderPackage, String jsonDecoderSimpleName, String jsonDecoderPackage, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonEncoderSimpleName = jsonEncoderSimpleName;
    this.jsonEncoderPackage = jsonEncoderPackage;
    this.jsonDecoderSimpleName = jsonDecoderSimpleName;
    this.jsonDecoderPackage = jsonDecoderPackage;
    this.targetJsonType = targetJsonType;
  }

  public String getJsonEncoderFQCN() {
    if (hasJsonEncoder())
      return jsonEncoderPackage + "." + jsonEncoderSimpleName;
    else return null;
  }

  public String getJsonEncoderSimpleName() {
    return jsonEncoderSimpleName;
  }

  public String getJsonEncoderPackage() {
    return jsonEncoderPackage;
  }

  public String getJsonDecoderFQCN() {
    if (hasJsonDecoder())
      return jsonDecoderPackage + "." + jsonDecoderSimpleName;
    else return null;
  }

  public String getJsonDecoderSimpleName() {
    return jsonDecoderSimpleName;
  }

  public String getJsonDecoderPackage() {
    return jsonDecoderPackage;
  }

  public TypeInfo getTargetJsonType() {
    return targetJsonType;
  }

  public boolean hasJsonEncoder() {
    return getJsonEncoderSimpleName() != null;
  }

  public boolean hasJsonDecoder() {
    return getJsonDecoderSimpleName() != null;
  }
}
