package gnnt.MEBS.member.firm.web;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.member.firm.services.ErrorLoginLogService;
import gnnt.MEBS.member.firm.util.SysData;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ErrorLoginLogController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(ErrorLoginLogController.class);
  
  public ModelAndView getErrorLoginLogList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getErrorLoginLogList' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "traderId", false);
    }
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ErrorLoginLogService localErrorLoginLogService = (ErrorLoginLogService)SysData.getBean("m_errorLoginLogService");
    List localList = localErrorLoginLogService.getTableList(localQueryConditions, localPageInfo);
    ModelAndView localModelAndView = new ModelAndView("member/errorLoginLog/errorLoginLogList", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView delete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getErrorLoginLogList' method...");
    ErrorLoginLogService localErrorLoginLogService = (ErrorLoginLogService)SysData.getBean("m_errorLoginLogService");
    String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
    String str2 = "";
    String str3 = paramHttpServletRequest.getParameter("manyOrAll");
    try
    {
      if ("many".equals(str3))
      {
        String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
        localErrorLoginLogService.delete(arrayOfString);
      }
      else
      {
        localErrorLoginLogService.delete(null);
      }
      str2 = "恢复成功！";
    }
    catch (RuntimeException localRuntimeException)
    {
      localRuntimeException.printStackTrace();
      str2 = "恢复失败！";
    }
    paramHttpServletRequest.setAttribute("msg", str2);
    return getErrorLoginLogList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView getErrorLoginLogMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getErrorLoginLog' method...");
    String str = paramHttpServletRequest.getParameter("tradeId");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "tradeId", false);
    }
    ErrorLoginLogService localErrorLoginLogService = (ErrorLoginLogService)SysData.getBean("m_errorLoginLogService");
    System.out.println(str);
    List localList = null;
    try
    {
      localList = localErrorLoginLogService.getErrorLoginById(str);
    }
    catch (RuntimeException localRuntimeException)
    {
      localRuntimeException.printStackTrace();
    }
    ModelAndView localModelAndView = new ModelAndView("member/errorLoginLog/errorLoginLogMod", "resultList", localList);
    System.out.println(localList.size());
    localModelAndView.addObject("pageInfo", localPageInfo);
    return localModelAndView;
  }
}
