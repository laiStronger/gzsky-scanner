package com.wenwo.message.utils;

import org.apache.velocity.VelocityContext;

import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.platform.entity.Answer;
import com.wenwo.platform.entity.Question;
import com.wenwo.platform.entity.WenwoUser;
import com.wenwo.platform.entity.doctor.DrQuestion;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 上下文工具类
 * 
 * @author StanleyDing
 * @date 2013-9-22
 * @since 2.0
 * 
 *        History： Date, By, What 2013-9-22, StanleyDing, Create
 */
public class ContextUtils {
	private static final String ORI_WEIBO_ID = "oriWeiboId";// 用于从context获取原始微博ID，用于发表评论

	public static String getSourceUserId(VelocityContext context) {
		// 实现从context中获取用户ID的逻辑（这个用户不是目标用户），用户对象是调用平台生成的WenwoUser对象，存在context的"user"这个key下面
		WenwoUser wenwoUser = (WenwoUser) context.get(TemplateConstants.CONTEXT_SOURCE_USER);
		if (wenwoUser != null) {
			return wenwoUser.getId();
		}
		return null;
	}
	
	public static String getTargetUserId(VelocityContext context) {
		// 实现从context中获取用户ID的逻辑（这个用户不是目标用户），用户对象是调用平台生成的WenwoUser对象，存在context的"user"这个key下面
		WenwoUser wenwoUser = (WenwoUser) context.get(TemplateConstants.CONTEXT_TARGET_USER);
		if (wenwoUser != null) {
			return wenwoUser.getId();
		}
		return null;
	}
	
	public static String getOpenUid(VelocityContext context) {
		// 实现从context中获取用户ID的逻辑（这个用户不是目标用户），用户对象是调用平台生成的WenwoUser对象，存在context的"user"这个key下面
		Object openUid = context.get(TemplateConstants.CONTEXT_OPEN_UID);
		if(openUid != null){
			return (String)openUid;
		}
		return null;
	}

	/**
	 * drquestion表
	 * @param context
	 * @return
	 */
	public static String getDRQuestionId(VelocityContext context) {
		//实现从context中获取问题ID的逻辑，问题对象是调用平台生成的Question对象，存在context的"question"这个key下面
		DrQuestion drquestion = (DrQuestion)context.get(TemplateConstants.CONTEXT_QUESTION);
		if (drquestion != null) {
			return drquestion.getId();
		}
		return null;
	}
	
	public static String getQuestionId(VelocityContext context) {
		//实现从context中获取问题ID的逻辑，问题对象是调用平台生成的Question对象，存在context的"question"这个key下面
		Question question = (Question)context.get(TemplateConstants.CONTEXT_QUESTION);
		if (question != null) {
			return question.getId();
		}
		return null;
	}
	
	public static Question getQuestion(VelocityContext context) {
		//实现从context中获取问题的逻辑，问题对象是调用平台生成的Question对象，存在context的"question"这个key下面
		return (Question) context.get(TemplateConstants.CONTEXT_QUESTION);
	}

	public static String getAnswerId(VelocityContext context) {
		//实现从context中获取答案ID的逻辑，答案对象是调用平台生成的Answer对象，存在context的"answer"这个key下面
		Answer answer = (Answer) context.get(TemplateConstants.CONTEXT_ANSWER);
		if (answer != null) {
			return answer.getId();
		}
		return null;
	}

	public static SubprojectType getProjectType(VelocityContext context){
		Object projectType = context.get(TemplateConstants.CONTEXT_PROJECT_TYPE);
		if(projectType == null){
			return null;
		}else{
			return (SubprojectType)projectType;
		}
	}
	/**
	 * 从context获取原始微博的ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getOriWeiboId(VelocityContext context) {
		return (String) context.get(ORI_WEIBO_ID);
	}
}
