package gnnt.MEBS.delivery.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.delivery.command.model.SettleAddObject;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.util.CommandExecute;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.LogValue;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.LogService;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import gnnt.MEBS.delivery.services.ToolService;
import gnnt.MEBS.delivery.util.SysData;

public class SettleMatchController extends BaseController {
	private final transient Log logger = LogFactory.getLog(SettleMatchController.class);
	private CommandExecute commandExecute;

	public void setCommandExecute(CommandExecute paramCommandExecute) {
		this.commandExecute = paramCommandExecute;
	}

	public CommandExecute getCommandExecute() {
		if (this.commandExecute == null) {
			this.commandExecute = ((CommandExecute) SysData.getBean("w_commandExecute"));
		}
		return this.commandExecute;
	}

	public ModelAndView settleMatchList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("----------enter settleMatchList()-------------");
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleMatchList");
		try {
			PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
			if (localPageInfo == null) {
				localPageInfo = new PageInfo(1, Condition.PAGESIZE, "matchId", false);
			}
			QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
			this.logger.debug("~~~~~~~~~~~~~~~~~conditions:" + localQueryConditions);
			if (localQueryConditions != null) {
				String localObject = (String) localQueryConditions.getConditionValue("firmId", "=");
				this.logger.debug("~~~~~~~~~~~~~~~~firmId:" + (String) localObject);
				if ((localObject != null) && (localObject != null) && (!"".equals(localObject))) {
					localQueryConditions.addCondition(" ", "or",
							"(s.FirmID_S='" + (String) localObject + "' or s.FirmID_B = '" + (String) localObject + "') ");
					localQueryConditions.removeCondition("firmId", "=");
				}
			} else {
				localQueryConditions = new QueryConditions();
			}
			Object localObject = (SettleMatchService) SysData.getBean("w_settleMatchService");
			List localList = ((SettleMatchService) localObject).getSettleMatchList(localQueryConditions, localPageInfo);
			Map localMap1 = (Map) SysData.getBean("w_moduleNameMap");
			Map localMap2 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
			String str = (String) localQueryConditions.getConditionValue("moduleId", "=");
			if (str == null) {
				str = paramHttpServletRequest.getParameter("moduleId");
			}
			if ("2".equals(str)) {
				localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleMatchForwardList");
			}
			localModelAndView.addObject("moduleId", str);
			localModelAndView.addObject("list", localList);
			localModelAndView.addObject("pageInfo", localPageInfo);
			localModelAndView.addObject("oldParams", localMap2);
			localModelAndView.addObject("moduleNameMap", localMap1);
		} catch (Exception localException) {
			localModelAndView.addObject("resultMsg", "操作异常！");
			localException.printStackTrace();
		}
		return localModelAndView;
	}

	public ModelAndView settleView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---查询单条交收信--enter settleView()-------------");
		String str1 = paramHttpServletRequest.getParameter("matchId");
		String str2 = paramHttpServletRequest.getParameter("moduleId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		MoneyDoService localMoneyDoService = (MoneyDoService) SysData.getBean("w_moneyDoService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
		CommodityService localCommodityService = (CommodityService) SysData.getBean("w_commodityService");
		LogService localLogService = (LogService) SysData.getBean("w_logService");
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("content", "like", str1);
		PageInfo localPageInfo = new PageInfo(1, Condition.PAGESIZE, "id", true);
		List localList1 = localLogService.getLogList(localQueryConditions, localPageInfo, null);
		LogValue localLogValue = new LogValue();
		if (localList1.size() > 0) {
			localLogValue = (LogValue) localList1.get(localList1.size() - 1);
		}
		String str3 = localLogValue.getOperator();
		double d = localMoneyDoService.getFirmFunds(localSettleMatch.getFirmID_B());
		List localList2 = (List) SysData.getBean("w_noPLList");
		boolean bool = localList2.contains(localSettleMatch.getModuleId());
		int i = 0;
		int j = 1;
		int k = 0;
		if ((localSettleMatch.getCommodityId() != null) && (!"".equals(localSettleMatch.getCommodityId()))) {
			List localObject = localCommodityService.getT_CommodityList(localSettleMatch.getCommodityId());
			if (((List) localObject).size() > 0) {
				j = Integer.parseInt(((Map) ((List) localObject).get(0)).get("aheadSettlePriceType").toString());
				k = Integer.parseInt(((Map) ((List) localObject).get(0)).get("settleWay").toString());
				if (k == 0) {
					i = Integer.parseInt(((Map) ((List) localObject).get(0)).get("settlePriceType").toString());
				} else {
					i = Integer.parseInt(((Map) ((List) localObject).get(0)).get("delaySettlePriceType").toString());
					if (i == 1) {
						i = 2;
					}
				}
			}
		} else {
			List localObject = localCommodityService.getT_BreedList(localSettleMatch.getBreedId());
			if (((List) localObject).size() > 0) {
				i = Integer.parseInt(((Map) ((List) localObject).get(0)).get("settlePriceType").toString());
			}
		}
		Object localObject = (Map) SysData.getBean("w_moduleNameMap");
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		ToolService localToolService = (ToolService) SysData.getBean("w_toolService");
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleView");
		if ("2".equals(str2)) {
			localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleForwordView");
		}
		if (localSettleMatch.getXml() != null) {
			String str4 = localToolService.getXmlNode(localSettleMatch.getXml(), "MATCHID");
			localModelAndView.addObject("xml", str4);
		}
		localModelAndView.addObject("mark", Boolean.valueOf(!bool));
		localModelAndView.addObject("aheadSettlePriceType", Integer.valueOf(j));
		localModelAndView.addObject("settleMatch", localSettleMatch);
		localModelAndView.addObject("settlePriceType", Integer.valueOf(i));
		localModelAndView.addObject("buyBalance", Double.valueOf(d));
		localModelAndView.addObject("moduleNameMap", localObject);
		localModelAndView.addObject("operator", str3);
		localModelAndView.addObject("moduleId", str2);
		return localModelAndView;
	}

	public ModelAndView viewOperator(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("----------查看所有操作记录-------------");
		String str1 = paramHttpServletRequest.getParameter("matchId");
		int i = str1.length();
		String str2 = "%" + str1 + "%";
		LogService localLogService = (LogService) SysData.getBean("w_logService");
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("content", "like", str2);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, Condition.PAGESIZE, "id", false);
		}
		List localList = localLogService.getLogList(localQueryConditions, localPageInfo, null);
		for (int j = 0; j < localList.size(); j++) {
			String str3 = ((LogValue) localList.get(j)).getContent();
			String[] arrayOfString = str3.split(str1);
			if (str3.indexOf(str1) != str3.length() - str1.length()) {
				if (arrayOfString.length == 1) {
					if (Character.isDigit(arrayOfString[0].charAt(0))) {
						localList.remove(j);
						j--;
					}
				} else if (Character.isDigit(arrayOfString[1].charAt(0))) {
					localList.remove(j);
					j--;
				}
			}
		}
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleViewOperator");
		localModelAndView.addObject("resultList", localList);
		localModelAndView.addObject("matchId", str1);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("noReturn", "1");
		return localModelAndView;
	}

	public ModelAndView viewStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("----------查看配对持仓信息-------------");
		String str = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str);
		RegStockService localRegStockService = (RegStockService) SysData.getBean("w_regStockService");
		RegStock localRegStock = localRegStockService.getRegStockById(localSettleMatch.getRegStockId());
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleViewStock");
		localModelAndView.addObject("result", localRegStock);
		localModelAndView.addObject("weight", Double.valueOf(localSettleMatch.getWeight()));
		return localModelAndView;
	}

	public ModelAndView settleMatchAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("----------enter settleMatchAddForward()-------------");
		CommodityService localCommodityService = (CommodityService) SysData.getBean("w_commodityService");
		List localList = localCommodityService.getCommodityListMap();
		Map localMap1 = (Map) SysData.getBean("w_moduleNameMap");
		Map localMap2 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/createSettleRecord");
		localModelAndView.addObject("commodityList", localList);
		localModelAndView.addObject("moduleNameMap", localMap1);
		return localModelAndView;
	}

	public ModelAndView settleMatchAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---添加交收信息--enter settleAdd()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "warehouseSettle/createSettleRecord");
		String str2 = paramHttpServletRequest.getParameter("dd");
		if (str2 != null) {
			String str3 = delNull(paramHttpServletRequest.getParameter("matchId"));
			this.logger.debug("matchId:" + str3);
			String str4 = delNull(paramHttpServletRequest.getParameter("contractId"));
			String str5 = delNull(paramHttpServletRequest.getParameter("breedId"));
			String str6 = delNull(paramHttpServletRequest.getParameter("commodityId"));
			String str7 = delNull(paramHttpServletRequest.getParameter("FirmID_B"));
			String str8 = delNull(paramHttpServletRequest.getParameter("FirmID_S"));
			String str9 = delNull(paramHttpServletRequest.getParameter("handleResult"));
			String str10 = delNull(paramHttpServletRequest.getParameter("quantity"));
			String str11 = delNull(paramHttpServletRequest.getParameter("moduleId"));
			double d1 = Double.parseDouble(paramHttpServletRequest.getParameter("buyPrice"));
			double d2 = Double.parseDouble(paramHttpServletRequest.getParameter("buyMargin"));
			double d3 = Double.parseDouble(paramHttpServletRequest.getParameter("buyPayout"));
			double d4 = Double.parseDouble(paramHttpServletRequest.getParameter("sellMargin"));
			double d5 = Double.parseDouble(paramHttpServletRequest.getParameter("sellPrice"));
			String str12 = delNull(paramHttpServletRequest.getParameter("regStockId"));
			SettleMatch localSettleMatch = new SettleMatch();
			if ((str4 != null) && (!"".equals(str4))) {
				localSettleMatch.setContractId(Long.parseLong(str4));
			}
			localSettleMatch.setBreedId(Long.parseLong(str5));
			localSettleMatch.setCommodityId(str6);
			localSettleMatch.setFirmID_B(str7);
			localSettleMatch.setFirmID_S(str8);
			localSettleMatch.setResult(Integer.parseInt(str9));
			localSettleMatch.setWeight(Double.parseDouble(str10));
			localSettleMatch.setModuleId(str11);
			localSettleMatch.setBuyMargin(d2);
			localSettleMatch.setBuyPayout(d3);
			localSettleMatch.setBuyPrice(d1);
			localSettleMatch.setSellMargin(d4);
			localSettleMatch.setSellPrice(d5);
			localSettleMatch.setRegStockId(str12);
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(0);
			localLogValue.setContent("添加配对 配对信息编号" + str3);
			localLogValue.setMatchId(str3);
			SettleAddObject localSettleAddObject = new SettleAddObject();
			localSettleAddObject.setSettleMatch(localSettleMatch);
			localSettleAddObject.setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
			localSettleAddObject.setSettleMatchOldId(str3);
			String str13 = getCommandExecute().execute("SETTLEADD", localSettleAddObject, localLogValue);
			setResultMsg(paramHttpServletRequest, str13);
			return new ModelAndView("redirect:" + Condition.PATH + "servlet/settleMatchController." + Condition.POSTFIX + "?funcflg=settleMatchList");
		}
		return localModelAndView;
	}

	public ModelAndView settleModifyRegStockForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("enter 'settleModifyRegStock' method");
		String str = paramHttpServletRequest.getParameter("matchId");
		RegStockService localRegStockService = (RegStockService) SysData.getBean("w_regStockService");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str);
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("breedId", "=", Long.valueOf(localSettleMatch.getBreedId()));
		localQueryConditions.addCondition("firmId", "=", localSettleMatch.getFirmID_S());
		localQueryConditions.addCondition("type", "<>", Integer.valueOf(3));
		localQueryConditions.addCondition("weight - frozenweight", ">=", Double.valueOf(localSettleMatch.getWeight()));
		List localList = localRegStockService.getRegStockList(localQueryConditions, null);
		ListOrderedMap localListOrderedMap = null;
		int i = 1;
		for (int j = 0; j < localList.size(); j++) {
			localListOrderedMap = (ListOrderedMap) localList.get(j);
			if (localListOrderedMap.get("REGSTOCKID").equals(localSettleMatch.getRegStockId())) {
				i = 0;
				break;
			}
		}
		if (i != 0) {
			RegStock localObject = localRegStockService.getRegStockById(localSettleMatch.getRegStockId());
			localList.add(localObject);
		}
		ModelAndView localObject = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleRegStockMod");
		((ModelAndView) localObject).addObject("list", localList);
		((ModelAndView) localObject).addObject("settleMatch", localSettleMatch);
		return localObject;
	}

	public ModelAndView getMatchSettleholdRelevanceList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str1 = paramHttpServletRequest.getParameter("xml");
		String str2 = paramHttpServletRequest.getParameter("BS_Flag");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("matchid", "=", str1);
		localQueryConditions.addCondition("bs_flag", "=", str2);
		List localList = localSettleMatchService.getMatchSettleholdRelevanceList(localQueryConditions, null);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleViewMatchSettlehold");
		localModelAndView.addObject("list", localList);
		return localModelAndView;
	}

	public ModelAndView settleModifyRegStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("enter 'settleModifyRegStock' method");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		String str3 = paramHttpServletRequest.getParameter("regStockId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		LogValue localLogValue = new LogValue();
		localLogValue.setOperator(str1);
		localLogValue.setOperatime(new Date());
		localLogValue.setType(0);
		localLogValue.setContent("修改仓单 配对信息编号" + str2);
		localLogValue.setMatchId(str2);
		SettleObject localSettleObject = new SettleObject();
		localSettleObject.setMatchId(str2);
		localSettleObject.setChangeRegStockId(str3);
		String str4 = getCommandExecute().execute("CHANGEREGSTOCK", localSettleObject, localLogValue);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleRegStockMod");
		localModelAndView.addObject("resultMsg", str4);
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleBalanceForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("enter 'settleBalanceForward' method");
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/balance");
		return localModelAndView;
	}

	public ModelAndView settleBalance(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---交收结算--enter settleBalance()-------------");
		String str1 = "";
		String str2 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		LogValue localLogValue = new LogValue();
		localLogValue.setOperator(str2);
		localLogValue.setOperatime(new Date());
		localLogValue.setType(0);
		localLogValue.setContent("交收结算");
		try {
			str1 = getCommandExecute().execute("SETTLEBALANCE", null, localLogValue);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/balance", "result", str1);
		return localModelAndView;
	}

	public ModelAndView settleIncomePayMent(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("//---收货款--enter settleIncomePayMent()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/incomePayMent");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("收货款 单号" + str2 + " 金额" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			String str5 = getCommandExecute().execute("PAYOUT", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settlePayPayMent(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---付货款--enter settlePayPayMent()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/payPayMent");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("付货款 单号" + str2 + " 金额" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			String str5 = getCommandExecute().execute("INCOME", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleIncomeFromBuyer(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("//---收买方违约金信息--enter settleIncomeFromBuyer()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/incomeFromBuyer");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("买方违约 单号" + str2 + " 收买方违约金" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(true);
			String str5 = getCommandExecute().execute("RECEIVEPENALTY", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleIncomeFromSeller(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("//---收卖方违约金信息--enter settleIncomeFromSeller()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/incomeFromSeller");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("卖方违约 单号" + str2 + " 收卖方违约金" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(false);
			String str5 = getCommandExecute().execute("RECEIVEPENALTY", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settlePayToSeller(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---付卖方违约金信息--enter settlePayToSeller()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/payToSeller");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent(" 单号" + str2 + " 付卖方违约金" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(false);
			String str5 = getCommandExecute().execute("PAYPENALTY", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settlePayToBuyer(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---付买方违约金信息--enter settlePayToBuyer()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/payToBuyer");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent(" 单号" + str2 + " 付买方违约金" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(true);
			String str5 = getCommandExecute().execute("PAYPENALTY", localSettleObject, localLogValue);
			this.logger.debug("resultMSG:" + str5);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleHL_Amount(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---升贴水信息--enter settleHL_Amount()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/HL_Amount");
		String str3 = paramHttpServletRequest.getParameter("dd");
		String str4 = paramHttpServletRequest.getParameter("thisPayMent");
		if (str3 != null) {
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("设置升贴水为:" + str4 + ",单号" + str2);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(true);
			String str5 = getCommandExecute().execute("SETTLEHL", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleSettlePL_B(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---买方交收盈亏信息--enter settleSettlePL_B()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/SettlePL_B");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(0);
			localLogValue.setContent("履约 单号" + str2 + " 买方交收盈亏" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(true);
			String str5 = getCommandExecute().execute("SETTLECHANGEPL", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleSettlePL_S(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---卖方交收盈亏信息--enter settleSettlePL_S()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/SettlePL_S");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
			localLogValue.setOperatime(new Date());
			localLogValue.setType(0);
			localLogValue.setContent("履约 单号" + str2 + " 卖方交收盈亏" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			localSettleObject.setSign(false);
			String str5 = getCommandExecute().execute("SETTLECHANGEPL", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleBailToPayment(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("//---保证金转货款信息--enter settleBailToPayment()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/bailToPayment");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("保证金转货款 单号" + str2 + " 金额" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			String str5 = getCommandExecute().execute("MARGINTURNGOODSPAYMENT", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleCancel(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---作废--enter settleCancel()-------------");
		String str1 = paramHttpServletRequest.getParameter("matchId");
		String str2 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str3 = paramHttpServletRequest.getParameter("moduleId");
		LogValue localLogValue = new LogValue();
		localLogValue.setOperator(str2);
		localLogValue.setOperatime(new Date());
		localLogValue.setType(0);
		localLogValue.setContent("废除交收记录 单号" + str1);
		localLogValue.setMatchId(str1);
		SettleObject localSettleObject = new SettleObject();
		localSettleObject.setMatchId(str1);
		String str4 = getCommandExecute().execute("SETTLECANCEL", localSettleObject, localLogValue);
		setResultMsg(paramHttpServletRequest, str4);
		return new ModelAndView("redirect:" + Condition.PATH + "servlet/settleMatchController." + Condition.POSTFIX + "?funcflg=settleView&matchId="
				+ str1 + "&moduleId=" + str3);
	}

	public ModelAndView settleInvoice(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("enter 'settleInvoice' method");
		String str1 = paramHttpServletRequest.getParameter("matchId");
		String str2 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str3 = paramHttpServletRequest.getParameter("moduleId");
		LogValue localLogValue = new LogValue();
		localLogValue.setOperator(str2);
		localLogValue.setOperatime(new Date());
		localLogValue.setType(0);
		localLogValue.setContent("确认收到发票，单号" + str1);
		localLogValue.setMatchId(str1);
		SettleObject localSettleObject = new SettleObject();
		localSettleObject.setMatchId(str1);
		String str4 = getCommandExecute().execute("SETTLEINVOICE", localSettleObject, localLogValue);
		setResultMsg(paramHttpServletRequest, str4);
		return new ModelAndView("redirect:" + Condition.PATH + "servlet/settleMatchController." + Condition.POSTFIX + "?funcflg=settleView&matchId="
				+ str1 + "&moduleId=" + str3);
	}

	public ModelAndView settleRestore(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("enter 'settleRestore' method");
		String str1 = paramHttpServletRequest.getParameter("matchId");
		String str2 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
		String str3 = localSettleMatch.getModuleId();
		LogValue localLogValue = new LogValue();
		localLogValue.setOperator(str2);
		localLogValue.setOperatime(new Date());
		localLogValue.setType(0);
		localLogValue.setContent("还原 配对信息编号" + str1);
		localLogValue.setMatchId(str1);
		SettleObject localSettleObject = new SettleObject();
		localSettleObject.setMatchId(str1);
		String str4 = getCommandExecute().execute("SETTLERESTORE", localSettleObject, localLogValue);
		setResultMsg(paramHttpServletRequest, str4);
		return new ModelAndView("redirect:" + Condition.PATH + "servlet/settleMatchController." + Condition.POSTFIX + "?funcflg=settleView&matchId="
				+ str1 + "&moduleId=" + str3);
	}

	public ModelAndView returnMoneyForSell(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("enter 'returnMoneyForSell' method");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleBackSeller");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("退卖方保证金 单号" + str2 + " 金额" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			String str5 = getCommandExecute().execute("RETURNMARGINFORSELL", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView returnMoneyForBuy(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---退买方保证金--enter returnMoneyForBuy()-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleBackBuyer");
		String str3 = paramHttpServletRequest.getParameter("dd");
		if (str3 != null) {
			String str4 = paramHttpServletRequest.getParameter("thisPayMent");
			LogValue localLogValue = new LogValue();
			localLogValue.setOperator(str1);
			localLogValue.setOperatime(new Date());
			localLogValue.setType(3);
			localLogValue.setContent("退买方保证金 单号" + str2 + " 金额" + str4);
			localLogValue.setMatchId(str2);
			SettleObject localSettleObject = new SettleObject();
			localSettleObject.setMatchId(str2);
			localSettleObject.setAmount(Double.parseDouble(str4));
			String str5 = getCommandExecute().execute("RETURNMARGINFORBUY", localSettleObject, localLogValue);
			localModelAndView.addObject("resultMsg", str5);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		}
		localModelAndView.addObject("settleMatch", localSettleMatch);
		return localModelAndView;
	}

	public ModelAndView settleHandleOK(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---处理完成--enter settleHandleOK()-------------");
		String str1 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
		String str2 = paramHttpServletRequest.getParameter("moduleId");
		String str3 = paramHttpServletRequest.getParameter("aheadSettlePriceType");
		ModelAndView localModelAndView = null;
		if ("2".equals(str2)) {
			localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleForwordView");
		} else if ("3".equals(str2)) {
			localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleView");
		}
		Map localMap1 = (Map) SysData.getBean("w_moduleNameMap");
		MoneyDoService localMoneyDoService = (MoneyDoService) SysData.getBean("w_moneyDoService");
		String str4 = paramHttpServletRequest.getParameter("dd");
		if (str4 != null) {
			LogValue localObject1 = new LogValue();
			((LogValue) localObject1).setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
			((LogValue) localObject1).setOperatime(new Date());
			((LogValue) localObject1).setType(0);
			((LogValue) localObject1).setContent("单号" + str1 + " 设置完成");
			((LogValue) localObject1).setMatchId(str1);
			SettleObject localObject2 = new SettleObject();
			((SettleObject) localObject2).setMatchId(str1);
			String localObject3 = getCommandExecute().execute("SETTLEFINISH", localObject2, (LogValue) localObject1);
			localModelAndView.addObject("resultMsg", localObject3);
			localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
		}
		Object localObject1 = (LogService) SysData.getBean("w_logService");
		Object localObject2 = new QueryConditions();
		((QueryConditions) localObject2).addCondition("matchId", "=", str1);
		Object localObject3 = ((LogService) localObject1).getLogList((QueryConditions) localObject2, null, null);
		for (int i = 0; i < ((List) localObject3).size(); i++) {
			String str5 = ((LogValue) ((List) localObject3).get(i)).getContent();
			String[] arrayOfString = str5.split(str1);
			if (str5.indexOf(str1) != str5.length() - str1.length()) {
				if (arrayOfString.length == 1) {
					if (Character.isDigit(arrayOfString[0].charAt(0))) {
						((List) localObject3).remove(i);
						i--;
					}
				} else if (Character.isDigit(arrayOfString[1].charAt(0))) {
					((List) localObject3).remove(i);
					i--;
				}
			}
		}
		LogValue localLogValue = new LogValue();
		if (((List) localObject3).size() > 0) {
			localLogValue = (LogValue) ((List) localObject3).get(0);
		}
		double d = localMoneyDoService.getFirmFunds(localSettleMatch.getFirmID_B());
		List localList = (List) SysData.getBean("w_noPLList");
		boolean bool = localList.contains(localSettleMatch.getModuleId());
		Map localMap2 = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		if (localSettleMatch.getXml() != null) {
			ToolService localObject4 = (ToolService) SysData.getBean("w_toolService");
			String str6 = ((ToolService) localObject4).getXmlNode(localSettleMatch.getXml(), "MATCHID");
			localModelAndView.addObject("xml", str6);
		}
		localModelAndView.addObject("mark", Boolean.valueOf(!bool));
		localModelAndView.addObject("buyBalance", Double.valueOf(d));
		Object localObject4 = localLogValue.getOperator();
		localModelAndView.addObject("moduleNameMap", localMap1);
		localModelAndView.addObject("settleMatch", localSettleMatch);
		localModelAndView.addObject("operator", localObject4);
		localModelAndView.addObject("aheadSettlePriceType", str3);
		return localModelAndView;
	}

	public ModelAndView settleTransfer(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//--- enter 'settleTransfer' method...");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		LogService localLogService = (LogService) SysData.getBean("w_logService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		String str3 = "";
		Double localDouble = Double.valueOf(localSettleMatch.getWeight());
		if (localSettleMatch.getIsChangeOwner() != 1) {
			QueryConditions localObject1 = new QueryConditions();
			((QueryConditions) localObject1).addCondition("content", "like", "单号" + str2 + "进行货权转移");
			List localObject2 = localLogService.getLogList((QueryConditions) localObject1, null, null);
			Double localObject3 = Double.valueOf(0.0D);
			Iterator localObject4 = ((List) localObject2).iterator();
			while (((Iterator) localObject4).hasNext()) {
				LogValue localObject5 = (LogValue) ((Iterator) localObject4).next();
				String localObject6 = ((LogValue) localObject5).getContent();
				String[] localObject7 = ((String) localObject6).split("：");
				if (localObject7[1] != null) {
					String localObject8 = localObject7[1].substring(0, localObject7[1].length() - 1);
					if (localObject8 != "") {
						localObject3 = Double.valueOf(Arith.add(Double.parseDouble((String) localObject8), ((Double) localObject3).doubleValue()));
					}
				}
			}
			Double localObject41 = Double.valueOf(Double.parseDouble(paramHttpServletRequest.getParameter("quality")));
			if (Arith.add(((Double) localObject3).doubleValue(), ((Double) localObject41).doubleValue()) < localDouble.doubleValue()) {
				LogValue localObject5 = new LogValue();
				((LogValue) localObject5).setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
				((LogValue) localObject5).setOperatime(new Date());
				((LogValue) localObject5).setType(0);
				((LogValue) localObject5).setContent("单号" + str2 + "进行货权转移，本次数量为：" + localObject41 + "。");
				((LogValue) localObject5).setMatchId(str2);
				localLogService.addLog((LogValue) localObject5);
				str3 = "记录数量成功！";
			} else if (Arith.add(((Double) localObject3).doubleValue(), ((Double) localObject41).doubleValue()) == localDouble.doubleValue()) {
				LogValue localObject5 = new LogValue();
				((LogValue) localObject5).setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
				((LogValue) localObject5).setOperatime(new Date());
				((LogValue) localObject5).setType(0);
				((LogValue) localObject5).setContent("单号" + str2 + "进行货权转移，已完成货权转移。本次数量为：" + localObject41 + "。");
				((LogValue) localObject5).setMatchId(str2);
				SettleObject localObject6 = new SettleObject();
				((SettleObject) localObject6).setMatchId(str2);
				str3 = getCommandExecute().execute("SETTLETRANSFER", localObject6, (LogValue) localObject5);
			} else {
				str3 = "输入数量不符合要求！";
			}
		} else {
			str3 = "设置失败，已完成货权转移！";
		}
		Object localObject1 = (Map) SysData.getBean("w_moduleNameMap");
		Object localObject2 = (MoneyDoService) SysData.getBean("w_moneyDoService");
		localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localObject3 = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/transforSettle");
		Object localObject4 = new QueryConditions();
		((QueryConditions) localObject4).addCondition("matchId", "=", str2);
		Object localObject5 = localLogService.getLogList((QueryConditions) localObject4, null, null);
		for (int i = 0; i < ((List) localObject5).size(); i++) {
			String localObject7 = ((LogValue) ((List) localObject5).get(i)).getContent();
			String[] localObject8 = ((String) localObject7).split(str2);
			if (((String) localObject7).indexOf(str2) != ((String) localObject7).length() - str2.length()) {
				if (localObject8.length == 1) {
					if (Character.isDigit(localObject8[0].charAt(0))) {
						((List) localObject5).remove(i);
						i--;
					}
				} else if (Character.isDigit(localObject8[1].charAt(0))) {
					((List) localObject5).remove(i);
					i--;
				}
			}
		}
		LogValue localLogValue = new LogValue();
		if (((List) localObject5).size() > 0) {
			localLogValue = (LogValue) ((List) localObject5).get(0);
		}
		double d = ((MoneyDoService) localObject2).getFirmFunds(localSettleMatch.getFirmID_B());
		List localList = (List) SysData.getBean("w_noPLList");
		boolean bool = localList.contains(localSettleMatch.getModuleId());
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		String str4 = localLogValue.getOperator();
		((ModelAndView) localObject3).addObject("mark", Boolean.valueOf(!bool));
		((ModelAndView) localObject3).addObject("buyBalance", Double.valueOf(d));
		((ModelAndView) localObject3).addObject("resultMsg", str3);
		((ModelAndView) localObject3).addObject("settleMatch", localSettleMatch);
		((ModelAndView) localObject3).addObject("moduleNameMap", localObject1);
		((ModelAndView) localObject3).addObject("operator", str4);
		return localObject3;
	}

	public ModelAndView settleMatchReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		return new ModelAndView("redirect:" + Condition.PATH + "servlet/settleMatchController." + Condition.POSTFIX
				+ "?funcflg=settleMatchList&_moduleId[%3D]=" + paramHttpServletRequest.getParameter("moduleId"));
	}

	public ModelAndView autoSettleMatch(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("//---自助交收-------------");
		String str1 = (String) paramHttpServletRequest.getSession().getAttribute("logonUser");
		String str2 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/settleView");
		localModelAndView = settleView(paramHttpServletRequest, paramHttpServletResponse);
		LogService localLogService = (LogService) SysData.getBean("w_logService");
		QueryConditions localQueryConditions1 = new QueryConditions();
		localQueryConditions1.addCondition("matchId", "=", str2);
		List localList1 = localLogService.getLogList(localQueryConditions1, null, null);
		for (int i = 0; i < localList1.size(); i++) {
			String str3 = ((LogValue) localList1.get(i)).getContent();
			String[] localObject1 = str3.split(str2);
			if (str3.indexOf(str2) != str3.length() - str2.length()) {
				if (localObject1.length == 1) {
					if (Character.isDigit(localObject1[0].charAt(0))) {
						localList1.remove(i);
						i--;
					}
				} else if (Character.isDigit(localObject1[1].charAt(0))) {
					localList1.remove(i);
					i--;
				}
			}
		}
		LogValue localLogValue1 = new LogValue();
		if (localList1.size() > 0) {
			localLogValue1 = (LogValue) localList1.get(0);
		}
		String str3 = localLogValue1.getOperator();
		Object localObject1 = new LogValue();
		SettleObject localSettleObject = new SettleObject();
		String str4 = paramHttpServletRequest.getParameter("dd");
		String str5 = "";
		if (str4 != null) {
			QueryConditions localQueryConditions2 = new QueryConditions();
			localQueryConditions2.addCondition("content", "like", "单号" + str2 + "进行货权转移");
			List localList2 = localLogService.getLogList(localQueryConditions2, null, null);
			double d1 = 0.0D;
			Object localObject2 = localList2.iterator();
			while (((Iterator) localObject2).hasNext()) {
				LogValue localLogValue2 = (LogValue) ((Iterator) localObject2).next();
				String str6 = localLogValue2.getContent();
				String[] arrayOfString = str6.split("：");
				if (arrayOfString[1] != null) {
					String str7 = arrayOfString[1].substring(0, arrayOfString[1].length() - 1);
					if (str7 != "") {
						d1 = Arith.add(Double.parseDouble(str7), d1);
					}
				}
			}
			if (d1 == 0.0D) {
				localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
				((LogValue) localObject1).setOperator(str1);
				((LogValue) localObject1).setOperatime(new Date());
				((LogValue) localObject1).setType(3);
				((LogValue) localObject1).setContent("设置升贴水为:" + -localSettleMatch.getBuyPayout_Ref() + ",单号" + str2);
				((LogValue) localObject1).setMatchId(str2);
				localSettleObject.setAmount(-localSettleMatch.getBuyPayout_Ref());
				localSettleObject.setMatchId(str2);
				localSettleObject.setSign(true);
				str5 = getCommandExecute().execute("SETTLEHL", localSettleObject, (LogValue) localObject1);
				localObject2 = paramHttpServletRequest.getParameter("contractId");
				localSettleMatchService.releaseRegStock((String) localObject2, localSettleMatch.getRegStockId());
				((LogValue) localObject1).setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
				((LogValue) localObject1).setOperatime(new Date());
				((LogValue) localObject1).setType(0);
				((LogValue) localObject1).setContent("单号" + str2 + " 设置完成");
				((LogValue) localObject1).setMatchId(str2);
				str5 = getCommandExecute().execute("SETTLEFINISHSELF", localSettleObject, (LogValue) localObject1);
			} else {
				double d2 = localSettleMatch.getWeight();
				if (d1 == d2) {
					str5 = "已货权转移，不能操作";
				} else {
					str5 = "已货权转移，不能操作";
				}
			}
		}
		localModelAndView.addObject("resultMsg", str5);
		localSettleMatch = localSettleMatchService.getSettleMatchById(str2);
		localModelAndView.addObject("settleMatch", localSettleMatch);
		localModelAndView.addObject("operator", str3);
		return localModelAndView;
	}

	public ModelAndView transforSettle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str1 = paramHttpServletRequest.getParameter("matchId");
		SettleMatchService localSettleMatchService = (SettleMatchService) SysData.getBean("w_settleMatchService");
		SettleMatch localSettleMatch = localSettleMatchService.getSettleMatchById(str1);
		CommodityService localCommodityService = (CommodityService) SysData.getBean("w_commodityService");
		Commodity localCommodity = localCommodityService.getCommodityById(localSettleMatch.getBreedId() + "", false);
		LogService localLogService = (LogService) SysData.getBean("w_logService");
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("content", "like", "单号" + str1 + "进行货权转移");
		List localList = localLogService.getLogList(localQueryConditions, null, null);
		Double localDouble = Double.valueOf(0.0D);
		Object localObject1 = localList.iterator();
		while (((Iterator) localObject1).hasNext()) {
			LogValue localObject2 = (LogValue) ((Iterator) localObject1).next();
			String str2 = ((LogValue) localObject2).getContent();
			String[] arrayOfString = str2.split("：");
			if (arrayOfString[1] != null) {
				String str3 = arrayOfString[1].substring(0, arrayOfString[1].length() - 1);
				if (str3 != "") {
					localDouble = Double.valueOf(Arith.add(Double.parseDouble(str3), localDouble.doubleValue()));
				}
			}
		}
		localObject1 = Double.valueOf(Arith.sub(localSettleMatch.getWeight(), localDouble.doubleValue()));
		ModelAndView localObject2 = new ModelAndView(Condition.SETTLEPATH + "warehouseSettle/transforSettle");
		((ModelAndView) localObject2).addObject("settleMatch", localSettleMatch);
		((ModelAndView) localObject2).addObject("commodity", localCommodity);
		((ModelAndView) localObject2).addObject("qu", localDouble);
		((ModelAndView) localObject2).addObject("total", localObject1);
		return localObject2;
	}
}
