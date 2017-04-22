package com.hailong.pfm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hailong.common.CacheData;
import com.hailong.common.handler.PFMHBHandler;
import com.hailong.common.redis.RedisUtil;
import com.hailong.common.utils.RequestProcessor;
import com.pfm.shared.AES;
import com.pfm.shared.PFMAES;
import com.pfm.shared.PFMDataObject;



@Controller
public class PFMServerController {
	public static final Logger logger = LoggerFactory.getLogger(PFMServerController.class);

	@RequestMapping("")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		  String param = "";
		  String data="";//心跳数据包
		  String msg="";
		 
		  PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		  
		  try {
			 param = IOUtils.toString(request.getInputStream());
			 
			 if(param == null || param.length()==0 ){
				 
				 pfmr.code="4000";
				 pfmr.verifi=false; 
				 pfmr.msg="param is not null";
				 
			 }else{
				 data=PFMHBHandler.getAESMsg(param);
				 logger.info("http,type=isPhoneExits,data:" + data);
				 PFMDataObject.PFM pfm= PFMDataObject.gson.fromJson(data, PFMDataObject.PFM.class);
		
				 boolean isExits=RedisUtil.isUserExist(pfm.phone);
				 pfmr.code="2000";
				 
				 if(isExits){
					 pfmr.verifi=false;
					 pfmr.msg="手机号已经注册.";
					 
					 /*如果手机已经注册，开始获取下发的任务等信息*/
				 }else{
					 pfmr.verifi=true;
					 pfmr.msg="手机号码未注册，查看手机短信是否收到验证码。";
				 }
			
			
		    	 msg=PFMDataObject.gson.toJson(pfmr);
			     logger.info("msg:"+msg);
			 }
		    
		  } catch (Exception e) {
				 pfmr.code="4000";
				 pfmr.verifi=false;
				 pfmr.msg="服务端错误，请联系管理员";
				 logger.error("pfmerver fail,param  :" + e.getMessage(), e);
				 e.printStackTrace();
		}
		  
		msg=PFMAES.compose(CacheData.encryptionKey, pfmr);
		RequestProcessor.outJsonMd5(response, msg);
		logger.info("returnMsg:"+AES.decrypt(CacheData.encryptionKey, msg));
	
	}
	
	
	
	
}
