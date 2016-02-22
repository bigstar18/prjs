package gnnt.MEBS.commodity.model;

import java.io.Serializable;

public class CommoditytradepropKey
  implements Serializable
{
  private String commidityid;
  private int sectionid;
  
  public String getCommidityid()
  {
    return this.commidityid;
  }
  
  public void setCommidityid(String commidityid)
  {
    this.commidityid = commidityid;
  }
  
  public int getSectionid()
  {
    return this.sectionid;
  }
  
  public void setSectionid(int sectionid)
  {
    this.sectionid = sectionid;
  }
  
  public CommoditytradepropKey() {}
  
  public CommoditytradepropKey(String commidityid, int sectionid)
  {
    this.commidityid = commidityid;
    this.sectionid = sectionid;
  }
}
