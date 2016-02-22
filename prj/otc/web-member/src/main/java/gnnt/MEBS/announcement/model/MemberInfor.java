package gnnt.MEBS.announcement.model;

import gnnt.MEBS.base.model.Clone;

public class MemberInfor
  extends Clone
{
  private String memberNo;
  private String notes;
  private String hrefaddress;
  private String zshrefaddress;
  
  public String getZshrefaddress()
  {
    return this.zshrefaddress;
  }
  
  public void setZshrefaddress(String zshrefaddress)
  {
    this.zshrefaddress = zshrefaddress;
  }
  
  public String getHrefaddress()
  {
    return this.hrefaddress;
  }
  
  public void setHrefaddress(String hrefaddress)
  {
    this.hrefaddress = hrefaddress;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getNotes()
  {
    return this.notes;
  }
  
  public void setNotes(String notes)
  {
    this.notes = notes;
  }
  
  public String getId()
  {
    return this.memberNo;
  }
  
  public void setPrimary(String primary)
  {
    this.memberNo = primary;
  }
}
