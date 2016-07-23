package com.wenwo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AndroidServerStart {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("classpath:context-*.xml");
		System.out.println("===================start OK");
	}
}
