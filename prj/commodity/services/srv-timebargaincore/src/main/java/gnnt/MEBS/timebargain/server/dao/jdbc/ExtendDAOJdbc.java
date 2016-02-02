package gnnt.MEBS.timebargain.server.dao.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

import gnnt.MEBS.timebargain.server.dao.ExtendDAO;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Settle;

public class ExtendDAOJdbc extends BaseDAOJdbc implements ExtendDAO {
	private Log log = LogFactory.getLog(getClass());

	public int gage(ApplyBill paramApplyBill) {
		Assert.hasText(paramApplyBill.getCustomerID_S());
		Assert.hasText(paramApplyBill.getCommodityID());
		Assert.notNull(paramApplyBill.getQuantity());
		GageStoredProcedure localGageStoredProcedure = new GageStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_ApplyID", paramApplyBill.getApplyID());
		localHashMap.put("p_CommodityID", paramApplyBill.getCommodityID());
		localHashMap.put("p_BS_Flag", paramApplyBill.getBS_Flag());
		localHashMap.put("p_CustomerID", paramApplyBill.getCustomerID_S());
		localHashMap.put("p_BillID", paramApplyBill.getBillID());
		localHashMap.put("p_Quantity", paramApplyBill.getQuantity());
		localHashMap.put("p_Modifier", paramApplyBill.getModifier());
		this.log.debug("param:" + localHashMap);
		Map localMap = localGageStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int gageCancel(ApplyBill paramApplyBill) {
		Assert.notNull(paramApplyBill.getValidID());
		Assert.notNull(paramApplyBill.getApplyType());
		GageCancelStoredProcedure localGageCancelStoredProcedure = new GageCancelStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_ValidID", paramApplyBill.getValidID());
		localHashMap.put("p_Modifier", paramApplyBill.getModifier());
		localHashMap.put("p_ApplyType", paramApplyBill.getApplyType());
		this.log.debug("param:" + localHashMap);
		Map localMap = localGageCancelStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int aheadSettle(ApplyBill paramApplyBill) {
		Assert.hasText(paramApplyBill.getCommodityID());
		Assert.notNull(paramApplyBill.getPrice());
		Assert.hasText(paramApplyBill.getCustomerID_B());
		Assert.hasText(paramApplyBill.getCustomerID_S());
		AheadSettleStoredProcedure localAheadSettleStoredProcedure = new AheadSettleStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_ApplyID", paramApplyBill.getApplyID());
		localHashMap.put("p_CommodityID", paramApplyBill.getCommodityID());
		localHashMap.put("p_BillID", paramApplyBill.getBillID());
		localHashMap.put("p_Quantity", paramApplyBill.getQuantity());
		localHashMap.put("p_Price", paramApplyBill.getPrice());
		localHashMap.put("p_sCustomerID", paramApplyBill.getCustomerID_S());
		localHashMap.put("p_sGageQty", paramApplyBill.getSGageQty());
		localHashMap.put("p_bCustomerID", paramApplyBill.getCustomerID_B());
		localHashMap.put("p_bGageQty", paramApplyBill.getBGageQty());
		localHashMap.put("p_Modifier", paramApplyBill.getModifier());
		localHashMap.put("p_ApplyType", paramApplyBill.getApplyType());
		localHashMap.put("p_ValidID", paramApplyBill.getValidID());
		this.log.debug("param:" + localHashMap);
		Map localMap = localAheadSettleStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int conferClose(Settle paramSettle) {
		Assert.hasText(paramSettle.getCommodityID());
		Assert.notNull(paramSettle.getPrice());
		Assert.hasText(paramSettle.getBCustomerID());
		Assert.hasText(paramSettle.getSCustomerID());
		ConferCloseStoredProcedure localConferCloseStoredProcedure = new ConferCloseStoredProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_CommodityID", paramSettle.getCommodityID());
		localHashMap.put("p_Price", paramSettle.getPrice());
		localHashMap.put("p_bCustomerID", paramSettle.getBCustomerID());
		localHashMap.put("p_bHoldQty", paramSettle.getBHoldQty());
		localHashMap.put("p_bGageQty", paramSettle.getBGageQty());
		localHashMap.put("p_sCustomerID", paramSettle.getSCustomerID());
		localHashMap.put("p_sHoldQty", paramSettle.getSHoldQty());
		localHashMap.put("p_sGageQty", paramSettle.getSGageQty());
		this.log.debug("param:" + localHashMap);
		Map localMap = localConferCloseStoredProcedure.execute(localHashMap);
		return ((Integer) localMap.get("ret")).intValue();
	}

	public int waitSettle(ApplyBill paramApplyBill) {
		String str = "select count(*) from T_ValidBill where BillID = ? and Status=1";
		this.log.debug("sql: " + str);
		if (getJdbcTemplate().queryForInt(str, new Object[] { paramApplyBill.getBillID() }) > 0) {
			return 2;
		}
		str = "insert into T_ValidBill(ValidID,ApplyID,CommodityID,FirmID_S,BillID,Quantity,BillType,Status,CreateTime,Creator) values(SEQ_T_ValidBill.nextval,?,?,?,?,?,3,1,sysdate,?)";
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

	public int waitSettleCancel(ApplyBill paramApplyBill) {
		String str = "select count(*) from T_ValidBill where ValidID = ? and Status=0";
		this.log.debug("sql: " + str);
		if (getJdbcTemplate().queryForInt(str, new Object[] { paramApplyBill.getValidID() }) > 0) {
			return 2;
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

	private class ConferCloseStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_ConferClose";

		public ConferCloseStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_ConferClose");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_bCustomerID", 12));
			declareParameter(new SqlParameter("p_bHoldQty", -5));
			declareParameter(new SqlParameter("p_bGageQty", -5));
			declareParameter(new SqlParameter("p_sCustomerID", 12));
			declareParameter(new SqlParameter("p_sHoldQty", -5));
			declareParameter(new SqlParameter("p_sGageQty", -5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class AheadSettleStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_AheadSettle";

		public AheadSettleStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_AheadSettle");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_ApplyID", -5));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_BillID", 12));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_Price", 8));
			declareParameter(new SqlParameter("p_sCustomerID", 12));
			declareParameter(new SqlParameter("p_sGageQty", -5));
			declareParameter(new SqlParameter("p_bCustomerID", 12));
			declareParameter(new SqlParameter("p_bGageQty", -5));
			declareParameter(new SqlParameter("p_Modifier", 12));
			declareParameter(new SqlParameter("p_ApplyType", 5));
			declareParameter(new SqlParameter("p_ValidID", -5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class GageCancelStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_GageCancel";

		public GageCancelStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_GageCancel");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_ValidID", -5));
			declareParameter(new SqlParameter("p_Modifier", 12));
			declareParameter(new SqlParameter("p_ApplyType", 5));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class GageStoredProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_T_Gage";

		public GageStoredProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_T_Gage");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 4));
			declareParameter(new SqlParameter("p_ApplyID", -5));
			declareParameter(new SqlParameter("p_CommodityID", 12));
			declareParameter(new SqlParameter("p_BS_Flag", 5));
			declareParameter(new SqlParameter("p_CustomerID", 12));
			declareParameter(new SqlParameter("p_BillID", 12));
			declareParameter(new SqlParameter("p_Quantity", -5));
			declareParameter(new SqlParameter("p_Modifier", 12));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
