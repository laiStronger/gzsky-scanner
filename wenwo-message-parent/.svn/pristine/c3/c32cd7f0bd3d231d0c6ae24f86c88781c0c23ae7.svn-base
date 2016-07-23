package com.wenwo.server.core;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidMessageServer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private ChannelFactory channelFactory;
	private ChannelPipelineFactory channelPipelineFactory;
	private InetSocketAddress address;

	public AndroidMessageServer(ChannelFactory channelFactory, ChannelPipelineFactory channelPipelineFactory, InetSocketAddress address) {
		super();
		this.channelFactory = channelFactory;
		this.channelPipelineFactory = channelPipelineFactory;
		this.address = address;
	}

	public void startup() {
		logger.debug("\r\n《==============Android消息服务器启动================》\r\n");
		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);

		bootstrap.setPipelineFactory(channelPipelineFactory);

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);

		logger.debug("\r\n绑定地址：" + address.toString());
		bootstrap.bind(address);
		logger.debug("\r\n《==============Android消息服务器启动完成================》\r\n");
	}
}
