package gnnt.MEBS.timebargain.front.action;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.timebargain.front.common.Page;
import gnnt.MEBS.timebargain.front.service.ReportService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("reportAction")
@Scope("request")
public class ReportAction extends StandardAction {
	private static final long serialVersionUID = -6309893987269409524L;

	@Autowired
	@Qualifier("reportService")
	private ReportService reportService;

	public ReportService getReportService() {
		return this.reportService;
	}

	public String firmFundsQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		HashMap localHashMap = new HashMap();
		localHashMap.put("firmid", localUser.getBelongtoFirm().getFirmID());
		localHashMap.put("b_date", this.request.getParameter("gnnt_b_date"));
		List localList1 = this.reportService.firmFundsQuery(localHashMap);
		List localList2 = this.reportService.getFundFields(localHashMap);
		this.request.setAttribute("fundList", localList1);
		this.request.setAttribute("fundFieldList", localList2);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("query", "query");
		return "success";
	}

	public String tradesQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("Entering 'tradesQuery' method [交易员代码firmid=" + localUser.getBelongtoFirm().getFirmID() + "]");
		PageRequest localPageRequest = null;
		try {
			localPageRequest = ActionUtil.getPageRequest(this.request);
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
		}
		String str1 = this.request.getParameter("d1") == null ? "" : this.request.getParameter("d1");
		String str2 = this.request.getParameter("d2") == null ? "" : this.request.getParameter("d2");
		if ((str1.equals("")) || (str1.equals("0")))
			str1 = Tools.fmtDate(new Date());
		if ((str2.equals("")) || (str2.equals("0")))
			str2 = str1;
		HashMap localHashMap = new HashMap();
		localHashMap.put("a.firmID", localUser.getBelongtoFirm().getFirmID());
		localHashMap.put("startDate", str1);
		localHashMap.put("endDate", str2);
		int i = (localPageRequest.getPageNumber() - 1) * localPageRequest.getPageSize() + 1;
		int j = localPageRequest.getPageNumber() * localPageRequest.getPageSize();
		localHashMap.put("startCount", String.valueOf(i));
		localHashMap.put("endCount", String.valueOf(j));
		List localList1 = this.reportService.tradesQuery(localHashMap);
		int k = 0;
		double d = 0.0D;
		List localList2 = this.reportService.tradesQuerySum(localHashMap);
		if ((localList2 != null) && (localList2.size() > 0)) {
			Map localMap = (Map) localList2.get(0);
			k = Integer.parseInt(localMap.get("QUANTITYSUM").toString());
			d = Double.parseDouble(localMap.get("TRADEFEESUM").toString());
		}
		int m = this.reportService.tradesQueryCount(localHashMap);
		Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), m);
		localPage.setResult(localList1);
		int i1 = localPageRequest.getPageSize();
		int n;
		if (i1 > 0) {
			if (m % i1 == 0)
				n = m / i1;
			else if (m < i1)
				n = 1;
			else
				n = m / i1 + 1;
		} else
			n = 1;
		localPage.setTotalPage(n);
		this.request.setAttribute("tradesList", localList1);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("d1", str1);
		this.request.setAttribute("d2", str2);
		this.request.setAttribute("quantitySum", Integer.valueOf(k));
		this.request.setAttribute("tradefeeSum", Double.valueOf(d));
		this.request.setAttribute("query", "query");
		return "success";
	}

	public String transferProfitAndLossQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("Entering 'transferProfitAndLossQuery' method [交易员代码firmid=" + localUser.getBelongtoFirm().getFirmID() + "]");
		PageRequest localPageRequest = null;
		try {
			localPageRequest = ActionUtil.getPageRequest(this.request);
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
		}
		String str = this.request.getParameter("d1") == null ? "" : this.request.getParameter("d1");
		if ((str.equals("")) || (str.equals("0")))
			str = Tools.fmtDate(new Date());
		HashMap localHashMap = new HashMap();
		localHashMap.put("a.firmID", localUser.getBelongtoFirm().getFirmID());
		localHashMap.put("a.ClearDate", str);
		int i = (localPageRequest.getPageNumber() - 1) * localPageRequest.getPageSize() + 1;
		int j = localPageRequest.getPageNumber() * localPageRequest.getPageSize();
		localHashMap.put("startCount", String.valueOf(i));
		localHashMap.put("endCount", String.valueOf(j));
		List localList = this.reportService.transferProfitAndLossQuery(localHashMap);
		int k = localList.size();
		Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), k);
		localPage.setResult(localList);
		int n = localPageRequest.getPageSize();
		int m;
		if (n > 0) {
			if (k % n == 0)
				m = k / n;
			else if (k < n)
				m = 1;
			else
				m = k / n + 1;
		} else
			m = 1;
		localPage.setTotalPage(m);
		this.request.setAttribute("transferProfitAndLossList", localList);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("d1", str);
		this.request.setAttribute("query", "query");
		return "success";
	}

	public String firmHoldSumQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("Entering 'firmHoldSumQuery' method [交易员代码firmid=" + localUser.getBelongtoFirm().getFirmID() + "]");
		PageRequest localPageRequest = null;
		try {
			localPageRequest = ActionUtil.getPageRequest(this.request);
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
		}
		String str = this.request.getParameter("d1") == null ? "" : this.request.getParameter("d1");
		if ((str.equals("")) || (str.equals("0")))
			str = Tools.fmtDate(new Date());
		HashMap localHashMap = new HashMap();
		localHashMap.put("a.firmID", localUser.getBelongtoFirm().getFirmID());
		localHashMap.put("a.ClearDate", str);
		int i = (localPageRequest.getPageNumber() - 1) * localPageRequest.getPageSize() + 1;
		int j = localPageRequest.getPageNumber() * localPageRequest.getPageSize();
		localHashMap.put("startCount", String.valueOf(i));
		localHashMap.put("endCount", String.valueOf(j));
		List localList = this.reportService.firmHoldSumQuery(localHashMap);
		int k = localList.size();
		Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), k);
		localPage.setResult(localList);
		int n = localPageRequest.getPageSize();
		int m;
		if (n > 0) {
			if (k % n == 0)
				m = k / n;
			else if (k < n)
				m = 1;
			else
				m = k / n + 1;
		} else
			m = 1;
		localPage.setTotalPage(m);
		this.request.setAttribute("holdSumList", localList);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("d1", str);
		this.request.setAttribute("query", "query");
		return "success";
	}

	public String validGageBillQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("Entering 'validGageBillQuery' method [交易员代码firmid=" + localUser.getBelongtoFirm().getFirmID() + "]");
		PageRequest localPageRequest = null;
		try {
			localPageRequest = ActionUtil.getPageRequest(this.request);
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
		}
		HashMap localHashMap = new HashMap();
		localHashMap.put("a.firmID", localUser.getBelongtoFirm().getFirmID());
		String str = this.request.getParameter("commodityID");
		localHashMap.put("a.commodityID", str);
		int i = (localPageRequest.getPageNumber() - 1) * localPageRequest.getPageSize() + 1;
		int j = localPageRequest.getPageNumber() * localPageRequest.getPageSize();
		localHashMap.put("startCount", String.valueOf(i));
		localHashMap.put("endCount", String.valueOf(j));
		List localList = this.reportService.validGageBillQuery(localHashMap);
		int k = localList.size();
		Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), k);
		localPage.setResult(localList);
		int n = localPageRequest.getPageSize();
		int m;
		if (n > 0) {
			if (k % n == 0)
				m = k / n;
			else if (k < n)
				m = 1;
			else
				m = k / n + 1;
		} else
			m = 1;
		localPage.setTotalPage(m);
		this.request.setAttribute("commodityID", str);
		this.request.setAttribute("validGageBillList", localList);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("query", "query");
		return "success";
	}

	public String fundDetailQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("Entering 'fundDetailQuery' method [交易员代码firmid=" + localUser.getBelongtoFirm().getFirmID() + "]");
		HashMap localHashMap = new HashMap();
		localHashMap.put("a.firmID", localUser.getBelongtoFirm().getFirmID());
		List localList1 = this.reportService.fundDetailQuery(localHashMap);
		List localList2 = this.reportService.firm_info(localUser.getBelongtoFirm().getFirmID(), localUser.getTraderID(), localUser.getName());
		this.request.setAttribute("fundDetail", localList1);
		this.request.setAttribute("futureList", localList2);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("query", "query");
		return "success";
	}

	public String tradeSettleQuery() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("Entering 'tradeSettleQuery' method [交易员代码firmid=" + localUser.getBelongtoFirm().getFirmID() + "]");
		PageRequest localPageRequest = null;
		try {
			localPageRequest = ActionUtil.getPageRequest(this.request);
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
		}
		String str = this.request.getParameter("d1") == null ? "" : this.request.getParameter("d1");
		if ((str.equals("")) || (str.equals("0")))
			str = Tools.fmtDate(new Date());
		HashMap localHashMap = new HashMap();
		localHashMap.put("shp.firmID", localUser.getBelongtoFirm().getFirmID());
		localHashMap.put("shp.settleprocessdate", str);
		int i = (localPageRequest.getPageNumber() - 1) * localPageRequest.getPageSize() + 1;
		int j = localPageRequest.getPageNumber() * localPageRequest.getPageSize();
		localHashMap.put("startCount", String.valueOf(i));
		localHashMap.put("endCount", String.valueOf(j));
		List localList = this.reportService.tradeSettleQuery(localHashMap);
		int k = localList.size();
		Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), k);
		localPage.setResult(localList);
		int n = localPageRequest.getPageSize();
		int m;
		if (n > 0) {
			if (k % n == 0)
				m = k / n;
			else if (k < n)
				m = 1;
			else
				m = k / n + 1;
		} else
			m = 1;
		localPage.setTotalPage(m);
		this.request.setAttribute("tradeSettleList", localList);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("d1", str);
		this.request.setAttribute("query", "query");
		return "success";
	}
}