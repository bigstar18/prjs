package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryCompositeInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryCompositeInterceptor.class);
  private String queryWord;
  private String queryCondition;
  
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
    String queryValue = request.getParameter(this.queryWord);
    if (queryValue != null) {
      request.setAttribute(ActionConstant.GNNT_ + this.queryCondition, queryValue);
    }
    this.logger.debug(this.queryWord + " value:" + queryValue + "    query:" + ActionConstant.GNNT_ + this.queryCondition);
    String result = invocation.invoke();
    return result;
  }
}
