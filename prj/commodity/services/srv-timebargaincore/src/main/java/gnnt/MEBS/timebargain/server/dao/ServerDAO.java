package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.NotTradeDay;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.model.Trader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface ServerDAO
  extends DAO
{
  public abstract Map getFirmMap();
  
  public abstract Map getTraderMap();
  
  public abstract Map getConsignerMap();
  
  public abstract Map getCustomerMap();
  
  public abstract Map getFirmMarginMap(String paramString);
  
  public abstract Map getFirmFeeMap(String paramString);
  
  public abstract Map getTariffMap();
  
  public abstract List getCommodityList();
  
  public abstract List getCommodityListByID(String paramString);
  
  public abstract List getCommodityList(Integer paramInteger);
  
  public abstract List getQuotationList();
  
  public abstract List getQuotationList(String paramString);
  
  public abstract List queryQuotationByTime(Date paramDate);
  
  public abstract List getTradeTimes();
  
  public abstract Market getMarket();
  
  public abstract NotTradeDay getNotTradeDay();
  
  public abstract SystemStatus getSystemStatus();
  
  public abstract void updateSystemStatus(SystemStatus paramSystemStatus);
  
  public abstract void updateSystemRecoverTime(String paramString);
  
  public abstract void insertSysLog(SysLog paramSysLog);
  
  public abstract Date getCurDbDate();
  
  public abstract int getTradeSecCount();
  
  public abstract int getComtyCount();
  
  public abstract Trader getOneTrader(String paramString);
  
  public abstract Firm getOneFirm(String paramString);
  
  public abstract Map getCusByFirm(String paramString);
  
  public abstract Consigner getConsigner(String paramString);
  
  public abstract String getFirmByCustmID(String paramString);
  
  public abstract List getFirmIDList();
  
  public abstract Date getLastClearDate();
  
  public abstract Map getDaySectionMap();
  
  public abstract void updateTradeSectionDateStatus(TradeTime paramTradeTime);
  
  public abstract void adjustQuotationTime(Timestamp paramTimestamp1, Timestamp paramTimestamp2, Timestamp paramTimestamp3);
  
  public abstract List getQuotationsByTrade();
  
  public abstract String getTraderIDByUserID(String paramString);
}
