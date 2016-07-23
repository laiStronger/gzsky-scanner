/**
 * 
 */
package com.wenwo.message.dao;

import java.util.List;

import com.wenwo.message.enums.MainType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.MessageTempConfig;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.WeiboShareType;

/**
 * @author shuangtai
 *
 */
public interface IMessageTypeDao {
	
	/**
	 * 根据mainType与messageProjectType获取已有消息类型
	 * 不考虑分页
	 * @param mainType
	 * @param messageProjectType
	 * @return
	 */
	public List<MessageType> getMessageTypeList(MainType mainType, MessageProjectType messageProjectType);

	/**
	 * @param messageTypeId
	 * @return
	 */
	public MessageType getMessageTypeById(String messageTypeId);

	/**
	 * @param messageType
	 * @return
	 */
	public void updateMessageType(MessageType messageType);

	/**
	 * @param messageTypeId
	 */
	public void deleteMessageTypeById(String messageTypeId);

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

	/**
	 * 
	 */
	public void dropMessageTempConfig();

	/**
	 * @param messageTempConfigList
	 */
	public void insertMessageTempConfig(
			List<MessageTempConfig> messageTempConfigList);

	/**
	 * @return
	 */
	public List<MessageTempConfig> loadAllTempConfig();

	public void changeNeedLoadStatus();
}
