package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class SpecialMemberStatus
  extends Clone
{
  private String s_memberNo;
  private String status;
  private BigDecimal netFL;
  
  public String getId()
  {
    return this.s_memberNo;
  }
  
  @ClassDiscription(name="持有净浮亏", key=true, keyWord=true)
  public BigDecimal getNetFL()
  {
    return formatDecimals(this.netFL, NumberDigits.NETFL);
  }
  
  public void setNetFL(BigDecimal netFL)
  {
    this.netFL = netFL;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  @ClassDiscription(name="特别会员状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="已入会"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="U", value="未激活"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="正常"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="F", value="冻结"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="D", value="终止")})
  public String getStatus()
  {
    return this.status;
  }
  
  @ClassDiscription(name="特别交易商Id", key=true, keyWord=true)
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public void setPrimary(String primary)
  {
    this.s_memberNo = primary;
  }
}
