package com.wenwo.message.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.gson.Gson;
import com.wenwo.message.constants.MessageConstants;
import com.wenwo.message.data.MessageData;
import com.wenwo.message.data.ShareData;
import com.wenwo.platform.types.WeiboType;
import com.wenwo.platform.types.project.SubprojectType;

/**
 * 针对redis操作的工具类
 * @author StanleyDing
 *
 */
public class RedisUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private JedisPool jedisPool;
	private Gson gson;

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	@Deprecated
	//redis 出问题后，会导致从0开始增长，造成id重复
	public String generateID(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return String.valueOf(jedis.incr(key));
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public void pushMessageData(MessageData data) {
		try {
			String json = gson.toJson(data);
			
			Jedis jedis = jedisPool.getResource();
			try {
				jedis.lpush(MessageConstants.MSG_QUEUE_KEY, json);
			} finally {
				jedisPool.returnResource(jedis);
			}
			logger.info("发布消息到redis成功："+data);
		} catch (Exception e) {
			logger.error("发布消息到redis失败："+data, e);
		}
	}

	public void pushShareData(ShareData data) {
		try {
			String json = gson.toJson(data);
			
			Jedis jedis = jedisPool.getResource();
			try {
				jedis.lpush(MessageConstants.SHARE_QUEUE_KEY, json);
			} finally {
				jedisPool.returnResource(jedis);
			}
			logger.info("发布分享到redis成功："+data);
		} catch (Exception e) {
			logger.error("发布分享到redis失败："+data, e);
		}
	}
	
	/**
	 * 记录对该问题对应微博发布的评论
	 * 只有在第一次回答时候，才发送评论，此后使用集合消息
	 * 不能使用 总回答数和有效回答数 原因
	 * 1.总回答数：第一条回答不通过，总回答数已经大于1，第二条有效回答也不会发送
	 * 2.有效回答数：审核通过后，若回答被投诉，有效回答数会－1，之后回答还会发送
	 * @param questionId
	 */
	public void cacheQuestionComment(String questionId){
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				double score = new Double(new Date().getTime());
				jedis.zadd(MessageConstants.QUSTION_WEIBO_COMMENT_KEY, score, questionId);
				
				//每次请求，删除5天前数据
				Date cleanTime = DateUtils.addDays(new Date(), -5);
				double maxScore = new Double(cleanTime.getTime());
				jedis.zremrangeByScore(MessageConstants.QUSTION_WEIBO_COMMENT_KEY, 0, maxScore);
			} catch(Exception e){
				logger.error("保存评论记录失败:", e);
			}
			finally {
				jedisPool.returnResource(jedis);
			}
		
	}
	
	public boolean isAlreadyComment(String questionId){
		boolean isCommented = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long index = jedis.zrank(MessageConstants.QUSTION_WEIBO_COMMENT_KEY, questionId);
			isCommented = index >= 0;
		} catch(Exception e){
			isCommented = false;
		}finally {
			jedisPool.returnResource(jedis);
		}
		return isCommented;
	}
	
	
	/**
	 * 微博id存储到redis中
	 * @param weiboId
	 * @param id
	 */
	public void pushWeiboIdForMatch(String weiboId,VelocityContext context,String messageType) {
		if(!messageType.equals("newAnswer") && !messageType.equals("answerMore") 
				&& !messageType.equals("askMore") && !messageType.equals("newMatchedUser")) {
			return;
		}
		
		Jedis jedis = null;
		
		try {
			jedis = jedisPool.getResource();
			String answerId = ContextUtils.getAnswerId(context);
			String questionId = ContextUtils.getQuestionId(context);
			//newAnswer,answerMore askMore,newMatchedUser
			if(messageType.equals("newMatchedUser")) {
				//问题
				jedis.hset(MessageConstants.MESSAGE_WEIBO_QMAP, weiboId, questionId);				
				logger.info("存储微博id到redis成功：MESSAGE:WEIBO:Q:MAP，微博id{},问题id{}",weiboId,questionId);
			} else {
				//答案
				jedis.hset(MessageConstants.MESSAGE_WEIBO_AMAP, weiboId, answerId);
				logger.info("存储微博id到redis成功：MESSAGE:WEIBO:A:MAP，微博id{},答案id{}",weiboId,answerId);
			}
				
		} catch (Exception e) {
			logger.error("存储微博id到redis失败："+weiboId, e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 获得questionId
	 * @param weiboId
	 * @return
	 */
	public String getQuestionIdByWeiboId(String weiboId) {
		String key = MessageConstants.MESSAGE_WEIBO_QMAP; 
		String id = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			id = jedis.hget(key,weiboId);
				
			logger.info("获取微博id成功：key值{},微博id{}",key,weiboId);
		} catch (Exception e) {
			logger.error("获取微博id失败：key值:{},微博id:{},发生异常:{}",key,weiboId,e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return id;
	}
	
	/**
	 * 获得answerId
	 * @param weiboId
	 * @return
	 */
	public String getAnswerIdByWeiboId(String weiboId) {
		String key = MessageConstants.MESSAGE_WEIBO_AMAP; 
		String id = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			id = jedis.hget(key,weiboId);
				
			logger.info("获取微博id成功：key值{},微博id{}",key,weiboId);
		} catch (Exception e) {
			logger.error("获取微博id失败：key值:{},微博id:{},发生异常:{}",key,weiboId,e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return id;
	}
	
	

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private static final String AT_LIMIT_KEY_FORMAT = "MESSAGE:AT:LIMIT:%s";
	private static final String MAP_KEY_FORMAT = "%s-%s-%s";
	public boolean exceedAtLimitPerDay(String openUid, WeiboType weiboType, SubprojectType subprojectType,int limit) {
		boolean isExceed = false;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String limitKey = getLimitKey();
			String mapKey = String.format(MAP_KEY_FORMAT, openUid, weiboType.name(),subprojectType.name());
			String atTime = jedis.hget(limitKey, mapKey);
			int atCount = StringUtils.isEmpty(atTime) ? 0 : Integer.parseInt(atTime);
			
			if(limit <= atCount) {
				isExceed = true;
			}
			
		}catch(Throwable t){
			logger.error("判断出错",t);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return isExceed;
	}

	private String getLimitKey() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
		String nowDay = format.format(calendar.getTime());
		return String.format(AT_LIMIT_KEY_FORMAT, nowDay);
	}

	public void increaseAtNum(String openUid, WeiboType weiboType,SubprojectType subprojectType) {
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			
			String limitKey = getLimitKey();
			
			if(!jedis.exists(limitKey)){
				jedis.hset(limitKey, "END_OF_DAY", limitKey);
				jedis.expire(limitKey, 86400);
			}
			
			String mapKey = String.format(MAP_KEY_FORMAT, openUid, weiboType.name(),subprojectType.name());
			String atTime = jedis.hget(limitKey, mapKey);
			int atCount = StringUtils.isEmpty(atTime) ? 0 : Integer.parseInt(atTime);
			jedis.hset(limitKey, mapKey, Integer.toString(atCount + 1));
		}catch(Throwable t){
			logger.error("更新at次数报错{}",openUid,t);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}

	

}
