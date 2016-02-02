package gnnt.MEBS.transformhq.server.model;

public class LocalIpConfig
{
  private String ip;
  private int socketPort;
  private int rmiPort;
  
  public LocalIpConfig() {}
  
  public LocalIpConfig(String ip, int socketPort, int rmiPort)
  {
    this.ip = ip;
    this.socketPort = socketPort;
    this.rmiPort = rmiPort;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public int getSocketPort()
  {
    return this.socketPort;
  }
  
  public void setSocketPort(int socketPort)
  {
    this.socketPort = socketPort;
  }
  
  public int getRmiPort()
  {
    return this.rmiPort;
  }
  
  public void setRmiPort(int rmiPort)
  {
    this.rmiPort = rmiPort;
  }
  
  public String toString()
  {
    return "ip:" + this.ip + " serverPort:" + this.socketPort + " rmiPort:" + this.rmiPort;
  }
}
