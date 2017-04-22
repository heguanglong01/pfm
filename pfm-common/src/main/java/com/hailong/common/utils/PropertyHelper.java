package com.hailong.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyHelper.class);

	// 属性文件的路径

	private static String profilepath;
	private static Properties props = new Properties();

	public static Properties initPropertyFile(String fileName) {

		InputStream inputStream = PropertyHelper.class.getClassLoader().getResourceAsStream(fileName);
		
		try {
			props.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return props;
	}
//	public static Properties initPropertyFile() {
//		
//		InputStream inputStream = PropertyHelper.class.getClassLoader().getResourceAsStream("pcconfig.properties");
//		Properties p = new Properties();
//		try {
//			p.load(inputStream);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		return p;
//	}

	public static void initProperty(String filePath) {
		profilepath = filePath;
		try {
			props.load(new FileInputStream(profilepath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param key
	 *            主键
	 * @return String
	 */
	public static String getKeyValue(String key) {
		return props.getProperty(key);
	}

	/**
	 * 根据主键key读取主键的值value
	 * 
	 * @param filePath
	 *            属性文件路径
	 * @param key
	 *            键名
	 */
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 */
	public static void updateProperties(String keyname, String keyvalue) {
		try {
			props.load(new FileInputStream(profilepath));
			OutputStream fos = new FileOutputStream(new File(profilepath));
			props.setProperty(keyname, keyvalue);

			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			logger.info("属性文件更新错误");
		}
	}

}
