package gnnt.MEBS.timebargain.tradeweb.service.impl;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.tradeweb.dao.OrdersDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class OrdersManagerImpl
  extends BaseManager
  implements OrdersManager
{
  private OrdersDAO dao;
  public static Map quotationMap = new HashMap();
  public static List broadcastList = new ArrayList();
  
  public void setOrdersDAO(OrdersDAO paramOrdersDAO)
  {
    this.dao = paramOrdersDAO;
  }
  
  public List commodity_query(Orders paramOrders)
  {
    return this.dao.commodity_query(paramOrders);
  }
  
  public List holding_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition)
  {
    return this.dao.holding_query(paramOrders, paramPrivilege, paramSortCondition);
  }
  
  public List tradequery(long paramLong)
  {
    return this.dao.tradequery(paramLong);
  }
  
  public List firm_info(Privilege paramPrivilege)
  {
    return this.dao.firm_info(paramPrivilege);
  }
  
  public List my_weekorder_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition)
  {
    return this.dao.my_weekorder_query(paramOrders, paramPrivilege, paramSortCondition);
  }
  
  public List my_order_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition)
  {
    return this.dao.my_order_query(paramOrders, paramPrivilege, paramSortCondition);
  }
  
  public List market_query(Market paramMarket)
  {
    return this.dao.market_query(paramMarket);
  }
  
  public List commodity_data_query(Orders paramOrders)
  {
    return this.dao.commodity_data_query(paramOrders);
  }
  
  public void commodity_data_query_task()
  {
    HashMap localHashMap = new HashMap();
    List localList = commodity_data_query(null);
    for (int i = 0; i < localList.size(); i++)
    {
      Map localMap = (Map)localList.get(i);
      String str = (String)localMap.get("CommodityID");
      localHashMap.put(str, localMap);
    }
    quotationMap = localHashMap;
  }
  
  public Map getQuotationMap()
  {
    return quotationMap;
  }
  
  public Privilege getradePrivilege(TraderLogonInfo paramTraderLogonInfo)
  {
    return this.dao.getTradePrivilege(paramTraderLogonInfo);
  }
  
  public DataSource getDataSource()
  {
    return this.dao.getDataSource();
  }
  
  public List getFirmList()
  {
    return this.dao.getFirmList();
  }
  
  public Date getDBTime()
  {
    return this.dao.getDBTime();
  }
  
  public List my_order_query(Orders paramOrders, Privilege paramPrivilege)
  {
    return this.dao.my_order_query(paramOrders, paramPrivilege);
  }
  
  public List getDirectFirmBreeds(String paramString)
  {
    return this.dao.directfirmbreed_query(paramString);
  }
  
  public List holdpositionbyprice(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition)
  {
    return this.dao.holdpositionbyprice(paramOrders, paramPrivilege, paramSortCondition);
  }
  
  public Map tariff()
  {
    return this.dao.tariff();
  }
}
