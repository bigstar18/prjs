package gnnt.MEBS.query.action;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomerFundFlowAction
  extends QueryBaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerFundFlowAction.class);
  @Resource(name="fundFlowMap")
  private Map fundFlowMap;
  
  public Map getFundFlowMap()
  {
    return this.fundFlowMap;
  }
}
