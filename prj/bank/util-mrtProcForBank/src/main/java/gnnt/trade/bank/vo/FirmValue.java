package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class FirmValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String name;
  public double maxAuditMoney;
  public double maxPerTransMoney;
  public double maxPerSglTransMoney;
  public int maxPerTransCount;
  public int status;
  public Timestamp registerDate;
  public Timestamp logoutDate;
  public String password;
  public double sysToSysBalnce;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("Name:" + this.name + sep);
    sb.append("maxAuditMoney:" + this.maxAuditMoney + sep);
    sb.append("maxPerTransMoney:" + this.maxPerTransMoney + sep);
    sb.append("maxPerTransCount:" + this.maxPerTransCount + sep);
    sb.append("Status:" + this.status + sep);
    sb.append("registerDate:" + this.registerDate + sep);
    sb.append("logoutDate:" + this.logoutDate + sep);
    sb.append(sep);
    return sb.toString();
  }
}