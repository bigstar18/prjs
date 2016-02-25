package gnnt.trade.bank.vo;

import gnnt.bank.platform.util.Tool;
import java.io.Serializable;
import java.util.Date;

public class QSRresult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public String firmID;
  public String firmName;
  public String account;
  public String account1;
  public double kyMoneyM;
  public double kyMoneyB;
  public double djMoneyM;
  public double djMoneyB;
  public double zckyMoney;
  public double zcdjMoney;
  public double moneyM;
  public double moneyB;
  public double zcMoney;
  public Date createDate;
  public Date tradeDate;
  public int status;
  public String note;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("银行编号[bankID](" + this.bankID + ")" + str);
    sb.append("银行名称[bankName](" + this.bankName + ")" + str);
    sb.append("交易商代码[firmID](" + this.firmID + ")" + str);
    sb.append("交易商名称[firmName](" + this.firmName + ")" + str);
    sb.append("银行账号[account](" + this.account + ")" + str);
    sb.append("交易商银行子账号[account1](" + this.account1 + ")" + str);
    sb.append("市场可用资金[kyMoneyM](" + this.kyMoneyM + ")" + str);
    sb.append("银行可用资金[kyMoneyB](" + this.kyMoneyB + ")" + str);
    sb.append("市场冻结资金[djMoneyM](" + this.djMoneyM + ")" + str);
    sb.append("银行冻结资金[djMoneyB](" + this.djMoneyB + ")" + str);
    sb.append("可用资金扎差[zckyMoney](" + this.zckyMoney + ")" + str);
    sb.append("冻结资金扎差[zcdjMoney](" + this.zcdjMoney + ")" + str);
    sb.append("市场总资金[moneyM](" + this.moneyM + ")" + str);
    sb.append("银行总资金[moneyB](" + this.moneyB + ")" + str);
    sb.append("资金扎差[zcMoney](" + this.zcMoney + ")" + str);
    sb.append("记录创建时间[createDate](" + (this.createDate == null ? "null" : Tool.fmtTime(this.createDate)) + ")" + str);
    sb.append("对账日期[tradeDate](" + (this.tradeDate == null ? "null" : Tool.fmtDate(this.tradeDate)) + ")" + str);
    sb.append("成功还是失败[status](" + this.status + ")" + str);
    sb.append("备注信息[note](" + this.note + ")" + str);
    return sb.toString();
  }
}
