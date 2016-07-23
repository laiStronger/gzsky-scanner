package com.wenwo.message.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.wenwo.message.api.IChannelService;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.ProjectChannel;

public class ChannelController extends BaseController {

	private IChannelService channelService;

	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}

	public ModelAndView channelView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("channel/channel");
		MessageProjectType messageProjectType = getProjectType(request);
		ProjectChannel projectChannel = channelService.getChannelByProjectType(messageProjectType);
		modelAndView.addObject("projectChannel", projectChannel);
		modelAndView.addObject("project", messageProjectType);
		return modelAndView;
	}

	public ModelAndView channelSave(HttpServletRequest request, HttpServletResponse response) {
		String data = "";
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String id = request.getParameter("id");
//			String messageProject = request.getParameter("messageProjectType");
			String sinaProject = request.getParameter("sinaProject");
			String sinaGroup = request.getParameter("sinaGroup");
			String qqProject = request.getParameter("qqProject");
			String qqGroup = request.getParameter("qqGroup");
			ProjectChannel projectChannel = new ProjectChannel();
			projectChannel.setSinaAccGroupType(sinaProject);
			projectChannel.setSinaGroupType(sinaGroup);
			projectChannel.setQqAccGroupType(qqProject);
			projectChannel.setQqGroupType(qqGroup);
			projectChannel.setMessageProjectType(getProjectType(request));
			if (id == null || "".equals(id)) {
				channelService.save(projectChannel);
				data = "1";
			} else {
				projectChannel.setId(id);
				channelService.update(projectChannel);
				data = "2";
			}
			pw.write(data);
		} catch (Exception e) {
			pw.write("0");
		}
		pw.flush();
		return null;
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
