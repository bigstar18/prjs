package gnnt.MEBS.report.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(MemberInfoInterceptor.class);
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   MemberInfoListInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    MemberInfo memberInfo = (MemberInfo)this.memberInfoService.getById((String)request.getSession().getAttribute(ActionConstant.REGISTERID));
    request.setAttribute("memberNo", memberInfo.getId());
    request.setAttribute("memberName", memberInfo.getName());
    String result = invocation.invoke();
    return result;
  }
}
