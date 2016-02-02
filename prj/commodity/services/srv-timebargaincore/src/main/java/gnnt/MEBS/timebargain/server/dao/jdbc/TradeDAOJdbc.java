package gnnt.MEBS.timebargain.server.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.MarginAdjust;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Trade;
import gnnt.MEBS.timebargain.server.util.StringUtil;

public class TradeDAOJdbc extends BaseDAOJdbc implements TradeDAO {
	private Log log = LogFactory.getLog(getClass());

	public List getNotTradeOrders() {
		String str = "select * from T_Orders where OrderType<>4 and Status in(1,2,4) order by a_orderno";
		this.log.debug("sql: " + str);
		List localList = getJdbcTemplate().queryForList(str);
		ArrayList localArrayList = new ArrayList(localList.size());
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			Map localMap = (Map) localIterator.next();
			Order localOrder = new Order();
			localOrder.setOrderNo(localMap.get("A_OrderNo") == null ? null : new Long(((BigDecimal) localMap.get("A_OrderNo")).longValue()));
			localOrder.setCustomerID((String) localMap.get("CustomerID"));
			localOrder.setFirmID((String) localMap.get("FirmID"));
			localOrder.setConsignerID((String) localMap.get("ConsignerID"));
			localOrder.setTraderID((String) localMap.get("TraderID"));
			localOrder.setCommodityID(localMap.get("CommodityID") == null ? "" : (String) localMap.get("CommodityID"));
			localOrder.setBuyOrSell(localMap.get("BS_Flag") == null ? null : new Short(((BigDecimal) localMap.get("BS_Flag")).shortValue()));
			localOrder.setPrice(localMap.get("Price") == null ? null : new Double(((BigDecimal) localMap.get("Price")).doubleValue()));
			localOrder.setQuantity(localMap.get("Quantity") == null ? null : new Long(((BigDecimal) localMap.get("Quantity")).longValue()));
			localOrder.setTradeQty(localMap.get("TradeQty") == null ? null : new Long(((BigDecimal) localMap.get("TradeQty")).longValue()));
			localOrder.setOrderType(localMap.get("OrderType") == null ? null : new Short(((BigDecimal) localMap.get("OrderType")).shortValue()));
			localOrder.setCloseFlag(localMap.get("CloseFlag") == null ? null : new Short(((BigDecimal) localMap.get("CloseFlag")).shortValue()));
			localOrder.setStatus(localMap.get("Status") == null ? null : new Short(((BigDecimal) localMap.get("Status")).shortValue()));
			localOrder.setWithdrawType(
					localMap.get("WithdrawType") == null ? null : new Short(((BigDecimal) localMap.get("WithdrawType")).shortValue()));
			localOrder.setOrderTime((Date) localMap.get("OrderTime"));
			localOrder.setSpecialOrderFlag(Short.valueOf(localMap.get("specialOrderFlag") == null ? 0
					: new Short(((BigDecimal) localMap.get("specialOrderFlag")).shortValue()).shortValue()));
			if ((localOrder.getOrderType() != null) && (localOrder.getOrderType().shortValue() == 2)) {
				localOrder.setCloseMode(localMap.get("CloseMode") == null ? null : new Short(((BigDecimal) localMap.get("CloseMode")).shortValue()));
				localOrder
						.setSpecPrice(localMap.get("SpecPrice") == null ? null : new Double(((BigDecimal) localMap.get("SpecPrice")).doubleValue()));
				localOrder.setSpecTime(localMap.get("TimeFlag") == null ? null : new Short(((BigDecimal) localMap.get("TimeFlag")).shortValue()));
			}
			localArrayList.add(localOrder);
		}
		return localArrayList;
	}

	public long openOrder(Order paramOrder) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'OpenOrder' method");
		}
		Assert.hasText(paramOrder.getFirmID());
		Assert.hasText(paramOrder.getCommodityID());
		Assert.notNull(paramOrder.getBuyOrSell());
		Assert.notNull(paramOrder.getPrice());
		Assert.notNull(paramOrder.getQuantity());
		openOrderStoredProcedure localopenOrderStoredProcedure = new openOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramOrder.getFirmID());
		localHashMap.put("p_TraderID", paramOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramOrder.getCommodityID());
		localHashMap.put("p_bs_flag", paramOrder.getBuyOrSell());
		localHashMap.put("p_price", paramOrder.getPrice());
		localHashMap.put("p_quantity", paramOrder.getQuantity());
		localHashMap.put("p_Margin", paramOrder.getMargin());
		localHashMap.put("p_Fee", paramOrder.getFee());
		localHashMap.put("p_customerID", paramOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramOrder.getConsignerID());
		localHashMap.put("p_specialOrderFlag", paramOrder.getSpecialOrderFlag());
		this.log.debug("param:" + localHashMap);
		Map localMap = localopenOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("ID")).longValue();
	}

	public long closeOrder(Order paramOrder) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'closeOrder' method");
		}
		Assert.hasText(paramOrder.getFirmID());
		Assert.hasText(paramOrder.getCommodityID());
		Assert.notNull(paramOrder.getBuyOrSell());
		Assert.notNull(paramOrder.getPrice());
		Assert.notNull(paramOrder.getQuantity());
		closeOrderStoredProcedure localcloseOrderStoredProcedure = new closeOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramOrder.getFirmID());
		localHashMap.put("p_TraderID", paramOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramOrder.getCommodityID());
		localHashMap.put("p_bs_flag", paramOrder.getBuyOrSell());
		localHashMap.put("p_price", paramOrder.getPrice());
		localHashMap.put("p_quantity", paramOrder.getQuantity());
		localHashMap.put("p_closeMode", paramOrder.getCloseMode());
		localHashMap.put("p_specPrice", paramOrder.getSpecPrice());
		localHashMap.put("p_timeFlag", paramOrder.getSpecTime());
		localHashMap.put("p_closeFlag", paramOrder.getCloseFlag());
		localHashMap.put("p_CloseAlgr", paramOrder.getCloseAlgr());
		localHashMap.put("p_customerID", paramOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramOrder.getConsignerID());
		localHashMap.put("p_specialOrderFlag", paramOrder.getSpecialOrderFlag());
		this.log.debug("param:" + localHashMap);
		Map localMap = localcloseOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("ID")).longValue();
	}

	public int consignerLogon(Consigner paramConsigner) {
		String str = "select AgentTraderID consignerID,Name,password,Type,Status,OperateFirm from M_AgentTrader  where AgentTraderID=?";
		Object[] arrayOfObject = { paramConsigner.getConsignerID() };
		this.log.debug("sql: " + str);
		this.log.debug("consignerID:" + arrayOfObject[0]);
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList == null) || (localList.size() <= 0)) {
			return -1;
		}
		Map localMap = (Map) localList.get(0);
		if (!((String) localMap.get("Password"))
				.equals(StringUtil.encodePassword(paramConsigner.getConsignerID() + paramConsigner.getPassword(), "MD5"))) {
			return -2;
		}
		if (((BigDecimal) localMap.get("Status")).intValue() == 1) {
			return -3;
		}
		return 0;
	}

	public Timestamp floatingComputer(String paramString1, String paramString2, Timestamp paramTimestamp) {
		UpdateFloatingLossStoredProcedure localUpdateFloatingLossStoredProcedure = new UpdateFloatingLossStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_fromFirm", paramString1);
		localHashMap.put("p_toFirm", paramString2);
		localHashMap.put("p_LastTime", paramTimestamp);
		Map localMap = localUpdateFloatingLossStoredProcedure.execute(localHashMap);
		return (Timestamp) localMap.get("ret");
	}

	public void updateExitCommodity() {
		String str = "update T_Commodity set Status=1 where SettleDate <= (select TradeDate from T_SystemStatus)";
		this.log.debug("sql: " + str);
		getJdbcTemplate().update(str);
	}

	public int updateTrade(Trade paramTrade1, Trade paramTrade2) {
		Assert.notNull(paramTrade1.getOrderNo());
		Assert.notNull(paramTrade1.getM_TradeNo());
		Assert.notNull(paramTrade2.getOrderNo());
		Assert.notNull(paramTrade2.getM_TradeNo());
		UpdateTradeStoredProcedure localUpdateTradeStoredProcedure = new UpdateTradeStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_A_OrderNo", paramTrade1.getOrderNo());
		localHashMap.put("p_M_TradeNo", paramTrade1.getM_TradeNo());
		localHashMap.put("p_Price", paramTrade1.getPrice());
		localHashMap.put("p_Quantity", paramTrade1.getQuantity());
		localHashMap.put("p_M_TradeNo_Opp", paramTrade1.getM_TradeNo_Opp());
		localHashMap.put("p_A_OrderNo_Other", paramTrade2.getOrderNo());
		localHashMap.put("p_M_TradeNo_Other", paramTrade2.getM_TradeNo());
		localHashMap.put("p_Price_Other", paramTrade2.getPrice());
		localHashMap.put("p_Quantity_Other", paramTrade2.getQuantity());
		localHashMap.put("p_M_TradeNo_Opp_Other", paramTrade2.getM_TradeNo_Opp());
		this.log.debug("param:" + localHashMap);
		Map localMap = localUpdateTradeStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int withdraw(Order paramOrder) {
		WithdrawStoredProcedure localWithdrawStoredProcedure = new WithdrawStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_WithdrawerID", paramOrder.getWithdrawerID());
		localHashMap.put("p_A_OrderNo_W", paramOrder.getWithdrawID());
		localHashMap.put("p_WithdrawType", paramOrder.getWithdrawType());
		localHashMap.put("p_Quantity", paramOrder.getQuantity());
		this.log.debug("param:" + localHashMap);
		Map localMap = localWithdrawStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int updateQuotation(Quotation paramQuotation) {
		Assert.hasText(paramQuotation.getCommodityID());
		Assert.notNull(paramQuotation.getPrice());
		UpdateQuotationStoredProcedure localUpdateQuotationStoredProcedure = new UpdateQuotationStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_CommodityID", paramQuotation.getCommodityID());
		localHashMap.put("p_YesterBalancePrice", paramQuotation.getYesterBalancePrice());
		localHashMap.put("p_ClosePrice", paramQuotation.getClosePrice());
		localHashMap.put("p_OpenPrice", paramQuotation.getOpenPrice());
		localHashMap.put("p_HighPrice", paramQuotation.getHighPrice());
		localHashMap.put("p_LowPrice", paramQuotation.getLowPrice());
		localHashMap.put("p_CurPrice", paramQuotation.getCurPrice());
		localHashMap.put("p_CurAmount", paramQuotation.getCurAmount());
		localHashMap.put("p_OpenAmount", paramQuotation.getOpenAmount());
		localHashMap.put("p_BuyOpenAmount", paramQuotation.getBuyOpenAmount());
		localHashMap.put("p_SellOpenAmount", paramQuotation.getSellOpenAmount());
		localHashMap.put("p_CloseAmount", paramQuotation.getCloseAmount());
		localHashMap.put("p_BuyCloseAmount", paramQuotation.getBuyCloseAmount());
		localHashMap.put("p_SellCloseAmount", paramQuotation.getSellCloseAmount());
		localHashMap.put("p_ReserveCount", paramQuotation.getReserveCount());
		localHashMap.put("p_ReserveChange", paramQuotation.getReserveChange());
		localHashMap.put("p_Price", paramQuotation.getPrice());
		localHashMap.put("p_TotalMoney", paramQuotation.getTotalMoney());
		localHashMap.put("p_TotalAmount", paramQuotation.getTotalAmount());
		localHashMap.put("p_Spread", paramQuotation.getSpread());
		localHashMap.put("p_BuyPrice1", Double.valueOf(paramQuotation.buy[0]));
		localHashMap.put("p_SellPrice1", Double.valueOf(paramQuotation.sell[0]));
		localHashMap.put("p_BuyAmount1", Long.valueOf(paramQuotation.buyqty[0]));
		localHashMap.put("p_SellAmount1", Long.valueOf(paramQuotation.sellqty[0]));
		localHashMap.put("p_BuyPrice2", Double.valueOf(paramQuotation.buy[1]));
		localHashMap.put("p_SellPrice2", Double.valueOf(paramQuotation.sell[1]));
		localHashMap.put("p_BuyAmount2", Long.valueOf(paramQuotation.buyqty[1]));
		localHashMap.put("p_SellAmount2", Long.valueOf(paramQuotation.sellqty[1]));
		localHashMap.put("p_BuyPrice3", Double.valueOf(paramQuotation.buy[2]));
		localHashMap.put("p_SellPrice3", Double.valueOf(paramQuotation.sell[2]));
		localHashMap.put("p_BuyAmount3", Long.valueOf(paramQuotation.buyqty[2]));
		localHashMap.put("p_SellAmount3", Long.valueOf(paramQuotation.sellqty[2]));
		localHashMap.put("p_BuyPrice4", Double.valueOf(paramQuotation.buy[3]));
		localHashMap.put("p_SellPrice4", Double.valueOf(paramQuotation.sell[3]));
		localHashMap.put("p_BuyAmount4", Long.valueOf(paramQuotation.buyqty[3]));
		localHashMap.put("p_SellAmount4", Long.valueOf(paramQuotation.sellqty[3]));
		localHashMap.put("p_BuyPrice5", Double.valueOf(paramQuotation.buy[4]));
		localHashMap.put("p_SellPrice5", Double.valueOf(paramQuotation.sell[4]));
		localHashMap.put("p_BuyAmount5", Long.valueOf(paramQuotation.buyqty[4]));
		localHashMap.put("p_SellAmount5", Long.valueOf(paramQuotation.sellqty[4]));
		localHashMap.put("p_OutAmount", paramQuotation.getOutAmount());
		localHashMap.put("p_InAmount", paramQuotation.getInAmount());
		localHashMap.put("p_TradeCue", paramQuotation.getTradeCue());
		localHashMap.put("p_NO", paramQuotation.getNo());
		localHashMap.put("p_CreateTime", paramQuotation.getCreateTime());
		this.log.debug("param:" + localHashMap);
		Map localMap = localUpdateQuotationStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public long getCustomerHoldSumFrozenQty() {
		String str = "select nvl(sum(FrozenQty),0) FrozenQty from T_CustomerHoldSum";
		long l = ((Long) getJdbcTemplate().queryForObject(str, Long.class)).longValue();
		return l;
	}

	public int balance() {
		CloseMarketProcessStoredProcedure localCloseMarketProcessStoredProcedure = new CloseMarketProcessStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		Map localMap = localCloseMarketProcessStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int settleProcess(String paramString) {
		Assert.hasText(paramString);
		SettleProcessStoredProcedure localSettleProcessStoredProcedure = new SettleProcessStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_CommodityID", paramString);
		localHashMap.put("p_SettleType", new Short((short) 1));
		this.log.debug("param:" + localHashMap);
		Map localMap = localSettleProcessStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int initTrade(Date paramDate) {
		InitTradeStoredProcedure localInitTradeStoredProcedure = new InitTradeStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_ClearDate", paramDate);
		Map localMap = localInitTradeStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int reComputeFunds(int paramInt) {
		ReComputeFundsStoredProcedure localReComputeFundsStoredProcedure = new ReComputeFundsStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_MarginFBFlag", new Integer(paramInt));
		this.log.debug("param:" + localHashMap);
		Map localMap = localReComputeFundsStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public long getMaxMatcherTradeNo() {
		return getJdbcTemplate().queryForLong("select nvl(max(M_TradeNo),0) from T_Trade");
	}

	public List getCommodityMarginAdjustList() {
		ArrayList localArrayList = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer("select * from T_Commodity");
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
		for (int i = 0; i < localList.size(); i++) {
			MarginAdjust localMarginAdjust = new MarginAdjust();
			Map localMap = (Map) localList.get(i);
			localMarginAdjust.setCommodityID((String) localMap.get("CommodityID"));
			localMarginAdjust.setMarginRate_B(new Double(((BigDecimal) localMap.get("MarginRate_B")).doubleValue()));
			localMarginAdjust.setMarginRate_S(new Double(((BigDecimal) localMap.get("MarginRate_S")).doubleValue()));
			localMarginAdjust.setMarginAssure_B(new Double(((BigDecimal) localMap.get("MarginAssure_B")).doubleValue()));
			localMarginAdjust.setMarginAssure_S(new Double(((BigDecimal) localMap.get("MarginAssure_S")).doubleValue()));
			Date localDate1 = (Date) localMap.get("SettleDate1");
			if (localDate1 != null) {
				localMarginAdjust.setSettleDate1(localDate1);
				localMarginAdjust.setMarginItem1(new Double(((BigDecimal) localMap.get("MarginItem1")).doubleValue()));
				localMarginAdjust.setMarginItem1_S(new Double(((BigDecimal) localMap.get("MarginItem1_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure1(new Double(((BigDecimal) localMap.get("MarginItemAssure1")).doubleValue()));
				localMarginAdjust.setMarginItemAssure1_S(new Double(((BigDecimal) localMap.get("MarginItemAssure1_S")).doubleValue()));
			}
			Date localDate2 = (Date) localMap.get("SettleDate2");
			if (localDate2 != null) {
				localMarginAdjust.setSettleDate2(localDate2);
				localMarginAdjust.setMarginItem2(new Double(((BigDecimal) localMap.get("MarginItem2")).doubleValue()));
				localMarginAdjust.setMarginItem2_S(new Double(((BigDecimal) localMap.get("MarginItem2_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure2(new Double(((BigDecimal) localMap.get("MarginItemAssure2")).doubleValue()));
				localMarginAdjust.setMarginItemAssure2_S(new Double(((BigDecimal) localMap.get("MarginItemAssure2_S")).doubleValue()));
			}
			Date localDate3 = (Date) localMap.get("SettleDate3");
			if (localDate3 != null) {
				localMarginAdjust.setSettleDate3(localDate3);
				localMarginAdjust.setMarginItem3(new Double(((BigDecimal) localMap.get("MarginItem3")).doubleValue()));
				localMarginAdjust.setMarginItem3_S(new Double(((BigDecimal) localMap.get("MarginItem3_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure3(new Double(((BigDecimal) localMap.get("MarginItemAssure3")).doubleValue()));
				localMarginAdjust.setMarginItemAssure3_S(new Double(((BigDecimal) localMap.get("MarginItemAssure3_S")).doubleValue()));
			}
			Date localDate4 = (Date) localMap.get("SettleDate4");
			if (localDate4 != null) {
				localMarginAdjust.setSettleDate4(localDate4);
				localMarginAdjust.setMarginItem4(new Double(((BigDecimal) localMap.get("MarginItem4")).doubleValue()));
				localMarginAdjust.setMarginItem4_S(new Double(((BigDecimal) localMap.get("MarginItem4_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure4(new Double(((BigDecimal) localMap.get("MarginItemAssure4")).doubleValue()));
				localMarginAdjust.setMarginItemAssure4_S(new Double(((BigDecimal) localMap.get("MarginItemAssure4_S")).doubleValue()));
			}
			Date localDate5 = (Date) localMap.get("SettleDate5");
			if (localDate5 != null) {
				localMarginAdjust.setSettleDate5(localDate5);
				localMarginAdjust.setMarginItem5(new Double(((BigDecimal) localMap.get("MarginItem5")).doubleValue()));
				localMarginAdjust.setMarginItem5_S(new Double(((BigDecimal) localMap.get("MarginItem5_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure5(new Double(((BigDecimal) localMap.get("MarginItemAssure5")).doubleValue()));
				localMarginAdjust.setMarginItemAssure5_S(new Double(((BigDecimal) localMap.get("MarginItemAssure5_S")).doubleValue()));
			}
			localArrayList.add(localMarginAdjust);
		}
		return localArrayList;
	}

	public void adjustCommodityMargin(MarginAdjust paramMarginAdjust) {
		String str = "update T_Commodity set MarginRate_B=?,MarginRate_S=?,MarginAssure_B=?,MarginAssure_S=? where CommodityID = ? ";
		Object[] arrayOfObject = { paramMarginAdjust.getMarginRate_B(), paramMarginAdjust.getMarginRate_S(), paramMarginAdjust.getMarginAssure_B(),
				paramMarginAdjust.getMarginAssure_S(), paramMarginAdjust.getCommodityID() };
		int[] arrayOfInt = { 8, 8, 8, 8, 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public List getFirmMarginAdjustList() {
		ArrayList localArrayList = new ArrayList();
		StringBuffer localStringBuffer = new StringBuffer(
				"select a.*,b.SettleDate1,b.SettleDate2,b.SettleDate3,b.SettleDate4,b.SettleDate5 from T_A_FirmMargin a,T_Commodity b where a.CommodityID=b.CommodityID");
		this.log.debug("sql:" + localStringBuffer.toString());
		List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
		for (int i = 0; i < localList.size(); i++) {
			MarginAdjust localMarginAdjust = new MarginAdjust();
			Map localMap = (Map) localList.get(i);
			localMarginAdjust.setFirmID((String) localMap.get("FirmID"));
			localMarginAdjust.setCommodityID((String) localMap.get("CommodityID"));
			localMarginAdjust.setMarginRate_B(new Double(((BigDecimal) localMap.get("MarginRate_B")).doubleValue()));
			localMarginAdjust.setMarginRate_S(new Double(((BigDecimal) localMap.get("MarginRate_S")).doubleValue()));
			localMarginAdjust.setMarginAssure_B(new Double(((BigDecimal) localMap.get("MarginAssure_B")).doubleValue()));
			localMarginAdjust.setMarginAssure_S(new Double(((BigDecimal) localMap.get("MarginAssure_S")).doubleValue()));
			Date localDate1 = (Date) localMap.get("SettleDate1");
			if (localDate1 != null) {
				localMarginAdjust.setSettleDate1(localDate1);
				localMarginAdjust.setMarginItem1(new Double(((BigDecimal) localMap.get("MarginItem1")).doubleValue()));
				localMarginAdjust.setMarginItem1_S(new Double(((BigDecimal) localMap.get("MarginItem1_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure1(new Double(((BigDecimal) localMap.get("MarginItemAssure1")).doubleValue()));
				localMarginAdjust.setMarginItemAssure1_S(new Double(((BigDecimal) localMap.get("MarginItemAssure1_S")).doubleValue()));
			}
			Date localDate2 = (Date) localMap.get("SettleDate2");
			if (localDate2 != null) {
				localMarginAdjust.setSettleDate2(localDate2);
				localMarginAdjust.setMarginItem2(new Double(((BigDecimal) localMap.get("MarginItem2")).doubleValue()));
				localMarginAdjust.setMarginItem2_S(new Double(((BigDecimal) localMap.get("MarginItem2_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure2(new Double(((BigDecimal) localMap.get("MarginItemAssure2")).doubleValue()));
				localMarginAdjust.setMarginItemAssure2_S(new Double(((BigDecimal) localMap.get("MarginItemAssure2_S")).doubleValue()));
			}
			Date localDate3 = (Date) localMap.get("SettleDate3");
			if (localDate3 != null) {
				localMarginAdjust.setSettleDate3(localDate3);
				localMarginAdjust.setMarginItem3(new Double(((BigDecimal) localMap.get("MarginItem3")).doubleValue()));
				localMarginAdjust.setMarginItem3_S(new Double(((BigDecimal) localMap.get("MarginItem3_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure3(new Double(((BigDecimal) localMap.get("MarginItemAssure3")).doubleValue()));
				localMarginAdjust.setMarginItemAssure3_S(new Double(((BigDecimal) localMap.get("MarginItemAssure3_S")).doubleValue()));
			}
			Date localDate4 = (Date) localMap.get("SettleDate4");
			if (localDate4 != null) {
				localMarginAdjust.setSettleDate4(localDate4);
				localMarginAdjust.setMarginItem4(new Double(((BigDecimal) localMap.get("MarginItem4")).doubleValue()));
				localMarginAdjust.setMarginItem4_S(new Double(((BigDecimal) localMap.get("MarginItem4_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure4(new Double(((BigDecimal) localMap.get("MarginItemAssure4")).doubleValue()));
				localMarginAdjust.setMarginItemAssure4_S(new Double(((BigDecimal) localMap.get("MarginItemAssure4_S")).doubleValue()));
			}
			Date localDate5 = (Date) localMap.get("SettleDate5");
			if (localDate5 != null) {
				localMarginAdjust.setSettleDate5(localDate5);
				localMarginAdjust.setMarginItem5(new Double(((BigDecimal) localMap.get("MarginItem5")).doubleValue()));
				localMarginAdjust.setMarginItem5_S(new Double(((BigDecimal) localMap.get("MarginItem5_S")).doubleValue()));
				localMarginAdjust.setMarginItemAssure5(new Double(((BigDecimal) localMap.get("MarginItemAssure5")).doubleValue()));
				localMarginAdjust.setMarginItemAssure5_S(new Double(((BigDecimal) localMap.get("MarginItemAssure5_S")).doubleValue()));
			}
			localArrayList.add(localMarginAdjust);
		}
		return localArrayList;
	}

	public void adjustFirmMargin(MarginAdjust paramMarginAdjust) {
		String str = "update T_A_FirmMargin set MarginRate_B=?,MarginRate_S=?,MarginAssure_B=?,MarginAssure_S=? where CommodityID=? and FirmID=? ";
		Object[] arrayOfObject = { paramMarginAdjust.getMarginRate_B(), paramMarginAdjust.getMarginRate_S(), paramMarginAdjust.getMarginAssure_B(),
				paramMarginAdjust.getMarginAssure_S(), paramMarginAdjust.getCommodityID(), paramMarginAdjust.getFirmID() };
		int[] arrayOfInt = { 8, 8, 8, 8, 12, 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public void deductCloseOrder(Order paramOrder) {
		Assert.hasText(paramOrder.getCustomerID());
		Assert.hasText(paramOrder.getFirmID());
		Assert.hasText(paramOrder.getCommodityID());
		Assert.notNull(paramOrder.getBuyOrSell());
		Assert.notNull(paramOrder.getPrice());
		Assert.notNull(paramOrder.getQuantity());
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(
				"insert into T_Orders( a_orderno,  a_orderno_w,   CommodityID,   Firmid,    traderid,   bs_flag,   ordertype, status, quantity, price, closemode, specprice,       timeflag,tradeqty, frozenfunds, unfrozenfunds, ordertime, withdrawtime, ordererip, signature,closeFlag,   CustomerID,ConsignerID,WithdrawType,WithdrawerID)");
		localStringBuffer.append(
				"       values( FN_T_ComputeOrderNo(SEQ_T_Orders.nextval),  null,           ?,          ?,         null,        ?,         2,         5,       ?,       ?,      1,        null,            null,    0,         0,             0,        sysdate,    sysdate,       null,     null,       1,            ?,     'deducter',     4,          null    )");
		Object[] arrayOfObject = { paramOrder.getCommodityID(), paramOrder.getFirmID(), paramOrder.getBuyOrSell(), paramOrder.getQuantity(),
				paramOrder.getPrice(), paramOrder.getCustomerID() };
		int[] arrayOfInt = { 12, 12, 5, -5, 8, 12 };
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(localStringBuffer.toString(), arrayOfObject, arrayOfInt);
	}

	public void updateCommodityFirmMaxHoldQty(String paramString, Long paramLong) {
		String str = "update T_Commodity set FirmMaxHoldQty=? where CommodityID=? ";
		Object[] arrayOfObject = { paramLong, paramString };
		int[] arrayOfInt = { -5, 12 };
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public long sellBillOrder(Order paramOrder) {
		this.log.debug("........sellBillOrder()........");
		Assert.hasText(paramOrder.getFirmID());
		Assert.hasText(paramOrder.getCommodityID());
		Assert.notNull(paramOrder.getBuyOrSell());
		Assert.notNull(paramOrder.getPrice());
		Assert.notNull(paramOrder.getQuantity());
		sellBillOrderStoredProcedure localsellBillOrderStoredProcedure = new sellBillOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramOrder.getFirmID());
		localHashMap.put("p_TraderID", paramOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramOrder.getCommodityID());
		localHashMap.put("p_bs_flag", paramOrder.getBuyOrSell());
		localHashMap.put("p_price", paramOrder.getPrice());
		localHashMap.put("p_quantity", paramOrder.getQuantity());
		localHashMap.put("p_Fee", paramOrder.getFee());
		localHashMap.put("p_customerID", paramOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramOrder.getConsignerID());
		this.log.debug("param:" + localHashMap);
		Map localMap = localsellBillOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("v_A_OrderNo")).longValue();
	}

	public long gageCloseOrder(Order paramOrder) {
		this.log.debug("........gageCloseOrder()........");
		Assert.hasText(paramOrder.getFirmID());
		Assert.hasText(paramOrder.getCommodityID());
		Assert.notNull(paramOrder.getBuyOrSell());
		Assert.notNull(paramOrder.getPrice());
		Assert.notNull(paramOrder.getQuantity());
		gageCloseOrderStoredProcedure localgageCloseOrderStoredProcedure = new gageCloseOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramOrder.getFirmID());
		localHashMap.put("p_TraderID", paramOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramOrder.getCommodityID());
		localHashMap.put("p_bs_flag", paramOrder.getBuyOrSell());
		localHashMap.put("p_price", paramOrder.getPrice());
		localHashMap.put("p_quantity", paramOrder.getQuantity());
		localHashMap.put("p_closeMode", paramOrder.getCloseMode());
		localHashMap.put("p_specPrice", paramOrder.getSpecPrice());
		localHashMap.put("p_timeFlag", paramOrder.getSpecTime());
		localHashMap.put("p_closeFlag", paramOrder.getCloseFlag());
		localHashMap.put("p_CustomerID", paramOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramOrder.getConsignerID());
		this.log.debug("param:" + localHashMap);
		Map localMap = localgageCloseOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("v_A_OrderNo")).longValue();
	}

	private class gageCloseOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_GageCloseOrder";

		public gageCloseOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_GageCloseOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("v_A_OrderNo", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_bs_flag", 5));
			declareParameter(new SqlParameter("p_price", 8));
			declareParameter(new SqlParameter("p_quantity", -5));
			declareParameter(new SqlParameter("p_closeMode", 8));
			declareParameter(new SqlParameter("p_specPrice", 12));
			declareParameter(new SqlParameter("p_timeFlag", 12));
			declareParameter(new SqlParameter("p_closeFlag", 12));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class sellBillOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_SellBillOrder";

		public sellBillOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_SellBillOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("v_A_OrderNo", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_bs_flag", 5));
			declareParameter(new SqlParameter("p_price", 8));
			declareParameter(new SqlParameter("p_quantity", -5));
			declareParameter(new SqlParameter("p_Fee", 8));
			declareParameter(new SqlParameter("p_customerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class ReComputeFundsStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_ReComputeFunds";

		public ReComputeFundsStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_ReComputeFunds");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_MarginFBFlag", 4));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class InitTradeStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_InitTrade";

		public InitTradeStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_InitTrade");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_ClearDate", 91));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class SettleProcessStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_SettleProcess";

		public SettleProcessStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_SettleProcess");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_SettleType", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class CloseMarketProcessStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_CloseMarketProcess";

		public CloseMarketProcessStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_CloseMarketProcess");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class UpdateQuotationStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_UpdateQuotation";

		public UpdateQuotationStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_UpdateQuotation");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_YesterBalancePrice", 8));
			declareParameter(new SqlParameter("p_ClosePrice", 8));
			declareParameter(new SqlParameter("p_OpenPrice", 8));
			declareParameter(new SqlParameter("p_HighPrice", 8));
			declareParameter(new SqlParameter("p_LowPrice", 8));
			declareParameter(new SqlParameter("p_CurPrice", 8));
			declareParameter(new SqlParameter("p_CurAmount", -5));
			declareParameter(new SqlParameter("p_OpenAmount", -5));
			declareParameter(new SqlParameter("p_BuyOpenAmount", -5));
			declareParameter(new SqlParameter("p_SellOpenAmount", -5));
			declareParameter(new SqlParameter("p_CloseAmount", -5));
			declareParameter(new SqlParameter("p_BuyCloseAmount", -5));
			declareParameter(new SqlParameter("p_SellCloseAmount", -5));
			declareParameter(new SqlParameter("p_ReserveCount", -5));
			declareParameter(new SqlParameter("p_ReserveChange", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_TotalMoney", 8));
			declareParameter(new SqlParameter("p_TotalAmount", -5));
			declareParameter(new SqlParameter("p_Spread", 8));
			declareParameter(new SqlParameter("p_BuyPrice1", 8));
			declareParameter(new SqlParameter("p_SellPrice1", 8));
			declareParameter(new SqlParameter("p_BuyAmount1", -5));
			declareParameter(new SqlParameter("p_SellAmount1", -5));
			declareParameter(new SqlParameter("p_BuyPrice2", 8));
			declareParameter(new SqlParameter("p_SellPrice2", 8));
			declareParameter(new SqlParameter("p_BuyAmount2", -5));
			declareParameter(new SqlParameter("p_SellAmount2", -5));
			declareParameter(new SqlParameter("p_BuyPrice3", 8));
			declareParameter(new SqlParameter("p_SellPrice3", 8));
			declareParameter(new SqlParameter("p_BuyAmount3", -5));
			declareParameter(new SqlParameter("p_SellAmount3", -5));
			declareParameter(new SqlParameter("p_BuyPrice4", 8));
			declareParameter(new SqlParameter("p_SellPrice4", 8));
			declareParameter(new SqlParameter("p_BuyAmount4", -5));
			declareParameter(new SqlParameter("p_SellAmount4", -5));
			declareParameter(new SqlParameter("p_BuyPrice5", 8));
			declareParameter(new SqlParameter("p_SellPrice5", 8));
			declareParameter(new SqlParameter("p_BuyAmount5", -5));
			declareParameter(new SqlParameter("p_SellAmount5", -5));
			declareParameter(new SqlParameter("p_OutAmount", -5));
			declareParameter(new SqlParameter("p_InAmount", -5));
			declareParameter(new SqlParameter("p_TradeCue", -5));
			declareParameter(new SqlParameter("p_NO", -5));
			declareParameter(new SqlParameter("p_CreateTime", 93));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class WithdrawStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_Withdraw";

		public WithdrawStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_Withdraw");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_WithdrawerID", 12));
			declareParameter(new SqlParameter("p_A_OrderNo_W", -5));
			declareParameter(new SqlParameter("p_WithdrawType", 5));
			declareParameter(new SqlParameter("p_Quantity", -5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class UpdateTradeStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_UpdateTrade";

		public UpdateTradeStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_UpdateTrade");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_A_OrderNo", -5));
			declareParameter(new SqlParameter("p_M_TradeNo", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_M_TradeNo_Opp", -5));
			declareParameter(new SqlParameter("p_A_OrderNo_Other", -5));
			declareParameter(new SqlParameter("p_M_TradeNo_Other", -5));
			declareParameter(new SqlParameter("p_Price_Other", 8));
			declareParameter(new SqlParameter("p_Quantity_Other", -5));
			declareParameter(new SqlParameter("p_M_TradeNo_Opp_Other", -5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class UpdateFloatingLossStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_UpdateFloatingLoss";

		public UpdateFloatingLossStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_UpdateFloatingLoss");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 93));
			declareParameter(new SqlParameter("p_fromFirm", 12));
			declareParameter(new SqlParameter("p_toFirm", 12));
			declareParameter(new SqlParameter("p_LastTime", 93));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class closeOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_CloseOrder";

		public closeOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_CloseOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("ID", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_bs_flag", 5));
			declareParameter(new SqlParameter("p_price", 8));
			declareParameter(new SqlParameter("p_quantity", -5));
			declareParameter(new SqlParameter("p_closeMode", 5));
			declareParameter(new SqlParameter("p_specPrice", 8));
			declareParameter(new SqlParameter("p_timeFlag", 5));
			declareParameter(new SqlParameter("p_closeFlag", 5));
			declareParameter(new SqlParameter("p_CloseAlgr", 5));
			declareParameter(new SqlParameter("p_customerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			declareParameter(new SqlParameter("p_specialOrderFlag", 4));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class openOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_OpenOrder";

		public openOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_OpenOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("ID", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_bs_flag", 5));
			declareParameter(new SqlParameter("p_price", 8));
			declareParameter(new SqlParameter("p_quantity", -5));
			declareParameter(new SqlParameter("p_Margin", 8));
			declareParameter(new SqlParameter("p_Fee", 8));
			declareParameter(new SqlParameter("p_customerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			declareParameter(new SqlParameter("p_specialOrderFlag", 4));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
