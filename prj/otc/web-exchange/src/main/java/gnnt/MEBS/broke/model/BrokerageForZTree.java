package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class BrokerageForZTree
  extends Clone
{
  private String brokerageNo;
  private String name;
  private String memberNo;
  private BrokerageAndOrganization broAndOrg;
  
  public BrokerageForZTree() {}
  
  public BrokerageForZTree(String brokerageNo, String name, String memberNo, BrokerageAndOrganization broAndOrg)
  {
    this.brokerageNo = brokerageNo;
    this.name = name;
    this.memberNo = memberNo;
    this.broAndOrg = broAndOrg;
  }
  
  public BrokerageAndOrganization getBroAndOrg()
  {
    return this.broAndOrg;
  }
  
  public void setBroAndOrg(BrokerageAndOrganization broAndOrg)
  {
    this.broAndOrg = broAndOrg;
  }
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
