package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmBalanceValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 交易账号代码
	 */
	public String firmId;
	
	/**
	 * 交易账号市场总余额
	 */
	public double marketBalance;
	
	/**
	 * 交易账号市场冻结资金
	 */
	public double frozenBalance;
	
	/**
	 * 交易账号市场可用余额
	 */
	public double avilableBalance;
	/**
	 * 交易账号出入金金额
	 */
	public double inOutMoney;
	/**
	 * 交易账号当前可出金额
	 */
	public double canOutMoney;
	/**
	 * 交易账号上日可用余额
	 */
	public double lastBalance;
	/**
	 * 交易账号注册银行ID
	 */
	public String firmBankId;
	
	/**
	 * 交易账号注册银行对应账号
	 */
	public String bankAccount;
	
	/**
	 * 交易账号注册银行对应账号余额
	 */
	public double bankBalance;

	/**
	 * 当前在途资金
	 */
	public double inTransitMoney;
	/**
	 * 银行名称
	 */
	public String bankName;
	//-----------------------------------------------------
	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("firmId:" + this.firmId+sep);
		sb.append("marketBalance:" + this.marketBalance+sep);
		sb.append("lastBalance:"+this.lastBalance+sep);
		sb.append("frozenBalance:" + this.frozenBalance+sep);
		sb.append("avilableBalance:" + this.avilableBalance+sep);
		sb.append("inOutMoney:"+this.inOutMoney+sep);
		sb.append("canOutMoney:"+this.canOutMoney+sep);
		sb.append("firmBankId:" + this.firmBankId+sep);
		sb.append("bankAccount:" + this.bankAccount+sep);
		sb.append("bankBalance:" + this.bankBalance+sep);
		sb.append("inTransitMoney:"+this.inTransitMoney+sep);
		sb.append("bankName:"+this.bankName+sep);
		sb.append(sep);
		return sb.toString();
	}
//-------------------------------------------------------------------------------
	
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public double getBankBalance() {
		return bankBalance;
	}

	public void setBankBalance(double bankBalance) {
		this.bankBalance = bankBalance;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public double getMarketBalance() {
		return marketBalance;
	}

	public void setMarketBalance(double marketBalance) {
		this.marketBalance = marketBalance;
	}

	public String getFirmBankId() {
		return firmBankId;
	}

	public void setFirmBankId(String firmBankId) {
		this.firmBankId = firmBankId;
	}

	public double getAvilableBalance() {
		return avilableBalance;
	}

	public void setAvilableBalance(double avilableBalance) {
		this.avilableBalance = avilableBalance;
	}

	public double getInOutMoney(){
		return inOutMoney;
	}

	public void setInOutMoney(double inOutMoney){
		this.inOutMoney = inOutMoney;
	}

	public double getCanOutMoney(){
		return canOutMoney;
	}

	public void setCanOutMoney(double canOutMoney){
		this.canOutMoney = canOutMoney;
	}

	public double getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(double frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	
	public double getLastBalance(){
		return lastBalance;
	}
	
	public void setLastBalance(double lastBalance){
		this.lastBalance = lastBalance;
	}

	public double getInTransitMoney() {
		return inTransitMoney;
	}

	public void setInTransitMoney(double inTransitMoney) {
		this.inTransitMoney = inTransitMoney;
	}

}
