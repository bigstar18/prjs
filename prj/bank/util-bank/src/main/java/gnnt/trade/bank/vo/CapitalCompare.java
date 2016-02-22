package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * 市场流水和银行流水资金汇总比较表
 */
public class CapitalCompare implements Serializable {
	private static final long serialVersionUID = 1L;
	/**交易商代码*/
	public String firmID;
	/**转账日期*/
	public java.util.Date tradeDate;
	/**转账银行*/
	public String bankID;
	/**市场记录的当天入金*/
	public double mInmoney;
	/**市场记录的当天出金*/
	public double mOutmoney;
	/**银行记录的当天入金*/
	public double bInmoney;
	/**银行记录的当天出金*/
	public double bOutmoney;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("firmID["+firmID+"]"+str);
		sb.append("tradeDate["+tradeDate+"]"+str);
		sb.append("bankID["+bankID+"]"+str);
		sb.append("mInmoney["+mInmoney+"]"+str);
		sb.append("mOutmoney["+mOutmoney+"]"+str);
		sb.append("bInmoney["+bInmoney+"]"+str);
		sb.append("bOutmoney["+bOutmoney+"]"+str);
		return sb.toString();
	}
}
