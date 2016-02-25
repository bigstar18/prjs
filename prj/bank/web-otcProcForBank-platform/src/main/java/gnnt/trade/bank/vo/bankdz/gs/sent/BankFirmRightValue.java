package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;
import java.sql.Timestamp;

public class BankFirmRightValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankId;
  public Timestamp bdate;
  public String firmId;
  public double bankRight;
  public double maketRight;
  public int reason;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("bankId[" + this.bankId + "]" + str);
    sb.append("bdate[" + this.bdate + "]" + str);
    sb.append("firmId[" + this.firmId + "]" + str);
    sb.append("bankRight[" + this.bankRight + "]" + str);
    sb.append("maketRight[" + this.maketRight + "]" + str);
    sb.append(str);
    return sb.toString();
  }
  
  public Timestamp getBdate()
  {
    return this.bdate;
  }
  
  public void setBdate(Timestamp bdate)
  {
    this.bdate = bdate;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public double getBankRight()
  {
    return this.bankRight;
  }
  
  public void setBankRight(double bankRight)
  {
    this.bankRight = bankRight;
  }
  
  public double getMaketRight()
  {
    return this.maketRight;
  }
  
  public void setMaketRight(double maketRight)
  {
    this.maketRight = maketRight;
  }
  
  public int getReason()
  {
    return this.reason;
  }
  
  public void setReason(int reason)
  {
    this.reason = reason;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String bankId)
  {
    this.bankId = bankId;
  }
}
