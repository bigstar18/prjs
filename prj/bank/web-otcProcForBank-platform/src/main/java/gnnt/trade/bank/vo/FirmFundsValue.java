package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class FirmFundsValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public double yesFunds;
  public double todayFunds;
  public double inMoney;
  public double outMoney;
  public double margin;
  public double zvMargin;
  public double runtimeMargin;
  public double settleMargin;
  public double fl;
  public Date tradeDate;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("交易商代码[firmID](" + this.firmID + ")" + str);
    sb.append("上日资金余额[yesFunds](" + this.yesFunds + ")" + str);
    sb.append("当日资金余额[todayFunds](" + this.todayFunds + ")" + str);
    sb.append("当日入金[inMoney](" + this.inMoney + ")" + str);
    sb.append("当日出金[outMoney](" + this.outMoney + ")" + str);
    sb.append("当前保证金[margin](" + this.margin + ")" + str);
    sb.append("现货竞价保证金[zvMargin](" + this.zvMargin + ")" + str);
    sb.append("远期保证金[runtimeMargin](" + this.runtimeMargin + ")" + str);
    sb.append("交收保证金[settleMargin](" + this.settleMargin + ")" + str);
    sb.append("浮亏[fl](" + this.fl + ")" + str);
    sb.append("交易日期[tradeDate](" + this.tradeDate + ")" + str);
    return sb.toString();
  }
}
