package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class RgstCapitalValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long iD;
  public long actionID;
  public String bankID;
  public String firmID;
  public String account;
  public String account1;
  public String accountName;
  public int status;
  public int type;
  public Timestamp createTime;
  public Timestamp compleTime;
  public String cardType;
  public String card;
  public String note;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("actionID:" + this.actionID + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("account:" + this.account + sep);
    sb.append("account1:" + this.account1 + sep);
    sb.append("accountName:" + this.accountName + sep);
    sb.append("status:" + this.status + sep);
    sb.append("createTime:" + this.createTime + sep);
    sb.append("compleTime:" + this.compleTime + sep);
    sb.append("cardType:" + this.cardType + sep);
    sb.append("card:" + this.card + sep);
    return sb.toString();
  }
}