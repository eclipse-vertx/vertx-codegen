package io.vertx.codegen.type;

public class JsonMapperInfo {
  private final String jsonSerializerSimpleName;
  private final String jsonSerializerPackage;
  private final String jsonSerializerEnclosingClass;

  private final String jsonDeserializerSimpleName;
  private final String jsonDeserializerEnclosingClass;
  private final String jsonDeserializerPackage;

  public JsonMapperInfo(String jsonSerializerSimpleName, String jsonSerializerPackage, String jsonSerializerEnclosingClass, String jsonDeserializerSimpleName, String jsonDeserializerPackage, String jsonDeserializerEnclosingClass) {
    this.jsonSerializerSimpleName = jsonSerializerSimpleName;
    this.jsonSerializerPackage = jsonSerializerPackage;
    this.jsonSerializerEnclosingClass = jsonSerializerEnclosingClass;

    this.jsonDeserializerSimpleName = jsonDeserializerSimpleName;
    this.jsonDeserializerEnclosingClass = jsonDeserializerEnclosingClass;
    this.jsonDeserializerPackage = jsonDeserializerPackage;
  }

  public String getJsonSerializerSimpleName() {
    return jsonSerializerSimpleName;
  }

  public String getJsonSerializerPackage() {
    return jsonSerializerPackage;
  }

  public String getJsonSerializerEnclosingClass() {
    return jsonSerializerEnclosingClass;
  }

  public String getJsonSerializerFQCN() {
    if (jsonSerializerSimpleName != null) {
      if (jsonSerializerEnclosingClass != null) {
        return jsonSerializerPackage + "." + jsonSerializerEnclosingClass + "." + jsonSerializerSimpleName;
      } else {
        return jsonSerializerPackage + "." + jsonSerializerSimpleName;
      }
    } else {
      return null;
    }
  }

  public String getJsonDeserializerSimpleName() {
    return jsonDeserializerSimpleName;
  }

  public String getJsonDeserializerEnclosingClass() {
    return jsonDeserializerEnclosingClass;
  }

  public String getJsonDeserializerPackage() {
    return jsonDeserializerPackage;
  }

  public String getJsonDeserializerFQCN() {
    if (jsonDeserializerSimpleName != null)
      if (jsonDeserializerEnclosingClass != null)
        return jsonDeserializerPackage + "." + jsonDeserializerEnclosingClass + "." + jsonDeserializerSimpleName;
      else
        return jsonDeserializerPackage + "." + jsonDeserializerSimpleName;
    else return null;
  }
}
