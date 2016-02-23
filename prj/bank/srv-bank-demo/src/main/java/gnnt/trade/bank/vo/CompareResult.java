package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;
/**
 * 对账后 错误信息
 * @author Administrator
 */
public class CompareResult implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 出错类型 0：账面不平 1：出入金类型不同 2：银行有 市场没有的流水 3：市场有银行没有的流水 */
	public int errorType;
	/** 银行代码 */
	public String bankID;
	/** 市场交易账号代码 */
	public String firmID;
	/** 市场和银行绑定号 */
	public String contact;
	/** 银行账号 */
	public String account;
	/** 内部账号 */
	public String account1;
	/** 市场流水号 */
	public long actionID;
	/** 银行流水号 */
	public String funID;
	/** 对账信息类型0：出金1：入金 */
	public int type;
	/** 流水金额 */
	public double money;
	/** 对账日期 */
	public Date tradeDate;
	/** 流水状态 */
	public int status;
	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("---"+this.getClass().getName()+"---"+sep);
		sb.append("errorType:"+errorType+sep);
		sb.append("bankID:"+bankID+sep);
		sb.append("firmID:"+firmID+sep);
		sb.append("contact:"+contact+sep);
		sb.append("account:"+account+sep);
		sb.append("account1:"+account1+sep);
		sb.append("actionID:"+actionID+sep);
		sb.append("funID:"+funID+sep);
		sb.append("type:"+type+sep);
		sb.append("money:"+money+sep);
		sb.append("tradeDate:"+tradeDate+sep);
		sb.append(sep);
		return sb.toString();
	}
}
