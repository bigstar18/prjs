package gnnt.MEBS.zcjs.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.zcjs.model.CommodityRule;
import gnnt.MEBS.zcjs.services.BreedService;
import gnnt.MEBS.zcjs.services.CommodityRuleService;
import gnnt.MEBS.zcjs.services.FirmPermissionService;
import gnnt.MEBS.zcjs.util.SysData;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class CommodityRuleController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(CommodityRuleController.class);
  
  private CommodityRuleService getBeanOfCommodityRuleService()
  {
    CommodityRuleService localCommodityRuleService = null;
    synchronized (CommodityRuleService.class)
    {
      if (localCommodityRuleService == null) {
        localCommodityRuleService = (CommodityRuleService)SysData.getBean("z_commodityRuleService");
      }
    }
    return localCommodityRuleService;
  }
  
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
  
  private FirmPermissionService getBeanOfFirmPermissionService()
  {
    FirmPermissionService localFirmPermissionService = null;
    synchronized (FirmPermissionService.class)
    {
      if (localFirmPermissionService == null) {
        localFirmPermissionService = (FirmPermissionService)SysData.getBean("z_firmPermissionService");
      }
    }
    return localFirmPermissionService;
  }
  
  public ModelAndView getSysCommodityRule(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter list========");
    CommodityRule localCommodityRule = getBeanOfCommodityRuleService().getObject(1L);
    if (localCommodityRule.getBailMode() == 1) {
      localCommodityRule.setBail(localCommodityRule.getBail() * 100.0D);
    }
    if (localCommodityRule.getTradePoundageMode() == 1) {
      localCommodityRule.setTradePoundage(localCommodityRule.getTradePoundage() * 100.0D);
    }
    if (localCommodityRule.getDeliveryPoundageMode() == 1) {
      localCommodityRule.setDeliveryPoundage(localCommodityRule.getDeliveryPoundage() * 100.0D);
    }
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "CommodityRule/SystemCommodityRule");
    localModelAndView.addObject("commodityRule", localCommodityRule);
    return localModelAndView;
  }
  
  public ModelAndView getList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter list========");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "CommodityRuleId", false);
    }
    if (localQueryConditions == null) {
      localQueryConditions = new QueryConditions();
    }
    localQueryConditions.addCondition("CommodityRuleId", "<>", Long.valueOf(1L));
    List localList = getBeanOfCommodityRuleService().getTableList(localQueryConditions, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "CommodityRule/CommodityRuleList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView addForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    List localList1 = getBeanOfBreedService().breedTableList(null, null);
    List localList2 = getBeanOfFirmPermissionService().getTableList(null, null);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "CommodityRule/addCommodityRule");
    localModelAndView.addObject("breedList", localList1);
    localModelAndView.addObject("firmPermissionList", localList2);
    return localModelAndView;
  }
  
  public ModelAndView add(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    CommodityRule localCommodityRule = new CommodityRule();
    ParamUtil.bindData(paramHttpServletRequest, localCommodityRule);
    String str = "";
    localCommodityRule.setCommodityRuleId(getBeanOfCommodityRuleService().getId());
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("breedId", "=", Long.valueOf(localCommodityRule.getBreedId()));
    localQueryConditions.addCondition("commodityRuleFirmId", "=", localCommodityRule.getCommodityRuleFirmId());
    localQueryConditions.addCondition("CommodityRuleBusinessDirection", "=", localCommodityRule.getCommodityRuleBusinessDirection());
    List localList = getBeanOfCommodityRuleService().getTableList(localQueryConditions, null);
    if ((localList != null) && (localList.size() > 0))
    {
      str = "添加失败，此条信息已存在，请确认！";
    }
    else
    {
      if (localCommodityRule.getBailMode() == 1) {
        localCommodityRule.setBail(localCommodityRule.getBail() / 100.0D);
      }
      if (localCommodityRule.getTradePoundageMode() == 1) {
        localCommodityRule.setTradePoundage(localCommodityRule.getTradePoundage() / 100.0D);
      }
      if (localCommodityRule.getDeliveryPoundageMode() == 1) {
        localCommodityRule.setDeliveryPoundage(localCommodityRule.getDeliveryPoundage() / 100.0D);
      }
      try
      {
        str = "添加成功";
        getBeanOfCommodityRuleService().add(localCommodityRule);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        str = "添加失败！";
      }
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "commodityRuleController.zcjs?funcflg=getList");
  }
  
  public ModelAndView updateForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    long l = Long.parseLong(paramHttpServletRequest.getParameter("commodityRuleId"));
    CommodityRule localCommodityRule = getBeanOfCommodityRuleService().getObject(l);
    if (localCommodityRule.getBailMode() == 1) {
      localCommodityRule.setBail(localCommodityRule.getBail() * 100.0D);
    }
    if (localCommodityRule.getTradePoundageMode() == 1) {
      localCommodityRule.setTradePoundage(localCommodityRule.getTradePoundage() * 100.0D);
    }
    if (localCommodityRule.getDeliveryPoundageMode() == 1) {
      localCommodityRule.setDeliveryPoundage(localCommodityRule.getDeliveryPoundage() * 100.0D);
    }
    List localList1 = getBeanOfBreedService().breedTableList(null, null);
    List localList2 = getBeanOfFirmPermissionService().getTableList(null, null);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "CommodityRule/updateCommodityRule");
    localModelAndView.addObject("commodityRule", localCommodityRule);
    localModelAndView.addObject("breedList", localList1);
    localModelAndView.addObject("firmPermissionList", localList2);
    return localModelAndView;
  }
  
  public ModelAndView update(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    CommodityRule localCommodityRule = new CommodityRule();
    ParamUtil.bindData(paramHttpServletRequest, localCommodityRule);
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("stamp");
    ModelAndView localModelAndView = null;
    if (localCommodityRule.getBailMode() == 1) {
      localCommodityRule.setBail(localCommodityRule.getBail() / 100.0D);
    }
    if (localCommodityRule.getTradePoundageMode() == 1) {
      localCommodityRule.setTradePoundage(localCommodityRule.getTradePoundage() / 100.0D);
    }
    if (localCommodityRule.getDeliveryPoundageMode() == 1) {
      localCommodityRule.setDeliveryPoundage(localCommodityRule.getDeliveryPoundage() / 100.0D);
    }
    if ("sys".equals(str2))
    {
      try
      {
        localCommodityRule.setCommodityRuleBusinessDirection("N");
        localCommodityRule.setCommodityRuleFirmId("-1");
        str1 = "修改成功！";
        getBeanOfCommodityRuleService().update(localCommodityRule);
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        str1 = "修改失败！";
      }
      setResultMsg(paramHttpServletRequest, str1);
      localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "commodityRuleController.zcjs?funcflg=getSysCommodityRule");
    }
    else
    {
      try
      {
        str1 = "修改成功！";
        getBeanOfCommodityRuleService().update(localCommodityRule);
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        str1 = "修改失败！";
      }
      setResultMsg(paramHttpServletRequest, str1);
      localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "commodityRuleController.zcjs?funcflg=getList");
    }
    return localModelAndView;
  }
  
  public ModelAndView delete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str = "";
    try
    {
      str = "删除成功！共删除" + arrayOfString.length + "条数据";
      getBeanOfCommodityRuleService().deleteSome(arrayOfString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "删除失败！";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "commodityRuleController.zcjs?funcflg=getList");
  }
}
