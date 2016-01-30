package gnnt.MEBS.common.core;

import gnnt.MEBS.common.communicate.IBalanceRMI;
import gnnt.MEBS.common.communicate.RMIServer;
import gnnt.MEBS.common.core.dao.SysSchedulerDAO;
import gnnt.MEBS.common.core.jms.ProducerToolSendQueue;
import gnnt.MEBS.common.core.jms.ProducerToolSendTopic;
import gnnt.MEBS.common.core.jms.StaticThreadPool;
import gnnt.MEBS.common.core.po.TradeModelPO;
import gnnt.MEBS.common.core.sysscheduler.ISysScheduler;
import gnnt.MEBS.common.core.util.DateUtil;
import gnnt.MEBS.common.core.util.GnntBeanFactory;
import gnnt.MEBS.common.core.util.Tool;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jms.JMSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Server
  implements ApplicationContextAware
{
  private Log log = LogFactory.getLog(getClass());
  private static volatile Server instance;
  private String ip;
  private StaticThreadPool staticThreadPool;
  public ISysScheduler sysScheduler;
  private SysSchedulerDAO sysSchedulerDAO;
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
    this.sysSchedulerDAO = ((SysSchedulerDAO)GnntBeanFactory.getBean("sysSchedulerDAO"));
    this.staticThreadPool = StaticThreadPool.getInstance();
    this.sysScheduler = ((ISysScheduler)GnntBeanFactory.getBean("sysScheduler"));
  }
  
  public boolean initServer()
  {
    try
    {
      this.sysScheduler.loadInitData();
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
  
  public void stop()
  {
    if (this.stop) {
      return;
    }
    if (this.staticThreadPool != null)
    {
      this.log.info("========释放静态线程池.==========");
      this.staticThreadPool.exit();
      this.staticThreadPool = null;
    }
    ProducerToolSendTopic localProducerToolSendTopic1 = (ProducerToolSendTopic)GnntBeanFactory.getBean("frontProducerTopic");
    if (localProducerToolSendTopic1 != null)
    {
      this.log.info("========释放前台消息订阅发送对象.==========");
      try
      {
        localProducerToolSendTopic1.close();
      }
      catch (JMSException localJMSException1) {}
      localProducerToolSendTopic1 = null;
    }
    ProducerToolSendTopic localProducerToolSendTopic2 = (ProducerToolSendTopic)GnntBeanFactory.getBean("mgrProducerTopic");
    if (localProducerToolSendTopic2 != null)
    {
      this.log.info("========释放 后台消息订阅发送对象.==========");
      try
      {
        localProducerToolSendTopic2.close();
      }
      catch (JMSException localJMSException2) {}
      localProducerToolSendTopic2 = null;
    }
    ProducerToolSendQueue localProducerToolSendQueue = (ProducerToolSendQueue)GnntBeanFactory.getBean("producerQueue");
    if (localProducerToolSendQueue != null)
    {
      this.log.info("========释放 点对点消息发送对象 ==========");
      try
      {
        localProducerToolSendQueue.close();
      }
      catch (JMSException localJMSException3) {}
      localProducerToolSendQueue = null;
    }
    this.stop = true;
  }
  
  public void close()
  {
    this.log.info("close");
    stop();
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
  
  public StaticThreadPool getStaticThreadPool()
  {
    return this.staticThreadPool;
  }
  
  public SysSchedulerDAO getSysSchedulerDAO()
  {
    return this.sysSchedulerDAO;
  }
  
  public void moveHistory() {}
  
  public void startBalanceRMI()
  {
    this.log.info("开始启动 BalanceRMI 服务...........");
    int i = Tool.strToInt(GnntBeanFactory.getConfig("SelfModuleID"), 99);
    TradeModelPO localTradeModelPO = getSysSchedulerDAO().getTradeModel(i);
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
    System.out.println("=                      欢迎进入后台管理核心系统!!                            =");
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
