/**
 * 
 */
package com.wenwo.message.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.wenwo.message.dao.IMessageTypeDao;
import com.wenwo.message.enums.MainType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.fieldconstants.MessageConfigBaseFields;
import com.wenwo.message.fieldconstants.MessageTypeFields;
import com.wenwo.message.model.MessageTempConfig;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.WeiboShareType;
import com.wenwo.message.model.base.MessageConfigBase.Status;

/**
 * @author shuangtai
 *
 */
public class MessageTypeDao extends BaseDao implements IMessageTypeDao {

	@Override
	public List<MessageType> getMessageTypeList(MainType mainType, MessageProjectType messageProjectType) {
		Query query = new Query();
		
		if(mainType != null){
			query.addCriteria(Criteria.where(MessageTypeFields.MAIN_TYPE).is(mainType.name()));
		}
		
		if(messageProjectType != null){
			query.addCriteria(Criteria.where(MessageTypeFields.MESSAGE_PROJECT_TYPE).is(messageProjectType.name()));
		}
		
		query.addCriteria(Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name()));
		
		Sort sort = new Sort(Direction.ASC, MessageTypeFields.MAIN_TYPE);
		sort.and(new Sort(Direction.ASC, MessageTypeFields.TYPE_NAME));
		query.with(sort);
		
		return mongoUtil.find(query, MessageType.class);
	}

	@Override
	public MessageType getMessageTypeById(String messageTypeId) {
		return mongoUtil.getEntityById(MessageType.class, messageTypeId);
	}

	@Override
	public void updateMessageType(MessageType messageType) {
		messageType.setStatus(Status.NEED_LOAD);
		mongoUtil.update(messageType);
	}

	@Override
	public void deleteMessageTypeById(String messageTypeId) {
		MessageType messageType = this.mongoUtil.getEntityById(MessageType.class, messageTypeId);
		if(messageType != null){
			messageType.setStatus(Status.DELETE);
			//由于唯一索引的问题，在typeName后加时间戳
			long time = new Date().getTime();
			messageType.setTypeName(messageType.getTypeName() + time);
			mongoUtil.update(messageType);
		}
	}

	@Override
	public void addNewMessageType(MessageType newMessageType) {
		mongoUtil.save(newMessageType);
	}

	@Override
	public List<MessageType> loadAllMessageTypes() {
		Query query = new Query();
		Criteria criteria= Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name());
		query.addCriteria(criteria);
		return mongoUtil.find(query, MessageType.class);
	}

	@Override
	public List<WeiboShareType> loadAllShareTypes() {
		Query query = new Query();
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name());
		query.addCriteria(criteria);
		return mongoUtil.find(query, WeiboShareType.class);
	}

	@Override
	public void changeNeedLoadStatus() {
		Query query = new Query();
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).is(Status.NEED_LOAD.name());
		query.addCriteria(criteria);
		
		Update update = Update.update(MessageConfigBaseFields.STATUS, Status.IN_USE.name());
		mongoUtil.updateMulti(query, update, MessageType.class);
	}

	@Override
	public void dropMessageTempConfig() {
		mongoUtil.dropCollection(MessageTempConfig.class);
	}

	@Override
	public void insertMessageTempConfig(List<MessageTempConfig> messageTempConfigList) {
		if(messageTempConfigList != null && messageTempConfigList.size() >= 0){
			mongoUtil.insertAll(messageTempConfigList);
		}
	}

	@Override
	public List<MessageTempConfig> loadAllTempConfig() {
		return mongoUtil.findAll(MessageTempConfig.class);
	}
}
