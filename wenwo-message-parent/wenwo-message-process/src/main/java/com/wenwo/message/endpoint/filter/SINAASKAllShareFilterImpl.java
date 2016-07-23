package com.wenwo.message.endpoint.filter;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.service.IAnswerService;
import com.wenwo.platform.service.IQuestionService;
import com.wenwo.platform.types.project.SubprojectType;
 
/**
 * 此处屏蔽SINAASK
 * @author laisq
 *
 */
public class SINAASKAllShareFilterImpl extends AbstractEndpointFilter {

	
	@Autowired
	private IQuestionService questionService;
	
	@Autowired
	private IAnswerService answerService;
	
	
	public boolean preHandler(MessageRequest request,Map<String,Object> preMap) throws Exception {
		SubprojectType subprojectType = request.getSubprojectType();
		
		if(SubprojectType.SINAASK == subprojectType){
			return false;
		}
		
		return true;
	}
	
	public void postHandler(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		
	}
	
	
	
	
	

	
	
}
