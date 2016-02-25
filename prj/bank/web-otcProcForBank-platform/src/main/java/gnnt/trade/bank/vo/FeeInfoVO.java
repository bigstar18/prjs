package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class FeeInfoVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public int upLimit;
  public int downLimit;
  public int tMode;
  public double rate;
  public double maxRateValue;
  public double minRateValue;
  public String type;
  public Timestamp createTime;
  public Timestamp updateTime;
  public String userID;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("id:" + this.id + sep);
    sb.append("upLimit:" + this.upLimit + sep);
    sb.append("downLimit:" + this.downLimit + sep);
    sb.append("type:" + this.type + sep);
    sb.append("tMode:" + this.tMode + sep);
    sb.append("rate:" + this.rate + sep);
    sb.append("maxRateValue:" + this.maxRateValue + sep);
    sb.append("minRateValue:" + this.minRateValue + sep);
    sb.append("type:" + this.type + sep);
    sb.append("createTime:" + this.createTime + sep);
    sb.append("updateTime:" + this.updateTime + sep);
    sb.append("userID:" + this.userID + sep);
    sb.append(sep);
    return sb.toString();
  }
}
