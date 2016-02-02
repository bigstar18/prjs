package gnnt.MEBS.timebargain.plugin.condition.model;

public class RmiConf
{
  private int moduleID;
  private String hostIP;
  private String serviceName;
  private int port;
  private int rmiDataPort;
  
  public int getModuleID()
  {
    return this.moduleID;
  }
  
  public void setModuleID(int paramInt)
  {
    this.moduleID = paramInt;
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
  
  public String getServiceName()
  {
    return this.serviceName;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
}
