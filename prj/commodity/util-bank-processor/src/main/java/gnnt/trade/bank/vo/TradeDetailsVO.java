package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class TradeDetailsVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public Date t_date;
  public String b_firmid;
  public String s_firmid;
  public double money;
  public int type;
  public String contractno;
  public String perct;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("t_date:" + this.t_date + sep);
    sb.append("b_firmid:" + this.b_firmid + sep);
    sb.append("s_firmid:" + this.s_firmid + sep);
    sb.append("money:" + this.money + sep);
    sb.append("type:" + this.type + sep);
    sb.append("contractno:" + this.contractno + sep);
    sb.append("perct:" + this.perct + sep);
    sb.append(sep);
    return sb.toString();
  }
}