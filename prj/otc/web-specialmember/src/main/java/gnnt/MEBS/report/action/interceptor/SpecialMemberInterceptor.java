package gnnt.MEBS.report.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SpecialMemberInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberInterceptor.class);
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   MemberInfoListInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    SpecialMember memberInfo = (SpecialMember)this.specialMemberService.getById((String)request.getSession().getAttribute(ActionConstant.REGISTERID));
    request.setAttribute("s_memberNo", memberInfo.getId());
    request.setAttribute("s_memberName", memberInfo.getName());
    String result = invocation.invoke();
    return result;
  }
}
