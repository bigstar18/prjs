package gnnt.trade.bank.vo.bankdz.pf.sent;

import java.io.Serializable;

public class FundsMarg
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String FIRM_ID;
  public String FUNDSTYPE;
  public double FUNDS;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("交易商代码(FIRM_ID)[" + this.FIRM_ID + "]" + str);
    sb.append("类型(FUNDSTYPE)[" + this.FUNDSTYPE + "]" + str);
    sb.append("余额(FUNDS)[" + this.FUNDS + "]" + str);
    return sb.toString();
  }
}