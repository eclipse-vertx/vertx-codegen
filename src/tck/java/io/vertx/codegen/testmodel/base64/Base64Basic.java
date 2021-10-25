package io.vertx.codegen.testmodel.base64;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

@DataObject(base64Type = "basic", generateConverter = true)
public class Base64Basic {

  Buffer data;

  public Base64Basic() {}
  public Base64Basic(JsonObject jsonObject) {
    Base64BasicConverter.fromJson(jsonObject, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    Base64BasicConverter.toJson(this, json);
    return json;
  }

  public Buffer getData() {
    return data;
  }

  public Base64Basic setData(Buffer data) {
    this.data = data;
    return this;
  }
}
