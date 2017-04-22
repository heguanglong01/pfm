package com.hailong.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	
	/**
	 * response公共操作
	 * 
	 * @param response
	 * @param responseStr
	 */
	public static void responseCommon(HttpServletResponse response,
			String responseStr) {
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8"); // 设置编码
			response.setHeader("Content-type","text/html;charset=UTF-8");
			printWriter = response.getWriter();
			printWriter.write(responseStr);
			//System.out.println("parseRequestJson printWriter jsonStr:"+ responseStr);
		} catch (IOException ex) {
			logger.error("", ex);
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
				//System.out.println("parseRequestJson printWriter.flush()");
			}
		}
	}

	public static String formatDate(java.util.Date date, String pattern) {
		if (date == null)
			date = now();

		if (pattern == null)
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return (sdf.format(date));
	}
	
	/**
	 * 获取当前 java.util.Date 格式完整日期
	 * 
	 * @return java.util.Date
	 */
	public static java.util.Date now() {
		return (new java.util.Date());
	}
	
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-real-ip");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("x-forwarded-for");  
        } else {
            return ip;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP");  
        } else {
            return ip;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        } else {
       	 return ip;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        } else {
       	 return ip;
        }
        
        return ip;
	}
	
	/**
     * 获得请求的ip地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("X-Real-IP");
		logger.debug("clientIP: "+request.getHeader("X-Real-IP"));
		logger.debug("clientIP: "+request.getHeader("x-forwarded-for"));
		logger.debug("clientIP: "+request.getHeader("Proxy-Client-IP"));
		logger.debug("clientIP: "+request.getHeader("WL-Proxy-Client-IP"));
		if (ip == null) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
    }
	    
    /**
	 * 从ip字符串中分割客户端ip
	 * @param ips
	 * @return
	 */
	public static String getRemoteIp(String ips){
		logger.info("ips: "+ips);
		if(ips.indexOf(",") > 1){
			int index = ips.indexOf(",");
			if(index > 1 && index < ips.length()){
				return ips.substring(0, index);
			}
		}
		return ips;
	}
	
	/**
	 * josn格式的字符串进行处理获取某一key的值
	 * @param string 
	 * @return
	 */
	public static Map<String,Integer> getJosnString(String strTemp) {

		strTemp = strTemp.replace("\"", "");
		strTemp = strTemp.replace("{", "");
		strTemp = strTemp.replace("}", "");
		strTemp = strTemp.trim();

		String[] temp = strTemp.split(",");
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		for (String x : temp) {
			String[] tString = x.split(":");
			map.put(tString[0], Integer.parseInt(tString[1]));
		}
		return map;
	}
	
	/**
	 * 判断host是不是有问题
	 * 
	 * @return boolean
	 */
	/*public static boolean isRightHost(String originalHost) {

		String hostString = originalHost.trim();
		if (HostDealUtil.SpaceHostRemove(hostString)) {
			return false;
		} else if (HostDealUtil.IpHostRemove(hostString)) {
			return false;
		} else if (HostDealUtil.PortHostRemove(hostString)) {
			return false;
		} else {
			return true;
		}
	}*/
	
	public static boolean isSQLInject(String str) {
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|or|-|,";
		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
	public static double doubleSave2(double value) {
        BigDecimal b = new BigDecimal(value);
        
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        
        return f1;
    }
	public static void main(String[] args) {

		Calendar cal=Calendar.getInstance(Locale.CHINA); 
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date date=cal.getTime();
		
		System.out.println(Util.formatDate(date,"yyyy-MM-dd"));
	}
	
}
