package com.wenwo.message.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.util.JSON;
import com.wenwo.message.constants.MessageConstants;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 获取android push消息
 * @author shuangtai
 *
 */
public class AndroidPushMessageHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidPushMessageHelper.class);
	
	@Autowired
	private JedisPool jedisPool;
	
	private long messageExpireTime;
	
	public List<Object> getAndroidPushMessage(String targetUid, SubprojectType subprojectType) {
		logger.info("获取android 用户{}-{}push消息", targetUid, subprojectType);
		if(targetUid == null || subprojectType == null){
			return null;
		}
		
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String key = "apush_" + subprojectType.name()+ "_" + targetUid;
			
			//更新在线时间戳
			updateOnLineStatus(targetUid, subprojectType);
			
			long endTime = new Date().getTime();
			long startTime = endTime - this.messageExpireTime;
			Set<String> androidPushMessageSet = jedis.zrangeByScore(key, startTime, endTime);
			
			logger.info("android:uid: {}：{}", targetUid, androidPushMessageSet);
			//移除已经取出的push消息
			jedis.zremrangeByScore(key, 0, endTime);
			
			if(androidPushMessageSet != null && androidPushMessageSet.size() > 0){
				return getJsonList(androidPushMessageSet);
			}
		}catch (Exception e) {
			logger.error("获取push消息报错:{},{}" ,targetUid, e);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return null;
	}
	
	
	/**
	 * 用户轮询消息时，将用户放入 在线列表
	 * @param targetUid
	 * @param subprojectType
	 */
	private void updateOnLineStatus(String targetUid, SubprojectType subprojectType) {
		String member = subprojectType.name() + targetUid;
		long score = new Date().getTime();
		Jedis jedis = null;
		try{
			logger.info("更新用户{}在线状态,score{}",member, score);
			jedis = jedisPool.getResource();
			jedis.zadd(MessageConstants.ANDROID_ON_LINE_KEY, score, member);
		}catch(Exception e){
			logger.error("更新用户在线状态到jedis报错", e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	
	private List<Object> getJsonList(Set<String> androidPushMessageSet) {
		List<Object> jsonList = new ArrayList<Object>();
		
		for(String jsonElement : androidPushMessageSet){
			jsonList.add(JSON.parse(jsonElement));
		}
		return jsonList;
	}


	public void setMessageExpireTime(long messageExpireTime) {
		this.messageExpireTime = messageExpireTime;
	}
}
