package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class MemberFundOutInInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(MemberFundOutInInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   LogQueryInterceptor");
    System.out.println("enter query >>>>>>>>>>>>>>>>>>");
    HttpServletRequest request = ServletActionContext.getRequest();
    String memberNo = (String)request.getSession().getAttribute(ActionConstant.REGISTERID);
    request.removeAttribute(ActionConstant.GNNT_ + "primary.memberNo[=]");
    request.setAttribute(ActionConstant.GNNT_ + "primary.firmId[=]", memberNo);
    request.setAttribute(ActionConstant.GNNT_ + "primary.firmType[=]", "M");
    String result = invocation.invoke();
    return result;
  }
}
