package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmBalanceValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交易商代码
	 */
	public String firmId;
	
	/**
	 * 交易商市场总余额
	 */
	public double marketBalance;
	
	/**
	 * 交易商市场冻结资金
	 */
	public double frozenBalance;
	
	/**
	 * 交易商市场可用余额
	 */
	public double avilableBalance;
	/**
	 * 交易商上日可用余额
	 */
	public double lastBalance;
	/**
	 * 交易商当前浮动盈亏
	 */
	public double floatingloss;
	/**
	 * 交易商注册银行ID
	 */
	public String firmBankId;
	
	/**
	 * 交易商注册银行
	 */
	public String firmBank;
	
	/**
	 * 交易商注册银行对应账号
	 */
	public String bankAccount;
	
	/**
	 * 交易商注册银行对应账号余额
	 */
	public double bankBalance;
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
		sb.append("floatingloss:"+this.floatingloss+sep);
		sb.append("frozenBalance:" + this.frozenBalance+sep);
		sb.append("avilableBalance:" + this.avilableBalance+sep);
		sb.append("firmBankId:" + this.firmBankId+sep);
		sb.append("firmBank:" + this.firmBank+sep);
		sb.append("bankAccount:" + this.bankAccount+sep);
		sb.append("bankBalance:" + this.bankBalance+sep);
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

	public String getFirmBank() {
		return firmBank;
	}

	public void setFirmBank(String firmBank) {
		this.firmBank = firmBank;
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
}
