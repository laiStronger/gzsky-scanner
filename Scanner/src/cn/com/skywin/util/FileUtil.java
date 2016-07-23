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
	 * ���ɵ��ļ�����·��
	 */
	private String _srcpath;
	
	/*
	 * �����ļ�·��
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
	 * ����ǰĿ¼�����е��ļ����뱸��Ŀ¼
	 * ����Ŀ¼��ָ��ǰ·���µ�Backup�ļ���
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
	 * @param list ʵ����
	 * @param dictionary �ֵ�����,������SPID��֮��Ӧ�Ķ������غ���
	 * @return true:�ļ������ɹ�  false:�ļ�����ʧ��
	 */
	public boolean CreateFile(List<TransInfo> list,Dictionary<String,String> dictionary) {
		FilesBackup();
		
		boolean result = false;
		
		String filename= CreateFileName();
		logger.debug("File name="+filename);
		
		FileWriter fileWriter=null;
		PrintWriter printWriter= null;
		
		try {
			//���ļ�,������������ȴ����ļ�
			fileWriter=new FileWriter(_srcpath+filename);
			printWriter=new PrintWriter(fileWriter);
			
			//�������¼
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
	 * @return �����ļ�����
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
	 * �ж�Ҫ���ɵ��ļ��Ƿ����
	 * true:����  false:������
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
	 * @param info ���׼�¼ʵ��
	 * @param dictionary �ֵ�����,������SPID��֮��Ӧ�Ķ������غ���
	 * @return �����õ����¼
	 */
	private String CreateRecord(TransInfo info,Dictionary<String,String> dictionary) {
		StringBuilder sb=new StringBuilder();
		String temp=null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//������¼���
		sb.append("20");
		
		//���к�		
		//ֱ��ȡ��ˮ��,��֤Ψһ�Լ���,20λ
		temp=info.get_transeqno()+EmptySpace(info.get_transeqno().length(), 20);
		sb.append(temp);
		
		//�Ʒ��û�����
		sb.append(info.get_usermobileno());
		
		//SP����
		temp=info.get_spid()+EmptySpace(info.get_spid().length(), 20);
		sb.append(temp);
		
		//�������,�û�ʹ�ö���Ϣʱʹ�õķ����ṩ�̴���,24λ
		temp =(String) dictionary.get(info.get_spid());
		temp=temp+EmptySpace(temp.length(), 24);
		sb.append(temp);
		
		//ҵ�����,SP�Զ���ҵ�����,10λ
		temp=info.get_serviceid()+EmptySpace(info.get_serviceid().length(), 10);
		sb.append(temp);
		
		//�û��Ʒ����,04:ʹ�û���(���»���)
		sb.append("04");
		
		//����ʱ��
		sb.append(simpleDateFormat.format(info.get_spdate()));
		
		//�س� ����
		sb.append("\r");
		
		return sb.toString();
	}
	
	/**
	 * @param currentindex
	 * @param length
	 * @return ���հ�
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
