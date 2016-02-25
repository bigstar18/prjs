package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Consigner;
import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.model.Trader;
import java.util.List;
import java.util.Map;

public abstract interface TraderDAO
  extends DAO
{
  public abstract Trader getTraderById(String paramString);
  
  public abstract List getTraders(Trader paramTrader);
  
  public abstract void insertTrader(Trader paramTrader);
  
  public abstract void updateTrader(Trader paramTrader);
  
  public abstract void deleteTraderById(String paramString);
  
  public abstract int login(String paramString1, String paramString2);
  
  public abstract void updateTraderPassword(String paramString1, String paramString2, String paramString3);
  
  public abstract List getCodeNotChoose(Trader paramTrader);
  
  public abstract String getOperateCode(Trader paramTrader);
  
  public abstract void updateOperateCode(Trader paramTrader);
  
  public abstract List getTradeTimes(TradeTime paramTradeTime);
  
  public abstract TradeTime getTradeTime(String paramString);
  
  public abstract void insertTradeTime(TradeTime paramTradeTime);
  
  public abstract void updateTradeTime(TradeTime paramTradeTime);
  
  public abstract void deleteTradeTimeById(String paramString);
  
  public abstract void updateStatusT(TradeTime paramTradeTime);
  
  public abstract void updateStatusTrader(Trader paramTrader);
  
  public abstract List getTradeTimeBreed(String paramString);
  
  public abstract List getConsigner();
  
  public abstract Consigner getConsigner(String paramString);
  
  public abstract void insertConsigner(Consigner paramConsigner);
  
  public abstract void updateConsigner(Consigner paramConsigner);
  
  public abstract void deleteConsignerById(String paramString);
  
  public abstract void updateStatusConsigner(Consigner paramConsigner);
  
  public abstract List getNotOperateFirm(Consigner paramConsigner);
  
  public abstract String getOperateFirm(Consigner paramConsigner);
  
  public abstract void updateOperateFirm(Consigner paramConsigner);
  
  public abstract void updateConsignerPassword(String paramString1, String paramString2, String paramString3);
  
  public abstract List getTradeTimeRelBreed(String paramString);
  
  public abstract TradeTime getNotTradeDay();
  
  public abstract void insertNotTradeDay(TradeTime paramTradeTime);
  
  public abstract void updateNotTradeDay(TradeTime paramTradeTime);
  
  public abstract List getTraderPrivilege(String paramString);
  
  public abstract void deleteTraderPrivilege(String paramString);
  
  public abstract void insertTraderPrivilege(Trader paramTrader);
  
  public abstract Trader getTraderPrivilegeById(Trader paramTrader);
  
  public abstract void insertNewTraderPrivilege(Trader paramTrader);
  
  public abstract void updateNewTraderPrivilege(Trader paramTrader);
  
  public abstract void deleteTraderPrivilegeById(Trader paramTrader);
  
  public abstract String getSysdate();
  
  public abstract List getTradeTimeId(String paramString);
  
  public abstract Map getDaySectionInfo();
  
  public abstract void updateDaySection(Map paramMap);
}
