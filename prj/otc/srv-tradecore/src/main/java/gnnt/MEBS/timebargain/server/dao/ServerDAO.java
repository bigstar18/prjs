package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.CommdityPriceProtect;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.ExchageRate;
import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.NotTradeDay;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface ServerDAO
  extends DAO
{
  public abstract List<Commodity> getCommodityList();
  
  public abstract List<Commodity> getCommodityListByID(String paramString);
  
  public abstract List<Commodity> getCommodityList(Integer paramInteger);
  
  public abstract List<TradeTime> getTradeTimes();
  
  public abstract Market getMarket();
  
  public abstract NotTradeDay getNotTradeDay();
  
  public abstract SystemStatus getSystemStatus();
  
  public abstract void updateSystemStatus(SystemStatus paramSystemStatus);
  
  public abstract void updateSystemRecoverTime(String paramString);
  
  public abstract void updateNextTradeDate(Date paramDate);
  
  public abstract void insertSysLog(SysLog paramSysLog);
  
  public abstract Date getCurDbDate();
  
  public abstract int getTradeSecCount();
  
  public abstract int getComtyCount();
  
  public abstract Consigner getConsigner(String paramString);
  
  public abstract Date getLastTradeDate();
  
  public abstract Map<String, CommdityPriceProtect> getCommdityPriceProtect();
  
  public abstract void updateCommodityStatusByS(String paramString, char paramChar);
  
  public abstract Date getLastClearDate();
  
  public abstract Map getDaySectionMap();
  
  public abstract void updateTradeSectionDateStatus(TradeTime paramTradeTime);
  
  public abstract List<HQServerInfo> getHQServerInfoList();
  
  public abstract void updateHQServer(int paramInt);
  
  public abstract List<ExchageRate> getExchageRates();
  
  public abstract long getDelayTradeTime();
}
