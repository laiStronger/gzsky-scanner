package com.wenwo.message.endpoint;

import com.wenwo.message.request.MessageRequest;

/**
 * 
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-20,	StanleyDing, 	Create
 */
public interface IMessageEndpoint {
	void send(MessageRequest request) throws Exception;
	//void addFilter(IEndpointFilter endpointFilter);
}
