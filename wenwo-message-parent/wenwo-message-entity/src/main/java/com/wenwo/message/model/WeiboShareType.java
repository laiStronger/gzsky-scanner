/**
 * 
 */
package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.base.MessageConfigBase;

/**
 * 微博同步使用的微博消息类型， 由用户触发
 * 
 * @author shuangtai
 * 
 */
public class WeiboShareType extends MessageConfigBase implements Serializable {

	private static final long serialVersionUID = 5602204733798582535L;

	private String id;

	private String typeName;

	private String description;
	
	private String textTemplateId;

	private String reTextTemlateId;// 转发微博的模板

	public String getReTextTemlateId() {
		return reTextTemlateId;
	}

	public void setReTextTemlateId(String reTextTemlateId) {
		this.reTextTemlateId = reTextTemlateId;
	}

	private String picTemplateId;

	private MessageProjectType messageProjectType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTextTemplateId() {
		return textTemplateId;
	}

	public void setTextTemplateId(String textTemplateId) {
		this.textTemplateId = textTemplateId;
	}

	public String getPicTemplateId() {
		return picTemplateId;
	}

	public void setPicTemplateId(String picTemplateId) {
		this.picTemplateId = picTemplateId;
	}

	public MessageProjectType getMessageProjectType() {
		return messageProjectType;
	}

	public void setMessageProjectType(MessageProjectType messageProjectType) {
		this.messageProjectType = messageProjectType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeiboShareType other = (WeiboShareType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WeiboShareType [id=" + id + ", typeName=" + typeName + ", textTemplateId=" + textTemplateId
				+ ", picTemplateId=" + picTemplateId + ", messageProjectType=" + messageProjectType + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
