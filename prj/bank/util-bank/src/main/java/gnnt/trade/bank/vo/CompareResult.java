package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;
/**
 * 对账后 错误信息
 * @author Administrator
 *
 */
public class CompareResult implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 出错类型 0：账面不平 1：出入金类型不同 2：银行有 市场没有的流水 3：市场有银行没有的流水
	 */
	public int errorType;
	/**
	 * 交易商代码
	 */
	public String firmID;
	/**
	 * 银行代码
	 */
	public String bankID;
	/**
	 * 银行帐号
	 */
	public String account;
	/**
	 * 银行流水号
	 */
	public String id;
	/**
	 * 市场流水号
	 */
	public long m_Id;
	/**
	 * 银行对账信息类型0：出金1：入金
	 */
	public int type;
	/**
	 * 市场对账信息类型0：出金1：入金
	 */
	public int m_type;
	/**
	 * 银行流水金额
	 */
	public double money;
	/**
	 * 市场流水金额
	 */
	public double m_money;
	/**
	 * 对账日期
	 */
	public Date compareDate;

	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("errorType:" + this.errorType+sep);
		sb.append("firmID:" + this.firmID+sep);
		sb.append("bankID:" + this.bankID+sep);
		sb.append("account:" + this.account+sep);
		sb.append("Id:" + this.id+sep);
		sb.append("m_Id:" + this.m_Id+sep);
		sb.append("type:" + this.type+sep);
		sb.append("m_type:" + this.m_type+sep);
		sb.append("money:" + this.money+sep);
		sb.append("m_money:" + this.m_money+sep);
		sb.append("compareDate:" + this.compareDate+sep);
		sb.append(sep);
		return sb.toString();
	}
}
