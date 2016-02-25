package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class TradeRestDate
  extends Clone
{
  private String marketId;
  private String weekRest;
  private String yearRest;
  
  public String getMarketId()
  {
    return this.marketId;
  }
  
  public void setMarketId(String paramString)
  {
    this.marketId = paramString;
  }
  
  public String getWeekRest()
  {
    return this.weekRest;
  }
  
  public void setWeekRest(String paramString)
  {
    this.weekRest = paramString;
  }
  
  public String getYearRest()
  {
    return this.yearRest;
  }
  
  public void setYearRest(String paramString)
  {
    this.yearRest = paramString;
  }
}
