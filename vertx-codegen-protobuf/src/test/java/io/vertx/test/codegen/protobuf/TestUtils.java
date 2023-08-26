package io.vertx.test.codegen.protobuf;

public class TestUtils {
  private static final boolean DEBUG = false;

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

  public static void debug(String prefix, byte[] bytes) {
    if (DEBUG) {
      System.out.print(prefix);
      System.out.println(":\n" + TestUtils.prettyHexDump(bytes));
    }
  }
}
