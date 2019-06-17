package io.vertx.codegen.type;

public class JsonCodecInfo {
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
