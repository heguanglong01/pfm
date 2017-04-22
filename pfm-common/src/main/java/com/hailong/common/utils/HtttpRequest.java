package com.hailong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hailong.base.entity.PFMPhoneVeri;




/**
 *http 请求的工具类
 *@author heguanglong
 *@data 2016年3月21日
 */
public class HtttpRequest {
	 private static final Logger logger = LoggerFactory.getLogger(HtttpRequest.class);
	 public static final String url="http://10.10.17.201:8089";
	    /**
	     * 通过HttpURLConnection模拟post表单提交
	     * @throws Exception
	     */
	    public static String sendPost(String httpUrl,String parms) {
	    	    String msg="";
			    	try {
			    		logger.debug("sendPost--》"+httpUrl+"  body msg="+parms);
						URL url = new URL(httpUrl);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");// 提交模式
						conn.setDoOutput(true);// 是否输入参数
						
						if(parms != null && parms.length()>0){
							StringBuffer params = new StringBuffer();
							params.append(parms);
							byte[] bypes = params.toString().getBytes();
							conn.getOutputStream().write(bypes);// 输入参数
						}
						
						StringBuilder json = new StringBuilder();
						 
						BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String inputLine = null;
						while ((inputLine = in.readLine()) != null) {
							json.append(inputLine);
						}
						in.close();
			
						if (!"".equals(json) && json != null) {
							msg=json.toString();
						}
				} catch (Exception e) {
					logger.error("sendPost error--->"+e);
					e.printStackTrace();
					return msg="";
				}
			  logger.debug("sendPost returnMsg--》"+msg);
	    	return msg;
		
	    }
	    
	    /**
		  * @author heguanglong
		  * @param url 请求的url
		  * @return json 字符串
		  */
		 public static String getHttpRequest(String url) {
		        StringBuilder json = new StringBuilder();
		        try {
		            URL oracle = new URL(url);
		            URLConnection yc = oracle.openConnection();
		            BufferedReader in = new BufferedReader(new InputStreamReader( yc.getInputStream(),"utf-8"));
		            String inputLine = null;
		            while ( (inputLine = in.readLine()) != null) {
		                json.append(inputLine);
		            }
		            in.close();
		        } catch (Exception e) {
		        	logger.error("url:"+e.getMessage());
		        }
		        return json.toString();
		    }
		 public static String getHttpRequestTelecom(String url) throws IOException {
			 StringBuilder json = new StringBuilder();
			 
				 URL urlObj = new URL(url);
				 URLConnection yc = urlObj.openConnection();
				 yc.setConnectTimeout(1000*60*3);//超时时间3分钟
				 yc.setReadTimeout(1000*60*5);//读取时间
				 BufferedReader in = new BufferedReader(new InputStreamReader( yc.getInputStream(),"utf-8"));
				 String inputLine = null;
				 while ( (inputLine = in.readLine()) != null) {
					 json.append(inputLine);
				 }
				 in.close();
			
			 return json.toString();
		 }
		 /**
		  * post方式访问   url 参数
		  * @param url
		  * @return
		  */
		 public static JSONObject getHttpRequestPost(String url,String param) {
			   JSONObject resultJson=null;
			   OutputStream outputStream = null;
       		 OutputStreamWriter outputStreamWriter = null;
       		InputStream inputStream = null;
       		InputStreamReader inputStreamReader = null;
       		BufferedReader reader = null;
       		StringBuffer resultBuffer = new StringBuffer();
       		String tempLine = null;
       		
			 try {
				   URL localURL = new URL(url);
	        		URLConnection connection = localURL.openConnection();
	        		HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
	        		httpURLConnection.setDoOutput(true);
	        		httpURLConnection.setConnectTimeout(30000);
	        		httpURLConnection.setRequestMethod("POST");
	        		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	        		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8;");
	        		httpURLConnection.setRequestProperty("Content-Length", param.length()+"");
	        		
	        		outputStream = httpURLConnection.getOutputStream();
	        		outputStreamWriter = new OutputStreamWriter(outputStream,"utf-8");
	        		outputStreamWriter.write(param);
	        		outputStreamWriter.flush();
	        		
	        		inputStream = httpURLConnection.getInputStream();
	        		inputStreamReader = new InputStreamReader(inputStream,"utf-8");
	        		reader = new BufferedReader(inputStreamReader);
	        		
	        		while ((tempLine = reader.readLine()) != null) {
	        			resultBuffer.append(tempLine);
	        		}
	        		if(resultBuffer.length()>0){
	        			resultJson = JSONObject.parseObject(resultBuffer.toString()) ;
	        		}
			 } catch (Exception e) {
				 logger.error("http请求错误，信息："+e);
			 }finally{
				 try {
					 if(inputStream!=null){
							inputStream.close();
					 }
					 if(outputStream!=null){
						 outputStream.close();
					 }
				 } catch (IOException e) {
						e.printStackTrace();
					}
			 }
			 return resultJson;
		 }
		 /**
		  * 调用http 请求，返回json对象
		  * @param method 方法名称
		  * @param parms  参数
		  * @return JSONObject
		  */
		 public static JSONObject getHttpRequestMap(String url){
			 JSONObject jo = new JSONObject();
			 try {
			 String json=HtttpRequest.getHttpRequest(url);//
		     if( !"".equals(json) && json!=null ){
				jo =JSONObject.parseObject(json); 
			 }
			} catch (Exception e) {
				logger.error("http请求错误，信息："+e);
				e.printStackTrace();
			}
			return jo;
		 }
		 
		  /**
		     * 通过HttpURLConnection模拟post表单提交
		     * @throws Exception
		     */
		    public static PFMPhoneVeri sendGetYZMXML(String httpUrl,String parms) {
		    	       PFMPhoneVeri fmv=new PFMPhoneVeri();
		    	
				    	try {
				    	    
				    		byte[] temp=httpUrl.getBytes("utf-8");//这里写原编码方式
				            String newStr=new String(temp,"GBK");//这里写转换后的编码方式
				    		logger.debug("sendPost--》"+newStr);
							URL url = new URL(httpUrl);
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");// 提交模式
							conn.setDoOutput(true);// 是否输入参数
							conn.setRequestProperty("Accept-Charset", "GBK");
							conn.setRequestProperty("Content-Length","0");
							conn.setRequestProperty("Content-Type", "text/xml");
							
							
							StringBuilder json = new StringBuilder();
							BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String inputLine = null;
							while ((inputLine = in.readLine()) != null) {
								json.append(inputLine);
							}
							in.close();
				
							if (!"".equals(json) && json != null) {
								String msg=json.toString();
								fmv =(PFMPhoneVeri)convertXmlStrToObject(PFMPhoneVeri.class,msg);
							}
					} catch (Exception e) {
						logger.error("sendPost error--->"+e);
						e.printStackTrace();
						return fmv=null;
					}
		    	return fmv;
			
		    }
		    
		    @SuppressWarnings("unchecked")  
		    /** 
		     * 将String类型的xml转换成对象 
		     */  
		    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {  
		        Object xmlObject = null;  
		        try {  
		            JAXBContext context = JAXBContext.newInstance(clazz);  
		            // 进行将Xml转成对象的核心接口  
		            Unmarshaller unmarshaller = context.createUnmarshaller();  
		            StringReader sr = new StringReader(xmlStr); 
		            xmlObject = unmarshaller.unmarshal(sr);  
		        } catch (JAXBException e) {  
		            e.printStackTrace();  
		        }  
		        return xmlObject;  
		    }  
}
	    
