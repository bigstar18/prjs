package gnnt.MEBS.timebargain.manage.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import gnnt.MEBS.timebargain.manage.service.CommodityManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.manage.service.OrdersManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.service.TradeRuleManager;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.util.DateUtil;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.form.AgencyForm;
import gnnt.MEBS.timebargain.manage.webapp.util.ExecRunner;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;
import gnnt.MEBS.timebargain.server.model.SystemStatus;

public class AgencyAction extends BaseAction {
	public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit' method");
		}
		try {
			getSelectAttribute(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("edit");
	}

	public ActionForward edit2(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit2' method");
		}
		try {
			StatQueryManager localStatQueryManager = (StatQueryManager) getBean("statQueryManager");
			String str1 = localStatQueryManager.getSystemStatus();
			paramHttpServletRequest.setAttribute("status", str1);
			String str2 = getServlet().getServletContext().getInitParameter("useDelay");
			paramHttpServletRequest.setAttribute("useDelay", str2);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("edit2");
	}

	public ActionForward edit3(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit3' method");
		}
		try {
			StatQueryManager localStatQueryManager = (StatQueryManager) getBean("statQueryManager");
			SystemStatus localSystemStatus = localStatQueryManager.getSystemStatusObject();
			int i = localSystemStatus.getStatus();
			if (i == 0) {
				paramHttpServletRequest.setAttribute("result", "0");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[0]);
			}
			if (i == 1) {
				paramHttpServletRequest.setAttribute("result", "1");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[1]);
			}
			if (i == 2) {
				paramHttpServletRequest.setAttribute("result", "2");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[2]);
			}
			if (i == 3) {
				paramHttpServletRequest.setAttribute("result", "3");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[3]);
			}
			if (i == 4) {
				paramHttpServletRequest.setAttribute("result", "4");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[4]);
			}
			if (i == 5) {
				paramHttpServletRequest.setAttribute("result", "5");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[5]);
			}
			if (i == 6) {
				paramHttpServletRequest.setAttribute("result", "6");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[6]);
			}
			if (i == 7) {
				paramHttpServletRequest.setAttribute("result", "7");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[7]);
			}
			if (i == 8) {
				paramHttpServletRequest.setAttribute("result", "8");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[8]);
			}
			if (i == 9) {
				paramHttpServletRequest.setAttribute("result", "9");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[9]);
			}
			if (i == 10) {
				paramHttpServletRequest.setAttribute("result", "10");
				paramHttpServletRequest.setAttribute("result2", gnnt.MEBS.timebargain.manage.util.Constants2.SYSTEM_STATUS[10]);
			}
			Date localDate = localSystemStatus.getTradeDate();
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (localDate != null) {
				String localObject1 = localSimpleDateFormat.format(localDate);
				paramHttpServletRequest.setAttribute("date", localObject1);
			} else {
				paramHttpServletRequest.setAttribute("date", "");
			}
			Object localObject1 = localSystemStatus.getSectionID();
			if (localObject1 != null) {
				paramHttpServletRequest.setAttribute("sectionID", ((Integer) localObject1).toString());
			} else {
				paramHttpServletRequest.setAttribute("sectionID", "");
			}
			String str1 = localSystemStatus.getNote();
			if ((str1 != null) && (!"".equals(str1))) {
				paramHttpServletRequest.setAttribute("note", str1);
			} else {
				paramHttpServletRequest.setAttribute("note", "");
			}
			MarketManager localMarketManager = (MarketManager) getBean("marketManager");
			List localList = localMarketManager.getMarketById(null);
			if ((localList != null) && (localList.size() > 0)) {
				Map localObject2 = (Map) localList.get(0);
				if (((Map) localObject2).get("RunMode") != null) {
					String str2 = "";
					if ("0".equals(((Map) localObject2).get("RunMode").toString())) {
						str2 = "手动";
					} else if ("1".equals(((Map) localObject2).get("RunMode").toString())) {
						str2 = "自动";
					}
					paramHttpServletRequest.setAttribute("RunMode", str2);
				}
			}
			if ((localSystemStatus != null) && (localSystemStatus.getRecoverTime() != null) && (!"".equals(localSystemStatus.getRecoverTime()))) {
				String localObject2 = localSystemStatus.getRecoverTime();
				paramHttpServletRequest.setAttribute("recoverTime2", localObject2);
			}
			Object localObject2 = localStatQueryManager.getSysdate();
			paramHttpServletRequest.setAttribute("sysdate", localObject2);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("edit3");
	}

	public ActionForward editMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editMargin' method");
		}
		return paramActionMapping.findForward("editMargin");
	}

	public ActionForward saveMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveMargin' method");
		}
		String str1 = paramHttpServletRequest.getParameter("commodityID");
		String str2 = paramHttpServletRequest.getParameter("marginRate_B");
		String str3 = paramHttpServletRequest.getParameter("marginRate_S");
		String str4 = paramHttpServletRequest.getParameter("marginAssure_B");
		String str5 = paramHttpServletRequest.getParameter("marginAssure_S");
		String str6 = paramHttpServletRequest.getParameter("marginAlgr");
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		Commodity localCommodity = new Commodity();
		try {
			if ((str1 != null) && (!"".equals(str1))) {
				localCommodity.setCommodityID(str1);
			}
			if ((str6 != null) && (!"".equals(str6)) && ("1".equals(str6))) {
				if ((str2 != null) && (!"".equals(str2))) {
					localCommodity.setMarginRate_B(Double.valueOf(Double.parseDouble(str2) / 100.0D));
				}
				if ((str3 != null) && (!"".equals(str3))) {
					localCommodity.setMarginRate_S(Double.valueOf(Double.parseDouble(str3) / 100.0D));
				}
				if ((str4 != null) && (!"".equals(str4))) {
					localCommodity.setMarginAssure_B(Double.valueOf(Double.parseDouble(str4) / 100.0D));
				}
				if ((str5 != null) && (!"".equals(str5))) {
					localCommodity.setMarginAssure_S(Double.valueOf(Double.parseDouble(str5) / 100.0D));
				}
			} else {
				if ((str2 != null) && (!"".equals(str2))) {
					localCommodity.setMarginRate_B(Double.valueOf(Double.parseDouble(str2)));
				}
				if ((str3 != null) && (!"".equals(str3))) {
					localCommodity.setMarginRate_S(Double.valueOf(Double.parseDouble(str3)));
				}
				if ((str4 != null) && (!"".equals(str4))) {
					localCommodity.setMarginAssure_B(Double.valueOf(Double.parseDouble(str4)));
				}
				if ((str5 != null) && (!"".equals(str5))) {
					localCommodity.setMarginAssure_S(Double.valueOf(Double.parseDouble(str5)));
				}
			}
			localCommodityManager.updateCommodityMargin(localCommodity);
			addSysLog(paramHttpServletRequest, "异常处理中修改商品[" + str1 + "]保证金");
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("saveMargin");
	}

	public ActionForward editOnlineMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editOnlineMargin' method");
		}
		String str1 = paramHttpServletRequest.getParameter("commodityID");
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
		paramHttpServletResponse.setHeader("Cache-Control", "no-store");
		paramHttpServletResponse.setHeader("Pragma", "no-cache");
		paramHttpServletResponse.setContentType("text/xml");
		paramHttpServletResponse.setCharacterEncoding("GBK");
		PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		String str2 = "";
		String str3 = "";
		String str4 = "";
		String str5 = "";
		String str6 = "";
		try {
			Commodity localCommodity = localCommodityManager.getCommodityById(str1);
			str6 = localCommodity.getMarginAlgr() + "";
			if ("1".equals(str6)) {
				str2 = Arith.mul((localCommodity.getMarginRate_B() == null ? new Double(0.0D) : localCommodity.getMarginRate_B()).doubleValue(),
						100.0F) + "";
				str3 = Arith.mul((localCommodity.getMarginRate_S() == null ? new Double(0.0D) : localCommodity.getMarginRate_S()).doubleValue(),
						100.0F) + "";
				str4 = Arith.mul((localCommodity.getMarginAssure_B() == null ? new Double(0.0D) : localCommodity.getMarginAssure_B()).doubleValue(),
						100.0F) + "";
				str5 = Arith.mul((localCommodity.getMarginAssure_S() == null ? new Double(0.0D) : localCommodity.getMarginAssure_S()).doubleValue(),
						100.0F) + "";
			} else {
				str2 = (localCommodity.getMarginRate_B() == null ? new Double(0.0D) : localCommodity.getMarginRate_B()) + "";
				str3 = (localCommodity.getMarginRate_S() == null ? new Double(0.0D) : localCommodity.getMarginRate_S()) + "";
				str4 = (localCommodity.getMarginAssure_B() == null ? new Double(0.0D) : localCommodity.getMarginAssure_B()) + "";
				str5 = (localCommodity.getMarginAssure_S() == null ? new Double(0.0D) : localCommodity.getMarginAssure_S()) + "";
			}
			localStringBuffer.append("<margin>");
			localStringBuffer.append("<marginRate_B>" + str2 + "</marginRate_B>");
			localStringBuffer.append("<marginRate_S>" + str3 + "</marginRate_S>");
			localStringBuffer.append("<marginAssure_B>" + str4 + "</marginAssure_B>");
			localStringBuffer.append("<marginAssure_S>" + str5 + "</marginAssure_S>");
			localStringBuffer.append("<marginAlgr>" + str6 + "</marginAlgr>");
			localStringBuffer.append("</margin>");
			localPrintWriter.flush();
			localPrintWriter.print(localStringBuffer);
			localPrintWriter.close();
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", "查询出错！");
		}
		return null;
	}

	public ActionForward editSpacMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editSpacMargin' method");
		}
		AgencyForm localAgencyForm = (AgencyForm) paramActionForm;
		String str1 = paramHttpServletRequest.getParameter("commodityID");
		String str2 = paramHttpServletRequest.getParameter("customerID");
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
		paramHttpServletResponse.setHeader("Cache-Control", "no-store");
		paramHttpServletResponse.setHeader("Pragma", "no-cache");
		paramHttpServletResponse.setContentType("text/xml");
		paramHttpServletResponse.setCharacterEncoding("GBK");
		PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		String str3 = "";
		String str4 = "";
		String str5 = "";
		String str6 = "";
		String str7 = "";
		try {
			TradeRuleGC localTradeRuleGC = localTradeRuleManager.getFirmMarginById(str2, str1);
			System.out.println("tradeRule.getMarginRate_B(): " + localTradeRuleGC.getMarginRate_B());
			str7 = localTradeRuleGC.getMarginAlgr() + "";
			if ("1".equals(str7)) {
				str3 = Arith.mul((localTradeRuleGC.getMarginRate_B() == null ? new Double(0.0D) : localTradeRuleGC.getMarginRate_B()).doubleValue(),
						100.0F) + "";
				str4 = Arith.mul((localTradeRuleGC.getMarginRate_S() == null ? new Double(0.0D) : localTradeRuleGC.getMarginRate_S()).doubleValue(),
						100.0F) + "";
				str5 = Arith.mul(
						(localTradeRuleGC.getMarginAssure_B() == null ? new Double(0.0D) : localTradeRuleGC.getMarginAssure_B()).doubleValue(),
						100.0F) + "";
				str6 = Arith.mul(
						(localTradeRuleGC.getMarginAssure_S() == null ? new Double(0.0D) : localTradeRuleGC.getMarginAssure_S()).doubleValue(),
						100.0F) + "";
			} else {
				str3 = (localTradeRuleGC.getMarginRate_B() == null ? new Double(0.0D) : localTradeRuleGC.getMarginRate_B()) + "";
				str4 = (localTradeRuleGC.getMarginRate_S() == null ? new Double(0.0D) : localTradeRuleGC.getMarginRate_S()) + "";
				str5 = (localTradeRuleGC.getMarginAssure_B() == null ? new Double(0.0D) : localTradeRuleGC.getMarginAssure_B()) + "";
				str6 = (localTradeRuleGC.getMarginAssure_S() == null ? new Double(0.0D) : localTradeRuleGC.getMarginAssure_S()) + "";
			}
			localAgencyForm.setMarginAlgr(str7);
			localAgencyForm.setMarginRate_B(str3);
			localAgencyForm.setMarginRate_S(str4);
			localAgencyForm.setMarginAssure_B(str5);
			localAgencyForm.setMarginAssure_S(str6);
			localStringBuffer.append("<margin>");
			localStringBuffer.append("<marginRate_B>" + str3 + "</marginRate_B>");
			localStringBuffer.append("<marginRate_S>" + str4 + "</marginRate_S>");
			localStringBuffer.append("<marginAssure_B>" + str5 + "</marginAssure_B>");
			localStringBuffer.append("<marginAssure_S>" + str6 + "</marginAssure_S>");
			localStringBuffer.append("<marginAlgr>" + str7 + "</marginAlgr>");
			localStringBuffer.append("</margin>");
			localPrintWriter.flush();
			localPrintWriter.print(localStringBuffer);
			localPrintWriter.close();
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", "此交易商不存在！");
		}
		return null;
	}

	public ActionForward saveSpacMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveSpacMargin' method");
		}
		String str1 = paramHttpServletRequest.getParameter("customerID");
		String str2 = paramHttpServletRequest.getParameter("commodityID");
		String str3 = paramHttpServletRequest.getParameter("marginRate_B");
		String str4 = paramHttpServletRequest.getParameter("marginRate_S");
		String str5 = paramHttpServletRequest.getParameter("marginAssure_B");
		String str6 = paramHttpServletRequest.getParameter("marginAssure_S");
		String str7 = paramHttpServletRequest.getParameter("marginAlgr");
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = new TradeRuleGC();
		try {
			if ((str2 != null) && (!"".equals(str2))) {
				localTradeRuleGC.setCommodityID(str2);
			}
			localTradeRuleGC.setFirmID(str1);
			if ("1".equals(str7)) {
				if ((str3 != null) && (!"".equals(str3))) {
					localTradeRuleGC.setMarginRate_B(Double.valueOf(Double.parseDouble(str3) / 100.0D));
				}
				if ((str4 != null) && (!"".equals(str4))) {
					localTradeRuleGC.setMarginRate_S(Double.valueOf(Double.parseDouble(str4) / 100.0D));
				}
				if ((str5 != null) && (!"".equals(str5))) {
					localTradeRuleGC.setMarginAssure_B(Double.valueOf(Double.parseDouble(str5) / 100.0D));
				}
				if ((str6 != null) && (!"".equals(str6))) {
					localTradeRuleGC.setMarginAssure_S(Double.valueOf(Double.parseDouble(str6) / 100.0D));
				}
			} else {
				if ((str3 != null) && (!"".equals(str3))) {
					localTradeRuleGC.setMarginRate_B(Double.valueOf(Double.parseDouble(str3)));
				}
				if ((str4 != null) && (!"".equals(str4))) {
					localTradeRuleGC.setMarginRate_S(Double.valueOf(Double.parseDouble(str4)));
				}
				if ((str5 != null) && (!"".equals(str5))) {
					localTradeRuleGC.setMarginAssure_B(Double.valueOf(Double.parseDouble(str5)));
				}
				if ((str6 != null) && (!"".equals(str6))) {
					localTradeRuleGC.setMarginAssure_S(Double.valueOf(Double.parseDouble(str6)));
				}
			}
			localTradeRuleManager.updateSpacMargin(localTradeRuleGC);
			addSysLog(paramHttpServletRequest, "异常处理中[" + str1 + "," + str2 + "]特殊保证金");
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", "此交易商不存在！");
		}
		return paramActionMapping.findForward("saveSpacMargin");
	}

	public ActionForward operate(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'operate' method");
		}
		AgencyForm localAgencyForm = (AgencyForm) paramActionForm;
		int i = 0;
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
			String str1 = paramHttpServletRequest.getParameter("recoverTime");
			String str2 = localAgencyForm.getType();
			if (str2 == null) {
				throw new Exception("操作类别不能为空！");
			}
			if (!str2.equals("03")) {
				if ("99".equals(str2)) {
					localAgencyRMIBean.timingContinueTrade(str1);
				} else if (str2.equals("04")) {
					localAgencyRMIBean.refreshMemory();
				} else if (str2.equals("05")) {
					localAgencyRMIBean.ctlTrade(0);
				} else if (str2.equals("06")) {
					localAgencyRMIBean.ctlTrade(1);
				} else {
					if (str2.equals("07")) {
						try {
							localAgencyRMIBean.close();
						} catch (Exception localException2) {
							localException2.printStackTrace();
							String[] localObject = localException2.getMessage().split(":");
							this.log.error("--->e.message:" + localException2.getMessage());
							paramHttpServletRequest.setAttribute("prompt", localObject[(localObject.length - 1)]);
						}
					} else if (str2.equals("08")) {
						try {
							localAgencyRMIBean.start();
						} catch (Exception localException3) {
							StatQueryManager localObject = (StatQueryManager) getBean("statQueryManager");
							SystemStatus localSystemStatus = ((StatQueryManager) localObject).getSystemStatusObject();
							throw new RuntimeException(localSystemStatus.getNote());
						}
					} else if (str2.equals("09")) {
						i = 1;
						localAgencyRMIBean.tradeEnd();
					} else if (!str2.equals("11")) {
						if ("12".equals(str2)) {
							localAgencyRMIBean.recoverTrade();
						} else if ("13".equals(str2)) {
							int j = localAgencyRMIBean.tradingReComputeFunds();
							if (j == 1) {
								paramHttpServletRequest.setAttribute("prompt", "操作成功！");
							}
							if (j == -100) {
								paramHttpServletRequest.setAttribute("prompt", "执行存储失败！");
							}
							if (j == -204) {
								paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
							}
							if (j == -207) {
								paramHttpServletRequest.setAttribute("prompt", "交易系统不是暂停状态，不能调整！");
							}
						} else if ("online".equals(str2)) {
							try {
								localAgencyRMIBean.refreshTradeTime();
							} catch (Exception localException4) {
								throw new Exception("生效失败！");
							}
						} else if ("recoverDelayTrade".equals(str2)) {
							try {
								localAgencyRMIBean.recoverTradeDelay();
							} catch (Exception localException5) {
								localException5.printStackTrace();
								String[] localObject = localException5.getMessage().split(":");
								paramHttpServletRequest.setAttribute("prompt", localObject[(localObject.length - 1)]);
							}
						} else if ("onlineDelay".equals(str2)) {
							localAgencyRMIBean.refreshDelayTradeTime();
						} else {
							throw new Exception("找不到对应的操作类别！");
						}
					}
				}
			}
		} catch (NullPointerException localNullPointerException) {
			this.log.error("--->e.message:" + localNullPointerException.getMessage());
			localNullPointerException.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", "空指针异常");
		} catch (Exception localException1) {
			localException1.printStackTrace();
			this.log.error("--->e.message:" + localException1.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException1.getMessage());
		}
		return paramActionMapping.findForward("operate");
	}

	public ActionForward operate2(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'operate2' method");
		}
		AgencyForm localAgencyForm = (AgencyForm) paramActionForm;
		int i = 0;
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
			String str1 = paramHttpServletRequest.getParameter("recoverTime");
			String str2 = localAgencyForm.getType();
			if (str2 == null) {
				throw new Exception("操作类别不能为空！");
			}
			if (!str2.equals("03")) {
				if ("99".equals(str2)) {
					localAgencyRMIBean.timingContinueTrade(str1);
				} else if (str2.equals("04")) {
					localAgencyRMIBean.refreshMemory();
				} else if (str2.equals("05")) {
					localAgencyRMIBean.ctlTrade(0);
				} else if (str2.equals("06")) {
					localAgencyRMIBean.ctlTrade(1);
				} else if (str2.equals("07")) {
					localAgencyRMIBean.close();
				} else if (str2.equals("08")) {
					localAgencyRMIBean.start();
				} else if (str2.equals("09")) {
					i = 1;
					localAgencyRMIBean.tradeEnd();
				} else if (!str2.equals("11")) {
					if ("12".equals(str2)) {
						localAgencyRMIBean.recoverTrade();
					} else if ("13".equals(str2)) {
						int j = localAgencyRMIBean.tradingReComputeFunds();
						if (j == 1) {
							paramHttpServletRequest.setAttribute("prompt", "操作成功！");
						}
						if (j == -100) {
							paramHttpServletRequest.setAttribute("prompt", "执行存储失败！");
						}
						if (j == -204) {
							paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
						}
						if (j == -207) {
							paramHttpServletRequest.setAttribute("prompt", "交易系统不是暂停状态，不能调整！");
						}
					} else {
						throw new Exception("找不到对应的操作类别！");
					}
				}
			}
		} catch (NullPointerException localNullPointerException) {
			this.log.error("--->e.message:" + localNullPointerException.getMessage());
			localNullPointerException.printStackTrace();
			paramHttpServletRequest.setAttribute("prompt", "空指针异常");
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("--->e.message:" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("operate2");
	}

	public ActionForward marketStatus_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'marketStatus_list' method");
		}
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("--->e.message:" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("marketStatus");
	}

	public ActionForward customer_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'customer_list' method");
		}
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
			List localList = localAgencyRMIBean.getConsigners();
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				Map localMap = (Map) localIterator.next();
				if ("gnnt_condi".equals(localMap.get("consignerID"))) {
					localIterator.remove();
				}
			}
			this.log.debug("----customer.size:" + localList.size());
			paramHttpServletRequest.setAttribute("customerList", localList);
		} catch (Exception localException) {
			this.log.error("--->e.message:" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("customer");
	}

	public ActionForward getNotMarketCodeOrders(ActionMapping paramActionMapping, ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'getNotMarketCodeOrders' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		try {
			paramHttpServletRequest.setAttribute("NotMarketCodeOrders", localOrdersManager.getNotMarketCodeOrders());
			paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			paramHttpServletRequest.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
			paramHttpServletRequest.setAttribute("CLOSEMODE", CommonDictionary.CLOSEMODE);
			paramHttpServletRequest.setAttribute("TIMEFLAG", CommonDictionary.TIMEFLAG);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("getNotMarketCodeOrders");
	}

	public ActionForward updateMarketOrderNo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'updateMarketOrderNo' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		try {
			localOrdersManager.updateMarketOrderNo(Long.valueOf(paramHttpServletRequest.getParameter("A_OrderNo")),
					Long.valueOf(paramHttpServletRequest.getParameter("M_OrderNo")));
			rendText(paramHttpServletResponse, "更新市场委托号成功！");
		} catch (Exception localException) {
			this.log.error("===>updateMarketOrderNo err：" + localException);
			rendText(paramHttpServletResponse, "更新失败，原因：" + localException.getMessage());
		}
		return null;
	}

	public ActionForward receiveMonitor_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'receiveMonitor_list' method");
		}
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("--->e.message:" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("receiveMonitor");
	}

	public ActionForward getAgencyStatus(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'getAgencyStatus' method");
		}
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
			String[] arrayOfString = { "交易暂停状态", "交易正常状态", "正在运行状态", "已关闭状态" };
		} catch (Exception localException) {
			this.log.error("===>getAgencyStatus err：" + localException);
			rendText(paramHttpServletResponse, "请求下单服务器状态失败，原因：" + localException.getMessage());
		}
		return null;
	}

	public ActionForward getWithdrawQueue(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'getWithdrawQueue' method");
		}
		try {
			AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
		} catch (Exception localException) {
			this.log.error("--->e.message:" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("getWithdrawQueue");
	}

	public ActionForward queryWithdrawReason(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'queryWithdrawReason' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		try {
			paramHttpServletRequest.setAttribute("queryWithdrawReason", localOrdersManager.queryWithdrawReason());
			paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
			paramHttpServletRequest.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
			paramHttpServletRequest.setAttribute("FAILCODE", CommonDictionary.FAILCODE);
			paramHttpServletRequest.setAttribute("WITHDRAWTYPE", CommonDictionary.WITHDRAWTYPE);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("queryWithdrawReason");
	}

	public ActionForward procLog_top(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'procLog_top' method");
		}
		paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
		return paramActionMapping.findForward("procLog_top");
	}

	public ActionForward procLog_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'procLog_list' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
		try {
			paramHttpServletRequest.setAttribute("procLogList", localOrdersManager.procLog(localQueryConditions));
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("procLog_list");
	}

	public ActionForward procLog_delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'procLog_delete' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
		try {
			localOrdersManager.deleteProcLog(localQueryConditions);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return procLog_list(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward sysLog_top(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'sysLog_top' method");
		}
		paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
		return paramActionMapping.findForward("sysLog_top");
	}

	public ActionForward sysLog_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'sysLog_list' method");
		}
		String str = paramHttpServletRequest.getParameter("querylogtype");
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
		try {
			paramHttpServletRequest.setAttribute("sysLogList", localOrdersManager.sysLog(localQueryConditions, str));
			paramHttpServletRequest.setAttribute("LOGTYPE", CommonDictionary.LOGTYPE);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("sysLog_list");
	}

	public ActionForward sysLog_delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'sysLog_delete' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
		try {
			localOrdersManager.deleteSysLog(localQueryConditions);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return sysLog_list(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward searchTradeRespond(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchTradeRespond' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		try {
			List localList = localOrdersManager.getTradeResponds();
			paramHttpServletRequest.setAttribute("tradeRespondList", localList);
		} catch (Exception localException) {
			this.log.error("查询出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("listTradeRespond");
	}

	public ActionForward backupdb_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'backupdb_list' method");
		}
		try {
			ArrayList localArrayList = new ArrayList();
			String str1 = File.separator;
			String str2 = getServlet().getServletContext().getInitParameter("DbBackupPath");
			File[] arrayOfFile = new File(str2).listFiles();
			for (int i = 0; i < arrayOfFile.length; i++) {
				for (int j = i; j < arrayOfFile.length; j++) {
					if (arrayOfFile[i].lastModified() - arrayOfFile[j].lastModified() < 0L) {
						File localFile = arrayOfFile[i];
						arrayOfFile[i] = arrayOfFile[j];
						arrayOfFile[j] = localFile;
					}
				}
			}
			for (int i = 0; i < arrayOfFile.length; i++) {
				HashMap localHashMap = new HashMap();
				localHashMap.put("name", arrayOfFile[i].getName());
				localHashMap.put("length", new Long(arrayOfFile[i].length()));
				localArrayList.add(localHashMap);
			}
			paramHttpServletRequest.setAttribute("backupdbList", localArrayList);
			paramHttpServletRequest.setAttribute("path", str2);
		} catch (Exception localException) {
			this.log.error("--->e.message:" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("listBackupdb");
	}

	public ActionForward backupdbDel(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'backupdbDel' method");
		}
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString != null) {
			this.log.debug("==ids.length:" + arrayOfString.length);
			String str2 = "";
			for (int j = 0; j < arrayOfString.length; j++) {
				String str1 = arrayOfString[j];
				try {
					String str3 = getServlet().getServletContext().getInitParameter("DbBackupPath");
					File localFile = new File(str3 + "/" + str1);
					localFile.delete();
					addSysLog(paramHttpServletRequest, "删除数据库备份[" + str1 + "]");
					i++;
				} catch (Exception localException) {
					str2 = str2 + str1 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]删除失败！");
				}
			}
			if (!str2.equals("")) {
				str2 = str2.substring(0, str2.length() - 1);
				str2 = str2 + "不能删除！";
			}
			str2 = str2 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str2);
		}
		return backupdb_list(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward backupdbcopy(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'backupdbcopy' method");
		}
		int i = 0;
		String str1 = "";
		try {
			String str2 = paramHttpServletRequest.getSession().getServletContext().getRealPath("/WEB-INF/");
			ExecRunner localExecRunner = new ExecRunner();
			localExecRunner.setMaxRunTimeSecs(120);
			localExecRunner.exec("/bin/sh " + str2 + "/expdata.sh");
			str1 = str1 + "手工备份成功";
		} catch (Exception localException) {
			this.log.error("--->e.message:" + localException.getMessage());
			str1 = str1 + "手工备份不成功";
		}
		paramHttpServletRequest.setAttribute("prompt", str1);
		return backupdb_list(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward traderLog_list(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'traderLog_list' method");
		}
		OrdersManager localOrdersManager = (OrdersManager) getBean("ordersManager");
		QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
		try {
			paramHttpServletRequest.setAttribute("traderLogList", localOrdersManager.traderLog(localQueryConditions));
			paramHttpServletRequest.setAttribute("OPRTYPE", CommonDictionary.OPRTYPE);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("traderLog_list");
	}

	public ActionForward getLastPrice(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'getLastPrice' method");
		}
		String str1 = paramHttpServletRequest.getParameter("commodityID");
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
		paramHttpServletResponse.setHeader("Cache-Control", "no-store");
		paramHttpServletResponse.setHeader("Pragma", "no-cache");
		paramHttpServletResponse.setContentType("text/xml");
		paramHttpServletResponse.setCharacterEncoding("GBK");
		PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		String str2 = "";
		try {
			Commodity localCommodity = localCommodityManager.getCommodityById(str1);
			str2 = String.valueOf(localCommodity.getLastPrice());
			localStringBuffer.append("<margin>");
			localStringBuffer.append("<openingPrice>" + str2 + "</openingPrice>");
			localStringBuffer.append("</margin>");
			localPrintWriter.flush();
			localPrintWriter.print(localStringBuffer);
			localPrintWriter.close();
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", "查询出错！");
		}
		return null;
	}

	public ActionForward editLastPrice(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editLastPrice' method");
		}
		String str1 = paramHttpServletRequest.getParameter("commodityID");
		String str2 = paramHttpServletRequest.getParameter("openingPrice");
		Double localDouble = Double.valueOf(str2);
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		Commodity localCommodity = new Commodity();
		try {
			localCommodity.setCommodityID(str1);
			localCommodity.setLastPrice(localDouble);
			localCommodityManager.updateLastPrice(localCommodity);
			addSysLog(paramHttpServletRequest, "异常处理中修改商品[" + str1 + "]结算价");
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("saveOpeningPrice");
	}

	private void getSelectAttribute(HttpServletRequest paramHttpServletRequest) throws Exception {
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		paramHttpServletRequest.setAttribute("marketSelect",
				localLookupManager.getSelectLabelValueByTable("T_A_MARKET", "MarketName", "MarketCode", " where Status=1"));
		paramHttpServletRequest.setAttribute("customerSelect",
				localLookupManager.getSelectLabelValueByTable("T_CUSTOMER", "Name", "CustomerID", " where Status=0"));
		paramHttpServletRequest.setAttribute("commoditySelect",
				localLookupManager.getSelectLabelValueByTable("T_commodity", "commodityID", "commodityID", " order by commodityID"));
	}
}
