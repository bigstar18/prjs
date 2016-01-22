package gnnt.trade.bank.vo.bankdz.xy.sent.child;

import java.io.Serializable;

public class FirmRightValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String actionID;
  public String firmID;
  public String tradeFee;
  public String settleFee;
  public String account;
  public String currency = "001";

  public int type = 0;
  public double firmErrorMoney;
  public double cashMoney;
  public double billMoney;
  public double availableBalance;
  public double cash;
  public double credit;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("actionID:" + this.actionID + str);
    sb.append("firmID:" + this.firmID + str);
    sb.append("tradeFee:" + this.tradeFee + str);
    sb.append("settleFee:" + this.settleFee + str);
    sb.append("account:" + this.account + str);
    sb.append("currency:" + this.currency + str);
    sb.append("type:" + this.type + str);
    sb.append("firmErrorMoney:" + this.firmErrorMoney + str);
    sb.append("cashMoney:" + this.cashMoney + str);
    sb.append("billMoney:" + this.billMoney + str);
    sb.append("availableBalance:" + this.availableBalance + str);
    sb.append("cash:" + this.cash + str);
    sb.append("credit:" + this.credit + str);
    sb.append(str);
    return sb.toString();
  }
}