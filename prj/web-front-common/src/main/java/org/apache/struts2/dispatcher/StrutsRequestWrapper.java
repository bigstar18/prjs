package org.apache.struts2.dispatcher;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.BooleanUtils;

public class StrutsRequestWrapper
  extends HttpServletRequestWrapper
{
  private static final String REQUEST_WRAPPER_GET_ATTRIBUTE = "__requestWrapper.getAttribute";
  private final boolean disableRequestAttributeValueStackLookup;
  
  public StrutsRequestWrapper(HttpServletRequest paramHttpServletRequest)
  {
    this(paramHttpServletRequest, false);
  }
  
  public StrutsRequestWrapper(HttpServletRequest paramHttpServletRequest, boolean paramBoolean)
  {
    super(paramHttpServletRequest);
    this.disableRequestAttributeValueStackLookup = paramBoolean;
  }
  
  public Object getAttribute(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("You must specify a key value");
    }
    if ((this.disableRequestAttributeValueStackLookup) || (paramString.startsWith("javax.servlet"))) {
      return super.getAttribute(paramString);
    }
    ActionContext localActionContext = ActionContext.getContext();
    Object localObject1 = super.getAttribute(paramString);
    if ((localActionContext != null) && (localObject1 == null))
    {
      boolean bool = BooleanUtils.isTrue((Boolean)localActionContext.get("__requestWrapper.getAttribute"));
      if ((!bool) && (!paramString.contains("#")))
      {
        ValueStack localValueStack;
        try
        {
          localActionContext.put("__requestWrapper.getAttribute", Boolean.TRUE);
          localValueStack = localActionContext.getValueStack();
          if (localValueStack != null) {
            localObject1 = localValueStack.findValue(paramString);
          }
        }
        finally
        {
          localActionContext.put("__requestWrapper.getAttribute", Boolean.FALSE);
        }
        if (localObject1 == null)
        {
          localValueStack = (ValueStack)super.getAttribute("struts.valueStack");
          if (localValueStack != null) {
            localObject1 = localValueStack.findValue(paramString);
          }
        }
      }
    }
    return localObject1;
  }
}
