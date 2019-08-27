package com.summer.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {

	private JsonUtils() {throw new IllegalStateException("Utility class");}

	private static Gson gson = new GsonBuilder()
//	.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
	//支持Map的key为复杂对象的形式
    .enableComplexMapKeySerialization()
	//时间转化为特定格式
    .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
//    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
    .setPrettyPrinting() //对json结果格式化.
    .setVersion(1.0).create() ;
	
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String json,Class<T> t){
		return gson.fromJson(json, t);
	}
	
	public static <T> T fromJson(String json,Type typeOfT){
		return gson.fromJson(json,typeOfT);
	}
	
	public static String getString(String json,String field){
		if(json==null){
			return null;
		}
		String result = null;
		try{
			@SuppressWarnings("unchecked")
			Map<String,String> p = gson.fromJson(json, Map.class);
			result = p.get(field);
		} catch(Exception e){
			//
		}
		return result;
	}
	
}
