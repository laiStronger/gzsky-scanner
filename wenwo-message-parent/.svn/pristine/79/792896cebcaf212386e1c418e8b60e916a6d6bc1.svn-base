package com.wenwo.message.utils;

import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

	/**
	 * 获取上周日期
	 * @param date
	 * @return
	 */
	public static Date getLastWeek(Date date) {		
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(date);		
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		date = calendar.getTime();		
		return date;	
	}
	
	public static void main(String args[]) {
		getLastWeek(new Date());
	}

}
