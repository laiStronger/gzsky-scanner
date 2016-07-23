/**
 * 
 */
package com.wenwo.main;

import java.io.IOException;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shuangtai
 * 消息系统接口实现
 */
public class MessageServiceStart {
	public static void main(String[] args) throws IOException {
		Logger logger = LoggerFactory.getLogger(MessageServiceStart.class);
		ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext(new String[]{"classpath:/context-*.xml"}); 
		NioSocketAcceptor acceptor = (NioSocketAcceptor)cpxac.getBean("minaAcceptor");
		acceptor.bind();
		logger.debug("Message system startup:OK");
		System.out.println("Message system startup:OK");
	}
}
