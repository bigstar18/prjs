package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class TradeResultValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long fundFlowId;
  public String firmid;
  public double amount;
  public double balance;
  public int oprCode;
  public double appendAmount;
  public Date date;
  public String bankid;
  public String account;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("fundFlowId:" + this.fundFlowId + sep);
    sb.append("firmid:" + this.firmid + sep);
    sb.append("amount:" + this.amount + sep);
    sb.append("balance:" + this.balance + sep);
    sb.append("oprCode:" + this.oprCode + sep);
    sb.append("appendAmount:" + this.appendAmount + sep);
    sb.append("date:" + this.date + sep);
    sb.append("bankid:" + this.bankid + sep);
    sb.append("account:" + this.account + sep);
    sb.append(sep);
    return sb.toString();
  }
}