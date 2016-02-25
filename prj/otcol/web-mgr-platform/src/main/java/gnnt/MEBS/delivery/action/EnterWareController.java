package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.delivery.model.KeyValue;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.Status;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.StatusService;
import gnnt.MEBS.delivery.services.WarehouseService;
import gnnt.MEBS.delivery.util.SysData;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class EnterWareController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(EnterWareController.class);
  
  public ModelAndView enterWareList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter enterWareList()-------------");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "enterware/enterWareList");
    try
    {
      QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "to_number(id)", false);
      }
      String str = "";
      if (localQueryConditions1 != null)
      {
        str = (String)localQueryConditions1.getConditionValue("existAmount");
        localQueryConditions1.removeCondition("existAmount", "=");
      }
      else
      {
        localQueryConditions1 = new QueryConditions();
      }
      if (str != null) {
        if ("0".equals(str)) {
          localQueryConditions1.addCondition("existAmount", "=", "0");
        } else if ("1".equals(str)) {
          localQueryConditions1.addCondition("existAmount", ">", "0");
        }
      }
      EnterWareService localEnterWareService = (EnterWareService)SysData.getBean("w_enterWareService");
      List localList1 = localEnterWareService.getEnterWareList(localQueryConditions1, localPageInfo);
      this.logger.debug("list: " + localList1.size());
      StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
      List localList2 = localStatusService.getStatusMap("EnterWare");
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList3 = localWarehouseService.getWarehouseList();
      CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
      QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
      List localList4 = localCommodityService.getCommodityList(localQueryConditions2, null);
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
      localModelAndView.addObject("statusList", localList2);
      localModelAndView.addObject("commodityList", localList4);
      localModelAndView.addObject("warehouseList", localList3);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常！");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView enterWareForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter enterWareforward()-------------");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    List localList1 = localCommodityService.getCommodityListMap();
    List localList2 = localWarehouseService.getWarehouseList();
    this.logger.debug("commodityList.size():" + localList1.size());
    this.logger.debug("warehouseList.size():" + localList2.size());
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap1 = localWorkflowFacade.getLastMsg(0, Condition.popedom, enterWareCommand);
    List localList3 = (List)localMap1.get("transHandleList");
    Map localMap2 = (Map)localList3.get(0);
    String str = (String)localMap2.get("value");
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "enterware/enterWareAdd");
    localModelAndView.addObject("warehouseList", localList2);
    localModelAndView.addObject("commodityList", localList1);
    localModelAndView.addObject("value", str);
    return localModelAndView;
  }
  
  public ModelAndView enterWareAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterWareAdd========");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str2 = paramHttpServletRequest.getParameter("operate");
    EnterWare localEnterWare = new EnterWare();
    ParamUtil.bindData(paramHttpServletRequest, localEnterWare);
    localEnterWare.setCreateDate(new Date());
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("qualityParameter");
    String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("qualityName");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString2.length; i++)
    {
      localObject = new KeyValue();
      if ((arrayOfString1[i].equals("")) || (arrayOfString1[i] == null)) {
        arrayOfString1[i] = "null";
      }
      ((KeyValue)localObject).setKey(arrayOfString2[i]);
      ((KeyValue)localObject).setValue(arrayOfString1[i]);
      localArrayList.add(localObject);
      this.logger.debug("key:" + arrayOfString2[i]);
      this.logger.debug("value:" + arrayOfString1[i]);
    }
    localEnterWare.addQualityStandardList(localArrayList);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localEnterWare);
    localOriginalModel.setOperate(str2);
    Object localObject = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str3 = ((WorkflowFacade)localObject).getLogContent(str2);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str3);
    localOperateLog.setUserId(str1);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("2");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = ((WorkflowFacade)localObject).dealWorkflow(localOriginalModel, enterWareCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterWareController." + Condition.POSTFIX + "?funcflg=enterWareList");
  }
  
  public ModelAndView enterWareView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterWareView========");
    String str1 = paramHttpServletRequest.getParameter("enterWareId");
    EnterWareService localEnterWareService = (EnterWareService)SysData.getBean("w_enterWareService");
    EnterWare localEnterWare = localEnterWareService.getEnterWareById(str1);
    String str2 = localEnterWare.getFirmId();
    DealerService localDealerService = (DealerService)SysData.getBean("w_dealerService");
    Dealer localDealer = localDealerService.getDealerById(str2);
    String str3 = localEnterWare.getWarehouseId();
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str3, false, false);
    int i = localEnterWare.getAbility();
    StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
    Status localStatus = localStatusService.getStatus("EnterWare", i);
    String str4 = localEnterWare.getCommodityId();
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(str4, false);
    List localList = localEnterWare.getQualityStandardList();
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    Map localMap = localWorkflowFacade.getLastMsg(localEnterWare.getCurrentStatus(), Condition.popedom, enterWareCommand);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "enterware/enterWareView", "operateObj", localEnterWare);
    localModelAndView.addObject("commodity", localCommodity);
    localModelAndView.addObject("warehouse", localWarehouse);
    localModelAndView.addObject("dealer", localDealer);
    localModelAndView.addObject("status", localStatus);
    localModelAndView.addObject("msgMap", localMap);
    localModelAndView.addObject("qualityStandardList", localList);
    localModelAndView.addObject("submitAction", "servlet/enterWareController." + Condition.POSTFIX + "?funcflg=enterWareDealWith");
    localModelAndView.addObject("returnAction", "servlet/enterWareController." + Condition.POSTFIX + "?funcflg=enterWareReturn");
    return localModelAndView;
  }
  
  public ModelAndView enterWareDealWith(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter enterWareDealWith========");
    String str1 = paramHttpServletRequest.getParameter("enterWareId");
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str3 = paramHttpServletRequest.getParameter("isDoSome");
    this.logger.debug("operate:" + str3);
    String str4 = paramHttpServletRequest.getParameter("note");
    EnterWareService localEnterWareService = (EnterWareService)SysData.getBean("w_enterWareService");
    EnterWare localEnterWare = localEnterWareService.getEnterWareById(str1);
    if ((str4 != null) && (!"".equals(str4))) {
      localEnterWare.setRejectedReasons(str4);
    }
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)SysData.getBean("w_workflowFacade");
    String str5 = localWorkflowFacade.getLogContent(str3);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setOperate(str3);
    localOriginalModel.setHoldAuthority(Condition.popedom);
    localOriginalModel.setObject(localEnterWare);
    OperateLog localOperateLog = new OperateLog();
    localOperateLog.setContent(str5);
    localOperateLog.setUserId(str2);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("2");
    localOperateLog.setPopedom(Condition.popedom);
    localOriginalModel.setLog(localOperateLog);
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, enterWareCommand);
    setResultMsg(paramHttpServletRequest, getValueFromResult(localMap));
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterWareController.wha?funcflg=enterWareList");
  }
  
  public ModelAndView enterWareReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/enterWareController." + Condition.POSTFIX + "?funcflg=enterWareList");
  }
}
