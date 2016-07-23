package com.wenwo.message.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.wenwo.message.api.IVariableService;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.Variable;
import com.wenwo.message.model.Variable.VariableType;

public class VariableController extends BaseController {

	private IVariableService variableService;

	public void setVariableService(IVariableService variableService) {
		this.variableService = variableService;
	}

	private final Gson gson = new Gson();

	public ModelAndView variableView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("variable/variable");
		VariableType[] variableTypes = VariableType.values();
		modelAndView.addObject("variableTypes", variableTypes);
		modelAndView.addObject("variables", getList(request));
		return modelAndView;
	}

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("variable/variable_list");
		modelAndView.addObject("variables", getList(request));
		return modelAndView;
	}

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("variable/variable_list");
		String id = request.getParameter("id");
		variableService.delete(id);
		modelAndView.addObject("variables", getList(request));
		return modelAndView;
	}

	public List<Variable> getList(HttpServletRequest request) {
		VariableType variableType = getVariableTypeFromRequest(request);
		MessageProjectType messageProjectType = getProjectType(request);
		List<Variable> variables = variableService.getVariables(variableType, messageProjectType);
		return variables;
	}

	public void groupVariableByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<VariableType, List<Variable>> variable = variableService.groupVariableByType(MessageProjectType.DEFAULT);
		this.printSuccess(response, variable);
	}

	/**
	 * 获取变量选择器
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getVariableSelectPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("variable/variable_select_page");
		String projectType = request.getParameter("projectType");
		MessageProjectType messageProjectType = StringUtils.isEmpty(projectType) ? MessageProjectType.DEFAULT
				: MessageProjectType.valueOf(projectType);
		Map<VariableType, List<Variable>> variableMap = variableService.groupVariableByType(messageProjectType);
		mv.addObject("variableMap", variableMap);
		return mv;
	}

	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("variable/variable_list");
//		VariableType variableType = getVariableTypeFromRequest(request);
		MessageProjectType messageProjectType = getProjectType(request);
		Variable variable = new Variable();
		String id = request.getParameter("id");
		if("variable".equals(id)){
			id = null;
		}
		String group = request.getParameter("group");
		String variableName = request.getParameter("variableName");
		String variableExpress = request.getParameter("variableExpess");
		if(StringUtils.isEmpty(variableName) || StringUtils.isEmpty(variableExpress)){
			return null;
		}
		variable.setVariableType(VariableType.valueOf(group));
		variable.setName(variableName.trim());
		variable.setVariableProjectType(messageProjectType);
		variable.setVelocityExpress(variableExpress.trim());
		if (id == null || "".equals(id)) {
			variableService.save(variable);
		} else {
			variable.setId(id);
			variableService.update(variable);
		}
		VariableType[] variableTypes = VariableType.values();
		modelAndView.addObject("variableTypes", variableTypes);
		modelAndView.addObject("variables", getList(request));
		return modelAndView;
	}

	public ModelAndView editer(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("variable/variable_list");
		String id = request.getParameter("id");
		VariableType[] variableTypes = VariableType.values();
		Variable variable = variableService.getVariableById(id);
		modelAndView.addObject("variableTypes", variableTypes);
		modelAndView.addObject("variable", variable);
		return modelAndView;
	}

	public ModelAndView getVariableByGroup(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		VariableType variableType = getVariableTypeFromRequest(request);
		List<Variable> variables = variableService.getVariableByType(variableType);
		String str = gson.toJson(variables);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(str);
			pw.flush();
		} catch (IOException e) {
			logger.error("失败", e);
		}
		return null;
	}

	public ModelAndView getexpress(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		List<Variable> variables = variableService.getexpress(name);
		String str = "";
		if (variables != null && variables.size() > 0) {
			str = variables.get(0).getVelocityExpress();
		}
		try {
			PrintWriter pw = response.getWriter();
			pw.write(str);
			pw.flush();
		} catch (IOException e) {
			//do nothind
		}
		return null;
	}

	public void save() {
		Variable variable = new Variable();
		variable.setVariableProjectType(MessageProjectType.WEIWEN);
		variable.setEndTime(new Date());
		variable.setName("push");
		variable.setVariableType(VariableType.ASK);
		variable.setVelocityExpress("dfadfaf");
		variableService.save(variable);
		variable.setVariableProjectType(MessageProjectType.WEIWENQQ);
		variableService.save(variable);
		variable.setVariableProjectType(MessageProjectType.WENWO);
		variableService.save(variable);
		variable.setVariableProjectType(MessageProjectType.DEFAULT);
		variableService.save(variable);
	}

	private VariableType getVariableTypeFromRequest(HttpServletRequest request) {
		String variable = request.getParameter("variableType");
		if (variable == null || variable.trim().length() <= 0) {
			return null;
		}
		try {
			return VariableType.valueOf(variable);
		} catch (Exception e) {
			return null;
		}

	}

	private MessageProjectType getProjectType(HttpServletRequest request) {
		String messageProjectType = request.getParameter("messageProjectType");
		if (messageProjectType == null || messageProjectType.trim().length() <= 0) {
			return MessageProjectType.DEFAULT;
		}
		try {
			return MessageProjectType.valueOf(messageProjectType);
		} catch (Exception e) {
			return null;
		}

	}

}
