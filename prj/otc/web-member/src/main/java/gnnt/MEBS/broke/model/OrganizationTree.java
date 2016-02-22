package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Set;

public class OrganizationTree
  extends Clone
{
  private String organizationNO;
  private String name;
  private Set<OrganizationTree> organizationSet;
  private Set<BrokerageTree> brokerageSet;
  private Set<ManagerTree> managerSet;
  
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
  
  public Set<OrganizationTree> getOrganizationSet()
  {
    return this.organizationSet;
  }
  
  public void setOrganizationSet(Set<OrganizationTree> organizationSet)
  {
    this.organizationSet = organizationSet;
  }
  
  public Set<BrokerageTree> getBrokerageSet()
  {
    return this.brokerageSet;
  }
  
  public void setBrokerageSet(Set<BrokerageTree> brokerageSet)
  {
    this.brokerageSet = brokerageSet;
  }
  
  public Set<ManagerTree> getManagerSet()
  {
    return this.managerSet;
  }
  
  public void setManagerSet(Set<ManagerTree> managerSet)
  {
    this.managerSet = managerSet;
  }
  
  public String forTree()
  {
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"organization\" value=\"" + 
    
      this.organizationNO + 
      "\"> " + 
      " <img src=\"../../tree1/organization.gif\"  align=\"absmiddle\"/><label id=\"" + 
      this.organizationNO + "_organization\">" + this.name + 
      "</label>";
    if (((this.organizationSet != null) && (this.organizationSet.size() > 0)) || 
      ((this.brokerageSet != null) && (this.brokerageSet.size() > 0)) || (
      (this.managerSet != null) && (this.managerSet.size() > 0)))
    {
      treeString = treeString + "<ul>";
      for (OrganizationTree organization : this.organizationSet) {
        treeString = treeString + organization.forTree();
      }
      for (BrokerageTree brokerage : this.brokerageSet) {
        treeString = treeString + brokerage.forTree();
      }
      for (ManagerTree manager : this.managerSet) {
        treeString = treeString + manager.forTree();
      }
      treeString = treeString + "</ul>";
    }
    treeString = treeString + "</li>";
    return treeString;
  }
  
  public String forRightTree()
  {
    String treeString = "<li><input type=\"radio\" name=\"checks\" id=\"checks\" value=\"" + 
    
      this.organizationNO + "\"> " + "<label id=\"" + 
      this.organizationNO + "_organization\">" + this.name + 
      "</label>";
    if ((this.organizationSet != null) && (this.organizationSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (OrganizationTree organization : this.organizationSet) {
        treeString = treeString + organization.forRightTree();
      }
      treeString = treeString + "</ul>";
    }
    treeString = treeString + "</li>";
    return treeString;
  }
  
  public String organizationTreePart(String deleteNo)
  {
    String treeString = "";
    if (!getId().equals(deleteNo))
    {
      treeString = 
      


        "<li><input type=\"radio\" name=\"checks\" id=\"checks\" value=\"" + this.organizationNO + "\"> " + "<label id=\"" + this.organizationNO + "_organization\">" + this.name + "</label>";
      if ((this.organizationSet != null) && (this.organizationSet.size() > 0))
      {
        treeString = treeString + "<ul>";
        for (OrganizationTree organization : this.organizationSet) {
          treeString = treeString + organization.organizationTreePart(deleteNo);
        }
        treeString = treeString + "</ul>";
      }
      treeString = treeString + "</li>";
    }
    return treeString;
  }
  
  public String getId()
  {
    return getOrganizationNO();
  }
  
  public void setPrimary(String primary)
  {
    this.organizationNO = primary;
  }
  
  public String forZTree(String pId)
  {
    String treeString = "{'id':'" + this.organizationNO + "_O','pId':'" + pId + "','name':'" + this.name + "【O】','title':'O'}";
    if ((this.organizationSet != null) && (this.organizationSet.size() > 0)) {
      for (OrganizationTree organization : this.organizationSet) {
        treeString = treeString + "," + organization.forZTree(new StringBuilder(String.valueOf(this.organizationNO)).append("_O").toString());
      }
    }
    if ((this.brokerageSet != null) && (this.brokerageSet.size() > 0)) {
      for (BrokerageTree brokerage : this.brokerageSet) {
        treeString = treeString + "," + brokerage.forZTree(new StringBuilder(String.valueOf(this.organizationNO)).append("_O").toString());
      }
    }
    return treeString;
  }
}
