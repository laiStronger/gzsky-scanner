package com.wenwo.message.endpoint.filter;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.utils.ContextUtils;
import com.wenwo.platform.service.IAnswerService;
 
/**
 * 对于新浪和qq，同步问题同步答案的filter
 * @author laisq
 *
 */
public class SINAQQsyncAnswerShareFilterImpl extends AbstractEndpointFilter {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IAnswerService answerService;
	
	
	public boolean preHandler(MessageRequest request,Map<String,Object> preMap) throws Exception {
		return true;
	}
	
	public void postHandler(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		//判断返回的map是否为空
		if(reponseMap.size() <= 0) {
			return;
		}
		
		VelocityContext context = request.getContext();
		String weiboId = reponseMap.get("weiboId").toString();
		
		//同步微博答案
		//if(request.getMessageType().equals("syncAnswer")) {
			answerService.updateSyncWeiboInfo(ContextUtils.getAnswerId(context), weiboId,
					request.getTargetUser().getId(), request.getAccountType().getWeiboType(), new Date());
			
		//}
			
		logger.info("同步微博答案-{}",weiboId);
	}
	
	
	
	
	

	
	
}
