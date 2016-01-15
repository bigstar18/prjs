package gnnt.MEBS.activeUser.vo;

import java.util.Map;

public class AULogonTimeOutThreadVO extends AUBaseVO
{
  private static final long serialVersionUID = -2238209963458782972L;
  private long timeSpace;
  private Map<String, Long> auExpireTimeMap;

  public long getTimeSpace()
  {
    return this.timeSpace;
  }

  public void setTimeSpace(long timeSpace)
  {
    this.timeSpace = timeSpace;
  }

  public Map<String, Long> getAuExpireTimeMap()
  {
    return this.auExpireTimeMap;
  }

  public void setAuExpireTimeMap(Map<String, Long> auExpireTimeMap)
  {
    this.auExpireTimeMap = auExpireTimeMap;
  }
}