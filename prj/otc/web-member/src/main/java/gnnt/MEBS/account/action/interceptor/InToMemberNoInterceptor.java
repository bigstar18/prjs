package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class InToMemberNoInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(InToMemberNoInterceptor.class);
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute("memberNo", (String)request.getSession().getAttribute(ActionConstant.REGISTERID));
    String result = invocation.invoke();
    return result;
  }
}
