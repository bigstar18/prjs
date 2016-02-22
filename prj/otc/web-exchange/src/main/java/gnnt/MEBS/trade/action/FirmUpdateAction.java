package gnnt.MEBS.trade.action;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.service.FirmUpdateService;
import java.io.PrintStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("firmUpdateAction")
@Scope("request")
public class FirmUpdateAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(FirmUpdateAction.class);
  @Autowired
  @Qualifier("firmUpdateService")
  private FirmUpdateService firmUpdateService;
  
  public InService getService()
  {
    return this.firmUpdateService;
  }
  
  public int getFirmUpdateCount()
  {
    int num = this.firmUpdateService.getTotalCount(null);
    System.out.println(num);
    return num;
  }
}
