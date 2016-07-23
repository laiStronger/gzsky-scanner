package com.wenwo.message.chain;

import com.wenwo.message.request.MessageRequest;

/**
 * 消息处理频道责任链
 * @author StanleyDing
 * @date 2013-9-18
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-18,	StanleyDing, 	Create
 */
public interface IMessageEndpointChain {
	/**
	 * 触发消息处理责任链
	 * @param messageId 当前这条消息的ID
	 * @param accountType 账号类型，主要是后续微博消息中会用到，由于targetUser对象可能为空，所以这个参数是必要的
	 * @param targetUser 发送目标用户对象，如果调用者传入的weiboUid，那么这个对象有可能有空（在我们系统中还没有创建用户）
	 * @param context 已经预先构建好的上下文
	 */
	void fire(MessageRequest request);
	
	int size();
}
