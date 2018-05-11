package io.vertx.core.buffer;

import io.vertx.codegen.annotations.VertxGen;

import java.util.Arrays;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Buffer {

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

  byte[] getBytes();
}
