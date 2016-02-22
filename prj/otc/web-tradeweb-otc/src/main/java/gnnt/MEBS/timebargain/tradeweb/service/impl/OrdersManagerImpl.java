package gnnt.MEBS.timebargain.tradeweb.service.impl;

import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.tradeweb.dao.OrdersDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
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
  
  public void setOrdersDAO(OrdersDAO dao)
  {
    this.dao = dao;
  }
  
  public List commodity_query(Orders orders)
  {
    return this.dao.commodity_query(orders);
  }
  
  public List holding_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return this.dao.holding_query(orders, prvlg, sc);
  }
  
  public List holding_detail_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return this.dao.holding_detail_query(orders, prvlg, sc);
  }
  
  public List tradequery(long lastTradeNo)
  {
    return this.dao.tradequery(lastTradeNo);
  }
  
  public List firm_info(Privilege prvlg)
  {
    return this.dao.firm_info(prvlg);
  }
  
  public List firm_funds_info(Privilege prvlg)
  {
    return this.dao.firm_funds_info(prvlg);
  }
  
  public List customer_order_query(Privilege prvlg, String commodityID)
  {
    return this.dao.customer_order_query(prvlg, commodityID);
  }
  
  public List other_firm_query(Privilege prvlg)
  {
    return this.dao.other_firm_query(prvlg);
  }
  
  public List other_firm_query_S(Privilege prvlg)
  {
    return this.dao.other_firm_query_S(prvlg);
  }
  
  public List firm_holdsum(Privilege prvlg)
  {
    return this.dao.firm_holdsum(prvlg);
  }
  
  public List my_weekorder_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return this.dao.my_weekorder_query(orders, prvlg, sc);
  }
  
  public List my_order_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return this.dao.my_order_query(orders, prvlg, sc);
  }
  
  public List market_query(Market market)
  {
    return this.dao.market_query(market);
  }
  
  public List commodity_data_query(Orders orders)
  {
    return this.dao.commodity_data_query(orders);
  }
  
  public void commodity_data_query_task()
  {
    Map quotationMap = new HashMap();
    List lst = commodity_data_query(null);
    for (int i = 0; i < lst.size(); i++)
    {
      Map map = (Map)lst.get(i);
      String CommodityID = (String)map.get("CommodityID");
      quotationMap.put(CommodityID, map);
    }
    quotationMap = quotationMap;
  }
  
  public Map getQuotationMap()
  {
    return quotationMap;
  }
  
  public List getBroadcastList(long lastNo)
  {
    return this.dao.getBroadcastList(lastNo);
  }
  
  public Privilege getradePrivilege(TraderInfo info)
  {
    return this.dao.getTradePrivilege(info);
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
  
  public List my_order_query(Orders orders, Privilege prvlg)
  {
    return this.dao.my_order_query(orders, prvlg);
  }
  
  public List getDirectFirmBreeds(String firmID)
  {
    return this.dao.directfirmbreed_query(firmID);
  }
  
  public List holdpositionbyprice(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return this.dao.holdpositionbyprice(orders, prvlg, sc);
  }
  
  public String getStatus(Privilege privilege)
  {
    return this.dao.getStatus(privilege);
  }
}
