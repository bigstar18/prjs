package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.TradePropsDAO;
import gnnt.MEBS.timebargain.manage.model.TradeProps;
import gnnt.MEBS.timebargain.manage.service.TradePropsManager;
import java.util.List;

public class TradePropsManagerImpl
  extends BaseManager
  implements TradePropsManager
{
  private TradePropsDAO dao;
  
  public void setTradePropsDAO(TradePropsDAO paramTradePropsDAO)
  {
    this.dao = paramTradePropsDAO;
  }
  
  public void updateCustomerMaxHoldQty(String paramString, Long paramLong)
  {
    this.dao.updateCustomerMaxHoldQty(paramString, paramLong);
  }
  
  public void updateCustomermMinClearDeposit(String paramString, Double paramDouble)
  {
    this.dao.updateCustomermMinClearDeposit(paramString, paramDouble);
  }
  
  public void updateCustomerMaxOverdraft(String paramString, Double paramDouble)
  {
    this.dao.updateCustomerMaxOverdraft(paramString, paramDouble);
  }
  
  public void updateCustomerDefProps(TradeProps paramTradeProps)
  {
    this.dao.updateCustomerDefProps(paramTradeProps);
  }
  
  public TradeProps getCustomerDefProps()
  {
    return this.dao.getCustomerDefProps();
  }
  
  public List getGroupTradePropss(TradeProps paramTradeProps)
  {
    return this.dao.getGroupTradePropss(paramTradeProps);
  }
  
  public TradeProps getGroupTradePropsById(Long paramLong)
  {
    return this.dao.getGroupTradePropsById(paramLong);
  }
  
  public void deleteGroupTradePropsById(Long paramLong)
  {
    this.dao.deleteGroupTradePropsById(paramLong);
  }
  
  public void insertGroupTradeProps(TradeProps paramTradeProps)
  {
    this.dao.insertGroupTradeProps(paramTradeProps);
  }
  
  public void updateGroupTradeProps(TradeProps paramTradeProps)
  {
    this.dao.updateGroupTradeProps(paramTradeProps);
  }
  
  public List getCustomerTradePropss(TradeProps paramTradeProps)
  {
    return this.dao.getCustomerTradePropss(paramTradeProps);
  }
  
  public TradeProps getCustomerTradePropsById(String paramString)
  {
    return this.dao.getCustomerTradePropsById(paramString);
  }
  
  public void deleteCustomerTradePropsById(String paramString)
  {
    this.dao.deleteCustomerTradePropsById(paramString);
  }
  
  public void insertCustomerTradeProps(TradeProps paramTradeProps)
  {
    this.dao.insertCustomerTradeProps(paramTradeProps);
  }
  
  public void updateCustomerTradeProps(TradeProps paramTradeProps)
  {
    this.dao.updateCustomerTradeProps(paramTradeProps);
  }
  
  public List getTradePropss(TradeProps paramTradeProps)
  {
    return this.dao.getTradePropss(paramTradeProps);
  }
  
  public void insertFirmValidTradeProps()
  {
    this.dao.insertFirmValidTradeProps();
  }
  
  public List getTradePropsTFirm(TradeProps paramTradeProps)
  {
    return this.dao.getTradePropsTFirm(paramTradeProps);
  }
  
  public TradeProps getTradePropsTFirmById(String paramString)
  {
    return this.dao.getTradePropsTFirmById(paramString);
  }
  
  public void saveTradePropsTFirm(TradeProps paramTradeProps)
  {
    this.dao.saveTradePropsTFirm(paramTradeProps);
  }
}
