package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.dao.TradeRuleDAO;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import gnnt.MEBS.timebargain.manage.service.TradeRuleManager;
import java.util.List;

public class TradeRuleManagerImpl
  extends BaseManager
  implements TradeRuleManager
{
  private TradeRuleDAO dao;
  
  public void setTradeRuleDAO(TradeRuleDAO paramTradeRuleDAO)
  {
    this.dao = paramTradeRuleDAO;
  }
  
  public TradeRuleGC getGM_TradeRuleById(Integer paramInteger, String paramString)
  {
    return this.dao.getGM_TradeRuleById(paramInteger, paramString);
  }
  
  public List getGM_TradeRules(TradeRuleGC paramTradeRuleGC)
  {
    return this.dao.getGM_TradeRules(paramTradeRuleGC);
  }
  
  public void insertGM_TradeRule(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.insertGM_TradeRule(paramTradeRuleGC);
  }
  
  public void updateGM_TradeRule(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.updateGM_TradeRule(paramTradeRuleGC);
  }
  
  public void deleteGM_TradeRuleById(String paramString, Integer paramInteger)
  {
    this.dao.deleteGM_TradeRuleById(paramString, paramInteger);
  }
  
  public List getFirmMargin()
  {
    return this.dao.getFirmMargin();
  }
  
  public TradeRuleGC getFirmMarginById(String paramString1, String paramString2)
  {
    return this.dao.getFirmMarginById(paramString1, paramString2);
  }
  
  public void insertFirmMargin(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.insertFirmMargin(paramTradeRuleGC);
  }
  
  public void updateFirmMargin(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.updateFirmMargin(paramTradeRuleGC);
  }
  
  public void deleteFirmMarginById(String paramString1, String paramString2)
  {
    this.dao.deleteFirmMarginById(paramString1, paramString2);
  }
  
  public List getFirmFee()
  {
    return this.dao.getFirmFee();
  }
  
  public TradeRuleGC getFirmFeeById(String paramString1, String paramString2)
  {
    return this.dao.getFirmFeeById(paramString1, paramString2);
  }
  
  public void insertFirmFee(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.insertFirmFee(paramTradeRuleGC);
  }
  
  public void updateFirmFee(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.updateFirmFee(paramTradeRuleGC);
  }
  
  public void deleteFirmFeeById(String paramString1, String paramString2)
  {
    this.dao.deleteFirmFeeById(paramString1, paramString2);
  }
  
  public List getFirmMaxHoldQty()
  {
    return this.dao.getFirmMaxHoldQty();
  }
  
  public TradeRuleGC getFirmMaxHoldQtyById(String paramString1, String paramString2)
  {
    return this.dao.getFirmMaxHoldQtyById(paramString1, paramString2);
  }
  
  public void insertFirmMaxHoldQty(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.insertFirmMaxHoldQty(paramTradeRuleGC);
  }
  
  public void updateFirmMaxHoldQty(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.updateFirmMaxHoldQty(paramTradeRuleGC);
  }
  
  public void deleteFirmMaxHoldQtyById(String paramString1, String paramString2)
  {
    this.dao.deleteFirmMaxHoldQtyById(paramString1, paramString2);
  }
  
  public void updateSpacMargin(TradeRuleGC paramTradeRuleGC)
  {
    this.dao.updateSpacMargin(paramTradeRuleGC);
  }
  
  public List getFirmMaxHoldQtyByFirmId(String paramString)
  {
    return this.dao.getFirmMaxHoldQtyByFirmId(paramString);
  }
  
  public Dealer getDealerByfirmId(String paramString)
  {
    return this.dao.getDealerByfirmId(paramString);
  }
}
