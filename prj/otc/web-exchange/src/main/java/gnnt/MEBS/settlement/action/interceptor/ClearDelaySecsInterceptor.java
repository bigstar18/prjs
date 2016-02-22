package gnnt.MEBS.settlement.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.settlement.service.QuotePointRunTimeService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ClearDelaySecsInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ClearDelaySecsInterceptor.class);
  @Autowired
  @Qualifier("quotePointRunTimeService")
  private QuotePointRunTimeService quotePointRunTimeService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    AgencyRMIBean remObject = new AgencyRMIBean(request);
    request.setAttribute("market", remObject.getMarketInfo());
    String result = invocation.invoke();
    return result;
  }
}
