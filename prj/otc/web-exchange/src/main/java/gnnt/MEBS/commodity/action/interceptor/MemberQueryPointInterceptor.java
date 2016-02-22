package gnnt.MEBS.commodity.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class MemberQueryPointInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(MemberQueryPointInterceptor.class);
  private String queryCondition;
  private String queryWord;
  
  public void setQueryWord(String queryWord)
  {
    this.queryWord = queryWord;
  }
  
  public void setQueryCondition(String queryCondition)
  {
    this.queryCondition = queryCondition;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + this.queryCondition, this.queryWord);
    String result = invocation.invoke();
    return result;
  }
}
