package com.wenwo.message.enums;

/**
 * 
 * @author "StanleyDing"
 * @date 2013-9-27
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-27,	"StanleyDing", 	Create
 */
public enum EndpointType {
	
	INSITE_WEB("WEB站内"),
	INSITE_APP("APP站内"),
	PUSH_APP("手机PUSH"),
	WEIBO_AT("微博@"),
	WEIBO_SHARE("微博分享"),
	WEIBO_BATCH("微博集合消息");
	
	
	private String typeName;
	EndpointType(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}
}
