package com.wenwo.message.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wenwo.message.enums.MessageProjectType;

public abstract class BaseController extends MultiActionController{
	
	protected static Gson GSON;
	static{
		GsonBuilder builder = new GsonBuilder();
		GSON = builder.create();
	}
	
	public void printFailed(HttpServletResponse response, String result) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("is_succ", "N");
		resultMap.put("error_msg", result);
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(GSON.toJson(resultMap));
		response.getWriter().flush();
	}
	
	public void printSuccess(HttpServletResponse response, Object result) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap.put("is_succ", "Y");
		resultMap.put("data", result);
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(GSON.toJson(resultMap));
		response.getWriter().flush();
	}
	
	public MessageProjectType getMessageProject(String project){
		if(StringUtils.isEmpty(project)){
			return MessageProjectType.DEFAULT;
		}
		return MessageProjectType.valueOf(project);
	}

}
