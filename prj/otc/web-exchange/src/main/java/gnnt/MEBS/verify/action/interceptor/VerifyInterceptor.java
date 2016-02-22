package gnnt.MEBS.verify.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.verify.constant.Constant;
import gnnt.MEBS.verify.handler.VerifyHandler;
import gnnt.MEBS.verify.util.ParameterMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class VerifyInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(VerifyInterceptor.class);
  private VerifyHandler verifyHandler;
  
  public void setVerifyHandler(VerifyHandler verifyHandler)
  {
    this.verifyHandler = verifyHandler;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    String result = "";
    HttpServletRequest request = ServletActionContext.getRequest();
    String actionName = invocation.getInvocationContext().getName();
    String nameSpace = invocation.getProxy().getNamespace();
    this.logger.info("actionName:" + actionName);
    this.logger.info("nameSpace:" + nameSpace);
    String key = nameSpace + "/" + actionName;
    this.logger.debug("key:" + key);
    Map<String, String> keys = ParameterMap.getParametersStartingWith(request, Constant.VERIFYKEY);
    Map map = this.verifyHandler.handle(key, keys);
    if ((map != null) && (map.size() > 0))
    {
      int value = ((Integer)map.get(Constant.RETURNRESULT)).intValue();
      this.logger.debug("value:" + value);
      if (value < 0)
      {
        result = Constant.VERIFYQUIT;
        this.logger.debug("msg:" + (String)map.get(Constant.RETURNMSG));
        request.setAttribute(ActionConstant.RESULTMSG, (String)map.get(Constant.RETURNMSG));
        request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
      }
      else
      {
        this.logger.debug("检查通过");
        result = invocation.invoke();
      }
    }
    else
    {
      this.logger.debug("不检查");
      result = invocation.invoke();
    }
    return result;
  }
}
