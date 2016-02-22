package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.settlement.service.MarketParametersService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CuopenQueryCustomerInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CuopenQueryCustomerInterceptor.class);
  @Autowired
  @Qualifier("marketParametersService")
  private MarketParametersService marketParametersService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter cuopenQueryCustomerInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    List market = this.marketParametersService.getList(null, null);
    request.setAttribute("market", market);
    String result = invocation.invoke();
    return result;
  }
}
