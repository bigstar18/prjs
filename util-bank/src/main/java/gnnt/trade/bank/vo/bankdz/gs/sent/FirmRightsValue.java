package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;
import java.util.Date;

public class FirmRightsValue implements Serializable {

	/**
	 * 交易商权益信息对象
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	public long id;
	
	/**
	 * 交易商代码
	 */
	public String firmid;
	
	/**
	 * 交易商银行账号
	 */
	public String account;
	
	/**
	 * 交易商账号名称
	 */
	public String accountname;
	
	/**
	 * 财务余额
	 */
	public double avilableBlance;
	
	/**
	 * 中远期部分权益
	 */
	public double timebargainBalance;
	
	/**
	 * 竞价部分占用保证金
	 */
	public double vendueBalance;
	
	/**
	 * 挂牌部分占用保证金
	 */
	public double zcjsBalance;
	
	/**
	 * 银行余额
	 */
	public double bankBalance;
	
	/**
	 * 收货款
	 */
	public double payment;
	
	/**
	 * 付货款
	 */
	public double income;
	
	/**
	 * 手续费
	 */
	public double fee;
	
	/**
	 * 借出金额
	 */
	public double jie;
	
	/**
	 * 贷入金额
	 */
	public double dai;
	
	/**
	 * 记录日期
	 */
	public Date recordDate;
//	--------------------------------------------------------------------------------

	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("id:" + this.id+sep);
		sb.append("firmid:" + this.firmid+sep);
		sb.append("account:" + this.account+sep);
		sb.append("accountname:" + this.accountname+sep);
		sb.append("avilableBlance:" + this.avilableBlance+sep);
		sb.append("vendueBalance:" + this.vendueBalance+sep);
		sb.append("timebargainBalance:" + this.timebargainBalance+sep);
		sb.append("zcjsBalance:" + this.zcjsBalance+sep);
		sb.append("bankBalance:" + this.bankBalance+sep);
		sb.append("payment:" + this.payment+sep);
		sb.append("income:" + this.income+sep);
		sb.append("fee:" + this.fee+sep);
		sb.append("jie:" + this.jie+sep);
		sb.append("dai:" + this.dai+sep);
		sb.append("recordDate:" + this.recordDate+sep);
		sb.append(sep);
		return sb.toString();
	}
	
	
	//----------------------------------------------------------------------
	/**财务余额*/
	public double getAvilableBlance() {
		return avilableBlance;
	}
	/**财务余额*/
	public void setAvilableBlance(double avilableBlance) {
		this.avilableBlance = avilableBlance;
	}
	/**银行余额*/
	public double getBankBalance() {
		return bankBalance;
	}
	/**银行余额*/
	public void setBankBalance(double bankBalance) {
		this.bankBalance = bankBalance;
	}
	/**交易商代码*/
	public String getFirmid() {
		return firmid;
	}
	/**交易商代码*/
	public void setFirmid(String firmid) {
		this.firmid = firmid;
	}
	/**id*/
	public long getId() {
		return id;
	}
	/**id*/
	public void setId(long id) {
		this.id = id;
	}
	/**记录日期*/
	public Date getRecordDate() {
		return recordDate;
	}
	/**记录日期*/
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	/**中远期部分权益*/
	public double getTimebargainBalance() {
		return timebargainBalance;
	}
	/**中远期部分权益*/
	public void setTimebargainBalance(double timebargainBalance) {
		this.timebargainBalance = timebargainBalance;
	}
	/**竞价部分占用保证金*/
	public double getVendueBalance() {
		return vendueBalance;
	}
	/**竞价部分占用保证金*/
	public void setVendueBalance(double vendueBalance) {
		this.vendueBalance = vendueBalance;
	}
	/**挂牌部分占用保证金*/
	public double getZcjsBalance() {
		return zcjsBalance;
	}
	/**挂牌部分占用保证金*/
	public void setZcjsBalance(double zcjsBalance) {
		this.zcjsBalance = zcjsBalance;
	}
	/**手续费*/
	public double getFee() {
		return fee;
	}
	/**手续费*/
	public void setFee(double fee) {
		this.fee = fee;
	}
	/**付货款*/
	public double getIncome() {
		return income;
	}
	/**付货款*/
	public void setIncome(double income) {
		this.income = income;
	}
	/**收货款*/
	public double getPayment() {
		return payment;
	}
	/**收货款*/
	public void setPayment(double payment) {
		this.payment = payment;
	}
	/**借出金额*/
	public double getJie() {
		return jie;
	}
	/**借出金额*/
	public void setJie(double jie) {
		this.jie = jie;
	}
	/**贷入金额*/
	public double getDai() {
		return dai;
	}
	/**贷入金额*/
	public void setDai(double dai) {
		this.dai = dai;
	}


	/**
	 * @return 交易商银行账号
	 */
	public String getAccount() {
		return account;
	}


	/**
	 * @param 交易商银行账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}


	/**
	 * @return 交易商账号名称
	 */
	public String getAccountname() {
		return accountname;
	}


	/**
	 * @param 交易商账号名称
	 */
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	
	//------------------------------------------------------------------
	
	
	
}
