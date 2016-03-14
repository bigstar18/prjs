package gnnt.MEBS.timebargain.manage.webapp.action;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.service.BreedManager;
import gnnt.MEBS.timebargain.manage.service.CmdtySortManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.form.BreedForm;

public class BreedAction extends BaseAction {
	public ActionForward breedGet(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		BreedManager localBreedManager = (BreedManager) getBean("breedManager");
		QueryConditions localQueryConditions = new QueryConditions();
		if ((null != paramHttpServletRequest.getParameter("sortid")) && (!"".equals(paramHttpServletRequest.getParameter("sortid")))) {
			localQueryConditions.addCondition("c.sortid", "=", paramHttpServletRequest.getParameter("sortid"));
		}
		List localList = localBreedManager.getBreeds(localQueryConditions);
		paramHttpServletRequest.setAttribute("breedList", localList);
		return paramActionMapping.findForward("list");
	}

	public ActionForward select(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		CmdtySortManager localCmdtySortManager = (CmdtySortManager) getBean("cmdtySortManager");
		List localList = localCmdtySortManager.getCmdtySorts(null);
		paramHttpServletRequest.setAttribute("sorts", localList);
		return paramActionMapping.findForward("sort");
	}

	public ActionForward topBreed(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'topBreed' method");
		}
		getSelectAttribute(paramHttpServletRequest);
		return paramActionMapping.findForward("topBreed");
	}

	public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit' method");
		}
		String str1 = getServlet().getServletContext().getInitParameter("useDelay");
		paramHttpServletRequest.setAttribute("useDelay", str1);
		String str2 = getServlet().getServletContext().getInitParameter("useBreedTradeMode");
		paramHttpServletRequest.setAttribute("useBreedTradeMode", str2);
		BreedForm localBreedForm = (BreedForm) paramActionForm;
		String str3 = localBreedForm.getCrud();
		BreedManager localBreedManager = (BreedManager) getBean("breedManager");
		LookupManager localLookupManager1 = (LookupManager) getBean("lookupManager");
		MarketManager localMarketManager = (MarketManager) getBean("marketManager");
		Map localMap = (Map) localMarketManager.getMarketById(null).get(0);
		paramHttpServletRequest.setAttribute("floatingType", localMap.get("FloatingLossComputeType"));
		List localList1 = localLookupManager1.getAllSection(null);
		paramHttpServletRequest.setAttribute("listSection", localList1);
		Breed localBreed = null;
		try {
			getSelectAttribute(paramHttpServletRequest);
			String str4 = "2";
			String str5 = "2";
			String str6 = "2";
			String str7 = "2";
			String str8 = "2";
			String str9 = "1";
			String str10 = "1";
			String str11 = "1";
			String str12 = "1";
			String str13 = "1";
			String str14 = "2";
			String str15 = "";
			String str16 = "2";
			String str17 = "2";
			String str18 = "2";
			String str19 = "2";
			String str20 = "2";
			String str21 = "2";
			String str22 = "2";
			int i = 0;
			String str23 = localBreedManager.getIsSettleFlagByModuleID("2");
			if ((str23 == null) || ("".equals(str23))) {
				str23 = "N";
			}
			LookupManager localLookupManager2 = (LookupManager) getBean("lookupManager");
			paramHttpServletRequest.setAttribute("breedSelect",
					localLookupManager2.getSelectLabelValueByTable("E_Breed", "breedName", "breedId", " where Status=0"));
			List localList2 = (List) getBean("isSettleList");
			if ((localList2 != null) && (localList2.size() > 0)) {
				for (int j = 0; j < localList2.size(); j++) {
					String str25 = (String) localList2.get(j);
					if (!localList2.contains(str23)) {
						throw new RuntimeException("此标志不存在！");
					}
				}
			}
			if (!str3.trim().equals("create")) {
				localBreed = localBreedManager.getBreedById(localBreedForm.getBreedID());
				if (localBreed != null) {
					if ((localBreed.getMarginItem1() != null) && (localBreed.getMarginItem1_S() != null)
							&& (localBreed.getMarginItemAssure1() != null) && (localBreed.getMarginItemAssure1_S() != null)
							&& (localBreed.getMarginItem1().toString().equals(localBreed.getMarginItem1_S().toString()))
							&& (localBreed.getMarginItemAssure1().toString().equals(localBreed.getMarginItemAssure1_S().toString()))) {
						str4 = "1";
					}
					if ((localBreed.getMarginItem2() != null) && (localBreed.getMarginItem2_S() != null)
							&& (localBreed.getMarginItemAssure2() != null) && (localBreed.getMarginItemAssure2_S() != null)
							&& (localBreed.getMarginItem2().toString().equals(localBreed.getMarginItem2_S().toString()))
							&& (localBreed.getMarginItemAssure2().toString().equals(localBreed.getMarginItemAssure2_S().toString()))) {
						str5 = "1";
					}
					if ((localBreed.getMarginItem3() != null) && (localBreed.getMarginItem3_S() != null)
							&& (localBreed.getMarginItemAssure3() != null) && (localBreed.getMarginItemAssure3_S() != null)
							&& (localBreed.getMarginItem3().toString().equals(localBreed.getMarginItem3_S().toString()))
							&& (localBreed.getMarginItemAssure3().toString().equals(localBreed.getMarginItemAssure3_S().toString()))) {
						str6 = "1";
					}
					if ((localBreed.getMarginItem4() != null) && (localBreed.getMarginItem4_S() != null)
							&& (localBreed.getMarginItemAssure4() != null) && (localBreed.getMarginItemAssure4_S() != null)
							&& (localBreed.getMarginItem4().toString().equals(localBreed.getMarginItem4_S().toString()))
							&& (localBreed.getMarginItemAssure4().toString().equals(localBreed.getMarginItemAssure4_S().toString()))) {
						str7 = "1";
					}
					if ((localBreed.getMarginItem5() != null) && (localBreed.getMarginItem5_S() != null)
							&& (localBreed.getMarginItemAssure5() != null) && (localBreed.getMarginItemAssure5_S() != null)
							&& (localBreed.getMarginItem5().toString().equals(localBreed.getMarginItem5_S().toString()))
							&& (localBreed.getMarginItemAssure5().toString().equals(localBreed.getMarginItemAssure5_S().toString()))) {
						str8 = "1";
					}
					if ((localBreed.getLimitBreedQty() != null) && ("-1".equals(localBreed.getLimitBreedQty().toString()))) {
						str9 = "2";
					}
					if ((localBreed.getLimitCmdtyQty() != null) && ("-1".equals(localBreed.getLimitCmdtyQty().toString()))) {
						str10 = "2";
					}
					if ((localBreed.getFirmCleanQty() != null) && ("-1".equals(localBreed.getFirmCleanQty().toString()))) {
						str11 = "2";
					}
					if ((localBreed.getFirmMaxHoldQty() != null) && ("-1".equals(localBreed.getFirmMaxHoldQty().toString()))) {
						str12 = "2";
					}
					if ((localBreed.getOneMaxHoldQty() != null) && ("-1".equals(localBreed.getOneMaxHoldQty().toString()))) {
						str13 = "2";
					}
					if ((localBreed.getSettlePriceType() != null) && ("1".equals(localBreed.getSettlePriceType().toString()))) {
						str14 = "1";
					}
					if ((localBreed.getSettlePriceType() != null) && ("3".equals(localBreed.getSettlePriceType().toString()))) {
						str14 = "3";
					}
					if (localBreed.getSettleMarginType() == 1) {
						str15 = "1";
					}
					if ((localBreed.getSpreadAlgr() != null) && ("1".equals(localBreed.getSpreadAlgr().toString()))) {
						str16 = "1";
						localBreed.setSpreadUpLmt(Arith.mul(localBreed.getSpreadUpLmt(), new Double(100.0D)));
						localBreed.setSpreadDownLmt(Arith.mul(localBreed.getSpreadDownLmt(), new Double(100.0D)));
					}
					if ((localBreed.getFeeAlgr() != null) && ("1".equals(localBreed.getFeeAlgr().toString()))) {
						str17 = "1";
						localBreed.setFeeRate_B(Arith.mul(localBreed.getFeeRate_B(), new Double(100.0D)));
						localBreed.setFeeRate_S(Arith.mul(localBreed.getFeeRate_S(), new Double(100.0D)));
						localBreed.setHistoryCloseFeeRate_B(Arith.mul(localBreed.getHistoryCloseFeeRate_B(), new Double(100.0D)));
						localBreed.setHistoryCloseFeeRate_S(Arith.mul(localBreed.getHistoryCloseFeeRate_S(), new Double(100.0D)));
						localBreed.setTodayCloseFeeRate_B(Arith.mul(localBreed.getTodayCloseFeeRate_B(), new Double(100.0D)));
						localBreed.setTodayCloseFeeRate_S(Arith.mul(localBreed.getTodayCloseFeeRate_S(), new Double(100.0D)));
						localBreed.setForceCloseFeeRate_B(Arith.mul(localBreed.getForceCloseFeeRate_B(), new Double(100.0D)));
						localBreed.setForceCloseFeeRate_S(Arith.mul(localBreed.getForceCloseFeeRate_S(), new Double(100.0D)));
					}
					if ((localBreed.getSettleFeeAlgr() != null) && ("1".equals(localBreed.getSettleFeeAlgr().toString()))) {
						str18 = "1";
						localBreed.setSettleFeeRate_B(Arith.mul(localBreed.getSettleFeeRate_B(), new Double(100.0D)));
						localBreed.setSettleFeeRate_S(Arith.mul(localBreed.getSettleFeeRate_S(), new Double(100.0D)));
					}
					if ((localBreed.getSettleMarginAlgr_B() != null) && ("1".equals(localBreed.getSettleMarginAlgr_B().toString()))) {
						str19 = "1";
						localBreed.setSettleMarginRate_B(Arith.mul(localBreed.getSettleMarginRate_B(), new Double(100.0D)));
					}
					if ((localBreed.getSettleMarginAlgr_S() != null) && ("1".equals(localBreed.getSettleMarginAlgr_S().toString()))) {
						str20 = "1";
						localBreed.setSettleMarginRate_S(Arith.mul(localBreed.getSettleMarginRate_S(), new Double(100.0D)));
					}
					if ((localBreed.getPayoutAlgr() != null) && ("1".equals(localBreed.getPayoutAlgr().toString()))) {
						str21 = "1";
						localBreed.setPayoutRate(Arith.mul(localBreed.getPayoutRate(), new Double(100.0D)));
					}
					localBreed.setAddedTax(Arith.mul(localBreed.getAddedTax(), new Double(100.0D)));
					if ((localBreed.getMarginAlgr() != null) && ("1".equals(localBreed.getMarginAlgr().toString()))) {
						localBreed.setMarginItem1(Arith.mul(localBreed.getMarginItem1(), new Double(100.0D)));
						localBreed.setMarginItem2(Arith.mul(localBreed.getMarginItem2(), new Double(100.0D)));
						localBreed.setMarginItem3(Arith.mul(localBreed.getMarginItem3(), new Double(100.0D)));
						localBreed.setMarginItem4(Arith.mul(localBreed.getMarginItem4(), new Double(100.0D)));
						localBreed.setMarginItem5(Arith.mul(localBreed.getMarginItem5(), new Double(100.0D)));
						localBreed.setMarginItem1_S(Arith.mul(localBreed.getMarginItem1_S(), new Double(100.0D)));
						localBreed.setMarginItem2_S(Arith.mul(localBreed.getMarginItem2_S(), new Double(100.0D)));
						localBreed.setMarginItem3_S(Arith.mul(localBreed.getMarginItem3_S(), new Double(100.0D)));
						localBreed.setMarginItem4_S(Arith.mul(localBreed.getMarginItem4_S(), new Double(100.0D)));
						localBreed.setMarginItem5_S(Arith.mul(localBreed.getMarginItem5_S(), new Double(100.0D)));
						localBreed.setMarginItemAssure1(Arith.mul(localBreed.getMarginItemAssure1(), new Double(100.0D)));
						localBreed.setMarginItemAssure2(Arith.mul(localBreed.getMarginItemAssure2(), new Double(100.0D)));
						localBreed.setMarginItemAssure3(Arith.mul(localBreed.getMarginItemAssure3(), new Double(100.0D)));
						localBreed.setMarginItemAssure4(Arith.mul(localBreed.getMarginItemAssure4(), new Double(100.0D)));
						localBreed.setMarginItemAssure5(Arith.mul(localBreed.getMarginItemAssure5(), new Double(100.0D)));
						localBreed.setMarginItemAssure1_S(Arith.mul(localBreed.getMarginItemAssure1_S(), new Double(100.0D)));
						localBreed.setMarginItemAssure2_S(Arith.mul(localBreed.getMarginItemAssure2_S(), new Double(100.0D)));
						localBreed.setMarginItemAssure3_S(Arith.mul(localBreed.getMarginItemAssure3_S(), new Double(100.0D)));
						localBreed.setMarginItemAssure4_S(Arith.mul(localBreed.getMarginItemAssure4_S(), new Double(100.0D)));
						localBreed.setMarginItemAssure5_S(Arith.mul(localBreed.getMarginItemAssure5_S(), new Double(100.0D)));
					}
					if ((localBreed.getFirmMaxHoldQtyAlgr() != null) && ("1".equals(localBreed.getFirmMaxHoldQtyAlgr().toString()))) {
						str22 = "1";
						localBreed.setMaxPercentLimit(Double.valueOf(Arith.mul(localBreed.getMaxPercentLimit().doubleValue(), 100.0F)));
					}
					localBreed.setDelayRecoupRate(Arith.mul(localBreed.getDelayRecoupRate(), new Double(100.0D)));
					localBreed.setDelayRecoupRate_S(Arith.mul(localBreed.getDelayRecoupRate_S(), new Double(100.0D)));
					localBreed.setMaxFeeRate(
							Arith.mul(localBreed.getMaxFeeRate() == null ? new Double(0.0D) : localBreed.getMaxFeeRate(), new Double(100.0D)));
					localBreed.setStoreRecoupRate(localBreed.getStoreRecoupRate());
					i = localBreed.getHoldDaysLimit();
					localBreed.setHoldDaysLimit(localBreed.getHoldDaysLimit());
					if ("1".equals(Integer.valueOf(localBreed.getHoldDaysLimit()))) {
						localBreed.setMaxHoldPositionDay(localBreed.getMaxHoldPositionDay());
					}
				}
				this.log.debug("edit Breed.BreedName:" + localBreed.getBreedName());
			} else {
				paramHttpServletRequest.setAttribute("mraketFee", "1");
				str4 = "1";
				str5 = "1";
				str6 = "1";
				str7 = "1";
				str8 = "1";
				str9 = "1";
				str10 = "1";
				str11 = "1";
				str12 = "1";
				str13 = "1";
				localBreed = new Breed();
			}
			String str24 = "";
			if ((localBreed.getMarginAlgr() != null) && (localBreed.getMarginAlgr().shortValue() == 1)) {
				str24 = "1";
			}
			paramHttpServletRequest.setAttribute("typeBZJ", str24);
			localBreedForm = (BreedForm) convert(localBreed);
			localBreedForm.setCrud(str3);
			localBreedForm.setIsSettle(str23);
			paramHttpServletRequest.setAttribute("type", str4);
			paramHttpServletRequest.setAttribute("type1", str5);
			paramHttpServletRequest.setAttribute("type2", str6);
			paramHttpServletRequest.setAttribute("type3", str7);
			paramHttpServletRequest.setAttribute("type4", str8);
			paramHttpServletRequest.setAttribute("type101", str9);
			paramHttpServletRequest.setAttribute("type102", str10);
			paramHttpServletRequest.setAttribute("type103", str11);
			paramHttpServletRequest.setAttribute("type104", str12);
			paramHttpServletRequest.setAttribute("type105", str13);
			paramHttpServletRequest.setAttribute("typeSettlePriceType", str14);
			paramHttpServletRequest.setAttribute("typeSettleMarginType", str15);
			paramHttpServletRequest.setAttribute("typeSpreadAlgr", str16);
			paramHttpServletRequest.setAttribute("typeFeeAlgr", str17);
			paramHttpServletRequest.setAttribute("typeSettleFeeAlgr", str18);
			paramHttpServletRequest.setAttribute("typeSettleMarginAlgr_B", str19);
			paramHttpServletRequest.setAttribute("typeSettleMarginAlgr_S", str20);
			paramHttpServletRequest.setAttribute("typePayoutAlgr", str21);
			paramHttpServletRequest.setAttribute("firmMaxHoldQtyAlgr", str22);
			paramHttpServletRequest.setAttribute("holdDaysLimit", Integer.valueOf(i));
			updateFormBean(paramActionMapping, paramHttpServletRequest, localBreedForm);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		paramHttpServletRequest.setAttribute("breed", localBreed);
		return paramActionMapping.findForward("edit");
	}

	public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'save' method");
		}
		BreedForm localBreedForm = (BreedForm) paramActionForm;
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("tradeTimes");
		Breed localBreed1 = new Breed();
		BeanUtils.copyProperties(localBreed1, localBreedForm);
		HashSet localHashSet = new HashSet();
		for (int i = 0; (arrayOfString != null) && (i < arrayOfString.length); i++) {
			Integer localObject1 = Integer.valueOf(Integer.parseInt(arrayOfString[i]));
			TradeTime localObject2 = new TradeTime();
			((TradeTime) localObject2).setSectionID((Integer) localObject1);
			localHashSet.add(localObject2);
		}
		localBreed1.setTradeTime(localHashSet);
		String str1 = paramHttpServletRequest.getParameter("type");
		Object localObject1 = paramHttpServletRequest.getParameter("type1");
		Object localObject2 = paramHttpServletRequest.getParameter("type2");
		String str2 = paramHttpServletRequest.getParameter("type3");
		String str3 = paramHttpServletRequest.getParameter("type4");
		String str4 = localBreedForm.getCrud();
		try {
			if ((localBreed1.getSpreadAlgr() != null) && ("1".equals(localBreed1.getSpreadAlgr().toString()))) {
				localBreed1.setSpreadUpLmt(Double.valueOf(Arith.div(localBreed1.getSpreadUpLmt().doubleValue(), new Double(100.0D).doubleValue())));
				localBreed1.setSpreadDownLmt(Double.valueOf(localBreed1.getSpreadDownLmt().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localBreed1.getFeeAlgr() != null) && ("1".equals(localBreed1.getFeeAlgr().toString()))) {
				localBreed1.setFeeRate_B(Double.valueOf(localBreed1.getFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setFeeRate_S(Double.valueOf(localBreed1.getFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setHistoryCloseFeeRate_B(
						Double.valueOf(localBreed1.getHistoryCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setHistoryCloseFeeRate_S(
						Double.valueOf(localBreed1.getHistoryCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setTodayCloseFeeRate_B(
						Double.valueOf(localBreed1.getTodayCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setTodayCloseFeeRate_S(
						Double.valueOf(localBreed1.getTodayCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setForceCloseFeeRate_B(
						Double.valueOf(localBreed1.getForceCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setForceCloseFeeRate_S(
						Double.valueOf(localBreed1.getForceCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localBreed1.getSettleFeeAlgr() != null) && ("1".equals(localBreed1.getSettleFeeAlgr().toString()))) {
				localBreed1.setSettleFeeRate_B(Double.valueOf(localBreed1.getSettleFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localBreed1.setSettleFeeRate_S(Double.valueOf(localBreed1.getSettleFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localBreed1.getSettleMarginAlgr_B() != null) && ("1".equals(localBreed1.getSettleMarginAlgr_B().toString()))) {
				localBreed1
						.setSettleMarginRate_B(Double.valueOf(localBreed1.getSettleMarginRate_B().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localBreed1.getSettleMarginAlgr_S() != null) && ("1".equals(localBreed1.getSettleMarginAlgr_S().toString()))) {
				localBreed1
						.setSettleMarginRate_S(Double.valueOf(localBreed1.getSettleMarginRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localBreed1.getPayoutAlgr() != null) && ("1".equals(localBreed1.getPayoutAlgr().toString()))) {
				localBreed1.setPayoutRate(Double.valueOf(localBreed1.getPayoutRate().doubleValue() / new Double(100.0D).doubleValue()));
			}
			localBreed1.setAddedTax(Double.valueOf(localBreed1.getAddedTax().doubleValue() / new Double(100.0D).doubleValue()));
			if ((localBreed1.getMarginAlgr() != null) && ("1".equals(localBreed1.getMarginAlgr().toString()))) {
				if (localBreed1.getMarginItem1() != null) {
					localBreed1.setMarginItem1(Double.valueOf(localBreed1.getMarginItem1().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem2() != null) {
					localBreed1.setMarginItem2(Double.valueOf(localBreed1.getMarginItem2().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem3() != null) {
					localBreed1.setMarginItem3(Double.valueOf(localBreed1.getMarginItem3().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem4() != null) {
					localBreed1.setMarginItem4(Double.valueOf(localBreed1.getMarginItem4().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem5() != null) {
					localBreed1.setMarginItem5(Double.valueOf(localBreed1.getMarginItem5().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem1_S() != null) {
					localBreed1.setMarginItem1_S(Double.valueOf(localBreed1.getMarginItem1_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem2_S() != null) {
					localBreed1.setMarginItem2_S(Double.valueOf(localBreed1.getMarginItem2_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem3_S() != null) {
					localBreed1.setMarginItem3_S(Double.valueOf(localBreed1.getMarginItem3_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem4_S() != null) {
					localBreed1.setMarginItem4_S(Double.valueOf(localBreed1.getMarginItem4_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItem5_S() != null) {
					localBreed1.setMarginItem5_S(Double.valueOf(localBreed1.getMarginItem5_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure1() != null) {
					localBreed1.setMarginItemAssure1(
							Double.valueOf(localBreed1.getMarginItemAssure1().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure2() != null) {
					localBreed1.setMarginItemAssure2(
							Double.valueOf(localBreed1.getMarginItemAssure2().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure3() != null) {
					localBreed1.setMarginItemAssure3(
							Double.valueOf(localBreed1.getMarginItemAssure3().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure4() != null) {
					localBreed1.setMarginItemAssure4(
							Double.valueOf(localBreed1.getMarginItemAssure4().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure5() != null) {
					localBreed1.setMarginItemAssure5(
							Double.valueOf(localBreed1.getMarginItemAssure5().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure1_S() != null) {
					localBreed1.setMarginItemAssure1_S(
							Double.valueOf(localBreed1.getMarginItemAssure1_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure2_S() != null) {
					localBreed1.setMarginItemAssure2_S(
							Double.valueOf(localBreed1.getMarginItemAssure2_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure3_S() != null) {
					localBreed1.setMarginItemAssure3_S(
							Double.valueOf(localBreed1.getMarginItemAssure3_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure4_S() != null) {
					localBreed1.setMarginItemAssure4_S(
							Double.valueOf(localBreed1.getMarginItemAssure4_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localBreed1.getMarginItemAssure5_S() != null) {
					localBreed1.setMarginItemAssure5_S(
							Double.valueOf(localBreed1.getMarginItemAssure5_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
			}
			if ((localBreed1.getFirmMaxHoldQtyAlgr() != null) && ("1".equals(localBreed1.getFirmMaxHoldQtyAlgr().toString()))) {
				localBreed1.setMaxPercentLimit(Double.valueOf(localBreed1.getMaxPercentLimit().doubleValue() / 100.0D));
			}
			localBreed1.setDelayRecoupRate(Double.valueOf(localBreed1.getDelayRecoupRate().doubleValue() / new Double(100.0D).doubleValue()));
			localBreed1.setDelayRecoupRate_S(Double.valueOf(localBreed1.getDelayRecoupRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			localBreed1.setStoreRecoupRate(localBreed1.getStoreRecoupRate());
			if (localBreed1.getMaxFeeRate() != null) {
				localBreed1.setMaxFeeRate(Double.valueOf(localBreed1.getMaxFeeRate().doubleValue() / new Double(100.0D).doubleValue()));
			}
			Object localObject3;
			if (str4.trim().equals("create")) {
				localObject3 = localBreed1.getBreedID();
				BreedManager localBreedManager = (BreedManager) getBean("breedManager");
				Breed localBreed2 = localBreedManager.getBreedById("" + localObject3);
				if (localBreed2 != null) {
					paramHttpServletRequest.setAttribute("prompt", "此品种已存在，请勿重复添加！");
					return paramActionMapping.findForward("save");
				}
				if (("1".equals(str1)) && (localBreedForm.getMarginItem1() != null) && (!"".equals(localBreedForm.getMarginItem1()))
						&& (localBreedForm.getMarginItemAssure1() != null) && (!"".equals(localBreedForm.getMarginItemAssure1()))) {
					localBreed1.setMarginItem1_S(localBreed1.getMarginItem1());
					localBreed1.setMarginItemAssure1_S(localBreed1.getMarginItemAssure1());
				}
				if (("1".equals(localObject1)) && (localBreedForm.getMarginItem2() != null) && (!"".equals(localBreedForm.getMarginItem2()))
						&& (localBreedForm.getMarginItemAssure2() != null) && (!"".equals(localBreedForm.getMarginItemAssure2()))) {
					localBreed1.setMarginItem2_S(localBreed1.getMarginItem2());
					localBreed1.setMarginItemAssure2_S(localBreed1.getMarginItemAssure2());
				}
				if (("1".equals(localObject2)) && (localBreedForm.getMarginItem3() != null) && (!"".equals(localBreedForm.getMarginItem3()))
						&& (localBreedForm.getMarginItemAssure3() != null) && (!"".equals(localBreedForm.getMarginItemAssure3()))) {
					localBreed1.setMarginItem3_S(localBreed1.getMarginItem3());
					localBreed1.setMarginItemAssure3_S(localBreed1.getMarginItemAssure3());
				}
				if (("1".equals(str2)) && (localBreedForm.getMarginItem4() != null) && (!"".equals(localBreedForm.getMarginItem4()))
						&& (localBreedForm.getMarginItemAssure4() != null) && (!"".equals(localBreedForm.getMarginItemAssure4()))) {
					localBreed1.setMarginItem4_S(localBreed1.getMarginItem4());
					localBreed1.setMarginItemAssure4_S(localBreed1.getMarginItemAssure4());
				}
				if (("1".equals(str3)) && (localBreedForm.getMarginItem5() != null) && (!"".equals(localBreedForm.getMarginItem5()))
						&& (localBreedForm.getMarginItemAssure5() != null) && (!"".equals(localBreedForm.getMarginItemAssure5()))) {
					localBreed1.setMarginItem5_S(localBreed1.getMarginItem5());
					localBreed1.setMarginItemAssure5_S(localBreed1.getMarginItemAssure5());
				}
				localBreedManager.insertBreedAndSettle(localBreed1);
				addSysLog(paramHttpServletRequest, "增加商品品种");
			} else if (str4.trim().equals("update")) {
				if (("1".equals(str1)) && (localBreedForm.getMarginItem1() != null) && (!"".equals(localBreedForm.getMarginItem1()))
						&& (localBreedForm.getMarginItemAssure1() != null) && (!"".equals(localBreedForm.getMarginItemAssure1()))) {
					localBreed1.setMarginItem1_S(localBreed1.getMarginItem1());
					localBreed1.setMarginItemAssure1_S(localBreed1.getMarginItemAssure1());
				}
				if (("1".equals(localObject1)) && (localBreedForm.getMarginItem2() != null) && (!"".equals(localBreedForm.getMarginItem2()))
						&& (localBreedForm.getMarginItemAssure2() != null) && (!"".equals(localBreedForm.getMarginItemAssure2()))) {
					localBreed1.setMarginItem2_S(localBreed1.getMarginItem2());
					localBreed1.setMarginItemAssure2_S(localBreed1.getMarginItemAssure2());
				}
				if (("1".equals(localObject2)) && (localBreedForm.getMarginItem3() != null) && (!"".equals(localBreedForm.getMarginItem3()))
						&& (localBreedForm.getMarginItemAssure3() != null) && (!"".equals(localBreedForm.getMarginItemAssure3()))) {
					localBreed1.setMarginItem3_S(localBreed1.getMarginItem3());
					localBreed1.setMarginItemAssure3_S(localBreed1.getMarginItemAssure3());
				}
				if (("1".equals(str2)) && (localBreedForm.getMarginItem4() != null) && (!"".equals(localBreedForm.getMarginItem4()))
						&& (localBreedForm.getMarginItemAssure4() != null) && (!"".equals(localBreedForm.getMarginItemAssure4()))) {
					localBreed1.setMarginItem4_S(localBreed1.getMarginItem4());
					localBreed1.setMarginItemAssure4_S(localBreed1.getMarginItemAssure4());
				}
				if (("1".equals(str3)) && (localBreedForm.getMarginItem5() != null) && (!"".equals(localBreedForm.getMarginItem5()))
						&& (localBreedForm.getMarginItemAssure5() != null) && (!"".equals(localBreedForm.getMarginItemAssure5()))) {
					localBreed1.setMarginItem5_S(localBreed1.getMarginItem5());
					localBreed1.setMarginItemAssure5_S(localBreed1.getMarginItemAssure5());
				}
				localObject3 = (BreedManager) getBean("breedManager");
				if ("Y".equals(localBreed1.getIsSettle())) {
					((BreedManager) localObject3).updateBreedAndSettle(localBreed1);
				} else {
					((BreedManager) localObject3).updateBreed(localBreed1);
				}
				addSysLog(paramHttpServletRequest, "修改商品品种[" + localBreed1.getBreedID() + "]");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("===>save err：" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("save");
	}

	public ActionForward delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'delete' method");
		}
		BreedManager localBreedManager = (BreedManager) getBean("breedManager");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString != null) {
			this.log.debug("==ids.length:" + arrayOfString.length);
			String str2 = "";
			for (int j = 0; j < arrayOfString.length; j++) {
				String str1 = arrayOfString[j];
				try {
					localBreedManager.deleteBreedById(str1);
					addSysLog(paramHttpServletRequest, "删除商品品种[" + str1 + "]");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str2 = str2 + str1 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
				} catch (RuntimeException localRuntimeException) {
					str2 = str2 + str1 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
				}
			}
			if (!str2.equals("")) {
				str2 = str2.substring(0, str2.length() - 1);
				str2 = str2 + "与其他数据关联，不能删除！";
			}
			str2 = str2 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str2);
		}
		return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'search' method");
		}
		BreedManager localBreedManager = (BreedManager) getBean("breedManager");
		try {
			List localList = localBreedManager.getBreeds(null);
			paramHttpServletRequest.setAttribute("breedList", localList);
		} catch (Exception localException) {
			this.log.error("查询Breed表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("list");
	}

	public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	private void getSelectAttribute(HttpServletRequest paramHttpServletRequest) throws Exception {
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		paramHttpServletRequest.setAttribute("cmdtySortSelect",
				localLookupManager.getSelectLabelValueByTable("T_A_CMDTYSORT", "SortName", "SortID", " order by SortID", (short) 1));
	}
}
