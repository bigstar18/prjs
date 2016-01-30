package gnnt.MEBS.timebargain.tradeweb.service;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.tradeweb.dao.OrdersDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public abstract interface OrdersManager
{
  public abstract void setOrdersDAO(OrdersDAO paramOrdersDAO);
  
  public abstract List commodity_query(Orders paramOrders);
  
  public abstract List holding_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List tradequery(long paramLong);
  
  public abstract List firm_info(Privilege paramPrivilege);
  
  public abstract List my_weekorder_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List my_order_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List my_order_query(Orders paramOrders, Privilege paramPrivilege);
  
  public abstract List market_query(Market paramMarket);
  
  public abstract List commodity_data_query(Orders paramOrders);
  
  public abstract void commodity_data_query_task();
  
  public abstract Map getQuotationMap();
  
  public abstract List getFirmList();
  
  public abstract List getDirectFirmBreeds(String paramString);
  
  public abstract Privilege getradePrivilege(TraderLogonInfo paramTraderLogonInfo);
  
  public abstract DataSource getDataSource();
  
  public abstract Date getDBTime();
  
  public abstract Map tariff();
  
  public abstract List holdpositionbyprice(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
}
