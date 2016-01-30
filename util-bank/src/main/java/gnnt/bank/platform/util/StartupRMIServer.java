package gnnt.bank.platform.util;

import gnnt.bank.platform.rmi.server.CapitalProcessorRMIServer;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartupRMIServer extends HttpServlet
{
  public static final long serialVersionUID = 1000000000L;

  public void destroy()
  {
    super.destroy();
  }

  public void init() throws ServletException
  {
    try {
      CapitalProcessorRMIServer rmiS = new CapitalProcessorRMIServer();
      String RmiIpAddress = Tool.getConfig("RmiIpAddress");
      String RmiPortNumber = Tool.getConfig("RmiPortNumber");
      String RmiDataPort = Tool.getConfig("RmiDataPort");
      String BankRmiServiceName = Tool.getConfig("BankRmiServiceName");
      String TradeRmiServiceName = Tool.getConfig("TradeRmiServiceName");
      int port = Integer.parseInt(RmiPortNumber);
      int dataPort = Integer.parseInt(RmiDataPort);
      rmiS.startRMI(RmiIpAddress, port, dataPort, BankRmiServiceName, TradeRmiServiceName);
      System.out.println("处理器RMI服务启动完成~");
    }
    catch (Exception e)
    {
      System.out.println("RMI启动发生异常，异常信息如下：");
      e.printStackTrace();
    }
  }
}