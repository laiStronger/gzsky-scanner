package com.wenwo.message.endpoint.impl;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.wenwo.message.constants.MessageConstants;
import com.wenwo.message.endpoint.AbstractMessageEndpoint;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.request.MessageRequest;

/**
 * 如果微博消息被设为进入集合发送，那么就会使用到这个endpoint。
 * 1）凡是进入集合消息的，都不会直接发送@，而只是在redis中增加计数
 * 2）另有一个定时任务每分钟执行一次，凡是发现有计数的用户，并且其间隔时间已有超过3个小时了，就会给该用户发送集合消息，同时清除redis中该用户的记录
 * 
 * redis中的数据结构包括两部分：
 * 1）每个用户对应一个hash结构的数据，key是消息类型，value是该消息类型进入集合消息的计数
 * 2）一个用户的有序集合，成员是uid，score是该用户开始有集合消息的时间戳
 * 
 * 在定时任务进行集合消息的时候，分以下几步：
 * 1）先在MessageConstants.BATCH_MESSAGE_USER_SET_KEY 这个有序集合中，使用zrangebyscore(0, 当前时间-3个小时)取出在集合消息列表中而且超过3个小时未发送的全部用户
 * 2）遍历上述用户列表，根据用户ID，取出该用户的集合消息hash，使用模板形成消息内容，并发送消息
 * 3）删除该用户的集合消息hash，并从MessageConstants.BATCH_MESSAGE_USER_SET_KEY中删除该用户的uid
 * 
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-20,	StanleyDing, 	Create
 */
public class WeiboBatchEndpoint extends AbstractMessageEndpoint {
	private ShardedJedisPool shardedJedisPool;
	
	
	public WeiboBatchEndpoint(ShardedJedisPool shardedJedisPool,List<IEndpointFilter> pointFilterList) {
		super(pointFilterList);
		this.shardedJedisPool = shardedJedisPool;
	}
	@Override
	protected void doSending(MessageRequest request,Map<String,Object> reponseMap) {
		if(request==null||request.getTargetUser()==null) {
			return;
		}
		String uid = request.getTargetUser().getId();
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.hincrBy(MessageConstants.BATCH_MESSAGE_USER_STATIS_PREFIX+uid, request.getMessageType(), 1);//增加该用户的这个消息类型的计数
			
			//sortedSet集合
			//如果该用户在集合消息列表中不存在，则把该用户加入此列表，同时，使用当前时间戳作为其score，方便后续3个小时后取出该用户
			//如果该用户已经存在，则不能再add一次，否则会覆盖掉score
			if(jedis.zrank(MessageConstants.BATCH_MESSAGE_USER_SET_KEY, uid)<0) {
				jedis.zadd(MessageConstants.BATCH_MESSAGE_USER_SET_KEY, System.currentTimeMillis(), uid);
			}
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
	}

}
