package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.MemberThresholdService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberThresholdAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberThresholdAction.class);
  @Autowired
  @Qualifier("memberThresholdService")
  private MemberThresholdService memberThresholdService;
  
  public InService getService()
  {
    return this.memberThresholdService;
  }
}
