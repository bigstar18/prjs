package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class TransferOriginalValueInterceptor
  extends AbstractInterceptor
{
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    Map<String, Object> keys = WebUtils.getParametersStartingWith(request, ActionConstant.ORIGINAL_);
    if ((keys != null) && (keys.size() > 0)) {
      for (String key : keys.keySet()) {
        request.setAttribute(ActionConstant.ORIGINAL_ + key, keys.get(key));
      }
    }
    String result = invocation.invoke();
    return result;
  }
}
