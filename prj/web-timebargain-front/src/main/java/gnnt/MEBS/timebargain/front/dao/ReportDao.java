package gnnt.MEBS.timebargain.front.dao;

import java.util.List;
import java.util.Map;

public abstract interface ReportDao {
	public abstract List<Map<Object, Object>> firmFundsQuery(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> getFundFields(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> tradesQuery(Map<String, String> paramMap);

	public abstract int tradesQueryCount(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> tradesQuerySum(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> firmHoldSumQuery(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> transferProfitAndLossQuery(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> tradeSettleQuery(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> validGageBillQuery(Map<String, String> paramMap);

	public abstract List<Map<Object, Object>> fundDetailQuery(Map<String, String> paramMap);

	public abstract List firm_info(String paramString1, String paramString2, String paramString3);
}