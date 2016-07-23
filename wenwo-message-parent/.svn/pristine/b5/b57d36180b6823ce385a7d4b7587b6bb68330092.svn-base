package com.wenwo.message.model.base;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.wenwo.message.enums.MainType;
import com.wenwo.platform.types.project.ProjectType;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * @author shuangtai
 *
 */
public abstract class MessageBase implements Serializable{
	private static final long serialVersionUID = 6837153339412744974L;
	
	@Id
	private String id;//由messageId和userIndex形成的唯一ID，将作为消息报的_id字段
	
	private int userIndex;//在当前这次消息请求中，该用户的序号，从0开始计数
	
	private int userCount;//当前这次消息总共要发送多少人
	
	private String messageId;//一次消息请求的id，由于一条消息针对多人，这个messageId对所有人都是一样的
	/**
	 * 详情{@link com.wenwo.platform.types.project.SubprojectType}
	 */
	private SubprojectType subprojectType;
	/**
	 * 详情{@link com.wenwo.platform.types.project.ProjectType}
	 */
	private ProjectType projectType;
	
	private String messageType;//所属消息类型
	
	private MainType mainType;
//	private String sourceUid;// 消息是由谁触发的
	private String targetUid;// 消息发送对象的UID
	private String sendIP;// 是从哪里发出的消息，暂时忽略
	private Date createTime = new Date();// 消息创建的时间
	// private Date readTime;// 阅读时间
	// private boolean read;// 是否已读
	private Date sendTime;// 微博发送时间

	public MessageBase() {

	}

	public MessageBase(String uniqueId,
			SubprojectType subprojectType, ProjectType projectType,
			String messageType, String targetUid,
			String sendIP, Date createTime, Date sendTime) {
		super();
		this.id = uniqueId;
//		this.qid = qid;
//		this.aid = aid;
		this.subprojectType = subprojectType;
		this.projectType = projectType;
		this.messageType = messageType;
//		this.sourceUid = sourceUid;
		this.targetUid = targetUid;
		this.sendIP = sendIP;
		this.createTime = createTime;
		this.sendTime = sendTime;
	}

	public SubprojectType getSubprojectType() {
		return subprojectType;
	}

	public void setSubprojectType(SubprojectType subprojectType) {
		this.subprojectType = subprojectType;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

//	public String getSourceUid() {
//		return sourceUid;
//	}
//
//	public void setSourceUid(String sourceUid) {
//		this.sourceUid = sourceUid;
//	}

	public String getTargetUid() {
		return targetUid;
	}

	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}

	public String getSendIP() {
		return sendIP;
	}

	public void setSendIP(String sendIP) {
		this.sendIP = sendIP;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String uniqueId) {
		this.id = uniqueId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public MainType getMainType() {
		return mainType;
	}

	public void setMainType(MainType mainType) {
		this.mainType = mainType;
	}
	
}
