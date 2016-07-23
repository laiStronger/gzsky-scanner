package com.wenwo.message.model;
import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wenwo.platform.types.WeiboType;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 特殊的微博日志
 * @author laisq
 * 
 */
@Document(collection="specialWeiboLog")
public class SpecialWeiboLog implements Serializable{
	
	private static final long serialVersionUID = -3281102489488790093L;
	
	public static final String WEIBO_Uid = "weiboUid";
	public static final String SUBPROJECT_TYPE = "projectType";
	public static final String WEIBO_TYPE = "weiboType";
	public static final String TYPE = "type";
	
	private String weiboUid;
	private int type;//1:微博 2:私信
	private WeiboType weiboType;
	private SubprojectType projectType;  //项目类型
	private Date createTime;
	
	public SpecialWeiboLog() {
		
	}
	
	public SpecialWeiboLog(String weiboUid, WeiboType weiboType,SubprojectType projectType,Date createTime, int type) {
		this.weiboUid = weiboUid;
		this.weiboType = weiboType;
		this.projectType = projectType;
		this.createTime = createTime;
		this.type = type;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public static void main(String args[]) {
		SpecialWeiboLog record = new SpecialWeiboLog("1864803505",WeiboType.SINA,SubprojectType.WEIWEN,new Date(),1);
		System.out.println(record);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SpecialWeiboLog [weiboUid=" + weiboUid + ", type=" + type + ", weiboType=" + weiboType + ", projectType=" + projectType + ", createTime="
				+ createTime + "]";
	}

	
}
