/**
 * @author acer
 * @date 2015-1-28
 */
package com.wenwo.message.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author acer
 *
 */
public class GetShortUrlUtil {

	public static String url="https://api.weibo.com/2/short_url/shorten.json?";
	public static String appkey="3174813925";
	
	public static String doGet(String urlNameString,Map<String, String> parameters) throws MalformedURLException {
		//String result="";
		//BufferedReader in=null;
		String responseContent = null;
		
		StringBuffer params = new StringBuffer();
		for(Iterator<?> iter=parameters.entrySet().iterator();iter.hasNext();){
			Entry<?, ?> element = (Entry<?, ?>) iter.next();
			if(element.getKey().equals("url_long")){
			params.append(element.getKey().toString());
			params.append("=");
			params.append(URLEncoder.encode(element.getValue().toString()));
			params.append("&");
			}else{
				params.append(element.getKey().toString());
				params.append("=");
				params.append(element.getValue().toString());
				params.append("&");
			}
		}
	    
		if (params.length() > 0) {
			params = params.deleteCharAt(params.length() - 1);
		}
		
		String urlName=urlNameString+params.toString();
		
		try {
			URL realUrl=new URL(urlName);
			
			//HttpUrlConnection connection=realUrl.openConnection();
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			//connection.setConnectTimeout(PhoneTest.connectTimeOut);
	        //connection.setReadTimeout(PhoneTest.readTimeOut);
	        connection.setDoOutput(true);
			InputStream in = connection.getInputStream();
			byte[] echo = new byte[10 * 1024];
			int len = in.read(echo);
			responseContent = (new String(echo, 0, len)).trim();
	       // byte[] b = params.toString().getBytes();
	        //InputStream in = connection.getInputStream();
	        //connection.connect();
	         // 定义 BufferedReader输入流来读取URL的响应
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 
		}

		return responseContent;
	}
}
