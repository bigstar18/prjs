package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class Commoditytradeprop
  extends Clone
{
  private CommoditytradepropKey myId;
  private Date modifytime;
  
  public CommoditytradepropKey getMyId()
  {
    return this.myId;
  }
  
  public void setMyId(CommoditytradepropKey myId)
  {
    this.myId = myId;
  }
  
  public Date getModifytime()
  {
    return this.modifytime;
  }
  
  public void setModifytime(Date modifytime)
  {
    this.modifytime = modifytime;
  }
  
  public Commoditytradeprop() {}
  
  public Commoditytradeprop(String commidityid, int sectionid, Date modifytime)
  {
    this.myId = new CommoditytradepropKey(commidityid, sectionid);
    this.modifytime = modifytime;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
