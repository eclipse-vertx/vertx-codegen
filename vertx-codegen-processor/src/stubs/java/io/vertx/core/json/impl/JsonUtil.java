/*
 * Copyright (c) 2011-2019 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.vertx.core.json.impl;

import java.util.Base64;

/**
 * Implementation utilities (details) affecting the way JSON objects are wrapped.
 */
public final class JsonUtil {

  public static final Base64.Encoder BASE64_ENCODER;
  public static final Base64.Decoder BASE64_DECODER;

  static {
    /*
     * Vert.x 3.x Json supports RFC-7493, however the JSON encoder/decoder format was incorrect.
     * Users who might need to interop with Vert.x 3.x applications should set the system property
     * {@code vertx.json.base64} to {@code legacy}.
     */
    if ("legacy".equalsIgnoreCase(System.getProperty("vertx.json.base64"))) {
      BASE64_ENCODER = Base64.getEncoder();
      BASE64_DECODER = Base64.getDecoder();
    } else {
      BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();
      BASE64_DECODER = Base64.getUrlDecoder();
    }
  }

  /**
   * Wraps well known java types to adhere to the Json expected types.
   * <ul>
   *   <li>{@code Map} will be wrapped to {@code JsonObject}</li>
   *   <li>{@code List} will be wrapped to {@code JsonArray}</li>
   *   <li>{@code Instant} will be converted to iso date {@code String}</li>
   *   <li>{@code byte[]} will be converted to base64 {@code String}</li>
   *   <li>{@code Enum} will be converted to enum name {@code String}</li>
   * </ul>
   *
   * @param val java type
   * @return wrapped type or {@code val} if not applicable.
   */
  public static Object wrapJsonValue(Object val) {
    throw new UnsupportedOperationException();
  }

  public static Object checkAndCopy(Object val) {
    throw new UnsupportedOperationException();
  }
}
