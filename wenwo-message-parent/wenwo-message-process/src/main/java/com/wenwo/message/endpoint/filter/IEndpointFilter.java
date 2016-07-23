package com.wenwo.message.endpoint.filter;

import java.util.Map;

import com.wenwo.message.request.MessageRequest;
 
/**
 * 这个接口用于处理某些消息类型的某个Endpoint下的一些特殊逻辑。
 * 这些特殊逻辑既不能通用，也不太容易在界面做配置，通过这个扩展点来实现是最好的办法
 * 例如：
 * 1）每个问题只发一次NEW_ANSWER消息，每次发送前检测一个标志位，发送后更新标志位
 * @author StanleyDing
 * @date 2013-9-24
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-24,	StanleyDing, 	Create
 */
public interface IEndpointFilter {

	/**
	 * 发送消息前filter操作
	 * @param request
	 * @param preMap
	 * @return
	 * @throws Exception
	 */
	boolean preHandler(MessageRequest request,Map<String,Object> preMap) throws Exception;
	
	/**
	 * 发送消息后filter操作
	 * @param request
	 * @param reponseMap
	 * @throws Exception
	 */
	void postHandler(MessageRequest request,Map<String,Object> reponseMap) throws Exception;

	
	
	
}
