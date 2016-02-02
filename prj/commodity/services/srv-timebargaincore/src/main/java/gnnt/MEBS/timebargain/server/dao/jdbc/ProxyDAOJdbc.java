package gnnt.MEBS.timebargain.server.dao.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import gnnt.MEBS.timebargain.server.dao.ProxyDAO;
import gnnt.MEBS.timebargain.server.model.Broadcast;
import gnnt.MEBS.timebargain.server.model.CustomerHoldSumInfo;
import gnnt.MEBS.timebargain.server.model.FirmInfo;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.TradeInfo;

public class ProxyDAOJdbc extends BaseDAOJdbc implements ProxyDAO {
	private Log log = LogFactory.getLog(getClass());

	public FirmInfo getFirmInfoByTraderID(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select mf.FirmID,mf.Name,ff.Balance,(ff.balance - ff.frozenfunds) usefulFund,tf.Status,tf.MaxHoldQty,tf.MaxOverdraft,tf.MinClearDeposit ")
				.append(" from M_Firm mf,F_FirmFunds ff,T_Firm tf,M_Trader mt ")
				.append(" where mf.FirmID=ff.FirmID and mf.FirmID=tf.FirmID and mf.FirmID=mt.FirmID and mt.TraderID = ?");
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList1 = getJdbcTemplate().queryForList(localStringBuffer.toString(), new Object[] { paramString });
		if ((localList1 == null) || (localList1.size() == 0)) {
			return null;
		}
		Map localMap1 = (Map) localList1.get(0);
		FirmInfo localFirmInfo = new FirmInfo();
		String str = (String) localMap1.get("FirmID");
		localFirmInfo.firmID = str;
		localFirmInfo.name = ((String) localMap1.get("Name"));
		localFirmInfo.status = ((BigDecimal) localMap1.get("Status")).shortValue();
		localFirmInfo.balance = ((BigDecimal) localMap1.get("usefulFund")).doubleValue();
		long l1 = getJdbcTemplate().queryForLong("select nvl(sum(HoldQty+GageQty),0) from T_FirmHoldSum where FirmID=?", new Object[] { str });
		localFirmInfo.cur_Open = l1;
		long l2 = ((BigDecimal) localMap1.get("MaxHoldQty")).longValue();
		localFirmInfo.maxHoldQty = (l2 == -1L ? 99999999L : l2);
		localFirmInfo.cur_MaxOpen = (localFirmInfo.maxHoldQty - localFirmInfo.cur_Open);
		List localList2 = getJdbcTemplate().queryForList("select Code from T_Customer where FirmID=?", new Object[] { str });
		Vector localVector = new Vector();
		Iterator localIterator = localList2.iterator();
		while (localIterator.hasNext()) {
			Object localObject = localIterator.next();
			Map localMap2 = (Map) localObject;
			localVector.add((String) localMap2.get("Code"));
		}
		localFirmInfo.vTradeCode = localVector;
		double d1 = ((BigDecimal) localMap1.get("MaxOverdraft")).doubleValue();
		double d2 = ((BigDecimal) localMap1.get("MinClearDeposit")).doubleValue();
		double d3 = ((BigDecimal) localMap1.get("usefulFund")).doubleValue();
		double d4 = 0.0D;
		if (d3 + d1 - d2 < 0.0D) {
			d4 = d2 - d3 - d1;
		}
		localFirmInfo.shouldAddFund = d4;
		return localFirmInfo;
	}

	public List queryCustomerHoldSumInfo(String paramString1, String paramString2, String paramString3) {
		ArrayList localArrayList = new ArrayList();
		String str1 = "";
		if ((paramString1 != null) && (!paramString1.equals(""))) {
			str1 = str1 + " and FirmID='" + paramString1 + "' ";
		}
		if ((paramString2 != null) && (!paramString2.equals(""))) {
			str1 = str1 + " and CommodityID='" + paramString2 + "' ";
		}
		if ((paramString3 != null) && (!paramString3.equals(""))) {
			str1 = str1 + " and CustomerID='" + paramString3 + "' ";
		}
		String str2 = "select FloatingProfitSubTax from T_A_Market ";
		int i = getJdbcTemplate().queryForInt(str2);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("select cmdty.commodityid commodityid,cmdty.customerid customerid,nvl(b.holdqty,0) buyQty,nvl(b.evenprice,0) buyEvenPrice,(nvl(s.holdqty,0)+nvl(s.gageqty,0)) sellQty,nvl(s.gageqty,0) gageqty,nvl(s.evenprice,0) sellEvenPrice,")
				.append("nvl(b.holdmargin,0)+nvl(s.holdmargin,0) holdmargin, ").append("nvl(b.holdqty,0)-nvl(b.frozenQty,0) buyAvailQty,")
				.append("nvl(s.holdqty,0)-nvl(s.frozenQty,0) sellAvailQty,")
				.append("(nvl(FN_T_ComputeFPSubTax(b.EvenPrice, qc.Price, b.HoldQty, qc.ContractFactor, 1,qc.addedtax,").append(i).append("), 0) ")
				.append("+nvl(FN_T_ComputeFPSubTax(case when s.holdQty>0 then (s.holdFunds-(s.gageQty*s.EvenPrice*qc.ContractFactor))/(s.holdQty*qc.ContractFactor) else 0 end, qc.Price, s.HoldQty, qc.ContractFactor, 2,qc.addedtax,")
				.append(i).append("), 0)) bsPL ").append(" From ").append("(select distinct customerid,commodityid from t_customerholdsum where 1=1 ")
				.append(str1).append(") cmdty,")
				.append("(select commodityid,customerid,holdqty,FrozenQty,holdmargin,evenprice from t_customerholdsum where bs_flag=1 ").append(str1)
				.append(") b,")
				.append("(select commodityid,customerid,holdqty,gageqty,FrozenQty,holdmargin,evenprice,holdfunds from t_customerholdsum where bs_flag=2 ")
				.append(str1).append(") s,")
				.append("(select q.commodityid,q.price,c.contractfactor,c.addedtax from T_Quotation q, T_Commodity c where q.commodityid=c.commodityid) qc ")
				.append("where cmdty.commodityid=qc.commodityid and cmdty.commodityid=b.commodityid(+) and cmdty.customerid=b.customerid(+) and ")
				.append("cmdty.commodityid=s.commodityid(+) and cmdty.customerid=s.customerid(+) ");
		this.log.debug("sql: " + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
		if ((localList == null) || (localList.size() == 0)) {
			return localArrayList;
		}
		for (int j = 0; j < localList.size(); j++) {
			Map localMap = (Map) localList.get(j);
			CustomerHoldSumInfo localCustomerHoldSumInfo = new CustomerHoldSumInfo();
			localCustomerHoldSumInfo.commodityID = ((String) localMap.get("commodityid"));
			localCustomerHoldSumInfo.customerID = ((String) localMap.get("customerid"));
			localCustomerHoldSumInfo.bHoldQty = ((BigDecimal) localMap.get("buyQty")).longValue();
			localCustomerHoldSumInfo.sHoldQty = ((BigDecimal) localMap.get("sellQty")).longValue();
			localCustomerHoldSumInfo.bCanSettleHoldQty = ((BigDecimal) localMap.get("buyAvailQty")).longValue();
			localCustomerHoldSumInfo.sCanSettleHoldQty = ((BigDecimal) localMap.get("sellAvailQty")).longValue();
			localCustomerHoldSumInfo.bPrice = ((BigDecimal) localMap.get("buyEvenPrice")).doubleValue();
			localCustomerHoldSumInfo.sPrice = ((BigDecimal) localMap.get("sellEvenPrice")).doubleValue();
			localCustomerHoldSumInfo.floatPL = ((BigDecimal) localMap.get("bsPL")).doubleValue();
			localCustomerHoldSumInfo.holdMargin = ((BigDecimal) localMap.get("holdmargin")).doubleValue();
			localArrayList.add(localCustomerHoldSumInfo);
		}
		return localArrayList;
	}

	public List queryOrder(Order paramOrder) {
		ArrayList localArrayList1 = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select * ").append("from T_Orders ").append("where OrderType <> 4 ");
		Object[] arrayOfObject = null;
		if (paramOrder.getOrderNo() != null) {
			localStringBuffer.append(" and A_OrderNo=? ");
			arrayOfObject = new Object[] { paramOrder.getOrderNo() };
		} else {
			ArrayList localArrayList2 = new ArrayList();
			if (!StringUtils.isEmpty(paramOrder.getTraderID())) {
				localStringBuffer.append(" and TraderID=? ");
				localArrayList2.add(paramOrder.getTraderID());
			}
			if (!StringUtils.isEmpty(paramOrder.getCommodityID())) {
				localStringBuffer.append(" and CommodityID=? ");
				localArrayList2.add(paramOrder.getCommodityID());
			}
			if (paramOrder.getBuyOrSell() != null) {
				localStringBuffer.append(" and BS_Flag=? ");
				localArrayList2.add(paramOrder.getBuyOrSell());
			}
			if (!StringUtils.isEmpty(paramOrder.getCustomerID())) {
				localStringBuffer.append(" and CustomerID=? ");
				localArrayList2.add(paramOrder.getCustomerID());
			}
			arrayOfObject = localArrayList2.toArray();
		}
		this.log.debug("sql: " + localStringBuffer.toString());
		if (arrayOfObject != null) {
			for (int i = 0; i < arrayOfObject.length; i++) {
				this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
			}
		}
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
		if ((localList == null) || (localList.size() == 0)) {
			return localArrayList1;
		}
		for (int j = 0; j < localList.size(); j++) {
			Map localMap = (Map) localList.get(j);
			Order localOrder = new Order();
			localOrder.setOrderNo(Long.valueOf(((BigDecimal) localMap.get("A_OrderNo")).longValue()));
			localOrder.setOrderTime((Date) localMap.get("OrderTime"));
			localOrder.setStatus(Short.valueOf(((BigDecimal) localMap.get("Status")).shortValue()));
			localOrder.setBuyOrSell(Short.valueOf(((BigDecimal) localMap.get("BS_Flag")).shortValue()));
			localOrder.setOrderType(Short.valueOf(((BigDecimal) localMap.get("OrderType")).shortValue()));
			localOrder.setTraderID((String) localMap.get("TraderID"));
			localOrder.setFirmID((String) localMap.get("FirmID"));
			localOrder.setCustomerID((String) localMap.get("CustomerID"));
			localOrder.setCommodityID((String) localMap.get("CommodityID"));
			localOrder.setPrice(Double.valueOf(((BigDecimal) localMap.get("Price")).doubleValue()));
			localOrder.setQuantity(Long.valueOf(((BigDecimal) localMap.get("Quantity")).longValue()));
			localOrder.setTradeQty(Long.valueOf(((BigDecimal) localMap.get("TradeQty")).longValue()));
			localArrayList1.add(localOrder);
		}
		return localArrayList1;
	}

	public List queryTradeInfo(long paramLong, String paramString) {
		List localObject = new ArrayList();
		String str = "select * from T_Trade Where A_TradeNo>? and FirmID=? order by A_TradeNo";
		Object[] arrayOfObject = { Long.valueOf(paramLong), paramString };
		localObject = getJdbcTemplate().query(str, arrayOfObject, new TradeRowMapper());
		return localObject;
	}

	public List getBroadcasts(long paramLong, String paramString) {
		List localObject = new ArrayList();
		String str = "select * from T_Broadcast where ID>? and (FirmID is null or FirmID=?) order by ID";
		Object[] arrayOfObject = { Long.valueOf(paramLong), paramString };
		localObject = getJdbcTemplate().query(str, arrayOfObject, new BroadcastRowMapper());
		return localObject;
	}

	private class BroadcastRowMapper implements RowMapper {
		private BroadcastRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			Broadcast localBroadcast = new Broadcast();
			localBroadcast.setId(new Long(paramResultSet.getLong("id")));
			localBroadcast.setTitle(paramResultSet.getString("title"));
			localBroadcast.setContent(paramResultSet.getString("content"));
			localBroadcast.setStatus(new Short(paramResultSet.getShort("status")));
			localBroadcast.setFirmID(paramResultSet.getString("FirmID"));
			localBroadcast.setCreateTime(paramResultSet.getTimestamp("createTime"));
			localBroadcast.setAuthor(paramResultSet.getString("author"));
			localBroadcast.setSendTime(paramResultSet.getTimestamp("sendTime"));
			return localBroadcast;
		}
	}

	private class TradeRowMapper implements RowMapper {
		private TradeRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			TradeInfo localTradeInfo = new TradeInfo();
			localTradeInfo.seqNum = paramResultSet.getLong("A_TradeNo");
			localTradeInfo.A_OrderNo = Long.valueOf(paramResultSet.getLong("A_OrderNo"));
			localTradeInfo.A_TradeNo = Long.valueOf(paramResultSet.getLong("A_TradeNo"));
			localTradeInfo.A_TradeNo_Closed = Long.valueOf(paramResultSet.getLong("A_TradeNo_Closed"));
			localTradeInfo.BS_Flag = Short.valueOf(paramResultSet.getShort("BS_Flag"));
			localTradeInfo.Close_PL = Double.valueOf(paramResultSet.getDouble("Close_PL"));
			localTradeInfo.CommodityID = paramResultSet.getString("CommodityID");
			localTradeInfo.CustomerID = paramResultSet.getString("CustomerID");
			localTradeInfo.FirmID = paramResultSet.getString("FirmID");
			localTradeInfo.HoldPrice = Double.valueOf(paramResultSet.getDouble("HoldPrice"));
			localTradeInfo.M_TradeNo = Long.valueOf(paramResultSet.getLong("M_TradeNo"));
			localTradeInfo.OrderType = Short.valueOf(paramResultSet.getShort("OrderType"));
			localTradeInfo.Price = Double.valueOf(paramResultSet.getDouble("Price"));
			localTradeInfo.Quantity = Long.valueOf(paramResultSet.getLong("Quantity"));
			localTradeInfo.TradeFee = Double.valueOf(paramResultSet.getDouble("TradeFee"));
			localTradeInfo.TradeTime = paramResultSet.getTimestamp("TradeTime");
			localTradeInfo.TradeType = Integer.valueOf(paramResultSet.getInt("TradeType"));
			localTradeInfo.HoldTime = paramResultSet.getTimestamp("HoldTime");
			return localTradeInfo;
		}
	}
}
