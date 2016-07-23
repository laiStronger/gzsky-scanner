package cn.com.skywin.thread;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.skywin.model.TransInfo;
import cn.com.skywin.oracle.OracleDao;
import cn.com.skywin.util.DatetimeUtil;
import cn.com.skywin.util.FileUtil;

/**
 * ����ɨ���߳�
 * 
 */
public class Scanner implements Runnable {

	static Logger logger = Logger.getLogger(Scanner.class);

	/*
	 * ������
	 */
	private static String ClassName;
	/*
	 * ���ݿ����Ӵ�
	 */
	private static String Url;
	/*
	 * �û���
	 */
	private static String UserName;
	/*
	 * ����
	 */
	private static String UserPwd;

	/*
	 * �����ļ���Ŀ¼
	 */
	private static String Directory_Data;

	/*
	 * �����ļ��ı���Ŀ¼
	 */
	private static String Directory_Backup;

	/*
	 * ���Ժ����������ļ���ͬ��ʱ��
	 */
	private static String EveryDay;

	/*
	 * �߳̽�����ѭʱÿ�����ߵ�ʱ��,��λ:����
	 */
	private static long SleepTimes;

	/*
	 * �߳�ִ�еı�ʶ
	 */
	private static boolean RunFlag = true;

	/*
	 * ��ѯ��־λ  0:��ѯ���еĽ��׼�¼������0Ԫ���׼�¼  1:ֻ��ѯ0Ԫ�Ľ��׼�¼  2:ֻ��ѯ������0�Ľ��׼�¼
	 */
	private static int Flag;

	/*
	 * ����SPID��������֮���ӳ���ϵ
	 */
	private static Dictionary<String, String> dictionary;

	/*
	 * Ҫ����ɨ���SPID,ÿ��SPID֮����;(�ֺ�)�ָ�
	 */
	private static String SPIDS;

	/**
	 * @param filepath
	 *            �����ļ�·��
	 * @return true:�������سɹ� false:��������ʧ��
	 */
	private boolean InitParams(String filepath) {
		// ��ȡ�����ļ�,ȷ�������ļ�·����ȷ
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filepath);
		} catch (FileNotFoundException e) {
			logger.debug("Execute InitParams() failed,FileNotFoundException="
					+ e.getMessage());
			return false;
		}

		// ��ȡһ������
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string = null;

		try {
			string = bufferedReader.readLine();
		} catch (IOException e) {
			logger.debug("Execute InitParams() failed,IOException="
					+ e.getMessage());
			return false;
		}

		// ��ȡ�����ݲ���
		while (string != null) {
			try {
				if (string.trim().length() > 0
						&& (!string.trim().startsWith("//"))) {
					int index = string.indexOf("=");
					if (index > -1) {
						String leftstr = string.substring(0, index).trim();
						String rightstr = string.substring(index + 1).trim();

						if (leftstr.equalsIgnoreCase("ClassName")) {
							ClassName = rightstr;
						} else if (leftstr.equalsIgnoreCase("Url")) {
							Url = rightstr;
						} else if (leftstr.equalsIgnoreCase("UserName")) {
							UserName = rightstr;
						} else if (leftstr.equalsIgnoreCase("UserPwd")) {
							UserPwd = rightstr;
						} else if (leftstr.equalsIgnoreCase("Directory_Data")) {
							Directory_Data = rightstr;
						} else if (leftstr.equalsIgnoreCase("Directory_BackUp")) {
							Directory_Backup = rightstr;
						} else if (leftstr.equalsIgnoreCase("EveryDay")) {
							EveryDay = rightstr;
						} else if (leftstr.equalsIgnoreCase("SleepTimes")) {
							// ��ʽ��:5*60*1000
							long temp = 1;
							int tempindex = rightstr.indexOf("*");
							while (tempindex > -1) {
								String tempstr = rightstr.substring(0,
										tempindex).trim();
								if (tempstr.length() > 0) {
									temp = temp * Integer.parseInt(tempstr);
								}
								rightstr = rightstr.substring(tempindex + 1)
										.trim();
								tempindex = rightstr.indexOf("*");
							}

							if (rightstr.length() > 0) {
								temp = temp * Integer.parseInt(rightstr);
							}

							SleepTimes = temp;
						} else if (leftstr.equalsIgnoreCase("SpIDs")) {
							dictionary = new Hashtable<String, String>();
							String tempstr = "";

							String[] array = rightstr.split(";");
							for (int i = 0; i < array.length; i++) {
								String str = array[i];
								String[] temparray = str.split(",");
								dictionary.put(temparray[0], temparray[1]);
								tempstr = tempstr + temparray[0] + ";";
							}
							// �������һ������(,)
							if (tempstr.length() > 0) {
								tempstr = tempstr.substring(0,
										tempstr.length() - 1);
							}
							SPIDS = tempstr;
						} else if (leftstr.equalsIgnoreCase("Flag")) {
							Flag=Integer.parseInt(rightstr);
						}
					}
				}

				string = bufferedReader.readLine();
			} catch (IOException e) {
				logger.debug("Execute InitParams() failed,IOException="
						+ e.getMessage());
				return false;
			}
		}

		return true;
	}

	/*
	 * �����ǰ������Ϣ
	 */
	private void PrintParams() {
		logger.debug("------------------- Start Config---------------------");
		logger.debug("ClassName=" + ClassName);
		logger.debug("Url=" + Url);
		logger.debug("UserName=" + UserName);
		logger.debug("UserPwd=" + UserPwd);
		logger.debug("Directory_Data=" + Directory_Data);
		logger.debug("Directory_Backup=" + Directory_Backup);

		logger.debug("EveryDay=" + EveryDay);
		logger.debug("SleepTimes=" + SleepTimes);
		logger.debug("RunFlag=" + RunFlag);
		logger.debug("SPIDS=" + SPIDS);
		logger.debug("Flag=" + Flag);
		logger.debug("-------------------End---------------------");
	}

	/*
	 * �߳���������
	 */
	public void run() {
		// �������ò���
		boolean state = InitParams("config.txt");
		if (state) {
			logger.debug("Load the config success.");
			PrintParams();
		} else {
			RunFlag = false;
			logger.debug("Load the config failed.");
		}

		// �ж��߳��Ƿ����ִ��
		while (RunFlag) {

			// �ж��Ƿ���ɨ��ʱ��
			if (DatetimeUtil.IsRunningTime(EveryDay)) {
				FileUtil fileUtil = new FileUtil(Directory_Data,Directory_Backup);
				if (!fileUtil.Exists()) {
					OracleDao dao = new OracleDao(ClassName, Url, UserName,UserPwd, Flag);
					List<TransInfo> list = dao.GetTransList(SPIDS);
					if (list.size() > 0) {
						boolean result = fileUtil.CreateFile(list, dictionary);
						if (result) {
							logger.debug("Fee file create success.");
						} else {
							logger.debug("Fee file create failed.");
						}
					} else {
						logger.debug("The result of scanner is empty.");
					}
				}
			}

			// ִ����֮���߳̽�����ݵ�˯��
			try {
				Thread.sleep(SleepTimes);
			} catch (InterruptedException e) {
				logger.debug("The method of Scanner running error,InterruptedException="
								+ e.getMessage());
			}
		}
	}

	/**
	 * �߳����
	 */
	public static void main(String[] args) {
		// �����߳�
		new Thread(new Scanner()).start();
	}
}
