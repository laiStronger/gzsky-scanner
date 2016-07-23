package com.wenwo.message.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息请求参数：
 * 1）id是比较特殊的，它可以是问题id、答案id和用户id；它究竟是什么，是IdType决定
 * 		a）如果是用户id，处理相对简单，系统会取出这个用户对象，存在context的“sourceUser”这个key下
 * 		b）如果是问题id，那么系统会取出2个对象：
 * 			①：问题对象存在“question”这个key下
 * 			②：提问者存在“questionUser”这个key下
 * 		c）如果是答案id，那么系统会取出4个对象以便处理
 * 			①：问题对象存在“question”这个key下
 * 			②：提问者存在“questionUser”这个key下
			①：答案对象存在“answer”这个key下
 * 			②：回答者存在“answerUser”这个key下
 * 2）properties算是额外的补充信息，还没有想到特别有效的方法来约束这个参数
 * @author StanleyDing
 * @date 2013-9-23
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-23,	StanleyDing, 	Create
 */
public class MessageParameter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8457976086257302431L;
	public enum IdType {
		QUESTION,
		ANSWER,
		USER
	}
	
	private String id;//针对问题的消息，传问题id；关于答案的消息，传答案id；关于用户的消息（如用户A关注了目标用户），则传用户id（用户A的id）；其它情况，这个id应该为null
	private IdType idType;//定义"id"的类型
	private Map<String, Object> properties;//关于一些额外的信息，通过这个传递
	
	
	/**
	 * 通常来说，跟问题/答案/用户相关的消息，使用这个构造函数就够了
	 * @param id
	 */
	public MessageParameter(String id, IdType idType) {
		super();
		this.id = id;
		this.idType = idType;
	}
	public MessageParameter(String id, IdType idType, Map<String, Object> properties) {
		super();
		this.id = id;
		this.idType = idType;
		this.properties = properties;
	}
	public MessageParameter(Map<String, Object> properties) {
		super();
		this.properties = properties;
	}
	
	public void addProperty(String key, Object value) {
		if(properties==null) {
			synchronized (this) {
				if(properties==null) {
					properties = new HashMap<String, Object>();
				}
			}
		}
		properties.put(key, value);
	}
	public String getId() {
		return id;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	
	public IdType getIdType() {
		return idType;
	}
	@Override
	public String toString() {
		return "MessageParameter [id=" + id + ", properties=" + properties
				+ "]";
	}
}
