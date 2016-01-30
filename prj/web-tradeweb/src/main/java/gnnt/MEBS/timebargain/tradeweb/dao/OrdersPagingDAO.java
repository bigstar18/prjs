package gnnt.MEBS.timebargain.tradeweb.dao;

import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.SmallHelper;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.util.List;
import java.util.Map;

public abstract interface OrdersPagingDAO
{
  public abstract List tradepagingquery(long paramLong, Trader paramTrader, SortCondition paramSortCondition, Map paramMap);
  
  public abstract List my_weekorder_pagingquery(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition, Map paramMap);
  
  public abstract List smallHelper(SmallHelper paramSmallHelper);
  
  public abstract List totalDateQuery(String paramString, Privilege paramPrivilege, Map paramMap);
}
