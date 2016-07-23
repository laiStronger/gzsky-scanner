package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.model.base.MessageConfigBase;

/**
 * 文字模版
 * @author shuangtai
 *
 */
public class TextTemplate extends MessageConfigBase implements Serializable, Cloneable{

	private static final long serialVersionUID = 6021789829820092015L;
	
	private String id;
	
	/**
	 * 文字模板内容:变量名与静态文本
	 * 例如：{提问者}你好
	 */
	private String text;

	public TextTemplate(){
		
	}
	
	public TextTemplate(String content, String actionUser){
		this.text = content;
		this.setInputor(actionUser);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TextTemplate [id=" + id + ", text=" + text + "]";
	}
}
