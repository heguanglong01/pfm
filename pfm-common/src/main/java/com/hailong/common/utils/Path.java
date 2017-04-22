/**
 * 
 */
package com.hailong.common.utils;

import java.io.File;

/**
 * @author 
 *
 */
public class Path {
//	上下文的绝对路径
	public static String ROOT_PATH ;       // C:\tomcat-5.5.23\webapps\topology
//	WEB-INF目录的绝对路径
	public static String WEBINF_PATH;      // C:\tomcat-5.5.23\webapps\topology\WEB-INF
//	配置文件目录
	public static String CONF_PATH;        //C:\tomcat-5.5.23\webapps\topology\WEB-INF\conf
	
	public static void initPath(String root){
		if(!root.endsWith(File.separator))
			root = root + File.separator;
		ROOT_PATH = root;
		WEBINF_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();;
		CONF_PATH = WEBINF_PATH + "conf" + File.separator;
	}
}
