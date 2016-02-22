package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
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
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {}
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {}
  
  public Object getBean(String name)
  {
    if (ctx == null) {
      ctx = 
        WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
    return ctx.getBean(name);
  }
  
  public void init()
    throws ServletException
  {
    initRMI();
    String filepath = getServletContext().getRealPath("WEB-INF/../") + File.separator + "logs";
    MonitorThread mt = new MonitorThread(filepath);
    mt.start();
  }
  
  private void initRMI()
  {
    Map rmiMap = LogonManager.getRMIConfig("2", 
      (DataSource)getBean("dataSource"));
    try
    {
      StringBuffer sb1 = new StringBuffer();
      sb1.append("rmi://").append(rmiMap.get("host")).append(":").append(
        rmiMap.get("port")).append("/ServerRMI");
      serverRMI = (ServerRMI)Naming.lookup(sb1.toString());
      StringBuffer sb2 = new StringBuffer();
      sb2.append("rmi://").append(rmiMap.get("host")).append(":").append(
        rmiMap.get("port")).append("/TradeRMI");
      tradeRMI = (TradeRMI)Naming.lookup(sb2.toString());
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    catch (NotBoundException e)
    {
      e.printStackTrace();
    }
  }
  
  public static String getCurDbDate()
  {
    return DateUtil.Mills2Date(System.currentTimeMillis() + TradeService.diff);
  }
  
  public static String getCurDbDateTime()
  {
    return 
      DateUtil.Mills2Date(System.currentTimeMillis() + TradeService.diff) + DateUtil.Mills2Time(System.currentTimeMillis() + TradeService.diff);
  }
  
  private class MonitorThread
    extends Thread
  {
    public String outFilePath = null;
    
    public MonitorThread(String outFilePath)
    {
      this.outFilePath = outFilePath;
    }
    
    public void run()
    {
      FileWriter fw = null;
      try
      {
        String curDate = MonitorAction.getCurDbDate();
        String preDate = curDate;
        File f = new File(this.outFilePath);
        if (!f.exists()) {
          f.mkdirs();
        }
        fw = new FileWriter(this.outFilePath + "/tradercount." + curDate, true);
        for (;;)
        {
          try
          {
            if (MonitorAction.getServerRMI().getSystemStatus().getStatus() != 3)
            {
              curDate = MonitorAction.getCurDbDate();
              if (!curDate.equals(preDate))
              {
                if (fw != null) {
                  fw.close();
                }
                fw = new FileWriter(this.outFilePath + "/tradercount." + curDate, true);
              }
              preDate = curDate;
              
              int count = MonitorAction.getTradeRMI().getTraders().size();
              fw.write(MonitorAction.getCurDbDateTime() + "," + count + "\r\n");
              fw.flush();
            }
            Thread.sleep(1000L);
          }
          catch (RemoteException localRemoteException) {}catch (InterruptedException localInterruptedException) {}
        }
      }
      catch (IOException e1)
      {
        if (fw != null) {
          try
          {
            fw.close();
          }
          catch (IOException localIOException1) {}
        }
      }
    }
  }
}
