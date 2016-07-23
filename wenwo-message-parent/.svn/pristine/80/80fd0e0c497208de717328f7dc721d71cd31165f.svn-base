package com.wenwo.message.sender;

import java.util.Iterator;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wenwo.message.builder.ContextBuilder;
import com.wenwo.message.builder.DoctorContextBuilder;
import com.wenwo.message.builder.ExternalContextBuilder;
import com.wenwo.message.chain.IMessageEndpointChain;
import com.wenwo.message.chain.MessageChainContainer;
import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.data.MessageData;
import com.wenwo.message.data.ShareData;
import com.wenwo.message.entity.OpenUserInfo;
import com.wenwo.message.printer.ExceptionPrinter;
import com.wenwo.message.request.MessageRequest;
import com.wenwo.platform.entity.WenwoUser;
import com.wenwo.platform.service.IWenwoPlatformUserService;
import com.wenwo.platform.types.project.SubprojectType;
import com.wenwo.platform.types.user.AccountType;


/**
 * 负责发送消息以及分享信息，Velocity上下文对象在此处构建，用户列表在这里进行遍历，进入后续处理流程的都是单个用户的信息了
 * @author StanleyDing
 * @date 2013-9-17
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-17,	StanleyDing, 	Create
 */
public class MessageSender{
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ContextBuilder contextBuilder;
	
	@Autowired
	private DoctorContextBuilder drContextBuilder;
	
	@Autowired
	private ExternalContextBuilder externalContextBuilder;
	@Autowired
	private IWenwoPlatformUserService wenwoPlatformUserService;
	
	@Autowired
	private ExceptionPrinter exceptionPrinter;
	
	@Autowired
	private MessageChainContainer messageChainContainer;
	
	/**
	 * 获取责任琏，构建velocity context
	 * 
	 * 责任链获取规则：
	 * 1.用户传入SubprojectType.WEIWEN/SubprojectType.WEIWENQQ 根据targetUser.accountType判断使用哪个责任链，例如：
	 * 		用户传入WEIWEN,目标用户accountType＝QQ，则使用WEIWENQQ的责任链；目标用户accountType=SINA,则使用WEIWEN责任链
	 * 2.用户传入其他，使用传入SubprojectType获取责任链
	 * @param message
	 */
	public void sendMessage(MessageData message) {
		
		VelocityContext context;
		try {
			//根据项目类型，构建context上下文
			SubprojectType subprojectType = message.getSubprojectType();
			if(subprojectType == SubprojectType.DOCTOR) {
				context = drContextBuilder.buildContext(message.getMessageParameter());
			} else {
				context = contextBuilder.buildContext(message.getMessageParameter());
			}
			
		} catch (Exception e) {
			printMessageError(null, message.getMessageId(), "生成Velocity Context失败", e);
			return;
		}
		
		//context.get(TemplateConstants.CONTEXT_GB_URL);
		
		UserIterator iterator;
		if(message.isOpenUid()) {
			iterator = new UserIterator(message.getTargetOpenUserList(), true);
		} else {
			iterator = new UserIterator(message.getTargetUidList());
		}
		
		int index = 0;
		String msgId = message.getMessageId();
		//循环为每个用户发送
		while(iterator.hasNext()) {
			User user = iterator.next();
			
			
			logger.info("开始处理{},用户为{},消息类型为{}", msgId,user,message.getMessageType());
			
			WenwoUser targetUser;
			AccountType accountType;
			String atName;//这是用于在微博消息中用于@目标用户的
			String openUid;
			
			try {
				if(user.isOpenUser) {
					OpenUserInfo openUserInfo = user.getOpenUserInfo();
					openUid = openUserInfo.getOpenUid();
					targetUser = wenwoPlatformUserService.getUserByOpenUid(openUserInfo.getOpenUid(), openUserInfo.getAccountType());
					if(targetUser==null) {
						logger.debug("用户[{}]在问我系统中不存在，将只会继续发送微博消息[{}]", openUserInfo, msgId);
					}
					
					atName = openUserInfo.getAtName();
					logger.info("使用openUser发送，{},得到atName:{}", openUserInfo, atName);
					accountType = openUserInfo.getAccountType();
				} else {
					String wenwoUid = user.getWenwoUid();
					targetUser = wenwoPlatformUserService.getUserByID(wenwoUid);
					if(targetUser==null) {
						printMessageError(wenwoUid, msgId, "目标用户不存在", null);
						//如果传入的是问我UID，而这个用户又不存在，则该用户不可继续发送
						continue;
					}
					openUid = targetUser.getOpenUid();
					atName = targetUser.getAtName();
					accountType = targetUser.getAccountType();
				}
			} catch (Throwable e) {
				printMessageError(user.getUniqueId(), msgId, "取目标用户时失败", e);
				continue;
			}
			
			
           
			if(targetUser!=null) {
				context.put(TemplateConstants.CONTEXT_TARGET_USER, targetUser);
			} else {
				context.remove(TemplateConstants.CONTEXT_TARGET_USER);
			}
			context.put(TemplateConstants.CONTEXT_TARGET_USER_AT_NAME, atName.trim());
			
			
			
			//责任链选取
			/**
			 * 责任链获取规则：
			 * 1.用户传入SubprojectType.WEIWEN/SubprojectType.WEIWENQQ 根据targetUser.accountType判断使用哪个责任链，例如：
			 * 		用户传入WEIWEN,目标用户accountType＝QQ，则使用WEIWENQQ的责任链；目标用户accountType=SINA,则使用WEIWEN责任链
			 * 2.用户传入其他，使用传入SubprojectType获取责任链
			 */
			SubprojectType projectType = null;
			try {
				projectType = getSubprojectType(accountType, message.getSubprojectType());
			} catch (Exception e) {
				logger.error("获取项目类型失败，{}", e);
				continue;
			}
			context.put(TemplateConstants.CONTEXT_PROJECT_TYPE, projectType);
			//获取消息处理链
			IMessageEndpointChain chain = messageChainContainer.getMessageChannelChain(projectType, message.getMessageType());
			
			if(chain==null || chain.size()==0) {
				printMessageError(null, message.getMessageId(), "未找到对应的消息处理责任链："+projectType+":"+message.getSubprojectType(), null);
				continue;
			}
			//责任链选取 END
			
			context.put(TemplateConstants.CONTEXT_OPEN_UID, openUid);
			try {
				chain.fire(new MessageRequest(message.getMessageId(), index, iterator.size(), projectType, message.getMessageType(), accountType, targetUser, context));
			} catch (Throwable e) {
				printMessageError(user.getUniqueId(), msgId, "发送消息失败", e);
			}
			index ++;
			logger.info("完成处理{},用户为{},消息类型为{}", msgId,user,message.getMessageType());
		}
	}

	

	/**
	 * 根据调用者传入SubprojectType与目标用户accountType返回projectType,用来确定使用哪个责任链
	 * @param targetUserAccountType 目标用户账号类型
	 * @param subprojectType
	 * @return
	 * @throws Exception 
	 */
	private SubprojectType getSubprojectType(AccountType targetUserAccountType,SubprojectType subprojectType) throws Exception {
		SubprojectType result = subprojectType;
		//WEIWEN/WEIWENQQ根据目标用户accoutType返回
		if(subprojectType == SubprojectType.DOCTOR){
			result = subprojectType;
		}else if(subprojectType == SubprojectType.WENWO){
			result = subprojectType;
		}else if(targetUserAccountType == AccountType.QQ){
			result = SubprojectType.WEIWENQQ;
		}else if(targetUserAccountType == AccountType.SINA){
			result = SubprojectType.WEIWEN;
		}else{
			throw new Exception("invalid account type");
		}
		
		return result;
	}

    public void sendExternalMessage(MessageData message){
    	VelocityContext context = null;
    	try {
    		SubprojectType subprojectType = message.getSubprojectType();
    		if(subprojectType.equals(SubprojectType.EXTERNAL)){
    			context =externalContextBuilder.buildContext(message.getMessageParameter());
    		}
    		
    		SubprojectType projectType = SubprojectType.EXTERNAL;
    		context.put(TemplateConstants.CONTEXT_PROJECT_TYPE, projectType);
    		
    		IMessageEndpointChain chain = messageChainContainer.getMessageChannelChain(projectType, message.getMessageType());
			if(chain==null || chain.size()==0) {
				printMessageError(null, message.getMessageId(), "未找到对应的消息处理责任链："+projectType+":"+message.getSubprojectType(), null);
				return;
			}
			
			try {
				chain.fire(new MessageRequest(message.getMessageId(),0,0, projectType, message.getMessageType(),null,null, context));
			} catch (Throwable e) {
				
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

	public void share(ShareData share) {
		VelocityContext context =  contextBuilder.buildContext(share.getMessageParameter());
		context.put(TemplateConstants.CONTEXT_PROJECT_TYPE, share.getSubprojectType());
		IMessageEndpointChain chain = messageChainContainer.getShareChannelChain(share.getSubprojectType(), share.getShareType());
		
		context.put(TemplateConstants.CONTEXT_PROJECT_TYPE, share.getSubprojectType());
		WenwoUser targetUser;
		try {
			targetUser = wenwoPlatformUserService.getUserByID(share.getActionUid());
			if(targetUser==null) {
				printShareError(share.getActionUid(), share.getShareId(), "目标用户不存在", null);
				//如果传入的是问我UID，而这个用户又不存在，则该用户不可继续发送
				return;
			}
		} catch (Throwable e) {
			printShareError(share.getActionUid(), share.getShareId(), "取目标用户时失败", e);
			return;
		}
		
		try {
			chain.fire(new MessageRequest(share.getShareId(), 0, 1, share.getSubprojectType(), share.getShareType(), targetUser.getAccountType(), targetUser, context));
		} catch (Throwable e) {
			printShareError(share.getActionUid(), share.getShareId(), "发送消息失败", e);
		}
		logger.info("完成处理{},用户为{}", share.getShareId(), share.getActionUid());
	} 
	
	public void printMessageError(String targetUid, String msgId, String description, Throwable e){
		if(e==null) {
			logger.error(description+":"+msgId+":"+targetUid);
		} else {
			logger.error(description+":"+msgId+":"+targetUid, e);
		}
		exceptionPrinter.printException(targetUid, msgId, description, e);
	}
	
	public void printShareError(String targetUid, String msgId, String description, Throwable e){
		if(e==null) {
			logger.error(description+":"+msgId+":"+targetUid);
		} else {
			logger.error(description+":"+msgId+":"+targetUid, e);
		}
	}
	
	private static class User {
		private String wenwoUid;
		private OpenUserInfo openUserInfo;
		private boolean isOpenUser = false;
		
		public User(String wenwoUid) {
			super();
			this.wenwoUid = wenwoUid;
			this.isOpenUser = false;
		}
		
		public User(OpenUserInfo openUserInfo) {
			super();
			this.openUserInfo = openUserInfo;
			this.isOpenUser = true;
		}

		public String getWenwoUid() {
			return wenwoUid;
		}

		public OpenUserInfo getOpenUserInfo() {
			return openUserInfo;
		}
		
		public String getUniqueId() {
			if(!isOpenUser) {
				return wenwoUid;
			} else {
				return openUserInfo.getUniqueId();
			}
		}

		@Override
		public String toString() {
			if(isOpenUser) {
				return this.openUserInfo.toString();
			} else {
				return this.wenwoUid;
			}
		}
		
		
	}
	
	private static class UserIterator implements Iterator<User> {
		private List<String> wenwoUidList;
		private List<OpenUserInfo> openUserList;
		private boolean isOpenUser = false;
		private int index;
		private int size;
		
		
		public UserIterator(List<String> wenwoUidList) {
			super();
			this.wenwoUidList = wenwoUidList;
			this.isOpenUser = false;
			this.size = wenwoUidList.size();
		}
		
		/**
		 * @param openUserList
		 * @param isOpenUser 这个参数本来无用，去掉参数之后就会报异常，因为List<OpenUserInfo>和List<String>在编译器之后就是一回事了
		 */
		public UserIterator(List<OpenUserInfo> openUserList, boolean isOpenUser) {
			super();
			this.openUserList = openUserList;
			this.isOpenUser = true;
			this.size = openUserList.size();
		}

		@Override
		public boolean hasNext() {
			return index<size;
		}

		@Override
		public User next() {
			if(isOpenUser) {
				return new User(openUserList.get(index++));
			} else {
				return new User(wenwoUidList.get(index++));
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		
		public int size() {
			return size;
		}
	}
}