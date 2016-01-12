package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommoditySelectInterceptor extends AbstractInterceptor
{

  @Autowired
  @Qualifier("com_tradeParamsService")
  private TradeParamsService tps;

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    ActionContext localActionContext = paramActionInvocation.getInvocationContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    localHttpServletRequest.setAttribute("commoditySelect", this.tps.getSelectLabelValueByTable("T_commodity", "NAME", "COMMODITYID", " where settleWay=1 order by name "));
    String str = paramActionInvocation.invoke();
    return str;
  }
}