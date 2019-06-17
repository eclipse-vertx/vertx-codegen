package io.vertx.codegen.type;

public class DataObjectAnnotatedInfo {
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
