package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;

public class TraderModule
  extends Clone
{
  private String moduleId;
  private String traderId;
  private String enabled;
  
  public String getEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(String paramString)
  {
    this.enabled = paramString;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }
}
