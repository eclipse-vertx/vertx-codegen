package io.vertx.core.json;

import io.vertx.codegen.annotations.Nullable;

/**
 * Primitive for conversion JSON_TYPE -> TARGET_TYPE
 *
 * @param <TARGET_TYPE>
 * @param <JSON_TYPE>
 */
public interface JsonDecoder<TARGET_TYPE, JSON_TYPE> {
  /**
   * decode performs the conversion JSON_TYPE -> TARGET_TYPE.
   * Note: This method must handle null values
   *
   * @param value
   * @return
   */
  @Nullable TARGET_TYPE decode(@Nullable JSON_TYPE value);
}
