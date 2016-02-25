package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class MarketConfig
  extends Clone
{
  private String marketId;
  private int financeStatus;
  private String submitEndTime;
  
  public String getMarketId()
  {
    return this.marketId;
  }
  
  public void setMarketId(String paramString)
  {
    this.marketId = paramString;
  }
  
  public int getFinanceStatus()
  {
    return this.financeStatus;
  }
  
  public void setFinanceStatus(int paramInt)
  {
    this.financeStatus = paramInt;
  }
  
  public String getSubmitEndTime()
  {
    return this.submitEndTime;
  }
  
  public void setSubmitEndTime(String paramString)
  {
    this.submitEndTime = paramString;
  }
}
