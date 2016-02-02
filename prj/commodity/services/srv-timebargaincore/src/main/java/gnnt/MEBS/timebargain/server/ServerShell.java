package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.common.communicate.IBalanceRMI;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.rmi.BalanceRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.DelayRMI;
import gnnt.MEBS.timebargain.server.rmi.DelayRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.ExtendRMI;
import gnnt.MEBS.timebargain.server.rmi.ExtendRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.ProxyRMI;
import gnnt.MEBS.timebargain.server.rmi.ProxyRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.ServerRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.TradeMonitorRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeMonitorRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMIImpl;
import gnnt.MEBS.timebargain.server.rmi.quotation.IQuotationRMI;
import gnnt.MEBS.timebargain.server.rmi.quotation.impl.QuotationRMIImpl;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import gnnt.MEBS.timebargain.server.util.RMIDataSocket;
import gnnt.MEBS.timebargain.server.util.SysConfig;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerShell
{
  public static String[] args = null;
  public static boolean RunModeIsSIM = false;
  public static boolean RunModeIsSpecial = false;
  public static ServerRMI serverRMI = null;
  public static TradeRMI tradeRMI = null;
  public static ExtendRMI extendRMI = null;
  public static ProxyRMI proxyRMI = null;
  public static DelayRMI delayRMI = null;
  public static TradeMonitorRMI tradeMonitorRMI = null;
  private static IBalanceRMI balanceRMI = null;
  public static IQuotationRMI quotationRMI = null;
  
  public static void main(String[] paramArrayOfString)
  {
    parseArgs(paramArrayOfString);
    Log localLog = LogFactory.getLog(ServerShell.class);
    localLog.info("正在启动交易服务器．．．");
    localLog.info("正在读取RMI配置信息．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在读取RMI配置信息．．．");
    Map localMap = null;
    try
    {
      localMap = SysConfig.getRMIConfig((DataSource)DAOBeanFactory.getBean("dataSource"));
      localLog.info("读取RMI配置信息完毕！");
      System.out.println(DateUtil.getCurDateTime() + "  读取RMI配置信息完毕！");
    }
    catch (Exception localException1)
    {
      localLog.error("读取RMI配置信息失败，原因：" + localException1.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "  读取RMI配置信息失败，原因：" + localException1.getMessage());
      System.exit(1);
    }
    Server localServer = Server.getInstance();
    localServer.init();
    localLog.info("正在启动开启交易服务器的定时任务．．．");
    System.out.println(DateUtil.getCurDateTime() + "  正在启动开启交易服务器的定时任务．．．");
    DAOBeanFactory.getBean("quartzScheduler");
    localLog.info("启动开启交易服务器的定时任务成功！");
    System.out.println(DateUtil.getCurDateTime() + "  启动开启交易服务器的定时任务成功！");
    try
    {
      localLog.info("正在启动RMI服务器．．．");
      System.out.println(DateUtil.getCurDateTime() + "  正在启动RMI服务器．．．");
      serverRMI = new ServerRMIImpl(localServer);
      tradeRMI = new TradeRMIImpl(localServer);
      extendRMI = new ExtendRMIImpl(localServer);
      proxyRMI = new ProxyRMIImpl(localServer);
      delayRMI = new DelayRMIImpl(localServer);
      tradeMonitorRMI = new TradeMonitorRMIImpl(localServer);
      balanceRMI = new BalanceRMIImpl(localServer);
      quotationRMI = new QuotationRMIImpl();
      String str = (String)localMap.get("host");
      localLog.info("rmi_ip:" + str);
      int i = ((Integer)localMap.get("port")).intValue();
      localLog.info("rmi_port:" + (Integer)localMap.get("port"));
      int j = ((Integer)localMap.get("rmidataport")).intValue();
      localLog.info("rmidataport:" + (Integer)localMap.get("rmidataport"));
      try
      {
        RMISocketFactory.setSocketFactory(new RMIDataSocket(j));
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        localLog.error("绑定数据端口失败" + localException2.toString());
      }
      LocateRegistry.createRegistry(i);
      Naming.rebind("rmi://" + str + ":" + i + "/ServerRMI", serverRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/TradeRMI", tradeRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/ExtendRMI", extendRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/ProxyRMI", proxyRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/DelayRMI", delayRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/TradeMonitorRMI", tradeMonitorRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/balanceRMI", balanceRMI);
      Naming.rebind("rmi://" + str + ":" + i + "/QuotationRMI", quotationRMI);
      localLog.info("RMI服务器启动成功！");
      System.out.println(DateUtil.getCurDateTime() + "  RMI服务器启动成功！");
    }
    catch (RemoteException localRemoteException)
    {
      localLog.error("RMI服务器启动失败，原因:" + localRemoteException.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "  RMI服务器启动失败，原因:" + localRemoteException.getMessage());
      System.exit(1);
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localLog.error("RMI服务器启动失败，原因:" + localMalformedURLException.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "  RMI服务器启动失败，原因:" + localMalformedURLException.getMessage());
      System.exit(1);
    }
    localServer.initServer(false);
  }
  
  private static void parseArgs(String[] paramArrayOfString)
  {
    args = paramArrayOfString;
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramArrayOfString[i].startsWith("-o"))
      {
        if (paramArrayOfString[i].equalsIgnoreCase("-oRunMode=SIM")) {
          RunModeIsSIM = true;
        }
        if (paramArrayOfString[i].equalsIgnoreCase("-oSpecial=TRUE")) {
          RunModeIsSpecial = true;
        }
      }
    }
  }
}
