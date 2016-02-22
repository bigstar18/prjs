package org.apache.struts2.dispatcher;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StrutsRequestWrapper
  extends HttpServletRequestWrapper
{
  private final transient Log logger = LogFactory.getLog(StrutsRequestWrapper.class);
  
  public StrutsRequestWrapper(HttpServletRequest req)
  {
    super(req);
  }
  
  public Object getAttribute(String s)
  {
    this.logger.debug("使用中" + s);
    if ((s != null) && (s.startsWith("javax.servlet"))) {
      return super.getAttribute(s);
    }
    ActionContext ctx = ActionContext.getContext();
    Object attribute = super.getAttribute(s);
    
    boolean alreadyIn = false;
    Boolean b = (Boolean)ctx.get("__requestWrapper.getAttribute");
    if (b != null) {
      alreadyIn = b.booleanValue();
    }
    if ((!alreadyIn) && (attribute == null) && (s.indexOf("#") == -1))
    {
      this.logger.debug("取值");
      









      ValueStack vs = (ValueStack)super.getAttribute("struts.valueStack");
      if (vs != null) {
        attribute = vs.findValue(s);
      }
    }
    return attribute;
  }
}
