package gnnt.trade.bank.vo;

import gnnt.trade.bank.util.Tool;
import java.io.Serializable;
import java.util.Date;

public class ClearingStatusVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long ID;
  public String bankID;
  public Date tradeDate;
  public Date generalTime;
  public Date sendTime;
  public int generalStatus;
  public int sendStatus;
  public Date createTime;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("ID:" + this.ID + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("tradeDate:" + fmtDate(this.tradeDate) + str);
    sb.append("generalTime:" + fmtDate(this.generalTime) + str);
    sb.append("sendTime:" + fmtDate(this.sendTime) + str);
    sb.append("generalStatus:" + this.generalStatus + str);
    sb.append("sendStatus:" + this.sendStatus + str);
    sb.append("createTime:" + fmtDate(this.createTime) + str);
    return sb.toString();
  }
  
  private String fmtDate(Date date)
  {
    if (date == null) {
      return "";
    }
    return Tool.fmtTime(date);
  }
}
