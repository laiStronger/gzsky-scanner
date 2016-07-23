/**
 * 
 */
package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.model.base.MessageConfigBase;

/**
 * 通道信息，每个通道都包括：新浪的通道信息和腾讯的通道信息
 * @author shuangtai
 *
 */
public class Channel extends MessageConfigBase implements Serializable {

	private static final long serialVersionUID = 1515263205622456274L;
	
	private String id;
	
	private AccGroupInfo sinaGroupInfo;
	
	private AccGroupInfo qqGroupInfo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccGroupInfo getSinaGroupInfo() {
		return sinaGroupInfo;
	}

	public void setSinaGroupInfo(AccGroupInfo sinaGroupInfo) {
		this.sinaGroupInfo = sinaGroupInfo;
	}

	public AccGroupInfo getQqGroupInfo() {
		return qqGroupInfo;
	}

	public void setQqGroupInfo(AccGroupInfo qqGroupInfo) {
		this.qqGroupInfo = qqGroupInfo;
	}

	public static class AccGroupInfo implements Serializable{
			/**
		 * 
		 */
		private static final long serialVersionUID = 4736515957315303608L;
			private String accGroupType;//取值可为：WEIWEN, WENWO, WEIWENQQ, SINAASK
			private String groupName;
			public String getAccGroupType() {
				return accGroupType;
			}
			public void setAccGroupType(String accGroupType) {
				this.accGroupType = accGroupType;
			}
			public String getGroupName() {
				return groupName;
			}
			public void setGroupName(String groupName) {
				this.groupName = groupName;
			}
	}
   
}
