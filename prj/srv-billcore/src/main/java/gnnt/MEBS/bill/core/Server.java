package gnnt.MEBS.bill.core;

import gnnt.MEBS.bill.communicate.RMIServer;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.po.TradeModelPO;
import gnnt.MEBS.bill.core.util.DateUtil;
import gnnt.MEBS.bill.core.util.GnntBeanFactory;
import gnnt.MEBS.bill.core.util.Tool;
import gnnt.MEBS.common.communicate.IBalanceRMI;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.impl.StdScheduler;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.rmi.RmiServiceExporter;

public class Server
  implements ApplicationContextAware
{
  private Log log = LogFactory.getLog(getClass());
  private static volatile Server instance;
  private String ip;
  private WareHouseStockDAO wareHouseStockDAO;
  boolean stop = false;
  
  public static Server getInstance()
  {
    if (instance == null) {
      synchronized (Server.class)
      {
        if (instance == null) {
          instance = (Server)GnntBeanFactory.getBean("server");
        }
      }
    }
    return instance;
  }
  
  public void init()
  {
    this.wareHouseStockDAO = ((WareHouseStockDAO)GnntBeanFactory.getBean("wareHouseStockDAO"));
  }
  
  public boolean initServer()
  {
    try
    {
      startBalanceRMI();
      printWelcome();
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("交易服务器初始化失败，原因：" + localException);
    }
    return false;
  }
  
  public void task()
  {
    this.log.info("task");
  }
  
  public void moveHistory() {}
  
  public WareHouseStockDAO getWareHouseStockDAO()
  {
    return this.wareHouseStockDAO;
  }
  
  public String getIp()
  {
    if (this.ip == null) {
      try
      {
        this.ip = InetAddress.getLocalHost().getHostAddress();
      }
      catch (UnknownHostException localUnknownHostException)
      {
        localUnknownHostException.printStackTrace();
      }
    }
    return this.ip;
  }
  
  public void stop()
  {
    if (this.stop) {
      return;
    }
    this.log.info("================停止服务=============");
    RmiServiceExporter localRmiServiceExporter1 = (RmiServiceExporter)GnntBeanFactory.getBean("kernelRMIService");
    if (localRmiServiceExporter1 != null) {
      try
      {
        this.log.info("================停止kernelRMIServiceExporter服务=============");
        localRmiServiceExporter1.destroy();
      }
      catch (Exception localException1)
      {
        this.log.info("================停止kernelRMIServiceExporter服务时发生异常=============");
        localException1.printStackTrace();
      }
    }
    RmiServiceExporter localRmiServiceExporter2 = (RmiServiceExporter)GnntBeanFactory.getBean("tradeRMIService");
    if (localRmiServiceExporter2 != null) {
      try
      {
        this.log.info("================停止tradeRMIService服务=============");
        localRmiServiceExporter2.destroy();
      }
      catch (Exception localException2)
      {
        this.log.info("================停止tradeRMIService服务时发生异常=============");
        localException2.printStackTrace();
      }
    }
    StdScheduler localStdScheduler = (StdScheduler)GnntBeanFactory.getBean("quartzScheduler");
    if (localStdScheduler != null) {
      try
      {
        this.log.info("================停止quartzScheduler任务=============");
        localStdScheduler.resumeAll();
        localStdScheduler.shutdown();
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    this.wareHouseStockDAO = null;
    this.stop = true;
  }
  
  public void close()
  {
    this.log.info("close");
    stop();
  }
  
  public void startBalanceRMI()
  {
    this.log.info("开始启动 BalanceRMI 服务....................");
    int i = Tool.strToInt(GnntBeanFactory.getConfig("SelfModuleID"), 13);
    TradeModelPO localTradeModelPO = this.wareHouseStockDAO.getTradeModel(i);
    if (localTradeModelPO == null)
    {
      this.log.error("启动时获取数据库中本模块信息失败");
      System.exit(0);
    }
    RMIServer localRMIServer = new RMIServer();
    IBalanceRMI localIBalanceRMI = (IBalanceRMI)GnntBeanFactory.getBean("balanceRMI");
    String str = GnntBeanFactory.getConfig("BalanceRMIServiceName");
    if ((str == null) || (str.trim().length() <= 0)) {
      str = "balanceRMI";
    }
    try
    {
      localRMIServer.startRMI(localTradeModelPO.getHostIP(), localTradeModelPO.getPort(), localTradeModelPO.getRmiDataPort(), str.trim(), localIBalanceRMI);
    }
    catch (Exception localException)
    {
      this.log.error("启动 RMI 服务失败", localException);
      System.exit(0);
    }
    this.log.info("启动 BalanceRMI 服务成功...........");
  }
  
  private void printWelcome()
  {
    System.out.println("=============================================================================");
    System.out.println("=                                                                          =");
    System.out.println("=                                                                          =");
    System.out.println("=                      欢迎进入仓单管理系统核心!!                              =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                            已成功启动!                                     =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                      北京金网安泰信息技术有限公司.                          =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                        " + DateUtil.getCurDate() + "         =");
    System.out.println("=============================================================================");
  }
  
  public void setApplicationContext(ApplicationContext paramApplicationContext)
    throws BeansException
  {
    String str = "org.springframework.aop.config.internalAutoProxyCreator";
    AspectJAwareAdvisorAutoProxyCreator localAspectJAwareAdvisorAutoProxyCreator = (AspectJAwareAdvisorAutoProxyCreator)paramApplicationContext.getBean(str);
    localAspectJAwareAdvisorAutoProxyCreator.setExposeProxy(true);
  }
}
