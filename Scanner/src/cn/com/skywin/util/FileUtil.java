package cn.com.skywin.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.skywin.model.TransInfo;

public class FileUtil {
	
	static Logger logger=Logger.getLogger(FileUtil.class);
	
	/*
	 * 生成的文件保存路径
	 */
	private String _srcpath;
	
	/*
	 * 备份文件路径
	 */
	private String _destpath;
	
	/**
	 * @param filepath
	 * @param isdebug
	 */
	public FileUtil(String srcpath,String destpath) {
		this._srcpath = srcpath;
		this._destpath = destpath;
	}
	
	
	/**
	 * 将当前目录下所有的文件移入备份目录
	 * 备份目录是指当前路径下的Backup文件夹
	 */
	private void FilesBackup() {
		boolean ret=false;
		
		File file=new File(_srcpath);
		File[]files=file.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile() && files[i].getName().startsWith("SJGC")) {
				File destfile=new File(_destpath+files[i].getName());
				ret = files[i].renameTo(destfile);
				logger.debug("Backup file="+files[i].getName()+",result="+ret);
			}
		}
		
		logger.debug("Execute FilesBackup() success.");
	}
	
	/**
	 * @param list 实体类
	 * @param dictionary 字典数据,关联了SPID与之对应的短信网关号码
	 * @return true:文件创建成功  false:文件创建失败
	 */
	public boolean CreateFile(List<TransInfo> list,Dictionary<String,String> dictionary) {
		FilesBackup();
		
		boolean result = false;
		
		String filename= CreateFileName();
		logger.debug("File name="+filename);
		
		FileWriter fileWriter=null;
		PrintWriter printWriter= null;
		
		try {
			//打开文件,如果不存在则先创建文件
			fileWriter=new FileWriter(_srcpath+filename);
			printWriter=new PrintWriter(fileWriter);
			
			//生成体记录
			for (int i = 0; i < list.size(); i++) {
				TransInfo transInfo=(TransInfo)list.get(i);
				printWriter.println(CreateRecord(transInfo,dictionary));
			}
		
			printWriter.flush();
			result=true;
			
		} catch (Exception e) {
			logger.debug("Execute CreateFile() error,Exception="+e.getMessage());
		}
		finally
		{			
			try {
				if (printWriter!=null) {
					printWriter.close();
				}
				if (fileWriter!=null) {
					fileWriter.close();
				}
			} catch (IOException e) {
				logger.debug("Close fileWriter error,IOException="+e.getMessage());
			}
		}
		
		logger.debug("Execute CreateFile() End,result="+result);
		return result;
	}
	
	
	/**
	 * @return 创建文件名称
	 */
	private String CreateFileName() {
		String result = "SJGC";
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String temp = simpleDateFormat.format(new Date());
		
		result=result+temp.substring(0,8)+".0";
		result=result+temp.substring(8,10);
		
		return result;
	}
	
	/*
	 * 判断要生成的文件是否存在
	 * true:存在  false:不存在
	 */
	public boolean Exists() {
		boolean result=false;
		String filename=CreateFileName();
		
		File file=new File(_srcpath+filename);
		if (file.exists()) {
			result=true;
			
			logger.debug("Execute Exists(),file already exist.");
		}
		
		return result;
	}
	
	
	/**
	 * @param info 交易记录实体
	 * @param dictionary 字典数据,关联了SPID与之对应的短信网关号码
	 * @return 创建好的体记录
	 */
	private String CreateRecord(TransInfo info,Dictionary<String,String> dictionary) {
		StringBuilder sb=new StringBuilder();
		String temp=null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//话单记录标记
		sb.append("20");
		
		//序列号		
		//直接取流水号,保证唯一性即可,20位
		temp=info.get_transeqno()+EmptySpace(info.get_transeqno().length(), 20);
		sb.append(temp);
		
		//计费用户号码
		sb.append(info.get_usermobileno());
		
		//SP代码
		temp=info.get_spid()+EmptySpace(info.get_spid().length(), 20);
		sb.append(temp);
		
		//服务代码,用户使用短消息时使用的服务提供商代码,24位
		temp =(String) dictionary.get(info.get_spid());
		temp=temp+EmptySpace(temp.length(), 24);
		sb.append(temp);
		
		//业务代码,SP自定的业务代码,10位
		temp=info.get_serviceid()+EmptySpace(info.get_serviceid().length(), 10);
		sb.append(temp);
		
		//用户计费类别,04:使用话单(包月话单)
		sb.append("04");
		
		//申请时间
		sb.append(simpleDateFormat.format(info.get_spdate()));
		
		//回车 换行
		sb.append("\r");
		
		return sb.toString();
	}
	
	/**
	 * @param currentindex
	 * @param length
	 * @return 补空白
	 */
	private String EmptySpace(int currentindex,int length) {
		String str="";
		if (currentindex>=length) {
			return str;
		}
		
		int sub = length-currentindex;
		for (int i = 0; i < sub; i++) {
			str=str+" ";
		}
		
		return str;
	}
}
