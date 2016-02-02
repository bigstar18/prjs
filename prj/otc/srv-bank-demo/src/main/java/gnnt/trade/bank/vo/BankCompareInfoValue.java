package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BankCompareInfoValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流水号
	 */
	public long id;
	/**
	 * 银行流水号
	 */
	public String funid;
	/**
	 * 交易账号代码
	 */
	public String firmid;
	/**
	 * 银行代码
	 */
	public String bankid;
	/**
	 * 银行账号
	 */
	public long account;
	/**
	 * 对账信息类型 0：出金 1：入金
	 */
	public int type;
	/**
	 * 金额
	 */
	public double money;
	/**
	 * 对账日期
	 */
	public String compareDate;
	/**
	 * 备注
	 */
	public String note;
	/**
	 * 创建日期
	 */
	public String createTime;
	/**
	 * 记录状态： 0：正常 1：已删除
	 */
	public int status;
	
	public long getAccount() {
		return account;
	}

	public void setAccount(long account) {
		this.account = account;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getCompareDate() {
		return compareDate;
	}

	public void setCompareDate(String compareDate) {
		this.compareDate = compareDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFirmid() {
		return firmid;
	}

	public void setFirmid(String firmid) {
		this.firmid = firmid;
	}

	public String getFunid() {
		return funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("id:" + this.id+sep);
		sb.append("funid:" + this.funid+sep);
		sb.append("firmid:" + this.firmid+sep);
		sb.append("bankid:" + this.bankid+sep);
		sb.append("account:" + this.account+sep);
		sb.append("type:" + this.type+sep);
		sb.append("money:" + this.money+sep);
		sb.append("compareDate:" + this.compareDate+sep);
		sb.append("note:" + this.note+sep);
		sb.append("createTime:" + this.createTime+sep);
		sb.append("status:" + this.status+sep);
		sb.append(sep);
		return sb.toString();
	}
}
