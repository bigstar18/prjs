package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.OrdersDAO;
import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.action.AgencyRMIBean;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.List;
import java.util.Map;

public abstract interface OrdersManager
{
  public abstract void setOrdersDAO(OrdersDAO paramOrdersDAO);
  
  public abstract List noTradeList(Orders paramOrders, String paramString1, String paramString2);
  
  public abstract List getNotMarketCodeOrders();
  
  public abstract void updateMarketOrderNo(Long paramLong1, Long paramLong2);
  
  public abstract List queryWithdrawReason();
  
  public abstract List holdMonitor(long paramLong);
  
  public abstract List fundMonitor(double paramDouble);
  
  public abstract Map performMonitor();
  
  public abstract List procLog(QueryConditions paramQueryConditions);
  
  public abstract void deleteProcLog(QueryConditions paramQueryConditions);
  
  public abstract List sysLog(QueryConditions paramQueryConditions, String paramString);
  
  public abstract void deleteSysLog(QueryConditions paramQueryConditions);
  
  public abstract List getTradeResponds();
  
  public abstract String getMarketCodeByTradeRespondID(Long paramLong);
  
  public abstract void deleteTradeRespondById(Long paramLong);
  
  public abstract void deleteTradeRespond();
  
  public abstract List getTradeRespondsByStatus(Short paramShort);
  
  public abstract void updateTradeRespond(String paramString, int paramInt);
  
  public abstract String getFirmIDToDefine(String paramString);
  
  public abstract List traderLog(QueryConditions paramQueryConditions);
  
  public abstract int insertOrder(AgencyRMIBean paramAgencyRMIBean, Long paramLong, Order paramOrder)
    throws Exception;
  
  public abstract int insertCloseOrder(AgencyRMIBean paramAgencyRMIBean, Long paramLong, Order paramOrder)
    throws Exception;
  
  public abstract String resultOrder(int paramInt)
    throws Exception;
}
