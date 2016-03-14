package gnnt.MEBS.member.broker.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.model.BrokerReward;

public class BrokerRewardDao extends DaoHelperImpl {
	public List getBrokerRewardList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select t.* from M_B_BrokerReward t where 1=1 and t.Amount > 0";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		this.logger.debug("sql: " + str);
		return queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public BrokerReward getObject(BrokerReward paramBrokerReward) {
		String str = "select * from m_b_brokerreward where moduleId=? and brokerId=? and OccurDate=?";
		Object[] arrayOfObject = { paramBrokerReward.getModuleId(), paramBrokerReward.getBrokerId(), paramBrokerReward.getOccurDate() };
		int[] arrayOfInt = { 12, 12, 91 };
		List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new BrokerReward()));
		if (localList.size() > 0) {
			paramBrokerReward = (BrokerReward) localList.get(0);
		}
		return paramBrokerReward;
	}

	public void updateBrokerReward(BrokerReward paramBrokerReward) {
		String str = "update m_b_brokerreward set amount=?, paidAmount=?,payDate=? where brokerID=? and occurDate=? and moduleId=?";
		Object[] arrayOfObject = { Double.valueOf(paramBrokerReward.getAmount()), Double.valueOf(paramBrokerReward.getPaidAmount()),
				paramBrokerReward.getPayDate(), paramBrokerReward.getBrokerId(), paramBrokerReward.getOccurDate(), paramBrokerReward.getModuleId() };
		int[] arrayOfInt = { 8, 8, 91, 12, 91, 12 };
		updateBySQL(str, arrayOfObject, arrayOfInt);
	}

	public BrokerReward getObjectLock(BrokerReward paramBrokerReward) {
		String str = "select * from m_b_brokerreward where moduleId=? and brokerId=? and OccurDate=? for update";
		Object[] arrayOfObject = { paramBrokerReward.getModuleId(), paramBrokerReward.getBrokerId(), paramBrokerReward.getOccurDate() };
		int[] arrayOfInt = { 12, 12, 91 };
		List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new BrokerReward()));
		if (localList.size() > 0) {
			paramBrokerReward = (BrokerReward) localList.get(0);
		}
		return paramBrokerReward;
	}

	public double updateFunds(String paramString1, String paramString2, double paramDouble) {
		UpdateFundsProcedure localUpdateFundsProcedure = new UpdateFundsProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_firmID", paramString1);
		localHashMap.put("p_oprcode", paramString2);
		localHashMap.put("p_amount", Double.valueOf(paramDouble));
		localHashMap.put("p_voucherNo", null);
		Map localMap = localUpdateFundsProcedure.execute(localHashMap);
		double d = ((BigDecimal) localMap.get("ret")).doubleValue();
		return d;
	}

	private class UpdateFundsProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_F_UpdateFunds";

		public UpdateFundsProcedure(DataSource paramDataSource) {
			super(paramDataSource, "FN_F_UpdateFunds");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_firmID", 12));
			declareParameter(new SqlParameter("p_oprcode", 12));
			declareParameter(new SqlParameter("p_amount", 2));
			declareParameter(new SqlParameter("p_voucherNo", 2));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
