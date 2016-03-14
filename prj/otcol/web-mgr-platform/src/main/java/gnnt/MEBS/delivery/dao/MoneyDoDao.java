package gnnt.MEBS.delivery.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class MoneyDoDao extends DaoHelperImpl {
	public List getFundflowList(String paramString, long paramLong) {
		String str = "select fundflowid,firmid,oprcode,amount from (select * from  (select t.fundflowid,t.firmid,t.oprcode,t.amount from f_fundflow t where t.contractno='"
				+ paramString + "') " + "union " + "(select t.fundflowid,t.firmid,t.oprcode,t.amount from f_h_fundflow t where t.contractno='"
				+ paramString + "')) where fundflowid>" + paramLong + " " + "order by fundflowid";
		return queryBySQL(str);
	}

	public double getRealFunds(String paramString, int paramInt) {
		double d = 0.0D;
		GetRealFunds localGetRealFunds = new GetRealFunds(getDataSource());
		HashMap localHashMap = new HashMap();
		this.logger.debug("firmId:" + paramString);
		localHashMap.put("p_firmid", paramString);
		localHashMap.put("p_lock", Integer.valueOf(paramInt));
		Map localMap = localGetRealFunds.execute(localHashMap);
		d = ((BigDecimal) localMap.get("ret")).doubleValue();
		return d;
	}

	public double updateFundsFull(String paramString1, String paramString2, double paramDouble, String paramString3, String paramString4) {
		UpdateFundsFullProcedure localUpdateFundsFullProcedure = new UpdateFundsFullProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_firmID", paramString1);
		localHashMap.put("p_oprcode", paramString2);
		localHashMap.put("p_amount", Double.valueOf(paramDouble));
		localHashMap.put("p_contractNo", paramString3);
		localHashMap.put("p_extraCode", paramString4);
		localHashMap.put("p_appendAmount", null);
		localHashMap.put("p_voucherNo", null);
		Map localMap = localUpdateFundsFullProcedure.execute(localHashMap);
		double d = ((BigDecimal) localMap.get("ret")).doubleValue();
		return d;
	}

	public int updateSettleMargin(String paramString1, String paramString2, double paramDouble) {
		UpdateSettleMarginProcedure localUpdateSettleMarginProcedure = new UpdateSettleMarginProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_moduleid", paramString1);
		localHashMap.put("p_FirmID", paramString2);
		localHashMap.put("p_settleMargin", Double.valueOf(paramDouble));
		Map localMap = localUpdateSettleMarginProcedure.execute(localHashMap);
		int i = ((BigDecimal) localMap.get("ret")).intValue();
		return i;
	}

	private class GetRealFunds extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_F_GetRealFunds";

		public GetRealFunds(DataSource paramDataSource) {
			super(paramDataSource, "FN_F_GetRealFunds");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_firmid", 12));
			declareParameter(new SqlParameter("p_lock", 2));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class UpdateFundsFullProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_F_UpdateFundsFull";

		public UpdateFundsFullProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_F_UpdateFundsFull");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_firmID", 12));
			declareParameter(new SqlParameter("p_oprcode", 12));
			declareParameter(new SqlParameter("p_amount", 2));
			declareParameter(new SqlParameter("p_contractNo", 12));
			declareParameter(new SqlParameter("p_extraCode", 12));
			declareParameter(new SqlParameter("p_appendAmount", 2));
			declareParameter(new SqlParameter("p_voucherNo", 2));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}

	private class UpdateSettleMarginProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_S_UpdateSettleMargin";

		public UpdateSettleMarginProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_S_UpdateSettleMargin");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_moduleid", 12));
			declareParameter(new SqlParameter("p_FirmID", 12));
			declareParameter(new SqlParameter("p_settleMargin", 2));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
