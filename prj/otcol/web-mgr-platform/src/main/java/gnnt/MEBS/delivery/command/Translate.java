package gnnt.MEBS.delivery.command;

import java.util.Map;

public class Translate
{
  private Map<String, String> settleResultMap;
  
  public void setSettleResultMap(Map<String, String> paramMap)
  {
    this.settleResultMap = paramMap;
  }
  
  public String transResult(String paramString)
  {
    return (String)this.settleResultMap.get(paramString);
  }
}
