package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class CapitalCompare
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String contact;
  public Date tradeDate;
  public String bankID;
  public double mInmoney;
  public double mOutmoney;
  public double bInmoney;
  public double bOutmoney;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("firmID[" + this.firmID + "]" + str);
    sb.append("tradeDate[" + this.tradeDate + "]" + str);
    sb.append("bankID[" + this.bankID + "]" + str);
    sb.append("mInmoney[" + this.mInmoney + "]" + str);
    sb.append("mOutmoney[" + this.mOutmoney + "]" + str);
    sb.append("bInmoney[" + this.bInmoney + "]" + str);
    sb.append("bOutmoney[" + this.bOutmoney + "]" + str);
    return sb.toString();
  }
}
