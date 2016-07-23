/**
 * 
 */
package cn.com.skywin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class DatetimeUtil {
	
	static Logger logger=Logger.getLogger(DatetimeUtil.class);
	
	/*
	 * �жϵ�ǰʱ���Ƿ���ͬ��ʱ��
	 * @return true:��ͬ��ʱ���   false:����ͬ��ʱ���
	 */
	public static boolean IsRunningTime(String EveryDay)
	{
		boolean result = false;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
		
		//ϵͳ��ǰʱ��
		Date currentdate = new Date();
		
		//ɨ�������ַ���,��ʽ��:2011-03-21
		String daystr=dateFormatDay.format(currentdate);
		
		//��ʱɨ���ʱ���
		Date begindateday = null;
		Date enddateday = null;
		
		try {
			int index = -1;
			
			//����ɨ��ʱ���
			index = EveryDay.indexOf("-");
			begindateday = simpleDateFormat.parse(daystr+" "+EveryDay.substring(0,index).trim());
			enddateday = simpleDateFormat.parse(daystr+" "+EveryDay.substring(index+1).trim());
		
			//�ж��Ƿ���ɨ��ʱ��
			if (currentdate.compareTo(begindateday)>=0 && currentdate.compareTo(enddateday)<=0) {
				logger.debug("Start to scanner.");
				result = true;
			}
			else {
				result = false;
			}
		} catch (Exception e) {
			logger.debug("Execute IsRunningTime() error,Exception="+e.getMessage());
			result = false;
		}
		
		return result;
	}
}
