package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class OrganizationForZTree
  extends Clone
{
  private String organizationNO;
  private String parentOrganizationNO;
  private String memberNo;
  private String name;
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public String getOrganizationNO()
  {
    return this.organizationNO;
  }
  
  public void setOrganizationNO(String organizationNO)
  {
    this.organizationNO = organizationNO;
  }
  
  public String getParentOrganizationNO()
  {
    return this.parentOrganizationNO;
  }
  
  public void setParentOrganizationNO(String parentOrganizationNO)
  {
    this.parentOrganizationNO = parentOrganizationNO;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
}
