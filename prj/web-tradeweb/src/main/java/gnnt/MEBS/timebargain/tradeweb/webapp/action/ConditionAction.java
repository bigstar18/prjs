package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.plugin.condition.rmi.ConditionRMI;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.TotalDate;
import gnnt.MEBS.timebargain.tradeweb.service.ConditionOrderManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersPagingManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class ConditionAction extends HttpXmlServlet {
	private static final long serialVersionUID = -1824445401243449425L;
	private final Log log = LogFactory.getLog(HttpXmlServlet.class);
	private HashMap conditionKeyMap;

	public ConditionAction() {
		initConditionKeyMap();
	}

	public void conditionOrderQuery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString,
			ResponseResult paramResponseResult) {
		this.log.info("------------> conditionOrderQuery ....");
		int i = 0;
		String str1 = "";
		String str2 = "";
		long l = 0L;
		try {
			str2 = getValueByTagName(paramString, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString, "TRADER_ID");
			}
			Privilege localPrivilege = (Privilege) paramHttpServletRequest.getSession().getAttribute("privilege");
			Object localObject1;
			Object localObject2;
			if (localPrivilege == null) {
				localObject1 = tradeRMI.getTraderInfo(str2);
				localObject2 = (OrdersManager) getBean("ordersManager");
				localPrivilege = ((OrdersManager) localObject2).getradePrivilege((TraderLogonInfo) localObject1);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
			} else {
				localObject1 = (ConditionOrderManager) getBean("conditionOrderManager");
				localObject2 = getValueByTagName(paramString, "COMMODITY_ID");
				String str3 = paramHttpServletRequest.getParameter("BUY_SELL");
				String str4 = paramHttpServletRequest.getParameter("SE_F");
				String str5 = paramHttpServletRequest.getParameter("CONTYPE");
				String str6 = paramHttpServletRequest.getParameter("ORDER_S");
				String str7 = getValueByTagName(paramString, "UT");
				int j = (str7 == null) || ("0".equals(str7)) || ("".equals(str7.trim())) ? 1 : 0;
				String str8 = j != 0 ? "queryAll" : str7;
				List localList = ((ConditionOrderManager) localObject1).conditionOrder_query(localPrivilege, (String) localObject2, str3, str4, str5,
						str6, str8);
				System.out.println(localList.size());
				Timestamp localObject3 = null;
				for (int k = 0; k < localList.size(); k++) {
					Timestamp localTimestamp = null;
					Map localMap = (Map) localList.get(k);
					if (localMap.containsKey("UpdateTime")) {
						localTimestamp = (Timestamp) localMap.get("UpdateTime");
					}
					if (localObject3 == null) {
						localObject3 = localTimestamp;
					} else if (localObject3.before(localTimestamp)) {
						localObject3 = localTimestamp;
					}
				}
				l = localObject3 == null ? Long.parseLong("".equals(str7) ? "0" : str7) : localObject3.getTime();
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
		} catch (Exception localException) {
			localException.printStackTrace();
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		renderXML(paramHttpServletResponse, ResponseXml.conditionOrderQuery(paramResponseResult, l));
	}

	public void conditionOrderPageQuery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		this.log.info("------------> conditionOrderQuery ....");
		int i = 0;
		String str1 = "";
		String str2 = "";
		long l = 0L;
		try {
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			Privilege localPrivilege = (Privilege) paramHttpServletRequest.getSession().getAttribute("privilege");
			Object localObject1;
			Object localObject2;
			if (localPrivilege == null) {
				localObject1 = tradeRMI.getTraderInfo(str2);
				localObject2 = (OrdersManager) getBean("ordersManager");
				localPrivilege = ((OrdersManager) localObject2).getradePrivilege((TraderLogonInfo) localObject1);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
			} else {
				localObject1 = getValueByTagName(paramString1, "COMMODITY_ID");
				localObject2 = getValueByTagName(paramString1, "BUY_SELL");
				String str3 = getValueByTagName(paramString1, "SE_F");
				String str4 = getValueByTagName(paramString1, "CONTYPE");
				String str5 = getValueByTagName(paramString1, "ORDER_S");
				String str6 = getValueByTagName(paramString1, "UT");
				int j = (str6 == null) || ("0".equals(str6)) || ("".equals(str6.trim())) ? 1 : 0;
				String str7 = j != 0 ? "queryAll" : str6;
				String str8 = getValueByTagName(paramString1, "ISDESC") == null ? "1" : getValueByTagName(paramString1, "ISDESC");
				String str9 = getValueByTagName(paramString1, "SORTFLD");
				String str10 = getValueByTagName(paramString1, "RECCNT");
				String str11 = getValueByTagName(paramString1, "PAGENUM");
				if ((str10 == null) || ("".endsWith(str10)) || ("0".endsWith(str10))) {
					str10 = "10000";
				}
				int k = (str10 == null ? 0 : Integer.valueOf(str10).intValue()) * (str11 == null ? 0 : Integer.valueOf(str11).intValue() - 1);
				if (k < 0) {
					k = 0;
				}
				int m = k + (str10 == null ? 0 : Integer.valueOf(str10).intValue());
				HashMap localHashMap = new HashMap();
				localHashMap.put("startPagingNum", Integer.valueOf(k));
				localHashMap.put("endPagingNum", Integer.valueOf(m));
				localHashMap.put("CommodityID", localObject1);
				localHashMap.put("BS_Flag", localObject2);
				localHashMap.put("OrderType", str3);
				localHashMap.put("conditionType", str4);
				localHashMap.put("SendStatus", str5);
				localHashMap.put("UpdateTime", str7);
				SortCondition localSortCondition = new SortCondition();
				localSortCondition.setIsdesc(Integer.parseInt(str8));
				localSortCondition.setReccnt(str10);
				localSortCondition.setSortfLD((String) this.conditionKeyMap.get(str9));
				ConditionOrderManager localConditionOrderManager = (ConditionOrderManager) getBean("conditionOrderManager");
				List localList1 = localConditionOrderManager.conditionOrderPageQuery(localPrivilege, localSortCondition, localHashMap);
				List localList2 = localConditionOrderManager.selectCondittion();
				OrdersPagingManager localOrdersPagingManager = (OrdersPagingManager) getBean("ordersPagingManager");
				List localList3 = localOrdersPagingManager.totalDateQuery("conditionorder_query", localPrivilege, localHashMap);
				Timestamp localObject3 = null;
				Timestamp localObject4 = null;
				Timestamp localTimestamp1 = null;
				Timestamp localTimestamp2 = null;
				int n;
				Map localMap;
				if (str7.equals("1")) {
					for (n = 0; n < localList2.size(); n++) {
						localMap = (Map) localList2.get(n);
						if ((Timestamp) localMap.get("SuccessTime") != null) {
							if (localMap.containsKey("SuccessTime")) {
								localTimestamp2 = (Timestamp) localMap.get("SuccessTime");
							}
							if (localMap.containsKey("UpdateTime")) {
								localTimestamp1 = (Timestamp) localMap.get("UpdateTime");
							}
							if (localObject4 == null) {
								localObject4 = localTimestamp2;
							} else if (localObject4.before(localTimestamp2)) {
								localObject4 = localTimestamp2;
							}
						} else {
							if (localMap.containsKey("UpdateTime")) {
								localTimestamp1 = (Timestamp) localMap.get("UpdateTime");
							}
							if (localObject4 != null) {
								if (localObject4.before(localTimestamp1)) {
									localObject4 = localTimestamp1;
								}
							} else {
								localObject4 = localTimestamp1;
							}
						}
						if (localObject3 == null) {
							localObject3 = localObject4;
						} else if (localObject3.before(localObject4)) {
							localObject3 = localObject4;
						}
					}
				} else {
					for (n = 0; n < localList1.size(); n++) {
						localMap = (Map) localList1.get(n);
						if ((Timestamp) localMap.get("SuccessTime") != null) {
							if (localMap.containsKey("SuccessTime")) {
								localTimestamp2 = (Timestamp) localMap.get("SuccessTime");
							}
							if (localMap.containsKey("UpdateTime")) {
								localTimestamp1 = (Timestamp) localMap.get("UpdateTime");
							}
							if (localObject4 == null) {
								localObject4 = localTimestamp2;
							} else if (localObject4.before(localTimestamp2)) {
								localObject4 = localTimestamp2;
							}
						} else {
							if (localMap.containsKey("UpdateTime")) {
								localTimestamp1 = (Timestamp) localMap.get("UpdateTime");
							}
							if (localObject4 != null) {
								if (localObject4.before(localTimestamp1)) {
									localObject4 = localTimestamp1;
								}
							} else {
								localObject4 = localTimestamp1;
							}
						}
						if (localObject3 == null) {
							localObject3 = localObject4;
						} else if (localObject3.before(localObject4)) {
							localObject3 = localObject4;
						}
					}
					if (localObject3 == null) {
						localObject3 = localObject4;
					} else if (localObject3.before(localObject4)) {
						localObject3 = localObject4;
					}
				}
				l = localObject3 == null ? Long.parseLong("".equals(str6) ? "0" : str6) : localObject3.getTime();
				if (localList1 == null) {
					i = -202;
					str1 = this.properties.getProperty("-208");
				} else if (localList1.size() == 0) {
					i = 0;
					str1 = this.properties.getProperty("-200");
				} else {
					paramResponseResult.setResultList(localList1);
				}
				if ((localList3 != null) && (localList3.size() > 0)) {
					TotalDate localTotalDate = (TotalDate) localList3.get(0);
					paramResponseResult.setTotalDate(localTotalDate);
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		renderXML(paramHttpServletResponse, ResponseXml.conditionOrderPageQuery(paramResponseResult, l));
	}

	public void conditionOrder(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		this.log.info("------------->conditionOrder");
		int i = 0;
		String str1 = "";
		String str2 = "";
		try {
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			Privilege localPrivilege = (Privilege) paramHttpServletRequest.getSession().getAttribute("privilege");
			Short localShort1 = new Short(getValueByTagName(paramString1, "BUY_SELL"));
			Short localShort2 = new Short(getValueByTagName(paramString1, "SETTLE_BASIS"));
			Double localDouble = new Double(getValueByTagName(paramString1, "PRICE"));
			String str3 = getValueByTagName(paramString1, "COMMODITY_ID");
			String str4 = getValueByTagName(paramString1, "CUSTOMER_ID");
			Object localObject1;
			Object localObject2;
			if (localPrivilege == null) {
				localObject1 = tradeRMI.getTraderInfo(str2);
				localObject2 = (OrdersManager) getBean("ordersManager");
				localPrivilege = ((OrdersManager) localObject2).getradePrivilege((TraderLogonInfo) localObject1);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
			}
			if (!PrivilegeController.checkDirectTradePrivilege(localPrivilege, str3, localShort1, localShort2)) {
				paramResponseResult.setRetCode(-230);
				paramResponseResult.setMessage(this.properties.getProperty("-221"));
			} else if (localDouble.doubleValue() <= 0.0D) {
				paramResponseResult.setRetCode(-231);
				paramResponseResult.setMessage(this.properties.getProperty("-222"));
			} else if (!PrivilegeController.checkBreedPrivilege(localPrivilege, localShort2, str3)) {
				paramResponseResult.setRetCode(-225);
				paramResponseResult.setMessage(this.properties.getProperty("-223"));
			} else if (!PrivilegeController.checkTraderPrvg(localPrivilege, localShort1, localShort2, str3)) {
				paramResponseResult.setRetCode(-221);
				paramResponseResult.setMessage(this.properties.getProperty("-224"));
			} else if (!PrivilegeController.checkFBreedPrivilege(localPrivilege, localShort1, localShort2, str3)) {
				paramResponseResult.setRetCode(-226);
				paramResponseResult.setMessage(this.properties.getProperty("-225"));
			} else if (!PrivilegeController.checkCommPrivilege(localPrivilege, localShort1, localShort2, str3)) {
				paramResponseResult.setRetCode(-227);
				paramResponseResult.setMessage(this.properties.getProperty("-226"));
			} else if (!PrivilegeController.checkFCodePrivilege(localPrivilege, localShort1, localShort2, str3)) {
				paramResponseResult.setRetCode(-228);
				paramResponseResult.setMessage(this.properties.getProperty("-227"));
			} else if (!PrivilegeController.checkCusBreedPrivilege(localPrivilege, localShort1, localShort2, str4, str3)) {
				paramResponseResult.setRetCode(-229);
				paramResponseResult.setMessage(this.properties.getProperty("-228"));
			} else if (!PrivilegeController.checkCusCommPrivilege(localPrivilege, localShort1, localShort2, str4, str3)) {
				paramResponseResult.setRetCode(-229);
				paramResponseResult.setMessage(this.properties.getProperty("-229"));
			} else {
				localObject1 = getValueByTagName(paramString1, "FIRM_ID");
				localObject2 = getValueByTagName(paramString1, "TRADER_ID");
				String str5 = getValueByTagName(paramString1, "QTY");
				String str6 = getValueByTagName(paramString1, "CONCOMMODITYID");
				String str7 = getValueByTagName(paramString1, "CONTYPE");
				String str8 = getValueByTagName(paramString1, "CONOPERATOR");
				String str9 = getValueByTagName(paramString1, "CONPRICE");
				String str10 = getValueByTagName(paramString1, "CONEXPIRE");
				SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				ConditionOrder localConditionOrder = new ConditionOrder();
				localConditionOrder.setCustomerID((String) localObject1 + "00");
				localConditionOrder.setTraderID((String) localObject2);
				localConditionOrder.setFirmID((String) localObject1);
				localConditionOrder.setCmtyID(str3);
				localConditionOrder.setBs_flag(localShort1);
				localConditionOrder.setOrderType(localShort2);
				localConditionOrder.setPrice(localDouble);
				localConditionOrder.setAmount(Long.valueOf(Long.parseLong(str5)));
				localConditionOrder.setConditionCmtyID(str6);
				localConditionOrder.setConditionType(Integer.valueOf(Integer.parseInt(str7)));
				localConditionOrder.setConditionOperation(Integer.valueOf(Integer.parseInt(str8)));
				localConditionOrder.setConditionPrice(Double.valueOf(Double.parseDouble(str9)));
				localConditionOrder.setEndDate(localSimpleDateFormat.parse(str10));
				int j = submitConditionOrder(1, localConditionOrder);
				if (j == 0) {
					i = 0;
					str1 = this.properties.getProperty("20");
				} else if (j == -1) {
					i = -1;
					str1 = this.properties.getProperty("21");
				} else if (j == -202) {
					i = j;
					str1 = this.properties.getProperty("-202");
				} else if (j == -203) {
					i = j;
					str1 = this.properties.getProperty("-203");
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		renderXML(paramHttpServletResponse,
				ResponseXml.responseXml(str2, paramResponseResult.getName(), paramResponseResult.getRetCode(), paramResponseResult.getMessage()));
	}

	public void withDrawConditionOrder(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1,
			ResponseResult paramResponseResult, String paramString2) {
		this.log.info("------------->withDrawConditionOrder");
		int i = 0;
		String str1 = "";
		String str2 = "";
		try {
			str2 = getValueByTagName(paramString1, "USER_ID");
			if ("".equals(str2)) {
				str2 = getValueByTagName(paramString1, "TRADER_ID");
			}
			international((String) lanaguages.get(str2));
			Privilege localPrivilege = (Privilege) paramHttpServletRequest.getSession().getAttribute("privilege");
			Object localObject1;
			if (localPrivilege == null) {
				localObject1 = tradeRMI.getTraderInfo(str2);
				OrdersManager localObject2 = (OrdersManager) getBean("ordersManager");
				localPrivilege = ((OrdersManager) localObject2).getradePrivilege((TraderLogonInfo) localObject1);
				paramHttpServletRequest.getSession().setAttribute("privilege", localPrivilege);
			} else {
				localObject1 = getValueByTagName(paramString1, "ORDER_NO");
				String[] localObject2 = ((String) localObject1).split("-");
				ConditionOrderManager localConditionOrderManager = (ConditionOrderManager) getBean("conditionOrderManager");
				int j = 0;
				if ((localObject2 != null) && (localObject2.length > 0)) {
					for (int k = 0; k < localObject2.length; k++) {
						ConditionOrder localConditionOrder = localConditionOrderManager.singl_order_query(localObject2[k]);
						j = submitConditionOrder(0, localConditionOrder);
						if (j == 0) {
							i = 0;
							str1 = this.properties.getProperty("22");
						} else if (j == -1) {
							i = -1;
							str1 = this.properties.getProperty("23");
						} else if (j == -2) {
							i = -2;
							str1 = this.properties.getProperty("25");
						} else if (j == -202) {
							i = j;
							str1 = this.properties.getProperty("-202");
						} else if (j == -203) {
							i = j;
							str1 = this.properties.getProperty("-203");
						}
						this.log.info("request checkBox:" + localObject2[k] + ",msg=" + str1);
					}
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			i = -203;
			str1 = this.properties.getProperty("-203");
		}
		paramResponseResult.setRetCode(i);
		paramResponseResult.setMessage(str1);
		paramResponseResult.setUserID(str2);
		renderXML(paramHttpServletResponse,
				ResponseXml.responseXml(str2, paramResponseResult.getName(), paramResponseResult.getRetCode(), paramResponseResult.getMessage()));
	}

	public void initConditionKeyMap() {
		this.conditionKeyMap = new HashMap();
		this.conditionKeyMap.put("CO_N", "A_OrderNo");
		this.conditionKeyMap.put("PRI", "CommodityID");
		this.conditionKeyMap.put("STA", "SendStatus");
		this.conditionKeyMap.put("TYPE", "BS_Flag");
		this.conditionKeyMap.put("SE_F", "OrderType");
		this.conditionKeyMap.put("CO_P", "Price");
		this.conditionKeyMap.put("QTY", "Quantity");
		this.conditionKeyMap.put("CO_PRI", "ConditionCommodityID");
		this.conditionKeyMap.put("CO_PRICE", "ConditionPrice");
		this.conditionKeyMap.put("CO_PT", "UpdateTime");
		this.conditionKeyMap.put("CO_MT", "ValidDate");
		this.conditionKeyMap.put("CO_OT", "SuccessTime");
		this.conditionKeyMap.put("CO_T", "ConditionType");
		this.conditionKeyMap.put("CO_S", "ConditionOperation");
	}

	public int submitConditionOrder(int paramInt, ConditionOrder paramConditionOrder) throws Exception {
		int i = -250;
		try {
			this.log.info("------------>ready for submitConditionOrder.......");
			this.conditionRMI = getConditionRMI();
			if (paramInt == 0) {
				i = this.conditionRMI.cancelOrder(paramConditionOrder);
			} else {
				i = this.conditionRMI.receiveOrder(paramConditionOrder);
			}
			this.log.info("------------>submitConditionOrder over....ret=" + i);
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
			i = -202;
			initConditionRMI();
		} catch (Exception localException) {
			localException.printStackTrace();
			errorException(localException);
			i = -203;
		}
		return i;
	}

	public ConditionRMI getConditionRMI() {
		if (this.conditionRMI == null) {
			initConditionRMI();
		}
		return this.conditionRMI;
	}

	public void initConditionRMI() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("rmi://").append(host).append(":").append(port).append("/ConditionRMI");
		this.log.debug("conditionRMI: " + localStringBuffer.toString());
		String str = localStringBuffer.toString();
		try {
			this.conditionRMI = ((ConditionRMI) Naming.lookup(str));
		} catch (MalformedURLException localMalformedURLException) {
			localMalformedURLException.printStackTrace();
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		} catch (NotBoundException localNotBoundException) {
			localNotBoundException.printStackTrace();
		}
	}

	public int selectConditionOrderCount(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString,
			ResponseResult paramResponseResult) {
		ConditionOrderManager localConditionOrderManager = (ConditionOrderManager) getBean("conditionOrderManager");
		String str1 = "";
		String str2 = getValueByTagName(paramString, "USER_ID");
		if ((paramString.contains("<UT>")) && (paramString.contains("</UT>"))) {
			str1 = getValueByTagName(paramString, "UT");
			return localConditionOrderManager.selectConditionOrdeCount(str1, str2);
		}
		return 0;
	}

	public int selectConditionOrderCountFromCache(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
			String paramString, ResponseResult paramResponseResult) {
		ConditionOrderManager localConditionOrderManager = (ConditionOrderManager) getBean("conditionOrderManager");
		String str1 = "";
		String str2 = getValueByTagName(paramString, "USER_ID");
		if ((paramString.contains("<UT>")) && (paramString.contains("</UT>"))) {
			str1 = getValueByTagName(paramString, "UT");
			return localConditionOrderManager.selectConditionOrderCountFromCache(str1, str2);
		}
		return 0;
	}

	public void getRmiConf(int paramInt) {
		ConditionOrderManager localConditionOrderManager = (ConditionOrderManager) getBean("conditionOrderManager");
		List localList = localConditionOrderManager.getRmiConf(paramInt);
		HttpXmlServlet.host = ((Map) localList.get(0)).get("HOSTIP").toString();
		HttpXmlServlet.port = ((Map) localList.get(0)).get("PORT").toString();
	}
}
