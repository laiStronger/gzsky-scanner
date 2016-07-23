/**
 * 
 */
package com.wenwo.message.dao.impl;

import com.wenwo.message.dao.IMessageErrorDao;
import com.wenwo.message.model.MessageError;
import com.wenwo.platform.dao.util.IMongoUtil;

/**
 * @author shuangtai
 *
 */
public class MessageErrorDao extends BaseDao implements IMessageErrorDao {

	@Override
	public void saveMessageError(MessageError messageError) {
		mongoUtil.save(messageError);
	}
	
	public IMongoUtil getMongoUtil() {
		return mongoUtil;
	}
	
	public void setMongoUtil(IMongoUtil mongoUtil) {
		this.mongoUtil = mongoUtil;
	}

}
