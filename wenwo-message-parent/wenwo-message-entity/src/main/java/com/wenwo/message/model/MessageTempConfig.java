package com.wenwo.message.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.ProjectChannel;
import com.wenwo.message.model.TextTemplate;
import com.wenwo.message.model.WeiboShareType;

/**
 * @author StanleyDing
 * @date 2013-9-22
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-22,	StanleyDing, 	Create
 */
public class MessageTempConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2373317799577270316L;
	private MessageProjectType messageProjectType;
	private Map<String, TextTemplate> textTemplateMap;
	private Map<String, PicTemplate> picTemplateMap;
	private List<MessageType> messageTypes;
	private List<WeiboShareType> shareTypes;
	private ProjectChannel defaultChannel;
	
	public MessageTempConfig(){}
	
	public MessageTempConfig(MessageProjectType messageProjectType, List<MessageType> messageTypes,
			List<WeiboShareType> shareTypes, ProjectChannel defaultChannel) {
		super();
		this.messageProjectType = messageProjectType;
		this.messageTypes = messageTypes;
		this.shareTypes = shareTypes;
		this.defaultChannel = defaultChannel;
	}
	
	public void addTextTemplate(TextTemplate template) {
		if(template == null){
			return;
		}
		
		if(textTemplateMap==null) {
			textTemplateMap = new HashMap<String, TextTemplate>();
		}
		textTemplateMap.put(template.getId(), template);
	}
	
	public void addPicTemplate(PicTemplate template) {
		if(template == null){
			return;
		}
		
		if(picTemplateMap==null) {
			picTemplateMap = new HashMap<String, PicTemplate>();
		}
		picTemplateMap.put(template.getId(), template);
	}
	

	public MessageProjectType getMessageProjectType() {
		return messageProjectType;
	}

	public void setMessageProjectType(MessageProjectType messageProjectType) {
		this.messageProjectType = messageProjectType;
	}

	public Map<String, TextTemplate> getTextTemplateMap() {
		return textTemplateMap;
	}

	public void setTextTemplateMap(Map<String, TextTemplate> textTemplateMap) {
		this.textTemplateMap = textTemplateMap;
	}

	public Map<String, PicTemplate> getPicTemplateMap() {
		return picTemplateMap;
	}

	public void setPicTemplateMap(Map<String, PicTemplate> picTemplateMap) {
		this.picTemplateMap = picTemplateMap;
	}

	public List<MessageType> getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(List<MessageType> messageTypes) {
		this.messageTypes = messageTypes;
	}

	public List<WeiboShareType> getShareTypes() {
		return shareTypes;
	}

	public void setShareTypes(List<WeiboShareType> shareTypes) {
		this.shareTypes = shareTypes;
	}

	public ProjectChannel getDefaultChannel() {
		return defaultChannel;
	}

	public void setDefaultChannel(ProjectChannel defaultChannel) {
		this.defaultChannel = defaultChannel;
	}

	@Override
	public String toString() {
		return "MessageTempConfig [messageProjectType=" + messageProjectType + ", textTemplateMap=" + textTemplateMap + ", picTemplateMap=" + picTemplateMap + ", messageTypes=" + messageTypes
				+ ", shareTypes=" + shareTypes + "]";
	}
}
