package com.wenwo.message.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.entity.Question;

/**
 * weiboAt不再写数据库表users.matchQuestion.info
 * 而以log的方式实现
 * @author shuangtai
 *
 */
public class UsersMatchQuestionInfoLogUtil {

	private static final Logger logger = LoggerFactory.getLogger(UsersMatchQuestionInfoLogUtil.class);
	
	/**
	 * 记录微博发送日志，以备统计
	 * 微博匹配消息再不保存数据库，只记录日志
	 * messageId<<T>>questionId<<T>>messageType<<T>>questionTime<<T>>matchedUid<<T>>openUid<<T>>atSuccess<<T>>weiboId<<T>>sendTime
	 * matchUid有可能是openUid
	 * @param request
	 * @param context
	 * @param weiboId
	 */
	public static void loggerForStatics(MessageRequest request, VelocityContext context, String weiboId) {
		//只有匹配时才需要打印该log
		if(!"newMatchedUser".equals(request.getMessageType())){
			return;
		}
		Question question = ContextUtils.getQuestion(context);
		Date questionTime = question == null ? null : question.getPostDate();
		
		Date sendTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatedPostDate = questionTime == null ? null : format.format(questionTime);
		
		boolean atSuccess = StringUtils.isEmpty(weiboId) ? false : true;
		
		logger.info("{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}", 
				request.getUniqueId(), 
				ContextUtils.getQuestionId(context), 
				request.getMessageType(),
				formatedPostDate,
				ContextUtils.getTargetUserId(context),
				ContextUtils.getOpenUid(context),
				atSuccess,
				weiboId,
				format.format(sendTime)
				);
	}
}
