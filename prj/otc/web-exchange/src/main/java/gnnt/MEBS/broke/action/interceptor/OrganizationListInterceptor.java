package gnnt.MEBS.broke.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OrganizationListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(MemberInfoListInterceptor.class);
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   OrganizationListInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "primary.name", false);
    List<Organization> orgList = this.organizationService.getList(null, pageInfo);
    request.setAttribute("orgList", orgList);
    String result = invocation.invoke();
    return result;
  }
}
