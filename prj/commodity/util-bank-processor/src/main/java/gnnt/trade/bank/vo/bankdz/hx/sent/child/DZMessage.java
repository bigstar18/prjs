package gnnt.trade.bank.vo.bankdz.hx.sent.child;

import java.io.Serializable;

public class DZMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public double todaybalance;
  public double rights;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("交易商可用资金[" + this.todaybalance + "](" + this.todaybalance + ")" + str);
    sb.append("交易商权益[" + this.rights + "](" + this.rights + ")" + str);
    return sb.toString();
  }
}