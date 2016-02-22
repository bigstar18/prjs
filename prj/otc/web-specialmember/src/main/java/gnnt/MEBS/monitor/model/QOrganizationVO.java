package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class QOrganizationVO
  extends Clone
{
  private String memberno;
  private String name;
  private String organizationo;
  private String parentorganizationo;
  
  public String getId()
  {
    return this.memberno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String memberName)
  {
    this.name = memberName;
  }
  
  public void setMemberno(String memberNo)
  {
    this.memberno = memberNo;
  }
  
  public String getOrganizationo()
  {
    return this.organizationo;
  }
  
  public void setOrganizationo(String orgNo)
  {
    this.organizationo = orgNo;
  }
  
  public String getParentorganizationo()
  {
    return this.parentorganizationo;
  }
  
  public void setParentorganizationo(String orgNo)
  {
    this.parentorganizationo = orgNo;
  }
  
  public void setPrimary(String primary) {}
}
