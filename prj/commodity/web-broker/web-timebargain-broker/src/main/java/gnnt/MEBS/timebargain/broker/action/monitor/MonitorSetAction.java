package gnnt.MEBS.timebargain.broker.action.monitor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
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

import gnnt.MEBS.common.broker.action.EcsideAction;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.timebargain.broker.dao.monitor.MonitorSet;
import gnnt.MEBS.timebargain.broker.service.monitor.MonitorSetService;
import gnnt.MEBS.timebargain.broker.util.ObjSet;

@Controller("monitorSetAction")
@Scope("request")
public class MonitorSetAction extends EcsideAction {
	protected final transient Log logger = LogFactory.getLog(getClass());
	private static final long serialVersionUID = 5124568190167465621L;

	@Autowired
	@Qualifier("monitorSetService")
	private MonitorSetService monitorSetService;
	private int refreshTime;
	private int pageSize;

	public String listTradeList() {
		try {
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " and FirmID in (" + localUser.getSql() + ")";
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

	public String listCommodityMonitorB() {
		try {
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where BS_Flag=1 and FirmID in (" + localUser.getSql() + ") ";
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
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where BS_Flag=2 and FirmID in (" + localUser.getSql() + ") ";
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
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = " ";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where FirmID in (" + localUser.getSql() + ") ";
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

	public String listOrderMonitor() {
		try {
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			String str2 = " where FirmID in (" + localUser.getSql() + ") ";
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

	public String listFundsAnalysis() {
		try {
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str1 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str1 = this.request.getParameter("orderby");
			String str2 = " where FirmID in (" + localUser.getSql() + ") ";
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

	public String listAnalyseInfo() {
		try {
			User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
			String str1 = " and f.FirmID in (" + localUser.getSql() + ")";
			int i = MonitorSet.getInstance().getPageSize();
			int j = 1;
			String str2 = "";
			if (this.request.getParameter("pageIndex") != null)
				j = MonitorSet.parseInt(this.request.getParameter("pageIndex"), 1);
			if (this.request.getParameter("orderby") != null)
				str2 = this.request.getParameter("orderby");
			ObjSet localObjSet = this.monitorSetService.getAnalyseInfo(i, j, str1, str2);
			createXML(this.request, this.response, localObjSet);
		} catch (Exception localException) {
			this.logger.error("排行分析出错：" + localException.getMessage());
			localException.printStackTrace();
		}
		return "none";
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

	public String getCommoditySelect() throws Exception {
		String str1 = this.request.getParameter("type");
		String str2 = this.request.getParameter("firmID");
		String str3 = this.request.getParameter("commodityID2");
		String str4 = "select distinct t.commodityid commodityId from t_orders t order by t.commodityid";
		List localList1 = getService().getListBySql(str4);
		String str5 = "select distinct t.commodityid commodityId from t_FirmHoldSum t order by t.commodityid ";
		List localList2 = getService().getListBySql(str5);
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
		if (str1.equals("orderMonitor"))
			return "orderMonitor";
		if (str1.equals("commodityMonitor"))
			return "commodityMonitor";
		throw new Exception("//Class:MonitorSetAction--method:getCommoditySelect没找到匹配的字符");
	}
}