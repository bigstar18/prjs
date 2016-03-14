package gnnt.MEBS.zcjs.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class MoneyDoDao extends DaoHelperImpl {
	public double getRealFunds(String paramString, int paramInt) {
		double d = 0.0D;
		GetRealFunds localGetRealFunds = new GetRealFunds(getDataSource());
		HashMap localHashMap = new HashMap();
		System.out.println("getRealFunds======firmId======" + paramString);
		System.out.println("getRealFunds======isLock======" + paramInt);
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

	public double updateFrozenFunds(String paramString1, double paramDouble, String paramString2) {
		UpdateFrozenFunds localUpdateFrozenFunds = new UpdateFrozenFunds(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_firmid", paramString1);
		localHashMap.put("p_amount", Double.valueOf(paramDouble));
		localHashMap.put("p_moduleID", paramString2);
		Map localMap = localUpdateFrozenFunds.execute(localHashMap);
		double d = ((BigDecimal) localMap.get("ret")).doubleValue();
		return d;
	}

	private class UpdateFrozenFunds extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_F_UpdateFrozenFunds";

		public UpdateFrozenFunds(DataSource paramDataSource) {
			super(paramDataSource, "FN_F_UpdateFrozenFunds");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_firmid", 12));
			declareParameter(new SqlParameter("p_amount", 2));
			declareParameter(new SqlParameter("p_moduleID", 12));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
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
}
