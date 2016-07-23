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
 * 定期扫描线程
 * 
 */
public class Scanner implements Runnable {

	static Logger logger = Logger.getLogger(Scanner.class);

	/*
	 * 驱动类
	 */
	private static String ClassName;
	/*
	 * 数据库连接串
	 */
	private static String Url;
	/*
	 * 用户名
	 */
	private static String UserName;
	/*
	 * 密码
	 */
	private static String UserPwd;

	/*
	 * 话单文件的目录
	 */
	private static String Directory_Data;

	/*
	 * 话单文件的备份目录
	 */
	private static String Directory_Backup;

	/*
	 * 测试号码日增量文件的同步时间
	 */
	private static String EveryDay;

	/*
	 * 线程进行轮循时每次休眠的时间,单位:毫秒
	 */
	private static long SleepTimes;

	/*
	 * 线程执行的标识
	 */
	private static boolean RunFlag = true;

	/*
	 * 查询标志位  0:查询所有的交易记录，包括0元交易记录  1:只查询0元的交易记录  2:只查询金额大于0的交易记录
	 */
	private static int Flag;

	/*
	 * 保存SPID与服务代码之间的映射关系
	 */
	private static Dictionary<String, String> dictionary;

	/*
	 * 要进行扫描的SPID,每个SPID之间用;(分号)分隔
	 */
	private static String SPIDS;

	/**
	 * @param filepath
	 *            配置文件路径
	 * @return true:参数加载成功 false:参数加载失败
	 */
	private boolean InitParams(String filepath) {
		// 读取配置文件,确保配置文件路径正确
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filepath);
		} catch (FileNotFoundException e) {
			logger.debug("Execute InitParams() failed,FileNotFoundException="
					+ e.getMessage());
			return false;
		}

		// 读取一行数据
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string = null;

		try {
			string = bufferedReader.readLine();
		} catch (IOException e) {
			logger.debug("Execute InitParams() failed,IOException="
					+ e.getMessage());
			return false;
		}

		// 读取的内容不空
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
							// 格式如:5*60*1000
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
							// 过滤最后一个逗号(,)
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
	 * 输出当前配置信息
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
	 * 线程启动函数
	 */
	public void run() {
		// 加载配置参数
		boolean state = InitParams("config.txt");
		if (state) {
			logger.debug("Load the config success.");
			PrintParams();
		} else {
			RunFlag = false;
			logger.debug("Load the config failed.");
		}

		// 判断线程是否继续执行
		while (RunFlag) {

			// 判断是否是扫描时间
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

			// 执行完之后线程进入短暂的睡眠
			try {
				Thread.sleep(SleepTimes);
			} catch (InterruptedException e) {
				logger.debug("The method of Scanner running error,InterruptedException="
								+ e.getMessage());
			}
		}
	}

	/**
	 * 线程入口
	 */
	public static void main(String[] args) {
		// 启动线程
		new Thread(new Scanner()).start();
	}
}
