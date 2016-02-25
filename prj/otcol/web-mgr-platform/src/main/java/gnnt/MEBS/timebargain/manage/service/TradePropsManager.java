package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.TradePropsDAO;
import gnnt.MEBS.timebargain.manage.model.TradeProps;
import java.util.List;

public abstract interface TradePropsManager
{
  public abstract void setTradePropsDAO(TradePropsDAO paramTradePropsDAO);
  
  public abstract void updateCustomerMaxHoldQty(String paramString, Long paramLong);
  
  public abstract void updateCustomermMinClearDeposit(String paramString, Double paramDouble);
  
  public abstract void updateCustomerMaxOverdraft(String paramString, Double paramDouble);
  
  public abstract void updateCustomerDefProps(TradeProps paramTradeProps);
  
  public abstract TradeProps getCustomerDefProps();
  
  public abstract List getGroupTradePropss(TradeProps paramTradeProps);
  
  public abstract TradeProps getGroupTradePropsById(Long paramLong);
  
  public abstract void deleteGroupTradePropsById(Long paramLong);
  
  public abstract void insertGroupTradeProps(TradeProps paramTradeProps);
  
  public abstract void updateGroupTradeProps(TradeProps paramTradeProps);
  
  public abstract List getCustomerTradePropss(TradeProps paramTradeProps);
  
  public abstract TradeProps getCustomerTradePropsById(String paramString);
  
  public abstract void deleteCustomerTradePropsById(String paramString);
  
  public abstract void insertCustomerTradeProps(TradeProps paramTradeProps);
  
  public abstract void updateCustomerTradeProps(TradeProps paramTradeProps);
  
  public abstract List getTradePropss(TradeProps paramTradeProps);
  
  public abstract void insertFirmValidTradeProps();
  
  public abstract List getTradePropsTFirm(TradeProps paramTradeProps);
  
  public abstract TradeProps getTradePropsTFirmById(String paramString);
  
  public abstract void saveTradePropsTFirm(TradeProps paramTradeProps);
}
