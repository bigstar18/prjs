package gnnt.MEBS.member.broker.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.ECSideUtils;
import org.ecside.util.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.member.broker.services.BrokerReportService;
import gnnt.MEBS.member.broker.util.SysData;
import gnnt.MEBS.timebargain.manage.util.DateUtil;

public class TradeFeeController extends BaseController {
	private final transient Log logger = LogFactory.getLog(TradeFeeController.class);

	public ModelAndView tradeFeeBrokerList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("Entering 'tradeFeeBrokerList' method");
		String str = paramHttpServletRequest.getParameter("moduleId");
		BrokerReportService localBrokerReportService = (BrokerReportService) SysData.getBean("m_brokerReportService");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		List localList = localBrokerReportService.getBrokerFeeDetailByCondition(localQueryConditions, localPageInfo, str);
		ModelAndView localModelAndView = new ModelAndView("/member/broker/report/tradeFeeBroker_list", "resultList", localList);
		return localModelAndView;
	}

	public ModelAndView topTradeFeeBroker(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'topTradeFeeBroker' method");
		}
		ModelAndView localModelAndView = new ModelAndView("/member/broker/report/tradeFeeBroker_top", "", null);
		return localModelAndView;
	}

	public ModelAndView tradeFeeFirmList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("Entering 'tradeFeeFirmList' method");
		BrokerReportService localBrokerReportService = (BrokerReportService) SysData.getBean("m_brokerReportService");
		QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		String str1 = paramHttpServletRequest.getParameter("moduleId");
		Object localObject = new ArrayList();
		Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
		Sort localSort = localLimit.getSort();
		Map localMap = localSort.getSortValueMap();
		int i = 0;
		int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
		try {
			if (j < 0) {
				j = localBrokerReportService.getFirmFeeDetailByConditionCount(localQueryConditions1, str1);
			}
			localLimit.setRowAttributes(j, 20);
			int k = localLimit.getRowStart() + i + 1;
			int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
			String str2 = ECSideUtils.getDefaultSortSQL(localMap);
			if (j < 0) {
				j = localBrokerReportService.getFirmFeeDetailByConditionCount(localQueryConditions1, str1);
			}
			localLimit.setRowAttributes(j, 20);
			try {
				localObject = localBrokerReportService.getFirmFeeDetailByCondition(localQueryConditions2, k, m, str1);
			} catch (Exception localException2) {
				localException2.printStackTrace();
			}
		} catch (Exception localException1) {
			System.out.println("------------------出现异常----------------------");
			localException1.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", localException1.getMessage());
		}
		ModelAndView localModelAndView = new ModelAndView("/member/broker/report/tradeFeeFirm_list", "resultList", localObject);
		return localModelAndView;
	}

	public ModelAndView tradeFeeFirmCommodityList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		this.logger.debug("Entering 'tradeFeeFirmCommodityList' method");
		String str = paramHttpServletRequest.getParameter("moduleId");
		BrokerReportService localBrokerReportService = (BrokerReportService) SysData.getBean("m_brokerReportService");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		Object localObject = new ArrayList();
		try {
			localObject = localBrokerReportService.getCommodityFeeDetailByCondition(localQueryConditions, localPageInfo, str);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		ModelAndView localModelAndView = new ModelAndView("/member/broker/report/tradeFeeFirmCommodity_list", "resultList", localObject);
		return localModelAndView;
	}

	public ModelAndView topTradeFeeFirm(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Entering 'topTradeFeeFirm' method");
		}
		ModelAndView localModelAndView = new ModelAndView("/member/broker/report/tradeFeeFirm_top", "", null);
		return localModelAndView;
	}

	protected String getThisDay() {
		Date localDate = DateUtil.GoDate(new Date(), 0);
		return DateUtil.formatDate(localDate, "yyyy-MM-dd");
	}

	public ModelAndView tradeFeeList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("Entering 'tradeFeeList' method");
		BrokerReportService localBrokerReportService = (BrokerReportService) SysData.getBean("m_brokerReportService");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
		List localList = localBrokerReportService.tradeFeeList(localQueryConditions);
		ModelAndView localModelAndView = new ModelAndView("member/broker/firmQuery/tradeFee", "resultList", localList);
		localModelAndView.addObject("oldParams", localMap);
		return localModelAndView;
	}

	public void getBreedByModuleId(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		paramHttpServletResponse.setContentType("text/html;charset=utf-8");
		String str1 = paramHttpServletRequest.getParameter("moduleId");
		BrokerReportService localBrokerReportService = (BrokerReportService) SysData.getBean("m_brokerReportService");
		List localList1 = null;
		List localList2 = null;
		if ("2".equals(str1)) {
			localList1 = localBrokerReportService.getDingDanBreeds(false);
			localList2 = localBrokerReportService.getDingDanBreeds(true);
		} else if ("3".equals(str1)) {
			localList1 = localBrokerReportService.getGuaPaiCommoditys(false);
			localList2 = localBrokerReportService.getGuaPaiCommoditys(true);
		} else {
			localList1 = localBrokerReportService.getJingJiaBreeds(false);
			localList2 = localBrokerReportService.getJingJiaBreeds(true);
		}
		String str2 = "";
		for (int i = 0; i < localList1.size(); i++) {
			str2 = str2 + ((Map) localList1.get(i)).get("breedId").toString();
			str2 = str2 + ":";
			str2 = str2 + ((Map) localList1.get(i)).get("breedName").toString();
			str2 = str2 + ";";
		}
		str2 = str2 + "-";
		for (int i = 0; i < localList2.size(); i++) {
			str2 = str2 + ((Map) localList2.get(i)).get("breedId").toString();
			str2 = str2 + ":";
			str2 = str2 + ((Map) localList2.get(i)).get("breedName").toString();
			str2 = str2 + ";";
		}
		paramHttpServletResponse.getWriter().print(str2);
	}
}
