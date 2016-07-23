package com.wenwo.message.data;

import java.io.Serializable;

import com.wenwo.message.entity.MessageParameter;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 用户向微博分享(同步)信息时push到redis队列的数据
 * @author StanleyDing
 *
 */
public class ShareData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3119547793951539118L;
	private String shareId;
	private String shareType;
	private String actionUid;
	private SubprojectType subprojectType;
	private MessageParameter messageParameter;
	
	public ShareData(){}
	
	public ShareData(String shareId, String shareType, String actionUid,
			SubprojectType subprojectType, MessageParameter messageParameter) {
		super();
		this.shareId = shareId;
		this.shareType = shareType;
		this.actionUid = actionUid;
		this.subprojectType = subprojectType;
		this.messageParameter = messageParameter;
	}

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getActionUid() {
		return actionUid;
	}

	public void setActionUid(String actionUid) {
		this.actionUid = actionUid;
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
		return "ShareData [shareId=" + shareId + ", shareType=" + shareType
				+ ", actionUid=" + actionUid + ", subprojectType="
				+ subprojectType + ", messageParameter=" + messageParameter + "]";
	}
	
}
