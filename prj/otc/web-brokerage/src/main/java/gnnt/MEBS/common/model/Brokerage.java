package gnnt.MEBS.common.model;

import gnnt.MEBS.base.model.Clone;

public class Brokerage
  extends Clone
{
  private String brokerageNo;
  private String memberNo;
  private String password;
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getId()
  {
    return this.brokerageNo;
  }
  
  public void setPrimary(String arg0) {}
}
