package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class ChargeAgainstValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public long actionID;
  public long actionIDCA;
  public String funID;
  public String funIDCA;
  public Timestamp bankTime;
  public Timestamp createtime;
  public String note;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("bankID[" + this.bankID + "]" + str);
    sb.append("actionID[" + this.actionID + "]" + str);
    sb.append("actionIDCA[" + this.actionIDCA + "]" + str);
    sb.append("funID[" + this.funID + "]" + str);
    sb.append("funIDCA[" + this.funIDCA + "]" + str);
    sb.append("bankTime[" + this.bankTime + "]" + str);
    sb.append("createtime[" + this.createtime + "]" + str);
    sb.append(str);
    return sb.toString();
  }
}
