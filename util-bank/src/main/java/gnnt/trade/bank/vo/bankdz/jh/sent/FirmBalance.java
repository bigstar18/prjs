package gnnt.trade.bank.vo.bankdz.jh.sent;

import java.io.Serializable;
import java.util.Date;

/**
 * 传送交易商的余额信息
 */
public class FirmBalance implements Serializable {
	// private static final long serialVersionUID = 1L;
	// /**交易商代码*/
	// public String firmID;
	// /**银行编号*/
	// public String bankID;
	// /**交易商银行账号*/
	// public String account;
	// /**交易商账户名*/
	// public String accountName;
	// /**交易商账户类型*/
	// public String cardType;
	// /**交易商证件号码*/
	// public String card;
	// /**手续费*/
	// public double FeeMoney;
	// /**交易商权益变化量(可用变化量)*/
	// public double QYChangeMoney;
	// /**交易商当日总权益(可用)*/
	// public double QYMoney;
	// /**要发送的对账日期*/
	// public java.util.Date date;
	// /**出入金汇总 入金-出金*/
	// public double CRJSum;
	// /**交易商上日权益(可用)*/
	// public double yesQYMoney;
	// /**
	// * 本类信息
	// */
	// public String toString(){
	// StringBuilder sb = new StringBuilder();
	// String str = "\n";
	// sb.append("交易商代码(firmID)["+firmID+"]"+str);
	// sb.append("银行编号(bankID)["+bankID+"]"+str);
	// sb.append("交易商银行账号(account)["+account+"]"+str);
	// sb.append("交易商账户名(accountName)["+accountName+"]"+str);
	// sb.append("交易商账户类型(cardType)["+cardType+"]"+str);
	// sb.append("交易商证件号码(card)["+card+"]"+str);
	// sb.append("手续费(FeeMoney)["+FeeMoney+"]"+str);
	// sb.append("交易商权益变化量(可用变化量)(QYChangeMoney)["+QYChangeMoney+"]"+str);
	// sb.append("交易商当日总权益(可用)(QYMoney)["+QYMoney+"]"+str);
	// sb.append("要发送的对账日期(date)["+date+"]"+str);
	// sb.append("交易商上日总权益(可用)(yesQYMoney)["+yesQYMoney+"]"+str);
	// sb.append("当日出入金汇总(入-出)(CRJSum)["+CRJSum+"]"+str);
	// return sb.toString();
	// }
	private static final long serialVersionUID = 1L;
	public String firmID;
	public String bankID;
	public String account;
	public String accountName;
	public String cardType;
	public String card;
	public double FeeMoney;
	public double QYChangeMoney;
	public double QYMoney;
	public Date date;
	public double CRJSum;
	public double inMoneySum;
	public double outMoneySum;
	public double yesQYMoney;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("交易商代码(firmID)[" + this.firmID + "]" + str);
		sb.append("银行编号(bankID)[" + this.bankID + "]" + str);
		sb.append("交易商银行账号(account)[" + this.account + "]" + str);
		sb.append("交易商账户名(accountName)[" + this.accountName + "]" + str);
		sb.append("交易商账户类型(cardType)[" + this.cardType + "]" + str);
		sb.append("交易商证件号码(card)[" + this.card + "]" + str);
		sb.append("手续费(FeeMoney)[" + this.FeeMoney + "]" + str);
		sb.append("交易商权益变化量(可用变化量)(QYChangeMoney)[" + this.QYChangeMoney + "]" + str);
		sb.append("交易商当日总权益(可用)(QYMoney)[" + this.QYMoney + "]" + str);
		sb.append("要发送的对账日期(date)[" + this.date + "]" + str);
		sb.append("交易商上日总权益(可用)(yesQYMoney)[" + this.yesQYMoney + "]" + str);
		sb.append("当日出入金汇总(入-出)(CRJSum)[" + this.CRJSum + "]" + str);
		return sb.toString();
	}

}
