package io.vertx.core.json;

import io.vertx.codegen.annotations.Nullable;

/**
 * Primitive for conversion TARGET_TYPE -> JSON_TYPE
 *
 * @param <TARGET_TYPE>
 * @param <JSON_TYPE>
 */
public interface JsonEncoder<TARGET_TYPE, JSON_TYPE> {
  /**
   * encode performs the conversion TARGET_TYPE -> JSON_TYPE.
   * Note: This method must handle null values
   *
   * @param value
   * @return
   */
  @Nullable JSON_TYPE encode(@Nullable TARGET_TYPE value);
}
