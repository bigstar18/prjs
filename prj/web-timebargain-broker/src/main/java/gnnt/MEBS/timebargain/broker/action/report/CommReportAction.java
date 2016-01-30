package gnnt.MEBS.timebargain.broker.action.report;

import gnnt.MEBS.common.broker.action.EcsideAction;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.timebargain.broker.service.report.CommReportService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("commReportAction")
@Scope("request")
public class CommReportAction extends EcsideAction
{
  private static final long serialVersionUID = 1L;

  @Autowired
  @Qualifier("commReportService")
  public CommReportService commReportService;
  public List<Map<Object, Object>> brokerAscList = new ArrayList();
  public List<Map<Object, Object>> brokerDescList = new ArrayList();
  public List<Map<Object, Object>> brokerageList = new ArrayList();
  public List<Map<Object, Object>> commodityIdAscList = new ArrayList();
  public List<Map<Object, Object>> commodityIdDescList = new ArrayList();
  public List<Map<Object, Object>> firmcategoryList = new ArrayList();
  public List<Map<Object, Object>> breedList = new ArrayList();

  public List<Map<Object, Object>> getBreedList()
  {
    return this.breedList;
  }

  public void setBreedList(List<Map<Object, Object>> paramList)
  {
    this.breedList = paramList;
  }

  public List<Map<Object, Object>> getFirmcategoryList()
  {
    return this.firmcategoryList;
  }

  public void setFirmcategoryList(List<Map<Object, Object>> paramList)
  {
    this.firmcategoryList = paramList;
  }

  public List<Map<Object, Object>> getBrokerageList()
  {
    return this.brokerageList;
  }

  public void setBrokerageList(List<Map<Object, Object>> paramList)
  {
    this.brokerageList = paramList;
  }

  public List<Map<Object, Object>> getBrokerAscList()
  {
    return this.brokerAscList;
  }

  public void setBrokerAscList(List<Map<Object, Object>> paramList)
  {
    this.brokerAscList = paramList;
  }

  public List<Map<Object, Object>> getBrokerDescList()
  {
    return this.brokerDescList;
  }

  public void setBrokerDescList(List<Map<Object, Object>> paramList)
  {
    this.brokerDescList = paramList;
  }

  public List<Map<Object, Object>> getCommodityIdAscList()
  {
    return this.commodityIdAscList;
  }

  public void setCommodityIdAscList(List<Map<Object, Object>> paramList)
  {
    this.commodityIdAscList = paramList;
  }

  public List<Map<Object, Object>> getCommodityIdDescList()
  {
    return this.commodityIdDescList;
  }

  public void setCommodityIdDescList(List<Map<Object, Object>> paramList)
  {
    this.commodityIdDescList = paramList;
  }

  public String getConditionList()
  {
    try
    {
      User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
      String str = localUser.getUserId();
      this.brokerAscList = getService().getListBySql(localUser.getSql() + " order by firmId asc");
      this.brokerDescList = getService().getListBySql(localUser.getSql() + " order by firmId desc");
      this.brokerageList = this.commReportService.getBrokerageList(str);
      this.firmcategoryList = getService().getListBySql("select c.id,c.name from M_FIRMCATEGORY c");
      System.out.println(((Map)this.firmcategoryList.get(0)).get("ID"));
      this.request.setAttribute("userType", localUser.getType());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }

  public String getCommodityList()
  {
    try
    {
      User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
      String str = localUser.getUserId();
      this.commodityIdAscList = this.commReportService.getCommodityList("asc");
      this.commodityIdDescList = this.commReportService.getCommodityList("desc");
      this.brokerageList = this.commReportService.getBrokerageList(str);
      this.breedList = getService().getListBySql("select t.breedid id, t.breedname name from M_BREED t where t.belongmodule like '%15%'");
      this.request.setAttribute("userType", localUser.getType());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "success";
  }
}