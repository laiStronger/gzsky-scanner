package com.wenwo.server.core;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerPipelineFactory {

	private ChannelHandler channelHandler;

	public HttpServerPipelineFactory(ChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}

	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());
		// http处理handler
		pipeline.addLast("handler", channelHandler);
		return pipeline;
	}
}
