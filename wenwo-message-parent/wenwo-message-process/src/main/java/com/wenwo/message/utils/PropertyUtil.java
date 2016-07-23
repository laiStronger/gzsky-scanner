package com.wenwo.message.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	private static Properties config = null;

	 static {
		  InputStream in = XingeAppUtil.class.getClassLoader().getResourceAsStream("wenwo-config.properties");
		  config = new Properties();
		  try {
		     config.load(in);
		     in.close();
		  } catch (IOException e) {
		     System.out.println("No properties defined error!");
		  }
	 }
	 
	 public static String readValue(String key) {
		  try {
		     String value = config.getProperty(key);
		     return value;
		  } catch (Exception e) {
		     e.printStackTrace();
		     logger.info("readValue读取参数错误：" + e.toString());
		     return null;
		  }
	 }

}
