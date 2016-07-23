package com.wenwo.message.endpoint.impl;

import java.util.List;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wenwo.message.channel.WeiboChannel;
import com.wenwo.message.endpoint.AbstractMessageEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.im4java.JMagickImageBuilder;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.message.template.gen.ContentGenerator;
import com.wenwo.message.utils.RedisUtil;
import com.wenwo.platform.service.IAnswerService;
import com.wenwo.platform.service.IQuestionService;
import com.wenwo.platform.types.WeiboType;
import com.wenwo.platform.types.project.SubprojectType;
import com.wenwo.platform.types.user.AccountType;
import com.wenwo.weibo4j.http.ImageItem;
import com.wenwo.weibo4j.model.PostResult;
import com.wenwo.weibo4j.model.WeiboException;
import com.wenwo.weiboproxy.service.IWeiboProxyService;
import com.wenwo.weiboproxy.service.WeiboBigNodeFacade;

/**
 * 分享endpoint，用自己身份发一条微博
 * 
 * @author StanleyDing
 * @date 2013-9-21
 * @since 2.0
 * 
 *        History： Date, By, What 2013-9-21, StanleyDing, Create
 */
public class ShareEndpoint extends AbstractMessageEndpoint {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String textTemplateId;
	private final String reTextTemplateId;
	private final String imageTemplateId;
	private final ContentGenerator contentGenerator;
	private final IWeiboProxyService weiboProxyService;
	private final WeiboChannel sinaChannel;// 目标用户是新浪，使用这个通道发@
	private final WeiboChannel qqChannel;// 目标用户是腾讯，使用这个通道发@
	private final JMagickImageBuilder jMagickImageBuilder;
	private final WeiboBigNodeFacade weiboBigNodeFacade;
	private final RedisUtil redisUtil;
	private final IQuestionService questionService;
	private final IAnswerService answerService;

	public ShareEndpoint(String textTemplateId, String reTextTemplateId, String imageTemplateId,
			ContentGenerator contentGenerator, IWeiboProxyService weiboProxyService,
			WeiboBigNodeFacade weiboBigNodeFacade, JMagickImageBuilder jMagickImageBuilder, 
			WeiboChannel qqChannel,WeiboChannel sinaChannel,IQuestionService questionService,
			IAnswerService answerService,RedisUtil redisUtil,List<IEndpointFilter> pointFilterList) {
		super(pointFilterList);
		this.textTemplateId = textTemplateId;
		this.reTextTemplateId = reTextTemplateId;
		this.imageTemplateId = imageTemplateId;
		this.contentGenerator = contentGenerator;
		this.weiboProxyService = weiboProxyService;
		this.weiboBigNodeFacade = weiboBigNodeFacade;
		this.jMagickImageBuilder = jMagickImageBuilder;
		this.sinaChannel = sinaChannel;
		this.qqChannel = qqChannel;
		this.questionService = questionService;
		this.answerService = answerService;
		this.redisUtil = redisUtil;
		
		
	}

	@Override
	protected void doSending(MessageRequest request,Map<String,Object> reponseMap) throws Exception {
		VelocityContext context = request.getContext();
		String weiboId = null;
		
		if (request == null || request.getTargetUser() == null) {
			return;
		}
		
		String msgContent = contentGenerator.generateText(textTemplateId, context);
		String reMsgContent = contentGenerator.generateText(reTextTemplateId, context);
		ImageItem imageItem = getImageItem(request.getUniqueId(), context, request);
		WeiboType weiboType = request.getAccountType().getWeiboType();
		SubprojectType subprojectType = request.getSubprojectType();
		AccountType accountType = request.getAccountType();
		WeiboChannel channel = AccountType.SINA == accountType ? sinaChannel : qqChannel;
		
		/*if (reTextTemplateId != null && !"".equals(reTextTemplateId) && !StringUtils.isEmpty(reMsgContent)) {
			PostResult result = weiboBigNodeFacade.post(msgContent, null, imageItem, channel.getAccGroup(), weiboType,
					subprojectType);
			logger.info("同步答案-原始微薄[{}],及发送者[{}]", result.getWeiboId(), result.getWeiboUid());
			PostResult result1 = weiboProxyService.repost(request.getTargetUser().getOpenUid(), result.getWeiboId(),
					reMsgContent, weiboType, subprojectType);
			logger.info("同步答案-转发微薄[{}],及发送者[{}]", result1.getWeiboId(), result1.getWeiboUid());
		    
			weiboId = result1.getWeiboId();
		} */
		
		//直接使用 用户账号发送
		try {
			PostResult result = weiboProxyService.post(request.getTargetUser().getOpenUid(), msgContent,
					null, imageItem, weiboType,subprojectType);
			weiboId =  result.getWeiboId();
		} catch(Exception e) {
			//如果出现subprojecttype为open的情况，由于系统不兼容，使用问我继续重发一遍
			PostResult result = weiboProxyService.post(request.getTargetUser().getOpenUid(), msgContent,
					null, imageItem, weiboType,SubprojectType.WENWO);
			weiboId =  result.getWeiboId();
		}
		
		reponseMap.put("weiboId", weiboId);
	}

	private ImageItem getImageItem(String msgId, VelocityContext context, MessageRequest request) {
		byte[] pic = null;
		String backGoundPic = jMagickImageBuilder.getBackGroupPicPath(request);
		if (imageTemplateId != null && backGoundPic != null) {
			//TODO：下一版可以配置是所有用户生成同一张图片还是不同图片，如果是生成一张图片，则传messageId;如果是不同图片，则传uniqueId
			pic = contentGenerator.generateImage(request.getMessageId(),imageTemplateId, context, backGoundPic, jMagickImageBuilder);
		}

		try {
			return pic == null ? null : new ImageItem(pic);
		} catch (WeiboException e) {
			logger.error(String.format("生成图片失败[%1s]", msgId), e);
		}
		return null;
	}
}
