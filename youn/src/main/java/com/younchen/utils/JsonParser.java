package com.younchen.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @author kang
 * @date 2014年11月27日
 * @description
 */
public class JsonParser {
	public static Gson gson = new Gson();

	public static   <T> T parse(String jsonData, Class<T> clazz) {
		T t = null;
		try {
			t = gson.fromJson(jsonData, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return t;
		}
		return t;
	}

	public static <T> T jsonToObject(String jsonData, Class<T> clazz) {
		try {
			return gson.fromJson(jsonData, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> changeGsonToList(String gsonString) {
		List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
		}.getType());
		return list;
	}

	public static String toJsonStr(Object object) {
		return gson.toJson(object);
	}

	public static <T> List<Map<String, T>> changeGsonToListMaps(
			String gsonString) {
		List<Map<String, T>> list = null;
		list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
		Map<String, T> map = null;
		map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}
}
