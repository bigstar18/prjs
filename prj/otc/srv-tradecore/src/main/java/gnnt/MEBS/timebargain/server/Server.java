package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.FirmDAO;
import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.quotation.QuotationEngine;
import gnnt.MEBS.timebargain.server.riskcontrol.RiskcontrolEngine;
import gnnt.MEBS.timebargain.server.trade.TradeEngine;
import gnnt.MEBS.timebargain.server.util.ActiveUserManager;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;

public class Server
{
  private Log log = LogFactory.getLog(getClass());
  private ServerInit serverInit;
  private TradeTimeTask tradeTimeTask;
  private TradeEngine tradeEngine;
  private QuotationEngine quotationEngine;
  private RiskcontrolEngine riskcontrolEngine;
  private static SystemStatus systemStatus;
  private LogonManager logonManager;
  private static ServerDAO serverDAO;
  private static TradeDAO tradeDAO;
  private static FirmDAO firmDAO;
  private static RiskcontrolDAO riskcontrolDAO;
  private int sectionStopOrder;
  private Scheduler scheduler;
  private static Server instance;
  private ActiveUserManager auManagerConsigner = new ActiveUserManager();
  public static final int SECTIONSTOPORDER_ACCEPT = 1;
  public static final int SECTIONSTOPORDER_NOTACCEPT = 2;
  public static final int ROLETYPE_TRADE = 1;
  public static final int ROLETYPE_SIM = 2;
  public static final int ENDWITHDRAW_YES = 1;
  public static final int ENDWITHDRAW_NO = 2;
  public static boolean acceptHQFlag = false;
  
  public static Server getInstance()
  {
    if (instance == null) {
      instance = (Server)DAOBeanFactory.getBean("server");
    }
    return instance;
  }
  
  public void init()
  {
    systemStatus = new SystemStatus();
    serverDAO = (ServerDAO)DAOBeanFactory.getBean("serverDAO");
    tradeDAO = (TradeDAO)DAOBeanFactory.getBean("tradeDAO");
    firmDAO = (FirmDAO)DAOBeanFactory.getBean("firmDAO");
    riskcontrolDAO = (RiskcontrolDAO)
      DAOBeanFactory.getBean("riskcontrolDAO");
    this.scheduler = ((Scheduler)DAOBeanFactory.getBean("quartzScheduler"));
    this.serverInit = ServerInit.getInstance();
    this.tradeTimeTask = new TradeTimeTask();
    this.tradeTimeTask.start();
    
    this.tradeEngine = TradeEngine.getInstance();
    this.quotationEngine = QuotationEngine.getInstance();
    this.riskcontrolEngine = RiskcontrolEngine.getInstance();
    
    this.logonManager = LogonManager.createInstance("2", 
      (DataSource)DAOBeanFactory.getBean("auDataSource"), 
      5, true);
    



    firmDAO.updateTraderDownLine();
  }
  
  public void statusListener()
  {
    int status = systemStatus.getStatus();
    StringBuffer noteSB = new StringBuffer();
    if (systemStatus.getSectionID() != null)
    {
      this.log.info("市场处于第" + systemStatus.getSectionID() + "交易节！");
      noteSB.append("市场处于第【").append(systemStatus.getSectionID()).append(
        "】交易节！");
    }
    noteSB.append("市场状态【").append(Constants.SYSTEM_STATUS[status]).append(
      "】");
    try
    {
      serverDAO.insertSysLog(new SysLog(noteSB.toString()));
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
      this.log.error("statusListener方法写系统状态日志到db中失败，原因：" + e1);
    }
    try
    {
      serverDAO.updateSystemStatus(systemStatus);
    }
    catch (Exception e1)
    {
      try
      {
        serverDAO.updateSystemStatus(systemStatus);
      }
      catch (Exception e2)
      {
        e2.printStackTrace();
        this.log.error("statusListener方法更新状态到db中失败，原因：" + e2);
        this.log
          .error("*******************警告：更新市场状态到db中失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
        
        System.exit(1);
        return;
      }
    }
    switch (status)
    {
    case 0: 
      this.log.info("市场初始化完成！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(0);
      this.tradeEngine
        .setTraderOrderStatus(1);
      break;
    case 1: 
      this.log.info("市场闭市状态！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(2);
      this.tradeEngine
        .setTraderOrderStatus(1);
      break;
    case 2: 
      this.log.info("市场结算中！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(2);
      this.tradeEngine
        .setTraderOrderStatus(1);
      break;
    case 3: 
      this.log.info(" 资金结算完成！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(2);
      this.tradeEngine
        .setTraderOrderStatus(1);
      break;
    case 4: 
      this.log.info("市场暂停交易！");
      this.riskcontrolEngine.setStatus(0);
      this.tradeTimeTask.setRunStatus(1);
      this.tradeEngine
        .setTraderOrderStatus(1);
      
      getFirmDAO().addGlobalLog("System", "", 
        1202, "市场状态修改为暂停交易！", 1);
      break;
    case 5: 
      this.log.info("市场交易中！");
      this.riskcontrolEngine.setStatus(0);
      this.tradeTimeTask.setRunStatus(0);
      this.tradeEngine.setTraderOrderStatus(0);
      
      getFirmDAO().addGlobalLog("System", "", 
        1202, "市场状态修改为开始交易！", 1);
      break;
    case 6: 
      this.log.info("市场节间休息！");
      this.riskcontrolEngine.setStatus(0);
      this.tradeTimeTask.setRunStatus(0);
      if (this.sectionStopOrder == 2) {
        this.tradeEngine.setTraderOrderStatus(1);
      } else {
        this.tradeEngine.setTraderOrderStatus(0);
      }
      break;
    case 7: 
      this.log.info("市场交易结束！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(2);
      this.tradeEngine
        .setTraderOrderStatus(1);
      acceptHQFlag = false;
      if (Integer.parseInt(DAOBeanFactory.getConfig("EndWithdraw")) == 1) {
        this.tradeEngine.autoWithdraw();
      }
      tradeDAO.clearBalancePrice();
      

      getFirmDAO().addGlobalLog("System", "", 
        1202, "市场状态修改为交易结束！", 1);
      break;
    case 8: 
      this.log.info("市场集合竞价交易中！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(0);
      this.tradeEngine.setTraderOrderStatus(0);
      break;
    case 9: 
      this.log.info("市场集合竞价交易结束！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(0);
      this.tradeEngine
        .setTraderOrderStatus(1);
      break;
    case 10: 
      this.log.info("交易结算完成！");
      this.riskcontrolEngine.setStatus(1);
      this.tradeTimeTask.setRunStatus(2);
      this.tradeEngine
        .setTraderOrderStatus(1);
      break;
    default: 
      this.log.error("未知交易系统状态[" + status + "]");
    }
  }
  
  public void autoOpenServer()
  {
    if (this.serverInit.getMarket().getRunMode().shortValue() == 0) {
      return;
    }
    initServer();
  }
  
  public void autoBalance()
  {
    if (this.serverInit.getMarket().getClearRunMode().shortValue() == 0) {
      return;
    }
    if (systemStatus.getStatus() == 3)
    {
      this.log.info("已经结算完成,不能自动交易结算！");
      return;
    }
    if (systemStatus.getStatus() != 7)
    {
      this.log.info("交易未结束，不能自动交易结算！");
      return;
    }
    this.log.info("正在进行自动交易结算．．．");
    try
    {
      balance();
    }
    catch (Exception e)
    {
      this.log.error("自动交易结算失败，原因：" + e.getMessage());
    }
  }
  
  public int balance()
  {
    if (systemStatus.getStatus() != 7)
    {
      this.log.info("没有交易结束，不能结算！");
      return -1;
    }
    long startDate = System.currentTimeMillis();
    
    serverDAO.insertSysLog(new SysLog("开始结算！"));
    

    this.log.info("交易结算时自动撤单...");
    this.tradeEngine.autoWithdraw();
    this.log.info("交易结算时自动撤单完毕！");
    
    this.log.info("交易结算...");
    
    Date nextTradeDate = this.serverInit.getNextTradeDate();
    
    serverDAO.updateNextTradeDate(nextTradeDate);
    systemStatus.setNextTradeDate(nextTradeDate);
    
    int ret = getTradeDAO().clear_Do();
    if (ret == 1)
    {
      this.log.info("交易结算成功！");
      
      systemStatus.setStatus(3);
      statusListener();
      try
      {
        this.serverInit.reScheduleOpenServerJob();
        this.serverInit.reScheduleBalanceJob();
      }
      catch (Exception e)
      {
        e.printStackTrace();
        this.log.error("reScheduleOpenServerJob Error,ErrorMessage=" + 
          e.getMessage());
        return -2;
      }
    }
    else if (ret == -1)
    {
      this.log.info("没有交易结束，不能结算！");
    }
    serverDAO.insertSysLog(new SysLog("交易服务器结算成功,结算时间" + (
      System.currentTimeMillis() - startDate) + "毫秒;"));
    
    return ret;
  }
  
  /**
   * @deprecated
   */
  public void close()
    throws Exception
  {
    if (systemStatus.getStatus() != 7) {
      throw new Exception("交易未结束，不能闭市操作！");
    }
    this.tradeEngine.autoWithdraw();
    
    systemStatus.setStatus(1);
    statusListener();
  }
  
  public void stop()
  {
    if (this.tradeTimeTask != null)
    {
      this.tradeTimeTask.close();
      this.tradeTimeTask = null;
    }
    this.serverInit = null;
    systemStatus = null;
    serverDAO = null;
    tradeDAO = null;
    firmDAO = null;
    this.auManagerConsigner = null;
    this.logonManager = null;
    this.tradeEngine.stop();
    this.quotationEngine.stop();
    this.riskcontrolEngine.stop();
    instance = null;
  }
  
  public static TradeDAO getTradeDAO()
  {
    return tradeDAO;
  }
  
  public static FirmDAO getFirmDAO()
  {
    return firmDAO;
  }
  
  public Scheduler getScheduler()
  {
    return this.scheduler;
  }
  
  public ServerInit getServerInit()
  {
    return this.serverInit;
  }
  
  public static SystemStatus getSystemStatus()
  {
    return systemStatus;
  }
  
  public TradeTimeTask getTradeTimeTask()
  {
    return this.tradeTimeTask;
  }
  
  public TradeEngine getTradeEngine()
  {
    return this.tradeEngine;
  }
  
  public QuotationEngine getQuotationEngine()
  {
    return this.quotationEngine;
  }
  
  public RiskcontrolEngine getRiskcontrolEngine()
  {
    return this.riskcontrolEngine;
  }
  
  public static ServerDAO getServerDAO()
  {
    return serverDAO;
  }
  
  public static RiskcontrolDAO getRiskcontrolDAO()
  {
    return riskcontrolDAO;
  }
  
  public static void setRiskcontrolDAO(RiskcontrolDAO riskcontrolDAO)
  {
    riskcontrolDAO = riskcontrolDAO;
  }
  
  public LogonManager getLogonManager()
  {
    return this.logonManager;
  }
  
  public ActiveUserManager getAuManagerConsigner()
  {
    return this.auManagerConsigner;
  }
  
  private static final ReentrantLock initServerLock = new ReentrantLock();
  
  public boolean initServer()
  {
    boolean result = false;
    initServerLock.lock();
    try
    {
      if (systemStatus.getStatus() == 0) {
        result = true;
      }
      result = initServer(true);
    }
    finally
    {
      initServerLock.unlock();
    }
    return result;
  }
  
  public boolean initServer(boolean isChangeTradeDate)
  {
    try
    {
      long startDate = System.currentTimeMillis();
      
      getFirmDAO().addGlobalLog("System", "", 
        1202, "开始初始化交易服务器", 1);
      
      serverDAO.insertSysLog(new SysLog("开始初始化交易服务器！"));
      
      this.sectionStopOrder = Integer.parseInt(
        DAOBeanFactory.getConfig("SectionStopOrder"));
      
      systemStatus = serverDAO.getSystemStatus();
      this.log.info("当前系统状态：" + systemStatus.toString());
      
      this.log.info("正在初始化ServerInit对象．．．");
      System.out.println(DateUtil.getCurDateTime() + 
        "  正在初始化ServerInit对象．．．");
      this.serverInit.init(this);
      this.log.info("初始化ServerInit对象完毕！");
      System.out.println(DateUtil.getCurDateTime() + 
        "  初始化ServerInit对象完毕！");
      if (isChangeTradeDate)
      {
        this.log.info("需要切换交易日");
        

        String curDbDate = DateUtil.formatDate(
          serverDAO.getCurDbDate(), "yyyy-MM-dd");
        
        String strTradeDate = DateUtil.formatDate(systemStatus
          .getTradeDate(), "yyyy-MM-dd");
        
        String strClearDate = DateUtil.formatDate(systemStatus
          .getClearDate(), "yyyy-MM-dd");
        
        this.log.info("当前数据库日期=" + curDbDate);
        this.log.info("交易日期=" + strTradeDate);
        this.log.info("结算日期=" + strClearDate);
        this.log.info("系统状态" + 
          Constants.SYSTEM_STATUS[systemStatus.getStatus()] + 
          "[" + systemStatus.getStatus() + "]");
        if (DateUtil.formatDate(this.serverInit.getTradeDate(), "yyyy-MM-dd").compareTo(strTradeDate) > 0)
        {
          this.log.info("开始交易服务器启动检查");
          if (!this.serverInit.startCheck()) {
            return false;
          }
          this.log.info("开始交易服务器启动检查通过");
          
          this.log.info("正在切换交易日．．．");
          System.out.println(DateUtil.getCurDateTime() + 
            "  正在切换交易日．．．");
          changeTradeDate(this.serverInit.getTradeDate(), this.serverInit
            .getClearDate(), this.serverInit.getNextTradeDate());
          this.log.info("切换交易日完毕！");
          System.out
            .println(DateUtil.getCurDateTime() + "  切换交易日完毕！");
        }
        else
        {
          this.log.info("上日未结算完成.无法切换交易日");
        }
      }
      this.serverInit.loadInitData();
      
      this.log.info("正在初始化交易节任务．．．");
      System.out.println(DateUtil.getCurDateTime() + "  正在初始化交易节任务．．．");
      this.tradeTimeTask.init(this);
      this.log.info("初始化交易节任务完毕！");
      System.out.println(DateUtil.getCurDateTime() + "  初始化交易节任务完毕！");
      
      this.log.info("正在初始化行情引擎．．．");
      System.out.println(DateUtil.getCurDateTime() + "  正在初始化行情引擎．．．");
      this.quotationEngine.init();
      this.log.info("初始化行情引擎完毕！");
      System.out.println(DateUtil.getCurDateTime() + "  初始化行情引擎完毕！");
      
      this.log.info("正在初始化交易引擎．．．");
      System.out.println(DateUtil.getCurDateTime() + "  正在初始化交易引擎．．．");
      this.tradeEngine.init();
      this.log.info("初始化交易引擎完毕！");
      System.out.println(DateUtil.getCurDateTime() + "  初始化交易引擎完毕！");
      
      this.log.info("正在初始化风险控制引擎．．．");
      System.out.println(DateUtil.getCurDateTime() + "  正在初始化风险控制引擎．．．");
      this.riskcontrolEngine.init();
      this.log.info("初始化风险控制引擎完毕！");
      System.out.println(DateUtil.getCurDateTime() + "  初始化风险控制引擎完毕！");
      
      statusListener();
      
      serverDAO.insertSysLog(new SysLog("交易服务器初始化成功,启动时间" + (
        System.currentTimeMillis() - startDate) + "毫秒;"));
      

      getFirmDAO().addGlobalLog(
        "System", 
        "", 
        1202, 
        "交易服务器初始化成功,启动时间" + (
        System.currentTimeMillis() - startDate) + "毫秒;", 
        1);
      
      printWelcome();
      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("交易服务器初始化失败，原因：" + e);
    }
    return false;
  }
  
  private void printWelcome()
  {
    System.out.println("=============================================================================");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                      欢迎进入交易服务器系统!!                             =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                            已成功启动!                                    =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                      北京金网安泰信息技术有限公司.                        =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                                           =");
    System.out
      .println("=                                                        " + 
      DateUtil.getCurDate() + "         =");
    System.out
      .println("=============================================================================");
  }
  
  private void changeTradeDate(Date tradeDate, Date clearDate, Date nextTradeDate)
  {
    systemStatus.setTradeDate(tradeDate);
    systemStatus.setNextTradeDate(nextTradeDate);
    systemStatus.setClearDate(clearDate);
    systemStatus.setStatus(0);
    systemStatus.setSectionID(null);
    systemStatus.setNote(null);
    systemStatus.setRecoverTime(null);
  }
}
