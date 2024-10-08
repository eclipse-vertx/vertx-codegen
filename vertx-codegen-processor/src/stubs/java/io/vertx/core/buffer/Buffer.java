package io.vertx.core.buffer;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.util.Arrays;
import java.util.Base64;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface Buffer {

  static Buffer fromJson(String base64) {
    Base64.Decoder decoder = Base64.getUrlDecoder();
    byte[] bytes = decoder.decode(base64);
    return buffer(bytes);
  }

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static Buffer buffer(byte[] bytes) {
    return new Buffer() {
      @Override
      public byte[] getBytes() {
        return bytes;
      }
      @Override
      public boolean equals(Object obj) {
        if (obj instanceof Buffer) {
          Buffer that = (Buffer) obj;
          return Arrays.equals(bytes, that.getBytes());
        }
        return false;
      }
    };
  }

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  byte[] getBytes();

  default String toJson() {
    Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    return encoder.encodeToString(getBytes());
  }
}
