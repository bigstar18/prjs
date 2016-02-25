package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class TradesVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public Date t_date;
  public String firmid;
  public double marg;
  public double add_marg;
  public double settle_marg;
  public double liq_pl;
  public double t_spl;
  public double tradecomm;
  public double completecomm;
  public double oliq_l;
  public double payment;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("t_date:" + this.t_date + sep);
    sb.append("firmid:" + this.firmid + sep);
    sb.append("marg:" + this.marg + sep);
    sb.append("add_marg:" + this.add_marg + sep);
    sb.append("settle_marg:" + this.settle_marg + sep);
    sb.append("liq_pl:" + this.liq_pl + sep);
    sb.append("t_spl:" + this.t_spl + sep);
    sb.append("tradecomm:" + this.tradecomm + sep);
    sb.append("completecomm:" + this.completecomm + sep);
    sb.append("oliq_l:" + this.oliq_l + sep);
    sb.append("payment:" + this.payment + sep);
    sb.append(sep);
    return sb.toString();
  }
}
