package gnnt.MEBS.announcement.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryHisAnnoInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryHisAnnoInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "okNotice.recipientType[=]", AnnouncementConstant.EXCHANGE_ADMINISTRATOR);
    String result = invocation.invoke();
    return result;
  }
}
