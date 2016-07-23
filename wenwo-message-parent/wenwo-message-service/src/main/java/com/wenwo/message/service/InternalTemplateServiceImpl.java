/**
 * 
 */
package com.wenwo.message.service;

import java.util.List;

import com.wenwo.message.api.IInternalTemplateService;
import com.wenwo.message.dao.ITemplateDao;
import com.wenwo.message.model.CollTemplate;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.TextTemplate;

/**
 * @author shuangtai
 *
 */
public class InternalTemplateServiceImpl implements IInternalTemplateService {

	private ITemplateDao templateDao;
	
	@Override
	public TextTemplate getTextTemplateById(String templateId) {
		return templateDao.getTextTemplateById(templateId);
	}

	@Override
	public PicTemplate getPicTemplateById(String picTemplateId) {
		return templateDao.getPicTemplateById(picTemplateId);
	}

	@Override
	public TextTemplate createTextTemplate(String content, String actionUser) {
		TextTemplate textTemplate = new TextTemplate(content, actionUser);
		return templateDao.createTextTemplate(textTemplate);
	}

	@Override
	public int updateTextTemplate(String templateId, String content, String actionUser) {
		return templateDao.updateTextTemplate(templateId, content, actionUser);
	}

	public ITemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	@Override
	public PicTemplate savePicTemplate(PicTemplate template) {
		if(template == null){
			return null;
		}
		return this.templateDao.savePicTemplate(template);
	}
	
	@Override
	public void updatePicTemplate(PicTemplate template) {
		if(template == null){
			 return;
		}
		this.templateDao.updatePicTemplate(template);
	}

	@Override
	public CollTemplate getCollTemplate() {
		return templateDao.getCollTemplate();
	}

	@Override
	public CollTemplate getCollTemplateById(String templateId) {
		return templateDao.getCollTemplateById(templateId);
	}

	@Override
	public void updateCollTemplate(CollTemplate template) {
		templateDao.updateCollTemplate(template);
	}

	@Override
	public void addNewCollTemplate(CollTemplate template) {
		templateDao.addNewCollTemplate(template);
	}

	@Override
	public List<TextTemplate> loadAllTextTemplate() {
		return templateDao.loadAllTextTemplate();
	}

	@Override
	public List<PicTemplate> loadAllPicTemplate() {
		return templateDao.loadAllPicTemplate();
	}

}
