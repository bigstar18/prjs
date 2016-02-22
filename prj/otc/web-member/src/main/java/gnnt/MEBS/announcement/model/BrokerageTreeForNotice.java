package gnnt.MEBS.announcement.model;

import java.io.Serializable;

public class BrokerageTreeForNotice
  implements Serializable
{
  private String brokerageNo;
  private String name;
  
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
  
  public String forTree()
  {
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"memberInfo\" value=\"" + this.brokerageNo + "\"> " + " <img src=\"../../tree1/brokerage.gif\" align=\"absmiddle\"/><label id=\"" + this.brokerageNo + "_memberInfo\">" + 
      this.name + "</label></li>";
    return treeString;
  }
}
