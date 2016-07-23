package cn.com.skywin.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.skywin.model.TransInfo;

public class OracleDao {
	
	static Logger logger=Logger.getLogger(OracleDao.class);
	
	/*
	 * 驱动类
	 */
	private String _classname;
	/*
	 * 数据库连接串
	 */
	private String _url;
	/*
	 * 用户名
	 */
	private String _username;
	/*
	 * 密码
	 */
	private String _userpwd;
	
	/*
	 * 查询标志位  0:查询所有的交易记录，包括0元交易记录  1:只查询0元的交易记录  2:只查询金额大于0的交易记录
	 */
	private int _flag=0;
	
	/**
	 * @param classname
	 * @param url
	 * @param username
	 * @param userpwd
	 * @param isdebug
	 * @param flag
	 */
	public OracleDao(String classname,String url,String username,String userpwd,int flag) {
		this._classname = classname;
		this._url = url;
		this._username = username;
		this._userpwd = userpwd;
		this._flag=flag;
	}
	
	/*
	 * 获取一个连接对象
	 */
	private Connection GetConnection() {
		try {
			Class.forName(_classname);
		} catch (ClassNotFoundException e) {
			logger.debug("Execute GetConnection() error, ClassNotFoundException="+e.getMessage());
			return null;
		}
		
		try {
			return DriverManager.getConnection(_url,_username, _userpwd);
		} catch (SQLException e) {
			logger.debug("Execute GetConnection() error, SQLException="+e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * @param spids 要进行扫描的SPID,每个SPID之间用;(分号)分隔,如654321;980001;950637
	 * @return 满足条件的所有的交易记录信息
	 */
	public List<TransInfo> GetTransList(String spids) {
		List<TransInfo> list= new ArrayList<TransInfo>();
		
		Connection conn = GetConnection();
		if (conn==null) {
			logger.debug("Create Connection Failed.");
			return list;
		}
		
		logger.debug("Execute GetTransList(),Spids="+spids);	
		
		//获取SPID
		String tempids = "";
		if (spids!=null && spids.length()>0) {
			String []temp = spids.split(";");
			for (int i = 0; i < temp.length; i++) {
				String string=temp[i];
				tempids = tempids+"'"+string+"',";
			}
		}
		//过滤最后一个单引号(,)
		if (tempids.length()>0) {
			tempids = tempids.substring(0,tempids.length()-1);
		}
		
		//天数减1,获得昨天的日期
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String MaxValue = simpleDateFormat.format(calendar.getTime())+ "235959";
		String MinValue = simpleDateFormat.format(calendar.getTime())+ "000000";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//构造查询的SQL语句
		StringBuilder sb = new StringBuilder();
		sb.append(" select TRADEDATE,TRADESEQNO,SERVICEID,TRADETYPE,PAYTYPE,");
		sb.append(" USERMOBILENO,USERACCOUNT,BANKDATE,BANKID,SPDATE,");
		sb.append(" SPID,SPORDERNO,MSID,MSACCOUNT,TRADEFLAG,");
		sb.append(" TRADEDWFLAG,TRADEACCREDITFLAG,TRADESTATE,TRADEOLDDATE,TRADEOLDSEQNO,");
		sb.append(" BATCHFLAG,SMSRES,TRADEDES,OPERATORID");
		sb.append(" from MPS_TXN_LOG");

		sb.append(" where TRADEDATE between TO_DATE('"+MinValue+"','yyyy-MM-dd HH24:MI:SS') and TO_DATE('"+MaxValue+"','yyyy-MM-dd HH24:MI:SS') ");
		sb.append(" and TRADEFLAG=0 ");
		
		//查询标志位  
		//0:查询所有的交易记录，包括0元交易记录  
		//1:只查询0元的交易记录  
		//2:只查询金额大于0的交易记录
		if (_flag==1) {
			sb.append(" and TradeAmount=0 ");
		}
		else if (_flag==2) {
			sb.append(" and TradeAmount>0 ");
		}
		
		//如果SPID不空,则查相应的SPID的记录,否则查询全部交易记录
		if (tempids.length()>0) {
			sb.append(" and SPID in("+tempids+")");
		}
		sb.append(" order by TRADEDATE asc");

		logger.debug("Execute SQL="+sb.toString());
		
		try {
			ps = conn.prepareStatement(sb.toString());
			rs=ps.executeQuery();
			
			TransInfo info = null;
			while (rs.next()) {
				info= new TransInfo();
				
				info.set_trandate(rs.getDate(1));
				info.set_transeqno(rs.getString(2));	
				info.set_serviceid(rs.getString(3));
				info.set_trantype(rs.getString(4));
				info.set_paytype(rs.getInt(5));
				
				info.set_usermobileno(rs.getString(6));
				info.set_useraccount(rs.getString(7));		
				info.set_bankdate(rs.getDate(8));
				info.set_bankid(rs.getString(9));
				info.set_spdate(rs.getDate(10));
				
				info.set_spid(rs.getString(11));
				info.set_sporderno(rs.getString(12));
				info.set_msid(rs.getString(13));
				info.set_msaccount(rs.getString(14));
				info.set_tranflag(rs.getInt(15));
				
				info.set_trandwflag(rs.getString(16));
				info.set_tranaccreditflag(rs.getInt(17));
				info.set_transtate(rs.getInt(18));
				info.set_tranolddate(rs.getDate(19));
				info.set_tranoldseqno(rs.getString(20));
				
				info.set_batchflag(rs.getInt(21));
				info.set_smsres(rs.getString(22));
				info.set_trandes(rs.getString(23));
				info.set_operateid(rs.getString(24));
				
				list.add(info);
			}
			
		} catch (Exception e) {
			logger.debug("Execute GetTransList() error,Exception="+e.getMessage());
			list.clear();
		}
		finally
		{				
			try {
				if (rs!=null) {
					rs.close();
					rs=null;
				}
				
				if (ps!=null) {
					ps.close();
					ps =null;
				}
				
				if (conn!=null) {
					if (!conn.isClosed()) {
						conn.close();
						conn=null;
					}
				}
			}
			catch (SQLException e) {
				logger.debug("Execute GetTransList() error,Exception="+e.getMessage());
			}
		}
		
		logger.debug("Execute GetTransList() Over,Total Rows="+list.size());
		
		return list;
	}
}
