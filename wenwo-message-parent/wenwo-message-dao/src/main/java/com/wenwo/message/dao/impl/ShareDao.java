package com.wenwo.message.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.wenwo.message.dao.IShareDao;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.fieldconstants.MessageConfigBaseFields;
import com.wenwo.message.fieldconstants.MessageTypeFields;
import com.wenwo.message.model.WeiboShareType;
import com.wenwo.message.model.base.MessageConfigBase.Status;

public class ShareDao extends BaseDao implements IShareDao {

	@Override
	public void addShare(WeiboShareType weiboShareType) {
		mongoUtil.save(weiboShareType);
	}

	@Override
	public List<WeiboShareType> getShareList(MessageProjectType messageProjectType) {
		Query query = new Query();

		if (messageProjectType != null) {
			query.addCriteria(Criteria.where(MessageTypeFields.MESSAGE_PROJECT_TYPE).is(messageProjectType.name()));
		}
		
		query.addCriteria(Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name()));
		return mongoUtil.find(query, WeiboShareType.class);
	}

	@Override
	public void changeNeedLoadStatus() {
		Query query = new Query();
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).is(Status.NEED_LOAD.name());
		query.addCriteria(criteria);
		
		Update update = Update.update(MessageConfigBaseFields.STATUS, Status.IN_USE.name());
		mongoUtil.updateMulti(query, update, WeiboShareType.class);
	}

	@Override
	public void updateShare(WeiboShareType weiboShareType) {
		mongoUtil.update(weiboShareType);

	}

	@Override
	public WeiboShareType getShareTypeById(String id) {
		WeiboShareType weiboShareType = mongoUtil.getEntityById(WeiboShareType.class, id);
		return weiboShareType;
	}

}
