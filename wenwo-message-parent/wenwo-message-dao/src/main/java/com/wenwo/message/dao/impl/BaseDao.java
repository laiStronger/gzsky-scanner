package com.wenwo.message.dao.impl;

import com.wenwo.platform.dao.util.IMongoUtil;

public class BaseDao {
	protected IMongoUtil mongoUtil;

	public void setMongoUtil(IMongoUtil mongoUtil) {
		this.mongoUtil = mongoUtil;
	}
}
