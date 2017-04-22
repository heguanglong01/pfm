package com.hailong.pfm.controller.welcome;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hailong.base.entity.BackInfo;
import com.hailong.base.entity.PfmUsers;
import com.hailong.common.CacheData;
import com.hailong.common.handler.PFMHBHandler;
import com.hailong.common.redis.RedisUtil;
import com.hailong.common.utils.ConfigUtil;
import com.hailong.common.utils.RandonUtil;
import com.hailong.common.utils.RequestProcessor;
import com.hailong.pfm.service.PfmFeedBackService;
import com.hailong.pfm.service.PfmUsersService;
import com.pfm.shared.AES;
import com.pfm.shared.PFMAES;
import com.pfm.shared.PFMDataObject;
import com.sun.tools.javac.util.List;



@Controller
@RequestMapping("/welcome")
public class PFMAPPWelcomeController {
	public static final Logger logger = LoggerFactory.getLogger(PFMAPPWelcomeController.class);
     
	@Autowired 
	private PfmUsersService pfmUsersService;
	
	@Autowired 
	private PfmFeedBackService pfmFeedBackService;
	
	
	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String msg="";
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		try {
			String  param = IOUtils.toString(request.getInputStream());
			if(param.isEmpty()){
				pfmr.code="4000";
				pfmr.msg="param is null";
			}else{
				String data=PFMHBHandler.getAESMsg(param);
				PfmUsers pfm= PFMDataObject.gson.fromJson(data, PfmUsers.class);
				/*加密密码*/
				pfm.pfm_user_password=AES.encrypt(CacheData.encryptionKey, pfm.pfm_user_password);
				int i=pfmUsersService.addPfmUserDao(pfm);
				if(i>0){
					pfmr.code="2000";
					pfmr.msg="register Success";
					pfmr.time=System.currentTimeMillis();
					RedisUtil.setUserInfo(pfm.pfm_phone_number, PFMDataObject.gson.toJson(pfm));
				}
			}
			} catch (Exception e) {
				 pfmr.code="4000";
				 pfmr.msg="服务端错误，请联系管理员";
				 logger.error("pfmerver fail,param  :" + e.getMessage(), e);
				 e.printStackTrace();
		    }
		msg=PFMAES.compose(CacheData.encryptionKey, pfmr);
		RequestProcessor.outJsonMd5(response, msg);
		logger.info("register returnMsg:"+AES.decrypt(CacheData.encryptionKey, msg));
		
	}
	
	@RequestMapping("/isPhoneExits")
	public void phoneExits(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  String param = "";//心跳的参数。
		  String data="";//心跳数据包
		  String msg="";
		  PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		  try {
			  
			 param = IOUtils.toString(request.getInputStream());
			 if(param == null || param.length()==0){
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
	
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String logType="login";
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		try {
			    String  param = IOUtils.toString(request.getInputStream());
			    PfmUsers pfm =RequestProcessor.setVeritedParamReg(param, logType, response);
			    if(RequestProcessor.setVeritedParamReg(param, logType, response) == null){return;}
				
				/*验证密码*/
				String value=RedisUtil.getUserInfo(pfm.pfm_phone_number);
				PFMDataObject.PfmUser pfmUser=PFMDataObject.gson.fromJson(value, PFMDataObject.PfmUser.class);
                String pwd= PFMHBHandler.getAESMsg(pfmUser.pfm_user_password); 
                if(!pfm.pfm_user_password.equals(pwd)){
                	pfmr.code="2001";
					pfmr.msg="密码不正确";
					RequestProcessor.outResponseLog(response, pfmr,logType);
					return;
                }
			
			} catch (Exception e) {
				 pfmr.code="4000";
				 pfmr.msg="服务端错误，请联系管理员";
				 logger.error("pfmerver fail,param  :" + e.getMessage(), e);
				 e.printStackTrace();
				 RequestProcessor.outResponseLog(response, pfmr,logType);
				 return;
		    }
		
		 pfmr.code="2000";
		 pfmr.msg="登录成功";
		 RequestProcessor.outResponseLog(response, pfmr,logType);
	}
	
	
	@RequestMapping("/verifCode")
	public void verifCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String logType="verifCode";
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		try {
			    String  param = IOUtils.toString(request.getInputStream());
			    PfmUsers pfm =RequestProcessor.setVeritedParamReg(param, logType, response);
			    if(RequestProcessor.setVeritedParamReg(param, logType, response) == null){return;}
				
				String ver=RandonUtil.getCharAndNumr(4);
				String content= ConfigUtil.getConfig("pfm_phone_yzm_content").replaceAll("pfm_phone_msg_info",ver);
                String httpUrl=ConfigUtil.getConfig("pfm_phone_zym_url")+"&mobile="+pfm.pfm_phone_number+"&content="+content;
			    logger.info("longin httpUrl"+httpUrl);
			    
//			    PFMPhoneVeri returnmsg=HtttpRequest.sendGetYZMXML(httpUrl, null);
//			    if(returnmsg != null){
			    	pfmr.code="2000";
			        pfmr.msg=ver;
			    	RequestProcessor.outResponseLog(response, pfmr,logType);
//			    }
			    
			} catch (Exception e) {
				 pfmr.code="4000";
				 pfmr.msg="服务端错误，请联系管理员";
				 logger.error("pfmerver fail,param  :" + e.getMessage(), e);
				 e.printStackTrace();
				 RequestProcessor.outResponseLog(response, pfmr,logType);
				 return;
		    }
	}
	
	@RequestMapping("/updatepwd")
	public void updatepwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String logType="updatepwd";
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		try {
			    String  param = IOUtils.toString(request.getInputStream());
			    PfmUsers pfm =RequestProcessor.setVeritedParam(param, logType, response);
			    if(RequestProcessor.setVeritedParamReg(param, logType, response) == null){return;}
				
			    String aesPwd=AES.encrypt(CacheData.encryptionKey, pfm.pfm_user_password);
			    /*加密密码*/
				pfm.pfm_user_password=aesPwd;
				pfm.pfm_user_name=System.currentTimeMillis()+"";
				int i=pfmUsersService.updatePfmUserDao(pfm);
				if(i>0){
					pfmr.code="2000";
					pfmr.msg="update Pwd Success";
					pfmr.time=System.currentTimeMillis();
					
					String userStr=RedisUtil.getUserInfo(pfm.pfm_phone_number);
					/*更新缓存中的密码,不存在直接添加*/
					if(userStr ==null ||userStr.isEmpty()){
						RedisUtil.setUserInfo(pfm.pfm_phone_number, PFMDataObject.gson.toJson(pfm)); return;
					}
					
					 /*存在只修改密码*/
					 PfmUsers redisPfm=PFMDataObject.gson.fromJson(userStr, PfmUsers.class);
					 redisPfm.pfm_user_password=aesPwd;
					 RedisUtil.setUserInfo(pfm.pfm_phone_number, PFMDataObject.gson.toJson(redisPfm));
					 RequestProcessor.outResponseLog(response, pfmr,logType);
				}
			    
			} catch (Exception e) {
				 pfmr.code="4000";
				 pfmr.msg="服务端错误，请联系管理员";
				 logger.error("pfmerver fail,param  :" + e.getMessage(), e);
				 e.printStackTrace();
				 RequestProcessor.outResponseLog(response, pfmr,logType);
				 return;
		    }
	}
	
	@RequestMapping("/addBackInfo")
	public void addBackInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String logType="addBackInfo";
		PFMDataObject.PFMR pfmr=new PFMDataObject.PFMR();
		try {
			    String  param = IOUtils.toString(request.getInputStream());
			    if(param.isEmpty()){
					pfmr.code="4000";
					pfmr.msg="param is null";
					RequestProcessor.outResponseLog(response, pfmr,logType);
					return ;
				}
			
				String data=PFMHBHandler.getAESMsg(param);
				BackInfo bakcINfo= PFMDataObject.gson.fromJson(data, BackInfo.class);
			    
				ArrayList<BackInfo> list=new ArrayList<BackInfo>();
				list.add(bakcINfo);
				int i=pfmFeedBackService.addBatchPfmFeedBack(list);
				if(i>0){
					pfmr.code="2000";
					pfmr.msg="add feedback Success";
					pfmr.time=System.currentTimeMillis();
					RequestProcessor.outResponseLog(response, pfmr,logType);
				}
			    
			} catch (Exception e) {
				 pfmr.code="4000";
				 pfmr.msg="服务端错误，请联系管理员";
				 logger.error("pfmerver fail,param  :" + e.getMessage(), e);
				 e.printStackTrace();
				 RequestProcessor.outResponseLog(response, pfmr,logType);
				 return;
		    }
	}
}
