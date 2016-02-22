package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class CustomerStatus
  extends Clone
{
  private String customerNo;
  private String status;
  
  public String getId()
  {
    return this.customerNo;
  }
  
  @ClassDiscription(name="客户状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="已开户"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="U", value="未激活"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="正常"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="F", value="冻结"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="D", value="终止")})
  public String getStatus()
  {
    return this.status;
  }
  
  @ClassDiscription(name="交易商Id", key=true, keyWord=true)
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String firmId)
  {
    this.customerNo = firmId;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public void setPrimary(String primary)
  {
    this.customerNo = primary;
  }
}
