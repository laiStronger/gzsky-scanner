/**
 * 
 */
package com.wenwo.message.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.api.IMessageTypeService;
import com.wenwo.message.dao.IMessageTypeDao;
import com.wenwo.message.enums.MainType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.MessageType.MessageTypeChannel;
import com.wenwo.message.model.MessageType.TemplateInfo;
import com.wenwo.message.model.MessageType.TemplateInfoType;
import com.wenwo.message.model.WeiboShareType;

/**
 * @author shuangtai
 *
 */
public class MessageTypeServiceImpl implements IMessageTypeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageTypeServiceImpl.class);
	private IMessageTypeDao messageTypeDao;
	
	@Override
	public List<MessageType> getMessageTypeList(MainType mainType, MessageProjectType messageProjectType) {
		return messageTypeDao.getMessageTypeList(mainType, messageProjectType);
	}

	public IMessageTypeDao getMessageTypeDao() {
		return messageTypeDao;
	}

	public void setMessageTypeDao(IMessageTypeDao messageTypeDao) {
		this.messageTypeDao = messageTypeDao;
	}

	@Override
	public void updateTextTemplateInfo(String messageTypeId, String textTemplateId, TemplateInfoType infoType) {
		MessageType messageType = messageTypeDao.getMessageTypeById(messageTypeId);
		if(messageType == null){
			LOGGER.info("不存在id为{}的messageType", messageTypeId);
			return;
		}
		
		TemplateInfo templateInfo = messageType.getTemplateInfo(infoType);
		if(templateInfo != null){
			templateInfo.setIsNeed(true);
			templateInfo.setTextTemplateId(textTemplateId);
		}else{
			templateInfo = new TemplateInfo(true, null, textTemplateId);
			messageType.setTemplateInfo(templateInfo, infoType);
		}
		
		messageTypeDao.updateMessageType(messageType);
	}

	@Override
	public void updatePicTemplateInfo(String messageTypeId, String picTemplateId, TemplateInfoType infoType) {
		MessageType messageType = messageTypeDao.getMessageTypeById(messageTypeId);
		if(messageType == null){
			LOGGER.info("不存在id为{}的messageType", messageTypeId);
			return;
		}
		
		TemplateInfo templateInfo = messageType.getTemplateInfo(infoType);
		if(templateInfo != null){
			templateInfo.setIsNeed(true);
			templateInfo.setPicTemplateId(picTemplateId);
		}else{
			templateInfo = new TemplateInfo(true, picTemplateId, null);
			messageType.setTemplateInfo(templateInfo, infoType);
		}
	
		messageTypeDao.updateMessageType(messageType);
	}
	
	@Override
	public MessageType getMessageTypeById(String messageTypeId) {
		return messageTypeDao.getMessageTypeById(messageTypeId);
	}

	@Override
	public int updateMessageTypeChannel(String messageTypeId, String sinaAccGroupType, String sinaGroupName, String qqAccGroupType, String qqGroupName) {
		MessageType messageType = messageTypeDao.getMessageTypeById(messageTypeId);
		if(messageType == null){
			return 0;
		}
		
		MessageTypeChannel messageTypeChannel  = messageType.new MessageTypeChannel(sinaAccGroupType, sinaGroupName, qqAccGroupType, qqGroupName);
				
		messageType.setMessageTypeChannel(messageTypeChannel);
		
		try{
			messageTypeDao.updateMessageType(messageType);
		}catch (Exception e) {
			LOGGER.info("更新消息类型通道报错{}",e);
			return 0;
		}
		return 1;
	}

	@Override
	public void deleteMessageTypeById(String messageTypeId) {
		messageTypeDao.deleteMessageTypeById(messageTypeId);
	}

	@Override
	public void updateMessageType(MessageType messageType) {
		messageTypeDao.updateMessageType(messageType);
	}

	@Override
	public void addNewMessageType(MessageType newMessageType) {
		messageTypeDao.addNewMessageType(newMessageType);
	}

	@Override
	public List<MessageType> loadAllMessageTypes() {
		return messageTypeDao.loadAllMessageTypes();
	}

	@Override
	public List<WeiboShareType> loadAllShareTypes() {
		return messageTypeDao.loadAllShareTypes();
	}

}
