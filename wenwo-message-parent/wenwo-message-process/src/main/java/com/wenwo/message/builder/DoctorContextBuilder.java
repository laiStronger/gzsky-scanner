package com.wenwo.message.builder;

import java.util.Calendar;
import javax.annotation.Resource;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.entity.MessageParameter;
import com.wenwo.message.entity.MessageParameter.IdType;
import com.wenwo.platform.doctor.service.IDrAnswerService;
import com.wenwo.platform.doctor.service.IDrQuestionService;
import com.wenwo.platform.doctor.service.IWeiwenDoctorService;
import com.wenwo.platform.entity.doctor.DrAnswer;
import com.wenwo.platform.entity.doctor.DrQuestion;
import com.wenwo.platform.service.IWenwoCategoryService;
import com.wenwo.platform.service.IWenwoPlatformUserService;

/**
 * 基于parameter构建velocity的上下文对象
 * 
 * 
 */
public class DoctorContextBuilder {
	private final Logger logger = LoggerFactory.getLogger(DoctorContextBuilder.class);
	
	@Resource
	private IWeiwenDoctorService weiwenDoctorService;  //druser表
	
	@Resource
	private IWenwoPlatformUserService wenwoPlatformUserService;  //wenwo用户表
	
	@Resource
	private IDrQuestionService drQuestionService;
	
	@Resource
	private IDrAnswerService drAnswerService;
	
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
				DrQuestion drQuestion = drQuestionService.findById(id);
				if (drQuestion != null) {
					context.put(TemplateConstants.CONTEXT_QUESTION, drQuestion);
					context.put(TemplateConstants.CONTEXT_QUESTION_USER,
							wenwoPlatformUserService.getUserByID(drQuestion.getUserId()));
				}
			} else if (idType == IdType.ANSWER) {
				DrAnswer drAnswer = drAnswerService.findById(id);
				if (drAnswer != null) {
					context.put(TemplateConstants.CONTEXT_QUESTION,
							drQuestionService.findById(drAnswer.getQuestionId()));
					context.put(TemplateConstants.CONTEXT_QUESTION_USER,
							wenwoPlatformUserService.getUserByID(drAnswer.getQuestionUserId()));
					context.put(TemplateConstants.CONTEXT_ANSWER, drAnswer);
					context.put(TemplateConstants.CONTEXT_ANSWER_USER,
							wenwoPlatformUserService.getUserByID(drAnswer.getUserId()));
				}
			}
			context.put(TemplateConstants.CONTEXT_CURRENT_TIME, Calendar.getInstance());
			context.put(TemplateConstants.ID_TYPE, idType);
		} catch (Exception e) {
			logger.info("生成VilocityContext调用平台出错", e);
		}
		return context;
	}


}
