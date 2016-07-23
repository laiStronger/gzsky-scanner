/**
 * 
 */
package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.base.MessageConfigBase;

/**
 * 
 * 消息类型通道选择逻辑： 1.针对消息类型的通道：直接根据{@link MessageType#getChannelId()}在表Channel查询相应通道
 * 
 * 2.如果{@link MessageType}中{@code channelId}为空，则根据传入的MessageProjetType在表ProjectChannel
 * 中查询到相应的channelId，根据channelId在Channel中查找相应的通道
 * 
 * @author shuangtai
 * 
 */
public class ProjectChannel extends MessageConfigBase implements Serializable {

	private static final long serialVersionUID = 2061818201602894729L;

	private String id;

	/**
	 * @see MessageProjectType
	 */
	private MessageProjectType messageProjectType;

	private String sinaAccGroupType;

	private String sinaGroupType;

	private String qqAccGroupType;

	private String qqGroupType;

	public String getSinaAccGroupType() {
		return sinaAccGroupType;
	}

	public void setSinaAccGroupType(String sinaAccGroupType) {
		this.sinaAccGroupType = sinaAccGroupType;
	}

	public String getSinaGroupType() {
		return sinaGroupType;
	}

	public void setSinaGroupType(String sinaGroupType) {
		this.sinaGroupType = sinaGroupType;
	}

	public String getQqAccGroupType() {
		return qqAccGroupType;
	}

	public void setQqAccGroupType(String qqAccGroupType) {
		this.qqAccGroupType = qqAccGroupType;
	}

	public String getQqGroupType() {
		return qqGroupType;
	}

	public void setQqGroupType(String qqGroupType) {
		this.qqGroupType = qqGroupType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MessageProjectType getMessageProjectType() {
		return messageProjectType;
	}

	public void setMessageProjectType(MessageProjectType messageProjectType) {
		this.messageProjectType = messageProjectType;
	}

}
