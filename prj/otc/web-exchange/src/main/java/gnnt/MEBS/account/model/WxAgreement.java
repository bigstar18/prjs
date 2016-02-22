package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;

public class WxAgreement
  extends Clone
{
  private Long id;
  private String agreement1;
  private String agreement2;
  private String agreement3;
  private String agreement4;
  private String agreement5;
  
  public String getAgreement1()
  {
    return this.agreement1;
  }
  
  public void setAgreement1(String agreement1)
  {
    this.agreement1 = agreement1;
  }
  
  public String getAgreement2()
  {
    return this.agreement2;
  }
  
  public void setAgreement2(String agreement2)
  {
    this.agreement2 = agreement2;
  }
  
  public String getAgreement3()
  {
    return this.agreement3;
  }
  
  public void setAgreement3(String agreement3)
  {
    this.agreement3 = agreement3;
  }
  
  public String getAgreement4()
  {
    return this.agreement4;
  }
  
  public void setAgreement4(String agreement4)
  {
    this.agreement4 = agreement4;
  }
  
  public String getAgreement5()
  {
    return this.agreement5;
  }
  
  public void setAgreement5(String agreement5)
  {
    this.agreement5 = agreement5;
  }
  
  public WxAgreement() {}
  
  public WxAgreement(String agreement1, String agreement2, String agreement3, String agreement4, String agreement5)
  {
    this.agreement1 = agreement1;
    this.agreement2 = agreement2;
    this.agreement3 = agreement3;
    this.agreement4 = agreement4;
    this.agreement5 = agreement5;
  }
  
  public WxAgreement(Long id, String agreement1, String agreement2, String agreement3, String agreement4, String agreement5)
  {
    this.id = id;
    this.agreement1 = agreement1;
    this.agreement2 = agreement2;
    this.agreement3 = agreement3;
    this.agreement4 = agreement4;
    this.agreement5 = agreement5;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setPrimary(String primary) {}
}
