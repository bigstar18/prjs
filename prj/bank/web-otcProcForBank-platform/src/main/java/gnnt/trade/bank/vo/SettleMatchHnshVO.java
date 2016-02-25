package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class SettleMatchHnshVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String contractno;
  public Date m_settledate;
  public String m_commodityid;
  public String b_firmid;
  public String s_firmid;
  public double m_delprice;
  public int m_settleqty;
  public double huokuan;
  public String perct;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("contractno:" + this.contractno + sep);
    sb.append("m_settledate:" + this.m_settledate + sep);
    sb.append("m_commodityid:" + this.m_commodityid + sep);
    sb.append("b_firmid:" + this.b_firmid + sep);
    sb.append("s_firmid:" + this.s_firmid + sep);
    sb.append("m_delprice:" + this.m_delprice + sep);
    sb.append("m_settleqty:" + this.m_settleqty + sep);
    sb.append("huokuan:" + this.huokuan + sep);
    sb.append("perct:" + this.perct + sep);
    sb.append(sep);
    return sb.toString();
  }
}
