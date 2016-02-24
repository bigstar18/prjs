package gnnt.trade.bank.vo.bankdz.xy.sent.child;

import java.io.Serializable;

/**交易商日终对账类*/
public class FirmDZValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**交易商代码*/
	public String firmID;
	/**交易商银行结算账号*/
	public String account;
	/**币别 001：人民币 013：港币 014：美元*/
	public String currency="001";
	/**钞汇标识（0：钞 1：汇）*/
	public int type=0;
	/**交易商权益*/
	public double firmRights;
	/**现金权益*/
	public double cashRights;
	/**票据权益*/
	public double billRights;
	/**可用资金*/
	public double availableBalance;
	/**占用现金（包含正负号）*/
	public double cash;
	/**占用贷款金额（包含正负号）*/
	public double credit;
	/**显示本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("firmID:"+firmID+str);
		sb.append("account:"+account+str);
		sb.append("currency:"+currency+str);
		sb.append("type:"+type+str);
		sb.append("firmRights:"+firmRights+str);
		sb.append("cashRights:"+cashRights+str);
		sb.append("billRights:"+billRights+str);
		sb.append("availableBalance:"+availableBalance+str);
		sb.append("cash:"+cash+str);
		sb.append("credit:"+credit+str);
		sb.append(str);
		return sb.toString();
	}
}