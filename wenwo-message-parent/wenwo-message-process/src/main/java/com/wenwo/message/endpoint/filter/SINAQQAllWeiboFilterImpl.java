package com.wenwo.message.endpoint.filter;

import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.utils.ContextUtils;
import com.wenwo.message.utils.PlatformMongoUtil;
import com.wenwo.message.utils.RedisUtil;
import com.wenwo.platform.entity.Question;
 
/**
 * 对于新浪和qq，所有消息类型的weiboatendpoint的filter
 * @author laisq
 *
 */
public class SINAQQAllWeiboFilterImpl extends AbstractEndpointFilter {

	@Autowired
	private RedisUtil redisUtil;	
	
	@Autowired
	private PlatformMongoUtil platformMongoUtil;	
		
	public boolean preHandler(MessageRequest request,Map<String,Object> preMap) throws Exception {
		VelocityContext context = request.getContext();
		String messageType = request.getMessageType();
		String oriWeiboId = ContextUtils.getOriWeiboId(context);
		
		if(oriWeiboId == null) {
			return true;
		}
		
		//newAnswer消息类型，并且有原始微博的id，才去查找有没有评论过，有评论，就直接返回，没有评论，继续执行下去
		if (oriWeiboId != null && messageType.equals("newAnswer")) { 
			Question question = (Question)context.get(TemplateConstants.CONTEXT_QUESTION);
			//已经评论过，直接返回
			if(question != null) {
				if(redisUtil.isAlreadyComment(question.getId())){
					return false;
				}
				
				redisUtil.cacheQuestionComment(question.getId());
			}
		}
		return true;
	}
	
	public void postHandler(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		//判断返回的map是否为空
		if(reponseMap.size() <= 0) {
			return;
		}
		
		VelocityContext context = request.getContext();
		String weiboId = reponseMap.get("weiboId").toString();
		
		//把weiboid存储到redis为了抓取评论
		if(weiboId != null) {
			String messageType = request.getMessageType();
			//redisUtil.pushWeiboIdForMatch(weiboId,context,messageType);
			
			//TODO:把weiboid存储到mongo为了抓取评论(目前先存储数据)
			platformMongoUtil.pushWeiboIdForMatch(weiboId, context, messageType);
		}
	}
	
	
}
