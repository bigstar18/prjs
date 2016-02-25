package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.zcjs.model.Broadcast;
import gnnt.MEBS.zcjs.services.BroadcastService;
import gnnt.MEBS.zcjs.util.SysData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class BroadcastController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(BroadcastController.class);
  
  private BroadcastService getBeanOfBroadcastService()
  {
    BroadcastService localBroadcastService = null;
    synchronized (BroadcastService.class)
    {
      if (localBroadcastService == null) {
        localBroadcastService = (BroadcastService)SysData.getBean("z_broadcastService");
      }
    }
    return localBroadcastService;
  }
  
  public ModelAndView list(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "broadcastid", false);
    }
    List localList = getBeanOfBroadcastService().getTableList(localQueryConditions, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "broadcast/broadcastList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView view(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'view' method...");
    Broadcast localBroadcast = new Broadcast();
    long l = Long.parseLong(paramHttpServletRequest.getParameter("id"));
    localBroadcast = getBeanOfBroadcastService().getObjectById(l);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "broadcast/broadcastUpdate");
    localModelAndView.addObject("broadcast", localBroadcast);
    return localModelAndView;
  }
  
  public ModelAndView update(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter update========");
    String str1 = "";
    try
    {
      String str2 = paramHttpServletRequest.getParameter("broadcastSendTime");
      Broadcast localBroadcast = new Broadcast();
      ParamUtil.bindData(paramHttpServletRequest, localBroadcast);
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date localDate = localSimpleDateFormat.parse(str2);
      localBroadcast.setBroadcastSendTime(localDate);
      getBeanOfBroadcastService().update(localBroadcast);
      str1 = "修改成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = "修改失败";
    }
    setResultMsg(paramHttpServletRequest, str1);
    return new ModelAndView("redirect:" + Condition.PATH + "broadcastController.zcjs?funcflg=list");
  }
  
  public ModelAndView listReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "broadcastController.zcjs?funcflg=list");
  }
  
  public ModelAndView addForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter addForward()-------------");
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "broadcast/broadcastAdd");
    localModelAndView.addObject("operator", str);
    return localModelAndView;
  }
  
  public ModelAndView add(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter add========");
    String str1 = paramHttpServletRequest.getParameter("broadcastSendTime");
    Broadcast localBroadcast = new Broadcast();
    ParamUtil.bindData(paramHttpServletRequest, localBroadcast);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date localDate = localSimpleDateFormat.parse(str1);
    localBroadcast.setBroadcastSendTime(localDate);
    localBroadcast.setBroadcastSender(AclCtrl.getLogonID(paramHttpServletRequest));
    String str2 = "";
    try
    {
      getBeanOfBroadcastService().add(localBroadcast);
      str2 = "添加成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str2 = "添加失败！";
    }
    setResultMsg(paramHttpServletRequest, str2);
    return new ModelAndView("redirect:" + Condition.PATH + "broadcastController.zcjs?funcflg=list");
  }
  
  public ModelAndView delForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter delForward========");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    Broadcast localBroadcast = new Broadcast();
    String str = "";
    try
    {
      if (arrayOfString != null) {
        for (int i = 0; i < arrayOfString.length; i++)
        {
          Long localLong = Long.valueOf(Long.parseLong(arrayOfString[i]));
          localBroadcast.setBroadcastId(localLong.longValue());
          getBeanOfBroadcastService().delete(localBroadcast);
        }
      }
      str = "删除成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "删除失败！";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "broadcastController.zcjs?funcflg=list");
  }
}
