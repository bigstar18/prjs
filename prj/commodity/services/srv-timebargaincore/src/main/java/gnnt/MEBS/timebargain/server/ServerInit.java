package gnnt.MEBS.timebargain.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.NotTradeDay;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.util.DateUtil;

public class ServerInit {
	private Log log = LogFactory.getLog(getClass());
	private Server server;
	private Map firmQueue = new ConcurrentHashMap();
	private Map traderQueue = new ConcurrentHashMap();
	private Map customerQueue = new ConcurrentHashMap();
	private Map commodityQueue = new HashMap();
	private Map consignerQueue = new ConcurrentHashMap();
	protected Map tariffQueue = new HashMap();
	private List commodityList = new ArrayList();
	private List tradeTimeList = new ArrayList();
	private Market market = new Market();
	public static long diffTime;
	private ServerDAO serverDAO;
	private TradeDAO tradeDAO;
	private static ServerInit instance;
	private boolean crossDay;
	private TradeDate tradeDateInterface;
	private Date clearDate;
	private AdjustMargin adjustMargin;

	public static ServerInit getInstance() {
		if (instance == null) {
			instance = new ServerInit();
		}
		return instance;
	}

	public void init(Server paramServer) {
		this.server = paramServer;
		this.serverDAO = paramServer.getServerDAO();
		this.tradeDAO = paramServer.getTradeDAO();
		diffTime = this.serverDAO.getCurDbDate().getTime() - System.currentTimeMillis();
		this.log.info("DB服务器和交易服务器时间的差额=" + diffTime + "毫秒");
		if (ServerShell.RunModeIsSIM) {
			long l = Server.simHqDAO.getCurDbDate().getTime() - this.serverDAO.getCurDbDate().getTime();
			this.log.info("正式盘面DB服务器与实盘模拟DB服务器时间的差额=" + l + "毫秒, 请尽可能保持两服务器的时间相同！");
		}
		this.crossDay = judgeCrossDay();
		if (this.crossDay) {
			this.tradeDateInterface = TradeDateFactory.createTradeDate((short) 1);
		} else {
			this.tradeDateInterface = TradeDateFactory.createTradeDate((short) 0);
		}
		this.clearDate = this.tradeDateInterface.calClearDate();
		this.log.info("结算日期：" + this.clearDate);
		loadMarket();
		this.adjustMargin = AdjustMargin.getInstance();
		this.adjustMargin.init(this);
	}

	public void setSystemFailurePrompt(String paramString) {
		this.server.getSystemStatus().setNote(paramString);
		this.serverDAO.updateSystemStatus(this.server.getSystemStatus());
	}

	public boolean startCheck() {
		this.log.info("正在检查交易节信息是否已设置. . . ");
		System.out.println(DateUtil.getCurDateTime() + "  正在检查交易节信息是否已设置. . . ");
		if (!isLoadTradeSec()) {
			this.log.info("还未设置交易节信息，请先设置后开启");
			System.out.println(DateUtil.getCurDateTime() + "  未设置交易节信息");
			setSystemFailurePrompt("未加载交易节");
			return false;
		}
		this.log.info("检查交易节信息是否已设置完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  检查交易节信息是否已设置完毕！");
		this.log.info("正在检查商品信息是否已设置. . . ");
		System.out.println(DateUtil.getCurDateTime() + "  正在检查商品信息是否已设置. . . ");
		if (!isLoadComty()) {
			this.log.info("还未设置商品信息信息，请先设置后开启");
			System.out.println(DateUtil.getCurDateTime() + "  未设置商品信息信息");
			setSystemFailurePrompt("未加载商品");
			return false;
		}
		this.log.info("检查商品信息是否已设置完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  检查商品信息是否已设置完毕！");
		this.log.info("正在检查上一交易日是否已做资金结算．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在检查上一交易日是否已做资金结算．．．");
		if (!isPreDayCalc()) {
			this.log.info("上一交易日资金结算未完成，请先结算后再开启！");
			System.out.println(DateUtil.getCurDateTime() + "  上一交易日资金结算未完成，请先结算后再开启！");
			setSystemFailurePrompt("资金结算未完成");
			return false;
		}
		this.log.info("检查上一交易日是否已做资金结算完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  检查上一交易日是否已做资金结算完毕！");
		if (!this.crossDay) {
			this.log.info("正在检查交易日期是否有效．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在检查交易日期是否有效．．．");
			if (!checkTradeDay(this.clearDate)) {
				this.log.info("非交易日，不能开启！");
				System.out.println(DateUtil.getCurDateTime() + "  非交易日，不能开启！");
				setSystemFailurePrompt("非交易日");
				return false;
			}
			this.log.info("检查交易日期是否有效完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  检查交易日期是否有效完毕！");
		}
		this.log.info("正在初始化交易数据．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在初始化交易数据．．．");
		if (this.tradeDAO.initTrade(this.clearDate) != 1) {
			this.log.info("初始化交易数据失败，不能开启！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化交易数据失败，不能开启！");
			setSystemFailurePrompt("初始化交易数据失败");
			return false;
		}
		this.log.info("初始化交易数据完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  初始化交易数据完毕！");
		this.log.info("正在做开市准备时调整商品保证金．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在做开市准备时调整商品保证金．．．");
		if ((!this.adjustMargin.adjustMargin(1)) || (this.tradeDAO.reComputeFunds(1) < 0)) {
			this.log.info("开市准备时重算资金失败，不能开启！");
			System.out.println(DateUtil.getCurDateTime() + "  开市准备时重算资金失败，不能开启！");
			setSystemFailurePrompt("开市准备时重算资金失败");
			return false;
		}
		this.log.info("开市准备时调整商品保证金完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  开市准备时调整商品保证金完毕！");
		return true;
	}

	public void loadInitData() {
		this.log.info("正在装载后台市场参数队列．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在装载后台市场参数队列．．．");
		loadMarket();
		this.log.info("装载后台市场参数队列完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  装载后台市场参数队列完毕！");
		this.log.info("正在装载交易节．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在装载交易节．．．");
		loadTradeTimeList();
		this.log.info("装载交易节完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  装载交易节完毕！");
		this.log.info("正在装载商品队列．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在装载商品队列．．．");
		loadCommodityQueue();
		loadCommodityList();
		this.log.info("装载商品队列完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  装载商品队列完毕！");
		this.log.info("正在装载手续费套餐．．．");
		System.out.println(DateUtil.getCurDateTime() + "  正在装载手续费套餐．．．");
		loadTariffQueue();
		this.log.info("装载手续费套餐完毕！");
		System.out.println(DateUtil.getCurDateTime() + "  装载手续费套餐完毕！");
	}

	public void loadFirmQueue() {
		this.firmQueue.clear();
		this.firmQueue.putAll(this.serverDAO.getFirmMap());
		Iterator localIterator = this.firmQueue.keySet().iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			Firm localFirm = (Firm) this.firmQueue.get(str);
			localFirm.setFirmMarginMap(this.serverDAO.getFirmMarginMap(str));
			localFirm.setFirmFeeMap(this.serverDAO.getFirmFeeMap(str));
		}
	}

	public void loadTraderQueue() {
		this.traderQueue.clear();
		this.traderQueue.putAll(this.serverDAO.getTraderMap());
	}

	public void loadConsignerQueue() {
		this.consignerQueue.clear();
		this.consignerQueue.putAll(this.serverDAO.getConsignerMap());
	}

	public void loadTariffQueue() {
		this.tariffQueue.clear();
		this.tariffQueue.putAll(this.serverDAO.getTariffMap());
	}

	public void loadCustomerQueue() {
		this.customerQueue.clear();
		this.customerQueue.putAll(this.serverDAO.getCustomerMap());
	}

	public void loadCommodityQueue() {
		this.commodityQueue.clear();
		for (int i = 0; i < this.tradeTimeList.size(); i++) {
			TradeTime localTradeTime = (TradeTime) this.tradeTimeList.get(i);
			Map localMap = convertCommodityList2Map(this.serverDAO.getCommodityList(localTradeTime.getSectionID()));
			this.commodityQueue.put(localTradeTime.getSectionID(), localMap);
		}
	}

	public void loadCommodityList() {
		this.commodityList.clear();
		this.commodityList.addAll(this.serverDAO.getCommodityList());
	}

	public void loadMarket() {
		Market localMarket = this.serverDAO.getMarket();
		BeanUtils.copyProperties(localMarket, this.market);
	}

	public void loadTradeTimeList() {
		this.tradeTimeList.clear();
		this.tradeTimeList.addAll(this.tradeDateInterface.getTradeTimes(this.clearDate));
	}

	public TradeTime getTradeTimeBySectionID(Integer paramInteger) {
		return getTradeTimeBySectionID(paramInteger, 1);
	}

	public TradeTime getTradeTimeBySectionID(Integer paramInteger, int paramInt) {
		TradeTime localObject1 = null;
		TradeTime localObject2 = null;
		for (int i = 0; i < this.tradeTimeList.size(); i++) {
			TradeTime localTradeTime = (TradeTime) this.tradeTimeList.get(i);
			if (localTradeTime.getSectionID().intValue() == paramInteger.intValue()) {
				localObject2 = localTradeTime;
				if (i != 0) {
					break;
				}
				localObject1 = localTradeTime;
				break;
			}
			localObject1 = localTradeTime;
		}
		if (paramInt == 0) {
			return localObject1;
		}
		return localObject2;
	}

	public long getDiffTime() {
		return diffTime;
	}

	public List getTradeTimeList() {
		return this.tradeTimeList;
	}

	public Map getCommodityQueue() {
		return this.commodityQueue;
	}

	public Map getCustomerQueue() {
		return this.customerQueue;
	}

	public Map getFirmQueue() {
		return this.firmQueue;
	}

	public Market getMarket() {
		return this.market;
	}

	public Map getTraderQueue() {
		return this.traderQueue;
	}

	public Map getConsignerQueue() {
		return this.consignerQueue;
	}

	public List getCommodityList() {
		return this.commodityList;
	}

	public void setCustomerQueue(Map paramMap) {
		this.customerQueue = paramMap;
	}

	public void setFirmQueue(Map paramMap) {
		this.firmQueue = paramMap;
	}

	public void setTraderQueue(Map paramMap) {
		this.traderQueue = paramMap;
	}

	public boolean isCrossDay() {
		return this.crossDay;
	}

	public Date getClearDate() {
		return this.clearDate;
	}

	public TradeDate getTradeDateInterface() {
		return this.tradeDateInterface;
	}

	public AdjustMargin getAdjustMargin() {
		return this.adjustMargin;
	}

	public Commodity getCommodityInListByID(String paramString) {
		for (int i = 0; i < this.commodityList.size(); i++) {
			Commodity localCommodity = (Commodity) this.commodityList.get(i);
			if (localCommodity.getCommodityID().equals(paramString)) {
				return localCommodity;
			}
		}
		return null;
	}

	private Map convertCommodityList2Map(List paramList) {
		HashMap localHashMap = new HashMap();
		for (int i = 0; i < paramList.size(); i++) {
			Commodity localCommodity = (Commodity) paramList.get(i);
			localHashMap.put(localCommodity.getCommodityID(), localCommodity);
		}
		return localHashMap;
	}

	public boolean checkTradeDay(Date paramDate) {
		NotTradeDay localNotTradeDay = this.serverDAO.getNotTradeDay();
		if (localNotTradeDay != null) {
			List localList1 = localNotTradeDay.getWeekList();
			Calendar localCalendar = Calendar.getInstance();
			localCalendar.setTime(paramDate);
			int i = localCalendar.get(7);
			if (localList1.contains(String.valueOf(i))) {
				return false;
			}
			List localList2 = localNotTradeDay.getDayList();
			if (localList2.contains(DateUtil.formatDate(paramDate, "yyyy-MM-dd"))) {
				return false;
			}
		}
		return true;
	}

	public void loadOneFirm(String paramString) {
		this.log.debug("加载交易商：[" + paramString + "]...");
		Firm localFirm = this.serverDAO.getOneFirm(paramString);
		localFirm.setFirmMarginMap(this.serverDAO.getFirmMarginMap(paramString));
		localFirm.setFirmFeeMap(this.serverDAO.getFirmFeeMap(paramString));
		this.firmQueue.put(paramString, localFirm);
		this.log.debug("交易商：[" + paramString + "] 加载完毕...");
	}

	public void loadCustomer(String paramString) {
		this.log.debug("加载[" + paramString + "]下交易客户...");
		Map localMap = this.serverDAO.getCusByFirm(paramString);
		if (localMap != null) {
			this.customerQueue.putAll(localMap);
			this.log.debug("交易客户加载完毕...");
		} else {
			this.log.debug("没有可加载的客户");
		}
	}

	private boolean isPreDayCalc() {
		boolean bool = true;
		Date localDate = this.server.getSystemStatus().getTradeDate();
		int i = this.server.getSystemStatus().getStatus();
		String str = DateUtil.formatDate(this.serverDAO.getCurDbDate(), "yyyy-MM-dd");
		if ((str.compareTo(DateUtil.formatDate(localDate, "yyyy-MM-dd")) > 0) && (i != 3)) {
			bool = false;
		}
		return bool;
	}

	private boolean isLoadTradeSec() {
		int i = this.serverDAO.getTradeSecCount();
		return i > 0;
	}

	private boolean isLoadComty() {
		int i = this.serverDAO.getComtyCount();
		return i > 0;
	}

	private boolean judgeCrossDay() {
		String str = null;
		List localList = this.serverDAO.getTradeTimes();
		for (int i = 0; i < localList.size(); i++) {
			TradeTime localTradeTime = (TradeTime) localList.get(i);
			if ((str != null) && (localTradeTime.getStartTime().compareTo(str) < 0)) {
				return true;
			}
			if (localTradeTime.getEndTime().compareTo(localTradeTime.getStartTime()) < 0) {
				return true;
			}
			str = localTradeTime.getEndTime();
		}
		return false;
	}
}
