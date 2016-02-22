package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;

public class MBrokerageAndOrganization
  extends Clone
{
  private String brokerageNO;
  private String organizationNO;
  
  public String getBrokerageNO()
  {
    return this.brokerageNO;
  }
  
  public void setBrokerageNO(String brokerageNO)
  {
    this.brokerageNO = brokerageNO;
  }
  
  public String getOrganizationNO()
  {
    return this.organizationNO;
  }
  
  public void setOrganizationNO(String organizationNO)
  {
    this.organizationNO = organizationNO;
  }
  
  public String getId()
  {
    return this.brokerageNO;
  }
  
  public void setPrimary(String primary)
  {
    this.brokerageNO = primary;
  }
}
