package gnnt.MEBS.timebargain.tradeweb.dao;

import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

public abstract interface OrdersDAO
  extends DAO
{
  public abstract List commodity_query(Orders paramOrders);
  
  public abstract List holding_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List holding_detail_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List tradequery(long paramLong);
  
  public abstract List firm_info(Privilege paramPrivilege);
  
  public abstract List firm_funds_info(Privilege paramPrivilege);
  
  public abstract List customer_order_query(Privilege paramPrivilege, String paramString);
  
  public abstract List other_firm_query(Privilege paramPrivilege);
  
  public abstract List other_firm_query_S(Privilege paramPrivilege);
  
  public abstract List firm_holdsum(Privilege paramPrivilege);
  
  public abstract List my_weekorder_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List my_order_query(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract List my_order_query(Orders paramOrders, Privilege paramPrivilege);
  
  public abstract List market_query(Market paramMarket);
  
  public abstract List commodity_data_query(Orders paramOrders);
  
  public abstract List getBroadcastList(long paramLong);
  
  public abstract Date getDBTime();
  
  public abstract Privilege getTradePrivilege(TraderInfo paramTraderInfo);
  
  public abstract List getFirmList();
  
  public abstract List directfirmbreed_query(String paramString);
  
  public abstract DataSource getDataSource();
  
  public abstract List holdpositionbyprice(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition);
  
  public abstract String getStatus(Privilege paramPrivilege);
}
