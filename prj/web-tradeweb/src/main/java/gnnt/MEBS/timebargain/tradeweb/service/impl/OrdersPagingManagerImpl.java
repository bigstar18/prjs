package gnnt.MEBS.timebargain.tradeweb.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.tradeweb.dao.OrdersPagingDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.SmallHelper;
import gnnt.MEBS.timebargain.tradeweb.model.TotalDate;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersPagingManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class OrdersPagingManagerImpl implements OrdersPagingManager {
	private final Log log = LogFactory.getLog(OrdersPagingManagerImpl.class);
	private OrdersPagingDAO dao;

	public void setOrdersPagingDAO(OrdersPagingDAO paramOrdersPagingDAO) {
		this.dao = paramOrdersPagingDAO;
	}

	public List my_weekorder_pagingquery(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition, Map paramMap) {
		return this.dao.my_weekorder_pagingquery(paramOrders, paramPrivilege, paramSortCondition, paramMap);
	}

	public List tradepagingquery(long paramLong, Trader paramTrader, SortCondition paramSortCondition, Map paramMap) {
		return this.dao.tradepagingquery(paramLong, paramTrader, paramSortCondition, paramMap);
	}

	public List smallHelper(SmallHelper paramSmallHelper) {
		SmallHelper localSmallHelper = new SmallHelper();
		String str1 = paramSmallHelper.getTable();
		String str2 = paramSmallHelper.getField();
		String str3 = paramSmallHelper.getAction();
		if ((str3 == null) || ("".equals(str3))) {
			return null;
		}
		localSmallHelper.setAction(str3);
		localSmallHelper.setField(str2 == null ? " * " : str2);
		if ((str1 == null) || ("".equals(str1))) {
			return null;
		}
		localSmallHelper.setTable(str1);
		localSmallHelper.setSortfld(paramSmallHelper.getSortfld());
		localSmallHelper.setSortASC(paramSmallHelper.getSortASC());
		localSmallHelper.setGroupfld(paramSmallHelper.getGroupfld());
		return this.dao.smallHelper(localSmallHelper);
	}

	public List<TotalDate> totalDateQuery(String paramString, Privilege paramPrivilege, Map paramMap) {
		LinkedList localLinkedList = new LinkedList();
		HashMap localHashMap = new HashMap();
		Object localObject1;

		if ((paramMap != null) && (paramMap.size() > 0) && (paramMap.get("parameter") != null)) {
			localObject1 = paramMap.get("parameter").toString();
			for (String localObject3 : ((String) localObject1).split(";")) {
				String[] localObject4 = ((String) localObject3).split(":");
				if ("UT".equals(localObject4[0])) {
					localHashMap.put("order", " and  UpdateTime >" + localObject4[1] + " ");
				}
				if ("TR_N".equals(localObject4[0])) {
					localHashMap.put("trade", " and A_TradeNo>" + localObject4[1] + "  ");
				}
			}
		}
		for (String localObject2 : paramString.split(",")) {
			TotalDate localObject3 = new TotalDate();
			String localObject4 = new String();
			if ("my_weekorder_pagingquery".equals(localObject2)) {
				localObject4 = "order";
			} else if ("tradepagingquery".equals(localObject2)) {
				localObject4 = "trade";
				if ((paramMap != null) && (paramMap.size() > 0)) {
					paramMap.put("parameters", localHashMap.get("trade"));
				}
			} else if ("my_order_pagingquery".equals(localObject2)) {
				localObject4 = "orderNoTrade";
			} else if ("conditionorder_query".equals(localObject2)) {
				localObject4 = "conditionorder";
			}
			List localList = this.dao.totalDateQuery((String) localObject4, paramPrivilege, paramMap);
			Map localMap = (Map) localList.get(0);
			((TotalDate) localObject3).setResponseName(localObject2);
			((TotalDate) localObject3)
					.setTotalNum(Long.valueOf(localMap.get("TOTALNUM") == null ? "0" : localMap.get("TOTALNUM").toString()).longValue());
			((TotalDate) localObject3)
					.setTotalQty(Long.valueOf(localMap.get("TOTALQTY") == null ? "0" : localMap.get("TOTALQTY").toString()).longValue());
			((TotalDate) localObject3)
					.setTotalLiqpl(Double.valueOf(localMap.get("TOTALLIQPL") == null ? "0" : localMap.get("TOTALLIQPL").toString()).doubleValue());
			((TotalDate) localObject3)
					.setTotalComm(Double.valueOf(localMap.get("TOTALCOMM") == null ? "0" : localMap.get("TOTALCOMM").toString()).doubleValue());
			((TotalDate) localObject3).setTotalUnTradeQty(
					Long.valueOf(localMap.get("totalUnTradeQty") == null ? "0" : localMap.get("totalUnTradeQty").toString()).longValue());
			localLinkedList.add(localObject3);
		}
		return localLinkedList;
	}
}
