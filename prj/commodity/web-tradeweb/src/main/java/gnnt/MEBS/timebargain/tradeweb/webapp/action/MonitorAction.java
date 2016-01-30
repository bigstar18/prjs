package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.util.SysConfig;
import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MonitorAction
  extends HttpServlet
{
  private static final long serialVersionUID = -6057097692864506807L;
  private static ServerRMI serverRMI = null;
  private static TradeRMI tradeRMI = null;
  private static ApplicationContext ctx = null;
  
  public static TradeRMI getTradeRMI()
  {
    return tradeRMI;
  }
  
  public static ServerRMI getServerRMI()
  {
    return serverRMI;
  }
  
  protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {}
  
  protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {}
  
  public Object getBean(String paramString)
  {
    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
    return ctx.getBean(paramString);
  }
  
  public void init()
    throws ServletException
  {
    initRMI();
    String str = getServletContext().getRealPath("WEB-INF/../") + File.separator + "logs";
    MonitorThread localMonitorThread = new MonitorThread(str);
    localMonitorThread.start();
  }
  
  private void initRMI()
  {
    Map localMap = SysConfig.getRMIConfig((DataSource)getBean("dataSource"));
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/ServerRMI");
      serverRMI = (ServerRMI)Naming.lookup(localStringBuffer1.toString());
      StringBuffer localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/TradeRMI");
      tradeRMI = (TradeRMI)Naming.lookup(localStringBuffer2.toString());
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    catch (NotBoundException localNotBoundException)
    {
      localNotBoundException.printStackTrace();
    }
  }
  
  public static String getCurDbDate()
  {
    return DateUtil.Mills2Date(System.currentTimeMillis() + TradeService.diff);
  }
  
  public static String getCurDbDateTime()
  {
    return DateUtil.Mills2Date(System.currentTimeMillis() + TradeService.diff) + DateUtil.Mills2Time(System.currentTimeMillis() + TradeService.diff);
  }
  
  private class MonitorThread
    extends Thread
  {
    public String outFilePath = null;
    
    public MonitorThread(String paramString)
    {
      this.outFilePath = paramString;
    }
    
    public void run()
    {
      FileWriter localFileWriter = null;
      try
      {
        String str1 = MonitorAction.getCurDbDate();
        String str2 = str1;
        File localFile = new File(this.outFilePath);
        if (!localFile.exists()) {
          localFile.mkdirs();
        }
        localFileWriter = new FileWriter(this.outFilePath + "/tradercount." + str1, true);
        for (;;)
        {
          try
          {
            if (MonitorAction.getServerRMI().getSystemStatus().getStatus() != 3)
            {
              str1 = MonitorAction.getCurDbDate();
              if (!str1.equals(str2))
              {
                if (localFileWriter != null) {
                  localFileWriter.close();
                }
                localFileWriter = new FileWriter(this.outFilePath + "/tradercount." + str1, true);
              }
              str2 = str1;
              int i = MonitorAction.getTradeRMI().getTraders().size();
              localFileWriter.write(MonitorAction.getCurDbDateTime() + "," + i + "\r\n");
              localFileWriter.flush();
            }
            Thread.sleep(1000L);
          }
          catch (RemoteException localRemoteException) {}catch (InterruptedException localInterruptedException) {}catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
      catch (IOException localIOException1)
      {
        if (localFileWriter != null) {
          try
          {
            localFileWriter.close();
          }
          catch (IOException localIOException2) {}
        }
      }
    }
  }
}
