package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class CapitalValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long iD;
  public String trader;
  public String funID;
  public long actionID;
  public String firmID;
  public String contact;
  public String bankID;
  public int type;
  public int launcher;
  public double money;
  public int status;
  public Timestamp bankTime;
  public Timestamp createtime;
  public String note;
  public String funID2;
  public String createdate;
  public String oldfunID;
  public int oldstatus = -1;
  public String bankName;
  public String account;
  public String accountName;
  public String firmType;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("iD:" + this.iD + sep);
    sb.append("trader:" + this.trader + sep);
    sb.append("funID:" + this.funID + sep);
    sb.append("actionID:" + this.actionID + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("contact:" + this.contact + sep);
    sb.append("bankID:" + this.bankID + sep);
    

    sb.append("type:" + this.type + sep);
    sb.append("money:" + this.money + sep);
    
    sb.append("status:" + this.status + sep);
    sb.append("bankTime:" + this.bankTime + sep);
    sb.append("createtime:" + this.createtime + sep);
    sb.append("note:" + this.note + sep);
    sb.append("funID2:" + this.funID2 + sep);
    sb.append("createdate:" + this.createdate + sep);
    
    sb.append("bankName:" + this.bankName + sep);
    sb.append("account:" + this.account + sep);
    sb.append("firmType:" + this.firmType + sep);
    sb.append(sep);
    return sb.toString();
  }
}
