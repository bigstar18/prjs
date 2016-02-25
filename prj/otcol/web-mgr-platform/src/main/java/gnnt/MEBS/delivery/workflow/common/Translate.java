package gnnt.MEBS.delivery.workflow.common;

import java.util.Map;

public class Translate
{
  private Map<String, String> resultMap;
  private Map<String, String> handleTransNameMap;
  private Map<String, String> logContentMap;
  
  public void setResultMap(Map<String, String> paramMap)
  {
    this.resultMap = paramMap;
  }
  
  public void setHandleTransNameMap(Map<String, String> paramMap)
  {
    this.handleTransNameMap = paramMap;
  }
  
  public void setLogContentMap(Map<String, String> paramMap)
  {
    this.logContentMap = paramMap;
  }
  
  public String transResult(String paramString)
  {
    return (String)this.resultMap.get(paramString);
  }
  
  public String transHandle(String paramString)
  {
    return (String)this.handleTransNameMap.get(paramString);
  }
  
  public String transLogContent(String paramString)
  {
    return (String)this.logContentMap.get(paramString);
  }
}
