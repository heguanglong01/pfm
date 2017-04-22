package com.hailong.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定制日志
 *
 * @author wangpeng
 * @since 2016年6月7日
 *
 */
public class CustomLog {

    private final static String separator = String.valueOf((char) 0x01);
    private static Logger requestLogger = LoggerFactory.getLogger("heartbeatRequest");
    private static Logger responseLogger = LoggerFactory.getLogger("hearbeatResponse");
    private static String hostname = "";
    
    static {
        if(StringUtils.isEmpty(hostname)){
            try {
                InetAddress ia = InetAddress.getLocalHost();
                //获取计算机主机名
                hostname = ia.getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 输出日志。
     * 当设备上报心跳数据时，type=1，jsonMsg为心跳数据string
     * @param type
     * @param jsonMsg
     */
    public static void trackLog(int type, String jsonMsg){
        switch(type) {
        case 1:
            requestLogger.info(DateUtil.formatDateTime()+"|"+jsonMsg);
            break;
        case 2:
            responseLogger.info(DateUtil.formatDateTime()+"|"+jsonMsg);
            break;
        default:
            break;
        }
    }
    
    /**
     * 按格式输出日志
     * @param type 设备->服务器为1，服务器->设备为2
     * @param time 设备端时间
     * @param ip 设备端ip
     * @param msg 其他如key\id\原始消息等信息的字符串数组
     */
    public static void trackLog(int type, String time, String ip, String[] msg){
        switch(type) {
            case 1:
                requestLogger.info(hostname+separator+type+separator+DateUtil.formatDateTime()+separator+time+separator+ip+separator+msgToString(msg));
                break;
            case 2:
                requestLogger.info(hostname+separator+type+separator+DateUtil.formatDateTime()+separator+time+separator+ip+separator+msgToString(msg));
                break;
            default:
                break;
            }
    }
    
    private static String msgToString(String[] msgs){
        StringBuilder sb = new StringBuilder();
        for(String s:msgs){
            sb.append(s);
            sb.append(separator);
        }
        
        return sb.toString();
    }
    
}
