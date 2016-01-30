package gnnt.MEBS.logonService.po;

public class LogonConfigPO extends Clone
{
  private Integer configID;
  private String hostIP;
  private Integer port;
  private Integer dataPort;
  private String serviceName;
  private Integer logonMode;
  private String sysname;

  public Integer getConfigID()
  {
    return this.configID;
  }

  public void setConfigID(Integer paramInteger)
  {
    this.configID = paramInteger;
  }

  public String getHostIP()
  {
    return this.hostIP;
  }

  public void setHostIP(String paramString)
  {
    this.hostIP = paramString;
  }

  public Integer getPort()
  {
    return this.port;
  }

  public void setPort(Integer paramInteger)
  {
    this.port = paramInteger;
  }

  public Integer getDataPort()
  {
    return this.dataPort;
  }

  public void setDataPort(Integer paramInteger)
  {
    this.dataPort = paramInteger;
  }

  public String getServiceName()
  {
    return this.serviceName;
  }

  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }

  public Integer getLogonMode()
  {
    return this.logonMode;
  }

  public void setLogonMode(Integer paramInteger)
  {
    this.logonMode = paramInteger;
  }

  public String getSysname()
  {
    return this.sysname;
  }

  public void setSysname(String paramString)
  {
    this.sysname = paramString;
  }
}