/**
 * 
 */
package com.wenwo.main;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shuangtai
 * 消息监听，消息的处理过程
 */
public class MessageSysStart {
	public static void main(String[] args) throws IOException {
		Logger logger = LoggerFactory.getLogger(MessageSysStart.class);
		@SuppressWarnings({ "unused" })
		ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext(new String[]{"classpath:/context-*.xml"}); 
		logger.debug("Message listener startup:OK");
		System.out.println("Message listener startup:OK");
	}
}
