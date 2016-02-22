package gnnt.MEBS.announcement.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Set;

public class BrokerageNoticeTree
  extends Clone
{
  private String id;
  private String name;
  private Set<BrokerageTreeForNotice> brokerageSet;
  
  public Set<BrokerageTreeForNotice> getBrokerageSet()
  {
    return this.brokerageSet;
  }
  
  public void setBrokerageSet(Set<BrokerageTreeForNotice> brokerageSet)
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
  
  public String forTree()
  {
    String treeString = "";
    if ((this.brokerageSet != null) && (this.brokerageSet.size() > 0)) {
      for (BrokerageTreeForNotice brokerage : this.brokerageSet) {
        treeString = treeString + brokerage.forTree();
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
