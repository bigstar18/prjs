package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.util.CommandExecute;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.RegStockService;
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

public class RegStockController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(RegStockController.class);
  private CommandExecute commandExecute;
  
  public CommandExecute getCommandExecute()
  {
    if (this.commandExecute == null) {
      this.commandExecute = ((CommandExecute)SysData.getBean("commandExecute"));
    }
    return this.commandExecute;
  }
  
  public void setCommandExecute(CommandExecute paramCommandExecute)
  {
    this.commandExecute = paramCommandExecute;
  }
  
  public ModelAndView regStockList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/regStockList");
    try
    {
      QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "to_number(regStockId)", false);
      }
      String str = "";
      if (localQueryConditions1 != null)
      {
        str = (String)localQueryConditions1.getConditionValue("weight");
        localQueryConditions1.removeCondition("weight", "=");
      }
      else
      {
        localQueryConditions1 = new QueryConditions();
      }
      if (str != null) {
        if ("0".equals(str)) {
          localQueryConditions1.addCondition("weight", "=", "0");
        } else if ("1".equals(str)) {
          localQueryConditions1.addCondition("weight", ">", "0");
        }
      }
      localQueryConditions1.addCondition("type", "!=", Integer.valueOf(3));
      RegStockService localRegStockService = (RegStockService)SysData.getBean("w_regStockService");
      List localList1 = localRegStockService.getRegStockList(localQueryConditions1, localPageInfo);
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList2 = localWarehouseService.getWarehouseList();
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
      List localList3 = localCommodityService.getCommodityList(localQueryConditions2, null);
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      if ((localMap == null) || (localMap.size() == 0)) {
        localMap.put("weight[=]", Integer.valueOf(-1));
      }
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("regWarehouseNamesList", localList2);
      localModelAndView.addObject("regCommoditysList", localList3);
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
  
  public ModelAndView regStockView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("requestId");
    String str2 = paramHttpServletRequest.getParameter("flag");
    RegStockService localRegStockService = (RegStockService)SysData.getBean("w_regStockService");
    RegStock localRegStock = localRegStockService.getRegStockById(str1);
    double d = Arith.sub(localRegStock.getWeight(), localRegStock.getFrozenWeight());
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/regStockView", "regStock", localRegStock);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(localRegStock.getBreedId() + "", false);
    localModelAndView.addObject("commodity", localCommodity);
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(localRegStock.getFirmId());
    localModelAndView.addObject("dealer", localDealer);
    if (localRegStock.getType() == 0)
    {
      EnterWareService localEnterWareService = null;
      EnterWare localEnterWare = null;
      localEnterWareService = (EnterWareService)SysData.getBean("w_enterWareService");
      localEnterWare = localEnterWareService.getEnterWareById(localRegStock.getStockId());
      localModelAndView.addObject("enterware", localEnterWare);
      WarehouseService localWarehouseService = null;
      Warehouse localWarehouse = null;
      localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      localWarehouse = localWarehouseService.getWarehouseById(localRegStock.getWarehouseId(), false, false);
      localModelAndView.addObject("house", localWarehouse);
    }
    localModelAndView.addObject("availablenum", Double.valueOf(d));
    localModelAndView.addObject("flag", str2);
    localModelAndView.addObject("returnAction", "servlet/regStockController." + Condition.POSTFIX + "?funcflg=regStockReturn");
    return localModelAndView;
  }
  
  public ModelAndView regStockReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:/delivery/servlet/regStockController." + Condition.POSTFIX + "?funcflg=regStockList");
  }
  
  public ModelAndView regStockTurnToEnterWare(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("id");
    RegStockService localRegStockService = (RegStockService)SysData.getBean("w_regStockService");
    RegStock localRegStock = localRegStockService.getRegStockById(str1);
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(localRegStock.getWarehouseId(), false, false);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(localRegStock.getBreedId() + "", false);
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap1 = localWorkflowFacade.getLastMsg(0, Condition.popedom, resStockToEnterWareCommand);
    List localList = (List)localMap1.get("transHandleList");
    Map localMap2 = (Map)localList.get(0);
    String str2 = (String)localMap2.get("value");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "settle/regstock/regStockTurnToEnterWareInfo", "regStock", localRegStock);
    localModelAndView.addObject("value", str2);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("commodity", localCommodity);
    return localModelAndView;
  }
}
