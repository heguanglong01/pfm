package com.hailong.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * 取配置文件工具类
 * @author wangpeng
 *
 */
public class ConfigUtil {
	private static Properties props = null;
	private static File configFile = null;
	private static long lastModified = 0L;
	
	static{
		init();
	}

	public static void init() {
		URL url = ConfigUtil.class.getClassLoader().getResource("conf.properties");

		String file = url.getFile();
		try {
			file = java.net.URLDecoder.decode(file, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		configFile = new File(file);
		lastModified = configFile.lastModified();
		props = new Properties();
		load();
	}

	private static void load() {
		try {
			props.clear();
			props.load(new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream("conf.properties"), "UTF-8"));
			lastModified = configFile.lastModified();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据指定的key获取对应的配置信息
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		if ((configFile == null) || (props == null))
			init();
		if (configFile.lastModified() != lastModified)
			load();
		return props.getProperty(key);
	}
	
}
