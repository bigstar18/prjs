package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class CustomerHoldQty
  extends Clone
{
  private String commodityId;
  private String commodityName;
  private String memberNo;
  private String customerNo;
  private String customerName;
  private Integer oneMaxOrderQty;
  private Integer oneMinOrderQty;
  private Integer maxCleanQty;
  private Integer maxHoldQty;
  
  public CustomerHoldQty() {}
  
  public CustomerHoldQty(String commodityId, String commdityName, String customerNo, String memberNo, Integer oneMaxOrderQty, Integer oneMinOrderQty, Integer maxCleanQty, Integer maxHoldQty, String customerName)
  {
    this.commodityId = commodityId;
    this.commodityName = commdityName;
    this.memberNo = memberNo;
    this.customerNo = customerNo;
    this.oneMaxOrderQty = oneMaxOrderQty;
    this.oneMinOrderQty = oneMinOrderQty;
    this.maxCleanQty = maxCleanQty;
    this.maxHoldQty = maxHoldQty;
    this.customerName = customerName;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  @ClassDiscription(name="最大持仓量")
  public Integer getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(Integer maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }
  
  @ClassDiscription(name="商品代码", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="客户ID")
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  @ClassDiscription(name="商品名称", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  @ClassDiscription(name="所属会员ID")
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="单笔最大下单量", isStatus=false)
  public Integer getOneMaxOrderQty()
  {
    return this.oneMaxOrderQty;
  }
  
  public void setOneMaxOrderQty(Integer oneMaxOrderQty)
  {
    this.oneMaxOrderQty = oneMaxOrderQty;
  }
  
  @ClassDiscription(name="单笔最小下单量")
  public Integer getOneMinOrderQty()
  {
    return this.oneMinOrderQty;
  }
  
  public void setOneMinOrderQty(Integer oneMinOrderQty)
  {
    this.oneMinOrderQty = oneMinOrderQty;
  }
  
  @ClassDiscription(name="最大净持仓量")
  public Integer getMaxCleanQty()
  {
    return this.maxCleanQty;
  }
  
  public void setMaxCleanQty(Integer maxCleanQty)
  {
    this.maxCleanQty = maxCleanQty;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
