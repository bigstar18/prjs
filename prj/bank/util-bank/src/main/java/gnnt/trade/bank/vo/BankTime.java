package gnnt.trade.bank.vo;

import java.io.Serializable;


public class BankTime implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**银行ID*/
	public String bankID;
	/**银行*/
	public String bankName;
	/**签到时间*/
	public String login;
	/**可以转账开始时间*/
	public String transferon;
	/**可以转账结束时间*/
	public String transferoff;
	/**签退时间*/
	public String quit;
	/**对账时间*/
	public String compareTime;
	/**是否自动对账1自动,-1不自动*/
	public int compareMode;
	/**是否是开市状态1开市,-1闭市*/
	public int openType;
	/**是否是可转账状态1可转账,-1不可转账*/
	public int transferType;
//	/**银行ID*/
//	private String bankID;
//	/**银行*/
//	private BankValue bankValue;
//	/**签到时间*/
//	private String login;
//	/**签退时间*/
//	private String quit;
//	/**对账时间*/
//	private String compareTime;
//	/**是否自动对账*/
//	private int compareMode;
//	/**银行ID*/
//	public String getBankID() {
//		return bankID;
//	}
//	/**银行ID*/
//	public void setBankID(String bankID) {
//		this.bankID = bankID;
//	}
//	/**银行*/
//	public BankValue getBankValue() {
//		return bankValue;
//	}
//	/**银行*/
//	public void setBankValue(BankValue bankValue) {
//		this.bankValue = bankValue;
//	}
//	/**签到时间*/
//	public String getLogin() {
//		return login;
//	}
//	/**签到时间*/
//	public void setLogin(String login) {
//		this.login = login;
//	}
//	/**签退时间*/
//	public String getQuit() {
//		return quit;
//	}
//	/**签退时间*/
//	public void setQuit(String quit) {
//		this.quit = quit;
//	}
//	/**对账时间*/
//	public String getCompareTime() {
//		return compareTime;
//	}
//	/**对账时间*/
//	public void setCompareTime(String compareTime) {
//		this.compareTime = compareTime;
//	}
//	/**是否自动对账*/
//	public int getCompareMode() {
//		return compareMode;
//	}
//	/**是否自动对账*/
//	public void setCompareMode(int compareMode) {
//		this.compareMode = compareMode;
//	}
}
