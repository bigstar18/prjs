package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.entity.Breed;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.OperateLogService;
import gnnt.MEBS.delivery.util.SysData;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class CommodityController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(CommodityController.class);
  
  public ModelAndView commodityList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'listCommodity' method...");
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/commodity/commodityList");
    try
    {
      Object localObject = null;
      QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "id", false);
      }
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      List localList = localCommodityService.getCommodityList(localQueryConditions, localPageInfo);
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
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
  
  public ModelAndView commodityAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityAdd' method...");
    String str1 = "";
    Commodity localCommodity = new Commodity();
    ParamUtil.bindData(paramHttpServletRequest, localCommodity);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Breed localBreed = localCommodityService.getEntityBreed(localCommodity.getId());
    localCommodity.setName(localBreed.getBreedName());
    String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
    localCommodity.setOperationId(str2);
    localCommodity.setModifyDate(new Date());
    localCommodity.setCreatetime(new Date());
    ModelAndView localModelAndView = null;
    try
    {
      int i = localCommodityService.addCommondity(localCommodity);
      if (i == -1)
      {
        str1 = "此品种Id已存在，请重新添加";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityAddForward");
      }
      else if (i == -2)
      {
        str1 = "此品种名称已存在，请重新添加";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityAddForward");
      }
      else
      {
        str1 = "添加成功";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityList");
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      str1 = "品种添加失败，请从新添加！";
      localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityAddForward");
      localRuntimeException.printStackTrace();
    }
    setResultMsg(paramHttpServletRequest, str1);
    return localModelAndView;
  }
  
  public ModelAndView commodityAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityAddForward' method...");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    List localList = localCommodityService.getEntityBreedList();
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/commodity/commodityAdd");
    localModelAndView.addObject("entityBreedList", localList);
    return localModelAndView;
  }
  
  public ModelAndView commodityUpdateForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityAddForward' method...");
    String str = paramHttpServletRequest.getParameter("id");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(str);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/commodity/commodityMod");
    localModelAndView.addObject("result", localCommodity);
    return localModelAndView;
  }
  
  public ModelAndView commodityMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityMod' method...");
    String str = "";
    Commodity localCommodity1 = new Commodity();
    ParamUtil.bindData(paramHttpServletRequest, localCommodity1);
    localCommodity1.setModifyDate(new Date());
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    try
    {
      str = "修改成功";
      Commodity localCommodity2 = localCommodityService.getCommodityById(localCommodity1.getId());
      localCommodity1.setName(localCommodity2.getName());
      localCommodityService.updateCommodity(localCommodity1);
    }
    catch (RuntimeException localRuntimeException)
    {
      str = "修改失败";
      localRuntimeException.printStackTrace();
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityList");
  }
  
  public ModelAndView commodityDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = new Commodity();
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    this.logger.debug("---------ids[0]:" + arrayOfString[0]);
    OperateLogService localOperateLogService = (OperateLogService)SysData.getBean("w_operateLogService");
    String str2 = "";
    if (arrayOfString != null) {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        String str3 = arrayOfString[i];
        localCommodity = localCommodityService.getCommodityById(str3);
        localCommodity.setId(str3);
        localCommodity.setAbility(-1);
        try
        {
          OperateLog localOperateLog = new OperateLog();
          localOperateLog.setContent("删除品种，品种ID：" + localCommodity.getId());
          localOperateLog.setUserId(str1);
          localOperateLog.setOperatetime(new Date());
          localOperateLog.setOperation("0");
          localOperateLog.setPopedom(Condition.popedom);
          localCommodityService.deleteCommodity(localCommodity, localOperateLog);
          str2 = "删除成功！";
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          str2 = "删除失败！";
        }
      }
    }
    setResultMsg(paramHttpServletRequest, str2);
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityList");
    return localModelAndView;
  }
  
  public ModelAndView commodityReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityList");
  }
  
  public ModelAndView refurbish(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str = "";
    try
    {
      localCommodityService.refurbish(arrayOfString);
      str = "同步成功，共同步" + arrayOfString.length + "条数据";
    }
    catch (RuntimeException localRuntimeException)
    {
      localRuntimeException.printStackTrace();
      str = "同步失败";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/commodityController." + Condition.POSTFIX + "?funcflg=commodityList");
  }
}
