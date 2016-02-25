package gnnt.trade.bank.vo.bankdz.xy.resave.child;

import java.io.Serializable;
import java.util.Date;

public class FirmDateValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public Date tradeDate;
  public String firmID;
  public String account;
  public String currency = "001";
  public int type;
  public int reason;
  public double balanceM;
  public double cashM;
  public double billM;
  public double useBalanceM;
  public double frozenCashM;
  public double frozenLoanM;
  public double balanceB;
  public double cashB;
  public double billB;
  public double useBalanceB;
  public double frozenCashB;
  public double frozenLoanB;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("firmID:" + this.firmID + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("tradeDate:" + this.tradeDate + str);
    sb.append("account:" + this.account + str);
    sb.append("currency:" + this.currency + str);
    sb.append("type:" + this.type + str);
    sb.append("reason:" + this.reason + str);
    sb.append(str);
    return sb.toString();
  }
}
