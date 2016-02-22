package gnnt.MEBS.trade.action;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class OnLineTraderAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(OnLineTraderAction.class);
  
  public InService getService()
  {
    return null;
  }
  
  public String list()
  {
    List traderList = null;
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      traderList = remObject.getTraders();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    setResultList(traderList);
    return getReturnValue();
  }
  
  public String update()
  {
    try
    {
      String traderId = this.request.getParameter("traderId");
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      if ((traderId != null) && (!"".equals(traderId))) {
        remObject.kickOnlineTrader(traderId);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return getReturnValue();
  }
}
