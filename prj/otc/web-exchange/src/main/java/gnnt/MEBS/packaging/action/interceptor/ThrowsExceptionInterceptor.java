package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ThrowsExceptionInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ThrowsExceptionInterceptor.class);
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("ThrowsExceptionInterceptor enter");
    HttpServletRequest request = ServletActionContext.getRequest();
    String result = "";
    try
    {
      this.logger.debug("执行之前");
      result = invocation.invoke();
      this.logger.debug("执行之后");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.debug("异常");
      result = "error";
      String resultMsg = (String)this.returnOperationMap.get(Integer.valueOf(-1));
      request.setAttribute(ActionConstant.RESULTMSG, resultMsg);
      request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    }
    return result;
  }
}
