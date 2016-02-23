package gnnt.trade.bank.vo;
import java.io.Serializable;
/**前台页面显示分银行客户出入金信息*/
public class FirmFundsBankValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/**银行编号*/
	public String bankID;
	/**银行名称*/
	public String bankName;
	/**交易商代码*/
	public String firmID;
	/**签约号*/
	public String contact;
	/**银行账号*/
	public String account;
	/**内部账号*/
	public String account1;
	/**帐号属性*/
	public String mainBank;
	/**期初余额*/
	public double lastBalance;
	/**当日出入金*/
	public double inOutMoney;
	/**可出金额*/
	public double canOutFunds;
	/**在途资金*/
	public double frozenFunds;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---");
		sb.append("bankID["+bankID+"]"+str);
		sb.append("bankName["+bankName+"]"+str);
		sb.append("firmID["+firmID+"]"+str);
		sb.append("contact["+contact+"]"+str);
		sb.append("account["+account+"]"+str);
		sb.append("account1["+account1+"]"+str);
		sb.append("mainBank["+mainBank+"]"+str);
		sb.append("lastBalance["+lastBalance+"]"+str);
		sb.append("inOutMoney["+inOutMoney+"]"+str);
		sb.append("canOutFunds["+canOutFunds+"]"+str);
		sb.append("frozenFunds["+frozenFunds+"]"+str);
		return sb.toString();
	}
}
