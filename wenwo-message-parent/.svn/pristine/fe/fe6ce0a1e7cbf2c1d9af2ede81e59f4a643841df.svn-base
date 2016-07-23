package com.wenwo.message.builder;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.entity.MessageParameter;
import com.wenwo.message.entity.MessageParameter.IdType;
import com.wenwo.platform.entity.Answer;
import com.wenwo.platform.entity.Question;
import com.wenwo.platform.entity.WenwoCategory;
import com.wenwo.platform.exception.WenwoDaoException;
import com.wenwo.platform.service.IAnswerService;
import com.wenwo.platform.service.IQuestionService;
import com.wenwo.platform.service.IWenwoCategoryService;
import com.wenwo.platform.service.IWenwoPlatformUserService;

/**
 * 基于parameter构建velocity的上下文对象
 * 
 * @author StanleyDing
 * 
 */
public class ContextBuilder {
	private final Logger logger = LoggerFactory.getLogger(ContextBuilder.class);
	
	@Resource
	private IWenwoPlatformUserService wenwoPlatformUserService;
	
	@Resource
	private IQuestionService questionService;
	
	@Resource
	private IAnswerService answerService;
	
	@Resource
	private IWenwoCategoryService wenwoCategoryService;
	
	public VelocityContext buildContext(MessageParameter parameter) {
		//可以不传参数
		if (parameter == null) {
			return new VelocityContext();
		}
		//把参数放入velocity上下文中
		VelocityContext context = new VelocityContext(parameter.getProperties());
		
		IdType idType = parameter.getIdType();
		String id = parameter.getId();
		try {
			if (idType == IdType.USER) {
				context.put(TemplateConstants.CONTEXT_SOURCE_USER, wenwoPlatformUserService.getUserByID(id));
			} else if (idType == IdType.QUESTION) {
				Question question = questionService.getQuestionById(id);
				if (question != null) {
					context.put(TemplateConstants.CONTEXT_QUESTION, question);
					context.put(TemplateConstants.CONTEXT_QUESTION_USER,
							wenwoPlatformUserService.getUserByID(question.getUserId()));
					context.put(TemplateConstants.CONTEXT_QUESTION_CATEGORY,
							getQuestionCategroy(question.getMainCatagory()));
					
					//把双引号字符串转义
					String content = question.getContent();
					content = content.replaceAll("\"", "”");
					question.setContent(content);
				}
			} else if (idType == IdType.ANSWER) {
				Answer answer = answerService.getAnswerById(id);
				if (answer != null) {
					context.put(TemplateConstants.CONTEXT_QUESTION,
							questionService.getQuestionById(answer.getQuestionId()));
					context.put(TemplateConstants.CONTEXT_QUESTION_USER,
							wenwoPlatformUserService.getUserByID(answer.getQuestionUserId()));
					context.put(TemplateConstants.CONTEXT_ANSWER, answer);
					context.put(TemplateConstants.CONTEXT_ANSWER_USER,
							wenwoPlatformUserService.getUserByID(answer.getUserId()));
					
					//把双引号字符串转义
					String content = answer.getContent();
					content = content.replaceAll("\"", "”");
					answer.setContent(content);
				}
			}
			context.put(TemplateConstants.CONTEXT_CURRENT_TIME, Calendar.getInstance());
			//模版中配置
//			context.put(TemplateConstants.WENWO_BASIC_URL, wenwoQuestionBasicUrl);
//			context.put(TemplateConstants.WEIWEN_BASIC_URL, weiwenQuestionBasicUrl);
//			context.put(TemplateConstants.WEIWENQQ_BASIC_URL, weiwenQQQuestionBasicUrl);
			context.put(TemplateConstants.ID_TYPE, idType);
		} catch (Exception e) {
			logger.info("生成VilocityContext调用平台出错", e);
		}
		return context;
	}

	public String getQuestionCategroy(String categoryId) throws WenwoDaoException {
		WenwoCategory category = wenwoCategoryService.getCategoryById(categoryId);
		if(category != null){
			return category.getCategoryName();
		}
		return null;

	}

	/*
	 * public void properties() { Map<String, String> Map = new HashMap<String, String>(); Map.put("multiple", null);//
	 * 主人指数倍数，helpIndex 消息类型的时候传 Map.put("gift", null);// 礼品名称，giftChange 消息类型的时候传 Map.put("curScore", null);//
	 * 礼品兑换分数，giftChange 消息类型的时候传 Map.put("upMedal", null);// 高级勋章 upMedal 消息类型的时候传 Map.put("opRemoveAnswerScore",
	 * null);// 扣除点数 answerOpRemoved 消息类型的时候传 Map.put("curMedal", null);// 当前勋章 UpMedal Map.put("content", null);//
	 * 游戏push的内容 Map.put("client", null);// 目标平台 Map.put("messageType", null);// 消息类型 Map.put("weiwenQuestionBasicUrl",
	 * null);// 微问问题基础地址 Map.put("weiboType", null);// 微博类型 }
	 */

}
