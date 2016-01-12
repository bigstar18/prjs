package gnnt.MEBS.broker.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.broker.mgr.model.configparam.BrokerRewardProps;
import gnnt.MEBS.common.mgr.action.StandardAction;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class BrokerRewardPropsFilterInterceptor extends AbstractInterceptor
{
  private String brokerId;
  private String commodityId;

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    StandardAction localStandardAction = (StandardAction)paramActionInvocation.getAction();
    BrokerRewardProps localBrokerRewardProps = (BrokerRewardProps)localStandardAction.getEntity();
    if ((this.brokerId != null) && (this.commodityId != null))
    {
      localBrokerRewardProps.setBrokerId(this.brokerId);
      localBrokerRewardProps.setCommodityId(this.commodityId);
      localHttpServletRequest.setAttribute("gnnt_primary.brokerId[=]", this.brokerId);
      localHttpServletRequest.setAttribute("gnnt_primary.commodityId[=]", this.commodityId);
    }
    else
    {
      localHttpServletRequest.setAttribute("gnnt_primary.brokerId[<>]", "-1");
      localHttpServletRequest.setAttribute("gnnt_primary.commodityId[<>]", "-1");
    }
    String str = paramActionInvocation.invoke();
    return str;
  }
}