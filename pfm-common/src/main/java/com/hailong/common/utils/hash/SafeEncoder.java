package com.hailong.common.utils.hash;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class SafeEncoder {
  public static final Logger logger = LoggerFactory.getLogger(SafeEncoder.class);
  private SafeEncoder(){
    throw new InstantiationError( "Must not instantiate this class" );
  }

  public static byte[][] encodeMany(final String... strs) {
    byte[][] many = new byte[strs.length][];
    for (int i = 0; i < strs.length; i++) {
      many[i] = encode(strs[i]);
    }
    return many;
  }

  public static byte[] encode(final String str) {
    try {
        return str.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
      return null;
  }

  public static String encode(final byte[] data) {
      try {
        return new String(data, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      
      return null;
  }
}
