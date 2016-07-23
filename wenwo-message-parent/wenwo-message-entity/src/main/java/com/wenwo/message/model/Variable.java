/**
 * 
 */
package com.wenwo.message.model;

import java.io.Serializable;

import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.base.MessageConfigBase;

/**
 * @author shuangtai
 *
 */
public class Variable extends MessageConfigBase implements Serializable {

	private static final long serialVersionUID = -8246973316716530951L;
	
	private String id;
	
	private String name;
	
	private VariableType variableType;
	
	private String velocityExpress;
	
	private MessageProjectType variableProjectType;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public VariableType getVariableType() {
		return variableType;
	}


	public void setVariableType(VariableType variableType) {
		this.variableType = variableType;
	}


	public String getVelocityExpress() {
		return velocityExpress;
	}


	public void setVelocityExpress(String velocityExpress) {
		this.velocityExpress = velocityExpress;
	}


	public MessageProjectType getVariableProjectType() {
		return variableProjectType;
	}


	public void setVariableProjectType(MessageProjectType variableProjectType) {
		this.variableProjectType = variableProjectType;
	}


	public enum VariableType{
		BASE("基础变量"),
		ASK("提问变量"),
		ANSWER("回答变量"),
		TARGET_USER("目标用户"),
		USER("用户变量"),
		OTHER("其他变量");
		
		private String typeName;
		
		private VariableType(String typeName){
			this.typeName = typeName;
		}

		public String getTypeName() {
			return typeName;
		}
		
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Variable [id=" + id + ", name=" + name + ", variableType=" + variableType + ", velocityExpress=" + velocityExpress + ", variableProjectType=" + variableProjectType + "]";
	}
}
