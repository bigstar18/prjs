package gnnt.MEBS.common.core.dao.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import gnnt.MEBS.common.core.dao.ObjectRowMapper;
import gnnt.MEBS.common.core.dao.SysSchedulerDAO;
import gnnt.MEBS.common.core.po.MarketInfoPO;
import gnnt.MEBS.common.core.po.TradeModelPO;

public class SysSchedulerDAOJdbc extends BaseDAOJdbc implements SysSchedulerDAO {
	public TradeModelPO getTradeModel(int paramInt) {
		TradeModelPO localTradeModelPO = null;
		String str = "select * from c_trademodule where moduleid=? ";
		Object[] arrayOfObject = { Integer.valueOf(paramInt) };
		List localList = getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new TradeModelPO()));
		if (localList.size() > 0) {
			localTradeModelPO = (TradeModelPO) localList.get(0);
		}
		return localTradeModelPO;
	}

	public Date getCurDbDate() {
		String str = "select sysdate from dual";
		this.log.debug("sql: " + str);
		return (Date) getJdbcTemplate().queryForObject(str, Date.class);
	}

	public void moveHistory(Date paramDate) {
		SPMoveHistoryProcedure localSPMoveHistoryProcedure = new SPMoveHistoryProcedure(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_EndDate", paramDate);
		Map localMap = localSPMoveHistoryProcedure.execute(localHashMap);
		System.out.println(localMap);
	}

	public MarketInfoPO getMarketInfo(String paramString) {
		MarketInfoPO localMarketInfoPO = null;
		String str = "select * from C_MARKETINFO where INFONAME=?";
		Object[] arrayOfObject = { paramString };
		List localList = getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new MarketInfoPO()));
		if ((localList != null) && (localList.size() > 0)) {
			localMarketInfoPO = (MarketInfoPO) localList.get(0);
		}
		return localMarketInfoPO;
	}

	private class SPMoveHistoryProcedure extends StoredProcedure {
		private static final String SFUNC_NAME = "SP_COMMON_MoveHistory";

		public SPMoveHistoryProcedure(DataSource paramDataSource) {
			super(paramDataSource, "SP_COMMON_MoveHistory");
			declareParameter(new SqlParameter("p_EndDate", 93));
			compile();
		}

		public Map<String, Object> execute(Map<String, ?> paramMap) {
			return super.execute(paramMap);
		}
	}
}
