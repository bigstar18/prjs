package gnnt.MEBS.timebargain.manage.webapp.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.ECSideUtils;
import org.ecside.util.RequestUtils;

import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.service.TradeCtlManager;
import gnnt.MEBS.timebargain.manage.util.DateUtil;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;

public class TradeCtlAction extends BaseAction {
	public ActionForward searchHold(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchHold' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getHolds();
			request.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			request.setAttribute("holdList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listHold");
	}

	public ActionForward searchTrade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchTrade' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getTrades();
			request.setAttribute("tradeList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listTrade");
	}

	public ActionForward searchOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchOrder' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getOrders();
			request.setAttribute("orderList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listOrder");
	}

	public ActionForward searchAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchAction' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getActions();
			request.setAttribute("actionList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listAction");
	}

	public ActionForward searchFund(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchFund' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		String p_clearDate = request.getParameter("ClearDate");
		Date clearDate = null;
		if ((p_clearDate != null) && (!p_clearDate.equals(""))) {
			clearDate = DateUtil.GoDate(new Date(), -1);
		}
		try {
			List lst = mgr.getFunds(clearDate);
			this.log.debug("===>fundList.size:" + lst.size());
			request.setAttribute("fundList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listFund");
	}

	public ActionForward searchTradeError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchTradeError' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getTradeErrors();
			request.setAttribute("tradeErrorList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listTradeError");
	}

	public ActionForward searchSpecFrozenHold(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchSpecFrozenHold' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		QueryConditions qc = QueryUtil.getQueryConditionsFromRequest(request);

		Limit limit = RequestUtils.getLimit(request);
		Sort sort = limit.getSort();
		Map sortValueMap = sort.getSortValueMap();
		int offset = 0;
		int totalRows = RequestUtils.getTotalRowsFromRequest(request);
		try {
			if (totalRows < 0) {
				totalRows = mgr.getSpecFrozenHoldsCount();
			}
			limit.setRowAttributes(totalRows, 20);
			int rowStart = limit.getRowStart() + offset + 1;
			int rowEnd = limit.getRowEnd() + offset == 0 ? 20 : limit.getRowEnd() + offset;
			String sst = ECSideUtils.getDefaultSortSQL(sortValueMap);
			List lst = mgr.getSpecFrozenHolds(qc, rowStart, rowEnd);
			request.setAttribute("specFrozenHoldList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listSpecFrozenHold");
	}

	public ActionForward searchForceClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchForceClose' method");
		}
		String sectionId = request.getParameter("sectionId");
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getForceClose(sectionId);
			List tradeTimeList = mgr.getTradeTime();
			request.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
			request.setAttribute("forceCloseList", lst);
			if ((sectionId != null) && (!"".equals(sectionId))) {
				request.setAttribute("sectionId", sectionId);
			}
			request.setAttribute("tradeTimeList", tradeTimeList);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listForceClose");
	}

	public ActionForward searchDetailForceClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchDetailForceClose' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		QueryConditions qc = QueryUtil.getQueryConditionsFromRequest(request);
		Limit limit = RequestUtils.getLimit(request);
		Sort sort = limit.getSort();
		Map sortValueMap = sort.getSortValueMap();
		int offset = 0;
		int totalRows = RequestUtils.getTotalRowsFromRequest(request);
		try {
			if (qc == null) {
				qc = new QueryConditions();
			}
			qc.addCondition("remainday", "=", Integer.valueOf(0));
			if (totalRows < 0) {
				totalRows = mgr.getHoldsDetailCount(qc);
			}
			String sst = ECSideUtils.getDefaultSortSQL(sortValueMap);
			String[] par = sst.split(" ");
			boolean boo = false;
			if ((par.length >= 3) && ("firmID".equalsIgnoreCase(par[3]))) {
				par[3] = "FirmID";
				sst = " ";
				boo = true;
			}
			if (boo) {
				for (int i = 0; i < par.length; i++) {
					sst = sst + par[i] + " ";
				}
			}
			qc.addCondition("orderby", "=", sst);
			limit.setRowAttributes(totalRows, 20);
			int rowStart = limit.getRowStart() + offset + 1;
			int rowEnd = limit.getRowEnd() + offset == 0 ? 20 : limit.getRowEnd() + offset;
			List lst = mgr.getHoldPositionsDetail(qc, rowStart, rowEnd);

			request.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			request.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
			request.setAttribute("TRADETYPE", CommonDictionary.TRADETYPE);
			request.setAttribute("holdList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listDetailForceClose");
	}

	/**
	 * @deprecated
	 */
	public ActionForward searchTradeRespond(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchTradeRespond' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getTradeResponds();
			request.setAttribute("tradeRespondList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listTradeRespond");
	}

	public ActionForward topHoldPosition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'topHoldPosition' method");
		}
		StatQueryManager mgr = (StatQueryManager) getBean("statQueryManager");
		getSelectAttribute(request);
		List firmCategoryList = mgr.getFirmCategory();
		request.setAttribute("lastDay", getThisDay());
		request.setAttribute("firmCategoryList", firmCategoryList);
		return mapping.findForward("topHoldPosition");
	}

	public ActionForward listHoldPosition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listHoldPosition' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");

		QueryConditions qc1 = QueryUtil.getQueryConditionsFromRequest(request);
		QueryConditions qc2 = QueryUtil.getQueryConditionsFromRequest(request);
		Limit limit = RequestUtils.getLimit(request);
		Sort sort = limit.getSort();
		Map sortValueMap = sort.getSortValueMap();
		int offset = 0;
		int totalRows = RequestUtils.getTotalRowsFromRequest(request);
		try {
			if (totalRows < 0) {
				totalRows = mgr.getHoldsCount(qc1);
			}
			String sst = ECSideUtils.getDefaultSortSQL(sortValueMap);
			String[] par = sst.split(" ");
			boolean boo = false;
			if ((par.length >= 3) && ("firmID".equalsIgnoreCase(par[3]))) {
				par[3] = "a.FirmID";
				sst = " ";
				boo = true;
			}
			if (boo) {
				for (int i = 0; i < par.length; i++) {
					sst = sst + par[i] + " ";
				}
			}
			qc2.addCondition("orderby", "=", sst);
			limit.setRowAttributes(totalRows, 20);
			int rowStart = limit.getRowStart() + offset + 1;
			int rowEnd = limit.getRowEnd() + offset == 0 ? 20 : limit.getRowEnd() + offset;
			List lst = mgr.getHoldPositions(qc2, rowStart, rowEnd);
			StatQueryManager mgr1 = (StatQueryManager) getBean("statQueryManager");
			List firmCategoryList = mgr1.getFirmCategory();
			request.setAttribute("firmCategoryList", firmCategoryList);
			request.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			request.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
			request.setAttribute("TRADETYPE", CommonDictionary.TRADETYPE);
			request.setAttribute("holdList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listHold");
	}

	public ActionForward listHoldPosition1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listHoldPosition1' method");
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		try {
			List lst = mgr.getHoldPositions1();
			StatQueryManager mgr1 = (StatQueryManager) getBean("statQueryManager");
			List firmCategoryList = mgr1.getFirmCategory();
			request.setAttribute("firmCategoryList", firmCategoryList);

			request.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			request.setAttribute("holdList", lst);
		} catch (Exception err) {
			this.log.error("查询出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("listHold");
	}

	public ActionForward searchForceCloseMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchForceCloseMoney' method");
		}
		String firmID = request.getParameter("firmID");
		String commodityID = request.getParameter("commodityID");
		String bS_Flag = request.getParameter("bS_Flag");
		String quantity = request.getParameter("quantity");
		String evenPrice = request.getParameter("evenPrice");
		String orderPrice = request.getParameter("price");
		String holdQty = request.getParameter("holdQty");
		String usefulFunds = request.getParameter("usefulFunds");
		this.log.debug("firmID: " + firmID);
		this.log.debug("commodityID: " + commodityID);
		this.log.debug("bS_Flag: " + bS_Flag);
		this.log.debug("quantity: " + quantity);
		this.log.debug("evenPrice: " + evenPrice);
		this.log.debug("orderPrice: " + orderPrice);
		this.log.debug("holdQty: " + holdQty);
		this.log.debug("usefulFunds: " + usefulFunds);
		Orders order = new Orders();
		order.setFirmID(firmID);
		order.setCommodityID(commodityID);
		if ((bS_Flag != null) && (!"".equals(bS_Flag))) {
			order.setBS_Flag(Short.valueOf(Short.parseShort(bS_Flag)));
		}
		if ((quantity != null) && (!"".equals(quantity))) {
			order.setQuantity(Long.valueOf(Long.parseLong(quantity)));
		}
		if ((evenPrice != null) && (!"".equals(evenPrice))) {
			order.setSpecPrice(Double.valueOf(Double.parseDouble(evenPrice)));
		}
		if ((orderPrice != null) && (!"".equals(orderPrice))) {
			order.setPrice(Double.valueOf(Double.parseDouble(orderPrice)));
		}
		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		double money = mgr.getForceCloseMoney(order);
		this.log.debug("money: " + money);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/xml");
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		String responseText = "";
		long compareQuantity = 0L;
		long relHoldQty = 0L;
		double relUsefulFunds = 0.0D;
		if ((holdQty != null) && (!"".equals(holdQty))) {
			relHoldQty = Long.parseLong(holdQty);
		}
		if ((usefulFunds != null) && (!"".equals(usefulFunds))) {
			relUsefulFunds = Double.parseDouble(usefulFunds);
		}
		if ((money > 0.0D) && (relUsefulFunds < 0.0D)) {
			compareQuantity = Long.valueOf(Math.ceil(relUsefulFunds * -1.0D / money) + "");
		}
		if (compareQuantity > relHoldQty) {
			responseText = relHoldQty + "";
		} else {
			responseText = compareQuantity + "";
		}
		out.print(responseText);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return searchHold(mapping, form, request, response);
	}

	private void getSelectAttribute(HttpServletRequest request) throws Exception {
		LookupManager lookupMgr = (LookupManager) getBean("lookupManager");
	}

	public ActionForward searchMayForceCloseHQty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchForceCloseMoney' method");
		}
		String firmID = request.getParameter("firmID");
		String commodityID = request.getParameter("commodityID");
		int bS_Flag = Integer.valueOf(request.getParameter("bS_Flag")).intValue();

		this.log.debug("firmID: " + firmID);
		this.log.debug("commodityID: " + commodityID);
		this.log.debug("bS_Flag: " + bS_Flag);

		QueryConditions qc = new QueryConditions();
		qc.addCondition("firmid", "=", firmID);
		qc.addCondition("commodityID", "=", commodityID);
		qc.addCondition("bS_Flag", "=", Integer.valueOf(bS_Flag));
		qc.addCondition("remainday", "=", Integer.valueOf(0));

		TradeCtlManager mgr = (TradeCtlManager) getBean("tradeCtlManager");
		List<Map> list = mgr.getHoldPositionsDetail(qc, 0, 2147483647);
		long maycloseQty = 0L;
		long holdqty = 0L;
		if ((list != null) && (list.size() > 1)) {
			this.log.error("searchMayForceCloseHQty -- maycloseQty error 应该只有一条记录 请检查查询条件");
			this.log.error("firmID: " + firmID);
			this.log.error("commodityID: " + commodityID);
			this.log.error("bS_Flag: " + bS_Flag);
		}
		for (Map map : list) {
			maycloseQty += Long.parseLong(map.get("maycloseQty").toString());
			holdqty += Long.parseLong(map.get("holdqty").toString());
		}
		this.log.info("searchMayForceCloseHQty----holdqty=" + holdqty + " --  maycloseQty=" + maycloseQty);

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/xml");
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		String responseText = maycloseQty + "," + holdqty;
		out.print(responseText);
		out.flush();
		out.close();
		return null;
	}
}
