package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VOnlineMonitor
  extends Clone
{
  private String memberno;
  private String traderid;
  private String tradername;
  private String traderip;
  private String onlinetime;
  private String tradercatalog;
  
  public String getId()
  {
    return this.traderid;
  }
  
  public void setPrimary(String primary) {}
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setTradername(String tradername)
  {
    this.tradername = tradername;
  }
  
  public String getTradername()
  {
    return this.tradername;
  }
  
  public void setTraderip(String traderip)
  {
    this.traderip = traderip;
  }
  
  public String getTraderip()
  {
    return this.traderip;
  }
  
  public void setTraderid(String traderid)
  {
    this.traderid = traderid;
  }
  
  public String getTraderid()
  {
    return this.traderid;
  }
  
  public void setOnlinetime(String onlinetime)
  {
    this.onlinetime = onlinetime;
  }
  
  public String getOnlinetime()
  {
    return this.onlinetime;
  }
  
  public void setTradercatalog(String tradercatalog)
  {
    this.tradercatalog = tradercatalog;
  }
  
  public String getTradercatalog()
  {
    return this.tradercatalog;
  }
}
