package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
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
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitRMI
{
  protected final Log logger = LogFactory.getLog(InitRMI.class);
  private TradeRMI tradeRMI = null;
  private ServerRMI serverRMI = null;
  
  public InitRMI(ServletContext servletContext)
    throws MalformedURLException, RemoteException, NotBoundException
  {
    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    Map rmiMap = LogonManager.getRMIConfig("2", (DataSource)ctx.getBean("dataSource"));
    StringBuffer sb = new StringBuffer();
    sb.append("rmi://").append(rmiMap.get("host")).append(":").append(rmiMap.get("port")).append("/TradeRMI");
    this.logger.debug("------->tradeRMI server url:" + sb.toString());
    this.tradeRMI = ((TradeRMI)Naming.lookup(sb.toString()));
    
    StringBuffer sb2 = new StringBuffer();
    sb2.append("rmi://").append(rmiMap.get("host")).append(":").append(rmiMap.get("port")).append("/ServerRMI");
    this.logger.debug("------->serverRMI server url:" + sb2.toString());
    this.serverRMI = ((ServerRMI)Naming.lookup(sb2.toString()));
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
