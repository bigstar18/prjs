package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.zcjs.model.Sort;
import gnnt.MEBS.zcjs.services.SortService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class SortController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(SortController.class);
  
  private SortService getBeanService()
  {
    SortService localSortService = null;
    synchronized (SortService.class)
    {
      if (localSortService == null) {
        localSortService = (SortService)SysData.getBean("z_sortService");
      }
    }
    return localSortService;
  }
  
  public ModelAndView getTableList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "sortId", false);
    }
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    List localList = getBeanService().getTableList(localQueryConditions, localPageInfo);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "sort/sortList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView updateForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'update' method...");
    String str = paramHttpServletRequest.getParameter("sortId");
    Sort localSort = getBeanService().getObject(Long.parseLong(str));
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "sort/sortMod");
    localModelAndView.addObject("sort", localSort);
    return localModelAndView;
  }
  
  public ModelAndView update(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'update' method...");
    String str = "";
    Sort localSort = new Sort();
    localSort.setSortId(Long.parseLong(paramHttpServletRequest.getParameter("sortId")));
    localSort.setSortName(paramHttpServletRequest.getParameter("sortName"));
    try
    {
      getBeanService().updateSort(localSort);
      str = "修改成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "修改失败！";
    }
    setResultMsg(paramHttpServletRequest, str);
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "sortController.zcjs?funcflg=getTableList");
    return localModelAndView;
  }
  
  public ModelAndView insertForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'insert' method...");
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "sort/sortInsert");
    return localModelAndView;
  }
  
  public ModelAndView insert(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception
  {
    this.logger.debug("entering 'update' method...");
    String str = "";
    Sort localSort = new Sort();
    localSort.setSortId(getBeanService().getId());
    localSort.setSortName(paramHttpServletRequest.getParameter("sortName"));
    try
    {
      getBeanService().insertSort(localSort);
      str = "添加成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "添加失败！";
    }
    setResultMsg(paramHttpServletRequest, str);
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "sortController.zcjs?funcflg=getTableList");
    return localModelAndView;
  }
  
  public ModelAndView delete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter delete========");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str = "";
    try
    {
      getBeanService().delete(arrayOfString);
      str = "删除成功，共删除" + arrayOfString.length + "条数据";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "删除失败！";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "sortController.zcjs" + "?funcflg=getTableList");
  }
}
