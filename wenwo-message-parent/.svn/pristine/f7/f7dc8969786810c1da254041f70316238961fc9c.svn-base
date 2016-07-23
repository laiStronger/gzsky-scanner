/**
 * 
 */
package com.wenwo.message.config;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rits.cloning.Cloner;
import com.wenwo.message.enums.EndpointType;
import com.wenwo.message.enums.MessageProjectType;
import com.wenwo.message.model.MessageType;
import com.wenwo.message.model.MessageType.TemplateInfo;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.PicTemplate.ImageInfo;
import com.wenwo.message.model.PicTemplate.TextInfo;
import com.wenwo.message.model.TextTemplate;
import com.wenwo.message.model.Variable;
import com.wenwo.message.utils.WenwoCollectionUtils;
import com.wenwo.platform.utils.CheckUtils;

/**
 * 用于对模拟进行替换的工具类，把用户配置的模版转化成velocity标准模版
 * @author StanleyDing
 * @date 2013-9-27
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-27,	StanleyDing, 	Create
 */
public class TemplateReplacer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 每个项目对应有自己的变量表，等于是项目个性化变量和系统标准变量的并集，如有变量名相等的情况，项目个性化变量优先。
	 * 如果项目下无个性化变量，那么在这个Map里面就没有这个项目的变量表，进行替换时，直接取标准化变量即可。
	 */
	private Map<MessageProjectType, List<Variable>> projectVariableMap;
	private Map<String, TextTemplate> textTemplateMap;
	Map<String, PicTemplate> picTemplateMap;
	private Cloner cloner = new Cloner();
	
	private static String VARIABLE_WITH_LIMIT_PATTERN = "\\{%s\\((\\d+)\\)\\}";
	
	public TemplateReplacer(List<Variable> variables, List<TextTemplate> textTemplates, List<PicTemplate> picTemplates) {
		projectVariableMap = new EnumMap<MessageProjectType, List<Variable>>(MessageProjectType.class);
		for(Variable variable:variables) {
			WenwoCollectionUtils.addToMappedList(projectVariableMap, variable.getVariableProjectType(), variable);
		}
		for(MessageProjectType messageProjectType : projectVariableMap.keySet()) {
			if(messageProjectType!=MessageProjectType.DEFAULT) {
				List<Variable> projectVariables = projectVariableMap.get(messageProjectType);
				List<Variable> defaultVariables = projectVariableMap.get(MessageProjectType.DEFAULT);
				List<Variable> totalVariables = WenwoCollectionUtils.mergeList(projectVariables, defaultVariables, "name");
				projectVariableMap.put(messageProjectType, totalVariables);
			}
		}
		
		textTemplateMap = WenwoCollectionUtils.asMap(textTemplates, String.class, TextTemplate.class, "getId");
		
		picTemplateMap = WenwoCollectionUtils.asMap(picTemplates, String.class, PicTemplate.class, "getId");
	}
	
	public String replaceText(String templateText, MessageProjectType messageProjectType) {
		
		if(StringUtils.isEmpty(templateText)){
			return null;
		}
		
		List<Variable> variables = projectVariableMap.get(messageProjectType);
		
		logger.info("projectType:{}", messageProjectType);
		if(variables==null) {
			variables = projectVariableMap.get(MessageProjectType.DEFAULT);
		}
		
		for(Variable variable : variables) {
			String velocityExpress = variable.getVelocityExpress().replace("$", "\\$");
			String variableName = variable.getName();
			
			templateText = replaceVariableWithLimit(variableName, velocityExpress, templateText);
			templateText = templateText.replaceAll("\\{"+variable.getName()+"\\}", velocityExpress);
//			templateText = templateText.replaceAll("\\{"+variable.getName()+"\\}", "\\$!{targetUserAtName}");
			int idx1 = templateText.indexOf('{');
			int idx2 = templateText.indexOf('}', idx1);
			if(idx1<0 || idx2<0) {
				break;//字符串中不存在变量，终止替换过程
			} 
		}
		return templateText;
	}
	
	/**
	 * @param variableName
	 * @param templateText
	 * @return
	 */
	private static final String VELOCITY_TRIM_FORMAT = "#textLimit(%s,%s)";
	private String replaceVariableWithLimit(String variableName, String velocityExpress ,String templateText) {
		if(StringUtils.isEmpty(variableName) || StringUtils.isEmpty(templateText)){
			return templateText;
		}
		
		String newTemplateText = templateText;
		String regex_pattern = String.format(VARIABLE_WITH_LIMIT_PATTERN, variableName);
		
		Matcher matcher_with_limit = Pattern.compile(regex_pattern).matcher(templateText);
		while(matcher_with_limit.find()){
			String matched_variable = matcher_with_limit.group(0);
			matched_variable = matched_variable.replace("{", "\\{");
			matched_variable = matched_variable.replace("}", "\\}");
			matched_variable = matched_variable.replace("(", "\\(");
			matched_variable = matched_variable.replace(")", "\\)");
			String limit_num = matcher_with_limit.group(1);
			//#trim(velocityExpress,limitnum)
			String velocityExpressWithLimit = String.format(VELOCITY_TRIM_FORMAT, velocityExpress, limit_num);
			newTemplateText = newTemplateText.replaceAll(matched_variable, velocityExpressWithLimit);
		}
		return newTemplateText;
	}

	public TextTemplate replateTextTemplate(String templateId, MessageProjectType messageProjectType) {
		if(textTemplateMap == null || !textTemplateMap.containsKey(templateId)){
			return null;
		}
		TextTemplate template = cloner.deepClone(textTemplateMap.get(templateId));
		template.setText(replaceText(template.getText(), messageProjectType));
		logger.info("文字模版替换完成,id={}, content={}", template.getId(), template);
		return template;
	}
	
	public PicTemplate replateImageTemplate(String templateId, MessageProjectType messageProjectType) {
		if(this.picTemplateMap == null || !this.picTemplateMap.containsKey(templateId)){
			return null;
		}
		PicTemplate template = cloner.deepClone(this.picTemplateMap.get(templateId));
		
		if(!CheckUtils.isEmptyCollection(template.getImageInfos())){
			for(ImageInfo imageInfo:template.getImageInfos()) {
				String url = imageInfo.getUrl();
				url = replaceText(url, messageProjectType);
				imageInfo.setUrl(url);
			}
		}
		
		if(!CheckUtils.isEmptyCollection(template.getTextInfos())){
			for(TextInfo textInfo:template.getTextInfos()) {
				String text = textInfo.getText();
				text = replaceText(text, messageProjectType);
				textInfo.setText(text);
			}
		}
		
		logger.info("图片模版替换完成,id={}, content={}", template.getId(), template);
		return template;
	}
	
	
	public TextTemplate replateTextTemplate(TemplateInfo templateInfo, EndpointType endpointType, MessageProjectType messageProjectType, MessageType messageType) {
		if(templateInfo==null || !templateInfo.getIsNeed() ) {
			return null;
		}
		if(templateInfo.getTextTemplateId()==null) {
			logger.error("没有配置模板，project={}，messagetType={}，endPoint={}",messageProjectType,messageType.getTypeName(), endpointType.getTypeName());
			return null;
		}
		TextTemplate template = textTemplateMap.get(templateInfo.getTextTemplateId());
		template = cloner.deepClone(template);
		template.setText(replaceText(template.getText(), messageProjectType));
		logger.info("文字模版替换完成,id={}, content={}", template.getId(), template);
		return template;
	}
	
	public PicTemplate replateImageTemplate(TemplateInfo templateInfo, EndpointType endpointType, MessageProjectType messageProjectType) {
		if(templateInfo==null || !templateInfo.getIsNeed() ) {
			return null;
		}
		if(templateInfo.getPicTemplateId()==null) {
			return null;
		}
		
		PicTemplate template = picTemplateMap.get(templateInfo.getPicTemplateId());
		template = cloner.deepClone(template);
		for(ImageInfo imageInfo:template.getImageInfos()) {
			String url = imageInfo.getUrl();
			url = replaceText(url, messageProjectType);
			imageInfo.setUrl(url);
		}
		
		for(TextInfo textInfo:template.getTextInfos()) {
			String text = textInfo.getText();
			text = replaceText(text, messageProjectType);
			textInfo.setText(text);
		}
		
		logger.info("图片模版替换完成,id={}, content={}", template.getId(), template);
		return template;
	}
	
	public boolean hasPrivateVariables(MessageProjectType messageProjectType) {
		return projectVariableMap.get(messageProjectType)!=null;
	}

	public Map<MessageProjectType, List<Variable>> getProjectVariableMap() {
		return projectVariableMap;
	}

	public void setProjectVariableMap(
			Map<MessageProjectType, List<Variable>> projectVariableMap) {
		this.projectVariableMap = projectVariableMap;
	}

	public Map<String, TextTemplate> getTextTemplateMap() {
		return textTemplateMap;
	}

	public void setTextTemplateMap(Map<String, TextTemplate> textTemplateMap) {
		this.textTemplateMap = textTemplateMap;
	}

	public Map<String, PicTemplate> getPicTemplateMap() {
		return picTemplateMap;
	}

	public void setPicTemplateMap(Map<String, PicTemplate> picTemplateMap) {
		this.picTemplateMap = picTemplateMap;
	}
}
