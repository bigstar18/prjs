package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.util.SysConfig;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitRMI
{
  protected final Log logger = LogFactory.getLog(InitRMI.class);
  private TradeRMI tradeRMI = null;
  private ServerRMI serverRMI = null;
  
  public InitRMI(ServletContext paramServletContext)
    throws MalformedURLException, RemoteException, NotBoundException
  {
    WebApplicationContext localWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(paramServletContext);
    Map localMap = SysConfig.getRMIConfig((DataSource)localWebApplicationContext.getBean("dataSource"));
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/TradeRMI");
    this.logger.debug("------->tradeRMI server url:" + localStringBuffer1.toString());
    this.tradeRMI = ((TradeRMI)Naming.lookup(localStringBuffer1.toString()));
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer2.append("rmi://").append(localMap.get("host")).append(":").append(localMap.get("port")).append("/ServerRMI");
    this.logger.debug("------->serverRMI server url:" + localStringBuffer2.toString());
    this.serverRMI = ((ServerRMI)Naming.lookup(localStringBuffer2.toString()));
  }
  
  public ServerRMI getServerRMI()
  {
    return this.serverRMI;
  }
  
  public TradeRMI getTradeRMI()
  {
    return this.tradeRMI;
  }
}
