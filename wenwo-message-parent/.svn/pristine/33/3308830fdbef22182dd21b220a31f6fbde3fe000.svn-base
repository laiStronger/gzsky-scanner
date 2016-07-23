package com.wenwo.message.dao;

import java.util.List;

import com.wenwo.message.model.CollTemplate;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.TextTemplate;

/**
 * 
 * @author shuangtai
 *
 */
public interface ITemplateDao {
	
	public TextTemplate getTextTemplateById(String templateId);
	
	public PicTemplate getPicTemplateById(String templateId);
	
	public TextTemplate createTextTemplate(TextTemplate template);

	/**
	 * @param templateId
	 * @param content
	 * @param actionUser
	 * @return 影响文档数
	 */
	public int updateTextTemplate(String templateId, String content, String actionUser);

	/**
	 * @param template
	 * @return
	 */
	public PicTemplate savePicTemplate(PicTemplate template);

	/**
	 * @return
	 */
	public CollTemplate getCollTemplate();

	/**
	 * @param templateId
	 * @return
	 */
	public CollTemplate getCollTemplateById(String templateId);

	/**
	 * @param template
	 */
	public void updateCollTemplate(CollTemplate template);

	/**
	 * @param template
	 */
	public void addNewCollTemplate(CollTemplate template);

	/**
	 * @return
	 */
	public List<TextTemplate> loadAllTextTemplate();

	public List<PicTemplate> loadAllPicTemplate();
	
	public void updatePicTemplate(PicTemplate template);
}
