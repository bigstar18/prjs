package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Market;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TradeTimeTypeInterceptor extends AbstractInterceptor
{

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;

  public void setStandardService(StandardService paramStandardService)
  {
    this.standardService = paramStandardService;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    PageRequest localPageRequest = new PageRequest();
    localPageRequest.setPageNumber(1);
    localPageRequest.setPageSize(1);
    Page localPage = this.standardService.getPage(localPageRequest, new Market());
    Market localMarket = (Market)localPage.getResult().get(0);
    localHttpServletRequest.setAttribute("tradeTimeType", localMarket.getTradeTimeType());
    localHttpServletRequest.setAttribute("pageRequest", localPageRequest);
    String str = paramActionInvocation.invoke();
    return str;
  }
}