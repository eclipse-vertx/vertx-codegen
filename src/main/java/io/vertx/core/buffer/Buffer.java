package io.vertx.core.buffer;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Buffer {

  static Buffer buffer(byte[] bytes) {
    throw new UnsupportedOperationException();
  }

  byte[] getBytes();
}
