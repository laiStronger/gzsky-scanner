package com.wenwo.message.endpoint.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.endpoint.AbstractMessageEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.model.SMSMessageInfo;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.template.gen.ContentGenerator;
import com.wenwo.platform.doctor.open.service.IAliMessageService;
import com.wenwo.platform.doctor.service.IWeiwenDoctorService;
import com.wenwo.platform.entity.doctor.DrAnswer;
import com.wenwo.platform.entity.doctor.DrQuestion;
import com.wenwo.platform.entity.doctor.WeiwenDoctorUser;
import com.wenwo.platform.types.project.SubprojectType;
import com.wenwo.sms.api.ISMSService;
import com.wenwo.sms.entity.SendResult;

public class SMSMessageEndpoint extends AbstractMessageEndpoint{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String textTemplateId;
	public static final String SMS_SEND_SUCCESSFUL="sms_send_successful";
	public static final String DOCTOR_URL="http://dr.wenwo.com";
	private final ContentGenerator contentGenerator;	
	private final IMessageDao messageDao;		
	private final ISMSService smsService;
	private final IAliMessageService aliMessageService; 
	//private final RedisUtil redisUtil;
	private final IWeiwenDoctorService weiwenDoctorService;
    private final ShardedJedisPool shardedJedisPool;
	   
	public SMSMessageEndpoint(String textTemplateId,
			ContentGenerator contentGenerator, IMessageDao messageDao,
			ISMSService smsService, IAliMessageService aliMessageService,IWeiwenDoctorService weiwenDoctorService,
			ShardedJedisPool shardedJedisPool, List<IEndpointFilter> pointFilterList) {
		super(pointFilterList);
		this.textTemplateId = textTemplateId;
		this.contentGenerator = contentGenerator;
		this.messageDao = messageDao;
		this.smsService = smsService;
		this.aliMessageService=aliMessageService;
		this.weiwenDoctorService = weiwenDoctorService;
		
		this.shardedJedisPool = shardedJedisPool;
	}
	
	@Override
	protected void doSending(MessageRequest request,
			Map<String, Object> reponseMap) throws Exception {
		// TODO Auto-generated method stub
		//追问和新问题时避免私信与短信一起发送
		VelocityContext context = request.getContext();
		SubprojectType projectType = request.getSubprojectType();  //项目类型
		String messageType = request.getMessageType(); //消息类型
        
		String msgContent = contentGenerator.generateText(textTemplateId, context);  //调用模板生成短信内容
	
		logger.info("进入发短信接口,项目类型{},消息类型{}",projectType,messageType);
		if(projectType.equals(SubprojectType.EXTERNAL)){
			logger.info("进入阿里发短信接口");
			String phone = (String) context.get("phoneNum");
			sendMessage(phone,msgContent,messageType,null);
			return;
		}
		String uid = request.getTargetUser().getId();
		String phone="";
		if (messageType.equals("doctorRefuseApply")
				|| messageType.equals("doctorThroughAudit")
				|| messageType.equals("doctorAppointment")) {
			phone = (String) context.get("phoneNum");
            
			logger.info("phone is:" + context.get("phoneNum"));
			logger.info("appointMentTime is:" + context.get("appointMentTime"));
			logger.info("address is:" + context.get("address"));
			logger.info("nickname is:" + context.get("nickname"));

		} else {
			WeiwenDoctorUser doctorUser = weiwenDoctorService.findById(uid);
			phone = doctorUser.getTelephoneNumber();
			logger.info("the phone of WeiwenDoctorUser is:" + phone);

		}
       
        
		String questionId="";
		if(messageType.equals("doctorNewQuestion")){
			DrQuestion drQuestion = (DrQuestion)context.get(TemplateConstants.CONTEXT_QUESTION);
			questionId=drQuestion.getId();
		}else if(messageType.equals("doctorQuestionMore")){
			DrAnswer drAnswer = (DrAnswer) context
					.get(TemplateConstants.CONTEXT_ANSWER);
			questionId = drAnswer.getId();
		}
		
		
		
		SMSMessageInfo info=null;
   		if(StringUtils.isEmpty(msgContent)){
   			//判断发送内容是否为空
   			logger.error("内容为空，不发送");
   			return;
   		}
   		
        info=sendMessage(phone,msgContent,messageType,uid);
        
        if(!info.isSendSuccess()){
        	logger.info("发送短信失败");
        	messageDao.saveSMSMessage(info);  //发送短信失败保存
        }else{
        	//JedisPool jedisPool=redisUtil.getJedisPool();
        	//发送成功后保存在redis一个小时，方便查看
        	saveSMSToHash(messageType,uid,questionId);    //发送短信成功会保存在redis上面，时间一个小时
        }
   		
        
   		
	}
	
    /*
     * 通过接口短信发送
     */
	private SMSMessageInfo sendMessage(String phoneNum,String content,String messageType,String uid){
		SMSMessageInfo info=new SMSMessageInfo();
		info.setPhoneNum(phoneNum);
		info.setReceiver_id(uid);
		info.setText(content);
		info.setType(messageType);
		info.setCreateTime(new Date().toString());
        
		try {
			Pattern p = Pattern.compile("^(180|189|133|134|153|181)\\d{8}$");
			Matcher m = p.matcher(phoneNum);
			SendResult result=null;
			if (m.matches()) {
				if (messageType.equals("doctorNewQuestion")||
						messageType.equals("doctorQuestionMore")||
						messageType.equals("doctorSetMessage")){
					content=content.substring(0,content.indexOf("回")-1);
					result = smsService.sendSmsMessage(content, phoneNum);
				}else {
					if (content.length() > 67) {
						while (content.length() > 67) {
							String content1 = content.substring(0, 67);
							result = smsService.sendSmsMessage(content1,
									phoneNum);
                            
							content = content.substring(67, content.length());

						}
						result = smsService.sendSmsMessage(content, phoneNum);
						
					}else{
						result = smsService.sendSmsMessage(content, phoneNum);
					}
				}

			}else{
				result= smsService.sendSmsMessage(content, phoneNum);
			}
			
			// System.out.println(result);
			if(result!=null){
			info.setSendSuccess(result.isSuccessfull());
			logger.info("短信号码：" + phoneNum + "短信内容：" + content + "短信发送是否成功："
					+ result.isSuccessfull() + "短信是否返回错误信息："
					+ result.getErrorMsgList() + "短信返回码："
					+ result.getSendResult());
			}

		} catch (Exception e) {
			logger.error("发送短信失败,电话号码为{}", phoneNum, e);
			// info.setSendSuccess(false);
			return info;
		}
		// info.setSendSuccess(true);
		return info;
	}
	
	private void saveSMSToHash(String messageType,String uid,String questionId){
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			//jedis.del(SMS_SEND_SUCCESSFUL);			
			Long timeTemp=System.currentTimeMillis();
			jedis.hset(SMS_SEND_SUCCESSFUL,questionId+"_"+uid+"_"+timeTemp,messageType);
			jedis.expire(SMS_SEND_SUCCESSFUL,3600);
			logger.info("添加短信成功");
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
}
