package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.rmi.QuotationRMI;
import gnnt.MEBS.timebargain.server.rmi.QuotationRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.ServerRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMIImpl;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerShell
{
  public static String[] args = null;
  public static boolean RunModeIsSIM = false;
  public static ServerRMI serverRMI = null;
  public static TradeRMI tradeRMI = null;
  public static QuotationRMI quotationRMI = null;
  
  public static void main(String[] args)
  {
    parseArgs(args);
    Log log = LogFactory.getLog(ServerShell.class);
    log.info("正在启动交易服务器．．．");
    log.info("正在读取RMI配置信息．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在读取RMI配置信息．．．");
    Thread.currentThread().setPriority(10);
    
    Map rmiMap = null;
    try
    {
      rmiMap = 
        LogonManager.getRMIConfig("2", (DataSource)
        DAOBeanFactory.getBean("dataSource"));
      log.info("读取RMI配置信息完毕！");
      System.out.println(DateUtil.getCurDateTime() + "  读取RMI配置信息完毕！");
    }
    catch (Exception e)
    {
      log.error("读取RMI配置信息失败，原因：" + e.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "  读取RMI配置信息失败，原因：" + 
        e.getMessage());
      System.exit(1);
    }
    Server server = Server.getInstance();
    server.init();
    log.info("正在启动开启交易服务器的定时任务．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在启动开启交易服务器的定时任务．．．");
    DAOBeanFactory.getBean("quartzScheduler");
    log.info("启动开启交易服务器的定时任务成功！");
    System.out.println(DateUtil.getCurDateTime() + "  启动开启交易服务器的定时任务成功！");
    try
    {
      log.info("正在启动RMI服务器．．．");
      System.out.println(DateUtil.getCurDateTime() + "  正在启动RMI服务器．．．");
      serverRMI = new ServerRMIImpl(server);
      tradeRMI = new TradeRMIImpl(server);
      quotationRMI = new QuotationRMIImpl(server);
      





      String rmi_ip = (String)rmiMap.get("host");
      log.info("rmi_ip:" + rmi_ip);
      int rmi_port = ((Integer)rmiMap.get("port")).intValue();
      log.info("rmi_port:" + (Integer)rmiMap.get("port"));
      





      Naming.rebind("rmi://" + rmi_ip + ":" + rmi_port + "/ServerRMI", 
        serverRMI);
      Naming.rebind("rmi://" + rmi_ip + ":" + rmi_port + "/TradeRMI", 
        tradeRMI);
      Naming.rebind("rmi://" + rmi_ip + ":" + rmi_port + "/QuotationRMI", 
        quotationRMI);
      log.info("RMI服务器启动成功！");
      System.out.println(DateUtil.getCurDateTime() + "  RMI服务器启动成功！");
    }
    catch (RemoteException re)
    {
      log.error("RMI服务器启动失败，原因:" + re.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "  RMI服务器启动失败，原因:" + 
        re.getMessage());
      System.exit(1);
    }
    catch (MalformedURLException mfe)
    {
      log.error("RMI服务器启动失败，原因:" + mfe.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "  RMI服务器启动失败，原因:" + 
        mfe.getMessage());
      System.exit(1);
    }
    server.initServer(false);
  }
  
  private static void parseArgs(String[] args)
  {
    args = args;
    for (int i = 0; i < args.length; i++) {
      if ((args[i].startsWith("-o")) && 
        (args[i].equalsIgnoreCase("-oRunMode=SIM"))) {
        RunModeIsSIM = true;
      }
    }
  }
}
