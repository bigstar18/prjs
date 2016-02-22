package gnnt.MEBS.report.action;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class MemberReportAction
  extends BaseReportAction
{
  private String oldMemberIds;
  @Resource(name="flagMap")
  private Map flagMap;
  @Resource(name="tradeMap")
  private Map tradeMap;
  @Resource(name="memberCodeMap_query")
  private Map memberCodeMap_query;
  @Resource(name="whetherMap")
  private Map whetherMap;
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public Map getTradeMap()
  {
    return this.tradeMap;
  }
  
  public Map getMemberCodeMap_query()
  {
    return this.memberCodeMap_query;
  }
  
  public Map getWhetherMap()
  {
    return this.whetherMap;
  }
  
  public String getOldMemberIds()
  {
    return this.oldMemberIds;
  }
  
  public void setOldMemberIds(String oldMemberIds)
  {
    this.oldMemberIds = oldMemberIds;
  }
  
  public String getMemberList()
  {
    String date = strDate();
    this.request.setAttribute("date", date);
    return "success";
  }
}
