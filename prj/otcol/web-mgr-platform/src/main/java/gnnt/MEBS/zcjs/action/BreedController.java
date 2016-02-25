package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.zcjs.model.Breed;
import gnnt.MEBS.zcjs.services.BreedService;
import gnnt.MEBS.zcjs.services.SortService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class BreedController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(BreedController.class);
  
  private BreedService getBeanOfBreedService()
  {
    BreedService localBreedService = null;
    synchronized (BreedService.class)
    {
      if (localBreedService == null) {
        localBreedService = (BreedService)SysData.getBean("z_breedService");
      }
    }
    return localBreedService;
  }
  
  private SortService getBeanOfSortService()
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
  
  public ModelAndView list(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'list' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "breedId", false);
    }
    List localList = getBeanOfBreedService().breedTableList(null, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "breed/breedList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView addForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    List localList1 = getBeanOfSortService().getTableList(null, null);
    List localList2 = getBeanOfBreedService().entityBreedList();
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "breed/breedAdd");
    localModelAndView.addObject("sortList", localList1);
    localModelAndView.addObject("breedList", localList2);
    return localModelAndView;
  }
  
  public ModelAndView saveBreed(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    Breed localBreed = new Breed();
    ParamUtil.bindData(paramHttpServletRequest, localBreed);
    String str = "";
    try
    {
      getBeanOfBreedService().breedInsert(localBreed);
      str = "添加成功";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "添加失败，已存在此产品";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "breedController.zcjs" + "?funcflg=list");
  }
  
  public ModelAndView updateBreed(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    Breed localBreed = new Breed();
    ParamUtil.bindData(paramHttpServletRequest, localBreed);
    String str = "";
    try
    {
      getBeanOfBreedService().updateBreed(localBreed);
      str = "修改成功";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "修改失败";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "breedController.zcjs" + "?funcflg=list");
  }
  
  public ModelAndView updateForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    int i = Integer.parseInt(paramHttpServletRequest.getParameter("breedId"));
    Breed localBreed = getBeanOfBreedService().getBreedById(i);
    List localList = getBeanOfSortService().getTableList(null, null);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "breed/breedMod");
    localModelAndView.addObject("breed", localBreed);
    localModelAndView.addObject("sortList", localList);
    return localModelAndView;
  }
  
  public ModelAndView refurbish(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter refurbish========");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString.length; i++) {
      localArrayList.add(Long.valueOf(Long.parseLong(arrayOfString[i])));
    }
    String str = "";
    try
    {
      getBeanOfBreedService().refurbish(localArrayList);
      str = "共同步" + arrayOfString.length + "条数据";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "同步失败";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "breedController.zcjs" + "?funcflg=list");
  }
}
