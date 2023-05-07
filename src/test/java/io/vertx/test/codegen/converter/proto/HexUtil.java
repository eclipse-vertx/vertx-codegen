/*
 * Copyright (C) 2022, MATRIXX Software, Inc. All rights reserved.
 */
package io.vertx.test.codegen.converter.proto;

public class HexUtil {

    private static final int SEG_SIZE = 16;

    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private HexUtil() {}

    /**
     * Creates a hex dump of a byte array which is suitable for debugging. Each
     * byte value is converted to a string of ASCII digits represented in
     * hexadecimal, with a leading 0. Non-printable ASCII characters are
     * represented with the '.' character, E.g.
     * <p>
     * <pre>
     * </pre>
     *
     * @param	b	the source buffer.
     * @param	off offset at which to start reading bytes.
     * @param	len the maximum number of bytes to read.
     * @return	a hexadecimal string in debug format.
     */
    public static String hexDump(byte[] b, int off, int len) {
        StringBuilder builder = new StringBuilder();
        builder.append("size : ");
        builder.append(len);
        builder.append("\n");

        byte[] tmp = null;
        int idx = 0;
        int k;

        while (idx < len) {
            for (int i=0; i<4; i++) {
                for (int j=0; j<4; j++) {
                    if ((idx + i*4 +j) < len) {
                        // Print one word, e.g. A3 + space.
                        char[] chr = new char[2];
                        k = b[idx + i*4 +j];
                        chr[0] = hexDigits[(k >>> 4) & 0x0f];
                        chr[1] = hexDigits[ k		 & 0x0f];
                        builder.append(chr).append(" ");
                    } else {
                        builder.append("   ");
                    }
                }
                builder.append(" ");
            }
            // Print a tab between HEX and ASCII.
            builder.append("\t");
            // Print 16 characters in ASCII.
            int end = ((idx + SEG_SIZE) < len) ? SEG_SIZE : (len - idx);
            tmp = new byte[end];
            System.arraycopy(b, idx, tmp, 0, end);
            tmp = fixNonPrintableAscii(tmp);
            builder.append(new String(tmp));
            idx += SEG_SIZE;
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Creats a hex dump of a byte array suitable for debugging. Each byte value
     * is converted to a string of ASCII digits in hexadecimal with a leading 0.
     * Non-printable ASCII characters are represented with the '.' character.
     *
     * @param b	the source buffer.
     * @return a hexidecimal string in debug format.
     */
    public static String hexDump(byte[] b) {
        return hexDump(b, 0, b.length);
    }

    /**
     * Returns a byte array wherein non-printable ASCII characters are converted
     * to the '.' (0x2e) character.
     * @param b
     * @return
     */
    private static byte[] fixNonPrintableAscii(byte[] b) {
        byte[] buf = new byte[b.length];

        for (int i = 0; i < b.length; i++) {
            if (Character.isISOControl((char)b[i])) {
                buf[i] = 0x2e; // '.'
            } else {
                buf[i] = b[i];
            }
        }

        return buf;
    }

    /**
     * Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols.
     * <p>
     * If offset and length are omitted, the entire array is used.
     *
     * @param ba
     * @param offset
     * @param length
     * @return
     */
    public static String toString(byte[] ba, int offset, int length) {
        char[] buf = new char[length * 2];
        int j = 0;
        int k;

        for (int i = offset; i < offset + length; i++) {
            k = ba[i];
            buf[j++] = hexDigits[(k >>> 4) & 0x0F];
            buf[j++] = hexDigits[ k		   & 0x0F];
        }

        return new String(buf);
    }

    /**
     * Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols.
     * <p>
     * If offset and length are omitted, the entire array is used.
     *
     * @param ba	the source buffer.
     * @return a hexadecimal string.
     */
    public static String toString(byte[] ba) {
        return toString(ba, 0, ba.length);
    }

    /**
     * Returns a byte array from a string of hexadecimal digits.
     *
     * @param hex the string to convert to a byte array.
     * @return
     */
    public static byte[] fromString(String hex) {
        int len = hex.length();
        byte[] buf = new byte[((len + 1) / 2)];
        int i = 0, j = 0;
        if ((len % 2) == 1) {
            buf[j++] = (byte)fromDigit(hex.charAt(i++));
        }

        while (i < len) {
            buf[j++] = (byte)((fromDigit(hex.charAt(i++)) << 4) |
                    fromDigit(hex.charAt(i++)));
        }

        return buf;
    }

    /**
     * Returns the hex digit corresponding to a number <i>n</i>, from 0 to 15.
     *
     * @param n
     * @return
     */
    public static char toDigit(int n) {
        try {
            return hexDigits[n];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(n
                    + " is out of range for a hex digit");
        }
    }

    /**
     * Returns the number from 0 to 15 corresponding to the hex digit <i>ch</i>.
     *
     * @param ch
     * @return
     */
    public static int fromDigit(char ch) {
        if ((ch >= '0') && (ch <= '9'))
            return ch - '0';
        if ((ch >= 'A') && (ch <= 'F'))
            return ch - 'A' + 10;
        if ((ch >= 'a') && (ch <= 'f'))
            return ch = 'a' + 10;

        throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
    }

    /**
     * Returns a copy of the input array stripped of any leading zero bytes.
     *
     * @param a
     * @return
     */
    public static byte[] stripLeadingZeroBytes(byte[] a) {
        int keep;

        // Find first nonzero byte.
        for (keep=0; keep<a.length && a[keep]==0; keep++)
            ;

        // Allocate a new array and copy relevant part of input array.
        byte[] result = new byte[a.length - keep];
        for (int i = keep; i<a.length; i++)
            result[i - keep] = a[i];

        return result;
    }
}
