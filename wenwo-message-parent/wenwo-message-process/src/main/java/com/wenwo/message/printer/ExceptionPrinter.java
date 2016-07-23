package com.wenwo.message.printer;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wenwo.message.dao.IMessageErrorDao;
import com.wenwo.message.model.MessageError;

/**
 * 负责把异常信息记录到mongodb，方便查找问题
 * @author StanleyDing
 * @date 2013-9-20
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-20,	StanleyDing, 	Create
 */
public class ExceptionPrinter {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IMessageErrorDao messageErrorDao;
	
	public void printException(String targetUid, String msgId, String description, Throwable e) {
		//消息有可能是给批量用户发送的，其id格式是parentId_序号_总用户数
		int idx = msgId.indexOf('_');
		String parentMsgId = idx> 0 ? msgId.substring(0,idx): msgId;
		String errorStack = null;
		String errorMessage = null;
		if(e!=null) {
			errorMessage = e.getMessage();
			StringWriter stringWriter = new StringWriter(1024);
			PrintWriter writer = new PrintWriter(stringWriter);
			try {
				e.printStackTrace(writer);
				writer.flush();
				errorStack = stringWriter.toString();
			} catch (Exception e1) {
				logger.error("堆栈形成字符串失败", e);
			} finally {
				writer.close();
			}	
		}
		MessageError error = new MessageError(parentMsgId, msgId, targetUid, description, errorMessage, errorStack);
		messageErrorDao.saveMessageError(error);
	}
}
