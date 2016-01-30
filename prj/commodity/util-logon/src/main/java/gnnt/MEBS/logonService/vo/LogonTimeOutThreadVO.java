package gnnt.MEBS.logonService.vo;

import java.util.Map;

public class LogonTimeOutThreadVO extends BaseVO
{
  private static final long serialVersionUID = -2238209963458782972L;
  private long timeSpace;
  private Map<String, Long> auExpireTimeMap;

  public long getTimeSpace()
  {
    return this.timeSpace;
  }

  public void setTimeSpace(long paramLong)
  {
    this.timeSpace = paramLong;
  }

  public Map<String, Long> getAuExpireTimeMap()
  {
    return this.auExpireTimeMap;
  }

  public void setAuExpireTimeMap(Map<String, Long> paramMap)
  {
    this.auExpireTimeMap = paramMap;
  }
}