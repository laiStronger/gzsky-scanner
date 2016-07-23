package cn.com.skywin.model;

import java.io.Serializable;
import java.util.Date;

/*
 * 交易实体类
 */
public class TransInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date _trandate;
	private String _transeqno;
	private String _serviceid;
	private String _trantype;
	private int _paytype;

	private String _usermobileno;
	private String _useraccount;
	private Date _bankdate;
	private String _bankid;
	private Date _spdate;

	private String _spid;
	private String _sporderno;
	private String _msid;
	private String _msaccount;
	private int _tranflag;

	private String _trandwflag;
	private int _tranaccreditflag;
	private int _transtate;
	private Date _tranolddate;
	private String _tranoldseqno;

	private int _batchflag;
	private String _smsres;
	private String _trandes;
	private String _operateid;

	/*
	 * 系统交易日期
	 */
	public Date get_trandate() {
		return _trandate;
	}

	/*
	 * 系统交易日期
	 */
	public void set_trandate(Date _trandate) {
		this._trandate = _trandate;
	}

	/*
	 * 系统交易流水号
	 */
	public String get_transeqno() {
		return _transeqno;
	}

	/*
	 * 系统交易流水号
	 */
	public void set_transeqno(String _transeqno) {
		this._transeqno = _transeqno;
	}

	/*
	 * 业务标识
	 */
	public String get_serviceid() {
		return _serviceid;
	}

	/*
	 * 业务标识
	 */
	public void set_serviceid(String _serviceid) {
		this._serviceid = _serviceid;
	}

	/*
	 * 交易种类
	 */
	public String get_trantype() {
		return _trantype;
	}

	/*
	 * 交易种类
	 */
	public void set_trantype(String _trantype) {
		this._trantype = _trantype;
	}

	/*
	 * 支付种类
	 */
	public int get_paytype() {
		return _paytype;
	}

	/*
	 * 支付种类
	 */
	public void set_paytype(int _paytype) {
		this._paytype = _paytype;
	}

	/*
	 * 用户手机号
	 */
	public String get_usermobileno() {
		return _usermobileno;
	}

	/*
	 * 用户手机号
	 */
	public void set_usermobileno(String _usermobileno) {
		this._usermobileno = _usermobileno;
	}

	/*
	 * 用户银行帐号
	 */
	public String get_useraccount() {
		return _useraccount;
	}

	/*
	 * 用户银行帐号
	 */
	public void set_useraccount(String _useraccount) {
		this._useraccount = _useraccount;
	}

	/*
	 * 银行日期
	 */
	public Date get_bankdate() {
		return _bankdate;
	}

	/*
	 * 银行日期
	 */
	public void set_bankdate(Date _bankdate) {
		this._bankdate = _bankdate;
	}

	/*
	 * 银行标识
	 */
	public String get_bankid() {
		return _bankid;
	}

	/*
	 * 银行标识
	 */
	public void set_bankid(String _bankid) {
		this._bankid = _bankid;
	}

	/*
	 * 交易日期
	 */
	public Date get_spdate() {
		return _spdate;
	}

	/*
	 * 交易日期
	 */
	public void set_spdate(Date _spdate) {
		this._spdate = _spdate;
	}

	/*
	 * SP标识
	 */
	public String get_spid() {
		return _spid;
	}

	/*
	 * SP标识
	 */
	public void set_spid(String _spid) {
		this._spid = _spid;
	}

	/*
	 * SP订单号
	 */
	public String get_sporderno() {
		return _sporderno;
	}

	/*
	 * SP订单号
	 */
	public void set_sporderno(String _sporderno) {
		this._sporderno = _sporderno;
	}

	/*
	 * 商户标识
	 */
	public String get_msid() {
		return _msid;
	}

	/*
	 * 商户标识
	 */
	public void set_msid(String _msid) {
		this._msid = _msid;
	}

	/*
	 * 商户帐号
	 */
	public String get_msaccount() {
		return _msaccount;
	}

	/*
	 * 商户帐号
	 */
	public void set_msaccount(String _msaccount) {
		this._msaccount = _msaccount;
	}

	/*
	 * 成功失败标志
	 */
	public int get_tranflag() {
		return _tranflag;
	}

	/*
	 * 成功失败标志
	 */
	public void set_tranflag(int _tranflag) {
		this._tranflag = _tranflag;
	}

	/*
	 * 收付标志
	 */
	public String get_trandwflag() {
		return _trandwflag;
	}

	/*
	 * 收付标志
	 */
	public void set_trandwflag(String _trandwflag) {
		this._trandwflag = _trandwflag;
	}

	/*
	 * 预授权标志
	 */
	public int get_tranaccreditflag() {
		return _tranaccreditflag;
	}

	/*
	 * 预授权标志
	 */
	public void set_tranaccreditflag(int _tranaccreditflag) {
		this._tranaccreditflag = _tranaccreditflag;
	}

	/*
	 * 交易状态（取消标志）
	 */
	public int get_transtate() {
		return _transtate;
	}

	/*
	 * 交易状态（取消标志）
	 */
	public void set_transtate(int _transtate) {
		this._transtate = _transtate;
	}

	/*
	 * 原交易日期
	 */
	public Date get_tranolddate() {
		return _tranolddate;
	}

	/*
	 * 原交易日期
	 */
	public void set_tranolddate(Date _tranolddate) {
		this._tranolddate = _tranolddate;
	}

	/*
	 * 原交易流水号
	 */
	public String get_tranoldseqno() {
		return _tranoldseqno;
	}

	/*
	 * 原交易流水号
	 */
	public void set_tranoldseqno(String _tranoldseqno) {
		this._tranoldseqno = _tranoldseqno;
	}

	/*
	 * 批处理标志
	 */
	public int get_batchflag() {
		return _batchflag;
	}

	/*
	 * 批处理标志
	 */
	public void set_batchflag(int _batchflag) {
		this._batchflag = _batchflag;
	}

	/*
	 * 短消息确认
	 */
	public String get_smsres() {
		return _smsres;
	}

	/*
	 * 短消息确认
	 */
	public void set_smsres(String _smsres) {
		this._smsres = _smsres;
	}

	/*
	 * 交易描述
	 */
	public String get_trandes() {
		return _trandes;
	}

	/*
	 * 交易描述
	 */
	public void set_trandes(String _trandes) {
		this._trandes = _trandes;
	}

	/*
	 * 操作员
	 */
	public String get_operateid() {
		return _operateid;
	}

	/*
	 * 操作员
	 */
	public void set_operateid(String _operateid) {
		this._operateid = _operateid;
	}
}
