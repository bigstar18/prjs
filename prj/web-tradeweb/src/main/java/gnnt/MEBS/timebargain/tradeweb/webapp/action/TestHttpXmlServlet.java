package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.OrderReturnValue;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.util.SysConfig;
import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.service.CustomerManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.util.Arith;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import gnnt.MEBS.timebargain.tradeweb.util.StringUtil;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class TestHttpXmlServlet extends HttpServlet {
	private static final long serialVersionUID = 3906934490856239410L;
	private final Log log = LogFactory.getLog(TestHttpXmlServlet.class);
	private TradeRMI tradeRMI = null;
	private String strTradeRMI = null;
	private static ApplicationContext ctx = null;
	private TradeService taken = null;
	private ServerRMI serverRMI = null;
	private String strServerRMI = null;
	public String tradeDay = null;

	public void init() throws ServletException {
		Map localMap = SysConfig.getRMIConfig((DataSource) getBean("dataSource"));
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/TradeRMI");
		this.strTradeRMI = localStringBuffer.toString();
		try {
			this.tradeRMI = ((TradeRMI) Naming.lookup(this.strTradeRMI));
			this.log.debug("rmi->tradeRMI");
		} catch (MalformedURLException localMalformedURLException) {
			localMalformedURLException.printStackTrace();
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		} catch (NotBoundException localNotBoundException) {
			localNotBoundException.printStackTrace();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException {
		execute(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException {
		execute(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException {
		ResponseResult localResponseResult = new ResponseResult();
		Document localDocument = null;
		String str1 = null;
		String str2 = null;
		try {
			str1 = "order";
		} catch (Exception localException) {
			this.log.error("Servlet出错:" + localException);
			localResponseResult.setRetCode(-203);
			localResponseResult.setMessage("未知异常！");
			localException.printStackTrace();
		}
		if (str1 == null) {
			str1 = "";
		}
		localResponseResult.setName(str1);
		if (str1.equals("logon")) {
			logon(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("logoff")) {
			logoff(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("change_password")) {
			change_password(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("commodity_query")) {
			commodity_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("order")) {
			order(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult);
		} else if (str1.equals("order_wd")) {
			order_wd(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("tradequery")) {
			tradequery(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("firm_info")) {
			firm_info(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("sys_time_query")) {
			sys_time_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("my_order_query")) {
			my_order_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("my_weekorder_query")) {
			my_weekorder_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("holding_query")) {
			holding_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("market_query")) {
			market_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		} else if (str1.equals("commodity_data_query")) {
			commodity_data_query(paramHttpServletRequest, paramHttpServletResponse, localDocument, localResponseResult);
		}
	}

	private void logon(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'logon' method");
		}
		long l1 = 0L;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String str3 = getValueByName(paramDocument, "PASSWORD");
			String str4 = getValueByName(paramDocument, "REGISTER_WORD");
			String str5 = paramHttpServletRequest.getRemoteAddr();
			gnnt.MEBS.timebargain.server.model.Trader localTrader = new gnnt.MEBS.timebargain.server.model.Trader();
			localTrader.setTraderID(str2);
			localTrader.setPassword(str3);
			localTrader.setKeyCode(str4);
			localTrader.setLogonIP(str5);
			TraderLogonInfo localTraderLogonInfo = this.tradeRMI.logon(localTrader);
			long l2 = localTraderLogonInfo.getSessionID();
			if (l2 == -1L) {
				l1 = -1L;
				str1 = "交易代码不存在！";
			} else if (l2 == -2L) {
				l1 = -2L;
				str1 = "交易密码输入有误！";
			} else if (l2 == -3L) {
				l1 = -3L;
				str1 = "禁止登陆！";
			} else if (l2 == -4L) {
				l1 = -4L;
				str1 = "Key盘验证错误！";
			} else if (l2 == -204L) {
				l1 = -204L;
				str1 = "下单服务器已关闭！";
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Privilege localPrivilege = localOrdersManager.getradePrivilege(localTraderLogonInfo);
				this.log.debug("PrivilegeObj:" + localPrivilege);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
				l1 = l2;
			}
		} catch (Exception localException) {
			this.log.error("logon error:" + localException);
			l1 = -203L;
			str1 = "未知异常！";
		}
		paramResponseResult.setLongRetCode(l1);
		paramResponseResult.setMessage(str1);
		renderXML(paramHttpServletResponse, ResponseXml.logon(paramResponseResult));
	}

	private void logoff(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'logoff' method");
		}
		int i = 0;
		String str1 = "";
		try {
			String str2 = "pc";
			String str3 = getValueByName(paramDocument, "TRADER_ID");
			long l = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!checkUserExist(str3, l)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				this.tradeRMI.logoff(l, str2);
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("logoff remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("logoff error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		renderXML(paramHttpServletResponse, ResponseXml.responseXml(paramResponseResult.getName(), i, str1));
	}

	private void change_password(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'change_password' method");
		}
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String str3 = getValueByName(paramDocument, "OLD_PASSWORD");
			String str4 = getValueByName(paramDocument, "NEW_PASSWORD");
			long l = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!checkUserExist(str2, l)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				CustomerManager localCustomerManager = (CustomerManager) getBean("customerManager");
				gnnt.MEBS.timebargain.tradeweb.model.Trader localTrader = new gnnt.MEBS.timebargain.tradeweb.model.Trader();
				localTrader = localCustomerManager.getTraderById(str2);
				if (!localTrader.getPassword().equals(StringUtil.encodePassword(str3, "MD5"))) {
					i = -2;
					str1 = "旧密码不正确！";
				} else {
					localTrader.setTraderID(str2);
					localTrader.setPassword(StringUtil.encodePassword(str4, "MD5"));
					localCustomerManager.updateTraderPassword(localTrader);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("change_password remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("change_password error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		renderXML(paramHttpServletResponse, ResponseXml.responseXml(paramResponseResult.getName(), i, str1));
	}

	private void commodity_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'commodity_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String localObject = getValueByName(paramDocument, "COMMODITY_ID");
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l7)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				localOrders.setCommodityID((String) localObject);
				l3 = System.currentTimeMillis();
				List localList = localOrdersManager.commodity_query(localOrders);
				l4 = System.currentTimeMillis();
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("commodity_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("commodity_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.commodity_query(paramResponseResult, getSessionBean(paramHttpServletRequest));
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
		this.log.debug("commodity_query all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private void order(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
		}
		try {
			paramResponseResult = submitOrder(0L, null, null);
		} catch (Exception localException) {
			this.log.error("order error:" + localException);
		}
		renderXML(paramHttpServletResponse,
				ResponseXml.responseXml(paramResponseResult.getName(), paramResponseResult.getRetCode(), paramResponseResult.getMessage()));
	}

	private void order_wd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'order_wd' method");
		}
		try {
			String str1 = getValueByName(paramDocument, "TRADER_ID");
			Long localLong = new Long(getValueByName(paramDocument, "ORDER_NO"));
			long l = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str1, l)) {
				paramResponseResult.setRetCode(-201);
				paramResponseResult.setMessage("身份不合法，请重新登陆！");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				localOrders.setA_OrderNo(localLong);
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				List localList = localOrdersManager.my_order_query(localOrders, localPrivilege);
				Map localMap = (Map) localList.get(0);
				String str2 = (String) localMap.get("TraderID");
				Order localOrder;
				if (str1 != null) {
					localOrder = new Order();
					localOrder.setCommodityID((String) localMap.get("COMMODITY_ID"));
					localOrder.setCustomerID((String) localMap.get("CustomerID"));
					localOrder.setTraderID(str1);
					localOrder.setOrderType(Short.valueOf("4"));
					localOrder.setWithdrawID(localLong);
					paramResponseResult = submitOrder(l, localOrder, paramResponseResult);
				} else {
					localOrder = new Order();
					localOrder.setCommodityID((String) localMap.get("COMMODITY_ID"));
					localOrder.setCustomerID((String) localMap.get("CustomerID"));
					localOrder.setTraderID(str1);
					localOrder.setOrderType(Short.valueOf("4"));
					localOrder.setWithdrawID(localLong);
					paramResponseResult = submitOrder(l, localOrder, paramResponseResult);
				}
			}
		} catch (Exception localException) {
			this.log.error("order_wd error:" + localException.getMessage());
			paramResponseResult.setRetCode(-203);
			paramResponseResult.setMessage("未知异常！");
		}
		renderXML(paramHttpServletResponse,
				ResponseXml.responseXml(paramResponseResult.getName(), paramResponseResult.getRetCode(), paramResponseResult.getMessage()));
	}

	private void tradequery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'tradequery' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String localObject1 = getValueByName(paramDocument, "LAST_TRADE_ID");
			Long localLong = null;
			if ((localObject1 != null) && (!((String) localObject1).equals(""))) {
				localLong = Long.valueOf((String) localObject1);
			}
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l7)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				String str4 = localPrivilege.getFirmId();
				if (this.taken == null) {
					OrdersManager localObject2 = (OrdersManager) getBean("ordersManager");
				}
				Object localObject2 = (LinkedList) this.taken.getTradeMap().get(str4);
				if ((localObject2 == null) || (((LinkedList) localObject2).size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					if (localLong == null) {
						localLong = Long.valueOf(0L);
					}
					this.log.debug("===>last_trade_id = " + localLong);
					ArrayList localArrayList = new ArrayList();
					for (int j = ((LinkedList) localObject2).size() - 1; j >= 0; j--) {
						Trade localTrade = (Trade) ((LinkedList) localObject2).get(j);
						this.log.debug("===>trade.getA_OrderNo()" + localTrade.getA_TradeNo());
						if (localLong.longValue() >= localTrade.getA_TradeNo().longValue()) {
							break;
						}
						localArrayList.add(localTrade);
					}
					this.log.debug("===>clint list size =" + localArrayList.size());
					if ((localArrayList == null) || (localArrayList.size() == 0)) {
						i = -202;
						str1 = "记录未找到！";
					}
					paramResponseResult.setResultList(localArrayList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("tradequery remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("tradequery error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.tradequery(paramResponseResult);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject1 = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject1).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject1).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject1).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject1).append("</GNNT>");
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject1).toString());
		this.log.debug("tradequery all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private void firm_info(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'firm_info' method");
		}
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			long l = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				List localList = localOrdersManager.firm_info(localPrivilege);
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("firm_info remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("firm_info error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		renderXML(paramHttpServletResponse, ResponseXml.firm_info(paramResponseResult));
	}

	private void sys_time_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'sys_time_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String str3 = getValueByName(paramDocument, "FIRM_ID");
			String localObject = getValueByName(paramDocument, "LAST_ID");
			long l7 = 0L;
			if ((localObject != null) && (!((String) localObject).equals(""))) {
				l7 = Long.parseLong((String) localObject);
			}
			long l8 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l8)) {
				paramResponseResult.setRetCode(-201);
				paramResponseResult.setMessage("身份不合法，请重新登陆！");
			} else {
				l3 = System.currentTimeMillis();
				ArrayList localArrayList = new ArrayList();
				HashMap localHashMap = new HashMap();
				localHashMap.put("CUR_TIME", DateUtil.getCurTime());
				localHashMap.put("CUR_DATE", DateUtil.getCurDate());
				localHashMap.put("TV_USEC", String.valueOf(System.currentTimeMillis()));
				localArrayList.add(localHashMap);
				l4 = System.currentTimeMillis();
				paramResponseResult.setResultList(localArrayList);
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("sys_time_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("sys_time_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		int j = 0;
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.sys_time_query(paramResponseResult, this.tradeDay, true, j);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		this.log.debug("sys_time_query with time:" + ((StringBuffer) localObject).toString());
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
		this.log.debug("sys_time_query all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private void my_order_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'my_order_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "BUY_SELL");
			Short localObject = str2.equals("") ? null : new Short(str2);
			String str4 = getValueByName(paramDocument, "ORDER_NO");
			Long localLong = str4.equals("") ? null : new Long(str4);
			String str5 = getValueByName(paramDocument, "TRADER_ID");
			String str6 = getValueByName(paramDocument, "COMMODITY_ID");
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			String str7 = getValueByName(paramDocument, "UT");
			int j = (str7 == null) || ("0".equals(str7)) ? 1 : 0;
			String str8 = j != 0 ? "queryAll" : DateUtil.Mills2Time(Long.parseLong(str7));
			Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
			if (!isLogon(paramHttpServletRequest, str5, l7)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				if (PrivilegeController.checkSuperTrader(localPrivilege)) {
					localOrders.setSuperTrader("A");
				}
				localOrders.setBS_Flag((Short) localObject);
				localOrders.setA_OrderNo(localLong);
				localOrders.setTraderID(str5);
				localOrders.setUpdateTime(str8);
				localOrders.setCommodityID(str6);
				List localList = localOrdersManager.my_order_query(localOrders, localPrivilege);
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("my_order_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("my_order_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.my_order_query(paramResponseResult, 0L);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
		this.log.debug("my_order_query all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private void my_weekorder_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'my_weekorder_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "BUY_SELL");
			Short localObject = str2.equals("") ? null : new Short(str2);
			String str4 = getValueByName(paramDocument, "ORDER_NO");
			Long localLong = str4.equals("") ? null : new Long(str4);
			String str5 = getValueByName(paramDocument, "TRADER_ID");
			String str6 = getValueByName(paramDocument, "COMMODITY_ID");
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			String str7 = getValueByName(paramDocument, "UT");
			int j = (str7 == null) || ("0".equals(str7)) ? 1 : 0;
			String str8 = j != 0 ? "queryAll" : DateUtil.Mills2Time(Long.parseLong(str7));
			Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
			if (!isLogon(paramHttpServletRequest, str5, l7)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				localOrders.setBS_Flag((Short) localObject);
				localOrders.setA_OrderNo(localLong);
				localOrders.setTraderID(str5);
				localOrders.setCommodityID(str6);
				localOrders.setUpdateTime(str8);
				SortCondition localSortCondition = new SortCondition();
				if (PrivilegeController.checkSuperTrader(localPrivilege)) {
					localOrders.setSuperTrader("A");
				}
				List localList = localOrdersManager.my_weekorder_query(localOrders, localPrivilege, localSortCondition);
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("my_weekorder_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("my_weekorder_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.my_order_query(paramResponseResult, 0L);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
		this.log.debug("my_order_query all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private void holding_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'holding_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String localObject = getValueByName(paramDocument, "COMMODITY_ID");
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l7)) {
				i = -201;
				str1 = "身份不合法，请重新登陆！";
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				localOrders.setTraderID(str2);
				localOrders.setCommodityID((String) localObject);
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				SortCondition localSortCondition = new SortCondition();
				List localList = localOrdersManager.holding_query(localOrders, localPrivilege, localSortCondition);
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("holding_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("holding_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.holding_query(paramResponseResult);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
		this.log.debug("holding_query all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private void market_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument,
			ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'market_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String localObject = getValueByName(paramDocument, "MARKET_ID");
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l7)) {
				paramResponseResult.setRetCode(-201);
				paramResponseResult.setMessage("身份不合法，请重新登陆！");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Market localMarket = new Market();
				localMarket.setMarketCode((String) localObject);
				l3 = System.currentTimeMillis();
				List localList = localOrdersManager.market_query(localMarket);
				l4 = System.currentTimeMillis();
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("market_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("market_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.market_query(paramResponseResult);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		this.log.debug("market_query with time:" + ((StringBuffer) localObject).toString());
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
	}

	private void commodity_data_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
			Document paramDocument, ResponseResult paramResponseResult) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'commodity_data_query' method");
		}
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l6 = 0L;
		l1 = System.currentTimeMillis();
		int i = 0;
		String str1 = "";
		try {
			String str2 = getValueByName(paramDocument, "TRADER_ID");
			String localObject = getValueByName(paramDocument, "COMMODITY_ID");
			long l7 = Long.parseLong(getValueByName(paramDocument, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l7)) {
				paramResponseResult.setRetCode(-201);
				paramResponseResult.setMessage("身份不合法，请重新登陆！");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				l3 = System.currentTimeMillis();
				ArrayList localArrayList = new ArrayList();
				Map localMap1 = localOrdersManager.getQuotationMap();
				this.log.debug("test map:" + localMap1);
				Iterator localIterator = localMap1.keySet().iterator();
				while (localIterator.hasNext()) {
					String str4 = (String) localIterator.next();
					Map localMap2 = (Map) localMap1.get(str4);
					localArrayList.add(localMap2);
				}
				this.log.debug("test map list:" + localArrayList);
				l4 = System.currentTimeMillis();
				if ((localArrayList == null) || (localArrayList.size() == 0)) {
					i = -202;
					str1 = "记录未找到！";
				} else {
					paramResponseResult.setResultList(localArrayList);
				}
			}
		} catch (RemoteException localRemoteException) {
			this.log.error("commodity_data_query remoteerror:" + localRemoteException);
			i = -204;
			str1 = "下单服务器已关闭！";
		} catch (Exception localException) {
			this.log.error("commodity_data_query error:" + localException);
			i = -203;
			str1 = "未知异常！";
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		l5 = System.currentTimeMillis();
		String str3 = ResponseXml.commodity_data_query(paramResponseResult);
		l6 = System.currentTimeMillis();
		l2 = System.currentTimeMillis();
		Object localObject = new StringBuffer(str3.substring(0, str3.length() - 7));
		((StringBuffer) localObject).append("<query_time>").append(Arith.div((float) (l4 - l3), 1000.0F)).append("秒").append("</query_time>");
		((StringBuffer) localObject).append("<parse_time>").append(Arith.div((float) (l6 - l5), 1000.0F)).append("秒").append("</parse_time>");
		((StringBuffer) localObject).append("<request_time>").append(Arith.div((float) (l2 - l1), 1000.0F)).append("秒").append("</request_time>");
		((StringBuffer) localObject).append("</GNNT>");
		renderXML(paramHttpServletResponse, ((StringBuffer) localObject).toString());
		this.log.debug("commodity_data_query all time:" + Arith.div((float) (System.currentTimeMillis() - l1), 1000.0F) + "秒");
	}

	private ResponseResult submitOrder(long paramLong, Order paramOrder, ResponseResult paramResponseResult) throws Exception {
		String str1 = "pc";
		OrderReturnValue localOrderReturnValue = this.tradeRMI.order(paramLong, paramOrder, str1);
		String str2 = "";
		switch (localOrderReturnValue.getRetCode()) {
		case 0:
			break;
		case 1:
			str2 = "身份不合法！";
			break;
		case 2:
			str2 = "市场未启用！";
			break;
		case 3:
			str2 = "市场未开市！";
			break;
		case 4:
			str2 = "市场正在进行集合竞价撮合！";
			break;
		case 5:
			str2 = "交易员和代为委托员不能同时存在！";
			break;
		case 10:
			str2 = "商品处于禁止交易状态！";
			break;
		case 11:
			str2 = "商品不属于当前交易节！";
			break;
		case 12:
			str2 = "委托价格超出涨幅上限！";
			break;
		case 13:
			str2 = "委托价格低于跌幅下限！";
			break;
		case 14:
			str2 = "委托价格不在此商品最小价位变动范围内！";
			break;
		case 15:
			str2 = "不存在此商品！";
			break;
		case 30:
			str2 = "此交易员不存在！";
			break;
		case 31:
			str2 = "此交易员没有操作该客户的权限！";
			break;
		case 32:
			str2 = "此交易客户不存在！";
			break;
		case 33:
			str2 = "此交易客户为禁止交易状态！";
			break;
		case 34:
			str2 = "此交易商不存在！";
			break;
		case 35:
			str2 = "此交易商为禁止交易状态！";
			break;
		case 36:
			str2 = "此交易商所在组为禁止交易状态！";
			break;
		case 37:
			str2 = "此代为委托员没有操作该交易商的权限！";
			break;
		case 100:
			str2 = "测试成功！";
			break;
		case 199:
			str2 = "通信故障！";
			break;
		case 200:
			str2 = "代码异常而失败！";
			break;
		case -1:
			str2 = "资金余额不足！";
			break;
		case -2:
			str2 = "超过交易商商品分类的最大持仓量！";
			break;
		case -3:
			str2 = "超过交易商总的最大持仓量！";
			break;
		case -4:
			str2 = " 超过品种最大订货量！";
			break;
		case -5:
			str2 = " 超过商品最大订货量！";
			break;
		case -6:
			str2 = "超过交易商净订货量的最大订货量！";
			break;
		case -11:
			str2 = "此委托已处于正在撤单状态！";
			break;
		case -12:
			str2 = "此委托已全部成交了！";
			break;
		case -13:
			str2 = "此委托已完成撤单了！";
			break;
		case -21:
			str2 = "持仓不足！";
			break;
		case -22:
			str2 = "指定仓不足！";
			break;
		case -99:
			str2 = "执行存储时未找到相关数据！";
			break;
		case -100:
			str2 = "执行存储失败！";
			break;
		case -204:
			str2 = "下单服务器已关闭！";
			break;
		case -205:
			str2 = "下单服务器未开启！";
			break;
		case -206:
			str2 = "委托信息不能为空！";
			break;
		default:
			localOrderReturnValue.setRetCode(-203);
			str2 = "未知异常！";
		}
		paramResponseResult.setRetCode(localOrderReturnValue.getRetCode());
		paramResponseResult.setMessage(str2);
		return paramResponseResult;
	}

	private boolean checkUserExist(String paramString, long paramLong) throws Exception {
		return true;
	}

	protected void renderXML(HttpServletResponse paramHttpServletResponse, String paramString) {
		try {
			paramHttpServletResponse.setContentType("text/xml;charset=GBK");
			paramHttpServletResponse.getWriter().print(paramString);
		} catch (IOException localIOException) {
			this.log.error(localIOException.getMessage(), localIOException);
		}
	}

	private String readXMLFromRequestBody(HttpServletRequest paramHttpServletRequest) throws Exception {
		String str1 = "1001";
		String str2 = "100100";
		String str3 = "TA0905";
		String str4 = "1";
		String str5 = "1";
		int i = 1500;
		int j = 1;
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<GNNT><REQ name='order'>").append("<TRADER_ID>").append(str1).append("</TRADER_ID>").append("<CUSTOMER_ID>")
				.append(str2).append("</CUSTOMER_ID>").append("<BUY_SELL>").append(j).append("</BUY_SELL>").append("<COMMODITY_ID>").append(str3)
				.append("</COMMODITY_ID>").append("<PRICE>").append(i).append("</PRICE>").append("<QTY>").append(str4).append("</QTY>")
				.append("<SETTLE_BASIS>").append(str5).append("</SETTLE_BASIS>").append("<CLOSEMODE></CLOSEMODE><TIMEFLAG></TIMEFLAG>")
				.append("<L_PRICE>0</L_PRICE>").append("<BROKER_REF>no</BROKER_REF>").append("<SESSION_ID>0</SESSION_ID>").append("</REQ></GNNT>");
		return localStringBuffer.toString();
	}

	private Document xml2DocumentByRequest(HttpServletRequest paramHttpServletRequest) throws Exception {
		String str = readXMLFromRequestBody(paramHttpServletRequest);
		Document localDocument = null;
		DocumentBuilder localDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(str.getBytes()));
		return localDocument;
	}

	private String getValueByName(Document paramDocument, String paramString) throws Exception {
		NodeList localNodeList = paramDocument.getElementsByTagName(paramString);
		if ((localNodeList == null) || (localNodeList.item(0) == null)) {
			return "";
		}
		String str;
		if (localNodeList.item(0).getFirstChild() == null) {
			str = "";
		} else {
			str = localNodeList.item(0).getFirstChild().getNodeValue();
		}
		return str;
	}

	public Object getBean(String paramString) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		}
		return ctx.getBean(paramString);
	}

	private Privilege getSessionBean(HttpServletRequest paramHttpServletRequest) {
		return (Privilege) paramHttpServletRequest.getSession().getAttribute("privilege");
	}

	private boolean isLogon(HttpServletRequest paramHttpServletRequest, String paramString, long paramLong) throws Exception {
		String str = "pc";
		boolean bool = this.tradeRMI.isLogon(paramString, paramLong, str);
		if (!bool) {
			return false;
		}
		if (paramHttpServletRequest.getSession().getAttribute("privilege") == null) {
			this.log.debug("====>Reload session ..");
			TraderLogonInfo localTraderLogonInfo = this.tradeRMI.getTraderInfo(paramString);
			OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
			Privilege localPrivilege = localOrdersManager.getradePrivilege(localTraderLogonInfo);
			this.log.debug("PrivilegeObj:" + localPrivilege);
			paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
		}
		return true;
	}

	private String getReqNameByXml(String paramString) {
		String str1 = "";
		String str2 = paramString.toUpperCase();
		int i = str2.indexOf("NAME=");
		if (i < 0) {
			return str1;
		}
		int j = str2.indexOf(">", i);
		if (j < 0) {
			return str1;
		}
		String str3 = str2.substring(i + 5, j);
		str3 = str3.replaceAll("'", "");
		str3 = str3.replaceAll("\"", "");
		str3 = str3.trim();
		str1 = str3.toLowerCase();
		return str1;
	}

	private String getValueByTagName(String paramString1, String paramString2) {
		String str1 = "";
		String str2 = paramString1.replaceAll("</", "<");
		String[] arrayOfString = str2.split("<" + paramString2 + ">");
		if (arrayOfString.length > 1) {
			str1 = arrayOfString[1];
		}
		return str1;
	}
}
