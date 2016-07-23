/**
 * 
 */
package com.wenwo.message.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wenwo.message.model.base.MessageBase;

/**
 * @author shuangtai
 *
 */
@Document(collection="messages.insite")
public class MessageInsite extends MessageBase{

	private static final long serialVersionUID = 3098359908449542328L;
	
	private Date readTime;
	
	private boolean read;
	
	private Object mobileData;
	
	private Object pushData;
	
	private Object webData;
	
	public Object getPushData() {
		return pushData;
	}

	public void setPushData(Object pushData) {
		this.pushData = pushData;
	}

	public Object getWebData() {
		return webData;
	}

	public void setWebData(Object webData) {
		this.webData = webData;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Object getMobileData() {
		return mobileData;
	}

	public void setMobileData(Object mobileData) {
		this.mobileData = mobileData;
	}

}
