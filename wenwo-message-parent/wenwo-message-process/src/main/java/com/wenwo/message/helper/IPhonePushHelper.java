package com.wenwo.message.helper;

import java.util.List;

import com.wenwo.message.data.PushData;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.types.project.SubprojectType;

public interface IPhonePushHelper {
	/**
	 * 
	 * @param pushData
	 * @param token
	 *            token
	 * @param product
	 *            是否是正式机证书
	 */
	void send(PushData pushData, String token, boolean product);
	
	/**
	 * push消息给iphone的weiwen
	 * @param request
	 * @param msgContent
	 * @throws Exception
	 */
	public void pushIphoneMessage(MessageRequest request, String msgContent) throws Exception;
	
	/**
	 * push消息给iphone的doctor
	 * @param request
	 * @param msgContent
	 * @throws Exception
	 */
	public void pushIphoneDoctorMessage(MessageRequest request, String msgContent) throws Exception;

	/**
	 * 如果用户不在线，则不发送push
	 * 
	 * 在线用户：有轮询获取android push消息时，则将用户保存redis在线用户中
	 * 用户离线：每20分钟，清除20分钟未轮询push消息的用户
	 * @param request
	 * @param msgContent
	 * @throws Exception
	 */
	public void pushAndroidMessage(MessageRequest request, String msgContent) throws Exception;
	
	public void pushAndroidMessageByXinge(MessageRequest request, String msgContent) throws Exception;
	
	public void pushPatientAndroidMessageByXinge(MessageRequest request, String msgContent) throws Exception;
	
	public List<Object> getAndroidPushMessage(String targetUid, SubprojectType subprojectType);

	/**
	 * push消息给iphone的patient
	 * @param request
	 * @param msgContent
	 * @throws Exception
	 */
	public void pushIphonePatientMessage(MessageRequest request, String msgContent) throws Exception;
	
}
