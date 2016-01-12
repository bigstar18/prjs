package gnnt.MEBS.timebargain.mgr.action.authorize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.AbstractService;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.timebargain.mgr.model.Orders;
import gnnt.MEBS.timebargain.mgr.model.authorize.OverdueHoldPosition;
import gnnt.MEBS.timebargain.mgr.model.dataquery.CommodityF;
import gnnt.MEBS.timebargain.mgr.service.AuthorizeService;
import gnnt.MEBS.timebargain.mgr.util.StringUtil;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;

@Controller("authorizeAction")
@Scope("request")
public class AuthorizeAction extends EcsideAction {
	private static final long serialVersionUID = 5124568112367465621L;

	@Autowired
	@Qualifier("TradeRMI")
	private TradeRMI tradeRMI;

	@Autowired
	@Qualifier("authorizeService")
	private AuthorizeService authorizeService;
	private String mkName;

	@Resource(name = "authorize_BS_FlagMap")
	private Map<String, String> authorize_BS_FlagMap;

	@Resource(name = "authorize_OrderTypeMap")
	private Map<String, String> authorize_OrderTypeMap;

	@Resource(name = "authorize_Order_StatusMap")
	private Map<String, String> authorize_Order_StatusMap;

	@Resource(name = "authorize_CloseFlagMap")
	private Map<String, String> authorize_CloseFlagMap;
	private String closeFlag;

	public TradeRMI getTradeRMI() {
		return this.tradeRMI;
	}

	public void setTradeRMI(TradeRMI paramTradeRMI) {
		this.tradeRMI = paramTradeRMI;
	}

	public String getMkName() {
		return this.mkName;
	}

	public void setMkName(String paramString) {
		this.mkName = paramString;
	}

	public Map<String, String> getAuthorize_BS_FlagMap() {
		return this.authorize_BS_FlagMap;
	}

	public Map<String, String> getAuthorize_OrderTypeMap() {
		return this.authorize_OrderTypeMap;
	}

	public Map<String, String> getAuthorize_Order_StatusMap() {
		return this.authorize_Order_StatusMap;
	}

	public Map<String, String> getAuthorize_CloseFlagMap() {
		return this.authorize_CloseFlagMap;
	}

	public void setAuthorize_BS_FlagMap(Map<String, String> paramMap) {
		this.authorize_BS_FlagMap = paramMap;
	}

	public void setAuthorize_OrderTypeMap(Map<String, String> paramMap) {
		this.authorize_OrderTypeMap = paramMap;
	}

	public void setAuthorize_Order_StatusMap(Map<String, String> paramMap) {
		this.authorize_Order_StatusMap = paramMap;
	}

	public void setAuthorize_CloseFlagMap(Map<String, String> paramMap) {
		this.authorize_CloseFlagMap = paramMap;
	}

	public String authorizeForward() throws Exception {
		this.logger.debug("------------authorizeForward 代为委托跳转--------------");
		return "success";
	}

	public String chkLogin() throws Exception {
		this.logger.debug("------------chkLogin 验证代为委托员是否登录--------------");
		try {
			if ((Long) this.request.getSession().getAttribute("sessionID") == null)
				throw new Exception("未登录下单服务器！");
			long l = ((Long) this.request.getSession().getAttribute("sessionID")).longValue();
			String str = this.tradeRMI.getConsignerID(l);
			if (str == null)
				throw new Exception("未登录下单服务器！");
		} catch (Exception localException) {
			this.logger.error("出错：" + localException.getMessage());
			this.request.setAttribute("prompt", localException.getMessage());
		}
		return "success";
	}

	public String login() throws Exception {
		this.logger.debug("------------login 代为委托员登录--------------");
		try {
			String str1 = this.request.getRemoteAddr();
			Consigner localConsigner = new Consigner();
			localConsigner.setConsignerID(this.request.getParameter("traderID"));
			localConsigner.setPassword(this.request.getParameter("password"));
			localConsigner.setLogonIP(str1);
			long l = this.tradeRMI.consignerLogon(localConsigner);
			if (l == -1L) {
				this.request.setAttribute("prompt", "代为委托员代码不存在！");
				writeOperateLog(1510, "代为委托员代码不存在！", 0, "");
			} else if (l == -2L) {
				this.request.setAttribute("prompt", "口令不正确！");
				writeOperateLog(1510, "口令不正确！", 0, "");
			} else if (l == -3L) {
				this.request.setAttribute("prompt", "禁止登录！");
				writeOperateLog(1510, "禁止登录！", 0, "");
			} else if (l == -204L) {
				this.request.setAttribute("prompt", "交易服务器已关闭！");
				writeOperateLog(1510, "交易服务器已关闭！", 0, "");
			} else {
				this.request.getSession().setAttribute("sessionID", new Long(l));
				String str2 = "select * from m_Agenttrader where AgenttraderID='" + this.request.getParameter("traderID") + "'";
				List localList = getService().getListBySql(str2);
				if ((localList != null) && (localList.size() > 0)) {
					Map localMap = (Map) localList.get(0);
					this.request.getSession().setAttribute("type", localMap.get("TYPE").toString());
				}
				writeOperateLog(1510, "委托员" + this.request.getParameter("traderID") + "登陆成功", 1, "");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.logger.error("===>save err：" + localException.getMessage());
			this.request.setAttribute("prompt", localException.getMessage());
		}
		return "success";
	}

	public String logoff() throws Exception {
		this.logger.debug("------------logoff 代为委托员注销--------------");
		try {
			Long localLong = (Long) this.request.getSession().getAttribute("sessionID");
			if (localLong == null)
				throw new Exception("未登录下单服务器！");
			this.tradeRMI.consignerLogoff(localLong.longValue());
			this.request.getSession().removeAttribute("sessionID");
			this.request.setAttribute("prompt", "注销成功！");
			writeOperateLog(1510, "委托员注销成功", 1, "");
		} catch (Exception localException) {
			this.logger.error("===>logoff err：" + localException.getMessage());
			this.request.setAttribute("prompt", localException.getMessage());
		}
		System.out.println(getMkName());
		return "success";
	}

	public String edit() throws Exception {
		this.logger.debug("------------edit 跳转委托界面--------------");
		return "success";
	}

	public String getCloseFlag() {
		return this.closeFlag;
	}

	public void setCloseFlag(String paramString) {
		this.closeFlag = paramString;
	}

	public String order() throws Exception {
		this.logger.debug("------------order 代为委托--------------");
		Orders localOrders = (Orders) this.entity;
		try {
			if ((Long) this.request.getSession().getAttribute("sessionID") == null)
				throw new Exception("未登录下单服务器！");
			Long localLong = (Long) this.request.getSession().getAttribute("sessionID");
			String str1 = this.tradeRMI.getConsignerID(localLong.longValue());
			if (str1 == null)
				throw new Exception("未登录下单服务器！");
			String[] arrayOfString = this.authorizeService.getOperateFirms(str1);
			Settle localSettle = new Settle();
			localSettle.setSCustomerID(localOrders.getCustomerId());
			localSettle.setCommodityID(localOrders.getCommodityId());
			if (arrayOfString != null) {
				List localList1 = getService()
						.getListBySql("select count(*) COUNT from T_Commodity where commodityid='" + localSettle.getCommodityID() + "'");
				List localList2 = getService()
						.getListBySql("select count(*) COUNT from T_Customer where CustomerID='" + localSettle.getSCustomerID() + "'");
				String str2 = "";
				String str3 = "";
				int i;
				if ((localList1 != null) && (localList1.size() > 0)) {
					i = Integer.parseInt(((Map) localList1.get(0)).get("COUNT").toString());
					str2 = i < 1 ? "0" : "1";
				}
				if ((localList2 != null) && (localList2.size() > 0)) {
					i = Integer.parseInt(((Map) localList2.get(0)).get("COUNT").toString());
					str3 = i < 1 ? "4" : "5";
				}
				if ("0".equals(str2)) {
					this.request.setAttribute("prompt", "商品代码不存在！");
					this.logger.debug("商品代码不存在！");
					writeOperateLog(1510, "商品代码不存在！", 0, "");
				} else if ("4".equals(str3)) {
					this.request.setAttribute("prompt", "客户代码不存在！");
					this.logger.debug("客户代码不存在！");
					writeOperateLog(1510, "客户代码不存在！", 0, "");
				} else {
					List localList3 = getService()
							.getListBySql("select firmId from T_Customer where CustomerID='" + localSettle.getSCustomerID() + "'");
					String str4 = ((Map) localList3.get(0)).get("FIRMID").toString();
					int j = 0;
					for (int k = 0; k < arrayOfString.length; k++)
						if (arrayOfString[k].equals(str4)) {
							j = 1;
							break;
						}
					if (j == 1) {
						Order localOrder = new Order();
						localOrder.setCustomerID(localOrders.getCustomerId());
						localOrder.setCommodityID(localOrders.getCommodityId());
						localOrder.setBuyOrSell(localOrders.getBS_Flag());
						localOrder.setOrderType(localOrders.getOrderType());
						localOrder.setPrice(localOrders.getPrice());
						localOrder.setQuantity(localOrders.getQuantity());
						localOrder.setConsignerID(str1);
						localOrder.setCloseMode(localOrders.getCloseMode());
						localOrder.setSpecPrice(localOrders.getSpecPrice());
						localOrder.setSpecTime(localOrders.getTimeFlag());
						if (localOrders.getOrderType().shortValue() == 2)
							localOrder.setCloseFlag(Short.valueOf(Short.parseShort(this.closeFlag == null ? "0" : this.closeFlag)));
						else
							localOrder.setCloseFlag(null);
						int m = submitOrder(localLong, localOrder);
						this.logger.debug("order ret: " + m);
						if (m == 0) {
							this.request.setAttribute("prompt", "委托下单成功！");
							writeOperateLog(1510, "委托下单成功！", 1, "");
						}
					} else {
						this.request.setAttribute("prompt", "无权限对该客户进行操作，委托下单失败！");
						writeOperateLog(1510, "无权限对该客户进行操作，委托下单失败！", 0, "");
					}
				}
			} else {
				this.request.setAttribute("prompt", "无权限对该客户进行操作，委托下单失败！");
				writeOperateLog(1510, "无权限对该客户进行操作，委托下单失败！", 0, "");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.request.setAttribute("prompt", localException.getMessage());
		}
		return "success";
	}

	public String noTradeListForward() throws Exception {
		this.logger.debug("------------noTradeListForward 代为撤单跳转--------------");
		return "success";
	}

	public String noTradeList() throws Exception {
		this.logger.debug("------------noTradeList 代为撤单页面查询--------------");
		String str1 = this.request.getParameter("traderId");
		String str2 = this.request.getParameter("customerId");
		String str3 = this.request.getParameter("consignerId");
		String str4 = this.request.getParameter("commodityId");
		Orders localOrders = new Orders();
		try {
			if ((Long) this.request.getSession().getAttribute("sessionID") == null)
				throw new Exception("未登录下单服务器！");
			long l = ((Long) this.request.getSession().getAttribute("sessionID")).longValue();
			String str5 = this.tradeRMI.getConsignerID(l);
			if (str5 == null)
				throw new Exception("未登录下单服务器！");
			String str6 = this.authorizeService.getOperateFirm(str5);
			String str7 = "";
			if ((str1 != null) && (!str1.equals("")))
				str7 = str7 + " and a.traderid = '" + str1 + "'";
			if ((str2 != null) && (!str2.equals("")))
				str7 = str7 + " and a.customerid = '" + str2 + "'";
			if ((str3 != null) && (!str3.equals("")))
				str7 = str7 + " and a.consignerId = '" + str3 + "'";
			if ((str4 != null) && (!str4.equals("")))
				str7 = str7 + " and a.commodityid = '" + str4 + "'";
			localOrders.setTraderId(str5);
			if (!str6.equals("")) {
				String str8 = "select a.*,c.CommodityID from T_Orders a,T_Commodity c where a.OrderType<>4 and a.CommodityID=c.CommodityID and a.Status in(1,2) and a.firmId in ("
						+ str6 + ") " + str7 + " order by a.OrderTime desc";
				List localList = getService().getListBySql(str8);
				PageRequest localPageRequest = super.getPageRequest(this.request);
				Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
				this.request.setAttribute("pageInfo", localPage);
			}
		} catch (Exception localException) {
			this.logger.error("查询orders表出错：" + localException.getMessage());
			this.request.setAttribute("prompt", localException.getMessage());
		}
		this.request.setAttribute("traderId", str1);
		this.request.setAttribute("customerId", str2);
		this.request.setAttribute("consignerId", str3);
		this.request.setAttribute("commodityId", str4);
		return "success";
	}

	public String withdrawOrder() throws Exception {
		this.logger.debug("------------withdrawOrder 下撤单委托--------------");
		String[] arrayOfString1 = this.request.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString1 != null) {
			Long localLong;
			String str1;
			try {
				if ((Long) this.request.getSession().getAttribute("sessionID") == null)
					throw new Exception("未登录下单服务器！");
				localLong = (Long) this.request.getSession().getAttribute("sessionID");
				str1 = this.tradeRMI.getConsignerID(localLong.longValue());
				if (str1 == null)
					throw new Exception("未登录下单服务器！");
			} catch (Exception localException1) {
				this.logger.error("===>withdrawOrder err：" + localException1.getMessage());
				this.request.setAttribute("prompt", localException1.getMessage());
				return "success";
			}
			Order localOrder = new Order();
			localOrder.setConsignerID(str1);
			localOrder.setOrderType(Short.valueOf("4"));
			String str3 = "";
			String str4 = "";
			for (int j = 0; j < arrayOfString1.length; j++) {
				String str2 = arrayOfString1[j];
				this.logger.debug("id:" + str2);
				String[] arrayOfString2 = str2.split(";");
				localOrder.setCommodityID(arrayOfString2[1]);
				localOrder.setCustomerID(arrayOfString2[2]);
				localOrder.setWithdrawID(Long.valueOf(arrayOfString2[0]));
				try {
					int k = submitOrder(localLong, localOrder);
					if (k == 0)
						i++;
					else
						str4 = str4 + localOrder.getWithdrawID() + ",";
				} catch (Exception localException2) {
					localException2.printStackTrace();
					this.logger.error("===>withdraw err：" + localException2.getMessage());
				}
			}
			str3 = str3 + "成功撤单" + i + "条纪录！";
			if (!str4.equals(""))
				str3 = str3 + "失败单号【" + str4.substring(0, str4.length() - 1) + "】";
			writeOperateLog(1510, str3, 1, "");
			this.request.setAttribute("prompt", str3);
		}
		return "success";
	}

	public String forceForward() throws Exception {
		this.logger.debug("------------forceForward 强行转让跳转--------------");
		return "success";
	}

	public String searchForceClose() throws Exception {
		this.logger.debug("------------searchForceClose 查看强行平仓记录--------------");
		if ((Long) this.request.getSession().getAttribute("sessionID") == null)
			throw new Exception("未登录下单服务器！");
		long l = ((Long) this.request.getSession().getAttribute("sessionID")).longValue();
		String str1 = this.tradeRMI.getConsignerID(l);
		if (str1 == null)
			throw new Exception("未登录下单服务器！");
		String str2 = this.authorizeService.getOperateFirm(str1);
		if (!str2.equals(""))
			try {
				String str3 = "select a.commodityid,a.bs_flag,a.holdqty,a.evenprice,b.firmID,b.mincleardeposit,(FN_F_GetRealFunds(a.FirmID, 0) + b.VirtualFunds + b.MaxOverdraft) usefulFunds,c.lastbalance leftoverPrice from T_Firmholdsum a, T_Firm b, f_firmfunds c where a.firmid = b.firmid and a.firmid = c.firmid and (((c.lastbalance + b.MaxOverdraft) < b.mincleardeposit) or ((FN_F_GetRealFunds(a.FirmID, 0) + b.VirtualFunds + b.MaxOverdraft) < b.mincleardeposit)) and a.holdqty > 0 and a.firmID in ("
						+ str2 + ") order by a.firmID";
				List localList = getService().getListBySql(str3);
				PageRequest localPageRequest = super.getPageRequest(this.request);
				Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
				this.request.setAttribute("pageInfo", localPage);
			} catch (Exception localException) {
				this.logger.error("查询出错：" + localException.getMessage());
				this.request.setAttribute("prompt", localException.getMessage());
			}
		return "success";
	}

	public String forceCloseInfo() throws Exception {
		logger.debug("------------forceCloseInfo  查看单条强行平仓详细信息--------------");
		String s = request.getParameter("CommodityID");
		String s1 = request.getParameter("FirmID");
		String s2 = request.getParameter("BS_Flag");
		String s3 = request.getParameter("HoldQty");
		String s4 = request.getParameter("EvenPrice");
		String s5 = (new StringBuilder())
				.append("select b.*, (FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) usefulFunds from T_Firmholdsum a ,T_Firm b where a.firmid=b.firmid and (FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) <b.mincleardeposit and b.firmID = '")
				.append(s1).append("'").toString();
		List list = getService().getListBySql(s5);
		String s6 = "";
		String s7 = "";
		String s8 = "";
		String s9 = "";
		String s10 = "";
		String s11 = "";
		String s12 = "";
		String s13 = "";
		if (list != null && list.size() > 0) {
			Map map = (Map) list.get(0);
			if (map.get("RUNTIMEFL") != null)
				s6 = map.get("RUNTIMEFL").toString();
			if (map.get("CLEARFL") != null)
				s7 = map.get("CLEARFL").toString();
			if (map.get("RUNTIMEMARGIN") != null)
				s8 = map.get("RUNTIMEMARGIN").toString();
			if (map.get("CLEARMARGIN") != null)
				s9 = map.get("CLEARMARGIN").toString();
			if (map.get("VIRTUALFUNDS") != null)
				s10 = map.get("VIRTUALFUNDS").toString();
			if (map.get("MAXOVERDRAFT") != null)
				s11 = map.get("MAXOVERDRAFT").toString();
			if (map.get("USEFULFUNDS") != null)
				s12 = map.get("USEFULFUNDS").toString();
			if (map.get("MINCLEARDEPOSIT") != null)
				s13 = map.get("MINCLEARDEPOSIT").toString();
		}
		String s14 = (String) request.getSession().getAttribute("type");
		String s15 = "";
		if (s14 != null && !"".equals(s14))
			s15 = s14;
		String s16 = "";
		List list1 = getService().getListBySql("select status from T_SystemStatus");
		String s17 = "";
		if (list1 != null && list1.size() > 0)
			s17 = ((Map) list1.get(0)).get("STATUS").toString();
		String s18 = "0";
		if ("3".equals(s17))
			s18 = (new StringBuilder()).append("select price price from T_Quotation where commodityID = '").append(s).append("'").toString();
		else
			s18 = (new StringBuilder()).append("select yesterBalancePrice price from T_Quotation where commodityID = '").append(s).append("'")
					.toString();
		List list2 = getService().getListBySql(s18);
		if (list2 != null && list2.size() > 0)
			s16 = ((Map) list2.get(0)).get("PRICE").toString();
		request.setAttribute("commodityID", s);
		request.setAttribute("firmID", s1);
		request.setAttribute("BS_Flag", s2);
		request.setAttribute("HoldQty", s3);
		request.setAttribute("EvenPrice", s4);
		request.setAttribute("runtimeFL", s6);
		request.setAttribute("clearFL", s7);
		request.setAttribute("runtimeMargin", s8);
		request.setAttribute("clearMargin", s9);
		request.setAttribute("virtualFunds", s10);
		request.setAttribute("maxOverdraft", s11);
		request.setAttribute("minClearDeposit", s13);
		request.setAttribute("usefulFunds", s12);
		request.setAttribute("relType", s15);
		request.setAttribute("lastPrice", s16);
		return "success";
	}

	public String forceClose() throws Exception {
		logger.debug("------------forceClose 在线强行平仓--------------");
		Orders orders = (Orders) entity;
		if (orders.getCloseFlag() == null)
			orders.setCloseFlag(Short.valueOf((short) 1));
		try {
			if ((Long) request.getSession().getAttribute("sessionID") == null)
				throw new Exception("未登录下单服务器！");
			Long long1 = (Long) request.getSession().getAttribute("sessionID");
			String s = tradeRMI.getConsignerID(long1.longValue());
			if (s == null)
				throw new Exception("未登录下单服务器！");
			String s1 = (String) request.getSession().getAttribute("type");
			if (s1 != null && !"".equals(s1) && "0".equals(s1)) {
				writeOperateLog(1510, "代为委托员不能进行强平操作！", 0, "");
				throw new Exception("代为委托员不能进行强平操作！");
			}
			String s2 = orders.getCustomerId();
			String s3 = "";
			String s4 = (new StringBuilder()).append("select count(*) count from T_Customer where customerid = '").append(s2).append("'").toString();
			List list = getService().getListBySql(s4);
			boolean flag = false;
			if (list != null && list.size() > 0) {
				int i = Integer.parseInt(((Map) list.get(0)).get("COUNT").toString());
				if (i > 0) {
					String s5 = (new StringBuilder())
							.append("select f.firmid firmId from T_Firm f, T_Customer c where c.firmid = f.firmid and c.customerid = '").append(s2)
							.append("'").toString();
					List list1 = getService().getListBySql(s5);
					if (list1 != null && list1.size() > 0) {
						s3 = ((Map) list1.get(0)).get("FIRMID").toString();
					} else {
						writeOperateLog(1510, "此客户代码不存在！", 0, "");
						throw new RuntimeException("此客户代码不存在！");
					}
				} else {
					writeOperateLog(1510, "此客户代码不存在！", 0, "");
					throw new RuntimeException("此客户代码不存在！");
				}
			} else {
				writeOperateLog(1510, "此客户代码不存在！", 0, "");
				throw new RuntimeException("此客户代码不存在！");
			}
			String s6 = orders.getFirmId();
			if (s3 != null && !"".equals(s3) && s6 != null && !"".equals(s6) && s3.equals(s6)) {
				Order order1 = new Order();
				String s7 = orders.getBS_Flag().toString();
				String s8 = "";
				if ("1".equals(s7))
					s8 = "1";
				if ("2".equals(s7))
					s8 = "2";
				String s9 = orders.getCommodityId();
				order1.setCustomerID(orders.getCustomerId());
				order1.setCommodityID(s9);
				if (s8 != null && !"".equals(s8))
					order1.setBuyOrSell(Short.valueOf(Short.parseShort(s8)));
				order1.setOrderType(orders.getOrderType());
				order1.setPrice(orders.getPrice());
				order1.setQuantity(orders.getQuantity());
				order1.setConsignerID(s);
				order1.setCloseMode(orders.getCloseMode());
				order1.setSpecPrice(orders.getSpecPrice());
				order1.setSpecTime(orders.getTimeFlag());
				order1.setCloseFlag(orders.getCloseFlag());
				int j = submitOrder(long1, order1);
				if (j == 0)
					writeOperateLog(1510, "操作成功", 1, "");
			} else {
				request.setAttribute("prompt", (new StringBuilder()).append("此交易商代码不属于此交易商").append(s3).append("！").toString());
				writeOperateLog(1510, (new StringBuilder()).append("此交易商代码不属于此交易商").append(s3).append("！").toString(), 0, "");
			}
		} catch (Exception exception) {
			logger.error((new StringBuilder()).append("===>save err：").append(exception.getMessage()).toString());
			request.setAttribute("prompt", exception.getMessage());
		}
		return "success";
	}

	public String passwordForward() throws Exception {
		this.logger.debug("------------passwordForward 代为委托员密码修改框架跳转--------------");
		System.out.println(getMkName());
		return "success";
	}

	public String updatePasswordForward() throws Exception {
		this.logger.debug("------------updatePasswordForward 修改代为委托员密码跳转--------------");
		try {
			long l = ((Long) this.request.getSession().getAttribute("sessionID")).longValue();
			String str = this.tradeRMI.getConsignerID(l);
			this.request.setAttribute("consignerID", str);
		} catch (Exception localException) {
			this.logger.error("==err:" + localException);
			localException.printStackTrace();
			this.request.setAttribute("prompt", localException.getMessage());
		}
		return "success";
	}

	public String updatePassword() throws Exception {
		this.logger.debug("------------updatePassword 修改代为委托员密码--------------");
		try {
			String str1 = this.request.getParameter("consignerId");
			String str2 = this.request.getParameter("oldpwd");
			String str3 = this.request.getParameter("newpwd");
			String str4 = "select Password from m_Agenttrader where AgenttraderID = '" + str1 + "'";
			List localList = getService().getListBySql(str4);
			String str5 = "";
			if ((localList != null) && (localList.size() > 0))
				str5 = ((Map) localList.get(0)).get("PASSWORD").toString();
			str2 = StringUtil.encodePassword(str1 + str2, "MD5");
			if (str2.equals(str5)) {
				str3 = StringUtil.encodePassword(str1 + str3, "MD5");
				String str6 = "update m_Agenttrader set Password = '" + str3 + "' where AgenttraderID = '" + str1 + "'";
				getService().executeUpdateBySql(str6);
				this.request.setAttribute("prompt", "修改成功");
				writeOperateLog(1510, "修改成功！", 1, "");
			} else {
				this.request.setAttribute("prompt", "原口令输入错误");
				writeOperateLog(1510, "原口令输入错误", 0, "");
			}
		} catch (Exception localException) {
			this.logger.error("==err:" + localException);
			localException.printStackTrace();
			this.request.setAttribute("prompt", localException.getMessage());
		}
		return "success";
	}

	private int submitOrder(Long paramLong, Order paramOrder) throws Exception {
		int i = this.tradeRMI.consignerOrder(paramLong.longValue(), paramOrder);
		switch (i) {
		case 0:
			break;
		case 1:
			this.request.setAttribute("prompt", "校验异常！");
			break;
		case 2:
			this.request.setAttribute("prompt", "市场未启用！");
			break;
		case 3:
			this.request.setAttribute("prompt", "不是交易时间！");
			break;
		case 4:
			this.request.setAttribute("prompt", "不是代为委托员交易时间！");
			break;
		case 5:
			this.request.setAttribute("prompt", "交易员和代为委托员不能同时存在！");
			break;
		case 10:
			this.request.setAttribute("prompt", "商品处于禁止交易状态！");
			break;
		case 11:
			this.request.setAttribute("prompt", "商品不属于当前交易节！");
			break;
		case 12:
			this.request.setAttribute("prompt", "委托价格超出涨幅上限！");
			break;
		case 13:
			this.request.setAttribute("prompt", "委托价格低于跌幅下限！");
			break;
		case 14:
			this.request.setAttribute("prompt", "委托价格不在此商品最小价位变动范围内！");
			break;
		case 15:
			this.request.setAttribute("prompt", "不存在此商品！");
			break;
		case 16:
			this.request.setAttribute("prompt", "委托数量不在此商品最小变动数量范围内！");
			break;
		case 30:
			this.request.setAttribute("prompt", "此交易员不存在！");
			break;
		case 31:
			this.request.setAttribute("prompt", "此交易员没有操作该交易商的权限！");
			break;
		case 32:
			this.request.setAttribute("prompt", "此交易商不存在！");
			break;
		case 33:
			this.request.setAttribute("prompt", "此交易商为禁止交易状态！");
			break;
		case 34:
			this.request.setAttribute("prompt", "此交易商不存在！");
			break;
		case 35:
			this.request.setAttribute("prompt", "此交易商为禁止交易状态！");
			break;
		case 37:
			this.request.setAttribute("prompt", "此代为委托员没有操作该交易商的权限！");
			break;
		case 38:
			this.request.setAttribute("prompt", "此代为委托员不存在！");
			break;
		case 40:
			this.request.setAttribute("prompt", "计算交易保证金错误！");
			break;
		case 41:
			this.request.setAttribute("prompt", "计算交易手续费错误！");
			break;
		case 42:
			this.request.setAttribute("prompt", "此委托已成交或已撤单！");
			break;
		case 199:
			this.request.setAttribute("prompt", "通信故障！");
			break;
		case 200:
			this.request.setAttribute("prompt", "代码异常而失败！");
			break;
		case -1:
			this.request.setAttribute("prompt", "资金余额不足！");
			break;
		case -2:
			this.request.setAttribute("prompt", "超过交易商对此商品的最大订货量！");
			break;
		case -3:
			this.request.setAttribute("prompt", "超过交易商总的最大订货量！");
			break;
		case -4:
			this.request.setAttribute("prompt", "超过品种最大订货量！");
			break;
		case -5:
			this.request.setAttribute("prompt", "超过商品最大订货量！");
			break;
		case -6:
			this.request.setAttribute("prompt", "超过交易商对此商品的最大净订货量！");
			break;
		case -7:
			this.request.setAttribute("prompt", "超过单笔最大委托量！");
			break;
		case -11:
			this.request.setAttribute("prompt", "此委托已处于正在撤单状态！");
			break;
		case -12:
			this.request.setAttribute("prompt", "此委托已全部成交了！");
			break;
		case -13:
			this.request.setAttribute("prompt", "此委托已完成撤单了！");
			break;
		case -21:
			this.request.setAttribute("prompt", "订货不足！");
			break;
		case -22:
			this.request.setAttribute("prompt", "指定仓不足！");
			break;
		case -99:
			this.request.setAttribute("prompt", "执行存储时未找到相关数据！");
			break;
		case -100:
			this.request.setAttribute("prompt", "执行存储失败！");
			break;
		case -204:
			this.request.setAttribute("prompt", "交易服务器已关闭！");
			break;
		case -206:
			this.request.setAttribute("prompt", "委托信息不能为空！");
			break;
		default:
			this.request.setAttribute("prompt", "委托失败！");
		}
		return i;
	}

	public String detailForceCloseForward() throws Exception {
		this.logger.debug("------------detailForceCloseForward 强行转让过期持仓跳转--------------");
		return "success";
	}

	public String searchDetailForceClose() throws Exception {
		this.logger.debug("------------searchForceClose 查看强行平仓过期持仓记录--------------");
		if ((Long) this.request.getSession().getAttribute("sessionID") == null)
			throw new Exception("未登录下单服务器！");
		long l = ((Long) this.request.getSession().getAttribute("sessionID")).longValue();
		String str1 = this.tradeRMI.getConsignerID(l);
		if (str1 == null)
			throw new Exception("未登录下单服务器！");
		String str2 = this.authorizeService.getOperateFirm(str1);
		if (!str2.equals(""))
			try {
				List localList = this.authorizeService.holdPositionsList(str2);
				PageRequest localPageRequest = super.getPageRequest(this.request);
				Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
				this.request.setAttribute("pageInfo", localPage);
			} catch (Exception localException) {
				this.logger.error("查询出错：" + localException.getMessage());
				this.request.setAttribute("prompt", localException.getMessage());
			}
		return "success";
	}

	public String viewDetailForceClose() throws Exception {
		String str = (String) this.request.getSession().getAttribute("type");
		if ((str != null) && (!"".equals(str)))
			this.request.setAttribute("type", str);
		PageRequest localPageRequest = getPageRequest(this.request);
		AbstractService localStandardService = localPageRequest.isQueryDB() ? (AbstractService) getQueryService() : (AbstractService) getService();
		this.entity = localStandardService.get(this.entity);
		OverdueHoldPosition localOverdueHoldPosition = (OverdueHoldPosition) this.entity;
		long l1 = this.authorizeService.getHoldQty(localOverdueHoldPosition);
		long l2 = l1 <= localOverdueHoldPosition.getMaycloseQty().longValue() ? l1 : localOverdueHoldPosition.getMaycloseQty().longValue();
		CommodityF localCommodityF1 = new CommodityF();
		localCommodityF1.setCommodityId(localOverdueHoldPosition.getCommodityID());
		CommodityF localCommodityF2 = (CommodityF) getService().get(localCommodityF1);
		double d1 = 0.0D;
		int i = Integer.parseInt(localCommodityF2.getSpreadAlgr().toString());
		double d2 = localCommodityF2.getSpreadUpLmt().doubleValue();
		double d3 = localCommodityF2.getSpreadDownLmt().doubleValue();
		double d4 = localCommodityF2.getLastPrice().doubleValue();
		if (i == 1) {
			d2 = Math.round(Arith.mul(d4, d2));
			d2 = Arith.add(d4, d2);
			d3 = Math.round(Arith.mul(d4, d3));
			d3 = Arith.sub(d4, d3);
		} else if (i == 2) {
			d2 = Arith.add(d4, d2);
			d3 = Arith.sub(d4, d3);
		}
		if (localOverdueHoldPosition.getBSFlag().longValue() == 1L)
			d1 = d2;
		else if (localOverdueHoldPosition.getBSFlag().longValue() == 2L)
			d1 = d3;
		this.request.setAttribute("holdQty", Long.valueOf(l1));
		this.request.setAttribute("closeQty", Long.valueOf(l2));
		this.request.setAttribute("price", Double.valueOf(d1));
		return "success";
	}

	public String detailForceClose() {
		Orders orders = (Orders) entity;
		if (orders.getCloseFlag() == null)
			orders.setCloseFlag(Short.valueOf((short) 1));
		try {
			if ((Long) request.getSession().getAttribute("sessionID") == null)
				throw new Exception("未登录下单服务器！");
			Long long1 = (Long) request.getSession().getAttribute("sessionID");
			String s = tradeRMI.getConsignerID(long1.longValue());
			if (s == null)
				throw new Exception("未登录下单服务器！");
			String s1 = (String) request.getSession().getAttribute("type");
			if (s1 != null && !"".equals(s1) && "0".equals(s1)) {
				writeOperateLog(1510, "代为委托员不能进行强平操作！", 0, "");
				throw new Exception("代为委托员不能进行强平操作！");
			}
			String s2 = orders.getCustomerId();
			String s3 = "";
			String s4 = (new StringBuilder()).append("select count(*) count from T_Customer where customerid = '").append(s2).append("'").toString();
			List list = getService().getListBySql(s4);
			boolean flag = false;
			if (list != null && list.size() > 0) {
				int i = Integer.parseInt(((Map) list.get(0)).get("COUNT").toString());
				if (i > 0) {
					String s5 = (new StringBuilder())
							.append("select f.firmid firmId from T_Firm f, T_Customer c where c.firmid = f.firmid and c.customerid = '").append(s2)
							.append("'").toString();
					List list1 = getService().getListBySql(s5);
					if (list1 != null && list1.size() > 0) {
						s3 = ((Map) list1.get(0)).get("FIRMID").toString();
					} else {
						writeOperateLog(1510, "此客户代码不存在！", 0, "");
						throw new RuntimeException("此客户代码不存在！");
					}
				} else {
					writeOperateLog(1510, "此客户代码不存在！", 0, "");
					throw new RuntimeException("此客户代码不存在！");
				}
			} else {
				writeOperateLog(1510, "此客户代码不存在！", 0, "");
				throw new RuntimeException("此客户代码不存在！");
			}
			String s6 = orders.getFirmId();
			if (s3 != null && !"".equals(s3) && s6 != null && !"".equals(s6) && s3.equals(s6)) {
				Order order1 = new Order();
				String s7 = orders.getBS_Flag().toString();
				String s8 = "";
				if ("1".equals(s7))
					s8 = "2";
				if ("2".equals(s7))
					s8 = "1";
				order1.setFirmID(s6);
				String s9 = orders.getCommodityId();
				order1.setCustomerID(orders.getCustomerId());
				order1.setCommodityID(s9);
				if (s8 != null && !"".equals(s8))
					order1.setBuyOrSell(Short.valueOf(Short.parseShort(s8)));
				order1.setOrderType(orders.getOrderType());
				order1.setPrice(orders.getPrice());
				order1.setQuantity(orders.getQuantity());
				order1.setConsignerID(s);
				order1.setCloseMode(orders.getCloseMode());
				order1.setSpecPrice(orders.getSpecPrice());
				order1.setSpecTime(orders.getTimeFlag());
				order1.setCloseFlag(orders.getCloseFlag());
				int j = authorizeService.insertCloseOrder(long1, order1);
				if (j == 0)
					addReturnValue(1, 0x1d463L);
				else if (j == -500) {
					addReturnValue(1, 0x24e3fL, new Object[] { "可平仓数量不足，请重新操作！" });
				} else {
					String s10 = authorizeService.resultOrder(j);
					addReturnValue(0, 0x24e3fL, new Object[] { s10 });
				}
			} else {
				addReturnValue(0, 0x24e3fL, new Object[] { (new StringBuilder()).append("此交易商代码不属于此交易商").append(s3).append("！").toString() });
				writeOperateLog(1510, (new StringBuilder()).append("此交易商代码不属于此交易商").append(s3).append("！").toString(), 0, "");
			}
		} catch (Exception exception) {
			logger.error((new StringBuilder()).append("===>save err：").append(exception.getMessage()).toString());
		}
		return "success";
	}

	public AuthorizeService getAuthorizeService() {
		return this.authorizeService;
	}
}