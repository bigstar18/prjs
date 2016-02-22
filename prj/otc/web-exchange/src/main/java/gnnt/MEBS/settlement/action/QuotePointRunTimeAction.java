package gnnt.MEBS.settlement.action;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.service.QuotePointRunTimeService;
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
public class QuotePointRunTimeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(QuotePointRunTimeAction.class);
  @Autowired
  @Qualifier("quotePointRunTimeService")
  private QuotePointRunTimeService quotePointRunTimeService;
  @Resource(name="memberTypeMap")
  private Map memberTypeMap;
  
  public Map getMemberTypeMap()
  {
    return this.memberTypeMap;
  }
  
  public InService getService()
  {
    return this.quotePointRunTimeService;
  }
}
