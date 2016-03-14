package gnnt.MEBS.finance.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.base.query.Utils;
import gnnt.MEBS.finance.service.AccountService;
import gnnt.MEBS.finance.service.ViewService;
import gnnt.MEBS.finance.service.VoucherService;
import gnnt.MEBS.finance.unit.Account;
import gnnt.MEBS.finance.util.SysData;

public class ReportController extends MultiActionController {
	private final transient Log logger = LogFactory.getLog(ReportController.class);

	public ModelAndView queryFirmBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryFirmBalance' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "firmId", false);
		}
		List resultList = vis.queryFirmBanlance(qc, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("finance/report/firmList", "resultList", resultList);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);

		return mv;
	}

	public ModelAndView firmFundsForward(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmFundsForward' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		String firmId = request.getParameter("firmId");
		List list = null;
		if ((firmId != null) && (!"".equals(firmId))) {
			list = vis.queryFirmFunds(firmId);
		}
		ModelAndView mv = new ModelAndView("finance/report/firmFunds", "list", list);

		return mv;
	}

	public ModelAndView queryMarketBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryMarketBalance' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		String occurDate = request.getParameter("occurDate");
		Date oDate = null;
		List list = null;
		if ((occurDate != null) && (occurDate.length() > 0)) {
			oDate = (Date) Utils.convert(occurDate, "date");
		}
		if (oDate != null) {
			list = vis.queryMarketBalance(oDate);
		}
		ModelAndView mv = new ModelAndView("finance/report/queryMarketBalance", "resultList", list);
		mv.addObject("occurDate", occurDate);
		return mv;
	}

	public ModelAndView queryContractBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryContractBalance' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);

		List resultList = vis.queryContractBalance(qc, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("finance/report/queryContractBalance", "resultList", resultList);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);

		return mv;
	}

	public ModelAndView redoBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'redoBalance' method...");
		VoucherService vs = (VoucherService) SysData.getBean("f_voucherService");
		String redoBeginDate = ServletRequestUtils.getRequiredStringParameter(request, "redoBeginDate");

		this.logger.debug("redoBeginDate:" + redoBeginDate);

		Date beginDate = (Date) Utils.convert(redoBeginDate, "date");

		String msg = null;
		msg = "重做结算成功！";
		if (!vs.voucherToBalance(beginDate)) {
			msg = "还有编辑中或待审核的凭证，重做结算未成功！";
		}
		return new ModelAndView("finance/public/done", "resultMsg", msg);
	}

	public ModelAndView queryClientLedger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryClientLedger' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		String type = request.getParameter("type");
		String sign = request.getParameter("sign");
		String partitionID = request.getParameter("partitionID");
		List resultList = null;
		List filed = null;
		ModelAndView mv = null;
		if ("sum".equals(sign)) {
			if (pageInfo == null) {
				pageInfo = new PageInfo(1, 15, "firmId", false);
			}
			Map all = vis.queryClientLedgerTotal(qc, pageInfo, partitionID);
			resultList = (List) all.get("value");
			List list = vis.queryFiledMap(partitionID);
			List listTrademodule = vis.getTrademoduleList();
			filed = (List) all.get("filed");
			mv = new ModelAndView("finance/report/queryClientLedgerAll", "resultList", resultList);
			mv.addObject("list", list);
			mv.addObject("listTrademodule", listTrademodule);
		} else {
			if (pageInfo == null) {
				pageInfo = new PageInfo(1, 15, "b_date", false);
			}
			Map all = vis.queryClientLedger(qc, pageInfo, partitionID);
			resultList = (List) all.get("value");
			List list = vis.queryFiledMap(partitionID);
			List listTrademodule = vis.getTrademoduleList();
			filed = (List) all.get("filed");
			mv = new ModelAndView("finance/report/queryClientLedger", "resultList", resultList);
			mv.addObject("list", list);
			mv.addObject("listTrademodule", listTrademodule);
		}
		Map map = WebUtils.getParametersStartingWith(request, "_");

		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);
		mv.addObject("fileds", filed);
		mv.addObject("partitionID", partitionID);
		return mv;
	}

	public ModelAndView queryFundflow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryFundflow' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		VoucherService vs = (VoucherService) SysData.getBean("f_voucherService");
		List moduleList = vis.getTrademoduleList();
		List summaryList = vs.getSummarys(null, null);

		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		String type = request.getParameter("type");
		if (type == null) {
			type = "d";
		}
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "fundFlowID", false);
		}
		List resultList = vis.queryFundflow(type, qc, pageInfo);
		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = null;
		mv = new ModelAndView("finance/report/queryFundflow", "resultList", resultList);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);
		mv.addObject("type", type);
		mv.addObject("moduleList", moduleList);
		mv.addObject("summaryList", summaryList);
		return mv;
	}

	public ModelAndView queryLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryFundflow' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "occurTime", false);
		}
		List resultList = vis.queryLog(qc, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = null;
		mv = new ModelAndView("finance/report/queryLog", "resultList", resultList);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);
		return mv;
	}

	public ModelAndView queryLedger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryLedger' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		AccountService as = (AccountService) SysData.getBean("f_accountService");
		String accountCode = request.getParameter("accountCode");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		Date bDate = null;
		Date eDate = null;
		List list = null;

		Account account = null;
		if ((beginDate != null) && (beginDate.length() > 0)) {
			bDate = (Date) Utils.convert(beginDate, "date");
		}
		if ((endDate != null) && (endDate.length() > 0)) {
			eDate = (Date) Utils.convert(endDate, "date");
		}
		String dCFlag = null;
		String dCFlagName = null;
		BigDecimal lastBal = new BigDecimal(0.0D);
		if ((bDate != null) && (eDate != null) && (accountCode != null)) {
			account = as.getAccountByCode(accountCode);
			if (account != null) {
				dCFlag = account.getDCFlag();
				dCFlagName = "D".equals(dCFlag) ? "借" : "贷";
			}
			list = vis.queryLedger(accountCode, bDate, eDate);
			lastBal = vis.getBeginBalance(accountCode, bDate, true);
		}
		ModelAndView mv = new ModelAndView("finance/report/queryLedger", "resultList", list);
		mv.addObject("accountCode", accountCode);
		mv.addObject("beginDate", beginDate);
		mv.addObject("endDate", endDate);
		mv.addObject("dCFlag", dCFlag);
		mv.addObject("dCFlagName", dCFlagName);
		mv.addObject("balance", lastBal);
		return mv;
	}

	public ModelAndView queryAccountLedger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryAccountLedger' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		String accountCode = request.getParameter("accountCode");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		List list = null;
		String today = Utils.formatDate("yyyy-MM-dd", new Date());
		if (beginDate == null) {
			beginDate = today;
		}
		if (endDate == null) {
			endDate = today;
		}
		list = vis.queryAccountLedger(accountCode, beginDate, endDate);
		Map map = vis.queryAccountBalance(accountCode, beginDate, endDate);

		ModelAndView mv = new ModelAndView("finance/report/queryAccountLedger", "resultList", list);
		mv.addObject("accountCode", accountCode);
		mv.addObject("beginDate", beginDate);
		mv.addObject("endDate", endDate);
		mv.addObject("balanceMap", map);

		return mv;
	}

	public ModelAndView queryAccountBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryAccountBook' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "b_date", false);
		}
		List resultList = vis.queryAccountBook(qc, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("finance/report/queryAccountBook", "resultList", resultList);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);

		return mv;
	}

	public ModelAndView queryDailyBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'queryDailyBalance' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "accountCode", false);
		}
		List resultList = vis.queryDailyBalance(qc, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("finance/report/queryDailyBalance", "resultList", resultList);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);

		return mv;
	}

	public ModelAndView reportDailyBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'reportDailyBalance' method...");
		ViewService vis = (ViewService) SysData.getBean("f_viewService");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		if (qc == null) {
			qc = new QueryConditions();
			qc.addCondition("b_date", "=", new Date());
		}
		qc.addCondition("accountLevel", "=", "1");
		qc.addCondition("dCFlag", "=", "D");
		List debitList = vis.queryDailyBalance(qc, null);

		qc.removeCondition("dCFlag");
		qc.addCondition("dCFlag", "=", "C");
		List creditList = vis.queryDailyBalance(qc, null);
		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("finance/report/reportDailyBalance", "debitList", debitList);
		mv.addObject("creditList", creditList);
		mv.addObject("oldParams", map);

		return mv;
	}
}
