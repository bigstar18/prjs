package gnnt.MEBS.report.model;

import java.util.List;
import java.util.Map;

public class DataTable
{
  private String template;
  private List<Map<String, Object>> dataList;
  
  public String getTemplate()
  {
    return this.template;
  }
  
  public void setTemplate(String template)
  {
    this.template = template;
  }
  
  public List<Map<String, Object>> getDataList()
  {
    return this.dataList;
  }
  
  public void setDataList(List<Map<String, Object>> dataList)
  {
    this.dataList = dataList;
  }
}
