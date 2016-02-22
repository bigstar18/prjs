package gnnt.MEBS.broke.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(MemberInfoListInterceptor.class);
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   MemberInfoListInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    List orgList = this.organizationService.getByMemberNoList(null, (String)session.getAttribute(ActionConstant.REGISTERID));
    request.setAttribute("memberInfoList", orgList);
    String result = invocation.invoke();
    return result;
  }
}
