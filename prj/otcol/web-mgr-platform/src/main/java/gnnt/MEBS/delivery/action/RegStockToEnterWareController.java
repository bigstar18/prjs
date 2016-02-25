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
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.RegStockToEnterWareService;
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
import org.springframework.web.servlet.ModelAndView;

public class RegStockToEnterWareController
  extends BaseController
{
  public ModelAndView regStockToEnterWareList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter regStockList-------------");
    ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "regstock/regStockToEnterWareList");
    try
    {
      QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      if (localQueryConditions1 == null) {
        localQueryConditions1 = new QueryConditions();
      }
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "regStockId", false);
      }
      RegStockToEnterWareService localRegStockToEnterWareService = (RegStockToEnterWareService)SysData.getBean("w_regStockToEnterWareService");
      List localList1 = localRegStockToEnterWareService.getRegStockToEnterWareList(localQueryConditions1, localPageInfo);
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList2 = localWarehouseService.getWarehouseList();
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
      List localList3 = localCommodityService.getCommodityList(localQueryConditions2, null);
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("warehouseList", localList2);
      localModelAndView.addObject("commodityList", localList3);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常！");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView regStockToEnterWareforward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter regStockToEnterWareforward()-------------");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    List localList1 = localCommodityService.getCommodityListMap();
    List localList2 = localWarehouseService.getWarehouseList();
    this.logger.debug("commodityList.size():" + localList1.size());
    this.logger.debug("warehouseList.size():" + localList2.size());
    ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "regstock/regStockToEnterWareAdd");
    localModelAndView.addObject("warehouseList", localList2);
    localModelAndView.addObject("commodityList", localList1);
    return localModelAndView;
  }
  
  public ModelAndView regStockToEnterWareAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter regStockToEnterWareAdd========");
    String str1 = paramHttpServletRequest.getParameter("operate");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str3 = paramHttpServletRequest.getParameter("turnToWeight");
    double d = 0.0D;
    if ((str3 != null) && (!"".equals(str3))) {
      d = Double.parseDouble(str3);
    }
    RegStockToEnterWare localRegStockToEnterWare = new RegStockToEnterWare();
    ParamUtil.bindData(paramHttpServletRequest, localRegStockToEnterWare);
    localRegStockToEnterWare.setCreateDate(new Date());
    localRegStockToEnterWare.setModifyTime(new Date());
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localRegStockToEnterWare);
    localOriginalModel.setOperate(str1);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str4 = localWorkflowFacade.getLogContent(str1);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str4);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("2");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, resStockToEnterWareCommand);
    paramHttpServletRequest.setAttribute("resultMsg", getValueFromResult(localMap));
    return regStockToEnterWareList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView regStockToEnterWareView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter regStockToEnterWareView========");
    String str1 = paramHttpServletRequest.getParameter("id");
    RegStockToEnterWareService localRegStockToEnterWareService = (RegStockToEnterWareService)SysData.getBean("w_regStockToEnterWareService");
    RegStockToEnterWare localRegStockToEnterWare = localRegStockToEnterWareService.getRegStockToEnterWareById(str1);
    String str2 = localRegStockToEnterWare.getFirmId();
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(str2);
    String str3 = localRegStockToEnterWare.getWarehouseId();
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str3, false, false);
    int i = localRegStockToEnterWare.getStatus();
    StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
    Status localStatus = localStatusService.getStatus("EnterWare", i);
    String str4 = localRegStockToEnterWare.getBreedId();
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(str4, false);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap = localWorkflowFacade.getLastMsg(localRegStockToEnterWare.getCurrentStatus(), Condition.popedom, resStockToEnterWareCommand);
    ModelAndView localModelAndView = new ModelAndView(Condition.SETTLEPATH + "regstock/regStockToEnterWareView", "operateObj", localRegStockToEnterWare);
    localModelAndView.addObject("commodity", localCommodity);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("dealer", localDealer);
    localModelAndView.addObject("status", localStatus);
    localModelAndView.addObject("msgMap", localMap);
    localModelAndView.addObject("submitAction", "servlet/regStockToEnterWareController." + Condition.POSTFIX + "?funcflg=regStockToEnterWareDealWith");
    localModelAndView.addObject("returnAction", "servlet/regStockToEnterWareController." + Condition.POSTFIX + "?funcflg=regStockToEnterWareReturn");
    return localModelAndView;
  }
  
  public ModelAndView regStockToEnterWareDealWith(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterWareDealWith========");
    String str1 = paramHttpServletRequest.getParameter("id");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str3 = paramHttpServletRequest.getParameter("isDoSome");
    String str4 = paramHttpServletRequest.getParameter("note");
    RegStockToEnterWareService localRegStockToEnterWareService = (RegStockToEnterWareService)SysData.getBean("w_regStockToEnterWareService");
    RegStockToEnterWare localRegStockToEnterWare = localRegStockToEnterWareService.getRegStockToEnterWareById(str1);
    localRegStockToEnterWare.setModifyTime(new Date());
    if ((str4 != null) && (!"".equals(str4))) {
      localRegStockToEnterWare.setRejectedReasons(str4);
    }
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str5 = localWorkflowFacade.getLogContent(str3);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setOperate(str3);
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localRegStockToEnterWare);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str5);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("2");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, resStockToEnterWareCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/regStockToEnterWareController.wha?funcflg=regStockToEnterWareList");
  }
  
  public ModelAndView regStockToEnterWareReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/regStockToEnterWareController." + Condition.POSTFIX + "?funcflg=regStockToEnterWareList");
  }
}
