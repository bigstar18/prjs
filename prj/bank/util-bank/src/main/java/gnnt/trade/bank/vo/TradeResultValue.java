package gnnt.trade.bank.vo;

import java.io.Serializable;

public class TradeResultValue  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 流水id
	 */
	public long fundFlowId;
	
	/**
	 * 交易商id
	 */
	public String firmid;
	
	/**
	 * 交易发生额
	 */
	public double amount;
	
	/**
	 * 资金余额
	 */
	public double balance;
	
	/**
	 * 业务代码
	 */
	public int oprCode;
	
	/**
	 * 附加帐金额
	 */
	public double appendAmount;
	
	/**
	 * 结算日期
	 */
	public java.util.Date date;
	
	/**
	 * 银行代码
	 */
	public String bankid;
	
	/**
	 * 银行账号
	 */
	public String account;
	
	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("fundFlowId:" + this.fundFlowId+sep);
		sb.append("firmid:" + this.firmid+sep);
		sb.append("amount:" + this.amount+sep);
		sb.append("balance:" + this.balance+sep);
		sb.append("oprCode:" + this.oprCode+sep);
		sb.append("appendAmount:" + this.appendAmount+sep);
		sb.append("date:" + this.date+sep);
		sb.append("bankid:" + this.bankid+sep);
		sb.append("account:" + this.account+sep);
		sb.append(sep);
		return sb.toString();
	}
}
