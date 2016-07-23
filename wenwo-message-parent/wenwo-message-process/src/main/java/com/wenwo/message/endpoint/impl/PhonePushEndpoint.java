package com.wenwo.message.endpoint.impl;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.util.JSON;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.endpoint.AbstractInsiteEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.helper.IPhonePushHelper;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.template.gen.ContentGenerator;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 手机push消息
 * 非spring组件，手工创建
 * @author shuangtai
 *
 */
public class PhonePushEndpoint extends AbstractInsiteEndpoint {
	private final Logger logger = LoggerFactory.getLogger(PhonePushEndpoint.class);

	private IPhonePushHelper phonePushHelper;

	public void setPhonePushHelper(IPhonePushHelper phonePushHelper) {
		this.phonePushHelper = phonePushHelper;
	}

	public PhonePushEndpoint(ContentGenerator contentGenerator, String templateId,
			IMessageDao messageInsiteDao,List<IEndpointFilter> pointFilterList) {
		super(contentGenerator, templateId, "pushData", messageInsiteDao,pointFilterList);
	}
	
	public PhonePushEndpoint(IPhonePushHelper phonePushHelper, ContentGenerator contentGenerator, 
			String templateId, IMessageDao messageDao,List<IEndpointFilter> pointFilterList) {
		super(contentGenerator, templateId, "pushData", messageDao,pointFilterList);
		this.phonePushHelper = phonePushHelper;
	}

	@Override
	protected Object parseMessage(String messageContent) {
		return JSON.parse(messageContent);
	}

	/**
	 * 手机push，不创建站内消息
	 */
	@Override
	protected void doSending(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		if(request==null || request.getTargetUser()==null) {
			return;
		}
		
		SubprojectType subProjectType = request.getSubprojectType();
		
		String msgContent = contentGenerator.generateText(templateId, request.getContext());
		Object messageFieldValue = parseMessage(msgContent);
		logger.info("发送android 和 iphone push消息{}", msgContent);
		
		
		try {
			//微问医生的,使用信鸽
			if(subProjectType == SubprojectType.DOCTOR) {
				
				//患者
				VelocityContext context = request.getContext();
				Object objmark = context.get("mark");
				if(objmark != null) {      //患者
					logger.info("当前objmark是:{}", objmark);
					phonePushHelper.pushPatientAndroidMessageByXinge(request, msgContent);
					phonePushHelper.pushIphonePatientMessage(request, msgContent);   //iphone发给患者
				} else {                   //医生
					phonePushHelper.pushAndroidMessageByXinge(request, msgContent);
					phonePushHelper.pushIphoneDoctorMessage(request, msgContent);
				}
				
				
			} else if(subProjectType != SubprojectType.DOCTOR) {  //其他的使用gpn
				phonePushHelper.pushAndroidMessage(request, msgContent);
				phonePushHelper.pushIphoneMessage(request, msgContent);
			}
			
		} catch (Exception e) {
			logger.error("发送Push信息失败，request: 【{}】, content:【{}】", request, msgContent, e);
		}
		
		if(sendEx(request, msgContent)) {
			messageDao.saveMessage(request.getUniqueId(), messageField, messageFieldValue);

		}
		
	}

	
    /*@Override
	protected boolean sendEx(MessageRequest request, String messageContent) {
		String targetUserId = "";
		String questionId = "";
		String answerId = "";
		String deviceToken = "";
		String deviceType = "";
		try {
			VelocityContext velocityContext = request.getContext();
			WenwoUser targetUser = (WenwoUser) velocityContext.get(TemplateConstants.CONTEXT_TARGET_USER);
			Question question = (Question) velocityContext.get(TemplateConstants.CONTEXT_QUESTION);
			Answer answer = (Answer) velocityContext.get(TemplateConstants.CONTEXT_ANSWER);
			if (targetUser != null) {
				targetUserId = targetUser.getId();
				MobileData mobileData = targetUser.getMobileData();
				if (mobileData != null) {
					deviceToken = mobileData.getDeviceToken();
					deviceType = mobileData.getDeviceType();
				}
			}
			if (question != null) {
				questionId = question.getId();
			}
			if (answer != null) {
				answerId = answer.getId();
			}
			if (!StringUtils.isBlank(deviceToken)) {
				PushData pushData = new PushData(messageContent, 1, questionId, answerId, targetUserId, 1);
				phonePushHelper.send(pushData, deviceToken, getDeviceType(deviceType));
				logger.info("IPhone推送消息[deviceToken:{},data:{}]", deviceToken, pushData);
			} else {
				logger.error("IPhone推送消息[用户[{}]deviceToken为空值]", targetUserId);
			}
		} catch (Exception e) {
			logger.info("发送push消息失败,targetUserId:" + targetUserId + e);
			return false;
		}
		return true;
	}*/

	
	public boolean getDeviceType(String deviceType) {
		if ("false".equals(deviceType)) {
			return false;
		}
		return true;
	}
}
