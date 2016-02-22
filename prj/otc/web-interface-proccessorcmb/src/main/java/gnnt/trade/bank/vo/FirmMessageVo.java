package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmMessageVo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmid;
  public String firmType;
  public String status;
  public String Password;
  
  public String getFirmid()
  {
    return this.firmid;
  }
  
  public void setFirmid(String firmid)
  {
    this.firmid = firmid;
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String password)
  {
    this.Password = password;
  }
  
  public String toString()
  {
    String str = "交易员ID[" + this.firmid + "],客户类型[" + this.firmType + "]状态[" + this.status + "]";
    return str;
  }
}
