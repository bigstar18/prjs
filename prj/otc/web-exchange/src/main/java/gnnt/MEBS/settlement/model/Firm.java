package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;

public class Firm
  extends Clone
{
  private String firmId;
  private String firmName;
  private String firmType;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public String getId()
  {
    return this.firmId;
  }
  
  public void setPrimary(String primary)
  {
    this.firmId = primary;
  }
}
