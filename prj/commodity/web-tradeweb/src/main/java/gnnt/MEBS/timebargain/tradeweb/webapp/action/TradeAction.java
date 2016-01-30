package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.TotalDate;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersPagingManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class TradeAction extends HttpXmlServlet {
	private static final long serialVersionUID = 1307100978962754165L;
	private final Log log = LogFactory.getLog(HttpXmlServlet.class);
	private TradeService taken = null;
	public MobileServlet mobileServlet = new MobileServlet();
	private HashMap sortKeyMap = new HashMap();
	private HashMap orderKeyMap = new HashMap();
	private HashMap holdKeyMap = new HashMap();
	private HashMap tradeKeyMap;
	public ServletContext config = null;
	public static String host = "";
	public static String port = "";

	public TradeAction(TradeService paramTradeService) {
		if ((serverRMI == null) || (tradeRMI == null)) {
			initRMI();
		}
		initTradeKeyMap();
		this.taken = paramTradeService;
	}

	protected void tradequery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'tradequery' method ");
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
			String str4 = getValueByTagName(paramString1, "LAST_TRADE_ID");
			Long localLong = null;
			if ((str4 != null) && (!str4.equals("")) && (!str4.equals("0"))) {
				localLong = Long.valueOf(str4);
			}
			long l = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				String str5 = localPrivilege.getFirmId();
				if (this.taken == null) {
					OrdersManager localObject = (OrdersManager) getBean("ordersManager");
					this.taken = TradeService.getInstance((OrdersManager) localObject, this);
				}
				Object localObject = (LinkedList) this.taken.getTradeMap().get(str5);
				if ((localObject == null) || (((LinkedList) localObject).size() == 0)) {
					this.log.debug("====> linkedlist has no found");
					i = -202;
					str1 = this.properties.getProperty("-209");
				} else {
					if (localLong == null) {
						localLong = Long.valueOf(0L);
					}
					this.log.debug("===>last_trade_id = " + localLong);
					ArrayList localArrayList = new ArrayList();
					for (int j = ((LinkedList) localObject).size() - 1; j >= 0; j--) {
						Trade localTrade = (Trade) ((LinkedList) localObject).get(j);
						if (localLong.longValue() >= localTrade.getA_TradeNo().longValue()) {
							break;
						}
						if (localPrivilege.getTraderStatus().equals("A")) {
							localArrayList.add(localTrade);
						} else if (localTrade.getTraderID().equals(str2)) {
							localArrayList.add(localTrade);
						}
					}
					this.log.debug("===>clint list size =" + localArrayList.size());
					if ((localArrayList == null) || (localArrayList.size() == 0)) {
						i = -202;
						str1 = this.properties.getProperty("-209");
					}
					paramResponseResult.setResultList(localArrayList);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("tradequery rmi conection exception" + localConnectException);
			i = -202;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("tradequery remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("tradequery error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		if ("mobile".equals(paramString2)) {
			this.mobileServlet.renderXML(paramHttpServletResponse, ResponseXml.tradequery(paramResponseResult));
		} else {
			renderXML(paramHttpServletResponse, ResponseXml.tradequery(paramResponseResult));
		}
	}

	protected void tradepagingquery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'tradepagingquery' method ");
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
			String str4 = getValueByTagName(paramString1, "LAST_TRADE_ID");
			long l1 = 1L;
			if ((str4 != null) && (!str4.equals("")) && (!str4.equals("0"))) {
				l1 = Long.valueOf(str4).longValue();
			}
			long l2 = parseLong(getValueByTagName(paramString1, "SESSION_ID"));
			if (!isLogon(paramHttpServletRequest, str2, l2, str3)) {
				i = -201;
				str1 = this.properties.getProperty("-205");
			} else {
				Privilege localPrivilege = getSessionBean(paramHttpServletRequest);
				String str5 = localPrivilege.getFirmId();
				OrdersPagingManager localOrdersPagingManager = (OrdersPagingManager) getBean("ordersPagingManager");
				Trader localTrader = new Trader();
				localTrader.setTraderID(str2);
				String str6 = getValueByTagName(paramString1, "ISDESC") == null ? "1" : getValueByTagName(paramString1, "ISDESC");
				String str7 = getValueByTagName(paramString1, "SORTFLD");
				String str8 = getValueByTagName(paramString1, "RECCNT");
				String str9 = getValueByTagName(paramString1, "PAGENUM");
				SortCondition localSortCondition = new SortCondition();
				localSortCondition.setIsdesc(Integer.parseInt(str6));
				localSortCondition.setReccnt(str8);
				localSortCondition.setSortfLD((String) this.tradeKeyMap.get(str7));
				if ((str8 == null) || ("".endsWith(str8)) || ("0".endsWith(str8))) {
					str8 = "10000";
				}
				int j = (str8 == null ? 0 : Integer.valueOf(str8).intValue()) * (str9 == null ? 0 : Integer.valueOf(str9).intValue() - 1);
				if (j < 0) {
					j = 0;
				}
				int k = j + (str8 == null ? 0 : Integer.valueOf(str8).intValue());
				String str10 = getValueByTagName(paramString1, "PRI");
				String str11 = getValueByTagName(paramString1, "TYPE");
				String str12 = getValueByTagName(paramString1, "SE_F");
				HashMap localHashMap = new HashMap();
				localHashMap.put("startPagingNum", Integer.valueOf(j));
				localHashMap.put("endPagingNum", Integer.valueOf(k));
				localHashMap.put(this.tradeKeyMap.get("PRI"), str10 == null ? "" : str10);
				localHashMap.put(this.tradeKeyMap.get("TYPE"), str11 == null ? "0" : str11);
				localHashMap.put(this.tradeKeyMap.get("SE_F"), str12 == null ? "0" : str12);
				List localList1 = localOrdersPagingManager.tradepagingquery(l1, localTrader, localSortCondition, localHashMap);
				List localList2 = localOrdersPagingManager.totalDateQuery("tradepagingquery", localPrivilege, localHashMap);
				if ((localList1 == null) || (localList1.size() == 0)) {
					this.log.debug("====> linkedlist has no found");
					i = -202;
					str1 = this.properties.getProperty("-209");
				} else {
					this.log.debug("===>last_trade_id = " + l1);
					paramResponseResult.setResultList(localList1);
				}
				if ((localList2 != null) && (localList2.size() > 0)) {
					TotalDate localTotalDate = (TotalDate) localList2.get(0);
					paramResponseResult.setTotalDate(localTotalDate);
				}
			}
		} catch (ConnectException localConnectException) {
			this.log.error("tradequery rmi conection exception" + localConnectException);
			i = -202;
			str1 = this.properties.getProperty("-201");
			initRMI();
		} catch (RemoteException localRemoteException) {
			this.log.error("tradequery remoteerror:" + localRemoteException);
			errorException(localRemoteException);
			i = -204;
			str1 = this.properties.getProperty("-204");
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("tradequery error:" + localException);
			errorException(localException);
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		renderXML(paramHttpServletResponse, ResponseXml.tradepagingquery(paramResponseResult));
	}

	public void initTradeKeyMap() {
		this.tradeKeyMap = new HashMap();
		this.tradeKeyMap.put("TR_N", "A_TradeNo");
		this.tradeKeyMap.put("OR_N", "A_OrderNo");
		this.tradeKeyMap.put("TIME", "TradeTime");
		this.tradeKeyMap.put("TYPE", "BS_Flag");
		this.tradeKeyMap.put("SE_F", "OrderType");
		this.tradeKeyMap.put("TR_I", "TraderID");
		this.tradeKeyMap.put("FI_I", "FirmID");
		this.tradeKeyMap.put("CU_I", "CustomerID");
		this.tradeKeyMap.put("PRI", "CommodityID");
		this.tradeKeyMap.put("TR_P", "Price");
		this.tradeKeyMap.put("OR_P", "HoldPrice");
		this.tradeKeyMap.put("QTY", "Quantity");
		this.tradeKeyMap.put("LIQPL", "Close_PL");
		this.tradeKeyMap.put("COMM", "TradeFee");
	}
}
