/**
 * 
 */
package com.wenwo.message.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wenwo.message.model.base.MessageBase;

/**
 * 仅仅作为展示，前端不会读取
 * @author shuangtai
 *
 */
@Document(collection="messages.weiboAt")
public class WeiboAtMessage extends MessageBase{

	private static final long serialVersionUID = -6182409169158946696L;
	
	private Object weiboData;
	
	private Date createTime;

	public Object getWeiboData() {
		return weiboData;
	}

	public void setWeiboData(Object weiboData) {
		this.weiboData = weiboData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
