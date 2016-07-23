package com.wenwo.message.chain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.endpoint.IMessageEndpoint;
import com.wenwo.message.endpoint.impl.PriMessageEndpoint;
import com.wenwo.message.endpoint.impl.SMSMessageEndpoint;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.printer.ExceptionPrinter;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.entity.WenwoUser;

public class DefaultMessageEndpointChain implements IMessageEndpointChain{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ExceptionPrinter exceptionPrinter;
	private List<IMessageEndpoint> messageEndpointList;
	
	/**
	 * 对应的消息类型
	 */
	private MessageType messageType;
	
	public DefaultMessageEndpointChain() {
		
	}
	
	public DefaultMessageEndpointChain(
			List<IMessageEndpoint> messageEndpointList) {
		super();
		this.messageEndpointList = messageEndpointList;
	}

	public void addEndpoint(IMessageEndpoint endpoint) {
		if(messageEndpointList==null) {
			messageEndpointList = new ArrayList<IMessageEndpoint>();
		}
		messageEndpointList.add(endpoint);
	}
    
	public void fire(MessageRequest request) {
		request.getContext().put(TemplateConstants.CONTEXT_MESSAGE_TYPE, this.messageType);
		//为某个消息类型循环遍历它的endpoint
		for(IMessageEndpoint messageEndpoint : messageEndpointList) {
			try {

					messageEndpoint.send(request);

			} catch (Throwable e) {
				logger.error("发送消息失败{}", e);
				WenwoUser targetUser = request.getTargetUser();
				exceptionPrinter.printException(targetUser==null? null: targetUser.getId(), request.getUniqueId(), "发送消息失败："+messageEndpoint.getClass().getSimpleName(), e);
			}
		}
	}
    
	public void setExceptionPrinter(ExceptionPrinter exceptionPrinter) {
		this.exceptionPrinter = exceptionPrinter;
	}

	@Override
	public int size() {
		return messageEndpointList==null ? 0 :messageEndpointList.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("EndpointChain [");
		if(!CollectionUtils.isEmpty(messageEndpointList)){
			for(IMessageEndpoint endpoint:messageEndpointList) {
				sb.append(endpoint.getClass().getSimpleName()).append("--->");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
}
