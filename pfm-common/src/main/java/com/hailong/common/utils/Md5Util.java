package com.hailong.common.utils;

 

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5Encode
     * @param origin
     * @return MD5 code
     */
    public static String MD5Encode(String origin) {
        String resultString = null;

        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes()));
        } catch (Exception ex) {

        }
        return resultString;
    }

    public static String MD5ByApacheCommons(String source) {
		return DigestUtils.md5Hex(source);
	}
    
    public static void main(String args[]) {
    	System.out.println("12345");
    	System.out.println(Md5Util.MD5Encode("12345"));
    	System.out.println(Md5Util.MD5Encode("12345").substring(24,32));
    	
    	System.out.println(Md5Util.MD5Encode(Md5Util.MD5ByApacheCommons("12345")));

        //System.out.println(Md5Util.MD5Encode(Md5Util.MD5ByApacheCommons("david11111112011-12-21 15:44:59")));
        //f257bb4ec15659b01d54df8045c82ee6
        //System.out.println(Md5Util.MD5ByApacheCommons("111111abcd"));
    }

}
