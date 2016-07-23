package com.wenwo.message.service;

import java.util.List;

import com.wenwo.message.api.IChannelService;
import com.wenwo.message.dao.IChannelDao;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.ProjectChannel;

public class ChannelServiceImpl implements IChannelService {

	private IChannelDao channelDao;

	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	@Override
	public void save(ProjectChannel projectChannel) {
		channelDao.save(projectChannel);

	}

	@Override
	public ProjectChannel getChannelByProjectType(MessageProjectType messageProjectType) {
		return channelDao.getChannelByProjectType(messageProjectType);
	}

	@Override
	public void update(ProjectChannel projectChannel) {
		channelDao.update(projectChannel);

	}

	@Override
	public List<ProjectChannel> loadAllChannel() {
		return channelDao.loadAllChannel();
	}

}
