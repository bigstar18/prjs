package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.util.Date;
/**
 * 交易商交易系统资金信息
 */
public class FirmFundsValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/**交易商代码*/
	public String firmID;
	/**上日资金余额*/
	public double yesFunds;
	/**当日资金余额*/
	public double todayFunds;
	/**当日入金*/
	public double inMoney;
	/**当日出金*/
	public double outMoney;
	/**当前保证金*/
	public double margin;
	/**现货竞价保证金*/
	public double zvMargin;
	/**远期保证金*/
	public double runtimeMargin;
	/**交收保证金*/
	public double settleMargin;
	/**浮亏*/
	public double fl;
	/**交易日期*/
	public Date tradeDate;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("交易商代码[firmID]("+firmID+")"+str);
		sb.append("上日资金余额[yesFunds]("+yesFunds+")"+str);
		sb.append("当日资金余额[todayFunds]("+todayFunds+")"+str);
		sb.append("当日入金[inMoney]("+inMoney+")"+str);
		sb.append("当日出金[outMoney]("+outMoney+")"+str);
		sb.append("当前保证金[margin]("+margin+")"+str);
		sb.append("现货竞价保证金[zvMargin]("+zvMargin+")"+str);
		sb.append("远期保证金[runtimeMargin]("+runtimeMargin+")"+str);
		sb.append("交收保证金[settleMargin]("+settleMargin+")"+str);
		sb.append("浮亏[fl]("+fl+")"+str);
		sb.append("交易日期[tradeDate]("+tradeDate+")"+str);
		return sb.toString();
	}
}
