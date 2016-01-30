package gnnt.MEBS.integrated.mgr.action.stocknamage;

import gnnt.MEBS.bill.core.bo.StockOutAuditBO;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.QueryService;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.PropertyType;
import gnnt.MEBS.integrated.mgr.model.stockmanage.Dismantle;
import gnnt.MEBS.integrated.mgr.model.stockmanage.Logistics;
import gnnt.MEBS.integrated.mgr.model.stockmanage.Stock;
import gnnt.MEBS.integrated.mgr.model.stockmanage.StockGoodsProperty;
import gnnt.MEBS.integrated.mgr.service.writeopreatelog.WriteOperateLogService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Controller("stockApartAction")
@Scope("request")
public class StockApartAction
  extends EcsideAction
{
  private static final long serialVersionUID = -5345964488202826357L;
  private Integer operationId;
  private String operation;
  @Autowired
  @Qualifier("billKernelService")
  private IKernelService kernelService;
  @Resource
  private Map<Integer, String> stockStatusMap;
  private Integer stockStatus;
  @Autowired
  @Qualifier("writeOperateLogService")
  private WriteOperateLogService writeOperateLogService;
  
  public Integer getStockStatus()
  {
    return this.stockStatus;
  }
  
  public void setStockStatus(Integer stockStatus)
  {
    this.stockStatus = stockStatus;
  }
  
  public Map<Integer, String> getStockStatusMap()
  {
    return this.stockStatusMap;
  }
  
  public void setStockStatusMap(Map<Integer, String> stockStatusMap)
  {
    this.stockStatusMap = stockStatusMap;
  }
  
  public IKernelService getKernelService()
  {
    return this.kernelService;
  }
  
  public void setKernelService(IKernelService kernelService)
  {
    this.kernelService = kernelService;
  }
  
  public String getOperation()
  {
    return this.operation;
  }
  
  public void setOperation(String operation)
  {
    this.operation = operation;
  }
  
  public Integer getOperationId()
  {
    return this.operationId;
  }
  
  public void setOperationId(Integer operationId)
  {
    this.operationId = operationId;
  }
  
  public String listBy()
  {
    return "success";
  }
  
  public String stockOperationByOperation()
    throws Exception
  {
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    
    qc.addCondition("operationId", "=", this.operationId);
    qc.addCondition("stock.warehouseId", "=", user.getWarehouseID());
    
    pageRequest.setSortColumns("order by stock.lastTime desc");
    
    listByLimit(pageRequest);
    
    this.request.setAttribute("operation", this.operation);
    return "success";
  }
  
  public String queryStockList()
  {
    String stockId = this.request.getParameter("stockId");
    
    Stock stock = new Stock();
    stock.setStockId(stockId);
    stock = (Stock)getService().get(stock);
    if (stock != null)
    {
      System.out.println(stock.getGoodsProperties());
      putStockPropertys(stock.getGoodsProperties());
    }
    try
    {
      PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
      QueryConditions qc = (QueryConditions)pageRequest.getFilters();
      
      qc.addCondition("stockId", "=", stockId);
      pageRequest.setSortColumns("order by operationId");
      Page<StandardModel> page = getQueryService()
        .getPage(pageRequest, this.entity);
      this.request.setAttribute("pageInfo", page);
      
      this.request.setAttribute("oldParams", getParametersStartingWith(
        this.request, "gnnt_"));
    }
    catch (Exception e)
    {
      addReturnValue(-1, -9000L, new Object[] { "仓单详情查看操作" });
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "success";
  }
  
  private void putStockPropertys(Set<StockGoodsProperty> goodsProperties)
  {
    if ((goodsProperties != null) && (goodsProperties.size() > 0))
    {
      Map<PropertyType, List<StockGoodsProperty>> map = new LinkedHashMap();
      QueryConditions ptqc = new QueryConditions();
      ptqc.addCondition("status", "=", Integer.valueOf(0));
      PageRequest<QueryConditions> ptpageRequest = new PageRequest(1, 100, ptqc, " order by sortNo ");
      Page<StandardModel> ptpage = getService().getPage(ptpageRequest, new PropertyType());
      if ((ptpage != null) && (ptpage.getResult() != null)) {
        for (int i = 0; i < ptpage.getResult().size(); i++)
        {
          PropertyType pt = (PropertyType)ptpage.getResult().get(i);
          map.put(pt, new ArrayList());
        }
      }
      List<StockGoodsProperty> other = new ArrayList();
      List<StockGoodsProperty> list;
      for (StockGoodsProperty sgp : goodsProperties)
      {
        list = null;
        for (PropertyType key : map.keySet()) {
          if ((sgp.getPropertyTypeID() != null) && (sgp.getPropertyTypeID().equals(key.getPropertyTypeID()))) {
            list = (List)map.get(key);
          }
        }
        if (list == null) {
          list = other;
        }
        list.add(sgp);
      }
      Map<PropertyType, List<StockGoodsProperty>> tpmap = new LinkedHashMap();
      for (PropertyType key : map.keySet()) {
        if (((List)map.get(key)).size() > 0) {
          tpmap.put(key, (List)map.get(key));
        }
      }
      if (other.size() > 0)
      {
        PropertyType pt = new PropertyType();
        pt.setName("其它属性");
        pt.setPropertyTypeID(Long.valueOf(-1L));
        tpmap.put(pt, other);
      }
      this.request.setAttribute("tpmap", tpmap);
    }
  }
  
  public String queryDisposeList()
  {
    String stockId = this.request.getParameter("stockId");
    try
    {
      PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
      QueryConditions qc = (QueryConditions)pageRequest.getFilters();
      
      qc.addCondition("stockId", "=", stockId);
      
      qc.addCondition("status", "=", "0");
      
      pageRequest.setSortColumns("order by dismantleId");
      Page<StandardModel> page = getService()
        .getPage(pageRequest, new Dismantle());
      this.request.setAttribute("pageInfo", page);
      
      this.request.setAttribute("oldParams", getParametersStartingWith(
        this.request, "gnnt_"));
    }
    catch (Exception e)
    {
      addReturnValue(-1, -9000L, new Object[] { "待拆仓单信息查询操作" });
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "success";
  }
  
  public String disposeSuccess()
  {
    this.logger.debug("enter disposeSuccess");
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    
    String[] stockIds = this.request.getParameterValues("stock.stockId");
    String[] dismantleIds = this.request.getParameterValues("dismantleId");
    try
    {
      if (dismantleIds.length > 0)
      {
        Map<String, String> map = new HashMap();
        
        String longStockId = stockIds[0];
        for (String dismantleId : dismantleIds)
        {
          String realStockCode = this.request.getParameter("realStockCode" + 
            dismantleId);
          map.put(dismantleId, realStockCode);
        }
        ResultVO resultVO = this.kernelService.dismantleStock(longStockId, 
          true, map);
        if (resultVO.getResult() < 0L)
        {
          addReturnValue(-1, -10001L, new Object[] {resultVO
            .getErrorInfo() });
          
          this.writeOperateLogService.writeOperateLog(1212, user, "处理仓单号为：" + 
            longStockId + "的仓单拆单", 0, resultVO.getErrorInfo());
        }
        else
        {
          addReturnValue(1, 130105L);
          this.writeOperateLogService.writeOperateLog(1212, user, "处理仓单号为：" + 
            longStockId + "的仓单拆单", 1, "");
        }
      }
      else
      {
        addReturnValue(-1, -130107L);
        this.writeOperateLogService.writeOperateLog(1212, user, "处理仓单拆单", 0, 
          ApplicationContextInit.getErrorInfo("-130107"));
      }
    }
    catch (Exception e)
    {
      addReturnValue(-1, -130106L, new Object[] { "拆仓单操作" });
      this.logger.error(Tools.getExceptionTrace(e));
      
      this.writeOperateLogService.writeOperateLog(1212, user, "处理仓单拆单", 0, e
        .getMessage());
    }
    return "success";
  }
  
  public String disposeFail()
  {
    this.logger.debug("enter disposeFail");
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    
    String stockId = this.request.getParameter("stock.stockId");
    try
    {
      ResultVO resultVO = this.kernelService.dismantleStock(stockId, false, 
        null);
      addReturnValue(1, -130105L);
      if (resultVO.getResult() < 0L)
      {
        addReturnValue(-1, -10001L, new Object[] {resultVO
          .getErrorInfo() });
        this.writeOperateLogService.writeOperateLog(1212, user, "撤销仓单号为：" + 
          stockId + "的仓单拆单", 0, resultVO.getErrorInfo());
      }
      else
      {
        addReturnValue(1, 130105L);
        this.writeOperateLogService.writeOperateLog(1212, user, "撤销仓单号为：" + 
          stockId + "的仓单拆单", 1, "");
      }
    }
    catch (Exception e)
    {
      addReturnValue(-1, -130106L, new Object[] { "拆仓单失败操作" });
      this.logger.error(Tools.getExceptionTrace(e));
      
      this.writeOperateLogService.writeOperateLog(1212, user, "撤销仓单号为：" + stockId + 
        "的仓单拆单", 0, e.getMessage());
    }
    return "success";
  }
  
  public String dismantleListByStatus()
    throws Exception
  {
    this.logger.debug("enter dismantleListByStatus ");
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
    
    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    
    qc.addCondition("status", "in", "(1,2)");
    qc.addCondition("stock.warehouseId", "=", user.getWarehouseID());
    
    pageRequest.setSortColumns("order by dismantleId desc");
    
    listByLimit(pageRequest);
    return "success";
  }
  
  public String getDismantleDetails()
    throws Exception
  {
    super.viewById();
    Dismantle dismantle = (Dismantle)this.entity;
    if ((dismantle != null) && (dismantle.getStock() != null)) {
      putStockPropertys(dismantle.getStock().getGoodsProperties());
    }
    return "success";
  }
  
  public String stockListByStatus()
    throws Exception
  {
    this.logger.debug(":::::status: " + this.stockStatus);
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    try
    {
      PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
      QueryConditions qc = (QueryConditions)pageRequest.getFilters();
      
      qc.addCondition("Stockstatus", "=", this.stockStatus);
      qc.addCondition("warehouseId", "=", user.getWarehouseID());
      if (this.stockStatus.intValue() == 2) {
        pageRequest.setSortColumns(" order by  lastTime  desc");
      } else {
        pageRequest.setSortColumns(" order by  createTime desc,to_number(stockId)  desc");
      }
      listByLimit(pageRequest);
    }
    catch (Exception e)
    {
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "success";
  }
  
  public String stockOutForward()
  {
    String stockId = this.request.getParameter("stockId");
    String sql = "select * from bi_outStock where stockid=" + stockId + " and status=0" + "and phone is not null";
    List<Map<Object, Object>> stockList = getService().getDao()
      .queryBySql(sql);
    if ((stockList.size() == 0) || (stockList == null)) {
      this.request.setAttribute("deliverstyle", Integer.valueOf(0));
    } else {
      this.request.setAttribute("deliverstyle", Integer.valueOf(1));
    }
    if ((stockId != null) && (stockId.length() > 0)) {
      this.request.setAttribute("stockId", stockId);
    }
    return "success";
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
  public String stockOutReal()
  {
    String stockId = this.request.getParameter("stockId");
    this.logger.debug("出库申请列表出库=====仓单号为：" + stockId);
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    if ((stockId != null) && (stockId.length() > 0))
    {
      String company = this.request.getParameter("company");
      String logisticsOrder = this.request.getParameter("logisticsOrder");
      

      String deliveryPerson = this.request.getParameter("deliveryPerson");
      

      String key = this.request.getParameter("key");
      StockOutAuditBO stockOutAuditBO = new StockOutAuditBO();
      stockOutAuditBO.setStockID(stockId);
      stockOutAuditBO.setDeliveryPerson(deliveryPerson);
      
      stockOutAuditBO.setKey(key);
      
      ResultVO result = this.kernelService.stockOutAudit(stockOutAuditBO);
      if (result.getResult() > 0L)
      {
        Logistics logistics = new Logistics();
        logistics.setStockID(stockId);
        logistics.setLogisticsorder(logisticsOrder);
        logistics.setCompany(company);
        getService().add(logistics);
        
        addReturnValue(1, 130110L, new Object[] { stockId });
        
        this.writeOperateLogService.writeOperateLog(1213, user, "仓单号为：" + 
          stockId + "的仓单出库", 1, null);
      }
      else
      {
        addReturnValue(-1, 130111L, new Object[] {result
          .getErrorInfo().replace("\n", "") });
        
        this.writeOperateLogService.writeOperateLog(1213, user, "仓单号为：" + 
          stockId + "的仓单出库", 0, result.getErrorInfo()
          .replace("\n", ""));
        this.logger.debug("=============== key:::" + 
          result.getErrorCode() + ",Map::" + 
          result.getInfoMap());
      }
    }
    return "success";
  }
}
