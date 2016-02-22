package gnnt.MEBS.broke.model;

import java.io.Serializable;

public class BrokerageTree
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
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"brokerage\" value=\"" + this.brokerageNo + "\"> " + 
      " <img src=\"../../tree1/brokerage.gif\" align=\"absmiddle\"/><label id=\"" + this.brokerageNo + "_brokerage\" value=\"" + this.brokerageNo + "\">" + 
      this.name + "</label></li>";
    return treeString;
  }
  
  public String forZTree(String pId)
  {
    String s = "{'id':'" + this.brokerageNo + "_B','pId':'" + pId + "','name':'" + this.name + "【B】','title':'B'}";
    return s;
  }
}
