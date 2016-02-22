package gnnt.MEBS.report.model;

import java.util.Map;

public class PrimaryTable
{
  private String template;
  private Map<String, Object> primaryMsg;
  
  public Map<String, Object> getPrimaryMsg()
  {
    return this.primaryMsg;
  }
  
  public void setPrimaryMsg(Map<String, Object> primaryMsg)
  {
    this.primaryMsg = primaryMsg;
  }
  
  public String getTemplate()
  {
    return this.template;
  }
  
  public void setTemplate(String template)
  {
    this.template = template;
  }
}
