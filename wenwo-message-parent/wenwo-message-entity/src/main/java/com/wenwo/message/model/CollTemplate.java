/**
 * 
 */
package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.model.base.MessageConfigBase;

/**
 * 集合消息使用模版
 * @author shuangtai
 *
 */
public class CollTemplate extends MessageConfigBase implements Serializable {

	private static final long serialVersionUID = -1060761071149219171L;
	
	private String id;
	
	private String templateContent;
	
	/**
	 * 通道信息
	 */
	private String sinaAccGroupType;//取值可为：WEIWEN, WENWO, WEIWENQQ, SINAASK
	private String sinaGroupName;
	private String qqAccGroupType;//取值可为：WEIWEN, WENWO, WEIWENQQ, SINAASK
	private String qqGroupName;
	
	private int period;//发送周期 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getSinaAccGroupType() {
		return sinaAccGroupType;
	}

	public void setSinaAccGroupType(String sinaAccGroupType) {
		this.sinaAccGroupType = sinaAccGroupType;
	}

	public String getSinaGroupName() {
		return sinaGroupName;
	}

	public void setSinaGroupName(String sinaGroupName) {
		this.sinaGroupName = sinaGroupName;
	}

	public String getQqAccGroupType() {
		return qqAccGroupType;
	}

	public void setQqAccGroupType(String qqAccGroupType) {
		this.qqAccGroupType = qqAccGroupType;
	}

	public String getQqGroupName() {
		return qqGroupName;
	}

	public void setQqGroupName(String qqGroupName) {
		this.qqGroupName = qqGroupName;
	}

}
