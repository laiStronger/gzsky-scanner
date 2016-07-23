package com.wenwo.message.template.gen;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.wenwo.message.im4java.ImageUtil;
import com.wenwo.message.im4java.JMagickImageBuilder;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.PicTemplate.CutInfo;
import com.wenwo.message.model.TextTemplate;
import com.wenwo.message.template.velocity.TemplateGenerator;

/**
 * 根据模板生成内容的逻辑实现
 * 
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 *        History： Date, By, What 2013-9-20, StanleyDing, Create
 */
public class ContentGenerator {

	private final TemplateGenerator templateGenerator = new TemplateGenerator();

	private final Logger logger = LoggerFactory.getLogger(ContentGenerator.class);

	private final Map<String, TextTemplate> textTemplateMap;
	private final Map<String, PicTemplate> picTemplateMap;
	private final VelocityEngine velocityEngine;

	public ContentGenerator(VelocityEngine velocityEngine, Map<String, TextTemplate> textTemplateMap,
			Map<String, PicTemplate> picTemplateMap) {
		super();
		this.velocityEngine = velocityEngine;
		this.textTemplateMap = textTemplateMap;
		this.picTemplateMap = picTemplateMap;
	}

	public String generateText(String templateId, VelocityContext context) {
		TextTemplate template = textTemplateMap.get(templateId);
		if (template != null) {
			return templateGenerator.generatorTemplate(template.getText(), context, velocityEngine);
		}
		return null;
	}

	public byte[] generateImage(String picId, String picTemplateId, VelocityContext context, String backGroupPic, JMagickImageBuilder jMagickImageBuilder) {

		try {
			PicTemplate picTemplate = picTemplateMap.get(picTemplateId);
			if(picTemplate == null){
				return null;
			}
			if(!picTemplate.getIsOnlyBackGroupPic() && CollectionUtils.isEmpty(picTemplate.getTextInfos()) && CollectionUtils.isEmpty(picTemplate.getImageInfos())){
				return null;
			}
			
			String tagertPath = getTagertPath(picId);
			if (tagertPath != null) {
				if (!new File(tagertPath).exists()) {
					String finalPath = createImage(picTemplate, tagertPath, context, backGroupPic, jMagickImageBuilder);
					if (finalPath == null) {
						logger.info("图片生成失败,尝试第二次");
						finalPath = createImage(picTemplate, tagertPath, context, backGroupPic, jMagickImageBuilder);
					}
					tagertPath = finalPath;
				}
				logger.info("生成图片磁盘路径[{}]", tagertPath);
			}
			if(StringUtils.isEmpty(tagertPath)){
				return null;
			}
			return FileUtils.readFileToByteArray(new File(tagertPath));
		} catch (Exception e) {
			logger.info("生成图片失败", e);
		}
		return null;
	}

	private String createImage(PicTemplate picTemplate, String tagertPath, VelocityContext context, String backGroundPic, JMagickImageBuilder jMagickImageBuilder) {
		String imagePath = ImageUtil.combineImage(picTemplate, tagertPath, context, backGroundPic, velocityEngine, jMagickImageBuilder);
		
		if(StringUtils.isEmpty(imagePath)){
			//图片没有生成，不剪切
			return null;
		}
		// 在这里判断是否需要剪切
		CutInfo cutInfo = picTemplate.getCutInfo();
		if (cutInfo != null) {
			imagePath = ImageUtil.cutImage(imagePath, imagePath, cutInfo);
		}
		return imagePath;
	}

	private String getTagertPath(String picId) {
		return "/tmp/" + picId + ".png";
        //return "F:\\targetimg\\" + picId + ".png";
	}

}
