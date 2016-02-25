package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;

public class FirmModule
  extends Clone
{
  private String moduleId;
  private String firmId;
  private String enabled;
  
  public String getEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(String paramString)
  {
    this.enabled = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
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
