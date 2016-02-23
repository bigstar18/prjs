package gnnt.trade.bank.vo;

import gnnt.trade.bank.util.Tool;

import java.io.Serializable;
import java.util.Date;

public class CompareSumMoney implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 银行编号 */
	public String bankID;
	/** 银行名称 */
	public String bankName;
	/** 入金笔数 */
	public int inMoneyCount;
	/** 出金笔数 */
	public int outMoneyCount;
	/** 入金金额 */
	public double inMoney;
	/** 出金金额 */
	public double outMoney;
	/** 日期 */
	public Date tradeDate;
	/** 判断是市场还是银行(1 银行,2市场) */
	public int mb;
	public CompareSumMoney(String bankID,Date tradeDate,int mb){
		this.bankID = bankID;
		this.tradeDate = tradeDate;
		this.mb = mb;
	}
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "/n";
		sb.append("bankID["+bankID+"]"+str);
		sb.append("bankName["+bankName+"]"+str);
		sb.append("inMoneyCount["+inMoneyCount+"]"+str);
		sb.append("outMoneyCount["+outMoneyCount+"]"+str);
		sb.append("inMoney["+inMoney+"]"+str);
		sb.append("outMoney["+outMoney+"]"+str);
		sb.append("tradeDate["+Tool.fmtDate(tradeDate)+"]"+str);
		sb.append("mb["+mb+"]"+str);
		return sb.toString();
	}
}
