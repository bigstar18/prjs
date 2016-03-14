package gnnt.MEBS.zcjs.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gnnt.MEBS.zcjs.memory.system.RestMsg;
import gnnt.MEBS.zcjs.memory.system.SystemObject;
import gnnt.MEBS.zcjs.memory.system.operate.OperateSystemObject;
import gnnt.MEBS.zcjs.model.MarketStatus;
import gnnt.MEBS.zcjs.model.TradeRestDate;
import gnnt.MEBS.zcjs.services.MarketStatusService;
import gnnt.MEBS.zcjs.services.TradeRestDateService;
import gnnt.MEBS.zcjs.services.TradeTimeService;
import gnnt.MEBS.zcjs.util.SysData;

public class SystemSettingServerRmiImpl extends UnicastRemoteObject implements SystemSettingServerRmi {
	private static final long serialVersionUID = 1L;

	public SystemSettingServerRmiImpl() throws RemoteException {
	}

	public void reloadFlowMode() throws RemoteException {
		boolean status = false;
		MarketStatusService marketStatusService = null;
		synchronized (MarketStatusService.class) {
			if (marketStatusService == null) {
				marketStatusService = (MarketStatusService) SysData.getBean("z_marketStatusService");
			}
		}
		MarketStatus marketStatus = marketStatusService.getObject();
		String isAuto = marketStatus.getIsAuto();
		if ("Y".equals(isAuto)) {
			status = true;
		} else {
			status = false;
		}
		OperateSystemObject.reloadFlowMode(status);
		OperateSystemObject.settingStatus(marketStatus.getCurrentState());
	}

	public void reloadRestMsg() throws RemoteException {
		RestMsg restMsg = new RestMsg();
		List<String> listWeek = new ArrayList();
		List<Integer> listYear = new ArrayList();
		TradeRestDateService tradeRestDateService = null;
		synchronized (TradeRestDateService.class) {
			if (tradeRestDateService == null) {
				tradeRestDateService = (TradeRestDateService) SysData.getBean("z_tradeRestDateService");
			}
		}
		TradeRestDate tradeRestDate = tradeRestDateService.getTradeRestDate();
		if ((tradeRestDate.getWeekRest() != null) && (!"".equals(tradeRestDate.getWeekRest()))) {
			String[] week = tradeRestDate.getWeekRest().split(",");
			for (String string : week) {
				listWeek.add(string);
			}
			if ((tradeRestDate.getYearRest() != null) && (!"".equals(tradeRestDate.getYearRest()))) {
				String[] year = tradeRestDate.getYearRest().split(",");
				for (String string2 : year) {
					listYear.add(Integer.valueOf(Integer.parseInt(string2)));
				}
				restMsg.setRestDays(listWeek);
				restMsg.setRestWeeks(listYear);
				OperateSystemObject.reloadRestMsg(restMsg);
			}
		}
	}

	public void reloadTradeTimes() throws RemoteException {
		SystemObject systemObject = SystemObject.createInstance();
		List<gnnt.MEBS.zcjs.model.TradeTime> tradeTimeList = new ArrayList();
		List<gnnt.MEBS.zcjs.memory.system.TradeTime> tradeTimeList2 = new ArrayList();
		TradeTimeService tradeTimeService = null;
		synchronized (TradeTimeService.class) {
			if (tradeTimeService == null) {
				tradeTimeService = (TradeTimeService) SysData.getBean("z_tradeTimeService");
			}
		}
		tradeTimeList = tradeTimeService.getObjectList(null, null);
		Date currentDate = new Date();
		for (gnnt.MEBS.zcjs.model.TradeTime tradeTime : tradeTimeList) {
			String startTime = tradeTime.getStartTime();
			String entTime = tradeTime.getEndTime();
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
			String dateNowStr = dateFormat.format(dateNow);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd-hh:mm");
			String str1 = dateNowStr + "-" + startTime;
			String str2 = dateNowStr + "-" + entTime;
			try {
				Date startDate = simpleDateFormat.parse(str1);
				Date endDate = simpleDateFormat.parse(str2);
				gnnt.MEBS.zcjs.memory.system.TradeTime tradeTime2 = new gnnt.MEBS.zcjs.memory.system.TradeTime();
				tradeTime2.setStartTime(startDate);
				tradeTime2.setEndTime(endDate);
				tradeTimeList2.add(tradeTime2);
				if ((currentDate.compareTo(startDate) < 0) || ((currentDate.compareTo(startDate) >= 0) && (currentDate.compareTo(endDate) <= 0))) {
					systemObject.setCurrentTradeTime(tradeTime2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OperateSystemObject.reloadTradeTime(tradeTimeList2);
	}

	public void setManualOrder(int status) throws RemoteException {
		OperateSystemObject.settingNextStatus(status);
	}

	public SystemObject getSystemObject() throws RemoteException {
		return SystemObject.createInstance();
	}

	public String systemCancleSubRmi(Long goodsOrderId) throws RemoteException {
		String msg = "";
		// try {
		// GoodsOrderService goodsOrderService = null;
		// synchronized (GoodsOrderService.class) {
		// if (goodsOrderService == null) {
		// goodsOrderService = (GoodsOrderService) SysData.getBean("z_goodsOrderService");
		// }
		// }
		// GoodsOrder goodsOrderForOld = goodsOrderService.getObject(goodsOrderId.longValue());
		// SubmitObject submitObject = new SubmitObject();
		// GoodsOrderMsg goodsOrderMsg = new GoodsOrderMsg();
		//
		// GoodsOrder goodsOrder = new GoodsOrder();
		// CopyObjectParamUtil.bindData(goodsOrderForOld, goodsOrder);
		// goodsOrderMsg.setGoodsOrder(goodsOrder);
		// TradeCommodityMsg tradeCommodityMsg = new TradeCommodityMsg();
		//
		// TradeCommodityMsgService tradeCommodityMsgService = null;
		// synchronized (TradeCommodityMsgService.class) {
		// if (tradeCommodityMsgService == null) {
		// tradeCommodityMsgService = (TradeCommodityMsgService) SysData.getBean("z_tradeCommodityMsgService");
		// }
		// }
		// tradeCommodityMsg = tradeCommodityMsgService.getObject(goodsOrder.getTradeCommodityMsgId());
		//
		// goodsOrderMsg.setOldBail(goodsOrderForOld.getTradeBail());
		// goodsOrderMsg.setOldPoundage(goodsOrderForOld.getTradePoundage());
		// goodsOrderMsg.setTradeCommodityMsg(tradeCommodityMsg);
		// goodsOrderMsg.setRelatedGoodsOrderId(goodsOrderId.longValue());
		// submitObject.setMsg(goodsOrderMsg);
		// SubmitFacade submit = (SubmitFacade) SysData.getBean("z_submitFacade");
		// submitObject.setCommandName("systemdrawGoodsOrder");
		// Map<String, Object> map = submit.dealSubmit(submitObject);
		//
		// String transResult = (String) map.get("transResult");
		//
		// msg = (String) map.get("transResult");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return msg;
	}
}
