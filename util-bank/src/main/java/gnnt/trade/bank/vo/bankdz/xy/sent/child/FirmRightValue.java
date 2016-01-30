package gnnt.trade.bank.vo.bankdz.xy.sent.child;
import java.io.Serializable;
/**交易商清算类*/
public class FirmRightValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**市场流水号*/
	public String actionID;
	/**交易商代码*/
	public String firmID;
	/**交易商银行结算账号*/
	public String account;
	/**币别 001：人民币 013：港币 014：美元*/
	public String currency="001";
	/**钞汇标识（0：钞 1：汇）*/
	public int type=0;
	/**交易商权益变化（包含正负号）*/
	public double firmErrorMoney;
	/**现金变化量*/
	public double cashMoney;
	/**票据变化量*/
	public double billMoney;
	/**可用资金变化量（包含正负号）*/
	public double availableBalance;
	/**占用现金变化量（包含正负号）*/
	public double cash;
	/**占用贷款金额变化量（包含正负号）*/
	public double credit;
	/**返回对象类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("actionID:"+this.actionID+str);
		sb.append("firmID:"+this.firmID+str);
		sb.append("account:"+this.account+str);
		sb.append("currency:"+this.currency+str);
		sb.append("type:"+this.type+str);
		sb.append("firmErrorMoney:"+this.firmErrorMoney+str);
		sb.append("cashMoney:"+this.cashMoney+str);
		sb.append("billMoney:"+this.billMoney+str);
		sb.append("availableBalance:"+this.availableBalance+str);
		sb.append("cash:"+this.cash+str);
		sb.append("credit:"+this.credit+str);
		sb.append(str);
		return sb.toString();
	}
}