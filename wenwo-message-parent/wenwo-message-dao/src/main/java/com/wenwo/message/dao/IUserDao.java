package com.wenwo.message.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDao {
	
	public UserDetails loadUserByUsername(String userName);
}
