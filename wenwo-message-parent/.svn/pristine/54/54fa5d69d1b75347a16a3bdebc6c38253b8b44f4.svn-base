/**
 * 
 */
package com.wenwo.message.api;

import java.util.List;

import com.wenwo.message.model.CollTemplate;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.TextTemplate;

/**
 * 对消息系统内部提供模版服务
 * @author shuangtai
 *
 */
public interface IInternalTemplateService {
	
	public TextTemplate getTextTemplateById(String templateId);
	
	public PicTemplate getPicTemplateById(String picTemplateId);

	/**
	 * 为message type 创建文字模版
	 * @param messageTypeId
	 * @param content
	 * @return new create template id
	 */
	public TextTemplate createTextTemplate(String content, String actionUser);
	
	/**
	 * 
	 * @param templateId
	 * @param content
	 * @param actionUser
	 * @return >0 更新成功，否则失败
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
	 * @return
	 */
	public CollTemplate getCollTemplateById(String templateId);

	public void updateCollTemplate(CollTemplate template);
	
	public void addNewCollTemplate(CollTemplate template);

	/**
	 * @return
	 */
	public List<TextTemplate> loadAllTextTemplate();
	
	public List<PicTemplate> loadAllPicTemplate();
	
	public void updatePicTemplate(PicTemplate template);
}
