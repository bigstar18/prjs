package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;

public class CompMember
  extends Clone
{
  private String id;
  private String sm_firmId;
  private Date statusChangeDate = new Date();
  private String firmName;
  private String status;
  
  @ClassDiscription(name="会员交易商Id", key=true, keyWord=true)
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String firmId)
  {
    this.id = firmId;
  }
  
  @ClassDiscription(name="默认特别会员Id")
  public String getSm_firmId()
  {
    return this.sm_firmId;
  }
  
  public void setSm_firmId(String sm_firmId)
  {
    this.sm_firmId = sm_firmId;
  }
  
  @ClassDiscription(name="会员交易商名称", keyWord=true)
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  @ClassDiscription(name="会员状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="已入会"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="U", value="未激活"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="正常"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="F", value="冻结"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="D", value="终止")})
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Date getStatusChangeDate()
  {
    return this.statusChangeDate;
  }
  
  public void setStatusChangeDate(Date statusChangeDate)
  {
    this.statusChangeDate = statusChangeDate;
  }
  
  public void setPrimary(String primary)
  {
    this.id = primary;
  }
}
