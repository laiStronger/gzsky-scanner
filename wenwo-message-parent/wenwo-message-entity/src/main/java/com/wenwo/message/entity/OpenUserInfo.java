package com.wenwo.message.entity;

import java.io.Serializable;

import com.wenwo.platform.types.user.AccountType;

/**
 * 消息系统也可以接收非问我ID，这时候就是三个信息：openUid、微博昵称、accountType，封装到这个对象，方便传递
 * @author StanleyDing
 * @date 2013-9-19
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-19,	StanleyDing, 	Create
 */
public class OpenUserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5932029442710092733L;
	private String openUid;
	private String nickName;
	private AccountType accountType;
	
	public OpenUserInfo() {
		
	}
	
	public OpenUserInfo(String openUid, String nickName, AccountType accountType) {
		super();
		this.openUid = openUid;
		this.nickName = nickName;
		this.accountType = accountType;
	}
	public String getOpenUid() {
		return openUid;
	}
	public void setOpenUid(String openUid) {
		this.openUid = openUid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * 调用该接口时，匹配系统已处理该字段，直接使用nickName at
	 * 或者@的名称，新浪用昵称，腾讯用openUid
	 * 
	 * @return
	 */
	public String getAtName() {
        //return this.nickName;
		if(this.accountType==AccountType.SINA) {
			return this.nickName;
		} else if(this.accountType==AccountType.QQ) {
			return this.openUid;
		}
		return null;
	}
	
	/**
	 * 获得一个唯一的ID，方便消息系统记录日志
	 * @return
	 */
	public String getUniqueId() {
		return this.accountType + "_" + this.openUid;
	}

	@Override
	public String toString() {
		return "TargetWeiboUser [openUid=" + openUid + ", nickName=" + nickName
				+ ", accountType=" + accountType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((openUid == null) ? 0 : openUid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenUserInfo other = (OpenUserInfo) obj;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (openUid == null) {
			if (other.openUid != null)
				return false;
		} else if (!openUid.equals(other.openUid))
			return false;
		return true;
	}
	
}
