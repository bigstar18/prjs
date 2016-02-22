package gnnt.MEBS.commodity.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.service.CommodityService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommodityListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CommodityListInterceptor.class);
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   MemberInfoListInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    QueryConditions conditions = new QueryConditions("status", "in", "(0,1,2)");
    List<Commodity> commodityList = this.commodityService.getList(conditions, null);
    request.setAttribute("commodityList", commodityList);
    String result = invocation.invoke();
    return result;
  }
}
