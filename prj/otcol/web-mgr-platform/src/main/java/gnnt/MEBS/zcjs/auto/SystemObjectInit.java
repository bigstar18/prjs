package gnnt.MEBS.zcjs.auto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.zcjs.memory.system.RestMsg;
import gnnt.MEBS.zcjs.memory.system.SystemObject;
import gnnt.MEBS.zcjs.memory.system.SystemState;
import gnnt.MEBS.zcjs.model.MarketStatus;
import gnnt.MEBS.zcjs.model.TradeRestDate;
import gnnt.MEBS.zcjs.services.MarketStatusService;
import gnnt.MEBS.zcjs.services.TradeRestDateService;
import gnnt.MEBS.zcjs.services.TradeTimeService;

public class SystemObjectInit {
	private final transient Log logger = LogFactory.getLog(SystemObjectInit.class);
	private MarketStatusService marketStatusService;
	private TradeRestDateService tradeRestDateService;
	private TradeTimeService tradeTimeService;

	public void setMarketStatusService(MarketStatusService marketStatusService) {
		this.marketStatusService = marketStatusService;
	}

	public void setTradeRestDateService(TradeRestDateService tradeRestDateService) {
		this.tradeRestDateService = tradeRestDateService;
	}

	public void setTradeTimeService(TradeTimeService tradeTimeService) {
		this.tradeTimeService = tradeTimeService;
	}

	public void init() {
		try {
			SystemObject systemObject = SystemObject.createInstance();

			SystemState currentState = new SystemState();
			SystemState nextState = new SystemState();
			MarketStatus marketStatus = this.marketStatusService.getObject();
			currentState.setState(marketStatus.getCurrentState());
			nextState.setState(marketStatus.getNextState());
			if ("Y".equals(marketStatus.getIsAuto())) {
				systemObject.setAuto(true);
				currentState.setAuto(true);
				nextState.setAuto(true);
			} else {
				systemObject.setAuto(false);
				currentState.setAuto(false);
				nextState.setAuto(false);
			}
			RestMsg restMsg = new RestMsg();
			TradeRestDate tradeRestDate = this.tradeRestDateService.getTradeRestDate();
			if ((tradeRestDate.getWeekRest() != null) && (!"".equals(tradeRestDate.getWeekRest()))) {
				List<String> listWeek = new ArrayList();
				String[] week = tradeRestDate.getWeekRest().split(",");
				for (String string : week) {
					if (Integer.parseInt(string) < 7) {
						string = (Integer.parseInt(string) + 1) + "";
					} else {
						string = "1";
					}
					listWeek.add(string);
				}
				restMsg.setRestDays(listWeek);
			}
			if ((tradeRestDate.getYearRest() != null) && (!"".equals(Boolean.valueOf(tradeRestDate.getYearRest().equals(""))))) {
				List<Integer> listYear = new ArrayList();
				String[] year = tradeRestDate.getYearRest().split(",");
				for (String string2 : year) {
					listYear.add(Integer.valueOf(Integer.parseInt(string2)));
				}
				restMsg.setRestWeeks(listYear);
			}
			List<gnnt.MEBS.zcjs.model.TradeTime> tradeTimeList = new ArrayList();
			List<gnnt.MEBS.zcjs.memory.system.TradeTime> tradeTimeList2 = new ArrayList();
			PageInfo pageInfo = new PageInfo(1, 10000, "starttime", false);
			tradeTimeList = this.tradeTimeService.getObjectList(null, pageInfo);
			Date currentDate = new Date();
			boolean flag = true;
			for (gnnt.MEBS.zcjs.model.TradeTime tradeTime : tradeTimeList) {
				this.logger.debug("加载交易节：" + tradeTime.getSerialNumber());
				String startTime = tradeTime.getStartTime();
				String entTime = tradeTime.getEndTime();
				int serialNumber = tradeTime.getSerialNumber();
				Date dateNow = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateNowStr = dateFormat.format(dateNow);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String str1 = dateNowStr + " " + startTime;
				String str2 = dateNowStr + " " + entTime;
				try {
					Date startDate = simpleDateFormat.parse(str1);
					Date endDate = simpleDateFormat.parse(str2);
					gnnt.MEBS.zcjs.memory.system.TradeTime tradeTime2 = new gnnt.MEBS.zcjs.memory.system.TradeTime();
					tradeTime2.setStartTime(startDate);
					tradeTime2.setEndTime(endDate);
					tradeTime2.setSerialNumber(serialNumber);
					tradeTimeList2.add(tradeTime2);
					this.logger.debug("currentDate:" + simpleDateFormat.format(currentDate));
					this.logger.debug("startDate:" + simpleDateFormat.format(startDate));
					this.logger.debug("endDate:" + simpleDateFormat.format(endDate));
					this.logger.debug(currentDate.toLocaleString());
					this.logger.debug(endDate.toLocaleString());
					this.logger.debug(Boolean.valueOf(currentDate.compareTo(startDate) < 0));
					this.logger.debug(Boolean.valueOf(currentDate.compareTo(startDate) >= 0));
					this.logger.debug(Boolean.valueOf(currentDate.compareTo(endDate) <= 0));
					if (((currentDate.compareTo(startDate) < 0) || ((currentDate.compareTo(startDate) >= 0) && (currentDate.compareTo(endDate) <= 0)))
							&& (flag)) {
						systemObject.setCurrentTradeTime(tradeTime2);
						this.logger.debug("当前交易节：" + tradeTime2.getSerialNumber());
						flag = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			systemObject.setTradeTimeList(tradeTimeList2);
			systemObject.setRestMsg(restMsg);
			systemObject.setCurrentState(currentState);
			systemObject.setRealNextState(nextState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
