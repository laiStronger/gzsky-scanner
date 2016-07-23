package com.wenwo.message.endpoint.impl;

import java.util.List;

import com.mongodb.util.JSON;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.endpoint.AbstractInsiteEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.template.gen.ContentGenerator;

/**
 * 用于发送站内消息, 方式消息是存在messageMap这个字段下的一个子文档
 * 
 * 这里我们使用的方式是：
 * 1）模板里必须配置的是标准的json格式
 * 2）生成内容后，在这里使用Mongodb的JSON工具类转成bson对象
 * 
 * 
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-20,	StanleyDing, 	Create
 */
public class WebInsiteEndpoint extends AbstractInsiteEndpoint {

	public WebInsiteEndpoint(ContentGenerator contentGenerator,
			String templateId, IMessageDao messageDao,List<IEndpointFilter> pointFilterList) {
		super(contentGenerator, templateId, "webData", messageDao,pointFilterList);
	}

	@Override
	protected Object parseMessage(String messageContent) {
		return JSON.parse(messageContent);
	}
}
