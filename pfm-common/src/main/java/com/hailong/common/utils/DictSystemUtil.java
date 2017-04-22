package com.hailong.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictSystemUtil {
//	private static final Logger logger = Logger.getLogger(DictSystemUtil.class);

	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
	private static Map<String, String> biKeyLableMap = new HashMap<String, String>();
	private static Map<String, String> biLableNameMap = new HashMap<String, String>();

	

	public static Map<String, String> getBiKeyLableMap() {
		return biKeyLableMap;
	}

	public static void setBiKeyLableMap(Map<String, String> biKeyLableMap) {
		DictSystemUtil.biKeyLableMap = biKeyLableMap;
	}

	public static Map<String, String> getBiLableNameMap() {
		return biLableNameMap;
	}

	public static void setBiLableNameMap(Map<String, String> biLableNameMap) {
		DictSystemUtil.biLableNameMap = biLableNameMap;
	}

	public static Map<String, List<String>> getMap() {
		return map;
	}

	public static void setMap(Map<String, List<String>> map) {
		DictSystemUtil.map = map;
	}

	public static List<String> getValueByTypeId(String typeId){
		return map.get(typeId);
	}

	public static String getValueByTypeIdOne(String typeId){
		List<String> list = map.get(typeId);
		
		if(list!=null && list.size()>0)
			return list.get(0);
		
		return "";
	}
}
