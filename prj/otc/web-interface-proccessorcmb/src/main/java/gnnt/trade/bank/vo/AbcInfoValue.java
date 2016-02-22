package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class AbcInfoValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String account1;
  public String orderNo;
  public long actionID;
  public int type;
  public String signInfo;
  public Date time;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("depositfirmID:" + this.firmID + sep);
    sb.append("depositAccount1:" + this.account1 + sep);
    sb.append("orderNo:" + this.orderNo + sep);
    sb.append("depositactionID:" + this.actionID + sep);
    sb.append("type:" + this.type + sep);
    sb.append("signInfo:" + this.signInfo + sep);
    sb.append("time:" + this.time + sep);
    sb.append(sep);
    return sb.toString();
  }
}
