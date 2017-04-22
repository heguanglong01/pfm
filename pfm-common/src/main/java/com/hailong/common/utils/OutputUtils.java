package com.hailong.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 输出对象
 * @author heguanglong
 *
 */
public class OutputUtils {
	public static final Integer CODE_PARAM_NULL = -1;//参数为空
	public static final Integer CODE_QUERY_NULL = -2;//查找对象为空
	public static final Integer CODE_QUERY_EXCEPTION = -3;//查找发生异常
	public static final Integer CODE_NOT_MATCh = -4;//比较信息不匹配
	public static final Integer CODE_DB_REPEART = -5;//数据库中已重复
	/**心跳服务返回码**/
	public static final Integer CODE_SUCC_HEART =  2000;//心跳服务成功返回码
	public static final Integer CODE_FAIL_HEART =  4000;//心跳服务失败返回码
	public static final Integer CODE_STATUS_HEART =  403;//心跳服务失败返回码
	
	/** 文件下载接口返回码 **/
	public static final Integer CODE_SUCC_DOWNLOADEX = 2000;  //文件下载接口处理成功
	public static final Integer CODE_FAIL_DOWNLOADEX = 4000;  //文件下载接口处理失败
	
	/** 数据劫持接口返回码 **/
	public static final Integer CODE_SUCC_DEVPRIVATE = 2000;  //数据劫持接口处理成功
	public static final Integer CODE_FAIL_DEVPRIVATE = 4000;  //数据劫持接口处理失败
	
	/** 文件下发停止接口返回码 **/
	public static final Integer CODE_SUCC_DOWNLOADEXSTOP  = 2000;  //文件下发停止接口处理成功
	public static final Integer CODE_FAIL_DOWNLOADEXSTOP  = 4000;  //文件下发停止接口处理失败
	
	/** smart update git hook shell 调用接口返回码**/
	public static final Integer CODE_SUCC_SU = 2000; //接口处理成功
	public static final Integer CODE_FAIL_URL_SU = 2001;  //url不正确
	public static final Integer CODE_FAIL_GITVER_SU = 2002; //git_ver 为空
	public static final Integer CODE_FAIL_VERSION_SU = 2003; //version为空 
	public static final Integer CODE_FAIL_UNALLOWABLE = 2004; //项目禁用
	
	
	/**
	 * 普通返回格式
	 * @param msg
	 * @param data
	 * @return
	 */
	public static String heartSuccess(String header,String msg, String data) {
		StringBuffer sb = new StringBuffer();
		sb.append(header+"{");
		sb.append("\"rv_code\":"+CODE_SUCC_HEART+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		if(!StringUtils.isEmpty(data)){
		   sb.append(data);
		}
		return sb.toString();
		
	}
	/**
	 * 获取数据失败
	 * @param msg
	 * @return
	 */
	public static String handleFail(String header,String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append(header+"{");
		sb.append("\"rv_code\":"+CODE_FAIL_HEART+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
	/**
	 * 获取数据失败
	 * @param msg
	 * @return
	 */
	public static String handleFailStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"rv_code\":"+CODE_STATUS_HEART+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 获取数据失败
	 * @param msg
	 * @return
	 */
	public static String getObjectOutputStream (Object obj,String utf8) {
		String msg="";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			msg = baos.toString(utf8);//指定字符集将字节流解码成字符串，否则在订
			oos.close();
			baos.close();
		} catch (IOException e) {
			msg="";
			e.printStackTrace();
		}
	     return msg;
	}
	
	/**
	 * 文件下载获取数据失败
	 * @param msg
	 * @return
	 */
	public static String downloadexFailStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"dl_code\":"+CODE_FAIL_DOWNLOADEX+",");
		sb.append("\"dl_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 文件下载处理数据成功
	 * @param msg
	 * @return
	 */
	public static String downloadexSuccStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"dl_code\":"+CODE_SUCC_DOWNLOADEX+",");
		sb.append("\"dl_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 数据劫持处理数据失败
	 * @param msg
	 * @return
	 */
	public static String devPrivateFailStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"dp_code\":"+CODE_FAIL_DEVPRIVATE+",");
		sb.append("\"dp_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 数据劫持处理数据成功
	 * @param msg
	 * @return
	 */
	public static String devPrivateSuccStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"dp_code\":"+CODE_SUCC_DEVPRIVATE+",");
		sb.append("\"dp_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 停止文件下发处理数据失败
	 * @param msg
	 * @return
	 */
	public static String downloadexStopFailStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"ds_code\":"+CODE_FAIL_DOWNLOADEXSTOP+",");
		sb.append("\"ds_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 停止文件下发处理数据成功
	 * @param msg
	 * @return
	 */
	public static String downloadexStopSuccStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"ds_code\":"+CODE_SUCC_DOWNLOADEXSTOP+",");
		sb.append("\"ds_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	
	/**
	 * smart update git hook shell 调用成功
	 * @param msg
	 * @return
	 */
	public static String smartUpdateSuccStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"rv_code\":"+CODE_SUCC_SU+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * smart update git hook shell url 不正确
	 * @param msg
	 * @return
	 */
	public static String smartUpdateFailUrlStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"rv_code\":"+CODE_FAIL_URL_SU+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * smart update git hook shell git_ver 不正确
	 * @param msg
	 * @return
	 */
	public static String smartUpdateFailGitverStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"rv_code\":"+CODE_FAIL_GITVER_SU+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * smart update git hook shell version 不正确
	 * @param msg
	 * @return
	 */
	public static String smartUpdateFailVersionStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"rv_code\":"+CODE_FAIL_VERSION_SU+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
	
	/**
	 * 项目禁用
	 * @param msg
	 * @return
	 */
	public static String smartUpdateFaillowableUnStatus(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"rv_code\":"+CODE_FAIL_UNALLOWABLE+",");
		sb.append("\"rv_value\":\""+msg+"\"}");
		return sb.toString();
	}
}
