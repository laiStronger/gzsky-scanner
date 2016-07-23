package com.wenwo.message.model;
import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wenwo.platform.types.WeiboType;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 特殊的微博日志
 * 
 * @author laisq
 * 
 */
@Document(collection = "specialWeiboRecord")
public class SpecialWeiboRecord implements Serializable {

	private static final long serialVersionUID = 2697169115060531934L;

	private Date postTime;
	private String targetId;
	private String weiboUid;
	private int type;// 1:微博 2:私信
	private WeiboType weiboType;
	private SubprojectType projectType; // 项目类型
	private String msgContent;

	public SpecialWeiboRecord() {

	}

	public SpecialWeiboRecord(String targetId, String weiboUid, WeiboType weiboType, SubprojectType projectType, Date postTime, int type, String msgContent) {
		this.targetId = targetId;
		this.weiboUid = weiboUid;
		this.weiboType = weiboType;
		this.projectType = projectType;
		this.postTime = postTime;
		this.type = type;
		this.msgContent = msgContent;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getWeiboUid() {
		return weiboUid;
	}

	public void setWeiboUid(String weiboUid) {
		this.weiboUid = weiboUid;
	}

	public WeiboType getWeiboType() {
		return weiboType;
	}

	public void setWeiboType(WeiboType weiboType) {
		this.weiboType = weiboType;
	}

	public SubprojectType getProjectType() {
		return projectType;
	}

	public void setProjectType(SubprojectType projectType) {
		this.projectType = projectType;
	}

	public static void main(String args[]) {
		SpecialWeiboRecord record = new SpecialWeiboRecord("123", "456", WeiboType.SINA, SubprojectType.DOCTOR, new Date(), 1, "test");
		System.out.println(record);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Override
	public String toString() {
		return "SpecialWeiboRecord [postTime=" + postTime + ", targetId=" + targetId + ", weiboUid=" + weiboUid + ", type=" + type + ", weiboType=" + weiboType
				+ ", projectType=" + projectType + ", msgContent=" + msgContent + "]";
	}

}
