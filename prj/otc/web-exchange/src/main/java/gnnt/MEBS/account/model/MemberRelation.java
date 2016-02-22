package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class MemberRelation
  extends Clone
{
  private String memberNo;
  private String s_MemberNo;
  private Integer sortNo;
  
  @ClassDiscription(name="会员编号", key=true, keyWord=true)
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="特别会员编号", key=true, keyWord=true)
  public String getS_MemberNo()
  {
    return this.s_MemberNo;
  }
  
  public void setS_MemberNo(String s_MemberNo)
  {
    this.s_MemberNo = s_MemberNo;
  }
  
  @ClassDiscription(name="排序号")
  public Integer getSortNo()
  {
    return this.sortNo;
  }
  
  public void setSortNo(Integer sortNo)
  {
    this.sortNo = sortNo;
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
