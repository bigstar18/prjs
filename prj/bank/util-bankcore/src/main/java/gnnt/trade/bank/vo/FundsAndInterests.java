package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FundsAndInterests implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交易商id
	 */
	public String firmid;
	/**
	 * 资金余额
	 */
	public double balance;
	
	/**
	 * 交易保证金
	 */
	public double RuntimeMargin;
	/**
	 * 交收保证金
	 */
	public double RuntimeSettleMargin;
	/**
	 * 当日盈亏
	 */
	public double RuntimeFL;
	/**
	 * 浮动盈亏
	 */
	public double floatingloss;
	/**
	 * 
	 * 当前权益
	 */
	public double fundsInterests;
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getFirmid() {
		return firmid;
	}
	public void setFirmid(String firmid) {
		this.firmid = firmid;
	}
	public double getFloatingloss() {
		return floatingloss;
	}
	public void setFloatingloss(double floatingloss) {
		this.floatingloss = floatingloss;
	}
	public double getRuntimeFL() {
		return RuntimeFL;
	}
	public void setRuntimeFL(double runtimeFL) {
		RuntimeFL = runtimeFL;
	}
	public double getRuntimeMargin() {
		return RuntimeMargin;
	}
	public void setRuntimeMargin(double runtimeMargin) {
		RuntimeMargin = runtimeMargin;
	}
	public double getRuntimeSettleMargin() {
		return RuntimeSettleMargin;
	}
	public void setRuntimeSettleMargin(double runtimeSettleMargin) {
		RuntimeSettleMargin = runtimeSettleMargin;
	}
	public double getFundsInterests() {
		return fundsInterests;
	}
	public void setFundsInterests(double fundsInterests) {
		this.fundsInterests = fundsInterests;
	}
	
}
