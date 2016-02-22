package gnnt.MEBS.report.action;

import java.util.Map;
import javax.annotation.Resource;

public class ManagerReportAction
  extends BaseReportAction
{
  @Resource(name="flagMap")
  private Map flagMap;
  @Resource(name="codeMap")
  private Map codeMap;
  @Resource(name="whetherMap")
  private Map whetherMap;
  @Resource(name="moldMap")
  private Map moldMap;
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public Map getCodeMap()
  {
    return this.codeMap;
  }
  
  public Map getWhetherMap()
  {
    return this.whetherMap;
  }
  
  public Map getMoldMap()
  {
    return this.moldMap;
  }
}
