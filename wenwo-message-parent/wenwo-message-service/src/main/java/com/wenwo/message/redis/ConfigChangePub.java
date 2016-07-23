package com.wenwo.message.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.wenwo.message.constants.MessageConstants;

/**
 * 配置发生变化后，由收到消息的服务器发布一个消息到redis，所有的节点都会订阅这个消息，从而更新自己的内存中的配置
 * @author StanleyDing
 * @date 2013-9-18
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-18,	StanleyDing, 	Create
 */
public class ConfigChangePub {
	
	private ShardedJedisPool shardedJedisPool;
	
	public void publishConfigurationReloading() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.getShard(MessageConstants.MESSAGE_SYSTEM_RELOADING_KEY).publish(MessageConstants.MESSAGE_SYSTEM_RELOADING_KEY, MessageConstants.MESSAGE_RELOAD);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
}
