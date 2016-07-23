package com.wenwo.message.model;

public class SMSMessageInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1379160314821391197L;
	
	private String id;
	private String type;
	private String receiver_id;
	private String phoneNum;
	private String createTime;
	private String text;
	private boolean isSendSuccess;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isSendSuccess() {
		return isSendSuccess;
	}
	public void setSendSuccess(boolean isSendSuccess) {
		this.isSendSuccess = isSendSuccess;
	}


}
