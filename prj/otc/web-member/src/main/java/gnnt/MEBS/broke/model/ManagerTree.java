package gnnt.MEBS.broke.model;

import java.io.Serializable;

public class ManagerTree
  implements Serializable
{
  private String managerNo;
  private String name;
  
  public String getManagerNo()
  {
    return this.managerNo;
  }
  
  public void setManagerNo(String managerNo)
  {
    this.managerNo = managerNo;
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
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"manager\" value=\"" + this.managerNo + "\"> " + " <img src=\"../../tree1/manager.gif\" align=\"absmiddle\"/><label id=\"" + this.managerNo + "_manager\">" + 
      this.name + "</label></li>";
    return treeString;
  }
}
