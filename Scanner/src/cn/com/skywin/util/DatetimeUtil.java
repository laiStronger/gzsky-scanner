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
	 * 判断当前时间是否是同步时间
	 * @return true:是同步时间段   false:不是同步时间段
	 */
	public static boolean IsRunningTime(String EveryDay)
	{
		boolean result = false;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
		
		//系统当前时间
		Date currentdate = new Date();
		
		//扫描日期字符串,格式如:2011-03-21
		String daystr=dateFormatDay.format(currentdate);
		
		//定时扫描的时间段
		Date begindateday = null;
		Date enddateday = null;
		
		try {
			int index = -1;
			
			//构造扫描时间段
			index = EveryDay.indexOf("-");
			begindateday = simpleDateFormat.parse(daystr+" "+EveryDay.substring(0,index).trim());
			enddateday = simpleDateFormat.parse(daystr+" "+EveryDay.substring(index+1).trim());
		
			//判断是否是扫描时间
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
