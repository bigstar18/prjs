package gnnt.MEBS.globalLog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecuteObject
{
  private List<Map<String, String>> keyList = new ArrayList();
  private List<Map<String, String>> propertyList = new ArrayList();
  private String note;
  
  public List<Map<String, String>> getKeyList()
  {
    return this.keyList;
  }
  
  public void setKeyList(List<Map<String, String>> keyList)
  {
    this.keyList = keyList;
  }
  
  public List<Map<String, String>> getPropertyList()
  {
    return this.propertyList;
  }
  
  public void setPropertyList(List<Map<String, String>> propertyList)
  {
    this.propertyList = propertyList;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
}
