package gnnt.MEBS.timebargain.front.service.impl;

import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.timebargain.front.dao.ReportDao;
import gnnt.MEBS.timebargain.front.service.ReportService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reportService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ReportServiceImpl extends StandardService implements ReportService {

	@Autowired
	@Qualifier("reportDao")
	private ReportDao reportDao;

	public ReportDao getReportDao() {
		return this.reportDao;
	}

	public List<Map<Object, Object>> firmFundsQuery(Map<String, String> paramMap) {
		return this.reportDao.firmFundsQuery(paramMap);
	}

	public List<Map<Object, Object>> getFundFields(Map<String, String> paramMap) {
		return this.reportDao.getFundFields(paramMap);
	}

	public List<Map<Object, Object>> tradesQuery(Map<String, String> paramMap) {
		return this.reportDao.tradesQuery(paramMap);
	}

	public int tradesQueryCount(Map<String, String> paramMap) {
		return this.reportDao.tradesQueryCount(paramMap);
	}

	public List<Map<Object, Object>> tradesQuerySum(Map<String, String> paramMap) {
		return this.reportDao.tradesQuerySum(paramMap);
	}

	public List<Map<Object, Object>> firmHoldSumQuery(Map<String, String> paramMap) {
		return this.reportDao.firmHoldSumQuery(paramMap);
	}

	public List<Map<Object, Object>> transferProfitAndLossQuery(Map<String, String> paramMap) {
		return this.reportDao.transferProfitAndLossQuery(paramMap);
	}

	public List<Map<Object, Object>> tradeSettleQuery(Map<String, String> paramMap) {
		return this.reportDao.tradeSettleQuery(paramMap);
	}

	public List<Map<Object, Object>> validGageBillQuery(Map<String, String> paramMap) {
		return this.reportDao.validGageBillQuery(paramMap);
	}

	public List<Map<Object, Object>> fundDetailQuery(Map<String, String> paramMap) {
		return this.reportDao.fundDetailQuery(paramMap);
	}

	public List firm_info(String paramString1, String paramString2, String paramString3) {
		return this.reportDao.firm_info(paramString1, paramString2, paramString3);
	}
}