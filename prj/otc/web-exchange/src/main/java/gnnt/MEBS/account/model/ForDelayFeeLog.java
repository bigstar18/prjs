package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.io.Serializable;

public class ForDelayFeeLog
  extends Clone
{
  private String commodityId;
  private String customerNo;
  private String delayFeeDescription;
  
  @ClassDiscription(name="商品名称", keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="客户名称", keyWord=true)
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  @ClassDiscription(name="各阶段的延期费比例")
  public String getDelayFeeDescription()
  {
    return this.delayFeeDescription;
  }
  
  public void setDelayFeeDescription(String delayFeeDescription)
  {
    this.delayFeeDescription = delayFeeDescription;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
