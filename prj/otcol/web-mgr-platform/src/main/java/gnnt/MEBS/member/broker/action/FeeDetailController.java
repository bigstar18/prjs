package gnnt.MEBS.member.broker.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.member.broker.services.FeeDetailService;
import gnnt.MEBS.member.broker.util.SysData;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.ECSideUtils;
import org.ecside.util.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

public class FeeDetailController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(FeeDetailController.class);
  
  public ModelAndView chiefPage(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Entering 'chiefPage' method");
    ModelAndView localModelAndView = new ModelAndView("member/broker/firmQuery/chiefPage");
    return localModelAndView;
  }
  
  public ModelAndView hisDealDetailList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Entering 'hisDealDetailList' method");
    FeeDetailService localFeeDetailService = (FeeDetailService)SysData.getBean("m_feeDetailService");
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap1 = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    Object localObject = new ArrayList();
    try
    {
      if (j < 0) {
        j = localFeeDetailService.hisDealDetailListCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap1);
      if (j < 0) {
        j = localFeeDetailService.hisDealDetailListCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      localObject = localFeeDetailService.hisDealDetailList(localQueryConditions2, k, m);
    }
    catch (Exception localException)
    {
      System.out.println("------------------出现异常----------------------");
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    Map localMap2 = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("member/broker/firmQuery/hisDealDetailList", "resultList", localObject);
    localModelAndView.addObject("oldParams", localMap2);
    return localModelAndView;
  }
  
  public ModelAndView hisDealDetailZcjsList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Entering 'hisDealDetailZcjsList' method");
    FeeDetailService localFeeDetailService = (FeeDetailService)SysData.getBean("m_feeDetailService");
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap1 = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    Object localObject = new ArrayList();
    try
    {
      if (j < 0) {
        j = localFeeDetailService.hisDealDetailZcjsListCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap1);
      if (j < 0) {
        j = localFeeDetailService.hisDealDetailZcjsListCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      localObject = localFeeDetailService.hisDealDetailZcjsList(localQueryConditions2, k, m);
    }
    catch (Exception localException)
    {
      System.out.println("------------------出现异常----------------------");
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    Map localMap2 = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("member/broker/firmQuery/hisDealDetailZcjsList", "resultList", localObject);
    localModelAndView.addObject("oldParams", localMap2);
    return localModelAndView;
  }
  
  public ModelAndView hisDealDetailVdList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Entering 'hisDealDetailVdList' method");
    FeeDetailService localFeeDetailService = (FeeDetailService)SysData.getBean("m_feeDetailService");
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap1 = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    Object localObject = new ArrayList();
    try
    {
      if (j < 0) {
        j = localFeeDetailService.hisDealDetailVdListCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap1);
      if (j < 0) {
        j = localFeeDetailService.hisDealDetailVdListCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      localObject = localFeeDetailService.hisDealDetailVdList(localQueryConditions2, k, m);
    }
    catch (Exception localException)
    {
      System.out.println("------------------出现异常----------------------");
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    Map localMap2 = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("member/broker/firmQuery/hisDealDetailVdList", "resultList", localObject);
    localModelAndView.addObject("oldParams", localMap2);
    return localModelAndView;
  }
  
  public ModelAndView brokerUserFeeList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Entering 'brokerUserFeeList' method");
    FeeDetailService localFeeDetailService = (FeeDetailService)SysData.getBean("m_feeDetailService");
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    String str1 = paramHttpServletRequest.getParameter("_summarizingWay1");
    String str2 = paramHttpServletRequest.getParameter("_summarizingWay2");
    String str3 = "";
    if ((str1 == null) || (str2 == null) || ("".equals(str1)) || ("".equals(str2)))
    {
      str3 = "summarizingFirmAndSummarizing";
      localMap.put("summarizingWay1", "summarizingFirm");
      localMap.put("summarizingWay2", "Summarizing");
    }
    else
    {
      str3 = str1 + "And" + str2;
    }
    List localList = localFeeDetailService.brokerUserFeeList(localQueryConditions, localPageInfo, str3);
    ModelAndView localModelAndView = new ModelAndView("member/broker/firmQuery/brokerUserfeeList", "resultList", localList);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
}
