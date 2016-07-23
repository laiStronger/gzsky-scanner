package com.wenwo.message.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.wenwo.message.dao.IUserDao;

public class UserDao extends BaseDao implements IUserDao {

	@Override
	public UserDetails loadUserByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoUtil.getEntityByQuery(UserDetails.class, query);
	}

}
