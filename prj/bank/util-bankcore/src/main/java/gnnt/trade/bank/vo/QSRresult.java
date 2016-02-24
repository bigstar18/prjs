package gnnt.trade.bank.vo;
import gnnt.trade.bank.util.Tool;
import java.io.Serializable;
/**
 * 保存各个银行清算结果信息
 */
public class QSRresult implements Serializable{
	private static final long serialVersionUID= 1L;
	/**银行编号*/
	public String bankID;
	/**银行名称*/
	public String bankName;
	/**交易商代码*/
	public String firmID;
	/**交易商名称*/
	public String firmName;
	/**银行账号*/
	public String account;
	/**交易商银行子账号*/
	public String account1;
	/**市场可用资金*/
	public double kyMoneyM;
	/**银行可用资金*/
	public double kyMoneyB;
	/**市场冻结资金*/
	public double djMoneyM;
	/**银行冻结资金*/
	public double djMoneyB;
	/**可用资金扎差*/
	public double zckyMoney;
	/**冻结资金扎差*/
	public double zcdjMoney;
	/**市场总资金*/
	public double moneyM;
	/**银行总资金*/
	public double moneyB;
	/**资金扎差*/
	public double zcMoney;
	/**记录创建时间*/
	public java.util.Date createDate;
	/**对账日期*/
	public java.util.Date tradeDate;
	/**成功还是失败(0:成功、1:失败、2:可用余额不等、3:冻结余额不等、4:总余额不等、6:账号资金异常)*/
	public int status;
	/**备注信息*/
	public String note;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("银行编号[bankID]("+bankID+")"+str);
		sb.append("银行名称[bankName]("+bankName+")"+str);
		sb.append("交易商代码[firmID]("+firmID+")"+str);
		sb.append("交易商名称[firmName]("+firmName+")"+str);
		sb.append("银行账号[account]("+account+")"+str);
		sb.append("交易商银行子账号[account1]("+account1+")"+str);
		sb.append("市场可用资金[kyMoneyM]("+kyMoneyM+")"+str);
		sb.append("银行可用资金[kyMoneyB]("+kyMoneyB+")"+str);
		sb.append("市场冻结资金[djMoneyM]("+djMoneyM+")"+str);
		sb.append("银行冻结资金[djMoneyB]("+djMoneyB+")"+str);
		sb.append("可用资金扎差[zckyMoney]("+zckyMoney+")"+str);
		sb.append("冻结资金扎差[zcdjMoney]("+zcdjMoney+")"+str);
		sb.append("市场总资金[moneyM]("+moneyM+")"+str);
		sb.append("银行总资金[moneyB]("+moneyB+")"+str);
		sb.append("资金扎差[zcMoney]("+zcMoney+")"+str);
		sb.append("记录创建时间[createDate]("+(createDate==null ? "null" : Tool.fmtTime(createDate))+")"+str);
		sb.append("对账日期[tradeDate]("+(tradeDate==null ? "null" : Tool.fmtDate(tradeDate))+")"+str);
		sb.append("成功还是失败[status]("+status+")"+str);
		sb.append("备注信息[note]("+note+")"+str);
		return sb.toString();
	}
}
