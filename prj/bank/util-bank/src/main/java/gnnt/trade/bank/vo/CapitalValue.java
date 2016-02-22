package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * Title: 资金流水对象类
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 *
 * <p>
 * Company: gnnt
 * </p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class CapitalValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 记录流水号
	 */
	public long iD;
	/**
	 * 市场业务流水号
	 */
	public long actionID;
	/**
	 * 交易商代码
	 */
	public String firmID;
	/**
	 * 银行业务流水号
	 */
	public String funID;
	/**
	 * 银行代码
	 */
	public String bankID;
	/**
	 * 借方科目代码
	 */
	public String debitID;
	/**
	 * 贷方科目代码
	 */
	public String creditID;
	/**
	 * 类型：0 入金 1 出金2 出入金手续费 3 其他资金划转 4 入金冲正 5 出金冲正
	 */
	public int type;
	/**
	 * 金额
	 */
	public double money;
	/**
	 * 业务代码
	 */
	public String oprcode;
	/**
	 * 记录创建时间
	 */
	public Timestamp createtime;
	/**
	 * 银行端操作时间
	 */
	public Timestamp bankTime;
	/**
	 * 状态：0：成功 1：失败 2：处理中 3:待审核 4:二次审核 5:银行无应答 6:市场流水号冲突 -1:流水被冲正
	 */
	public int status;
	/**
	 * 是否加急：0：正常 1：加急
	 */
	public int express;
	/**
	 * 备注
	 */
	public String note;
	/**
	 * 银行名称
	 */
	public String bankName;
	/**
	 * 账户名
	 */
	public String firmName;
	/**
	 * 银行账号
	 */
	public String account;

	public String sysFirmID;

	public String systemID;
	public String tradeSource;
	public double feeMoney;
	public String contact;

	public long getiD() {
		return iD;
	}

	public void setiD(long iD) {
		this.iD = iD;
	}

	public long getActionID() {
		return actionID;
	}

	public void setActionID(long actionID) {
		this.actionID = actionID;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getFunID() {
		return funID;
	}

	public void setFunID(String funID) {
		this.funID = funID;
	}

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getDebitID() {
		return debitID;
	}

	public void setDebitID(String debitID) {
		this.debitID = debitID;
	}

	public String getCreditID() {
		return creditID;
	}

	public void setCreditID(String creditID) {
		this.creditID = creditID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getOprcode() {
		return oprcode;
	}

	public void setOprcode(String oprcode) {
		this.oprcode = oprcode;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getBankTime() {
		return bankTime;
	}

	public void setBankTime(Timestamp bankTime) {
		this.bankTime = bankTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getExpress() {
		return express;
	}

	public void setExpress(int express) {
		this.express = express;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSysFirmID() {
		return sysFirmID;
	}

	public void setSysFirmID(String sysFirmID) {
		this.sysFirmID = sysFirmID;
	}

	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	public String getTradeSource() {
		return tradeSource;
	}

	public void setTradeSource(String tradeSource) {
		this.tradeSource = tradeSource;
	}

	public double getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(double feeMoney) {
		this.feeMoney = feeMoney;
	}

	/**
	 * 返回对象属性值
	 */
	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**" + this.getClass().getName() + "**" + sep);
		sb.append("ID:" + this.iD + sep);
		sb.append("actionID:" + this.actionID + sep);
		sb.append("firmID:" + this.firmID + sep);
		sb.append("funID:" + this.funID + sep);
		sb.append("bankID:" + this.bankID + sep);
		sb.append("debitID:" + this.debitID + sep);
		sb.append("creditID:" + this.creditID + sep);
		sb.append("type:" + this.type + sep);
		sb.append("money:" + this.money + sep);
		sb.append("operator:" + this.oprcode + sep);
		sb.append("createtime:" + this.createtime + sep);
		sb.append("bankTime:" + this.bankTime + sep);
		sb.append("status:" + this.status + sep);
		sb.append("express:" + this.express + sep);
		sb.append("note:" + this.note + sep);
		sb.append("bankName:" + this.bankName + sep);
		sb.append("firmName:" + this.firmName + sep);
		sb.append("account:" + this.account + sep);
		sb.append(sep);
		return sb.toString();
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
