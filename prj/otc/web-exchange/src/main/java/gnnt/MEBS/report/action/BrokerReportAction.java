package gnnt.MEBS.report.action;

import java.util.Map;
import javax.annotation.Resource;

public class BrokerReportAction
  extends BaseReportAction
{
  @Resource(name="flagMap")
  private Map flagMap;
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
}
