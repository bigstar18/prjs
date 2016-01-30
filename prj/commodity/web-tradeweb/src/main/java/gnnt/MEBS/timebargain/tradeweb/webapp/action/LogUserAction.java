package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;

public class LogUserAction extends HttpXmlServlet {
	private static final long serialVersionUID = -4132357775705947600L;
	private final Log log = LogFactory.getLog(HttpXmlServlet.class);
	private TradeService taken = null;
	private boolean debugcommodity = false;
	public MobileServlet mobileServlet = new MobileServlet();
	public static final String SYSTIME_ISLOGON_KEY = "SYSTIME_ISLOGON_KEY";
	public static final int SYSTIME_ISLOGON_NUM = 10;
	int a = 0;
	int ee = 0;

	public LogUserAction(TradeService paramTradeService) {
		this.taken = paramTradeService;
		if ((serverRMI == null) || (tradeRMI == null)) {
			initRMI();
		}
	}

	protected boolean isSysTimeLogon(HttpServletRequest paramHttpServletRequest, String paramString1, long paramLong, String paramString2)
			throws Exception {
		String str = "SYSTIME_ISLOGON_KEY_" + paramString1 + "_" + paramLong;
		Object localObject = paramHttpServletRequest.getSession().getAttribute(str);
		if (localObject != null) {
			try {
				int i = Integer.parseInt(localObject.toString());
				if (i < 10) {
					paramHttpServletRequest.getSession().setAttribute(str, Integer.valueOf(i + 1));
					return Boolean.parseBoolean("" + paramHttpServletRequest.getSession().getAttribute("last_islogon"));
				}
			} catch (Exception localException) {
				this.log.error("Count fails, will continue to perform the original logic ," + localException);
			}
		}
		paramHttpServletRequest.getSession().setAttribute(str, Integer.valueOf(1));
		boolean bool = isLogon(paramHttpServletRequest, paramString1, paramLong, paramString2);
		paramHttpServletRequest.getSession().setAttribute("last_islogon", Boolean.valueOf(bool));
		return bool;
	}

	protected void logon(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'logon' method");
		}
		long l1 = 0L;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			String str4 = getValueByTagName(paramString1, "LA");
			lanaguages.put(str2, str4);
			international((String) lanaguages.get(str2));
			this.log.debug("logon userid=" + str2);
			String str5 = getValueByTagName(paramString1, "PASSWORD");
			String str6 = getValueByTagName(paramString1, "REGISTER_WORD");
			String str7 = paramHttpServletRequest.getRemoteAddr();
			String str8 = getValueByTagName(paramString1, "L_M");
			int i = 0;
			if ((str8 != null) && (!str8.endsWith(""))) {
				i = Integer.parseInt(str8);
			}
			Trader localTrader = new Trader();
			localTrader.setTraderID(str2);
			localTrader.setPassword(str5);
			localTrader.setKeyCode(str6);
			localTrader.setLogonIP(str7);
			localTrader.setLogonMark(i);
			localTrader.setLogonType(str3);
			TraderLogonInfo localTraderLogonInfo = tradeRMI.logon(localTrader);
			long l2 = localTraderLogonInfo.getSessionID();
			long l3 = 0L;
			if (localTraderLogonInfo.getRecode() != null) {
				l3 = Long.parseLong(localTraderLogonInfo.getRecode());
			}
			if (l2 > 0L) {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Privilege localPrivilege = localOrdersManager.getradePrivilege(localTraderLogonInfo);
				this.log.debug("PrivilegeObj:" + localPrivilege);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
				l1 = l2;
				ArrayList localArrayList = new ArrayList();
				localArrayList.add(localTraderLogonInfo);
				paramResponseResult.setResultList(localArrayList);
				paramHttpServletRequest.getSession().setAttribute("LOGON_TYPE", str3);
			}
			if ((l3 == -1L) || (l3 == -2L)) {
				l1 = -1L;
				str1 = this.properties.getProperty("-31");
			} else if (l3 == -3L) {
				l1 = -3L;
				str1 = this.properties.getProperty("-33");
			} else if (l3 == -4L) {
				l1 = -4L;
				str1 = this.properties.getProperty("-34");
			} else if (l3 == -5L) {
				l1 = -5L;
				str1 = this.properties.getProperty("-38");
			} else if (l3 == -6L) {
				l1 = -6L;
				str1 = this.properties.getProperty("36");
			} else if (l3 == -7L) {
				l1 = -7L;
				str1 = this.properties.getProperty("35");
			} else if (l3 == -1000L) {
				l1 = -1000L;
				str1 = this.properties.getProperty("-35");
			}
		} catch (ConnectException localConnectException) {
			this.log.error("logon rmi conection exception" + localConnectException);
			l1 = -201L;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
			this.log.error("logon remoteExceotion:" + localRemoteException);
			errorException(localRemoteException);
			l1 = -202L;
			str1 = this.properties.getProperty("-202");
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("logon error:" + localException);
			errorException(localException);
			l1 = -203L;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setLongRetCode(l1);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.logon(paramResponseResult));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.logon(paramResponseResult));
		}
	}

	protected void logoff(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'logoff' method ");
		}
		int i = 0;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			lanaguages.remove(str2);
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				tradeRMI.logoff(l, str3);
				paramHttpServletRequest.getSession().invalidate();
			}
		} catch (ConnectException localConnectException) {
			this.log.error("logoff rmi conection exception" + localConnectException);
			i = -201;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("logoff remoteerror:" + localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("logoff error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.responseXml(str2, paramResponseResult.getName(), i, str1));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.responseXml(str2, paramResponseResult.getName(), i, str1));
		}
	}

	protected void check_user(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'check_user' method ");
		}
		long l1 = 0L;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			long l2 = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			String str4 = getValueByTagName(paramString1, "MODULE_ID");
			this.log.debug("ModuleID=" + str4);
			String str5 = getValueByTagName(paramString1, "F_LOGONTYPE");
			if (str5.equals("")) {
				str5 = "pc";
			}
			String str6 = paramHttpServletRequest.getRemoteAddr();
			int i = Integer.parseInt(str4);
			int j = Integer.parseInt("15");
			TraderLogonInfo localTraderLogonInfo = tradeRMI.remoteLogon(str2, l2, str6, str5, i, str3, j);
			this.log.debug("checkuser info" + localTraderLogonInfo.toString());
			this.log.debug(localTraderLogonInfo.getSessionID() + "," + localTraderLogonInfo.getFirmId() + "," + localTraderLogonInfo.getTraderId());
			this.log.info("交易员: " + str2 + ", 远程登录，IP: " + str6 + ", fromLogonType: " + str5 + ", fromModuleID: " + i + ", toLogonType: " + str3
					+ ", toModuleID: " + j);
			this.log.info("返回码: " + localTraderLogonInfo.getRecode() + ", 返回信息：" + localTraderLogonInfo.getMessage());
			long l3 = 0L;
			if (localTraderLogonInfo.getRecode() != null) {
				l3 = Long.parseLong(localTraderLogonInfo.getRecode());
			}
			long l4 = localTraderLogonInfo.getSessionID();
			if ((l4 > 0L) && (paramHttpServletRequest.getSession().getAttribute("privilege") == null)) {
				this.log.debug("====>Reload session ..");
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Privilege localPrivilege = localOrdersManager.getradePrivilege(localTraderLogonInfo);
				this.log.debug("PrivilegeObj:" + localPrivilege);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
			}
			if (l3 == -1L) {
				l1 = -1L;
				str1 = this.properties.getProperty("-1300");
			} else if (l3 == -2L) {
				l1 = -2L;
				str1 = this.properties.getProperty("-37");
			} else if (l3 == -1202L) {
				l1 = -1202L;
				str1 = this.properties.getProperty("-1202");
			} else if (l3 == -1301L) {
				l1 = -1301L;
				str1 = this.properties.getProperty("-1301");
			} else if (l3 == -1302L) {
				l1 = -1302L;
				str1 = this.properties.getProperty("-1302");
			} else if (l3 == -1303L) {
				l1 = -1303L;
				str1 = this.properties.getProperty("-1303");
			}
		} catch (ConnectException localConnectException) {
			this.log.error("checkUser rmi conection exception" + localConnectException);
			l1 = -201L;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("checkUser remoteerror:" + localRemoteException);
			l1 = -204L;
			str1 = this.properties.getProperty("-202");
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("checkUser error:" + localException);
			errorException(localException);
			l1 = -203L;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setLongRetCode(l1);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.check_user(paramResponseResult));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.check_user(paramResponseResult));
		}
	}

	protected void change_password(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'change_password' method");
		}
		int i = 0;
		String str1 = "";
		String str2 = getValueByTagName(paramString1, "USER_ID");
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			this.log.debug("change_password userid=" + str2);
			international((String) lanaguages.get(str2));
			String str4 = getValueByTagName(paramString1, "OLD_PASSWORD");
			String str5 = getValueByTagName(paramString1, "NEW_PASSWORD");
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				i = tradeRMI.changePassowrd(str2, str4, str5, paramHttpServletRequest.getRemoteAddr());
				if (i == 1) {
					i = 0;
				} else if (i == -1) {
					i = -1;
					str1 = this.properties.getProperty("-30");
				} else if (i == -2) {
					i = -2;
					str1 = this.properties.getProperty("200");
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("change_password rmi conection exception" + localConnectException);
			i = -201;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("change_password remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("change_password error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.responseXml(str2, paramResponseResult.getName(), i, str1));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.responseXml(str2, paramResponseResult.getName(), i, str1));
		}
	}

	public void commodity_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if ((this.log.isDebugEnabled()) && (this.debugcommodity)) {
			this.log.debug("Entering 'commodity_query' method ");
		}
		int i = 0;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			String str4 = getValueByTagName(paramString1, "COMMODITY_ID");
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Orders localOrders = new Orders();
				this.log.debug("-------Commodity=" + str4);
				localOrders.setCommodityID(str4);
				List localList = localOrdersManager.commodity_query(localOrders);
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = this.properties.getProperty("-209");
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("change_password rmi conection exception" + localConnectException);
			i = -201;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("commodity_query remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("commodity_query error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse,
					ResponseXml.commodity_query(paramResponseResult, getSessionBean(paramHttpServletRequest)));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.commodity_query(paramResponseResult, getSessionBean(paramHttpServletRequest)));
		}
	}

	protected void firm_info(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'firm_info' method ");
		}
		int i = 0;
		String str1 = "";
		String str2 = "";
		try {
			String str3 = getLogonType(paramString2);
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				List localList = localOrdersManager.firm_info(localPrivilege);
				if ((localList == null) || (localList.size() == 0)) {
					i = -202;
					str1 = this.properties.getProperty("-209");
				} else {
					paramResponseResult.setResultList(localList);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("firm_info rmi conection exception" + localConnectException);
			i = -202;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("firm_info remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("firm_info error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.firm_info(paramResponseResult));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.firm_info(paramResponseResult));
		}
	}

	protected void sys_time_query(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		int i = 0;
		int j = 0;
		String str1 = "";
		boolean bool = true;
		try {
			String str2 = getLogonType(paramString2);
			String str3 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str3)) {
				str3 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str3));
			String str4 = getValueByTagName(paramString1, "LAST_ID");
			long l1 = 0L;
			if ((str4 != null) && (!str4.equals(""))) {
				l1 = parseLong(str4);
			}
			int k = "".equals(getValueByTagName(paramString1, "TD_CNT")) ? 0 : Integer.parseInt(getValueByTagName(paramString1, "TD_CNT"));
			bool = !"1".equals(getValueByTagName(paramString1, "CU_LG"));
			long l2 = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isSysTimeLogon(paramHttpServletRequest, str3, l2, str2)) {
				j = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				ArrayList localArrayList1 = new ArrayList();
				HashMap localHashMap = new HashMap();
				localHashMap.put("CUR_TIME", DateUtil.Mills2Time(System.currentTimeMillis() + TradeService.diff));
				localHashMap.put("CUR_DATE", DateUtil.Mills2Date(System.currentTimeMillis() + TradeService.diff));
				localHashMap.put("TV_USEC", String.valueOf(System.currentTimeMillis() + TradeService.diff));
				if (this.taken == null) {
					OrdersManager localObject = (OrdersManager) getBean("ordersManager");
					this.taken = TradeService.getInstance((OrdersManager) localObject, this);
				}
				Object localObject = getSessionBean(paramHttpServletRequest);
				String str5 = ((Privilege) localObject).getFirmId();
				LinkedList localLinkedList = (LinkedList) this.taken.getTradeMap().get(str5);
				int m = 0;
				if (localLinkedList != null) {
					if ((bool) && (localLinkedList.size() > k)) {
						ArrayList localArrayList2 = new ArrayList();
						for (int i1 = k; i1 < localLinkedList.size(); i1++) {
							Trade localTrade = (Trade) localLinkedList.get(i1);
							localArrayList2.add(localTrade);
						}
						m = 1;
						localHashMap.put("Trades", localArrayList2);
					}
					localHashMap.put("TD_TTL", Integer.valueOf(localLinkedList.size()));
				}
				if (this.conditionAction == null) {
					this.conditionAction = new ConditionAction();
				}
				int n = this.conditionAction.selectConditionOrderCountFromCache(paramHttpServletRequest, paramHttpServletResponse, paramString1,
						paramResponseResult);
				if (n == 0) {
					i = 0;
				} else {
					i = 1;
				}
				localHashMap.put("NEW_T", Integer.valueOf(bool ? m : 0));
				localArrayList1.add(localHashMap);
				paramResponseResult.setResultList(localArrayList1);
			}
		} catch (ConnectException localConnectException) {
			this.log.error("sys_time_query rmi conection exception" + localConnectException);
			j = -202;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("sys_time_query remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			j = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			this.log.error("sys_time_query error:" + localException);
			errorException(localException);
			localException.printStackTrace();
			j = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(j);
		paramResponseResult.setMessage(str1);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.sys_time_query(paramResponseResult, this.taken.tradeDay, bool, i));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.sys_time_query(paramResponseResult, this.taken.tradeDay, bool, i));
		}
	}
}
