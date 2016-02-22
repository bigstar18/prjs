package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.monitor.model.PerformanceMonitor;
import gnnt.MEBS.monitor.service.PerformanceMonitorService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("performanceMonitorAction")
@Scope("request")
public class PerformanceMonitorAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(PerformanceMonitorAction.class);
  @Autowired
  @Qualifier("performanceMonitorService")
  protected PerformanceMonitorService performanceMonitorService;
  @Autowired
  @Qualifier("commodityService")
  protected CommodityService commodityService;
  
  public InService getService()
  {
    return this.performanceMonitorService;
  }
  
  public CommodityService getCommodityService()
  {
    return this.commodityService;
  }
  
  private Map<String, JSONArray> jsonMap = new HashMap();
  
  public Map<String, JSONArray> getJsonMap()
  {
    return this.jsonMap;
  }
  
  public String forwardMonitor()
  {
    return "success";
  }
  
  public String getOnlineList()
  {
    JSONArray json = new JSONArray();
    String type = this.request.getParameter("type");
    if (((type == null) && ("".equals(type))) || (
      (type != null) && (!"1".equals(type)))) {
      return "success";
    }
    List<PerformanceMonitor> list = getMonitorList(type, 240);
    String jsonStr = "";
    for (int i = list.size() - 1; i >= 0; i--)
    {
      PerformanceMonitor p = (PerformanceMonitor)list.get(i);
      jsonStr = "[" + p.getDateTime().getTime() + "," + p.getNum() + "]";
      json.add(jsonStr);
      this.jsonMap.put("online", json);
    }
    return "success";
  }
  
  public String getOrdersList()
  {
    String type = this.request.getParameter("type");
    if (((type == null) && ("".equals(type))) || (
      (type != null) && (!"2".equals(type)))) {
      return "success";
    }
    QueryConditions qc = new QueryConditions("primary.status", "=", Integer.valueOf(1));
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.marketDate,primary.id", false);
    this.commodityList = this.commodityService.getList(qc, pageInfo);
    Commodity sumCommodity = new Commodity();
    sumCommodity.setId("All");
    sumCommodity.setName("总和");
    this.commodityList.add(sumCommodity);
    List<PerformanceMonitor> list = getMonitorList(type, (int)Arith.mul(240.0F, this.commodityList.size()));
    String jsonStr = "";
    for (int i = list.size() - 1; i >= 0; i--)
    {
      PerformanceMonitor p = (PerformanceMonitor)list.get(i);
      if ((this.commodityList != null) && (this.commodityList.size() > 0)) {
        for (Commodity commodity : this.commodityList)
        {
          JSONArray commJson = (JSONArray)this.jsonMap.get(commodity.getId());
          if (commJson == null) {
            commJson = new JSONArray();
          }
          if (commodity.getId().equals(p.getCategoryType()))
          {
            jsonStr = 
              "[" + p.getDateTime().getTime() + "," + p.getNum() + "]";
            commJson.add(jsonStr);
            this.jsonMap.put(commodity.getId(), commJson);
          }
        }
      }
    }
    return "success";
  }
  
  public String getTradesList()
  {
    String type = this.request.getParameter("type");
    if (((type == null) && ("".equals(type))) || (
      (type != null) && (!"3".equals(type)))) {
      return "success";
    }
    QueryConditions qc = new QueryConditions("primary.status", "=", Integer.valueOf(1));
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.marketDate,primary.id", false);
    this.commodityList = this.commodityService.getList(qc, pageInfo);
    Commodity sumCommodity = new Commodity();
    sumCommodity.setId("All");
    sumCommodity.setName("总和");
    this.commodityList.add(sumCommodity);
    List<PerformanceMonitor> list = getMonitorList(type, (int)Arith.mul(240.0F, this.commodityList.size()));
    String jsonStr = "";
    for (int i = list.size() - 1; i >= 0; i--)
    {
      PerformanceMonitor p = (PerformanceMonitor)list.get(i);
      if ((this.commodityList != null) && (this.commodityList.size() > 0)) {
        for (Commodity commodity : this.commodityList)
        {
          JSONArray commJson = (JSONArray)this.jsonMap.get(commodity.getId());
          if (commJson == null) {
            commJson = new JSONArray();
          }
          if (commodity.getId().equals(p.getCategoryType()))
          {
            jsonStr = 
              "[" + p.getDateTime().getTime() + "," + p.getNum() + "]";
            commJson.add(jsonStr);
            this.jsonMap.put(commodity.getId(), commJson);
          }
        }
      }
    }
    return "success";
  }
  
  private List<Commodity> commodityList = new LinkedList();
  
  public List<Commodity> getCommodityList()
  {
    return this.commodityList;
  }
  
  public String getHoldList()
  {
    String type = this.request.getParameter("type");
    if (((type == null) && ("".equals(type))) || (
      (type != null) && (!"4".equals(type)))) {
      return "success";
    }
    QueryConditions qc = new QueryConditions("primary.status", "=", Integer.valueOf(1));
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.marketDate,primary.id", false);
    this.commodityList = this.commodityService.getList(qc, pageInfo);
    Commodity sumCommodity = new Commodity();
    sumCommodity.setId("All");
    sumCommodity.setName("总和");
    this.commodityList.add(sumCommodity);
    List<PerformanceMonitor> list = getMonitorList(type, (int)Arith.mul(240.0F, this.commodityList.size()));
    
    String jsonStr = "";
    for (int i = list.size() - 1; i >= 0; i--)
    {
      PerformanceMonitor p = (PerformanceMonitor)list.get(i);
      if ((this.commodityList != null) && (this.commodityList.size() > 0)) {
        for (Commodity commodity : this.commodityList)
        {
          JSONArray commJson = (JSONArray)this.jsonMap.get(commodity.getId());
          if (commJson == null) {
            commJson = new JSONArray();
          }
          if (commodity.getId().equals(p.getCategoryType()))
          {
            jsonStr = 
              "[" + p.getDateTime().getTime() + "," + p.getNum() + "]";
            commJson.add(jsonStr);
            this.jsonMap.put(commodity.getId(), commJson);
          }
        }
      }
    }
    return "success";
  }
  
  public List getMonitorList(String type, int pageSize)
  {
    QueryConditions qc = new QueryConditions("type", "=", type);
    PageInfo pageInfo = new PageInfo(1, pageSize, "datetime", true);
    List<PerformanceMonitor> list = getService().getList(qc, pageInfo);
    return list;
  }
}
