package com.wenwo.message.template.velocity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stanleyding at 2012-4-12 description: 加载 classpath:消息模板.xlsx 模版资源到内存
 * 
 * <pre>
 * </pre>
 * 
 *         Usage: getTemplate("newAnswer.weibo.1.defaultQuestion.vm");
 * 
 * <pre>
 * </pre>
 */
public class TemplateManagerSourceLoader extends ResourceLoader {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String encoding;
	
	@Override
	public long getLastModified(Resource resource) {
		return 0L;
	}

	@Override
	public InputStream getResourceStream(String source) {
		try {
			return new ByteArrayInputStream(source.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			logger.info("模版资源有误", e);
		}
		return null;
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		return false;
	}
	
	@Override
	public boolean isCachingOn() {
		return false;
	}

	@Override
	public void init(ExtendedProperties configuration) {
		logger.info("I do nothing.....");
		encoding = this.rsvc.getString("input.encoding");
	}
}
