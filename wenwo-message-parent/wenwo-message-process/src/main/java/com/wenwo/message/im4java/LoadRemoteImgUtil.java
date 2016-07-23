package com.wenwo.message.im4java;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoadRemoteImgUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadRemoteImgUtil.class);
	
	private static final String CACHE_IMG_PATH = "/tmp/%s";
	private static ThreadSafeClientConnManager connectionManager = null;
	private static HttpClient httpClient = null;
	static{
		connectionManager = new ThreadSafeClientConnManager();
		connectionManager.setDefaultMaxPerRoute(50);
		connectionManager.setMaxTotal(20);
		httpClient = new DefaultHttpClient(connectionManager);
	}
	
	public static void main(String[] args) {
		LoadRemoteImgUtil.loadRemoteImg("http://pic.wenwo.com/fimg/77648151307_240.jpg");
	}
	
	private static final Pattern REMOTE_IMG_URL_PATTERN = Pattern.compile("http:.+\\.(png|jpg)\\.*");
	public static String loadRemoteImg(String url){
		Matcher match = REMOTE_IMG_URL_PATTERN.matcher(url);
		if(!match.matches()){
			return url;
		}
		
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		String absolut_path = String.format(CACHE_IMG_PATH, fileName);
		File file = new File(absolut_path);
		if(file.exists()){
			return absolut_path;
		}
		
		HttpGet get = new HttpGet(url);
		BufferedOutputStream out = null;
		InputStream inputStream = null;
		try {
			HttpResponse response = httpClient.execute(get);
			
			if(response.getStatusLine().getStatusCode() != 200 || !response.getEntity().getContentType().getValue().contains("image")){
				return null;
			}
			inputStream = response.getEntity().getContent();
			out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[1024];
			int readLength = 0;
			while((readLength=inputStream.read(buffer)) > 0){
				out.write(buffer,0,readLength);
				buffer = new byte[1024];
			}
			out.flush();
			logger.info("load remote {} to local {} success", url, absolut_path);
			return absolut_path;
		} catch (Throwable e) {
			logger.warn("加载远程图片{}失败", absolut_path, e);
		} finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(out);
		}
		return null;
	}
}
