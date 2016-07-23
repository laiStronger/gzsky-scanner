package com.wenwo.message.endpoint.filter;



public abstract class AbstractEndpointFilter implements IEndpointFilter{

	private String projectScope;
	private String messageTypeScope;
	private String endpointScope;
	
	
	public String getProjectScope() {
		return projectScope;
	}

	public void setProjectScope(String projectScope) {
		this.projectScope = projectScope;
	}

	public String getMessageTypeScope() {
		return messageTypeScope;
	}

	public void setMessageTypeScope(String messageTypeScope) {
		this.messageTypeScope = messageTypeScope;
	}

	public String getEndpointScope() {
		return endpointScope;
	}

	public void setEndpointScope(String endpointScope) {
		this.endpointScope = endpointScope;
	}
}
