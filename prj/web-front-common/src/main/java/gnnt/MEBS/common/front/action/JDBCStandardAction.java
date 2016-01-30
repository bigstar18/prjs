package gnnt.MEBS.common.front.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.common.front.common.ReturnValue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class JDBCStandardAction
  extends ActionSupport
  implements ServletRequestAware, ServletResponseAware
{
  private static final long serialVersionUID = 1479876290655836738L;
  protected final transient Log logger = LogFactory.getLog(getClass());
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  
  public void setServletRequest(HttpServletRequest paramHttpServletRequest)
  {
    this.request = paramHttpServletRequest;
  }
  
  public void setServletResponse(HttpServletResponse paramHttpServletResponse)
  {
    this.response = paramHttpServletResponse;
  }
  
  public void addReturnValue(int paramInt, long paramLong)
  {
    addReturnValue(paramInt, paramLong, null);
  }
  
  public void addReturnValue(int paramInt, long paramLong, Object[] paramArrayOfObject)
  {
    ReturnValue localReturnValue = new ReturnValue();
    localReturnValue.setResult(paramInt);
    if (paramArrayOfObject == null) {
      localReturnValue.addInfo(paramLong);
    } else {
      localReturnValue.addInfo(paramLong, paramArrayOfObject);
    }
    this.request.setAttribute("ReturnValue", localReturnValue);
  }
}
