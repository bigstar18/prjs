package gnnt.MEBS.announcement.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class AnnoListInterceptor
  extends AbstractInterceptor
{
  private String source;
  
  public void setSource(String source)
  {
    this.source = source;
  }
  
  private final transient Log logger = LogFactory.getLog(AnnoListInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "source[=]", this.source);
    String result = invocation.invoke();
    return result;
  }
}
