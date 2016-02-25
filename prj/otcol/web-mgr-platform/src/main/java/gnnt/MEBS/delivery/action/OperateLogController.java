package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.services.OperateLogService;
import gnnt.MEBS.delivery.util.SysData;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class OperateLogController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(OperateLogController.class);
  
  public ModelAndView operateLogsList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/log/logList");
    try
    {
      OperateLogService localOperateLogService = (OperateLogService)SysData.getBean("w_operateLogService");
      QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "id", false);
      }
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      List localList = localOperateLogService.getOprLogList(localQueryConditions, localPageInfo);
      this.logger.debug("loglist.size:" + localList.size());
      localModelAndView.addObject("resultList", localList);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
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
    String str = paramHttpServletRequest.getParameter("id");
    OperateLogService localOperateLogService = (OperateLogService)SysData.getBean("w_operateLogService");
    OperateLog localOperateLog = localOperateLogService.getOprLogById(str);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/log/logView", "oprLog", localOperateLog);
    return localModelAndView;
  }
  
  public ModelAndView operateLogReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/operateLogController." + Condition.POSTFIX + "?funcflg=operateLogsList");
  }
}
