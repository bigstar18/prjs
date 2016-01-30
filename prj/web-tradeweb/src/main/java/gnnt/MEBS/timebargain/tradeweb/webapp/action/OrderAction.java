package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.OrderReturnValue;
import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.SmallHelper;
import gnnt.MEBS.timebargain.tradeweb.model.TotalDate;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersPagingManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class OrderAction extends HttpXmlServlet {
	private static final long serialVersionUID = -2287033851965181876L;
	private final Log log = LogFactory.getLog(HttpXmlServlet.class);
	private TradeService taken = null;
	private HashMap sortKeyMap = new HashMap();
	private HashMap orderKeyMap;
	private HashMap holdKeyMap;
	private HashMap tradeKeyMap;
	public ServletContext config = null;
	public static String host = "";
	public static String port = "";
	public MobileServlet mobileServlet = new MobileServlet();

	public OrderAction() {
		if ((serverRMI == null) || (tradeRMI == null)) {
			initRMI();
		}
		initOrderKeyMap();
	}

	protected void order(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'order' method ");
		}
		String str1 = "";
		OrderReturnValue localOrderReturnValue = new OrderReturnValue();
		try {
			String str2 = getLogonType(paramString2);
			str1 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str1)) {
				str1 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str1));
			String str3 = getValueByTagName(paramString1, "CUSTOMER_ID");
			Short localShort1 = new Short(getValueByTagName(paramString1, "BUY_SELL"));
			String str4 = getValueByTagName(paramString1, "COMMODITY_ID");
			this.log.debug("commID: " + str4);
			Double localDouble = new Double(getValueByTagName(paramString1, "PRICE"));
			Long localLong = new Long(getValueByTagName(paramString1, "QTY"));
			Short localShort2 = new Short(getValueByTagName(paramString1, "SETTLE_BASIS"));
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
			this.log.debug("Begin to Check Order Privilege........");
			if (localPrivilege == null) {
				this.log.debug("====>Reload session ..");
				TraderLogonInfo localObject1 = tradeRMI.getTraderInfo(str1);
				Object localObject2 = (OrdersManager) getBean("ordersManager");
				localPrivilege = ((OrdersManager) localObject2).getradePrivilege((TraderLogonInfo) localObject1);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
			}
			if (!PrivilegeController.checkDirectTradePrivilege(localPrivilege, str4, localShort1, localShort2)) {
				paramResponseResult.setRetCode(-230);
				paramResponseResult.setMessage(this.properties.getProperty("-221"));
			} else if (localDouble.doubleValue() <= 0.0D) {
				paramResponseResult.setRetCode(-231);
				paramResponseResult.setMessage(this.properties.getProperty("-222"));
			} else if (!PrivilegeController.checkBreedPrivilege(localPrivilege, localShort2, str4)) {
				paramResponseResult.setRetCode(-225);
				paramResponseResult.setMessage(this.properties.getProperty("-223"));
			} else if (!PrivilegeController.checkTraderPrvg(localPrivilege, localShort1, localShort2, str4)) {
				paramResponseResult.setRetCode(-221);
				paramResponseResult.setMessage(this.properties.getProperty("-224"));
			} else if (!PrivilegeController.checkFBreedPrivilege(localPrivilege, localShort1, localShort2, str4)) {
				paramResponseResult.setRetCode(-226);
				paramResponseResult.setMessage(this.properties.getProperty("-225"));
			} else if (!PrivilegeController.checkCommPrivilege(localPrivilege, localShort1, localShort2, str4)) {
				paramResponseResult.setRetCode(-227);
				paramResponseResult.setMessage(this.properties.getProperty("-226"));
			} else if (!PrivilegeController.checkFCodePrivilege(localPrivilege, localShort1, localShort2, str4)) {
				paramResponseResult.setRetCode(-228);
				paramResponseResult.setMessage(this.properties.getProperty("-227"));
			} else if (!PrivilegeController.checkCusBreedPrivilege(localPrivilege, localShort1, localShort2, str3, str4)) {
				paramResponseResult.setRetCode(-229);
				paramResponseResult.setMessage(this.properties.getProperty("-228"));
			} else if (!PrivilegeController.checkCusCommPrivilege(localPrivilege, localShort1, localShort2, str3, str4)) {
				paramResponseResult.setRetCode(-229);
				paramResponseResult.setMessage(this.properties.getProperty("-229"));
			} else {
				this.log.debug("Order Privilege Pass,Begin to Submit Order..");
				Short localShort3 = null;
				Short localObject1 = null;
				Double localObject2 = null;
				if (localShort2.shortValue() == 2) {
					String str5 = getValueByTagName(paramString1, "CLOSEMODE");
					localObject1 = Short.valueOf(str5.equals("") ? 1 : new Short(str5).shortValue());
					String localObject3 = getValueByTagName(paramString1, "L_PRICE");
					localObject2 = ((String) localObject3).equals("") ? null : new Double((String) localObject3);
					String str6 = getValueByTagName(paramString1, "TIMEFLAG");
					localShort3 = str6.trim().equals("") ? null : new Short(str6);
				}
				String str5 = getValueByTagName(paramString1, "BILLTYPE");
				Object localObject3 = "".equals(str5.trim()) ? null : new Short(str5);
				String str6 = getValueByTagName(paramString1, "SO");
				Order localOrder = new Order();
				localOrder.setTraderID(str1);
				localOrder.setCustomerID(str3);
				localOrder.setCommodityID(str4);
				localOrder.setBuyOrSell(localShort1);
				localOrder.setOrderType(localShort2);
				localOrder.setPrice(localDouble);
				localOrder.setQuantity(localLong);
				localOrder.setCloseMode((Short) localObject1);
				localOrder.setSpecPrice((Double) localObject2);
				localOrder.setSpecTime(localShort3);
				localOrder.setBillTradeType((Short) localObject3);
				int i = localOrder.getOrderType().shortValue();
				if (!"".equals(str6)) {
					localOrder.setSpecialOrderFlag(Short.valueOf(Short.parseShort(str6)));
				}
				paramResponseResult = submitOrder(l, localOrder, paramResponseResult, localOrderReturnValue, str2);
			}
		} catch (ConnectException localConnectException) {
			this.log.error("change_password rmi conection exception" + localConnectException);
			paramResponseResult.setRetCode(-201);
			paramResponseResult.setMessage(this.properties.getProperty("-201"));
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("order error:" + localRemoteException);
			errorException(localRemoteException);
			paramResponseResult.setRetCode(-202);
			paramResponseResult.setMessage(this.properties.getProperty("-202"));
		} catch (Exception localException) {
			this.log.error("order error:" + localException);
			errorException(localException);
			paramResponseResult.setRetCode(-203);
			paramResponseResult.setMessage(this.properties.getProperty("-203"));
		}
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.order(str1, paramResponseResult, localOrderReturnValue));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.order(str1, paramResponseResult, localOrderReturnValue));
		}
	}

	protected void my_weekorder_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'my_weekorder_query' method ");
		}
		int i = 0;
		long l1 = 0L;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			String str4 = getValueByTagName(paramString1, "BUY_SELL");
			Short localShort = (str4.equals("")) || (str4.equals("0")) ? null : new Short(str4);
			String str5 = getValueByTagName(paramString1, "ORDER_NO");
			Long localLong = (str5.equals("")) || (str5.equals("0")) ? null : new Long(str5);
			String str6 = getValueByTagName(paramString1, "COMMODITY_ID");
			String str7 = getValueByTagName(paramString1, "STARTNUM");
			String str8 = getValueByTagName(paramString1, "RECCNT");
			String str9 = getValueByTagName(paramString1, "SORTFLD");
			String str10 = getValueByTagName(paramString1, "ISDESC") == null ? "0" : "1";
			long l2 = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			String str11 = getValueByTagName(paramString1, "UT");
			int j = (str11 == null) || ("0".equals(str11)) || ("".equals(str11.trim())) ? 1 : 0;
			String str12 = j != 0 ? "queryAll" : str11;
			if (!isLogon(paramHttpServletRequest, str2, l2, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				localOrders.setBS_Flag(localShort);
				localOrders.setA_OrderNo(localLong);
				localOrders.setTraderID(str2);
				localOrders.setCommodityID(str6);
				localOrders.setUpdateTime(str12);
				SortCondition localSortCondition = new SortCondition();
				localSortCondition.setStartNu(str7);
				localSortCondition.setIsdesc(Integer.parseInt(str10));
				localSortCondition.setReccnt(str8);
				localSortCondition.setSortfLD((String) this.orderKeyMap.get(str9));
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				if (PrivilegeController.checkSuperTrader(localPrivilege)) {
					localOrders.setSuperTrader("A");
				}
				List localList = localOrdersManager.my_weekorder_query(localOrders, localPrivilege, localSortCondition);
				Timestamp localObject = null;
				for (int k = 0; k < localList.size(); k++) {
					Timestamp localTimestamp = null;
					Map localMap = (Map) localList.get(k);
					if (localMap.containsKey("UPDATETIME")) {
						localTimestamp = (Timestamp) localMap.get("UPDATETIME");
					}
					if (localObject == null) {
						localObject = localTimestamp;
					} else if (localObject.before(localTimestamp)) {
						localObject = localTimestamp;
					}
				}
				l1 = localObject == null ? parseLong(str11) : localObject.getTime();
				if (localList == null) {
					i = -202;
					str1 = this.properties.getProperty("-208");
				} else if (localList.size() == 0) {
					i = 0;
					str1 = this.properties.getProperty("-200");
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("my_weekorder_query rmi conection exception" + localConnectException);
			i = -202;
			str1 = this.properties.getProperty("-202");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("my_weekorder_query remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("my_weekorder_query error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.my_order_query(paramResponseResult, l1));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.my_order_query(paramResponseResult, l1));
		}
	}

	protected void order_wd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'order_wd' method ");
		}
		String str1 = "";
		OrderReturnValue localOrderReturnValue = new OrderReturnValue();
		try {
			String str2 = getLogonType(paramString2);
			str1 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str1)) {
				str1 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str1));
			Long localLong = new Long(getValueByTagName(paramString1, "ORDER_NO"));
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str1, l, str2)) {
				paramResponseResult.setRetCode(-201);
				paramResponseResult.setMessage(this.properties.getProperty("-205"));
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				localOrders.setA_OrderNo(localLong);
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				List localList = localOrdersManager.my_order_query(localOrders, localPrivilege);
				Map localMap = (Map) localList.get(0);
				String str3 = (String) localMap.get("TraderID");
				int i = 0;
				if (localMap.get("CloseFlag") != null) {
					i = ((BigDecimal) localMap.get("CloseFlag")).intValue();
				}
				if ((str1 != null) && (i != 2)) {
					Order localOrder = new Order();
					localOrder.setCommodityID((String) localMap.get("CommodityID"));
					localOrder.setCustomerID((String) localMap.get("CustomerID"));
					localOrder.setTraderID(str1);
					localOrder.setOrderType(Short.valueOf("4"));
					localOrder.setWithdrawID(localLong);
					String str4 = (String) localMap.get("TRADER_ID");
					if ((str4 == null) || (tradeRMI.getFirmID(str3).equals(localPrivilege.getFirmId()))) {
						paramResponseResult = submitOrder(l, localOrder, paramResponseResult, localOrderReturnValue, str2);
					} else {
						paramResponseResult.setRetCode(1);
						paramResponseResult.setMessage(this.properties.getProperty("1"));
					}
				} else if (i == 2) {
					paramResponseResult.setRetCode(1);
					paramResponseResult.setMessage(this.properties.getProperty("-14"));
				} else {
					paramResponseResult.setRetCode(1);
					paramResponseResult.setMessage(this.properties.getProperty("1"));
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("change_password rmi conection exception" + localConnectException);
			paramResponseResult.setRetCode(-201);
			paramResponseResult.setMessage(this.properties.getProperty("-201"));
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("order_wd error:" + localRemoteException.getMessage());
			errorException(localRemoteException);
			paramResponseResult.setRetCode(-202);
			paramResponseResult.setMessage(this.properties.getProperty("-202"));
		} catch (Exception localException) {
			this.log.error("order_wd error:" + localException.getMessage());
			errorException(localException);
			paramResponseResult.setRetCode(-203);
			paramResponseResult.setMessage(this.properties.getProperty("-203"));
		}
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse,
					ResponseXml.responseXml(str1, paramResponseResult.getName(), paramResponseResult.getRetCode(), paramResponseResult.getMessage()));
		} else {
			renderXML(paramHttpServletResponse,
					ResponseXml.responseXml(str1, paramResponseResult.getName(), paramResponseResult.getRetCode(), paramResponseResult.getMessage()));
		}
	}

	private ResponseResult submitOrder(long paramLong, Order paramOrder, ResponseResult paramResponseResult, OrderReturnValue paramOrderReturnValue,
			String paramString) throws Exception {
		OrderReturnValue localOrderReturnValue = tradeRMI.order(paramLong, paramOrder, paramString);
		paramOrderReturnValue.setRetCode(localOrderReturnValue.getRetCode());
		paramOrderReturnValue.setOrderNo(localOrderReturnValue.getOrderNo());
		paramOrderReturnValue.setOrderTime(localOrderReturnValue.getOrderTime());
		String str = "";
		switch (paramOrderReturnValue.getRetCode()) {
		case 0:
			break;
		case 1:
			str = this.properties.getProperty("1");
			break;
		case 2:
			str = this.properties.getProperty("2");
			break;
		case 3:
			str = this.properties.getProperty("3");
			break;
		case 4:
			str = this.properties.getProperty("4");
			break;
		case 5:
			str = this.properties.getProperty("5");
			break;
		case 10:
			str = this.properties.getProperty("10");
			break;
		case 11:
			str = this.properties.getProperty("11");
			break;
		case 12:
			str = this.properties.getProperty("12");
			break;
		case 13:
			str = this.properties.getProperty("13");
			break;
		case 14:
			str = this.properties.getProperty("14");
			break;
		case 15:
			str = this.properties.getProperty("15");
			break;
		case 16:
			str = this.properties.getProperty("16");
			break;
		case 30:
			str = this.properties.getProperty("30");
			break;
		case 31:
			str = this.properties.getProperty("31");
			break;
		case 32:
			str = this.properties.getProperty("32");
			break;
		case 33:
			str = this.properties.getProperty("33");
			break;
		case 34:
			str = this.properties.getProperty("34");
			break;
		case 35:
			str = this.properties.getProperty("35");
			break;
		case 37:
			str = this.properties.getProperty("37");
			break;
		case 38:
			str = this.properties.getProperty("38");
			break;
		case 200:
			str = this.properties.getProperty("200");
			break;
		case 201:
			str = this.properties.getProperty("201");
			break;
		case 202:
			str = this.properties.getProperty("202");
			break;
		case -1:
			str = this.properties.getProperty("-1");
			break;
		case -2:
			str = this.properties.getProperty("-2");
			break;
		case -3:
			str = this.properties.getProperty("-3");
			break;
		case -4:
			str = this.properties.getProperty("-4");
			break;
		case -5:
			str = this.properties.getProperty("-5");
			break;
		case -6:
			str = this.properties.getProperty("-6");
			break;
		case -7:
			str = this.properties.getProperty("-7");
			break;
		case -8:
			str = this.properties.getProperty("-8");
			break;
		case -9:
			str = this.properties.getProperty("-9");
			break;
		case -11:
			str = this.properties.getProperty("-11");
			break;
		case -12:
			str = this.properties.getProperty("-12");
			break;
		case -13:
			str = this.properties.getProperty("-13");
			break;
		case -21:
			str = this.properties.getProperty("-21");
			break;
		case -22:
			str = this.properties.getProperty("-22");
			break;
		case -99:
			str = this.properties.getProperty("-99");
			break;
		case -100:
			str = this.properties.getProperty("-100");
			break;
		case -204:
			str = this.properties.getProperty("-204");
			break;
		case -206:
			str = this.properties.getProperty("-206");
			break;
		default:
			this.log.error("-->unconfirm ret:" + paramOrderReturnValue.getRetCode());
			paramOrderReturnValue.setRetCode(-203);
			str = this.properties.getProperty("-207");
		}
		paramResponseResult.setRetCode(paramOrderReturnValue.getRetCode());
		paramResponseResult.setMessage(str);
		return paramResponseResult;
	}

	protected void my_order_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'my_order_query' method ");
		}
		int i = 0;
		String str1 = "";
		String str2 = "";
		long l1 = 0L;
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			String str4 = getValueByTagName(paramString1, "BUY_SELL");
			Short localShort = (str4.equals("")) || (str4.equals("0")) ? null : new Short(str4);
			String str5 = getValueByTagName(paramString1, "ORDER_NO");
			Long localLong = (str5.equals("")) || (str5.equals("0")) ? null : new Long(str5);
			String str6 = getValueByTagName(paramString1, "COMMODITY_ID");
			String str7 = getValueByTagName(paramString1, "STARTNUM");
			String str8 = getValueByTagName(paramString1, "RECCNT");
			String str9 = getValueByTagName(paramString1, "SORTFLD");
			String str10 = getValueByTagName(paramString1, "ISDESC") == "" ? "0" : "1";
			String str11 = getValueByTagName(paramString1, "UT");
			int j = (str11 == null) || ("0".equals(str11)) || ("".equals(str11.trim())) ? 1 : 0;
			String str12 = j != 0 ? "queryAll" : str11;
			SortCondition localSortCondition = new SortCondition();
			localSortCondition.setStartNu(str7);
			localSortCondition.setIsdesc(Integer.parseInt(str10));
			localSortCondition.setReccnt(str8);
			localSortCondition.setSortfLD((String) this.orderKeyMap.get(str9));
			long l2 = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l2, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				if (PrivilegeController.checkSuperTrader(localPrivilege)) {
					localOrders.setSuperTrader("A");
				}
				localOrders.setBS_Flag(localShort);
				localOrders.setA_OrderNo(localLong);
				localOrders.setTraderID(str2);
				localOrders.setUpdateTime(str12);
				localOrders.setCommodityID(str6);
				List localList = localOrdersManager.my_order_query(localOrders, localPrivilege, localSortCondition);
				Timestamp localObject = null;
				for (int k = 0; k < localList.size(); k++) {
					Timestamp localTimestamp = null;
					Map localMap = (Map) localList.get(k);
					if (localMap.containsKey("UPDATETIME")) {
						localTimestamp = (Timestamp) localMap.get("UPDATETIME");
					}
					if (localObject == null) {
						localObject = localTimestamp;
					} else if (localObject.before(localTimestamp)) {
						localObject = localTimestamp;
					}
				}
				l1 = localObject == null ? parseLong("".equals(str11) ? "0" : str11) : localObject.getTime();
				if (localList == null) {
					i = -202;
					str1 = this.properties.getProperty("-208");
				} else if (localList.size() == 0) {
					i = 0;
					str1 = this.properties.getProperty("-200");
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("my_order_query rmi conection exception" + localConnectException);
			i = -202;
			str1 = this.properties.getProperty("-202");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("my_order_query remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("my_order_query error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.my_order_query(paramResponseResult, l1));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.my_order_query(paramResponseResult, l1));
		}
	}

	protected void my_weekorder_pagingquery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
			String paramString1, ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'my_weekorder_pagingquery' method ");
		}
		int i = 0;
		long l1 = 0L;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			String str4 = getValueByTagName(paramString1, "BUY_SELL");
			Short localShort = (str4.equals("")) || (str4.equals("0")) ? null : new Short(str4);
			String str5 = getValueByTagName(paramString1, "ORDER_NO");
			Long localLong = (str5.equals("")) || (str5.equals("0")) ? null : new Long(str5);
			String str6 = getValueByTagName(paramString1, "COMMODITY_ID");
			String str7 = getValueByTagName(paramString1, "STARTNUM");
			String str8 = getValueByTagName(paramString1, "RECCNT");
			String str9 = getValueByTagName(paramString1, "SORTFLD");
			String str10 = getValueByTagName(paramString1, "ISDESC") == null ? "1" : getValueByTagName(paramString1, "ISDESC");
			if ((str8 == null) || ("".endsWith(str8)) || ("0".endsWith(str8))) {
				str8 = "1000";
			}
			String str11 = getValueByTagName(paramString1, "PAGENUM");
			String str12 = getValueByTagName(paramString1, "ISQUERYALL");
			String str13 = getValueByTagName(paramString1, "PRI");
			String str14 = getValueByTagName(paramString1, "TYPE");
			String str15 = getValueByTagName(paramString1, "STA");
			int j = (str8 == null ? 0 : Integer.valueOf(str8).intValue()) * (str11 == null ? 0 : Integer.valueOf(str11).intValue() - 1);
			if (j < 0) {
				j = 0;
			}
			int k = j + (str8 == null ? 0 : Integer.valueOf(str8).intValue());
			HashMap localHashMap = new HashMap();
			localHashMap.put("startPagingNum", Integer.valueOf(j));
			localHashMap.put("endPagingNum", Integer.valueOf(k));
			localHashMap.put("isQueryAll", str12 == null ? "0" : str12);
			localHashMap.put(this.orderKeyMap.get("PRI"), str13 == null ? "" : str13);
			localHashMap.put(this.orderKeyMap.get("TYPE"), str14 == null ? "0" : str14);
			localHashMap.put(this.orderKeyMap.get("STA"), str15 == null ? "0" : str15);
			long l2 = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			String str16 = getValueByTagName(paramString1, "UT");
			int m = (str16 == null) || ("0".equals(str16)) || ("".equals(str16.trim())) ? 1 : 0;
			String str17 = m != 0 ? "queryAll" : str16;
			SmallHelper localSmallHelper = new SmallHelper();
			if (!isLogon(paramHttpServletRequest, str2, l2, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				OrdersPagingManager localOrdersPagingManager = (OrdersPagingManager) getBean("ordersPagingManager");
				Orders localOrders = new Orders();
				localOrders.setBS_Flag(localShort);
				localOrders.setA_OrderNo(localLong);
				localOrders.setTraderID(str2);
				localOrders.setCommodityID(str6);
				localOrders.setUpdateTime(str17);
				SortCondition localSortCondition = new SortCondition();
				localSortCondition.setStartNu(str7);
				localSortCondition.setIsdesc(Integer.parseInt(str10));
				localSortCondition.setReccnt(str8);
				localSortCondition.setSortfLD((String) this.orderKeyMap.get(str9));
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				if (PrivilegeController.checkSuperTrader(localPrivilege)) {
					localOrders.setSuperTrader("A");
				}
				localSmallHelper.setAction("select ");
				localSmallHelper.setField(" max(" + this.orderKeyMap.get("UT").toString() + ")  " + this.orderKeyMap.get("UT") + " ");
				localSmallHelper.setTable(" t_orders ");
				List localList1 = localOrdersPagingManager.smallHelper(localSmallHelper);
				List localList2 = localOrdersPagingManager.my_weekorder_pagingquery(localOrders, localPrivilege, localSortCondition, localHashMap);
				List localList3 = localOrdersPagingManager.totalDateQuery(
						("1".equals(str12)) || ("2".equals(str12)) ? "my_order_pagingquery" : "my_weekorder_pagingquery", localPrivilege,
						localHashMap);
				if (localList2 == null) {
					i = -202;
					str1 = this.properties.getProperty("-208");
				} else if (localList2.size() == 0) {
					i = 0;
					str1 = this.properties.getProperty("-200");
				} else {
					paramResponseResult.setResultList(localList2);
				}
				Object localObject;
				if ((localList1 != null) && (localList1.size() > 0)) {
					localObject = (Map) localList1.get(0);
					l1 = ((Timestamp) ((Map) localObject).get(this.orderKeyMap.get("UT"))).getTime();
				}
				if ((localList3 != null) && (localList3.size() > 0)) {
					localObject = (TotalDate) localList3.get(0);
					paramResponseResult.setTotalDate((TotalDate) localObject);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("my_weekorder_pagingquery rmi conection exception" + localConnectException);
			i = -202;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("my_weekorder_pagingquery remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("my_weekorder_pagingquery error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		renderXML(paramHttpServletResponse, ResponseXml.my_order_pagingquery(paramResponseResult, l1));
	}

	public void initOrderKeyMap() {
		this.orderKeyMap = new HashMap();
		this.orderKeyMap.put("OR_N", "A_OrderNo");
		this.orderKeyMap.put("TIME", "OrderTime");
		this.orderKeyMap.put("STA", "Status");
		this.orderKeyMap.put("TYPE", "BS_Flag");
		this.orderKeyMap.put("SE_F", "OrderType");
		this.orderKeyMap.put("TR_I", "TraderID");
		this.orderKeyMap.put("FI_I", "FirmID");
		this.orderKeyMap.put("CU_I", "CustomerID");
		this.orderKeyMap.put("CO_I", "OrderTime");
		this.orderKeyMap.put("PRI", "CommodityID");
		this.orderKeyMap.put("QTY", "Quantity");
		this.orderKeyMap.put("BAL", "notTradeQty");
		this.orderKeyMap.put("L_P", "SpecPrice");
		this.orderKeyMap.put("WD_T", "WithdrawTime");
		this.orderKeyMap.put("OR_P", "Price");
		this.orderKeyMap.put("UT", "UpdateTime");
	}
}
