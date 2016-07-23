package com.wenwo.message.endpoint.impl;

import java.util.List;

import com.mongodb.util.JSON;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.endpoint.AbstractInsiteEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.template.gen.ContentGenerator;

/**
 * 手机站内消息，目前是纯文本，处理相对简单一些
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-20,	StanleyDing, 	Create
 */
public class PhoneInsiteEndpoint extends AbstractInsiteEndpoint {

	public PhoneInsiteEndpoint(ContentGenerator contentGenerator,
			String templateId, IMessageDao messageInsiteDao,List<IEndpointFilter> pointFilterList) {
		super(contentGenerator, templateId, "mobileData", messageInsiteDao,pointFilterList);
	}


	@Override
	protected Object parseMessage(String messageContent) {
		return JSON.parse(messageContent);
	}

}
