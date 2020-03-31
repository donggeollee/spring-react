package com.web.react.utils;

import com.google.gson.Gson;

public class JsonHelper {
	
	public static Object Obj2Json(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	
}
 