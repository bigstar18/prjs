package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class MoneyInfoValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String id;
  public long m_Id;
  public String firmID;
  public String bankID;
  public String account;
  public int type;
  public double money;
  public double m_money;
  public Date compareDate;
  public String note;
  public Timestamp createtime;
  public int status;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("Id:" + this.id + sep);
    sb.append("m_Id:" + this.m_Id + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("account:" + this.account + sep);
    sb.append("type:" + this.type + sep);
    sb.append("money:" + this.money + sep);
    sb.append("m_money:" + this.m_money + sep);
    sb.append("compareDate:" + this.compareDate + sep);
    sb.append("Note:" + this.note + sep);
    sb.append("Createtime:" + this.createtime + sep);
    sb.append("status:" + this.status + sep);
    sb.append(sep);
    return sb.toString();
  }
}
