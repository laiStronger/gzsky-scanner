/**
 * 
 */
package com.wenwo.message.dao.impl;

import org.springframework.data.mongodb.core.WenwoMongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.wenwo.message.dao.IWeiboAtMessageDao;
import com.wenwo.message.fieldconstants.WeiboAtMessageFields;

/**
 * @author shuangtai
 *
 */
public class WeiboAtMessageDao extends BaseDao implements IWeiboAtMessageDao {

	private WenwoMongoTemplate mongoTemplate;
	
	@Override
	public String getQuestionIdByWeiboId(String weiboId) {
		DBCollection coll = mongoTemplate.getCollection(WeiboAtMessageFields.COLLECTION_NAME);
		DBObject query = new BasicDBObject(WeiboAtMessageFields.WEIBO_DATA_WEIBO_ID, weiboId);
		query.put(WeiboAtMessageFields.WEIBO_DATA_QUESTION_ID, new BasicDBObject("$exists", Boolean.TRUE));
		
		DBObject returnKeys = new BasicDBObject(WeiboAtMessageFields.WEIBO_DATA_QUESTION_ID, 1);
		DBObject result = coll.findOne(query, returnKeys);
		if(result == null){
			return null;
		}
		return ((BasicDBObject)result).getString(WeiboAtMessageFields.WEIBO_DATA_QUESTION_ID);
		
	}

	@Override
	public String getAnswerIdByWeiboId(String weiboId) {
		DBCollection coll = mongoTemplate.getCollection(WeiboAtMessageFields.COLLECTION_NAME);
		DBObject query = new BasicDBObject(WeiboAtMessageFields.WEIBO_DATA_WEIBO_ID, weiboId);
		query.put(WeiboAtMessageFields.WEIBO_DATA_ANSWER_ID, new BasicDBObject("$exists", Boolean.TRUE));
		
		DBObject returnKeys = new BasicDBObject(WeiboAtMessageFields.WEIBO_DATA_ANSWER_ID, 1);
		DBObject result = coll.findOne(query, returnKeys);
		if(result == null){
			return null;
		}
		return ((BasicDBObject)result).getString(WeiboAtMessageFields.WEIBO_DATA_ANSWER_ID);
		
	}

	public WenwoMongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(WenwoMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	

}
