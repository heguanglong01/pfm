package com.hailong.common.utils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
public class GsonUtil {
	/** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) {  
        //Gson gson = new Gson();  
    	Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type 
     * @return  
     */  
    public static <T> T fromJson(String str, Type type) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, type);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type  
     * @return  
     */  
    public static <T> T fromJson(String str, Class<T> type) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, type);  
    }  
    

   /**
    * 将Json数组解析成相应的映射对象列表，示例：List<ProductRelevant> prList=
    * GsonUtil.parseJsonArrayWithGson(productRelevant, new TypeToken<ArrayList<ProductRelevant>>() {});
    * @param jsonData 需要转换的json数组
    * @param type 转换的list包含的对象的类型
    * @return list对象
    */
    public static <T> List<T> parseJsonArrayWithGson(String jsonData, TypeToken<ArrayList<T>> type) {
        Gson gson = new Gson();
        List<T> result = gson.fromJson(jsonData, type.getType());
        return result;
    }


}
