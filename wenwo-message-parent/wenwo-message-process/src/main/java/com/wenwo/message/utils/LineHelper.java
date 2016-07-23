package com.wenwo.message.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public final class LineHelper {
	public static List<String> newLines(int lineWidth, int fontSize, String text,boolean delete) {
		List<String> lines = new ArrayList<String>();
		int lineCount = lineWidth / fontSize;
		text = text==null?"":text;
		if (text.length() > lineCount) {
			do {
				String line = subString(text,lineCount*2);
				text = text.substring(line.length());
				lines.add(line);
			} while (text.length() > lineCount);
		}
		lines.add(text);
		if(delete && lines.size() > 3){
			lines = lines.subList(0, 3);
		}
		return lines;
	}
	
	public static List<String> newLines(int lineWidth, int fontSize, String text,int showLineNum) {
		if(StringUtils.isEmpty(text)){
			return null;
		}
		List<String> lines = new ArrayList<String>();
		int lineCount = lineWidth / fontSize;
		if (text.length() > lineCount) {
			do {
				String line = subString(text,lineCount*2);
				text = text.substring(line.length());
				lines.add(line);
			} while (text.length() > lineCount);
		}
		lines.add(text);
		if(showLineNum > 0 && lines.size() > showLineNum){
			lines = lines.subList(0, showLineNum);
		}
		return lines;
	}

	public static void main(String[] args) {
		String text = "1960年提出调整、巩固、充实、提高的方针，并采取一系列措施，使国民经济顺利地得到恢复和发展。" + "还提出了中国知识分子绝大多数已经是劳动人民的知识分子";
		 List<String> lines = newLines(200, 14, text,false);
		 for (String line : lines) {
		 System.out.println(line);
		 }
	}

	public static String subString(String str, int len) {
		if (str == null || "".equals(str)) {
			return null;
		}
		// 将字符串中的char数组转换成指定编码方式的byte数组的函数
		byte[] strBytes = null;
		try {
			strBytes = str.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 得到字符串的长度，判断截取字符串的长度是否在判断的范围内，否则返回原串
		int strLen = strBytes.length;
		if (len >= strLen || len < 1) {
			return str;
		}
		int count = 0;
		for (int i = 0; i < len; i++) {
			// 将每个字节数组转换为整型数，以为后面根据值的正负来判断是否为汉字
			int value = strBytes[i];
//			System.out.print(value + ","); // 我ABC你 -50,-46,65,66,67,-60,-29
			// 对于第一种情况：
			// 注，一个函数转换成整型数就为两个负整数,上面的”我ABC你“，
			// 转换成整型数就为-50,-46,65,66,67,-60,-29，但是len=6，所以截取下来的就是-50,-46,65,66,67,-60,count就为3
			// 如果是汉字(负)，则统计截取字符串中的汉字所占字节数
			if (value < 0) {
				count++;
			}
		}
		// 依据判断给定的字符串是否含有汉字，利用String类的substring()方法来截取不同的长度

		// 根据所统计的字节数，判断截取到字符是否为半个汉字，奇数为半个汉字
		if (count % 2 != 0) {
			// 如果在截取长度为1时，则将该汉字取出，
			// 其他情况则不截取这里的截取长度则按字符长度截取（截取字节长度数-截取汉字字节数/2-截取到的半个汉字的字节数）
			len = (len == 1) ? len : len - count / 2 - 1; // len=6-3/2-1=4 我ABC
			// System.out.println("处理后的len="+len);
		} else {
			// 截取字符长度为字节长度-汉字所占字节长度/2（汉字占两个字节）
			len = len - (count / 2);
		}
		return str.substring(0, len);

	}
}