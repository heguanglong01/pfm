package com.hailong.common.utils.mobileUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hailong.common.utils.HtttpRequest;


public class MobileApiUtils {
	
	public static final String appKey="v0b9kphtw0";
	public static final String secret="62000755b09d2d042e80296cdd9837c4";

    /**
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramNames  需要签名的参数名
     * @param paramValues 参数列表
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, String secret) {
        return sign(paramValues,null,secret);
    }

    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, List<String> ignoreParamNames,String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if(ignoreParamNames != null && ignoreParamNames.size() > 0){
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }    

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    /**
     * phoneNum 手机号
     * date  2017-02-01
     * 查询某一天的流量
     * 注意：该接口只能查询出两天之前的流量
     * @return
     * @throws IOException 
     */
    public static Double queryMobileFlowByDate(String phoneNum,String date) throws Exception{
    	Map<String, String> paramValues =new HashMap<String, String>();
    	paramValues.put("appKey", appKey);
    	paramValues.put("format", "xml");
    	paramValues.put("v", "2.0");
    	paramValues.put("method", "iot.member.dailydatausage.query");
    	paramValues.put("msisdn", phoneNum);
    	paramValues.put("dateStr", date.replace("-", "").substring(0, 6));//到月份 yyyyMM
    	
    	String sign= sign(paramValues, secret);//得到签名
    	String url="http://120.197.89.173:8081/openapi/router?"
    			+ "appKey="+appKey+"&format=xml&v=2.0&method="+paramValues.get("method")+"&msisdn="+phoneNum+"&dateStr="+paramValues.get("dateStr")+"&sign="+sign+"";
			
    	String res=HtttpRequest.getHttpRequestTelecom(url);//http请求
    	Double flowValue=0.00;
//    	System.out.println(res);
		if(res!=null&&!res.equals("")){
			    Document doc = DocumentHelper.parseText(res);
		        Element rootElement = doc.getRootElement();
		        Element resultCodeElement= rootElement.element("resultCode");
		        Element errorMessageElement= rootElement.element("errorMessage");
		        Element olMberDdFlowsElement= rootElement.element("olMberDdFlows");
		        String code=resultCodeElement.getText();
		        if(code!=null&&code.equals("0")){//表示请求成功
		        	if(olMberDdFlowsElement!=null){
		        		List<Element> olMberDdFlowList= olMberDdFlowsElement.elements("olMberDdFlow");
		        		if(olMberDdFlowList!=null&&olMberDdFlowList.size()>0){
		        			for(Element elemet:olMberDdFlowList){
		        				  String dateStr= elemet.elementText("dateStr");
		        				  String flowSum= elemet.elementTextTrim("flowSum");
		        				  if(dateStr.contains(date)){
		        					  flowValue=new BigDecimal(flowSum).divide(new BigDecimal(1024),3,RoundingMode.HALF_UP).doubleValue();
		        					  break;
		        				  }
		        			}
		        		}
		        		
		        	}
		        }else{//请求不成功  抛出不成功的异常
		        	 if(errorMessageElement!=null){
		        		 String errMess=errorMessageElement.getText();
		        		 throw new Exception("errorCode:"+code+",errMess:"+errMess);
		        	 }
		        }
		}
		System.err.println("phoneNum:"+phoneNum+",日期:"+date+",流量:"+flowValue);
    	return flowValue;
    }
    /**
     * main方法
     * @param args
     */
    public static void main(String[] args) {
//    	Map<String, String> paramValues =new HashMap<String, String>();
//    	paramValues.put("appKey", "v0b9kphtw0");
//    	paramValues.put("format", "xml");
//    	paramValues.put("v", "2.0");
//    	paramValues.put("method", "iot.member.dailydatausage.query");
//    	paramValues.put("msisdn", "898602B5191650780023");
//    	paramValues.put("dateStr", "201702");
    	
//    	paramValues.put("appKey", "v0b9kphtw0");
//    	paramValues.put("format", "xml");
//    	paramValues.put("v", "2.0");
//    	paramValues.put("method", "iot.member.monthlydatausage.query");
//    	paramValues.put("msisdn", "1064856060001");
//    	paramValues.put("startDateStr", "201701");
//    	paramValues.put("endDateStr", "201702");
//    	
//    	String secret="62000755b09d2d042e80296cdd9837c4";
//    	String sign= sign(paramValues, secret);
//    	System.out.println(sign);
    	
    	
    	try {
			queryMobileFlowByDate("1064856061425","2017-02-15");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

