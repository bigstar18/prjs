package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.LogValue;
import gnnt.MEBS.delivery.services.LogService;
import gnnt.MEBS.delivery.util.SysData;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class SettleLogController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(SettleLogController.class);
  
  public ModelAndView operateLogsList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//-----查询所有交收日志------enter listOperateLogs()------");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/log/logList");
    try
    {
      LogService localLogService = (LogService)SysData.getBean("w_logService");
      QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      String str = paramHttpServletRequest.getParameter("operatime");
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "operatime", false);
      }
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      List localList = localLogService.getLogList(localQueryConditions, localPageInfo, str);
      localModelAndView.addObject("resultList", localList);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
      localModelAndView.addObject("operatime", str);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView operateLogView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//-----查看单条日志信息------enter viewOperateLog()------");
    String str = paramHttpServletRequest.getParameter("id");
    LogService localLogService = (LogService)SysData.getBean("w_logService");
    LogValue localLogValue = localLogService.getLogById(str);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/log/logView", "oprLog", localLogValue);
    return localModelAndView;
  }
  
  public ModelAndView operateLogReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:/delivery/servlet/settleLogController." + Condition.POSTFIX + "?funcflg=operateLogsList");
  }
}
