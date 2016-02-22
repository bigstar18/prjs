package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Set;

public class MemberInfoTree
  extends Clone
{
  private String id;
  private String name;
  private Set<ManagerTree> managerSet;
  private Set<OrganizationTree> organizationSet;
  private Set<BrokerageTree> brokerageSet;
  
  public Set<ManagerTree> getManagerSet()
  {
    return this.managerSet;
  }
  
  public void setManagerSet(Set<ManagerTree> managerSet)
  {
    this.managerSet = managerSet;
  }
  
  public Set<BrokerageTree> getBrokerageSet()
  {
    return this.brokerageSet;
  }
  
  public void setBrokerageSet(Set<BrokerageTree> brokerageSet)
  {
    this.brokerageSet = brokerageSet;
  }
  
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
  
  public Set<OrganizationTree> getOrganizationSet()
  {
    return this.organizationSet;
  }
  
  public void setOrganizationSet(Set<OrganizationTree> organizationSet)
  {
    this.organizationSet = organizationSet;
  }
  
  public String forTree()
  {
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"memberInfo\" value=\"" + 
      this.id + "\"> " + 
      " <img src=\"../../tree1/memberInfo.gif\" align=\"absmiddle\"/><label id=\"" + this.id + "_memberInfo\">(" + getMemberNo() + ")" + this.name + "</label>";
    if ((this.organizationSet != null) && (this.organizationSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (OrganizationTree organization : this.organizationSet) {
        treeString = treeString + organization.forTree();
      }
      treeString = treeString + "</ul>";
    }
    if ((this.managerSet != null) && (this.managerSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (ManagerTree manager : this.managerSet) {
        treeString = treeString + manager.forTree();
      }
      treeString = treeString + "</ul>";
    }
    if ((this.brokerageSet != null) && (this.brokerageSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (BrokerageTree brokerage : this.brokerageSet) {
        treeString = treeString + brokerage.forTree();
      }
      treeString = treeString + "</ul>";
    }
    treeString = treeString + "</li>";
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
  
  public String forTreeSyn()
  {
    String treeString = "<li id=\"" + this.id + "_li\">" + 
      "<input type=\"checkbox\" name=\"checks\" id=\"memberInfo\" value=\"" + this.id + "\"> " + "<input type=\"hidden\" id=\"" + this.id + "_mem\" value=\"false\">" + 
      " <img src=\"../../tree1/memberInfo.gif\" align=\"absmiddle\"/><label id=\"" + this.id + "_memberInfo\" onclick=\"clickMember(\\'" + this.id + "\\')\">(" + getMemberNo() + ")" + this.name + "</label>";
    treeString = treeString + "</li>";
    return treeString;
  }
  
  public String forZTree()
  {
    String treeString = "{'id':'" + this.id + "_M','pId':'0','name':'" + this.name + "【M】','title':'M'}";
    if ((this.organizationSet != null) && (this.organizationSet.size() > 0)) {
      for (OrganizationTree organization : this.organizationSet) {
        treeString = treeString + "," + organization.forZTree(new StringBuilder(String.valueOf(this.id)).append("_M").toString());
      }
    }
    if ((this.brokerageSet != null) && (this.brokerageSet.size() > 0)) {
      for (BrokerageTree brokerage : this.brokerageSet) {
        treeString = treeString + "," + brokerage.forZTree(new StringBuilder(String.valueOf(this.id)).append("_M").toString());
      }
    }
    return treeString;
  }
}
