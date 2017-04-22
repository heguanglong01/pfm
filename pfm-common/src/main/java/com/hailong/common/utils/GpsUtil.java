package com.hailong.common.utils;

import com.alibaba.fastjson.JSONObject;

public class GpsUtil {
	/**
	 * 百度接口返回地址
	 * @param lng
	 * @param lat
	 * @return
	 */
	 public static String getGpsInoAddress(String lng,String lat){
		 String gpsAddress="";
	      if(!"".equals(lat) && ! "".equals(lng)){
			lng = lng.endsWith("E") ? lng.substring(0, lng.length() -1) : lng;
			lat = lat.endsWith("N") ? lat.substring(0, lat.length() -1) : lat;
			lng = lng.substring(0, 3) + "." + (int)(Double.parseDouble(lng.substring(3))/60*100000);
			lat = lat.substring(0, 2) + "." + (int)(Double.parseDouble(lat.substring(2))/60*100000);
			String gpsurl="http://api.map.baidu.com/geocoder?output=json&location="+lat+","+lng+"8&key=37492c0ee6f924cb5e934fa08c6b1676";
			JSONObject objgps=HtttpRequest.getHttpRequestMap(gpsurl);
			if(objgps != null && objgps.containsKey("status") && "OK".equals(objgps.getString("status"))){
				String name= objgps.getJSONObject("result").getString("formatted_address") ;
				gpsAddress=name;
				
			 }
	      }
	      return gpsAddress;
	 }
	 
	 /**
	  * 高德接口  返回 位置
	  * @param lng_lat  (经度,维度)
	  * @return
	  */
	 public static String getGpsInoAddress(String lng_lat){
		 String gpsAddress="";
		 if(lng_lat!=null&&!lng_lat.equals("")){
			 String gpsurl="http://restapi.amap.com/v3/geocode/regeo?output=json&location="+lng_lat+"8&key=ad5a84667e78110b985685cceeed565c";
			 JSONObject objgps=HtttpRequest.getHttpRequestMap(gpsurl);
			 if(objgps != null && objgps.containsKey("info") && "OK".equals(objgps.getString("info"))){
				 String name= objgps.getJSONObject("regeocode").getString("formatted_address") ;
				 gpsAddress=name;
			 }
		 }
		 return gpsAddress;
	 }
	 /** 
     *  
     * @param dms 坐标 
     * @param type 坐标类型 
     * @return String 解析后的经纬度 
     */  
    public static String xypase(String dms, String type) {
        if (dms == null || dms.equals("")) {  
            return "0.0";  
        }  
        double result = 0.0D;  
        String temp = "";  
        if (type.equals("E")) {//经度  
        	dms = dms.endsWith("E") ? dms.substring(0,dms.length() - 1) : dms;
            String e1 = dms.substring(0, 3);//截取3位数字，经度共3位，最多180度  
            String e2 = dms.substring(3, dms.length());//需要运算的小数  
            result = Double.parseDouble(e1);  
            result += (Double.parseDouble(e2) / 60.0D);  
            temp = String.valueOf(result);  
            if (temp.length() > 9) {  
                temp = e1 + temp.substring(temp.indexOf("."), 9);  
            }  
        } else if (type.equals("N")) {      //纬度，纬度是以赤道为基准,相当于把地球分两半,两个半球面上的点和平面夹角0~90度  
        	dms = dms.endsWith("N") ? dms.substring(0,dms.length() - 1) : dms;
        	String n1 = dms.substring(0, 2);//截取2位，纬度共2位，最多90度  
            String n2 = dms.substring(2, dms.length());  
  
            result = Double.parseDouble(n1);  
            result += Double.parseDouble(n2) / 60.0D;  
            temp = String.valueOf(result);  
            if (temp.length() > 8) {  
                temp = n1 + temp.substring(temp.indexOf("."), 8);  
            }  
        }  
        return temp;  
    }  
}
