package com.sss.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AppJson {
	
	public static JSONObject ajaxReturn(int status , String message , Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("s", status);
		jsonObject.put("m", message);
		jsonObject.put("d", JSON.toJSONString(data));
		return jsonObject;
	}
	public static JSONObject ajaxReturn(int status , String message ) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("s", status);
		jsonObject.put("m", message);
		return jsonObject;
	}
	public static JSONObject ajaxReturn(int status ) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("s", status);
		return jsonObject;
	}
}
