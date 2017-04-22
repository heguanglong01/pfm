package com.hailong.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncodeUtil {
	
	/**
	 * 编码工具类
	 * 加密后的byte数组是不能强制转换成字符串的，换言之：
	 * 字符串和byte数组在这种情况下不是互逆的；要避免这种情况，我们需要做一些修订，可以考虑将二进制数据转换成十六进制表示.
	 */

	public static void main(String[] args) throws Exception {
		/*String content = "e4eb21953e1c900f10";
		System.out.println("加密前：" + content);

		String key = "asaXJsYoZzgHS2fr";
		System.out.println("加密密钥和解密密钥：" + key);
		
		String deviceKey = "m6Eillk7ei5jgIeb";
		
		String encrypt = "jeqOTHhhFKErjVvCh85qwcHaTQSPOc57IEJAxY6thYU=";
		
		byte[] encryptResult  = aesEncrypt(content, deviceKey);
		
		System.out.println("加密后：" + Encodes.encodeBase64(encryptResult));*/
	}
	
	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}
	
	/**
	 * 获取byte[]的md5值
	 * @param bytes byte[]
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);
		
		return md.digest();
	}
	
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncrypt(String content, String encryptKey) throws Exception {
		byte[] raw = encryptKey.getBytes();
		SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(encryptKey.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
		
		return cipher.doFinal(content.getBytes());
		
	}
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncrypt(String content, byte[] encryptKey) throws Exception {
		SecretKeySpec sKeySpec = new SecretKeySpec(encryptKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(encryptKey);
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/**
	 * AES解密
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		SecretKeySpec sKeySpec = new SecretKeySpec(decryptKey.getBytes("utf-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(decryptKey.getBytes("utf-8"));
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		
		
		return new String(decryptBytes, "utf-8");
	}
	
	/**
	 * AES解密
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static byte[] aesDecryptByStr(byte[] encryptBytes, String decryptKey) throws Exception {
		SecretKeySpec sKeySpec = new SecretKeySpec(decryptKey.getBytes("utf-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(decryptKey.getBytes("utf-8"));
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		
		
		return decryptBytes;
	}
	
	/**
	 * AES解密
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, byte[] decryptKey) throws Exception {

		SecretKeySpec sKeySpec = new SecretKeySpec(decryptKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(decryptKey);
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		
		
		return new String(decryptBytes, "utf-8");
	}
	
	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
