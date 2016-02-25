package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.TradeBreedRuleGC;
import java.util.List;

public abstract interface TradeBreedRuleDAO
  extends DAO
{
  public abstract TradeBreedRuleGC getGM_TradeBreedRuleById(Integer paramInteger, String paramString);
  
  public abstract List getGM_TradeBreedRules(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void insertGM_TradeBreedRule(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void updateGM_TradeBreedRule(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void deleteGM_TradeBreedRuleById(String paramString, Integer paramInteger);
  
  public abstract List getFirmCommodityIDFromMargin(String paramString1, String paramString2);
  
  public abstract List getFirmCommodityIDFromFee(String paramString1, String paramString2);
  
  public abstract List getFirmCommodityIDFromMaxHoldQty(String paramString1, String paramString2);
  
  public abstract List getFirmCommodityIDFromMarginForAdd(String paramString1, String paramString2);
  
  public abstract List getFirmCommodityIDFromFeeForAdd(String paramString1, String paramString2);
  
  public abstract List getFirmCommodityIDFromMaxHoldQtyForAdd(String paramString1, String paramString2);
  
  public abstract List getFirmIDFromMarginForAdd(String paramString);
  
  public abstract List getFirmIDFromFeeForAdd(String paramString);
  
  public abstract List getFirmIDFromMaxHoldQtyForAdd(String paramString);
  
  public abstract List getFirmBreedMargin();
  
  public abstract TradeBreedRuleGC getFirmBreedMarginById(String paramString1, String paramString2);
  
  public abstract void insertFirmBreedMargin(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void updateFirmBreedMargin(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void deleteFirmBreedMarginById(String paramString1, String paramString2);
  
  public abstract List getFirmBreedFee();
  
  public abstract TradeBreedRuleGC getFirmBreedFeeById(String paramString1, String paramString2);
  
  public abstract void insertFirmBreedFee(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void updateFirmBreedFee(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void deleteFirmBreedFeeById(String paramString1, String paramString2);
  
  public abstract List getFirmBreedMaxHoldQty();
  
  public abstract TradeBreedRuleGC getFirmBreedMaxHoldQtyById(String paramString1, String paramString2);
  
  public abstract void insertFirmBreedMaxHoldQty(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void updateFirmBreedMaxHoldQty(TradeBreedRuleGC paramTradeBreedRuleGC);
  
  public abstract void deleteFirmBreedMaxHoldQtyById(String paramString1, String paramString2);
  
  public abstract void updateSpacMargin(TradeBreedRuleGC paramTradeBreedRuleGC);
}
