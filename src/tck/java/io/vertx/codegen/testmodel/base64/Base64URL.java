package io.vertx.codegen.testmodel.base64;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

@DataObject(base64Type = "base64url", generateConverter = true)
public class Base64URL {

  Buffer data;

  public Base64URL() {}
  public Base64URL(JsonObject jsonObject) {
    Base64URLConverter.fromJson(jsonObject, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    Base64URLConverter.toJson(this, json);
    return json;
  }

  public Buffer getData() {
    return data;
  }

  public Base64URL setData(Buffer data) {
    this.data = data;
    return this;
  }
}
