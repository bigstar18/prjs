package gnnt.MEBS.timebargain.broker.service.authorize;

import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.timebargain.broker.model.AgentTrader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("authorizeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class AuthorizeService extends StandardService
{
  public String getOperateFirm(String paramString)
  {
    AgentTrader localAgentTrader1 = new AgentTrader();
    localAgentTrader1.setAgentTraderId(paramString);
    AgentTrader localAgentTrader2 = (AgentTrader)get(localAgentTrader1);
    String str = "";
    if ((localAgentTrader2 != null) && (localAgentTrader2.getOperateFirm() != null) && (localAgentTrader2.getOperateFirm().trim().length() > 0))
    {
      String[] arrayOfString = localAgentTrader2.getOperateFirm().split(",");
      for (int i = 0; i < arrayOfString.length; i++)
        str = str + "'" + arrayOfString[i] + "',";
      str = str.substring(0, str.length() - 1);
    }
    return str;
  }

  public String[] getOperateFirms(String paramString)
  {
    AgentTrader localAgentTrader1 = new AgentTrader();
    localAgentTrader1.setAgentTraderId(paramString);
    AgentTrader localAgentTrader2 = (AgentTrader)get(localAgentTrader1);
    String[] arrayOfString = null;
    if ((localAgentTrader2 != null) && (localAgentTrader2.getOperateFirm() != null) && (localAgentTrader2.getOperateFirm().trim().length() > 0))
      arrayOfString = localAgentTrader2.getOperateFirm().split(",");
    return arrayOfString;
  }
}