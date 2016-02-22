package gnnt.trade.bank.vo.bankdz.hx.sent.child;

import java.io.Serializable;

public class QSMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public double balanceChanges;
  public double frozenFunds;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("交易商可用资金变化量[balanceChanges](" + this.balanceChanges + ")" + str);
    sb.append("当前冻结资金[frozenFunds](" + this.frozenFunds + ")" + str);
    return sb.toString();
  }
}