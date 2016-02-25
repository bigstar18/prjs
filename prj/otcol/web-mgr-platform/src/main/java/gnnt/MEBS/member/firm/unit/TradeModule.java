package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;

public class TradeModule
  extends Clone
{
  private String moduleId;
  private String name;
  private String enabled;
  private String hostip;
  private long rmi_Port;
  private String mutex;
  private String issettle;
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(String paramString)
  {
    this.enabled = paramString;
  }
  
  public String getHostip()
  {
    return this.hostip;
  }
  
  public void setHostip(String paramString)
  {
    this.hostip = paramString;
  }
  
  public long getRmi_Port()
  {
    return this.rmi_Port;
  }
  
  public void setRmi_Port(long paramLong)
  {
    this.rmi_Port = paramLong;
  }
  
  public String getMutex()
  {
    return this.mutex;
  }
  
  public void setMutex(String paramString)
  {
    this.mutex = paramString;
  }
  
  public String getIssettle()
  {
    return this.issettle;
  }
  
  public void setIssettle(String paramString)
  {
    this.issettle = paramString;
  }
}
