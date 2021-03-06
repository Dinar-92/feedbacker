package tech.itpark.crypto;

public class Hex {
  private Hex() {
  }

  private static final char[] chars = "0123456789abcdef".toCharArray();

  public static String encode(byte[] bytes) {
    final var result = new char[2 * bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      byte b = bytes[i];
      result[i * 2] = chars[(0b1111_0000 & b) >>> 4]; // 0xF0
      result[i * 2 + 1] = chars[0b0000_1111 & b];
    }
    return new String(result);
  }
}
