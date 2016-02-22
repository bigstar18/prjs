package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;

public class MemberInfoOnly
  extends Clone
{
  private String id;
  private String name;
  
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
}
