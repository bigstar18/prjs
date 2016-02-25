package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.delivery.model.Status;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.services.StatusService;
import gnnt.MEBS.delivery.services.WarehouseService;
import gnnt.MEBS.delivery.util.SysData;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class EnterInformController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(EnterInformController.class);
  
  public ModelAndView enterInformList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter enterInformList()-------------");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "/enterware/enterInformList");
    try
    {
      QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "to_number(id)", false);
      }
      WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
      int i = ((Integer)localWorkflowFacade.getFinalStatus(enterApplyCommand).get(0)).intValue();
      this.logger.debug("----finalStatus:--" + i);
      if (localQueryConditions1 == null) {
        localQueryConditions1 = new QueryConditions();
      }
      localQueryConditions1.addCondition("ability", "=", Integer.valueOf(i));
      EnterApplyService localEnterApplyService = (EnterApplyService)SysData.getBean("w_enterApplyService");
      List localList1 = localEnterApplyService.getEnterApplyList(localQueryConditions1, localPageInfo);
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList2 = localWarehouseService.getWarehouseList();
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
      List localList3 = localCommodityService.getCommodityList(localQueryConditions2, null);
      StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
      List localList4 = localStatusService.getStatusMap("EnterInform");
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
  
  public ModelAndView enterInformView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter enterInformView()-------------");
    String str1 = paramHttpServletRequest.getParameter("enterInformId");
    EnterApplyService localEnterApplyService = (EnterApplyService)SysData.getBean("w_enterApplyService");
    EnterApply localEnterApply = localEnterApplyService.getEnterApplyById(str1);
    String str2 = localEnterApply.getFirmId();
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(str2);
    String str3 = localEnterApply.getWarehouseId();
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str3, false, false);
    int i = localEnterApply.getInformAbility();
    StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
    Status localStatus = localStatusService.getStatus("EnterInform", i);
    String str4 = localEnterApply.getCommodityId();
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(str4, false);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "/enterware/enterInformView");
    localModelAndView.addObject("operateObj", localEnterApply);
    localModelAndView.addObject("dealer", localDealer);
    localModelAndView.addObject("commodity", localCommodity);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("status", localStatus);
    return localModelAndView;
  }
  
  public ModelAndView enterInformReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterInformController." + Condition.POSTFIX + "?funcflg=enterInformList");
  }
}
