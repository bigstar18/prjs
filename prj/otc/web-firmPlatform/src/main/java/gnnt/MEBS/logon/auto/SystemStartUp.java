package gnnt.MEBS.logon.auto;

import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import java.io.PrintStream;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemStartUp
  extends HttpServlet
{
  private final transient Log logger = LogFactory.getLog(SystemStartUp.class);
  
  public void init()
    throws ServletException
  {
    super.init();
    System.out.println("******************启动**********************");
    DataSource dataSource = null;
    InitialContext ic = null;
    try
    {
      ic = new InitialContext();
      dataSource = (DataSource)ic.lookup(ActionConstant.JNDINAME);
    }
    catch (NamingException e)
    {
      e.printStackTrace();
    }
    LogonManager logonManager = 
      LogonManager.createInstance("4", dataSource);
    System.out.println("*****************启动成功******************");
    this.logger.debug("firmPlatform.systemStartUp start");
    this.logger.debug("----------------------------------");
  }
}
