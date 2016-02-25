package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class TradeModule
  extends Clone
{
  private String moduleId;
  private String name;
  private String enabled;
  private String hostIp;
  private int rmi_Port;
  private String mutex;
  private String isSettle;
  
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
  
  public String getHostIp()
  {
    return this.hostIp;
  }
  
  public void setHostIp(String paramString)
  {
    this.hostIp = paramString;
  }
  
  public int getRmi_Port()
  {
    return this.rmi_Port;
  }
  
  public void setRmi_Port(int paramInt)
  {
    this.rmi_Port = paramInt;
  }
  
  public String getMutex()
  {
    return this.mutex;
  }
  
  public void setMutex(String paramString)
  {
    this.mutex = paramString;
  }
  
  public String getIsSettle()
  {
    return this.isSettle;
  }
  
  public void setIsSettle(String paramString)
  {
    this.isSettle = paramString;
  }
}
