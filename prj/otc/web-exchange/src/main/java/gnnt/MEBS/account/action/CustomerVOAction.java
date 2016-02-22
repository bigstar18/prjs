package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.CustomerVOService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CustomerVOAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerVOAction.class);
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  @Autowired
  @Qualifier("customerVOService")
  private CustomerVOService customerVOService;
  @Resource(name="papersTypeMap")
  private Map papersTypeMap;
  @Resource(name="papersTypeUpdateMap")
  private Map papersTypeUpdateMap;
  @Resource(name="firmStatusMap")
  private Map firmStatusMap;
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
  }
  
  public Map getPapersTypeMap()
  {
    return this.papersTypeMap;
  }
  
  public Map getPapersTypeUpdateMap()
  {
    return this.papersTypeUpdateMap;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
  
  public InService getService()
  {
    return this.customerVOService;
  }
  
  public String list()
  {
    return super.list();
  }
  
  public String customerList()
  {
    this.logger.debug("enter customerList");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    if (this.request.getAttribute("sortString") != null)
    {
      String orderString = (String)this.request.getAttribute("sortString");
      ThreadStore.put("orderString", orderString);
    }
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    

    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.customerVOService.getCustomerList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
}
