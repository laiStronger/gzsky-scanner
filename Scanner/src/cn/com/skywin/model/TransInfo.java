package cn.com.skywin.model;

import java.io.Serializable;
import java.util.Date;

/*
 * ����ʵ����
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
	 * ϵͳ��������
	 */
	public Date get_trandate() {
		return _trandate;
	}

	/*
	 * ϵͳ��������
	 */
	public void set_trandate(Date _trandate) {
		this._trandate = _trandate;
	}

	/*
	 * ϵͳ������ˮ��
	 */
	public String get_transeqno() {
		return _transeqno;
	}

	/*
	 * ϵͳ������ˮ��
	 */
	public void set_transeqno(String _transeqno) {
		this._transeqno = _transeqno;
	}

	/*
	 * ҵ���ʶ
	 */
	public String get_serviceid() {
		return _serviceid;
	}

	/*
	 * ҵ���ʶ
	 */
	public void set_serviceid(String _serviceid) {
		this._serviceid = _serviceid;
	}

	/*
	 * ��������
	 */
	public String get_trantype() {
		return _trantype;
	}

	/*
	 * ��������
	 */
	public void set_trantype(String _trantype) {
		this._trantype = _trantype;
	}

	/*
	 * ֧������
	 */
	public int get_paytype() {
		return _paytype;
	}

	/*
	 * ֧������
	 */
	public void set_paytype(int _paytype) {
		this._paytype = _paytype;
	}

	/*
	 * �û��ֻ���
	 */
	public String get_usermobileno() {
		return _usermobileno;
	}

	/*
	 * �û��ֻ���
	 */
	public void set_usermobileno(String _usermobileno) {
		this._usermobileno = _usermobileno;
	}

	/*
	 * �û������ʺ�
	 */
	public String get_useraccount() {
		return _useraccount;
	}

	/*
	 * �û������ʺ�
	 */
	public void set_useraccount(String _useraccount) {
		this._useraccount = _useraccount;
	}

	/*
	 * ��������
	 */
	public Date get_bankdate() {
		return _bankdate;
	}

	/*
	 * ��������
	 */
	public void set_bankdate(Date _bankdate) {
		this._bankdate = _bankdate;
	}

	/*
	 * ���б�ʶ
	 */
	public String get_bankid() {
		return _bankid;
	}

	/*
	 * ���б�ʶ
	 */
	public void set_bankid(String _bankid) {
		this._bankid = _bankid;
	}

	/*
	 * ��������
	 */
	public Date get_spdate() {
		return _spdate;
	}

	/*
	 * ��������
	 */
	public void set_spdate(Date _spdate) {
		this._spdate = _spdate;
	}

	/*
	 * SP��ʶ
	 */
	public String get_spid() {
		return _spid;
	}

	/*
	 * SP��ʶ
	 */
	public void set_spid(String _spid) {
		this._spid = _spid;
	}

	/*
	 * SP������
	 */
	public String get_sporderno() {
		return _sporderno;
	}

	/*
	 * SP������
	 */
	public void set_sporderno(String _sporderno) {
		this._sporderno = _sporderno;
	}

	/*
	 * �̻���ʶ
	 */
	public String get_msid() {
		return _msid;
	}

	/*
	 * �̻���ʶ
	 */
	public void set_msid(String _msid) {
		this._msid = _msid;
	}

	/*
	 * �̻��ʺ�
	 */
	public String get_msaccount() {
		return _msaccount;
	}

	/*
	 * �̻��ʺ�
	 */
	public void set_msaccount(String _msaccount) {
		this._msaccount = _msaccount;
	}

	/*
	 * �ɹ�ʧ�ܱ�־
	 */
	public int get_tranflag() {
		return _tranflag;
	}

	/*
	 * �ɹ�ʧ�ܱ�־
	 */
	public void set_tranflag(int _tranflag) {
		this._tranflag = _tranflag;
	}

	/*
	 * �ո���־
	 */
	public String get_trandwflag() {
		return _trandwflag;
	}

	/*
	 * �ո���־
	 */
	public void set_trandwflag(String _trandwflag) {
		this._trandwflag = _trandwflag;
	}

	/*
	 * Ԥ��Ȩ��־
	 */
	public int get_tranaccreditflag() {
		return _tranaccreditflag;
	}

	/*
	 * Ԥ��Ȩ��־
	 */
	public void set_tranaccreditflag(int _tranaccreditflag) {
		this._tranaccreditflag = _tranaccreditflag;
	}

	/*
	 * ����״̬��ȡ����־��
	 */
	public int get_transtate() {
		return _transtate;
	}

	/*
	 * ����״̬��ȡ����־��
	 */
	public void set_transtate(int _transtate) {
		this._transtate = _transtate;
	}

	/*
	 * ԭ��������
	 */
	public Date get_tranolddate() {
		return _tranolddate;
	}

	/*
	 * ԭ��������
	 */
	public void set_tranolddate(Date _tranolddate) {
		this._tranolddate = _tranolddate;
	}

	/*
	 * ԭ������ˮ��
	 */
	public String get_tranoldseqno() {
		return _tranoldseqno;
	}

	/*
	 * ԭ������ˮ��
	 */
	public void set_tranoldseqno(String _tranoldseqno) {
		this._tranoldseqno = _tranoldseqno;
	}

	/*
	 * �������־
	 */
	public int get_batchflag() {
		return _batchflag;
	}

	/*
	 * �������־
	 */
	public void set_batchflag(int _batchflag) {
		this._batchflag = _batchflag;
	}

	/*
	 * ����Ϣȷ��
	 */
	public String get_smsres() {
		return _smsres;
	}

	/*
	 * ����Ϣȷ��
	 */
	public void set_smsres(String _smsres) {
		this._smsres = _smsres;
	}

	/*
	 * ��������
	 */
	public String get_trandes() {
		return _trandes;
	}

	/*
	 * ��������
	 */
	public void set_trandes(String _trandes) {
		this._trandes = _trandes;
	}

	/*
	 * ����Ա
	 */
	public String get_operateid() {
		return _operateid;
	}

	/*
	 * ����Ա
	 */
	public void set_operateid(String _operateid) {
		this._operateid = _operateid;
	}
}
