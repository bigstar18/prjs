package gnnt.bank.platform.vo;

import java.io.Serializable;

public class CapitalValueMoney
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String createDate;
  public String firmID;
  public String sysFirmID;
  public String systemID;
  public String bankID;
  public String sysActionID;
  public String platformActionID;
  public int sysType;
  public int platformType;
  public double sysMoney;
  public double money;
  
  public String getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(String createDate)
  {
    this.createDate = createDate;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getSysFirmID()
  {
    return this.sysFirmID;
  }
  
  public void setSysFirmID(String sysFirmID)
  {
    this.sysFirmID = sysFirmID;
  }
  
  public String getSystemID()
  {
    return this.systemID;
  }
  
  public void setSystemID(String systemID)
  {
    this.systemID = systemID;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }
  
  public String getSysActionID()
  {
    return this.sysActionID;
  }
  
  public void setSysActionID(String sysActionID)
  {
    this.sysActionID = sysActionID;
  }
  
  public String getPlatformActionID()
  {
    return this.platformActionID;
  }
  
  public void setPlatformActionID(String platformActionID)
  {
    this.platformActionID = platformActionID;
  }
  
  public int getSysType()
  {
    return this.sysType;
  }
  
  public void setSysType(int sysType)
  {
    this.sysType = sysType;
  }
  
  public int getPlatformType()
  {
    return this.platformType;
  }
  
  public void setPlatformType(int platformType)
  {
    this.platformType = platformType;
  }
  
  public double getSysMoney()
  {
    return this.sysMoney;
  }
  
  public void setSysMoney(double sysMoney)
  {
    this.sysMoney = sysMoney;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
}
