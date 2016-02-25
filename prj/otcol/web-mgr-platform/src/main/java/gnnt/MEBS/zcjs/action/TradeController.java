package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.zcjs.memory.system.constant.SystemStatusValue;
import gnnt.MEBS.zcjs.model.MarketStatus;
import gnnt.MEBS.zcjs.rmi.SystemSettingServerRmi;
import gnnt.MEBS.zcjs.services.MarketConfigService;
import gnnt.MEBS.zcjs.services.MarketStatusService;
import gnnt.MEBS.zcjs.util.SysData;
import java.io.PrintStream;
import java.rmi.Naming;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class TradeController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(TradeController.class);
  
  private MarketStatusService getBeanOfMarketStatusService()
  {
    MarketStatusService localMarketStatusService = null;
    synchronized (MarketConfigService.class)
    {
      if (localMarketStatusService == null) {
        localMarketStatusService = (MarketStatusService)SysData.getBean("z_marketStatusService");
      }
    }
    return localMarketStatusService;
  }
  
  public ModelAndView init(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'view' method...");
    MarketStatus localMarketStatus = getBeanOfMarketStatusService().getObject();
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "marketconfig/manageMarket");
    localModelAndView.addObject("marketStatus", localMarketStatus);
    return localModelAndView;
  }
  
  public ModelAndView tradeManage(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'tradeManage' method...");
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("optFlag");
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    DataSource localDataSource = localDaoHelper.getDataSource();
    Map localMap = LogonManager.getRMIConfig("3", localDataSource);
    String str3 = (String)localMap.get("host");
    int i = ((Integer)localMap.get("port")).intValue();
    SystemSettingServerRmi localSystemSettingServerRmi = (SystemSettingServerRmi)Naming.lookup("rmi://" + str3 + ":" + i + "/SystemSettingServerRmi");
    System.out.println("rmi://" + str3 + ":" + i + "/SystemSettingServerRmi" + ";*****rmi");
    System.out.println("进行的操作：：：：" + str2);
    try
    {
      if ("start".equals(str2))
      {
        localSystemSettingServerRmi.setManualOrder(SystemStatusValue.MARKET_READY);
        str1 = "系统启动成功";
      }
      else if ("pause".equals(str2))
      {
        localSystemSettingServerRmi.setManualOrder(SystemStatusValue.MARKET_SUSPEND);
        str1 = "系统暂停成功";
      }
      else if ("resume".equals(str2))
      {
        localSystemSettingServerRmi.setManualOrder(SystemStatusValue.MARKET_RECOVER);
        str1 = "系统恢复成功";
      }
      else if ("tradeover".equals(str2))
      {
        localSystemSettingServerRmi.setManualOrder(SystemStatusValue.MARKET_FINISH);
        str1 = "交易结束成功";
      }
      else if ("shut".equals(str2))
      {
        localSystemSettingServerRmi.setManualOrder(SystemStatusValue.MARKET_CLOSE);
        str1 = "闭市成功";
      }
    }
    catch (Exception localException)
    {
      str1 = "操作失败！";
      localException.printStackTrace();
    }
    Thread.sleep(2000L);
    setResultMsg(paramHttpServletRequest, str1);
    return new ModelAndView("redirect:" + Condition.PATH + "tradeController.zcjs?funcflg=init");
  }
}
