package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.zcjs.model.TradeTime;
import gnnt.MEBS.zcjs.rmi.SystemSettingServerRmi;
import gnnt.MEBS.zcjs.services.TradeTimeService;
import gnnt.MEBS.zcjs.util.SysData;
import java.rmi.Naming;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class TradeTimeController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(TradeTimeController.class);
  
  private TradeTimeService getBeanOfTradetimeService()
  {
    TradeTimeService localTradeTimeService = null;
    synchronized (TradeTimeService.class)
    {
      if (localTradeTimeService == null) {
        localTradeTimeService = (TradeTimeService)SysData.getBean("z_tradeTimeService");
      }
    }
    return localTradeTimeService;
  }
  
  public ModelAndView tradeTimeList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "startTime", false);
    }
    List localList = getBeanOfTradetimeService().getTableList(null, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "tradetime/tradeTimeList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView tradeTimeView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'view' method...");
    int i = Integer.parseInt(paramHttpServletRequest.getParameter("serialNumber"));
    TradeTime localTradeTime = getBeanOfTradetimeService().getObject(i);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "tradetime/tradetimeMod");
    localModelAndView.addObject("tradeTime", localTradeTime);
    return localModelAndView;
  }
  
  public ModelAndView tradeTimeMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'mod' method......");
    TradeTime localTradeTime = new TradeTime();
    ParamUtil.bindData(paramHttpServletRequest, localTradeTime);
    String str = "";
    try
    {
      getBeanOfTradetimeService().update(localTradeTime);
      str = "修改成功";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = " 修改失败";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "tradeTimeController.zcjs?funcflg=tradeTimeList");
  }
  
  public ModelAndView tradeTimeAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter add========");
    TradeTime localTradeTime1 = new TradeTime();
    ParamUtil.bindData(paramHttpServletRequest, localTradeTime1);
    String str = "";
    List localList = getBeanOfTradetimeService().getObjectList(null, null);
    TradeTime localTradeTime2 = null;
    int i = 0;
    if ((localList != null) && (localList.size() > 0)) {
      for (int j = 0; j < localList.size(); j++)
      {
        localTradeTime2 = (TradeTime)localList.get(j);
        if (getBeanOfTradetimeService().isCross(localTradeTime1.getStartTime(), localTradeTime1.getEndTime(), localTradeTime2.getStartTime(), localTradeTime2.getEndTime())) {
          i = 1;
        }
      }
    }
    if (i != 0) {
      str = "交易节不能交叉，请重新添加！";
    } else {
      try
      {
        getBeanOfTradetimeService().add(localTradeTime1);
        str = "添加成功";
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        str = "添加失败";
      }
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "tradeTimeController.zcjs?funcflg=tradeTimeList");
  }
  
  public ModelAndView delForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter del========");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    int[] arrayOfInt = new int[20];
    for (int i = 0; i < arrayOfString.length; i++) {
      arrayOfInt[i] = Integer.parseInt(arrayOfString[i]);
    }
    String str = "";
    try
    {
      getBeanOfTradetimeService().delete(arrayOfInt);
      str = "共删除" + arrayOfString.length + "条数据";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "删除失败失败";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "tradeTimeController.zcjs?funcflg=tradeTimeList");
  }
  
  public ModelAndView addForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "tradetime/tradetimeAdd");
    return localModelAndView;
  }
  
  public ModelAndView settingTradeTimeSame(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    DataSource localDataSource = localDaoHelper.getDataSource();
    Map localMap = LogonManager.getRMIConfig("3", localDataSource);
    String str = (String)localMap.get("host");
    int i = ((Integer)localMap.get("port")).intValue();
    SystemSettingServerRmi localSystemSettingServerRmi = (SystemSettingServerRmi)Naming.lookup("rmi://" + str + ":" + i + "/SystemSettingServerRmi");
    localSystemSettingServerRmi.reloadTradeTimes();
    return new ModelAndView("redirect:" + Condition.PATH + "tradeTimeController.zcjs?funcflg=tradeTimeList");
  }
}
