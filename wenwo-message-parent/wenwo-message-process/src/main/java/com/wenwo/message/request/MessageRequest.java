package com.wenwo.message.request;

import java.io.Serializable;

import org.apache.velocity.VelocityContext;

import com.wenwo.message.sender.MessageSender;
import com.wenwo.platform.entity.WenwoUser;
import com.wenwo.platform.types.project.SubprojectType;
import com.wenwo.platform.types.user.AccountType;

/**
 * 这个对象用于在EndpointChain和Endpoint内传输
 * @author StanleyDing
 * @date 2013-9-23
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-23,	StanleyDing, 	Create
 */
public class MessageRequest implements Serializable{
	private static final char separatorChar = '_';
	private static final long serialVersionUID = 7323158865838741867L;
	
	private String messageId;//一次消息请求的id，由于一条消息针对多人，这个messageId对所有人都是一样的
	private String uniqueId;//由messageId和userIndex形成的唯一ID，将作为消息报的_id字段
	private int userIndex;//在当前这次消息请求中，该用户的序号，从0开始计数
	private int userCount;//当前这次消息总共要发送多少人
	private SubprojectType subprojectType;
	private String messageType;
	private AccountType accountType;
	
	/**
	 * 1.传入的是openUser时，如果该openUser不存在，此处可以为空，只发微博消息
	 * 2.传入的是wenwoUid时，若此处为空，则异常
	 * @see MessageSender#sendMessage(com.wenwo.message.data.MessageData)
	 */
	private WenwoUser targetUser;
	private VelocityContext context;
	
	public MessageRequest(String messageId, int userIndex, int userCount,
			SubprojectType subprojectType,
			String messageType, AccountType accountType, WenwoUser targetUser,
			VelocityContext context) {
		super();
		this.messageId = messageId;
		this.userIndex = userIndex;
		this.userCount = userCount;
		this.subprojectType = subprojectType;
		this.messageType = messageType;
		this.accountType = accountType;
		this.targetUser = targetUser;
		this.context = context;
		/**
		 * uniqueId值得说明一下：
		 * 以前的消息系统的messages表的id是由mongodb自行生成的，无含义；
		 * 这一版改成由消息系统生成，好处是提前把messageId返回给调用者，双方可以用于定位问题
		 * 但由于messageId针对的是一次消息请求，而一次请求中可能给多个用户发送消息，不能用作messages表的主键
		 * uniqueId是组合而成的，可用作主键，其规则是这样的：
		 * messageId_userIndex_userCount
		 */
		uniqueId = messageId +separatorChar + userIndex +separatorChar + userCount;
	}

	
	public String getMessageId() {
		return messageId;
	}

	public int getUserIndex() {
		return userIndex;
	}

	public int getUserCount() {
		return userCount;
	}

	public SubprojectType getSubprojectType() {
		return subprojectType;
	}

	public String getMessageType() {
		return messageType;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public WenwoUser getTargetUser() {
		return targetUser;
	}

	public VelocityContext getContext() {
		return context;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	@Override
	public String toString() {
		return "MessageRequest [messageId=" + messageId + ", userIndex="
				+ userIndex + ", userCount=" + userCount + ", subprojectType="
				+ subprojectType + ", messageType=" + messageType
				+ ", accountType=" + accountType + ", targetUser=" + (targetUser==null ? "" : targetUser.getId())
				+ "]";
	}
	
}
