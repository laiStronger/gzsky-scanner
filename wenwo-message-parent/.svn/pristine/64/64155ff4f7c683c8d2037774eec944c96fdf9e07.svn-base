package com.wenwo.message.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wenwo.message.endpoint.filter.IEndpointFilter;
import com.wenwo.message.request.MessageRequest;

/**
 * 
 * @author StanleyDing
 * @date 2013-9-24
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-24,	StanleyDing, 	Create
 */
public abstract class AbstractMessageEndpoint implements IMessageEndpoint {
	private Logger logger = LoggerFactory.getLogger(getClass());

	//doSending之前的map，方便以后扩展
	Map<String,Object> preMap = new HashMap<String,Object>();
		
	//doSending返回的map
	Map<String,Object> reponseMap = new HashMap<String,Object>();

	
	protected List<IEndpointFilter> pointFilterList = new ArrayList<IEndpointFilter>();
	public AbstractMessageEndpoint(List<IEndpointFilter> pointFilterList){
		this.pointFilterList = pointFilterList;
	}
	@Override
	public void send(MessageRequest request) throws Exception {
		//发送消息前的filter
		//该filter配置了发送消息前拦截
		
		Boolean preFlag = true; //判断是否继续执行到doSending的方法，需要保证filter集合都执行完毕
		for(IEndpointFilter filter : pointFilterList) {
			try {
				if(!filter.preHandler(request,preMap)) {
					logger.info("Filter{}返回值为false，消息发送终止:{}", filter.getClass().getSimpleName(), request);
					preFlag = false;
					continue;
				}
			} catch (Exception e) {
				preFlag = false;
				logger.error("Filter:{}返回值为false，消息发送终止:{}", filter.getClass().getSimpleName(), request, e);
				continue;   //发生异常后，继续下一个filter，最后不发送该消息
			}
		}
		
		
		if(!preFlag) {
			return;
		}
		
		doSending(request,reponseMap);
		
		//发送消息后的filter
		//该filter配置了发送消息后拦截
		for(IEndpointFilter filter : pointFilterList) {
			try {
				filter.postHandler(request,reponseMap);
			} catch (Exception e) {
				logger.error("Filter:{}发生异常:{}", filter.getClass().getSimpleName(), request,e);
				continue;  //发生异常后，继续后面filter的执行
			}
		}
		
		preMap.clear();
		reponseMap.clear();
	}

	protected abstract void doSending(MessageRequest request,Map<String,Object> reponseMap) throws Exception;


}
