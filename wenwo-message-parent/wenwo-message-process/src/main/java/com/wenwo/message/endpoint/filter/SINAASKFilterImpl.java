package com.wenwo.message.endpoint.filter;

import java.util.Map;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.types.project.SubprojectType;
 
/**
 * 此处屏蔽SINAASK除了 newMatchedUser之外的消息类型，以后实现在filter中
 * @author laisq
 *
 */
public class SINAASKFilterImpl extends AbstractEndpointFilter {

	
	
	public boolean preHandler(MessageRequest request,Map<String,Object> preMap) throws Exception {
		SubprojectType subprojectType = request.getSubprojectType();
		String messageType = request.getMessageType();
		
		if(SubprojectType.SINAASK == subprojectType && !"newMatchedUser".equals(messageType)){
			return false;
		}
		
		return true;
	}
	
	public void postHandler(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		
	}
	
	
	
	
	

	
	
}
