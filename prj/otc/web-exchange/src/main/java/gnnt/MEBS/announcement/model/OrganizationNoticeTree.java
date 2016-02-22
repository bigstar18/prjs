package gnnt.MEBS.announcement.model;

import java.io.Serializable;
import java.util.Set;

public class OrganizationNoticeTree
  implements Serializable
{
  private String organizationNO;
  private String name;
  private Set<OrganizationNoticeTree> organizationSet;
  
  public String getOrganizationNO()
  {
    return this.organizationNO;
  }
  
  public void setOrganizationNO(String organizationNO)
  {
    this.organizationNO = organizationNO;
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
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"organization\" value=\"" + this.organizationNO + "\"> " + " <img src=\"../../tree1/organization.gif\"  align=\"absmiddle\"/><label id=\"" + this.organizationNO + "_organization\" value=\"" + this.organizationNO + "\" >" + 
      this.name + "</label>";
    if ((this.organizationSet != null) && (this.organizationSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (OrganizationNoticeTree organization : this.organizationSet) {
        treeString = treeString + organization.forTree();
      }
      treeString = treeString + "</ul>";
    }
    treeString = treeString + "</li>";
    return treeString;
  }
}
