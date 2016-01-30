package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class CompareResult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int errorType;
  public String firmID;
  public String bankID;
  public String account;
  public String id;
  public long m_Id;
  public int type;
  public int m_type;
  public double money;
  public double m_money;
  public Date compareDate;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("errorType:" + this.errorType + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("account:" + this.account + sep);
    sb.append("Id:" + this.id + sep);
    sb.append("m_Id:" + this.m_Id + sep);
    sb.append("type:" + this.type + sep);
    sb.append("m_type:" + this.m_type + sep);
    sb.append("money:" + this.money + sep);
    sb.append("m_money:" + this.m_money + sep);
    sb.append("compareDate:" + this.compareDate + sep);
    sb.append(sep);
    return sb.toString();
  }
}