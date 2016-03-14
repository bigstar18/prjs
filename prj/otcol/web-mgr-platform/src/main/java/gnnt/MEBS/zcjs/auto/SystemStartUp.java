package gnnt.MEBS.zcjs.auto;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.zcjs.rmi.SystemSettingServerRmi;
import gnnt.MEBS.zcjs.rmi.SystemSettingServerRmiImpl;
import java.io.PrintStream;
import java.rmi.Naming;
import java.util.Map;
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
    DaoHelper dao = (DaoHelper)gnnt.MEBS.base.util.SysData.getBean("daoHelper");
    DataSource dataSource = dao.getDataSource();
    Map<String, Object> map = LogonManager.getRMIConfig("3", dataSource);
    String rmiIP = (String)map.get("host");
    int rmiPort = ((Integer)map.get("port")).intValue();
    String rmi = "rmi://" + rmiIP + ":" + rmiPort + "/";
    LogonManager logonManager = LogonManager.createInstance("3", dataSource);
    this.logger.debug("zcjs rmi start");
    this.logger.debug("----------------------------------");
    try
    {
      SystemSettingServerRmi sssri = new SystemSettingServerRmiImpl();
      Naming.rebind(rmi + "SystemSettingServerRmi", sssri);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    SystemObjectInit systemObjectInit = (SystemObjectInit)gnnt.MEBS.zcjs.util.SysData.getBean("z_systemObjectInit");
    systemObjectInit.init();
    ProsceniumShowInit prosceniumShowInit = (ProsceniumShowInit)gnnt.MEBS.zcjs.util.SysData.getBean("z_prosceniumShowInit");
    prosceniumShowInit.init();
  }
}
