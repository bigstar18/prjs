package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class FirmVO
  extends Clone
{
  private String firmId;
  private String firmType;
  
  public FirmVO() {}
  
  public FirmVO(String firmId, String firmType)
  {
    this.firmId = firmId;
    this.firmType = firmType;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
