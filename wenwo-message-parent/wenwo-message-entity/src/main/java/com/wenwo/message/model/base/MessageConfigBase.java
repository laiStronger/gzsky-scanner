package com.wenwo.message.model.base;

import java.io.Serializable;
import java.util.Date;

public class MessageConfigBase implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8481630402675585647L;

	private String inputor;//输入者
    
    private String editor;//修改者
    
    private Date endTime;//最后修改时间

    private Date createTime = new Date();//创建时间
    
    private Status status = Status.NEED_LOAD;
    
	public enum Status{
		IN_USE,//启用
		NEED_LOAD,//需要加载
		DELETE//已经删除
		;
	}


	public String getInputor() {
		return inputor;
	}

	public void setInputor(String inputor) {
		this.inputor = inputor;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "MessageBase [inputor=" + inputor + ", editor=" + editor
				+ ", endTime=" + endTime + "]";
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
