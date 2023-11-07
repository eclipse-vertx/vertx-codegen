package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

@DataObject
@VertxGen
public interface AutoMappedWithVertxGen {

  static AutoMappedWithVertxGen fromJson(JsonObject json) {
    int port = json.getInteger("port", -1);
    String host = json.getString("host");
    return of(host, port);
  }

  static AutoMappedWithVertxGen of(String host, int port) {
    return new AutoMappedWithVertxGen() {
      @Override
      public int port() {
        return port;
      }
      @Override
      public String host() {
        return host;
      }
      @Override
      public int hashCode() {
        return host.hashCode() + port;
      }
      @Override
      public boolean equals(Object obj) {
        AutoMappedWithVertxGen that = (AutoMappedWithVertxGen) obj;
        return Objects.equals(host(), that.host()) && port() == that.port();
      }
    };
  }


  int port();

  String host();

  default JsonObject toJson() {
    return new JsonObject().put("port", port()).put("host", host());
  }
}
