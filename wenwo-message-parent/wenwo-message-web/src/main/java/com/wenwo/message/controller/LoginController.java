package com.wenwo.message.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录
 * @author laisq
 *
 */
public class LoginController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response) {
		request.removeAttribute("errorTip");
		ModelAndView modelAndView = new ModelAndView("common/login");
		return modelAndView;
	}

	
	/**
	 * form表单提交
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView loginInForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.removeAttribute("errorTip");
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		logger.info("获取的用户名是：{}",username);
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Charset", "UTF-8");
		
		if(checkLogin(username,pass)) {
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("pass", pass);
			ModelAndView mv = new ModelAndView("redirect:/message.do?act=templateView");
			//ModelAndView mv = new ModelAndView("messageType/search_msg");
			return mv;
		} else {
			//ModelAndView mv = new ModelAndView("login.do?act=loginView");
			ModelAndView mv = new ModelAndView("common/login");
			request.setAttribute("errorTip", "用户名或密码错误!");
			request.setAttribute("username", username);
			request.setAttribute("pass", pass);
            return mv;        	 
        }
		
	}
	
	/**
	 * js登录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void loginIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		logger.info("登录用户：{}",username);
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Charset", "UTF-8");
		
		if(checkLogin(username,pass)) {
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("pass", pass);
			response.getWriter().write("message.do?act=templateView");
			return;
		} else {
            return;        	 
        }
		
	}
	
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void loginout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("username");
		response.getWriter().write("login.do?act=loginView");
		
	}
	
	/**
	 * 检查登录
	 * @param username
	 * @param pass
	 * @return
	 */
	private boolean checkLogin(String username,String pass) {
		if(username == null || pass == null) {
			return false;
		}
		
		if(username.equals("chulin") && pass.equals("wenwo125")) {
			return true;
        } else if(username.equals("admin") && pass.equals("wenwo123")) {
        	return true;
        }  else if(username.equals("yunying") && pass.equals("yunying123")) {
        	return true;
        }
		return false; 
	}
	
	
}
