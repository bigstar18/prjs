package gnnt.MEBS.member.firm.web;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.member.firm.services.SystemService;
import gnnt.MEBS.member.firm.unit.TradeModule;
import gnnt.MEBS.member.firm.util.SysData;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

public class SystemController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(SystemController.class);
  
  public ModelAndView listSystem(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'listSystem' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "moduleID", false);
    }
    SystemService localSystemService = (SystemService)SysData.getBean("m_systemService");
    List localList = localSystemService.getSystemList(localQueryConditions, localPageInfo);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("member/system/listSystem", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView setSystem(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'setSystem' method...");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str1 = paramHttpServletRequest.getParameter("type");
    String str2 = "";
    SystemService localSystemService = (SystemService)SysData.getBean("m_systemService");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      TradeModule localTradeModule = localSystemService.getSystem(arrayOfString[i]);
      localTradeModule.setEnabled(str1);
      localSystemService.updateSystem(localTradeModule);
    }
    str2 = "设置成功！";
    ModelAndView localModelAndView = new ModelAndView("member/public/done", "resultMsg", str2);
    return localModelAndView;
  }
}
