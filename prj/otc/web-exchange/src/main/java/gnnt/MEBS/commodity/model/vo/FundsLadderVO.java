package gnnt.MEBS.commodity.model.vo;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.commodity.model.FundsLadder;
import java.util.ArrayList;
import java.util.List;

public class FundsLadderVO
  extends Clone
{
  private String memberNo;
  private String memberName;
  private String description;
  private List<FundsLadder> fundsLadderList = new ArrayList();
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getMemberName()
  {
    if (this.memberName != null) {
      return this.memberName;
    }
    return this.memberNo;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public List<FundsLadder> getFundsLadderList()
  {
    return this.fundsLadderList;
  }
  
  public void setFundsLadderList(List<FundsLadder> fundsLadderList)
  {
    this.fundsLadderList = fundsLadderList;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  @ClassDiscription(name="出金阈值各阶梯值:")
  public String getDescription()
  {
    String msg = "";
    if ((this.fundsLadderList != null) && (this.fundsLadderList.size() > 0)) {
      for (FundsLadder fundsLadder : this.fundsLadderList) {
        msg = msg + "阶梯" + fundsLadder.getStepNo() + "的值为" + fundsLadder.getStepRate_log() + ";";
      }
    }
    return msg;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
}
