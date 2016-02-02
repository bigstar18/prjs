package gnnt.trade.bank.vo;
import java.io.Serializable;
public class FirmFundsValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/**交易商帐号*/
	public String firmID;
	/**签约号*/
	public String contact;
	/**当前可用资金*/
	public double firmFunds;
	/**当前可出资金*/
	public double canOutFunds;
	/**期初权益*/
	public double lastBalance;
	/**当日出入金*/
	public double inOutFunds;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("firmID["+firmID+"]"+str);
		sb.append("contact["+contact+"]"+str);
		sb.append("firmFunds["+firmFunds+"]"+str);
		sb.append("canOutFunds["+canOutFunds+"]"+str);
		sb.append("lastBalance["+lastBalance+"]"+str);
		sb.append("inOutFunds["+inOutFunds+"]"+str);
		return sb.toString();
	}
}
