package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.dao.TradeRuleDAO;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import java.util.List;

public abstract interface TradeRuleManager
{
  public abstract void setTradeRuleDAO(TradeRuleDAO paramTradeRuleDAO);
  
  public abstract TradeRuleGC getGM_TradeRuleById(Integer paramInteger, String paramString);
  
  public abstract List getGM_TradeRules(TradeRuleGC paramTradeRuleGC);
  
  public abstract void insertGM_TradeRule(TradeRuleGC paramTradeRuleGC);
  
  public abstract void updateGM_TradeRule(TradeRuleGC paramTradeRuleGC);
  
  public abstract void deleteGM_TradeRuleById(String paramString, Integer paramInteger);
  
  public abstract List getFirmMargin();
  
  public abstract TradeRuleGC getFirmMarginById(String paramString1, String paramString2);
  
  public abstract void insertFirmMargin(TradeRuleGC paramTradeRuleGC);
  
  public abstract void updateFirmMargin(TradeRuleGC paramTradeRuleGC);
  
  public abstract void deleteFirmMarginById(String paramString1, String paramString2);
  
  public abstract List getFirmFee();
  
  public abstract TradeRuleGC getFirmFeeById(String paramString1, String paramString2);
  
  public abstract void insertFirmFee(TradeRuleGC paramTradeRuleGC);
  
  public abstract void updateFirmFee(TradeRuleGC paramTradeRuleGC);
  
  public abstract void deleteFirmFeeById(String paramString1, String paramString2);
  
  public abstract List getFirmMaxHoldQty();
  
  public abstract TradeRuleGC getFirmMaxHoldQtyById(String paramString1, String paramString2);
  
  public abstract void insertFirmMaxHoldQty(TradeRuleGC paramTradeRuleGC);
  
  public abstract void updateFirmMaxHoldQty(TradeRuleGC paramTradeRuleGC);
  
  public abstract void deleteFirmMaxHoldQtyById(String paramString1, String paramString2);
  
  public abstract void updateSpacMargin(TradeRuleGC paramTradeRuleGC);
  
  public abstract List getFirmMaxHoldQtyByFirmId(String paramString);
  
  public abstract Dealer getDealerByfirmId(String paramString);
}
