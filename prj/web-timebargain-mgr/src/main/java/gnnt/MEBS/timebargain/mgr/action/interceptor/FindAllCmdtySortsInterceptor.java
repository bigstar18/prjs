package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class FindAllCmdtySortsInterceptor extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(FindAllCmdtySortsInterceptor.class);

  @Autowired
  @Qualifier("com_tradeParamsService")
  private TradeParamsService ts;

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter FindAllCmdtySortsInterceptor's intercept");
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    List localList = this.ts.executeSelect("findAllCmdtySorts");
    localHttpServletRequest.setAttribute("allSortsList", localList);
    String str = paramActionInvocation.invoke();
    return str;
  }
}