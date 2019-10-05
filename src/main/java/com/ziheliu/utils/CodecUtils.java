package com.ziheliu.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;

public final class CodecUtils {
  private static final int SALT_LENGTH = 8;
  public static final char[] RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

  public static String sha256Encode(String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String strWithSalt = str + salt;
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] hash = messageDigest.digest(strWithSalt.getBytes("UTF-8"));
    return Hex.encodeHexString(hash);
  }

  public static String randomSalt() {
    return randomString(SALT_LENGTH);
  }

  public static String randomString(int length) {
    StringBuilder builder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      char ch = RANDOM_CHARS[random.nextInt(RANDOM_CHARS.length)];
      builder.append(ch);
    }
    return builder.toString();
  }
}
