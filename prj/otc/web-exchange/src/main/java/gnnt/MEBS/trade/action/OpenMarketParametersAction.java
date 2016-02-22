package gnnt.MEBS.trade.action;

import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.MarketParameters;
import gnnt.MEBS.settlement.service.MarketParametersService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class OpenMarketParametersAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(OpenMarketParametersAction.class);
  @Autowired
  @Qualifier("marketParametersService")
  private MarketParametersService marketParametersService;
  
  public InService getService()
  {
    return this.marketParametersService;
  }
  
  public String forwardUpdate()
  {
    this.obj = this.marketParametersService.getById(null);
    return getReturnValue();
  }
  
  public String update()
  {
    int resultValue = this.marketParametersService.update((MarketParameters)this.obj);
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updateAgencyRMIBean()
  {
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      remObject.loadMarketStartInfo();
      addResultSessionMsg(this.request, 1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      if (e.getCause().getMessage() != null)
      {
        this.request.getSession().setAttribute(ActionConstant.RESULTMSG, e.getCause().getMessage());
        this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-602));
      }
    }
    return getReturnValue();
  }
}
