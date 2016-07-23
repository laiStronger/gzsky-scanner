package com.wenwo.message.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.wenwo.message.dao.IChannelDao;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.fieldconstants.MessageConfigBaseFields;
import com.wenwo.message.fieldconstants.MessageTypeFields;
import com.wenwo.message.model.ProjectChannel;
import com.wenwo.message.model.base.MessageConfigBase.Status;

public class ChannelDao extends BaseDao implements IChannelDao {

	@Override
	public void update(ProjectChannel projectChannel) {
		mongoUtil.update(projectChannel);
	}

	@Override
	public void save(ProjectChannel projectChannel) {
		mongoUtil.save(projectChannel);

	}

	@Override
	public ProjectChannel getChannelByProjectType(MessageProjectType messageProjectType) {
		Query query = new Query();
		if (messageProjectType != null) {
			query.addCriteria(Criteria.where(MessageTypeFields.MESSAGE_PROJECT_TYPE).is(messageProjectType.name()));
		}
		return mongoUtil.getEntityByQuery(ProjectChannel.class, query);
	}

	@Override
	public List<ProjectChannel> loadAllChannel() {
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name());
		return mongoUtil.getList(ProjectChannel.class, null, criteria);
	}
}
