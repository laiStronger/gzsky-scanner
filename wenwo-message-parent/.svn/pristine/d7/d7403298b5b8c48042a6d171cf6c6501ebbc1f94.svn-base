package com.wenwo.message.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.api.IShareService;
import com.wenwo.message.dao.IShareDao;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.WeiboShareType;

public class ShareServiceImpl implements IShareService {

	private final Logger LOGGER = LoggerFactory.getLogger(ShareServiceImpl.class);

	private IShareDao shareDao;

	public void setShareDao(IShareDao shareDao) {
		this.shareDao = shareDao;
	}

	@Override
	public void addShare(WeiboShareType weiboShareType) {
		shareDao.addShare(weiboShareType);

	}

	@Override
	public List<WeiboShareType> getShareList(MessageProjectType messageProjectType) {
		return shareDao.getShareList(messageProjectType);
	}

	@Override
	public void updateShare(String id, String picId) {
		WeiboShareType shareType = shareDao.getShareTypeById(id);
		if (shareType == null) {
			LOGGER.info("不存在id为{}的messageType", id);
			return;
		}

//		String picTemplateId = shareType.getPicTemplateId();
		// if (picTemplateId != null && !"".equals(picTemplateId)) {
		// return;
		// }
		shareType.setPicTemplateId(picId);
		shareDao.updateShare(shareType);

	}

}
