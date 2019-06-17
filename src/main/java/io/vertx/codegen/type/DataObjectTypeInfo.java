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

  public static class JsonCodecInfo {
    private final String jsonEncoderSimpleName;
    private final String jsonEncoderPackage;
    private final String jsonEncoderEnclosingClass;

    private final String jsonDecoderSimpleName;
    private final String jsonDecoderEnclosingClass;
    private final String jsonDecoderPackage;

    public JsonCodecInfo(String jsonEncoderSimpleName, String jsonEncoderPackage, String jsonEncoderEnclosingClass, String jsonDecoderSimpleName, String jsonDecoderPackage, String jsonDecoderEnclosingClass) {
      this.jsonEncoderSimpleName = jsonEncoderSimpleName;
      this.jsonEncoderPackage = jsonEncoderPackage;
      this.jsonEncoderEnclosingClass = jsonEncoderEnclosingClass;

      this.jsonDecoderSimpleName = jsonDecoderSimpleName;
      this.jsonDecoderEnclosingClass = jsonDecoderEnclosingClass;
      this.jsonDecoderPackage = jsonDecoderPackage;
    }

    public String getJsonEncoderSimpleName() {
      return jsonEncoderSimpleName;
    }

    public String getJsonEncoderPackage() {
      return jsonEncoderPackage;
    }

    public String getJsonEncoderEnclosingClass() {
      return jsonEncoderEnclosingClass;
    }

    public String getJsonEncoderFQCN() {
      if (jsonEncoderSimpleName != null)
        if (jsonEncoderEnclosingClass != null)
          return jsonEncoderPackage + "." + jsonEncoderEnclosingClass + "." + jsonEncoderSimpleName;
        else
          return jsonEncoderPackage + "." + jsonEncoderSimpleName;
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

    public String getJsonDecoderFQCN() {
      if (jsonDecoderSimpleName != null)
        if (jsonDecoderEnclosingClass != null)
          return jsonDecoderPackage + "." + jsonDecoderEnclosingClass + "." + jsonDecoderSimpleName;
        else
          return jsonDecoderPackage + "." + jsonDecoderSimpleName;
      else return null;
    }
  }

  public static class DataObjectAnnotatedInfo {
    private final boolean isDecodeUsingConstructor;
    private final boolean encodable;
    private final boolean decodable;

    public DataObjectAnnotatedInfo(boolean isDecodeUsingConstructor, boolean encodable, boolean decodable) {
      this.isDecodeUsingConstructor = isDecodeUsingConstructor;
      this.encodable = encodable;
      this.decodable = decodable;
    }

    public boolean isDecodableUsingConstructor() {
      return isDecodeUsingConstructor;
    }

    public boolean isEncodable() {
      return encodable;
    }

    public boolean isDecodable() {
      return decodable;
    }
  }

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
    return match(jsonCodecInfo -> jsonCodecInfo.jsonEncoderSimpleName != null, DataObjectAnnotatedInfo::isEncodable);
  }

  public boolean isDecodable() {
    return match(jsonCodecInfo -> jsonCodecInfo.jsonDecoderSimpleName != null, DataObjectAnnotatedInfo::isDecodable);
  }
}
