package com.wenwo.message.api;

import java.util.List;

import com.wenwo.message.enums.MainType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.MessageType.TemplateInfoType;
import com.wenwo.message.model.WeiboShareType;

/**
 * 供消息系统本身使用
 * @author shuangtai
 *
 */
public interface IMessageTypeService {
	
	public List<MessageType> getMessageTypeList(MainType mainType, MessageProjectType messageProjectType);

	/**
	 * @param messageTypeId
	 * @param templateId 文本类型模版id
	 * @param infoType 更新消息类型中哪种模版
	 */
	public void updateTextTemplateInfo(String messageTypeId, String textTemplateId, TemplateInfoType infoType);
	
	/**
	 * @param messageTypeId
	 * @param templateId 图片模版id
	 * @param infoType 更新消息类型中哪种模版
	 */
	public void updatePicTemplateInfo(String messageTypeId, String picTemplateId, TemplateInfoType infoType);

	/**
	 * @param messageTypeId
	 * @return
	 */
	public MessageType getMessageTypeById(String messageTypeId);
	
	/**
	 * 
	 * @param messageTypeId
	 * @param sinaAccGroupType
	 * @param sinaGroupName
	 * @param qqAccGroupType
	 * @param qqGroupName
	 * @return 0 没有更新 1 更新成功
	 */
	public int updateMessageTypeChannel(String messageTypeId, String sinaAccGroupType, String sinaGroupName, String qqAccGroupType, String qqGroupName);

	/**
	 * @param messageTypeId
	 */
	public void deleteMessageTypeById(String messageTypeId);

	/**
	 * @param messageType
	 */
	public void updateMessageType(MessageType messageType);

	/**
	 * @param newMessageType
	 */
	public void addNewMessageType(MessageType newMessageType);

	/**
	 * @return
	 */
	public List<MessageType> loadAllMessageTypes();

	/**
	 * @return
	 */
	public List<WeiboShareType> loadAllShareTypes();
	
}
