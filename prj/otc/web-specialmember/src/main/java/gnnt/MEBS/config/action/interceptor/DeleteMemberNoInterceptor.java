package gnnt.MEBS.config.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class DeleteMemberNoInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(DeleteMemberNoInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    String memberNo = (String)request.getAttribute(ActionConstant.GNNT_ + "primary.s_memberNo[=]");
    request.removeAttribute(ActionConstant.GNNT_ + "primary.s_memberNo[=]");
    String result = invocation.invoke();
    return result;
  }
}
