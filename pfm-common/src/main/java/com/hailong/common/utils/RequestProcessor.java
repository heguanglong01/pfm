package com.hailong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hailong.base.entity.PfmUsers;
import com.hailong.common.CacheData;
import com.hailong.common.handler.PFMHBHandler;
import com.hailong.common.redis.RedisUtil;
import com.pfm.shared.AES;
import com.pfm.shared.PFMAES;
import com.pfm.shared.PFMDataObject;
import com.pfm.shared.PFMDataObject.Base;




/**
 * 基础处理请求类，用来封装处理过程和输出控制，使controller专注业务逻辑处理
 * 
 * @author heguanglong
 * 
 * @date 2016年5月29日
 *
 */
public abstract class RequestProcessor {
	public static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

	public final static void outJson(HttpServletResponse response, String result) {
		PrintWriter out = null;
		try {
			setContentType2Json(response);
			logger.info("return msg--->"+result);
			out = response.getWriter();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
   
	public final static void outJsonMd5(HttpServletResponse response, String result) {
		PrintWriter out = null;
		try {
			setContentType2Json(response);
			out = response.getWriter();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("outJsonMd5-->"+e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public final static String outResponse(HttpServletResponse response,  Base base){
		PrintWriter out = null;
		String msg=PFMAES.compose(CacheData.encryptionKey, base);
		try {
			setContentType2Json(response);
			out = response.getWriter();
			out.print(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("outJsonMd5-->"+e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		
		return msg;
	}

	public final static void outResponseLog(HttpServletResponse response,  Base base,String logType){
		PrintWriter out = null;
		String msg=PFMAES.compose(CacheData.encryptionKey, base);
		try {
			setContentType2Json(response);
			out = response.getWriter();
			out.print(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("outJsonMd5-->"+e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		logger.info(logType+":"+AES.decrypt(CacheData.encryptionKey, msg));
	}
	
	private static void setContentType2Text(HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
	}

	public static void setContentType2Json(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
	}
	
	/*
	 * 验证参数是否正确
	 * */
	public static  PfmUsers  setVeritedParam(String param,String type,HttpServletResponse response){
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		if(param.isEmpty()){
			pfmr.code="4000";
			pfmr.msg="param is null";
			RequestProcessor.outResponseLog(response, pfmr,type);
			return null;
		}
	
		String data=PFMHBHandler.getAESMsg(param);
		PfmUsers pfm= PFMDataObject.gson.fromJson(data, PfmUsers.class);
		/*验证参数*/
		if(pfm ==null || pfm.pfm_phone_number.isEmpty() ){
			pfmr.code="4000";
			pfmr.msg="手机号不能为空";
			RequestProcessor.outResponseLog(response, pfmr,type);
			return null;
		}
		return pfm;
	}
	
	/*
	 * 验证参数是否正确和手机号是否注册
	 * */
	public static  PfmUsers  setVeritedParamReg(String param,String logType,HttpServletResponse response){
		
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		if(param.isEmpty()){
			pfmr.code="4000";
			pfmr.msg="param is null";
			RequestProcessor.outResponseLog(response, pfmr,logType);
			return null;
		}
	
		String data=PFMHBHandler.getAESMsg(param);
		PfmUsers pfm= PFMDataObject.gson.fromJson(data, PfmUsers.class);
		/*验证参数*/
		if(pfm ==null || pfm.pfm_phone_number.isEmpty() ){
			pfmr.code="4000";
			pfmr.msg="手机号不能为空";
			RequestProcessor.outResponseLog(response, pfmr,logType);
			return null;
		}
		
		/*验证是否注册*/
		boolean isExits=RedisUtil.isUserExist(pfm.pfm_phone_number);
		if(!isExits){
			pfmr.code="2002";
			pfmr.msg="用户未注册";
			RequestProcessor.outResponseLog(response, pfmr,logType);
			return null;
		}
		return pfm;
	}
	
	 public static String toJsonFromRequest(HttpServletRequest req) throws IOException {
	        StringBuffer jb = new StringBuffer();
	        String line = null;
	        BufferedReader reader = req.getReader();
	        while ((line = reader.readLine()) != null) {
	            if (logger.isDebugEnabled()) {
	                logger.debug("req to str = " + line);
	            }
	            jb.append(line);
	        }
	        if (logger.isDebugEnabled()) {
	            logger.debug("req to str = " + line);
	        }
	        return jb.toString();
	    }

}
