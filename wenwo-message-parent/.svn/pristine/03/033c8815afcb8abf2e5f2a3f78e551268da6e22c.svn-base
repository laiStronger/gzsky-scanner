package com.wenwo.message.endpoint.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wenwo.message.channel.WeiboChannel;
import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.endpoint.AbstractMessageEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.im4java.JMagickImageBuilder;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.template.gen.ContentGenerator;
import com.wenwo.message.utils.ContextUtils;
import com.wenwo.message.utils.PlatformMongoUtil;
import com.wenwo.message.utils.RedisUtil;
import com.wenwo.message.utils.UsersMatchQuestionInfoLogUtil;
import com.wenwo.message.utils.WeiboSendMsgLogUtil;
import com.wenwo.platform.types.user.AccountType;
import com.wenwo.weibo4j.http.ImageItem;
import com.wenwo.weibo4j.model.CommentResult;
import com.wenwo.weibo4j.model.PostResult;
import com.wenwo.weiboproxy.service.WeiboBigNodeFacade;

/**
 * 
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 *        History： Date, By, What 2013-9-20, StanleyDing, Create
 */
public class WeiboAtEndpoint extends AbstractMessageEndpoint {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String textTemplateId;
	private final String imageTemplateId;
	private final ContentGenerator contentGenerator;
	private final WeiboChannel sinaChannel;// 目标用户是新浪，使用这个通道发@
	private final WeiboChannel qqChannel;// 目标用户是腾讯，使用这个通道发@
	private final WeiboBigNodeFacade weiboBigNodeFacade;
	private final JMagickImageBuilder jMagickImageBuilder;
	private final RedisUtil redisUtil;
	protected IMessageDao messageDao;
	protected PlatformMongoUtil platformMongoUtil;
	

	public WeiboAtEndpoint(String textTemplateId, String imageTemplateId, ContentGenerator contentGenerator,
			WeiboChannel sinaChannel, WeiboChannel qqChannel, WeiboBigNodeFacade weiboBigNodeFacade,
			JMagickImageBuilder jMagickImageBuilder, RedisUtil redisUtil,PlatformMongoUtil platformMongoUtil,List<IEndpointFilter> pointFilterList) {
		super(pointFilterList);
		this.textTemplateId = textTemplateId;
		this.imageTemplateId = imageTemplateId;
		this.contentGenerator = contentGenerator;
		this.sinaChannel = sinaChannel;
		this.qqChannel = qqChannel;
		this.weiboBigNodeFacade = weiboBigNodeFacade;
		this.jMagickImageBuilder = jMagickImageBuilder;
		this.redisUtil = redisUtil;
		this.platformMongoUtil = platformMongoUtil;
	}

	@Override
	protected void doSending(MessageRequest request,Map<String,Object> reponseMap) throws Exception {

		VelocityContext context = request.getContext();
		AccountType accountType = request.getAccountType();
		String messageType = request.getMessageType();
		
		String oriWeiboId = ContextUtils.getOriWeiboId(context);// 如果拿到原始微博的id，就给这条微博发评论，否则直接发一条微博进行@
		WeiboChannel channel = AccountType.SINA == accountType ? sinaChannel : qqChannel;
		
		//文字信息
		String msgContent = contentGenerator.generateText(textTemplateId, context);
		if(StringUtils.isEmpty(msgContent)){
			logger.error("内容为空，不发送");
			return;
		}

		String weiboId = null;
		boolean needSendAt = true;
		//只有newAnswer才发评论
		if (oriWeiboId != null && messageType.equals("newAnswer")) {
			weiboId = postComment(oriWeiboId, accountType, msgContent, channel,messageType);
			needSendAt = StringUtils.isEmpty(weiboId);
		} 
		
		//医生评论
		if(messageType.equals("doctorComment")) {
			String doctorCommentWeiboId = context.get(TemplateConstants.CONTEXT_DOCTOR_COMMENT_WEIBOID).toString();
			logger.info("调用消息类型doctorComment,传递的weiboId为{}",doctorCommentWeiboId);
			weiboId = postComment(doctorCommentWeiboId, accountType, msgContent, channel,messageType);
			needSendAt = StringUtils.isEmpty(weiboId);
			logger.info("调用消息类型doctorComment,生成的weiboId为{}",weiboId);
		}
		
		//处理后台特殊微博逻辑,处理完就结束
		if(messageType.equals("specialBGMessage")) {
			platformMongoUtil.dealSpecialWeibo(weiboBigNodeFacade,channel,request,msgContent);
			return;
		}
		
		if(needSendAt){
			boolean expertFlag = isOrNotExpert(context);
			String openUid = (String)context.get(TemplateConstants.CONTEXT_OPEN_UID);
			
			//专家不限时次数
			if(!expertFlag) {
				if(redisUtil.exceedAtLimitPerDay(openUid, accountType.getWeiboType(),request.getSubprojectType(), 7)){
					logger.info("user{} exceed AT limit per day", ContextUtils.getTargetUserId(context));
					return;
				}
			}
			
			//TODO：下一版可以配置是所有用户生成同一张图片还是不同图片，如果是生成一张图片，则传messageId;如果是不同图片，则传uniqueId
			ImageItem imageItem = this.getImageItem(request.getMessageId(), context, request);
			
			logger.info("WeiboAtEndpoint：消息类型{},post weibo:{}-{}-{}-{}, with pic? {}", messageType,msgContent, channel.getAccGroup(), accountType.getWeiboType(), channel.getSubprojectType(), imageItem != null);
			if(imageItem != null){
				weiboId = postWithPic(accountType, msgContent, imageItem, channel,messageType);
			}
			
			//防止发送失败使用重新发送
			if(StringUtils.isEmpty(weiboId)){
				weiboId = postWithNoPic(accountType, msgContent, channel,messageType);
			}
			
			if(!expertFlag) {
				redisUtil.increaseAtNum(openUid, accountType.getWeiboType(),request.getSubprojectType());
			}
			
			//匹配日志
			UsersMatchQuestionInfoLogUtil.loggerForStatics(request, context, weiboId);
		}
		
		//微博发送日志
		boolean isSuccess = StringUtils.isEmpty(weiboId) ? false : true;
		WeiboSendMsgLogUtil.logWeiboPostRecord(messageType, new Date(), 
				request.getSubprojectType(), request.getAccountType(), needSendAt, context, isSuccess);
		
		//这个主要为了去抓取评论
		reponseMap.put("weiboId", weiboId);
	}

	

	private String postWithNoPic(AccountType accountType, String msgContent, WeiboChannel channel,String messageType) {
		String weiboId = null;
		try {
			PostResult result = weiboBigNodeFacade.post(msgContent, null,null, channel.getAccGroup(), accountType.getWeiboType(), channel.getSubprojectType());
			weiboId = result.getWeiboId();
		} catch (Exception e) {
			logger.error("postWithNoPic：消息类型{},post weibo without pic failed",messageType,e);
		}
		return weiboId;
	}

	private String postWithPic(AccountType accountType, String msgContent, ImageItem imageItem, WeiboChannel channel,String messageType) {
		String weiboId = null;
		try {
			PostResult result = weiboBigNodeFacade.post(msgContent, null,
					imageItem, channel.getAccGroup(),
					accountType.getWeiboType(), channel.getSubprojectType());
			weiboId = result.getWeiboId();
		} catch (Exception e) {
			logger.error("postWithPic：消息类型{},post weibo with pic failed", messageType,e);
		}
		return weiboId;
	}

	private String postComment(String oriWeiboId, AccountType accountType, String msgContent, WeiboChannel channel,String messageType) {
		String weiboId = null;
		logger.info("post weibo comment:消息类型{},{}-{}-{}-{}-{}", messageType,msgContent, oriWeiboId, channel.getAccGroup(), accountType.getWeiboType(), channel.getSubprojectType());
		for(int tryTime = 1; tryTime <=3 ; tryTime++){
			try {
				CommentResult result = weiboBigNodeFacade.postComment(oriWeiboId, msgContent, channel.getAccGroup(),
						accountType.getWeiboType(), channel.getSubprojectType());
				weiboId = result.getId() + "_COMMENTS";
				break;
			} catch (Exception e) {
				logger.error("第{}次发评论失败{}", tryTime, e);
			}
		}
		return weiboId;
	}

	private ImageItem getImageItem(String picId, VelocityContext context, MessageRequest request) {
		byte[] pic = null;
		String backGroupPic = jMagickImageBuilder.getBackGroupPicPath(request);
		try {
			if (imageTemplateId != null && backGroupPic != null) {
				pic = contentGenerator.generateImage(picId, imageTemplateId, context, backGroupPic, jMagickImageBuilder);
			}
			return pic == null ? null : new ImageItem(pic);
		} catch (Throwable e) {
			logger.warn("生成图片失败 {}", picId, e);
		}
		return null;
	}
	
	/**
	 * 不限制专家发送微博次数
	 * @param context
	 * @return
	 */
	private Boolean isOrNotExpert(VelocityContext context) {
		if(context.get(TemplateConstants.CONTEXT_EXPERT_FLAG) == null) {
			return false;
		} else {
			if(context.get(TemplateConstants.CONTEXT_EXPERT_FLAG).toString().equals("true")) {
				return true;
			} else {
				return false;
			}
		}
		
	}
}
