package gnnt.MEBS.transformhq.server.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPConfig
{
  private static Log log = LogFactory.getLog(IPConfig.class);
  private String port;
  private String iP;
  private String name;
  private String password;
  private long demoTime = 0L;
  private boolean status;
  private int roadLevel;
  
  public IPConfig() {}
  
  public IPConfig(String iP, String port, String name, String password, int roadLevel, long demoTime)
  {
    this.iP = iP;
    this.port = port;
    this.name = name;
    this.password = password;
    this.roadLevel = roadLevel;
    this.demoTime = demoTime;
  }
  
  public boolean isUsable()
  {
    return this.status;
  }
  
  public void changeStatusTrue()
  {
    if (!this.status)
    {
      this.status = true;
      log.warn("status change true :" + toString());
    }
  }
  
  public void changeStatusFalse()
  {
    if (this.status)
    {
      this.status = false;
      log.warn("status change false :" + toString());
    }
  }
  
  public String getPort()
  {
    return this.port;
  }
  
  public void setPort(String port)
  {
    this.port = port;
  }
  
  public String getiP()
  {
    return this.iP;
  }
  
  public void setiP(String iP)
  {
    this.iP = iP;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setStatus(boolean status)
  {
    this.status = status;
  }
  
  public void setRoadLevel(int roadLevel)
  {
    this.roadLevel = roadLevel;
  }
  
  public int getRoadLevel()
  {
    return this.roadLevel;
  }
  
  public void setDemoTime(long demoTime)
  {
    this.demoTime = demoTime;
  }
  
  public long getDemoTime()
  {
    return this.demoTime;
  }
  
  public String toString()
  {
    return "[" + getiP() + ":" + getPort() + "]";
  }
}
