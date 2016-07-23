package com.wenwo.message.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 微博Id和问题的实体
 * @author laisq
 *
 */
public class WeiboAnswerInfo  implements Serializable{

	private static final long serialVersionUID = 8775071879847709613L;

	private String weiboId;
	
	private String answerId;
	
	private String messageType;
	
	private Date createTime;
	
	public WeiboAnswerInfo() {
		
	}
	
	public WeiboAnswerInfo(String weiboId,String answerId,String messageType,Date createTime) {
		this.weiboId = weiboId;
		this.answerId = answerId;
		this.messageType = messageType;
		this.createTime = createTime;
		
	}
	
	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
