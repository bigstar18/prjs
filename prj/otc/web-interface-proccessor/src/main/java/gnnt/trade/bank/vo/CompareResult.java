package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class CompareResult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int errorType;
  public String bankID;
  public String firmID;
  public String contact;
  public String account;
  public String account1;
  public long actionID;
  public String funID;
  public int type;
  public double money;
  public Date tradeDate;
  public int status;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("---" + getClass().getName() + "---" + sep);
    sb.append("errorType:" + this.errorType + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("contact:" + this.contact + sep);
    sb.append("account:" + this.account + sep);
    sb.append("account1:" + this.account1 + sep);
    sb.append("actionID:" + this.actionID + sep);
    sb.append("funID:" + this.funID + sep);
    sb.append("type:" + this.type + sep);
    sb.append("money:" + this.money + sep);
    sb.append("tradeDate:" + this.tradeDate + sep);
    sb.append(sep);
    return sb.toString();
  }
}
