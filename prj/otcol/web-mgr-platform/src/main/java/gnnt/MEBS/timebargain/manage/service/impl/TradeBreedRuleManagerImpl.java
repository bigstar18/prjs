package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.TradeBreedRuleDAO;
import gnnt.MEBS.timebargain.manage.model.TradeBreedRuleGC;
import gnnt.MEBS.timebargain.manage.service.TradeBreedRuleManager;
import java.util.List;

public class TradeBreedRuleManagerImpl
  extends BaseManager
  implements TradeBreedRuleManager
{
  private TradeBreedRuleDAO dao;
  
  public void setTradeBreedRuleDAO(TradeBreedRuleDAO paramTradeBreedRuleDAO)
  {
    this.dao = paramTradeBreedRuleDAO;
  }
  
  public TradeBreedRuleGC getGM_TradeBreedRuleById(Integer paramInteger, String paramString)
  {
    return this.dao.getGM_TradeBreedRuleById(paramInteger, paramString);
  }
  
  public List getGM_TradeBreedRules(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    return this.dao.getGM_TradeBreedRules(paramTradeBreedRuleGC);
  }
  
  public void insertGM_TradeBreedRule(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.insertGM_TradeBreedRule(paramTradeBreedRuleGC);
  }
  
  public void updateGM_TradeBreedRule(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.updateGM_TradeBreedRule(paramTradeBreedRuleGC);
  }
  
  public void deleteGM_TradeBreedRuleById(String paramString, Integer paramInteger)
  {
    this.dao.deleteGM_TradeBreedRuleById(paramString, paramInteger);
  }
  
  public List getFirmCommodityIDFromMargin(String paramString1, String paramString2)
  {
    return this.dao.getFirmCommodityIDFromMargin(paramString1, paramString2);
  }
  
  public List getFirmCommodityIDFromFee(String paramString1, String paramString2)
  {
    return this.dao.getFirmCommodityIDFromFee(paramString1, paramString2);
  }
  
  public List getFirmCommodityIDFromMaxHoldQty(String paramString1, String paramString2)
  {
    return this.dao.getFirmCommodityIDFromMaxHoldQty(paramString1, paramString2);
  }
  
  public List getFirmCommodityIDFromMarginForAdd(String paramString1, String paramString2)
  {
    return this.dao.getFirmCommodityIDFromMarginForAdd(paramString1, paramString2);
  }
  
  public List getFirmCommodityIDFromFeeForAdd(String paramString1, String paramString2)
  {
    return this.dao.getFirmCommodityIDFromFeeForAdd(paramString1, paramString2);
  }
  
  public List getFirmCommodityIDFromMaxHoldQtyForAdd(String paramString1, String paramString2)
  {
    return this.dao.getFirmCommodityIDFromMaxHoldQtyForAdd(paramString1, paramString2);
  }
  
  public List getFirmIDFromMarginForAdd(String paramString)
  {
    return this.dao.getFirmIDFromMarginForAdd(paramString);
  }
  
  public List getFirmIDFromFeeForAdd(String paramString)
  {
    return this.dao.getFirmIDFromFeeForAdd(paramString);
  }
  
  public List getFirmIDFromMaxHoldQtyForAdd(String paramString)
  {
    return this.dao.getFirmIDFromMaxHoldQtyForAdd(paramString);
  }
  
  public List getFirmBreedMargin()
  {
    return this.dao.getFirmBreedMargin();
  }
  
  public TradeBreedRuleGC getFirmBreedMarginById(String paramString1, String paramString2)
  {
    return this.dao.getFirmBreedMarginById(paramString1, paramString2);
  }
  
  public void insertFirmBreedMargin(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.insertFirmBreedMargin(paramTradeBreedRuleGC);
  }
  
  public void updateFirmBreedMargin(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.updateFirmBreedMargin(paramTradeBreedRuleGC);
  }
  
  public void deleteFirmBreedMarginById(String paramString1, String paramString2)
  {
    this.dao.deleteFirmBreedMarginById(paramString1, paramString2);
  }
  
  public List getFirmBreedFee()
  {
    return this.dao.getFirmBreedFee();
  }
  
  public TradeBreedRuleGC getFirmBreedFeeById(String paramString1, String paramString2)
  {
    return this.dao.getFirmBreedFeeById(paramString1, paramString2);
  }
  
  public void insertFirmBreedFee(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.insertFirmBreedFee(paramTradeBreedRuleGC);
  }
  
  public void updateFirmBreedFee(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.updateFirmBreedFee(paramTradeBreedRuleGC);
  }
  
  public void deleteFirmBreedFeeById(String paramString1, String paramString2)
  {
    this.dao.deleteFirmBreedFeeById(paramString1, paramString2);
  }
  
  public List getFirmBreedMaxHoldQty()
  {
    return this.dao.getFirmBreedMaxHoldQty();
  }
  
  public TradeBreedRuleGC getFirmBreedMaxHoldQtyById(String paramString1, String paramString2)
  {
    return this.dao.getFirmBreedMaxHoldQtyById(paramString1, paramString2);
  }
  
  public void insertFirmBreedMaxHoldQty(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.insertFirmBreedMaxHoldQty(paramTradeBreedRuleGC);
  }
  
  public void updateFirmBreedMaxHoldQty(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.updateFirmBreedMaxHoldQty(paramTradeBreedRuleGC);
  }
  
  public void deleteFirmBreedMaxHoldQtyById(String paramString1, String paramString2)
  {
    this.dao.deleteFirmBreedMaxHoldQtyById(paramString1, paramString2);
  }
  
  public void updateSpacMargin(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    this.dao.updateSpacMargin(paramTradeBreedRuleGC);
  }
}
