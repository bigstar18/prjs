package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.util.CloneParameterValue;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class CheckInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CheckInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter check");
    HttpServletRequest request = ServletActionContext.getRequest();
    String requestUri = request.getRequestURI();
    this.logger.debug("requestUri:" + requestUri);
    invocation.getInvocationContext();this.logger.debug("com.opensymphony.xwork2.ActionContext.name");
    this.logger.debug(invocation.getAction().toString());
    if (!requestUri.toLowerCase().contains("list".toLowerCase()))
    {
      HttpSession session = request.getSession();
      ActionContext ac = invocation.getInvocationContext();
      ValueStack stack = ac.getValueStack();
      Clone clone = (Clone)stack.findValue(ActionConstant.OBJ);
      if (CloneParameterValue.judgeParameter(clone, "memberNo"))
      {
        Object value = CloneParameterValue.getParameter(clone, "memberNo");
        this.logger.debug("value:" + value);
        
        String memberNo = (String)session.getAttribute(ActionConstant.REGISTERID);
        this.logger.debug("memberNo:" + memberNo);
        if ((value != null) && (!memberNo.equals(value.toString()))) {
          throw new Exception("illegality operate");
        }
        if (value == null)
        {
          this.logger.debug("延时处理");
          
          ThreadStore.put(ThreadStoreConstant.MEMBERNO, memberNo);
        }
        else
        {
          this.logger.debug("检查无误");
        }
      }
      else
      {
        this.logger.debug("无参数");
      }
    }
    else
    {
      this.logger.debug("不检查");
    }
    String result = invocation.invoke();
    return result;
  }
}
