package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class QMemberVO
  extends Clone
{
  private String memberno;
  private String name;
  
  public String getId()
  {
    return this.memberno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setMemberno(String memberNo)
  {
    this.memberno = memberNo;
  }
  
  public void setName(String memberName)
  {
    this.name = memberName;
  }
  
  public void setPrimary(String primary) {}
}
