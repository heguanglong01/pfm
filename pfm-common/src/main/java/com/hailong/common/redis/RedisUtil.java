package com.hailong.common.redis;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    
    public static JedisPool pool;
   
    public static void init(String host,String port, String auth){
    	// 建立连接池配置参数
    	JedisPoolConfig  config = new JedisPoolConfig ();
        config.setMaxIdle(1000);
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(false);
        config.setTestOnBorrow(true); 
        config.setMaxTotal(5000);
        if(!"".equals(auth) && auth!= null){
        	 // 创建连接池
            pool = new JedisPool(config, host, Integer.valueOf(port),Protocol.DEFAULT_TIMEOUT,auth);
        }else{
        	pool = new JedisPool(config, host, Integer.valueOf(port),Protocol.DEFAULT_TIMEOUT);
        }
       
    }
    public static void main(String[] args) throws IOException {
    	   RedisUtil.init("123.56.246.11", "6379", null);
    	   String phone="1111111111111";
    	   RedisUtil.setUserInfo(phone, "test");
    	   System.out.println(RedisUtil.isUserExist(phone));
    	   System.out.println(RedisUtil.getUserInfo(phone));
    	   
    	    phone="150013290961";
    	   System.out.println(RedisUtil.isUserExist(phone));
     	   System.out.println(RedisUtil.getUserInfo(phone));
    }
    /**
	 * generatorKey
	 * @param serviceName:项目简称
	 * @param moduleName：模块简称
	 * @param name 保存的名称
	 * @return
	 */
	public static String generatorKey(String serviceName, String moduleName, String name){
	    return serviceName + "#" + moduleName+"#" + name;
	}
	
	/**
	 * smart 设置用户信息
	 * @return
	 */
	public static void setUserInfo(String phone,String values){
		Jedis jedis =pool.getResource();
		String key = generatorKey(RedisName.PFM,RedisName.USERS,"INFO");
		jedis.hset(key, phone, values);
		jedis.close();
	}
	
	/**
	 * smart 设置用户信息
	 * @return
	 */
	public static boolean isUserExist(String phone){
		boolean isExist;
		Jedis jedis =pool.getResource();
		String key = generatorKey(RedisName.PFM,RedisName.USERS,"INFO");
		isExist=jedis.hexists(key, phone);
		jedis.close();
		return isExist;
	}
	
	/**
	 * 获取 设置用户信息
	 * @return
	 */
	public static String getUserInfo(String phone){
		String value;
		Jedis jedis =pool.getResource();
		String key = generatorKey(RedisName.PFM,RedisName.USERS,"INFO");
		value=jedis.hget(key, phone);
		jedis.close();
		return value;
	}
				
}
