/**
 * 
 */
package com.wenwo.message.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.wenwo.message.dao.ITemplateDao;
import com.wenwo.message.fieldconstants.MessageConfigBaseFields;
import com.wenwo.message.fieldconstants.TextTemplateFields;
import com.wenwo.message.model.CollTemplate;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.TextTemplate;
import com.wenwo.message.model.base.MessageConfigBase.Status;

/**
 * @author shuangtai
 *
 */
public class TemplateDao extends BaseDao implements ITemplateDao {

	@Override
	public TextTemplate getTextTemplateById(String templateId) {
		return mongoUtil.getEntityById(TextTemplate.class, templateId);
	}

	@Override
	public PicTemplate getPicTemplateById(String templateId) {
		return mongoUtil.getEntityById(PicTemplate.class, templateId);
	}

	@Override
	public  TextTemplate createTextTemplate(TextTemplate template) {
		return mongoUtil.save(template);
	}

	@Override
	public int updateTextTemplate(String templateId, String content, String actionUser) {
		Query query = new Query();
		query.addCriteria(Criteria.where(MessageConfigBaseFields.ID).is(templateId));
		Update update = Update.update(TextTemplateFields.TEXT, content);
		update.addToSet(MessageConfigBaseFields.EDITOR, actionUser);
		update.addToSet(MessageConfigBaseFields.END_TIME, new Date());
		return mongoUtil.updateFirst(query, update, TextTemplate.class);
	}

	@Override
	public PicTemplate savePicTemplate(PicTemplate template) {
		return this.mongoUtil.save(template);
	}
	
	@Override
	public void updatePicTemplate(PicTemplate template) {
		 this.mongoUtil.update(template);
	}

	@Override
	public CollTemplate getCollTemplate() {
		return this.mongoUtil.getEntityByQuery(CollTemplate.class, new Query());
	}

	@Override
	public CollTemplate getCollTemplateById(String templateId) {
		return mongoUtil.getEntityById(CollTemplate.class, templateId);
	}

	@Override
	public void updateCollTemplate(CollTemplate template) {
		mongoUtil.update(template);
	}

	@Override
	public void addNewCollTemplate(CollTemplate template) {
		mongoUtil.save(template);
	}

	@Override
	public List<TextTemplate> loadAllTextTemplate() {
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name());
		return mongoUtil.getList(TextTemplate.class, null, criteria);
	}

	@Override
	public List<PicTemplate> loadAllPicTemplate() {
		Criteria criteria = Criteria.where(MessageConfigBaseFields.STATUS).ne(Status.DELETE.name());
		return mongoUtil.getList(PicTemplate.class, null, criteria);
	}

}
