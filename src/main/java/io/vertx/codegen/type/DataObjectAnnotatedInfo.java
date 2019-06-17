package io.vertx.codegen.type;

public class DataObjectAnnotatedInfo {
  private final boolean encodable;
  private final boolean decodable;

  public DataObjectAnnotatedInfo(boolean encodable, boolean decodable) {
    this.encodable = encodable;
    this.decodable = decodable;
  }

  public boolean isEncodable() {
    return encodable;
  }

  public boolean isDecodable() {
    return decodable;
  }
}
