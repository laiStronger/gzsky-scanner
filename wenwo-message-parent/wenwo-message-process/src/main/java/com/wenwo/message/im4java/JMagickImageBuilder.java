package com.wenwo.message.im4java;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.entity.WenwoUser;
import com.wenwo.platform.entity.WenwoUser.ActiveStatus;
import com.wenwo.platform.types.YesNoMark;
import com.wenwo.platform.types.project.SubprojectType;
import com.wenwo.platform.types.user.VerifiedType;

public class JMagickImageBuilder {

	private final Logger logger = LoggerFactory.getLogger(JMagickImageBuilder.class);
	private final Map<String, String> fileImageBufferMap = new HashMap<String, String>();

	private String backGroupImagePath;// 背景图片存放的目录

	protected Map<String, String> imgNames;
	protected Map<String, Integer> imgPros;
	
	public JMagickImageBuilder(String backGroupImagePath) {
		this.backGroupImagePath = backGroupImagePath;
		init();
	}

	public void reloadBufferImg(){
		fileImageBufferMap.clear();
		init();
		logger.info("fileImageBufferMap:{}", fileImageBufferMap);
	}
	
	private void init() {
		try {
			File imageDir = new File(backGroupImagePath);
			File[] files = imageDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return StringUtils.containsIgnoreCase(name, "png") || StringUtils.containsIgnoreCase(name, "jpg");
				}
			});
			for (File file : files) {
				String name = file.getName();
				String filePath = file.getAbsolutePath();
				logger.info("初始化image: " + filePath);
				fileImageBufferMap.put(name, filePath);
			}
		} catch (Exception e) {
			logger.error("初始化缓存失败，将退出", e);
			System.exit(-1);
		}
	}

	public String getBufferPath(String appType, String key) {
		return fileImageBufferMap.get(appType + "_" + key);
	}

	
	/**
	 * 获取本地背景图片的路径
	 * 
	 * 1.优先返回根据用户信息获取的背景图
	 * 2. 步骤1，未能返回，则获取不关注用户信息的背景图
	 * 
	 * 注意：由于此处获取背景图片的逻辑，涉及到消息类型的名称，所以在配置后台更改消息类型名称时，
	 * 需要关注对应背景图名称的变化
	 * 
	 * @param request
	 * @return
	 */
	public String getBackGroupPicPath(MessageRequest request) {
		if (request == null) {
			return null;
		}
		
		SubprojectType subprojectType = request.getSubprojectType();
		
		String messageType = request.getMessageType();
		
		boolean isActiveUser = isActiveUser(request.getTargetUser());
		
		boolean isWeiboAgencyOrFamous = isWeiboAgencyOrFamous(request.getTargetUser());
		
		String backGroudPicPath = getBackGroudPicByUserInfo(isActiveUser, isWeiboAgencyOrFamous, messageType, subprojectType);
		
		//优先返回有身份信息的背景图片
		if(!StringUtils.isEmpty(backGroudPicPath)){
			return backGroudPicPath;
		}
		
		backGroudPicPath = getBackGroudPicPath(messageType, subprojectType);
		return backGroudPicPath;
	}
	
	/**
	 * 返回背景图片路径，不关注用户信息
	 * @param messageType
	 * @param subprojectType
	 * @return
	 */
	//不关注用户信息的背景图名称格式：项目名称_消息类型.jpg
	private static final String BG_PIC_NAME_FORMAT = "%s_%s.jpg";
	private String getBackGroudPicPath(String messageType,SubprojectType subprojectType) {
		String picName = String.format(BG_PIC_NAME_FORMAT, subprojectType.name(), messageType);
		return this.fileImageBufferMap.get(picName);
	}

	/**
	 * 根据用户信息，消息类型，项目类型获取背景图片
	 * @param isActiveUser
	 * @param isWeiboAgencyOrFamous
	 * @param messageType
	 * @param subprojectType
	 * @return
	 */
	//项目名称_消息类型_是否激活(Y/N)_是否达人名人(Y/N).jpg
	private static final String BACK_GROUND_PIC_NAME_BY_USER_INFO_FORMAT = "%s_%s_%s_%s.jpg";
	private String getBackGroudPicByUserInfo(boolean isActiveUser,boolean isWeiboAgencyOrFamous, String messageType,SubprojectType subprojectType) {
		String picName = String.format(BACK_GROUND_PIC_NAME_BY_USER_INFO_FORMAT,
										subprojectType.name(),
										messageType,
										YesNoMark.valueOf(isActiveUser).name(),
										YesNoMark.valueOf(isWeiboAgencyOrFamous).name());
		return this.fileImageBufferMap.get(picName);
	}

	/**
	 * 是否是微博达人名人 企业用户
	 * @param targetUser
	 * @return
	 */
	private boolean isWeiboAgencyOrFamous(WenwoUser targetUser) {
		if(targetUser == null || targetUser.getWeiboInfo() == null){
			return false;
		}
		
		VerifiedType verifiedType = targetUser.getWeiboInfo().getVerifiedType();
		
		if(verifiedType == VerifiedType.FAMOUS || verifiedType == VerifiedType.AGENCY){
			return true;
		}
		
		return false;
	}

	private boolean isActiveUser(WenwoUser targetUser) {
		boolean isActive = false;
		if(targetUser != null){
			isActive = (targetUser.getActive() == ActiveStatus.A);
		}
		return isActive;
	}

	/**
	 * 获取本地配置图片
	 * 例如：默认广告图片
	 * 
	 * @param request
	 * @return
	 */
	public String getRequestPic(SubprojectType subprojectType, String picName) {
		if (subprojectType == null || StringUtils.isEmpty(picName)) {
			return null;
		}
		String requestPic = getBufferPath(subprojectType.name(), picName);
		return requestPic;
	}
	
	
	public String getBackGroupImagePath() {
		return backGroupImagePath;
	}

	public void setBackGroupImagePath(String backGroupImagePath) {
		this.backGroupImagePath = backGroupImagePath;
	}
	
}
