package io.vertx.core.buffer;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.util.Arrays;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface Buffer {

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
}
