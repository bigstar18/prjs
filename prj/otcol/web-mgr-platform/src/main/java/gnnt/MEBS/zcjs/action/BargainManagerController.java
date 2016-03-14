package gnnt.MEBS.zcjs.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.zcjs.model.Delivery;
import gnnt.MEBS.zcjs.model.HisTrade;
import gnnt.MEBS.zcjs.model.TradeModule;
import gnnt.MEBS.zcjs.model.innerObject.KeyValue;
import gnnt.MEBS.zcjs.services.BargainManagerService;
import gnnt.MEBS.zcjs.services.TradeModuleService;
import gnnt.MEBS.zcjs.util.SysData;

public class BargainManagerController extends BaseController {
	private final transient Log logger = LogFactory.getLog(BargainManagerController.class);

	private BargainManagerService getBeanService() {
		BargainManagerService localBargainManagerService = null;
		synchronized (BargainManagerService.class) {
			if (localBargainManagerService == null) {
				localBargainManagerService = (BargainManagerService) SysData.getBean("z_bargainManagerService");
			}
		}
		return localBargainManagerService;
	}

	private TradeModuleService getBeanOfTradeModuleService() {
		TradeModuleService localTradeModuleService = null;
		synchronized (TradeModuleService.class) {
			if (localTradeModuleService == null) {
				localTradeModuleService = (TradeModuleService) SysData.getBean("z_tradeModuleService");
			}
		}
		return localTradeModuleService;
	}

	public ModelAndView getHisTradelist(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("entering 'list' method...");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, Condition.PAGESIZE, "tradeNo", false);
		}
		String str = SysData.getConfig("quantityDecimalsString");
		paramHttpServletRequest.getSession().setAttribute("quantityDecimalsString", str);
		List localList = getBeanService().getHisTradeList(localQueryConditions, localPageInfo);
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainManager/tradeResultHisList");
		localModelAndView.addObject("resultList", localList);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		return localModelAndView;
	}

	public ModelAndView getDeliveryList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("entering 'getDeliveryList' method...");
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, Condition.PAGESIZE, "tradeId", false);
		}
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		if (localQueryConditions == null) {
			localQueryConditions = new QueryConditions();
		}
		localQueryConditions.addCondition("TradeNo", "=", Long.valueOf(l));
		localQueryConditions.addCondition("status", "<>", Integer.valueOf(4));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		List localList = getBeanService().getDeliveryTableList(localQueryConditions, null);
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		TradeModule localTradeModule = getBeanOfTradeModuleService().getObject("3");
		if ((localTradeModule.getIsSettle() == null) || ("".equals(localTradeModule.getIsSettle()))) {
			localTradeModule.setIsSettle("N");
		}
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainManager/deliveryList");
		localModelAndView.addObject("num", Integer.valueOf(localList.size()));
		localModelAndView.addObject("resultList", localList);
		localModelAndView.addObject("hisTrade", localHisTrade);
		localModelAndView.addObject("hasWarehouse", localTradeModule.getIsSettle());
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		return localModelAndView;
	}

	public ModelAndView auditing(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainManager/auditing");
		localModelAndView.addObject("hisTrade", localHisTrade);
		return localModelAndView;
	}

	public ModelAndView autoDelivery(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainManager/autoDelivery");
		localModelAndView.addObject("hisTrade", localHisTrade);
		return localModelAndView;
	}

	public ModelAndView auditingFaitch(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str = "";
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeNo"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		int i = Integer.parseInt(paramHttpServletRequest.getParameter("type"));
		double d1 = Double.parseDouble(paramHttpServletRequest.getParameter("quantity"));
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("tradeNo", "=", Long.valueOf(l));
		localQueryConditions.addCondition("status", "<>", Integer.valueOf(4));
		double d2 = getBeanService().getSum(localQueryConditions);
		double d3 = d1 / localHisTrade.getQuantity();
		if (d2 + d1 > localHisTrade.getQuantity()) {
			str = "数量设置已超过成交数量，请重新设置。";
		} else {
			Delivery localObject = new Delivery();
			((Delivery) localObject).setDeliveryId(getBeanService().getDeliveryId());
			((Delivery) localObject).setBreedId(localHisTrade.getBreedId());
			((Delivery) localObject).setBuyMargin(localHisTrade.getTradeBail_B() * d3);
			((Delivery) localObject).setFirmId_b(localHisTrade.getFirmId_B());
			((Delivery) localObject).setFirmId_s(localHisTrade.getFirmId_S());
			((Delivery) localObject).setPrice(localHisTrade.getPrice());
			((Delivery) localObject).setQuantity(d1);
			((Delivery) localObject).setRegStockId("");
			((Delivery) localObject).setSellMargin(localHisTrade.getTradeBail_S() * d3);
			((Delivery) localObject).setStatus(2);
			((Delivery) localObject).setTradeNo(localHisTrade.getTradeNo());
			((Delivery) localObject).setType(i);
			try {
				getBeanService().insertDelivery((Delivery) localObject);
				str = "违约设置成功！";
			} catch (Exception localException) {
				localException.printStackTrace();
				str = "违约设置失败";
			}
		}
		setResultMsg(paramHttpServletRequest, str);
		Object localObject = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId=" + l);
	}

	public ModelAndView autoDeliveryFaitch(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str = "";
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeNo"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		double d1 = Double.parseDouble(paramHttpServletRequest.getParameter("quantity"));
		double d2 = d1 / localHisTrade.getQuantity();
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("tradeNo", "=", Long.valueOf(l));
		localQueryConditions.addCondition("status", "<>", Integer.valueOf(4));
		double d3 = getBeanService().getSum(localQueryConditions);
		if (d3 + d1 > localHisTrade.getQuantity()) {
			str = "数量设置已超过剩余数量，请重新设置。";
		} else {
			Delivery localObject = new Delivery();
			((Delivery) localObject).setDeliveryId(getBeanService().getDeliveryId());
			((Delivery) localObject).setBreedId(localHisTrade.getBreedId());
			((Delivery) localObject).setBuyMargin(localHisTrade.getTradeBail_B() * d2);
			((Delivery) localObject).setFirmId_b(localHisTrade.getFirmId_B());
			((Delivery) localObject).setFirmId_s(localHisTrade.getFirmId_S());
			((Delivery) localObject).setPrice(localHisTrade.getPrice());
			((Delivery) localObject).setQuantity(d1);
			((Delivery) localObject).setRegStockId("");
			((Delivery) localObject).setSellMargin(localHisTrade.getTradeBail_S() * d2);
			((Delivery) localObject).setStatus(2);
			((Delivery) localObject).setTradeNo(localHisTrade.getTradeNo());
			((Delivery) localObject).setType(5);
			try {
				getBeanService().insertDelivery((Delivery) localObject);
				str = "自主交收设置成功！";
			} catch (Exception localException) {
				localException.printStackTrace();
				str = "自主交收设置失败！";
			}
		}
		setResultMsg(paramHttpServletRequest, str);
		Object localObject = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId=" + l);
	}

	public ModelAndView deleteMatch(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
		int i = 0;
		int j = 0;
		String str2 = "";
		HisTrade localHisTrade = null;
		Delivery localDelivery = null;
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		for (int k = 0; k < arrayOfString.length; k++) {
			localDelivery = getBeanService().getDelivery(Long.parseLong(arrayOfString[k]));
			localHisTrade = getBeanService().getHisTrade(l);
			if (localHisTrade.getStatus() == 3) {
				i = 1;
			}
			if ((localDelivery.getStatus() == 3) || (localDelivery.getStatus() == 4)) {
				j = 1;
			}
		}
		if ((i == 0) && (j == 0)) {
			try {
				getBeanService().deleteMatch(arrayOfString, str1);
				str2 = "废除成功!";
			} catch (Exception localException) {
				localException.printStackTrace();
				str2 = "废除失败!";
			}
		} else if (i == 1) {
			str2 = "此成交记录已被处理完成，无法进行操作！";
		} else {
			str2 = "请确认要废除的配对状态为未确认或已确认！";
		}
		setResultMsg(paramHttpServletRequest, str2);
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs" + "?funcflg=getDeliveryList&tradeId=" + l);
	}

	public ModelAndView send(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeNo"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		String str2 = "";
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("tradeNo", "=", Long.valueOf(l));
		localQueryConditions.addCondition("status", "<>", Integer.valueOf(4));
		double d = getBeanService().getSum(localQueryConditions);
		if (localHisTrade.getStatus() == 3) {
			str2 = "此合同已处理完成，无法发送仓库！";
		} else if (d != localHisTrade.getQuantity()) {
			str2 = "此合同已处理数量之和小于总合同应处理数量，请添加合同！";
		} else {
			try {
				str2 = "发送仓库成功";
				getBeanService().send(l, str1);
			} catch (Exception localException) {
				localException.printStackTrace();
				str2 = "发送仓库失败";
			}
		}
		setResultMsg(paramHttpServletRequest, str2);
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId=" + l);
	}

	public ModelAndView selectFit(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("entering 'selectFit' method...");
		PageInfo localPageInfo = null;
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, Condition.PAGESIZE, "to_number(regStockID)", false);
		}
		String str = "";
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		List localList = getBeanService().getRegStockFit(localHisTrade, localPageInfo);
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainManager/matchSettle");
		localModelAndView.addObject("tradeId", Long.valueOf(l));
		localModelAndView.addObject("quantity", Double.valueOf(localHisTrade.getQuantity()));
		localModelAndView.addObject("resultList", localList);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		localModelAndView.addObject("hisTrade", localHisTrade);
		return localModelAndView;
	}

	public ModelAndView match(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		this.logger.debug("entering 'match' method...");
		String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		String str2 = "";
		double d1 = 0.0D;
		ArrayList localArrayList = new ArrayList();
		KeyValue localKeyValue = null;
		for (int i = 0; i < arrayOfString.length; i++) {
			String str3 = arrayOfString[i];
			double d3 = Double.parseDouble(paramHttpServletRequest.getParameter(str3));
			localKeyValue = new KeyValue();
			localKeyValue.setKey(str3);
			localKeyValue.setValue(Double.valueOf(d3));
			localArrayList.add(localKeyValue);
			d1 += d3;
		}
		double d2 = Double.parseDouble(paramHttpServletRequest.getParameter("quantity"));
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("tradeNo", "=", Long.valueOf(l));
		localQueryConditions.addCondition("type", "=", Integer.valueOf(1));
		localQueryConditions.addCondition("status", "<>", Integer.valueOf(4));
		double d4 = getBeanService().getSum(localQueryConditions);
		if (d2 < d1) {
			str2 = "配对数量大于交收数量，请重新选择！";
		} else if (d2 < d4 + d1) {
			str2 = "此次配对数量与已有配对数量之和大于交收数量，请重新选择！";
		} else {
			try {
				getBeanService().match(localArrayList, str1, l);
				str2 = "配对成功";
			} catch (Exception localException) {
				str2 = "配对失败";
				localException.printStackTrace();
			}
		}
		setResultMsg(paramHttpServletRequest, str2);
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs" + "?funcflg=getDeliveryList&tradeId=" + l);
	}

	public ModelAndView matchForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeId"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "bargainManager/matchOne");
		localModelAndView.addObject("hisTrade", localHisTrade);
		return localModelAndView;
	}

	public ModelAndView dealMatch(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str1 = "";
		String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
		long l = Long.parseLong(paramHttpServletRequest.getParameter("tradeNo"));
		HisTrade localHisTrade = getBeanService().getHisTrade(l);
		double d1 = Double.parseDouble(paramHttpServletRequest.getParameter("trueQuantity"));
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("tradeNo", "=", Long.valueOf(l));
		localQueryConditions.addCondition("status", "<>", Integer.valueOf(4));
		double d2 = d1 / localHisTrade.getQuantity();
		double d3 = getBeanService().getSum(localQueryConditions);
		if (Arith.add(d3, d1) > localHisTrade.getQuantity()) {
			str1 = "已添加的交易信息总数量大于总交收数量，请重新确认添加";
			setResultMsg(paramHttpServletRequest, str1);
			return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs?funcflg=matchForward&tradeId=" + l);
		}
		Delivery localDelivery = new Delivery();
		localDelivery.setDeliveryId(getBeanService().getDeliveryId());
		localDelivery.setBreedId(localHisTrade.getBreedId());
		localDelivery.setBuyMargin(localHisTrade.getTradeBail_B() * d2);
		localDelivery.setFirmId_b(localHisTrade.getFirmId_B());
		localDelivery.setFirmId_s(localHisTrade.getFirmId_S());
		localDelivery.setPrice(localHisTrade.getPrice());
		localDelivery.setQuantity(d1);
		localDelivery.setRegStockId("");
		localDelivery.setSellMargin(localHisTrade.getTradeBail_S() * d2);
		localDelivery.setStatus(2);
		localDelivery.setTradeNo(localHisTrade.getTradeNo());
		localDelivery.setType(1);
		try {
			getBeanService().matchOne(localDelivery, str2, l, d1);
			str1 = "添加成功！";
			setResultMsg(paramHttpServletRequest, str1);
			return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs?funcflg=getDeliveryList&tradeId=" + l);
		} catch (Exception localException) {
			localException.printStackTrace();
			str1 = "添加失败！";
			setResultMsg(paramHttpServletRequest, str1);
		}
		return new ModelAndView("redirect:" + Condition.PATH + "bargainManagerController.zcjs?funcflg=matchForward&tradeId=" + l);
	}
}
