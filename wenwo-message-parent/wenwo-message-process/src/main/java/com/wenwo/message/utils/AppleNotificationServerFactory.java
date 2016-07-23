package com.wenwo.message.utils;

import java.io.IOException;
import java.io.InputStream;

import javapns.communication.exceptions.KeystoreException;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppleNotificationServerFactory {
	private static Logger logger = LoggerFactory.getLogger(AppleNotificationServerFactory.class);

	private final CertInfo devCerts;

	private final CertInfo productCerts;

	private AppleNotificationServer devAppleNotificationServer;

	private AppleNotificationServer prodevAppleNotificationServer;

	public AppleNotificationServerFactory(CertInfo devInfos, CertInfo productCerts) {
		super();
		this.devCerts = devInfos;
		this.productCerts = productCerts;
		try {
			devAppleNotificationServer = new AppleNotificationServerBasicImpl(getData(devCerts),
					devCerts.getPassword(), false);

			prodevAppleNotificationServer = new AppleNotificationServerBasicImpl(getData(productCerts),
					productCerts.getPassword(), true);
		} catch (IOException e) {
			logger.info("读取文件证书文件错误", e);
		} catch (KeystoreException e) {
			logger.info("创建 AppleNotificationServerBasicImpl 错误", e);
		}
	}

	public byte[] getData(CertInfo certInfo) throws IOException {
		String fileile = certInfo.getFile();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileile);
		byte[] data = IOUtils.toByteArray(in);
		return data;
	}

	public AppleNotificationServer getCert(boolean serviceFlag) {
		if (serviceFlag)
			return prodevAppleNotificationServer;
		else
			return devAppleNotificationServer;
	}

	public CertInfo getProductCerts() {
		return productCerts;
	}

	public static class CertInfo {
		private final String file;
		private final String password;

		public CertInfo(String file, String password) {
			super();
			this.file = file;
			this.password = password;
		}

		public String getFile() {
			return file;
		}

		public String getPassword() {
			return password;
		}

	}

}
