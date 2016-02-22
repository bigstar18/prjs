package gnnt.MEBS.trade.model.vo;

import java.util.HashMap;
import java.util.Map;

public class HQServerInfoVO
{
  private Map<String, Map<String, Object>> commodityMap = new HashMap();
  private String serverAddr;
  private Integer serverPort;
  private Integer status;
  private Boolean isExistMusic;
  private String isUsed;
  
  public Boolean getIsExistMusic()
  {
    return this.isExistMusic;
  }
  
  public void setIsExistMusic(Boolean isExistMusic)
  {
    this.isExistMusic = isExistMusic;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public Map<String, Map<String, Object>> getCommodityMap()
  {
    return this.commodityMap;
  }
  
  public void setCommodityMap(Map<String, Map<String, Object>> commodityMap)
  {
    this.commodityMap = commodityMap;
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
  
  public String getIsUsed()
  {
    return this.isUsed;
  }
  
  public void setIsUsed(String isUsed)
  {
    this.isUsed = isUsed;
  }
}
