package com.wenwo.message.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 拦截器中的prehandler中返回false,则根本不会渲染到view层了
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String username = (String) request.getSession().getAttribute("username"); 
		if(username==null){  //进入登录页
			logger.info("进入username=null，username：{}",username);  
			return true; 
		} else {
			logger.info("进入username!=null，username：{}",username);  
			return false;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		logger.info("开始跳转到:login.do?act=loginView");  
		response.sendRedirect("login.do?act=loginView");  
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	

}
