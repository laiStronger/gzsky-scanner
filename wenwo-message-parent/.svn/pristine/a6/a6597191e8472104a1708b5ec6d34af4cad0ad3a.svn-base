package com.wenwo.message.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.wenwo.message.model.SpecialWeiboLog;
import com.wenwo.message.model.WeiboAnswerInfo;
import com.wenwo.message.model.WeiboQuestionInfo;
import com.wenwo.platform.dao.util.IMongoUtil;

/**
 * mongo操作类
 * @author StanleyDing
 *
 */
public class PlatformMongoUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private IMongoUtil mongoUtil;
	
	public void setMongoUtil(IMongoUtil mongoUtil) {
		this.mongoUtil = mongoUtil;
	}

	
	/**
	 * 获得questionId
	 * @param weiboId
	 * @return
	 */
	public String getQuestionIdByWeiboId(String weiboId) {
		String id = null;
		try {
			Query query = new Query(Criteria.where("weiboId").is(weiboId));
			WeiboQuestionInfo qInfo = mongoUtil.getEntityByQuery(WeiboQuestionInfo.class,query);	
			id = qInfo.getQuestionId();
			logger.info("获取questionId成功,微博id{}",weiboId);
		} catch (Exception e) {
			logger.error("获取questionId失败,微博id:{}",weiboId,e);
		} 
		
		return id;
	}
	
	/**
	 * 获得answerId
	 * @param weiboId
	 * @return
	 */
	public String getAnswerIdByWeiboId(String weiboId) {
		String id = null;
		try {
			Query query = new Query(Criteria.where("weiboId").is(weiboId));
			WeiboAnswerInfo aInfo = mongoUtil.getEntityByQuery(WeiboAnswerInfo.class,query);	
			id = aInfo.getAnswerId();
			logger.info("获取questionId成功,微博id{}",weiboId);
		} catch (Exception e) {
			logger.error("获取questionId失败,微博id:{}",weiboId,e);
		} 
		
		return id;
	}

	/**
	 * 保存SpecialWeiboLog到20数据库
	 * @param specialWeiboLog
	 */
	public void saveSpecialWeiboLog(SpecialWeiboLog specialWeiboLog) {
		MongoTemplate springMongoTemplate = null;
		springMongoTemplate = WenwoMongoConnection.init();
		springMongoTemplate.save(specialWeiboLog);
	}

}
