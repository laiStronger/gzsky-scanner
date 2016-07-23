package com.wenwo.message.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.platform.types.project.SubprojectType;
import com.wenwo.platform.types.user.AccountType;

/**
 * messageType，createTime，targetUid，weiboType，qid，subprojectType comment/weibo
 * @author shuangtai
 *
 */
public class WeiboSendMsgLogUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WeiboSendMsgLogUtil.class);
	/**
	 * 
	 * @param messageType
	 * @param createTime
	 * @param qid
	 * @param subprojectType
	 * @param weiboType 
	 * @param sendType weibo/comment
	 */
	public static void logWeiboPostRecord(String messageType, Date createTime,
			SubprojectType subprojectType, AccountType accountType, 
			boolean isAt, VelocityContext context, boolean isSuccess){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//医生表的问题
		String questionId = null;
		if(subprojectType == SubprojectType.DOCTOR) {
			questionId = ContextUtils.getDRQuestionId(context);
		} else {
			questionId = ContextUtils.getQuestionId(context);
		}
		
		String sendType = isAt ? "weibo" : "comment";
		logger.info("当前微博发送日志:{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}<<T>>{}", 
				messageType, 
				format.format(createTime),
				ContextUtils.getTargetUserId(context),
				accountType.name(),
				questionId, 
				subprojectType,
				sendType,
				isSuccess
				);
	}
}
