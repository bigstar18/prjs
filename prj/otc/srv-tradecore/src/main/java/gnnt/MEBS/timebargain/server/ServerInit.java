package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.FirmDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.CommdityPriceProtect;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.riskcontrol.FloatingComputer;
import gnnt.MEBS.timebargain.server.riskcontrol.RiskcontrolEngine;
import gnnt.MEBS.timebargain.server.trade.TradeEngine;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.quartz.CronTriggerBean;

public class ServerInit
{
  private Log log = LogFactory.getLog(getClass());
  private Server server;
  private static Map<String, Firm> firmQueue = new ConcurrentHashMap();
  private static Map<String, Member> memberQueue = new HashMap();
  private static Map<String, Trader> traderQueue = new ConcurrentHashMap();
  private static Map<Integer, Map<String, Commodity>> commodityQueue = new HashMap();
  private static Map<String, Consigner> consignerQueue = new ConcurrentHashMap();
  private static Map<String, Commodity> commodityMap = new HashMap();
  private static Map<String, CommdityPriceProtect> commdityPriceProtectMap = new HashMap();
  private static List<TradeTime> tradeTimeList = new ArrayList();
  private Market market = new Market();
  private long diffTime;
  private ServerDAO serverDAO;
  private FirmDAO firmDAO;
  private TradeDAO tradeDAO;
  private static ServerInit instance;
  private boolean crossDay;
  private TradeDate tradeDateInterface;
  private Date tradeDate;
  private Date clearDate;
  public static long maxDelayTime = 0L;
  
  public static ServerInit getInstance()
  {
    if (instance == null) {
      instance = new ServerInit();
    }
    return instance;
  }
  
  public void init(Server server)
  {
    this.server = server;
    this.serverDAO = Server.getServerDAO();
    this.tradeDAO = Server.getTradeDAO();
    this.firmDAO = Server.getFirmDAO();
    
    this.diffTime = 
      (this.serverDAO.getCurDbDate().getTime() - System.currentTimeMillis());
    this.log.info("DB服务器和交易服务器时间的差额=" + this.diffTime + "毫秒");
    this.crossDay = judgeCrossDay();
    if (this.crossDay) {
      this.tradeDateInterface = 
        TradeDateFactory.createTradeDate((short)1);
    } else {
      this.tradeDateInterface = 
        TradeDateFactory.createTradeDate((short)0);
    }
    this.tradeDate = this.tradeDateInterface.calTradeDate();
    this.clearDate = this.tradeDateInterface.calClearDateByTradeDate(this.tradeDate);
    
    this.log.info("交易日期：" + DateUtil.formatDate(this.tradeDate, "yyyy-MM-dd"));
    this.log.info("结算日期：" + DateUtil.formatDate(this.clearDate, "yyyy-MM-dd"));
    
    loadMarket();
  }
  
  public void setSystemFailurePrompt(String prompt)
  {
    Server.getSystemStatus().setNote(prompt);
    this.serverDAO.updateSystemStatus(Server.getSystemStatus());
  }
  
  public boolean startCheck()
  {
    this.log.info("正在检查交易节信息是否已设置. . . ");
    System.out
      .println(DateUtil.getCurDateTime() + "  正在检查交易节信息是否已设置. . . ");
    if (!isLoadTradeSec())
    {
      this.log.info("还未设置交易节信息，请先设置后开启");
      System.out.println(DateUtil.getCurDateTime() + "  未设置交易节信息");
      setSystemFailurePrompt("未加载交易节");
      return false;
    }
    this.log.info("检查交易节信息是否已设置完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  检查交易节信息是否已设置完毕！");
    
    this.log.info("正在检查商品信息是否已设置. . . ");
    System.out.println(DateUtil.getCurDateTime() + "  正在检查商品信息是否已设置. . . ");
    if (!isLoadComty())
    {
      this.log.info("还未设置商品信息信息，请先设置后开启");
      System.out.println(DateUtil.getCurDateTime() + "  未设置商品信息信息");
      setSystemFailurePrompt("未加载商品");
      return false;
    }
    this.log.info("检查商品信息是否已设置完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  检查商品信息是否已设置完毕！");
    
    this.log.info("正在检查上一交易日是否已做资金结算．．．");
    System.out
      .println(DateUtil.getCurDateTime() + "  正在检查上一交易日是否已做资金结算．．．");
    if (!isDayCalc())
    {
      this.log.info("上一交易日资金结算未完成，请先结算后再开启！");
      System.out.println(DateUtil.getCurDateTime() + 
        "  上一交易日资金结算未完成，请先结算后再开启！");
      setSystemFailurePrompt("资金结算未完成");
      return false;
    }
    this.log.info("检查上一交易日是否已做资金结算完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  检查上一交易日是否已做资金结算完毕！");
    

    this.log.info("正在初始化交易数据．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在初始化交易数据．．．");
    if (this.tradeDAO.initTrade(this.tradeDate) != 1)
    {
      this.log.info("初始化交易数据失败，不能开启！");
      System.out.println(DateUtil.getCurDateTime() + "  初始化交易数据失败，不能开启！");
      setSystemFailurePrompt("初始化交易数据失败");
      return false;
    }
    this.log.info("初始化交易数据完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  初始化交易数据完毕！");
    
    this.log.info("正在做开市准备时重算商品保证金．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在做开市准备时重算商品保证金．．．");
    if (this.tradeDAO.reComputeFunds(1) < 0)
    {
      this.log.info("开市准备时重算资金失败，不能开启！");
      System.out.println(DateUtil.getCurDateTime() + 
        "  开市准备时重算资金失败，不能开启！");
      setSystemFailurePrompt("开市准备时重算资金失败");
      return false;
    }
    this.log.info("开市准备时重算商品保证金完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  开市准备时重算商品保证金完毕！");
    
    return true;
  }
  
  public void reScheduleOpenServerJob()
    throws SchedulerException, ParseException
  {
    CronTriggerBean autoOpenServerTrigger = (CronTriggerBean)this.server
      .getScheduler().getTrigger("cronTrigger", 
      "DEFAULT");
    
    String cronExpression = getAutoOpenServerExpression(tradeTimeList);
    
    String originConExpression = autoOpenServerTrigger.getCronExpression();
    if (!originConExpression.equalsIgnoreCase(cronExpression))
    {
      this.log.info("自动开启交易服务器表达式 " + cronExpression);
      autoOpenServerTrigger.setCronExpression(cronExpression);
      this.server.getScheduler().rescheduleJob("cronTrigger", 
        "DEFAULT", autoOpenServerTrigger);
      
      Server.getFirmDAO().addGlobalLog("System", "", 
        1202, 
        "自动开启交易服务器表达式 " + cronExpression, 1);
    }
    else
    {
      this.log.info("自动开启交易服务器表达式 " + originConExpression);
    }
  }
  
  public void reScheduleBalanceJob()
    throws SchedulerException, ParseException
  {
    CronTriggerBean autoBalanceTrigger = (CronTriggerBean)this.server
      .getScheduler().getTrigger("cronTriggerAutoBalance", 
      "DEFAULT");
    
    String cronExpression = getAutoBalanceExpression(tradeTimeList);
    String originConExpression = autoBalanceTrigger.getCronExpression();
    if (!originConExpression.equalsIgnoreCase(cronExpression))
    {
      this.log.info("自动结算时间表达式 " + cronExpression);
      autoBalanceTrigger.setCronExpression(cronExpression);
      this.server.getScheduler().rescheduleJob("cronTriggerAutoBalance", 
        "DEFAULT", autoBalanceTrigger);
      
      Server.getFirmDAO().addGlobalLog("System", "", 
        1202, 
        "自动结算时间表达式 " + cronExpression, 1);
    }
    else
    {
      this.log.info("自动结算时间表达式 " + originConExpression);
    }
  }
  
  private String getAutoOpenServerExpression(List<TradeTime> list)
  {
    TradeTime firstTradeTime = null;
    if ((list != null) && (list.size() > 0)) {
      firstTradeTime = (TradeTime)list.get(0);
    } else {
      throw new IllegalArgumentException(
        " getAutoOpenServerExpression Fail List<TradeTime> is Null");
    }
    Date startTradeTime = new Date(firstTradeTime.getStartTimeMillis());
    
    Date autoStartServerTime = DateUtil.GoSecond(startTradeTime, 0 - this.market
      .getInitPreSecs());
    

    Date dbDate = this.serverDAO.getCurDbDate();
    if (dbDate.getTime() < autoStartServerTime.getTime()) {
      return getCronExpByDate(autoStartServerTime);
    }
    Date nextTradeDate = this.tradeDateInterface.calNextTradeDate(new Date(
      firstTradeTime.getStartTimeMillis()));
    
    List<TradeTime> allTradeTimes = this.tradeDateInterface
      .getAllTradeTimesByTD(nextTradeDate);
    
    List<TradeTime> validTradeList = this.tradeDateInterface
      .getTradeTimes(allTradeTimes);
    

    return getAutoOpenServerExpression(validTradeList);
  }
  
  private String getAutoBalanceExpression(List<TradeTime> list)
  {
    TradeTime firstTradeTime = null;
    if ((list != null) && (list.size() > 0)) {
      firstTradeTime = (TradeTime)list.get(0);
    }
    TradeTime lastTradeTime = null;
    if ((list != null) && (list.size() > 0)) {
      lastTradeTime = (TradeTime)list.get(list.size() - 1);
    } else {
      throw new IllegalArgumentException(
        "getAutoBalanceExpression Fail list is Null");
    }
    Date endTradeTime = new Date(lastTradeTime.getEndTimeMillis());
    
    Date autoBalanceTime = DateUtil.GoSecond(endTradeTime, this.market
      .getClearDelaySecs());
    

    Date dbDate = this.serverDAO.getCurDbDate();
    if (dbDate.getTime() < autoBalanceTime.getTime()) {
      return getCronExpByDate(autoBalanceTime);
    }
    Date nextTradeDate = this.tradeDateInterface.calNextTradeDate(new Date(
      firstTradeTime.getStartTimeMillis()));
    
    List<TradeTime> allTradeTimes = this.tradeDateInterface
      .getAllTradeTimesByTD(nextTradeDate);
    
    List<TradeTime> validTradeList = this.tradeDateInterface
      .getTradeTimes(allTradeTimes);
    

    return getAutoBalanceExpression(validTradeList);
  }
  
  private String getCronExpByDate(Date date)
  {
    this.log.info("CronExpDate=" + 
      DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
    this.log.info("diffTime=" + this.diffTime);
    Calendar cal = Calendar.getInstance();
    
    cal.setTime(new Date(date.getTime() - this.diffTime));
    







    return cal.get(13) + " " + cal.get(12) + " " + 
      cal.get(11) + " " + cal.get(5) + 
      " " + (cal.get(2) + 1) + " " + " ? " + "*" + " ";
  }
  
  public void loadInitData()
    throws SchedulerException, ParseException
  {
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
    loadCommodityMap();
    this.log.info("装载商品队列完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  装载商品队列完毕！");
    

    System.out.println(DateUtil.getCurDateTime() + "  正在装载商品行情保护队列．．．");
    loadCommdityPriceProtectMap();
    this.log.info("装载商品行情保护队列完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  装载商品行情保护队列完毕！");
    

    this.log.info("正在装载会员队列．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在装载会员队列．．．");
    loadMemberQueue();
    this.log.info("装载会员队列完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  装载会员队列完毕！");
    

    this.log.info("设置自动开启交易服务器时间．．．");
    System.out.println(DateUtil.getCurDateTime() + "  设置自动开启交易服务器时间．．．");
    reScheduleOpenServerJob();
    this.log.info("设置自动开启交易服务器时间完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  设置自动开启交易服务器时间完毕！");
    

    this.log.info("设置自动结算时间．．．");
    System.out.println(DateUtil.getCurDateTime() + "  设置自动结算时间．．．");
    reScheduleBalanceJob();
    this.log.info("设置自动结算时间完毕！");
    System.out.println(DateUtil.getCurDateTime() + "  设置自动结算时间完毕！");
    


    loadMaxDelayTime();
  }
  
  public void loadMaxDelayTime()
  {
    maxDelayTime = this.serverDAO.getDelayTradeTime();
    this.log.warn("最长缓存价格时间为：" + maxDelayTime);
  }
  
  public void loadMemberQueue()
  {
    memberQueue.clear();
    memberQueue.putAll(this.firmDAO.getMemberMap());
    
    Iterator itr = memberQueue.keySet().iterator();
    while (itr.hasNext())
    {
      String m_FirmID = (String)itr.next();
      Member member = (Member)memberQueue.get(m_FirmID);
      member.setQuotePointMap(this.firmDAO.getQuotePointMap(m_FirmID));
      member.setQuotationMap(this.firmDAO.getQuotationMap(m_FirmID));
      this.log.info("会员信息--->>" + member);
    }
  }
  
  public void loadOneMember2Queue(String firmID)
  {
    if (memberQueue.containsKey(firmID)) {
      return;
    }
    Member member = null;
    try
    {
      member = this.firmDAO.getOneMember(firmID);
    }
    catch (Exception e)
    {
      this.log.warn("加载一个会员到内存队列失败 会员ID 为 " + firmID + " 的会员在行情运行时表中不存在!");
    }
    if (member == null) {
      throw new IllegalArgumentException("加载一个会员到内存队列失败 会员ID 为 " + firmID + 
        " 的会员在行情运行时表中不存在!");
    }
    member.setQuotePointMap(this.firmDAO.getQuotePointMap(firmID));
    member.setQuotationMap(this.firmDAO.getQuotationMap(firmID));
    memberQueue.put(member.getM_FirmID(), member);
    

    Server.getInstance().getRiskcontrolEngine().getFloatingComputer()
      .addOneFloatingComputerThread(member);
    
    Server.getInstance().getTradeEngine().addOneMemberOrder(member);
  }
  
  public void loadCommodityQueue()
  {
    commodityQueue.clear();
    for (int i = 0; i < tradeTimeList.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)tradeTimeList.get(i);
      Map cmdtyMap = convertCommodityList2Map(this.serverDAO
        .getCommodityList(tradeTime.getSectionID()));
      commodityQueue.put(tradeTime.getSectionID(), cmdtyMap);
    }
    this.log.info("装载商品队列 内存构造是以交易节为健值，值是以商品代码为健值--->>" + commodityQueue);
  }
  
  public void loadCommodityMap()
  {
    commodityMap.clear();
    commodityMap.putAll(convertCommodityList2Map(this.serverDAO
      .getCommodityList()));
    this.log.info("装载所有商品对象List---->>" + commodityMap);
  }
  
  public void setCommodityStatus(String commodityID, char status)
  {
    if ((status != 'N') && 
      (status != 'P')) {
      throw new IllegalArgumentException("商品状态即不属于正常交易 也不属于暂停交易");
    }
    Commodity commodity = (Commodity)getCommodityMap().get(commodityID);
    if (commodity == null) {
      throw new IllegalArgumentException("输入的商品 " + commodityID + 
        " 在内存中不存在！");
    }
    if (status == commodity.getTradeStatus()) {
      throw new IllegalArgumentException("当前商品状态" + 
        commodity.getTradeStatus() + "和您要设置的状态" + status + 
        "一致,无需设置！");
    }
    if ((status == 'N') && 
      (commodity.getTradeStatus() == '\004') && 
      (commodity.getPauseType() == 'S')) {
      throw new IllegalArgumentException(
        "系统在设定时间内没有收到行情数据暂停交易，不允许手工恢复交易！");
    }
    if (commodity != null)
    {
      commodity.setTradeStatus(status);
      commodity.setPauseType('M');
    }
    Map<Integer, Map<String, Commodity>> commodityQueue = 
      getCommodityQueue();
    for (Map<String, Commodity> map : commodityQueue.values())
    {
      Commodity sectionCommodity = (Commodity)map.get(commodityID);
      if (sectionCommodity != null)
      {
        sectionCommodity.setTradeStatus(status);
        sectionCommodity.setPauseType('M');
      }
    }
    Server.getServerDAO().updateCommodityStatusByS(commodityID, status);
  }
  
  public void loadCommdityPriceProtectMap()
  {
    commdityPriceProtectMap.clear();
    commdityPriceProtectMap = Server.getServerDAO()
      .getCommdityPriceProtect();
    
    this.log.info("装载行情保护商品对象 只包含上市的且交易状态是正常交易的商品---->>");
    
    Iterator localIterator = commdityPriceProtectMap.values().iterator();
    while (localIterator.hasNext())
    {
      CommdityPriceProtect commdityPriceProtect = (CommdityPriceProtect)localIterator.next();
      this.log.info(commdityPriceProtect.toString());
    }
  }
  
  public void loadMarket()
  {
    Market m = this.serverDAO.getMarket();
    BeanUtils.copyProperties(m, this.market);
    this.log.info("市场参数--->>" + this.market);
  }
  
  public void loadTradeTimeList()
  {
    tradeTimeList.clear();
    List<TradeTime> allTradeTimes = this.tradeDateInterface
      .getAllTradeTimesByTD(this.tradeDate);
    for (TradeTime tradeTime : allTradeTimes) {
      this.serverDAO.updateTradeSectionDateStatus(tradeTime);
    }
    tradeTimeList.addAll(this.tradeDateInterface.getTradeTimes(allTradeTimes));
    
    this.log.info("加载交易节列表---->>" + tradeTimeList);
  }
  
  public TradeTime getTradeTimeBySectionID(Integer sectionID)
  {
    return getTradeTimeBySectionID(sectionID, 1);
  }
  
  public TradeTime getTradeTimeBySectionID(Integer sectionID, int curFlag)
  {
    TradeTime preTradeTime = null;
    TradeTime tt = null;
    for (int i = 0; i < tradeTimeList.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)tradeTimeList.get(i);
      if (tradeTime.getSectionID().intValue() == sectionID.intValue())
      {
        tt = tradeTime;
        if (i != 0) {
          break;
        }
        preTradeTime = tradeTime;
        
        break;
      }
      preTradeTime = tradeTime;
    }
    if (curFlag == 0) {
      return preTradeTime;
    }
    return tt;
  }
  
  public long getDiffTime()
  {
    return this.diffTime;
  }
  
  public static List<TradeTime> getTradeTimeList()
  {
    return tradeTimeList;
  }
  
  public static Map<String, Member> getMemberQueue()
  {
    return memberQueue;
  }
  
  public static Map<Integer, Map<String, Commodity>> getCommodityQueue()
  {
    return commodityQueue;
  }
  
  public static Map<String, Firm> getFirmQueue()
  {
    return firmQueue;
  }
  
  public Market getMarket()
  {
    return this.market;
  }
  
  public static Map<String, Trader> getTraderQueue()
  {
    return traderQueue;
  }
  
  public static Map<String, Consigner> getConsignerQueue()
  {
    return consignerQueue;
  }
  
  public static Map<String, Commodity> getCommodityMap()
  {
    return commodityMap;
  }
  
  public static Map<String, CommdityPriceProtect> getCommdityPriceProtectMap()
  {
    return commdityPriceProtectMap;
  }
  
  public boolean isCrossDay()
  {
    return this.crossDay;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public Date getNextTradeDate()
  {
    return this.tradeDateInterface.calNextTradeDate(this.tradeDate);
  }
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public TradeDate getTradeDateInterface()
  {
    return this.tradeDateInterface;
  }
  
  public void setTradeDateInterface(TradeDate tradeDateInterface)
  {
    this.tradeDateInterface = tradeDateInterface;
  }
  
  private Map<String, Commodity> convertCommodityList2Map(List<Commodity> commodityList)
  {
    Map<String, Commodity> cmdtyMap = new HashMap();
    for (Commodity commodity : commodityList) {
      cmdtyMap.put(commodity.getCommodityID(), commodity);
    }
    return cmdtyMap;
  }
  
  public void loadOneFirm(String firmID)
  {
    Firm firm = this.firmDAO.getOneFirm(firmID);
    firm.setMarginMap(this.firmDAO.getMarginMap(firmID));
    firm.setFeeMap(this.firmDAO.getFeeMap(firmID));
    firm.setHoldQtyMap(this.firmDAO.getHoldQtyMap(firmID));
    firmQueue.put(firmID, firm);
  }
  
  public boolean isDayCalc()
  {
    if (Server.getSystemStatus().getStatus() == 3) {
      return true;
    }
    return false;
  }
  
  private boolean isLoadTradeSec()
  {
    int count = this.serverDAO.getTradeSecCount();
    if (count > 0) {
      return true;
    }
    return false;
  }
  
  private boolean isLoadComty()
  {
    int count = this.serverDAO.getComtyCount();
    if (count > 0) {
      return true;
    }
    return false;
  }
  
  public boolean judgeCrossDay()
  {
    String endTime = null;
    List lst = this.serverDAO.getTradeTimes();
    for (int i = 0; i < lst.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)lst.get(i);
      if ((endTime != null) && 
        (tradeTime.getStartTime().compareTo(endTime) < 0)) {
        return true;
      }
      if (tradeTime.getEndTime().compareTo(tradeTime.getStartTime()) < 0) {
        return true;
      }
      endTime = tradeTime.getEndTime();
    }
    return false;
  }
}
