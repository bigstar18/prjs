package gnnt.MEBS.timebargain.manage.webapp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.manage.service.OrdersManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.service.TradeCtlManager;
import gnnt.MEBS.timebargain.manage.service.TraderManager;
import gnnt.MEBS.timebargain.manage.webapp.form.OrdersForm;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.model.SystemStatus;

public class OrdersAction extends BaseAction {
	public ActionForward chkLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'chkLogin' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			if ((Long) request.getSession().getAttribute("sessionID") == null) {
				throw new Exception("未登录下单服务器！");
			}
			long sessionID = ((Long) request.getSession().getAttribute("sessionID")).longValue();
			String traderID = remObject.getConsignerID(sessionID);
			if (traderID == null) {
				throw new Exception("未登录下单服务器！");
			}
		} catch (Exception err) {
			this.log.error("出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("chkLogin");
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'login' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			String loginIP = request.getRemoteAddr();
			gnnt.MEBS.timebargain.server.model.Consigner consigner = new gnnt.MEBS.timebargain.server.model.Consigner();

			consigner.setConsignerID(request.getParameter("traderID"));
			consigner.setPassword(request.getParameter("password"));
			consigner.setLogonIP(loginIP);
			long ret = remObject.consignerLogon(consigner);
			if (ret == -1L) {
				request.setAttribute("prompt", "代为委托员代码不存在！");
			} else if (ret == -2L) {
				request.setAttribute("prompt", "口令不正确！");
			} else if (ret == -3L) {
				request.setAttribute("prompt", "禁止登录！");
			} else if (ret == -204L) {
				request.setAttribute("prompt", "交易服务器已关闭！");
			} else {
				request.getSession().setAttribute("sessionID", new Long(ret));

				TraderManager mgr = (TraderManager) getBean("traderManager");
				if ((mgr.getConsigner(request.getParameter("traderID")) != null)
						&& (mgr.getConsigner(request.getParameter("traderID")).getType() != null)) {
					request.getSession().setAttribute("type", mgr.getConsigner(request.getParameter("traderID")).getType().toString());
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
			this.log.error("===>save err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("login");
	}

	public ActionForward logoff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'logoff' method");
		}
		try {
			String mkName = request.getParameter("mkName");
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			Long sessionID = (Long) request.getSession().getAttribute("sessionID");
			if (sessionID == null) {
				throw new Exception("未登录下单服务器！");
			}
			remObject.consignerLogoff(sessionID.longValue());
			request.getSession().removeAttribute("sessionID");
			request.setAttribute("prompt", "注销成功！");
			request.setAttribute("mkName", mkName);
		} catch (Exception err) {
			this.log.error("===>logoff err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("msg");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit' method");
		}
		try {
			OrdersForm ordersForm = (OrdersForm) form;
			String type = (String) request.getSession().getAttribute("type");
			if ((type != null) && (!"".equals(type))) {
				ordersForm.setType(type);
			}
			request.setAttribute("type", type);
			updateFormBean(mapping, request, ordersForm);
		} catch (Exception err) {
			this.log.error("==err:" + err);
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("edit");
	}

	public ActionForward editConferClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editConferClose' method");
		}
		return mapping.findForward("editConferClose");
	}

	public ActionForward password(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'password' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			long sessionID = ((Long) request.getSession().getAttribute("sessionID")).longValue();
			String consignerID = remObject.getConsignerID(sessionID);
			request.setAttribute("consignerID", consignerID);
		} catch (Exception err) {
			this.log.error("==err:" + err);
			err.printStackTrace();
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("updatePassword");
	}

	public ActionForward marketChg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'marketChg' method");
		}
		LookupManager lookupMgr = (LookupManager) getBean("lookupManager");
		try {
			request.setAttribute("commoditySelect", lookupMgr.getSelectLabelValueByTable("Commodity", "CommodityID", "Uni_Cmdty_Code",
					" where MarketDate<=sysdate and Status<>1 and MarketCode='" + request.getParameter("MarketCode") + "' order by CommodityID"));
		} catch (Exception err) {
			this.log.error("查询Commodity表出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("marketChg");
	}

	public ActionForward order(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'order' method");
		}
		OrdersForm ordersForm = (OrdersForm) form;
		if (ordersForm.getCloseFlag() == null) {
			ordersForm.setCloseFlag(Short.valueOf((short) 1));
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			if ((Long) request.getSession().getAttribute("sessionID") == null) {
				throw new Exception("未登录下单服务器！");
			}
			Long sessionID = (Long) request.getSession().getAttribute("sessionID");
			String consignerID = remObject.getConsignerID(sessionID.longValue());
			if (consignerID == null) {
				throw new Exception("未登录下单服务器！");
			}
			Settle settle = new Settle();
			StatQueryManager mgr = (StatQueryManager) getBean("statQueryManager");
			settle.setSCustomerID(ordersForm.getCustomerID());
			settle.setCommodityID(ordersForm.getCommodityID());

			String flag = mgr.getConferClose(settle);
			String flag3 = mgr.getConferClose3(settle);
			if ("0".equals(flag)) {
				request.setAttribute("prompt", "商品代码不存在！");
				throw new Exception("商品代码不存在！");
			}
			if ("4".equals(flag3)) {
				request.setAttribute("prompt", "客户代码不存在！");
				throw new Exception("客户代码不存在！");
			}
			Order ov = new Order();

			ov.setCustomerID(ordersForm.getCustomerID());
			ov.setCommodityID(ordersForm.getCommodityID());
			ov.setBuyOrSell(ordersForm.getBS_Flag());
			ov.setOrderType(ordersForm.getOrderType());
			ov.setPrice(ordersForm.getPrice());
			ov.setQuantity(ordersForm.getQuantity());

			ov.setConsignerID(consignerID);
			ov.setCloseMode(ordersForm.getCloseMode());
			ov.setSpecPrice(ordersForm.getSpecPrice());
			ov.setSpecTime(ordersForm.getTimeFlag());
			if (ordersForm.getOrderType().shortValue() == 2) {
				ov.setCloseFlag(ordersForm.getCloseFlag());
				if ((ordersForm.getCloseMode().shortValue() == 1) && (ordersForm.getCloseAlgr() != null)) {
					ov.setCloseAlgr(ordersForm.getCloseAlgr());
				}
			} else {
				ov.setCloseFlag(null);
			}
			this.log.debug("---------------------------------" + ov.getCustomerID());
			this.log.debug("---------------------------------" + ov.getCommodityID());
			this.log.debug("---------------------------------" + ov.getBuyOrSell());
			this.log.debug("---------------------------------" + ov.getOrderType());
			this.log.debug("---------------------------------" + ov.getPrice());
			this.log.debug("---------------------------------" + ov.getQuantity());
			this.log.debug("---------------------------------" + ov.getTraderID());
			this.log.debug("---------------------------------" + ov.getCloseMode());
			this.log.debug("---------------------------------" + ov.getSpecPrice());
			this.log.debug("---------------------------------" + ov.getSpecTime());
			this.log.debug("---------------------------------" + ov.getCloseFlag());

			OrdersManager osm = (OrdersManager) getBean("ordersManager");
			int ret = osm.insertOrder(remObject, sessionID, ov);

			String message = osm.resultOrder(ret);

			request.setAttribute("prompt", message);
		} catch (Exception err) {
			err.printStackTrace();
			this.log.error("===>save err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("order");
	}

	public ActionForward forceClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'forceClose' method");
		}
		OrdersForm ordersForm = (OrdersForm) form;
		if (ordersForm.getCloseFlag() == null) {
			ordersForm.setCloseFlag(Short.valueOf((short) 1));
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			if ((Long) request.getSession().getAttribute("sessionID") == null) {
				throw new Exception("未登录下单服务器！");
			}
			Long sessionID = (Long) request.getSession().getAttribute("sessionID");
			String consignerID = remObject.getConsignerID(sessionID.longValue());
			if (consignerID == null) {
				throw new Exception("未登录下单服务器！");
			}
			String type = (String) request.getSession().getAttribute("type");
			if ((type != null) && (!"".equals(type)) && ("0".equals(type))) {
				throw new Exception("代为委托员不能进行强平操作！");
			}
			String customerID = ordersForm.getCustomerID();

			OrdersManager osm = (OrdersManager) getBean("ordersManager");
			String firmID = osm.getFirmIDToDefine(customerID);
			String relFirmID = ordersForm.getFirmID();
			if ((firmID != null) && (!"".equals(firmID)) && (relFirmID != null) && (!"".equals(relFirmID)) && (firmID.equals(relFirmID))) {
				Order ov = new Order();
				StatQueryManager mgr = (StatQueryManager) getBean("statQueryManager");

				String bS_Flag = request.getParameter("bS_Flag");
				String relBS_Flag = "";
				if ("1".equals(bS_Flag)) {
					relBS_Flag = "2";
				}
				if ("2".equals(bS_Flag)) {
					relBS_Flag = "1";
				}
				String commodityID = request.getParameter("CommodityID");

				ov.setFirmID(firmID);
				ov.setCustomerID(ordersForm.getCustomerID());

				ov.setCommodityID(commodityID);
				if ((relBS_Flag != null) && (!"".equals(relBS_Flag))) {
					ov.setBuyOrSell(Short.valueOf(Short.parseShort(relBS_Flag)));
				}
				ov.setOrderType(ordersForm.getOrderType());
				ov.setPrice(ordersForm.getPrice());
				ov.setQuantity(ordersForm.getQuantity());

				ov.setConsignerID(consignerID);
				ov.setCloseMode(ordersForm.getCloseMode());
				ov.setSpecPrice(ordersForm.getSpecPrice());
				ov.setSpecTime(ordersForm.getTimeFlag());
				ov.setCloseFlag(ordersForm.getCloseFlag());
				request.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);

				int ret = osm.insertOrder(remObject, sessionID, ov);

				String message = osm.resultOrder(ret);
				request.setAttribute("prompt", message);
			} else {
				request.setAttribute("prompt", "此客户代码不属于此交易商" + firmID + "！");
			}
		} catch (Exception err) {
			this.log.error("===>save err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("forceClose");
	}

	public ActionForward forceCloseExpire(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'forceCloseExpire' method");
		}
		OrdersForm ordersForm = (OrdersForm) form;
		if (ordersForm.getCloseFlag() == null) {
			ordersForm.setCloseFlag(Short.valueOf((short) 1));
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			if ((Long) request.getSession().getAttribute("sessionID") == null) {
				throw new Exception("未登录下单服务器！");
			}
			Long sessionID = (Long) request.getSession().getAttribute("sessionID");
			String consignerID = remObject.getConsignerID(sessionID.longValue());
			if (consignerID == null) {
				throw new Exception("未登录下单服务器！");
			}
			String type = (String) request.getSession().getAttribute("type");
			if ((type != null) && (!"".equals(type)) && ("0".equals(type))) {
				throw new Exception("代为委托员不能进行强平操作！");
			}
			String customerID = ordersForm.getCustomerID();

			OrdersManager osm = (OrdersManager) getBean("ordersManager");
			String firmID = osm.getFirmIDToDefine(customerID);
			String relFirmID = ordersForm.getFirmID();
			if ((firmID != null) && (!"".equals(firmID)) && (relFirmID != null) && (!"".equals(relFirmID)) && (firmID.equals(relFirmID))) {
				Order ov = new Order();
				StatQueryManager mgr = (StatQueryManager) getBean("statQueryManager");

				String bS_Flag = request.getParameter("bS_Flag");
				String relBS_Flag = "";
				if ("1".equals(bS_Flag)) {
					relBS_Flag = "2";
				}
				if ("2".equals(bS_Flag)) {
					relBS_Flag = "1";
				}
				String commodityID = request.getParameter("CommodityID");

				ov.setFirmID(firmID);
				ov.setCustomerID(ordersForm.getCustomerID());

				ov.setCommodityID(commodityID);
				if ((relBS_Flag != null) && (!"".equals(relBS_Flag))) {
					ov.setBuyOrSell(Short.valueOf(Short.parseShort(relBS_Flag)));
				}
				ov.setOrderType(ordersForm.getOrderType());
				ov.setPrice(ordersForm.getPrice());
				ov.setQuantity(ordersForm.getQuantity());

				ov.setConsignerID(consignerID);
				ov.setCloseMode(ordersForm.getCloseMode());
				ov.setSpecPrice(ordersForm.getSpecPrice());
				ov.setSpecTime(ordersForm.getTimeFlag());
				ov.setCloseFlag(ordersForm.getCloseFlag());
				request.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);

				int ret = osm.insertCloseOrder(remObject, sessionID, ov);
				if (ret == -500) {
					request.setAttribute("expireMessage", "可平仓数量不足，请重新操作！");
				} else {
					String message = osm.resultOrder(ret);
					request.setAttribute("prompt", message);
				}
			} else {
				request.setAttribute("prompt", "此客户代码不属于此交易商" + firmID + "！");
			}
		} catch (Exception err) {
			this.log.error("===>save err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("forceClose");
	}

	public ActionForward noTradeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'noTradeList' method");
		}
		String sectionId = request.getParameter("sectionId");
		String tradeId = request.getParameter("traderId");
		OrdersManager mgr = (OrdersManager) getBean("ordersManager");
		TradeCtlManager mgr1 = (TradeCtlManager) getBean("tradeCtlManager");
		Orders orders = new Orders();
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			if ((Long) request.getSession().getAttribute("sessionID") == null) {
				throw new Exception("未登录下单服务器！");
			}
			long sessionID = ((Long) request.getSession().getAttribute("sessionID")).longValue();
			String traderID = remObject.getConsignerID(sessionID);
			if (traderID == null) {
				throw new Exception("未登录下单服务器！");
			}
			orders.setTraderID(traderID);
			if (tradeId != null) {
				request.setAttribute("traderId", tradeId);
			}
			List lst = mgr.noTradeList(orders, sectionId, tradeId);
			List tradeTimeList = mgr1.getTradeTime();
			request.setAttribute("noTradeList", lst);
			request.setAttribute("BS_FLAG", CommonDictionary.BS_FLAG);
			request.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
			request.setAttribute("ORDER_STATUS", CommonDictionary.ORDER_STATUS);
			request.setAttribute("CLOSEFLAG", CommonDictionary.CLOSEFLAG);
			if ((sectionId != null) && (!"".equals(sectionId))) {
				request.setAttribute("sectionId", sectionId);
			}
			request.setAttribute("tradeTimeList", tradeTimeList);
		} catch (Exception err) {
			this.log.error("查询orders表出错：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("noTradeList");
	}

	public ActionForward withdrawOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'withdrawOrder' method");
		}
		String[] ids = request.getParameterValues("itemlist");
		int success = 0;
		if (ids != null) {
			Long sessionID;
			String traderID = null;
			AgencyRMIBean remObject = null;
			try {
				remObject = new AgencyRMIBean(request);
				if ((Long) request.getSession().getAttribute("sessionID") == null) {
					throw new Exception("未登录下单服务器！");
				}
				sessionID = (Long) request.getSession().getAttribute("sessionID");
				traderID = remObject.getConsignerID(sessionID.longValue());
				if (traderID == null) {
					throw new Exception("未登录下单服务器！");
				}
			} catch (Exception err) {
				this.log.error("===>withdrawOrder err：" + err.getMessage());
				request.setAttribute("prompt", err.getMessage());
				return noTradeList(mapping, form, request, response);
			}
			Order ov = new Order();

			ov.setConsignerID(traderID);
			ov.setOrderType(Short.valueOf("4"));

			String prompt = "";
			String prompt2 = "";
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				this.log.debug("--->id:" + id);
				String[] idArr = id.split(";");
				ov.setCommodityID(idArr[1]);
				ov.setCustomerID(idArr[2]);
				ov.setWithdrawID(Long.valueOf(idArr[0]));
				try {
					OrdersManager osm = (OrdersManager) getBean("ordersManager");
					int ret = osm.insertOrder(remObject, sessionID, ov);

					String message = osm.resultOrder(ret);
					request.setAttribute("prompt", message);
					if (ret == 0) {
						success++;
					} else {
						prompt2 = prompt2 + ov.getWithdrawID() + ",";
					}
				} catch (Exception err) {
					err.printStackTrace();
					this.log.error("===>withdraw err：" + err.getMessage());
				}
			}
			prompt = prompt + "成功撤单" + success + "条纪录！";
			if (!prompt2.equals("")) {
				prompt = prompt + "失败单号【" + prompt2.substring(0, prompt2.length() - 1) + "】";
			}
			request.setAttribute("prompt", prompt);
		}
		return noTradeList(mapping, form, request, response);
	}

	public ActionForward balanceChk(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceChk' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			int ret = remObject.checkFrozenQtyAtBalance();
			request.setAttribute("chkRet", new Integer(ret));
		} catch (Exception err) {
			this.log.error("===>balanceChk err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceChk");
	}

	public ActionForward balanceUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceUpload' method");
		}
		try {
			MarketManager mgr = (MarketManager) getBean("marketManager");
			List list = mgr.getMarkets(null);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String marketId = (String) map.get("MarketCode");

				request.setAttribute("marketId", marketId);
			}
			OrdersManager mgr1 = (OrdersManager) getBean("ordersManager");
			mgr1.deleteTradeRespond();
		} catch (Exception err) {
			this.log.error("==err:" + err);
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceUpload");
	}

	public ActionForward listTradeRespond(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'listTradeRespond' method");
		}
		String sign = "false";
		String sign1 = "false";
		String sign2 = "false";
		String exp = request.getParameter("exp");
		try {
			OrdersManager mgr = (OrdersManager) getBean("ordersManager");
			List list = mgr.getTradeResponds();
			List list1 = mgr.getTradeRespondsByStatus(new Short("1"));
			List list2 = mgr.getTradeRespondsByStatus(new Short("0"));
			if ((list1 != null) && (list1.size() > 0)) {
				sign = "true";
			}
			if ((list2 != null) && (list2.size() == 0)) {
				sign1 = "true";
			}
			if (list.size() == 0) {
				sign2 = "true";
			}
			request.setAttribute("sign", sign);
			request.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			request.setAttribute("RESPOND_STATUS", CommonDictionary.RESPOND_STATUS);
			request.setAttribute("tradeRespondList", list);
		} catch (Exception err) {
			this.log.error("==err:" + err);
			request.setAttribute("prompt", err.getMessage());
		}
		if ("0".equals(exp)) {
			if ("false".equals(sign2)) {
				if (("false".equals(sign)) && ("true".equals(sign1))) {
					request.setAttribute("r", "0");
					request.setAttribute("result", "上传处理");
					return mapping.findForward("balanceChkStatus");
				}
				return mapping.findForward("tradeRespondList");
			}
			request.setAttribute("r", "上传有误");
			return mapping.findForward("balanceUpload");
		}
		request.setAttribute("r", exp);
		return mapping.findForward("balanceUpload");
	}

	public ActionForward balanceChkStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceChkStatus' method");
		}
		try {
			StatQueryManager stat = (StatQueryManager) getBean("statQueryManager");
			SystemStatus sys = stat.getSystemStatusObject();
			String result = "";

			int r = sys.getStatus();
			if (r == 0) {
				request.setAttribute("result", "0");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[0]);
			}
			if (r == 1) {
				request.setAttribute("result", "1");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[1]);
			}
			if (r == 2) {
				request.setAttribute("result", "2");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[2]);
			}
			if (r == 3) {
				request.setAttribute("result", "3");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[3]);
			}
			if (r == 4) {
				request.setAttribute("result", "4");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[4]);
			}
			if (r == 5) {
				request.setAttribute("result", "5");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[5]);
			}
			if (r == 6) {
				request.setAttribute("result", "6");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[6]);
			}
			if (r == 7) {
				request.setAttribute("result", "7");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[7]);
			}
			if (r == 8) {
				request.setAttribute("result", "8");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[8]);
			}
			if (r == 9) {
				request.setAttribute("result", "9");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[9]);
			}
			if (r == 10) {
				request.setAttribute("result", "10");
				request.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[10]);
			}
		} catch (Exception err) {
			this.log.error("===>balanceChkStatus err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceChkStatus");
	}

	public ActionForward balanceChkStatus1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceChkStatus1' method");
		}
		try {
			request.setAttribute("result", "上传处理");
			request.setAttribute("r", "0");
		} catch (Exception err) {
			this.log.error("===>balanceChkStatus err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceChkStatus");
	}

	public ActionForward balanceChkFroenFund(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceChkFroenFund' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			int ret = remObject.checkFrozenQtyAtBalance();

			request.setAttribute("chkRet", new Integer(ret));
		} catch (Exception err) {
			this.log.error("===>balanceChkFroenFund err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceChkFroenFund");
	}

	public ActionForward balanceChkFroenFundEXC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceChkFroenFundEXC' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			int ret = remObject.checkFrozenQtyAtBalance();

			request.setAttribute("chkRet", new Integer(ret));
		} catch (Exception err) {
			this.log.error("===>balanceChkFroenFund err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceChkFroenFundEXC");
	}

	public ActionForward balanceCloseMarket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceCloseMarket' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			int ret = remObject.balance();
			request.setAttribute("chkRet", new Integer(ret));
		} catch (Exception err) {
			this.log.error("===>balanceCloseMarket err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceCloseMarket");
	}

	public ActionForward balanceCloseMarketEXC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balanceCloseMarketEXC' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			int ret = remObject.balance();
			request.setAttribute("chkRet", new Integer(ret));
		} catch (Exception err) {
			this.log.error("===>balanceCloseMarket err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balanceCloseMarketEXC");
	}

	public ActionForward balance(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'balance' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			int ret = remObject.balance();
			if (ret == 1) {
				throw new Exception("成功！");
			}
			if (ret == -1) {
				throw new Exception("更新商品保证金错误！");
			}
			if (ret == -2) {
				throw new Exception("交收处理出错！");
			}
			if (ret == -100) {
				throw new Exception("执行存储失败！");
			}
		} catch (Exception err) {
			this.log.error("===>save err：" + err.getMessage());
			request.setAttribute("prompt", err.getMessage());
		}
		return mapping.findForward("balance");
	}

	public ActionForward trader_list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'trader_list' method");
		}
		try {
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			List lst = remObject.getTraders();
			request.setAttribute("traderList", lst);
		} catch (Exception e) {
			this.log.error("--->e.message:" + e.getMessage());
			request.setAttribute("prompt", e.getMessage());
		}
		return mapping.findForward("traderList");
	}

	public ActionForward outLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'outLine' method");
		}
		try {
			String traderID = request.getParameter("traderID");
			String firmID = request.getParameter("firmID");
			AgencyRMIBean remObject = new AgencyRMIBean(request);
			List lst = remObject.getTraders();
			String traderID1 = "";
			int a = 0;
			if ((lst != null) && (lst.size() > 0)) {
				for (Object o : lst) {
					Map map = (Map) o;
					traderID1 = (String) map.get("traderID");
					if (traderID.equals(traderID1)) {
						a++;
					}
				}
			}
			if (a == 0) {
				request.setAttribute("prompt", "此交易员不存在或未上线！");
				return mapping.findForward("save");
			}
			remObject.kickOnlineTrader(traderID);
			if ((firmID != null) && (!"".equals(firmID))) {
				remObject.kickAllTrader(firmID);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error("--->e.message:" + e.getMessage());
			request.setAttribute("prompt", e.getMessage());
			return trader_list(mapping, form, request, response);
		}
		return trader_list(mapping, form, request, response);
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	private void getSelectAttribute(HttpServletRequest request) throws Exception {
		LookupManager lookupMgr = (LookupManager) getBean("lookupManager");
		request.setAttribute("marketSelect", lookupMgr.getSelectLabelValueByTable("Market", "MarketName", "MarketCode", " where Status=1"));
	}

	private int submitOrder(AgencyRMIBean remObject, Long sessionID, Order ov, HttpServletRequest request) throws Exception {
		int ret = remObject.consignerOrder(sessionID.longValue(), ov);
		switch (ret) {
		case 0:
			break;
		case 1:
			request.setAttribute("prompt", "校验异常！");
			break;
		case 2:
			request.setAttribute("prompt", "市场未启用！");
			break;
		case 3:
			request.setAttribute("prompt", "不是交易时间！");
			break;
		case 4:
			request.setAttribute("prompt", "不是代为委托员交易时间！");
			break;
		case 5:
			request.setAttribute("prompt", "交易员和代为委托员不能同时存在！");
			break;
		case 10:
			request.setAttribute("prompt", "商品处于禁止交易状态！");
			break;
		case 11:
			request.setAttribute("prompt", "商品不属于当前交易节！");
			break;
		case 12:
			request.setAttribute("prompt", "委托价格超出涨幅上限！");
			break;
		case 13:
			request.setAttribute("prompt", "委托价格低于跌幅下限！");
			break;
		case 14:
			request.setAttribute("prompt", "委托价格不在此商品最小价位变动范围内！");
			break;
		case 15:
			request.setAttribute("prompt", "不存在此商品！");
			break;
		case 16:
			request.setAttribute("prompt", "委托数量不在此商品最小变动数量范围内！");
			break;
		case 30:
			request.setAttribute("prompt", "此交易员不存在！");
			break;
		case 31:
			request.setAttribute("prompt", "此交易员没有操作该客户的权限！");
			break;
		case 32:
			request.setAttribute("prompt", "此客户不存在！");
			break;
		case 33:
			request.setAttribute("prompt", "此客户为禁止交易状态！");
			break;
		case 34:
			request.setAttribute("prompt", "此交易商不存在！");
			break;
		case 35:
			request.setAttribute("prompt", "此交易商为禁止交易状态！");
			break;
		case 37:
			request.setAttribute("prompt", "此代为委托员没有操作该交易商的权限！");
			break;
		case 38:
			request.setAttribute("prompt", "此代为委托员不存在！");
			break;
		case 40:
			request.setAttribute("prompt", "计算交易保证金错误！");
			break;
		case 41:
			request.setAttribute("prompt", "计算交易手续费错误！");
			break;
		case 42:
			request.setAttribute("prompt", "此委托已成交或已撤单！");
			break;
		case 199:
			request.setAttribute("prompt", "通信故障！");
			break;
		case 200:
			request.setAttribute("prompt", "代码异常而失败！");
			break;
		case -1:
			request.setAttribute("prompt", "资金余额不足！");
			break;
		case -2:
			request.setAttribute("prompt", "超过交易商对此商品的最大订货量！");
			break;
		case -3:
			request.setAttribute("prompt", "超过客户总的最大订货量！");
			break;
		case -4:
			request.setAttribute("prompt", "超过品种最大订货量！");
			break;
		case -5:
			request.setAttribute("prompt", "超过商品最大订货量！");
			break;
		case -6:
			request.setAttribute("prompt", "超过交易商对此商品的最大净订货量！");
			break;
		case -7:
			request.setAttribute("prompt", "超过单笔最大委托量！");
			break;
		case -11:
			request.setAttribute("prompt", "此委托已处于正在撤单状态！");
			break;
		case -12:
			request.setAttribute("prompt", "此委托已全部成交了！");
			break;
		case -13:
			request.setAttribute("prompt", "此委托已完成撤单了！");
			break;
		case -21:
			request.setAttribute("prompt", "订货不足！");
			break;
		case -22:
			request.setAttribute("prompt", "指定仓不足！");
			break;
		case -99:
			request.setAttribute("prompt", "执行存储时未找到相关数据！");
			break;
		case -100:
			request.setAttribute("prompt", "执行存储失败！");
			break;
		case -204:
			request.setAttribute("prompt", "交易服务器已关闭！");
			break;
		case -206:
			request.setAttribute("prompt", "委托信息不能为空！");
			break;
		default:
			request.setAttribute("prompt", "委托失败！");
		}
		return ret;
	}
}
