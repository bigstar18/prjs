package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.zcjs.model.MarketConfig;
import gnnt.MEBS.zcjs.model.MarketStatus;
import gnnt.MEBS.zcjs.model.TradeRestDate;
import gnnt.MEBS.zcjs.rmi.SystemSettingServerRmi;
import gnnt.MEBS.zcjs.services.SystemConfigService;
import gnnt.MEBS.zcjs.util.SysData;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class SystemConfigController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(SystemConfigController.class);
  
  private SystemConfigService getBeanOfMarketConfigService()
  {
    SystemConfigService localSystemConfigService = null;
    synchronized (SystemConfigService.class)
    {
      if (localSystemConfigService == null) {
        localSystemConfigService = (SystemConfigService)SysData.getBean("z_systemConfigService");
      }
    }
    return localSystemConfigService;
  }
  
  public ModelAndView viewForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter viewForward!");
    SystemConfigService localSystemConfigService = getBeanOfMarketConfigService();
    List localList = localSystemConfigService.systemManagerView();
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "marketconfig/marketConfig");
    localModelAndView.addObject("tradeRestDate", (TradeRestDate)localList.get(0));
    localModelAndView.addObject("marketConfig", (MarketConfig)localList.get(1));
    localModelAndView.addObject("marketStatus", (MarketStatus)localList.get(2));
    return localModelAndView;
  }
  
  public ModelAndView saveSystemConfig(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter saveSystemConfig!");
    SystemConfigService localSystemConfigService = getBeanOfMarketConfigService();
    ArrayList localArrayList = new ArrayList();
    TradeRestDate localTradeRestDate = new TradeRestDate();
    MarketConfig localMarketConfig = new MarketConfig();
    MarketStatus localMarketStatus = new MarketStatus();
    String str1 = paramHttpServletRequest.getParameter("marketId");
    String str2 = paramHttpServletRequest.getParameter("weekRest");
    String str3 = paramHttpServletRequest.getParameter("yearRest");
    String str4 = paramHttpServletRequest.getParameter("submitEndTime");
    int i = Integer.parseInt(paramHttpServletRequest.getParameter("financeStatus"));
    localTradeRestDate.setMarketId(str1);
    localTradeRestDate.setWeekRest(str2);
    localTradeRestDate.setYearRest(str3);
    localMarketConfig.setMarketId(str1);
    localMarketConfig.setFinanceStatus(i);
    localMarketConfig.setSubmitEndTime(str4);
    localMarketStatus.setMarketId(str1);
    localMarketStatus.setIsAuto(paramHttpServletRequest.getParameter("isAuto"));
    localArrayList.add(localTradeRestDate);
    localArrayList.add(localMarketConfig);
    localArrayList.add(localMarketStatus);
    String str5 = "";
    try
    {
      localSystemConfigService.systemManagerUpdate(localArrayList);
      str5 = "修改成功";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str5 = "修改失败";
    }
    setResultMsg(paramHttpServletRequest, str5);
    return new ModelAndView("redirect:" + Condition.PATH + "systemConfigController.zcjs?funcflg=viewForward");
  }
  
  public ModelAndView settingSame(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    DataSource localDataSource = localDaoHelper.getDataSource();
    Map localMap = LogonManager.getRMIConfig("3", localDataSource);
    String str = (String)localMap.get("host");
    int i = ((Integer)localMap.get("port")).intValue();
    SystemSettingServerRmi localSystemSettingServerRmi = (SystemSettingServerRmi)Naming.lookup("rmi://" + str + ":" + i + "/SystemSettingServerRmi");
    localSystemSettingServerRmi.reloadFlowMode();
    localSystemSettingServerRmi.reloadRestMsg();
    return new ModelAndView("redirect:" + Condition.PATH + "systemConfigController.zcjs?funcflg=viewForward");
  }
}
