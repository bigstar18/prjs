package gnnt.trade.bank.vo;

import gnnt.trade.bank.util.Tool;
import java.io.Serializable;
import java.util.Date;

public class CompareSumMoney
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public int inMoneyCount;
  public int outMoneyCount;
  public double inMoney;
  public double outMoney;
  public Date tradeDate;
  public int mb;
  
  public CompareSumMoney(String bankID, Date tradeDate, int mb)
  {
    this.bankID = bankID;
    this.tradeDate = tradeDate;
    this.mb = mb;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "/n";
    sb.append("bankID[" + this.bankID + "]" + str);
    sb.append("bankName[" + this.bankName + "]" + str);
    sb.append("inMoneyCount[" + this.inMoneyCount + "]" + str);
    sb.append("outMoneyCount[" + this.outMoneyCount + "]" + str);
    sb.append("inMoney[" + this.inMoney + "]" + str);
    sb.append("outMoney[" + this.outMoney + "]" + str);
    sb.append("tradeDate[" + Tool.fmtDate(this.tradeDate) + "]" + str);
    sb.append("mb[" + this.mb + "]" + str);
    return sb.toString();
  }
}
