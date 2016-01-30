package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import gnnt.MEBS.timebargain.tradeweb.dao.OrdersPagingDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.SmallHelper;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class OrdersPagingDAOJdbc extends BaseDAOJdbc implements OrdersPagingDAO {
	private Log log = LogFactory.getLog(OrdersPagingDAOJdbc.class);

	public List my_weekorder_pagingquery(Orders paramOrders, Privilege paramPrivilege, SortCondition paramSortCondition, Map paramMap) {
		StringBuffer localStringBuffer1 = new StringBuffer();
		StringBuffer localStringBuffer2 = new StringBuffer();
		StringBuffer localStringBuffer3 = new StringBuffer();
		Object[] arrayOfObject = null;
		String str1 = paramSortCondition.getStartNu();
		String str2 = paramSortCondition.getReccnt();
		String str3 = paramSortCondition.getSortfLD();
		String str4 = paramSortCondition.getIsdesc() == 0 ? "asc" : "desc";
		int i = Integer.valueOf(paramMap.get("startPagingNum").toString()).intValue();
		int j = Integer.valueOf(paramMap.get("endPagingNum").toString()).intValue();
		String str5 = paramMap.get("CommodityID").toString();
		if (!"".equals(str5)) {
			localStringBuffer2.append("  and commodityid= '" + str5 + "'  ");
		}
		String str6 = paramMap.get("BS_Flag").toString();
		if (!"0".equals(str6)) {
			localStringBuffer2.append("  and  BS_Flag= '" + str6 + "'  ");
		}
		String str7 = paramMap.get("Status").toString();
		if (!"0".equals(str7)) {
			localStringBuffer2.append("   and Status in (1,2)");
		}
		String str8 = !"1".equals(paramMap.get("isQueryAll")) ? " " : "   and status in (1,2) ";
		localStringBuffer1.append("select o_r.* from ( ");
		localStringBuffer1.append("select    b.*,rownum  r from ( select  a.*,(a.Quantity-a.TradeQty) notTradeQty ").append("from T_Orders a ")
				.append("where OrderType <> 4 ").append(str8);
		String str9 = paramPrivilege.getTraderStatus();
		if (paramOrders.getA_OrderNo() != null) {
			localStringBuffer1.append("and A_OrderNo=? ");
			arrayOfObject = new Object[] { paramOrders.getA_OrderNo() };
		} else {
			ArrayList localArrayList = new ArrayList();
			if (!StringUtils.isEmpty(paramOrders.getTraderID())) {
				if ("A".equals(str9)) {
					localStringBuffer1.append(" and firmid =?");
					localArrayList.add(paramPrivilege.getFirmId());
				} else {
					localStringBuffer1.append(" and TraderID=?");
					localArrayList.add(paramPrivilege.getTraderID());
				}
			}
			if (!StringUtils.isEmpty(paramOrders.getCommodityID())) {
				localStringBuffer1.append("and CommodityID=? ");
				localArrayList.add(paramOrders.getCommodityID());
			}
			if (paramOrders.getBS_Flag() != null) {
				localStringBuffer1.append("and BS_Flag=? ");
				localArrayList.add(paramOrders.getBS_Flag());
			}
			if (!"queryAll".equalsIgnoreCase(paramOrders.getUpdateTime())) {
				localStringBuffer1.append(" and UpdateTime>? ");
				localArrayList.add(new Timestamp(Long.parseLong(paramOrders.getUpdateTime())));
			} else {
				localStringBuffer1.append(localStringBuffer2);
				localStringBuffer3.append("  where r>").append(i).append(" and r<=").append(j);
			}
			arrayOfObject = localArrayList.toArray();
		}
		localStringBuffer1.append(" order by ").append(" " + str3).append(" " + str4 + ") b ").append(" ) o_r ").append(localStringBuffer3);
		this.log.debug("sql: " + localStringBuffer1.toString());
		if (arrayOfObject != null) {
			for (int k = 0; k < arrayOfObject.length; k++) {
				this.log.debug("params[" + k + "]: " + arrayOfObject[k]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer1.toString(), arrayOfObject);
	}

	public List tradepagingquery(long paramLong, Trader paramTrader, SortCondition paramSortCondition, Map paramMap) {
		StringBuffer localStringBuffer1 = new StringBuffer();
		StringBuffer localStringBuffer2 = new StringBuffer();
		StringBuffer localStringBuffer3 = new StringBuffer();
		String str1 = paramTrader.getTraderID();
		String str2 = paramSortCondition.getReccnt();
		String str3 = paramSortCondition.getSortfLD();
		String str4 = paramSortCondition.getIsdesc() == 0 ? "asc" : "desc";
		int i = Integer.valueOf(paramMap.get("startPagingNum").toString()).intValue();
		int j = Integer.valueOf(paramMap.get("endPagingNum").toString()).intValue();
		if (paramLong == 1L) {
			localStringBuffer3.append(" where r>").append(i).append("  and r<=").append(j);
		}
		String str5 = paramMap.get("CommodityID").toString();
		if (!"".equals(str5)) {
			localStringBuffer2.append("  and commodityid= '" + str5 + "'   ");
		}
		String str6 = paramMap.get("BS_Flag").toString();
		if (!"0".equals(str6)) {
			localStringBuffer2.append("  and  BS_Flag='" + str6 + "'   ");
		}
		String str7 = paramMap.get("OrderType").toString();
		if (!"0".equals(str7)) {
			localStringBuffer2.append("   and OrderType='" + str7 + "'   ");
		}
		localStringBuffer1.append("select tr.* from (");
		localStringBuffer1.append("select t.*,rownum r from ( select  a.*,b.TraderID from T_Trade a, (select TraderID,a_orderno  from  t_orders ) b ")
				.append(" Where a.A_TradeNo>? and  a.A_OrderNo=b.A_OrderNo ");
		localStringBuffer1.append("  and firmid=?").append(localStringBuffer2);
		localStringBuffer1.append(" order by  ").append(" a." + str3).append(" " + str4);
		localStringBuffer1.append(") t ").append(" ) tr ");
		localStringBuffer1.append(localStringBuffer3);
		Object[] arrayOfObject = null;
		arrayOfObject = new Object[] { Long.valueOf(paramLong), str1 };
		this.log.debug("tradepagingquery  Sql===============================" + localStringBuffer1.toString());
		return getJdbcTemplate().query(localStringBuffer1.toString(), arrayOfObject, new TradeRowMapper());
	}

	public List smallHelper(SmallHelper paramSmallHelper) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(paramSmallHelper.getAction() + " ").append(paramSmallHelper.getField() + " ").append(" from ")
				.append(paramSmallHelper.getTable());
		if ((paramSmallHelper.getSortfld() != null) && (!"".equals(paramSmallHelper.getSortfld().trim()))) {
			localStringBuffer.append(" order by ").append(paramSmallHelper.getSortfld());
			if ((paramSmallHelper.getSortASC() != null) && ("".equals(paramSmallHelper.getSortASC().trim()))) {
				localStringBuffer.append(paramSmallHelper.getSortASC() + " ");
			}
		}
		this.log.debug("SmallHelper  sql" + localStringBuffer.toString());
		return getJdbcTemplate().queryForList(localStringBuffer.toString());
	}

	public List totalDateQuery(String paramString, Privilege paramPrivilege, Map paramMap) {
		StringBuffer localStringBuffer1 = new StringBuffer();
		StringBuffer localStringBuffer2 = new StringBuffer();
		if ((paramMap != null) && (paramMap.size() > 0)) {
			String localObject1 = (String) paramMap.get("CommodityID");
			if ((localObject1 != null) && (!"".equals(localObject1))) {
				localStringBuffer2.append("  and commodityid= '" + (String) localObject1 + "'  ");
			}
			String localObject2 = (String) paramMap.get("BS_Flag");
			if ((localObject2 != null) && (!"0".equals(localObject2))) {
				localStringBuffer2.append("  and  BS_Flag= '" + (String) localObject2 + "'  ");
			}
			String str1 = (String) paramMap.get("Status");
			if ((str1 != null) && (!"0".equals(str1))) {
				localStringBuffer2.append("   and Status in (1,2) ");
			}
			String str2 = (String) paramMap.get("OrderType");
			if ((str2 != null) && (!"0".equals(str2))) {
				localStringBuffer2.append("   and OrderType='" + str2 + "'   ");
			}
			String str3 = (String) paramMap.get("parameters");
			if ((str3 != null) && (!"".equals(str3)) && (paramString.equals("trade"))) {
				localStringBuffer2.append(str3);
			}
			String str4 = (String) paramMap.get("conditionType");
			if ((str4 != null) && (!"0".equals(str4))) {
				localStringBuffer2.append("   and conditionType='" + str4 + "'   ");
			}
			String str5 = (String) paramMap.get("SendStatus");
			if ((str5 != null) && (str5.equals("11")) && (!str5.equals(""))) {
				localStringBuffer2.append(" and SendStatus='委托成功'");
			} else if ((str5 != null) && (str5.equals("12")) && (!str5.equals(""))) {
				localStringBuffer2.append(" and SendStatus='委托失败'");
			} else if ((str5 != null) && (str5.equals("01")) && (!str5.equals(""))) {
				localStringBuffer2.append(" and SendStatus='未委托')");
			} else if ((str5 != null) && (str5.equals("02")) && (!str5.equals(""))) {
				localStringBuffer2.append(" and SendStatus='已过期'");
			} else if ((str5 != null) && (str5.equals("2")) && (!str5.equals(""))) {
				localStringBuffer2.append(" and SendStatus='已撤单'");
			}
		}
		localStringBuffer1.append("select count(*) totalNum,");
		if ("trade".equals(paramString)) {
			localStringBuffer1.append("  sum(quantity) totalQty,sum(close_PL) totalLiqpl,sum(tradefee) totalComm from t_trade");
		} else if (paramString.startsWith("order")) {
			localStringBuffer1.append(" sum(quantity) totalQty,sum(quantity-tradeqty) totalUnTradeQty from t_orders");
		} else if (paramString.equals("conditionorder")) {
			localStringBuffer1
					.append(" sum(quantity) totalQty from (select conditionType,a_orderno,quantity,commodityid,BS_Flag,OrderType,firmid ,Retcode,to_char(ValidDate,'yyyy-MM-dd') as ValidDate,")
					.append("case when SendStatus = 0 and (ValidDate < trunc(sysdate)) then '已过期' when (SendStatus = 0 and (ValidDate >= trunc(sysdate))) then '未委托' when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' end as SendStatus ")
					.append(" from t_conditionorder union select  conditionType,a_orderno,quantity,commodityid,BS_Flag,OrderType,firmid ,Retcode,to_char(ValidDate,'yyyy-MM-dd') as ValidDate,")
					.append("case when SendStatus = 0 and (ValidDate < trunc(sysdate)) then '已过期' when (SendStatus = 0 and (ValidDate >= trunc(sysdate))) then '未委托' when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' end as SendStatus ")
					.append("from t_h_conditionorder)");
		}
		localStringBuffer1.append(" where firmid=? ").append(localStringBuffer2);
		if (paramString.endsWith("NoTrade")) {
			localStringBuffer1.append("  and status in (1,2) ");
		}
		Object localObject2 = new ArrayList();
		String str1 = paramPrivilege.getFirmId();
		((List) localObject2).add(str1);
		Object[] localObject1 = ((List) localObject2).toArray();
		this.log.debug("sql: " + localStringBuffer1.toString());
		if (localObject1 != null) {
			for (int i = 0; i < localObject1.length; i++) {
				this.log.debug("params[" + i + "]: " + localObject1[i]);
			}
		}
		return getJdbcTemplate().queryForList(localStringBuffer1.toString(), (Object[]) localObject1);
	}

	private class TradeRowMapper implements RowMapper {
		private TradeRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			Trade localTrade = new Trade();
			localTrade.setA_OrderNo(Long.valueOf(paramResultSet.getLong("A_OrderNo")));
			localTrade.setA_TradeNo(Long.valueOf(paramResultSet.getLong("A_TradeNo")));
			localTrade.setA_TradeNo_Closed(Long.valueOf(paramResultSet.getLong("A_TradeNo_Closed")));
			localTrade.setBS_Flag(Short.valueOf(paramResultSet.getShort("BS_Flag")));
			localTrade.setClose_PL(Double.valueOf(paramResultSet.getDouble("Close_PL")));
			localTrade.setCommodityID(paramResultSet.getString("CommodityID"));
			localTrade.setCustomerID(paramResultSet.getString("CustomerID"));
			localTrade.setFirmID(paramResultSet.getString("FirmID"));
			localTrade.setHoldPrice(Double.valueOf(paramResultSet.getDouble("HoldPrice")));
			localTrade.setM_TradeNo(Long.valueOf(paramResultSet.getLong("M_TradeNo")));
			localTrade.setOrderType(Short.valueOf(paramResultSet.getShort("OrderType")));
			localTrade.setPrice(Double.valueOf(paramResultSet.getDouble("Price")));
			localTrade.setQuantity(Long.valueOf(paramResultSet.getLong("Quantity")));
			localTrade.setTradeFee(Double.valueOf(paramResultSet.getDouble("TradeFee")));
			localTrade.setTradeTime(paramResultSet.getTimestamp("TradeTime"));
			localTrade.setTradeType(Integer.valueOf(paramResultSet.getInt("TradeType")));
			localTrade.setTraderID(paramResultSet.getString("TraderID"));
			return localTrade;
		}
	}
}
