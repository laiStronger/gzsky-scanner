package com.wenwo.message.model;

import java.io.Serializable;
import java.util.Date;

//package com.wenwo.message.entity;
//
///**
// * @author StanleyDing
// * @date 2013-9-22
// * @since 2.0
// * 
// * History：
// * Date,	By,		What
// * 2013-9-22,	StanleyDing, 	Create
// */
public class MessageError implements Serializable{

	private static final long serialVersionUID = -8452067889122048593L;
	
	private String parentMsgId;
	private String msgId;
	private String targetUid;
	private String description;
	private String errorMessage;
	private String errorStack;
	private Date createTime = new Date();
	
	public MessageError(){
		
	}
	
	public MessageError(String parentMsgId, String msgId, String targetUid,
			String description, String errorMessage, String errorStack){
		this.parentMsgId = parentMsgId;
		this.msgId = msgId;
		this.targetUid = targetUid;
		this.description = description;
		this.errorMessage = errorMessage;
		this.errorStack = errorStack;

	}
	
	public String getParentMsgId() {
		return parentMsgId;
	}
	public void setParentMsgId(String parentMsgId) {
		this.parentMsgId = parentMsgId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getTargetUid() {
		return targetUid;
	}
	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorStack() {
		return errorStack;
	}
	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
