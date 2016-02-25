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
import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.OutWareService;
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

public class OutWareController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(WarehouseController.class);
  
  public ModelAndView outWaresList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Outing 'outWaresList' method...");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "outware/outWareList");
    try
    {
      QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "id", false);
      }
      OutWareService localOutWareService = (OutWareService)SysData.getBean("w_outWareService");
      List localList1 = localOutWareService.getOutWareList(localQueryConditions1, localPageInfo);
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList2 = localWarehouseService.getWarehouseList();
      StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
      List localList3 = localStatusService.getStatusMap("OutWare");
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
      List localList4 = localCommodityService.getCommodityList(localQueryConditions2, null);
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
      localModelAndView.addObject("commodityList", localList4);
      localModelAndView.addObject("warehouseList", localList2);
      localModelAndView.addObject("statusList", localList3);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView outWareInfoView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("Outing 'viewOutWareInfo' method...");
    String str = paramHttpServletRequest.getParameter("requestId");
    OutWareService localOutWareService = (OutWareService)SysData.getBean("w_outWareService");
    OutWare localOutWare = localOutWareService.getOutWareById(str);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "outware/outWareView", "operateObj", localOutWare);
    StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
    Status localStatus = localStatusService.getStatus("OutWare", localOutWare.getAbility());
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(localOutWare.getWarehouseId(), false, false);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(localOutWare.getCommodityId(), false);
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(localOutWare.getFirmId());
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap = localWorkflowFacade.getLastMsg(localOutWare.getCurrentStatus(), Condition.popedom, outWareCommand);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("commodity", localCommodity);
    localModelAndView.addObject("dealer", localDealer);
    localModelAndView.addObject("status", localStatus);
    localModelAndView.addObject("msgMap", localMap);
    localModelAndView.addObject("submitAction", "servlet/outWareController." + Condition.POSTFIX + "?funcflg=outWareDealWith");
    localModelAndView.addObject("returnAction", "servlet/outWareController." + Condition.POSTFIX + "?funcflg=outWareReturn");
    return localModelAndView;
  }
  
  public ModelAndView outWareForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap1 = localWorkflowFacade.getLastMsg(0, Condition.popedom, outWareCommand);
    List localList = (List)localMap1.get("transHandleList");
    Map localMap2 = (Map)localList.get(0);
    String str = (String)localMap2.get("value");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "outware/outWareAdd");
    localModelAndView.addObject("value", str);
    return localModelAndView;
  }
  
  public ModelAndView outWareAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("operate");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    this.logger.debug("operate::" + str1);
    OutWare localOutWare = new OutWare();
    ParamUtil.bindData(paramHttpServletRequest, localOutWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localOutWare);
    localOriginalModel.setOperate(str1);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str3 = localWorkflowFacade.getLogContent(str1);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str3);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("3");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, outWareCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/outWareController." + Condition.POSTFIX + "?funcflg=outWaresList");
  }
  
  public ModelAndView outWareDealWith(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("id");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str3 = paramHttpServletRequest.getParameter("isDoSome");
    String str4 = paramHttpServletRequest.getParameter("note");
    OutWareService localOutWareService = (OutWareService)SysData.getBean("w_outWareService");
    OutWare localOutWare = localOutWareService.getOutWareById(str1);
    if ((str4 != null) && (!"".equals(str4))) {
      localOutWare.setRejectedReasons(str4);
    }
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str5 = localWorkflowFacade.getLogContent(str3);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str5);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setPopedom(Condition.popedom);
    localOperateLog.setBillId(str1);
    localOperateLog.setOperation("3");
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setLog(localOperateLog);
    localOriginalModel.setOperate(str3);
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localOutWare);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, outWareCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:/delivery/servlet/outWareController.wha?funcflg=outWaresList");
  }
  
  public ModelAndView outWareReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:/delivery/servlet/outWareController." + Condition.POSTFIX + "?funcflg=outWaresList");
  }
}
