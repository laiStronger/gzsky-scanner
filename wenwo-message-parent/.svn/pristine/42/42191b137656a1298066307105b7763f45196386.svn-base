package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.enums.MainType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.base.MessageConfigBase;

public class MessageType extends MessageConfigBase implements Serializable{
	
	private static final long serialVersionUID = -4317328064763974911L;
	
	private String id;
	
	private MainType mainType;//消息主类型
	
	private String typeName;//消息类型名称
	
	private String description;
	
	private TemplateInfo insiteWebTemplateInfo;//站内消息(Web)模版，只有文字模版
	
	private TemplateInfo insiteAppTemplateInfo;//站内消息(App)模版，只有文字模版
	
	private TemplateInfo pushTemplateInfo;//推送消息模版，只有文字模版
	
	private TemplateInfo priMessageTemplateInfo;//私信模板
	
	private TemplateInfo weiboTemplateInfo;//微博消息模版，文字模版和图片模版
	
	private TemplateInfo smsTemplateInfo;  //短信消息模板
	
	private TemplateInfo externalTemplateInfo;  //外部消息模板

	private boolean isSendCollMessage;//是否发送集合消息，为true时，微博消息模版不能生效，发送微博消息则不能发送集合消息
	
	//private boolean isSendPriMessage;//是否发送私信
	

	/**
	 * 1.MessageProjectType.DEFAULT 默认配置
	 * 2.其他，针对各个项目的个性化配置
	 */
	private MessageProjectType messageProjectType;
	
//	private String channelId;//针对该消息类型配置的个性化通道，没有配置时为空
	
	//消息类型相关的通道信息，为空时使用项目或者标准通道
	private MessageTypeChannel messageTypeChannel;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MainType getMainType() {
		return mainType;
	}

	public void setMainType(MainType mainType) {
		this.mainType = mainType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public TemplateInfo getInsiteWebTemplateInfo() {
		return insiteWebTemplateInfo;
	}

	public void setInsiteWebTemplateInfo(TemplateInfo insiteWebTemplateInfo) {
		this.insiteWebTemplateInfo = insiteWebTemplateInfo;
	}

	public TemplateInfo getInsiteAppTemplateInfo() {
		return insiteAppTemplateInfo;
	}

	public void setInsiteAppTemplateInfo(TemplateInfo insiteAppTemplateInfo) {
		this.insiteAppTemplateInfo = insiteAppTemplateInfo;
	}

	public TemplateInfo getPushTemplateInfo() {
		return pushTemplateInfo;
	}

	public void setPushTemplateInfo(TemplateInfo pushTemplateInfo) {
		this.pushTemplateInfo = pushTemplateInfo;
	}

	public TemplateInfo getWeiboTemplateInfo() {
		return weiboTemplateInfo;
	}

	public void setWeiboTemplateInfo(TemplateInfo weiboTemplateInfo) {
		this.weiboTemplateInfo = weiboTemplateInfo;
	}
	
	public TemplateInfo getPriMessageTemplateInfo() {
		return priMessageTemplateInfo;
	}

	public void setPriMessageTemplateInfo(TemplateInfo priMessageTemplateInfo) {
		this.priMessageTemplateInfo = priMessageTemplateInfo;
	}

	public TemplateInfo getSmsTemplateInfo() {
		return smsTemplateInfo;
	}
	public void setSmsTemplateInfo(TemplateInfo smsTemplateInfo) {
		this.smsTemplateInfo = smsTemplateInfo;
	}
	

	public TemplateInfo getExternalTemplateInfo() {
		return externalTemplateInfo;
	}

	public void setExternalTemplateInfo(TemplateInfo externalTemplateInfo) {
		this.externalTemplateInfo = externalTemplateInfo;
	}

	public boolean getIsSendCollMessage() {
		return isSendCollMessage;
	}

	public void setIsSendCollMessage(boolean isSendCollMessage) {
		this.isSendCollMessage = isSendCollMessage;
	}
	
	/*public boolean isSendPriMessage() {
		return isSendPriMessage;
	}

	public void setSendPriMessage(boolean isSendPriMessage) {
		this.isSendPriMessage = isSendPriMessage;
	}*/

	public MessageProjectType getMessageProjectType() {
		return messageProjectType;
	}

	public void setMessageProjectType(MessageProjectType messageProjectType) {
		this.messageProjectType = messageProjectType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public TemplateInfo getTemplateInfo(TemplateInfoType templateInfoType){
		if(TemplateInfoType.INSITE_WEB == templateInfoType){
			return this.insiteWebTemplateInfo;
		}else if(TemplateInfoType.INSITE_APP == templateInfoType){
			return this.insiteAppTemplateInfo;
		}else if(TemplateInfoType.PUSH == templateInfoType){
			return this.pushTemplateInfo;
		}else if(TemplateInfoType.WEIBO == templateInfoType){
			return this.weiboTemplateInfo;
		}else if(TemplateInfoType.PRIMESSAGE == templateInfoType){
			return this.priMessageTemplateInfo;
		}else if(TemplateInfoType.SMS == templateInfoType){
			return this.smsTemplateInfo;
		}else if(TemplateInfoType.EXTERNAL == templateInfoType){
			return this.externalTemplateInfo;
		}else{
			return null;
		}
	}
	
	
	public void setTemplateInfo(TemplateInfo templateInfo, TemplateInfoType templateInfoType){
		if(TemplateInfoType.INSITE_WEB == templateInfoType){
			this.insiteWebTemplateInfo = templateInfo;
		}else if(TemplateInfoType.INSITE_APP == templateInfoType){
			this.insiteAppTemplateInfo = templateInfo;
		}else if(TemplateInfoType.PUSH == templateInfoType){
			this.pushTemplateInfo = templateInfo;
		}else if(TemplateInfoType.WEIBO == templateInfoType){
			this.weiboTemplateInfo = templateInfo;
		}else if(TemplateInfoType.PRIMESSAGE == templateInfoType){
			this.priMessageTemplateInfo = templateInfo;
		}else if(TemplateInfoType.SMS == templateInfoType){
			this.smsTemplateInfo = templateInfo;
		}else if(TemplateInfoType.EXTERNAL == templateInfoType){
			this.externalTemplateInfo=templateInfo;
		}
	}

	public class MessageTypeChannel implements Serializable{
		private static final long serialVersionUID = 2252232109934032276L;
		
		private String sinaAccGroupType;//取值可为：WEIWEN, WENWO, WEIWENQQ, SINAASK
		private String sinaGroupName;
		private String qqAccGroupType;//取值可为：WEIWEN, WENWO, WEIWENQQ, SINAASK
		private String qqGroupName;
		
		public MessageTypeChannel(String sinaAccGroupType, String sinaGroupName, String qqAccGroupType, String qqGroupName){
			super();
			this.sinaAccGroupType = sinaAccGroupType;
			this.sinaGroupName = sinaGroupName;
			this.qqAccGroupType = qqAccGroupType;
			this.qqGroupName = qqGroupName;
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
	public static class TemplateInfo implements Serializable{
		
		private static final long serialVersionUID = 2345227858009698123L;

		private boolean isNeed;//该模版是否生效
		
		//文字模版与图片模版可以单独出现，也可以同时出现
		private String picTemplateId;//图片模版ID
		
		private String textTemplateId;//文字模版ID
		
		public TemplateInfo(){
			super();
		}
		
		public TemplateInfo(boolean isNeed, String picTemplateId, String textTemplateId){
			super();
			this.isNeed = isNeed;
			this.picTemplateId = picTemplateId;
			this.textTemplateId = textTemplateId;
		}
		public boolean getIsNeed() {
			return isNeed;
		}
		public void setIsNeed(boolean isNeed) {
			this.isNeed = isNeed;
		}
		public String getPicTemplateId() {
			return picTemplateId;
		}
		public void setPicTemplateId(String picTemplateId) {
			this.picTemplateId = picTemplateId;
		}
		public String getTextTemplateId() {
			return textTemplateId;
		}
		public void setTextTemplateId(String textTemplateId) {
			this.textTemplateId = textTemplateId;
		}
	}
	
	public enum TemplateInfoType{
		INSITE_WEB,//站内消息，web
		INSITE_APP,//站内消息，app
		PUSH,//push消息
		WEIBO,//微博消息
		PRIMESSAGE, //私信接口
		SMS,//短信接口
		EXTERNAL;//阿里健康调用
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
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
		MessageType other = (MessageType) obj;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}

	public MessageTypeChannel getMessageTypeChannel() {
		return messageTypeChannel;
	}

	public void setMessageTypeChannel(MessageTypeChannel messageTypeChannel) {
		this.messageTypeChannel = messageTypeChannel;
	}
}
