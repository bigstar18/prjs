package gnnt.bank.complex.util;

import gnnt.bank.complex.rmi.server.ComplexProcessorRMIServer;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartupRMIServer extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  public void destroy()
  {
    super.destroy();
  }

  public void init() throws ServletException {
    try {
      ComplexProcessorRMIServer rmiS = new ComplexProcessorRMIServer();
      String RmiIpAddress = Util.getConfig("RmiIpAddress");
      String RmiPortNumber = Util.getConfig("RmiPortNumber");
      String RmiDataPort = Util.getConfig("RmiDataPort");
      String RmiServiceName = Util.getConfig("RmiServiceName");
      int port = Integer.parseInt(RmiPortNumber);
      int dataPort = Integer.parseInt(RmiDataPort);
      rmiS.startRMI(RmiIpAddress, port, dataPort, RmiServiceName);
      System.out.println("处理器RMI服务启动完成~");
    } catch (Exception e) {
      System.out.println("RMI启动发生异常，异常信息如下：");
      e.printStackTrace();
    }
  }
}