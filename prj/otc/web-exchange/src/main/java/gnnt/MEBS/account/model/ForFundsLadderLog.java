package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.io.Serializable;

public class ForFundsLadderLog
  extends Clone
{
  private String customerNo;
  private String fundsLadderDescription;
  
  @ClassDiscription(name="客户名称", keyWord=true)
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  @ClassDiscription(name="各阶段的出金阈值比例")
  public String getFundsLadderDescription()
  {
    return this.fundsLadderDescription;
  }
  
  public void setFundsLadderDescription(String fundsLadderDescription)
  {
    this.fundsLadderDescription = fundsLadderDescription;
  }
}
