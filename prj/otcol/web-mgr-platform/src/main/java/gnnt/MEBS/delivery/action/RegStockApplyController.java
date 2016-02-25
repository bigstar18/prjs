package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.services.WarehouseService;
import gnnt.MEBS.delivery.util.SysData;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class RegStockApplyController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(RegStockApplyController.class);
  
  public ModelAndView regStockApplyList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/regStockApplyList");
    try
    {
      QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "applyId", false);
      }
      String str = paramHttpServletRequest.getParameter("ApplyTime");
      RegStockApplyService localRegStockApplyService = (RegStockApplyService)SysData.getBean("w_regStockApplyService");
      List localList1 = localRegStockApplyService.getRegStockApplyList(localQueryConditions, localPageInfo);
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      List localList2 = localCommodityService.getCommodityListMap();
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList3 = localWarehouseService.getWarehouseList();
      this.logger.debug("//---list大小----regStockApplyList.size()--:" + localList1.size());
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
      paramHttpServletRequest.setAttribute("ApplyTime", str);
      localModelAndView.addObject("breedList", localList2);
      localModelAndView.addObject("warehouseList", localList3);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常");
      localException.printStackTrace();
    }
    this.logger.debug("//---查询所有注册仓单信息------enter regStockApplyList()-------------");
    return localModelAndView;
  }
  
  public ModelAndView regStockApplyView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//---查询单条注册仓单信息------enter regStockApplyView()-------------");
    String str = paramHttpServletRequest.getParameter("id");
    RegStockApplyService localRegStockApplyService = (RegStockApplyService)SysData.getBean("w_regStockApplyService");
    RegStockApply localRegStockApply = localRegStockApplyService.getRegStockApplyById(str);
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(localRegStockApply.getWarehouseId(), false, false);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(localRegStockApply.getBreedId() + "", false);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap = localWorkflowFacade.getLastMsg(localRegStockApply.getCurrentStatus(), Condition.popedom, resStockApplyCommand);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/regStockApplyView", "operateObj", localRegStockApply);
    localModelAndView.addObject("msgMap", localMap);
    localModelAndView.addObject("commodity", localCommodity);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("submitAction", "servlet/regStockApplyController." + Condition.POSTFIX + "?funcflg=regStockApplyDealWith");
    localModelAndView.addObject("returnAction", "servlet/regStockApplyController." + Condition.POSTFIX + "?funcflg=regStockApplyReturn");
    return localModelAndView;
  }
  
  public ModelAndView addRegStockApply(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter addRegStockApply-------------");
    RegStockApply localRegStockApply = new RegStockApply();
    String str1 = paramHttpServletRequest.getParameter("operate");
    ParamUtil.bindData(paramHttpServletRequest, localRegStockApply);
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    localRegStockApply.setApplicant(str2);
    localRegStockApply.setApplyTime(new Date());
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localRegStockApply);
    localOriginalModel.setOperate(str1);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str3 = localWorkflowFacade.getLogContent(str1);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str3);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("1");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, resStockApplyCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/regStockApplyController." + Condition.POSTFIX + "?funcflg=addStandardregStockApplyForward&noReturn=1");
  }
  
  public ModelAndView regStockApplyDealWith(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str2 = paramHttpServletRequest.getParameter("applyId");
    String str3 = paramHttpServletRequest.getParameter("isDoSome");
    String str4 = paramHttpServletRequest.getParameter("note");
    RegStockApplyService localRegStockApplyService = (RegStockApplyService)SysData.getBean("w_regStockApplyService");
    RegStockApply localRegStockApply = localRegStockApplyService.getRegStockApplyById(str2);
    if ((str4 != null) && (!"".equals(str4))) {
      localRegStockApply.setRejectedReasons(str4);
    }
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str5 = localWorkflowFacade.getLogContent(str3);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str5);
    localOperateLog.setUserId(str1);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setPopedom(Condition.popedom);
    localOperateLog.setBillId(str2);
    localOperateLog.setOperation("1");
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setOperate(str3);
    localOriginalModel.setLog(localOperateLog);
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localRegStockApply);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, resStockApplyCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/regStockApplyController." + Condition.POSTFIX + "?funcflg=regStockApplyList");
  }
  
  public ModelAndView addnonStandardregStockApply(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter addnonStandardregStockApply-------------");
    RegStockApply localRegStockApply = new RegStockApply();
    ParamUtil.bindData(paramHttpServletRequest, localRegStockApply);
    localRegStockApply.setApplicant(AclCtrl.getLogonID(paramHttpServletRequest));
    String str1 = paramHttpServletRequest.getParameter("operate");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    localRegStockApply.setApplyTime(new Date());
    this.logger.debug("regStockApply.type:" + localRegStockApply.getType());
    this.logger.debug("regStockApply.breedid:" + localRegStockApply.getBreedId());
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localRegStockApply);
    localOriginalModel.setOperate(str1);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str3 = localWorkflowFacade.getLogContent(str1);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str3);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("1");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, resStockApplyCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/regStockApplyController." + Condition.POSTFIX + "?funcflg=addnonStandardregStockApplyForward&noReturn=1&flag=" + localRegStockApply.getType());
  }
  
  public ModelAndView addStandardregStockApplyForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap1 = localWorkflowFacade.getLastMsg(0, Condition.popedom, resStockApplyCommand);
    List localList = (List)localMap1.get("transHandleList");
    Map localMap2 = (Map)localList.get(0);
    String str = (String)localMap2.get("value");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/regStockAdd");
    localModelAndView.addObject("value", str);
    return localModelAndView;
  }
  
  public ModelAndView addnonStandardregStockApplyForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter addnonStandardregStockApplyForward-------------");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    List localList1 = localCommodityService.getCommodityListMap();
    int i = 1;
    if (paramHttpServletRequest.getParameter("flag") != null) {
      i = Integer.parseInt(paramHttpServletRequest.getParameter("flag"));
    }
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap1 = localWorkflowFacade.getLastMsg(0, Condition.popedom, resStockApplyCommand);
    List localList2 = (List)localMap1.get("transHandleList");
    Map localMap2 = (Map)localList2.get(0);
    String str = (String)localMap2.get("value");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/nonStandardregStock");
    localModelAndView.addObject("commodityList", localList1);
    localModelAndView.addObject("type", Integer.valueOf(i));
    localModelAndView.addObject("value", str);
    return localModelAndView;
  }
  
  public ModelAndView regStockApplyReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:/delivery/servlet/regStockApplyController." + Condition.POSTFIX + "?funcflg=regStockApplyList");
  }
}
