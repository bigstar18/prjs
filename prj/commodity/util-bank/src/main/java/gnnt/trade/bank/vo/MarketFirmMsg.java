package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 市场签约交易商类
 */
public class MarketFirmMsg implements Serializable{
	private static final long serialVersionUID=1;
	/**交易商代码*/
	public String firmID;
	/**银行编号*/
	public String bankID;
	/**银行账号*/
	public String account;
	/**账户名*/
	public String accountName;
	/**证件类型*/
	public String cardType;
	/**证件号码*/
	public String card;
	/**可用资金*/
	public double balance;
	/**冻结资金*/
	public double frozen;
	/**权益*/
	public double right;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append(str+"---"+this.getClass().getName()+"---"+str);
		sb.append("交易商代码[firmID]("+firmID+")"+str);
		sb.append("银行编号[bankID]("+bankID+")"+str);
		sb.append("银行账号[account]("+account+")"+str);
		sb.append("账户名[accountName]("+accountName+")"+str);
		sb.append("证件类型[cardType]("+cardType+")"+str);
		sb.append("证件号码[card]("+card+")"+str);
		sb.append("可用资金[balance]("+balance+")"+str);
		sb.append("冻结资金[frozen]("+frozen+")"+str);
		sb.append("权益[right]("+right+")"+str);
		return sb.toString();
	}
}
