package gnnt.MEBS.common.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class BrokerageRelateOrganization
  extends Clone
{
  private String brokerageNo;
  private String organizationNo;
  private String actualOrganizationNo;
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
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
