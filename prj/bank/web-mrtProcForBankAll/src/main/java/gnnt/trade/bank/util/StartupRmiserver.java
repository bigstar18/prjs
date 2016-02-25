package gnnt.trade.bank.util;

import gnnt.trade.bank.data.ccb.vo.CCBConstant;
import gnnt.trade.bank.processorrmi.server.CapitalProcessorRMIServer;
import java.io.PrintStream;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartupRmiserver
  extends HttpServlet
{
  public static final long serialVersionUID = 1000000000L;
  
  public void destroy()
  {
    super.destroy();
  }
  
  public void init()
    throws ServletException
  {
    try
    {
      CapitalProcessorRMIServer rmiS = new CapitalProcessorRMIServer();
      Properties props = new Configuration().getSection("BANK.Processor");
      
      new CCBConstant(props.getProperty("CCBbankID"), props.getProperty("CCBmarketID"), props.getProperty("changID"));
      
      String RmiIpAddress = props.getProperty("RmiIpAddress");
      String RmiPortNumber = props.getProperty("RmiPortNumber");
      String RmiServerNames = props.getProperty("RmiServiceName");
      int port = Integer.parseInt(RmiPortNumber);
      rmiS.startRMI(RmiIpAddress, port, RmiServerNames);
      
      System.out.println("处理器RMI服务启动完成~");
      System.out.println("初始化参数完成~");
    }
    catch (Exception e)
    {
      System.out.println("RMI启动发生异常，异常信息如下：");
      e.printStackTrace();
    }
  }
  
  public static void main(String[] b)
  {
    StartupRmiserver rs = new StartupRmiserver();
    try
    {
      rs.init();
    }
    catch (ServletException e)
    {
      e.printStackTrace();
    }
  }
}
