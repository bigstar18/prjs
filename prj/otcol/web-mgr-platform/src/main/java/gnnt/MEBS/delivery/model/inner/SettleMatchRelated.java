package gnnt.MEBS.delivery.model.inner;

import gnnt.MEBS.base.model.Clone;

public class SettleMatchRelated
  extends Clone
{
  private String parentMatchId;
  private String childMatchId;
  
  public String getParentMatchId()
  {
    return this.parentMatchId;
  }
  
  public void setParentMatchId(String paramString)
  {
    this.parentMatchId = paramString;
  }
  
  public String getChildMatchId()
  {
    return this.childMatchId;
  }
  
  public void setChildMatchId(String paramString)
  {
    this.childMatchId = paramString;
  }
}
