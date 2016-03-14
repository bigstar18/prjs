package gnnt.MEBS.timebargain.manage.webapp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.member.broker.services.BrokerService;
import gnnt.MEBS.member.broker.util.SysData;
import gnnt.MEBS.timebargain.manage.model.LabelValue;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.service.TradeRuleManager;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.webapp.form.TradeRuleGCForm;
import gnnt.MEBS.timebargain.server.model.SystemStatus;

public class TradeRuleAction extends BaseAction {
	public ActionForward editGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editGroup' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str1 = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = null;
		try {
			getSelectAttribute(paramHttpServletRequest);
			String str2 = "2";
			String str3 = "2";
			String str4 = "2";
			String str5 = "2";
			String str6 = "2";
			if (!str1.trim().equals("create")) {
				localTradeRuleGC = localTradeRuleManager.getGM_TradeRuleById(new Integer(localTradeRuleGCForm.getGroupID()),
						localTradeRuleGCForm.getCommodityID());
				if ((localTradeRuleGC.getMarginAssure_B().toString().equals(localTradeRuleGC.getMarginAssure_S().toString()))
						&& (localTradeRuleGC.getMarginRate_B().toString().equals(localTradeRuleGC.getMarginRate_S().toString()))) {
					str2 = "1";
				}
				if ((localTradeRuleGC.getMarginItem1().toString().equals(localTradeRuleGC.getMarginItem1_S().toString()))
						&& (localTradeRuleGC.getMarginItemAssure1().toString().equals(localTradeRuleGC.getMarginItemAssure1_S().toString()))) {
					str3 = "1";
				}
				if ((localTradeRuleGC.getMarginItem2().toString().equals(localTradeRuleGC.getMarginItem2_S().toString()))
						&& (localTradeRuleGC.getMarginItemAssure2().toString().equals(localTradeRuleGC.getMarginItemAssure2_S().toString()))) {
					str4 = "1";
				}
				if ((localTradeRuleGC.getMarginItem3().toString().equals(localTradeRuleGC.getMarginItem3_S().toString()))
						&& (localTradeRuleGC.getMarginItemAssure3().toString().equals(localTradeRuleGC.getMarginItemAssure3_S().toString()))) {
					str5 = "1";
				}
				if ((localTradeRuleGC.getMarginItem4().toString().equals(localTradeRuleGC.getMarginItem4_S().toString()))
						&& (localTradeRuleGC.getMarginItemAssure4().toString().equals(localTradeRuleGC.getMarginItemAssure4_S().toString()))) {
					str6 = "1";
				}
			} else {
				localTradeRuleGC = new TradeRuleGC();
				str2 = "1";
				str3 = "1";
				str4 = "1";
				str5 = "1";
				str6 = "1";
			}
			localTradeRuleGCForm = (TradeRuleGCForm) convert(localTradeRuleGC);
			String str7 = localTradeRuleGC.getGroupID();
			List localList = (List) paramHttpServletRequest.getAttribute("customerGroupSelect");
			if ((localList != null) && (localList.size() > 0)) {
				for (int i = 0; i < localList.size(); i++) {
					LabelValue localObject = (LabelValue) localList.get(i);
					if (((LabelValue) localObject).getValue().equals(str7)) {
						paramHttpServletRequest.setAttribute("groupName", ((LabelValue) localObject).getLabel());
					}
				}
			}
			paramHttpServletRequest.setAttribute("groupID", str7);
			String str8 = localTradeRuleGC.getCommodityID();
			Object localObject = (List) paramHttpServletRequest.getAttribute("commoditySelect");
			if ((localObject != null) && (((List) localObject).size() > 0)) {
				for (int j = 0; j < ((List) localObject).size(); j++) {
					LabelValue localLabelValue = (LabelValue) ((List) localObject).get(j);
					if (localLabelValue.getValue().equals(str8)) {
						paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
					}
				}
			}
			paramHttpServletRequest.setAttribute("commodityID", str8);
			localTradeRuleGCForm.setCrud(str1);
			updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeRuleGCForm);
			paramHttpServletRequest.setAttribute("type", str2);
			paramHttpServletRequest.setAttribute("type1", str3);
			paramHttpServletRequest.setAttribute("type2", str4);
			paramHttpServletRequest.setAttribute("type3", str5);
			paramHttpServletRequest.setAttribute("type4", str6);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		return paramActionMapping.findForward("editGroup");
	}

	public ActionForward saveGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveGroup' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str1 = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = (TradeRuleGC) convert(localTradeRuleGCForm);
		String str2 = paramHttpServletRequest.getParameter("type1");
		String str3 = paramHttpServletRequest.getParameter("type2");
		String str4 = paramHttpServletRequest.getParameter("type3");
		String str5 = paramHttpServletRequest.getParameter("type4");
		String str6 = paramHttpServletRequest.getParameter("type");
		try {
			if (str1.trim().equals("create")) {
				if ("1".equals(str6)) {
					localTradeRuleGC.setMarginRate_S(new Double(localTradeRuleGCForm.getMarginRate_B()));
					localTradeRuleGC.setMarginAssure_S(new Double(localTradeRuleGCForm.getMarginAssure_B()));
				}
				if (("1".equals(str2)) && (localTradeRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem1()))
						&& (localTradeRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure1()))) {
					localTradeRuleGC.setMarginItem1_S(new Double(localTradeRuleGCForm.getMarginItem1()));
					localTradeRuleGC.setMarginItemAssure1_S(new Double(localTradeRuleGCForm.getMarginItemAssure1()));
				}
				if (("1".equals(str3)) && (localTradeRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem2()))
						&& (localTradeRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure2()))) {
					localTradeRuleGC.setMarginItem2_S(new Double(localTradeRuleGCForm.getMarginItem2()));
					localTradeRuleGC.setMarginItemAssure2_S(new Double(localTradeRuleGCForm.getMarginItemAssure2()));
				}
				if (("1".equals(str4)) && (localTradeRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem3()))
						&& (localTradeRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure3()))) {
					localTradeRuleGC.setMarginItem3_S(new Double(localTradeRuleGCForm.getMarginItem3()));
					localTradeRuleGC.setMarginItemAssure3_S(new Double(localTradeRuleGCForm.getMarginItemAssure3()));
				}
				if (("1".equals(str5)) && (localTradeRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem4()))
						&& (localTradeRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure4()))) {
					localTradeRuleGC.setMarginItem4_S(new Double(localTradeRuleGCForm.getMarginItem4()));
					localTradeRuleGC.setMarginItemAssure4_S(new Double(localTradeRuleGCForm.getMarginItemAssure4()));
				}
				localTradeRuleManager.insertGM_TradeRule(localTradeRuleGC);
				addSysLog(paramHttpServletRequest, "增加客户组交易规则[" + localTradeRuleGC.getCommodityID() + "," + localTradeRuleGC.getGroupID() + "]");
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			} else if (str1.trim().equals("update")) {
				if ("1".equals(str6)) {
					if ((localTradeRuleGCForm.getMarginRate_B() != null) && (!"".equals(localTradeRuleGCForm.getMarginRate_B()))) {
						localTradeRuleGC.setMarginRate_S(new Double(localTradeRuleGCForm.getMarginRate_B()));
					}
					if ((localTradeRuleGCForm.getMarginAssure_B() != null) && (!"".equals(localTradeRuleGCForm.getMarginAssure_B()))) {
						localTradeRuleGC.setMarginAssure_S(new Double(localTradeRuleGCForm.getMarginAssure_B()));
					}
				}
				if (("1".equals(str2)) && (localTradeRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem1()))
						&& (localTradeRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure1()))) {
					localTradeRuleGC.setMarginItem1_S(new Double(localTradeRuleGCForm.getMarginItem1()));
					localTradeRuleGC.setMarginItemAssure1_S(new Double(localTradeRuleGCForm.getMarginItemAssure1()));
				}
				if (("1".equals(str3)) && (localTradeRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem2()))
						&& (localTradeRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure2()))) {
					localTradeRuleGC.setMarginItem2_S(new Double(localTradeRuleGCForm.getMarginItem2()));
					localTradeRuleGC.setMarginItemAssure2_S(new Double(localTradeRuleGCForm.getMarginItemAssure2()));
				}
				if (("1".equals(str4)) && (localTradeRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem3()))
						&& (localTradeRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure3()))) {
					localTradeRuleGC.setMarginItem3_S(new Double(localTradeRuleGCForm.getMarginItem3()));
					localTradeRuleGC.setMarginItemAssure3_S(new Double(localTradeRuleGCForm.getMarginItemAssure3()));
				}
				if (("1".equals(str5)) && (localTradeRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem4()))
						&& (localTradeRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure4()))) {
					localTradeRuleGC.setMarginItem4_S(new Double(localTradeRuleGCForm.getMarginItem4()));
					localTradeRuleGC.setMarginItemAssure4_S(new Double(localTradeRuleGCForm.getMarginItemAssure4()));
				}
				localTradeRuleManager.updateGM_TradeRule(localTradeRuleGC);
				addSysLog(paramHttpServletRequest, "修改客户组交易规则[" + localTradeRuleGC.getCommodityID() + "," + localTradeRuleGC.getGroupID() + "]");
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("===>save err：" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			getSelectAttribute(paramHttpServletRequest);
			return paramActionMapping.findForward("editGroup");
		}
		return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward deleteGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'deleteGroup' method");
		}
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString1 != null) {
			this.log.debug("==ids.length:" + arrayOfString1.length);
			String str2 = "";
			for (int j = 0; j < arrayOfString1.length; j++) {
				String[] arrayOfString2 = arrayOfString1[j].split(",");
				String str1 = arrayOfString2[1];
				Integer localInteger = new Integer(arrayOfString2[0]);
				try {
					localTradeRuleManager.deleteGM_TradeRuleById(str1, localInteger);
					addSysLog(paramHttpServletRequest, "删除客户组交易规则[" + str1 + "," + localInteger + "]");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str2 = str2 + arrayOfString1[j] + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
				}
			}
			if (!str2.equals("")) {
				str2 = str2.substring(0, str2.length() - 1);
				str2 = str2 + "与其他数据关联，不能删除！";
			}
			str2 = str2 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str2);
		}
		return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward searchGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchGroup' method");
		}
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		try {
			List localList = localTradeRuleManager.getGM_TradeRules(null);
			paramHttpServletRequest.setAttribute("tradeRuleList", localList);
		} catch (Exception localException) {
			this.log.error("查询GM_TradeRule表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("listGroup");
	}

	public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward searchFirmMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchFirmMargin' method");
		}
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		try {
			List localList = localTradeRuleManager.getFirmMargin();
			paramHttpServletRequest.setAttribute("firmMarginList", localList);
			paramHttpServletRequest.setAttribute("MARGINALGR", CommonDictionary.MARGINALGR);
		} catch (Exception localException) {
			this.log.error("查询CM_TradeRule表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("searchFirmMargin");
	}

	public ActionForward editFirmMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editFirmMargin' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str1 = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = null;
		try {
			getSelectAttribute(paramHttpServletRequest);
			String str2 = "2";
			String str3 = "2";
			String str4 = "2";
			String str5 = "2";
			String str6 = "2";
			String str7 = "2";
			String str8 = "2";
			String str9 = "2";
			if (!str1.trim().equals("create")) {
				localTradeRuleGC = localTradeRuleManager.getFirmMarginById(localTradeRuleGCForm.getFirmID(), localTradeRuleGCForm.getCommodityID());
				if (localTradeRuleGC != null) {
					if ((localTradeRuleGC.getMarginItem1() != null) && (localTradeRuleGC.getMarginItem1_S() != null)
							&& (localTradeRuleGC.getMarginItemAssure1() != null) && (localTradeRuleGC.getMarginItemAssure1_S() != null)
							&& (localTradeRuleGC.getMarginItem1().toString().equals(localTradeRuleGC.getMarginItem1_S().toString()))
							&& (localTradeRuleGC.getMarginItemAssure1().toString().equals(localTradeRuleGC.getMarginItemAssure1_S().toString()))) {
						str2 = "1";
					}
					if ((localTradeRuleGC.getMarginItem2() != null) && (localTradeRuleGC.getMarginItem2_S() != null)
							&& (localTradeRuleGC.getMarginItemAssure2() != null) && (localTradeRuleGC.getMarginItemAssure2_S() != null)
							&& (localTradeRuleGC.getMarginItem2().toString().equals(localTradeRuleGC.getMarginItem2_S().toString()))
							&& (localTradeRuleGC.getMarginItemAssure2().toString().equals(localTradeRuleGC.getMarginItemAssure2_S().toString()))) {
						str3 = "1";
					}
					if ((localTradeRuleGC.getMarginItem3() != null) && (localTradeRuleGC.getMarginItem3_S() != null)
							&& (localTradeRuleGC.getMarginItemAssure3() != null) && (localTradeRuleGC.getMarginItemAssure3_S() != null)
							&& (localTradeRuleGC.getMarginItem3().toString().equals(localTradeRuleGC.getMarginItem3_S().toString()))
							&& (localTradeRuleGC.getMarginItemAssure3().toString().equals(localTradeRuleGC.getMarginItemAssure3_S().toString()))) {
						str4 = "1";
					}
					if ((localTradeRuleGC.getMarginItem4() != null) && (localTradeRuleGC.getMarginItem4_S() != null)
							&& (localTradeRuleGC.getMarginItemAssure4() != null) && (localTradeRuleGC.getMarginItemAssure4_S() != null)
							&& (localTradeRuleGC.getMarginItem4().toString().equals(localTradeRuleGC.getMarginItem4_S().toString()))
							&& (localTradeRuleGC.getMarginItemAssure4().toString().equals(localTradeRuleGC.getMarginItemAssure4_S().toString()))) {
						str5 = "1";
					}
					if ((localTradeRuleGC.getMarginItem5() != null) && (localTradeRuleGC.getMarginItem5_S() != null)
							&& (localTradeRuleGC.getMarginItemAssure5() != null) && (localTradeRuleGC.getMarginItemAssure5_S() != null)
							&& (localTradeRuleGC.getMarginItem5().toString().equals(localTradeRuleGC.getMarginItem5_S().toString()))
							&& (localTradeRuleGC.getMarginItemAssure5().toString().equals(localTradeRuleGC.getMarginItemAssure5_S().toString()))) {
						str6 = "1";
					}
					if ((localTradeRuleGC.getSettleMarginAlgr_B() != null) && ("1".equals(localTradeRuleGC.getSettleMarginAlgr_B().toString()))) {
						str7 = "1";
						localTradeRuleGC.setSettleMarginRate_B(Arith.mul(localTradeRuleGC.getSettleMarginRate_B(), new Double(100.0D)));
					}
					if ((localTradeRuleGC.getSettleMarginAlgr_S() != null) && ("1".equals(localTradeRuleGC.getSettleMarginAlgr_S().toString()))) {
						str8 = "1";
						localTradeRuleGC.setSettleMarginRate_S(Arith.mul(localTradeRuleGC.getSettleMarginRate_S(), new Double(100.0D)));
					}
					if ((localTradeRuleGC.getPayoutAlgr() != null) && ("1".equals(localTradeRuleGC.getPayoutAlgr().toString()))) {
						str9 = "1";
						localTradeRuleGC.setPayoutRate(Arith.mul(localTradeRuleGC.getPayoutRate(), new Double(100.0D)));
					}
					if ((localTradeRuleGC.getMarginAlgr() != null) && ("1".equals(localTradeRuleGC.getMarginAlgr().toString()))) {
						localTradeRuleGC.setMarginItem1(Arith.mul(localTradeRuleGC.getMarginItem1(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem2(Arith.mul(localTradeRuleGC.getMarginItem2(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem3(Arith.mul(localTradeRuleGC.getMarginItem3(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem4(Arith.mul(localTradeRuleGC.getMarginItem4(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem5(Arith.mul(localTradeRuleGC.getMarginItem5(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem1_S(Arith.mul(localTradeRuleGC.getMarginItem1_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem2_S(Arith.mul(localTradeRuleGC.getMarginItem2_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem3_S(Arith.mul(localTradeRuleGC.getMarginItem3_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem4_S(Arith.mul(localTradeRuleGC.getMarginItem4_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItem5_S(Arith.mul(localTradeRuleGC.getMarginItem5_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure1(Arith.mul(localTradeRuleGC.getMarginItemAssure1(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure2(Arith.mul(localTradeRuleGC.getMarginItemAssure2(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure3(Arith.mul(localTradeRuleGC.getMarginItemAssure3(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure4(Arith.mul(localTradeRuleGC.getMarginItemAssure4(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure5(Arith.mul(localTradeRuleGC.getMarginItemAssure5(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure1_S(Arith.mul(localTradeRuleGC.getMarginItemAssure1_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure2_S(Arith.mul(localTradeRuleGC.getMarginItemAssure2_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure3_S(Arith.mul(localTradeRuleGC.getMarginItemAssure3_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure4_S(Arith.mul(localTradeRuleGC.getMarginItemAssure4_S(), new Double(100.0D)));
						localTradeRuleGC.setMarginItemAssure5_S(Arith.mul(localTradeRuleGC.getMarginItemAssure5_S(), new Double(100.0D)));
					}
				}
			} else {
				localTradeRuleGC = new TradeRuleGC();
				str2 = "1";
				str3 = "1";
				str4 = "1";
				str5 = "1";
				str6 = "1";
			}
			localTradeRuleGCForm = (TradeRuleGCForm) convert(localTradeRuleGC);
			String str10 = localTradeRuleGC.getCommodityID();
			List localList = (List) paramHttpServletRequest.getAttribute("commoditySelect");
			if ((localList != null) && (localList.size() > 0)) {
				for (int i = 0; i < localList.size(); i++) {
					LabelValue localLabelValue = (LabelValue) localList.get(i);
					if (localLabelValue.getValue().equals(str10)) {
						paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
					}
				}
			}
			paramHttpServletRequest.setAttribute("commodityID", str10);
			localTradeRuleGCForm.setCrud(str1);
			updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeRuleGCForm);
			paramHttpServletRequest.setAttribute("type", str2);
			paramHttpServletRequest.setAttribute("type1", str3);
			paramHttpServletRequest.setAttribute("type2", str4);
			paramHttpServletRequest.setAttribute("type3", str5);
			paramHttpServletRequest.setAttribute("type4", str6);
			paramHttpServletRequest.setAttribute("typeSettleMarginAlgr_B", str7);
			paramHttpServletRequest.setAttribute("typeSettleMarginAlgr_S", str8);
			paramHttpServletRequest.setAttribute("typePayoutAlgr", str9);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			return searchFirmMargin(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		return paramActionMapping.findForward("editFirmMargin");
	}

	public ActionForward saveFirmMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveFirmMargin' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str1 = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = (TradeRuleGC) convert(localTradeRuleGCForm);
		String str2 = paramHttpServletRequest.getParameter("type1");
		String str3 = paramHttpServletRequest.getParameter("type2");
		String str4 = paramHttpServletRequest.getParameter("type3");
		String str5 = paramHttpServletRequest.getParameter("type4");
		String str6 = paramHttpServletRequest.getParameter("type");
		try {
			if ((localTradeRuleGC.getSettleMarginAlgr_B() != null) && ("1".equals(localTradeRuleGC.getSettleMarginAlgr_B().toString()))) {
				localTradeRuleGC.setSettleMarginRate_B(
						Double.valueOf(localTradeRuleGC.getSettleMarginRate_B().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localTradeRuleGC.getSettleMarginAlgr_S() != null) && ("1".equals(localTradeRuleGC.getSettleMarginAlgr_S().toString()))) {
				localTradeRuleGC.setSettleMarginRate_S(
						Double.valueOf(localTradeRuleGC.getSettleMarginRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localTradeRuleGC.getPayoutAlgr() != null) && ("1".equals(localTradeRuleGC.getPayoutAlgr().toString()))) {
				localTradeRuleGC.setPayoutRate(Double.valueOf(localTradeRuleGC.getPayoutRate().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localTradeRuleGC.getMarginAlgr() != null) && ("1".equals(localTradeRuleGC.getMarginAlgr().toString()))) {
				if (localTradeRuleGC.getMarginItem1() != null) {
					localTradeRuleGC
							.setMarginItem1(Double.valueOf(localTradeRuleGC.getMarginItem1().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem2() != null) {
					localTradeRuleGC
							.setMarginItem2(Double.valueOf(localTradeRuleGC.getMarginItem2().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem3() != null) {
					localTradeRuleGC
							.setMarginItem3(Double.valueOf(localTradeRuleGC.getMarginItem3().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem4() != null) {
					localTradeRuleGC
							.setMarginItem4(Double.valueOf(localTradeRuleGC.getMarginItem4().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem5() != null) {
					localTradeRuleGC
							.setMarginItem5(Double.valueOf(localTradeRuleGC.getMarginItem5().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem1_S() != null) {
					localTradeRuleGC
							.setMarginItem1_S(Double.valueOf(localTradeRuleGC.getMarginItem1_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem2_S() != null) {
					localTradeRuleGC
							.setMarginItem2_S(Double.valueOf(localTradeRuleGC.getMarginItem2_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem3_S() != null) {
					localTradeRuleGC
							.setMarginItem3_S(Double.valueOf(localTradeRuleGC.getMarginItem3_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem4_S() != null) {
					localTradeRuleGC
							.setMarginItem4_S(Double.valueOf(localTradeRuleGC.getMarginItem4_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItem5_S() != null) {
					localTradeRuleGC
							.setMarginItem5_S(Double.valueOf(localTradeRuleGC.getMarginItem5_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure1() != null) {
					localTradeRuleGC.setMarginItemAssure1(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure1().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure2() != null) {
					localTradeRuleGC.setMarginItemAssure2(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure2().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure3() != null) {
					localTradeRuleGC.setMarginItemAssure3(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure3().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure4() != null) {
					localTradeRuleGC.setMarginItemAssure4(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure4().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure5() != null) {
					localTradeRuleGC.setMarginItemAssure5(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure5().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure1_S() != null) {
					localTradeRuleGC.setMarginItemAssure1_S(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure1_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure2_S() != null) {
					localTradeRuleGC.setMarginItemAssure2_S(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure2_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure3_S() != null) {
					localTradeRuleGC.setMarginItemAssure3_S(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure3_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure4_S() != null) {
					localTradeRuleGC.setMarginItemAssure4_S(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure4_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localTradeRuleGC.getMarginItemAssure5_S() != null) {
					localTradeRuleGC.setMarginItemAssure5_S(
							Double.valueOf(localTradeRuleGC.getMarginItemAssure5_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
			}
			if (str1.trim().equals("create")) {
				if (("1".equals(str6)) && (localTradeRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem1()))
						&& (localTradeRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure1()))) {
					localTradeRuleGC.setMarginItem1_S(localTradeRuleGC.getMarginItem1());
					localTradeRuleGC.setMarginItemAssure1_S(localTradeRuleGC.getMarginItemAssure1());
				}
				if (("1".equals(str2)) && (localTradeRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem2()))
						&& (localTradeRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure2()))) {
					localTradeRuleGC.setMarginItem2_S(localTradeRuleGC.getMarginItem2());
					localTradeRuleGC.setMarginItemAssure2_S(localTradeRuleGC.getMarginItemAssure2());
				}
				if (("1".equals(str3)) && (localTradeRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem3()))
						&& (localTradeRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure3()))) {
					localTradeRuleGC.setMarginItem3_S(localTradeRuleGC.getMarginItem3());
					localTradeRuleGC.setMarginItemAssure3_S(localTradeRuleGC.getMarginItemAssure3());
				}
				if (("1".equals(str4)) && (localTradeRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem4()))
						&& (localTradeRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure4()))) {
					localTradeRuleGC.setMarginItem4_S(localTradeRuleGC.getMarginItem4());
					localTradeRuleGC.setMarginItemAssure4_S(localTradeRuleGC.getMarginItemAssure4());
				}
				if (("1".equals(str5)) && (localTradeRuleGCForm.getMarginItem5() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem5()))
						&& (localTradeRuleGCForm.getMarginItemAssure5() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure5()))) {
					localTradeRuleGC.setMarginItem5_S(localTradeRuleGC.getMarginItem5());
					localTradeRuleGC.setMarginItemAssure5_S(localTradeRuleGC.getMarginItemAssure5());
				}
				localTradeRuleManager.insertFirmMargin(localTradeRuleGC);
				addSysLog(paramHttpServletRequest, "增加[" + localTradeRuleGC.getFirmID() + "," + localTradeRuleGC.getCommodityID() + "]特殊保证金");
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			} else if (str1.trim().equals("update")) {
				if (("1".equals(str6)) && (localTradeRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem1()))
						&& (localTradeRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure1()))) {
					localTradeRuleGC.setMarginItem1_S(localTradeRuleGC.getMarginItem1());
					localTradeRuleGC.setMarginItemAssure1_S(localTradeRuleGC.getMarginItemAssure1());
				}
				if (("1".equals(str2)) && (localTradeRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem2()))
						&& (localTradeRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure2()))) {
					localTradeRuleGC.setMarginItem2_S(localTradeRuleGC.getMarginItem2());
					localTradeRuleGC.setMarginItemAssure2_S(localTradeRuleGC.getMarginItemAssure2());
				}
				if (("1".equals(str3)) && (localTradeRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem3()))
						&& (localTradeRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure3()))) {
					localTradeRuleGC.setMarginItem3_S(localTradeRuleGC.getMarginItem3());
					localTradeRuleGC.setMarginItemAssure3_S(localTradeRuleGC.getMarginItemAssure3());
				}
				if (("1".equals(str4)) && (localTradeRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem4()))
						&& (localTradeRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure4()))) {
					localTradeRuleGC.setMarginItem4_S(localTradeRuleGC.getMarginItem4());
					localTradeRuleGC.setMarginItemAssure4_S(localTradeRuleGC.getMarginItemAssure4());
				}
				if (("1".equals(str5)) && (localTradeRuleGCForm.getMarginItem5() != null) && (!"".equals(localTradeRuleGCForm.getMarginItem5()))
						&& (localTradeRuleGCForm.getMarginItemAssure5() != null) && (!"".equals(localTradeRuleGCForm.getMarginItemAssure5()))) {
					localTradeRuleGC.setMarginItem5_S(localTradeRuleGC.getMarginItem5());
					localTradeRuleGC.setMarginItemAssure5_S(localTradeRuleGC.getMarginItemAssure5());
				}
				localTradeRuleManager.updateFirmMargin(localTradeRuleGC);
				addSysLog(paramHttpServletRequest, "修改[" + localTradeRuleGC.getFirmID() + "," + localTradeRuleGC.getCommodityID() + "]特殊保证金");
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("===>save err：" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			getSelectAttribute(paramHttpServletRequest);
			return paramActionMapping.findForward("editFirmMargin");
		}
		return searchFirmMargin(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward deleteFirmMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'deleteFirmMargin' method");
		}
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString1 != null) {
			this.log.debug("==ids.length:" + arrayOfString1.length);
			String str3 = "";
			for (int j = 0; j < arrayOfString1.length; j++) {
				String[] arrayOfString2 = arrayOfString1[j].split(",");
				String str1 = arrayOfString2[1];
				String str2 = arrayOfString2[0];
				try {
					StatQueryManager localStatQueryManager = (StatQueryManager) getBean("statQueryManager");
					SystemStatus localSystemStatus = localStatQueryManager.getSystemStatusObject();
					int k = localSystemStatus.getStatus();
					if (("5".equals(k + "")) || ("6".equals(k + ""))) {
						paramHttpServletRequest.setAttribute("prompt", "此时市场状态不允许删除特殊保证金记录！");
						str3 = "此时市场状态不允许删除特殊保证金记录！ ";
						break;
					}
					localTradeRuleManager.deleteFirmMarginById(str2, str1);
					addSysLog(paramHttpServletRequest, "删除[" + str2 + "," + str1 + "]特殊保证金");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str3 = str3 + arrayOfString1[j] + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
				}
			}
			if (!str3.equals("")) {
				str3 = str3.substring(0, str3.length() - 1);
				str3 = str3 + "与其他数据关联，不能删除！";
			}
			str3 = str3 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str3);
		}
		return searchFirmMargin(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward searchFirmFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchFirmFee' method");
		}
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		try {
			List localList = localTradeRuleManager.getFirmFee();
			paramHttpServletRequest.setAttribute("firmFeeList", localList);
			paramHttpServletRequest.setAttribute("FEEALGR", CommonDictionary.FEEALGR);
		} catch (Exception localException) {
			this.log.error("查询T_A_FirmFee表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("searchFirmFee");
	}

	public ActionForward editFirmFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editFirmFee' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str1 = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = null;
		String str2 = "2";
		String str3 = "2";
		try {
			getSelectAttribute(paramHttpServletRequest);
			if (!str1.trim().equals("create")) {
				localTradeRuleGC = localTradeRuleManager.getFirmFeeById(localTradeRuleGCForm.getFirmID(), localTradeRuleGCForm.getCommodityID());
				if (localTradeRuleGC != null) {
					if ((localTradeRuleGC.getFeeAlgr() != null) && ("1".equals(localTradeRuleGC.getFeeAlgr().toString()))) {
						str2 = "1";
						localTradeRuleGC.setFeeRate_B(Arith.mul(localTradeRuleGC.getFeeRate_B(), new Double(100.0D)));
						localTradeRuleGC.setFeeRate_S(Arith.mul(localTradeRuleGC.getFeeRate_S(), new Double(100.0D)));
						localTradeRuleGC.setHistoryCloseFeeRate_B(Arith.mul(localTradeRuleGC.getHistoryCloseFeeRate_B(), new Double(100.0D)));
						localTradeRuleGC.setHistoryCloseFeeRate_S(Arith.mul(localTradeRuleGC.getHistoryCloseFeeRate_S(), new Double(100.0D)));
						localTradeRuleGC.setTodayCloseFeeRate_B(Arith.mul(localTradeRuleGC.getTodayCloseFeeRate_B(), new Double(100.0D)));
						localTradeRuleGC.setTodayCloseFeeRate_S(Arith.mul(localTradeRuleGC.getTodayCloseFeeRate_S(), new Double(100.0D)));
						localTradeRuleGC.setForceCloseFeeRate_B(Arith.mul(localTradeRuleGC.getForceCloseFeeRate_B(), new Double(100.0D)));
						localTradeRuleGC.setForceCloseFeeRate_S(Arith.mul(localTradeRuleGC.getForceCloseFeeRate_S(), new Double(100.0D)));
					}
					if ((localTradeRuleGC.getSettleFeeAlgr() != null) && ("1".equals(localTradeRuleGC.getSettleFeeAlgr().toString()))) {
						str3 = "1";
						localTradeRuleGC.setSettleFeeRate_B(Arith.mul(localTradeRuleGC.getSettleFeeRate_B(), new Double(100.0D)));
						localTradeRuleGC.setSettleFeeRate_S(Arith.mul(localTradeRuleGC.getSettleFeeRate_S(), new Double(100.0D)));
					}
				}
			} else {
				localTradeRuleGC = new TradeRuleGC();
			}
			localTradeRuleGCForm = (TradeRuleGCForm) convert(localTradeRuleGC);
			String str4 = localTradeRuleGC.getCommodityID();
			List localList = (List) paramHttpServletRequest.getAttribute("commoditySelect");
			if ((localList != null) && (localList.size() > 0)) {
				for (int i = 0; i < localList.size(); i++) {
					LabelValue localLabelValue = (LabelValue) localList.get(i);
					if (localLabelValue.getValue().equals(str4)) {
						paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
					}
				}
			}
			paramHttpServletRequest.setAttribute("commodityID", str4);
			paramHttpServletRequest.setAttribute("typeFeeAlgr", str2);
			paramHttpServletRequest.setAttribute("typeSettleFeeAlgr", str3);
			localTradeRuleGCForm.setCrud(str1);
			updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeRuleGCForm);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			return searchFirmFee(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		return paramActionMapping.findForward("editFirmFee");
	}

	public ActionForward saveFirmFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveFirmFee' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC1 = (TradeRuleGC) convert(localTradeRuleGCForm);
		try {
			if ((localTradeRuleGC1.getFeeAlgr() != null) && ("1".equals(localTradeRuleGC1.getFeeAlgr().toString()))) {
				localTradeRuleGC1.setFeeRate_B(Double.valueOf(localTradeRuleGC1.getFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setFeeRate_S(Double.valueOf(localTradeRuleGC1.getFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setHistoryCloseFeeRate_B(
						Double.valueOf(localTradeRuleGC1.getHistoryCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setHistoryCloseFeeRate_S(
						Double.valueOf(localTradeRuleGC1.getHistoryCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setTodayCloseFeeRate_B(
						Double.valueOf(localTradeRuleGC1.getTodayCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setTodayCloseFeeRate_S(
						Double.valueOf(localTradeRuleGC1.getTodayCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setForceCloseFeeRate_B(
						Double.valueOf(localTradeRuleGC1.getForceCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1.setForceCloseFeeRate_S(
						Double.valueOf(localTradeRuleGC1.getForceCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localTradeRuleGC1.getSettleFeeAlgr() != null) && ("1".equals(localTradeRuleGC1.getSettleFeeAlgr().toString()))) {
				localTradeRuleGC1
						.setSettleFeeRate_B(Double.valueOf(localTradeRuleGC1.getSettleFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localTradeRuleGC1
						.setSettleFeeRate_S(Double.valueOf(localTradeRuleGC1.getSettleFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			DecimalFormat localDecimalFormat = new DecimalFormat("#,##0.00######");
			StringBuffer localStringBuffer1 = new StringBuffer("交易商[" + localTradeRuleGC1.getFirmID() + "]商品:[" + localTradeRuleGC1.getCommodityID()
					+ "](" + localTradeRuleGC1.getFeeAlgr() + "," + localDecimalFormat.format(localTradeRuleGC1.getFeeRate_B()) + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getFeeRate_S()) + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getTodayCloseFeeRate_B()) + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getTodayCloseFeeRate_S()) + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getHistoryCloseFeeRate_B()) + " ,"
					+ localDecimalFormat.format(localTradeRuleGC1.getHistoryCloseFeeRate_S()) + "," + localTradeRuleGC1.getSettleFeeAlgr() + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getSettleFeeRate_B()) + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getSettleFeeRate_S()) + "," + localTradeRuleGC1.getForceCloseFeeAlgr() + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getForceCloseFeeRate_B()) + ","
					+ localDecimalFormat.format(localTradeRuleGC1.getForceCloseFeeRate_S()) + ")");
			if (str.trim().equals("create")) {
				localTradeRuleManager.insertFirmFee(localTradeRuleGC1);
				addSysLog(paramHttpServletRequest, "增加特殊手续费" + localStringBuffer1);
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			} else if (str.trim().equals("update")) {
				localTradeRuleManager.updateFirmFee(localTradeRuleGC1);
				TradeRuleGC localTradeRuleGC2 = localTradeRuleManager.getFirmFeeById(localTradeRuleGC1.getFirmID(),
						localTradeRuleGC1.getCommodityID());
				StringBuffer localStringBuffer2 = new StringBuffer(
						"调整为 (" + localTradeRuleGC2.getFeeAlgr() + "," + localDecimalFormat.format(localTradeRuleGC2.getFeeRate_B()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getFeeRate_S()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getTodayCloseFeeRate_B()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getTodayCloseFeeRate_S()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getHistoryCloseFeeRate_B()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getHistoryCloseFeeRate_S()) + "," + localTradeRuleGC2.getSettleFeeAlgr()
								+ "," + localDecimalFormat.format(localTradeRuleGC2.getSettleFeeRate_B()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getSettleFeeRate_S()) + "," + localTradeRuleGC2.getForceCloseFeeAlgr()
								+ "," + localDecimalFormat.format(localTradeRuleGC2.getForceCloseFeeRate_B()) + ","
								+ localDecimalFormat.format(localTradeRuleGC2.getForceCloseFeeRate_S()) + ")");
				addSysLog(paramHttpServletRequest, "修改特殊手续费" + localStringBuffer1 + localStringBuffer2);
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("===>save err：" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			getSelectAttribute(paramHttpServletRequest);
			return paramActionMapping.findForward("editFirmFee");
		}
		return searchFirmFee(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward deleteFirmFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'deleteFirmFee' method");
		}
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString1 != null) {
			this.log.debug("==ids.length:" + arrayOfString1.length);
			String str3 = "";
			for (int j = 0; j < arrayOfString1.length; j++) {
				String[] arrayOfString2 = arrayOfString1[j].split(",");
				String str1 = arrayOfString2[1];
				String str2 = arrayOfString2[0];
				try {
					localTradeRuleManager.deleteFirmFeeById(str2, str1);
					addSysLog(paramHttpServletRequest, "删除特殊手续费[" + str2 + "][" + str1 + "]");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str3 = str3 + arrayOfString1[j] + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
				}
			}
			if (!str3.equals("")) {
				str3 = str3.substring(0, str3.length() - 1);
				str3 = str3 + "与其他数据关联，不能删除！";
			}
			str3 = str3 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str3);
		}
		return searchFirmFee(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward searchFirmMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'searchFirmMaxHoldQty' method");
		}
		String str1 = paramHttpServletRequest.getParameter("firmId");
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		String str2 = "";
		try {
			if (("".equals(str1)) || (str1 == null)) {
				List localObject = localTradeRuleManager.getFirmMaxHoldQty();
				paramHttpServletRequest.setAttribute("firmMaxHoldQtyList", localObject);
				str2 = "searchFirmMaxHoldQty";
			} else {
				List localObject = localTradeRuleManager.getFirmMaxHoldQtyByFirmId(str1);
				paramHttpServletRequest.setAttribute("firmMaxHoldQtyList", localObject);
				str2 = "searchFirmMaxHoldQtyByFirmId";
				paramHttpServletRequest.setAttribute("RfirmID", str1);
			}
			Object localObject = paramHttpServletRequest.getAttribute("firmId");
			if (localObject != null) {
				List localList = localTradeRuleManager.getFirmMaxHoldQtyByFirmId(localObject.toString());
				paramHttpServletRequest.setAttribute("firmMaxHoldQtyList", localList);
				str2 = "searchFirmMaxHoldQtyByFirmId";
				paramHttpServletRequest.setAttribute("RfirmID", localObject.toString());
			}
		} catch (Exception localException) {
			this.log.error("查询T_A_FirmFee表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward(str2);
	}

	public ActionForward editFirmMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'editFirmMaxHoldQty' method");
		}
		String str1 = paramHttpServletRequest.getParameter("firmID");
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str2 = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = null;
		String str3 = "1";
		String str4 = "1";
		try {
			getSelectAttribute(paramHttpServletRequest);
			if (!str2.trim().equals("create")) {
				localTradeRuleGC = localTradeRuleManager.getFirmMaxHoldQtyById(localTradeRuleGCForm.getFirmID(),
						localTradeRuleGCForm.getCommodityID());
				if ((localTradeRuleGC.getMaxHoldQty() != null) && ("-1".equals(localTradeRuleGC.getMaxHoldQty().toString()))) {
					str3 = "2";
				}
				if ((localTradeRuleGC.getCleanMaxHoldQty() != null) && ("-1".equals(localTradeRuleGC.getCleanMaxHoldQty().toString()))) {
					str4 = "2";
				}
			} else {
				localTradeRuleGC = new TradeRuleGC();
			}
			paramHttpServletRequest.setAttribute("type101", str3);
			paramHttpServletRequest.setAttribute("type102", str4);
			localTradeRuleGCForm = (TradeRuleGCForm) convert(localTradeRuleGC);
			String str5 = localTradeRuleGC.getCommodityID();
			List localList = (List) paramHttpServletRequest.getAttribute("commoditySelect");
			if ((localList != null) && (localList.size() > 0)) {
				for (int i = 0; i < localList.size(); i++) {
					LabelValue localLabelValue = (LabelValue) localList.get(i);
					if (localLabelValue.getValue().equals(str5)) {
						paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
					}
				}
			}
			paramHttpServletRequest.setAttribute("commodityID", str5);
			localTradeRuleGCForm.setCrud(str2);
			localTradeRuleGCForm.setFirmID(str1);
			updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeRuleGCForm);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			return searchFirmMaxHoldQty(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		String str6 = "";
		if ("firm".equals(paramHttpServletRequest.getParameter("move"))) {
			if ((paramHttpServletRequest.getParameter("firmID") == null) || ("undefined".equals(str1))) {
				localTradeRuleGCForm.setFirmID("");
			}
			str6 = "editFirmMaxHoleQtyById";
		} else {
			str6 = "editFirmMaxHoldQty";
		}
		return paramActionMapping.findForward(str6);
	}

	public ActionForward saveFirmMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'saveFirmMaxHoldQty' method");
		}
		TradeRuleGCForm localTradeRuleGCForm = (TradeRuleGCForm) paramActionForm;
		String str = localTradeRuleGCForm.getCrud();
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeRuleGC localTradeRuleGC = (TradeRuleGC) convert(localTradeRuleGCForm);
		try {
			if (str.trim().equals("create")) {
				localTradeRuleManager.insertFirmMaxHoldQty(localTradeRuleGC);
				addSysLog(paramHttpServletRequest, "增加[" + localTradeRuleGC.getFirmID() + "," + localTradeRuleGC.getCommodityID() + "]特殊订货量");
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			} else if (str.trim().equals("update")) {
				localTradeRuleManager.updateFirmMaxHoldQty(localTradeRuleGC);
				addSysLog(paramHttpServletRequest, "修改[" + localTradeRuleGC.getFirmID() + "," + localTradeRuleGC.getCommodityID() + "]特殊订货量");
				paramHttpServletRequest.setAttribute("prompt", "操作成功！");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("===>save err：" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			getSelectAttribute(paramHttpServletRequest);
			if ("firm".equals(paramHttpServletRequest.getParameter("move"))) {
				return paramActionMapping.findForward("editFirmMaxHoleQtyById");
			}
			return paramActionMapping.findForward("editFirmMaxHoldQty");
		}
		if ("firm".equals(paramHttpServletRequest.getParameter("move"))) {
			paramHttpServletRequest.setAttribute("firmId", localTradeRuleGCForm.getFirmID());
		}
		return searchFirmMaxHoldQty(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward deleteFirmMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm,
			HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'deleteFirmMaxHoldQty' method");
		}
		String str1 = paramHttpServletRequest.getParameter("move");
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
		Object localObject = "";
		int i = 0;
		if (arrayOfString1 != null) {
			this.log.debug("==ids.length:" + arrayOfString1.length);
			String str4 = "";
			for (int j = 0; j < arrayOfString1.length; j++) {
				String[] arrayOfString2 = arrayOfString1[j].split(",");
				String str2 = arrayOfString2[1];
				String str3 = arrayOfString2[0];
				localObject = str3;
				try {
					localTradeRuleManager.deleteFirmMaxHoldQtyById(str3, str2);
					addSysLog(paramHttpServletRequest, "删除[" + str3 + "," + str2 + "]特殊订货量");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str4 = str4 + arrayOfString1[j] + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
				}
			}
			if (!str4.equals("")) {
				str4 = str4.substring(0, str4.length() - 1);
				str4 = str4 + "与其他数据关联，不能删除！";
			}
			str4 = str4 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str4);
		}
		if ("firm".equals(str1)) {
			paramHttpServletRequest.setAttribute("firmId", localObject);
		}
		return searchFirmMaxHoldQty(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	private void getSelectAttribute(HttpServletRequest paramHttpServletRequest) throws Exception {
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		paramHttpServletRequest.setAttribute("commoditySelect",
				localLookupManager.getSelectLabelValueByTable("T_commodity", "CommodityID", "CommodityID", " order by name "));
	}

	public void getFirmInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) {
		int i = 0;
		try {
			paramHttpServletRequest.setCharacterEncoding("GBK");
			paramHttpServletResponse.setContentType("text/html;charset=GBK");
			PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
			String str = paramHttpServletRequest.getParameter("firmId");
			TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
			BrokerService localBrokerService = (BrokerService) SysData.getBean("m_brokerService");
			Dealer localDealer = localBrokerService.getDealerByfirmId(str);
			if (localDealer == null) {
				i = 1;
			}
			localPrintWriter.print(i);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}
}
