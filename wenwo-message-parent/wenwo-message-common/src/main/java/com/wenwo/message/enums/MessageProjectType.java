package com.wenwo.message.enums;

/**
 * 消息系统对问我ProjectType的一层包装，
 * 主要是多了类型DEFAULT表示是否使用默认标准的配置
 * @author shuangtai
 *
 */
public enum MessageProjectType {
	WENWO("问我"),
	WEIWEN("微问"),
	WEIWENQQ("腾讯微问"),
	SINAASK("新浪帮助"),
	DOCTOR("微问医生"),
	EXTERNAL("外部"),
	DEFAULT("默认标准");
	
	private String projectName;
	
	private MessageProjectType(String projectName){
		this.projectName = projectName;
	}

	public MessageProjectType getMessageProjectType(String projectName){
		if(projectName == null || projectName.trim().length() <= 0){
			return MessageProjectType.DEFAULT;
		}
		for(MessageProjectType type : MessageProjectType.values()){
			if(projectName.equals(type.getProjectName())){
				return type;
			}
		}
		return null;
	}
	public String getProjectName() {
		return projectName;
	}
	
}
