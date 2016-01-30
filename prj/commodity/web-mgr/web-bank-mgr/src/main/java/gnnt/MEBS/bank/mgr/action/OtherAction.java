package gnnt.MEBS.bank.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("otherAction")
@Scope("request")
public class OtherAction extends EcsideAction
{
  private static final long serialVersionUID = 625872834549079577L;

  @Resource(name="dictionaryType")
  private Map<Integer, String> dictionaryType;

  public Map<Integer, String> getDictionaryType()
  {
    return this.dictionaryType;
  }

  public String logList()
    throws Exception
  {
    return listByLimit();
  }

  public String dictionaryList()
    throws Exception
  {
    return listByLimit();
  }
}