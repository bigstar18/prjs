package gnnt.MEBS.bankadded.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class Banks
  extends Clone
{
  private String bankId;
  private String bankName;
  
  @ClassDiscription(name="银行代码", key=true, keyWord=true)
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String bankId)
  {
    this.bankId = bankId;
  }
  
  @ClassDiscription(name="银行名称")
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public String getId()
  {
    return this.bankId;
  }
  
  public void setPrimary(String primary) {}
}
