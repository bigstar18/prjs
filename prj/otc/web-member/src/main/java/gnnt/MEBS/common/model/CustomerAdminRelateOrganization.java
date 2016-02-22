package gnnt.MEBS.common.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class CustomerAdminRelateOrganization
  extends Clone
{
  private String traderId;
  private String organizationNo;
  private String actualOrganizationNo;
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String customerNo)
  {
    this.traderId = customerNo;
  }
  
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  public String getActualOrganizationNo()
  {
    return this.actualOrganizationNo;
  }
  
  public void setActualOrganizationNo(String actualOrganizationNo)
  {
    this.actualOrganizationNo = actualOrganizationNo;
  }
}
