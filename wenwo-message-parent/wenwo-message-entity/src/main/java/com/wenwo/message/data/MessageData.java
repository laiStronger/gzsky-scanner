package com.wenwo.message.data;

import java.io.Serializable;
import java.util.List;

import com.wenwo.message.entity.MessageParameter;
import com.wenwo.message.entity.OpenUserInfo;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 用于放到redis队列的消息数据对象
 * @author StanleyDing
 * @date 2013-9-19
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-19,	StanleyDing, 	Create
 */
public class MessageData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 833001539960668147L;
	private String messageId;//預先生成的消息ID
	private String messageType;//消息類型
	private List<String> targetUidList;//目標用戶id
	private List<OpenUserInfo> targetOpenUserList;//也可以直接给还未创建用户的微博用户发消息
	private boolean isOpenUid = false;//true:目標用戶ID是openUid；false：目標用戶id是問我ID
	private SubprojectType subprojectType;//發送的項目類型
	private MessageParameter messageParameter;//json數據
	
	public MessageData(){}

	public MessageData(String messageId, String messageType,
			List<String> targetUidList,
			SubprojectType subprojectType, MessageParameter messageParameter) {
		super();
		this.messageId = messageId;
		this.messageType = messageType;
		this.targetUidList = targetUidList;
		this.isOpenUid = false;
		this.subprojectType = subprojectType;
		this.messageParameter = messageParameter;
	}
	
	public MessageData(String messageId, String messageType,
			List<OpenUserInfo> targetOpenUserList,
			MessageParameter messageParameter, SubprojectType subprojectType) {
		super();
		this.messageId = messageId;
		this.messageType = messageType;
		this.targetOpenUserList = targetOpenUserList;
		this.isOpenUid = true;
		this.subprojectType = subprojectType;
		this.messageParameter = messageParameter;
	}
	
	

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public List<String> getTargetUidList() {
		return targetUidList;
	}

	public void setTargetUidList(List<String> targetUidList) {
		this.targetUidList = targetUidList;
	}

	public List<OpenUserInfo> getTargetOpenUserList() {
		return targetOpenUserList;
	}

	public void setTargetOpenUserList(List<OpenUserInfo> targetOpenUserList) {
		this.targetOpenUserList = targetOpenUserList;
	}

	public boolean isOpenUid() {
		return isOpenUid;
	}

	public void setOpenUid(boolean isOpenUid) {
		this.isOpenUid = isOpenUid;
	}

	public SubprojectType getSubprojectType() {
		return subprojectType;
	}

	public void setSubprojectType(SubprojectType subprojectType) {
		this.subprojectType = subprojectType;
	}


	public MessageParameter getMessageParameter() {
		return messageParameter;
	}

	public void setMessageParameter(MessageParameter messageParameter) {
		this.messageParameter = messageParameter;
	}

	@Override
	public String toString() {
		return "MessageData [messageId=" + messageId + ", messageType="
				+ messageType + ", targetUidList=" + targetUidList
				+ ", targetOpenUserList=" + targetOpenUserList + ", isOpenUid="
				+ isOpenUid + ", subprojectType=" + subprojectType + ", messageParameter="
				+ messageParameter + "]";
	}

}
	