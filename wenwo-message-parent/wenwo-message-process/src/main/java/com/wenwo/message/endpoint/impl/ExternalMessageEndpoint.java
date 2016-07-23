/**
 * @author acer
 * @date 2015-2-6
 */
package com.wenwo.message.endpoint.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.endpoint.AbstractMessageEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.template.gen.ContentGenerator;
import com.wenwo.platform.doctor.open.service.IAliMessageService;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * @author acer
 *
 */
public class ExternalMessageEndpoint extends AbstractMessageEndpoint{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String textTemplateId;
	private final ContentGenerator contentGenerator;
	private final IAliMessageService aliMessageService;
	
	public ExternalMessageEndpoint(String textTemplateId,ContentGenerator contentGenerator,
			IAliMessageService aliMessageService,List<IEndpointFilter> pointFilterList){
		super(pointFilterList);
		this.textTemplateId = textTemplateId;
		this.contentGenerator = contentGenerator;
		this.aliMessageService=aliMessageService;
	}
	@Override
	protected void doSending(MessageRequest request,
			Map<String, Object> reponseMap) throws Exception {
		VelocityContext context = request.getContext();
		SubprojectType projectType = request.getSubprojectType();  //项目类型
		String messageType = request.getMessageType(); //消息类型
		
		logger.info("进入外部调用阿里信息接口,项目类型{},消息类型{}",projectType,messageType);
		
		String msgContent = contentGenerator.generateText(textTemplateId, context);  //调用模板生成短信内容
		logger.info("userId is:"+context.get("userId"));
		Map<String, Object> properties=new HashMap<String,Object>();
		properties=(Map<String, Object>) context.get(TemplateConstants.CONTEXT_DOCTOR_PARAMETER);
		logger.info("properties is:"+properties);
		logger.info("properties is:"+properties.get("userId"));
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("targetType",properties.get("targetType"));
		param.put("targetId",properties.get("targetId"));
		param.put("userId",properties.get("userId"));
		param.put("messageContent",msgContent);
		logger.info("targetType is:"+param.get("targetType")+
				" targetId is:"+param.get("targetId")+" userId is:"+
				param.get("userId")+" messageContent is:"+param.get("messageContent"));
		String time=aliMessageService.callback(param);
		logger.info("时间戳为{}",time);
		logger.info("进入外部调用阿里信息接口成功,发送内容{}",msgContent);
	}

}
