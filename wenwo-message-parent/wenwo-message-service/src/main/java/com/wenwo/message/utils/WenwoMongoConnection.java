package com.wenwo.message.utils;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class WenwoMongoConnection {

	private static Logger logger = LoggerFactory.getLogger(WenwoMongoConnection.class);
	
	//获取mongodb连接20数据库
	private static Mongo wenwoMongo;
	
	@SuppressWarnings("deprecation")
	public static Mongo getWenwoMongo() {
		if (wenwoMongo == null) {
			try {
				MongoOptions mongoOptions = new MongoOptions();
				mongoOptions.setConnectionsPerHost(80);
				mongoOptions.setThreadsAllowedToBlockForConnectionMultiplier(100);
				mongoOptions.setConnectTimeout(35000);
				mongoOptions.setMaxWaitTime(15000);
				mongoOptions.setAutoConnectRetry(true);
				mongoOptions.setSocketKeepAlive(true);
				mongoOptions.setSocketTimeout(50000);
				
				ServerAddress server = new ServerAddress("mongodb1.sys.wenwo.com", 30000);
				wenwoMongo = new Mongo(server, mongoOptions);
				wenwoMongo.getMongoOptions().setReadPreference(ReadPreference.secondaryPreferred());
			} catch (UnknownHostException e) {
				logger.error(e.getMessage(), e);
			} catch (MongoException e) {
				logger.error(e.getMessage(), e);
			} 
		}
		return wenwoMongo;
	}
	
	public static MongoTemplate init() {
		getWenwoMongo();
		MongoTemplate template = new MongoTemplate(wenwoMongo, "database");
		return template;
	}

}
