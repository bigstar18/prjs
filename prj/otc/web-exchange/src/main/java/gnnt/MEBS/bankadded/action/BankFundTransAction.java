package gnnt.MEBS.bankadded.action;

import gnnt.MEBS.bankadded.service.BankFundTransService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class BankFundTransAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BankFundTransAction.class);
  @Autowired
  @Qualifier("bankFundTransService")
  private BankFundTransService bankFundTransService;
  @Resource(name="transTypeMap")
  private Map transTypeMap;
  
  public Map getTransTypeMap()
  {
    return this.transTypeMap;
  }
  
  public InService getService()
  {
    return this.bankFundTransService;
  }
}
