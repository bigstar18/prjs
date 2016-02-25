package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.zcjs.services.CommodityPropertyService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class CommodityPropertyController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(CommodityPropertyController.class);
  
  private CommodityPropertyService getBeanOfCommodityPropertyService()
  {
    CommodityPropertyService localCommodityPropertyService = null;
    synchronized (CommodityPropertyService.class)
    {
      if (localCommodityPropertyService == null) {
        localCommodityPropertyService = (CommodityPropertyService)SysData.getBean("z_commodityPropertyService");
      }
    }
    return localCommodityPropertyService;
  }
  
  public ModelAndView list(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter list========");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "PropertyId", false);
    }
    List localList = getBeanOfCommodityPropertyService().getTableList(localQueryConditions, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "commodityProperty/commodityPropertyList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView mod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter mod========");
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("type");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      long l = Long.parseLong(arrayOfString[i]);
      localArrayList.add(Long.valueOf(l));
    }
    try
    {
      getBeanOfCommodityPropertyService().update(localArrayList, str2);
      str1 = "修改成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = "修改失败";
    }
    setResultMsg(paramHttpServletRequest, str1);
    return new ModelAndView("redirect:" + Condition.PATH + "commodityPropertyController.zcjs" + "?funcflg=list");
  }
  
  public ModelAndView listReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "commodityPropertyController.zcjs?funcflg=list");
  }
}
