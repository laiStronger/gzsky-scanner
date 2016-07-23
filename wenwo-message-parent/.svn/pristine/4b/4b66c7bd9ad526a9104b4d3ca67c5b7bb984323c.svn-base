/**
 * 
 */
package com.wenwo.message.api;

import java.util.List;

import com.wenwo.message.model.MessageTempConfig;

/**
 * 消息系统配置的重载
 * @author shuangtai
 *
 */
public interface IInternalConfigService {

	/**
	 * 这个方法主要是给消息类型管理后台用的
	 * 重新从mongodb中加载所有的消息类型配置信息。分为两步：
	 * 1）从原始配置表（变量、消息类型、分享类型）里加载信息，进行变量替换之后，存储到临时表中
	 * 2）各消息节点从临时表中读取配置到内存
	 * 
	 * 除非重新调用这个方法，否则以后哪怕消息服务节点宕机重启，也只会从临时表读取配置
	 */
	void reloadConfiguration();
	
	

	/**
	 * @return
	 */
	List<MessageTempConfig> loadAllTempConfig();
	
	public void updateObjectStatusByNeedLoad();
}
