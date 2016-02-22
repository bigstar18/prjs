package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FrozenBalanceVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmid;
  public int moduleid;
  public double frozenBalance;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("firmid" + this.firmid + sep);
    sb.append("moduleid:" + this.moduleid + sep);
    sb.append("frozenBalance:" + this.frozenBalance + sep);
    sb.append(sep);
    return sb.toString();
  }
}