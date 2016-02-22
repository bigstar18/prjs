package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class CustomerMappingBroker
  extends Clone
{
  private String customerNo;
  private String organizationNo;
  private String brokerageNo;
  private String managerNo;
  private String memberNo;
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="居间代码")
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  @ClassDiscription(name="客户号", key=true, keyWord=true)
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  @ClassDiscription(name="机构代码")
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  @ClassDiscription(name="客户代表代码")
  public String getManagerNo()
  {
    return this.managerNo;
  }
  
  public void setManagerNo(String managerNo)
  {
    this.managerNo = managerNo;
  }
  
  public String getId()
  {
    return this.customerNo;
  }
  
  public void setPrimary(String arg0)
  {
    this.customerNo = arg0;
  }
}
