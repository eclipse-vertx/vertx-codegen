/*
 * Copyright (c) 2014 Red Hat, Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.vertx.test.codegen.converter;

import io.vertx.core.buffer.Buffer;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.cert.X509Certificate;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class TestUtils {

  private static Random random = new Random();

  /**
   * Creates a Buffer of random bytes.
   *
   * @param length The length of the Buffer
   * @return the Buffer
   */
  public static Buffer randomBuffer(int length) {
    return randomBuffer(length, false, (byte) 0);
  }

  /**
   * Create an array of random bytes
   *
   * @param length The length of the created array
   * @return the byte array
   */
  public static byte[] randomByteArray(int length) {
    return randomByteArray(length, false, (byte) 0);
  }

  /**
   * Create an array of random bytes
   *
   * @param length    The length of the created array
   * @param avoid     If true, the resulting array will not contain avoidByte
   * @param avoidByte A byte that is not to be included in the resulting array
   * @return an array of random bytes
   */
  public static byte[] randomByteArray(int length, boolean avoid, byte avoidByte) {
    byte[] line = new byte[length];
    for (int i = 0; i < length; i++) {
      byte rand;
      do {
        rand = randomByte();
      } while (avoid && rand == avoidByte);

      line[i] = rand;
    }
    return line;
  }

  /**
   * Creates a Buffer containing random bytes
   *
   * @param length    the size of the Buffer to create
   * @param avoid     if true, the resulting Buffer will not contain avoidByte
   * @param avoidByte A byte that is not to be included in the resulting array
   * @return a Buffer of random bytes
   */
  public static Buffer randomBuffer(int length, boolean avoid, byte avoidByte) {
    byte[] line = randomByteArray(length, avoid, avoidByte);
    return Buffer.buffer(line);
  }

  /**
   * @return a random byte
   */
  public static byte randomByte() {
    return (byte) ((int) (Math.random() * 255) - 128);
  }

  /**
   * @return a random int
   */
  public static int randomInt() {
    return random.nextInt();
  }

  /**
   * @return a random port
   */
  public static int randomPortInt() {
    return random.nextInt(65536);
  }

  /**
   * @return a random port > 1024
   */
  public static int randomHighPortInt() {
    return random.nextInt(65536 - 1024) + 1024;
  }

  /**
   * @return a random positive int
   */
  public static int randomPositiveInt() {
    while (true) {
      int rand = random.nextInt();
      if (rand > 0) {
        return rand;
      }
    }
  }

  /**
   * @return a random positive long
   */
  public static long randomPositiveLong() {
    while (true) {
      long rand = random.nextLong();
      if (rand > 0) {
        return rand;
      }
    }
  }

  /**
   * @return a random long
   */
  public static long randomLong() {
    return random.nextLong();
  }

  /**
   * @return a random boolean
   */
  public static boolean randomBoolean() {
    return random.nextBoolean();
  }

  /**
   * @return a random char
   */
  public static char randomChar() {
    return (char) (random.nextInt(16));
  }

  /**
   * @return a random short
   */
  public static short randomShort() {
    return (short) (random.nextInt(1 << 15));
  }

  /**
   * @return a random random float
   */
  public static float randomFloat() {
    return random.nextFloat();
  }

  /**
   * @return a random random double
   */
  public static double randomDouble() {
    return random.nextDouble();
  }

  /**
   * Creates a String containing random unicode characters
   *
   * @param length The length of the string to create
   * @return a String of random unicode characters
   */
  public static String randomUnicodeString(int length) {
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char c;
      do {
        c = (char) (0xFFFF * Math.random());
      } while ((c >= 0xFFFE && c <= 0xFFFF) || (c >= 0xD800 && c <= 0xDFFF)); //Illegal chars
      builder.append(c);
    }
    return builder.toString();
  }

  /**
   * Creates a random string of ascii alpha characters
   *
   * @param length the length of the string to create
   * @return a String of random ascii alpha characters
   */
  public static String randomAlphaString(int length) {
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char c = (char) (65 + 25 * Math.random());
      builder.append(c);
    }
    return builder.toString();
  }

  public static <E extends Enum<E>> Set<E> randomEnumSet(Class<E> enumType) {
    EnumSet<E> set = EnumSet.noneOf(enumType);
    for (E e : EnumSet.allOf(enumType)) {
      if (randomPositiveInt() % 2 == 1) {
        set.add(e);
      }
    }
    return set;
  }

  public static <E> E randomElement(E[] array) {
    return array[randomPositiveInt() % array.length];
  }

  /**
   * Determine if two byte arrays are equal
   *
   * @param b1 The first byte array to compare
   * @param b2 The second byte array to compare
   * @return true if the byte arrays are equal
   */
  public static boolean byteArraysEqual(byte[] b1, byte[] b2) {
    if (b1.length != b2.length) return false;
    for (int i = 0; i < b1.length; i++) {
      if (b1[i] != b2[i]) return false;
    }
    return true;
  }

  /**
   * Asserts that an IllegalArgumentException is thrown by the code block.
   *
   * @param runnable code block to execute
   */
  public static void assertIllegalArgumentException(Runnable runnable) {
    try {
      runnable.run();
      fail("Should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  /**
   * Asserts that a NullPointerException is thrown by the code block.
   *
   * @param runnable code block to execute
   */
  public static void assertNullPointerException(Runnable runnable) {
    try {
      runnable.run();
      fail("Should throw NullPointerException");
    } catch (NullPointerException e) {
      // OK
    }
  }

  /**
   * Asserts that an IllegalStateException is thrown by the code block.
   *
   * @param runnable code block to execute
   */
  public static void assertIllegalStateException(Runnable runnable) {
    try {
      runnable.run();
      fail("Should throw IllegalStateException");
    } catch (IllegalStateException e) {
      // OK
    }
  }

  /**
   * Asserts that an IndexOutOfBoundsException is thrown by the code block.
   *
   * @param runnable code block to execute
   */
  public static void assertIndexOutOfBoundsException(Runnable runnable) {
    try {
      runnable.run();
      fail("Should throw IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      // OK
    }
  }

  /**
   * @param source
   * @return gzipped data
   * @throws Exception
   */
  public static byte[] compressGzip(String source) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gos = new GZIPOutputStream(baos);
    gos.write(source.getBytes());
    gos.close();
    return baos.toByteArray();
  }

  public static String cnOf(X509Certificate cert) throws Exception {
    String dn = cert.getSubjectDN().getName();
    LdapName ldapDN = new LdapName(dn);
    for (Rdn rdn : ldapDN.getRdns()) {
      if (rdn.getType().equalsIgnoreCase("cn")) {
        return rdn.getValue().toString();
      }
    }
    return null;
  }

  /**
   * Create a temp file that does not exists.
   */
  public static File tmpFile(String prefix, String suffix) throws Exception {
    File tmp = Files.createTempFile(prefix, suffix).toFile();
    assertTrue(tmp.delete());
    return tmp;
  }

  /**
   * Hexdump prettily
   */
  public static String prettyHexDump(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      if (i % 16 == 0) {
        sb.append(String.format("%05x: ", i));
      }
      sb.append(String.format("%02x ", bytes[i]));
      if (i % 4 == 3) {
        sb.append(" ");
      }
      if (i % 16 == 15 || i == bytes.length - 1) {
        for (int j = i + 1; j % 16 != 0; j++) {
          sb.append("   ");
          if (j % 4 == 3) {
            sb.append(" ");
          }
        }
        int start = (i / 16) * 16;
        sb.append("  ");
        for (int j = start; j <= i; j++) {
          if (bytes[j] >= 32 && bytes[j] < 127) {
            sb.append((char) bytes[j]);
          } else {
            sb.append(".");
          }
          if ((j - start) % 4 == 3 && j < i) {
            sb.append(" ");
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}
