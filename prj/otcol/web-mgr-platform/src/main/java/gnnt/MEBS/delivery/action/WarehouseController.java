package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.User;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.OperateLogService;
import gnnt.MEBS.delivery.services.StatusService;
import gnnt.MEBS.delivery.services.WarehouseService;
import gnnt.MEBS.delivery.util.SysData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class WarehouseController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(WarehouseController.class);
  
  public ModelAndView warehouseList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'warehouseList' method...");
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/warehouse/warehouseList");
    try
    {
      QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "id", false);
      }
      WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
      List localList1 = localWarehouseService.getWarehouseList(localQueryConditions, localPageInfo);
      StatusService localStatusService = (StatusService)SysData.getBean("w_statusService");
      List localList2 = localStatusService.getStatusMap("Warehouse");
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      localModelAndView.addObject("resultList", localList1);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
      localModelAndView.addObject("statusList", localList2);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常！");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView warehouseAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'warehouseAddForward' method...");
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/warehouse/warehouseAdd");
    return localModelAndView;
  }
  
  public ModelAndView warehouseAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'warehouseAdd' method...");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    Warehouse localWarehouse = new Warehouse();
    ParamUtil.bindData(paramHttpServletRequest, localWarehouse);
    User localUser = new User();
    localUser.setUserId("admin_" + localWarehouse.getId());
    localUser.setName(localWarehouse.getName() + "超级管理员");
    localUser.setPassword("11111111");
    localUser.setManage_id(localWarehouse.getId());
    localUser.setRoleStatus(0);
    localUser.setPopedom("1");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    String str2 = "";
    int i = 0;
    try
    {
      OperateLog localOperateLog = new OperateLog();
      localOperateLog.setContent("添加仓库，仓库代码：" + localWarehouse.getId());
      localOperateLog.setUserId(str1);
      localOperateLog.setOperatetime(new Date());
      localOperateLog.setOperation("0");
      localOperateLog.setPopedom(Condition.popedom);
      OperateLogService localOperateLogService = (OperateLogService)SysData.getBean("w_operateLogService");
      localOperateLogService.addOprLog(localOperateLog);
      i = localWarehouseService.addWarehouse(localWarehouse, localUser, localOperateLog);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -4;
    }
    this.logger.debug("result:" + i);
    if (i == 0) {
      str2 = "操作成功!";
    } else if (i == -1) {
      str2 = "仓库id重复!";
    } else if (i == -2) {
      str2 = "仓库名称重复！";
    } else if (i == -3) {
      str2 = "操作异常！";
    } else if (i == -4) {
      str2 = "系统异常！";
    }
    this.logger.debug("msg:" + str2);
    setResultMsg(paramHttpServletRequest, str2);
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/warehouseController." + Condition.POSTFIX + "?funcflg=warehouseList");
  }
  
  public ModelAndView warehouseView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'warehouseView' method...");
    String str = paramHttpServletRequest.getParameter("warehouseId");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str, false, false);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/warehouse/warehouseMod", "warehouse", localWarehouse);
    return localModelAndView;
  }
  
  public ModelAndView warehouseMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'warehouseMod' method...");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    Warehouse localWarehouse = new Warehouse();
    ParamUtil.bindData(paramHttpServletRequest, localWarehouse);
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    String str2 = "";
    int i = 0;
    try
    {
      OperateLog localOperateLog = new OperateLog();
      localOperateLog.setContent("修改仓库，仓库代码：" + localWarehouse.getId());
      localOperateLog.setUserId(str1);
      localOperateLog.setOperatetime(new Date());
      localOperateLog.setOperation("0");
      localOperateLog.setPopedom(Condition.popedom);
      OperateLogService localOperateLogService = (OperateLogService)SysData.getBean("w_operateLogService");
      localOperateLogService.addOprLog(localOperateLog);
      i = localWarehouseService.updateWarehouse(localWarehouse, localOperateLog);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -4;
    }
    this.logger.debug("result:" + i);
    if (i == 0) {
      str2 = "操作成功!";
    } else if (i == -1) {
      str2 = "仓库id重复!";
    } else if (i == -2) {
      str2 = "仓库名称重复！";
    } else if (i == -3) {
      str2 = "操作异常！";
    } else if (i == -4) {
      str2 = "系统异常！";
    }
    setResultMsg(paramHttpServletRequest, str2);
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/warehouseController.wha?funcflg=warehouseList");
  }
  
  public ModelAndView warehouseDel(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'warehouseDel' method...");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("delCheck");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (String str : arrayOfString1)
    {
      String[] arrayOfString3 = str.split(",");
      i = localWarehouseService.deleteWarehouseJudge(arrayOfString3[0]);
      if (i == 1) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      }
      if (i == 2) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      }
      if (i == 3) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      }
      if (i == 0) {
        localArrayList.add(arrayOfString3[0]);
      }
    }
    if (localStringBuffer.length() > 0) {
      localStringBuffer.append("与其他数据有关联，不能删除！");
    }
    if (localArrayList.size() > 0)
    {
      for (int j = 0; j < localArrayList.size(); j++) {
        localStringBuffer.append((String)localArrayList.get(j) + ",");
      }
      try
      {
        localWarehouseService.deleteWarehouse(localArrayList);
        localStringBuffer.append("删除成功！成功删除" + localArrayList.size() + "条数据！");
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localStringBuffer.append("删除失败！");
      }
    }
    setResultMsg(paramHttpServletRequest, localStringBuffer.toString());
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/warehouseController." + Condition.POSTFIX + "?funcflg=warehouseList");
  }
  
  public ModelAndView commodityWarehouseView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityWarehouseView' method...");
    String str1 = paramHttpServletRequest.getParameter("warehouseId");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str1, true, false);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    List localList = localCommodityService.getCommodityListMap();
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/warehouse/warehouseRelateCommodity", "resultList", localList);
    localModelAndView.addObject("warehouse", localWarehouse);
    Set localSet = localWarehouse.getCommoditySet();
    Iterator localIterator1 = localSet.iterator();
    String str2 = "";
    String str3 = "";
    Iterator localIterator2 = localSet.iterator();
    while (localIterator2.hasNext())
    {
      Commodity localCommodity = (Commodity)localIterator2.next();
      str3 = localCommodity.getId();
      str2 = str2 + "," + str3 + ",";
    }
    localModelAndView.addObject("permissions", str2);
    return localModelAndView;
  }
  
  public ModelAndView commodityWarehouseRelate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityWarehouseRelate' method...");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    String str2 = paramHttpServletRequest.getParameter("warehouseId");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("commodityId");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    Warehouse localWarehouse = localWarehouseService.getWarehouseById(str2, false, false);
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Object localObject1;
    Object localObject2;
    if (arrayOfString != null)
    {
      HashSet localHashSet = new HashSet();
      for (int j = 0; j < arrayOfString.length; j++)
      {
        localObject1 = arrayOfString[j];
        localObject2 = localCommodityService.getCommodityById((String)localObject1, false);
        localHashSet.add(localObject2);
      }
      localWarehouse.addCommoditySet(localHashSet);
    }
    else
    {
      localWarehouse.addCommoditySet(null);
    }
    int i = 0;
    String str3 = "";
    try
    {
      localObject1 = new OperateLog();
      ((OperateLog)localObject1).setContent("仓库关联商品，仓库代码：" + localWarehouse.getId());
      ((OperateLog)localObject1).setUserId(str1);
      ((OperateLog)localObject1).setOperatetime(new Date());
      ((OperateLog)localObject1).setOperation("0");
      ((OperateLog)localObject1).setPopedom(Condition.popedom);
      localObject2 = (OperateLogService)SysData.getBean("w_operateLogService");
      ((OperateLogService)localObject2).addOprLog((OperateLog)localObject1);
      i = localWarehouseService.RelateCommodityWarehouse(localWarehouse, (OperateLog)localObject1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -4;
    }
    this.logger.debug("result:" + i);
    if (i == 0) {
      str3 = "操作成功!";
    } else if (i == -4) {
      str3 = "系统异常！";
    }
    this.logger.debug("msg:" + str3);
    setResultMsg(paramHttpServletRequest, str3);
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/warehouseController." + Condition.POSTFIX + "?funcflg=warehouseList");
  }
  
  public ModelAndView commodityWarehouseForbidOrResume(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'commodityWarehouseForbidOrResume' method...");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("logonUser");
    WarehouseService localWarehouseService = (WarehouseService)SysData.getBean("w_warehouseService");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str2 = paramHttpServletRequest.getParameter("tag");
    int i = 0;
    String str3 = "";
    String str4 = "";
    try
    {
      if (arrayOfString != null) {
        for (int j = 0; j < arrayOfString.length; j++)
        {
          OperateLog localOperateLog = new OperateLog();
          localOperateLog.setContent("禁止仓库，仓库代码：" + arrayOfString[j]);
          localOperateLog.setUserId(str1);
          localOperateLog.setOperatetime(new Date());
          localOperateLog.setOperation("0");
          localOperateLog.setPopedom(Condition.popedom);
          OperateLogService localOperateLogService = (OperateLogService)SysData.getBean("w_operateLogService");
          localOperateLogService.addOprLog(localOperateLog);
          if ("del".equals(str2))
          {
            str4 = arrayOfString[j];
            i = localWarehouseService.deleteWareHouseById(str4, localOperateLog);
          }
          else if ("resume".equals(str2))
          {
            str4 = arrayOfString[j];
            i = localWarehouseService.deleteGoResumeWareHouseById(str4, localOperateLog);
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -4;
    }
    this.logger.debug("result:" + i);
    if (i == 0) {
      str3 = "操作成功!";
    } else if (i == -4) {
      str3 = "系统异常！";
    }
    this.logger.debug("msg:" + str3);
    setResultMsg(paramHttpServletRequest, str3);
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/warehouseController." + Condition.POSTFIX + "?funcflg=warehouseList");
  }
  
  public ModelAndView warehouseReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/warehouseController." + Condition.POSTFIX + "?funcflg=warehouseList");
  }
}
