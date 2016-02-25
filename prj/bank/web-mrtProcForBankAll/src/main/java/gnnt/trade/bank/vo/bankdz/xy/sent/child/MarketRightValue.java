package gnnt.trade.bank.vo.bankdz.xy.sent.child;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketRightValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public BigDecimal maketMoney;
  public double bankErrorMoney;
  public double maketErrorMoney;
  public String currency = "001";
  public int type = 0;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("maketMoney:" + this.maketMoney + str);
    sb.append("bankErrorMoney:" + this.bankErrorMoney + str);
    sb.append("maketErrorMoney:" + this.maketErrorMoney + str);
    sb.append("currency:" + this.currency + str);
    sb.append("type:" + this.type + str);
    sb.append(str);
    return sb.toString();
  }
}
