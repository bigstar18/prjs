package gnnt.MEBS.settlement.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.settlement.model.QuotePointRunTime;
import gnnt.MEBS.settlement.service.QuotePointRunTimeService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QuotationListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QuotationListInterceptor.class);
  @Autowired
  @Qualifier("quotePointRunTimeService")
  private QuotePointRunTimeService quotePointRunTimeService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    List<QuotePointRunTime> list = this.quotePointRunTimeService.getSpemcialMemberNo();
    if ((list != null) && (list.size() > 0)) {
      request.setAttribute(ActionConstant.GNNT_ + "quotepoint.m_FirmId[=]", ((QuotePointRunTime)list.get(0)).getM_FirmId());
    }
    String result = invocation.invoke();
    return result;
  }
}
