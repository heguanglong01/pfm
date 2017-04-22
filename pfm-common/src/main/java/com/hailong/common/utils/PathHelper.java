package com.hailong.common.utils;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class PathHelper {
    private static final Logger logger = Logger.getLogger(PathHelper.class);
    
    public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
    // 默认的路径,私有属性，只能读取
    private static String webAbsolutePath = "not init";
    
    public static String getWebAbsolutePath() {
        return webAbsolutePath;
    }

    /**
     * 初始化路径
     * @param context
     */
    public static void initPath(ServletContext context) {
        if (context == null)
            return;
        webAbsolutePath = context.getRealPath("/");
        
        logger.debug("webAbsolutePath:"+webAbsolutePath);
    }

}
