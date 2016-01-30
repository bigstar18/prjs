package gnnt.trade.bank.vo.bankdz.xy.resave.child;

import java.io.Serializable;
import java.util.Date;

/**交易商分分核对类*/
public class FirmDateValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**银行代码*/
	public String bankID;
	/**交易日期*/
	public Date tradeDate;
	/**交易商代码*/
	public String firmID;
	/**交易商银行结算账号*/
	public String account;
	/**币别 001：人民币 013：港币 014：美元*/
	public String currency="001";
	/**钞汇标识（0：钞 1：汇）*/
	public int type;
	/**不平原因 0:金额不平 1:银行端资金存管账户未建立 2:市场端交易商代码不存在*/
	public int reason;
//****************************市场的
	/**市场总权益*/
	public double balanceM;
	/**市场现金权益*/
	public double cashM;
	/**市场票据权益*/
	public double billM;
	/**市场可用资金*/
	public double useBalanceM;
	/**市场占用现金*/
	public double frozenCashM;
	/**市场占用贷款金额*/
	public double frozenLoanM;
//****************************银行的
	/**银行总权益*/
	public double balanceB;
	/**银行现金权益*/
	public double cashB;
	/**银行票据权益*/
	public double billB;
	/**银行可用资金*/
	public double useBalanceB;
	/**银行占用现金*/
	public double frozenCashB;
	/**银行占用贷款金额*/
	public double frozenLoanB;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("firmID:"+this.firmID+str);
		sb.append("bankID:"+this.bankID+str);
		sb.append("tradeDate:"+this.tradeDate+str);
		sb.append("account:"+this.account+str);
		sb.append("currency:"+this.currency+str);
		sb.append("type:"+this.type+str);
		sb.append("reason:"+this.reason+str);
		sb.append(str);
		return sb.toString();
	}
}
