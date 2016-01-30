package gnnt.MEBS.integrated.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("statisticsQueryAction")
@Scope("request")
public class StatisticsQueryAction
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  
  public String forwardQuery()
  {
    return "success";
  }
}
