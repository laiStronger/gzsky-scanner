/**
 * 
 */
package com.wenwo.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wenwo.message.api.IInternalConfigService;
import com.wenwo.message.config.ConfigLoader;
import com.wenwo.message.dao.IMessageDao;
import com.wenwo.message.dao.IMessageTypeDao;
import com.wenwo.message.model.MessageTempConfig;
import com.wenwo.message.redis.ConfigChangePub;

/**
 * @author shuangtai
 *
 */
public class InternalConfigServiceImpl implements IInternalConfigService {

	@Autowired
	private ConfigLoader configLoader;
	
	@Autowired
	private IMessageDao messageDao;
	
	@Autowired
	private ConfigChangePub configChangePub;
	
	@Autowired
	private IMessageTypeDao messageTypeDao;
	
	public ConfigChangePub getConfigChangePub() {
		return configChangePub;
	}

	public void setConfigChangePubsub(ConfigChangePub configChangePub) {
		this.configChangePub = configChangePub;
	}

	/** 
	 * 这个方法主要是给消息类型管理后台用的
	 * 重新从mongodb中加载所有的消息类型配置信息。分为两步：
	 * 1）从原始配置表（变量、消息类型、分享类型）里加载信息，进行变量替换之后，存储到临时表中
	 * 2）通知各消息节点从临时表中读取配置到内存
	 * 
	 * 除非重新调用这个方法，否则以后哪怕消息服务节点宕机重启，也只会从临时表读取配置
	 * 
	 * @see com.wenwo.message.redis.ConfigChangePubsub.ConfigChangingSub#onMessage()
	 */
	@Override
	public void reloadConfiguration() {
		configLoader.reloadConfiguration();
		configChangePub.publishConfigurationReloading();
	}

	@Override
	public List<MessageTempConfig> loadAllTempConfig() {
		return messageTypeDao.loadAllTempConfig();
	}

	public void updateObjectStatusByNeedLoad(){
		messageDao.updateObjectStatusByNeedLoad();
	}
}
