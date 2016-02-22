package gnnt.trade.bank.vo.bankdz.xy.sent.child;

import java.io.Serializable;

public class FirmDZValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String account;
  public String currency = "001";

  public int type = 0;
  public double firmRights;
  public double cashRights;
  public double billRights;
  public double availableBalance;
  public double butfunds;
  public double cash;
  public double credit;
  public double quanyibh;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("firmID:" + this.firmID + str);
    sb.append("account:" + this.account + str);
    sb.append("currency:" + this.currency + str);
    sb.append("type:" + this.type + str);
    sb.append("firmRights:" + this.firmRights + str);
    sb.append("cashRights:" + this.cashRights + str);
    sb.append("billRights:" + this.billRights + str);
    sb.append("availableBalance:" + this.availableBalance + str);
    sb.append("butfunds:" + this.butfunds + str);
    sb.append("cash:" + this.cash + str);
    sb.append("credit:" + this.credit + str);
    sb.append("quanyibh:" + this.quanyibh + str);
    sb.append(str);
    return sb.toString();
  }
}