package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.MarketDAO;
import gnnt.MEBS.timebargain.manage.model.Market;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.util.List;

public class MarketManagerImpl
  extends BaseManager
  implements MarketManager
{
  private MarketDAO dao;
  
  public void setMarketDAO(MarketDAO paramMarketDAO)
  {
    this.dao = paramMarketDAO;
  }
  
  public List getMarketById(String paramString)
  {
    return this.dao.getMarketById(paramString);
  }
  
  public List getMarkets(Market paramMarket)
  {
    return this.dao.getMarkets(paramMarket);
  }
  
  public void insertMarket(Market paramMarket)
  {
    this.dao.insertMarket(paramMarket);
  }
  
  public void updateMarket(Market paramMarket)
  {
    this.dao.updateMarket(paramMarket);
  }
  
  public void updateMarketEndTime(Market paramMarket)
  {
    this.dao.updateMarketEndTime(paramMarket);
  }
  
  public void updateTradePassword(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.dao.updateTradePassword(paramString1, paramString2, paramString3, paramString4);
  }
  
  public void deleteMarketById(String paramString)
  {
    this.dao.deleteMarketById(paramString);
  }
  
  public String getMarketName(String paramString)
  {
    return this.dao.getMarketName(paramString);
  }
  
  public String getMarketName()
  {
    return this.dao.getMarketName();
  }
  
  public void deleteMarket()
  {
    this.dao.deleteMarket();
  }
  
  public int checkMarket()
  {
    return this.dao.checkMarket();
  }
  
  public SystemStatus getSystemStatus()
  {
    return this.dao.getSystemStatus();
  }
}
