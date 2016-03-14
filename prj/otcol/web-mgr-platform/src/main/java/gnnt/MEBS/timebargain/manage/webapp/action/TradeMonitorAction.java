package gnnt.MEBS.timebargain.manage.webapp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import gnnt.MEBS.timebargain.manage.model.MonitorSet;
import gnnt.MEBS.timebargain.manage.service.TradeMonitorManager;
import gnnt.MEBS.timebargain.manage.util.ObjSet;
import gnnt.MEBS.timebargain.manage.util.Utils;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.TradeMonitor;

public class TradeMonitorAction extends BaseAction {
	public ActionForward listQuotationInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listQuotationInfo' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str = paramHttpServletRequest.getParameter("orderby");
			}
			ObjSet localObjSet = localTradeMonitorManager.getQuotation(i, j, "", str);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("查询商品行情出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listOrderMonitor(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listOrderMonitor' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			String str2 = " where 1=1 ";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			if (paramHttpServletRequest.getParameter("status") != null) {
				str2 = str2 + " and status=" + Utils.parseInt(paramHttpServletRequest.getParameter("status"), 1) + " ";
			}
			if ((paramHttpServletRequest.getParameter("parameter") != null)
					&& (paramHttpServletRequest.getParameter("parameter").trim().length() > 0)) {
				int k = Utils.parseInt(paramHttpServletRequest.getParameter("queryType"), 0);
				if (k == 0) {
					str2 = str2 + " and FirmID='" + paramHttpServletRequest.getParameter("parameter") + "' ";
				} else {
					str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
				}
			}
			ObjSet localObjSet = localTradeMonitorManager.getOrders(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("查询商品委托出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listUnTradeInfoB(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listUnTradeInfoB' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where (status=1 or status=2) and BS_Flag=1 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			} else {
				str2 = str2 + " and commodityid=' ' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getOrders(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("查询未成交委托买队列出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listUnTradeInfoS(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listUnTradeInfoS' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where (status=1 or status=2) and BS_Flag=2 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			} else {
				str2 = str2 + " and commodityid=' ' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getOrders(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("查询未成交委托卖队列出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listTradeList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listTradeList' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " ";
			if ((paramHttpServletRequest.getParameter("parameter") != null)
					&& (paramHttpServletRequest.getParameter("parameter").trim().length() > 0)) {
				int k = Utils.parseInt(paramHttpServletRequest.getParameter("queryType"), 0);
				if (k == 0) {
					str2 = str2 + " and FirmID='" + paramHttpServletRequest.getParameter("parameter") + "' ";
				} else {
					str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
				}
			}
			ObjSet localObjSet = localTradeMonitorManager.getTrade(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("查询成交明细出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listTradeStatistic(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listTradeStatistic' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str = paramHttpServletRequest.getParameter("orderby");
			}
			ObjSet localObjSet = localTradeMonitorManager.getTradeStatistic(i, j, "", str);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("查询成交综合统计出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listFirmMonitor(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listFirmMonitor' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where 1=1 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and FirmID='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			} else {
				str2 = str2 + " and FirmID='' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getFirmHoldSum(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("交易商持仓监控出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listFundsAnalysis(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listFundsInfo' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where 1=1 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and FirmID='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getFundsAnalysis(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("资金预警分析出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listAnalyseInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listAnalyseInfo' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str = "";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str = paramHttpServletRequest.getParameter("orderby");
			}
			ObjSet localObjSet = localTradeMonitorManager.getAnalyseInfo(i, j, "", str);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("综合分析出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listCommodityMonitorB(ActionMapping paramActionMapping, ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listCommodityMonitorB' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where BS_Flag=1 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			} else {
				str2 = str2 + " and commodityid=' ' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getFirmHoldSum(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("商品持仓查询买订货：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listCommodityMonitorS(ActionMapping paramActionMapping, ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listCommodityMonitorS' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where BS_Flag=2 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			} else {
				str2 = str2 + " and commodityid=' ' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getFirmHoldSum(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("商品持仓查询卖订货：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward listCommodityMonitorQ(ActionMapping paramActionMapping, ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listCommodityMonitorQ' method");
		}
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (paramHttpServletRequest.getParameter("pageIndex") != null) {
				j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
			}
			if (paramHttpServletRequest.getParameter("orderby") != null) {
				str1 = paramHttpServletRequest.getParameter("orderby");
			}
			String str2 = " where 1=1 ";
			if (paramHttpServletRequest.getParameter("parameter") != null) {
				str2 = str2 + " and commodityid='" + paramHttpServletRequest.getParameter("parameter") + "' ";
			} else {
				str2 = str2 + " and commodityid=' ' ";
			}
			ObjSet localObjSet = localTradeMonitorManager.getFirmTradeQuantity(i, j, str2, str1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, localObjSet);
		} catch (Exception localException) {
			this.log.error("商品持仓查询监控成交量：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward fundsAnalysisInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'fundsAnalysisInfo' method");
		}
		String str = paramHttpServletRequest.getParameter("firmID");
		TradeMonitorManager localTradeMonitorManager = (TradeMonitorManager) getBean("tradeMonitorManager");
		List localList1 = null;
		List localList2 = null;
		Map localMap = null;
		int i = 1;
		try {
			localList1 = localTradeMonitorManager.getFirmInfo(str);
			if ((localList1 != null) && (localList1.size() > 0)) {
				localMap = (Map) localList1.get(0);
			}
			localList2 = localTradeMonitorManager.getFirmHold(str);
			paramHttpServletRequest.setAttribute("mapInfo", localMap);
			paramHttpServletRequest.setAttribute("listHold", localList2);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("查询短信平台模板：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("fundsAnalysisInfo");
	}

	public ActionForward ajaxFundsAnalysis(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'ajaxFundsAnalysis' method");
		}
		String str1 = paramHttpServletRequest.getParameter("id");
		int i = 1;
		String str2 = "";
		try {
			paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
			paramHttpServletResponse.setHeader("Cache-Control", "no-store");
			paramHttpServletResponse.setHeader("Pragma", "no-cache");
			paramHttpServletResponse.setContentType("text/xml");
			paramHttpServletResponse.setCharacterEncoding("GBK");
			PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
			localPrintWriter.println(str2);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("返回模板内容：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return null;
	}

	public ActionForward memoryMonitor(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'memoryMonitor' method");
		}
		int i = MonitorSet.getInstance().getPageSize();
		int j = 1;
		if (paramHttpServletRequest.getParameter("pageIndex") != null) {
			j = Utils.parseInt(paramHttpServletRequest.getParameter("pageIndex"), 1);
		}
		HashMap localHashMap1 = new HashMap();
		localHashMap1.put("pageSize", Integer.valueOf(i));
		localHashMap1.put("pageIndex", Integer.valueOf(j));
		int k = 0;
		AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
		Map localMap = null;
		String str1 = paramHttpServletRequest.getParameter("parameter");
		String str2 = paramHttpServletRequest.getParameter("queryOrderType");
		int m = 0;
		LinkedList localLinkedList = new LinkedList();
		HashMap localHashMap2 = null;
		Object localObject1;
		Object localObject2;
		Object localObject3;
		Object localObject4;
		if ("waitOrders".equals(str2)) {
			localObject1 = paramHttpServletRequest.getParameter("firmId");
			localObject2 = paramHttpServletRequest.getParameter("status");
			localObject3 = paramHttpServletRequest.getParameter("commodityId");
			localObject4 = "";
			if ((null != localObject1) && (!"".equals(((String) localObject1).trim()))) {
				localObject4 = (String) localObject4 + "0_" + ((String) localObject1).trim() + "_";
			} else if ((null != localObject3) && (!"".equals(((String) localObject3).trim()))) {
				localObject4 = (String) localObject4 + "1_" + ((String) localObject3).trim() + "_";
			} else if (((null == localObject1) || ("".equals(((String) localObject1).trim())))
					&& ((null == localObject3) || ("".equals(localObject3)))) {
				localObject4 = (String) localObject4 + "*_*_";
			}
			if ((null != localObject2) && (!"".equals(((String) localObject2).trim()))) {
				localObject4 = (String) localObject4 + ((String) localObject2).trim();
			} else {
				localObject4 = (String) localObject4 + "*";
			}
			localMap = localAgencyRMIBean.tradeMonitorRMIMap((String) localObject4, str2, localHashMap1);
			if (localMap != null) {
				ArrayList localArrayList = new ArrayList();
				TradeMonitor localTradeMonitor = (TradeMonitor) localMap.get(localObject4);
				if (localTradeMonitor.getWaitOrders().size() != 0) {
					localArrayList.addAll(localTradeMonitor.getWaitOrders());
				}
				k = ((Integer) localTradeMonitor.getMap().get("wTotalCount")).intValue();
				Iterator localIterator = localArrayList.iterator();
				while (localIterator.hasNext()) {
					Order localOrder = (Order) localIterator.next();
					localHashMap2 = new HashMap();
					localHashMap2.put("COMMODITYID", localOrder.getCommodityID());
					localHashMap2.put("FIRMID", localOrder.getFirmID());
					localHashMap2.put("BS_FLAG", localOrder.getBuyOrSell());
					localHashMap2.put("ORDERTYPE", localOrder.getOrderType());
					localHashMap2.put("PRICE", localOrder.getPrice());
					localHashMap2.put("QUANTITY", localOrder.getQuantity());
					localHashMap2.put("A_ORDERNO", localOrder.getOrderNo());
					localHashMap2.put("ORDERTIME", localOrder.getOrderTime());
					localHashMap2.put("STATUS", localOrder.getStatus());
					localLinkedList.add(localHashMap2);
				}
				m = 1;
			}
		} else if (str1 != null) {
			str1 = str1.trim();
			localMap = localAgencyRMIBean.tradeMonitorRMIMap(str1, str2, localHashMap1);
			if (localMap != null) {
				localObject1 = (TradeMonitor) localMap.get(str1);
				localObject2 = null;
				if ("sellOrders".equals(str2)) {
					k = ((Integer) ((TradeMonitor) localObject1).getMap().get("sTotalCount")).intValue();
					localObject2 = ((TradeMonitor) localObject1).getSellQueue();
				} else if ("buyOrders".equals(str2)) {
					k = ((Integer) ((TradeMonitor) localObject1).getMap().get("bTotalCount")).intValue();
					localObject2 = ((TradeMonitor) localObject1).getBuyQueue();
				}
				localObject3 = ((List) localObject2).iterator();
				while (((Iterator) localObject3).hasNext()) {
					localObject4 = (Order) ((Iterator) localObject3).next();
					localHashMap2 = new HashMap();
					localHashMap2.put("CUSTOMERID", ((Order) localObject4).getCustomerID());
					localHashMap2.put("ORDERTYPE", ((Order) localObject4).getOrderType());
					localHashMap2.put("PRICE", ((Order) localObject4).getPrice());
					localHashMap2.put("QUANTITY", ((Order) localObject4).getQuantity());
					localHashMap2.put("ORDERNO", ((Order) localObject4).getOrderNo());
					if (((Order) localObject4).getOrderTime() != null) {
						localHashMap2.put("ORDERTIME", new SimpleDateFormat("hh:mm:ss").format(((Order) localObject4).getOrderTime()));
					}
					localLinkedList.add(localHashMap2);
				}
				m = 1;
			}
		}
		if (m != 0) {
			localObject1 = new HashMap();
			((Map) localObject1).put("status", localAgencyRMIBean.getSystemStatus().getStatus() + "");
			localObject2 = ObjSet.getInstance(localLinkedList, k, i, j);
			((ObjSet) localObject2).setMap((Map) localObject1);
			createXML(paramHttpServletRequest, paramHttpServletResponse, (ObjSet) localObject2);
			paramHttpServletRequest.setAttribute("queryCommodityID", str1);
		}
		paramHttpServletRequest.setAttribute("saleOrdersMap", localMap);
		return paramActionMapping.findForward(null);
	}

	private void createXML(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, ObjSet paramObjSet) {
		try {
			int i = MonitorSet.getInstance().getRefreshTime() * 1000;
			List localList = paramObjSet.getList();
			int j = paramObjSet.getTotalCount();
			int k = paramObjSet.getPageIndex();
			int m = paramObjSet.getPageCount();
			int n = paramObjSet.getPageSize();
			Map localMap = paramObjSet.getMap();
			paramHttpServletRequest.setCharacterEncoding("GBK");
			paramHttpServletResponse.setContentType("text/xml;charset=GBK");
			paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
			paramHttpServletResponse.setContentType("application/xml");
			Document localDocument = new Document();
			Element localElement1 = new Element("list");
			Element localElement2 = new Element("timerInterval").addContent(String.valueOf(i));
			localElement1.addContent(localElement2);
			Element localElement3 = new Element("pageIndex").addContent(String.valueOf(k));
			localElement1.addContent(localElement3);
			Element localElement4 = new Element("totalPage").addContent(String.valueOf(m));
			localElement1.addContent(localElement4);
			Element localElement5 = new Element("totalCount").addContent(String.valueOf(j));
			localElement1.addContent(localElement5);
			Element localElement6 = new Element("pageSize").addContent(String.valueOf(n));
			localElement1.addContent(localElement6);
			Object localObject2;
			if (localMap != null) {
				Iterator localObject1 = localMap.keySet().iterator();
				while (((Iterator) localObject1).hasNext()) {
					String str1 = (String) ((Iterator) localObject1).next();
					localObject2 = new Element(str1).addContent((String) localMap.get(str1));
					localElement1.addContent((Content) localObject2);
				}
			}
			Object localObject1 = new Element("datalist");
			for (int i1 = 0; i1 < localList.size(); i1++) {
				localObject2 = (Map) localList.get(i1);
				Iterator localIterator = ((Map) localObject2).entrySet().iterator();
				Element localElement7 = new Element("data");
				while (localIterator.hasNext()) {
					Map.Entry localEntry = (Map.Entry) localIterator.next();
					if (localEntry.getValue() != null) {
						String str2 = localEntry.getKey().toString();
						String str3 = localEntry.getValue().toString();
						if (str2.equalsIgnoreCase("num")) {
							localElement7.setAttribute("rownum", str3);
						} else {
							localElement7.addContent(new Element(str2).addContent(str3));
						}
					}
				}
				((Element) localObject1).addContent(localElement7);
			}
			localElement1.addContent((Content) localObject1);
			localDocument.addContent(localElement1);
			XMLOutputter localXMLOutputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("ISO8859-1"));
			localXMLOutputter.output(localDocument, paramHttpServletResponse.getWriter());
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			this.log.error("生成商品行情xml出错：" + localUnsupportedEncodingException.getMessage());
		} catch (IOException localIOException) {
			this.log.error("生成商品行情xml出错：" + localIOException.getMessage());
		} catch (Exception localException) {
			this.log.error("生成商品行情xml出错：" + localException.toString());
		}
	}
}
