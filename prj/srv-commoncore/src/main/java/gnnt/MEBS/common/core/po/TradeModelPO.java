package gnnt.MEBS.common.core.po;

import java.io.Serializable;

public class TradeModelPO
  extends Clone
  implements Serializable
{
  private static final long serialVersionUID = -4694713224538328888L;
  private int moduleID;
  private String cnName;
  private String enName;
  private String shortName;
  private String addFirmFn;
  private String updateFirmStatusFn;
  private String delFirmFn;
  private String isFirmSet;
  private String hostIP;
  private int port;
  private int rmiDataPort;
  private String isBalanceCheck;
  
  public int getModuleID()
  {
    return this.moduleID;
  }
  
  public void setModuleID(int paramInt)
  {
    this.moduleID = paramInt;
  }
  
  public String getCnName()
  {
    return this.cnName;
  }
  
  public void setCnName(String paramString)
  {
    this.cnName = paramString;
  }
  
  public String getEnName()
  {
    return this.enName;
  }
  
  public void setEnName(String paramString)
  {
    this.enName = paramString;
  }
  
  public String getShortName()
  {
    return this.shortName;
  }
  
  public void setShortName(String paramString)
  {
    this.shortName = paramString;
  }
  
  public String getAddFirmFn()
  {
    return this.addFirmFn;
  }
  
  public void setAddFirmFn(String paramString)
  {
    this.addFirmFn = paramString;
  }
  
  public String getUpdateFirmStatusFn()
  {
    return this.updateFirmStatusFn;
  }
  
  public void setUpdateFirmStatusFn(String paramString)
  {
    this.updateFirmStatusFn = paramString;
  }
  
  public String getDelFirmFn()
  {
    return this.delFirmFn;
  }
  
  public void setDelFirmFn(String paramString)
  {
    this.delFirmFn = paramString;
  }
  
  public String getIsFirmSet()
  {
    return this.isFirmSet;
  }
  
  public void setIsFirmSet(String paramString)
  {
    this.isFirmSet = paramString;
  }
  
  public String getHostIP()
  {
    return this.hostIP;
  }
  
  public void setHostIP(String paramString)
  {
    this.hostIP = paramString;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public void setPort(int paramInt)
  {
    this.port = paramInt;
  }
  
  public int getRmiDataPort()
  {
    return this.rmiDataPort;
  }
  
  public void setRmiDataPort(int paramInt)
  {
    this.rmiDataPort = paramInt;
  }
  
  public String getIsBalanceCheck()
  {
    return this.isBalanceCheck;
  }
  
  public void setIsBalanceCheck(String paramString)
  {
    this.isBalanceCheck = paramString;
  }
}
