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
  private final String jsonEncoderEnclosingClass;
  private final String jsonDecoderSimpleName;
  private final String jsonDecoderEnclosingClass;
  private final String jsonDecoderPackage;
  private final TypeInfo targetJsonType;

  public DataObjectTypeInfo(String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, String jsonEncoderSimpleName, String jsonEncoderEnclosingClass, String jsonEncoderPackage, String jsonDecoderSimpleName, String jsonDecoderEnclosingClass, String jsonDecoderPackage, TypeInfo targetJsonType) {
    super(ClassKind.DATA_OBJECT, name, module, nullable, params);
    this.jsonEncoderSimpleName = jsonEncoderSimpleName;
    this.jsonEncoderEnclosingClass = jsonEncoderEnclosingClass;
    this.jsonEncoderPackage = jsonEncoderPackage;
    this.jsonDecoderSimpleName = jsonDecoderSimpleName;
    this.jsonDecoderEnclosingClass = jsonDecoderEnclosingClass;
    this.jsonDecoderPackage = jsonDecoderPackage;
    this.targetJsonType = targetJsonType;
  }

  public String getJsonEncoderFQCN() {
    if (hasJsonEncoder())
      if (getJsonEncoderEnclosingClass() != null)
        return jsonEncoderPackage + "." + getJsonEncoderEnclosingClass() + "." + jsonEncoderSimpleName;
      else
        return jsonEncoderPackage + "." + jsonEncoderSimpleName;
    else return null;
  }

  public String getJsonEncoderSimpleName() {
    return jsonEncoderSimpleName;
  }

  public String getJsonEncoderEnclosingClass() {
    return jsonEncoderEnclosingClass;
  }

  public String getJsonEncoderPackage() {
    return jsonEncoderPackage;
  }

  public String getJsonDecoderFQCN() {
    if (hasJsonDecoder())
      if (getJsonDecoderEnclosingClass() != null)
        return jsonDecoderPackage + "." + getJsonDecoderEnclosingClass() + "." + jsonDecoderSimpleName;
      else
        return jsonDecoderPackage + "." + jsonDecoderSimpleName;
    else return null;
  }

  public String getJsonDecoderSimpleName() {
    return jsonDecoderSimpleName;
  }

  public String getJsonDecoderEnclosingClass() {
    return jsonDecoderEnclosingClass;
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
