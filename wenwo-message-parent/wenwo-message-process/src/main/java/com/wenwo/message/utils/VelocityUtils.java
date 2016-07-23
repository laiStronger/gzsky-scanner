/**
 * 
 */
package com.wenwo.message.utils;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author shuangtai
 *
 */
public class VelocityUtils
{
	public static String generatorTemplate(String content, VelocityContext velocityContext, VelocityEngine velocityEngine) {
		StringWriter sw = new StringWriter();
		velocityEngine.evaluate(velocityContext, sw, "", content);
		String msg = sw.toString();
		return msg;
	}
}
