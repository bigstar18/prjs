package gnnt.MEBS.announcement.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Set;

public class MemberNoticeTree
  extends Clone
{
  private String id;
  private String name;
  private Set<OrganizationNoticeTree> organizationSet;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Set<OrganizationNoticeTree> getOrganizationSet()
  {
    return this.organizationSet;
  }
  
  public void setOrganizationSet(Set<OrganizationNoticeTree> organizationSet)
  {
    this.organizationSet = organizationSet;
  }
  
  public String forTree()
  {
    String treeString = "";
    if ((this.organizationSet != null) && (this.organizationSet.size() > 0)) {
      for (OrganizationNoticeTree organization : this.organizationSet) {
        treeString = treeString + organization.forTree();
      }
    }
    return treeString;
  }
  
  public void setPrimary(String primary)
  {
    this.id = primary;
  }
  
  public String getMemberNo()
  {
    return this.id;
  }
}
