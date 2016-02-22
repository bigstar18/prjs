package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class HQServerInfo
  implements Serializable
{
  private static final long serialVersionUID = -463203699730254812L;
  private int serverId;
  private String serverName;
  private int serialNum;
  private String serverAddr;
  private int serverPort;
  private int rmiPort;
  private int reConnectLoop = 0;
  
  public HQServerInfo(int serverId, String serverAddr, int serverPort, int rmiPort, int serialNum)
  {
    this(serverId, "", serverAddr, serverPort, rmiPort, serialNum);
  }
  
  public HQServerInfo(int serverId, String serverName, String serverAddr, int serverPort, int rmiPort, int serialNum)
  {
    if ((serverPort < 0) || (serverPort > 65535)) {
      throw new IllegalArgumentException("port out of range:" + 
        serverPort);
    }
    if ((serverAddr == null) || (serverAddr.length() == 0)) {
      throw new IllegalArgumentException("serverAddr can't be null");
    }
    this.serverId = serverId;
    this.serverName = serverName;
    this.serverAddr = serverAddr;
    this.serverPort = serverPort;
    this.rmiPort = rmiPort;
    this.serialNum = serialNum;
  }
  
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
  
  public int getServerPort()
  {
    return this.serverPort;
  }
  
  public int getReConnectLoop()
  {
    return this.reConnectLoop;
  }
  
  public void setRmiPort(int rmiPort)
  {
    this.rmiPort = rmiPort;
  }
  
  public int getRmiPort()
  {
    return this.rmiPort;
  }
  
  public void setSerialNum(int serialNum)
  {
    this.serialNum = serialNum;
  }
  
  public int getSerialNum()
  {
    return this.serialNum;
  }
  
  public void setReConnectLoop(int reConnectLoop)
  {
    this.reConnectLoop = reConnectLoop;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.DEFAULT_STYLE);
  }
  
  public void setServerId(int serverId)
  {
    this.serverId = serverId;
  }
  
  public int getServerId()
  {
    return this.serverId;
  }
}
