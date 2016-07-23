package com.wenwo.message.endpoint;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.endpoint.impl.PhoneInsiteEndpoint;
import com.wenwo.message.endpoint.impl.WebInsiteEndpoint;
import com.wenwo.message.model.MessageInsite;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.template.gen.ContentGenerator;

/**
 * 站内（WEB、Phone、Push）消息发送的抽象父类
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-20,	StanleyDing, 	Create
 */
public abstract class AbstractInsiteEndpoint extends AbstractMessageEndpoint{
	
	protected ContentGenerator contentGenerator;
	protected String templateId;
	
	protected IMessageDao messageDao;
	
	protected String messageField;
	
	

	public AbstractInsiteEndpoint(ContentGenerator contentGenerator,
			String templateId, String messageField, IMessageDao messageDao,List<IEndpointFilter> pointFilterList) {
		super(pointFilterList);
		this.contentGenerator = contentGenerator;
		this.templateId = templateId;
		this.messageField = messageField;
		this.messageDao = messageDao;
	}

	@Override
	protected void doSending(MessageRequest request,Map<String,Object> reponseMap) throws Exception{
		if(request==null || request.getTargetUser()==null) {
			return;
		}
		
		this.createInsiteMessage(request);
		
		String msgContent = contentGenerator.generateText(templateId, request.getContext());
		Object messageFieldValue = parseMessage(msgContent);
		
		if(sendEx(request, msgContent)) {
			messageDao.saveMessage(request.getUniqueId(), messageField, messageFieldValue);

		}
	}
	
	/**
	 * 发送站内消息时，首先创建保存站内消息基本信息
	 * @see WebInsiteEndpoint
	 * @see PhoneInsiteEndpoint
	 * 1> 如果 手机站内 与 web站内，都不发送，则不会进行到这一步
	 * 2> 保证不会重复创建
	 * 3> 单独发送手机或者web站内时，都会创建
	 * @param request
	 */
	protected void createInsiteMessage(MessageRequest request) {
		
		if(messageDao.isMessageExist(request.getUniqueId())){
			return;
		}
		
		MessageInsite messageInsite = new MessageInsite();
		messageInsite.setId(request.getUniqueId());
		messageInsite.setMessageId(request.getMessageId());
		messageInsite.setUserIndex(request.getUserIndex());
		messageInsite.setUserCount(request.getUserCount());
		messageInsite.setMessageType(request.getMessageType());
		messageInsite.setSubprojectType(request.getSubprojectType());
		messageInsite.setProjectType(request.getSubprojectType().getRootProjectType());
		messageInsite.setSendTime(new Date());
		
		messageInsite.setTargetUid(request.getTargetUser().getId());
		
		VelocityContext context = request.getContext();
		
		if(context.get(TemplateConstants.CONTEXT_MESSAGE_TYPE) != null){
			MessageType messageType = (MessageType)context.get(TemplateConstants.CONTEXT_MESSAGE_TYPE);
			messageInsite.setMainType(messageType.getMainType());
		}
		
		messageDao.saveNewInsiteMessage(messageInsite);
	}

	/**
	 * 这个方法主要用于站内（WEB）和其它类型的不同
	 * 站内消息的内容是JSON，进行进行转换
	 * 而其它（手机站内、手机PUSH）则是纯文本，不需要转换，那么这个方法会原样返回原字符串
	 * @param messageContent
	 * @return
	 */
	protected abstract Object parseMessage(String messageContent);

	/**
	 * @param request
	 * @param messageContent
	 * @return true-记录数据库
	 */
	protected boolean sendEx(MessageRequest request, String messageContent) {
		return true;//do nothing by default. Children classes can override this method to do something.
	}
}
