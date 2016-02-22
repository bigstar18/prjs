package gnnt.MEBS.firm.model;

public class MemberInfor
  extends Clone
{
  private String memberNo;
  private String notes;
  private String hrefaddress;
  
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
}
