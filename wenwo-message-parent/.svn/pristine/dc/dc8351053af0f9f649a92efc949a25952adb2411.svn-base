package com.wenwo.message.enums;
/**
 * 消息主分类
 * @author StanleyDing
 * @date 2013-9-24
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-24,	StanleyDing, 	Create
 */
public enum MainType{
		ASK("提问", 2),
		ANSWER("回答", 3),
		ASKMORE("追问", 4),
		SYS("系统消息", 1);
		
		private int type;
		
		private String typeName;
		
		private MainType(String typeName, int type){
			this.typeName = typeName;
			this.setType(type);
		}

		public String getTypeName() {
			return typeName;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

	}