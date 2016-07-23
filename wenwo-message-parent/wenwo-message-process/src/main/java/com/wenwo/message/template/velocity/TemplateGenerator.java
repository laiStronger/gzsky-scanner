package com.wenwo.message.template.velocity;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class TemplateGenerator {

	// private final VelocityEngine velocityEngine = new VelocityEngine();

	public String generatorTemplate(String content, VelocityContext velocityContext, VelocityEngine velocityEngine) {
		if(StringUtils.isEmpty(content)){
			return null;
		}
		Template template = velocityEngine.getTemplate(content);
		StringWriter sw = new StringWriter();
		template.merge(velocityContext, sw);
		String msg = sw.toString();
		return msg;
	}
}
