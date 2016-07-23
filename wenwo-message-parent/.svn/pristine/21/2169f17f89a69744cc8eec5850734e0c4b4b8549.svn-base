package com.wenwo.message.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.wenwo.message.chain.MessageChainContainer;
import com.wenwo.message.constants.MessageConstants;
import com.wenwo.message.im4java.JMagickImageBuilder;

/**
 * 订阅配置变化的消息
 * @author StanleyDing
 * @date 2013-9-18
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-18,	StanleyDing, 	Create
 */
public class ConfigChangeSub {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private ShardedJedisPool shardedJedisPool;
	
	private MessageChainContainer messageChainContainer;
	
	private JMagickImageBuilder jMagickImageBuilder;
	
	public void init() {
		
		new Thread(){
			public void run() {
				final ShardedJedis jedis = shardedJedisPool.getResource();
				try {
					jedis.getShard(MessageConstants.MESSAGE_SYSTEM_RELOADING_KEY).subscribe(new ConfigChangingSub(), MessageConstants.MESSAGE_SYSTEM_RELOADING_KEY);
				} finally {
					shardedJedisPool.returnResource(jedis);
				}
				
			}
		}.start();
	}
	
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public void setMessageChainContainer(MessageChainContainer messageChainContainer) {
		this.messageChainContainer = messageChainContainer;
	}
	
	public void setjMagickImageBuilder(JMagickImageBuilder jMagickImageBuilder) {
		this.jMagickImageBuilder = jMagickImageBuilder;
	}

	private class ConfigChangingSub extends JedisPubSub {
		
		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) {
			;//do nothing
		}
		
		@Override
		public void onSubscribe(String channel, int subscribedChannels) {
			;//do nothing
		}
		
		@Override
		public void onPUnsubscribe(String pattern, int subscribedChannels) {
			;//do nothing
		}
		
		@Override
		public void onPSubscribe(String pattern, int subscribedChannels) {
			;//do nothing
		}
		
		@Override
		public void onPMessage(String pattern, String channel, String message) {
			;//do nothing
		}
		
		/* 
		 * 这里接收到reload指令之后，是从临时表中重新加载一次，是因为：
		 * 后台给消息系统发送reload指令时，消息服务集群中的某一个节点（节点A）会收到这个指令，这个节点会预先把数据从mongodb里面加载出来，并把redis里面替换之后，再向所有消息服务节点发布reload指令。
		 * 这里就是处理节点A发出来的reload指令
		 * 节点@接受后台的指令见下面的方法：
		 * @see com.wenwo.message.service。MessageServiceImpl#reloadConfiguration()
		 */
		@Override
		public void onMessage(String channel, String message) {
			logger.info("收到Reload指令："+message);
			if(message.equals(MessageConstants.MESSAGE_RELOAD)) {
				messageChainContainer.load();
				//将背景图片重新载入
				jMagickImageBuilder.reloadBufferImg();
			} else {
				logger.error("不能接受的reload指令："+message);
			}
		}
	}
}
