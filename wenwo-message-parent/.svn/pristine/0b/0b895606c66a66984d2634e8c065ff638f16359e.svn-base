package com.wenwo.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wenwo.message.api.IInternalTemplateService;
import com.wenwo.message.api.IMessageTypeService;
import com.wenwo.message.api.IShareService;
import com.wenwo.message.enums.MainType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.MessageType.MessageTypeChannel;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.PicTemplate.CutInfo;
import com.wenwo.message.model.PicTemplate.ImageInfo;
import com.wenwo.message.model.PicTemplate.LineInfo;
import com.wenwo.message.model.PicTemplate.TextInfo;
import com.wenwo.message.model.TextTemplate;
import com.wenwo.message.model.WeiboShareType;
import com.wenwo.platform.utils.CheckUtils;

/**
 * 消息类型相关
 * 
 * @author shuangtai
 * 
 */
public class WeiboShareController extends BaseController {

	public IInternalTemplateService getInternalTemplateService() {
		return internalTemplateService;
	}

	public void setInternalTemplateService(IInternalTemplateService internalTemplateService) {
		this.internalTemplateService = internalTemplateService;
	}

	public void setShareService(IShareService shareService) {
		this.shareService = shareService;
	}

	private IMessageTypeService messageTypeService;

	private IInternalTemplateService internalTemplateService;

	private IShareService shareService;

	public ModelAndView addShare(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("share/share_msg");
		String project = request.getParameter("project");
		MessageProjectType projectType = this.getMessageProject(project);
		mv.addObject("project_type", projectType);
		MainType[] mainTypes = MainType.values();
		mv.addObject("main_types", mainTypes);
		return mv;
	}

	public ModelAndView shareView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("share/share_view");
		String projectType = request.getParameter("projectType");
		MessageProjectType projectTypeEnum = MessageProjectType.valueOf(projectType);
		List<WeiboShareType> shareList = shareService.getShareList(projectTypeEnum);
		mv.addObject("shareList", shareList);
		return mv;
	}

	public ModelAndView getShareList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("share/share_list");
		String projectType = request.getParameter("projectType");
		MessageProjectType projectTypeEnum = MessageProjectType.valueOf(projectType);
		List<WeiboShareType> shareList = shareService.getShareList(projectTypeEnum);
		mv.addObject("shareList", shareList);
		return mv;
	}

	public void updateMessageTypeChannel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String messageTypeId = request.getParameter("messageTypeId");
		if (StringUtils.isEmpty(messageTypeId)) {
			logger.info("未传入有效消息类型id");
			return;
		}
		String sinaAccGroupType = request.getParameter("sinaAccGroupType");
		String sinaGroupName = request.getParameter("sinaGroupName");
		String qqAccGroupType = request.getParameter("qqAccGroupType");
		String qqGroupName = request.getParameter("qqGroupName");
		int result = messageTypeService.updateMessageTypeChannel(messageTypeId, sinaAccGroupType, sinaGroupName,
				qqAccGroupType, qqGroupName);
		if (result > 0) {
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("messageTypeId", messageTypeId);
			this.printSuccess(response, resultMap);
		} else {
			this.printFailed(response, "更新失败");
		}
	}

	@Deprecated
	public void updateMessageTypeStatus(HttpServletRequest request, HttpServletResponse response) {
		String messageTypeId = request.getParameter("messageTypeId");
		if (StringUtils.isEmpty(messageTypeId)) {
			logger.info("未传入有效消息类型id");
			return;
		}
		// Status newStatusEnum = StringUtils.isEmpty(newStatus) ? Status.IN_USE : Status.valueOf(newStatus);
		// MessageType messageType = messageTypeService.getMessageTypeById(messageTypeId);
		// if(messageType != null && messageType.getStatus() != newStatusEnum){
		// messageType.setStatus(newStatusEnum);
		// messageTypeService.updateMessageType(messageType);
		// }
	}

	public void deleteMessageType(HttpServletRequest request, HttpServletResponse response) {
		String messageTypeId = request.getParameter("messageTypeId");
		if (StringUtils.isEmpty(messageTypeId)) {
			return;
		}
		messageTypeService.deleteMessageTypeById(messageTypeId);
	}

	public void addShareMessageType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonEncoded = request.getParameter("encoded");
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(jsonEncoded).getAsJsonObject();

		WeiboShareType weiboShareType = new WeiboShareType();
		String messageProjectType = jsonObject.get("projectType").getAsString();
		weiboShareType.setMessageProjectType(MessageProjectType.valueOf(messageProjectType));
		String typeName = jsonObject.get("typeName").getAsString();
		weiboShareType.setTypeName(typeName);
		JsonObject weiboTemplateInfo = jsonObject.get("weiboTemplateInfo") == null ? null : jsonObject.get(
				"weiboTemplateInfo").getAsJsonObject();
		if (weiboTemplateInfo != null) {

			String textTemplateId = null;
			String content = weiboTemplateInfo.get("weibo_template_text") == null ? null : weiboTemplateInfo
					.get("weibo_template_text").getAsString().trim();
			if (StringUtils.isEmpty(content)) {
				this.printFailed(response, "微博文本模版为空");
				return;
			}
			String description = weiboTemplateInfo.get("weibo_template_description") == null ? null :weiboTemplateInfo
					.get("weibo_template_description").getAsString().trim();
			if(StringUtils.isEmpty(description)){
				this.printFailed(response, "微博文本描述为空");
				return;
			}
			boolean isNeed = weiboTemplateInfo.get("isNeed").getAsBoolean();
			String reContent = null;
			if (isNeed) {
				reContent = weiboTemplateInfo.get("re_weibo_template_text") == null ? null : weiboTemplateInfo
						.get("re_weibo_template_text").getAsString().trim();
				if (StringUtils.isEmpty(reContent)) {
					this.printFailed(response, "微博转发文本模版为空");
					return;
				}
				weiboShareType.setDescription(description);
				TextTemplate reTextTemplate = internalTemplateService.createTextTemplate(reContent, "shuangtai.li");
				String reTextTemplateId = reTextTemplate == null ? null : reTextTemplate.getId();
				weiboShareType.setReTextTemlateId(reTextTemplateId);
			}
			TextTemplate textTemplate = internalTemplateService.createTextTemplate(content, "shuangtai.li");
			textTemplateId = textTemplate == null ? null : textTemplate.getId();

			JsonObject pic_template_info = weiboTemplateInfo.getAsJsonObject("pic_template_info");
			PicTemplate picTemplate = getPicTemplate(pic_template_info);

			PicTemplate dbTemplate = picTemplate == null ? null : internalTemplateService.savePicTemplate(picTemplate);
			String dbTemplateId = dbTemplate == null ? null : dbTemplate.getId();

			weiboShareType.setPicTemplateId(dbTemplateId);
			weiboShareType.setTextTemplateId(textTemplateId);
		}
		shareService.addShare(weiboShareType);
		this.printSuccess(response, "上传成功");
	}

	/**
	 * @param weiboTemplateInfo
	 * @return
	 */
	private MessageTypeChannel getMessageTypeChannel(JsonObject weiboTemplateInfo, MessageType newMessageType) {
		if (weiboTemplateInfo == null || newMessageType == null) {
			return null;
		}

		JsonObject messageTypeChannel = weiboTemplateInfo.getAsJsonObject("messageTypeChannel");
		if (messageTypeChannel == null) {
			return null;
		}

		if (messageTypeChannel.get("sinaAccGroupType") == null || messageTypeChannel.get("sinaGroupName") == null
				|| messageTypeChannel.get("qqAccGroupType") == null || messageTypeChannel.get("qqGroupName") == null) {
			return null;
		}
		String sinaAccGroupType = messageTypeChannel.get("sinaAccGroupType").getAsString().trim();
		String sinaGroupName = messageTypeChannel.get("sinaGroupName").getAsString().trim();
		String qqAccGroupType = messageTypeChannel.get("qqAccGroupType").getAsString().trim();
		String qqGroupName = messageTypeChannel.get("qqGroupName").getAsString().trim();
		return newMessageType.new MessageTypeChannel(sinaAccGroupType, sinaGroupName, qqAccGroupType, qqGroupName);
	}

	/**
	 * @param pic_template_info
	 * @return
	 */
	private PicTemplate getPicTemplate(JsonObject pic_template_info) {
		if (pic_template_info == null) {
			return null;
		}

		PicTemplate picTemplate = new PicTemplate();
		String backPicId = pic_template_info.get("back_pic_id") == null ? null : pic_template_info.get("back_pic_id")
				.getAsString();
		picTemplate.setBackGroundPicId(backPicId);

		if (pic_template_info.get("text_info") != null && JsonNull.INSTANCE != pic_template_info.get("text_info")) {
			JsonArray textInfos = pic_template_info.get("text_info").getAsJsonArray();
			List<TextInfo> textInfoList = GSON.fromJson(textInfos, new TypeToken<List<TextInfo>>() {
			}.getType());
			picTemplate.setTextInfos(textInfoList);
		}

		if (pic_template_info.get("pic_info") != null && JsonNull.INSTANCE != pic_template_info.get("pic_info")) {
			JsonArray pic_infos = pic_template_info.get("pic_info").getAsJsonArray();
			List<ImageInfo> picInfoList = GSON.fromJson(pic_infos, new TypeToken<List<ImageInfo>>() {
			}.getType());
			picTemplate.setImageInfos(picInfoList);
		}

		if (pic_template_info.get("line_info") != null && JsonNull.INSTANCE != pic_template_info.get("line_info")) {
			JsonObject line_info = pic_template_info.get("line_info").getAsJsonObject();
			if (line_info.get("x") != null && JsonNull.INSTANCE != line_info.get("x") && line_info.get("y") != null
					&& JsonNull.INSTANCE != line_info.get("y") && line_info.get("height") != null
					&& JsonNull.INSTANCE != line_info.get("height") && line_info.get("width") != null
					&& JsonNull.INSTANCE != line_info.get("width")) {
				LineInfo lineInfo = GSON.fromJson(pic_template_info.get("line_info"), LineInfo.class);
				picTemplate.setLineInfo(lineInfo);
			}
		}

		picTemplate.setInputor("shuangtai.li");

		if (pic_template_info.get("cut_info_height") != null
				&& JsonNull.INSTANCE != pic_template_info.get("cut_info_height")
				&& pic_template_info.get("cut_info_width") != null
				&& JsonNull.INSTANCE != pic_template_info.get("cut_info_width")) {
			CutInfo cutInfo = new CutInfo();
			if (!CheckUtils.isEmpty(pic_template_info.get("cut_info_height").getAsString())) {
				int height = pic_template_info.get("cut_info_height").getAsInt();
				cutInfo.setHeight(height);
			}

			if (!CheckUtils.isEmpty(pic_template_info.get("cut_info_width").getAsString())) {
				int width = pic_template_info.get("cut_info_width").getAsInt();
				cutInfo.setWidth(width);
			}

			picTemplate.setCutInfo(cutInfo);
		}
		return picTemplate;

	}

	/**
	 * @param request
	 * @return
	 */
	private MessageProjectType getProjectType(HttpServletRequest request) {
		String messageProjectType = request.getParameter("messageProjectType");
		if (messageProjectType == null || messageProjectType.trim().length() <= 0) {
			return MessageProjectType.DEFAULT;
		}
		return MessageProjectType.valueOf(messageProjectType);
	}

	/**
	 * @param request
	 * @return
	 */
	private MainType getMainTypeFromRequest(HttpServletRequest request) {
		String mainType = request.getParameter("mainType");
		if (mainType == null || mainType.trim().length() <= 0) {
			return null;
		}
		return MainType.valueOf(mainType);
	}

	public ModelAndView newMsgType(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("template/new_msg");
	}

	public IMessageTypeService getMessageTypeService() {
		return messageTypeService;
	}

	public void setMessageTypeService(IMessageTypeService messageTypeService) {
		this.messageTypeService = messageTypeService;
	}

}
