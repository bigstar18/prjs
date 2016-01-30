package gnnt.MEBS.timebargain.mgr.action.monitor;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.timebargain.mgr.service.monitor.MonitorSetService;
import gnnt.MEBS.timebargain.mgr.util.MonitorSet;
import gnnt.MEBS.timebargain.mgr.util.ObjSet;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeMonitor;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeMonitorRMI;

@Controller("monitorSetAction")
@Scope("request")
public class MonitorSetAction extends EcsideAction {
	protected final transient Log logger = LogFactory.getLog(getClass());
	private static final long serialVersionUID = 5124568190167465621L;

	@Autowired
	@Qualifier("monitorSetService")
	private MonitorSetService monitorSetService;

	@Autowired
	@Qualifier("TradeMonitorRMI")
	private TradeMonitorRMI tradeMonitorRMI;

	@Autowired
	@Qualifier("ServerRMI")
	private ServerRMI serverRMI;
	private int refreshTime;
	private int pageSize;

	public String listQuotationInfo() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str = this.request.getParameter("orderby");
			ObjSet localObjSet = this.monitorSetService.getQuotation(i, j, "", str);
			createXML(this.request, this.response, localObjSet);
			addReturnValue(1, 119907L);
		} catch (Exception localException) {
			localException.printStackTrace();
			addReturnValue(-1, 119908L);
		}
		return "none";
	}

	public String listOrderMonitor() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			String str2 = " where 1=1 ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			if (this.request.getParameter("status") != null)
				str2 = str2 + " and status=" + MonitorSet.parseInt(this.request.getParameter("status"), 1) + " ";
			if ((this.request.getParameter("parameter") != null) && (this.request.getParameter("parameter").trim().length() > 0)) {
				int k = MonitorSet.parseInt(this.request.getParameter("queryType"), 0);
				if (k == 0)
					str2 = str2 + " and FirmID='" + this.request.getParameter("parameter") + "' ";
				else
					str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			}
			ObjSet localObjSet = this.monitorSetService.getOrders(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("查询商品委托出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String listUnTradeInfoB() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where (status=1 or status=2) and BS_Flag=1 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			else
				str2 = str2 + " and commodityid=' ' ";
			ObjSet localObjSet = this.monitorSetService.getOrders(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("查询未成交委托买队列出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String listUnTradeInfoS() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where (status=1 or status=2) and BS_Flag=2 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			else
				str2 = str2 + " and commodityid=' ' ";
			ObjSet localObjSet = this.monitorSetService.getOrders(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("查询未成交委托卖队列出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String listTradeList() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " ";
			if ((this.request.getParameter("parameter") != null) && (this.request.getParameter("parameter").trim().length() > 0)) {
				int k = MonitorSet.parseInt(this.request.getParameter("queryType"), 0);
				if (k == 0)
					str2 = str2 + " and FirmID='" + this.request.getParameter("parameter") + "' ";
				else
					str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			}
			ObjSet localObjSet = this.monitorSetService.getTrade(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("查询成交明细出错：" + localException.getMessage());
		}
		return "none";
	}

	public String listTradeStatistic() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str = this.request.getParameter("orderby");
			ObjSet localObjSet = this.monitorSetService.getTradeStatistic(i, j, "", str);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("查询成交综合统计出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String listFirmMonitor() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where 1=1 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and FirmID='" + this.request.getParameter("parameter") + "' ";
			else
				str2 = str2 + " and FirmID='' ";
			ObjSet localObjSet = this.monitorSetService.getFirmHoldSum(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("交易商持仓监控出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String listFundsAnalysis() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where 1=1 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and FirmID='" + this.request.getParameter("parameter") + "' ";
			ObjSet localObjSet = this.monitorSetService.getFundsAnalysis(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("资金预警分析出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String listCommodityMonitorB() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where BS_Flag=1 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			else
				str2 = str2 + " and commodityid=' ' ";
			ObjSet localObjSet = this.monitorSetService.getFirmHoldSum(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("商品持仓查询买订货：" + localException.getMessage());
			localException.printStackTrace();
		}
		return null;
	}

	public String listCommodityMonitorS() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where BS_Flag=2 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			else
				str2 = str2 + " and commodityid=' ' ";
			ObjSet localObjSet = this.monitorSetService.getFirmHoldSum(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("商品持仓查询卖订货：" + localException.getMessage());
			localException.printStackTrace();
		}
		return null;
	}

	public String listCommodityMonitorQ() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where 1=1 ";
			if (this.request.getParameter("parameter") != null)
				str2 = str2 + " and commodityid='" + this.request.getParameter("parameter") + "' ";
			else
				str2 = str2 + " and commodityid=' ' ";
			ObjSet localObjSet = this.monitorSetService.getFirmTradeQuantity(i, j, str2, str1);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("商品持仓查询监控成交量：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String fundsAnalysisInfo() {
		String str = this.request.getParameter("firmID");
		List localList1 = null;
		List localList2 = null;
		Map localMap = null;
		int i = 1;
		try {
			localList1 = this.monitorSetService.getFirmInfo(str);
			if ((localList1 != null) && (localList1.size() > 0))
				localMap = (Map) localList1.get(0);
			localList2 = this.monitorSetService.getFirmHold(str);
			this.request.setAttribute("mapInfo", localMap);
			this.request.setAttribute("listHold", localList2);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.logger.error("查询交易商信息：" + localException.getMessage());
		}
		return "success";
	}

	public String listAnalyseInfo() {
		try {
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str = this.request.getParameter("orderby");
			ObjSet localObjSet = this.monitorSetService.getAnalyseInfo(i, j, "", str);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("排行分析出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
	}

	public String editMonitorParameter() {
		this.pageSize = MonitorSet.getInstance().getPageSize();
		this.refreshTime = MonitorSet.getInstance().getRefreshTime();
		this.request.setAttribute("pageSize", Integer.valueOf(this.pageSize));
		this.request.setAttribute("refreshTime", Integer.valueOf(this.refreshTime));
		return "success";
	}

	public String updateMonitorParameter() {
		int i = Integer.parseInt(this.request.getParameter("pageSize"));
		int j = Integer.parseInt(this.request.getParameter("refreshTime"));
		try {
			if ((i > 0) && (j >= 3)) {
				MonitorSet.getInstance().setPageSize(i);
				MonitorSet.getInstance().setRefreshTime(j);
				addReturnValue(1, 119907L);
			} else {
				throw new Exception();
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			addReturnValue(-1, 119907L);
		}
		return "success";
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
						if (str2.equalsIgnoreCase("num"))
							localElement7.setAttribute("rownum", str3);
						else
							localElement7.addContent(new Element(str2).addContent(str3));
					}
				}
				((Element) localObject1).addContent(localElement7);
			}
			localElement1.addContent((Content) localObject1);
			localDocument.addContent(localElement1);
			XMLOutputter localXMLOutputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("ISO8859-1"));
			localXMLOutputter.output(localDocument, paramHttpServletResponse.getWriter());
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			this.logger.error("生成商品行情xml出错：" + localUnsupportedEncodingException.getMessage());
		} catch (IOException localIOException) {
			this.logger.error("生成商品行情xml出错：" + localIOException.getMessage());
		} catch (Exception localException) {
			this.logger.error("生成商品行情xml出错：" + localException.toString());
		}
	}

	public String memoryMonitor() throws Exception {
		int i = MonitorSet.getInstance().getPageSize();
		int j = 1;
		if (this.request.getParameter("pageIndex") != null)
			j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
		HashMap localHashMap1 = new HashMap();
		localHashMap1.put("pageSize", Integer.valueOf(i));
		localHashMap1.put("pageIndex", Integer.valueOf(j));
		int k = 0;
		Map localMap = null;
		String str1 = this.request.getParameter("parameter");
		String str2 = this.request.getParameter("queryOrderType");
		int m = 0;
		LinkedList localLinkedList = new LinkedList();
		HashMap localHashMap2 = null;
		Object localObject1;
		Object localObject2;
		Object localObject3;
		Object localObject4;
		if ("waitOrders".equals(str2)) {
			localObject1 = this.request.getParameter("firmId");
			localObject2 = this.request.getParameter("status");
			localObject3 = this.request.getParameter("commodityId");
			localObject4 = "";
			if ((null != localObject1) && (!"".equals(((String) localObject1).trim())))
				localObject4 = (String) localObject4 + "0_" + ((String) localObject1).trim() + "_";
			else if ((null != localObject3) && (!"".equals(((String) localObject3).trim())))
				localObject4 = (String) localObject4 + "1_" + ((String) localObject3).trim() + "_";
			else if (((null == localObject1) || ("".equals(((String) localObject1).trim()))) && ((null == localObject3) || ("".equals(localObject3))))
				localObject4 = (String) localObject4 + "*_*_";
			if ((null != localObject2) && (!"".equals(((String) localObject2).trim())))
				localObject4 = (String) localObject4 + ((String) localObject2).trim();
			else
				localObject4 = (String) localObject4 + "*";
			localMap = this.tradeMonitorRMI.queryWaitOrders((String) localObject4, localHashMap1);
			if (localMap != null) {
				ArrayList localArrayList = new ArrayList();
				TradeMonitor localTradeMonitor = (TradeMonitor) localMap.get(localObject4);
				if (localTradeMonitor.getWaitOrders().size() != 0)
					localArrayList.addAll(localTradeMonitor.getWaitOrders());
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
			localMap = this.tradeMonitorRMI.querySaleOrders(str1, localHashMap1);
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
					if (((Order) localObject4).getOrderTime() != null)
						localHashMap2.put("ORDERTIME", new SimpleDateFormat("hh:mm:ss").format(((Order) localObject4).getOrderTime()));
					localLinkedList.add(localHashMap2);
				}
				m = 1;
			}
		} else {
			localObject1 = new ArrayList();
			localObject2 = ObjSet.getInstance((List) localObject1, 0, 10, 1);
			createXML(this.request, this.response, (ObjSet) localObject2);
		}
		if (m != 0) {
			localObject1 = new HashMap();
			((Map) localObject1).put("status", this.serverRMI.getSystemStatus().getStatus() + "");
			localObject2 = ObjSet.getInstance(localLinkedList, k, i, j);
			((ObjSet) localObject2).setMap((Map) localObject1);
			createXML(this.request, this.response, (ObjSet) localObject2);
			this.request.setAttribute("queryCommodityID", str1);
		}
		this.request.setAttribute("saleOrdersMap", localMap);
		return "none";
	}

	public String getCommoditySelect() throws Exception {
		String str1 = this.request.getParameter("type");
		String str2 = this.request.getParameter("firmID");
		String str3 = this.request.getParameter("commodityID2");
		SystemStatus localSystemStatus = this.serverRMI.getSystemStatus();
		int i = localSystemStatus.getStatus();
		String str4 = "select distinct t.commodityid commodityId from t_orders t order by t.commodityid ";
		List localList1 = getService().getListBySql(str4);
		String str5 = "select distinct t.commodityid commodityId from t_FirmHoldSum t order by t.commodityid ";
		List localList2 = getService().getListBySql(str5);
		this.request.setAttribute("sysStatus", Integer.valueOf(i));
		this.request.setAttribute("commoditySelect", localList1);
		this.request.setAttribute("commoditySelect2", localList2);
		this.request.setAttribute("firmID", str2);
		this.request.setAttribute("commodityID2", str3);
		HttpSession localHttpSession = this.request.getSession();
		localHttpSession.setAttribute("commoditySelect", localList1);
		localHttpSession.setAttribute("commoditySelect2", localList2);
		localHttpSession.setAttribute("commodityID2", str3);
		if (str1.equals("tradeList"))
			return "tradeList";
		if (str1.equals("commodityMonitor"))
			return "commodityMonitor";
		if (str1.equals("orderMonitor"))
			return "orderMonitor";
		if (str1.equals("firmMonitor"))
			return "firmMonitor";
		if (str1.equals("waitOrder"))
			return "waitOrder";
		if (str1.equals("saleQueue"))
			return "saleQueue";
		if (str1.equals("unTradeInfo"))
			return "unTradeInfo";
		throw new Exception("//Class:MonitorSetAction--method:getCommoditySelect没找到匹配的字符");
	}

	public TradeMonitorRMI getTradeMonitorRMI() {
		return this.tradeMonitorRMI;
	}

	public void setTradeMonitorRMI(TradeMonitorRMI paramTradeMonitorRMI) {
		this.tradeMonitorRMI = paramTradeMonitorRMI;
	}

	public ServerRMI getServerRMI() {
		return this.serverRMI;
	}

	public void setServerRMI(ServerRMI paramServerRMI) {
		this.serverRMI = paramServerRMI;
	}
}