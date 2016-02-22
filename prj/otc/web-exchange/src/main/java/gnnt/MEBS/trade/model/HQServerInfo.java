package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;

public class HQServerInfo
  extends Clone
{
  private Integer id;
  private String serverName;
  private String serverAddr;
  private Integer serverPort;
  private Integer serverRank;
  private String isUsed;
  
  public String getServerName()
  {
    return this.serverName;
  }
  
  public void setServerName(String serverName)
  {
    this.serverName = serverName;
  }
  
  public String getServerAddr()
  {
    return this.serverAddr;
  }
  
  public void setServerAddr(String serverAddr)
  {
    this.serverAddr = serverAddr;
  }
  
  public Integer getServerPort()
  {
    return this.serverPort;
  }
  
  public void setServerPort(Integer serverPort)
  {
    this.serverPort = serverPort;
  }
  
  public Integer getServerRank()
  {
    return this.serverRank;
  }
  
  public void setServerRank(Integer serverRank)
  {
    this.serverRank = serverRank;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getIsUsed()
  {
    return this.isUsed;
  }
  
  public void setIsUsed(String isUsed)
  {
    this.isUsed = isUsed;
  }
  
  public void setPrimary(String primary) {}
}
