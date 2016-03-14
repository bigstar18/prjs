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
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.ECSideUtils;
import org.ecside.util.RequestUtils;
import org.springframework.dao.DataIntegrityViolationException;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.LabelValue;
import gnnt.MEBS.timebargain.manage.model.Market;
import gnnt.MEBS.timebargain.manage.model.TradeBreedRuleGC;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.service.BreedManager;
import gnnt.MEBS.timebargain.manage.service.CommodityManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.MarketManager;
import gnnt.MEBS.timebargain.manage.service.TariffManager;
import gnnt.MEBS.timebargain.manage.service.TradeBreedRuleManager;
import gnnt.MEBS.timebargain.manage.service.TradeRuleManager;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.form.CommodityForm;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;

public class CommodityAction extends BaseAction {
	public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'edit' method");
		}
		String str1 = getServlet().getServletContext().getInitParameter("useDelay");
		paramHttpServletRequest.setAttribute("useDelay", str1);
		CommodityForm localCommodityForm = (CommodityForm) paramActionForm;
		String str2 = localCommodityForm.getCrud();
		String str3 = localCommodityForm.getOper();
		String str4 = localCommodityForm.getBreedID();
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		List localList1 = localLookupManager.getAllSectionC(null);
		paramHttpServletRequest.setAttribute("listSection", localList1);
		MarketManager localMarketManager = (MarketManager) getBean("marketManager");
		Map localMap = (Map) localMarketManager.getMarketById(null).get(0);
		paramHttpServletRequest.setAttribute("floatingType", localMap.get("FloatingLossComputeType"));
		Commodity localCommodity = null;
		try {
			getBreedSelectAttribute(paramHttpServletRequest);
			List localList2 = (List) paramHttpServletRequest.getAttribute("breedSelect");
			if (localList2.size() != 2) {
				paramHttpServletRequest.setAttribute("prompt", "商品品种有误");
			} else {
				LabelValue localObject1 = (LabelValue) localList2.get(1);
				paramHttpServletRequest.setAttribute("LabelValue1", localObject1);
			}
			Object localObject1 = (List) paramHttpServletRequest.getAttribute("cmdtySortSelect");
			if (((List) localObject1).size() != 2) {
				paramHttpServletRequest.setAttribute("prompt", "商品分类有误");
			} else {
				LabelValue localObject2 = (LabelValue) ((List) localObject1).get(1);
				paramHttpServletRequest.setAttribute("LabelValue2", localObject2);
			}
			Object localObject2 = "2";
			String str5 = "2";
			String str6 = "2";
			String str7 = "2";
			String str8 = "2";
			String str9 = "1";
			String str10 = "1";
			String str11 = "1";
			String str12 = "1";
			String str13 = "1";
			String str14 = "";
			String str15 = "";
			String str16 = "2";
			String str17 = "2";
			String str18 = "2";
			String str19 = "2";
			String str20 = "2";
			String str21 = "2";
			String str22 = "0";
			int i = 0;
			String str23 = "2";
			if (!str2.trim().equals("create")) {
				Commodity localObject3 = new Commodity();
				((Commodity) localObject3).setCommodityID(localCommodityForm.getCommodityID());
				((Commodity) localObject3).setOper(str3);
				localCommodity = localCommodityManager.getCommodityByIdCURorHIS((Commodity) localObject3);
				this.log.debug("edit Commodity.Name:" + localCommodity.getName());
				paramHttpServletRequest.setAttribute("MarketCode", localCommodity.getMarketCode());
				MarketManager localObject4 = (MarketManager) getBean("marketManager");
				List localObject5 = ((MarketManager) localObject4).getMarketById(localCommodity.getMarketCode());
				Market localMarket = null;
				if ((localObject5 != null) && (((List) localObject5).size() > 0)) {
					Map localObject6 = (Map) ((List) localObject5).get(0);
					if (((Map) localObject6).get("CommoditySetType") != null) {
						localMarket = new Market();
						localMarket.setCommoditySetType(Short.valueOf(Short.parseShort(((Map) localObject6).get("CommoditySetType").toString())));
					}
				}
				paramHttpServletRequest.setAttribute("market", localMarket);
				if ((localCommodity != null) && (localCommodity.getCommodityID() != null) && (!"".equals(localCommodity.getCommodityID()))) {
					if ((localCommodity.getMarginItem1() != null) && (localCommodity.getMarginItem1_S() != null)
							&& (localCommodity.getMarginItemAssure1() != null) && (localCommodity.getMarginItemAssure1_S() != null)
							&& (localCommodity.getMarginItem1().toString().equals(localCommodity.getMarginItem1_S().toString()))
							&& (localCommodity.getMarginItemAssure1().toString().equals(localCommodity.getMarginItemAssure1_S().toString()))) {
						localObject2 = "1";
					}
					if ((localCommodity.getMarginItem2() != null) && (localCommodity.getMarginItem2_S() != null)
							&& (localCommodity.getMarginItemAssure2() != null) && (localCommodity.getMarginItemAssure2_S() != null)
							&& (localCommodity.getMarginItem2().toString().equals(localCommodity.getMarginItem2_S().toString()))
							&& (localCommodity.getMarginItemAssure2().toString().equals(localCommodity.getMarginItemAssure2_S().toString()))) {
						str5 = "1";
					}
					if ((localCommodity.getMarginItem3() != null) && (localCommodity.getMarginItem3_S() != null)
							&& (localCommodity.getMarginItemAssure3() != null) && (localCommodity.getMarginItemAssure3_S() != null)
							&& (localCommodity.getMarginItem3().toString().equals(localCommodity.getMarginItem3_S().toString()))
							&& (localCommodity.getMarginItemAssure3().toString().equals(localCommodity.getMarginItemAssure3_S().toString()))) {
						str6 = "1";
					}
					if ((localCommodity.getMarginItem4() != null) && (localCommodity.getMarginItem4_S() != null)
							&& (localCommodity.getMarginItemAssure4() != null) && (localCommodity.getMarginItemAssure4_S() != null)
							&& (localCommodity.getMarginItem4().toString().equals(localCommodity.getMarginItem4_S().toString()))
							&& (localCommodity.getMarginItemAssure4().toString().equals(localCommodity.getMarginItemAssure4_S().toString()))) {
						str7 = "1";
					}
					if ((localCommodity.getMarginItem5() != null) && (localCommodity.getMarginItem5_S() != null)
							&& (localCommodity.getMarginItemAssure5() != null) && (localCommodity.getMarginItemAssure5_S() != null)
							&& (localCommodity.getMarginItem5().toString().equals(localCommodity.getMarginItem5_S().toString()))
							&& (localCommodity.getMarginItemAssure5().toString().equals(localCommodity.getMarginItemAssure5_S().toString()))) {
						str8 = "1";
					}
					if ((localCommodity.getLimitCmdtyQty() != null) && ("-1".equals(localCommodity.getLimitCmdtyQty().toString()))) {
						str9 = "2";
					}
					if ((localCommodity.getFirmCleanQty() != null) && ("-1".equals(localCommodity.getFirmCleanQty().toString()))) {
						str10 = "2";
					}
					if ((localCommodity.getFirmMaxHoldQty() != null) && ("-1".equals(localCommodity.getFirmMaxHoldQty().toString()))) {
						str11 = "2";
					}
					if ((localCommodity.getOneMaxHoldQty() != null) && ("-1".equals(localCommodity.getOneMaxHoldQty().toString()))) {
						str12 = "2";
					}
					if ((localCommodity.getSettlePriceType() != null) && ("1".equals(localCommodity.getSettlePriceType().toString()))) {
						str14 = "1";
					}
					if ((localCommodity.getSettlePriceType() != null) && ("3".equals(localCommodity.getSettlePriceType().toString()))) {
						str14 = "3";
					}
					if (1 == localCommodity.getSettleMarginType()) {
						str15 = "1";
					}
					if ((localCommodity.getSpreadAlgr() != null) && ("1".equals(localCommodity.getSpreadAlgr().toString()))) {
						str16 = "1";
						localCommodity.setSpreadUpLmt(Arith.mul(localCommodity.getSpreadUpLmt(), new Double(100.0D)));
						localCommodity.setSpreadDownLmt(Arith.mul(localCommodity.getSpreadDownLmt(), new Double(100.0D)));
					}
					if ((localCommodity.getFeeAlgr() != null) && ("1".equals(localCommodity.getFeeAlgr().toString()))) {
						str17 = "1";
						localCommodity.setFeeRate_B(Arith.mul(localCommodity.getFeeRate_B(), new Double(100.0D)));
						localCommodity.setFeeRate_S(Arith.mul(localCommodity.getFeeRate_S(), new Double(100.0D)));
						localCommodity.setHistoryCloseFeeRate_B(Arith.mul(localCommodity.getHistoryCloseFeeRate_B(), new Double(100.0D)));
						localCommodity.setHistoryCloseFeeRate_S(Arith.mul(localCommodity.getHistoryCloseFeeRate_S(), new Double(100.0D)));
						localCommodity.setTodayCloseFeeRate_B(Arith.mul(localCommodity.getTodayCloseFeeRate_B(), new Double(100.0D)));
						localCommodity.setTodayCloseFeeRate_S(Arith.mul(localCommodity.getTodayCloseFeeRate_S(), new Double(100.0D)));
						localCommodity.setForceCloseFeeRate_B(Arith.mul(localCommodity.getForceCloseFeeRate_B(), new Double(100.0D)));
						localCommodity.setForceCloseFeeRate_S(Arith.mul(localCommodity.getForceCloseFeeRate_S(), new Double(100.0D)));
					}
					if ((localCommodity.getSettleFeeAlgr() != null) && ("1".equals(localCommodity.getSettleFeeAlgr().toString()))) {
						str18 = "1";
						localCommodity.setSettleFeeRate_B(Arith.mul(localCommodity.getSettleFeeRate_B(), new Double(100.0D)));
						localCommodity.setSettleFeeRate_S(Arith.mul(localCommodity.getSettleFeeRate_S(), new Double(100.0D)));
					}
					if ((localCommodity.getSettleMarginAlgr_B() != null) && ("1".equals(localCommodity.getSettleMarginAlgr_B().toString()))) {
						str19 = "1";
						localCommodity.setSettleMarginRate_B(Arith.mul(localCommodity.getSettleMarginRate_B(), new Double(100.0D)));
					}
					if ((localCommodity.getSettleMarginAlgr_S() != null) && ("1".equals(localCommodity.getSettleMarginAlgr_S().toString()))) {
						str20 = "1";
						localCommodity.setSettleMarginRate_S(Arith.mul(localCommodity.getSettleMarginRate_S(), new Double(100.0D)));
					}
					if ((localCommodity.getPayoutAlgr() != null) && ("1".equals(localCommodity.getPayoutAlgr().toString()))) {
						str21 = "1";
						localCommodity.setPayoutRate(Arith.mul(localCommodity.getPayoutRate(), new Double(100.0D)));
					}
					if ((localCommodity.getAheadSettlePriceType() != 0) && ("1".equals(String.valueOf(localCommodity.getAheadSettlePriceType())))) {
						str22 = "1";
					}
					localCommodity.setAddedTax(Arith.mul(localCommodity.getAddedTax(), new Double(100.0D)));
					if ((localCommodity.getMarginAlgr() != null) && ("1".equals(localCommodity.getMarginAlgr().toString()))) {
						localCommodity.setMarginItem1(Arith.mul(localCommodity.getMarginItem1(), new Double(100.0D)));
						localCommodity.setMarginItem2(Arith.mul(localCommodity.getMarginItem2(), new Double(100.0D)));
						localCommodity.setMarginItem3(Arith.mul(localCommodity.getMarginItem3(), new Double(100.0D)));
						localCommodity.setMarginItem4(Arith.mul(localCommodity.getMarginItem4(), new Double(100.0D)));
						localCommodity.setMarginItem5(Arith.mul(localCommodity.getMarginItem5(), new Double(100.0D)));
						localCommodity.setMarginItem1_S(Arith.mul(localCommodity.getMarginItem1_S(), new Double(100.0D)));
						localCommodity.setMarginItem2_S(Arith.mul(localCommodity.getMarginItem2_S(), new Double(100.0D)));
						localCommodity.setMarginItem3_S(Arith.mul(localCommodity.getMarginItem3_S(), new Double(100.0D)));
						localCommodity.setMarginItem4_S(Arith.mul(localCommodity.getMarginItem4_S(), new Double(100.0D)));
						localCommodity.setMarginItem5_S(Arith.mul(localCommodity.getMarginItem5_S(), new Double(100.0D)));
						localCommodity.setMarginItemAssure1(Arith.mul(localCommodity.getMarginItemAssure1(), new Double(100.0D)));
						localCommodity.setMarginItemAssure2(Arith.mul(localCommodity.getMarginItemAssure2(), new Double(100.0D)));
						localCommodity.setMarginItemAssure3(Arith.mul(localCommodity.getMarginItemAssure3(), new Double(100.0D)));
						localCommodity.setMarginItemAssure4(Arith.mul(localCommodity.getMarginItemAssure4(), new Double(100.0D)));
						localCommodity.setMarginItemAssure5(Arith.mul(localCommodity.getMarginItemAssure5(), new Double(100.0D)));
						localCommodity.setMarginItemAssure1_S(Arith.mul(localCommodity.getMarginItemAssure1_S(), new Double(100.0D)));
						localCommodity.setMarginItemAssure2_S(Arith.mul(localCommodity.getMarginItemAssure2_S(), new Double(100.0D)));
						localCommodity.setMarginItemAssure3_S(Arith.mul(localCommodity.getMarginItemAssure3_S(), new Double(100.0D)));
						localCommodity.setMarginItemAssure4_S(Arith.mul(localCommodity.getMarginItemAssure4_S(), new Double(100.0D)));
						localCommodity.setMarginItemAssure5_S(Arith.mul(localCommodity.getMarginItemAssure5_S(), new Double(100.0D)));
						localCommodity.setMarginRate_B(Arith.mul(localCommodity.getMarginRate_B(), new Double(100.0D)));
						localCommodity.setMarginRate_S(Arith.mul(localCommodity.getMarginRate_S(), new Double(100.0D)));
					}
					if ((localCommodity.getFirmMaxHoldQtyAlgr() != null) && ("1".equals(localCommodity.getFirmMaxHoldQtyAlgr().toString()))) {
						str23 = "1";
						localCommodity.setMaxPercentLimit(Double.valueOf(Arith.mul(localCommodity.getMaxPercentLimit().doubleValue(), 100.0F)));
					}
					localCommodity.setDelayRecoupRate(Arith.mul(localCommodity.getDelayRecoupRate(), new Double(100.0D)));
					localCommodity.setDelayRecoupRate_S(Arith.mul(localCommodity.getDelayRecoupRate_S(), new Double(100.0D)));
					localCommodity.setStoreRecoupRate(localCommodity.getStoreRecoupRate());
					localCommodity.setMaxFeeRate(Arith.mul(localCommodity.getMaxFeeRate() == null ? new Double(0.0D) : localCommodity.getMaxFeeRate(),
							new Double(100.0D)));
				}
				Object localObject6 = (BreedManager) getBean("breedManager");
				Breed localBreed = ((BreedManager) localObject6).getBreedById(localCommodityForm.getBreedID().toString());
				localCommodity.setContractFactorName(localBreed.getContractFactorName());
				i = localCommodity.getHoldDaysLimit();
			} else {
				localCommodity = new Commodity();
				BreedManager localObject3 = (BreedManager) getBean("breedManager");
				Breed localObject4 = ((BreedManager) localObject3).getBreedById(localCommodityForm.getBreedID().toString());
				localCommodity.setBreedID(((Breed) localObject4).getBreedID());
				localCommodity.setOneMaxHoldQty(((Breed) localObject4).getOneMaxHoldQty());
				localCommodity.setMinQuantityMove(((Breed) localObject4).getMinQuantityMove());
				localCommodity.setMinSettleMoveQty(((Breed) localObject4).getMinSettleMoveQty());
				localCommodity.setMinSettleQty(((Breed) localObject4).getMinSettleQty());
				localCommodity.setContractFactorName(((Breed) localObject4).getContractFactorName());
				localCommodity.setMarginAlgr(((Breed) localObject4).getMarginAlgr());
				localCommodity.setSortID(((Breed) localObject4).getSortID());
				localCommodity.setContractFactor(((Breed) localObject4).getContractFactor());
				localCommodity.setMinPriceMove(((Breed) localObject4).getMinPriceMove());
				localCommodity.setSpreadAlgr(((Breed) localObject4).getSpreadAlgr());
				if ((((Breed) localObject4).getSpreadAlgr() != null) && ("1".equals(((Breed) localObject4).getSpreadAlgr().toString()))) {
					str16 = "1";
					localCommodity.setSpreadUpLmt(Arith.mul(((Breed) localObject4).getSpreadUpLmt(), new Double(100.0D)));
					localCommodity.setSpreadDownLmt(Arith.mul(((Breed) localObject4).getSpreadDownLmt(), new Double(100.0D)));
				} else {
					localCommodity.setSpreadDownLmt(((Breed) localObject4).getSpreadDownLmt());
					localCommodity.setSpreadUpLmt(((Breed) localObject4).getSpreadUpLmt());
				}
				localCommodity.setFeeAlgr(((Breed) localObject4).getFeeAlgr());
				if ((((Breed) localObject4).getFeeAlgr() != null) && ("1".equals(((Breed) localObject4).getFeeAlgr().toString()))) {
					str17 = "1";
					localCommodity.setFeeRate_B(Arith.mul(((Breed) localObject4).getFeeRate_B(), new Double(100.0D)));
					localCommodity.setFeeRate_S(Arith.mul(((Breed) localObject4).getFeeRate_S(), new Double(100.0D)));
					localCommodity.setHistoryCloseFeeRate_B(Arith.mul(((Breed) localObject4).getHistoryCloseFeeRate_B(), new Double(100.0D)));
					localCommodity.setHistoryCloseFeeRate_S(Arith.mul(((Breed) localObject4).getHistoryCloseFeeRate_S(), new Double(100.0D)));
					localCommodity.setTodayCloseFeeRate_B(Arith.mul(((Breed) localObject4).getTodayCloseFeeRate_B(), new Double(100.0D)));
					localCommodity.setTodayCloseFeeRate_S(Arith.mul(((Breed) localObject4).getTodayCloseFeeRate_S(), new Double(100.0D)));
					localCommodity.setForceCloseFeeRate_B(Arith.mul(((Breed) localObject4).getForceCloseFeeRate_B(), new Double(100.0D)));
					localCommodity.setForceCloseFeeRate_S(Arith.mul(((Breed) localObject4).getForceCloseFeeRate_S(), new Double(100.0D)));
				} else {
					localCommodity.setFeeRate_B(((Breed) localObject4).getFeeRate_B());
					localCommodity.setFeeRate_S(((Breed) localObject4).getFeeRate_S());
					localCommodity.setHistoryCloseFeeRate_B(((Breed) localObject4).getHistoryCloseFeeRate_B());
					localCommodity.setHistoryCloseFeeRate_S(((Breed) localObject4).getHistoryCloseFeeRate_S());
					localCommodity.setTodayCloseFeeRate_B(((Breed) localObject4).getTodayCloseFeeRate_B());
					localCommodity.setTodayCloseFeeRate_S(((Breed) localObject4).getTodayCloseFeeRate_S());
					localCommodity.setForceCloseFeeRate_B(((Breed) localObject4).getForceCloseFeeRate_B());
					localCommodity.setForceCloseFeeRate_S(((Breed) localObject4).getForceCloseFeeRate_S());
				}
				localCommodity.setSettleFeeAlgr(((Breed) localObject4).getSettleFeeAlgr());
				if ((((Breed) localObject4).getSettleFeeAlgr() != null) && ("1".equals(((Breed) localObject4).getSettleFeeAlgr().toString()))) {
					str18 = "1";
					localCommodity.setSettleFeeRate_B(Arith.mul(((Breed) localObject4).getSettleFeeRate_B(), new Double(100.0D)));
					localCommodity.setSettleFeeRate_S(Arith.mul(((Breed) localObject4).getSettleFeeRate_S(), new Double(100.0D)));
				} else {
					localCommodity.setSettleFeeRate_B(((Breed) localObject4).getSettleFeeRate_B());
					localCommodity.setSettleFeeRate_S(((Breed) localObject4).getSettleFeeRate_S());
				}
				localCommodity.setForceCloseFeeAlgr(((Breed) localObject4).getForceCloseFeeAlgr());
				localCommodity.setSettleMarginAlgr(((Breed) localObject4).getSettleMarginAlgr());
				localCommodity.setSettleMarginRate_B(((Breed) localObject4).getSettleMarginRate_B());
				localCommodity.setSettleMarginRate_S(((Breed) localObject4).getSettleMarginRate_S());
				localCommodity.setLimitCmdtyQty(((Breed) localObject4).getLimitCmdtyQty());
				localCommodity.setTradeTime(((Breed) localObject4).getTradeTime());
				localCommodity.setAddedTax(Arith.mul(((Breed) localObject4).getAddedTax(), new Double(100.0D)));
				localCommodity.setMarginPriceType(((Breed) localObject4).getMarginPriceType());
				localCommodity.setLowestSettleFee(((Breed) localObject4).getLowestSettleFee());
				localCommodity.setFirmCleanQty(((Breed) localObject4).getFirmCleanQty());
				if ((((Breed) localObject4).getMarginAlgr() != null) && ("1".equals(((Breed) localObject4).getMarginAlgr().toString()))) {
					localCommodity.setMarginItem1(Arith.mul(((Breed) localObject4).getMarginItem1(), new Double(100.0D)));
					localCommodity.setMarginItem2(Arith.mul(((Breed) localObject4).getMarginItem2(), new Double(100.0D)));
					localCommodity.setMarginItem3(Arith.mul(((Breed) localObject4).getMarginItem3(), new Double(100.0D)));
					localCommodity.setMarginItem4(Arith.mul(((Breed) localObject4).getMarginItem4(), new Double(100.0D)));
					localCommodity.setMarginItem5(Arith.mul(((Breed) localObject4).getMarginItem5(), new Double(100.0D)));
					localCommodity.setMarginItem1_S(Arith.mul(((Breed) localObject4).getMarginItem1_S(), new Double(100.0D)));
					localCommodity.setMarginItem2_S(Arith.mul(((Breed) localObject4).getMarginItem2_S(), new Double(100.0D)));
					localCommodity.setMarginItem3_S(Arith.mul(((Breed) localObject4).getMarginItem3_S(), new Double(100.0D)));
					localCommodity.setMarginItem4_S(Arith.mul(((Breed) localObject4).getMarginItem4_S(), new Double(100.0D)));
					localCommodity.setMarginItem5_S(Arith.mul(((Breed) localObject4).getMarginItem5_S(), new Double(100.0D)));
					localCommodity.setMarginItemAssure1(Arith.mul(((Breed) localObject4).getMarginItemAssure1(), new Double(100.0D)));
					localCommodity.setMarginItemAssure2(Arith.mul(((Breed) localObject4).getMarginItemAssure2(), new Double(100.0D)));
					localCommodity.setMarginItemAssure3(Arith.mul(((Breed) localObject4).getMarginItemAssure3(), new Double(100.0D)));
					localCommodity.setMarginItemAssure4(Arith.mul(((Breed) localObject4).getMarginItemAssure4(), new Double(100.0D)));
					localCommodity.setMarginItemAssure5(Arith.mul(((Breed) localObject4).getMarginItemAssure5(), new Double(100.0D)));
					localCommodity.setMarginItemAssure1_S(Arith.mul(((Breed) localObject4).getMarginItemAssure1_S(), new Double(100.0D)));
					localCommodity.setMarginItemAssure2_S(Arith.mul(((Breed) localObject4).getMarginItemAssure2_S(), new Double(100.0D)));
					localCommodity.setMarginItemAssure3_S(Arith.mul(((Breed) localObject4).getMarginItemAssure3_S(), new Double(100.0D)));
					localCommodity.setMarginItemAssure4_S(Arith.mul(((Breed) localObject4).getMarginItemAssure4_S(), new Double(100.0D)));
					localCommodity.setMarginItemAssure5_S(Arith.mul(((Breed) localObject4).getMarginItemAssure5_S(), new Double(100.0D)));
				} else {
					localCommodity.setMarginItem1(((Breed) localObject4).getMarginItem1());
					localCommodity.setMarginItem2(((Breed) localObject4).getMarginItem2());
					localCommodity.setMarginItem3(((Breed) localObject4).getMarginItem3());
					localCommodity.setMarginItem4(((Breed) localObject4).getMarginItem4());
					localCommodity.setMarginItem1_S(((Breed) localObject4).getMarginItem1_S());
					localCommodity.setMarginItem2_S(((Breed) localObject4).getMarginItem2_S());
					localCommodity.setMarginItem3_S(((Breed) localObject4).getMarginItem3_S());
					localCommodity.setMarginItem4_S(((Breed) localObject4).getMarginItem4_S());
					localCommodity.setMarginItemAssure1(((Breed) localObject4).getMarginItemAssure1());
					localCommodity.setMarginItemAssure2(((Breed) localObject4).getMarginItemAssure2());
					localCommodity.setMarginItemAssure3(((Breed) localObject4).getMarginItemAssure3());
					localCommodity.setMarginItemAssure4(((Breed) localObject4).getMarginItemAssure4());
					localCommodity.setMarginItemAssure1_S(((Breed) localObject4).getMarginItemAssure1_S());
					localCommodity.setMarginItemAssure2_S(((Breed) localObject4).getMarginItemAssure2_S());
					localCommodity.setMarginItemAssure3_S(((Breed) localObject4).getMarginItemAssure3_S());
					localCommodity.setMarginItemAssure4_S(((Breed) localObject4).getMarginItemAssure4_S());
					localCommodity.setMarginItem5(((Breed) localObject4).getMarginItem5());
					localCommodity.setMarginItemAssure5(((Breed) localObject4).getMarginItemAssure5());
					localCommodity.setMarginItem5_S(((Breed) localObject4).getMarginItem5_S());
					localCommodity.setMarginItemAssure5_S(((Breed) localObject4).getMarginItemAssure5_S());
				}
				localCommodity.setFirmMaxHoldQtyAlgr(((Breed) localObject4).getFirmMaxHoldQtyAlgr());
				if ((((Breed) localObject4).getFirmMaxHoldQtyAlgr() != null)
						&& ("1".equals(((Breed) localObject4).getFirmMaxHoldQtyAlgr().toString()))) {
					str23 = "1";
					localCommodity.setStartPercentQty(((Breed) localObject4).getStartPercentQty());
					localCommodity.setMaxPercentLimit(Double.valueOf(Arith.mul(((Breed) localObject4).getMaxPercentLimit().doubleValue(), 100.0F)));
				} else {
					localCommodity.setMaxPercentLimit(((Breed) localObject4).getMaxPercentLimit());
				}
				localCommodity.setSettleMarginAlgr_B(((Breed) localObject4).getSettleMarginAlgr_B());
				if ((((Breed) localObject4).getSettleMarginAlgr_B() != null)
						&& ("1".equals(((Breed) localObject4).getSettleMarginAlgr_B().toString()))) {
					str19 = "1";
					localCommodity.setSettleMarginRate_B(Arith.mul(((Breed) localObject4).getSettleMarginRate_B(), new Double(100.0D)));
				} else {
					localCommodity.setSettleMarginRate_B(((Breed) localObject4).getSettleMarginRate_B());
				}
				localCommodity.setSettleMarginAlgr_S(((Breed) localObject4).getSettleMarginAlgr_S());
				if ((((Breed) localObject4).getSettleMarginAlgr_S() != null)
						&& ("1".equals(((Breed) localObject4).getSettleMarginAlgr_S().toString()))) {
					str20 = "1";
					localCommodity.setSettleMarginRate_S(Arith.mul(((Breed) localObject4).getSettleMarginRate_S(), new Double(100.0D)));
				} else {
					localCommodity.setSettleMarginRate_S(((Breed) localObject4).getSettleMarginRate_S());
				}
				localCommodity.setPayoutAlgr(((Breed) localObject4).getPayoutAlgr());
				if ((((Breed) localObject4).getPayoutAlgr() != null) && ("1".equals(((Breed) localObject4).getPayoutAlgr().toString()))) {
					str21 = "1";
					localCommodity.setPayoutRate(Arith.mul(((Breed) localObject4).getPayoutRate(), new Double(100.0D)));
				} else {
					localCommodity.setPayoutRate(((Breed) localObject4).getPayoutRate());
				}
				localCommodity.setFirmMaxHoldQty(((Breed) localObject4).getFirmMaxHoldQty());
				localCommodity.setAddedTaxFactor(((Breed) localObject4).getAddedTaxFactor());
				localCommodity.setSettlePriceType(((Breed) localObject4).getSettlePriceType());
				localCommodity.setBeforeDays(((Breed) localObject4).getBeforeDays());
				localCommodity.setSpecSettlePrice(((Breed) localObject4).getSpecSettlePrice());
				localCommodity.setAheadSettlePriceType(((Breed) localObject4).getAheadSettlePriceType());
				localCommodity.setSettleMarginType(((Breed) localObject4).getSettleMarginType());
				localCommodity.setBeforeDays_M(((Breed) localObject4).getBeforeDays_M());
				if ((((Breed) localObject4).getMarginItem1() != null) && (((Breed) localObject4).getMarginItem1_S() != null)
						&& (((Breed) localObject4).getMarginItemAssure1() != null) && (((Breed) localObject4).getMarginItemAssure1_S() != null)
						&& (((Breed) localObject4).getMarginItem1().toString().equals(((Breed) localObject4).getMarginItem1_S().toString()))
						&& (((Breed) localObject4).getMarginItemAssure1().toString()
								.equals(((Breed) localObject4).getMarginItemAssure1_S().toString()))) {
					localObject2 = "1";
				}
				if ((((Breed) localObject4).getMarginItem2() != null) && (((Breed) localObject4).getMarginItem2_S() != null)
						&& (((Breed) localObject4).getMarginItemAssure2() != null) && (((Breed) localObject4).getMarginItemAssure2_S() != null)
						&& (((Breed) localObject4).getMarginItem2().toString().equals(((Breed) localObject4).getMarginItem2_S().toString()))
						&& (((Breed) localObject4).getMarginItemAssure2().toString()
								.equals(((Breed) localObject4).getMarginItemAssure2_S().toString()))) {
					str5 = "1";
				}
				if ((((Breed) localObject4).getMarginItem3() != null) && (((Breed) localObject4).getMarginItem3_S() != null)
						&& (((Breed) localObject4).getMarginItemAssure3() != null) && (((Breed) localObject4).getMarginItemAssure3_S() != null)
						&& (((Breed) localObject4).getMarginItem3().toString().equals(((Breed) localObject4).getMarginItem3_S().toString()))
						&& (((Breed) localObject4).getMarginItemAssure3().toString()
								.equals(((Breed) localObject4).getMarginItemAssure3_S().toString()))) {
					str6 = "1";
				}
				if ((((Breed) localObject4).getMarginItem4() != null) && (((Breed) localObject4).getMarginItem4_S() != null)
						&& (((Breed) localObject4).getMarginItemAssure4() != null) && (((Breed) localObject4).getMarginItemAssure4_S() != null)
						&& (((Breed) localObject4).getMarginItem4().toString().equals(((Breed) localObject4).getMarginItem4_S().toString()))
						&& (((Breed) localObject4).getMarginItemAssure4().toString()
								.equals(((Breed) localObject4).getMarginItemAssure4_S().toString()))) {
					str7 = "1";
				}
				if ((((Breed) localObject4).getMarginItem5() != null) && (((Breed) localObject4).getMarginItem5_S() != null)
						&& (((Breed) localObject4).getMarginItemAssure5() != null) && (((Breed) localObject4).getMarginItemAssure5_S() != null)
						&& (((Breed) localObject4).getMarginItem5().toString().equals(((Breed) localObject4).getMarginItem5_S().toString()))
						&& (((Breed) localObject4).getMarginItemAssure5().toString()
								.equals(((Breed) localObject4).getMarginItemAssure5_S().toString()))) {
					str8 = "1";
				}
				if ((localCommodity.getLimitCmdtyQty() != null) && ("-1".equals(localCommodity.getLimitCmdtyQty().toString()))) {
					str9 = "2";
				}
				if ((localCommodity.getFirmCleanQty() != null) && ("-1".equals(localCommodity.getFirmCleanQty().toString()))) {
					str10 = "2";
				}
				if ((localCommodity.getFirmMaxHoldQty() != null) && ("-1".equals(localCommodity.getFirmMaxHoldQty().toString()))) {
					str11 = "2";
				}
				if ((localCommodity.getOneMaxHoldQty() != null) && ("-1".equals(localCommodity.getOneMaxHoldQty().toString()))) {
					str12 = "2";
				}
				if ((localCommodity.getSettlePriceType() != null) && ("1".equals(localCommodity.getSettlePriceType().toString()))) {
					str14 = "1";
				}
				if ((localCommodity.getSettlePriceType() != null) && ("3".equals(localCommodity.getSettlePriceType().toString()))) {
					str14 = "3";
				}
				if (1 == localCommodity.getSettleMarginType()) {
					str15 = "1";
				}
				localCommodity.setDelayRecoupRate(Arith.mul(((Breed) localObject4).getDelayRecoupRate(), new Double(100.0D)));
				localCommodity.setDelayRecoupRate_S(Arith.mul(((Breed) localObject4).getDelayRecoupRate_S(), new Double(100.0D)));
				localCommodity.setSettleWay(((Breed) localObject4).getSettleWay());
				localCommodity.setDelayFeeWay(((Breed) localObject4).getDelayFeeWay());
				localCommodity.setStoreRecoupRate(((Breed) localObject4).getStoreRecoupRate());
				localCommodity.setMaxFeeRate(
						Arith.mul(((Breed) localObject4).getMaxFeeRate() == null ? new Double(0.0D) : ((Breed) localObject4).getMaxFeeRate(),
								new Double(100.0D)));
				localCommodity.setDelaySettlePriceType(((Breed) localObject4).getDelaySettlePriceType());
				localCommodity.setHoldDaysLimit(((Breed) localObject4).getHoldDaysLimit());
				localCommodity.setMaxHoldPositionDay(((Breed) localObject4).getMaxHoldPositionDay());
				i = localCommodity.getHoldDaysLimit();
			}
			Object localObject3 = "";
			Object localObject4 = "";
			if (localCommodity.getStatus() != null) {
				if (localCommodity.getStatus().shortValue() == 0) {
					localObject4 = "有效";
				} else if (localCommodity.getStatus().shortValue() == 1) {
					localObject4 = "等待交收";
				} else if (localCommodity.getStatus().shortValue() == 2) {
					localObject4 = "暂停交易";
				}
				localObject3 = localCommodity.getStatus().toString();
			}
			Object localObject5 = "";
			if ((localCommodity.getMarginAlgr() != null) && (localCommodity.getMarginAlgr().shortValue() == 1)) {
				localObject5 = "1";
			}
			paramHttpServletRequest.setAttribute("aheadSettlePriceType", str22);
			paramHttpServletRequest.setAttribute("typeBZJ", localObject5);
			paramHttpServletRequest.setAttribute("status", localObject3);
			paramHttpServletRequest.setAttribute("name", localObject4);
			localCommodityForm = (CommodityForm) convert(localCommodity);
			localCommodityForm.setOper(str3);
			localCommodityForm.setCrud(str2);
			paramHttpServletRequest.setAttribute("type", localObject2);
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
			paramHttpServletRequest.setAttribute("firmMaxHoldQtyAlgr", str23);
			paramHttpServletRequest.setAttribute("holdDaysLimit", Integer.valueOf(i));
			updateFormBean(paramActionMapping, paramHttpServletRequest, localCommodityForm);
		} catch (Exception localException) {
			this.log.error("==err:" + localException);
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
			localException.printStackTrace();
			return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
		}
		return paramActionMapping.findForward("edit");
	}

	public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'save' method");
		}
		String str1 = paramHttpServletRequest.getParameter("logos");
		TradeRuleManager localTradeRuleManager = (TradeRuleManager) getBean("tradeRuleManager");
		TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager) getBean("tradeBreedRuleManager");
		CommodityForm localCommodityForm = (CommodityForm) paramActionForm;
		String str2 = localCommodityForm.getCrud();
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		TariffManager localTariffManager = (TariffManager) getBean("tariffManager");
		Commodity localCommodity = new Commodity();
		BeanUtils.copyProperties(localCommodity, localCommodityForm);
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("tradeTimes");
		HashSet localHashSet = new HashSet();
		for (int i = 0; (arrayOfString != null) && (i < arrayOfString.length); i++) {
			Integer localObject1 = Integer.valueOf(Integer.parseInt(arrayOfString[i]));
			TradeTime localObject2 = new TradeTime();
			((TradeTime) localObject2).setSectionID((Integer) localObject1);
			localHashSet.add(localObject2);
		}
		localCommodity.setTradeTime(localHashSet);
		localCommodity.setUni_Cmdty_Code(localCommodity.getMarketCode() + localCommodity.getCommodityID());
		String str3 = paramHttpServletRequest.getParameter("type1");
		Object localObject1 = paramHttpServletRequest.getParameter("type2");
		Object localObject2 = paramHttpServletRequest.getParameter("type3");
		String str4 = paramHttpServletRequest.getParameter("type4");
		String str5 = paramHttpServletRequest.getParameter("type");
		try {
			if ((localCommodity.getSpreadAlgr() != null) && ("1".equals(localCommodity.getSpreadAlgr().toString()))) {
				localCommodity.setSpreadUpLmt(Double.valueOf(localCommodity.getSpreadUpLmt().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setSpreadDownLmt(Double.valueOf(localCommodity.getSpreadDownLmt().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localCommodity.getFeeAlgr() != null) && ("1".equals(localCommodity.getFeeAlgr().toString()))) {
				localCommodity.setFeeRate_B(Double.valueOf(localCommodity.getFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setFeeRate_S(Double.valueOf(localCommodity.getFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setHistoryCloseFeeRate_B(
						Double.valueOf(localCommodity.getHistoryCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setHistoryCloseFeeRate_S(
						Double.valueOf(localCommodity.getHistoryCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setTodayCloseFeeRate_B(
						Double.valueOf(localCommodity.getTodayCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setTodayCloseFeeRate_S(
						Double.valueOf(localCommodity.getTodayCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setForceCloseFeeRate_B(
						Double.valueOf(localCommodity.getForceCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity.setForceCloseFeeRate_S(
						Double.valueOf(localCommodity.getForceCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localCommodity.getSettleFeeAlgr() != null) && ("1".equals(localCommodity.getSettleFeeAlgr().toString()))) {
				localCommodity
						.setSettleFeeRate_B(Double.valueOf(localCommodity.getSettleFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
				localCommodity
						.setSettleFeeRate_S(Double.valueOf(localCommodity.getSettleFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localCommodity.getSettleMarginAlgr_B() != null) && ("1".equals(localCommodity.getSettleMarginAlgr_B().toString()))) {
				localCommodity.setSettleMarginRate_B(
						Double.valueOf(localCommodity.getSettleMarginRate_B().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localCommodity.getSettleMarginAlgr_S() != null) && ("1".equals(localCommodity.getSettleMarginAlgr_S().toString()))) {
				localCommodity.setSettleMarginRate_S(
						Double.valueOf(localCommodity.getSettleMarginRate_S().doubleValue() / new Double(100.0D).doubleValue()));
			}
			if ((localCommodity.getPayoutAlgr() != null) && ("1".equals(localCommodity.getPayoutAlgr().toString()))) {
				localCommodity.setPayoutRate(Double.valueOf(localCommodity.getPayoutRate().doubleValue() / new Double(100.0D).doubleValue()));
			}
			localCommodity.setAddedTax(Double.valueOf(localCommodity.getAddedTax().doubleValue() / new Double(100.0D).doubleValue()));
			if ((localCommodity.getMarginAlgr() != null) && ("1".equals(localCommodity.getMarginAlgr().toString()))) {
				if (localCommodity.getMarginItem1() != null) {
					localCommodity.setMarginItem1(Double.valueOf(localCommodity.getMarginItem1().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem2() != null) {
					localCommodity.setMarginItem2(Double.valueOf(localCommodity.getMarginItem2().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem3() != null) {
					localCommodity.setMarginItem3(Double.valueOf(localCommodity.getMarginItem3().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem4() != null) {
					localCommodity.setMarginItem4(Double.valueOf(localCommodity.getMarginItem4().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem5() != null) {
					localCommodity.setMarginItem5(Double.valueOf(localCommodity.getMarginItem5().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem1_S() != null) {
					localCommodity
							.setMarginItem1_S(Double.valueOf(localCommodity.getMarginItem1_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem2_S() != null) {
					localCommodity
							.setMarginItem2_S(Double.valueOf(localCommodity.getMarginItem2_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem3_S() != null) {
					localCommodity
							.setMarginItem3_S(Double.valueOf(localCommodity.getMarginItem3_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem4_S() != null) {
					localCommodity
							.setMarginItem4_S(Double.valueOf(localCommodity.getMarginItem4_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItem5_S() != null) {
					localCommodity
							.setMarginItem5_S(Double.valueOf(localCommodity.getMarginItem5_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure1() != null) {
					localCommodity.setMarginItemAssure1(
							Double.valueOf(localCommodity.getMarginItemAssure1().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure2() != null) {
					localCommodity.setMarginItemAssure2(
							Double.valueOf(localCommodity.getMarginItemAssure2().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure3() != null) {
					localCommodity.setMarginItemAssure3(
							Double.valueOf(localCommodity.getMarginItemAssure3().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure4() != null) {
					localCommodity.setMarginItemAssure4(
							Double.valueOf(localCommodity.getMarginItemAssure4().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure5() != null) {
					localCommodity.setMarginItemAssure5(
							Double.valueOf(localCommodity.getMarginItemAssure5().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure1_S() != null) {
					localCommodity.setMarginItemAssure1_S(
							Double.valueOf(localCommodity.getMarginItemAssure1_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure2_S() != null) {
					localCommodity.setMarginItemAssure2_S(
							Double.valueOf(localCommodity.getMarginItemAssure2_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure3_S() != null) {
					localCommodity.setMarginItemAssure3_S(
							Double.valueOf(localCommodity.getMarginItemAssure3_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure4_S() != null) {
					localCommodity.setMarginItemAssure4_S(
							Double.valueOf(localCommodity.getMarginItemAssure4_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
				if (localCommodity.getMarginItemAssure5_S() != null) {
					localCommodity.setMarginItemAssure5_S(
							Double.valueOf(localCommodity.getMarginItemAssure5_S().doubleValue() / new Double(100.0D).doubleValue()));
				}
			}
			if ((localCommodity.getFirmMaxHoldQtyAlgr() != null) && ("1".equals(localCommodity.getFirmMaxHoldQtyAlgr().toString()))) {
				localCommodity.setMaxPercentLimit(Double.valueOf(localCommodity.getMaxPercentLimit().doubleValue() / 100.0D));
			}
			localCommodity.setDelayRecoupRate(Double.valueOf(localCommodity.getDelayRecoupRate().doubleValue() / 100.0D));
			localCommodity.setDelayRecoupRate_S(Double.valueOf(localCommodity.getDelayRecoupRate_S().doubleValue() / 100.0D));
			localCommodity.setStoreRecoupRate(localCommodity.getStoreRecoupRate());
			if (localCommodity.getMaxFeeRate() != null) {
				localCommodity.setMaxFeeRate(Double.valueOf(localCommodity.getMaxFeeRate().doubleValue() / new Double(100.0D).doubleValue()));
			}
			String str6;
			if (str2.trim().equals("create")) {
				if (("1".equals(str5)) && (localCommodityForm.getMarginItem1() != null) && (!"".equals(localCommodityForm.getMarginItem1()))
						&& (localCommodityForm.getMarginItemAssure1() != null) && (!"".equals(localCommodityForm.getMarginItemAssure1()))) {
					localCommodity.setMarginItem1_S(localCommodity.getMarginItem1());
					localCommodity.setMarginItemAssure1_S(localCommodity.getMarginItemAssure1());
				}
				if (("1".equals(str3)) && (localCommodityForm.getMarginItem2() != null) && (!"".equals(localCommodityForm.getMarginItem2()))
						&& (localCommodityForm.getMarginItemAssure2() != null) && (!"".equals(localCommodityForm.getMarginItemAssure2()))) {
					localCommodity.setMarginItem2_S(localCommodity.getMarginItem2());
					localCommodity.setMarginItemAssure2_S(localCommodity.getMarginItemAssure2());
				}
				if (("1".equals(localObject1)) && (localCommodityForm.getMarginItem3() != null) && (!"".equals(localCommodityForm.getMarginItem3()))
						&& (localCommodityForm.getMarginItemAssure3() != null) && (!"".equals(localCommodityForm.getMarginItemAssure3()))) {
					localCommodity.setMarginItem3_S(localCommodity.getMarginItem3());
					localCommodity.setMarginItemAssure3_S(localCommodity.getMarginItemAssure3());
				}
				if (("1".equals(localObject2)) && (localCommodityForm.getMarginItem4() != null) && (!"".equals(localCommodityForm.getMarginItem4()))
						&& (localCommodityForm.getMarginItemAssure4() != null) && (!"".equals(localCommodityForm.getMarginItemAssure4()))) {
					localCommodity.setMarginItem4_S(localCommodity.getMarginItem4());
					localCommodity.setMarginItemAssure4_S(localCommodity.getMarginItemAssure4());
				}
				if (("1".equals(str4)) && (localCommodityForm.getMarginItem5() != null) && (!"".equals(localCommodityForm.getMarginItem5()))
						&& (localCommodityForm.getMarginItemAssure5() != null) && (!"".equals(localCommodityForm.getMarginItemAssure5()))) {
					localCommodity.setMarginItem5_S(localCommodity.getMarginItem5());
					localCommodity.setMarginItemAssure5_S(localCommodity.getMarginItemAssure5());
				}
				str6 = localCommodityManager.getSystemStatus();
				String str7;
				if ((str1 != null) && (str1.equals("true"))) {
					str7 = localCommodity.getBreedID() + "";
					TradeBreedRuleGC localTradeBreedRuleGC = new TradeBreedRuleGC();
					TradeRuleGC localTradeRuleGC = new TradeRuleGC();
					localTradeRuleGC.setCommodityID(localCommodity.getCommodityID());
					List localList1 = localTradeBreedRuleManager.getFirmIDFromMarginForAdd(str7);
					for (int j = 0; j < localList1.size(); j++) {
						Map localMap1 = (Map) localList1.get(j);
						localTradeBreedRuleGC = localTradeBreedRuleManager.getFirmBreedMarginById((String) localMap1.get("FIRMID"), str7);
						BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
						localTradeRuleGC.setFirmID((String) localMap1.get("FIRMID"));
						localTradeRuleManager.insertFirmMargin(localTradeRuleGC);
					}
					List localList2 = localTradeBreedRuleManager.getFirmIDFromFeeForAdd(str7);
					for (int k = 0; k < localList2.size(); k++) {
						Map localMap2 = (Map) localList2.get(k);
						localTradeBreedRuleGC = localTradeBreedRuleManager.getFirmBreedFeeById((String) localMap2.get("FIRMID"), str7);
						BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
						localTradeRuleGC.setFirmID((String) localMap2.get("FIRMID"));
						localTradeRuleManager.insertFirmFee(localTradeRuleGC);
					}
					List localList3 = localTradeBreedRuleManager.getFirmIDFromMaxHoldQtyForAdd(str7);
					for (int m = 0; m < localList3.size(); m++) {
						Map localMap3 = (Map) localList3.get(m);
						localTradeBreedRuleGC = localTradeBreedRuleManager.getFirmBreedMaxHoldQtyById((String) localMap3.get("FIRMID"), str7);
						BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
						localTradeRuleGC.setFirmID((String) localMap3.get("FIRMID"));
						localTradeRuleManager.insertFirmMaxHoldQty(localTradeRuleGC);
					}
					String str8 = AclCtrl.getLogonID(paramHttpServletRequest);
					localCommodityManager.insertCommodity(localCommodity, localTariffManager, str8);
					addSysLog(paramHttpServletRequest, "增加商品[" + localCommodity.getCommodityID() + "]");
				} else {
					str7 = AclCtrl.getLogonID(paramHttpServletRequest);
					localCommodityManager.insertCommodity(localCommodity, localTariffManager, str7);
					addSysLog(paramHttpServletRequest, "增加商品[" + localCommodity.getCommodityID() + "]");
				}
			} else if (str2.trim().equals("update")) {
				if (("1".equals(str5)) && (localCommodityForm.getMarginItem1() != null) && (!"".equals(localCommodityForm.getMarginItem1()))
						&& (localCommodityForm.getMarginItemAssure1() != null) && (!"".equals(localCommodityForm.getMarginItemAssure1()))) {
					localCommodity.setMarginItem1_S(localCommodity.getMarginItem1());
					localCommodity.setMarginItemAssure1_S(localCommodity.getMarginItemAssure1());
				}
				if (("1".equals(str3)) && (localCommodityForm.getMarginItem2() != null) && (!"".equals(localCommodityForm.getMarginItem2()))
						&& (localCommodityForm.getMarginItemAssure2() != null) && (!"".equals(localCommodityForm.getMarginItemAssure2()))) {
					localCommodity.setMarginItem2_S(localCommodity.getMarginItem2());
					localCommodity.setMarginItemAssure2_S(localCommodity.getMarginItemAssure2());
				}
				if (("1".equals(localObject1)) && (localCommodityForm.getMarginItem3() != null) && (!"".equals(localCommodityForm.getMarginItem3()))
						&& (localCommodityForm.getMarginItemAssure3() != null) && (!"".equals(localCommodityForm.getMarginItemAssure3()))) {
					localCommodity.setMarginItem3_S(localCommodity.getMarginItem3());
					localCommodity.setMarginItemAssure3_S(localCommodity.getMarginItemAssure3());
				}
				if (("1".equals(localObject2)) && (localCommodityForm.getMarginItem4() != null) && (!"".equals(localCommodityForm.getMarginItem4()))
						&& (localCommodityForm.getMarginItemAssure4() != null) && (!"".equals(localCommodityForm.getMarginItemAssure4()))) {
					localCommodity.setMarginItem4_S(localCommodity.getMarginItem4());
					localCommodity.setMarginItemAssure4_S(localCommodity.getMarginItemAssure4());
				}
				if (("1".equals(str4)) && (localCommodityForm.getMarginItem5() != null) && (!"".equals(localCommodityForm.getMarginItem5()))
						&& (localCommodityForm.getMarginItemAssure5() != null) && (!"".equals(localCommodityForm.getMarginItemAssure5()))) {
					localCommodity.setMarginItem5_S(localCommodity.getMarginItem5());
					localCommodity.setMarginItemAssure5_S(localCommodity.getMarginItemAssure5());
				}
				str6 = AclCtrl.getLogonID(paramHttpServletRequest);
				localCommodityManager.updateCommodity(localCommodity, localTariffManager, str6);
				addSysLog(paramHttpServletRequest, "修改商品[" + localCommodity.getCommodityID() + "]");
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
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		TariffManager localTariffManager = (TariffManager) getBean("tariffManager");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
		int i = 0;
		if (arrayOfString != null) {
			this.log.debug("==ids.length:" + arrayOfString.length);
			String str2 = "";
			for (int j = 0; j < arrayOfString.length; j++) {
				String str1 = arrayOfString[j];
				try {
					Commodity localCommodity1 = new Commodity();
					localCommodity1.setCommodityID(str1);
					Commodity localCommodity2 = localCommodityManager.getCommodityById(str1);
					String str3 = localCommodity2.getStatus().toString();
					if ("2".equals(str3)) {
						localCommodityManager.deleteCommodityById(localTariffManager, str1);
						addSysLog(paramHttpServletRequest, "删除商品[" + str1 + "]");
						i++;
					} else {
						str2 = str2 + str1 + ",";
						paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]状态为暂停交易时才能删除！");
					}
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					localDataIntegrityViolationException.printStackTrace();
					str2 = str2 + str1 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
				} catch (RuntimeException localRuntimeException) {
					localRuntimeException.printStackTrace();
					str2 = str2 + str1 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
				}
			}
			if (!str2.equals("")) {
				str2 = str2.substring(0, str2.length() - 1);
				str2 = str2 + "与其他数据关联或状态不是暂停交易，不能删除！";
			}
			str2 = str2 + "成功删除" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str2);
		}
		return paramActionMapping.findForward("deleteCommodity");
	}

	public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'search' method");
		}
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		Commodity localCommodity = new Commodity();
		String str1 = paramHttpServletRequest.getParameter("breedID");
		Long localLong = (str1 == null) || (str1.equals("")) ? null : Long.valueOf(str1);
		String str2 = paramHttpServletRequest.getParameter("oper");
		try {
			localCommodity.setBreedID(localLong);
			List localList = null;
			if ("cur".equals(str2)) {
				localList = localCommodityManager.getCommoditys(localCommodity, null);
			} else if ("his".equals(str2)) {
				localList = localCommodityManager.getCommoditysHis(localCommodity, null);
			}
			paramHttpServletRequest.setAttribute("oper", str2);
			paramHttpServletRequest.setAttribute("commodityList", localList);
			String str3 = "";
			if ((localList != null) && (localList.size() > 0)) {
				Map localMap = (Map) localList.get(0);
				str3 = localMap.get("BreedName").toString();
			} else {
				str3 = " ";
			}
			paramHttpServletRequest.setAttribute("breedName", str3);
			paramHttpServletRequest.setAttribute("COMMODITY_STATUS", CommonDictionary.COMMODITY_STATUS);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("查询Commodity表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("list");
	}

	public ActionForward marketChg(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'marketChg' method");
		}
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		try {
			paramHttpServletRequest.setAttribute("breedSelect", localLookupManager.getSelectLabelValueByTable("Breed", "BreedName", "BreedID",
					" where MarketCode='" + paramHttpServletRequest.getParameter("MarketCode") + "'", (short) 1));
		} catch (Exception localException) {
			this.log.error("查询Breed表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("marketChg");
	}

	public ActionForward updateStatus(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'updateStatus' method");
		}
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
		String str1 = paramHttpServletRequest.getParameter("crud");
		int i = 0;
		if (arrayOfString != null) {
			this.log.debug("==ids.length:" + arrayOfString.length);
			Commodity localCommodity = new Commodity();
			String str3 = "";
			for (int j = 0; j < arrayOfString.length; j++) {
				String str2 = arrayOfString[j];
				try {
					localCommodity.setCommodityID(str2);
					if ("correct".equals(str1)) {
						localCommodity.setStatus(Short.valueOf(Short.parseShort("0")));
					} else if ("incorrect".equals(str1)) {
						localCommodity.setStatus(Short.valueOf(Short.parseShort("1")));
					} else if ("pause".equals(str1)) {
						localCommodity.setStatus(Short.valueOf(Short.parseShort("2")));
					}
					localCommodityManager.updateCommodityStatus(localCommodity);
					paramHttpServletRequest.setAttribute("ifSave", "save");
					addSysLog(paramHttpServletRequest, "修改状态[" + str2 + "]");
					i++;
				} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
					str3 = str3 + str2 + ",";
					paramHttpServletRequest.setAttribute("prompt", "[" + str2 + "]修改失败！");
				}
			}
			if (!str3.equals("")) {
				str3 = str3.substring(0, str3.length() - 1);
				str3 = str3 + "修改失败！";
			}
			str3 = str3 + "成功修改" + i + "条纪录！";
			paramHttpServletRequest.setAttribute("prompt", str3);
		}
		return paramActionMapping.findForward("update");
	}

	public ActionForward queryCommodity(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Entering 'queryCommodity' method");
		}
		CommodityManager localCommodityManager = (CommodityManager) getBean("commodityManager");
		QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
		Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
		Sort localSort = localLimit.getSort();
		Map localMap = localSort.getSortValueMap();
		int i = 0;
		int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
		Commodity localCommodity = new Commodity();
		String str1 = paramHttpServletRequest.getParameter("year");
		String str2 = paramHttpServletRequest.getParameter("month");
		String str3 = paramHttpServletRequest.getParameter("oper");
		localCommodity.setYear(str1);
		localCommodity.setMonth(str2);
		try {
			List localList = null;
			if (j < 0) {
				if ("cur".equals(str3)) {
					j = localCommodityManager.queryCommodityCount();
				} else if ("his".equals(str3)) {
					j = localCommodityManager.queryHisCommodityCount();
				}
			}
			localLimit.setRowAttributes(j, 20);
			int k = localLimit.getRowStart() + i + 1;
			int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
			String str4 = ECSideUtils.getDefaultSortSQL(localMap);
			if ("cur".equals(str3)) {
				localList = localCommodityManager.getCurCommoditys(localCommodity, k, m);
			} else if ("his".equals(str3)) {
				localList = localCommodityManager.getHisCommoditys(localCommodity, k, m);
			}
			paramHttpServletRequest.setAttribute("oper", str3);
			paramHttpServletRequest.setAttribute("queryCommodityList", localList);
			paramHttpServletRequest.setAttribute("COMMODITY_STATUS", CommonDictionary.COMMODITY_STATUS);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("查询Commodity表出错：" + localException.getMessage());
			paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
		}
		return paramActionMapping.findForward("queryCommodity");
	}

	public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
	}

	private void getBreedSelectAttribute(HttpServletRequest paramHttpServletRequest) throws Exception {
		LookupManager localLookupManager = (LookupManager) getBean("lookupManager");
		paramHttpServletRequest.setAttribute("breedSelect", localLookupManager.getSelectLabelValueByTable("T_A_BREED", "BreedName", "BreedID",
				" where  BreedID=" + paramHttpServletRequest.getParameter("breedID"), (short) 1));
		paramHttpServletRequest.setAttribute("cmdtySortSelect",
				localLookupManager.getSelectLabelValueByTable("T_A_CMDTYSORT", "SortName", "SortID",
						" where SortID in (select SortID from T_A_BREED where BreedID=" + paramHttpServletRequest.getParameter("breedID")
								+ ") order by SortID",
						(short) 1));
	}
}
