package gnnt.trade.bank.vo;

import java.io.Serializable;

public class SystemMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String systemID;
  public String systemName;
  public String rmiURL;
  public String startTime;
  public String endTime;
  
  public String toString()
  {
    return 
      "平台保存各交易系统（市场）信息:SystemMessage [systemID=" + this.systemID + ", systemName=" + this.systemName + "]";
  }
  
  public String getRmiURL()
  {
    return this.rmiURL;
  }
  
  public void setRmiURL(String rmiURL)
  {
    this.rmiURL = rmiURL;
  }
  
  public String getSystemID()
  {
    return this.systemID;
  }
  
  public void setSystemID(String systemID)
  {
    this.systemID = systemID;
  }
  
  public String getSystemName()
  {
    return this.systemName;
  }
  
  public void setSystemName(String systemName)
  {
    this.systemName = systemName;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
}
