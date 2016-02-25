package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Market;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.util.List;

public abstract interface MarketDAO
  extends DAO
{
  public abstract List getMarketById(String paramString);
  
  public abstract List getMarkets(Market paramMarket);
  
  public abstract void insertMarket(Market paramMarket);
  
  public abstract void updateMarket(Market paramMarket);
  
  public abstract void updateMarketEndTime(Market paramMarket);
  
  public abstract void updateTradePassword(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void deleteMarketById(String paramString);
  
  public abstract String getMarketName(String paramString);
  
  public abstract String getMarketName();
  
  public abstract void deleteMarket();
  
  public abstract int checkMarket();
  
  public abstract SystemStatus getSystemStatus();
}
