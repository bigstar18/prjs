package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.Status;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.services.StatusService;
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

public class EnterApplyController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(EnterApplyController.class);
  
  public ModelAndView enterApplyList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter enterApplyList()-------------");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "enterware/enterApplyList");
    try
    {
      QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "to_number(id)", false);
      }
      EnterApplyService localEnterApplyService = (EnterApplyService)SysData.getBean("w_enterApplyService");
      List localList1 = localEnterApplyService.getEnterApplyList(localQueryConditions1, localPageInfo);
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList2 = localWarehouseService.getWarehouseList();
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
      List localList3 = localCommodityService.getCommodityList(localQueryConditions2, null);
      StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
      List localList4 = localStatusService.getStatusMap("EnterApply");
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
      localModelAndView.addObject("commodityList", localList3);
      localModelAndView.addObject("warehouseList", localList2);
      localModelAndView.addObject("statusList", localList4);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常！");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView enterApplyForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter enterApplyforward()-------------");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    List localList1 = localCommodityService.getCommodityListMap();
    List localList2 = localWarehouseService.getWarehouseList();
    this.logger.debug("commodityList.size():" + localList1.size());
    this.logger.debug("warehouseList.size():" + localList2.size());
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap1 = localWorkflowFacade.getLastMsg(0, Condition.popedom, enterApplyCommand);
    List localList3 = (List)localMap1.get("transHandleList");
    Map localMap2 = (Map)localList3.get(0);
    String str = (String)localMap2.get("value");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "enterware/enterApplyAdd");
    localModelAndView.addObject("warehouseList", localList2);
    localModelAndView.addObject("commodityList", localList1);
    localModelAndView.addObject("value", str);
    return localModelAndView;
  }
  
  public ModelAndView enterApplyAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterApplyAdd========");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str2 = paramHttpServletRequest.getParameter("operate");
    EnterApply localEnterApply = new EnterApply();
    ParamUtil.bindData(paramHttpServletRequest, localEnterApply);
    localEnterApply.setCreateDate(new Date());
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localEnterApply);
    localOriginalModel.setOperate(str2);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str3 = localWorkflowFacade.getLogContent(str2);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str3);
    localOperateLog.setUserId(str1);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("1");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, enterApplyCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterApplyController." + Condition.POSTFIX + "?funcflg=enterApplyList");
  }
  
  public ModelAndView enterApplyView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterApplyView========");
    String str1 = paramHttpServletRequest.getParameter("enterApplyId");
    EnterApplyService localEnterApplyService = (EnterApplyService)SysData.getBean("w_enterApplyService");
    EnterApply localEnterApply = localEnterApplyService.getEnterApplyById(str1);
    String str2 = localEnterApply.getFirmId();
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(str2);
    String str3 = localEnterApply.getWarehouseId();
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str3, false, false);
    int i = localEnterApply.getAbility();
    StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
    Status localStatus = localStatusService.getStatus("EnterApply", i);
    String str4 = localEnterApply.getCommodityId();
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(str4, false);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap = localWorkflowFacade.getLastMsg(localEnterApply.getCurrentStatus(), Condition.popedom, enterApplyCommand);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "enterware/enterApplyView", "operateObj", localEnterApply);
    localModelAndView.addObject("commodity", localCommodity);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("dealer", localDealer);
    localModelAndView.addObject("status", localStatus);
    localModelAndView.addObject("msgMap", localMap);
    localModelAndView.addObject("submitAction", "servlet/enterApplyController." + Condition.POSTFIX + "?funcflg=enterApplyDealWith");
    localModelAndView.addObject("returnAction", "servlet/enterApplyController." + Condition.POSTFIX + "?funcflg=enterApplyReturn");
    return localModelAndView;
  }
  
  public ModelAndView enterApplyDealWith(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterApplyView========");
    String str1 = paramHttpServletRequest.getParameter("enterApplyId");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str3 = paramHttpServletRequest.getParameter("isDoSome");
    String str4 = paramHttpServletRequest.getParameter("note");
    EnterApplyService localEnterApplyService = (EnterApplyService)SysData.getBean("w_enterApplyService");
    EnterApply localEnterApply = localEnterApplyService.getEnterApplyById(str1);
    if ((str4 != null) && (!"".equals(str4))) {
      localEnterApply.setRejectedReasons(str4);
    }
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str5 = localWorkflowFacade.getLogContent(str3);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str5);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setPopedom(Condition.popedom);
    localOperateLog.setBillId(str1);
    localOperateLog.setOperation("1");
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setLog(localOperateLog);
    localOriginalModel.setOperate(str3);
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localEnterApply);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, enterApplyCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterApplyController.wha?funcflg=enterApplyList");
  }
  
  public ModelAndView enterApplyReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterApplyController." + Condition.POSTFIX + "?funcflg=enterApplyList");
  }
}
