package com.hailong.pfm;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.hailong.common.redis.RedisUtil;
import com.hailong.common.thread.PFMThreadPoolExecutor;
import com.hailong.common.utils.ConfigUtil;


@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger logger = Logger.getLogger(StartupListener.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
		//只在初始化“根上下文”的时候执行
        if (applicationEvent.getSource() instanceof XmlWebApplicationContext) {
        	System.out.println("root webappname:"+((XmlWebApplicationContext) applicationEvent.getSource()).getDisplayName());
//            if (((XmlWebApplicationContext) applicationEvent.getSource()).getDisplayName().equals("Root WebApplicationContext")) {
            	logger.debug("init dmc data ......");
            	//初始化配置信息
    			ConfigUtil.init();
    					
    			/*连接池的监控属性 初始化线程池的参数*/
    			String poolValue=ConfigUtil.getConfig("maximum.pool.size");
    			RedisUtil.init(ConfigUtil.getConfig("redis_ip"), ConfigUtil.getConfig("redis_port"),ConfigUtil.getConfig("redis_password"));
    			int maximumPoolSize=0;
    			if("MAX_VALUE".equals(poolValue)){
    				maximumPoolSize=Integer.MAX_VALUE;	
    			  }else{
    				Integer.valueOf(poolValue);
    			}
    			new PFMThreadPoolExecutor(Integer.valueOf(ConfigUtil.getConfig("core.pool.size")),maximumPoolSize, Long.valueOf(ConfigUtil.getConfig("keep.alive.time")));
    			
//            }
        }
 }

}
