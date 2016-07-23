package com.wenwo.server.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.wenwo.message.api.IMessageService;
import com.wenwo.platform.types.WeiboType;
import com.wenwo.platform.types.project.SubprojectType;

public class HttpServerChannelHandler extends SimpleChannelUpstreamHandler {
	private Logger logger = LoggerFactory.getLogger(HttpServerChannelHandler.class);
	
	private IMessageService messageService;

	private static Gson gson = new Gson();
	
	private HttpServerChannelHandler() {
	}

	// http://gpn.sys.wenwo.com:8484/android/uid=501b4a69e4b037ebd180a855&c=AppType.name()&weiboType=SINA
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		HttpRequest request = (HttpRequest) e.getMessage();
		String uri = request.getUri();
		logger.info(uri);
		if (StringUtils.contains(uri, "android/uid")) {
			SubprojectType subprojectType = getProjectType(getQueryParam(uri, "weiboType"));
			String uid = getQueryParam(uri, "uid");
			logger.info("数据ID:{}-{}" ,subprojectType, uid);
			List<Object> pushMessageList = messageService.getAndroidPushMessage(uid, subprojectType);
			
			AndroidMessageReturn result = new AndroidMessageReturn(pushMessageList);
//			data:[]
			response(e, gson.toJson(result));
		} else {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
		}
	}
	
	public static class AndroidMessageReturn{
		private List<Object> data;
		
		public AndroidMessageReturn(List<Object> messageList){
			this.setData(messageList);
		}

		public List<Object> getData() {
			return data;
		}

		public void setData(List<Object> data) {
			this.data = data;
		}
	
		
	}


	private void response(MessageEvent e, String json) throws IOException {
		logger.info("返回数据：" + json);
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		ChannelBuffer buffer = new DynamicChannelBuffer(2048);
		buffer.writeBytes(json.getBytes(Charset.defaultCharset()));
		response.setContent(buffer);
		Channel ch = e.getChannel();
		ch.write(response);
		ch.disconnect();
		ch.close();
	}
	
	/**
	 * 
	 * @author matti
	 * @Description: AppType depend on weiboType,  default->AppType.wenwo for美食V推荐
	 * @param weiboType
	 * @return
	 */
	private SubprojectType getProjectType(String weiboType){
		SubprojectType subprojectType = SubprojectType.WENWO;
		if(WeiboType.SINA.name().equals(weiboType)){
			subprojectType = SubprojectType.WEIWEN;
		}else if(WeiboType.QQ.name().equals(weiboType)){
			subprojectType = SubprojectType.WEIWENQQ;
		}
		return subprojectType;
	}
	
	/**
	 * 
	 * @author matti
	 * @Description: only for the bad-format url ,no need to encode now
	 * @param url
	 * @return
	 */
	private String getQueryParam(String url,String key){

        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(key)) {
            return null;
        }

        String prefix = key + "=";

        if (url.length() < prefix.length()) {
            return null;
        }

        int start = url.indexOf(prefix);
        if(start == -1){
        	// Not found.
            return null;
        }else{
        	start = start+prefix.length();
        }

        // Find end of value.
        int end = url.indexOf('&', start);
        if (end == -1) {
            end = url.length();
        }

        return url.substring(start, end);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		Channel ch = e.getChannel();
		Throwable cause = e.getCause();
		if (cause instanceof TooLongFrameException) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		cause.printStackTrace();
		if (ch.isConnected()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
		response.setHeader("Content-Type", "text/plain; charset=UTF-8");
		response.setContent(ChannelBuffers.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
	}
	
}
