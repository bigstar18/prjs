package gnnt.MEBS.timebargain.server.dao.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

import gnnt.MEBS.timebargain.server.dao.DelayDAO;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayQuotation;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.model.DelayTradeTime;

public class DelayDAOJdbc extends BaseDAOJdbc implements DelayDAO {
	private Log log = LogFactory.getLog(getClass());

	public List getDelayTradeTimes() {
		ArrayList localArrayList = new ArrayList();
		String str = "select * from T_A_DelayTradeTime order by SectionID";
		this.log.debug("sql:" + str);
		List localList = getJdbcTemplate().queryForList(str);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			DelayTradeTime localDelayTradeTime = new DelayTradeTime();
			localDelayTradeTime.setSectionID(new Integer(((BigDecimal) localMap.get("SectionID")).intValue()));
			localDelayTradeTime.setName((String) localMap.get("Name"));
			localDelayTradeTime.setStartTime((String) localMap.get("StartTime"));
			localDelayTradeTime.setEndTime((String) localMap.get("EndTime"));
			localDelayTradeTime.setStatus(new Short(((BigDecimal) localMap.get("Status")).shortValue()));
			localDelayTradeTime.setType(new Short(((BigDecimal) localMap.get("Type")).shortValue()));
			localDelayTradeTime.setModifyTime((Date) localMap.get("ModifyTime"));
			localArrayList.add(localDelayTradeTime);
		}
		return localArrayList;
	}

	public DelayStatus getDelayStatus() {
		DelayStatus localDelayStatus = null;
		String str = "select * from T_DelayStatus";
		this.log.debug("sql:" + str);
		List localList = getJdbcTemplate().queryForList(str);
		if ((localList != null) && (localList.size() > 0)) {
			Map localMap = (Map) localList.get(0);
			localDelayStatus = new DelayStatus();
			localDelayStatus.setTradeDate((Date) localMap.get("TradeDate"));
			localDelayStatus.setStatus(((BigDecimal) localMap.get("Status")).intValue());
			localDelayStatus
					.setSectionID(localMap.get("SectionID") == null ? null : new Integer(((BigDecimal) localMap.get("SectionID")).intValue()));
			localDelayStatus.setNote((String) localMap.get("Note"));
			localDelayStatus.setRecoverTime((String) localMap.get("RecoverTime"));
		}
		return localDelayStatus;
	}

	public void updateDelayStatus(DelayStatus paramDelayStatus) {
		String str = "update T_DelayStatus set TradeDate=?,Status=?,SectionID=?,Note=?,RecoverTime=? ";
		Object[] arrayOfObject = { paramDelayStatus.getTradeDate(), new Integer(paramDelayStatus.getStatus()), paramDelayStatus.getSectionID(),
				paramDelayStatus.getNote(), paramDelayStatus.getRecoverTime() };
		int[] arrayOfInt = { 91, 5, 4, 12, 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public void updateDelayRecoverTime(String paramString) {
		String str = "update T_DelayStatus set RecoverTime=?";
		Object[] arrayOfObject = { paramString };
		int[] arrayOfInt = { 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public long buySettleOrder(DelayOrder paramDelayOrder) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'BuySettleOrder' method");
		}
		Assert.hasText(paramDelayOrder.getFirmID());
		Assert.hasText(paramDelayOrder.getCommodityID());
		Assert.notNull(paramDelayOrder.getPrice());
		Assert.notNull(paramDelayOrder.getQuantity());
		BuySettleOrderStoredProcedure localBuySettleOrderStoredProcedure = new BuySettleOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramDelayOrder.getFirmID());
		localHashMap.put("p_TraderID", paramDelayOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramDelayOrder.getCommodityID());
		localHashMap.put("p_Quantity", paramDelayOrder.getQuantity());
		localHashMap.put("p_Price", paramDelayOrder.getPrice());
		localHashMap.put("p_CustomerID", paramDelayOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramDelayOrder.getConsignerID());
		localHashMap.put("p_TradeMargin_B", paramDelayOrder.getMargin());
		localHashMap.put("p_DelayQuoShowType", paramDelayOrder.getDelayQuoShowType());
		this.log.debug("param:" + localHashMap);
		Map localMap = localBuySettleOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("ID")).longValue();
	}

	public long sellSettleOrder(DelayOrder paramDelayOrder) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'SellSettleOrder' method");
		}
		Assert.hasText(paramDelayOrder.getFirmID());
		Assert.hasText(paramDelayOrder.getCommodityID());
		Assert.notNull(paramDelayOrder.getPrice());
		Assert.notNull(paramDelayOrder.getQuantity());
		SellSettleOrderStoredProcedure localSellSettleOrderStoredProcedure = new SellSettleOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramDelayOrder.getFirmID());
		localHashMap.put("p_TraderID", paramDelayOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramDelayOrder.getCommodityID());
		localHashMap.put("p_Quantity", paramDelayOrder.getQuantity());
		localHashMap.put("p_Price", paramDelayOrder.getPrice());
		localHashMap.put("p_CustomerID", paramDelayOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramDelayOrder.getConsignerID());
		localHashMap.put("p_DelayQuoShowType", paramDelayOrder.getDelayQuoShowType());
		localHashMap.put("p_DelayNeedBill", paramDelayOrder.getDelayNeedBill());
		this.log.debug("param:" + localHashMap);
		Map localMap = localSellSettleOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("ID")).longValue();
	}

	public int withdraw(DelayOrder paramDelayOrder) {
		WithdrawStoredProcedure localWithdrawStoredProcedure = new WithdrawStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_WithdrawerID", paramDelayOrder.getWithdrawerID());
		localHashMap.put("p_A_OrderNo_W", paramDelayOrder.getWithdrawID());
		localHashMap.put("p_WithdrawType", paramDelayOrder.getWithdrawType());
		localHashMap.put("p_Quantity", paramDelayOrder.getQuantity());
		localHashMap.put("p_DelayQuoShowType", paramDelayOrder.getDelayQuoShowType());
		this.log.debug("param:" + localHashMap);
		Map localMap = localWithdrawStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int settleMatch() {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'SettleMatch' method");
		}
		SettleMatchStoredProcedure localSettleMatchStoredProcedure = new SettleMatchStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		Map localMap = localSettleMatchStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int neutralMatch() {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'NeutralMatch' method");
		}
		NeutralMatchStoredProcedure localNeutralMatchStoredProcedure = new NeutralMatchStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		Map localMap = localNeutralMatchStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public void updateDelayQuotation() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(" update T_DelayQuotation a set (BuySettleQty,SellSettleQty,BuyNeutralQty,SellNeutralQty,CreateTime) = ")
				.append(" ( select ")
				.append(" nvl(sum(case when DelayOrderType=1 and BS_Flag=1 then decode(WithdrawType,1,TradeQty,Quantity) else 0 end),0) BuySettleQty,")
				.append(" nvl(sum(case when DelayOrderType=1 and BS_Flag=2 then decode(WithdrawType,1,TradeQty,Quantity) else 0 end),0) SellSettleQty,")
				.append(" nvl(sum(case when DelayOrderType=2 and BS_Flag=1 then decode(WithdrawType,1,TradeQty,Quantity) else 0 end),0) BuyNeutralQty,")
				.append(" nvl(sum(case when DelayOrderType=2 and BS_Flag=2 then decode(WithdrawType,1,TradeQty,Quantity) else 0 end),0) SellNeutralQty,")
				.append(" sysdate from T_DelayOrders x ").append(" where x.CommodityID=a.CommodityID group by x.CommodityID  )")
				.append(" where a.CommodityID in(select distinct CommodityID from T_DelayOrders)");
		String str = localStringBuffer.toString();
		this.log.debug("sql: " + str);
		getJdbcTemplate().update(str);
	}

	public void updateDelaySettleQuotation() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(" update T_DelayQuotation a set (BuySettleQty,SellSettleQty,CreateTime) = ").append(" ( select ")
				.append(" nvl(sum(case when DelayOrderType=1 and BS_Flag=1 then decode(WithdrawType,1,TradeQty,Quantity) else 0 end),0) BuySettleQty,")
				.append(" nvl(sum(case when DelayOrderType=1 and BS_Flag=2 then decode(WithdrawType,1,TradeQty,Quantity) else 0 end),0) SellSettleQty,")
				.append(" sysdate from T_DelayOrders x ").append(" where x.CommodityID=a.CommodityID group by x.CommodityID  )")
				.append(" where a.CommodityID in(select distinct CommodityID from T_DelayOrders)");
		String str = localStringBuffer.toString();
		this.log.debug("sql: " + str);
		getJdbcTemplate().update(str);
	}

	public List getNotTradeOrderNos() {
		ArrayList localArrayList = new ArrayList();
		String str = "select A_OrderNo from T_DelayOrders where Status in(1,2) order by A_OrderNo";
		this.log.debug("sql: " + str);
		List localList = getJdbcTemplate().queryForList(str);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			Long localLong = localMap.get("A_OrderNo") == null ? null : new Long(((BigDecimal) localMap.get("A_OrderNo")).longValue());
			localArrayList.add(localLong);
		}
		return localArrayList;
	}

	public Map getNeutralSideMap() {
		HashMap localHashMap = new HashMap();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select CommodityID, nvl(sum(decode(BS_Flag,1,Quantity-TradeQty,0)),0) BuySettleQty,")
				.append("nvl(sum(decode(BS_Flag,2,Quantity-TradeQty,0)),0) SellSettleQty  ").append("from T_DelayOrders  ")
				.append("where DelayOrderType=1 and Status in(1,2) group by CommodityID ");
		String str1 = localStringBuffer.toString();
		this.log.debug("sql: " + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		for (int i = 0; i < localList.size(); i++) {
			Map localMap = (Map) localList.get(i);
			DelayQuotation localDelayQuotation = new DelayQuotation();
			String str2 = (String) localMap.get("CommodityID");
			long l1 = localMap.get("BuySettleQty") == null ? 0L : ((BigDecimal) localMap.get("BuySettleQty")).longValue();
			long l2 = localMap.get("SellSettleQty") == null ? 0L : ((BigDecimal) localMap.get("SellSettleQty")).longValue();
			localDelayQuotation.setCommodityID(str2);
			localDelayQuotation.setBuySettleQty(new Long(l1));
			localDelayQuotation.setSellSettleQty(new Long(l2));
			short s = 0;
			if (l1 > l2) {
				s = 2;
			} else if (l1 < l2) {
				s = 1;
			}
			localDelayQuotation.setNeutralSide(new Short(s));
			localHashMap.put(str2, localDelayQuotation);
		}
		return localHashMap;
	}

	public void updateNeutralSide(DelayQuotation paramDelayQuotation) {
		String str = "update T_DelayQuotation set NeutralSide=? where CommodityID=? ";
		Object[] arrayOfObject = { paramDelayQuotation.getNeutralSide(), paramDelayQuotation.getCommodityID() };
		int[] arrayOfInt = { 5, 12 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
	}

	public int delaySettleBill(ApplyBill paramApplyBill) {
		String str = "select count(*) from T_ValidBill where BillID = ? and Status=1";
		this.log.debug("sql: " + str);
		if (getJdbcTemplate().queryForInt(str, new Object[] { paramApplyBill.getBillID() }) > 0) {
			return 2;
		}
		str = "insert into T_ValidBill(ValidID,ApplyID,CommodityID,FirmID_S,BillID,Quantity,BillType,Status,CreateTime,Creator) values(SEQ_T_ValidBill.nextval,?,?,?,?,?,5,1,sysdate,?)";
		Object[] arrayOfObject = { paramApplyBill.getApplyID(), paramApplyBill.getCommodityID(), paramApplyBill.getFirmID_S(),
				paramApplyBill.getBillID(), paramApplyBill.getQuantity(), paramApplyBill.getModifier() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
			return 1;
		} catch (DataAccessException localDataAccessException) {
		}
		return -100;
	}

	public int delaySettleBillCancel(ApplyBill paramApplyBill) {
		String str = "select count(*) from T_ValidBill where ValidID = ? and Status=0";
		this.log.debug("sql: " + str);
		if (getJdbcTemplate().queryForInt(str, new Object[] { paramApplyBill.getValidID() }) > 0) {
			return 2;
		}
		str = "select nvl(FrozenDelayQty,0) from T_ValidBill where ValidID = ? and Status=1";
		this.log.debug("sql: " + str);
		if (getJdbcTemplate().queryForLong(str, new Object[] { paramApplyBill.getValidID() }) > 0L) {
			return 3;
		}
		str = "update T_ValidBill set Status=0,ModifyTime=sysdate,Modifier=? where ValidID=?";
		Object[] arrayOfObject = { paramApplyBill.getModifier(), paramApplyBill.getValidID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
			return 1;
		} catch (DataAccessException localDataAccessException) {
		}
		return -100;
	}

	public long buyNeutralOrder(DelayOrder paramDelayOrder) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'buyNeutralOrder' method");
		}
		Assert.hasText(paramDelayOrder.getFirmID());
		Assert.hasText(paramDelayOrder.getCommodityID());
		Assert.notNull(paramDelayOrder.getPrice());
		Assert.notNull(paramDelayOrder.getQuantity());
		BuyNeutralOrderStoredProcedure localBuyNeutralOrderStoredProcedure = new BuyNeutralOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramDelayOrder.getFirmID());
		localHashMap.put("p_TraderID", paramDelayOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramDelayOrder.getCommodityID());
		localHashMap.put("p_Quantity", paramDelayOrder.getQuantity());
		localHashMap.put("p_Price", paramDelayOrder.getPrice());
		localHashMap.put("p_CustomerID", paramDelayOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramDelayOrder.getConsignerID());
		localHashMap.put("p_DelayQuoShowType", paramDelayOrder.getDelayQuoShowType());
		this.log.debug("param:" + localHashMap);
		Map localMap = localBuyNeutralOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("ID")).longValue();
	}

	public long sellNeutralOrder(DelayOrder paramDelayOrder) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'sellNeutralOrder' method");
		}
		Assert.hasText(paramDelayOrder.getFirmID());
		Assert.hasText(paramDelayOrder.getCommodityID());
		Assert.notNull(paramDelayOrder.getPrice());
		Assert.notNull(paramDelayOrder.getQuantity());
		SellNeutralOrderStoredProcedure localSellNeutralOrderStoredProcedure = new SellNeutralOrderStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_FirmID", paramDelayOrder.getFirmID());
		localHashMap.put("p_TraderID", paramDelayOrder.getTraderID());
		localHashMap.put("p_CommodityID", paramDelayOrder.getCommodityID());
		localHashMap.put("p_Quantity", paramDelayOrder.getQuantity());
		localHashMap.put("p_Price", paramDelayOrder.getPrice());
		localHashMap.put("p_CustomerID", paramDelayOrder.getCustomerID());
		localHashMap.put("p_ConsignerID", paramDelayOrder.getConsignerID());
		localHashMap.put("p_DelayQuoShowType", paramDelayOrder.getDelayQuoShowType());
		localHashMap.put("p_DelayNeedBill", paramDelayOrder.getDelayNeedBill());
		this.log.debug("param:" + localHashMap);
		Map localMap = localSellNeutralOrderStoredProcedure.execute(localHashMap);
		return ((Long) localMap.get("ID")).longValue();
	}

	public void loadCommoditySettleProp(Map paramMap) {
		if (paramMap == null) {
			return;
		}
		paramMap.clear();
		String str1 = "select * from T_A_CommoditySettleProp order by COMMODITYID";
		this.log.debug("sql: " + str1);
		List localList = getJdbcTemplate().queryForList(str1);
		if ((localList != null) && (localList.size() > 0)) {
			Object localObject1 = null;
			for (int i = 0; i < localList.size(); i++) {
				Map localMap = (Map) localList.get(i);
				Integer localInteger = new Integer(((BigDecimal) localMap.get("SECTIONID")).intValue());
				String str2 = (String) localMap.get("COMMODITYID");
				Object localObject2 = (Map) paramMap.get(localInteger);
				if (localObject2 == null) {
					localObject2 = new HashMap();
				}
				((Map) localObject2).put(str2, str2);
				paramMap.put(localInteger, localObject2);
			}
		}
	}

	private class SellNeutralOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_SellNeutralOrder";

		public SellNeutralOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_SellNeutralOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("ID", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			declareParameter(new SqlParameter("p_DelayQuoShowType", 5));
			declareParameter(new SqlParameter("p_DelayNeedBill", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class BuyNeutralOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_BuyNeutralOrder";

		public BuyNeutralOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_BuyNeutralOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("ID", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			declareParameter(new SqlParameter("p_DelayQuoShowType", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class NeutralMatchStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_NeutralMatch";

		public NeutralMatchStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_NeutralMatch");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class SettleMatchStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_SettleMatch";

		public SettleMatchStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_SettleMatch");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class WithdrawStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_Order_WD";

		public WithdrawStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_Order_WD");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_WithdrawerID", 12));
			declareParameter(new SqlParameter("p_A_OrderNo_W", -5));
			declareParameter(new SqlParameter("p_WithdrawType", 5));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_DelayQuoShowType", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class SellSettleOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_SellSettleOrder";

		public SellSettleOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_SellSettleOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("ID", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			declareParameter(new SqlParameter("p_DelayQuoShowType", 5));
			declareParameter(new SqlParameter("p_DelayNeedBill", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class BuySettleOrderStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_D_BuySettleOrder";

		public BuySettleOrderStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_D_BuySettleOrder");
			setFunction(true);
			declareParameter(new SqlOutParameter("ID", -5));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_TraderID", 12));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlParameter("p_ConsignerID", 12));
			declareParameter(new SqlParameter("p_TradeMargin_B", 8));
			declareParameter(new SqlParameter("p_DelayQuoShowType", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
